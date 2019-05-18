package ru.leshif.wol;

class Messages {
    private static final String reset_colour = "\u001B[0m";

    static final String WRONG_MAC = "Wrong MAC-address! Examples of using: [FF:FF:FF:FF:FF:FF] [FF-FF-FF-FF-FF-FF]";
    static final String WRONG_IP = "Wrong IP-address! Example of using: [192.168.10.1]";
    static final String WRONG_PORT = "Wrong Port!";
    static final String PORT_OUT_RANGE = "Port out of range!";
    static final String WRONG_NET = "Wrong number of Network!";
    static final String NO_MAC = "MAC-address is required option! Use option -m [hardware_address]";
    static final String WRONG_PARAM = "No value set: ";
    static final String WRONG_OPTION = " option don't found. Use --help or -h to display all options";
    static final String NO_NETWORKS = "Active network interfaces don't detected";
    static final String ERROR_GETTING_NET = "Error getting net-interface with number: ";
    static final String ERROR_SEND_WOL = "Error sending wol-packet to ";
    static final String INFO_NET = "Use option -na to display all available network interfaces";
    static final String INFO_SEND = "Sending to ";
    static final String ERROR_HELP = "Error trying to read HELP file. You may use git documentation";

    static void throwExitMessage(String message, colour col){
        if (!System.getProperty("os.name").toLowerCase().contains("windows")){
            System.out.println((char)27 + getColour(col) + message + reset_colour);
        }
        else{
            System.out.println(message);
        }
        System.exit(0);
    }
    static void throwInfoMessage(String message, colour col){
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.out.println((char) 27 + getColour(col) + message + reset_colour);
        }
        else {
            System.out.println(message);
        }
    }
    static private String getColour(colour col){
        switch (col){
            case red:
                return "[31m";
            case green:
                return "[32m";
            case white:
            default:
                return "[0m";
        }
    }
}