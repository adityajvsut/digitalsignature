$(document).ready(function(){
    checkSigned();
      $("#processSign").click(function(){
          $("#myModal").modal('show');
          $('.modal-dialog').draggable();
          $('.modal-content').resizable({});
      });
      $("#sendotp").click(function(){
        document.getElementById("send-otpmodal").style.display = "none";
        document.getElementById("verifyotp").style.display = "block";
        documents = getParams(window.location.href);
        sendotp(documents['email']);
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
        if(documents["ttd"]=="mt"){
        document.getElementById("verifyotp").style.display = "none";
        document.getElementById("signdraw").style.display = "block";
        }

      });
      
  });

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

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onreadystatechange = function() {
        console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          console.log(this.responseText);
          signed();
        }        
    };
    xhr.open("POST", "http://localhost:9012/digisign/verifyotp");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);
    //signed();

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
    // var xhr = new XMLHttpRequest();
    // xhr.withCredentials = true;

    // xhr.onreadystatechange = function() {
    //     console.log("hii");
    //     if (this.readyState == 4 && this.status == 200) {
          console.log(this.responseText);
          $("#myModal").modal('hide');
          
          alert("Signed");
          document.getElementById("processSign").style.display = "none";
          
          //location.reload();
    //     }
    // };
    // xhr.open("POST", "http://localhost:9012/digisign/digisigndocs");
    // xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    // xhr.setRequestHeader("Content-Type", "application/json");
    // xhr.setRequestHeader("Accept", "*/*");
    // xhr.send(data);
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
          if(this.responseText=="true"){
            document.getElementById("processSign").style.display = "none";
          }
          console.log(this.responseText);
        }
    };
    xhr.open("POST", "http://localhost:9012/digisign/checkUserSigned");
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "*/*");
    xhr.send(data);
  }