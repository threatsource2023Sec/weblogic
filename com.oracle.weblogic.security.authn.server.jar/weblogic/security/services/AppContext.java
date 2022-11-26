package weblogic.security.services;

public interface AppContext {
   int size();

   String[] getNames();

   AppContextElement getElement(String var1);

   AppContextElement[] getElements(String[] var1);
}
