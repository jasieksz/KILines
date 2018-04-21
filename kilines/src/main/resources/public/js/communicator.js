class Communicator{

    constructor(address, updateCallback){
        this.address = address
        this.updateCallback = updateCallback
    }

    receiveUpdates(e) {
        let msg = JSON.parse(e)
        if(msg.type == "update")
            this.updateCallback(msg)
    }

    login() {
         resp = httpGet(this.address + "/login")
             if(resp.isOk)
                 this.token = resp.token
             else
                 document.getElementById("errors").innerHTML = resp.msg
    }

    openWebsocket() {
        this.socket = new WebSocket(address)
        this.socket.onmessage((e) => this.receiveUpdates(e))
    }

    init() {
        this.initState = httpGet(this.address + "/init")
    }

    update (direction) {
        let update = this._buildDirectionUpdate(direction);
        this.socket.send(JSON.stringify(update));
    }

    _buildUpdate(direction){
        return {
                type: "update",
                direction: direction,
                token: this.token
            }
        }

    httpGet(theUrl){
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
        xmlHttp.send( null );
        return JSON.stringify(xmlHttp.responseText);
    }
}
