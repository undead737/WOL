package ru.leshif.wol;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

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
            Messages.throwExitMessage("'" + mac + "' : " + Messages.WRONG_MAC, colour.red);
        }
    }
    void setIp(String ip){
        try {
            _ipList.add(checkIp(ip));
        }
        catch (Exception ex){
            Messages.throwExitMessage("'" + ip + "' : " + Messages.WRONG_IP, colour.red);
        }
    }
    void setPort(String port){
        try {
            _port = checkInt(port);
            if (_port<1 || _port>65535){
                Messages.throwExitMessage(Messages.PORT_OUT_RANGE, colour.red);
            }
        }
        catch (Exception ex){
            Messages.throwExitMessage("'" + port + "' : " + Messages.WRONG_PORT, colour.red);
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
            Messages.throwExitMessage(Messages.WRONG_NET, colour.red);
        }
    }
    void wakeUp(){
        if (_mac == null) {
            Messages.throwExitMessage(Messages.NO_MAC, colour.red);
        }
        if(_full) {
            _ipList.addAll(_network.getAllBroadcast());
        }
        else if (_ipList.isEmpty()){
            try {
                _ipList.add(_network.getBroadcast(_usedNetwork));
            }
            catch (Exception ex){
                Messages.throwExitMessage(Messages.ERROR_GETTING_NET + _usedNetwork + ". " + Messages.INFO_NET, colour.red);
            }
        }
        byte[] wolPackage = new byte[17*6];
        for(int i=0;i<6;i++){
            wolPackage[i] = (byte)0xFF;
        }
        for(int i=1;i<17;i++) {
            System.arraycopy(_mac, 0, wolPackage, i * 6, 6);
        }
        for (InetAddress ip : _ipList){
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.send(new DatagramPacket(wolPackage, wolPackage.length, ip, _port));
                socket.close();
                Messages.throwInfoMessage(Messages.INFO_SEND + ip + ":" + _port, colour.green);
            }
            catch (IOException io){
                Messages.throwExitMessage(Messages.ERROR_SEND_WOL + ip + ":" + _port, colour.red);
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
