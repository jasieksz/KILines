class Communicator{

    constructor(address, updateCallback){
        console.log(address)
        this.address = address
        this.updateCallback = updateCallback
    }

    receiveUpdates(e) {
        let msg = JSON.parse(e)
        if(msg.type == "update")
            this.updateCallback(msg)
    }

    login(name) {
         var resp = this.httpGet(this.address + "/login/" + name)
         console.log("resp: " + resp)
             if(resp.isOk)
                 this.token = resp.token
             else
                 alert(resp.msg)
    }

    openWebsocket() {
        this.socket = new WebSocket(httpUrlToWebSockeUrl(this.address))
        this.socket.onmessage((e) => this.receiveUpdates(e))
    }

    init() {
        this.initState = this.httpGet(this.address + "/init")
        console.log(this.initState)
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

    httpUrlToWebSockeUrl(url){
    	return url.replace(/(http)(s)?\:\/\//, "ws$2://");
    }
}
