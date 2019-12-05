package com.ut.digitalsignature.services;

import java.util.List;

import com.ut.digitalsignature.dto.Request.DocumentJSONFile;
import com.ut.digitalsignature.dto.Request.DocumentReqSign;
import com.ut.digitalsignature.exceptions.ColumnValueNotFoundException;
import com.ut.digitalsignature.models.DigiSignDoc;
import com.ut.digitalsignature.models.DigiSignUser;
import com.ut.digitalsignature.repositories.IGenericDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  FileService fileService;

  @Autowired
  DigiSignDoc digisignDoc;

  public void sendDocument(@Autowired DocumentJSONFile document, MultipartFile docu) throws Exception {
    String filename = fileService.savePdf(document.getDocument_id(), docu);
    List<DocumentReqSign> listusers = document.getReq_sign();
    for (int i = 0; i < listusers.size(); i++) {
      if(userdao.findValueByColumn("email", listusers.get(i).getEmail()).isEmpty()){
        throw new ColumnValueNotFoundException("User with email "+listusers.get(i).getEmail()+" not registered");
      }
    }
    listusers.forEach(users -> digisignDoc.setDocumentData(document.getUserid(), users, filename));
  }
}