import React from 'react';


class App extends React.Component{
  ws;
  constructor(props) {
    super(props);
    let urlParams = new URLSearchParams(window.location.search);
    this.state={
      username: urlParams.get("user")
    };

    this.send = this.send.bind(this);
    this.connectTo = this.connectTo.bind(this);
  }

  componentDidMount() {
    let host = window.location.host;
    let pathname = window.location.pathname;
    this.ws = new WebSocket("ws://" + host  + pathname + "chat/" + this.state.username);
    this.ws.onmessage = function(event) {
      let log = document.getElementById("log");
      let message = JSON.parse(event.data);
      log.innerHTML += message.from + " : " + message.content + "\n";
    };
  }

  send(){
    let content = document.getElementById("msg").value;
    let json = JSON.stringify({
      "content":content
    });
    this.ws.send(json);
  }

  connectTo(){
    let chatWith = document.getElementById("connectTo").value;
    let url = "http://localhost:8080/prattle/rest/user/connectToUsers";

    if(chatWith !== "") {
      let jsonBody = {
        "userFrom" : this.state.username ,
        "userTo" : chatWith
      };

      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type' : 'application/json'
        },
        body: JSON.stringify(jsonBody)
      }).then(res => res.status!==200? alert("User doesnt exist"):console.log(res));
    }
  }

  render(){
    return(
        <div>
          <h1>Welcome to Prattle Chat Application</h1>
          <table>
            <tbody>
            <tr>
              <td colSpan="2">
                <input type="text" id="connectTo"
                       placeholder="Username"/>
              </td>
              <td><button onClick={this.connectTo}>Connect</button></td>
            </tr>
            <tr>
              <td>
                <textarea readOnly={true} rows="10" cols="80" id="log"/>
              </td>
            </tr>
            <tr>
              <td>
                <input type="text" size="51" id="msg" placeholder="Message"/>
                <button type="button"
                        onClick={this.send}
                >Send</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default App;
