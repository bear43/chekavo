async function sendPostReq(url, data, successFunction)  {
    await $.ajax({
    url: url,
    type: "POST",
    data: JSON.stringify(data),
    headers: {
        'Content-Type': 'application/json'
    },
    success: successFunction
});
}
async function sendPostReqRet(url, data)  {
    let retVal;
    await sendPostReq(url, data, function(variable) { retVal = variable; });
    return retVal;
}