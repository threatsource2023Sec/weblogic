package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleDerivationSet;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleType;
import org.apache.xmlbeans.impl.xb.xsdschema.UnionDocument;

public class SimpleTypeImpl extends AnnotatedImpl implements SimpleType {
   private static final QName RESTRICTION$0 = new QName("http://www.w3.org/2001/XMLSchema", "restriction");
   private static final QName LIST$2 = new QName("http://www.w3.org/2001/XMLSchema", "list");
   private static final QName UNION$4 = new QName("http://www.w3.org/2001/XMLSchema", "union");
   private static final QName FINAL$6 = new QName("", "final");
   private static final QName NAME$8 = new QName("", "name");

   public SimpleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public RestrictionDocument.Restriction getRestriction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RestrictionDocument.Restriction target = null;
         target = (RestrictionDocument.Restriction)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRestriction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESTRICTION$0) != 0;
      }
   }

   public void setRestriction(RestrictionDocument.Restriction restriction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RestrictionDocument.Restriction target = null;
         target = (RestrictionDocument.Restriction)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
         if (target == null) {
            target = (RestrictionDocument.Restriction)this.get_store().add_element_user(RESTRICTION$0);
         }

         target.set(restriction);
      }
   }

   public RestrictionDocument.Restriction addNewRestriction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RestrictionDocument.Restriction target = null;
         target = (RestrictionDocument.Restriction)this.get_store().add_element_user(RESTRICTION$0);
         return target;
      }
   }

   public void unsetRestriction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)RESTRICTION$0, 0);
      }
   }

   public ListDocument.List getList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().find_element_user((QName)LIST$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LIST$2) != 0;
      }
   }

   public void setList(ListDocument.List list) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().find_element_user((QName)LIST$2, 0);
         if (target == null) {
            target = (ListDocument.List)this.get_store().add_element_user(LIST$2);
         }

         target.set(list);
      }
   }

   public ListDocument.List addNewList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().add_element_user(LIST$2);
         return target;
      }
   }

   public void unsetList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)LIST$2, 0);
      }
   }

   public UnionDocument.Union getUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().find_element_user((QName)UNION$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNION$4) != 0;
      }
   }

   public void setUnion(UnionDocument.Union union) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().find_element_user((QName)UNION$4, 0);
         if (target == null) {
            target = (UnionDocument.Union)this.get_store().add_element_user(UNION$4);
         }

         target.set(union);
      }
   }

   public UnionDocument.Union addNewUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnionDocument.Union target = null;
         target = (UnionDocument.Union)this.get_store().add_element_user(UNION$4);
         return target;
      }
   }

   public void unsetUnion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)UNION$4, 0);
      }
   }

   public Object getFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$6);
         return target == null ? null : target.getObjectValue();
      }
   }

   public SimpleDerivationSet xgetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDerivationSet target = null;
         target = (SimpleDerivationSet)this.get_store().find_attribute_user(FINAL$6);
         return target;
      }
   }

   public boolean isSetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FINAL$6) != null;
      }
   }

   public void setFinal(Object xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FINAL$6);
         }

         target.setObjectValue(xfinal);
      }
   }

   public void xsetFinal(SimpleDerivationSet xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDerivationSet target = null;
         target = (SimpleDerivationSet)this.get_store().find_attribute_user(FINAL$6);
         if (target == null) {
            target = (SimpleDerivationSet)this.get_store().add_attribute_user(FINAL$6);
         }

         target.set(xfinal);
      }
   }

   public void unsetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FINAL$6);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$8);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$8) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$8);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$8);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$8);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$8);
      }
   }
}
