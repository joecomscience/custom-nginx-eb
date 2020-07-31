# Jenkins Configuration

## Purpose
bla bla bla

## Getting started

- rename file `.env.example` to `.env`.
- assign value in `.env` file.
- run command `Make run`.

## Parameter Description

| Name                   | Value             | Description                        |
| :--------------------- | :---------------- | :--------------------------------- |
| ADMIN_USERNAME         |                   | Get it from keyweb `jenkins` entry |

## Get Jenkins plugin list

- Go to Jenkins script url `http://localhost:8080/script` then run below script for get plugins list.

```groovy
  Jenkins.instance.pluginManager.plugins.each{
    plugin -> 
      println ("${plugin.getShortName()}:${plugin.getVersion()}")
  }
```

#### Development
password: e411907ab6cc40b493e4dc1bf8a42985

- [Setup IDE](https://www.bonusbits.com/wiki/HowTo:Setup_Project_in_IntellJ_IDEA_for_Working_with_Jenkins_Plugins_Groovy_Init_Scripts)
