package com.wzg.channelTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public class ChannelTestOne {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("E:\\testDocument\\test_20200520.txt"));
            FileChannel channel = fileInputStream.getChannel();
            System.out.println(channel.size());
//            channel.position(5);
            channel.truncate(10l);
            ByteBuffer byteBuffer = ByteBuffer.allocate(200);
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(200);
            while(channel.read(byteBuffer) != -1){
                System.out.println("*************************************");
                System.out.println(channel.position());
                System.out.println("*************************************");
                byte[] strs = new byte[byteBuffer.position()];
                byteBuffer.rewind();
                byteBuffer.get(strs);
                for(byte str : strs){
                    if(str == 10){
                        int position = byteBuffer1.position();
                        byte[] strbytes = new byte[position];
                        byteBuffer1.rewind();
                        byteBuffer1.get(strbytes);
                        String line = new String(strbytes, 0, strbytes.length-1);
                        System.out.println(line);
                        byteBuffer1.clear();
                        continue;
                    }
                    byteBuffer1.put(str);
                }
            }
            byte[] strs = new byte[byteBuffer1.position()];
            byteBuffer1.rewind();
            byteBuffer1.get(strs);
            System.out.println(new String(strs));
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
