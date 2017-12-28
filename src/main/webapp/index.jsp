<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>WebSocket Client</title>
    </head>
    <body>
        <table>
            <tr>
                <td> <label id="rateLbl">Current Rate:</label></td>
                <td> <label id="rate">0</label></td>
            </tr>
        </table>

        <input id="send-token" placeholder="token" type="text">
        <input id="send" type="button" value="conectar" onclick="connect('',document.getElementById('send-token').value,'rate')">
        <input id="send2" type="text" placeholder="token envio">
        <input id="send2-m" type="text" placeholder="mensaje">
        <input id="send-m" type="button" value="mandar-mensaje" onclick="mandar(document.getElementById('send2').value,document.getElementById('send2-m').value)">
        <p>Count numbers: <output id="result"></output></p>
        <script type="text/javascript" src="index.js">
        </script>
    </body>
</html>