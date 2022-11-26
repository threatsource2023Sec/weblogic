package org.mozilla.javascript;

public interface Scriptable {
   Object NOT_FOUND = new Object();

   void delete(int var1);

   void delete(String var1);

   Object get(int var1, Scriptable var2);

   Object get(String var1, Scriptable var2);

   String getClassName();

   Object getDefaultValue(Class var1);

   Object[] getIds();

   Scriptable getParentScope();

   Scriptable getPrototype();

   boolean has(int var1, Scriptable var2);

   boolean has(String var1, Scriptable var2);

   boolean hasInstance(Scriptable var1);

   void put(int var1, Scriptable var2, Object var3);

   void put(String var1, Scriptable var2, Object var3);

   void setParentScope(Scriptable var1);

   void setPrototype(Scriptable var1);
}
