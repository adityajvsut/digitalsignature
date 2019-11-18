package com.ut.digitalsignature.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ut.digitalsignature.dto.Request.DocumentJSONFile;
import com.ut.digitalsignature.dto.Request.DocumentReqSign;
import com.ut.digitalsignature.models.DigiSignDoc;
import com.ut.digitalsignature.repositories.IGenericDao;
import com.ut.digitalsignature.exceptions.ColumnValueNotFoundException;
import com.ut.digitalsignature.models.DigiSignUser;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SendDocumentService {

  IGenericDao<DigiSignDoc> dao;

  @Autowired
  public void setDao(IGenericDao<DigiSignDoc> daoToSet) {
    dao = daoToSet;
    dao.setClazz(DigiSignDoc.class);
  }

  IGenericDao<DigiSignUser> userdao;

  @Autowired
  public void setDao2(IGenericDao<DigiSignUser> daoToSet) {
    userdao = daoToSet;
    userdao.setClazz(DigiSignUser.class);
  }

  @Value("${documents.filepath}")
  String filePath;

  public void sendDocument(DocumentJSONFile document, MultipartFile docu) throws IllegalStateException, IOException {
    if(!dao.findValueByColumn("file_path",document.getDocument_id()+".pdf").isEmpty()) throw new ColumnValueNotFoundException("Document "+document.getDocument_id()+" already exists");
        
    List<DocumentReqSign> listusers = document.getReq_sign();
    for (int i = 0; i < listusers.size(); i++) {
      if(userdao.findValueByColumn("email", listusers.get(i).getEmail()).isEmpty()){
        throw new ColumnValueNotFoundException("User with email "+listusers.get(i).getEmail()+" not registered");
      }
    }
    System.out.println(docu.getContentType());
    if(!docu.getContentType().equals("application/pdf")){
      throw new ColumnValueNotFoundException("Document should be pdf");
    }
    String FilePath = filePath+document.getDocument_id()+".pdf";
    File savefile = new File(FilePath);
    docu.transferTo(savefile);

    for (int i = 0; i < listusers.size(); i++) {
      DigiSignDoc digisignDoc = new DigiSignDoc();
      digisignDoc.setFile_path(document.getDocument_id()+".pdf");
      digisignDoc.setUser_id(document.getUserid());
      digisignDoc.setUser_email(listusers.get(i).getEmail());
      digisignDoc.setUser_name(listusers.get(i).getName());
      digisignDoc.setMobile_number(userdao.findValueByColumn("email", listusers.get(i).getEmail()).get(0).getMobile_number());
      digisignDoc.setPage(listusers.get(i).getPage());
      digisignDoc.setLlx(listusers.get(i).getLlx());
      digisignDoc.setLly(listusers.get(i).getLly());
      digisignDoc.setUrx(listusers.get(i).getUrx());
      digisignDoc.setUry(listusers.get(i).getUry());
      digisignDoc.setSign_type(listusers.get(i).getAksi_ttd());
      digisignDoc.setSign_status(false);
      dao.save(digisignDoc);
    }
  }
}