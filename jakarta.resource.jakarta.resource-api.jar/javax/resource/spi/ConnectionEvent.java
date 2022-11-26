package javax.resource.spi;

import java.util.EventObject;

public class ConnectionEvent extends EventObject {
   public static final int CONNECTION_CLOSED = 1;
   public static final int LOCAL_TRANSACTION_STARTED = 2;
   public static final int LOCAL_TRANSACTION_COMMITTED = 3;
   public static final int LOCAL_TRANSACTION_ROLLEDBACK = 4;
   public static final int CONNECTION_ERROR_OCCURRED = 5;
   private Exception exception;
   protected int id;
   private Object connectionHandle;

   public ConnectionEvent(ManagedConnection source, int eid) {
      super(source);
      this.id = eid;
   }

   public ConnectionEvent(ManagedConnection source, int eid, Exception exception) {
      super(source);
      this.exception = exception;
      this.id = eid;
   }

   public Object getConnectionHandle() {
      return this.connectionHandle;
   }

   public void setConnectionHandle(Object connectionHandle) {
      this.connectionHandle = connectionHandle;
   }

   public Exception getException() {
      return this.exception;
   }

   public int getId() {
      return this.id;
   }
}
