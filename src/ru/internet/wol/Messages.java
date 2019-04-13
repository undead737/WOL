package ru.internet.wol;

enum param{
    mac, ip, port, network
}
enum colour{
    white, red, green
}

class Messages {
    private static final String reset_colour = "\u001B[0m";

    static final String wrong_mac = "Wrong MAC-address! Examples of using: [FF:FF:FF:FF:FF:FF] [FF-FF-FF-FF-FF-FF] [FF;FF;FF;FF;FF;FF]";
    static final String wrong_ip = "Wrong IP-address! Example of using: [255.255.255.255]";
    static final String wrong_port = "Wrong Port!";
    static final String wrong_net = "Wrong number of Network!";
    static final String no_mac = "MAC-address is required option! Use option -m [hardware_address]";
    static final String wrong_param = "No parameter set: ";
    static final String wrong_option = ": option not found. Use --help to display all options";
    static final String no_networks = "Active network interfaces not detected";

    static final String sendInfo = "Sending to ";

    static void throwExitMessage(String message, colour col){
        System.out.println((char)27 + getColour(col) + message + reset_colour);
        System.exit(0);
    }

    static void throwInfoMessage(String message, colour col){
        System.out.println((char)27 + getColour(col) +  message + reset_colour);
    }

    static private String getColour(colour col){
        String colour = "[0m";
        switch (col){
            case red:
                colour = "[31m";
                break;
            case green:
                colour = "[32m";
                break;
        }
        return colour;
    }
}