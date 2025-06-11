const express = require('express');
const authController = require('../controller/authController');
const { sendEmail } = require('../utils/mail');
const router = express.Router();

router.post('/register', authController.register);

router.post('/login', authController.login);

router.post('/logout', authController.logout);

router.post('/token_verify', authController.verifyToken);

router.post('/user_exists', authController.userExists);

router.post('/testmail', async (req, res) => {
    const email = req.body.email
    await sendEmail([email])
    res.status(200).send('Email sent!')
})

module.exports = router;