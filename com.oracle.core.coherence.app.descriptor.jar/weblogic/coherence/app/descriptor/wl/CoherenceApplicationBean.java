package weblogic.coherence.app.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceApplicationBean extends SettableBean {
   String getCacheConfigurationRef();

   String getPofConfigurationRef();

   ApplicationLifecycleListenerBean getApplicationLifecycleListener();

   ConfigurableCacheFactoryConfigBean getConfigurableCacheFactoryConfig();
}
