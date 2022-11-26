package weblogic.apache.xerces.dom;

import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

public class DOMImplementationListImpl implements DOMImplementationList {
   private final ArrayList fImplementations;

   public DOMImplementationListImpl() {
      this.fImplementations = new ArrayList();
   }

   public DOMImplementationListImpl(ArrayList var1) {
      this.fImplementations = var1;
   }

   public DOMImplementationListImpl(Vector var1) {
      this.fImplementations = new ArrayList(var1);
   }

   public DOMImplementation item(int var1) {
      int var2 = this.getLength();
      return var1 >= 0 && var1 < var2 ? (DOMImplementation)this.fImplementations.get(var1) : null;
   }

   public int getLength() {
      return this.fImplementations.size();
   }
}
