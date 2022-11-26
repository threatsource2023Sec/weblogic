package weblogic.corba.idl;

import java.util.ArrayList;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.TypeCode;

public class ExceptionListImpl extends ExceptionList {
   private ArrayList elems = new ArrayList();

   public int count() {
      return this.elems.size();
   }

   public void add(TypeCode typeCode) {
      this.elems.add(typeCode);
   }

   public void remove(int index) throws Bounds {
      try {
         this.elems.remove(index);
      } catch (IndexOutOfBoundsException var3) {
         throw new Bounds();
      }
   }

   public TypeCode item(int index) throws Bounds {
      try {
         return (TypeCode)this.elems.get(index);
      } catch (IndexOutOfBoundsException var3) {
         throw new Bounds();
      }
   }
}
