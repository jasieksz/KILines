class PowerUp {

    constructor(data, scale) {
        this.id = color;
        this.color = [238, 244, 66];
        this.x = data.position.x;
        this.y = data.position.y;
        this.scale = scale;
        this.updated = true;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * this.scale, this.y * this.scale, this.scale, this.scale);
    }

    hide () {
        stroke(23);
        fill(23);
        rect(this.x * this.scale, this.y * this.scale, this.scale, this.scale);
    }

}