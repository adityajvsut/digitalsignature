package com.ut.digitalsignature.services;

import com.ut.digitalsignature.dto.Request.RegisterUser;
import com.ut.digitalsignature.exceptions.CustomerAlreadyExistsException;
import com.ut.digitalsignature.models.DigiSignUser;
import com.ut.digitalsignature.repositories.IGenericDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegisterUserService {

  IGenericDao<DigiSignUser> dao;

  @Autowired
  public void setDao(IGenericDao<DigiSignUser> daoToSet) {
    dao = daoToSet;
    dao.setClazz(DigiSignUser.class);
  }

  @Value("${documents.filepath}")
  String filePath;

  @Autowired
  FileService fileService;

  DigiSignUser digiSignUser = new DigiSignUser();

  public void registerUser(RegisterUser userDetails, MultipartFile signFile) throws Exception{
      if(!dao.findValueByColumns("user_id",userDetails.getUser_id(),"email",userDetails.getEmail(),
      "name",userDetails.getName()).isEmpty())throw new CustomerAlreadyExistsException("Customer Already Exists");
      String path = fileService.saveImage(userDetails.getEmail(), signFile);
      digiSignUser.setUserDetails(userDetails,path);
      dao.save(digiSignUser);
    }

}