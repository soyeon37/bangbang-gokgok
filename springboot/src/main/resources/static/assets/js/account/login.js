const login_modal = new bootstrap.Modal(document.getElementById("modal_login"), {
  keyboard: false,
});

const id_login = document.querySelector("#id_login");
const id_login_check = id_login.querySelector("input");
const pw_login = document.querySelector("#pw_login");
const pw_login_check = pw_login.querySelector("input");

const login_off = document.getElementById("login-off");
const login_on = document.getElementById("login-on");

const loginBtn = document.querySelector(".loginBtn");
const logoutBtn = document.getElementById("logout-btn");

function checkLogin() {
  const defaultName = "d-flex justify-content-end";
  const isLogin = localStorage.getItem("isLogin");
  if (isLogin) {
    login_off.className = defaultName + " d-none";
    login_on.className = defaultName;
  } else {
    login_off.className = defaultName;
    login_on.className = defaultName + " d-none";
  }
}

function checkVal() {
  const ID = id_login_check.value;
  const PW = pw_login_check.value;

  if (localStorage.getItem(ID).split(",")[0] == PW) {
    alert("로그인 성공");
    const defaultName = "d-flex justify-content-end";
    login_off.className = defaultName + " d-none";
    login_on.className = defaultName;
    localStorage.setItem("isLogin", ID);
    myId.appendChild(document.createTextNode(ID));
    const info = localStorage.getItem(ID).split(",");
    changePW.value = info[0];
    changeNickname.value = info[1];
    login_modal.hide();
  } else {
    alert("로그인 실패!");
  }
}

function init() {
  checkLogin();
  loginBtn.addEventListener("click", (e) => {
    // 새로고침 방지
    e.preventDefault();
    checkVal();
  });
  logoutBtn.addEventListener("click", logout);
}

init();

function logout() {
  localStorage.removeItem("isLogin");
  window.location.reload();
}
