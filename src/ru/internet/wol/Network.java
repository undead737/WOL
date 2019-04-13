package ru.internet.wol;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

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
            _netInterfaces = AllInterfaces();
            if (_netInterfaces.size()==0){
                Messages.throwExitMessage(Messages.no_networks, colour.red);
            }
        } catch (Exception ex){
            Messages.throwExitMessage(ex.getMessage(), colour.red);
        }
    }

    private ArrayList<NetInerface> AllInterfaces() throws Exception{
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
        for (NetInerface net : _netInterfaces){
            result.add(net._number + "   " + net._name + "   " + net._broadcast);
        }
        return result;
    }
    InetAddress getBroadcast(int number) throws Exception{
        InetAddress result = _netInterfaces.stream().filter((x) -> x._number == number).findFirst().get()._broadcast;
        if (result == null){
            throw new Exception(Messages.wrong_net + " : " + number);
        }
        return result;
    }
    ArrayList<InetAddress> getAllBroadcast(){
        //return new ArrayList<InetAddress>().addAll(_netInterfaces.stream().forEach((x) -> x._broadcast));
        ArrayList<InetAddress> result = new ArrayList<>();
        for (NetInerface net : _netInterfaces){
            result.add(net._broadcast);
        }
        return result;
    }
}
