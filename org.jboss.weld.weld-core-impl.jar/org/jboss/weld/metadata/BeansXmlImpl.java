package org.jboss.weld.metadata;

import java.net.URL;
import java.util.List;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Scanning;

public class BeansXmlImpl implements BeansXml {
   private final List enabledAlternatives;
   private final List enabledAlternativeStereotypes;
   private final List enabledDecorators;
   private final List enabledInterceptors;
   private final Scanning scanning;
   private final URL url;
   private final BeanDiscoveryMode discoveryMode;
   private final String version;
   private final boolean isTrimmed;

   public BeansXmlImpl(List enabledAlternatives, List enabledAlternativeStereotypes, List enabledDecorators, List enabledInterceptors, Scanning scanning, URL url, BeanDiscoveryMode discoveryMode, String version, boolean isTrimmed) {
      this.enabledAlternatives = enabledAlternatives;
      this.enabledAlternativeStereotypes = enabledAlternativeStereotypes;
      this.enabledDecorators = enabledDecorators;
      this.enabledInterceptors = enabledInterceptors;
      this.scanning = scanning == null ? Scanning.EMPTY_SCANNING : scanning;
      this.url = url;
      this.discoveryMode = discoveryMode;
      this.version = version;
      this.isTrimmed = isTrimmed;
   }

   public List getEnabledAlternativeClasses() {
      return this.enabledAlternatives;
   }

   public List getEnabledAlternativeStereotypes() {
      return this.enabledAlternativeStereotypes;
   }

   public List getEnabledDecorators() {
      return this.enabledDecorators;
   }

   public List getEnabledInterceptors() {
      return this.enabledInterceptors;
   }

   public Scanning getScanning() {
      return this.scanning;
   }

   public URL getUrl() {
      return this.url;
   }

   public BeanDiscoveryMode getBeanDiscoveryMode() {
      return this.discoveryMode;
   }

   public String getVersion() {
      return this.version;
   }

   public boolean isTrimmed() {
      return this.isTrimmed;
   }
}
