package weblogic.corba.idl;

import org.omg.CORBA.Any;
import org.omg.CORBA.NamedValue;

public class NamedValueImpl extends NamedValue {
   private Any nvalue;
   private String nname;
   private int nflags;

   public NamedValueImpl(String name, Any any, int flags) {
      this.nname = name;
      this.nflags = flags;
      this.nvalue = any;
   }

   public Any value() {
      return this.nvalue;
   }

   public String name() {
      return this.nname;
   }

   public int flags() {
      return this.nflags;
   }
}
