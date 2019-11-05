package com.ut.digitalsignature.services;

import java.io.File;
import java.io.IOException;

import com.ut.digitalsignature.dto.Request.RegisterUser;
import com.ut.digitalsignature.exceptions.ColumnValueNotFoundException;
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

    public RegisterUser registerUser(RegisterUser userDetails, MultipartFile signFile)
            throws IllegalStateException, IOException {
        System.out.println(signFile.getContentType());
        if(!(signFile.getContentType().equals("image/png") || signFile.getContentType().equals("image/jpeg"))){
          throw new ColumnValueNotFoundException("File should be image jpg or png");
        }
        String FilePath = filePath+ signFile.getOriginalFilename();
        File savefile = new File(FilePath);
        if(!dao.findValueByColumns("user_id",userDetails.getUser_id(),"email",userDetails.getEmail(),"name",userDetails.getName()).isEmpty())throw new CustomerAlreadyExistsException("Customer Already Exists");
        signFile.transferTo(savefile);
        DigiSignUser digiSignUser = new DigiSignUser();
        digiSignUser.setUserDetails(userDetails);
        digiSignUser.setTtd_path(signFile.getOriginalFilename());
        dao.save(digiSignUser);
        return userDetails;
    }

}