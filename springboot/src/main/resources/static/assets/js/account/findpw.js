const find_pw_modal = new bootstrap.Modal(document.getElementById("modal_find_pw"), {
  keyboard: false,
});

const id_findpw = document.querySelector("#id_findpw");
const id_findpw_check = id_findpw.querySelector("input");
const findpwBtn = document.querySelector(".findpwBtn");

function active_find() {
  let find_id = id_findpw_check.value;
  console.log("find activated");
  let isFind = false;
  for (let i = 0; i < localStorage.length; i++) {
    let key = localStorage.key(i);
    if (find_id == key) {
      isFind = true;
      const info = localStorage.getItem(key).split(",");
      alert(`비밀번호는 ${info[0]} 입니다.`);
      find_pw_modal.hide();
      break;
    }
  }
  if (!isFind) {
    alert("일치하는 아이디가 없습니다.");
  }
}

function init() {
  findpwBtn.addEventListener("click", (e) => {
    e.preventDefault();
    active_find();
  });
  console.log("init find");
}

init();
