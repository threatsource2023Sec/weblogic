package weblogic.jms.integration.injection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.transaction.TransactionScoped;

@TransactionScoped
public class TransactedJMSContextManager extends AbstractJMSContextManager implements Serializable {
   private static final long serialVersionUID = -6849779741809763537L;

   private void writeObject(ObjectOutputStream out) throws IOException {
      throw new IOException("Class cannot be serialized");
   }
}
