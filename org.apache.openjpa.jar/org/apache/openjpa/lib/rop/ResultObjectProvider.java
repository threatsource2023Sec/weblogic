package org.apache.openjpa.lib.rop;

import org.apache.openjpa.lib.util.Closeable;

public interface ResultObjectProvider extends Closeable {
   boolean supportsRandomAccess();

   void open() throws Exception;

   Object getResultObject() throws Exception;

   boolean next() throws Exception;

   boolean absolute(int var1) throws Exception;

   int size() throws Exception;

   void reset() throws Exception;

   void close() throws Exception;

   void handleCheckedException(Exception var1);
}
