package javax.faces.component;

import java.io.Serializable;

public interface StateHelper extends StateHolder {
   Object put(Serializable var1, Object var2);

   Object remove(Serializable var1);

   Object put(Serializable var1, String var2, Object var3);

   Object get(Serializable var1);

   Object eval(Serializable var1);

   Object eval(Serializable var1, Object var2);

   void add(Serializable var1, Object var2);

   Object remove(Serializable var1, Object var2);
}
