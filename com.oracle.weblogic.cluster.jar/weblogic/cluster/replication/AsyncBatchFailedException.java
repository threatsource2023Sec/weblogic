package weblogic.cluster.replication;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.rmi.RemoteException;

public class AsyncBatchFailedException extends RemoteException implements Serializable {
   static final long serialVersionUID = -1L;
   private ROID[] ids;

   public AsyncBatchFailedException(ROID[] ids) {
      this.ids = ids;
   }

   public ROID[] getIDs() {
      return this.ids;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.ids);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.ids = (ROID[])((ROID[])in.readObject());
   }
}
