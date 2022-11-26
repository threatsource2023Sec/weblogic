package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.InterceptorOrderType;

public class InterceptorOrderTypeImpl extends XmlComplexContentImpl implements InterceptorOrderType {
   private static final long serialVersionUID = 1L;
   private static final QName INTERCEPTORCLASS$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "interceptor-class");
   private static final QName ID$2 = new QName("", "id");

   public InterceptorOrderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType[] getInterceptorClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INTERCEPTORCLASS$0, targetList);
         FullyQualifiedClassType[] result = new FullyQualifiedClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FullyQualifiedClassType getInterceptorClassArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(INTERCEPTORCLASS$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInterceptorClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERCEPTORCLASS$0);
      }
   }

   public void setInterceptorClassArray(FullyQualifiedClassType[] interceptorClassArray) {
      this.check_orphaned();
      this.arraySetterHelper(interceptorClassArray, INTERCEPTORCLASS$0);
   }

   public void setInterceptorClassArray(int i, FullyQualifiedClassType interceptorClass) {
      this.generatedSetterHelperImpl(interceptorClass, INTERCEPTORCLASS$0, i, (short)2);
   }

   public FullyQualifiedClassType insertNewInterceptorClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().insert_element_user(INTERCEPTORCLASS$0, i);
         return target;
      }
   }

   public FullyQualifiedClassType addNewInterceptorClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(INTERCEPTORCLASS$0);
         return target;
      }
   }

   public void removeInterceptorClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERCEPTORCLASS$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
