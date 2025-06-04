const app = require('./src/app');
require('dotenv').config

app.listen(process.env.PORT, '0.0.0.0', () => {
    console.log(`Server listening on port: ${process.env.PORT}`);
});
