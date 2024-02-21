import socket
import random
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
        moisture = random.randint(1, 2)
        print(moisture)
        c.send(bytes(str(moisture), 'utf-8'))
        
        # Send random numbers for 15 seconds
        start_time = time.time()
        while time.time() - start_time < 4:
            # Generate random numbers
            randNum = generateRandomInt()
            randNumTwo = generateRandomInt()
            randNumThree = generateRandomInt()

            # Send the random number as bytes
            c.send(bytes(str(randNum) + '\n', 'utf-8'))
            c.send(bytes(str(randNumTwo) + '\n', 'utf-8'))
            c.send(bytes(str(randNumThree) + '\n', 'utf-8'))

            # Wait for a short while before sending the next set of random numbers
            time.sleep(1)
            
    except ConnectionResetError:
        # Client disconnected
        print("Client disconnected")
        c.close()
