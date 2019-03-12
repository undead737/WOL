package ru.internet.wol;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        WOL wol = new WOL();
        //todo make norm switch
        for(int i = 0;i<args.length;i++){
            switch (args[i]){
                case "-m":
                    wol.setMac(args[i+1]);
                    break;
                case "-i":
                    wol.setIp(args[i+1]);
                    break;
                case "-p":
                    wol.setPort(args[i+1]);
                    break;
                case "--help":
                    help();
                    System.exit(0);
                    break;
            }
        }
        wol.wakeUp();
    }

    private static void help(){
        try {
            //todo Make HELP file/
            List<String> lines =  Files.readAllLines(Paths.get("./ru/internet/wol/Help"));
            for(String line : lines){
                System.out.println(line);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
//-m E0:3F:49:15:D5:AF -i 85.113.58.194