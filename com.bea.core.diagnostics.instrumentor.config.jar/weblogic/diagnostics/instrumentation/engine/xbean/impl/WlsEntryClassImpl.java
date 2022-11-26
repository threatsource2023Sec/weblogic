package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClass;

public class WlsEntryClassImpl extends XmlComplexContentImpl implements WlsEntryClass {
   private static final long serialVersionUID = 1L;
   private static final QName CLASSNAME$0 = new QName("", "classname");

   public WlsEntryClassImpl(SchemaType sType) {
      super(sType);
   }

   public String getClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$0);
         return target;
      }
   }

   public void setClassname(String classname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CLASSNAME$0);
         }

         target.setStringValue(classname);
      }
   }

   public void xsetClassname(XmlString classname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(CLASSNAME$0);
         }

         target.set(classname);
      }
   }
}
