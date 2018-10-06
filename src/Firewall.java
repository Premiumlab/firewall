import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Firewall {
    private HashMap<Integer, Boolean> map = new HashMap<>();

    public Firewall(String filePath) {
        try{
            Scanner input = new Scanner(new File(filePath));
            while(input.hasNextLine()){
                String record = input.nextLine();
                buildMap(record);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file \""+ filePath + "\" found!");
        }
    }

    public void buildMap(String record){
        if(record == null || record.length() == 0){
            return ;
        }
        String[] rules = record.split(",");
        String direction = rules[0];
        String protocol = rules[1];
        String portAddress = rules[2];
        String ipAddress = rules[3];
        List<String> portRange = new ArrayList<>();
        List<String> ipRange = new ArrayList<>();
        if(hasRange(portAddress)){
            portRange = getRange(portAddress);
        }
        if(hasRange(ipAddress)){
            ipRange = getRange(ipAddress);
        }

        if(hasRange(portAddress) && hasRange(ipAddress)){
            for(int i = 0; i < portRange.size(); i++){
                String port = portRange.get(i);
                for(int j = 0; j < ipRange.size(); j++){
                    String ip = ipRange.get(j);
                    Packet packet = new Packet(direction, protocol, port, ip);
                    map.put(packet.getHashCode(), Boolean.TRUE);
                }
            }
        }else if(hasRange(portAddress) && !hasRange(ipAddress)){
            for(int i = 0; i < portRange.size(); i++){
                String port = portRange.get(i);
                Packet packet = new Packet(direction, protocol, port, ipAddress);
                map.put(packet.getHashCode(), Boolean.TRUE);
            }
        }else if(!hasRange(portAddress) && hasRange(ipAddress)) {
            for(int j = 0; j < ipRange.size(); j++){
                String ip = ipRange.get(j);
                Packet packet = new Packet(direction, protocol, portAddress, ip);
                map.put(packet.getHashCode(), Boolean.TRUE);
            }
        }else{
            Packet packet = new Packet(direction, protocol, portAddress, ipAddress);
            map.put(packet.getHashCode(), Boolean.TRUE);
        }
    }

    public boolean acceptPacket(String direction, String protocol, Integer port, String ip){
        String protString = port.toString();
        return acceptPacket(direction, protocol, protString, ip);
    }

    public boolean acceptPacket(String direction, String protocol, String port, String ip){
        if(direction == null || protocol == null || port == null || ip == null){
            return false;
        }
        Packet packet = new Packet(direction, protocol, port, ip);
        return map.get(packet.getHashCode());

    }


    public boolean hasRange(String s){
        return s.contains("-");
    }

    public List<String> getRange(String s){
        if(!hasRange(s)){
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        if(!s.contains(".")){
            //input string is port
            for(int i =  Integer.parseInt(s.split("-")[0]); i <= Integer.parseInt(s.split("-")[1]); i++ ){
                result.add(String.valueOf(i));
            }
        }else{
            //input string is ip
            int smallIP = Integer.parseInt(s.split("-")[0].replace(".", ""));
            int bigIP = Integer.parseInt(s.split("-")[1].replace(".", ""));
            for(int i = smallIP; i <= bigIP; i++){
                result.add(String.valueOf(i));
            }
        }
        return result;
    }

}
