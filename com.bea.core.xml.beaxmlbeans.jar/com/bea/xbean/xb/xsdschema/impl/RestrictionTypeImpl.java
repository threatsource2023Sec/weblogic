package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.All;
import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.AttributeGroupRef;
import com.bea.xbean.xb.xsdschema.ExplicitGroup;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.GroupRef;
import com.bea.xbean.xb.xsdschema.LocalSimpleType;
import com.bea.xbean.xb.xsdschema.NoFixedFacet;
import com.bea.xbean.xb.xsdschema.NumFacet;
import com.bea.xbean.xb.xsdschema.PatternDocument;
import com.bea.xbean.xb.xsdschema.RestrictionType;
import com.bea.xbean.xb.xsdschema.TotalDigitsDocument;
import com.bea.xbean.xb.xsdschema.WhiteSpaceDocument;
import com.bea.xbean.xb.xsdschema.Wildcard;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlQName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RestrictionTypeImpl extends AnnotatedImpl implements RestrictionType {
   private static final QName GROUP$0 = new QName("http://www.w3.org/2001/XMLSchema", "group");
   private static final QName ALL$2 = new QName("http://www.w3.org/2001/XMLSchema", "all");
   private static final QName CHOICE$4 = new QName("http://www.w3.org/2001/XMLSchema", "choice");
   private static final QName SEQUENCE$6 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");
   private static final QName SIMPLETYPE$8 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
   private static final QName MINEXCLUSIVE$10 = new QName("http://www.w3.org/2001/XMLSchema", "minExclusive");
   private static final QName MININCLUSIVE$12 = new QName("http://www.w3.org/2001/XMLSchema", "minInclusive");
   private static final QName MAXEXCLUSIVE$14 = new QName("http://www.w3.org/2001/XMLSchema", "maxExclusive");
   private static final QName MAXINCLUSIVE$16 = new QName("http://www.w3.org/2001/XMLSchema", "maxInclusive");
   private static final QName TOTALDIGITS$18 = new QName("http://www.w3.org/2001/XMLSchema", "totalDigits");
   private static final QName FRACTIONDIGITS$20 = new QName("http://www.w3.org/2001/XMLSchema", "fractionDigits");
   private static final QName LENGTH$22 = new QName("http://www.w3.org/2001/XMLSchema", "length");
   private static final QName MINLENGTH$24 = new QName("http://www.w3.org/2001/XMLSchema", "minLength");
   private static final QName MAXLENGTH$26 = new QName("http://www.w3.org/2001/XMLSchema", "maxLength");
   private static final QName ENUMERATION$28 = new QName("http://www.w3.org/2001/XMLSchema", "enumeration");
   private static final QName WHITESPACE$30 = new QName("http://www.w3.org/2001/XMLSchema", "whiteSpace");
   private static final QName PATTERN$32 = new QName("http://www.w3.org/2001/XMLSchema", "pattern");
   private static final QName ATTRIBUTE$34 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
   private static final QName ATTRIBUTEGROUP$36 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
   private static final QName ANYATTRIBUTE$38 = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");
   private static final QName BASE$40 = new QName("", "base");

   public RestrictionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public GroupRef getGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUP$0) != 0;
      }
   }

   public void setGroup(GroupRef group) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$0, 0);
         if (target == null) {
            target = (GroupRef)this.get_store().add_element_user(GROUP$0);
         }

         target.set(group);
      }
   }

   public GroupRef addNewGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().add_element_user(GROUP$0);
         return target;
      }
   }

   public void unsetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)GROUP$0, 0);
      }
   }

   public All getAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALL$2) != 0;
      }
   }

   public void setAll(All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$2, 0);
         if (target == null) {
            target = (All)this.get_store().add_element_user(ALL$2);
         }

         target.set(all);
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$2);
         return target;
      }
   }

   public void unsetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ALL$2, 0);
      }
   }

   public ExplicitGroup getChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHOICE$4) != 0;
      }
   }

   public void setChoice(ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$4, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$4);
         }

         target.set(choice);
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$4);
         return target;
      }
   }

   public void unsetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)CHOICE$4, 0);
      }
   }

   public ExplicitGroup getSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$6) != 0;
      }
   }

   public void setSequence(ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$6, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$6);
         }

         target.set(sequence);
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$6);
         return target;
      }
   }

   public void unsetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SEQUENCE$6, 0);
      }
   }

   public LocalSimpleType getSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLETYPE$8) != 0;
      }
   }

   public void setSimpleType(LocalSimpleType simpleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$8, 0);
         if (target == null) {
            target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$8);
         }

         target.set(simpleType);
      }
   }

   public LocalSimpleType addNewSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$8);
         return target;
      }
   }

   public void unsetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SIMPLETYPE$8, 0);
      }
   }

   public Facet[] getMinExclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MINEXCLUSIVE$10, targetList);
         Facet[] result = new Facet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Facet getMinExclusiveArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MINEXCLUSIVE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMinExclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINEXCLUSIVE$10);
      }
   }

   public void setMinExclusiveArray(Facet[] minExclusiveArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(minExclusiveArray, MINEXCLUSIVE$10);
      }
   }

   public void setMinExclusiveArray(int i, Facet minExclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MINEXCLUSIVE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(minExclusive);
         }
      }
   }

   public Facet insertNewMinExclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().insert_element_user(MINEXCLUSIVE$10, i);
         return target;
      }
   }

   public Facet addNewMinExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MINEXCLUSIVE$10);
         return target;
      }
   }

   public void removeMinExclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINEXCLUSIVE$10, i);
      }
   }

   public Facet[] getMinInclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MININCLUSIVE$12, targetList);
         Facet[] result = new Facet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Facet getMinInclusiveArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MININCLUSIVE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMinInclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MININCLUSIVE$12);
      }
   }

   public void setMinInclusiveArray(Facet[] minInclusiveArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(minInclusiveArray, MININCLUSIVE$12);
      }
   }

   public void setMinInclusiveArray(int i, Facet minInclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MININCLUSIVE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(minInclusive);
         }
      }
   }

   public Facet insertNewMinInclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().insert_element_user(MININCLUSIVE$12, i);
         return target;
      }
   }

   public Facet addNewMinInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MININCLUSIVE$12);
         return target;
      }
   }

   public void removeMinInclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MININCLUSIVE$12, i);
      }
   }

   public Facet[] getMaxExclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MAXEXCLUSIVE$14, targetList);
         Facet[] result = new Facet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Facet getMaxExclusiveArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MAXEXCLUSIVE$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMaxExclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXEXCLUSIVE$14);
      }
   }

   public void setMaxExclusiveArray(Facet[] maxExclusiveArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(maxExclusiveArray, MAXEXCLUSIVE$14);
      }
   }

   public void setMaxExclusiveArray(int i, Facet maxExclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MAXEXCLUSIVE$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(maxExclusive);
         }
      }
   }

   public Facet insertNewMaxExclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().insert_element_user(MAXEXCLUSIVE$14, i);
         return target;
      }
   }

   public Facet addNewMaxExclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MAXEXCLUSIVE$14);
         return target;
      }
   }

   public void removeMaxExclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXEXCLUSIVE$14, i);
      }
   }

   public Facet[] getMaxInclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MAXINCLUSIVE$16, targetList);
         Facet[] result = new Facet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Facet getMaxInclusiveArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MAXINCLUSIVE$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMaxInclusiveArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXINCLUSIVE$16);
      }
   }

   public void setMaxInclusiveArray(Facet[] maxInclusiveArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(maxInclusiveArray, MAXINCLUSIVE$16);
      }
   }

   public void setMaxInclusiveArray(int i, Facet maxInclusive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().find_element_user(MAXINCLUSIVE$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(maxInclusive);
         }
      }
   }

   public Facet insertNewMaxInclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().insert_element_user(MAXINCLUSIVE$16, i);
         return target;
      }
   }

   public Facet addNewMaxInclusive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Facet target = null;
         target = (Facet)this.get_store().add_element_user(MAXINCLUSIVE$16);
         return target;
      }
   }

   public void removeMaxInclusive(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXINCLUSIVE$16, i);
      }
   }

   public TotalDigitsDocument.TotalDigits[] getTotalDigitsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)TOTALDIGITS$18, targetList);
         TotalDigitsDocument.TotalDigits[] result = new TotalDigitsDocument.TotalDigits[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TotalDigitsDocument.TotalDigits getTotalDigitsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user(TOTALDIGITS$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTotalDigitsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOTALDIGITS$18);
      }
   }

   public void setTotalDigitsArray(TotalDigitsDocument.TotalDigits[] totalDigitsArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(totalDigitsArray, TOTALDIGITS$18);
      }
   }

   public void setTotalDigitsArray(int i, TotalDigitsDocument.TotalDigits totalDigits) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user(TOTALDIGITS$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(totalDigits);
         }
      }
   }

   public TotalDigitsDocument.TotalDigits insertNewTotalDigits(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().insert_element_user(TOTALDIGITS$18, i);
         return target;
      }
   }

   public TotalDigitsDocument.TotalDigits addNewTotalDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TotalDigitsDocument.TotalDigits target = null;
         target = (TotalDigitsDocument.TotalDigits)this.get_store().add_element_user(TOTALDIGITS$18);
         return target;
      }
   }

   public void removeTotalDigits(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOTALDIGITS$18, i);
      }
   }

   public NumFacet[] getFractionDigitsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)FRACTIONDIGITS$20, targetList);
         NumFacet[] result = new NumFacet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NumFacet getFractionDigitsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(FRACTIONDIGITS$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFractionDigitsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FRACTIONDIGITS$20);
      }
   }

   public void setFractionDigitsArray(NumFacet[] fractionDigitsArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(fractionDigitsArray, FRACTIONDIGITS$20);
      }
   }

   public void setFractionDigitsArray(int i, NumFacet fractionDigits) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(FRACTIONDIGITS$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(fractionDigits);
         }
      }
   }

   public NumFacet insertNewFractionDigits(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().insert_element_user(FRACTIONDIGITS$20, i);
         return target;
      }
   }

   public NumFacet addNewFractionDigits() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(FRACTIONDIGITS$20);
         return target;
      }
   }

   public void removeFractionDigits(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FRACTIONDIGITS$20, i);
      }
   }

   public NumFacet[] getLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)LENGTH$22, targetList);
         NumFacet[] result = new NumFacet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NumFacet getLengthArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(LENGTH$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LENGTH$22);
      }
   }

   public void setLengthArray(NumFacet[] lengthArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(lengthArray, LENGTH$22);
      }
   }

   public void setLengthArray(int i, NumFacet length) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(LENGTH$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(length);
         }
      }
   }

   public NumFacet insertNewLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().insert_element_user(LENGTH$22, i);
         return target;
      }
   }

   public NumFacet addNewLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(LENGTH$22);
         return target;
      }
   }

   public void removeLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LENGTH$22, i);
      }
   }

   public NumFacet[] getMinLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MINLENGTH$24, targetList);
         NumFacet[] result = new NumFacet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NumFacet getMinLengthArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(MINLENGTH$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMinLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINLENGTH$24);
      }
   }

   public void setMinLengthArray(NumFacet[] minLengthArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(minLengthArray, MINLENGTH$24);
      }
   }

   public void setMinLengthArray(int i, NumFacet minLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(MINLENGTH$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(minLength);
         }
      }
   }

   public NumFacet insertNewMinLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().insert_element_user(MINLENGTH$24, i);
         return target;
      }
   }

   public NumFacet addNewMinLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(MINLENGTH$24);
         return target;
      }
   }

   public void removeMinLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINLENGTH$24, i);
      }
   }

   public NumFacet[] getMaxLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)MAXLENGTH$26, targetList);
         NumFacet[] result = new NumFacet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NumFacet getMaxLengthArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(MAXLENGTH$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMaxLengthArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXLENGTH$26);
      }
   }

   public void setMaxLengthArray(NumFacet[] maxLengthArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(maxLengthArray, MAXLENGTH$26);
      }
   }

   public void setMaxLengthArray(int i, NumFacet maxLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().find_element_user(MAXLENGTH$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(maxLength);
         }
      }
   }

   public NumFacet insertNewMaxLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().insert_element_user(MAXLENGTH$26, i);
         return target;
      }
   }

   public NumFacet addNewMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NumFacet target = null;
         target = (NumFacet)this.get_store().add_element_user(MAXLENGTH$26);
         return target;
      }
   }

   public void removeMaxLength(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXLENGTH$26, i);
      }
   }

   public NoFixedFacet[] getEnumerationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ENUMERATION$28, targetList);
         NoFixedFacet[] result = new NoFixedFacet[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NoFixedFacet getEnumerationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().find_element_user(ENUMERATION$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnumerationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENUMERATION$28);
      }
   }

   public void setEnumerationArray(NoFixedFacet[] enumerationArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(enumerationArray, ENUMERATION$28);
      }
   }

   public void setEnumerationArray(int i, NoFixedFacet enumeration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().find_element_user(ENUMERATION$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(enumeration);
         }
      }
   }

   public NoFixedFacet insertNewEnumeration(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().insert_element_user(ENUMERATION$28, i);
         return target;
      }
   }

   public NoFixedFacet addNewEnumeration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoFixedFacet target = null;
         target = (NoFixedFacet)this.get_store().add_element_user(ENUMERATION$28);
         return target;
      }
   }

   public void removeEnumeration(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENUMERATION$28, i);
      }
   }

   public WhiteSpaceDocument.WhiteSpace[] getWhiteSpaceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)WHITESPACE$30, targetList);
         WhiteSpaceDocument.WhiteSpace[] result = new WhiteSpaceDocument.WhiteSpace[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WhiteSpaceDocument.WhiteSpace getWhiteSpaceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user(WHITESPACE$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWhiteSpaceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WHITESPACE$30);
      }
   }

   public void setWhiteSpaceArray(WhiteSpaceDocument.WhiteSpace[] whiteSpaceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(whiteSpaceArray, WHITESPACE$30);
      }
   }

   public void setWhiteSpaceArray(int i, WhiteSpaceDocument.WhiteSpace whiteSpace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user(WHITESPACE$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(whiteSpace);
         }
      }
   }

   public WhiteSpaceDocument.WhiteSpace insertNewWhiteSpace(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().insert_element_user(WHITESPACE$30, i);
         return target;
      }
   }

   public WhiteSpaceDocument.WhiteSpace addNewWhiteSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().add_element_user(WHITESPACE$30);
         return target;
      }
   }

   public void removeWhiteSpace(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WHITESPACE$30, i);
      }
   }

   public PatternDocument.Pattern[] getPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)PATTERN$32, targetList);
         PatternDocument.Pattern[] result = new PatternDocument.Pattern[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PatternDocument.Pattern getPatternArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().find_element_user(PATTERN$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PATTERN$32);
      }
   }

   public void setPatternArray(PatternDocument.Pattern[] patternArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(patternArray, PATTERN$32);
      }
   }

   public void setPatternArray(int i, PatternDocument.Pattern pattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().find_element_user(PATTERN$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(pattern);
         }
      }
   }

   public PatternDocument.Pattern insertNewPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().insert_element_user(PATTERN$32, i);
         return target;
      }
   }

   public PatternDocument.Pattern addNewPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().add_element_user(PATTERN$32);
         return target;
      }
   }

   public void removePattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PATTERN$32, i);
      }
   }

   public Attribute[] getAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTE$34, targetList);
         Attribute[] result = new Attribute[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Attribute getAttributeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTE$34);
      }
   }

   public void setAttributeArray(Attribute[] attributeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeArray, ATTRIBUTE$34);
      }
   }

   public void setAttributeArray(int i, Attribute attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(attribute);
         }
      }
   }

   public Attribute insertNewAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().insert_element_user(ATTRIBUTE$34, i);
         return target;
      }
   }

   public Attribute addNewAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().add_element_user(ATTRIBUTE$34);
         return target;
      }
   }

   public void removeAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTE$34, i);
      }
   }

   public AttributeGroupRef[] getAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$36, targetList);
         AttributeGroupRef[] result = new AttributeGroupRef[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AttributeGroupRef getAttributeGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTEGROUP$36);
      }
   }

   public void setAttributeGroupArray(AttributeGroupRef[] attributeGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$36);
      }
   }

   public void setAttributeGroupArray(int i, AttributeGroupRef attributeGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(attributeGroup);
         }
      }
   }

   public AttributeGroupRef insertNewAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().insert_element_user(ATTRIBUTEGROUP$36, i);
         return target;
      }
   }

   public AttributeGroupRef addNewAttributeGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().add_element_user(ATTRIBUTEGROUP$36);
         return target;
      }
   }

   public void removeAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTEGROUP$36, i);
      }
   }

   public Wildcard getAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTE$38) != 0;
      }
   }

   public void setAnyAttribute(Wildcard anyAttribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$38, 0);
         if (target == null) {
            target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$38);
         }

         target.set(anyAttribute);
      }
   }

   public Wildcard addNewAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$38);
         return target;
      }
   }

   public void unsetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ANYATTRIBUTE$38, 0);
      }
   }

   public QName getBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$40);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(BASE$40);
         return target;
      }
   }

   public void setBase(QName base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$40);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(BASE$40);
         }

         target.setQNameValue(base);
      }
   }

   public void xsetBase(XmlQName base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(BASE$40);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(BASE$40);
         }

         target.set(base);
      }
   }
}
