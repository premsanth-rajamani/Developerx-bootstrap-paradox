const mongoose = require("mongoose");

const ProductImageSchema = mongoose.Schema(
  {
    name: String,
    path: String,
    status: String,
    totalSold: Number
  },
  {
    timestamps: true
  }
);

module.exports = mongoose.model("Product_images", ProductImageSchema);
