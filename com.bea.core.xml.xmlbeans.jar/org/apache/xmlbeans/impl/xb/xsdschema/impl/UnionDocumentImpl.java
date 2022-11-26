package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.apache.xmlbeans.impl.xb.xsdschema.UnionDocument;

public class UnionDocumentImpl extends XmlComplexContentImpl implements UnionDocument {
   private static final QName UNION$0 = new QName("http://www.w3.org/2001/XMLSchema", "union");

   public UnionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public UnionDocument.Union getUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().find_element_user((QName)UNION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setUnion(UnionDocument.Union union) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().find_element_user((QName)UNION$0, 0);
         if (target == null) {
            target = (UnionDocument.Union)this.get_store().add_element_user(UNION$0);
         }

         target.set(union);
      }
   }

   public UnionDocument.Union addNewUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().add_element_user(UNION$0);
         return target;
      }
   }

   public static class UnionImpl extends AnnotatedImpl implements UnionDocument.Union {
      private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
      private static final QName MEMBERTYPES$2 = new QName("", "memberTypes");

      public UnionImpl(SchemaType sType) {
         super(sType);
      }

      public LocalSimpleType[] getSimpleTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)SIMPLETYPE$0, targetList);
            LocalSimpleType[] result = new LocalSimpleType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public LocalSimpleType getSimpleTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user(SIMPLETYPE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfSimpleTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SIMPLETYPE$0);
         }
      }

      public void setSimpleTypeArray(LocalSimpleType[] simpleTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(simpleTypeArray, SIMPLETYPE$0);
         }
      }

      public void setSimpleTypeArray(int i, LocalSimpleType simpleType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user(SIMPLETYPE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(simpleType);
            }
         }
      }

      public LocalSimpleType insertNewSimpleType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().insert_element_user(SIMPLETYPE$0, i);
            return target;
         }
      }

      public LocalSimpleType addNewSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
            return target;
         }
      }

      public void removeSimpleType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SIMPLETYPE$0, i);
         }
      }

      public List getMemberTypes() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MEMBERTYPES$2);
            return target == null ? null : target.getListValue();
         }
      }

      public UnionDocument.Union.MemberTypes xgetMemberTypes() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            UnionDocument.Union.MemberTypes target = null;
            target = (UnionDocument.Union.MemberTypes)this.get_store().find_attribute_user(MEMBERTYPES$2);
            return target;
         }
      }

      public boolean isSetMemberTypes() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(MEMBERTYPES$2) != null;
         }
      }

      public void setMemberTypes(List memberTypes) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MEMBERTYPES$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(MEMBERTYPES$2);
            }

            target.setListValue(memberTypes);
         }
      }

      public void xsetMemberTypes(UnionDocument.Union.MemberTypes memberTypes) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            UnionDocument.Union.MemberTypes target = null;
            target = (UnionDocument.Union.MemberTypes)this.get_store().find_attribute_user(MEMBERTYPES$2);
            if (target == null) {
               target = (UnionDocument.Union.MemberTypes)this.get_store().add_attribute_user(MEMBERTYPES$2);
            }

            target.set(memberTypes);
         }
      }

      public void unsetMemberTypes() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(MEMBERTYPES$2);
         }
      }

      public static class MemberTypesImpl extends XmlListImpl implements UnionDocument.Union.MemberTypes {
         public MemberTypesImpl(SchemaType sType) {
            super(sType, false);
         }

         protected MemberTypesImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }
}
