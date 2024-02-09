import socket
import random
import time

def generate_pressure_reading():
    print("Generating pressure reading!")
    # Your logic to generate simulated pressure readings
    rand_pressure = random.randint(0, 100)
    return rand_pressure

def main():
    # Initialize server socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # server_socket.bind(('localhost', 55786))
    server_socket.bind(('127.0.0.1', 55786))
    server_socket.listen(1)

    print("Waiting for client connection...")
    client_socket, client_address = server_socket.accept()
    print(f"Connected to client at {client_address}")

    # Send pressure readings to the client
    while True:
        pressure_reading1 = generate_pressure_reading()
        pressure_reading2 = generate_pressure_reading()
        pressure_reading3 = generate_pressure_reading()

        # Print out three pressure readings
        print("Printing out three pressure readings")
        print(f"Reading 1: {pressure_reading1}, Reading 2: {pressure_reading2}, Reading 3: {pressure_reading3}")

        # Send the pressure readings to the client
        client_socket.send(f"{pressure_reading1} {pressure_reading2} {pressure_reading3}\n".encode())

        # Simulate sending data every ten seconds
        time.sleep(10)

    # Close the socket connection
    # client_socket.close()
    # server_socket.close()

if __name__ == "__main__":
    main()
