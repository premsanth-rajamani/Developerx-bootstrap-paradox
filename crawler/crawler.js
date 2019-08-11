let axios = require('axios')
const jsdom =  require('jsdom')
const {JSDOM} = jsdom
let productName = "oil"
let additionalData = {}
let price
let imageURL
const getData = (productName)=>{
    axios.get(`https://grofers.com/s/?q=${productName}`)
    .then(response =>{
         if(response.status == 200){
            price = getPrice(response.data)
           
         } 
         axios.get(`https://duckduckgo.com/?q=${productName}&t=h_&iax=images&ia=images`)
         .then(res => {
            if(res.status === 200){
                imageURL = getImage(res.data)
            }
         }) 
         .catch(err => {
            console.log(err)
         }) 

    })
    .catch(e =>{
        console.log('parse error',e)
    })

    


const getPrice  = (html)=>{
    const dom = new JSDOM(html)
    const doc = dom.window.document
   // const grofersDom = doc.querySelector(".price.price--grid")
    const priceDom = doc.querySelector('.plp-product__price--new')
    const price = priceDom.innerHTML.replace("<!-- -->","").replace("₹","")
    console.log(price)
    return price
}
const getImage = (html)=>{
    const dom = new JSDOM(html)
    const doc = dom.window.document
    const duckgo = doc.querySelector(".tile--img__img.js-lazyload")
    
}


additionalData['price'] = price
    additionalData['url'] = imageURL
    return additionalData
}
getData(productName)