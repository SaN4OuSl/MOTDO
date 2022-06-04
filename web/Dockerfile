FROM node:alpine AS node
WORKDIR /app
COPY . .
RUN npm ci && npm run build

FROM nginx:alpine
COPY ngnix.conf /etc/nginx/nginx.conf
COPY --from=node /app/dist/client /usr/share/nginx/html
EXPOSE 4200

