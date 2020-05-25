package com.wzg.IPTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

    public static void main(String[] args) {
        InetAddress inetAddress;
        {
            try {
                //在给定主机名的情况下确定主机的IP地址,如果参数为null,获得的是本机的IP地址
                inetAddress = InetAddress.getByName("localhost");
                System.out.println(inetAddress.getHostAddress());
                System.out.println(inetAddress.getHostName());
                System.out.println(inetAddress.getCanonicalHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
