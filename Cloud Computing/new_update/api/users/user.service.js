const pool = require("../../config/database");

module.exports = {
  create: (data, callBack) => {
    pool.query(
      `insert into registration(name, email, password)
      values(?,?,?)`,
      [
          data.name,
          data.email,
          data.password
      ],
      (error, results, fields) => {
        if (error) {
          return callBack(error);
        }
        return callBack(null, results);
        }
    );
},
getUserByUserEmail: (email, callBack) => {
    pool.query(
      `select * from registration where email = ?`,
      [email],
      (error, results, fields) => {
        if (error) {
          callBack(error);
        }
        return callBack(null, results[0]);
      }
    );
  },
  getUserByUserId: (id, callBack) => {
    pool.query(
      `select id,name,email from registration where id = ?`,
      [id],
      (error, results, fields) => {
        if (error) {
        callBack(error);
        }
        return callBack(null, results[0]);
      }
    );
  },
  getUsers: callBack => {
    pool.query(
      `select id,name,email from registration`,
      [],
      (error, results, fields) => {
        if (error) {
          callBack(error);
        }
        return callBack(null, results);
      }
    );
  },
  updateUser: (data, callBack) => {
    pool.query(
      `update registration set name=?, email=?, password=? where id = ?`,
      [
        data.name,
        data.email,
        data.password,
        data.id
      ],
      (error, results, fields) => {
        if (error) {
          callBack(error);
        }
        return callBack(null, results[0]);
      }
    );
  },
  deleteUser: (data, callBack) => {
    pool.query(
      `delete from registration where id = ?`,
      [data.id],
      (error, results, fields) => {
        if (error) {
          callBack(error);
        }
        return callBack(null, results[0]);
      }
    );
  }
};