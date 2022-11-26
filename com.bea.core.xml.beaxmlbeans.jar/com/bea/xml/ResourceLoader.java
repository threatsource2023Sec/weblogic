package com.bea.xml;

import java.io.InputStream;

public interface ResourceLoader {
   InputStream getResourceAsStream(String var1);

   void close();
}
