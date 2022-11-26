package org.apache.xmlbeans.impl.store;

public interface OurWstxConfig {
   void setMaxEntityCount(Integer var1);

   void configureForConvenience();

   void configureForLowMemUsage();

   void configureForRoundTripping();

   void configureForSpeed();

   void configureForXmlConformance();
}
