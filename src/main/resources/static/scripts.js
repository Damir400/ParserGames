//Отправляет запрос для получения гифки.
let imagesUrls = [];
let gamesUrls = [];
let imagesItems = [];
let imagesLoaded = 0;

// function waiter(){
//     let gifWaiter = new Image();
//     gifWaiter.src = "/images/waitLoader.gif";
//     document.body.appendChild(gifWaiter);
// }
//
// function createGif() {
//     for (const item of imagesUrls) {
//         let img = new Image(500,500);
//         img.onload = function() {
//             imagesLoaded++;
//         }
//         img.src = item.url;
//         imagesItems.push(img);
//     }
// }
//
// async function loadResultGif() {
//     imagesLoaded = 0;
//     imagesUrls = [];
//     imagesItems = [];
//     document.querySelector('img')?.remove();
//     waiter();
//     let code = $("#codesSelector").val();
//     const result = await (await fetch('./api/epic/' + code)).json();
//     imagesUrls.push(...result);
//     createGif();
// }

//Заполняет select
async function loadGames() {
    imagesItems = [];
    gamesUrls = [];
    imagesUrls = [];

    const data = await (await fetch('./api/getgames')).json();
    buildCardsContainer(data);

}

function buildCard(game){
    let h5 = document.createElement('h5');
    h5.className = 'card-title';
    h5.textContent = game.name;

    let div3 = document.createElement('div');
    div3.className = 'card-body';
    div3.href = game.url;
    let img = new Image();
    img.className = 'card-img-top';
    img.src = game.img;
    let div2 = document.createElement('div');
    div2.className = 'card';
    let div1 = document.createElement('div');
    div1.className = 'col';

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
    let addgames = document.querySelector("#gamecards");
    for (let i = 0; i < count; i++) {
        let card = buildCard(games[i]);
        addgames.insertAdjacentElement("beforeend", card);
    }
}

async function parsingData(){
    await (await fetch('./api/load'));
}
//window.onload = loadGames;

// let changeIndex = 0;

// setInterval(() => {
//     if (imagesLoaded == imagesUrls.length && imagesLoaded > 0) {
//         document.querySelector('img')?.remove();
//         document.body.appendChild(imagesItems[changeIndex]);
//         changeIndex++;
//         if(changeIndex === imagesUrls.length) {
//             changeIndex = 0;
//         }
//     }
// }, 100)