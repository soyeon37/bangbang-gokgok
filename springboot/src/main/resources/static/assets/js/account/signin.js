const id_signin = document.querySelector("#id_signin");
const id_signin_input = id_signin.querySelector("input");
const pw_signin = document.querySelector("#pw_signin");
const pw_signin_input = pw_signin.querySelector("input");
const nicknmae_signin = document.getElementById("nickname_signin");
const nickname_signin_input = nicknmae_signin.querySelector("input");
const form = document.querySelector(".signinBtn");

let cur_id = id_signin_input.value;
let cur_pw = pw_signin_input.value;

function handleSubmitInfo(event) {
  console.log("start handle");
  let cur_id = id_signin_input.value;
  let cur_pw = pw_signin_input.value;
  let cur_nickname = nickname_signin_input.value;
  localStorage.setItem(cur_id, `${cur_pw},${cur_nickname}`);
}

function init() {
  form.addEventListener("click", handleSubmitInfo);
  console.log("init finish");
}

init();
