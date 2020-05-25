package com.wzg.channelTest;

import java.nio.ByteBuffer;

public class ByteBufferCompTest {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for(int i=0;i<=9;i++){
            byteBuffer.put((byte) (48 +i));
        }
        System.out.println(new String(byteBuffer.array()));

        byteBuffer.position(5);
        byteBuffer.compact();
        System.out.println(new String(byteBuffer.array()));
        System.out.println(byteBuffer.toString());
    }
}
