


function filterTag(){
    let allTags = [];
    let tags = document.querySelectorAll("#TagFilter_Container > div:nth-child(n+0)");
    for (let i = 1; i < tags.length; i++) {
        allTags.push({
            'tagName': tags[i].getElementsByClassName('tab_filter_control_label')[0].innerText.replaceAll('\n', '').replaceAll('\t',''),
            'count': 0
        });
    }
    return allTags;
}

function steamParserWithTag() {
    let allTags = [];
    let result = [];
    let tagNamesGame = [];

    let tags = document.querySelectorAll("#TagFilter_Container > div:nth-child(n+0)");
    for (let i = 1; i < tags.length; i++) {
        allTags.push({
            'tagNumber': tags[i].getAttribute('data-value'),
            'tagName': tags[i].getElementsByClassName('tab_filter_control_label')[0].innerText.replaceAll('\n', '').replaceAll('\t','')
        });
    }
    let all = document.querySelectorAll("#search_resultsRows > a:nth-child(n+0)");
    const ref = 'header.jpg?';
    for (let i=0, max=all.length; i < max; i++) {
        let prev = all[i].getElementsByTagName('img')[0].src.split('capsule');
        let res = prev[0] + ref + prev[1].split('?')[1];
        //---------------------------
        let allTagsGame = all[i].getAttribute('data-ds-tagids').split(',');
        tagNamesGame = allTags.filter((el) => {
            return allTagsGame.some((f) => {
                return f === el.tagNumber;
            });
        });
        result.push({
            'name': all[i].innerText.split('\n')[1],
            'url': all[i].href,
            'img': res,
            'tags': tagNamesGame.map(a => a.tagName).join(';'),
            'nameStore': 'Steam'
        });

    }
    return JSON.stringify(result);
}







function steamParser() {
    let result = [];
    let all = document.querySelectorAll("#search_resultsRows > a:nth-child(n+0)");
    const ref = 'header.jpg?';
    for (let i=0, max=all.length; i < max; i++) {
        let prev = all[i].getElementsByTagName('img')[0].src.split('capsule');
        let res = prev[0] + ref + prev[1].split('?')[1];
        result.push({
            'tag': all[i].innerText.split('\n')[0],
            'url': all[i].href,
            'img': res
        });

    }
    return result;
}


function steamParser1() {
    let result = [];
    let all = document.querySelectorAll("#search_resultsRows > a:nth-child(n+0)");

    for (let i=0, max=all.length; i < max; i++) {
        result.push({
            'tag': all[i].innerText.split('\n')[1],
            'url': all[i].href,
            'img': all[i].getElementsByTagName('img')[0].src
        });

    }
    return result;

}











steamParser();

function ubisoftParser(){
    let result = [];
    let count = 20;
    for (let i=1; i <= count; i++) {
        result.push({
            'name': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +')').innerText.split('\n')[0],
            'url': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +') > div:nth-child(2) > a').href,
            'img': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +') > div > div').getElementsByTagName('img')[0].src
        });

    }
    return JSON.stringify(result);
}

steamParser();

function hambleParser(){
    let result = [];
    let count = document.querySelectorAll('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +
        'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +
        'div.list-content.js-list-content.show-status-container > div > ul > li').length;
    for (let i=1; i < count; i++) {
        let element = document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > ' +
            'section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > ' +
            'div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i + ') ').innerText.split('\n');
        if(element[element.length-1]==='₽ 0.00'){
            result.push({
                'name': element[0],
                'url': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +
                    'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +
                    'div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a').href,
                'img': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > ' +
                    'div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list >' +
                    ' div > div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a > div > img').src
            });
        }
        else {
            break;
        }


    }
    return JSON.stringify(result);
}

function hambleParser2(){
    let result = [];
    let count = document.querySelectorAll('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +
        'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +
        'div.list-content.js-list-content.show-status-container > div > ul > li').length;
    for (let i=1; i < count; i++) {
        let element = document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > ' +
            'section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > ' +
            'div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i + ') ').innerText.split('\n');
        if(element[element.length-1]==='₽ 0.00'){
            result.push({
                'name': element[0],
                'url': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +
                    'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +
                    'div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a').href,
                'img': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > ' +
                    'div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list >' +
                    ' div > div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a > div > img').src
            });
        }
        else {
            break;
        }


    }
    return JSON.stringify(document.querySelectorAll('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +
        'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +
        'div.list-content.js-list-content.show-status-container > div > ul > li').length);
}