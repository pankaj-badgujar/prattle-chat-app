import React from 'react';
import {CONNECT_TO_USER_URL} from './constants';

const defaultState = {
    username: '',
    messageContent: '',
    chatWith: '',
    logContent: ''
};

class App extends React.Component {
    ws;

    constructor(props) {
        super(props);
        this.state = defaultState;
        const username = new URLSearchParams(window.location.search).get("user");
        this.ws = new WebSocket("ws://" + window.location.host + window.location.pathname + "chat/" + username);
        this.setState({...this.state, username}, () => {
            console.log(this.state)
        });
        this.ws.onmessage = this.messageHandler;
    }

    updateMessageContent = (e) => {
        this.setState({
            messageContent: e.target.value
        });
    };

    updateChatWith = (e) => {
        this.setState({
            chatWith: e.target.value
        });
    };

    send = () => {
        let json = JSON.stringify({
            "content": this.state.messageContent
        });
        this.ws.send(json);
    };

    messageHandler = (event) => {
        let message = JSON.parse(event.data);
        const logContent = this.state.logContent + message.from + " : " + message.content + "\n";
        this.setState({...this.state, logContent})
    };

    connectTo = (event) => {
        event.preventDefault();
        if (this.state.chatWith !== "") {
            let jsonBody = {
                "userFrom": this.state.username,
                "userTo": this.state.chatWith
            };

            fetch("http://" + window.location.host + CONNECT_TO_USER_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonBody)
            }).then(res => res.status !== 200 ? alert("User doesnt exist") : console.log(res));
        }
    };

    render() {
        return (
            <div>
                <h1>Welcome to Prattle Chat Application</h1>
                <table>
                    <tbody>
                    <tr>
                        <td colSpan="2">
                            <input type="text" id="connectTo"
                                   placeholder="Username" onChange={this.updateChatWith}/>
                        </td>
                        <td>
                            <button onClick={this.connectTo}>Connect</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <textarea readOnly={true} value={this.state.logContent} rows="10" cols="80" id="log"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" size="51" id="msg" placeholder="Message"
                                   onChange={this.updateMessageContent}/>
                            <button type="button"
                                    onClick={this.send}
                            >Send
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        )
    }
}

export default App;
