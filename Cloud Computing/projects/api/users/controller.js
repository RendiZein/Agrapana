const { 
    create , 
    getAccountByUserEmail ,
    getAccountByUserId,
    getUsers,
    updateUser,
} = require("./service")

  const { hashSync, genSaltSync, compareSync } = require("bcrypt");
  const { sign } = require("jsonwebtoken");
  
  module.exports = {
    createUser: (req, res) => {
      const body = req.body;
      const salt = genSaltSync(10);
      body.password = hashSync(body.password, salt);
      create(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            success: 0,
            message: err.message 
          });
        }
        return res.status(200).json({
          success: 1,
          data: results
        });
      });
    },
    login: (req, res) => {
        const body = req.body;
        getAccountByUserEmail(body.email, (err, results) => {
          if (err) {
            console.log(err);
          }
          if (!results) {
            return res.json({
              success: 0,
              data: "Your email or password  invalid. Please try agail!"
            });
          }
          const result = compareSync(body.password, results.password);
         console.log(body.password,results.password)
          if (result) {
            results.password = undefined;
            const jsontoken = sign({ result: results }, "qwe1234", {
              expiresIn: "1h"
            });
            return res.json({
              success: 1,
              message: "Login successfully. Welcome to Agraprana App",
              token: jsontoken
            });
          } else {
            return res.json({
              success: 0,
              data: "Your email or password  invalid. Please try agail!"
            });
          }
        });
      },
      getAccountByUserId: (req, res) => {
        const id = req.params.id;
        getAccountByUserId(id, (err, results) => {
          if (err) {
            console.log(err);
            return;
          }
          if (!results) {
            return res.json({
              success: 0,
              message: "Record not Found"
            });
          }
          results.password = undefined;
          return res.json({
            success: 1,
            data: results
          });
        });
      },
      getUsers: (req, res) => {
        getUsers((err, results) => {
          if (err) {
            console.log(err);
            return;
          }
          return res.json({
            success: 1,
            data: results
          });
        });
      },
      updateUsers: (req, res) => {
        const body = req.body;
        const salt = genSaltSync(10);
        body.password = hashSync(body.password, salt);
        updateUser(body, (err, results) => {
          if (err) {
            console.log(err);
            return;
          }
          return res.json({
            success: 1,
            message: "updated successfully"
          });
        });
      }
    };