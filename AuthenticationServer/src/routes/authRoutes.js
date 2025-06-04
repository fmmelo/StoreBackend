const express = require('express');
const authController = require('../controller/authController');
const router = express.Router();

router.post('/register', authController.register);

router.post('/login', authController.login);

router.post('/logout', authController.logout);

router.post('/token_verify', authController.verifyToken);

module.exports = router;