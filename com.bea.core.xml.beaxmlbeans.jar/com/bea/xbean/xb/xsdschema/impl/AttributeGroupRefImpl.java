package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.AttributeGroupRef;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlQName;
import javax.xml.namespace.QName;

public class AttributeGroupRefImpl extends AttributeGroupImpl implements AttributeGroupRef {
   private static final QName REF$0 = new QName("", "ref");

   public AttributeGroupRefImpl(SchemaType sType) {
      super(sType);
   }

   public QName getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$0);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$0);
         return target;
      }
   }

   public boolean isSetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(REF$0) != null;
      }
   }

   public void setRef(QName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$0);
         }

         target.setQNameValue(ref);
      }
   }

   public void xsetRef(XmlQName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$0);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(REF$0);
         }

         target.set(ref);
      }
   }

   public void unsetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(REF$0);
      }
   }
}
