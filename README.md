# Firewall

## Implementation Overall
* CSV file is read from the Firewall constructor and the file path should be specified.
* Each line of the CSV is encapsulated as Packet class.
* Packet is constructed with directory, protocol, port and ip. And every packet object has a unique identifier hashCode.
* HashMap is used in Firewall to effectively store the CSV records as the look up time is O(1).

## Further Improvement
* Store port and ip range effectively. This project uses a native approach, which is to traverse all the possible. combinations. Further improvement may use segment line data structure to reduce space and time.
* More test cases. Due to time limit, test cases are not included. Test cases may include large data set (more than 500k). 
* Complex error handling. This project only considers FileNotFoundException, more exceptions should be considered. 
