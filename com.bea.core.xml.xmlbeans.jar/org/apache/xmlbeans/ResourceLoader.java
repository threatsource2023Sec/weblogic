package org.apache.xmlbeans;

import java.io.InputStream;

public interface ResourceLoader {
   InputStream getResourceAsStream(String var1);

   void close();
}
