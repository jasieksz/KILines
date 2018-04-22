class Communicator {

    constructor(address, updateCallback) {
        this.address = address;
        this.updateCallback = updateCallback;
    }

    init (callback) {
        $.get(this.address + "/init", (data) => {
            const state = JSON.parse(data);
            callback(state);
        });
    };

    receiveUpdates(e) {
        let msg = JSON.parse(e.data);
        this.updateCallback(msg)
    }

    openWebsocket() {
        this.socket = new WebSocket("ws://localhost:4567/game/websocket");
        this.socket.onmessage = (e) => this.receiveUpdates(e);
    }

    update(direction) {
        this.socket.send(JSON.stringify(direction));
    }

}
