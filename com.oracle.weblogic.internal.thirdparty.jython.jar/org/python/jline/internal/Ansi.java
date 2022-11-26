package org.python.jline.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.fusesource.jansi.AnsiOutputStream;

public class Ansi {
   public static String stripAnsi(String str) {
      if (str == null) {
         return "";
      } else {
         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AnsiOutputStream aos = new AnsiOutputStream(baos);
            aos.write(str.getBytes());
            aos.close();
            return baos.toString();
         } catch (IOException var3) {
            return str;
         }
      }
   }
}
