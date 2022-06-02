require('./config/db');

const app = require('express')();
const port = 3000;

const UserRouter = require('./api/Register and Login');

const bodyParser = require('express').json;
app.use(bodyParser());

app.use('/user', UserRouter)

app.listen(port, () => {
    console.log(`Server running on port ${port}`)
})