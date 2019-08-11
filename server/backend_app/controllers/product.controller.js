const Product = require("../models/product.model.js");

exports.create = (req, res) => {
  console.log("req: " + req);
  // Validate request
  if (!req.body.name) {
    return res.status(400).send({
      message: "member email can not be empty"
    });
  }

  const product = new Product({
    name: req.body.name,
    category: req.body.category,
    price: req.body.price
  });

  product
    .save()
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message: err.message || "Some error occurred while creating the member."
      });
    });
};

exports.get = (req, res) => {
  return res.status(200).send({
    message: "sample response.. test"
  });
};
