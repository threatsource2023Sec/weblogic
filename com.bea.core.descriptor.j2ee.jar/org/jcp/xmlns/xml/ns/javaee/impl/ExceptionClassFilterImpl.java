package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ExceptionClassFilter;

public class ExceptionClassFilterImpl extends XmlComplexContentImpl implements ExceptionClassFilter {
   private static final long serialVersionUID = 1L;
   private static final QName INCLUDE$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "include");
   private static final QName EXCLUDE$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "exclude");

   public ExceptionClassFilterImpl(SchemaType sType) {
      super(sType);
   }

   public ExceptionClassFilter.Include[] getIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDE$0, targetList);
         ExceptionClassFilter.Include[] result = new ExceptionClassFilter.Include[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExceptionClassFilter.Include getIncludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Include target = null;
         target = (ExceptionClassFilter.Include)this.get_store().find_element_user(INCLUDE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDE$0);
      }
   }

   public void setIncludeArray(ExceptionClassFilter.Include[] includeArray) {
      this.check_orphaned();
      this.arraySetterHelper(includeArray, INCLUDE$0);
   }

   public void setIncludeArray(int i, ExceptionClassFilter.Include include) {
      this.generatedSetterHelperImpl(include, INCLUDE$0, i, (short)2);
   }

   public ExceptionClassFilter.Include insertNewInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Include target = null;
         target = (ExceptionClassFilter.Include)this.get_store().insert_element_user(INCLUDE$0, i);
         return target;
      }
   }

   public ExceptionClassFilter.Include addNewInclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Include target = null;
         target = (ExceptionClassFilter.Include)this.get_store().add_element_user(INCLUDE$0);
         return target;
      }
   }

   public void removeInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDE$0, i);
      }
   }

   public ExceptionClassFilter.Exclude[] getExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCLUDE$2, targetList);
         ExceptionClassFilter.Exclude[] result = new ExceptionClassFilter.Exclude[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExceptionClassFilter.Exclude getExcludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Exclude target = null;
         target = (ExceptionClassFilter.Exclude)this.get_store().find_element_user(EXCLUDE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDE$2);
      }
   }

   public void setExcludeArray(ExceptionClassFilter.Exclude[] excludeArray) {
      this.check_orphaned();
      this.arraySetterHelper(excludeArray, EXCLUDE$2);
   }

   public void setExcludeArray(int i, ExceptionClassFilter.Exclude exclude) {
      this.generatedSetterHelperImpl(exclude, EXCLUDE$2, i, (short)2);
   }

   public ExceptionClassFilter.Exclude insertNewExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Exclude target = null;
         target = (ExceptionClassFilter.Exclude)this.get_store().insert_element_user(EXCLUDE$2, i);
         return target;
      }
   }

   public ExceptionClassFilter.Exclude addNewExclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter.Exclude target = null;
         target = (ExceptionClassFilter.Exclude)this.get_store().add_element_user(EXCLUDE$2);
         return target;
      }
   }

   public void removeExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDE$2, i);
      }
   }

   public static class ExcludeImpl extends XmlComplexContentImpl implements ExceptionClassFilter.Exclude {
      private static final long serialVersionUID = 1L;
      private static final QName CLASS1$0 = new QName("", "class");

      public ExcludeImpl(SchemaType sType) {
         super(sType);
      }

      public String getClass1() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(CLASS1$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetClass1() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(CLASS1$0);
            return target;
         }
      }

      public void setClass1(String class1) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(CLASS1$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(CLASS1$0);
            }

            target.setStringValue(class1);
         }
      }

      public void xsetClass1(XmlString class1) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(CLASS1$0);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(CLASS1$0);
            }

            target.set(class1);
         }
      }
   }

   public static class IncludeImpl extends XmlComplexContentImpl implements ExceptionClassFilter.Include {
      private static final long serialVersionUID = 1L;
      private static final QName CLASS1$0 = new QName("", "class");

      public IncludeImpl(SchemaType sType) {
         super(sType);
      }

      public String getClass1() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(CLASS1$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetClass1() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(CLASS1$0);
            return target;
         }
      }

      public void setClass1(String class1) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(CLASS1$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(CLASS1$0);
            }

            target.setStringValue(class1);
         }
      }

      public void xsetClass1(XmlString class1) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(CLASS1$0);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(CLASS1$0);
            }

            target.set(class1);
         }
      }
   }
}
