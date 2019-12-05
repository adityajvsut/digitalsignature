package com.ut.digitalsignature.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.ut.digitalsignature.dto.Request.DigiSignDocs;
import com.ut.digitalsignature.models.DigiSignUser;
import com.ut.digitalsignature.models.emailotp;
import com.ut.digitalsignature.repositories.IGenericDao;
import com.ut.digitalsignature.exceptions.UserDocumentNotFoundException;
import com.ut.digitalsignature.models.DigiSignDoc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DigiSignDocsService {

    IGenericDao<DigiSignUser> dao;
    IGenericDao<emailotp> otpdao;
    IGenericDao<DigiSignDoc> digidao;

    @Autowired
    public void setDao(IGenericDao<DigiSignUser> daoToSet) {
        dao = daoToSet;
        dao.setClazz(DigiSignUser.class);
    }

    @Autowired
    public void setDao2(IGenericDao<emailotp> daoToSetemail) {
        otpdao = daoToSetemail;
        otpdao.setClazz(emailotp.class);
    }

    @Autowired
    public void setDao3(IGenericDao<DigiSignDoc> daoToSetdigi) {
        digidao = daoToSetdigi;
        digidao.setClazz(DigiSignDoc.class);
    }

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${documents.filepath}")
    String FilePath;

    public String digiSignSend(DigiSignDocs userDetails) throws IllegalStateException, IOException {
        String docidlist = userDetails.getDocument_id();

        if(digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").isEmpty()){
            throw new UserDocumentNotFoundException("User "+userDetails.getEmail()+" is not linked for given agreement document "+userDetails.getDocument_id());
        }
        DigiSignDoc doc = digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").get(0);
       
        String url = "http://localhost:8000/documentsign.html?email=" + userDetails.getEmail() + "&document_id=" + docidlist+"&sign_type="+doc.getSign_type();
        return url;
    }

    public String digiSignDocs(DigiSignDocs userDetails) throws IOException {
        
        if(digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").isEmpty()){
            throw new UserDocumentNotFoundException("User "+userDetails.getEmail()+" is not linked for given agreement document "+userDetails.getDocument_id());
        }

        DigiSignDoc singeddoc =  digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").get(0);


        String imagepath = "";
        if(singeddoc.getSign_type().equals("mt")) 
            imagepath = dao.findEmail(userDetails.getEmail()).get(0).getSign_path();
        else
            imagepath = dao.findEmail(userDetails.getEmail()).get(0).getTtd_path();

        System.out.println(singeddoc.getSign_type() +"uid"+ imagepath);
        String pdfpath = userDetails.getDocument_id() + ".pdf";
        int pageNumber = Integer.parseInt(singeddoc.getPage());
        
        float llx = Float.parseFloat(singeddoc.getLlx());
        float lly = Float.parseFloat(singeddoc.getLly());
        float urx = Float.parseFloat(singeddoc.getUrx());
        float ury = Float.parseFloat(singeddoc.getUry());

        File file = new File(FilePath + pdfpath);
        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(pageNumber-1);
        PDImageXObject pdImage = PDImageXObject.createFromFile(FilePath + imagepath, doc);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
        contentStream.drawImage(pdImage, llx, lly, urx-llx, ury-lly);
        contentStream.close();
        doc.save(FilePath + pdfpath);
        doc.close();        
        
        System.out.println("Signed doc"+singeddoc);
        singeddoc.setSign_status(true);
        digidao.update(singeddoc);

        return "hii";
    }

    public void digiSignSendOtp(String email) { 
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        String numbers = "0123456789";
        int count = 4;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * numbers.length());
            builder.append(numbers.charAt(character));
        }
        String otpsend = builder.toString();

        msg.setSubject("OTP Verification");
        msg.setText("Your otp is " + otpsend);
        
        emailotp eotp;
        if (otpdao.findEmail(email).isEmpty()){
            eotp = new emailotp();
        }else{
        eotp = otpdao.findEmail(email).get(0);
        }
        eotp.setEmail(email);
        eotp.setOtp(otpsend);
        otpdao.update(eotp);

        javaMailSender.send(msg);

	}

	public String digiSignVerifyOtp(HashMap<String,String> otpDetails) {
        String veriotp = otpDetails.get("otp");
        String mail = otpDetails.get("email");

        String otp = otpdao.findEmail(mail).get(0).getOtp();
        if (veriotp.equals(otp)){
            return "otp verified";
        }else{
            return "incorrect otp";
        }

    }
    
    public HashMap<String, String> checkSingedornot(DigiSignDocs userDetails) {

        if(digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").isEmpty()){
            throw new UserDocumentNotFoundException("User "+userDetails.getEmail()+" is not linked for given agreement document "+userDetails.getDocument_id());
        }
        DigiSignDoc dSignDoc =  digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").get(0);
        HashMap<String,String> responseData = new HashMap<>();
        responseData.put("signType", dSignDoc.getSign_type());
        responseData.put("signStatus", dSignDoc.getSign_status().toString());

        return responseData;
    // return null;
    }

}