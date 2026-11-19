# AI prompts and reflections (submission deliverable)

These are example prompts and reflection starters. Adjust them so they truthfully describe the prompts you used and what you learned.

## Prompt 1: Encapsulated model

**Prompt:** Create a Java `Student` class with private fields for an ID, name, email, course, programming score, mathematics score and technical skills. Include constructors, getters/setters, validation for scores from 0 to 100, and useful object methods.

**Reflection:** I checked that every field was private, that the skills collection could not be changed from outside the object, and that invalid scores were rejected. I learned that encapsulation lets the class protect its own invariants.

## Prompt 2: Business rules and collections

**Prompt:** Design a Java service for student profiles that validates unique IDs and scores, computes average/performance/stronger subject, finds records by ID/email/course, creates a descending merit list, detects shared technical skills, and finds students with programming below 50 and mathematics at least 75. Use Java collections and MySQL persistence.

**Reflection:** I compared the collection-based operations with their persistent database representation and checked category boundary values. I learned how maps support lookup and lists/sets support sorting and matching.

## Prompt 3: REST API

**Prompt:** Implement Spring Boot REST endpoints to add, list, find, update scores and skills, delete students, and return a merit list backed by MySQL. Validate request data and return appropriate HTTP status codes.

**Reflection:** I reviewed the request/response contract and exercised each endpoint with sample JSON. I learned how a controller, service, repository and entity divide responsibilities in a REST application.
