package ru.internet.wol;

enum param{
    mac, ip, port
}

class Exceptions {
    static final String wrong_mac = "Wrong MAC-address! Examples of using: [FF:FF:FF:FF:FF:FF] [FF-FF-FF-FF-FF-FF] [FF;FF;FF;FF;FF;FF]";
    static final String wrong_ip = "Wrong IP-address! Examples of using: [255.255.255.255]";
    static final String wrong_port = "Wrong Port!";
    static final String no_mac = "MAC-address is required option! Usage: leshif-wol -m [hardware_address]";
    static final String wrong_param = "No parameter set: ";


    static void throwException(String message){
        System.out.println(message);
        System.exit(0);
    }
}
