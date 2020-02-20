    // If absolute URL from the remote server is provided, configure the CORS
    // header on that server.
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

      documents = getParams(window.location.href);
      var version=1;
    var url = 'http://localhost:8000/'.concat(documents["document_id"]).concat('.pdf')+"?v="+toString(version+1);
    version +=1;
    
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = 'http://10.15.15.65:8000/build/pdf.worker.js';
    
var pdfDoc = null,
pageNum = 1,
pageRendering = true,
pageNumPending = null,
scale = 1.5

var renderTask = null;


function renderPage(num) {
  if( $('#document').length )
  {
    document.getElementById("document").remove();
  }
  var canvas = document.createElement('canvas');
  canvas.setAttribute("id","document");
    canvas.style.cssText = 'border:1px solid #000000;';
      ctx = canvas.getContext('2d');
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      // Using promise to fetch the pag
      pdfDoc.getPage(num).then(function(page) {
        console.log(num);
        var container = document.getElementById('the-container');
        ori_width = page.getViewport(1).width;
        ori_height = page.getViewport(1).height;
        // var scale_required = window.innerWidth / page.getViewport(1).width;
        var viewport = page.getViewport(0.5);
        viewport = page.getViewport(container.clientWidth/viewport.width);
        canvas.height = viewport.height;
				canvas.width = viewport.width;
        canvas.style.width = '100%';
        container.appendChild(canvas);
        var renderContext = {
					canvasContext: ctx,
					viewport: viewport
        };
        var renderTask = page.render(renderContext);
        // Wait for rendering to finish
        renderTask.promise.then(function() {
          pageRendering = false;
          if (pageNumPending !== null) {
            // New page rendering is pending
            renderPage(pageNumPending);
            pageNumPending = null;
          }
        });
      });
        //   // Update page
    }
        /**    * If another page rendering in progress, waits until the rendering is
     * finised. Otherwise, executes  immediately.
     */
    function queueRenderPage(num) {
      if (pageRendering) {
        pageNumPending = num;
      } else {
        renderPage(num);
        document.getElementById('p_num').value = num;
      }
    }
    
    /**
     * Displays previous page.
     */

    function gotoPage(){
      var page_num = parseInt(document.getElementById("p_num").value);
      var page_count = parseInt(document.getElementById('page_count').textContent);
      if(page_num>page_count || page_num==null || page_num<1){
        console.log(page_num);
        console.log("Hii");
        queueRenderPage(pageNum);
      }
      else{
        pageNum = parseInt(page_num);
        queueRenderPage(parseInt(page_num));
        
      }
    }


    function onPrevPage() {
      if (pageNum <= 1) {
        return;
      }
      pageNum--;
      queueRenderPage(pageNum);
    }
    document.getElementById('prev').addEventListener('click', onPrevPage);
    
    /**
     * Displays next page.
     */
    function onNextPage() {
      if (pageNum >= pdfDoc.numPages) {
        return;
      }
      pageNum++;
      queueRenderPage(pageNum);
    }
    document.getElementById('next').addEventListener('click', onNextPage);
    
    function reloadPage() {
      pdfjsLib.getDocument(url).promise.then(function(pdfDoc_) {
        pdfDoc = pdfDoc_;
        document.getElementById('page_count').textContent = pdfDoc.numPages;
        // Initial/first page rendering
        renderPage(pageNum);

      });
    }
    $("#signsubmit").click(function(){
      //signed();
      $("#myModal").modal('hide');
      
      reloadPage();

    });
    
    /**
     * Asynchronously downloads PDF.
     */
//     $('#the-container').each(function() {
// var pdfDiv = $(this);
// var pdfUrl = 'http://localhost:8000/agreement1.pdf';
// renderPdf(pdfUrl, pdfDiv);
// });
    pdfjsLib.getDocument(url).promise.then(function(pdfDoc_) {
      pdfDoc = pdfDoc_;
      document.getElementById('page_count').textContent = pdfDoc.numPages;
    
      // Initial/first page rendering
      renderPage(pageNum);
    });