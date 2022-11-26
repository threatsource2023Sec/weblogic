package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.edit.EditSessionServiceMBean;
import weblogic.management.provider.EditAccess;

public final class EditSessionServiceMBeanImpl extends EditServiceMBeanImpl implements EditSessionServiceMBean {
   EditSessionServiceMBeanImpl(EditAccess edit, WLSModelMBeanContext context) {
      super("EditSessionService", EditSessionServiceMBean.class.getName(), edit, context);
   }
}
