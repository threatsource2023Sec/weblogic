package weblogic.ejb;

import java.util.Collection;
import javax.ejb.EJBLocalHome;

public interface CachingLocalHome extends EJBLocalHome {
   void invalidate(Object var1);

   void invalidate(Collection var1);

   void invalidateAll();

   void invalidateLocalServer(Object var1);

   void invalidateLocalServer(Collection var1);

   void invalidateAllLocalServer();
}
