package com.ut.digitalsignature.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;

import com.ut.digitalsignature.dto.Request.BulkSignDocs;
import com.ut.digitalsignature.dto.Request.DigiSignDocs;
import com.ut.digitalsignature.dto.Request.DocumentJSONFile;
import com.ut.digitalsignature.dto.Request.JsonFile;
import com.ut.digitalsignature.dto.Request.RegisterUser;
import com.ut.digitalsignature.dto.Request.UploadFile;
import com.ut.digitalsignature.dto.Response.ResponseField;
import com.ut.digitalsignature.models.DigiSignUser;
import com.ut.digitalsignature.repositories.IGenericDao;
import com.ut.digitalsignature.services.BulkSignService;
import com.ut.digitalsignature.services.DigiSignDocsService;
import com.ut.digitalsignature.services.FileService;
import com.ut.digitalsignature.services.RegisterUserService;
import com.ut.digitalsignature.services.SendDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping(path = "/digisign")
public class DigisignController {

    IGenericDao<DigiSignUser> dao;

    @Autowired
    public void setDao(IGenericDao<DigiSignUser> daoToSet) {
        dao = daoToSet;
        dao.setClazz(DigiSignUser.class);
    }

    @Autowired
    SendDocumentService senddocumentService;

    @Autowired
    FileService fs;

    @Autowired
    RegisterUserService registeruserService;

    @Autowired
    BulkSignService bulksignService;

    @Autowired
    DigiSignDocsService digisignService;

    ResponseField responseField = new ResponseField();

    @PostMapping(path = "/senddocument", consumes = "multipart/form-data")
    public void sendDocument(@RequestPart("jsonfield") JsonFile<DocumentJSONFile> document,
            @RequestPart("file") MultipartFile file) throws Exception{
        senddocumentService.sendDocument(document.getJSONFile(), file);
    }

    @PostMapping(path = "/registeruser", consumes = "multipart/form-data")
    public ResponseEntity<Object> registerUser(@Valid @RequestPart("jsonfield") JsonFile<RegisterUser> userDetails,
            @Valid UploadFile file) throws Exception {
        registeruserService.registerUser(userDetails.getJSONFile(), file.getFile());
        return new ResponseEntity<Object>(responseField.setSuccess(), HttpStatus.OK);
    }

    @PostMapping(path = "/bulksign", consumes = "multipart/form-data")
    public String bulkSign(@RequestPart("jsonfield") JsonFile<BulkSignDocs> bulkDocs)
            throws IllegalStateException, IOException {
        return bulksignService.bulkSign(bulkDocs.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/bulksigndocs", consumes = "application/json")
    public void bulkSignDocs(@RequestBody JsonFile<BulkSignDocs> userDetails)
            throws IllegalStateException, IOException {
        bulksignService.bulkSignDocs(userDetails.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/digisigndocs", consumes = "application/json")
    public void digiSignDocs(@RequestBody JsonFile<DigiSignDocs> userDetails)
            throws IllegalStateException, IOException {
        System.out.println(userDetails);
        digisignService.digiSignDocs(userDetails.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/digisignsend", consumes = "application/json")
    public String digiSignSend(@RequestBody JsonFile<DigiSignDocs> userDetails)
            throws IllegalStateException, IOException {
        return digisignService.digiSignSend(userDetails.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/sendotp", consumes = "text/plain")
    public void digiSignSendOtp(@RequestBody String email) throws IllegalStateException, IOException {
        digisignService.digiSignSendOtp(email);
    }

    @CrossOrigin
    @PostMapping(path = "/verifyotp", consumes = "application/json")
    public String digiSignVerifyOtp(@RequestBody HashMap<String, String> otpDetails)
            throws IllegalStateException, IOException {
        System.out.println(otpDetails);
        return digisignService.digiSignVerifyOtp(otpDetails);
    }

    @CrossOrigin
    @PostMapping(path = "/test", consumes = "multipart/form-data")
    public String digitest(@ModelAttribute UploadFile file) throws IllegalStateException, IOException {
        // System.out.println(dao.findValueByColumns("email", "abc@abc.com", "name",
        // "asdsads", "user_id", "admin@gmail.com"));

        // HttpHeaders headers = new HttpHeaders();
        // headers.set("Authorization","Basic YWRtaW46cGFzc3dvcmQ=");
        // System.out.println("Hii");
        // RestTemplate restTemplate = new RestTemplate();
        // HttpEntity<?> entity = new HttpEntity<>(headers);
        // restTemplate.exchange("http://localhost:9012/digisign/callback?id={id}&status={status}",
        // HttpMethod.GET,
        // entity,
        // String.class,
        // "13faaa",
        // "complete"
        // );
        // fs.saveImage(file.getFile());
        try {
            String encryptionKeyString = "digisignsecret12";
            String originalMessage = "abc@gmail.com";
            byte[] encryptionKeyBytes = encryptionKeyString.getBytes();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Cipher cipher2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());
            String encr = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            System.out.println(encr);

            cipher2.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encrbytes= Base64.getDecoder().decode(encr);
            byte[] decryptedMessageBytes = cipher2.doFinal(encrbytes);
            String str = new String(decryptedMessageBytes);
            System.out.println(str);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return file.getFile().getContentType();
    }


    @CrossOrigin
    @GetMapping(path = "/callback")
    public String digicallback(@RequestParam("id") String id,@RequestParam("status") String status) throws IllegalStateException, IOException
    {
        System.out.println(status);
         
        // DigiSignUser digi = listdigi.get(0);
        // digi.setEmail("acd@gmail.com");
        // dao.update(digi);

        return "Hii";
    }

    @CrossOrigin
    @PostMapping(path = "/checkSigned", consumes = "application/json")
    public Boolean checkUserSignedDoc(@RequestBody JsonFile<BulkSignDocs> userDetails)
            throws IllegalStateException, IOException
    {
        return bulksignService.checkSingedornot(userDetails.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/checkUserSigned", consumes = "application/json")
    public HashMap<String, String> checkUserSignedDocument(@RequestBody JsonFile<DigiSignDocs> userDetails)
            throws IllegalStateException, IOException
    {
        return digisignService.checkSingedornot(userDetails.getJSONFile());
    }

    @CrossOrigin
    @PostMapping(path = "/saveUserSignImage")
    public String saveSign(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws Exception {
        System.out.println("Entered" + file); 
        DigiSignUser dsu = dao.findValueByColumn("email", email).get(0);
        System.out.println("filename " + file.getOriginalFilename());
        String path = fs.saveImage(file.getOriginalFilename()+dsu.getName(),file);
        
        dsu.setSign_path(path);
        dao.update(dsu);

        return "uploaded";
    }



}