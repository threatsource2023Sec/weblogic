package org.apache.velocity.runtime.resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import org.apache.velocity.exception.ResourceNotFoundException;

public class ContentResource extends Resource {
   public boolean process() throws ResourceNotFoundException {
      BufferedReader reader = null;

      boolean len;
      try {
         StringWriter sw = new StringWriter();
         reader = new BufferedReader(new InputStreamReader(super.resourceLoader.getResourceStream(super.name), super.encoding));
         char[] buf = new char[1024];
         len = false;

         int len;
         while((len = reader.read(buf, 0, 1024)) != -1) {
            sw.write(buf, 0, len);
         }

         this.setData(sw.toString());
         boolean var5 = true;
         return var5;
      } catch (ResourceNotFoundException var16) {
         throw var16;
      } catch (Exception var17) {
         super.rsvc.error("Cannot process content resource : " + var17.toString());
         len = false;
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (Exception var15) {
            }
         }

      }

      return len;
   }
}
