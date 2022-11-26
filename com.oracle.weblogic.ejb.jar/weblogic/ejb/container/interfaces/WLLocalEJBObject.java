package weblogic.ejb.container.interfaces;

import weblogic.ejb.container.internal.BaseLocalObject;
import weblogic.rmi.LocalObject;

public interface WLLocalEJBObject extends LocalObject {
   BaseLocalObject getWLLocalObject();

   Object getWLPrimaryKey();
}
