package weblogic.connector.outbound;

import javax.naming.Reference;
import javax.resource.spi.ManagedConnectionFactory;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.monitoring.outbound.FailedConnectionPoolRuntimeMBeanImpl;

class ConnectionFactoryMetaInfo {
   OutboundInfo outBoundInfo = null;
   ManagedConnectionFactory mcf = null;
   ConnectionPool pool = null;
   Reference ref = null;
   FailedConnectionPoolRuntimeMBeanImpl failPoolRunMBean = null;
}
