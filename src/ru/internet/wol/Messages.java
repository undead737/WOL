package ru.internet.wol;

enum param{
    mac, ip, port
}

class Messages {
    static final String wrong_mac = "Wrong MAC-address! Examples of using: [FF:FF:FF:FF:FF:FF] [FF-FF-FF-FF-FF-FF] [FF;FF;FF;FF;FF;FF]";
    static final String wrong_ip = "Wrong IP-address! Example of using: [255.255.255.255]";
    static final String wrong_port = "Wrong Port!";
    static final String no_mac = "MAC-address is required option! Use option -m [hardware_address]";
    static final String wrong_param = "No parameter set: ";
    static final String wrong_option = ": option not found. Use --help to display all options";


    static void throwMessage(String message){
        System.out.println(message);
        System.exit(0);
    }
}
