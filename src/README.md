## To run heartbeat

1. In terminal, navigate to the `src` directory of the project
and compile `BatteryChecker.java` and `Battery.java` classes
```
$ javac BatteryChecker.java Battery.java
```

2. Run the registry
```
$ rmiregistry <port_number>
```
default port is 1099, `<port_number>` can be left empty
```
 $ rmiregistry
```
3. On a new terminal window, run the `BatteryChecker` object (server)
```Â
$ java BatteryChecker
```
4. On a new terminal window, run the `Battery2` object
```
$ java Battery2
```
5. On a new terminal window, run the `Battery1` object
```
$ java Battery1
```
6. On a new terminal window, run the `Simulator` object
```
$ java Simulator
```
