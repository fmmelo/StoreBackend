const nodemailer = require("nodemailer");
require('dotenv')

const transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    port: 587,
    auth: {
        user: process.env.MAILER_USER,
        pass: process.env.MAILER_PWD
    }
});

const sendAccountConfirmationEmail = async (recipients, token) => {
    console.log(recipients)
    const info = await transporter.sendMail({
        from: '"Web Store"',
        replyTo: 'no-reply',
        to: recipients.toString(),
        subject: "Account confirmation",
        text: "Confirm Account",
        html: `
            <html>
                <body>
                    <h3>Please confirm account</h3><br/>
                    <span>Click <a href='http://localhost:5050/auth/activate/${token}'>here</a> to confirm your account</span><br/>
                    <span>Wasn't you? Ignore this email</span><br/>
                </body>
            </html>
        `,
    });

    console.log("Message sent:", info.messageId);
};

module.exports = {
    sendAccountConfirmationEmail
}