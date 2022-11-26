package weblogic.xml.xpath.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Context implements Cloneable {
   public List scratchList = new ArrayList();
   public Map scratchMap = new HashMap();
   public Object node;
   public int position = 0;
   public int size = 0;

   protected Context() {
   }

   protected Context(Object n) {
      this.node = n;
   }

   protected Context(Object n, int p, int s) {
      this.position = p;
      this.size = s;
      this.node = n;
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new IllegalStateException(var2.getMessage());
      }
   }
}
