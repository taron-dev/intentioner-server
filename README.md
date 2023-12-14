# Intentioner server

Serves as REST API for intentions.
Takes care over intentions of prayers. Also helps manage and plan notifications for them.

## Database

As persistence layer is for now used **MySql** from [PlanetScale](https://planetscale.com/).
> Note: The used free layer has some restrictions which affects also the way how it's implemented. We should consider improvements also in integration parts of code once DB changed.

## Deployment

Currently, the application is running as docker image via service [Render.com](https://render.com/).
Application itself is accessible on https://intentioner-server-0-0-1.onrender.com.
Recent version(s) are accessible on https://hub.docker.com/r/tarondev/repository/tags.