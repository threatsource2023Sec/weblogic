package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.portable.IDLEntity;

public final class WNameComponent implements IDLEntity {
   public String id = null;
   public String kind = null;

   public WNameComponent() {
   }

   public WNameComponent(String _id, String _kind) {
      this.id = _id;
      this.kind = _kind;
   }
}
