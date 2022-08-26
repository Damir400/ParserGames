const prevBtnName = 'Prev';
const nextBtnName = 'Next';
let countPages = 0;
let currentPage = 0;

async function loadGames(page) {
    if(page) {
        currentPage = page;
    }
    else {
        currentPage = page = 1;
    }
    await reloadPageNavBar();
    const data = await (await fetch("./api/games/" + page)).json();
    buildCardsContainer(data);
}

async function searchGames(){
    let name = document.getElementById("searchGames").value;
    const data = await (await fetch("./api/searchGames/" + name)).json();
    buildCardsContainer(data);
}

function buildCard(game){
    let h5 = document.createElement('h5');
    h5.className = 'card-title';
    h5.textContent = game.name;

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

    if(name === prevBtnName && currentPage === 1) {
        li.className = li.className + ' disabled';
    }

    if(name == nextBtnName && currentPage === countPages) {
        li.className = li.className + ' disabled';
    }

    if(page === currentPage) {
        li.setAttribute('aria-current', "page");
        li.className = li.className + ' active';
    }

    let a = document.createElement('a');
    a.className = 'page-link';
    a.textContent = name;

    if(page > 0 && page !== currentPage) {
        a.addEventListener('click', function () {
            a.href = '#';
            loadGames(page);
        }, false);
    }

    li.appendChild(a);
    return li;
}

function addPageBar(countPages){
    if(currentPage === 0 && countPages > 0) {
        currentPage = 1;
    }
    let addPageNavBar = document.querySelector("#topPageNavBar");

    addPageNavBar.innerHTML = '';

    if(currentPage === 1) {
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(currentPage, prevBtnName));
    }
    else {
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(currentPage-1, prevBtnName));
    }

    if(countPages<=7) {
        for (let i = 1; i <= countPages; i++) {
            addPageNavBar.insertAdjacentElement("beforeend", getPageButton(i, i));
        }
    }
    else {
        for (let i = 1; i <= 3; i++) {
            addPageNavBar.insertAdjacentElement("beforeend", getPageButton(i, i));
        }
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(0, '...'));
        for (let i = countPages-2; i <= countPages; i++) {
                addPageNavBar.insertAdjacentElement("beforeend", getPageButton(i, i));
        }
    }

    if(currentPage === countPages) {
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(countPages, nextBtnName));
    }
    else if(currentPage < countPages) {
        addPageNavBar.insertAdjacentElement("beforeend", getPageButton(currentPage + 1, nextBtnName));
    }
}

async function parsingData(){
    countPages = await (await fetch('./api/load')).json();
    addPageBar(countPages);
}
//window.onload = loadGames;
async function getPageCount(){
    countPages = await (await fetch('./api/getPagesCount')).json();
}


async function reloadPageNavBar(){
    await getPageCount();
    addPageBar(countPages);
}







//вызывется при загрузке страницы (подгружает navBar)
window.onload = async function (){
    await reloadPageNavBar();
};


