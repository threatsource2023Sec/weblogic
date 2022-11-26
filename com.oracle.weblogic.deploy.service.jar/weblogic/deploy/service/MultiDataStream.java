package weblogic.deploy.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public interface MultiDataStream {
   int getSize();

   Iterator getDataStreams();

   Iterator getInputStreams() throws IOException;

   void close();

   void addDataStream(DataStream var1);

   void addFileDataStream(String var1, File var2, boolean var3);

   void addFileDataStream(String var1, File var2, String var3, boolean var4);

   void addFileDataStream(String var1, boolean var2);

   void removeDataStream(DataStream var1);
}
