package weblogic.security.service;

public interface ContextHandler {
   int size();

   String[] getNames();

   Object getValue(String var1);

   ContextElement[] getValues(String[] var1);
}
