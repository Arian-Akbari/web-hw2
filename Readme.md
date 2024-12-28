run with

```
./mvnw spring-boot:run
```

For development with auto-reload:

```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

# Form Builder API Documentation

## Overview

The Form Builder is a RESTful API service built with Spring Boot that enables dynamic form creation and management. It allows users to create, manage, and publish customizable forms with various field types and submission endpoints.

## Technical Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL/H2 Database
- Maven
- Lombok

## Data Models

### Form Entity

The core entity representing a form with the following attributes:

```java
public class Form {
    private Long id;              // Unique identifier
    private String name;          // Form name
    private boolean published;    // Publication status
    private List<Field> fields;   // Form fields
    private String submitMethod;  // HTTP method for form submission
    private String submitUrl;     // Endpoint for form submission
}
```

### Field Entity

Represents individual form fields with these properties:

```java
public class Field {
    private Long id;              // Unique identifier
    private String fieldName;     // Field identifier
    private String label;         // Display label
    private String type;          // Field type (text, number, boolean, date)
    private String defaultValue;  // Default field value
    private Form form;            // Parent form reference
}
```

## API Endpoints

### Form Management

#### Get All Forms

```
GET /forms
```

Returns a list of all forms in the system.

Response: `200 OK`

```json
[
  {
    "id": 1,
    "name": "Sample Form",
    "published": false,
    "fields": [],
    "submitMethod": "POST",
    "submitUrl": "/submit"
  }
]
```

#### Create Form

```
POST /forms
```

Creates a new form with specified fields.

Request Body:

```json
{
  "name": "New Form",
  "submitMethod": "POST",
  "submitUrl": "/api/submit",
  "fields": [
    {
      "fieldName": "fullName",
      "label": "Full Name",
      "type": "text",
      "defaultValue": ""
    }
  ]
}
```

Response: `200 OK`

#### Get Form by ID

```
GET /forms/{id}
```

Retrieves a specific form by its ID.

Response: `200 OK` or `404 Not Found`

#### Update Form

```
PUT /forms/{id}
```

Updates an existing form's properties.

Request Body:

```json
{
  "name": "Updated Form Name",
  "submitMethod": "POST",
  "submitUrl": "/api/submit-updated"
}
```

Response: `200 OK` or `404 Not Found`

#### Delete Form

```
DELETE /forms/{id}
```

Removes a form from the system.

Response: `204 No Content`

### Field Management

#### Get Form Fields

```
GET /forms/{id}/fields
```

Retrieves all fields for a specific form.

Response: `200 OK`

```json
[
  {
    "id": 1,
    "fieldName": "email",
    "label": "Email Address",
    "type": "text",
    "defaultValue": ""
  }
]
```

#### Update Form Fields

```
PUT /forms/{id}/fields
```

Updates all fields for a specific form.

Request Body:

```json
[
  {
    "fieldName": "email",
    "label": "Email Address",
    "type": "text",
    "defaultValue": ""
  }
]
```

Response: `200 OK`

### Publication Management

#### Toggle Publication Status

```
POST /forms/{id}/publish
```

Toggles the publication status of a form.

Response: `200 OK`

#### Get Published Forms

```
GET /forms/published
```

Retrieves all published forms.

Response: `200 OK`

## Field Types

The system supports the following field types:

- `text`: Text input field
- `number`: Numeric input field
- `boolean`: Checkbox field
- `date`: Date input field

## Error Handling

The API implements comprehensive error handling with appropriate HTTP status codes:

- `400 Bad Request`: Invalid input data
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server-side errors

Error Response Format:

```json
{
  "status": 404,
  "message": "Form not found",
  "timestamp": "2024-12-28T10:15:30",
  "errors": {
    "fieldName": "error description"
  }
}
```

## Database Schema

The database uses two main tables with a one-to-many relationship:

```sql
CREATE TABLE form (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    published BOOLEAN DEFAULT FALSE,
    submit_method VARCHAR(10),
    submit_url VARCHAR(255)
);

CREATE TABLE field (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    field_name VARCHAR(255) NOT NULL,
    label VARCHAR(255),
    type VARCHAR(50),
    default_value VARCHAR(255),
    form_id BIGINT,
    FOREIGN KEY (form_id) REFERENCES form(id)
);
```

## Security Considerations

- Input validation is implemented for all endpoints
- Field types are validated against allowed values
- Form names must be unique
- Field names must be unique within a form

## Transaction Management

The service implements transactional operations for:

- Form creation with fields
- Form updates
- Field updates
- Publication status changes

This ensures data consistency across related operations.

## Future Enhancements

Potential improvements for future versions:

- Form validation rules
- Form submission handling
- User authentication and authorization
- Form templates
- Field dependencies
- Custom field types
- Form versioning

## Testing

To test the API endpoints, use the provided curl commands or import the Postman collection available in the project repository.

This documentation provides a comprehensive overview of the Form Builder API's capabilities and implementation details. For specific setup instructions or deployment guidelines, please refer to the project's setup documentation.
