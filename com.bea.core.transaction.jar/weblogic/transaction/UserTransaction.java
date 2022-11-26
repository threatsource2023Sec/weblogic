package weblogic.transaction;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

public interface UserTransaction extends javax.transaction.UserTransaction {
   void begin(String var1) throws NotSupportedException, SystemException;

   void begin(String var1, int var2) throws NotSupportedException, SystemException;
}
