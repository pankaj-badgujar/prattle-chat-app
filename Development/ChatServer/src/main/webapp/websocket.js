var ws;
var username;

window.onload = function() {
    var urlParams = new URLSearchParams(window.location.search);
    username = urlParams.get("user");

    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" +host  + pathname + "chat/" + username);
    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
    };
}

function connectTo() {
    var chatWith = document.getElementById("connectTo").value;
    var url = "http://localhost:8080/prattle/rest/user/connectToUsers"

    if(chatWith !== "") {
        // Add API call here!
        var jsonBody = {
            "userFrom" : username ,
            "userTo" : chatWith
        };

        console.log(jsonBody);

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(jsonBody)
        });

        console.log("connect to", chatWith)
    }
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
                                  "content":content
                              });
    ws.send(json);
}