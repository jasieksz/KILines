const w = 1280, h = 960;
const scale = 2;

const backgroundColor = 23;
const obstacleColour = [255, 255, 255];

let playerColourCounter = 0;
const playerColours = [
    [0, 167, 247],
    [244, 80, 66],
    [52, 237, 49],
    [49, 218, 237],
];

let deathSound;

let players = [];
let powerUps = [];
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
    function renderObstacle(pos) {
        noStroke();
        fill(obstacleColour);
        rect(pos.x * scale, pos.y * scale, scale, scale)
    }

    state.obstacles.map((obs) => {
        renderObstacle(obs.pos);
    });
    players = initPlayers(state);
    loop();
}

function initPlayers(state) {
    return state.players.map( data =>{
        return new Player(data, getColor(), scale);
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
                scoreCounter.html(player.score);
            } else {
                $('#scoreboard').append('<tr><td>' + player.nick + '</td>' + '<td id=' + player.nick + '>' + player.score +'</td></tr>')
            }
        });
    }
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

function handleUpdate(update){
    if (update.players) {
        updatePlayers(update.players);
    }
    if(update.powerups) {
        updatePowerUps(update.powerups);
    }
}

function updatePlayers(updates) {
    updates.map( (pl) => {
            let selectedPlayer = _.find(players, (pl2) => pl2.nick === pl.nick);
            if (selectedPlayer) {
                selectedPlayer.x = pl.pos.x;
                selectedPlayer.y = pl.pos.y;
                selectedPlayer.score = pl.score.score;
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

function updatePowerUps(updates) {
    powerUps.map((pu) => pu.updated = false);
    if (updates) {
        updates.map((up) => {
                let selected = _.find(powerUps, (pu) => pu.x === up.position.x && pu.y === up.position.y);
                if (selected) {
                    selected.updated = true;
                } else {
                    let power = new PowerUp(up, scale);
                    powerUps.push(power);
                    power.render();
                }
            }
        );
    }
    powerUps.filter((pu) => !pu.updated).forEach((pu) => {
        pu.hide();
        let indx = powerUps.indexOf(pu);
        powerUps.splice(indx, 1)
    });
}

function kill(player) {
    deathSound.play();
}

function start() {
    communicator.start();
}

function getColor(){
    let colour = playerColours[playerColourCounter];
    playerColourCounter = (playerColourCounter + 1) % 4 ;
    return colour;
}

function restart() {
    function callback (){
        players = [];
        powerUps = [];
        clear();
        setup();
    }
    communicator.restart(callback);
}