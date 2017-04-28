<html>
    <head>
        <script src="game.js"></script>
        <script src="grid.js"></script>
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
			<select id="wordList" size="25" style="width: 100%">
			</select>
			<label>Filter by word length:  <select>
				<option value="0">Show all</option>
				<option value="3">3</option>
				<option value="3">4</option>
				<option value="3">5</option>
				<option value="3">6</option>
				<option value="3">7</option>
				<option value="3">8</option>
				<option value="3">9</option>
				<option value="3">10</option>
				<option value="3">11</option>
				<option value="3">12</option>
				<option value="3">13</option>
				<option value="3">14</option>
				<option value="3">15</option>
			</select></label>		
        </div>
    </div>
    </body>
</html>

