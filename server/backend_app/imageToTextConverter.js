const parser = require("./parser");
const Product = require("./models/product.model");

const Thing = require("./models/things.model");
const mongoose = require("mongoose");
const Merchant = require("./models/merchant.model");
const vision = require("@google-cloud/vision")({
  projectId: "bootstrap-paradox",
  keyfileName: "../resources/credentials.json"
});

module.exports.run = (fileName, res) => {
  convertToTextAndStore(__dirname + "/upload/" + fileName, res, fileName);
};

function convertToTextAndStore(fileName, res, actualName) {
  console.log("filename: " + fileName);
  vision
    .readDocument(fileName)
    .then(results => {
      if (
        results &&
        results[1] &&
        results[1].responses &&
        results[1].responses[0]
      ) {
        parseAndStoreData(results[1].responses[0], actualName, res);
      } else {
        console.log("cannot parse");
      }
    })
    .catch(err => {
      console.error("ERROR:", err);
      // return res.status(200).send({
      //   message: "image to text conversion is failed"
      // });
    });
}

function parseAndStoreData(json, imgName, res) {
  // try {
  let responseData = parser.init(json);
  console.log("responseData: " + JSON.stringify(responseData));
  saveImgData(responseData, imgName);
  if (responseData) {
    // console.log("as: " + JSON.stringify(responseData));
    // console.log("res " + JSON.stringify(responseData.merchantData));
    let { productData, merchantData } = responseData;
    if (merchantData && imgName && productData) {
      merchantData["imgPath"] = imgName;
      productData["imgPath"] = imgName;
    }
    productData.map(e => saveProductData(e, merchantData));
  }
  // } catch (e) {
  //   console.log("error :" + e);
  //   // res.status(500).send({
  //   //   message: err.message || "Some error occurred while creating the product."
  //   // });
  // }
}

function saveProductData(data, merchantData) {
  const product = new Product(
    {
      _id: new mongoose.Types.ObjectId(),
      name: data.name,
      price: data.price,
      category: "default",
      status: "TO_BE_PROCESSED",
      imgPath: [data.imgPath]
    },
    {
      timestamps: true
    }
  );

  Product.find({ name: data.name }, function(err, doc) {
    if (doc && doc.length) {
      console.log("1");
      let imgPath = doc.imgPath
        ? doc.imgPath.push(data.imgPath)
        : [data.imgPath];
      // doc["imgPath"].push(imgPath);
      product.update({ _id: doc.id }, product, function(err, value) {
        // console.log(JSON.stringify("value" + value));
        saveMerchantData(merchantData, value.id);
      });
    } else {
      product.save(function(err, value) {
        // console.log(JSON.stringify("value" + value));
        saveMerchantData(merchantData, value.id);
      });
    }
  });
}

function saveMerchantData(data, productId) {
  const merchant = new Merchant(
    {
      _id: new mongoose.Types.ObjectId(),
      name: data.name,
      address: data.address,
      tin: data.tin,
      pincode: data.pincode,
      products: [{ id: productId }]
    },
    {
      timestamps: true
    }
  );
  Merchant.find({ name: data.name }, function(err, doc) {
    console.log(doc);
    if (doc && doc.length > 0) {
      console.log("3");
      merchant.update(
        { _id: doc.id },
        { $push: { products: { id: productId } } }
      );
    } else {
      //console.log("4");
      merchant.save();
    }
  });
}

function saveImgData(data, img) {
  data["img"] = img;
  let thing = new Thing({
    _id: new mongoose.Types.ObjectId(),
    img: img,
    productData: data.productData,
    merchantData: data.merchantData
  });
  thing.save();
}

exports.getImgData = (req, res) => {
  console.log("req: " + JSON.stringify(req.params.img));
  const img = req.params.img;
  Thing.find({ img: img }, function(err, responseData) {
    //console.log(responseData);
    if (responseData && responseData.length > 0) {
      res.send({ data: responseData });
    } else {
      return res.status(404).send({ responseData: "img not found " });
    }
  }).catch(err => {
    return res.status(500).send({
      message: "Error retrieving member with email " + req.params.filename
    });
  });
};
