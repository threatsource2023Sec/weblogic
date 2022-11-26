package com.bea.xml.stream;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.util.XMLEventAllocator;

public class ConfigurationContextBase {
   private static HashSet supportedFeatures = new HashSet();
   private static String EVENT_FILTER = "RI_EVENT_FILTER";
   private static String STREAM_FILTER = "RI_STREAM_FILTER";
   private static String NOTATIONS = "javax.xml.stream.notations";
   private static String ENTITIES = "javax.xml.stream.entities";
   private Hashtable features = new Hashtable();

   public ConfigurationContextBase() {
      this.features.put("javax.xml.stream.isValidating", Boolean.FALSE);
      this.features.put("javax.xml.stream.isCoalescing", Boolean.FALSE);
      this.features.put("javax.xml.stream.isReplacingEntityReferences", Boolean.TRUE);
      this.features.put("javax.xml.stream.isSupportingExternalEntities", Boolean.FALSE);
      this.features.put("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
      this.features.put("javax.xml.stream.supportDTD", Boolean.FALSE);
      this.features.put("javax.xml.stream.isRepairingNamespaces", Boolean.FALSE);
   }

   public void setEventAllocator(XMLEventAllocator a) {
      this.features.put("javax.xml.stream.allocator", a);
   }

   public XMLEventAllocator getEventAllocator() {
      return (XMLEventAllocator)this.features.get("javax.xml.stream.allocator");
   }

   public void setProperty(String name, Object feature) {
      this.check(name);
      if (name.equals("javax.xml.stream.isValidating") && Boolean.TRUE.equals(feature)) {
         throw new IllegalArgumentException("This implementation does not support validation");
      } else if (name.equals("javax.xml.stream.isSupportingExternalEntities") && Boolean.TRUE.equals(feature)) {
         throw new IllegalArgumentException("This implementation does not resolve external entities ");
      } else {
         this.features.put(name, feature);
      }
   }

   public void check(String name) {
      if (!supportedFeatures.contains(name)) {
         throw new IllegalArgumentException("Unable to access unsupported property " + name);
      }
   }

   public Object getProperty(String name) {
      this.check(name);
      return this.features.get(name);
   }

   public void setXMLReporter(XMLReporter r) {
      this.features.put("javax.xml.stream.reporter", r);
   }

   public XMLReporter getXMLReporter() {
      return (XMLReporter)this.features.get("javax.xml.stream.reporter");
   }

   public void setXMLResolver(XMLResolver r) {
      this.features.put("javax.xml.stream.resolver", r);
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

   public boolean isPropertySupported(String name) {
      return supportedFeatures.contains(name);
   }

   static {
      supportedFeatures.add("javax.xml.stream.isValidating");
      supportedFeatures.add("javax.xml.stream.isCoalescing");
      supportedFeatures.add("javax.xml.stream.isReplacingEntityReferences");
      supportedFeatures.add("javax.xml.stream.isSupportingExternalEntities");
      supportedFeatures.add("javax.xml.stream.isRepairingNamespaces");
      supportedFeatures.add("javax.xml.stream.isNamespaceAware");
      supportedFeatures.add("javax.xml.stream.supportDTD");
      supportedFeatures.add("javax.xml.stream.reporter");
      supportedFeatures.add("javax.xml.stream.resolver");
      supportedFeatures.add("javax.xml.stream.allocator");
      supportedFeatures.add(NOTATIONS);
      supportedFeatures.add(ENTITIES);
   }
}
