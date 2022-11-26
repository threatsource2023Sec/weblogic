package weblogic.security.net;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConnectionFilterService {
   boolean getConnectionFilterEnabled();

   ConnectionFilter getConnectionFilter();

   void setConnectionFilter(ConnectionFilter var1);

   boolean getConnectionLoggerEnabled();

   boolean getCompatibilityConnectionFiltersEnabled();
}
