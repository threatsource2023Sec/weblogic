package weblogic.corba.idl;

import java.util.ArrayList;
import org.omg.CORBA.Any;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;

public class NVListImpl extends NVList {
   private ArrayList elems;

   public NVListImpl(int count) {
      this.elems = new ArrayList(count);
   }

   public int count() {
      return this.elems.size();
   }

   public NamedValue add(int flags) {
      NamedValue nv = new NamedValueImpl("", new AnyImpl(), flags);
      this.elems.add(nv);
      return nv;
   }

   public void remove(int index) throws Bounds {
      try {
         this.elems.remove(index);
      } catch (IndexOutOfBoundsException var3) {
         throw new Bounds();
      }
   }

   public NamedValue add_item(String name, int flags) {
      NamedValue nv = new NamedValueImpl(name, new AnyImpl(), flags);
      this.elems.add(nv);
      return nv;
   }

   public NamedValue add_value(String name, Any any, int flags) {
      NamedValue nv = new NamedValueImpl(name, any, flags);
      this.elems.add(nv);
      return nv;
   }

   public NamedValue item(int index) throws Bounds {
      try {
         return (NamedValue)this.elems.get(index);
      } catch (IndexOutOfBoundsException var3) {
         throw new Bounds();
      }
   }
}
