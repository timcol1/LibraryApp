function comparePasswords() {
    var input1 = document.getElementById("password").value;
    var input2 = document.getElementById("formGroupExampleInput1").value;
    if (input1 === input2) {
        document.getElementById("create-pass-form").submit();
    } else {
        document.getElementById("result").innerHTML = "Паролі не співпадають";
    }
}