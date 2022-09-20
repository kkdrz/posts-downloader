# JSON Placeholder downloader

It fetches list of posts from `https://jsonplaceholder.typicode.com/posts`
and saves them to separate JSON files.

## Properties

You can customize app with below properties:

| Property             | Definition               | Default                               |
|----------------------|--------------------------|---------------------------------------|
| app.json-api-url     | API host                 | https://jsonplaceholder.typicode.com/ |
| app.output-directory | Where to store the files | ./build/app-output                    |


## How to run

It's a basic Spring Boot app, so any way supported 
by Spring Boot is supported by the app.

Example:
```
java -jar build/libs/posts-downloader-0.0.1-SNAPSHOT.jar --app.output-directory=/my/custom/dir
```

## Shell one-liner ;)

*you probably will need to `apt-get install jq`*

```shell
curl -s https://jsonplaceholder.typicode.com/posts | jq -cr '.[] | .id, .' | awk 'NR%2{file=$0".json" ; next} {print $0 > file ; close(file)}'
```