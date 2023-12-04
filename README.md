# CSCI24000_Fall23_FinalProject
Final Project for Computing II: A library Management System
# Library Management System (CSCI24000_Fall23_FinalProject)

This project is the Final Project for Computing II, implemented in Java, to create a robust Library Management System. The system has been thoroughly tested on a Unix Operating System. The primary purpose of this system is to efficiently manage clients, administrators, and books through a user-friendly interface.

## Getting Started

To begin using the Library Management System, follow the steps below:

1. Clone the repository to your local machine.
2. Compile and run the project using the provided makefile
   make to compile
   make run to run
   make clean to clean (This cleans both compiled files and serialized data)
## Features

### User Authentication
The system prompts users to register or log in as either a client or an administrator. User authentication is a crucial step for accessing various features of the Library Management System.
### Library Functions
A wide variety of library functions are implemented in this project. 
### Sorting Capability
Users have the ability to sort the library based on any attribute of the book. This functionality is achieved through the implementation of the `BookComparator` interface and related classes.
### Real-time Book Tracking
The system keeps track of books in real-time, calculating due dates and late fees. This feature ensures accurate and up-to-date information on all library items.
### Email Notifications
One of the standout features is the system's capability to send broadcast notifications to all registered clients via email. This is achieved through an SMTP protocol, and a dummy account (`siddharthmurali786@gmail.com` with password `ppxjxbmvjdzxpnna`) has been created for testing purposes.
### Admin Privileges
Only administrators can add and remove books. Initially, no admin is added, and the addition of the first admin can be done without requiring admin privileges. Subsequent admin registrations must be performed by a logged-in admin.
### Clean Code
Extensive commenting has been incorporated into the codebase to enhance readability and understanding. This ensures that future developers can easily navigate and maintain the system.
## Usage
The makefile simplifies the compilation and execution process:
- To compile the project: `make`
- To compile and run the project: `make run`
- To clean up compiled files and serialized data: `make clean`
## Testing
To test the email notification feature, use the provided dummy account (`siddharthmurali786@gmail.com` with password `ppxjxbmvjdzxpnna`). This account has been set up specifically for validating the functionality.
