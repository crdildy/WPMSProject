Capstone II Wearable Pressure and Moisture sensors

USAGE INTRUCTIONS:

Running the data simulation

In order to recieve data from TCP connection you must run the server(datasim.py) first and then the client(MainActivity.kt). Create a new user login using an email of your choice and right after logging in you should be prompted to a Please Wait Reading... screen, and after a few seconds the data from the TCP connection should then be displayed onto the emulator screen following with an alert if the threshold for pressure is reached.

Log in process
The application utilizes Firebase Authentication to store and verify log in credentials. Upon starting the application, the user is prompted to enter their username and password. The username must be a valid email address and the password must not be empty. The entered information will be verified with Firebase and will display errors with logging in as toast messages. If the user does not already have credentials stored in the database they are given the option to click a sign up button. They will be taken to a sign up page and asked to enter their desired log in information. This new information will be directed to Firebase for storage and later verification. 

Patient homepage

Caregiver homepage
