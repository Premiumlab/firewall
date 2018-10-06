public class Packet {

    private String direction;
    private String protocol;
    private Integer port;
    private Long ip;

    public Packet(String direction, String protocol, String port, String ip){
        this.direction = direction;
        this.protocol = protocol;
        this.port =  Integer.parseInt(port);
        this.ip = Long.valueOf(ip);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) {return true;}
        if(!(o instanceof Packet)){return false;}

        if(direction == null || protocol == null || port == null || ip == null){return false;}
        Packet packet = (Packet) o;
        return direction == packet.direction && protocol == packet.protocol && port == packet.port && ip == packet.ip;
    }

    public int getHashCode(){
        //return a unique identifier of a packet
        long hash = 31 * (direction.hashCode() + protocol.hashCode() + port + ip);
        return (int) hash;
    }

}
