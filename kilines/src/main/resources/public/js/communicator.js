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
        let wsAddress = this.address.replace('http', 'ws') + '/game/websocket';
        this.socket = new WebSocket(wsAddress);
        this.socket.onmessage = (e) => this.receiveUpdates(e);
    }

    update(direction) {
        this.socket.send(JSON.stringify(direction));
    }

}
