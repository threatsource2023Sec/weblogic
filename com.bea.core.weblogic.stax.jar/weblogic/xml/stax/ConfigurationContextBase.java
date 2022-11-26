package weblogic.xml.stax;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.util.XMLEventAllocator;

public class ConfigurationContextBase {
   protected static final HashSet supportedFeatures = new HashSet();
   private static String EVENT_FILTER = "RI_EVENT_FILTER";
   private static String STREAM_FILTER = "RI_STREAM_FILTER";
   protected Hashtable features = new Hashtable();

   public ConfigurationContextBase() {
      supportedFeatures.add("javax.xml.stream.isValidating");
      supportedFeatures.add("javax.xml.stream.isCoalescing");
      supportedFeatures.add("javax.xml.stream.isReplacingEntityReferences");
      supportedFeatures.add("javax.xml.stream.isSupportingExternalEntities");
      supportedFeatures.add("javax.xml.stream.isNamespaceAware");
      supportedFeatures.add("javax.xml.stream.supportDTD");
      supportedFeatures.add("javax.xml.stream.reporter");
      supportedFeatures.add("javax.xml.stream.resolver");
      supportedFeatures.add("javax.xml.stream.allocator");
      supportedFeatures.add("weblogic.xml.stream.isEscapingCR");
      supportedFeatures.add("weblogic.xml.stream.isEscapingCRLFTAB");
      supportedFeatures.add("weblogic.xml.stax.MaxAttrsPerElement");
      supportedFeatures.add("weblogic.xml.stax.MaxChildElements");
      supportedFeatures.add("weblogic.xml.stax.MaxElementDepth");
      supportedFeatures.add("weblogic.xml.stax.MaxInputSize");
      supportedFeatures.add("weblogic.xml.stax.MaxTotalElements");
      supportedFeatures.add("weblogic.xml.stax.EnableAllLimitChecks");
      supportedFeatures.add("weblogic.xml.stax.EnableStartElementChecks");
      this.features.put("javax.xml.stream.isValidating", Boolean.FALSE);
      this.features.put("javax.xml.stream.isCoalescing", Boolean.TRUE);
      this.features.put("javax.xml.stream.isReplacingEntityReferences", Boolean.TRUE);
      this.features.put("javax.xml.stream.isSupportingExternalEntities", Boolean.FALSE);
      this.features.put("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
      this.features.put("javax.xml.stream.supportDTD", Boolean.FALSE);
      this.features.put("weblogic.xml.stax.MaxAttrsPerElement", 1000);
      this.features.put("weblogic.xml.stax.MaxChildElements", -1);
      this.features.put("weblogic.xml.stax.MaxElementDepth", 1000);
      this.features.put("weblogic.xml.stax.MaxInputSize", -1L);
      this.features.put("weblogic.xml.stax.MaxTotalElements", Long.MAX_VALUE);
      this.features.put("weblogic.xml.stax.EnableAllLimitChecks", true);
      this.features.put("weblogic.xml.stax.EnableStartElementChecks", true);
   }

   public void setEventAllocator(XMLEventAllocator a) {
      if (a != null) {
         this.features.put("javax.xml.stream.allocator", a);
      }

   }

   public XMLEventAllocator getEventAllocator() {
      return (XMLEventAllocator)this.features.get("javax.xml.stream.allocator");
   }

   public void setProperty(String name, Object feature) {
      this.check(name);
      this.features.put(name, feature);
   }

   public void check(String name) {
      if (!this.isSupported(name)) {
         throw new IllegalArgumentException("Unable to access unsupported property " + name);
      }
   }

   public boolean isSupported(String name) {
      return supportedFeatures.contains(name);
   }

   public Object getProperty(String name) {
      this.check(name);
      return this.features.get(name);
   }

   public void setXMLReporter(XMLReporter r) {
      if (r != null) {
         this.features.put("javax.xml.stream.reporter", r);
      }

   }

   public XMLReporter getXMLReporter() {
      return (XMLReporter)this.features.get("javax.xml.stream.reporter");
   }

   public void setXMLResolver(XMLResolver r) {
      if (r != null) {
         this.features.put("javax.xml.stream.resolver", r);
      }

   }

   public XMLResolver getXMLResolver() {
      return (XMLResolver)this.features.get("javax.xml.stream.resolver");
   }

   public boolean getBool(String name) {
      this.check(name);
      Boolean val = (Boolean)this.features.get(name);
      return val;
   }

   public void setBool(String name, boolean val) {
      this.check(name);
      this.features.put(name, new Boolean(val));
   }

   public void setCoalescing(boolean val) {
      this.setBool("javax.xml.stream.isCoalescing", val);
   }

   public boolean isCoalescing() {
      return this.getBool("javax.xml.stream.isCoalescing");
   }

   public void setValidating(boolean val) {
      this.setBool("javax.xml.stream.isValidating", val);
   }

   public boolean isValidating() {
      return this.getBool("javax.xml.stream.isValidating");
   }

   public void setReplacingEntities(boolean val) {
      this.setBool("javax.xml.stream.isReplacingEntityReferences", val);
   }

   public boolean isReplacingEntities() {
      return this.getBool("javax.xml.stream.isReplacingEntityReferences");
   }

   public void setSupportExternalEntities(boolean val) {
      this.setBool("javax.xml.stream.isSupportingExternalEntities", val);
   }

   public boolean isSupportingExternalEntities() {
      return this.getBool("javax.xml.stream.isSupportingExternalEntities");
   }

   public void setPrefixDefaulting(boolean val) {
      this.setBool("javax.xml.stream.isRepairingNamespaces", val);
   }

   public boolean isPrefixDefaulting() {
      return this.getBool("javax.xml.stream.isRepairingNamespaces");
   }

   public void setNamespaceAware(boolean val) {
      this.setBool("javax.xml.stream.isNamespaceAware", val);
   }

   public boolean isNamespaceAware() {
      return this.getBool("javax.xml.stream.isNamespaceAware");
   }

   public String getVersion() {
      return "1.0";
   }

   public Enumeration getProperties() {
      return this.features.keys();
   }

   public String toString() {
      return this.features.toString();
   }
}
