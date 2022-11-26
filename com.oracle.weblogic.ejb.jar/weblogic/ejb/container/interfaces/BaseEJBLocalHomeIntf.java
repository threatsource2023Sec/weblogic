package weblogic.ejb.container.interfaces;

import javax.ejb.EJBLocalHome;
import weblogic.ejb20.interfaces.LocalHomeHandle;

public interface BaseEJBLocalHomeIntf extends BaseEJBHomeIntf, EJBLocalHome {
   BaseEJBLocalObjectIntf allocateELO(Object var1);

   BaseEJBLocalObjectIntf allocateELO();

   LocalHomeHandle getLocalHomeHandle();
}
