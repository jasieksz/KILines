const w = 1280, h = 960;
const backgroundColor = 23;
const rectW = 4;
const rectH = 4;
const playerColours = [
    [0, 167, 247]
];
let deathSound;

let players;
let communicator = new Communicator(location.href.replace('/game.html',''), handleUpdate);

function preload() {
    deathSound = loadSound('sound/explosion.mp3');
}

function setup() {
    createCanvas(w, h);
    background(backgroundColor);
    communicator.init(initState);
    communicator.openWebsocket();
}

function draw() {
    renderPlayers();
    scoreboard()
}

function initState(state){
    state.obstacles.map((obs) => {
        renderPoint(obs.pos, state.colors[obs.color]);
    });
    players = initPlayers(state);
}

function initPlayers(state) {
    let i = 0;
    return state.players.map( data =>{
        let player = new Player(data, playerColours[i]);
        i++;
        return player;
    })
}

function renderPlayers() {
    if(players){
        players.forEach((pl) => pl.render())
    }
}

function scoreboard() {
    if(players && players.length > 0){
        fill(255);
        stroke(255);
        textFont("Orbitron");
        textSize(18);
        text("Score", 1000, 25);
        let i = 25;
        players.map((player) =>{
            i += 25;
            text(player.nick + " : " + player.score, 1000, i)
            }
        );
       }
}

function renderPoint(pos, color) {
    noStroke();
    fill(color);
    rect(pos.x, pos.y, rectW, rectH)
}

function keyPressed() {
    if (key === 'w' || key === 'W'){
        console.log('w');
        communicator.update("UP");
    } else if (key === 'd' || key === 'D'){
        console.log('d');
        communicator.update("RIGHT");
    } else if (key === 's' || key === 'S'){
        console.log('s');
        communicator.update("DOWN");
    } else if (key === 'a' || key === 'A'){
        console.log('a');
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
        rect(this.x, this.y, rectW, rectH);
    }

}

function handleUpdate(update){
    update.players.map( (pl) => {
            let selectedPlayer = _.find(players, (pl2) => pl2.nick === pl.nick);
            if (selectedPlayer) {
                selectedPlayer.x = pl.pos.x;
                selectedPlayer.y = pl.pos.y;
                if (selectedPlayer.isAlive !== pl.isAlive) kill(selectedPlayer);
            }
        }
    )
}

function kill(player) {
    deathSound.play();
}