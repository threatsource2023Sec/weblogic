package weblogic.management.provider.beaninfo;

public interface BeanInfoRegistration {
   void registerBeanInfoFactoryClass(String var1, ClassLoader var2);

   void discoverBeanInfoFactories(ClassLoader var1);
}
