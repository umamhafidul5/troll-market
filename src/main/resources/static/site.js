
const tambahDana = document.querySelector(".tambah-dana");

const popUp = document.querySelector(".pop-up");

const batal = document.querySelector(".batal");


tambahDana.addEventListener("click", () => {
    console.log("click");
    popUp.classList.add("show");
})

batal.addEventListener("click", () => {
    console.log("click");
    popUp.classList.remove("show");
})