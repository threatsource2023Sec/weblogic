package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsAction;

public class WlsActionImpl extends XmlComplexContentImpl implements WlsAction {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("", "type");
   private static final QName CLASSNAME$2 = new QName("", "className");

   public WlsActionImpl(SchemaType sType) {
      super(sType);
   }

   public String getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPE$0);
         return target;
      }
   }

   public void setType(String type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TYPE$0);
         }

         target.setStringValue(type);
      }
   }

   public void xsetType(XmlString type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TYPE$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(TYPE$0);
         }

         target.set(type);
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
}
