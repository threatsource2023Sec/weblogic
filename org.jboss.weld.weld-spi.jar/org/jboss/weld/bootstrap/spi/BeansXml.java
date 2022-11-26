package org.jboss.weld.bootstrap.spi;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public interface BeansXml {
   BeansXml EMPTY_BEANS_XML = new BeansXml() {
      public List getEnabledInterceptors() {
         return Collections.emptyList();
      }

      public List getEnabledDecorators() {
         return Collections.emptyList();
      }

      public List getEnabledAlternativeStereotypes() {
         return Collections.emptyList();
      }

      public List getEnabledAlternativeClasses() {
         return Collections.emptyList();
      }

      public Scanning getScanning() {
         return Scanning.EMPTY_SCANNING;
      }

      public URL getUrl() {
         return null;
      }

      public BeanDiscoveryMode getBeanDiscoveryMode() {
         return BeanDiscoveryMode.ALL;
      }

      public String getVersion() {
         return null;
      }

      public boolean isTrimmed() {
         return false;
      }
   };

   List getEnabledAlternativeStereotypes();

   List getEnabledAlternativeClasses();

   List getEnabledDecorators();

   List getEnabledInterceptors();

   Scanning getScanning();

   URL getUrl();

   BeanDiscoveryMode getBeanDiscoveryMode();

   String getVersion();

   boolean isTrimmed();
}
