package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class CachingElementMBeanImpl extends XMLElementMBeanDelegate implements CachingElementMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_groupName = false;
   private String groupName;
   private boolean isSet_cmrField = false;
   private String cmrField;
   private boolean isSet_cachingElements = false;
   private List cachingElements;

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.groupName;
      this.groupName = value;
      this.isSet_groupName = value != null;
      this.checkChange("groupName", old, this.groupName);
   }

   public String getCmrField() {
      return this.cmrField;
   }

   public void setCmrField(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cmrField;
      this.cmrField = value;
      this.isSet_cmrField = value != null;
      this.checkChange("cmrField", old, this.cmrField);
   }

   public CachingElementMBean[] getCachingElements() {
      if (this.cachingElements == null) {
         return new CachingElementMBean[0];
      } else {
         CachingElementMBean[] result = new CachingElementMBean[this.cachingElements.size()];
         result = (CachingElementMBean[])((CachingElementMBean[])this.cachingElements.toArray(result));
         return result;
      }
   }

   public void setCachingElements(CachingElementMBean[] value) {
      CachingElementMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getCachingElements();
      }

      this.isSet_cachingElements = true;
      if (this.cachingElements == null) {
         this.cachingElements = Collections.synchronizedList(new ArrayList());
      } else {
         this.cachingElements.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.cachingElements.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("CachingElements", _oldVal, this.getCachingElements());
      }

   }

   public void addCachingElement(CachingElementMBean value) {
      this.isSet_cachingElements = true;
      if (this.cachingElements == null) {
         this.cachingElements = Collections.synchronizedList(new ArrayList());
      }

      this.cachingElements.add(value);
   }

   public void removeCachingElement(CachingElementMBean value) {
      if (this.cachingElements != null) {
         this.cachingElements.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<caching-element");
      result.append(">\n");
      if (null != this.getCmrField()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cmr-field>").append(this.getCmrField()).append("</cmr-field>\n");
      }

      if (null != this.getGroupName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<group-name>").append(this.getGroupName()).append("</group-name>\n");
      }

      if (null != this.getCachingElements()) {
         for(int i = 0; i < this.getCachingElements().length; ++i) {
            result.append(this.getCachingElements()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</caching-element>\n");
      return result.toString();
   }
}
