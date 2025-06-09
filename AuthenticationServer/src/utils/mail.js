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

const sendEmail = async (recipients) => {
    console.log(recipients)
    const info = await transporter.sendMail({
        from: '"Web Store" <tod31@ethereal.email>',
        replyTo: 'no-reply',
        to: recipients.toString(),
        subject: "Account confirmation",
        text: "Confirm Account",
        html: `
            <html>
                <body>
                    <h3>Please confirm account</h3><br/>
                    <span>Click <a href='https://www.google.com'>here</a> to confirm your account</span><br/>
                    <span>Wasn't you?, ignore this email</span><br/>
                </body>
            </html>
        `,
    });

    console.log("Message sent:", info.messageId);
};

module.exports = {
    sendEmail
}