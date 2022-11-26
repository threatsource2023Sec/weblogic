package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsInstrumentationSupport;

public class WlsInstrumentationSupportImpl extends XmlComplexContentImpl implements WlsInstrumentationSupport {
   private static final long serialVersionUID = 1L;
   private static final QName CLASSNAME$0 = new QName("", "className");

   public WlsInstrumentationSupportImpl(SchemaType sType) {
      super(sType);
   }

   public String getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$0);
         return target;
      }
   }

   public boolean isSetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(CLASSNAME$0) != null;
      }
   }

   public void setClassName(String className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CLASSNAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CLASSNAME$0);
         }

         target.setStringValue(className);
      }
   }

   public void xsetClassName(XmlString className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CLASSNAME$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(CLASSNAME$0);
         }

         target.set(className);
      }
   }

   public void unsetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(CLASSNAME$0);
      }
   }
}
