package weblogic.ejb.spi;

import java.util.Collection;

public interface BeanInfo {
   String getEJBName();

   Class getBeanClass();

   boolean hasResourceRefs();

   Collection getAllEnvironmentEntries();

   void registerInjector(Injector var1);
}
