package ru.internet.wol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static String _version = "version 1.0.0";

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
                        Messages.throwMessage(_version);
                        break;
                    case "-f":
                        wol.setFullMode();
                        break;
                    case "--help":
                        help();
                        System.exit(0);
                        break;
                    default:
                        Messages.throwMessage(args[i] + Messages.wrong_option);
                        break;
                }
            }
            catch (Exception ex){
                Messages.throwMessage(Messages.wrong_param + param);
            }
        }
        wol.wakeUp();
    }
    //todo Make HELP file/
    private static void help(){
        try {
            InputStreamReader fileReader = new InputStreamReader (Main.class.getResourceAsStream("Help"), "UTF-8");
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