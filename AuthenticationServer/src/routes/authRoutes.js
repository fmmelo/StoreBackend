const express = require('express');
const authController = require('../controller/authController');
const router = express.Router();

router.post('/register', authController.register);

router.get('/activate/:token', authController.activateUser);

router.post('/login', authController.login);

router.post('/logout', authController.logout);

router.post('/token_verify', authController.verifyToken);

router.post('/user_exists', authController.userExists);

module.exports = router;