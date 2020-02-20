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


$( document ).ready(function() {

    window.documents = getParams(window.location.href);
    var doclist = documents["document_id"];
    console.log(doclist);
    Array.from(JSON.parse(doclist)).forEach(buttonCreate);
    CheckSigned();

});

function buttonCreate(value, index, array) {
  var listbtn = document.createElement("li");
  var btn = document.createTextNode(value);
  var foo = document.getElementById("fooBar");
  foo.appendChild(listbtn);
  listbtn.appendChild(btn);
}

function signed(){
  documents = getParams(window.location.href);
  lender = documents['user'];
  console.log(documents["document_id"]);
  delete documents["user"];
  documentjson = {
	"JSONFile" : {
		"userid" : "admin@gmail.com",
		"email" : lender,
		"document_id": JSON.parse(documents["document_id"])
	  }
  }
  console.log(documentjson);
  var data = JSON.stringify(documentjson);
  var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("sign").style.display = "none";
          var myList = document.getElementById('fooBar');
          myList.innerHTML = '';
          var listbtn = document.createElement("li");
          var btn = document.createTextNode("Signatures Done");
          var foo = document.getElementById("fooBar");
          foo.appendChild(listbtn);
          listbtn.appendChild(btn);


    console.log(this.responseText);}

};

xhr.open("POST", "http://localhost:9012/digisign/bulksigndocs");
xhr.setRequestHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXIiLCJwYXNzd29yZCI6InBhc3MifQ.jouO1S9Kk-_aYwesErRBTf0Di10XSWD2g70neWtGUoA");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("Accept", "*/*");


xhr.send(data);


}

function CheckSigned(){
  documents = getParams(window.location.href);
  lender = documents['user'];
  console.log(lender);
  delete documents["user"];
  doculist = [];
  for(var key in documents){
    doculist.push(documents[key]);
  }
  documentjson = {
	"JSONFile" : {
		"userid" : "admin@gmail.com",
		"email" : lender,
		"document_id": doculist
	  }
  }
  console.log(documentjson);
  var data = JSON.stringify(documentjson);
  console.log(data);
  var xhr = new XMLHttpRequest();
xhr.withCredentials = true;
xhr.open("POST", "http://localhost:9012/digisign/checkSigned");
xhr.setRequestHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXIiLCJwYXNzd29yZCI6InBhc3MifQ.jouO1S9Kk-_aYwesErRBTf0Di10XSWD2g70neWtGUoA  ");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("Accept", "*/*");

xhr.send(data);
xhr.onreadystatechange = function() {
    console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          if(this.responseText == "true"){
          document.getElementById("sign").style.display = "none";
          console.log(this.responseText);
        }
        else{
      document.getElementById("sign").style.display = "block";
    }
};

}
}