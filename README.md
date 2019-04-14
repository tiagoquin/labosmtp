# Labo SMTP

> HEIG-VD
>
> Gabrielli Alexandre, Póvoa Tiago

## Description

Ce laboratoire permet d'envoyer des mails fictifs à un serveur MockMock. On peut configurer son comportement avec les ressources:

* config.properties
* jokes.utf8
* victimes.utf8

## Guide d'installation

Au chemin suivant: 

> /install/run.sh

un petit exécutable `run.sh` est fournit. Il va créer build l'image docker avec le MockMock.jar à l'intérieur et mapper les ports 2525 et 8080 (et les exposer).

