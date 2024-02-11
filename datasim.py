# first of all import the socket library 
import socket
import random  

# return random integer when called from 1-100
def generateRandomInt():
    return random.randint(1,100)

def moisutreDetection():
    return random.randint(1,2)

# Generate two random numbers
randNum = generateRandomInt()
print(randNum)

# Generate two random numbers
randNumTwo = generateRandomInt()
print(randNumTwo)

randNumThree = generateRandomInt()
print(randNumThree)

randNumFour = moisutreDetection()
print(randNumFour)
 
# next create a socket object 
s = socket.socket()         
print ("Socket successfully created")

# reserve a port on your computer in our 
# case it is 12345 but it can be anything 
port = 12345            

# Next bind to the port 
# we have not typed any ip in the ip field 
# instead we have inputted an empty string 
# this makes the server listen to requests 
# coming from other computers on the network 
s.bind(('', port))         
print ("socket binded to %s" %(port)) 

# put the socket into listening mode 
s.listen(5)     
print ("socket is listening")         

# a forever loop until we interrupt it or 
# an error occurs 
while True: 

# Establish connection with client. 
    c, addr = s.accept()     
    print ('Got connection from', addr )
 
# Send the random number as bytes
    c.send(bytes(str(randNum) + '\n', 'utf-8'))
 
# Send the random number as bytes
    c.send(bytes(str(randNumTwo) + '\n', 'utf-8'))
    
# Send the random number as bytes
    c.send(bytes(str(randNumThree) + '\n', 'utf-8'))
    
# Send the random number as bytes
    c.send(bytes(str(randNumFour), 'utf-8'))

# Close the connection with the client 
    c.close()

# Breaking once connection closed
    break
