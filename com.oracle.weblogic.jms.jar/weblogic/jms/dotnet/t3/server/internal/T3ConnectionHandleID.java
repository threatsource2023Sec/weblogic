package weblogic.jms.dotnet.t3.server.internal;

import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;

public class T3ConnectionHandleID {
   private long value;
   private T3ConnectionHandle handle;
   private T3Connection connection;

   public long getValue() {
      return this.value;
   }

   public T3ConnectionHandleID(long id) {
      this.value = id;
   }

   public int hashCode() {
      return (int)(this.value >> 32 ^ this.value & -1L);
   }

   public T3Connection getConnection() {
      return this.connection;
   }

   public void setHandle(T3ConnectionHandle handle) {
      this.handle = handle;
   }

   public void setConnection(T3Connection connection) {
      this.connection = connection;
   }

   public T3ConnectionHandle getHandle() {
      return this.handle;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof T3ConnectionHandleID)) {
         return false;
      } else {
         return ((T3ConnectionHandleID)obj).value == this.value;
      }
   }
}
