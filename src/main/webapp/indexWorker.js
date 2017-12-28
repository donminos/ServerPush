var i = 0;
var wsocket='';

function timedCount() {
    i = i + 1;    
    try{
    console.log(wsocket.readyState);
}catch(err){
    console.log('error');
}
    postMessage(i);
    setTimeout("timedCount()",500);
}

timedCount(); 