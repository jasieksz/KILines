const w = 1280, h = 960;
const backgroundColor = 23;
const rectW = 4;
const rectH = 4;
const scale = 2;
let playerColourCounter = 0;
const playerColours = [
    [0, 167, 247],
    [244, 80, 66],
    [52, 237, 49],
    [49, 218, 237],
];
const obstacleColour = [255, 255, 255];

let deathSound;

let players;
let communicator = new Communicator(location.href.replace('/game.html',''), handleUpdate);

function preload() {
    deathSound = loadSound('sound/explosion.mp3');
    communicator.openWebsocket();

}

function setup() {
    createCanvas(w, h);
    background(backgroundColor);
    noLoop();
    communicator.init(initState);
}

function draw() {
    renderPlayers();
    scoreboard()
}

function initState(state){
    state.obstacles.map((obs) => {
        renderPoint(obs.pos);
    });
    players = initPlayers(state);
    loop();
}

function initPlayers(state) {
    return state.players.map( data =>{
        return new Player(data, getColor());
    })
}

function renderPlayers() {
    if(players){
        players.forEach((pl) => pl.render())
    }
}

function scoreboard() {
    if(players && players.length > 0){
        players.map((player) =>{
            let scoreCounter = $('#' + player.nick);
            if (scoreCounter.length){
                scoreCounter.val(player.score);
            } else {
                $('#scoreboard').append('<tr><td>' + player.nick + '</td>' + '<td id=' + player.nick + '>' + player.score +'</td></tr>')
            }
        });
    }
}

function renderPoint(pos) {
    noStroke();
    fill(obstacleColour);
    rect(pos.x * scale, pos.y * scale, rectW, rectH)
}

function keyPressed() {
    if (key === 'w' || key === 'W'){
        communicator.update("UP");
    } else if (key === 'd' || key === 'D'){
        communicator.update("RIGHT");
    } else if (key === 's' || key === 'S'){
        communicator.update("DOWN");
    } else if (key === 'a' || key === 'A'){
        communicator.update("LEFT");
    }
}

class Player {

    constructor(data , color) {
        this.color = color;
        this.x = data.pos.x;
        this.y = data.pos.y;
        this.nick = data.nick;
        this.score = 0;
        this.isAlive = true;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * scale, this.y * scale, rectW, rectH);
    }

}


class PowerUp {

    constructor(data) {
        this.id = color;
        this.color = [238, 244, 66];
        this.x = data.pos.x;
        this.y = data.pos.y;
    }

    render () {
        stroke(this.color);
        fill(this.color);
        rect(this.x * scale, this.y * scale, rectW, rectH);
    }

}

function handleUpdate(update){
    console.log(update);
    if(update.type == "reset"){
        clear();
        setup();
    }
    else
        updatePlayers(update);
}

function updatePlayers(updates) {
    updates.map( (pl) => {
            let selectedPlayer = _.find(players, (pl2) => pl2.nick === pl.nick);
            if (selectedPlayer) {
                selectedPlayer.x = pl.pos.x;
                selectedPlayer.y = pl.pos.y;
                if (selectedPlayer.isAlive !== pl.isAlive) kill(selectedPlayer);
                selectedPlayer.isAlive = pl.isAlive;
            } else {
                players.push(
                    new Player(pl, getColor())
                );
            }
        }
    )
}

function updatePowerUps(powerUps) {
    powerUps.map();
}

function kill(player) {
    deathSound.play();
}

function start() {
    communicator.start();
}

function getColor(){
    let colour = playerColours[playerColourCounter];
    playerColourCounter = playerColourCounter + 1 % 4 ;
    return colour;
}

function restart() {
    function callback (){
        clear();
        setup();
    }
    communicator.restart(callback);
}