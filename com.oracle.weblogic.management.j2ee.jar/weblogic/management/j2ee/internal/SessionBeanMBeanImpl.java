package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.StatefulSessionBeanMBean;

public class SessionBeanMBeanImpl extends EJBMBeanImpl implements StatefulSessionBeanMBean {
   public SessionBeanMBeanImpl(String name) {
      super(name);
   }
}
