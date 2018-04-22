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

    start (){
        $.get(this.address + '/start');
    }

    restart (callback) {
        $.get(this.address + '/reset', callback);
    }

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
