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
      // documents["document_id"]
    var url = 'http://localhost:8000/agreement1.pdf';
    
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = 'http://localhost:8000/build/pdf.worker.js';
    
    var pdfDoc = null,
        pageNum = 1,
        pageRendering = false,
        pageNumPending = null,
        scale = 1.5,
        canvas = document.getElementById('the-canvas'),
        ctx = canvas.getContext('2d');
    
    /**
     * Get page info from document, resize canvas accordingly, and render page.
     * @param num Page number.
     */

//      function renderPage(num,activeServiceOnEntry, pdfDocument, pageNumber, size) {
//           var scratchCanvas = activeService.scratchCanvas;
//           var PRINT_RESOLUTION = _app_options.AppOptions.get('printResolution') || 720;
//           console.log(PRINT_RESOLUTION);
//           var PRINT_UNITS = PRINT_RESOLUTION / 72.0;
//           scratchCanvas.width = Math.floor(size.width * PRINT_UNITS);
//           scratchCanvas.height = Math.floor(size.height * PRINT_UNITS);
//           var width = Math.floor(size.width * _ui_utils.CSS_UNITS) + 'px';
//           var height = Math.floor(size.height * _ui_utils.CSS_UNITS) + 'px';
//           var ctx = scratchCanvas.getContext('2d');
//           ctx.save();
//           ctx.fillStyle = 'rgb(255, 255, 255)';
//           ctx.fillRect(0, 0, scratchCanvas.width, scratchCanvas.height);
//           ctx.restore();
//           return pdfDocument.getPage(pageNumber).then(function (pdfPage) {
//             var renderContext = {
//               canvasContext: ctx,
//               transform: [PRINT_UNITS, 0, 0, PRINT_UNITS, 0, 0],
//               viewport: pdfPage.getViewport({
//                 scale: 1,
//                 rotation: size.rotation
//               }),
//               intent: 'print'
//             };
//             return pdfPage.render(renderContext).promise;
//           }).then(function () {
//             return {
//               width: width,
//               height: height
//             };
//           });
// }
    function renderPage(num) {
      // Using promise to fetch the page
      pageRendering = true;
      pdfDoc.getPage(num).then(function(page) {
        ori_width = page.getViewport(1).width;
        ori_height = page.getViewport(1).height;
        var scale_required = window.innerWidth / page.getViewport(1).width;
        var viewport = page.getViewport(4);
        canvas.height = viewport.height;
				canvas.width = viewport.width;
        canvas.style.width = '100%';
        var renderContext = {
					canvasContext: ctx,
					viewport: viewport
        };
        

    //     // var viewport = page.getViewport({1:1});
    //     // console.log(viewport.width);
    //     // scale = Math.min((canvas.height/viewport.height),(canvas.width/viewport.width));
    //     // var viewport = page.getViewport({scale:scale});
    //     // // canvas.height = "1000";
    //     // // canvas.width = "3000";
    //     // canvas.style.width = "100%";
    //     // canvas.style.height = "100%";
    //   // var desiredWidth = 100;
    //   // var viewport = page.getViewport({ scale: 0.1, });
    //   // console.log(viewport.width)
    //   // var scale = desiredWidth / viewport.width;
    //   // var scaledViewport = page.getViewport({ scale: scale, });
    //   //         // // canvas.height = "1000";
    //   //   // // canvas.width = "3000";
    //   //   canvas.style.width = "100%";
    //   //   canvas.style.height = "100%";
    //   var PRINT_UNITS = 200 / 100.0;
    //   console.log(window.devicePixelRatio);
    //   canvas.width = Math.floor(800 * PRINT_UNITS);
    //   canvas.height = Math.floor(1000 * PRINT_UNITS);
    // // canvas.width = viewport.width;//keep high definition drawing canvas
    // // canvas.style.width = canvas.width+'px';//de-z
    
    //     // Render PDF page into canvas context
    //     var renderContext = {
    //       canvasContext: ctx,
    //       transform: [PRINT_UNITS, 0, 0, PRINT_UNITS, 0, 0],
    //       viewport: page.getViewport({
    //             scale: 1
    //           }),
    //     };
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
    
    //   // Update page counters
    //   document.getElementById('page_num').textContent = num;

    }
    
    /**
     * If another page rendering in progress, waits until the rendering is
     * finised. Otherwise, executes rendering immediately.
     */
    function queueRenderPage(num) {
      if (pageRendering) {
        pageNumPending = num;
      } else {
        renderPage(num);
      }
    }
    
    /**
     * Displays previous page.
     */
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
    
    /**
     * Asynchronously downloads PDF.
     */
    pdfjsLib.getDocument(url).promise.then(function(pdfDoc_) {
      pdfDoc = pdfDoc_;
      document.getElementById('page_count').textContent = pdfDoc.numPages;
    
      // Initial/first page rendering
      renderPage(pageNum);
    });