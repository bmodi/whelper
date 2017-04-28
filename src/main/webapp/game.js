var currentRow=0;
var currentCol=0;
var maxRow=9;
var maxCol=9;
var highlightCanvas;
var highlightContext;
var textCanvas;
var textContext;
var gridCanvas;
var gridContext;
var borderWidth=10;
var cellSize=40;
var fontSize=36;
var cells;

function initGame() {
	init();
	drawBoard();
    addHighlightCanvasClickListener();
    addHighlightCanvasKeyListener();
	highlightCell(currentRow,currentCol);
    highlightCanvas.focus();
}

function init() {
    highlightCanvas = document.getElementById("highlightCanvas");
    highlightContext = highlightCanvas.getContext("2d");
    gridCanvas = document.getElementById("gridCanvas");
    gridContext = gridCanvas.getContext("2d");    
    textCanvas = document.getElementById("textCanvas");
    textContext = gridCanvas.getContext("2d");
    createEmptyCells();
}

function createEmptyCells() {
	cells = new Array(10);
	for (var i = 0; i < 10; i++) {
		cells[i] = new Array(10);
		for( var j=0; j<10; j++) {
			cells[i][j]="";
		}
	}
}

function addHighlightCanvasKeyListener() {
	highlightCanvas.addEventListener('keydown', function (event) {
		keyCode=event.keyCode;
		if (keyCode == 37) { // left
			--currentCol;
		} else if (keyCode == 38) { // up
			--currentRow;
		} else if (keyCode == 40) { // down
			++currentRow;
		} else if (keyCode == 39) { // right
			++currentCol;
		} else if (keyCode>=65 && keyCode<=90 ) {
			text=String.fromCharCode(keyCode);
			setCellText(currentRow,currentCol,text);
		} else if (keyCode==32 || keyCode==8 || keyCode==46) {
			setCellText(currentRow,currentCol,"");
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

function generateWords(gridType) {
	var url = "api/grid";
	var client = new XMLHttpRequest();

	client.open("POST", url, false);
	client.setRequestHeader("Content-Type", "application/json");
	var grid={"gridType":gridType, "cells":cells};
	client.send(JSON.stringify(grid));

	if (client.status == 200) {
	    var words = JSON.parse(client.responseText);
        var select = document.getElementById("wordList");
        select.innerHTML = "";
        for (var i = 0; i < words.length; i++) {
	    	console.log("word "+i+": "+words[i]);
            var opt = words[i];
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
        }
	}	
	else
	    alert("The request did not succeed!\n\nThe response status was: " + client.status + " " + client.statusText + ".");

}

function filterWordsByLength() {
    if (document.getElementById("word-length-filter").value == "0"){
    	$( "#debug-message" ).css( "border", "3px solid red" );
//        document.getElementById("debug-message").innerHTML = "Clear";
    }     
    else{
    	$( "#debug-message" ).css( "border", "3px solid yellow" );
    	$( "#debug-message" ).text( "hello" );
//    	document.getElementById("debug-message").innerHTML = document.getElementById("word-length-filter").value;
    }        
}
