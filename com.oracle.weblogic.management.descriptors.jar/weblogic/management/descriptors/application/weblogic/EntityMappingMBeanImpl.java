package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityMappingMBeanImpl extends XMLElementMBeanDelegate implements EntityMappingMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_publicId = false;
   private String publicId;
   private boolean isSet_entityMappingName = false;
   private String entityMappingName;
   private boolean isSet_cacheTimeoutInterval = false;
   private int cacheTimeoutInterval;
   private boolean isSet_entityURI = false;
   private String entityURI;
   private boolean isSet_systemId = false;
   private String systemId;
   private boolean isSet_whenToCache = false;
   private String whenToCache;

   public String getPublicId() {
      return this.publicId;
   }

   public void setPublicId(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.publicId;
      this.publicId = value;
      this.isSet_publicId = value != null;
      this.checkChange("publicId", old, this.publicId);
   }

   public String getEntityMappingName() {
      return this.entityMappingName;
   }

   public void setEntityMappingName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.entityMappingName;
      this.entityMappingName = value;
      this.isSet_entityMappingName = value != null;
      this.checkChange("entityMappingName", old, this.entityMappingName);
   }

   public int getCacheTimeoutInterval() {
      return this.cacheTimeoutInterval;
   }

   public void setCacheTimeoutInterval(int value) {
      int old = this.cacheTimeoutInterval;
      this.cacheTimeoutInterval = value;
      this.isSet_cacheTimeoutInterval = value != -1;
      this.checkChange("cacheTimeoutInterval", old, this.cacheTimeoutInterval);
   }

   public String getEntityURI() {
      return this.entityURI;
   }

   public void setEntityURI(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.entityURI;
      this.entityURI = value;
      this.isSet_entityURI = value != null;
      this.checkChange("entityURI", old, this.entityURI);
   }

   public String getSystemId() {
      return this.systemId;
   }

   public void setSystemId(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.systemId;
      this.systemId = value;
      this.isSet_systemId = value != null;
      this.checkChange("systemId", old, this.systemId);
   }

   public String getWhenToCache() {
      return this.whenToCache;
   }

   public void setWhenToCache(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.whenToCache;
      this.whenToCache = value;
      this.isSet_whenToCache = value != null;
      this.checkChange("whenToCache", old, this.whenToCache);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity-mapping");
      result.append(">\n");
      if (null != this.getEntityMappingName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<entity-mapping-name>").append(this.getEntityMappingName()).append("</entity-mapping-name>\n");
      }

      if (null != this.getPublicId()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<public-id>").append(this.getPublicId()).append("</public-id>\n");
      }

      if (null != this.getSystemId()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<system-id>").append(this.getSystemId()).append("</system-id>\n");
      }

      if (null != this.getEntityURI()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<entity-uri>").append(this.getEntityURI()).append("</entity-uri>\n");
      }

      if (null != this.getWhenToCache()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<when-to-cache>").append(this.getWhenToCache()).append("</when-to-cache>\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<cache-timeout-interval>").append(this.getCacheTimeoutInterval()).append("</cache-timeout-interval>\n");
      result.append(ToXML.indent(indentLevel)).append("</entity-mapping>\n");
      return result.toString();
   }
}
