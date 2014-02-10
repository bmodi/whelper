<html>
    <head>
        <script src="game.js"></script>
        <link href="default.css" type="text/css" rel="stylesheet" />
    </head>
    <body onload="initGame();">
    <div id="wrapper">
        <div id="grid">
                <canvas id="gridCanvas" width="420px" height="420px" style="background: #eee; z-index: 0;"></canvas>
                <canvas id="textCanvas" width="420px" height="420px" style="z-index: 1;"></canvas>
                <canvas id="highlightCanvas" tabindex="0" width="420px" height="420px" style="z-index: 2;"></canvas>
        </div>
        <div id="controls">
	        <button type="button" onclick="generateWords()">Find Words</button>
			<select id="wordList" size="25" style="width: 100%">
			</select>
        </div>
    </div>
    </body>
</html>

