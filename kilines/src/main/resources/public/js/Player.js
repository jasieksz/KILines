class Player {

    constructor(data , color) {
        this.color = color;
        this.x = data.pos.x;
        this.y = data.pos.y;
        this.nick = data.nick;
        this.score = 0;
        this.isAlive = true;
        this.scale = 2;
        this.rectW = 2;
        this.rectH = 2;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * this.scale, this.y * this.scale, this.rectW, this.rectH);
    }

}