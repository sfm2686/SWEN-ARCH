## To run heartbeat

1. In terminal, navigate to the `src` directory of the project
and compile `BatteryChecker.java` and `Battery.java` classes
```
$ javac BatteryChecker.java Battery.java
```

3. Run the registry
```
$ rmiregistry <port_number>
```
default port is 1099, `<port_number>` can be left empty
```
 $ rmiregistry
```
4. On a new terminal window, run the `BatteryChecker` object (server)
```
$ java BatteryChecker
```
5. On a new terminal window, run the `Battery` object (client)
```
$ java Battery
```
