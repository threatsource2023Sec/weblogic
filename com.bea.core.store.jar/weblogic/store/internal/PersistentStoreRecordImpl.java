package weblogic.store.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.nio.ByteBuffer;
import weblogic.store.ByteBufferObjectHandler;
import weblogic.store.CustomObjectInput;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.StoreLogger;
import weblogic.store.io.IORecord;

final class PersistentStoreRecordImpl implements PersistentStoreRecord {
   private final PersistentHandleImpl handle;
   private final PersistentStoreConnectionImpl connection;
   private final ByteBuffer[] data;
   private Object body;
   private final ObjectHandler handler;

   PersistentStoreRecordImpl(PersistentHandleImpl handle, ByteBuffer[] data, ObjectHandler handler, PersistentStoreConnectionImpl connection, boolean readOverride) throws PersistentStoreException {
      this.handle = handle;
      this.connection = connection;
      if (readOverride) {
         this.data = data;
         this.handler = handler;
      } else if (connection.deserializationDeferred()) {
         for(int i = 0; i < data.length; ++i) {
            ByteBuffer b = data[i];
            if (b != null) {
               ByteBuffer copyBuf = ByteBuffer.allocate(b.remaining());
               copyBuf.put(b);
               copyBuf.position(0);
               data[i] = copyBuf;
            }
         }

         this.data = data;
         this.handler = handler;
      } else {
         this.initBody(data, handler);
         this.data = null;
         this.handler = null;
      }

   }

   PersistentStoreRecordImpl(IORecord iorec, ObjectHandler handler, PersistentStoreConnectionImpl connection, boolean dynamicCursor) throws PersistentStoreException {
      this.handle = new PersistentHandleImpl(iorec.getTypeCode(), iorec.getHandle());
      ByteBuffer d = iorec.getData();
      this.connection = connection;
      if (dynamicCursor && connection.deserializationDeferred()) {
         if (d == null) {
            this.data = null;
         } else {
            ByteBuffer copyBuf = ByteBuffer.allocate(d.remaining());
            copyBuf.put(d);
            copyBuf.position(0);
            this.data = new ByteBuffer[]{copyBuf};
         }

         this.handler = handler;
      } else {
         this.initBody(d == null ? null : new ByteBuffer[]{d}, handler);
         this.data = null;
         this.handler = null;
      }

   }

   private void initBody(ByteBuffer[] data, ObjectHandler handler) throws PersistentStoreException {
      if (data != null) {
         if (handler instanceof ByteBufferObjectHandler) {
            ByteBuffer copy = ByteBuffer.allocate(data[0].remaining());
            copy.put(data[0]);
            copy.clear();
            this.body = copy;
         } else {
            try {
               Object objectInput;
               if (handler instanceof CustomObjectInput) {
                  objectInput = ((CustomObjectInput)handler).getObjectInput(data);
               } else {
                  objectInput = new PersistentStoreInputStreamImpl(data);
               }

               this.body = handler.readObject((ObjectInput)objectInput);
            } catch (IOException var5) {
               throw new PersistentStoreException(StoreLogger.logReadFailedLoggable(), var5);
            } catch (ClassNotFoundException var6) {
               throw new PersistentStoreException(StoreLogger.logReadFailedLoggable(), var6);
            }
         }
      }

   }

   public PersistentHandle getHandle() {
      return this.handle;
   }

   public Object getData() throws PersistentStoreException {
      if (this.body == null) {
         this.initBody(this.data, this.handler);
      }

      return this.body;
   }
}
