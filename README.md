# Talk-to-Talk


### Advice
If you want to communicate with other users of your LAN. Please set up this line and add your Laptop, PC, or Server IP Address.<br>
Suppose this is your server IP Address ***192.168.100.7***:<br><br>
**Server.java** file:
```JAVA
server = new ServerSocket(5000,0,InetAddress.getByName("192.168.100.7"));
```
Subsequently, all users will have to connect to that IP.<br><br>
**User.java** file:
```JAVA
user = new Socket("192.168.100.7", 5000);
```
After you have completed that setup, you can now communicate with other users on your LAN.

> [!NOTE]
> Enable port of your preference with **ufw**

### Warning
While the server is turned on, you will communicate with other users, and the **user.java** will not throw an error. Please try to activate it first (Server).

### Note:<br>
###### Credits: <br>[NeuralNine Channel](https://www.youtube.com/@NeuralNine) <br>[Video](https://www.youtube.com/watch?v=hIc_9Wbn704)
- I looked for his Github repo but I couldn't find it, hence the credit (September 01, 2023). <br>
- I did some settings.
