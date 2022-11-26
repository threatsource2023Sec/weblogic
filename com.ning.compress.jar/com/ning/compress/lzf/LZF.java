package com.ning.compress.lzf;

import com.ning.compress.lzf.util.LZFFileInputStream;
import com.ning.compress.lzf.util.LZFFileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZF {
   public static final String SUFFIX = ".lzf";

   protected void process(String[] args) throws IOException {
      if (args.length == 2) {
         String oper = args[0];
         boolean compress = "-c".equals(oper);
         boolean toSystemOutput = !compress && "-o".equals(oper);
         if (compress || toSystemOutput || "-d".equals(oper)) {
            String filename = args[1];
            File src = new File(filename);
            if (!src.exists()) {
               System.err.println("File '" + filename + "' does not exist.");
               System.exit(1);
            }

            if (!compress && !filename.endsWith(".lzf")) {
               System.err.println("File '" + filename + "' does end with expected suffix ('" + ".lzf" + "', won't decompress.");
               System.exit(1);
            }

            if (compress) {
               int inputLength = 0;
               File resultFile = new File(filename + ".lzf");
               InputStream in = new FileInputStream(src);
               OutputStream out = new LZFFileOutputStream(resultFile);
               byte[] buffer = new byte[8192];

               int bytesRead;
               while((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                  inputLength += bytesRead;
                  out.write(buffer, 0, bytesRead);
               }

               in.close();
               out.flush();
               out.close();
               System.out.printf("Compressed '%s' into '%s' (%d->%d bytes)\n", src.getPath(), resultFile.getPath(), inputLength, resultFile.length());
            } else {
               LZFFileInputStream in = new LZFFileInputStream(src);
               File resultFile = null;
               Object out;
               if (toSystemOutput) {
                  out = System.out;
               } else {
                  resultFile = new File(filename.substring(0, filename.length() - ".lzf".length()));
                  out = new FileOutputStream(resultFile);
               }

               int uncompLen = in.readAndWrite((OutputStream)out);
               in.close();
               ((OutputStream)out).flush();
               ((OutputStream)out).close();
               if (resultFile != null) {
                  System.out.printf("Uncompressed '%s' into '%s' (%d->%d bytes)\n", src.getPath(), resultFile.getPath(), src.length(), uncompLen);
               }
            }

            return;
         }
      }

      System.err.println("Usage: java " + this.getClass().getName() + " -c/-d/-o source-file");
      System.err.println(" -d parameter: decompress to file");
      System.err.println(" -c parameter: compress to file");
      System.err.println(" -o parameter: decompress to stdout");
      System.exit(1);
   }

   public static void main(String[] args) throws IOException {
      (new LZF()).process(args);
   }
}
