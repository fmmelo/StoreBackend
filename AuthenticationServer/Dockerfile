FROM node:22 AS build

WORKDIR /app

ENV NODE_ENV=production

COPY package*.json ./
RUN npm ci --only=production

COPY . .

CMD [ "node", "index.js" ]
