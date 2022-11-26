package weblogic.coherence.app.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ConfigurableCacheFactoryConfigBean extends SettableBean {
   String getClassName();

   void setClassName(String var1);

   InitParamsBean getInitParams();
}
