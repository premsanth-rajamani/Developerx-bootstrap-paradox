module.exports = app => {
  const navigation = require("../controllers/navigation.controller.js");

  // redirect to homepage
  app.get("/", navigation.home);
};
