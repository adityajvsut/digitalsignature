package com.ut.digitalsignature.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ut.digitalsignature.dto.Request.BulkSignDocs;
import com.ut.digitalsignature.models.DigiSignDoc;
import com.ut.digitalsignature.models.DigiSignUser;
import com.ut.digitalsignature.repositories.IGenericDao;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BulkSignService {

    IGenericDao<DigiSignUser> dao;
    IGenericDao<DigiSignDoc> daodigi;

    @Autowired
    public void setDao(IGenericDao<DigiSignUser> daoToSet) {
      dao = daoToSet;
      dao.setClazz(DigiSignUser.class);
    }
    @Autowired
    public void setDao2(IGenericDao<DigiSignDoc> daoToSetDigi) {
      daodigi = daoToSetDigi;
      daodigi.setClazz(DigiSignDoc.class);
    }

    
    @Value("${documents.filepath}")
    String FilePath;

    @Value("${frontend.url}")
    String url1;

    public String bulkSign(BulkSignDocs userDetails)
            throws IllegalStateException, IOException {
        List<String> docidlist = userDetails.getDocument_id();
        String url = url1+"user="+userDetails.getEmail()+"&";
        for (int i = 0; i < docidlist.size(); i++) {
            url += "document"+String.valueOf(i)+"=";
            url += docidlist.get(i)+"&";
        }
        return url.substring(0, url.length()-1);
    }

    public String bulkSignDocs(BulkSignDocs userDetails) throws IOException {
        List<String> docidlist = userDetails.getDocument_id();
        String imagepath = dao.findValueByColumn("email",userDetails.getEmail()).get(0).getTtd_path();
        for (int i = 0; i < docidlist.size(); i++) {
            DigiSignDoc singeddoc =  daodigi.findValueByColumn("user_email",userDetails.getEmail()).get(0);
            
            int pageNumber = Integer.parseInt(singeddoc.getPage());
            float llx = Float.parseFloat(singeddoc.getLlx());
            float lly = Float.parseFloat(singeddoc.getLly());
            float urx = Float.parseFloat(singeddoc.getUrx());
            float ury = Float.parseFloat(singeddoc.getUry());

            String pdfpath = userDetails.getDocument_id().get(i)+".pdf";
            File file = new File(FilePath+pdfpath);
            PDDocument doc = PDDocument.load(file);
            PDPage page = doc.getPage(pageNumber-1);
            PDImageXObject pdImage = PDImageXObject.createFromFile(FilePath+imagepath, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page,AppendMode.APPEND,true);
            contentStream.drawImage(pdImage, llx, lly, urx-llx, ury-lly);
            contentStream.close();
            doc.save(FilePath+pdfpath);
            doc.close();

            
            System.out.println("Signed doc"+singeddoc);
            singeddoc.setSign_status(true);
            daodigi.save(singeddoc);

        }
        return "hii";
    }

    public Boolean checkSingedornot(BulkSignDocs userDetails){
        System.out.println(userDetails.getUserid());
        List<String> docidlist = userDetails.getDocument_id();
        // String imagepath = dao.findValueByColumn("email",userDetails.getEmail()).get(0).getTtd_path();
        Boolean signed = false;
        for (int i = 0; i < docidlist.size(); i++) {
            // String documentId = userDetails.getDocument_id().get(i);

            signed = daodigi.findValueByColumn("user_email",userDetails.getEmail()).get(0).getSign_status();

            if(signed == false)
                return false;
        }
        return signed;
    }
}

