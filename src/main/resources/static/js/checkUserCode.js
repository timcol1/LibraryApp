function compareCodeFromEmailWithUserCode() {
    var input1 = document.getElementById("code").value;
    var input2 = document.getElementById("secret_code").value;
    if (input1 === input2) {
        document.getElementById("restore-form").submit();
    } else {
        document.getElementById("result").innerHTML = "Було ведено невірне значення";
    }
}