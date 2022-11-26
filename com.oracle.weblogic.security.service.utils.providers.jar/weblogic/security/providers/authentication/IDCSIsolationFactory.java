package weblogic.security.providers.authentication;

import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.spi.IdentityAsserterV2;

public interface IDCSIsolationFactory {
   IDCSConfiguration getIDCSConfiguration(Object var1, LoggerSpi var2);

   IDCSAtnDelegate getDelegate(LoggerSpi var1, IDCSConfiguration var2);

   IdentityAsserterV2 getIdentityAsserter(LoggerSpi var1, IDCSConfiguration var2);

   void postInitialize(IDCSConfiguration var1, IDCSAtnDelegate var2);
}
