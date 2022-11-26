package weblogic.connector.external;

import java.util.Hashtable;
import java.util.List;
import weblogic.connector.exception.RAOutboundException;
import weblogic.j2ee.descriptor.wl.LoggingBean;

public interface OutboundInfo {
   String UNKNOWN_MCF_CLASS = "[unknown MCF class]";

   String getRADescription();

   RAInfo getRAInfo();

   String getDisplayName();

   String getVendorName();

   String getEisType();

   String getTransactionSupport();

   List getAuthenticationMechanisms();

   boolean getReauthenticationSupport();

   String getMCFClass() throws RAOutboundException;

   Hashtable getMCFProps() throws RAOutboundException;

   String getCFInterface();

   String getCFImpl() throws RAOutboundException;

   String getConnectionInterface() throws RAOutboundException;

   String getConnectionImpl() throws RAOutboundException;

   String getConnectionFactoryName();

   String getJndiName();

   int getInitialCapacity();

   int getMaxCapacity();

   int getCapacityIncrement();

   boolean isShrinkingEnabled();

   int getShrinkFrequencySeconds();

   int getInactiveConnectionTimeoutSeconds();

   boolean getConnectionProfilingEnabled();

   int getHighestNumWaiters();

   int getHighestNumUnavailable();

   int getConnectionCreationRetryFrequencySeconds();

   int getConnectionReserveTimeoutSeconds();

   int getTestFrequencySeconds();

   boolean isTestConnectionsOnCreate();

   boolean isTestConnectionsOnRelease();

   boolean isTestConnectionsOnReserve();

   Boolean getUseConnectionProxies();

   String getRaLinkRef();

   boolean isMatchConnectionsSupported();

   boolean isUseFirstAvailable();

   void setBaseOutboundInfo(OutboundInfo var1);

   LoggingBean getLoggingBean();

   int getProfileHarvestFrequencySeconds();

   boolean isIgnoreInUseConnectionsEnabled();

   String getLogFilename();

   boolean isLoggingEnabled();

   String getRotationType();

   String getRotationTime();

   boolean isNumberOfFilesLimited();

   int getFileCount();

   int getFileSizeLimit();

   int getFileTimeSpan();

   boolean isRotateLogOnStartup();

   String getLogFileRotationDir();

   String getDateFormatPattern();

   String getResAuth();

   String getDescription();

   String getKey();
}
