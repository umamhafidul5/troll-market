function showPasswordDoesntMatch() {
    var password = document.getElementById('password');
    var confirm = document.getElementById('passwordConfirmation');
    var text = document.getElementById('text');
    var button = document.getElementById('button');
    if (password.value == "" && confirm.value == "") {
        text.innerHTML = "";
        button.setAttribute('disabled', 'disabled');
    } else if (password.value === confirm.value) {
        text.innerHTML = "";
        button.removeAttribute('disabled');
    } else {
        text.innerHTML = "Password doesn't match";
        button.setAttribute('disabled', 'disabled');
    }
}

function toggleShowPassword() {
    var password = document.getElementById('password');
    var confirm = document.getElementById('passwordConfirmation');
    if (password.type === 'password') {
        password.type = 'text';
        confirm.type = 'text';
    } else {
        password.type = 'password';
        confirm.type = 'password';
    }
}

function isInputtedKeyANumber(event) {
    event = (event) ? event : window.event;
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function maxNumberOnly() {
    var inputtedNumber =  parseInt(document.getElementById('totalGuest').value);

    if (inputtedNumber > 200) {
        document.getElementById('totalGuest').value = '200';
    }
}

window.onload = function() {
    document.getElementById('totalGuest').value = '1';
}
