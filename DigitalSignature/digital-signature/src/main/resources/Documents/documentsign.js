$(document).ready(function(){
  document.getElementById("wrong_otp").style.display = "none";
  document.getElementById("statusfail").style.display = "none";
  document.getElementById("sendotpsuccess").style.display = "none";
  document.getElementById("statussuccess").style.display = "none";
  var canvas = document.getElementById("signaturedraw");
  var signaturePad = new SignaturePad(canvas, {
    // It's Necessary to use an opaque color when saving image as JPEG;
    // this option can be omitted if only saving as PNG or SVG
    backgroundColor: 'rgb(255, 255, 255)',
  });
  window.addEventListener("resize", resizeCanvas);
  signaturePad.on();
  // $("#wrong_otp").hide();
  
    checkSigned();
    
    $(".close-modal").click(function(){
      $("#myModal").modal('hide');
      location.reload();      
  });
      $("#processSign").click(function(){
          $("#myModal").modal({backdrop:'static', keyboard:false});
          // $('.modal-content').resizable({});
      });
      $("#sendotp").click(function(){
        document.getElementById("send-otpmodal").style.display = "none";
        document.getElementById("verifyotp").style.display = "block";
        documents = getParams(window.location.href);
        sendotp(documents['email']);
      });
      $("#resendotp").click(function(){
        // document.getElementById("send-otpmodal").style.display = "none";
        // document.getElementById("verifyotp").style.display = "block";
        documents = getParams(window.location.href);
        sendotp(documents['email']);
        document.getElementById("sendotpsuccess").style.display = "block";
      });
      $("#veriotp").click(function(){
        console.log(document.getElementById("otp").value)
        // signed();
        documents = getParams(window.location.href);
        data = {
            "email" : documents['email'],
            "otp" : document.getElementById("otp").value
        }
        console.log(data);
        console.log(documents);
        verifyotp(JSON.stringify(data));
        console.log("completed otp verif");
        document.getElementById("signsubmit").style.display = "none";
        // if(documents["ttd"]=="mt"){
        // document.getElementById("verifyotp").style.display = "none";
        // document.getElementById("signdraw").style.display = "block";
        // }

      });

      $("#signclear").click(function(){
        signaturePad.clear();
        document.getElementById("signsubmit").style.display = "none";
      });

      $("#signsave").click(function(){

          // uploadSignature('image/jpeg');

          if (signaturePad.isEmpty()) {
            alert("Please provide a signature first.");
          } else {

          
          var dataurl = signaturePad.toDataURL("image/jpeg");
          uploadSignature(dataurl);
          document.getElementById("signsubmit").style.display = "block";
          // console.log(signaturePad.toDataURL("image/jpeg"));
          // const data = signaturePad.toData();
          // console.log(data);
          // console.log("======================");
          // var canvas = document.getElementById("signaturedraw");
          // console.log(canvas);
          // canvas.toBlob(function(blob) {
          //   saveAs(blob, "./image.png");
          // });
        //signed();
          }
      });
      $("#signsubmit").click(function(){
        signed();
      });
      
  });

  function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
  }

  function uploadSignature(dataurl) {
    
    var blobdata = dataURLtoBlob(dataurl);

    documents = getParams(window.location.href);
    var mail = documents["email"];
    var filename = documents["document_id"].concat(".jpg");
    console.log(mail+filename);
    console.log("=================");

    console.log(blobdata);
  
    var fd = new FormData();
    fd.append("file", blobdata, filename);
    fd.append("email", mail);
    
    console.log("hiiiiiii");
    console.log(fd);
  
    var xhr = new XMLHttpRequest();
      xhr.withCredentials = true;
  
      xhr.onreadystatechange = function() {
          console.log("hii");
          if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            // document.getElementById("signsubmit").style.disabled = "false";
            console.log("disabled");
          }
      };
      xhr.open("POST", "http://localhost:9012/digisign/saveUserSignImage");
      xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
      xhr.setRequestHeader("Accept", "*/*");
      xhr.send(fd);
  }

  function resizeCanvas() {
    var ratio =  Math.max(window.devicePixelRatio || 1, 1);
    canvas.width = canvas.offsetWidth * ratio;
    canvas.height = canvas.offsetHeight * ratio;
    canvas.getContext("2d").scale(ratio, ratio);
    // otherwise isEmpty() might return incorrect value
}

  var getParams = function (url) {
    var params = {};
    var parser = document.createElement('a');
    parser.href = url;
    var query = parser.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
      var pair = vars[i].split('=');
      params[pair[0]] = decodeURIComponent(pair[1]);
    }
    return params;
  };

  function verifyotp(data){
    documents = getParams(window.location.href);
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onreadystatechange = function() {
        console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          console.log(this.responseText);
          if(this.responseText == "otp verified"){

            if(documents["sign_type"]=="mt"){
              document.getElementById("wrong_otp").style.display = "none";
              document.getElementById("verifyotp").style.display = "none";            
              document.getElementById("signdraw").style.display = "block";
            }else{
              document.getElementById("verifyotp").style.display = "none";
              document.getElementById("signdraw").style.display = "none";
              signed();
            }
          }else{
          document.getElementById("wrong_otp").style.display = "block";
          document.getElementById("signdraw").style.display = "none";
          document.getElementById("sendotpsuccess").style.display = "none";
        }
      }    
    };
    xhr.open("POST", "http://localhost:9012/digisign/verifyotp");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);
    // signed();

    }
  function sendotp(email){

    var data = email;
    // console.log(document.querySelector('#veriotp').value)
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onreadystatechange = function() {
        console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          console.log(this.responseText);
        }        
    };
    xhr.open("POST", "http://localhost:9012/digisign/sendotp");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);

  }

  function signed(){
    documents = getParams(window.location.href);
    console.log(documents);
    documentjson = {
    "JSONFile" : {
      "userid" : "admin@gmail.com",
      "email" : documents["email"],
      "document_id": documents["document_id"]
      }
    }
    var data = JSON.stringify(documentjson);
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;


    xhr.onreadystatechange = function() {
        console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          console.log(this.responseText);
          // $("#myModal").modal('hide');
          
          // alert("Signed");
          document.getElementById("signdraw").style.display = "none";
          document.getElementById("statusfail").style.display = "none"; 
          document.getElementById("statussuccess").style.display = "block";          
          document.getElementById("processSign").style.display = "none";
          
          // location.reload();
        }else if (this.status == 400){
          document.getElementById("signdraw").style.display = "none";
          document.getElementById("statussuccess").style.display = "none";  
          document.getElementById("statusfail").style.display = "block";
        }
    };
    xhr.open("POST", "http://localhost:9012/digisign/digisigndocs");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);
  }

  function checkSigned(){
    documents = getParams(window.location.href);
    console.log(documents);
    documentjson = {
    "JSONFile" : {
      "userid" : "admin@gmail.com",
      "email" : documents["email"],
      "document_id": documents["document_id"]
      }
    }
    var data = JSON.stringify(documentjson);
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onreadystatechange = function() {
        console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          var responseData = JSON.parse(xhr.response);
          console.log(responseData["signStatus"]);
          if(responseData["signStatus"]=="true"){
            document.getElementById("processSign").style.display = "none";
          }
        }
    };
    xhr.open("POST", "http://localhost:9012/digisign/checkUserSigned");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);
  }