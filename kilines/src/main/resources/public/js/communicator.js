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
        this.socket = new WebSocket(addressWs);
        this.socket.onmessage((e) => this.receiveUpdates(e));
    }

    update(direction) {
        console.log("elo");
        let update = this._buildDirectionUpdate(direction);
        this.socket.send(JSON.stringify(update));
    }

    _buildUpdate(direction) {
        return {
            type: "update",
            direction: direction,
            token: this.token
        }
    }

}
