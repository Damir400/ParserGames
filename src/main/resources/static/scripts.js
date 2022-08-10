

let countPages = 0;

//Заполняет select
async function loadGames() {
    const data = await (await fetch('./api/getgames')).json();
    buildCardsContainer(data);
}

async function loadGames(page) {
    const data = await (await fetch("./api/games/" + page)).json();
    buildCardsContainer(data);
}

function buildCard(game){
    let h5 = document.createElement('h5');
    h5.className = 'card-title';
    h5.textContent = game.name;
    //h5.href = game.url;
    let div3 = document.createElement('div');
    div3.className = 'card-body';
    let img = new Image();
    img.className = 'card-img-top';
    img.src = game.img;
    let div2 = document.createElement('div');
    div2.className = 'card';

    let div1 = document.createElement('div');
    div1.className = 'col';
    div1.addEventListener('click', function() {
        location.href = game.url
    }, false);

    div3.appendChild(h5);
    div2.appendChild(img);
    div2.appendChild(div3);
    div1.appendChild(div2);
    return div1;
}

function buildCardsContainer(games){
    let count = 0;
    if(games.length>50){
        count = 50;
    }
    else {
        count = games.length;
    }
    let addGames = document.querySelector("#gamecards");
    addGames.innerHTML = '';

    for (let i = 0; i < count; i++) {
        let card = buildCard(games[i]);
        addGames.insertAdjacentElement("beforeend", card);
    }
}

function getPageButton(page, name){

    let li = document.createElement('li');
    li.className = 'page-item';
    let a = document.createElement('a');
    a.className = 'page-link';
    a.textContent = name;

    a.addEventListener('click', function() {
        a.href = '#';
        loadGames(page);
    }, false);
    li.appendChild(a);
    return li;
}

function addPageBar(countPages){
    let addPageNavBar = document.querySelector("#topPageNavBar");

    addPageNavBar.innerHTML = '';
    addPageNavBar.insertAdjacentElement("beforeend", getPageButton(12,'previous'));

    for (let i = 1; i <= countPages; i++) {
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(i,i));
    }
    addPageNavBar.insertAdjacentElement("beforeend", getPageButton(13,'next'));
}

async function parsingData(){
    countPages = await (await fetch('./api/load')).json();
}
//window.onload = loadGames;
async function getPageCount(){
    countPages = await (await fetch('./api/getPagesCount')).json();
}

window.onload = async function (){
    await getPageCount();
    addPageBar(countPages);
};
