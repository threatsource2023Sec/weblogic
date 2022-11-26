package weblogic.jms.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Response extends weblogic.messaging.dispatcher.Response implements Externalizable {
   static final long serialVersionUID = -4057384450154825617L;

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
