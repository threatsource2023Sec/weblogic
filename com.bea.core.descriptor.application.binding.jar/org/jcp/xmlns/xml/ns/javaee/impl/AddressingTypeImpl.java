package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AddressingResponsesType;
import org.jcp.xmlns.xml.ns.javaee.AddressingType;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;

public class AddressingTypeImpl extends XmlComplexContentImpl implements AddressingType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "enabled");
   private static final QName REQUIRED$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "required");
   private static final QName RESPONSES$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "responses");

   public AddressingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(TrueFalseType enabled) {
      this.generatedSetterHelperImpl(enabled, ENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLED$0);
         return target;
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public TrueFalseType getRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REQUIRED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRED$2) != 0;
      }
   }

   public void setRequired(TrueFalseType required) {
      this.generatedSetterHelperImpl(required, REQUIRED$2, 0, (short)1);
   }

   public TrueFalseType addNewRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REQUIRED$2);
         return target;
      }
   }

   public void unsetRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRED$2, 0);
      }
   }

   public AddressingResponsesType getResponses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingResponsesType target = null;
         target = (AddressingResponsesType)this.get_store().find_element_user(RESPONSES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResponses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPONSES$4) != 0;
      }
   }

   public void setResponses(AddressingResponsesType responses) {
      this.generatedSetterHelperImpl(responses, RESPONSES$4, 0, (short)1);
   }

   public AddressingResponsesType addNewResponses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingResponsesType target = null;
         target = (AddressingResponsesType)this.get_store().add_element_user(RESPONSES$4);
         return target;
      }
   }

   public void unsetResponses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPONSES$4, 0);
      }
   }
}
