require("dotenv").config();

var express = require("express");
const dbConfig = require("./config/database.config.js");
const mongoose = require("mongoose");
const ImgModel = require("./models/product-img.model");
var multer = require("multer");
var fileName,
  storage = multer.diskStorage({
    destination: function(req, file, callback) {
      callback(null, "./upload");
    },
    filename: function(req, file, callback) {
      fileName = file.fieldname + "_" + Date.now() + "_" + file.originalname;
      callback(null, fileName);
    }
  }),
  upload = multer({ storage: storage }),
  fUpload = upload.fields([{ name: "image", maxCount: 1 }]),
  app = express();
var bodyParser = require("body-parser");

app.use(bodyParser.json());
// parse requests of content-type - application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// Configuring the database

/**
 * Upload routing.
 * Update form data. Upload file using multer.
 */
app.listen(3000, () => {
  console.log("Server is listening on port 3000");
});
require("./routes/navigation.route")(app);
require("./routes/product.routes")(app);

app.post("/product/img-to-text", fUpload, function(req, res, next) {
  // Error handling

  console.log(JSON.stringify(fUpload));
  fUpload(req, res, function(err) {
    if (err) {
      res.send({ message: "An error occurred when uploading" });
    } else {
      console.log("req: " + fileName);
      saveImgInDb(fileName, res);
      res.send({ message: "Form data saved!", data: fileName });
    }
  });
});

function saveImgInDb(fileName, res) {
  const imageModel = new ImgModel(
    {
      _id: new mongoose.Types.ObjectId(),
      name: fileName,
      status: "TO_BE_PROCESSED"
    },
    {
      timestamps: true
    }
  );
  imageModel.save().then(() => {
    require("./imageToTextConverter.js").run(fileName, res);
  });
}

app.get("/image-parse/:img", require("./imageToTextConverter.js").getImgData);

// Connecting to the database
mongoose
  .connect(dbConfig.url, {
    useNewUrlParser: true
  })
  .then(() => {
    console.log("Successfully connected to the database");
  })
  .catch(err => {
    console.log("Could not connect to the database. Exiting now...", err);
    process.exit();
  });
