const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        unique: true
    },
    email: {
        type: String,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    token: { type: String, default: undefined },
    refreshToken: { type: String, default: undefined },
    isActive: { type: Boolean, default: false }
});

module.exports.User = mongoose.model("User", userSchema);