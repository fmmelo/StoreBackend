const {
    UserNotFoundException,
    WrongCredentialsException,
    MissingCredentialsException,
    UserExistsException,
    MissingTokenException,
    InvalidTokenException,
    EmptyUsernameException,
    UserNotActiveException,
    UserIsAlreadyActiveException
} = require("../exception/userExceptions");
const service = require("../service/authService")
const { StatusCodes } = require('http-status-codes')

const register = async (req, res) => {
    try {
        const user = await service.registerUser(req.body.user);
        res.status(StatusCodes.CREATED).json(user);
    } catch (e) {
        if (e instanceof MissingCredentialsException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof UserExistsException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else
            res.status(StatusCodes.INTERNAL_SERVER_ERROR).send(e.message);
    }
}

const activateUser = async (req, res) => {
    console.log("called")
    try {
        const token = req.params.token
        await service.activateUserAccount(token)
        res.status(StatusCodes.ACCEPTED).send("Account created");
    } catch (e) {
        if (e instanceof InvalidTokenException)
            res.status(StatusCodes.UNAUTHORIZED).send(e.message)
        else if (e instanceof UserNotFoundException)
            res.status(StatusCodes.NOT_FOUND).send(e.message)
        else if (e instanceof UserIsAlreadyActiveException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message)
    }
}

const login = async (req, res) => {
    try {
        const user = await service.loginUser(req.body.user);
        res.status(StatusCodes.CREATED).json(user);
    } catch (e) {
        if (e instanceof MissingCredentialsException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof UserNotFoundException)
            res.status(StatusCodes.NOT_FOUND).send(e.message);
        else if (e instanceof UserNotActiveException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof WrongCredentialsException)
            res.status(StatusCodes.UNAUTHORIZED).send(e.message);
    }
}

const logout = async (req, res) => {
    try {
        await service.logoutUser(req.body.username);
        res.status(StatusCodes.OK).send();
    } catch (e) {
        if (e instanceof UserNotFoundException)
            res.status(StatusCodes.NOT_FOUND).send(e.message);
        else
            res.status(StatusCodes.INTERNAL_SERVER_ERROR).send(e.message);
    }
}

const verifyToken = async (req, res) => {
    try {
        const authHeaders = req.headers['authorization']
        const token = authHeaders.split(' ')[1];
        const validation = await service.verifyToken(token);
        res.status(StatusCodes.OK).json(validation);
    } catch (e) {
        if (e instanceof MissingTokenException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof UserNotActiveException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof InvalidTokenException)
            res.status(StatusCodes.UNAUTHORIZED).send(e.message);
    }
}

const userExists = async (req, res) => {
    try {
        const username = req.body.username
        const exists = await service.userExists(username)
        if (exists)
            res.status(StatusCodes.CONFLICT).send()
        else
            res.status(StatusCodes.ACCEPTED).send()
    } catch (e) {
        if (e instanceof EmptyUsernameException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message)
    }
}

module.exports = {
    register,
    activateUser,
    login,
    logout,
    verifyToken,
    userExists
}