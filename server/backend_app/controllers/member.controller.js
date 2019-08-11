const Member = require("../models/member.model.js");

// Create and Save a new Note
exports.create = (req, res) => {
  // Validate request
  if (!req.body.email) {
    return res.status(400).send({
      message: "member email can not be empty"
    });
  }

  const member = new Member({
    email: req.body.email,
    name: req.body.name
  });

  member
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

// Find a single member with email id
exports.findOne = (req, res) => {
  const emailId = req.params.emailId;
  Member.find({ email: emailId })
    .then(member => {
      if (member && member.length > 0) {
        res.send(member);
      } else {
        return res.status(404).send({ message: "Email not found " });
      }
    })
    .catch(err => {
      return res.status(500).send({
        message: "Error retrieving member with email " + req.params.email
      });
    });
};
