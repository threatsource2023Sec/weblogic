package weblogic.servlet.internal.session.management;

import com.tangosol.coherence.servlet.management.HttpSessionManagerMBean;

public interface WebLogicHttpSessionManagerMBean extends HttpSessionManagerMBean {
   String OBJECT_NAME = "type=WebLogicHttpSessionManager";

   String getDomainName();

   String getServerName();

   Boolean isEar();

   String getName();

   String getVersion();

   String getApplicationId();

   String getListenAddress();

   Boolean isListenPortEnabled();

   Integer getListenPort();

   Boolean isSSLListenPortEnabled();

   Integer getSSLListenPort();
}
