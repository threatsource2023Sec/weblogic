package weblogic.ejb.container.interfaces;

import java.util.Collection;
import weblogic.ejb.container.InternalException;

public interface InvalidationBeanManager extends BeanManager {
   void invalidate(Object var1, Object var2) throws InternalException;

   void invalidate(Object var1, Collection var2) throws InternalException;

   void invalidateAll(Object var1) throws InternalException;

   void invalidateLocalServer(Object var1, Object var2);

   void invalidateLocalServer(Object var1, Collection var2);

   void invalidateAllLocalServer(Object var1);
}
