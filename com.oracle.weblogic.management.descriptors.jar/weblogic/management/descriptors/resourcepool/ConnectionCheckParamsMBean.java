package weblogic.management.descriptors.resourcepool;

import weblogic.management.descriptors.XMLElementMBean;

public interface ConnectionCheckParamsMBean extends XMLElementMBean {
   int DEFAULT_INACTIVE_TIMEOUT_SECONDS = 0;
   int DEFAULT_CREATION_RETRY_FREQUENCY_SECONDS = 0;
   int DEFAULT_RESERVE_TIMEOUT_SECONDS = 10;
   int DEFAULT_TEST_FREQUENCY_SECONDS = 0;
   boolean DEFAULT_CHECK_ON_CREATE_ENABLED = false;
   boolean DEFAULT_CHECK_ON_RELEASE_ENABLED = false;
   boolean DEFAULT_CHECK_ON_RESERVE_ENABLED = false;

   boolean isCheckOnReserveEnabled();

   void setCheckOnReserveEnabled(boolean var1);

   boolean isCheckOnReleaseEnabled();

   void setCheckOnReleaseEnabled(boolean var1);

   boolean isCheckOnCreateEnabled();

   void setCheckOnCreateEnabled(boolean var1);

   int getConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   int getConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(int var1);

   int getInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(int var1);

   int getTestFrequencySeconds();

   void setTestFrequencySeconds(int var1);
}
