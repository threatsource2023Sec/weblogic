package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.InjectionTargetType;
import com.sun.java.xml.ns.javaee.JavaIdentifierType;
import javax.xml.namespace.QName;

public class InjectionTargetTypeImpl extends XmlComplexContentImpl implements InjectionTargetType {
   private static final long serialVersionUID = 1L;
   private static final QName INJECTIONTARGETCLASS$0 = new QName("http://java.sun.com/xml/ns/javaee", "injection-target-class");
   private static final QName INJECTIONTARGETNAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "injection-target-name");

   public InjectionTargetTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getInjectionTargetClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(INJECTIONTARGETCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setInjectionTargetClass(FullyQualifiedClassType injectionTargetClass) {
      this.generatedSetterHelperImpl(injectionTargetClass, INJECTIONTARGETCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewInjectionTargetClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(INJECTIONTARGETCLASS$0);
         return target;
      }
   }

   public JavaIdentifierType getInjectionTargetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(INJECTIONTARGETNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setInjectionTargetName(JavaIdentifierType injectionTargetName) {
      this.generatedSetterHelperImpl(injectionTargetName, INJECTIONTARGETNAME$2, 0, (short)1);
   }

   public JavaIdentifierType addNewInjectionTargetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(INJECTIONTARGETNAME$2);
         return target;
      }
   }
}
