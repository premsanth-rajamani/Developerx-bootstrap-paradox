const mongoose = require("mongoose");

const MemberSchema = mongoose.Schema(
  {
    email: String,
    name: String,
    title: String
  },
  {
    timestamps: true
  }
);

module.exports = mongoose.model("Member", MemberSchema);
