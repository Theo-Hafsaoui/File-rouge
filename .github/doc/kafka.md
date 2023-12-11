![Pasted image 20240121203311](https://github.com/Theo-Hafsaoui/i311-tp-fil-rouge-Theo-Hafsaoui/assets/63098041/b6be9da8-c253-45ee-b1ed-e8f38282d772)

Les logs sont depuis longtemps un élément clé de l'intégrité des systèmes, un preuve de cette ancienneté peut se trouver dans IBM's System R, l'une des premières bases de données où des logs étaient mis en place pour permettre la reprise après une panne. Aujourd'hui, ces logs sont essentiels pour gérer le volume de données et la complexité des environnements distribués.

C'est dans ce contexte que Kafka se distingue en tant message broker. Il offre une solution robuste pour la gestion efficace des flux de données en temps réel, l'intégration de données et le traitement d'événements. Kafka tire parti de son architecture distribuée et de sa scalabilité pour répondre aux besoins croissants de la gestion des logs dans des environnements informatiques complexes.

# Premier contact avec Kafka
Dans un premier temps, afin de saisir pleinement l'utilité de Kafka, j'ai entrepris la lecture du livre "I Heart Logs" rédigé par Jay Kreps, qui explore la philosophie et la genèse de Kafka. Ce livre offre une introduction captivante à la problématique à laquelle un gestionnaire de messages tente de répondre, en se penchant notamment sur le problème du théorème CAP, déjà abordé dans le cadre de mon étude sur [Zanzibar](https://github.com/Theo-Hafsaoui/Master/blob/main/S2/TER/T_E_R_Zanzibar.pdf), le système d'autorisation de Google, qui constitue également le sujet de mon T.E.R.

L'auteur va même jusqu'à affirmer que Kafka représente une solution à ce problème, tout particulièrement dans le domaine de l'intégration des données qu'il désigne comme des pipelines ETL (Extract, Transform, and Load). Cette affirmation souligne l'importance de Kafka dans la résolution de défis majeurs liés à la gestion et au traitement efficace des flux de données, renforçant ainsi son rôle crucial dans les architectures modernes de traitement de données.

```
“Each working data pipeline is designed like a log; each
broken data pipeline is broken in its own way.” — Kreps paraphrasant le principe d'Anna Karénine
```
![Pasted image 20231226132018](https://github.com/Theo-Hafsaoui/i311-tp-fil-rouge-Theo-Hafsaoui/assets/63098041/3c7399a6-14e2-4e89-8ec3-ee75ee9b2ec1)


Ayant apprecie cette lecture, j'ai vérifié si je pouvais partir de ce contexte avec [ChatGPT](https://chat.openai.com/share/6e2e303f-e0ab-4ad3-85ec-d77b0461162c). Manifestement oui et j'ai donc tenté de voir de ces [propositions](https://chat.openai.com/share/016857af-8a04-4971-b2b2-f095243d7124)
## Première approche :
Une première approche consisterait à permettre au client d'effectuer des appels directs à Kafka. Cependant, cette méthode présente un inconvénient majeur : les appels HTTP risquent de compromettre les performances du système. Cette observation découle de discussions que j'ai lue sur un post [Stack Overflow](https://stackoverflow.com/questions/74240455/sending-http-request-with-kafka-stream), où des utilisateurs ont soulevé des préoccupations quant à l'utilisation d'appels HTTP avec Kafka Stream.

![oldKafkaArchitecture](https://github.com/Theo-Hafsaoui/i311-tp-fil-rouge-Theo-Hafsaoui/assets/63098041/6163c9d3-4ee5-4693-a16c-0a4248b90cd5)


Suite à ces inquiétudes, j'ai approfondi la question en cherchant des clarifications à travers une discussion sur [ChatGPT](https://chat.openai.com/share/6418453c-de94-4860-9b7b-5ac162efde4b). Il est apparu clairement que l'utilisation d'appels HTTP directement avec Kafka pouvait entraîner une diminution significative des performances du système. Par conséquent, une réflexion plus approfondie et des alternatives peuvent être nécessaires pour garantir une intégration efficace sans compromettre les performances globales.
## Deuxième approche :
J'ai trouvé l'article de Red Hat particulièrement instructif [ici](https://developers.redhat.com/articles/2022/04/05/developers-guide-using-kafka-java-part-1#what_is_kafka/). À partir de son contenu, je commence à entrevoir l'architecture présentée ci-dessus, bien que certaines nuances demeurent incertaines.



![kafkaArchitecture](https://github.com/Theo-Hafsaoui/i311-tp-fil-rouge-Theo-Hafsaoui/assets/63098041/0641c0be-3e16-45c3-87ba-f1ff79c4c4d8)

Cependant, en pratique, cette architecture semble moins intéressante, car elle ne garantit pas la robustesse de la première. Néanmoins, elle n'est pas dépourvue d'utilité. Imaginons un scénario où Kafka est déployé sur un serveur JEE, ce qui peut sembler peu attrayant. Toutefois, si ce serveur est divisé en plusieurs services, éventuellement connectés à différentes bases de données, cela crée une solution maintenable et évolutive. Ainsi, on obtient une _source de vérité_ permettant d'assurer la fraîcheur des informations et de faciliter une reprise après une panne de manière très efficace.

Au-delà de cela, je vais reformuler mon approche [ici](https://chat.openai.com/share/da0ba718-ab41-4bc3-98fe-43a5ba763e46) pour explorer d'autres perspectives.

J'expérimente également la possibilité d'obtenir un exemple de code concret en interagissant avec [ChatGPT](https://chat.openai.com/share/03f86adc-81e5-4916-ba17-a6b16ba0cad6).

### Docker
Pour cette section, ChatGPT a été abandonné au profit de Bard. En effet, l'expérience du TP avec ChatGPT 3.5 s'est révélée très frustrante avec Docker, et Bard offre des réponses bien meilleures dans ce contexte. Il est toutefois important de noter que Bard est limité dans la génération de réponses techniques intéressantes, d'où son utilisation dans les questions précédentes.

La décision a été prise de se tourner vers une image assez ancienne. En effet, la configuration de Zookeeper était hautement complexe, et cette image propose un lancement beaucoup plus simple.

```Dockerfile
#[Backend] Kafka message-broker
broker:
  image: quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64
  ports:
    - "9092:9092"
  environment:
    - LOG_DIR=/tmp/logs
  command: >
    /bin/sh -c '
    export CLUSTER_ID=$$(bin/kafka-storage.sh random-uuid) &&
    bin/kafka-storage.sh format -t $$CLUSTER_ID -c config/kraft/server.properties &&
    bin/kafka-server-start.sh config/kraft/server.properties'
```

Dans ce Dockerfile, nous configurons une image de Kafka à l'aide de Strimzi. Cette image, basée sur la version 2.8.1, simplifie le lancement en utilisant une configuration spécifique pour le stockage Kafka avec la gestion du format et le démarrage du serveur. Le port 9092 est exposé pour permettre les connexions externes au broker Kafka.

### Intégration
Dans le TP, les singletons *Consumer* et *Producer* ont été ajoutés. Ce sont eux qui se connectent à Kafka et intègrent les changements.

```java
ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
records.forEach(record -> {
    switch (record.key()) {
        case "CLIENT-PUT":
            clientDAO.save(ImplClient.fromJson(record.value()));
            break;
        case "CLIENT-DELETE":
            // Opération de suppression du client
            break;
        default:
            log.error("Err: Clé inconnue - " + record.key());
    }
});
```

### Intérêt dans le TP

L'intégration de Kafka dans mon TP présente un intérêt majeur en permettant d'établir une source de vérité. En effet, l'un des principaux arguments en faveur de JEE réside dans sa facilité à être réparti sur plusieurs instances de serveur. Cependant, la gestion de cette architecture distribuée peut être complexe. Kafka, avec son système de producteur et de consommateur, offre une solution élégante à ce défi.

**Conclusion**

Kafka est un outil puissant qui peut être utilisé pour une variété de cas d'utilisation. Il est important de comprendre les principes de base de Kafka avant de l'utiliser dans votre tp.

J'ai appris beaucoup de choses sur Kafka au cours de mes recherches. Je suis maintenant en mesure de l'utiliser pour mettre en œuvre une source de vérité dans mon tp.

---
Réécriture par [GPT](https://chat.openai.com/share/b6cd0870-2bef-4a7f-b083-b57e39495982)
