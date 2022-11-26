package weblogic.jdbc.wrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import weblogic.jdbc.common.internal.UCPDataSourceManager;

public class UCPXADataSource extends AbstractXADataSource implements Referenceable {
   private static final long serialVersionUID = 6600299559662335454L;
   private String referenceKey;

   public void setReferenceKey(String refKey) {
      this.referenceKey = refKey;
   }

   public Reference getReference() throws NamingException {
      Reference reference = new Reference(this.getClass().getName(), UCPDataSourceManager.class.getCanonicalName(), (String)null);
      reference.add(new StringRefAddr("key", this.referenceKey));
      return reference;
   }
}
