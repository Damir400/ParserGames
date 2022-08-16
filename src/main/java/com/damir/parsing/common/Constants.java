package com.damir.parsing.common;

public class Constants {
    public static String getSteamParingJsScript(){
        return "let allTags = [];\n" +
                "    let result = [];\n" +
                "    let tagNamesGame = [];\n" +
                "\n" +
                "    let tags = document.querySelectorAll(\"#TagFilter_Container > div:nth-child(n+0)\");\n" +
                "    for (let i = 1; i < tags.length; i++) {\n" +
                "        allTags.push({\n" +
                "            'tagNumber': tags[i].getAttribute('data-value'),\n" +
                "            'tagName': tags[i].getElementsByClassName('tab_filter_control_label')[0].innerText.replaceAll('\\n', '').replaceAll('\\t','')\n" +
                "        });\n" +
                "    }\n" +
                "    let all = document.querySelectorAll(\"#search_resultsRows > a:nth-child(n+0)\");\n" +
                "    const ref = 'header.jpg?';\n" +
                "    for (let i=0, max=all.length; i < max; i++) {\n" +
                "        let prev = all[i].getElementsByTagName('img')[0].src.split('capsule');\n" +
                "        let res = prev[0] + ref + prev[1].split('?')[1];\n" +
                "        //---------------------------\n" +
                "        let allTagsGame = all[i].getAttribute('data-ds-tagids').split(',');\n" +
                "        tagNamesGame = allTags.filter((el) => {\n" +
                "            return allTagsGame.some((f) => {\n" +
                "                return f === el.tagNumber;\n" +
                "            });\n" +
                "        });\n" +
                "        result.push({\n" +
                "            'name': all[i].innerText.split('\\n')[1],\n" +
                "            'url': all[i].href,\n" +
                "            'img': res,\n" +
                "            'tags': tagNamesGame.map(a => a.tagName).join(';'),\n" +
                "            'nameStore': 'Steam'\n" +
                "        });\n" +
                "\n" +
                "    }\n" +
                "    return JSON.stringify(result);";
    }
}
