package org.python.core;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

public class PlainConsole implements Console {
   public final String encoding;
   public final Charset encodingCharset;

   public PlainConsole(String encoding) throws IllegalCharsetNameException, UnsupportedCharsetException {
      if (encoding == null) {
         encoding = Charset.defaultCharset().name();
      }

      this.encoding = encoding;
      this.encodingCharset = Charset.forName(encoding);
   }

   public void install() {
   }

   public void uninstall() throws UnsupportedOperationException {
      Class myClass = this.getClass();
      if (myClass != PlainConsole.class) {
         throw new UnsupportedOperationException(myClass.getSimpleName() + " console may not be uninstalled.");
      }
   }

   public String getEncoding() {
      return this.encoding;
   }

   public Charset getEncodingCharset() {
      return this.encodingCharset;
   }
}
