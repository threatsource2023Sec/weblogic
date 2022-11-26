package weblogic.persist;

import java.io.IOException;
import java.util.Enumeration;

public interface TxIndexedFile extends TxFile {
   int BATCH_SIZE = 10;

   void store(String var1, Object var2) throws IOException;

   Object retrieve(String var1);

   Enumeration keys();
}
