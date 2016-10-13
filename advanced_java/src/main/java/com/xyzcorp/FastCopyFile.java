package com.xyzcorp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by danno on 10/12/16.
 */
public class FastCopyFile {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java FastCopyfile original dest");
            System.exit(1);
        }

        String infile = args[0];
        String outfile = args[1];
        try (
                FileInputStream fileInputStream = new FileInputStream(infile);
                FileOutputStream fileOutputStream = new FileOutputStream(outfile);

                FileChannel inChannel = fileInputStream.getChannel();
                FileChannel outChannel = fileOutputStream.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                buffer.clear();
                int r = inChannel.read(buffer);
                if (r == -1) break;

                buffer.flip();
                outChannel.write(buffer);
            }
        }
    }
}
