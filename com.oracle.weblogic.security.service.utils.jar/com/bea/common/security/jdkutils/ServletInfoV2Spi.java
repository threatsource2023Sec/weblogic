package com.bea.common.security.jdkutils;

public interface ServletInfoV2Spi extends ServletInfoSpi {
   Object getSAML2ServletFilterService();

   Object getLoginSessionService();

   Object getIdentityService();
}
