package weblogic.ejb.container.swap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanState {
   private final Map fieldValues = new HashMap();

   public Object get(Field f) {
      return this.fieldValues.get(f);
   }

   public void update(Field f, Object newv) {
      this.fieldValues.put(f, newv);
   }

   boolean isEmpty() {
      return this.fieldValues.isEmpty();
   }

   BeanState cloneBeanState() {
      BeanState bs = new BeanState();
      bs.fieldValues.putAll(this.fieldValues);
      return bs;
   }

   void copyBeanState(BeanState bs) {
      this.fieldValues.clear();
      this.fieldValues.putAll(bs.fieldValues);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.fieldValues.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         sb.append(e.getKey()).append("-->").append(e.getValue()).append("\n");
      }

      return sb.toString();
   }
}
