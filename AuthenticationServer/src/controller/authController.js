const { UserNotFoundException, WrongCredentialsException, MissingCredentialsException, UserExistsException, MissingTokenException, InvalidTokenException } = require("../exception/userExceptions");
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
    }
}

const login = async (req, res) => {
    try {
        const user = await service.loginUser(req.body.user);
        res.status(StatusCodes.CREATED).json(user.token);
    } catch (e) {
        if (e instanceof MissingCredentialsException)
            res.status(StatusCodes.BAD_REQUEST).send(e.message);
        else if (e instanceof UserNotFoundException)
            res.status(StatusCodes.NOT_FOUND).send(e.message);
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
        else if (e instanceof InvalidTokenException)
            res.status(StatusCodes.UNAUTHORIZED).send(e.message);
    }
}

module.exports = {
    register,
    login,
    logout,
    verifyToken
}