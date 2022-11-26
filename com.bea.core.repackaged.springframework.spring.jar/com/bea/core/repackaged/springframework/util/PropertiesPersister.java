package com.bea.core.repackaged.springframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public interface PropertiesPersister {
   void load(Properties var1, InputStream var2) throws IOException;

   void load(Properties var1, Reader var2) throws IOException;

   void store(Properties var1, OutputStream var2, String var3) throws IOException;

   void store(Properties var1, Writer var2, String var3) throws IOException;

   void loadFromXml(Properties var1, InputStream var2) throws IOException;

   void storeToXml(Properties var1, OutputStream var2, String var3) throws IOException;

   void storeToXml(Properties var1, OutputStream var2, String var3, String var4) throws IOException;
}
