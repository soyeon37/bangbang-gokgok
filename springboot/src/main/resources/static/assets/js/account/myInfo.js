const myInfo_modal = new bootstrap.Modal(document.getElementById("modal_myInfo"), {
  keyboard: false,
});

const myId = document.getElementById("id-mine");
const changePW = document.getElementById("password-change");
const changeNickname = document.getElementById("nickname-change");
const confirmChangeBtn = document.getElementById("confirm-change");
const confirmDeleteBtn = document.getElementById("confirm-delete");

function init() {
  const ID = localStorage.getItem("isLogin");
  if (ID != null) {
    myId.appendChild(document.createTextNode(ID));
    const info = localStorage.getItem(ID).split(",");
    changePW.value = info[0];
    changeNickname.value = info[1];
  }
  confirmChangeBtn.addEventListener("click", (e) => {
    e.preventDefault();
    changeInfo();
  });
  confirmDeleteBtn.addEventListener("click", deleteAccount);
}

// 내 정보 수정하기
function changeInfo() {
  const ID = localStorage.getItem("isLogin");
  if (changePW.value) {
    if (changeNickname.value) {
      localStorage.setItem(ID, `${changePW.value},${changeNickname.value}`);
      myInfo_modal.hide();
    } else {
      alert("닉네임을 입력하세요!");
    }
  } else {
    alert("비밀번호를 입력하세요!");
  }
}

// 탈퇴하기
function deleteAccount() {
  const ID = localStorage.getItem("isLogin");
  localStorage.removeItem(ID);
  localStorage.removeItem("isLogin");
}

init();
