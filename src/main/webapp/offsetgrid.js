function drawBoard() {
    var bw = 400;
    var bh = 400;

    gridContext.strokeStyle = "black";
	var odd=1;
    for (var x = 0; x <= bw-cellSize; x += cellSize) {
    	odd=1-odd;
    	offset = odd*cellSize/2;
    	console.log("odd="+odd+", offset="+offset);
        for (var y = 0; y <= bh-cellSize; y += cellSize) {
        	gridContext.rect(0.5 + x + borderWidth, 0.5 + y + borderWidth + offset, cellSize, cellSize);
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
		if(currentCol%2==1)y-=cellSize/2;
        currentRow = (y/cellSize|0);
        highlightCell(currentRow, currentCol);
    }, false);
}

function highlightCell(row, col) {
	highlightContext.beginPath();
	highlightContext.clearRect(0, 0, 420, 440);
	highlightContext.strokeStyle = "maroon";
	highlightContext.lineWidth = 5;
	var offset=0;
	if(col%2==1)offset=cellSize/2;
	highlightContext.rect(col*cellSize+10, row*cellSize+10+offset, cellSize, cellSize);
	highlightContext.stroke();
}

function setCellText(row, col, text) {
	cells[row][col]=text;
	var offset=0;
	if(col%2==1)offset=cellSize/2;
	textContext.beginPath();
	textContext.clearRect(col*cellSize+11, row*cellSize+11+offset, cellSize-1, cellSize-1);
	textContext.fillStyle = "indigo";
	textContext.font = "bold "+fontSize+"px Arial";
	textContext.textAlign = 'center';
	textContext.textBaseline = 'middle';
	textContext.fillText(text, col*cellSize+10+cellSize/2, row*cellSize+11+cellSize/2+offset);
	textContext.stroke();
	generateWords("OFFSET_GRID");
}
