package com.bea.xbean.store;

public interface OurWstxConfig {
   void setMaxEntityCount(Integer var1);

   void configureForConvenience();

   void configureForLowMemUsage();

   void configureForRoundTripping();

   void configureForSpeed();

   void configureForXmlConformance();
}
