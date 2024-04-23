import socket
import random
import struct
import time
import json

def generateRandomInt():
    return random.randint(1, 100)

# Create a socket object
s = socket.socket()

# Bind to a port
s.bind(('localhost', 12345))

# Listen for incoming connections
s.listen(5)

print("Waiting for connection...")

while True:
    # Accept a connection from a client
    c, addr = s.accept()
    print("Connection from", addr)

    try:
        # Receive the login confirmation message from the client
        login_confirmation = c.recv(1024).decode()
        login_data = json.loads(login_confirmation)
        if login_data['status'] == 'logged_in':
            print("Client logged in successfully")

            # Continuously send random numbers every 2 seconds
            while True:
                # Generate and send moisture value
                moisture = random.randint(0, 1)
                print("Moisture:", moisture)
                c.send(struct.pack('>i', moisture))

                randNum = generateRandomInt()
                print("Pressure Center:", randNum)
                randNumTwo = generateRandomInt()
                print("Pressure Left:", randNumTwo)
                randNumThree = generateRandomInt()
                print("Pressure Right:", randNumThree)
                message = struct.pack('>iii', randNum, randNumTwo, randNumThree)
                c.send(message)
                time.sleep(2)
        else:
            print("Login failed")

    except ConnectionResetError:
        print("Client disconnected")
    except ConnectionAbortedError:
        print("Connection aborted by the host")
    except json.JSONDecodeError:
        print("Invalid JSON received")
    finally:
        c.close()
