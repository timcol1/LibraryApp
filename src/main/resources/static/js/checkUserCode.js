function checkUserCode() {
    var userInput = document.getElementById("code");
    var secretCode = document.getElementById("secret_code");

    var userValue = userInput.value;
    var secretCodeValue = secretCode.value;

    if (userValue.trim() === "" || userValue !== secretCodeValue) {
        alert("Ваше значення невірне!");
    } else {
        alert("Введенное значение: " + userValue);
    }
}