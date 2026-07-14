# XML vs JSON (Data Interchange Formats)

## Overview

XML and JSON are data interchange formats used to exchange information between systems. While both represent structured data, JSON is lighter and more commonly used in modern REST APIs.

---

## XML

XML (Extensible Markup Language) is a markup language that stores data using nested tags.

Characteristics:

- Tree-like structure
- Opening and closing tags
- Self-descriptive
- More verbose

Example:

<user>
    <id>1</id>
    <name>Akhil</name>
</user>

---

## JSON

JSON (JavaScript Object Notation) is a lightweight data interchange format that represents data using key-value pairs.

Characteristics:

- Lightweight
- Easy to read
- Easy to parse
- Widely used in REST APIs

Example:

{
  "id": 1,
  "name": "Akhil"
}

---

## XML vs JSON

| XML | JSON |
|-----|------|
| Markup language | Data interchange format |
| Uses tags | Uses key-value pairs |
| More verbose | More compact |
| Larger payload | Smaller payload |
| Harder to read | Easier to read |
| Less common in REST APIs | Standard choice for REST APIs |

---

## Why JSON is Preferred

- Smaller request and response size
- Faster parsing
- Easier to read
- Better support in web applications
- Native support in JavaScript

---

## Spring Boot

Spring Boot uses Jackson to automatically convert Java objects to JSON and JSON back to Java objects using HttpMessageConverter.