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
let buttonField;
let buttonNumeric;
let buttonBackspace;

async function onButtonShowNumberClick() {
    let answer = await sendPostReqRet("/giveup", null);
    if(answer.successful === true) {
        answerField[0].textContent = answer.number;
    } else {
        answerField[0].textContent = "Exception: " + answer.exception;
    }
}

async function onButtonSendClick()  {
    if($("#fieldUsersPredict")[0].textContent.length < 4){
        alert("Please enter 4 digits");
        return;
    }
    let answer = await sendPostReqRet("/check", {
        userValue: $("#fieldUsersPredict")[0].textContent
    });
    if(answer.successful === true) {
        answerField[0].textContent =
            answer.bulls + "Б" +
            answer.cows + "К";
            if(answer.equals === true)  {
                answerField[0].textContent += " (Число угадано)";
                buttonShowNumber.hide();
                buttonSend.hide();
                buttonField.hide();
            }
    } else {
        answerField[0].textContent = "Exception: " + answer.exception;
    }
}

async function onButtonNewGameClick() {
    await sendPostReqRet("/newGame", null);
    answerField[0].textContent = "";
    $("#fieldUsersPredict")[0].textContent = null;
    gameField.show();
    buttonShowNumber.show();
    infoField.hide();
    buttonSend.show();
    historyField.hide();
    buttonField.show();
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
    buttonField.hide();
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
    buttonField.hide();
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

function checkNumber(current, newOne) {
    current = current.toString();
    for(let i in current){
        if(current[i] === newOne)
            return false;
    }
    return true;
}

function onButtonNumericClick(data) {
    let currentNumber = $("#fieldUsersPredict")[0].textContent;
    if(currentNumber.length < 4 &&
        checkNumber(currentNumber, data.target.textContent)) {
        $("#fieldUsersPredict")[0].textContent += data.target.textContent
    } else {
        alert("You have already entered 4 numbers or your digit is already in the number");
    }
}

function onButtonBackspaceClick() {
    let text = $("#fieldUsersPredict")[0].textContent;
    if(text.length > 0) {
        $("#fieldUsersPredict")[0].textContent =
            text.substring(0, text.length-1);
    }
}

function onButtonClearClick()  {
    $("#fieldUsersPredict")[0].textContent = "";
}