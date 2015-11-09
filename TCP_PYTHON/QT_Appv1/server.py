'''
Created on Sep 16, 2015

@author: David
'''
#!/usr/bin/env python
#http://stackoverflow.com/questions/12362542/python-server-only-one-usage-of-each-socket-address-is-normally-permitted

#from socket import *
import socket
serverName =socket.gethostbyname("localhost")
serverPort = 4486
BUFFER_SIZE = 1024

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR, 1)
s.bind((serverName, serverPort))
s.listen(1)

print "Server is ready to receive data..."

while 1:
        newConnection, client = s.accept()#creates the auxiliary socket
        msg = newConnection.recv(BUFFER_SIZE)

        print msg

        newConnection.send("+OK message received")
        newConnection.close()