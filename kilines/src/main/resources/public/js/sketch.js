const w = 1280, h = 960;
const playerStartX = w / 2 - 80;
const playerStartY = h / 2;
const backgroundColor = 23;
const rectW = 4;
const rectH = 4;
const playerColours = [
    [0, 167, 247]
];

let players;
let communicator = new Communicator(location.href.replace('/game.html',''), handleUpdate);

function setup() {
    createCanvas(w, h);
    background(backgroundColor);
    communicator.init(initState);
}

function draw() {
    renderPlayers();
    scoreboard()
}

function initState(state){
    console.log(state);
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
        textFont("Orbitron");
        textSize(18);
        text("Score");
        let i = 0;
        players.map((player) =>{
            i += 25;
            text(player.nick + " : " + player.score, 1000, 25)
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
        communicator.update(0);
    } else if (key === 'd' || key === 'D'){
        console.log('d');
        communicator.update(1);
    } else if (key === 's' || key === 'S'){
        console.log('s');
        communicator.update(2);
    } else if (key === 'a' || key === 'A'){
        console.log('a');
        communicator.update(3);
    }
}

class Player {

    constructor(data , color) {
        this.color = color;
        this.x = data.pos.x;
        this.y = data.pos.y;
        this.nick = data.nick;
        this.score = 0;
    }

    render () {
        fill(255);
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
            }
        }
    )
}

let c = 0;

function getState() {
    return {
        'type' : 'init',
        'players': [
            {
                'nick': 'Andrzej',
                'pos': {x: playerStartX, y: playerStartY},
                'dir': 0
            }
        ],
        'colors': [[244, 66, 66],[143, 244, 65]],
        'obstacles' :[
            {'pos': {'x': 0,'y': 0}, 'color': 0},
            {'pos': {'x': 0,'y': 1}, 'color': 0},
            {'pos': {'x': 0,'y': 2}, 'color': 0},
            {'pos': {'x': 0,'y': 3}, 'color': 0},
            {'pos': {'x': 0,'y': 4}, 'color': 0},
            {'pos': {'x': 0,'y': 5}, 'color': 0},
            {'pos': {'x': 0,'y': 6}, 'color': 0},
            {'pos': {'x': 0,'y': 7}, 'color': 0},
            {'pos': {'x': 0,'y': 8}, 'color': 0},
            {'pos': {'x': 0,'y': 9}, 'color': 0},
            {'pos': {'x': 0,'y': 10}, 'color': 0},
            {'pos': {'x': 0,'y': 11}, 'color': 0},
            {'pos': {'x': 0,'y': 12}, 'color': 0},
            {'pos': {'x': 0,'y': 13}, 'color': 0},
            {'pos': {'x': 0,'y': 14}, 'color': 0},
            {'pos': {'x': 0,'y': 15}, 'color': 0},
            {'pos': {'x': 0,'y': 16}, 'color': 0},
            {'pos': {'x': 0,'y': 17}, 'color': 0},
            {'pos': {'x': 300,'y': 300}, 'color': 1},
            {'pos': {'x': 301,'y': 300}, 'color': 1},
            {'pos': {'x': 302,'y': 300}, 'color': 1},
            {'pos': {'x': 303,'y': 300}, 'color': 1},
            {'pos': {'x': 304,'y': 300}, 'color': 1},
            {'pos': {'x': 305,'y': 300}, 'color': 1},
            {'pos': {'x': 306,'y': 300}, 'color': 1},
            {'pos': {'x': 307,'y': 300}, 'color': 1}
        ]
    }
}

