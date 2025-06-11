const { UserExistsException, MissingCredentialsException, UserNotFoundException, WrongCredentialsException, MissingTokenException, InvalidTokenException, UserNotActiveException, UserIsAlreadyActiveException } = require("../exception/userExceptions");
const { User } = require("../schemas/User");
const jwt = require('jsonwebtoken');
const { sendAccountConfirmationEmail } = require("../utils/mail");

const registerUser = async ({ username, email, password }) => {
    if (!username || !email || !password)
        throw new MissingCredentialsException();

    if (await User.exists({ username: username }))
        throw new UserExistsException(username);

    const token = jwt.sign({ username, email }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '30m' })

    const user = new User({
        username: username,
        email: email,
        password: password
    });
    await user.save();
    
    sendAccountConfirmationEmail([email], token)
    return token;
}

const activateUser = async (token) => {
    const tokenObj = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if (err) throw new InvalidTokenException();
        return user;
    })
    const user = await User.findOne({username: tokenObj.username})
    if(!user) 
        throw new UserNotFoundException(tokenObj.username)

    if(user.isActive)
        throw new UserIsAlreadyActiveException(tokenObj.username)

    user.isActive = true;
    user.save();

    return user;
}

// TODO explore refresh token!
const loginUser = async ({ username, password }) => {
    if (!username || !password)
        throw new MissingCredentialsException();

    const user = await User.findOne({ username: username });
    if (!user)
        throw new UserNotFoundException(username);

    if(!user.isActive)
        throw new UserNotActiveException();

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

    if(!user.isActive)
        throw new UserNotActiveException();

    const tokenObj = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if (err) throw new InvalidTokenException();
        return user;
    });
    console.log(tokenObj)
    return tokenObj;
}

const userExists = async (username) => {
    if (!username)
        throw new EmptyUsernameException();

    const user = await User.findOne({ username: username });
    if (user)
        return true;

    return false
}

module.exports = {
    registerUser,
    loginUser,
    logoutUser,
    verifyToken,
    userExists,
    activateUser
}