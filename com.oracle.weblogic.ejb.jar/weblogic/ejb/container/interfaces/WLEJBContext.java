package weblogic.ejb.container.interfaces;

import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;

public interface WLEJBContext extends weblogic.ejb.spi.WLEJBContext {
   void setBean(Object var1);

   void setEJBObject(EJBObject var1);

   void setEJBLocalObject(EJBLocalObject var1);
}
