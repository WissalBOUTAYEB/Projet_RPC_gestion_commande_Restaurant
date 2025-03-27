Restaurant Order Management System - RPC Application
Project Overview
This project is a distributed restaurant order management system developed using Java RMI (Remote Method Invocation) for client-server communication and Java Swing for the user interface. It was created as part of the Distributed Applications module at Euro Mediterranean University of Fes.

Key Features
Client Management: Customers can connect by entering their name

Menu Consultation: View available dishes with descriptions and prices

Order Placement: Select dishes from the menu and place orders

Bill Calculation: Automatic calculation of the total bill

Real-time Updates: Server interface displays received orders in real-time

System Architecture
The application follows a client-server model using Java RMI:

Client Side:

Java Swing GUI

Communicates with server via RMI calls

Server Side:

Provides RMI services (menu, orders, billing)

Manages dish data and customer orders

RMI Interface: Defines remote methods for restaurant services

Technologies Used
Java: Primary programming language

Java RMI: For remote method invocation between client and server

Java Swing: For building the graphical user interface

JFrame (main window)

JPanel (component organization)

JTextArea (multi-line text display)

JButton (action buttons)

Installation & Setup
Prerequisites:

Java Development Kit (JDK) installed

RMI registry configured

Server Setup:

Start the RMI registry

Launch the server application

Ensure correct IP and port configuration

Client Setup:

Launch client application

Connect to server using proper RMI URL

Challenges & Solutions
RMI Connection Issues:

Problem: Client couldn't locate remote service (NamingException)

Solution: Corrected RMI registry configuration and URL format

Synchronization Problems:

Problem: Data inconsistencies with multiple concurrent clients

Solution: Implemented synchronized blocks and thread-safe collections

Project Status
Completed as a semester-end project. Core functionalities are fully implemented and tested.

Future Enhancements
Database integration for persistent storage

Enhanced security features

Mobile client application

Advanced reporting and analytics
