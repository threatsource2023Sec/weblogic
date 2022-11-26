package com.bea.ns.weblogic.x90.impl;

import com.bea.ns.weblogic.x90.PropertyNamevalueType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class PropertyNamevalueTypeImpl extends XmlComplexContentImpl implements PropertyNamevalueType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/ns/weblogic/90", "name");
   private static final QName VALUE$2 = new QName("http://www.bea.com/ns/weblogic/90", "value");

   public PropertyNamevalueTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(String name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public String addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(VALUE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setValue(String value) {
      this.generatedSetterHelperImpl(value, VALUE$2, 0, (short)1);
   }

   public String addNewValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(VALUE$2);
         return target;
      }
   }
}
