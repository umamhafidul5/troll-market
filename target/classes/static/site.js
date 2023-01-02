
const tambahDana = document.querySelector(".tambah-dana");
const addToCart = document.querySelectorAll(".addToCart");


const popUp = document.querySelector(".pop-up");
const popUp2 = document.querySelector(".pop-up2");

const batal = document.querySelector(".batal");
const batal2 = document.querySelector(".batal2");

let object;

const formatter = new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR'
});

const info = document.querySelectorAll(".info");

if (tambahDana != null) {
    tambahDana.addEventListener("click", () => {
        console.log("click");
        popUp.classList.add("show");
    })
}

if (addToCart != null) {
    addToCart.forEach(btn => {
        btn.addEventListener("click", (event) => {
            event.preventDefault()
            const link = btn.parentElement.href;
            const idMerchandise = link.substring(link.indexOf("=") + 1,  link.length);

            document.querySelector(".id-merchandise").value = idMerchandise;
            popUp2.classList.add("show");
        })

    })

}

//if (addToCart != null) {
//    addToCart.addEventListener("click", (event) => {
//        event.preventDefault()
//        const link = addToCart.parentElement.href;
//        const link = "http://localhost:8080/shop/addToCart2";
//        const link = "/shop/addToCart2"
//        popUp2.classList.add("show");
//
//        let data = {
//            "id": 4,
//            "quantity": 5,
//            "shipment": "JNE"
//        }
//
//        const submit = document.querySelector(".submit");
//
//        submit.addEventListener("click", () => {
//            fetchPost(link, data);
//            console.log(data);
//            console.log(link);
//        })
//
//    })
//}


if (batal != null) {
    batal.addEventListener("click", () => {
        console.log("click");
        popUp.classList.remove("show");
    })
}

if (batal2 != null) {
    batal2.addEventListener("click", () => {
        console.log("click");
        popUp2.classList.remove("show");
    })
}



if (info != null) {
    info.forEach(i => {
        i.addEventListener("click", (event)=>{
            event.preventDefault();
            fetchInfo(i.parentElement.href);
        })//fetchInfo(i.value))
    })
}

async function fetchInfo (url){
    popUp.classList.add("show");

//    await fetch('http://localhost:8080/merchandise/infoProduct?id='+id)
    await fetch(url)
            .then(response => response.json())
            .then(data => object = data)

    popUp.children[0].children[1].innerText = object.name;
    popUp.children[1].children[1].innerText = object.category;
    popUp.children[2].children[1].innerText = object.description;
    popUp.children[3].children[1].innerText = formatter.format(object.price);
<<<<<<< HEAD

    if (url.includes("merchandise")) {
        popUp.children[4].children[1].innerText = object.isDiscontinue == true? "yes" : "no" ;
    } else if (url.includes("shop")) {
        popUp.children[4].children[1].innerText = object.seller.firstName + " " + object.seller.lastName ;
    }

}

async function fetchPost (url, data){

//    let data = new FormData();
//        data.append("quantity", document.querySelector('.quantity').value);
//        data.append("shipment", document.querySelector('.shipment').value);

    await fetch(url, {
          body: JSON.stringify(data),
          method: 'POST',
          headers: {
            'Content-Type': 'application/json; charset = UTF-8'
          }
    })

//    .then(res => res.json())
//        .then(teks => console.log(teks))
//        .catch(err => console.log(err));
=======
    popUp.children[4].children[1].innerText = object.isDiscontinue == true? "yes" : "no" ;
}


if (window.location.href === "http://localhost:8080/shipment/add" ||
    window.location.href === "http://localhost:8080/profile/top-up"

) {

    popUp.classList.add("show");
>>>>>>> 444ed5b (menambahkan validasi)
}