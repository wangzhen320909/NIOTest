package com.wzg.socketTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(InetAddress.getByName("172.16.0.29"));
        System.out.println("****************************");
        InetAddress[] inetAddresses = InetAddress.getAllByName(inetAddress.getHostName());
        System.out.println(inetAddress);
        String[] strs = new String[inetAddresses.length];
        for(int i=0;i<inetAddresses.length;i++){
            strs[i] = inetAddresses[i].getHostAddress();
            System.out.println(inetAddresses[i]);
            System.out.println(inetAddresses[i].getHostAddress());
        }
        System.out.println(strs[0]);
    }
}
