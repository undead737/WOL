package ru.leshif.wol;

enum param{
    mac, ip, port, network
}

enum colour{
    white("[0m"),
    red("[31m"),
    green("[32m");

    private String value;

    colour(String value){
        this.value=value;
    }

    public String getColour(){
        return value;
    }
}

class ProjectData {
    static final String VERSION = "Version 1.0.0";
    static final String RESOURCE_HELP = "Help";
}
