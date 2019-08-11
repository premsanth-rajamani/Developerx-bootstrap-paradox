module.exports = app => {
  const product = require("../controllers/product.controller.js");
  const productImg = require("../controllers/product-img.controller.js");
  // Create a new Member
  app.post("/product/create", product.create);

  app.get("/product/get", product.get);

  app.get("/product/image", productImg.get);

  //app.post("/product/img-to-text", productImg.saveImg);
  // Retrieve a single Member with email
  //   app.get("/product/:name", product.findOne);
};
