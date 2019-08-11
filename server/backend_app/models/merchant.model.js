const mongoose = require("mongoose");

const Merchant = mongoose.Schema(
  {
    name: String,
    address: String,
    tin: Number,
    pincode: Number,
    phone: Number
  },
  {
    timestamps: true
  }
);

module.exports = mongoose.model("Merchant", Merchant);
