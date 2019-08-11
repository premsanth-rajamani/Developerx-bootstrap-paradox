const ImgModel = require("../models/product-img.model");
// var formidable = require("formidable"),
//   util = require("util");
const multer = require("multer"),
  storage = multer.diskStorage({
    destination: function(req, file, callback) {
      callback(null, "./upload");
    },
    filename: function(req, file, callback) {
      const filename =
        file.fieldname + "_" + Date.now() + "_" + file.originalname;
      callback(null, filename);
    }
  }),
  upload = multer({ storage: storage }),
  fUpload = upload.fields([{ name: "image", maxCount: 1 }]);

exports.saveImg = (req, res, next) => {
  fUpload(req, res, function(err) {
    if (err) {
      res.send({ message: "An error occurred when uploading" });
    } else {
      saveDataInDb(filename, res);
    }
  });
};

exports.get = (req, res) => {
  ImgModel.find({}).then(resp => {
    if (resp && resp.length > 0) {
      res.send(resp);
    }
  });
};

function saveDataInDb(filename, res) {
  const imageModel = new ImgModel({
    name: filename,
    status: "TO_BE_PROCCESSED"
  });
  imageModel
    .save()
    .then(data => {
      res.status(200).send({ message: "success" });
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while inserting data in DB."
      });
    });
}
