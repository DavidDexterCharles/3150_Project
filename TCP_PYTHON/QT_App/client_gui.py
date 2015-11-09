'''
Created on Sep 16, 2015
#https://pythonspot.com/pyqt4/
@author: David
'''

#! /usr/bin/env python
# -*- coding: utf-8 -*-
#

import sys
from PyQt4.QtCore import pyqtSlot # needed for @pyqtSlot() to work
from PyQt4.QtGui import * # imports all the gui components
 
 
# create our window
app = QApplication(sys.argv) # Create an PyQT4 application object.      
w = QWidget()# The QWidget widget is the base class of all user interface objects in PyQt4.

#w=QMainWindow()# The QWidget widget is the base class of all user interface objects in PyQt4.

w.resize(600, 600)# Set window size. 
w.setWindowTitle("Hello World! Time for QT Training") # Set window title  


# Create main menu
# mainMenu = w.menuBar()
# fileMenu = mainMenu.addMenu('&File')

# Add exit button
# exitButton = QAction(QIcon('exit24.png'), 'Exit', w)
# exitButton.setShortcut('Ctrl+Q')
# exitButton.setStatusTip('Exit application')
# exitButton.triggered.connect(w.close)
# fileMenu.addAction(exitButton)


 
# Add a button exit button
btnexit = QPushButton('Exit', w)   # creates a button in the window
btnexit.setToolTip('Click to close window!')
btnexit.clicked.connect(exit)
btnexit.resize(btnexit.sizeHint())
btnexit.move(100, 80) # move(x, y) if x increase you go to the right if y increase you go down


# Add another button 
btn1 = QPushButton('My output show when clicked Shadae', w)
btn1.move(200, 80)
btn2 = QPushButton('My output show when pressed', w)
btn2.move(300, 150)
btn3 = QPushButton(' My output show on release', w)
btn3.move(400, 200)


# Create the actions 
@pyqtSlot()
def on_click():
    result = QMessageBox.question(w, 'Message', "Do you like Python?", QMessageBox.Yes | QMessageBox.No, QMessageBox.No) # Show a message box, not the site have different types of message box
    if result == QMessageBox.Yes:
        print 'Yes. was clicked'
    else:
        print 'No.  was clicked' 

    
 
@pyqtSlot()
def on_press():
    print('pressed')
 
@pyqtSlot()
def on_release():
    print('released')
 
# connect the signals to the slots
btn1.clicked.connect(on_click)
btn2.pressed.connect(on_press)
btn3.released.connect(on_release)

 
# Show window
w.show() 
 
sys.exit(app.exec_())