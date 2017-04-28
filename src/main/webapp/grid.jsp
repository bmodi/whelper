<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>        
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
			<label>Filter by word length:  <select id="word-length-filter" onchange="filterWordsByLength()">
				<option value="0">Show all</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
			</select></label>		
			<div id="debug-message">debug</div>
        </div>
    </div>
    
    <script>
</script>
    
    
    </body>
</html>

