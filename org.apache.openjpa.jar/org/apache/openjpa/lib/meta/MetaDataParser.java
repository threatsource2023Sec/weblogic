package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;

public interface MetaDataParser {
   void setClassLoader(ClassLoader var1);

   List getResults();

   void parse(String var1) throws IOException;

   void parse(URL var1) throws IOException;

   void parse(File var1) throws IOException;

   void parse(Class var1, boolean var2) throws IOException;

   void parse(Reader var1, String var2) throws IOException;

   void parse(MetaDataIterator var1) throws IOException;

   void clear();
}
