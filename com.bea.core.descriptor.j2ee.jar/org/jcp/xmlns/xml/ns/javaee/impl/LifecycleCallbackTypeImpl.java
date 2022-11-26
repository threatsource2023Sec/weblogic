package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.JavaIdentifierType;
import org.jcp.xmlns.xml.ns.javaee.LifecycleCallbackType;

public class LifecycleCallbackTypeImpl extends XmlComplexContentImpl implements LifecycleCallbackType {
   private static final long serialVersionUID = 1L;
   private static final QName LIFECYCLECALLBACKCLASS$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "lifecycle-callback-class");
   private static final QName LIFECYCLECALLBACKMETHOD$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "lifecycle-callback-method");

   public LifecycleCallbackTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getLifecycleCallbackClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(LIFECYCLECALLBACKCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLifecycleCallbackClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIFECYCLECALLBACKCLASS$0) != 0;
      }
   }

   public void setLifecycleCallbackClass(FullyQualifiedClassType lifecycleCallbackClass) {
      this.generatedSetterHelperImpl(lifecycleCallbackClass, LIFECYCLECALLBACKCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewLifecycleCallbackClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(LIFECYCLECALLBACKCLASS$0);
         return target;
      }
   }

   public void unsetLifecycleCallbackClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LIFECYCLECALLBACKCLASS$0, 0);
      }
   }

   public JavaIdentifierType getLifecycleCallbackMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(LIFECYCLECALLBACKMETHOD$2, 0);
         return target == null ? null : target;
      }
   }

   public void setLifecycleCallbackMethod(JavaIdentifierType lifecycleCallbackMethod) {
      this.generatedSetterHelperImpl(lifecycleCallbackMethod, LIFECYCLECALLBACKMETHOD$2, 0, (short)1);
   }

   public JavaIdentifierType addNewLifecycleCallbackMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(LIFECYCLECALLBACKMETHOD$2);
         return target;
      }
   }
}
