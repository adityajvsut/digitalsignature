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
​
$( document ).ready(function() {
​
    documents = getParams(window.location.href);
    delete documents["user"];
    for(var key in documents){
      console.log(documents[key]);
      var listbtn = document.createElement("li");
      var btn = document.createElement("BUTTON");
      btn.innerHTML = documents[key];
      var foo = document.getElementById("fooBar");
  //Append the element in page (in span).  
    foo.appendChild(listbtn);
    listbtn.appendChild(btn);
      }
​
      console.log( "ready!" );
  CheckSigned();
​
});
​
function signed(){
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
  var xhr = new XMLHttpRequest();
xhr.withCredentials = true;
​
xhr.onreadystatechange = function() {
    console.log("hii");
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("sign").style.display = "none";
    console.log(this.responseText);}
​
};
​
xhr.open("POST", "http://localhost:9012/digisign/bulksigndocs");
xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("Accept", "*/*");
​
​
xhr.send(data);
​
​
}
​
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
xhr.setRequestHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("Accept", "*/*");
​
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
​
}
}