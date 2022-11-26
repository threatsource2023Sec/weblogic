package org.python.jline.internal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Urls {
   public static URL create(String input) {
      if (input == null) {
         return null;
      } else {
         try {
            return new URL(input);
         } catch (MalformedURLException var2) {
            return create(new File(input));
         }
      }
   }

   public static URL create(File file) {
      try {
         return file != null ? file.toURI().toURL() : null;
      } catch (MalformedURLException var2) {
         throw new IllegalStateException(var2);
      }
   }
}
