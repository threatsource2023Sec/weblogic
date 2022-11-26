package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.RespectBindingType;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;

public class RespectBindingTypeImpl extends XmlComplexContentImpl implements RespectBindingType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "enabled");

   public RespectBindingTypeImpl(SchemaType sType) {
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
}
