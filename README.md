# search-engine

## Background

Capstone project for the course Introductory Programming at the IT University of Copenhagen.

The search engine operates over a databse of documents parsed from English Wikipedia articles. The documents are stored in an [inverted index](https://en.wikipedia.org/wiki/Inverted_index) data structure, which allows for efficient querying of database. The results of the queries can be ranked using different ranking methods, such as the [tf-idf](https://en.wikipedia.org/wiki/Tf%E2%80%93idf) formula.

## Setup

To run the project:

- Clone the repository using `git clone https://github.com/simontitk/search-engine`.

- Navigate to the directory of the project and run `App.java` in the folder `src/main/java/searchengine/` or use the commend `gradle run`.

- Navigate to `http://localhost:8080` in your browser to see the frontend client.
