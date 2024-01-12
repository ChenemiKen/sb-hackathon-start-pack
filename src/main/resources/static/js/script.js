let password1 = document.getElementById("inputPassword1")
let password2 = document.getElementById("inputPassword2")
let confirmPasswordText = document.getElementById("confirmPasswordText")

if(password1 && password2){
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
}

const toastLiveExample = document.getElementById('liveToast')
const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
if(success){
  document.getElementById("toast-title").innerHTML = "Success"
  document.getElementById("toast-icon").src = "/images/check.png"
  toastBootstrap.show()
}

if(error){
  document.getElementById("toast-title").innerHTML = "Error"
  document.getElementById("toast-icon").src = "/images/error.png"
  toastBootstrap.show()
}
