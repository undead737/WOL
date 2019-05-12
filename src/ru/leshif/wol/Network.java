package ru.leshif.wol;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

class Network {
    private ArrayList<NetInerface> _netInterfaces;
    private int _numberUsed;

    private class NetInerface{
        private int _number;
        private String _name;
        private InetAddress _broadcast;

        NetInerface(int number, String name, InetAddress broadcast){
            _number = number;
            _name = name;
            _broadcast = broadcast;
        }
    }

    Network(){
        try {
            _netInterfaces = allInterfaces();
            if (_netInterfaces.isEmpty()){
                Messages.throwExitMessage(Messages.NO_NETWORKS, colour.red);
            }
        } catch (Exception ex){
            Messages.throwExitMessage(ex.getMessage(), colour.red);
        }
    }
    private ArrayList<NetInerface> allInterfaces() throws Exception{
        ArrayList<NetInerface> result = new ArrayList<>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()){
            NetworkInterface net = netInterfaces.nextElement();
            for (InterfaceAddress addr : net.getInterfaceAddresses()){
                if (addr.getAddress().isSiteLocalAddress()){
                    result.add(new NetInerface(net.getIndex(), net.getDisplayName(), addr.getBroadcast()));
                    _numberUsed = net.getIndex();
                }
            }
        }
        return result;
    }
    int getNumberUsed(){
        return _numberUsed;
    }
    String getUsedInterface(){
        String result = "";
        for (NetInerface net : _netInterfaces){
            if (net._number == _numberUsed){
                result = net._number + "   " + net._name + "   " + net._broadcast;
            }
        }
        return result;
    }
    ArrayList<String> getAllInterfaces(){
        ArrayList<String> result = new ArrayList<>();
        _netInterfaces.forEach(x -> result.add(x._number + "   " + x._name + "   " + x._broadcast));
        return result;
    }
    InetAddress getBroadcast(int number){
        return Objects.requireNonNull(_netInterfaces.stream().filter(x -> x._number == number).findFirst().orElse(null))._broadcast;
    }
    ArrayList<InetAddress> getAllBroadcast(){
        ArrayList<InetAddress> result = new ArrayList<>();
        _netInterfaces.forEach(x -> result.add(x._broadcast));
        return result;
    }
}
