var wsocket, url = "ws://localhost:8084/NotificationPush/pushNotification?info=", id;
//var wsocket, url = "wss://www.tdmobile.com.mx/NotificationPush-1.0//pushNotification?info=", id;
function connect(url, token, id) {
    this.id = id;
    wsocket = new WebSocket(this.url + token+"=ABC");
    wsocket.onmessage = onMessage;
}
function onMessage(evt) {
    document.getElementById(id).innerHTML = evt.data;
}

function mandar(tokenSend,info) {
    param = {opcion: 1, token: tokenSend, msn: info};
    wsocket.send(JSON.stringify(param));
}
/*hilo*/
var w;
function startWorker() {
    if(typeof(Worker) !== "undefined") {
        if(typeof(w) == "undefined") {
            w = new Worker("indexWorker.js");
        }
        
        w.onmessage = function(event) {
            document.getElementById("result").innerHTML = event.data;
        };
        w.postMessage(wsocket);
    } else {
        document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Workers...";
    }
}

function stopWorker() { 
    w.terminate();
    w = undefined;
}
