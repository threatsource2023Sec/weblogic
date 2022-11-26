package weblogic.transaction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface TransactionLoggable {
   void readExternal(DataInput var1) throws IOException;

   void writeExternal(DataOutput var1) throws IOException;

   void onDisk(TransactionLogger var1);

   void onError(TransactionLogger var1);

   void onRecovery(TransactionLogger var1);
}
