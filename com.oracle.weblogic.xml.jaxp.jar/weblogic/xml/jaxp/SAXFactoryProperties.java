package weblogic.xml.jaxp;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class SAXFactoryProperties {
   public static final String NAMESPACEAWARE = "Namespaceaware";
   public static final String VALIDATING = "Validating";
   public static final String XINCL = "XIncludeAware";
   public static final String SCHEMA = "Schema";
   private LinkedHashMap factoryProperties = new LinkedHashMap();
   private Set facPropertySettingMarks;
   private LinkedHashMap features;
   private LinkedHashMap properties;
   private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

   public SAXFactoryProperties() {
      this.factoryProperties.put("Namespaceaware", Boolean.FALSE);
      this.factoryProperties.put("Validating", Boolean.FALSE);
      this.factoryProperties.put("XIncludeAware", Boolean.FALSE);
      this.facPropertySettingMarks = new HashSet();
      this.features = new LinkedHashMap();
      this.properties = new LinkedHashMap();
   }

   public void put(String key, boolean value) {
      this.factoryProperties.put(key, new Boolean(value));
      this.facPropertySettingMarks.add(key);
   }

   public boolean get(String key) {
      Boolean property = (Boolean)this.factoryProperties.get(key);
      return property;
   }

   public boolean isSetExplicitly(String key) {
      return this.facPropertySettingMarks.contains(key);
   }

   public void setFeature(String key, boolean value) {
      this.features.put(key, new Boolean(value));
   }

   public Boolean getFeature(String key) {
      Boolean value = (Boolean)this.features.get(key);
      return value;
   }

   public void setProperty(String key, Object value) {
      this.properties.put(key, value);
      if ("http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(key) && this.properties.containsKey("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
         Object source = this.properties.remove("http://java.sun.com/xml/jaxp/properties/schemaSource");
         this.properties.put("http://java.sun.com/xml/jaxp/properties/schemaSource", source);
      }

   }

   public Object getProperty(String key) {
      Object value = this.properties.get(key);
      return value;
   }

   public Enumeration features() {
      return Collections.enumeration(this.features.keySet());
   }

   public Enumeration properties() {
      return Collections.enumeration(this.properties.keySet());
   }

   public Object clone() {
      SAXFactoryProperties fp = new SAXFactoryProperties();
      fp.factoryProperties.clear();
      fp.facPropertySettingMarks.clear();
      fp.features.clear();
      fp.properties.clear();
      fp.factoryProperties.putAll(this.factoryProperties);
      fp.facPropertySettingMarks.addAll(this.facPropertySettingMarks);
      fp.features.putAll(this.features);
      fp.properties.putAll(this.properties);
      return fp;
   }

   public void copyFrom(SAXFactoryProperties initSaxFactoryProperties) {
      this.factoryProperties.clear();
      this.facPropertySettingMarks.clear();
      this.features.clear();
      this.properties.clear();
      this.factoryProperties.putAll(initSaxFactoryProperties.factoryProperties);
      this.facPropertySettingMarks.addAll(initSaxFactoryProperties.facPropertySettingMarks);
      this.features.putAll(initSaxFactoryProperties.features);
      this.properties.putAll(initSaxFactoryProperties.properties);
   }
}
