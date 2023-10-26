function checkUserCode() {
    var userInput = document.getElementById("code");
    var secretCode = document.getElementById("secret_code");
    // Получить значение, введенное пользователем
    var userValue = userInput.value;
    var secretCodeValue = secretCode.value;

    // Выполнить проверку, например, проверить, что введенное значение не пустое
    if (userValue.trim() === "" || userValue !== secretCodeValue) {
        alert("Ваше значення невірне!");
    } else {
        alert("Введенное значение: " + userValue);
        // Здесь вы можете выполнить другие проверки или обработку данных
    }
}