class MissingCredentialsException extends Error {
    constructor() {
        super('Missing user credentials.')
        this.name = this.constructor.name
    }
}

class UserExistsException extends Error {
    constructor(username) {
        super(`The user with ${username} already exists.`)
        this.name = this.constructor.name
    }
}

class UserNotFoundException extends Error {
    constructor(username) {
        super(`The user with ${username} does not exist.`)
        this.name = this.constructor.name
    }
}

class WrongCredentialsException extends Error {
    constructor() {
        super('The given credentials do not match')
        this.name = this.constructor.name
    }
}

class MissingTokenException extends Error {
    constructor() {
        super('The given credentials do not match')
        this.name = this.constructor.name
    }
}

class InvalidTokenException extends Error {
    constructor() {
        super('The token is not valid')
        this.name = this.constructor.name
    }
}

module.exports = {
    MissingCredentialsException,
    UserExistsException,
    UserNotFoundException,
    WrongCredentialsException,
    MissingTokenException,
    InvalidTokenException
}