package weblogic.ejb.spi;

public interface DynamicEJBModule {
   void setEjbDescriptorBean(EjbDescriptorBean var1);

   boolean deployDynamicEJB();

   boolean startDynamicEJB();

   void undeployDynamicEJB();
}
