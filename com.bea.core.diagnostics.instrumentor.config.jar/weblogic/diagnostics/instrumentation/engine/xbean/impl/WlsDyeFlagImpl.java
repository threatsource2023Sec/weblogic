package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlag;

public class WlsDyeFlagImpl extends XmlComplexContentImpl implements WlsDyeFlag {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("", "name");
   private static final QName INDEX$2 = new QName("", "index");

   public WlsDyeFlagImpl(SchemaType sType) {
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

   public int getIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(INDEX$2);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_attribute_user(INDEX$2);
         return target;
      }
   }

   public void setIndex(int index) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(INDEX$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(INDEX$2);
         }

         target.setIntValue(index);
      }
   }

   public void xsetIndex(XmlInt index) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_attribute_user(INDEX$2);
         if (target == null) {
            target = (XmlInt)this.get_store().add_attribute_user(INDEX$2);
         }

         target.set(index);
      }
   }
}
