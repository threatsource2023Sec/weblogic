package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xmlconfig.NamespaceList;
import com.bea.xbean.xb.xmlconfig.NamespacePrefixList;
import com.bea.xbean.xb.xmlconfig.Nsconfig;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.List;
import javax.xml.namespace.QName;

public class NsconfigImpl extends XmlComplexContentImpl implements Nsconfig {
   private static final QName PACKAGE$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "package");
   private static final QName PREFIX$2 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "prefix");
   private static final QName SUFFIX$4 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "suffix");
   private static final QName URI$6 = new QName("", "uri");
   private static final QName URIPREFIX$8 = new QName("", "uriprefix");

   public NsconfigImpl(SchemaType sType) {
      super(sType);
   }

   public String getPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)PACKAGE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)PACKAGE$0, 0);
         return target;
      }
   }

   public boolean isSetPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PACKAGE$0) != 0;
      }
   }

   public void setPackage(String xpackage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)PACKAGE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PACKAGE$0);
         }

         target.setStringValue(xpackage);
      }
   }

   public void xsetPackage(XmlString xpackage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)PACKAGE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PACKAGE$0);
         }

         target.set(xpackage);
      }
   }

   public void unsetPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)PACKAGE$0, 0);
      }
   }

   public String getPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)PREFIX$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)PREFIX$2, 0);
         return target;
      }
   }

   public boolean isSetPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFIX$2) != 0;
      }
   }

   public void setPrefix(String prefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)PREFIX$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PREFIX$2);
         }

         target.setStringValue(prefix);
      }
   }

   public void xsetPrefix(XmlString prefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)PREFIX$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PREFIX$2);
         }

         target.set(prefix);
      }
   }

   public void unsetPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)PREFIX$2, 0);
      }
   }

   public String getSuffix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)SUFFIX$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSuffix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)SUFFIX$4, 0);
         return target;
      }
   }

   public boolean isSetSuffix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUFFIX$4) != 0;
      }
   }

   public void setSuffix(String suffix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)SUFFIX$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUFFIX$4);
         }

         target.setStringValue(suffix);
      }
   }

   public void xsetSuffix(XmlString suffix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)SUFFIX$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUFFIX$4);
         }

         target.set(suffix);
      }
   }

   public void unsetSuffix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SUFFIX$4, 0);
      }
   }

   public Object getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(URI$6);
         return target == null ? null : target.getObjectValue();
      }
   }

   public NamespaceList xgetUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespaceList target = null;
         target = (NamespaceList)this.get_store().find_attribute_user(URI$6);
         return target;
      }
   }

   public boolean isSetUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(URI$6) != null;
      }
   }

   public void setUri(Object uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(URI$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(URI$6);
         }

         target.setObjectValue(uri);
      }
   }

   public void xsetUri(NamespaceList uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespaceList target = null;
         target = (NamespaceList)this.get_store().find_attribute_user(URI$6);
         if (target == null) {
            target = (NamespaceList)this.get_store().add_attribute_user(URI$6);
         }

         target.set(uri);
      }
   }

   public void unsetUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(URI$6);
      }
   }

   public List getUriprefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(URIPREFIX$8);
         return target == null ? null : target.getListValue();
      }
   }

   public NamespacePrefixList xgetUriprefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespacePrefixList target = null;
         target = (NamespacePrefixList)this.get_store().find_attribute_user(URIPREFIX$8);
         return target;
      }
   }

   public boolean isSetUriprefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(URIPREFIX$8) != null;
      }
   }

   public void setUriprefix(List uriprefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(URIPREFIX$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(URIPREFIX$8);
         }

         target.setListValue(uriprefix);
      }
   }

   public void xsetUriprefix(NamespacePrefixList uriprefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespacePrefixList target = null;
         target = (NamespacePrefixList)this.get_store().find_attribute_user(URIPREFIX$8);
         if (target == null) {
            target = (NamespacePrefixList)this.get_store().add_attribute_user(URIPREFIX$8);
         }

         target.set(uriprefix);
      }
   }

   public void unsetUriprefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(URIPREFIX$8);
      }
   }
}
