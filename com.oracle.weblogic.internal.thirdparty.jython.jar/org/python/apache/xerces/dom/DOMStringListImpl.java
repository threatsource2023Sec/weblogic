package org.python.apache.xerces.dom;

import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.DOMStringList;

public class DOMStringListImpl implements DOMStringList {
   private final ArrayList fStrings;

   public DOMStringListImpl() {
      this.fStrings = new ArrayList();
   }

   public DOMStringListImpl(ArrayList var1) {
      this.fStrings = var1;
   }

   public DOMStringListImpl(Vector var1) {
      this.fStrings = new ArrayList(var1);
   }

   public String item(int var1) {
      int var2 = this.getLength();
      return var1 >= 0 && var1 < var2 ? (String)this.fStrings.get(var1) : null;
   }

   public int getLength() {
      return this.fStrings.size();
   }

   public boolean contains(String var1) {
      return this.fStrings.contains(var1);
   }

   public void add(String var1) {
      this.fStrings.add(var1);
   }
}
