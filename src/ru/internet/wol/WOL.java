package ru.internet.wol;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

//todo Поменять все на свои Messages
public class WOL {

    private ArrayList<InetAddress> _ip;
    private int _port;
    private byte[] _mac;
    private boolean _full = false;

    public WOL(){
        _port = 9;
        _ip = new ArrayList<>();
    }
    void setMac(String mac){
        try {
            _mac = checkMac(mac);
        }
        catch (Exception ex){
            Messages.throwOutMessage(Messages.wrong_mac);
        }
    }
    void setIp(String ip){
        try {
            _ip.add(checkIp(ip));
        }
        catch (Exception ex){
            Messages.throwOutMessage("'" + ip + "' : " + Messages.wrong_ip);
        }
    }
    void setPort(String port){
        try {
            _port = checkPort(port);
        }
        catch (Exception ex){
            Messages.throwOutMessage(Messages.wrong_port);
        }
    }
    void setFullMode(){
        _full = true;
    }

    void wakeUp(){
        if (_mac == null){
            Messages.throwOutMessage(Messages.no_mac);
        }
        try {
            if(_full){
                _ip.addAll(getAllBroadcast());
            }
            else if (_ip.size() == 0){
                _ip.add(getBroadcast());
            }
        }
        //todo
        catch (Exception ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        byte[] upPackage = new byte[17*6];
        for(int i=0;i<6;i++){
            upPackage[i] = (byte)0xFF;
        }
        //todo
        for(int i=1;i<17;i++) System.arraycopy(_mac, 0, upPackage, i * 6, 6);

        //todo "255.255.255.255" = 192.168.10.255
        for (InetAddress ip : _ip){
            try {
                DatagramPacket pck = new DatagramPacket(upPackage, upPackage.length, ip, _port);
                DatagramSocket socket = new DatagramSocket();
                socket.send(pck);
                Messages.throwInfoMessage(Messages.sendInfo + ip + ":" + _port);
                socket.close();
            }
            catch (Exception ex){
                //Port out of range
                System.out.println(ex.getMessage());
            }
        }
    }

    private byte[] checkMac(String mac) throws Exception{
        byte[] result = new byte[6];
        mac = mac.replace("-", "");
        mac = mac.replace(":", "");
        mac = mac.replace(";", "");
        if (mac.length() > 12 || mac.length() < 12){
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

    private int checkPort(String port) throws Exception{
        return Integer.valueOf(port);
    }

    private InetAddress getBroadcast() throws Exception{
        InetAddress _net = null;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface next = interfaces.nextElement();
            for(InterfaceAddress interaddress : next.getInterfaceAddresses()){
                if (interaddress.getAddress().isSiteLocalAddress()){
                    _net = interaddress.getBroadcast();
                }
            }
        }
        return _net;
    }

    //todo broadcast addr + gateway or router
    //todo test broadcast ...255
    private ArrayList<InetAddress> getAllBroadcast() throws Exception {
        ArrayList<InetAddress> _net = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface next = interfaces.nextElement();
            for(InterfaceAddress interaddress : next.getInterfaceAddresses()){
                if (interaddress.getAddress().isSiteLocalAddress()){
                    _net.add(interaddress.getBroadcast());
                }
            }
        }
        return _net;
    }
}
