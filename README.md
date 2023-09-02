# Talk-to-Talk

Clone the repo:
```
https://github.com/PineberryCode/Talk-to-Talk.git
```
### Advice
If you want to communicate with other users of your own LAN. Please, set up this line and add your Laptop, PC, or Server IP Address.<br>
Suppose this is your server IP Address ***192.168.100.7***:<br><br>
**Server.java** file
```JAVA
server = new ServerSocket(PORT,0,InetAddress.getByName("192.168.100.7"));
```
Afterward, all users will have to connect to that IP.<br><br>
**User.java** file
```JAVA
user = new Socket("192.168.100.7", 5000);
```
Once you have done that setting up. Now you can communicate with other users of your LAN.

### Warning
While the server is on you will communicate with other users and the **user.java** not throw an error. Please, try to activate it first (Server).

### Note:<br>
###### Credits: <br>[NeuralNine Channel](https://www.youtube.com/@NeuralNine) <br>[Video](https://www.youtube.com/watch?v=hIc_9Wbn704)
- I looked for his repo in Github but I couldn't find it, hence the credit (September 01, 2023). <br>
- I did some settings.
