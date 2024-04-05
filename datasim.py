import socket
import random
import struct
import time

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
        # Generate and send moisture value
        moisture = random.randint(0, 1)
        print("Moisture:", moisture)
        c.send(struct.pack('>i', moisture))
        
        # Send random numbers for 1 seconds
        start_time = time.time()
        while time.time() - start_time < 1:
            # Generate random numbers
            randNum = generateRandomInt()
            randNumTwo = generateRandomInt()
            randNumThree = generateRandomInt()

            # Pack the random numbers in big-endian format (4 bytes each for integers)
            message = struct.pack('>iii', randNum, randNumTwo, randNumThree)
            c.send(message)

            # Wait for a short while before sending the next set of random numbers
            time.sleep(1)
    
    except ConnectionResetError:
        # Client disconnected
        print("Client disconnected")
        c.close()
