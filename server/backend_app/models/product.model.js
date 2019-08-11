const mongoose = require("mongoose");

const ProductSchema = mongoose.Schema(
  {
    name: String,
    price: Number,
    category: String
  },
  {
    timestamps: true
  }
);

module.exports = mongoose.model("Product", ProductSchema);
