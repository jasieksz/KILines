class PowerUp {

    constructor(data) {
        this.id = color;
        this.color = [238, 244, 66];
        this.x = data.position.x;
        this.y = data.position.y;
        this.scale = 2;
        this.rectW = 2;
        this.rectH = 2
        ;
        this.updated = true;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * this.scale, this.y * this.scale, this.rectW, this.rectH);
    }

    hide () {
        stroke(23);
        fill(23);
        rect(this.x * this.scale, this.y * this.scale, this.rectW, this.rectH);
    }

}