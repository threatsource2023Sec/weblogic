package weblogic.utils.jars;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JarMaker {
   public static void main(String[] args) throws Exception {
      try {
         if (args.length < 2) {
            throw new ArrayIndexOutOfBoundsException("Not enough arguments");
         }

         ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(args[args.length - 1]));

         for(int i = 0; i < args.length - 1; ++i) {
            BufferedReader br = new BufferedReader(new FileReader(args[i]));
            byte[] bytes = new byte[8192];

            String line;
            while((line = br.readLine()) != null) {
               line = line.trim();
               ZipEntry ze = new ZipEntry(line);
               zos.putNextEntry(ze);
               InputStream is = JarMaker.class.getClassLoader().getResourceAsStream(line);
               if (is == null) {
                  System.err.println("Warning: File " + line + " not found in classpath");
               } else {
                  int read;
                  while((read = is.read(bytes, 0, bytes.length)) != -1) {
                     zos.write(bytes, 0, read);
                  }

                  is.close();
               }
            }
         }

         zos.close();
      } catch (ArrayIndexOutOfBoundsException var9) {
         System.err.println("Usage: weblogic.utils.jars.JarMaker [list of files filename] [jar file name]");
      }

   }
}
