package org.antlr.stringtemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class CommonGroupLoader extends PathGroupLoader {
   public CommonGroupLoader(StringTemplateErrorListener errors) {
      super(errors);
   }

   public CommonGroupLoader(String dirStr, StringTemplateErrorListener errors) {
      super(dirStr, errors);
   }

   protected BufferedReader locate(String name) throws IOException {
      for(int i = 0; i < this.dirs.size(); ++i) {
         String dir = (String)this.dirs.get(i);
         String fileName = dir + "/" + name;
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         InputStream is = cl.getResourceAsStream(fileName);
         if (is == null) {
            cl = this.getClass().getClassLoader();
            is = cl.getResourceAsStream(fileName);
         }

         if (is != null) {
            return new BufferedReader(this.getInputStreamReader(is));
         }
      }

      return null;
   }
}
