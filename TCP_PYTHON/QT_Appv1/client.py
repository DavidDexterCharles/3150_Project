'''
Created on Sep 16, 2015

@author: David
'''
#!/usr/bin/env python
#not eclipse behaves funny when open, ensure eclipse IDE is closed when running client and server    
import socket
   
   
TCP_IP = socket.gethostbyname("localhost")
TCP_PORT = 4486
BUFFER_SIZE = 1024
MESSAGE =raw_input('Enter your message: ') #note raw_input have to be used in python versions lower than 3

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(MESSAGE)
data = s.recv(BUFFER_SIZE)
s.close()
  
print "received data:", data