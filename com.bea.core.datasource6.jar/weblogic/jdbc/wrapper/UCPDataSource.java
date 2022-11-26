package weblogic.jdbc.wrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import weblogic.jdbc.common.internal.UCPDataSourceManager;

public class UCPDataSource extends AbstractDataSource implements Referenceable {
   private static final long serialVersionUID = 5705424218240300786L;
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
