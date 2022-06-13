require("dotenv").config();
const express = require("express");
const app = express();
const userRouter = require("./api/users/router");

app.use(express.json());
app.get('/', (req, res) => {
    res.send('hello world')
  })
app.use("/api/users", userRouter);
app.listen(process.env.APP_PORT  || 8080, () => {
    console.log("App deployed at port : ", process.env.APP_PORT);
});