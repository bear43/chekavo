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

async function onButtonHistoryShow() {
    gameField.hide();
    historyField.show();
    adventures = await sendPostReqRet("/history", null);
    labelTotalGames[0].textContent = "Total adventures count: " + adventures.length;
    adventuresField[0].innerHTML = "";
    for (let i in adventures) {
        adventuresField[0].innerHTML +=
            "<label class=\"text-primary\" class=\"ads\" id=" + adventures[i].id + ">"+
            adventures[i].number + " | " + adventures[i].turns.length +
            "</label>\n" +
            "        <br/>";

    }
}