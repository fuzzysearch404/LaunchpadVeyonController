# Veyon Launchpad Controller

This software turns Novation Launchpad S into a control device for Veyon remote screen access management.

![Main image](https://i.imgur.com/sMGDcKwl.jpg)

## Features

* Bind Veyon connection data information to each of Launchpad's 64 pads.
* Use that connection data to remotely view or control device screens via Veyon with a single push of a Launchpad's pad.
* Create and import/export multiple configurations.

## Requirements

#### Hardware

It is highly recommended to have Novation Launchpad S device, but it is not mandatory.

#### Software (to run the program)
1) Java 11+
2) [Veyon](https://veyon.io/)

#### Java dependencies

1) [LP4J](https://github.com/OlivierCroisier/LP4J)
2) [JavaFX 11](https://openjfx.io/)
3) [JSON-Java](https://github.com/stleary/JSON-java)

## Building

1) Download or clone this repository.
2) Download and build all the required Java dependencies.
3) Add LP4J `lp4j-api` and `lp4j-midi` dependencies to the project's classpath.
4) Set up JavaFX 11 and add it to the project's classpath.
5) Add JSON-Java dependency to the project's classpath.
6) Compile and run the program. (Don't forget to add the VM arguments for the JavaFX)

## Getting started

Before using this software, there are some things to set up first.

#### Veyon

1) Install and configure Veyon on the desired devices. 
2) **Add Veyon CLI (command line interface) to the system's environment where this program will run.**  
This is very important and this must be done or else this program won't be able to work as intended.
3) For best experience, it is recommended to set up and enable Veyon's authorization keys.

#### Configuration (via GUI)

Configuration via graphical user interface is very simple and can even be done in program's runtime.

1) Open the program and turn the configuration mode on. `File -> Configure Pads`

![Confugure Pads Option](https://i.imgur.com/HoeODbh.png)

2) Configure the pads: 
	* The blue pads represent empty pads - press any of them in the GUI or on the physical Launchpad device to configure them.
	* The red pads represent configured pads - press any of them in the GUI or on the physical Launchpad device to configure them or delete their configurations.

![Editing the pads](https://i.imgur.com/3FFVySU.png)  
3) Exit the configuration mode. Turn `File -> Configure Pads` off.

#### Configuration files
Configuration can also be done by creating a custom configuration JSON file.
The only note is that configuration indexes start from 0.
##### Configuration file structure
```
columns - columns of the Launchpad
 └ rows - rows of the Launchpad
  └ ip - the IP addess (and optionally the TCP port) or name of the device
```
 
##### Example

Let's create an example configuration file.
* Device A: Let's put device named `DESKTOP-1234` on Launchpad's Pad column: 3, row: 2
* Device B: Let's put device with IP address `127.1.0.1` on Launchpad's Pad column: 3, row: 3
* Device C: Let's put device with IP address `127.1.0.2` and TCP port 1234 on Launchpad's Pad column: 5, row: 1

Here is the configuration file:
```
{
  {
   "2":{
      "1":{
         "ip":"DESKTOP-1234"
      },
      "2":{
         "ip":"127.1.0.1"
      }
   },
   "4":{
      "0":{
         "ip":"127.1.0.2:1234"
      }
   }
}
```

Where the devices are on the Launchpad with this configuration file:
```

	                   Top buttons
	  	    +---+---+---+---+---+---+---+---+ 
	        |   |   |   |   |   |   |   |	 |
	        +---+---+---+---+---+---+---+---+ 
	          
	                      Pads
	          0   1   2   3   4   5   6   7   X (rows) ->
	        +---+---+---+---+---+---+---+---+  +---+
	      0 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      1 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      2 |   | A | B |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      3 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      4 | C |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      5 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      6 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      7 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
          Y (columns)                    Right side buttons  
          |
		  ⌄
          
   
```
After creating the custom configuration file, it can be imported via the GUI or by replacing `config.json` under the project's `/files` directory.