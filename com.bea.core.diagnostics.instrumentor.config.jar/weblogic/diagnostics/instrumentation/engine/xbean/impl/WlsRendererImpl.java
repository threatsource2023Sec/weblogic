package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsRenderer;

public class WlsRendererImpl extends XmlComplexContentImpl implements WlsRenderer {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("", "name");
   private static final QName CLASSNAME$2 = new QName("", "className");
   private static final QName TYPECLASSNAME$4 = new QName("", "typeClassName");

   public WlsRendererImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$2);
         return target;
      }
   }

   public void setClassName(String className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CLASSNAME$2);
         }

         target.setStringValue(className);
      }
   }

   public void xsetClassName(XmlString className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(CLASSNAME$2);
         }

         target.set(className);
      }
   }

   public String getTypeClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPECLASSNAME$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTypeClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPECLASSNAME$4);
         return target;
      }
   }

   public boolean isSetTypeClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TYPECLASSNAME$4) != null;
      }
   }

   public void setTypeClassName(String typeClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPECLASSNAME$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TYPECLASSNAME$4);
         }

         target.setStringValue(typeClassName);
      }
   }

   public void xsetTypeClassName(XmlString typeClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPECLASSNAME$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(TYPECLASSNAME$4);
         }

         target.set(typeClassName);
      }
   }

   public void unsetTypeClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TYPECLASSNAME$4);
      }
   }
}
