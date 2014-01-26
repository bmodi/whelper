function initGame() {
    drawBoard();
}

function drawBoard() {
    var bw = 400;
    var bh = 400;
    var p = 10;
    var cw = bw + (p * 2) + 1;
    var ch = bh + (p * 2) + 1;
    
    var gridCanvas = document.getElementById("gridCanvas");
    var highlightCanvas = document.getElementById("highlightCanvas");
    var gridContext = gridCanvas.getContext("2d");
    var highlightContext = highlightCanvas.getContext("2d");
    
    for (var x = 0; x <= bw; x += 40) {
        gridContext.moveTo(0.5 + x + p, p);
        gridContext.lineTo(0.5 + x + p, bh + p);
    }


    for (var x = 0; x <= bh; x += 40) {
        gridContext.moveTo(p, 0.5 + x + p);
        gridContext.lineTo(bw + p, 0.5 + x + p);
    }

    gridContext.strokeStyle = "black";
    gridContext.stroke();
    
    addClickListener(highlightCanvas);
}

function addClickListener(canvas) {
    var context = canvas.getContext("2d");
    canvas.addEventListener('mousedown', function (event) {
        var x = event.pageX-30;
        var y = event.pageY-30;
        context.beginPath();
        context.clearRect(0, 0, 420, 420);
        context.strokeStyle = "maroon";
        context.lineWidth = 5;
        var col = (event.pageX/40|0)-1;
        var row = (event.pageY/40|0)-1;
        context.rect(col*40+10, row*40+10, 40, 40);
        context.fillRect(x,y,1,1);
        context.stroke();
    }, false);
}

