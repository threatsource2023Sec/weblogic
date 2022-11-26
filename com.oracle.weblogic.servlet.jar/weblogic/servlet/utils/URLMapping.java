package weblogic.servlet.utils;

public interface URLMapping {
   void put(String var1, Object var2);

   Object get(String var1);

   void remove(String var1);

   void setDefault(Object var1);

   Object getDefault();

   void setCaseInsensitive(boolean var1);

   boolean isCaseInsensitive();

   void setExtensionCaseInsensitive(boolean var1);

   boolean isExtensionCaseInsensitive();

   int size();

   Object[] values();

   String[] keys();

   Object clone();
}
