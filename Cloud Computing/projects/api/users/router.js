const router = require("express").Router();
const {
  createUser,
  login,
  getAccountByUserId,
  getUsers,
  updateUsers,
} = require("./controller");

router.get("/", getUsers);
router.post("/", createUser);
router.get("/:id", getAccountByUserId);
router.post("/login", login);
router.patch("/", updateUsers);

module.exports = router;
