package weblogic.ejb.container.interfaces;

import javax.ejb.EJBLocalObject;
import weblogic.ejb20.interfaces.LocalHandle;

public interface BaseEJBLocalObjectIntf extends EJBLocalObject {
   LocalHandle getLocalHandle();
}
