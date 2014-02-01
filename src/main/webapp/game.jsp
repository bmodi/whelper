<html>
    <head>
        <script src="game.js"></script>
    </head>
    <body style="background: steelblue;" onload="initGame();">
        <div style="position: relative;">
                <canvas id="gridCanvas" width="420px" height="420px" style="background: #eee; margin:20px; position: absolute; left: 0; top: 0; z-index: 0;"></canvas>
                <canvas id="textCanvas" tabindex="0" width="420px" height="420px" style="margin:20px; position: absolute; left: 0; top: 0; z-index: 1;"></canvas>
                <canvas id="highlightCanvas" tabindex="0" width="420px" height="420px" style="margin:20px; position: absolute; left: 0; top: 0; z-index: 2;"></canvas>
        </div>
    </body>
</html>

