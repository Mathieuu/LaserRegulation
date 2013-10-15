LaserRegulation
===============

Java application controlling the laser power of a confocal microscope.

#### A word about the software

To run the demo version, just launch Regulation-Demo-Mode.jar. This file needs backup.ml0 and the folders: images, L1 and L2 in same folder in order to work properly.

Only the Java application is available on download. If you are interested in the Arduino program or any technical detail, please contact me directly.

This version has been modified for demo purpose. The code portion in charge of the communication with the Arduino device has been disabled. To try the application in real conditions, uncomment lines in Regulation.java and each method in Communication.java. Since Communication.java uses a low level library to manipulate the serial port, the real software can only be used with Window.

#### Purpose of the project

The aim of this project is to control the laser power of an experimental confocal microscope. This microscope allows biologists to get a full 3d + time modelization of a fish embryon development. By modifying the laser calibration, it is also possible to obtain images of the cells inside the embryon. The regulation task is to increase/decrease the laser power to keep the same image quality as the laser penetrates deeper in the embryon and get absorbed.

The laser power is composed of a polarized filter coupled to an Arduino card and a computer with a Java software used by biologists to configure, compute the regulation curve and launch the control process.