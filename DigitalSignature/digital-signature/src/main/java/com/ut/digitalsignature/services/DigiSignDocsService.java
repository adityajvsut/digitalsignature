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

        String url = "http://localhost:8000/some.html?email=" + userDetails.getEmail() + "&document_id=" + docidlist;
        return url;
    }

    public String digiSignDocs(DigiSignDocs userDetails) throws IOException {
        
        if(digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").isEmpty()){
            throw new UserDocumentNotFoundException("User "+userDetails.getEmail()+" is not linked for given agreement document "+userDetails.getDocument_id());
        }
        System.out.println(userDetails.getUserid());
        String imagepath = dao.findEmail(userDetails.getEmail()).get(0).getTtd_path();
        String pdfpath = userDetails.getDocument_id() + ".pdf";
        File file = new File(FilePath + pdfpath);
        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(14);
        PDImageXObject pdImage = PDImageXObject.createFromFile(FilePath + imagepath, doc);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
        contentStream.drawImage(pdImage, 337, 430, 203, 70);
        contentStream.close();
        doc.save(FilePath + pdfpath);
        doc.close();

        
        DigiSignDoc singeddoc =  digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").get(0);
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
    
    public Boolean checkSingedornot(DigiSignDocs userDetails) {

        if(digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").isEmpty()){
            throw new UserDocumentNotFoundException("User "+userDetails.getEmail()+" is not linked for given agreement document "+userDetails.getDocument_id());
        }
        return digidao.findValueByColumns("user_id",userDetails.getUserid(),"user_email",userDetails.getEmail(),"file_path",userDetails.getDocument_id()+".pdf").get(0).getSign_status();
    // return null;
    }

}