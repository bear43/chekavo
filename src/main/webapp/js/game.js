let gameField;
let infoField;
let buttonShowNumber;
let buttonSend;
let answerField;
let historyField;
let adventuresField;
let buttonHistory;
let labelTotalGames;
let adventures;
let oldContent;
let buttonBackFromTurns;

async function onButtonShowNumberClick() {
    let answer = await sendPostReqRet("/giveup", null);
    if(answer.successful === true) {
        answerField[0].textContent = answer.number;
    } else {
        answerField[0].textContent = "Exception: " + answer.exception;
    }
}

async function onButtonSendClick()  {
    let answer = await sendPostReqRet("/check", {
        userValue: $("#fieldUsersPredict")[0].value
    });
    if(answer.successful === true) {
        answerField[0].textContent =
            answer.bulls + "Б" +
            answer.cows + "К";
            if(answer.equals === true)  {
                answerField[0].textContent += " (Число угадано)";
                buttonShowNumber.hide();
                buttonSend.hide();
            }
    } else {
        answerField[0].textContent = "Exception: " + answer.exception;
    }
}

async function onButtonNewGameClick() {
    await sendPostReqRet("/newGame", null);
    answerField[0].textContent = "";
    $("#fieldUsersPredict")[0].value = null;
    gameField.show();
    buttonShowNumber.show();
    infoField.hide();
    buttonSend.show();
    historyField.hide();
}

function onButtonBackFromTurns() {
    buttonBackFromTurns.hide();
    labelTotalGames.show();
    adventuresField[0].innerHTML = oldContent;
    $(".ads").on("click", function(elem) { onAdsClick(elem) });
}

function onAdsClick(elem) {
    labelTotalGames.hide();
    infoField.hide();
    oldContent = adventuresField[0].innerHTML;
    let index = elem.currentTarget.firstChild.value;
    let turns = adventures[index].turns;
    adventuresField[0].innerHTML = "";
    for(let i in turns) {
        adventuresField[0].innerHTML +=
            "<p class='text-dark'>#"  +
            turns[i].turnNumber + " " +
            turns[i].userNumber + " " +
            turns[i].bullCount + "Б" +
            turns[i].cowCount + "К";
            if(turns[i].lastTurn === true) {
                adventuresField[0].innerHTML += " (число угадано)";
            }
        //adventuresField[0].innerHTML += "</p>";
    }
    buttonBackFromTurns.show();
    //adventuresField[0].innerHTML = "";
}

async function onButtonHistoryShow() {
    gameField.hide();
    historyField.show();
    infoField.hide();
    adventures = await sendPostReqRet("/history", null);
    labelTotalGames[0].textContent = "Total adventures count: " + adventures.length;
    adventuresField[0].innerHTML = "";
    for (let i in adventures) {
        adventuresField[0].innerHTML +=
            "<div class = ads>" +
                "<input type='hidden' value=\""+ i +"\">" +
                "<label class=\"text-primary\">"+
                adventures[i].number + " | " + adventures[i].turns.length +
                "</label>\n" +
                "        <br/>"+
            "</div>";
    }
    $(".ads").on("click", function(elem) { onAdsClick(elem); });
}