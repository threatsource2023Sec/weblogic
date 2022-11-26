package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.ServiceRefBean;

public interface WeblogicWseeStandaloneclientBean {
   ServiceRefBean getServiceRef();

   ServiceRefBean createServiceRef();

   void destroyServiceRef(ServiceRefBean var1);

   String getVersion();

   void setVersion(String var1);
}
