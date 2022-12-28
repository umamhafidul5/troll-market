
const tambahDana = document.querySelector(".tambah-dana");

const popUp = document.querySelector(".pop-up");

const batal = document.querySelector(".batal");

var object;

const info = document.querySelectorAll(".info");

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



if (info != null) {
    info.forEach(i => {
        i.addEventListener("click", ()=>{
            fetchInfo(i.value);
        })//fetchInfo(i.value))
    })

}

async function fetchInfo (id){
    popUp.classList.add("show");

    await fetch('http://localhost:8080/merchandise/infoProduct?id='+id)
            .then(response => response.json())
            .then(data => object = data)

    popUp.children[0].children[0].innerText = object.name;
    popUp.children[1].children[0].innerText = object.category;
    popUp.children[2].children[0].innerText = object.description;
    popUp.children[3].children[0].innerText = object.price;
    popUp.children[4].children[0].innerText = object.isDiscontinue == true? 'yes' : 'no' ;
}