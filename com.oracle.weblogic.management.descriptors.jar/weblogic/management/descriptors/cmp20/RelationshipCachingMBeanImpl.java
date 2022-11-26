package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RelationshipCachingMBeanImpl extends XMLElementMBeanDelegate implements RelationshipCachingMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_cachingName = false;
   private String cachingName;
   private boolean isSet_cachingElements = false;
   private List cachingElements;

   public String getCachingName() {
      return this.cachingName;
   }

   public void setCachingName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cachingName;
      this.cachingName = value;
      this.isSet_cachingName = value != null;
      this.checkChange("cachingName", old, this.cachingName);
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
      result.append(ToXML.indent(indentLevel)).append("<relationship-caching");
      result.append(">\n");
      if (null != this.getCachingName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<caching-name>").append(this.getCachingName()).append("</caching-name>\n");
      }

      if (null != this.getCachingElements()) {
         for(int i = 0; i < this.getCachingElements().length; ++i) {
            result.append(this.getCachingElements()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</relationship-caching>\n");
      return result.toString();
   }
}
