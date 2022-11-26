package weblogic.store.io;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;

public interface PersistentStoreIO {
   boolean exists(Map var1) throws PersistentStoreException;

   /** @deprecated */
   @Deprecated
   int open(StoreWritePolicy var1, int var2) throws PersistentStoreException;

   int open(HashMap var1) throws PersistentStoreException;

   void close() throws PersistentStoreException;

   boolean supportsFastReads();

   boolean supportsAsyncIO();

   int getPreferredFlushLoadSize();

   int getWorkerCount();

   boolean isIdle();

   int allocateHandle(int var1);

   void ensureHandleAllocated(int var1, int var2);

   void releaseHandle(int var1, int var2);

   void create(int var1, int var2, ByteBuffer[] var3, int var4) throws PersistentStoreException;

   boolean isHandleReadable(int var1, int var2);

   IORecord read(int var1, int var2) throws PersistentStoreException;

   void update(int var1, int var2, ByteBuffer[] var3, int var4) throws PersistentStoreException;

   void delete(int var1, int var2, int var3) throws PersistentStoreException;

   int drop(int var1) throws PersistentStoreException;

   int getNumObjects(int var1) throws PersistentStoreException;

   void flush() throws PersistentStoreException;

   void flush(IOListener var1) throws PersistentStoreException;

   Cursor createCursor(int var1, int var2) throws PersistentStoreException;

   void dump(XMLStreamWriter var1) throws XMLStreamException;

   void dump(XMLStreamWriter var1, int var2) throws XMLStreamException;

   void setTestException(PersistentStoreException var1);

   void prepareToClose();

   public interface Cursor {
      IORecord next() throws PersistentStoreException;
   }
}
