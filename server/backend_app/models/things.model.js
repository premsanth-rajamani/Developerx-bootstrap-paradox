const mongoose = require("mongoose");

const ThingSchema = mongoose.Schema(
  {
    img: String,
    productData: [
      { name: String, price: Number, total: Number, quantity: Number }
    ],
    merchantData: [
      { pinCode: String, address: String, name: String, tinNo: String }
    ]
  },
  {
    timestamps: true
  }
);

module.exports = mongoose.model("Things", ThingSchema);
