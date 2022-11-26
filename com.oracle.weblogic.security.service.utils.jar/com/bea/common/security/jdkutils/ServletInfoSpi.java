package com.bea.common.security.jdkutils;

public interface ServletInfoSpi {
   Object getLogger(String var1);

   Object getNegotiateFilterService();

   Object getSAMLServletFilterService();
}
