package weblogic.xml.jaxp;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class DOMFactoryProperties {
   public static final String COALESCING = "Coalescing";
   public static final String EXPANDENTITYREFERENCES = "ExpandEntityReferences";
   public static final String IGNORINGCOMMENTS = "IgnoringComments";
   public static final String IGNORINGELEMENTCONTENTWHITESPACE = "IgnoringElementContentWhitespace";
   public static final String NAMESPACEAWARE = "Namespaceaware";
   public static final String VALIDATING = "Validating";
   public static final String SCHEMA = "Schema";
   public static final String XINCL = "XIncludeAware";
   private Hashtable factoryProperties = new Hashtable();
   private Set facPropertySettingMarks;
   private LinkedHashMap attributes;

   public DOMFactoryProperties() {
      this.factoryProperties.put("Coalescing", Boolean.FALSE);
      this.factoryProperties.put("ExpandEntityReferences", Boolean.TRUE);
      this.factoryProperties.put("IgnoringComments", Boolean.FALSE);
      this.factoryProperties.put("IgnoringElementContentWhitespace", Boolean.FALSE);
      this.factoryProperties.put("Namespaceaware", Boolean.FALSE);
      this.factoryProperties.put("Validating", Boolean.FALSE);
      this.factoryProperties.put("XIncludeAware", Boolean.FALSE);
      this.facPropertySettingMarks = new HashSet();
      this.attributes = new LinkedHashMap();
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

   public void setAttribute(String key, Object value) {
      this.attributes.put(key, value);
   }

   public Object getAttribute(String key) {
      Object value = this.attributes.get(key);
      return value;
   }

   public Iterator attributes() {
      return this.attributes.keySet().iterator();
   }

   public Iterator properties() {
      return this.factoryProperties.keySet().iterator();
   }

   public Object clone() {
      DOMFactoryProperties fp = new DOMFactoryProperties();
      fp.factoryProperties.clear();
      fp.facPropertySettingMarks.clear();
      fp.attributes.clear();
      fp.factoryProperties.putAll(this.factoryProperties);
      fp.facPropertySettingMarks.addAll(this.facPropertySettingMarks);
      fp.attributes.putAll(this.attributes);
      return fp;
   }
}
