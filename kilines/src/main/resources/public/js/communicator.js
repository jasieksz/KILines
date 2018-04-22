class Communicator {

    constructor(address, updateCallback) {
        this.address = address;
        this.updateCallback = updateCallback;
    }

    init (callback) {
        $.get(this.address + "/init", (data) => {
            console.log(data);
            const state = JSON.parse(data);
            callback(state);
        });
    };

    receiveUpdates(e) {
        let msg = JSON.parse(e);
        if (msg.type === "update")
            this.updateCallback(msg)
    }

    openWebsocket() {
        console.log(this.addressWs);
        this.socket = new WebSocket("ws://localhost:4567/game/websocket");
        console.log(this.socket);
        this.socket.onconnect = (e) => this.receiveUpdates(e);
    }

    update(direction) {
        this.socket.send(JSON.stringify(direction));
    }

}
