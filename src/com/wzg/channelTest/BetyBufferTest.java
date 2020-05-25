package com.wzg.channelTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BetyBufferTest {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("E:\\testDocument\\test_20200520.txt"));
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(200);
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(200);
            while (channel.read(byteBuffer) != -1){
                byteBuffer.clear();
                byteBuffer.compact();
//                byteBuffer.flip();
//                byte str = byteBuffer.get(0);
//                System.out.println(str);
//                byte[] bytes = {str};
//
//                System.out.println(new String(bytes));
//                byteBuffer.mark();
//                byteBuffer.position(1);
//                byteBuffer.limit(4);
//                System.out.println("bteBuffer position: "+ byteBuffer.position() +
//                                    " buyteBuffer limit: "+ byteBuffer.limit());
//                ByteBuffer sliceBufer= byteBuffer.asReadOnlyBuffer();
//                byte s = sliceBufer.get();
//                System.out.println(s);
//                System.out.println(sliceBufer.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
