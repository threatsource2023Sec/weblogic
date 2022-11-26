package weblogic.managedbean;

public interface ManagedBeanCreator {
   Object createInstance(String var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException;

   Object createInstance(Class var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException;

   void notifyPreDestroy(String var1, Object var2);

   void notifyPostConstruct(String var1, Object var2);

   void destroy();
}
