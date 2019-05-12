package ru.leshif.wol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class Main {
    public static void main(String[] args) {
        param param = null;
        WOL wol = new WOL();
        for(int i = 0;i<args.length;i++){
            try {
                switch (args[i]){
                    case "-m":
                        param = ru.leshif.wol.param.mac;
                        wol.setMac(args[i+1]);
                        i++;
                        break;
                    case "-i":
                        param = ru.leshif.wol.param.ip;
                        wol.setIp(args[i+1]);
                        i++;
                        break;
                    case "-p":
                        param = ru.leshif.wol.param.port;
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
                        param = ru.leshif.wol.param.network;
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
                    case "-h":
                        help();
                        System.exit(0);
                        break;
                    default:
                        Messages.throwExitMessage(args[i] + Messages.WRONG_OPTION, colour.red);
                        break;
                }
            }
            catch (Exception ex){
                Messages.throwExitMessage(Messages.WRONG_PARAM + param, colour.red);
            }
        }
        wol.wakeUp();
    }
    private static void help(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader (Main.class.getResourceAsStream(ProjectData.RESOURCE_HELP), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}