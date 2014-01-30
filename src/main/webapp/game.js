var currentRow=1;
var currentCol=3;
var highlightCanvas;
var highlightContext;
var gridCanvas;
var gridContext;

function initGame() {
	init();
	drawBoard();
    addHighlightCanvasClickListener();
    addHighlightCanvasKeyListener();
	highlightCell(currentRow,currentCol);
}

function init() {
    highlightCanvas = document.getElementById("highlightCanvas");
    highlightContext = highlightCanvas.getContext("2d");
    gridCanvas = document.getElementById("gridCanvas");
    gridContext = gridCanvas.getContext("2d");    
}

function drawBoard() {
    var bw = 400;
    var bh = 400;
    var p = 10;
    
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
}

function addHighlightCanvasClickListener() {
	highlightCanvas.addEventListener('mousedown', function (event) {
        var col = (event.pageX/40|0)-1;
        var row = (event.pageY/40|0)-1;
        highlightCell(row, col);
    }, false);
}

function addHighlightCanvasKeyListener() {
	highlightCanvas.addEventListener('keydown', function (event) {
		if (event.keyCode == 87) {
			++currentRow;
		}
		highlightCell(currentRow,currentCol);
    }, true);
}

function highlightCell(row, col) {
	highlightContext.beginPath();
	highlightContext.clearRect(0, 0, 420, 420);
	highlightContext.strokeStyle = "maroon";
	highlightContext.lineWidth = 5;
	highlightContext.rect(col*40+10, row*40+10, 40, 40);
	highlightContext.stroke();	
}