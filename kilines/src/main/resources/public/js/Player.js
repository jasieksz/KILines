class Player {

    constructor(data , color, scale) {
        this.color = color;
        this.x = data.pos.x;
        this.y = data.pos.y;
        this.nick = data.nick;
        this.score = 0;
        this.isAlive = true;
        this.scale = scale;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * this.scale, this.y * this.scale, this.scale, this.scale);
    }

}