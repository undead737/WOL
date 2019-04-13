package ru.internet.wol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class Main {
    public static void main(String[] args) {
        param param = null;
        WOL wol = new WOL();
        //todo make norm switch
        for(int i = 0;i<args.length;i++){
            try {
                switch (args[i]){
                    case "-m":
                        param = ru.internet.wol.param.mac;
                        wol.setMac(args[i+1]);
                        i++;
                        break;
                    case "-i":
                        param = ru.internet.wol.param.ip;
                        wol.setIp(args[i+1]);
                        i++;
                        break;
                    case "-p":
                        param = ru.internet.wol.param.port;
                        wol.setPort(args[i+1]);
                        i++;
                        break;
                    case "-v":
                        wol.getVersion();
                        break;
                    case "-f":
                        wol.setFullMode();
                        break;
                    case "-n":
                        param = ru.internet.wol.param.network;
                        wol.setNetwork(args[i+1]);
                        i++;
                        break;
                    case "-na":
                        wol.getAllInterfaces();
                        break;
                    case "-nu":
                        wol.getUsedInterface();
                        break;
                    case "--help":
                        help();
                        System.exit(0);
                        break;
                    default:
                        Messages.throwExitMessage(args[i] + Messages.wrong_option, colour.red);
                        break;
                }
            }
            catch (Exception ex){
                Messages.throwExitMessage(Messages.wrong_param + param, colour.red);
            }
        }
        wol.wakeUp();
    }

    private static void help(){
        try {
            InputStreamReader fileReader = new InputStreamReader (Main.class.getResourceAsStream("Help"), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
//-m E0:3F:49:15:D5:AF -i 85.113.58.194