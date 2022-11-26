package weblogic.messaging.kernel;

import javax.transaction.xa.Xid;

public interface Event {
   String getSubjectName();

   Xid getXid();

   long getMilliseconds();

   long getNanoseconds();
}
