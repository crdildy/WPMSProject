import socket
import random
import struct
import time
import json

def pressureDetectionAlwaysFalse():
    return random.randint(1, 85)

def pressureDetectionAlwaysTrue():
    return random.randint(86, 100)

# Create a socket object
s = socket.socket()

# Bind to a port
s.bind(("192.168.2.178", 12345))

# Listen for incoming connections
s.listen(5)

print("Waiting for connection...")

test_mode = 0  # Start with the first test case

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
            
            # Continuously send the selected test mode moisture and pressure value
            while True:
                # Check for new commands from client (non-blocking with timeout)
                c.settimeout(0.1)  # Set a short timeout for non-blocking receive
                try:
                    command = c.recv(1024).decode().strip()
                    if command == 'switch':
                        test_mode = (test_mode + 1) % 4  # Cycle through 0, 1, 2, 3
                        print(f"Switched test mode to {test_mode}")
                except socket.timeout:
                    pass  # No command received, continue as normal

                # Determine moisture and pressure based on test_mode
                if test_mode == 0: # This is always going to send moisture alerts
                    moisture = 1
                    pressureOne = pressureDetectionAlwaysFalse()
                    pressureTwo = pressureDetectionAlwaysFalse()
                    pressureThree = pressureDetectionAlwaysFalse()
                elif test_mode == 1: # This is always going to send pressure alerts
                    moisture = 0
                    pressureOne = pressureDetectionAlwaysTrue()
                    pressureTwo = pressureDetectionAlwaysTrue()
                    pressureThree = pressureDetectionAlwaysTrue()
                elif test_mode == 2: # This is always going to send pressure and moisture alerts
                    moisture = 1
                    pressureOne = pressureDetectionAlwaysTrue()
                    pressureTwo = pressureDetectionAlwaysTrue()
                    pressureThree = pressureDetectionAlwaysTrue()
                elif test_mode == 3: # This is never going to send pressure and moisture alerts
                    moisture = 0
                    pressureOne = pressureDetectionAlwaysFalse()
                    pressureTwo = pressureDetectionAlwaysFalse()
                    pressureThree = pressureDetectionAlwaysFalse()

                print("Moisture:", moisture)
                c.send(struct.pack('>i', moisture))
                message = struct.pack('>iii', pressureOne, pressureTwo, pressureThree)
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
