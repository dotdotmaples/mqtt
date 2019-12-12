package gprs.com.entity;

public class MqttClient {

    private String client_id;
    private String username;
    private String ipaddress;
    private Integer port;
    private boolean clean_sess;
    private Integer proto_ver;
    private Integer keepalive;
    private String connected_at;
    public String getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getIpaddress() {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public boolean isClean_sess() {
        return clean_sess;
    }
    public void setClean_sess(boolean clean_sess) {
        this.clean_sess = clean_sess;
    }
    public Integer getProto_ver() {
        return proto_ver;
    }
    public void setProto_ver(Integer proto_ver) {
        this.proto_ver = proto_ver;
    }
    public Integer getKeepalive() {
        return keepalive;
    }
    public void setKeepalive(Integer keepalive) {
        this.keepalive = keepalive;
    }
    public String getConnected_at() {
        return connected_at;
    }
    public void setConnected_at(String connected_at) {
        this.connected_at = connected_at;
    }
    @Override
    public String toString() {
        return "MqttClient [client_id=" + client_id + ", username=" + username + ", ipaddress=" + ipaddress + ", port="
                + port + ", clean_sess=" + clean_sess + ", proto_ver=" + proto_ver + ", keepalive=" + keepalive
                + ", connected_at=" + connected_at + "]";
    }

}
