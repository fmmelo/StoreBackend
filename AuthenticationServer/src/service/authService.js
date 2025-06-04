const { UserExistsException, MissingCredentialsException, UserNotFoundException, WrongCredentialsException, MissingTokenException, InvalidTokenException } = require("../exception/userExceptions");
const { User } = require("../schemas/User");
const jwt = require('jsonwebtoken');

const registerUser = async ({ username, password }) => {
    if (!username || !password)
        throw new MissingCredentialsException();

    if (await User.exists({ username: username }))
        throw new UserExistsException(username);

    const user = new User({
        username: username,
        password: password
    });
    await user.save();

    return user;
}

// TODO explore refresh token!
const loginUser = async ({ username, password }) => {
    if (!username || !password)
        throw new MissingCredentialsException();

    const user = await User.findOne({ username: username });
    if (!user)
        throw new UserNotFoundException(username);

    if (user.password !== password)
        throw new WrongCredentialsException();

    const token = jwt.sign({ username: username }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '30m' });
    user.token = token;
    await user.save();
    return user;
}

const logoutUser = async (username) => {
    if (!username)
        throw UserNotFoundException(username);

    const user = await User.findOne({ username: username });
    if (!user)
        throw UserNotFoundException(username);

    user.token = '';
    await user.save();
    return user;
}

const verifyToken = async (token) => {
    if (!token)
        throw new MissingTokenException();

    const user = await User.findOne({ token: token });
    if (!user)
        throw new InvalidTokenException(token);

    const tokenObj = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if (err) throw new InvalidTokenException();
        return user;
    });
    console.log(tokenObj)
    return tokenObj;
}

module.exports = {
    registerUser,
    loginUser,
    logoutUser,
    verifyToken
}