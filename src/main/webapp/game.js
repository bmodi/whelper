var currentRow=0;
var currentCol=0;
var maxRow=9;
var maxCol=9;
var highlightCanvas;
var highlightContext;
var gridCanvas;
var gridContext;
var borderWidth=10;
var cellSize=40;

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
    
    for (var x = 0; x <= bw; x += cellSize) {
        gridContext.moveTo(0.5 + x + borderWidth, borderWidth);
        gridContext.lineTo(0.5 + x + borderWidth, bh + borderWidth);
    }


    for (var x = 0; x <= bh; x += cellSize) {
        gridContext.moveTo(borderWidth, 0.5 + x + borderWidth);
        gridContext.lineTo(bw + borderWidth, 0.5 + x + borderWidth);
    }

    gridContext.strokeStyle = "black";
    gridContext.stroke();
}

function addHighlightCanvasClickListener() {
	highlightCanvas.addEventListener('click', function (event) {
        currentCol = (event.pageX/cellSize|0)-1;
        currentRow = (event.pageY/cellSize|0)-1;
        highlightCell(currentRow, currentCol);
    }, false);
}

function addHighlightCanvasKeyListener() {
	highlightCanvas.addEventListener('keydown', function (event) {
		if (event.keyCode == 37) { // left
			--currentCol;
		} else if (event.keyCode == 38) { // up
			--currentRow;
		} else if (event.keyCode == 40) { // down
			++currentRow;
		} else if (event.keyCode == 39) { // right
			++currentCol;
		}
		fixRowColOutsideBoundaries();
		highlightCell(currentRow,currentCol);
    }, true);
}

function fixRowColOutsideBoundaries() {
	if ( currentRow<0 ) currentRow=maxRow;
	if ( currentCol<0 ) currentCol=maxCol;
	if ( currentRow>maxRow ) currentRow=0;
	if ( currentCol>maxCol ) currentCol=0;
}

function highlightCell(row, col) {
	highlightContext.beginPath();
	highlightContext.clearRect(0, 0, 420, 420);
	highlightContext.strokeStyle = "maroon";
	highlightContext.lineWidth = 5;
	highlightContext.rect(col*cellSize+10, row*cellSize+10, cellSize, cellSize);
	highlightContext.stroke();	
}