package weblogic.transaction;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

public interface BeginNotificationListener {
   void beginNotification(Object var1) throws NotSupportedException, SystemException;
}
