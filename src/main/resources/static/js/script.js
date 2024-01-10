let password1 = document.getElementById("inputPassword1")
let password2 = document.getElementById("inputPassword2")
let confirmPasswordText = document.getElementById("confirmPasswordText")

password1.addEventListener("blur", confirmPassword)
password2.addEventListener("blur", confirmPassword)
password2.addEventListener("input", confirmPassword)

function confirmPassword(){
    if(password1.value != password2.value){
        confirmPasswordText.innerHTML = "passwords do not match!"
        confirmPasswordText.hidden = false
    }else{
        confirmPasswordText.innerHTML = ""
        confirmPasswordText.hidden = false
    }
}
