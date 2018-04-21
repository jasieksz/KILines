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
        console.log(this.addressWs)
        this.socket = new WebSocket("ws://localhost:4567/game/websocket")
        console.log(this.socket)
        this.socket.onconnect = (e) => this.receiveUpdates(e)
        this.socket.onopen = () => this.socket.send("ala ma psa")
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
