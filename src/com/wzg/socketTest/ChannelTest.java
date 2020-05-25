package com.wzg.socketTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChannelTest {

    public static void main(String[] args) {
        System.out.println("-----start---" + new Date());
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("E:\\testDocument\\ZYD001_20200520_1234567896.txt"));
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(200);
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
            byte[] strbutes = null;
            int length = fileChannel.read(byteBuffer);
            List<String> stringList = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            while (length != -1) {
//                byteBuffer.flip();
                strbutes = new byte[byteBuffer.position()];
                byteBuffer.rewind();
                // 相对读，从position位置读取一个byte[]
                byteBuffer.get(strbutes);
                byteBuffer.clear();
                // 清楚缓冲区，供下次使用
                for (byte str : strbutes) {
                    if (str == 10) {
                        int position = byteBuffer1.position();
                        byte[] strbytes = new byte[position];
                        byteBuffer1.rewind();
                        // 从position开始读取byteLine.length个字节
                        byteBuffer1.get(strbytes);
                        String line = new String(strbytes, 0, strbytes.length - 1);
                        stringList.add(line);
                        sb.append(line);
                        byteBuffer1.clear();
                        continue;
                    }
                    byteBuffer1.put(str);
                }
//                System.out.println(new String(byteBuffer1.array()));
//                byteBuffer.clear();
                length = fileChannel.read(byteBuffer);
            }
            // 循环完毕，处理剩余数据,注释同上
            if (byteBuffer1.position() > 0) {
                int byteSize = byteBuffer1.position();
                byte[] byteLine = new byte[byteSize];
                byteBuffer1.rewind();
                byteBuffer1.get(byteLine);
                String line = new String(byteLine, 0, byteLine.length);
                stringList.add(line);
                byteBuffer1.clear();
            }
            System.out.println("-----end---" + new Date());
            System.out.println(stringList.size());
            System.out.println(sb);
//            System.out.println(new String(byteBuffer1.array()));
//            for (String str : stringList) {
//                System.out.println(str);
//            }
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
