function drawBoard() {
    var bw = 400;
    var bh = 400;

    gridContext.strokeStyle = "black";
    for (var x = 0; x <= bw-cellSize; x += cellSize) {
        for (var y = 0; y <= bh-cellSize; y += cellSize) {
        	gridContext.rect(0.5 + x + borderWidth, 0.5 + y + borderWidth, cellSize, cellSize);
        }
    }
    gridContext.stroke();
}

function addHighlightCanvasClickListener() {
	highlightCanvas.addEventListener('click', function (event) {
		var rect = highlightCanvas.getBoundingClientRect();
	    var x=event.x-rect.left-borderWidth;
	    var y=event.y-rect.top-borderWidth;

        currentCol = (x/cellSize|0);
        currentRow = (y/cellSize|0);
        highlightCell(currentRow, currentCol);
    }, false);
}

function highlightCell(row, col) {
	highlightContext.beginPath();
	highlightContext.clearRect(0, 0, 420, 420);
	highlightContext.strokeStyle = "maroon";
	highlightContext.lineWidth = 5;
	highlightContext.rect(col*cellSize+10, row*cellSize+10, cellSize, cellSize);
	highlightContext.stroke();
}

function setCellText(row, col, text) {
	cells[row][col]=text;
	textContext.beginPath();
	textContext.clearRect(col*cellSize+11, row*cellSize+11, cellSize-1, cellSize-1);
	textContext.fillStyle = "indigo";
	textContext.font = "bold "+fontSize+"px Arial";
	textContext.textAlign = 'center';
	textContext.textBaseline = 'middle';
	textContext.fillText(text, col*cellSize+10+cellSize/2, row*cellSize+11+cellSize/2);
	textContext.stroke();
	generateWords();
}
