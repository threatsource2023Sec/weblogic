package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackage;

public class WlsPackageImpl extends XmlComplexContentImpl implements WlsPackage {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("", "type");
   private static final QName PACKAGE$2 = new QName("", "package");

   public WlsPackageImpl(SchemaType sType) {
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

   public String getPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PACKAGE$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPackage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PACKAGE$2);
         return target;
      }
   }

   public void setPackage(String xpackage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PACKAGE$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(PACKAGE$2);
         }

         target.setStringValue(xpackage);
      }
   }

   public void xsetPackage(XmlString xpackage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PACKAGE$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(PACKAGE$2);
         }

         target.set(xpackage);
      }
   }
}
