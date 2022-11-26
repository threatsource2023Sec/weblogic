package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.GroupDocument;
import com.bea.xbean.xb.xsdschema.NamedGroup;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class GroupDocumentImpl extends XmlComplexContentImpl implements GroupDocument {
   private static final QName GROUP$0 = new QName("http://www.w3.org/2001/XMLSchema", "group");

   public GroupDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NamedGroup getGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedGroup target = null;
         target = (NamedGroup)this.get_store().find_element_user((QName)GROUP$0, 0);
         return target == null ? null : target;
      }
   }

   public void setGroup(NamedGroup group) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedGroup target = null;
         target = (NamedGroup)this.get_store().find_element_user((QName)GROUP$0, 0);
         if (target == null) {
            target = (NamedGroup)this.get_store().add_element_user(GROUP$0);
         }

         target.set(group);
      }
   }

   public NamedGroup addNewGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedGroup target = null;
         target = (NamedGroup)this.get_store().add_element_user(GROUP$0);
         return target;
      }
   }
}
