package weblogic.ejb.container.timer;

import java.io.Serializable;
import weblogic.store.PersistentHandle;
import weblogic.store.gxa.GXid;

public final class PrepareRecord implements Serializable {
   public static final int CREATE = 1;
   public static final int CANCEL = 2;
   public static final int TIMEOUT = 3;
   public static final int TIMEOUT_CANCEL = 4;
   public Long timerID;
   public int operation;
   public GXid xid;
   public PersistentHandle handle;

   public PrepareRecord(Long timerID, int operation, GXid xid) {
      this.timerID = timerID;
      this.operation = operation;
      this.xid = xid;
   }
}
