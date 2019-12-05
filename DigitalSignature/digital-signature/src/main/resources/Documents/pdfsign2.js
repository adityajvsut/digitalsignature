$("#pdf-loader").show();
		$("#pdf-canvas").hide();
		$('#sgn-user').hide();

		
		var __PDF_DOC,
			__CURRENT_PAGE,
			__TOTAL_PAGES,
			__PAGE_RENDERING_IN_PROGRESS = 0,
			__CANVAS = $('#pdf-canvas').get(0),
			__CANVAS_CTX = __CANVAS.getContext('2d');
		
		var dataUser = {"idDoc":"1972254","user":[{"idAccess":"4140311","lx":"337.0","ly":"430.0","rx":"540.0","ry":"500.0","page":15,"type":"sign","sgn":"0"}],"refTrx":"SGN20191203140421000193"};
		var jsonText = dataUser.user;
		
		usersign.usersign = $("#inputUser").val();
		
		var last=0;
		function initData(){
			var html="";
			for (x in jsonText) {
				//jsonText[x].sgn="0";
				if(jsonText[x].type=="sign"){ 
					//$domainapi/img/sign.png <img src="$domainapi/img/sign.png" height="24" width="20"/>
					//$domainapi/img/initial.png <img src="$domainapi/img/initial.png" height="24" width="20"/>
					html+="<div id='sgn-user-"+x+"' class='sign-box' style='postion :static; vertical-align: middle;' ><a align='center' id='sgnClick-"+x+"' href='javascript:void(0)' onClick='signProcess("+x+");' style='text-align:center;'> <img src='https://api.digisign.id/img/sign.png' height='37%' width='37%'/> </a>		<img  id='sgnImg-"+x+"' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQAQAAAACoxAthAAABzUlEQVR42u3bS3aDMAwFUHnEMliqWSrLYISKbUnIFNpkaPEY5DSJb0Y6tj4u8dcPgYCAgICAgICAgIxIdpJnYl5mri87Zd6I8qrfZZCopL1fpwPPZWFdLZ91S0AikhIXuXy9EKVjYXupn5VwmkBeQGrApOaW8heDvIfYnqEHSAZ5AbHDQpyuzh+cLyCDE00Ud1toL//lliCDk8uz+0Txs7oSZFxiB4NGDUmlyC1faEkDSFRyREiy7oC0CM6f8fEDEo+4pLC0CM63dWH9GQaJSdhtCK1FYJnh03YBEoZIY4DvWgS+RwgSkdQwkeNgkXaAlQ7niQESlDBvfY5omYOrD0FCElkojcLWGJDdI19rRpBoRDcJawSTHRYtR1x/NYdBwhBNEFqFUO8FXG4D3MYYSBCivSGZCmg3eO+yBJC4JPly0UYDZ6MQJDKxATCllh66yfDjdgEyPulmQhYmvLo9Iz3OkkAGJ91QuHWEustg+banBBKFnJc+pWCoEbJJwND8xx0SkPHJ6iZBOh2a+x4hSHzSMkPS0NFuIchLSD8UrjXj7YUQkDDEXQSlcxi4u4vhIGGJvwiqVSH5/wNgkKjkmwcEBAQEBAQEBARkLPIDz0jn0miurckAAAAASUVORK5CYII=' style='width:auto;height:auto;display:none;max-height:100%;' /></div>";
				}
				else {
					html+="<div id='sgn-user-"+x+"' class='sign-box' style='postion :static; vertical-align: middle;' ><a align='center' id='sgnClick-"+x+"' href='javascript:void(0)' onClick='signProcess("+x+");' style='text-align:center;'> <img src='https://api.digisign.id/img/initial.png' height='37%' width='37%'/> </a>		<img  id='sgnImg-"+x+"' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQAQAAAACoxAthAAABzUlEQVR42u3bS3aDMAwFUHnEMliqWSrLYISKbUnIFNpkaPEY5DSJb0Y6tj4u8dcPgYCAgICAgICAgIxIdpJnYl5mri87Zd6I8qrfZZCopL1fpwPPZWFdLZ91S0AikhIXuXy9EKVjYXupn5VwmkBeQGrApOaW8heDvIfYnqEHSAZ5AbHDQpyuzh+cLyCDE00Ud1toL//lliCDk8uz+0Txs7oSZFxiB4NGDUmlyC1faEkDSFRyREiy7oC0CM6f8fEDEo+4pLC0CM63dWH9GQaJSdhtCK1FYJnh03YBEoZIY4DvWgS+RwgSkdQwkeNgkXaAlQ7niQESlDBvfY5omYOrD0FCElkojcLWGJDdI19rRpBoRDcJawSTHRYtR1x/NYdBwhBNEFqFUO8FXG4D3MYYSBCivSGZCmg3eO+yBJC4JPly0UYDZ6MQJDKxATCllh66yfDjdgEyPulmQhYmvLo9Iz3OkkAGJ91QuHWEustg+banBBKFnJc+pWCoEbJJwND8xx0SkPHJ6iZBOh2a+x4hSHzSMkPS0NFuIchLSD8UrjXj7YUQkDDEXQSlcxi4u4vhIGGJvwiqVSH5/wNgkKjkmwcEBAQEBAQEBARkLPIDz0jn0miurckAAAAASUVORK5CYII=' style='width:auto;height:auto;display:none;max-height:100%;' /></div>";
				}
			} 
			
			document.getElementById("sgn-widget").innerHTML= html;
		
		}
		
		function setLocation(){
		
			for (x in jsonText) {

				var position = $('#pdf-canvas').position();
				var canvasElem = document.querySelector('canvas');
				var scale_sign_w=$('#pdf-canvas').width()/ori_width;
				var scale_sign_h=$('#pdf-canvas').height()/ori_height;
				
				//var cw=(130*scale_sign_w)/2;
				//var ch=(65*scale_sign_h)/2;
				//var sz_font=(32*scale_sign_w/2);
				
				//$('#sgn-user-'+x).css('width',(130*scale_sign_w)+'px');
				//$('#sgn-user-'+x).css('height',(65*scale_sign_h)+'px');
				
				$('#sgn-user-'+x).css('width',((jsonText[x].rx-jsonText[x].lx)*scale_sign_w)+'px');
				$('#sgn-user-'+x).css('height',((jsonText[x].ry-jsonText[x].ly)*scale_sign_h)+'px');
				
				var topSgn=ori_height-jsonText[x].ry;
				document.getElementById('sgn-user-'+x).style.top=(position.top+(topSgn*scale_sign_h))+'px';
				document.getElementById('sgn-user-'+x).style.left=(position.left+(jsonText[x].lx*scale_sign_w))+'px';
				document.getElementById('sgn-user-'+x).style.position='absolute';
				if(__CURRENT_PAGE==jsonText[x].page){
					$('#sgn-user-'+x).show();
				}else{
					$('#sgn-user-'+x).hide();
					
				}
			}
		}
		
		
		
		
		function showPDF(path) {
			$("#pdf-loader").show();
			PDFJS.cMapUrl = 'https://cdn.jsdelivr.net/npm/pdfjs-dist@2.0.288/cmaps/';
			PDFJS.cMapPacked = true;
			PDFJS.getDocument({ url: path }).then(function(pdf_doc) {
				__PDF_DOC = pdf_doc;
				__TOTAL_PAGES = __PDF_DOC.numPages;
				
				// Hide the pdf loader and show pdf container in HTML
				$("#pdf-loader").hide();
				$("#pdf-contents").show();
				$("#pdf-total-pages").text(__TOTAL_PAGES);
		
				// Show the first page
				showPage(1);
				//Android.setPage(__TOTAL_PAGES);
				initData();
		
			}).catch(function(error) {
				// If error re-show the upload button
				$("#pdf-loader").hide();
				$("#upload-button").show();
				
				alert(error.message);
			});;
		}
		
		
		
		var options = options || { scale: 1 };
		var ori_width;
		var ori_height;
		var scale_sign_w;
		var scale_sign_h;
		
		function showPage(page_no) {
			__PAGE_RENDERING_IN_PROGRESS = 1;
			__CURRENT_PAGE = page_no;
		
			// Disable Prev & Next buttons while page is being loaded
			$("#pdf-next, #pdf-prev").attr('disabled', 'disabled');
		
			// While page is being rendered hide the canvas and show a loading message
			$("#pdf-canvas").hide();
			$("#page-loader").show();
		
			
			// Update current page in HTML
			$("#pageText").val(page_no);
			
			// Fetch the page
			__PDF_DOC.getPage(page_no).then(function(page) {
		
				ori_width= page.getViewport(1).width;
				ori_height= page.getViewport(1).height;
				// As the canvas is of a fixed width we need to set the scale of the viewport accordingly
				var scale_required = window.innerWidth / page.getViewport(1).width;
			        //var viewport = page.getViewport(options.scale);
				// Get viewport of the page at required scale
				var viewport = page.getViewport(4);
				
				// Set canvas height
				__CANVAS.height = viewport.height;
				__CANVAS.width = viewport.width;
				__CANVAS.style.width = '100%';
		
				
				//__CANVAS.height = window.innerHeight;
				//__CANVAS.width = window.innerWidth;
		
				var renderContext = {
					canvasContext: __CANVAS_CTX,
					viewport: viewport
				};
				
				// Render the page contents in the canvas
				page.render(renderContext).then(function() {
					__PAGE_RENDERING_IN_PROGRESS = 0;
		
					// Re-enable Prev & Next buttons
					$("#pdf-next, #pdf-prev").removeAttr('disabled');
		
					// Show the canvas and hide the page loader
					$("#pdf-canvas").show();
					$("#page-loader").hide();

					$("#main-body").show();
					$("#loadingPage").hide();
					setLocation();
					
		
				});
				
				
				
		
			});
			
		}
		
		// Upon click this should should trigger click on the #file-to-upload file input element
		// This is better than showing the not-good-looking file input element
		$("#upload-button").on('click', function() {
			$("#file-to-upload").trigger('click');
		});
		
		// When user chooses a PDF file
		$("#file-to-upload").on('change', function() {
			// Validate whether PDF
		    if(['application/pdf'].indexOf($("#file-to-upload").get(0).files[0].type) == -1) {
		        alert('Error : Not a PDF');
		        return;
		    }
		
			$("#upload-button").hide();
		
			// Send the object url of the pdf
			
		});
		
		// Previous page of the PDF
		$("#pdf-prev").on('click', function() {
			prev();
		});
		
		// Next page of the PDF
		$("#pdf-next").on('click', function() {
			next();
		});

		//untuk tombol simpan pwd
		var simpanpwd=document.getElementById('agree');
		var simpanpwdbtn = document.getElementById('simpanpwd');
		simpanpwd.onchange = function() {
			simpanpwdbtn.disabled = !this.checked;
		};

		  document.getElementById("agree").disabled = true;
		$('#inputNewPassword, #retypeNewPassword').on('keyup', function () {
			var lowerCaseLetters = /[a-z]/g;
			var upperCaseLetters = /[A-Z]/g;
			var numbers = /[0-9]/g;
			var scoreWeak = 0;
			var scoreMatch = 0;
			
			if($('#inputNewPassword').val().length<6){
				$('#messagepwd').html('Minimal 6 karakter').css('color', 'red');
				scoreWeak = 0;
			} 
			else 
			{
				if($('#inputNewPassword').val().match(lowerCaseLetters) && $('#inputNewPassword').val().match(upperCaseLetters) && $('#inputNewPassword').val().match(numbers)) {
					$('#messagepwd').html('Kuat').css('color', 'green');
					scoreWeak = 1;
					
				}
				else if($('#inputNewPassword').val().match(lowerCaseLetters) && $('#inputNewPassword').val().match(upperCaseLetters)){
					$('#messagepwd').html('Sedang. Gunakan kombinasi angka dan huruf besar kecil').css('color', 'green');
					scoreWeak = 1;
					
				}
				else if($('#inputNewPassword').val().match(lowerCaseLetters) && $('#inputNewPassword').val().match(numbers)){
					$('#messagepwd').html('Sedang. Gunakan kombinasi angka dan huruf besar kecil').css('color', 'green');
					scoreWeak = 1;
			
				}
				else {
					$('#messagepwd').html('Lemah. Gunakan kombinasi angka dan huruf besar kecil').css('color', 'red');
					scoreWeak = 0;
				}	
			}
			
			  if ($('#inputNewPassword').val() == $('#retypeNewPassword').val() && $('#inputNewPassword').val().length>5) {
			    $('#message').html('<img src="https://wvapi.digisign.id/img/centang.png" height="24" width="20"/>').css('color', 'green');
			    	scoreMatch = 1;
			  } else 
				  {
				    $('#message').html('<img src="https://wvapi.digisign.id/img/close.png" height="24" width="20"/>').css('color', 'red');
				    scoreMatch = 0;
				}
		
				if(scoreMatch == 1 && scoreWeak == 1)
		  		{
		  			document.getElementById('agree').disabled = false;
		  		}
		  		else
		  		{
		  			document.getElementById('agree').disabled = true;
		  		}
		});
		
		function next(){
			if(__CURRENT_PAGE != __TOTAL_PAGES)
				showPage(++__CURRENT_PAGE);
		}
		
		function prev(){
			if(__CURRENT_PAGE != 1)
				showPage(--__CURRENT_PAGE);
		}
		
		//function save(){
		//		DS.ResultSignDoc(1);
		//}
		
		
		function goPage(pageTo){
			if(pageTo > 0 && pageTo <= __TOTAL_PAGES)
				showPage(pageTo);
			else alert("Error!");
			
		}
		
	
		function setPage(){
			var pageTo=parseInt($("#pageText").val());
			if(pageTo > 0 && pageTo <= __TOTAL_PAGES)
				showPage(pageTo);
			else{
				 $("#pageText").val(__CURRENT_PAGE);
				 alert("Error!");
			}
		}
		
		function toSgn(){
			goPage(jsonText[last].page);
			if(last+1>=jsonText.length){
				last=0;
			}else{
				last++;
			}
		}
		
		function alertDanger(text, ret){
			$("#headerAlert").removeClass('modal-header-success').addClass('modal-header-danger');
			$("#textAlert").text(text);
			$("#titleAlert").text("Failed");
			$("#footerAlert").show();
			$('#alertModal').modal({
			  keyboard: true,
			  backdrop:true
			});
			
			$("#alertModal").modal('show');
		
		
		}
		
		
		function alertSucess(text){
			$("#headerAlert").removeClass('modal-header-danger').addClass('modal-header-success');
			$("#textAlert").text(text);
			$("#titleAlert").text("Saved");
			$("#footerAlert").show();
			$('#alertModal').modal({
			  keyboard: true,
			  backdrop:true
			});
			
			$("#alertModal").modal('show');
		
		
		}
		
		function cekComplete(){
			
			//for(x in jsonText){
				//sign=jsonText[x].sgn;
				//if(sign==0){break;}
			//}
			if(sign==1)return true;
			return false;
		}
		function loading(){
			$("#headerAlert").removeClass('modal-header-success').removeClass('modal-header-danger');
			$("#titleAlert").text("Data Sedang Diproses");
			$("#footerAlert").hide();
			$("#textAlert").html("<div class=\"progress\"><div class=\"progress-bar progress-bar-striped progress-bar-animated\" role=\"progressbar\" aria-valuenow=\"100\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 100%\"></div></div>");
			$('#alertModal').modal({
			  keyboard: false,
			  backdrop:'static'
			});
			$("#alertModal").modal('show');
		}
		
		function signProcess(x){
			$('#sgn-user-'+x).removeClass('sign-box').addClass('sign-box2');
			$('#sgnClick-'+x).hide();
			$('#sgnImg-'+x).show();
			jsonText[x].sgn="1";
			jmlttd++;
			dataUser.user=jsonText;
			sign=1;
		}
		
		function active(status){
			if(status=='1') {
				$('#modalActive').show();
			}
			else {
				$('#modalActive').hide();
			}
		}
		
		//untuk tombol setuju
		var checkme=document.getElementById('checkme');
		var setujubtn = document.getElementById('setuju');
		checkme.onchange = function() {
		 	setujubtn.disabled = !this.checked;
		};
		
		//http://localhost:8086/doc/listdoc.html?frmProcess=getFile&path=../UploadFile/86/original/&name=DS20171004121746.pdf&filename=Surat%20ijin.pdf
		//var urlParams = new URLSearchParams(window.location.search);
		//var path = urlParams.toString();
		
		active(statususer);
		showPDF('https://wvapi.digisign.id/dt02.html?id=IeMEhnDpBR73ZOBPJRy3ng%3D%3D&doc=JIc9V1pSrz5%2B3l0x0DvqCgIISeiZ1mNOPthSddQl6cA%3D');
		
		if(isSign == true)
		{
			$("#Flag").modal('show');
			//$("#cancelLocation").hide();
			$("#prosesSign").hide();
			$("#location").hide();
		}
		else
		{
			$("#Flag").modal('hide');
		}
		
		if(false)
		{
			for (x in jsonText) {
				signProcess(x);
			} 
			$("#sgnClick-").click();
			$("#sgnClick-").trigger("click");

			sign = 1;
		}