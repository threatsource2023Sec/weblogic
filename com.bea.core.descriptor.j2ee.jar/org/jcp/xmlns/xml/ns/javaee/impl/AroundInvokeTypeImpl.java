package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AroundInvokeType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.JavaIdentifierType;

public class AroundInvokeTypeImpl extends XmlComplexContentImpl implements AroundInvokeType {
   private static final long serialVersionUID = 1L;
   private static final QName CLASS1$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "class");
   private static final QName METHODNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "method-name");

   public AroundInvokeTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getClass1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CLASS1$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClass1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASS1$0) != 0;
      }
   }

   public void setClass1(FullyQualifiedClassType class1) {
      this.generatedSetterHelperImpl(class1, CLASS1$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewClass1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CLASS1$0);
         return target;
      }
   }

   public void unsetClass1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASS1$0, 0);
      }
   }

   public JavaIdentifierType getMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(METHODNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMethodName(JavaIdentifierType methodName) {
      this.generatedSetterHelperImpl(methodName, METHODNAME$2, 0, (short)1);
   }

   public JavaIdentifierType addNewMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(METHODNAME$2);
         return target;
      }
   }
}
