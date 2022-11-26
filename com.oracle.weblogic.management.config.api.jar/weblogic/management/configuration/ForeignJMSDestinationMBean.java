package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;

/** @deprecated */
@Deprecated
public interface ForeignJMSDestinationMBean extends ForeignJNDIObjectMBean {
   void setLocalJNDIName(String var1);

   String getLocalJNDIName();

   void setRemoteJNDIName(String var1);

   String getRemoteJNDIName();

   void useDelegates(ForeignDestinationBean var1);
}
