Capstone II Wearable Pressure and Moisture sensors

USAGE INTRUCTIONS:

Running the data simulation

We have a data simulation file which is used for development and testing purposes. It utilizes TCP connection to simulate a physical device. The data simulation file can be configured to run on your laptop by making sure the port is binding at "localhost" and to an unused port number. Within the DataHandler.kt file there is a corresponding configuration to set up a connection on address "localhost" and the port number selected. Our goal was to establish a Bluetooth connection with hardware sensors which would send data to our application about pressure and moisture.
In order to recieve data from the data simulation, you must run the server file (datasim.py) first. This file is written in python and can be ran using your preferred python IDE. Next, the Android Studio application should be ran within the Android Studio IDE. The application acts as the client for the TCP connections. You will notice when the datasim.py file is started it prints a message to the terminal letting the user know is it watiting for a connection to the application. Once the application is started and a user logs in it will begin receiving data through the TCP connection established between server and client. The data simulation will loop through a series of test cases and print them in the terminal where it is ran. 

Sign up/Log in process

Create a new user login using an email of your choice by clicking the sign up button near the bottom of the log in screen. The username must be a valid email address and the password must not be empty. Enter the prompted fields and select whether you will be utilizing this application as a patient monitoring yourself or a caregiver monitoring several patients. Once a user is signed up they will be taken back to the log in screen to enter their credentials. All user log in credentials are stored within Firebase Authentication and user type is stored in Firebase Firestore. If there are any errors with the log in process, a toast message with pop up giving insight into what went wrong.

Patient homepage

Users who select "Patient" when creating an account will be directed to a patient home screen. Within the patient homescreen there will be progress bars representing current pressure levels and a bar chart representing high pressure levels over a period of time. Incoming data will populate both the progress bars and the bar chart. There are three of each, representing the right hip, left hip and tailbone pressure sensors. Alerts will pop up and sound an alarm as the data simulation loops through test cases. 

Caregiver homepage

Users who select "Caregiver" when creating an account will be directed to a caregiver home screen. Within the caregiver homescreen the user can view patients under thier care. The can click on each of these patients and be directed to a version of the patient home screen. A caregiver can also add patients which are currently stored in the database. Caregivers do not have direct access to the sensors which the patient device is receiving data from, so for the purpose of testing this portion of the code is a simple display which resembles the patient home screen. 
