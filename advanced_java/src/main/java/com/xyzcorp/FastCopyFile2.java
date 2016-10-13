package com.xyzcorp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

/**
 * Created by danno on 10/12/16.
 */
public class FastCopyFile2 {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java FastCopyfile original dest");
            System.exit(1);
        }

        Path src = Paths.get(args[0]);
        Path target = Paths.get(args[1]);

        CopyOption[] copyOptions = new CopyOption[]{
                StandardCopyOption.COPY_ATTRIBUTES,
                StandardCopyOption.REPLACE_EXISTING
        };

        Files.copy(src, target, copyOptions);
    }


}
