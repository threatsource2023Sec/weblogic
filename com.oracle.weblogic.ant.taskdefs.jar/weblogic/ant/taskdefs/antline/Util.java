package weblogic.ant.taskdefs.antline;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/** @deprecated */
@Deprecated
public class Util {
   public static String fileToString(String filename) throws ScriptException {
      try {
         InputStream fio = (new URL(filename)).openConnection().getInputStream();
         ByteArrayOutputStream bout = new ByteArrayOutputStream();

         int ch;
         while((ch = fio.read()) != -1) {
            bout.write(ch);
         }

         fio.close();
         return new String(bout.toByteArray(), "UTF-8");
      } catch (IOException var4) {
         throw new ScriptException("Failed to open include file " + filename);
      }
   }

   public static void stringToFile(String filename, String data) throws ScriptException {
      try {
         FileOutputStream out = new FileOutputStream(filename);
         out.write(data.getBytes());
         out.close();
      } catch (IOException var3) {
         throw new ScriptException("unable to write javascript file:" + filename);
      }
   }
}
