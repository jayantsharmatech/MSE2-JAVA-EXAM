# Question 2: Business logic and MySQL

Implements all eight requirements with a `LinkedHashMap` collection for efficient ID lookup, stream-based searching/merit sorting, skill grouping and candidate filtering, plus MySQL persistence through prepared JDBC statements.

1. Create the schema with `mysql -u root -p < sql/schema.sql`.
2. Export `DB_PASSWORD` (and optionally `DB_USERNAME`, `DB_URL`).
3. Run `mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java` and enter `help` for commands.
4. Try `mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.args="--demo"` to see collection operations without MySQL.

The database enforces primary-key IDs, unique emails and score range checks; the Java model validates inputs too.
