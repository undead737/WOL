package ru.internet.wol;

import java.net.*;
import java.util.ArrayList;

//todo Поменять все на свои Messages
class WOL {
    private Network _network;
    private ArrayList<InetAddress> _ipList;
    private int _port;
    private byte[] _mac;
    private boolean _full;
    private int _usedNetwork;

    WOL(){
        _network = new Network();
        _port = 9;
        _full = false;
        _ipList = new ArrayList<>();
        _usedNetwork = _network.getNumberUsed();
    }
    void getVersion(){
        Messages.throwExitMessage(ProjectData.VERSION, colour.white);
    }
    void setMac(String mac){
        try {
            _mac = checkMac(mac);
        }
        catch (Exception ex){
            Messages.throwExitMessage("'" + mac + "' : " + Messages.wrong_mac, colour.red);
        }
    }
    void setIp(String ip){
        try {
            _ipList.add(checkIp(ip));
        }
        catch (Exception ex){
            Messages.throwExitMessage("'" + ip + "' : " + Messages.wrong_ip, colour.red);
        }
    }
    void setPort(String port){
        try {
            _port = checkInt(port);
        }
        catch (Exception ex){
            Messages.throwExitMessage("'" + port + "' : " + Messages.wrong_port, colour.red);
        }
    }
    void setFullMode(){
        _full = true;
    }
    void setNetwork(String net){
        try {
            _usedNetwork = checkInt(net);
        }
        catch (Exception ex){
            Messages.throwExitMessage(Messages.wrong_net, colour.red);
        }
    }
    void wakeUp(){
        if (_mac == null) {
            Messages.throwExitMessage(Messages.no_mac, colour.red);
        }
        try {
            if(_full) {
                _ipList.addAll(_network.getAllBroadcast());
            }
            else if (_ipList.size() == 0){
                _ipList.add(_network.getBroadcast(_usedNetwork));
            }
        }
        catch (Exception ex){
            Messages.throwExitMessage(ex.getMessage(), colour.red);
        }
        byte[] wolPackage = new byte[17*6];
        for(int i=0;i<6;i++){
            wolPackage[i] = (byte)0xFF;
        }
        for(int i=1;i<17;i++) {
            System.arraycopy(_mac, 0, wolPackage, i * 6, 6);
        }
        //todo "255.255.255.255" = 192.168.10.255
        for (InetAddress ip : _ipList){
            try {
                DatagramPacket pck = new DatagramPacket(wolPackage, wolPackage.length, ip, _port);
                DatagramSocket socket = new DatagramSocket();
                socket.send(pck);
                Messages.throwInfoMessage(Messages.sendInfo + ip + ":" + _port, colour.green);
                socket.close();
            }
            catch (Exception ex){
                //Port out of range
                System.out.println(ex.getMessage());
            }
        }
    }
    void getAllInterfaces(){
        ArrayList<String> interfaces = _network.getAllInterfaces();
        for (String net : interfaces){
            Messages.throwInfoMessage(net, colour.green);
        }
        System.exit(0);
    }
    void getUsedInterface(){
        Messages.throwExitMessage(_network.getUsedInterface(), colour.green);
    }
    private byte[] checkMac(String mac) throws Exception{
        byte[] result = new byte[6];
        mac = mac.replace("-", "");
        mac = mac.replace(":", "");
        mac = mac.replace(";", "");
        if (mac.length() != 12){
            throw new Exception();
        }
        for (int i=0;i<6;i++){
            result[i] = (byte)Integer.parseInt(mac.substring(i*2, i*2+2), 16);
        }
        return result;
    }
    private InetAddress checkIp(String ip) throws Exception{
        return InetAddress.getByName(ip);
    }
    private int checkInt(String checkedInt) {
        return Integer.valueOf(checkedInt);
    }
}
