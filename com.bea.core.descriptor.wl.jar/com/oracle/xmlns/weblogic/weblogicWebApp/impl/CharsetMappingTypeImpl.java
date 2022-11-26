package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.CharsetMappingType;
import com.oracle.xmlns.weblogic.weblogicWebApp.IanaCharsetNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.JavaCharsetNameType;
import javax.xml.namespace.QName;

public class CharsetMappingTypeImpl extends XmlComplexContentImpl implements CharsetMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName IANACHARSETNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "iana-charset-name");
   private static final QName JAVACHARSETNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "java-charset-name");
   private static final QName ID$4 = new QName("", "id");

   public CharsetMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public IanaCharsetNameType getIanaCharsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IanaCharsetNameType target = null;
         target = (IanaCharsetNameType)this.get_store().find_element_user(IANACHARSETNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setIanaCharsetName(IanaCharsetNameType ianaCharsetName) {
      this.generatedSetterHelperImpl(ianaCharsetName, IANACHARSETNAME$0, 0, (short)1);
   }

   public IanaCharsetNameType addNewIanaCharsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IanaCharsetNameType target = null;
         target = (IanaCharsetNameType)this.get_store().add_element_user(IANACHARSETNAME$0);
         return target;
      }
   }

   public JavaCharsetNameType getJavaCharsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaCharsetNameType target = null;
         target = (JavaCharsetNameType)this.get_store().find_element_user(JAVACHARSETNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaCharsetName(JavaCharsetNameType javaCharsetName) {
      this.generatedSetterHelperImpl(javaCharsetName, JAVACHARSETNAME$2, 0, (short)1);
   }

   public JavaCharsetNameType addNewJavaCharsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaCharsetNameType target = null;
         target = (JavaCharsetNameType)this.get_store().add_element_user(JAVACHARSETNAME$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
