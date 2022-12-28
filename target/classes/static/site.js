
const tambahDana = document.querySelector(".tambah-dana");

const popUp = document.querySelector(".pop-up");

const batal = document.querySelector(".batal");


<<<<<<< HEAD
const info = document.querySelector(".info");

if (tambahDana != null) {
    tambahDana.addEventListener("click", () => {
        console.log("click");
        popUp.classList.add("show");
    })
}

if (batal != null) {
    batal.addEventListener("click", () => {
        console.log("click");
        popUp.classList.remove("show");
    })
}


console.log("info ", info);
if (info != null) {
    info.addEventListener("click", () => {
        console.log("click");
        popUp.classList.add("show");
    })
}

=======
tambahDana.addEventListener("click", () => {
    popUp.classList.add("show");
})

batal.addEventListener("click", () => {
    popUp.classList.remove("show");
})
>>>>>>> cfa365c615add0d49928bccfe5d001b1e562c9df
