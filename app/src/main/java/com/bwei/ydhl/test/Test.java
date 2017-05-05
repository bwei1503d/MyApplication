package com.bwei.ydhl.test;

/**
 * Created by muhanxi on 17/4/9.
 */

public class Test {

    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuffer stringBuffer = new StringBuffer();

                long stringBufferstart = System.currentTimeMillis();
                for (int i = 0; i < 1000000; i++) {
                    stringBuffer.append("" + i);
                }
                long stringBufferend = System.currentTimeMillis();
                System.out.println("StringBuffer (end - start) = " + (stringBufferend - stringBufferstart));
//                System.out.println("s = " + stringBuffer.toString());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuilder stringBuilder = new StringBuilder();

                long stringBuilderstart = System.currentTimeMillis();
                for (int i = 0; i < 1000000; i++) {
                    stringBuilder.append("" + i);
                }
                long stringBuilderend = System.currentTimeMillis();
                System.out.println("StringBuilder (end - start) = " + (stringBuilderend - stringBuilderstart));
//                System.out.println("s = " + stringBuilder.toString());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                String s = "";

                long start = System.currentTimeMillis();
                for (int i = 0; i < 10000; i++) {
                    s = s + "" + i;
                }
                long end = System.currentTimeMillis();
                System.out.println("string (end - start) = " + (end - start));
//                System.out.println("s = " + s);



            }
        }).start();


    }


}
