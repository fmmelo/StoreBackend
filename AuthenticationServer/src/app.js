const express = require('express');
const authRoutes = require('./routes/authRoutes');
const mongoose = require('mongoose')
const cors = require('cors');

mongoose.connect(process.env.MONGO_URL, {
    dbName: 'MyProjectDatabase'
})
    .then(() => console.log(`Connected to MongoDB`))
    .catch(() => console.log(`Failed to connect to MongoDB`))

const app = express();

app.use(express.json());

app.use(cors({ origin: '*' }))

/* --------------------------- ROUTES --------------------------- */

app.use('/auth', authRoutes)

module.exports = app;