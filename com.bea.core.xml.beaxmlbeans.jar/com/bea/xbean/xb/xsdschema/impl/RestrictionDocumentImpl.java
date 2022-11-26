package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.Facet;
import com.bea.xbean.xb.xsdschema.LocalSimpleType;
import com.bea.xbean.xb.xsdschema.NoFixedFacet;
import com.bea.xbean.xb.xsdschema.NumFacet;
import com.bea.xbean.xb.xsdschema.PatternDocument;
import com.bea.xbean.xb.xsdschema.RestrictionDocument;
import com.bea.xbean.xb.xsdschema.TotalDigitsDocument;
import com.bea.xbean.xb.xsdschema.WhiteSpaceDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlQName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RestrictionDocumentImpl extends XmlComplexContentImpl implements RestrictionDocument {
   private static final QName RESTRICTION$0 = new QName("http://www.w3.org/2001/XMLSchema", "restriction");

   public RestrictionDocumentImpl(SchemaType sType) {
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

   public static class RestrictionImpl extends AnnotatedImpl implements RestrictionDocument.Restriction {
      private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
      private static final QName MINEXCLUSIVE$2 = new QName("http://www.w3.org/2001/XMLSchema", "minExclusive");
      private static final QName MININCLUSIVE$4 = new QName("http://www.w3.org/2001/XMLSchema", "minInclusive");
      private static final QName MAXEXCLUSIVE$6 = new QName("http://www.w3.org/2001/XMLSchema", "maxExclusive");
      private static final QName MAXINCLUSIVE$8 = new QName("http://www.w3.org/2001/XMLSchema", "maxInclusive");
      private static final QName TOTALDIGITS$10 = new QName("http://www.w3.org/2001/XMLSchema", "totalDigits");
      private static final QName FRACTIONDIGITS$12 = new QName("http://www.w3.org/2001/XMLSchema", "fractionDigits");
      private static final QName LENGTH$14 = new QName("http://www.w3.org/2001/XMLSchema", "length");
      private static final QName MINLENGTH$16 = new QName("http://www.w3.org/2001/XMLSchema", "minLength");
      private static final QName MAXLENGTH$18 = new QName("http://www.w3.org/2001/XMLSchema", "maxLength");
      private static final QName ENUMERATION$20 = new QName("http://www.w3.org/2001/XMLSchema", "enumeration");
      private static final QName WHITESPACE$22 = new QName("http://www.w3.org/2001/XMLSchema", "whiteSpace");
      private static final QName PATTERN$24 = new QName("http://www.w3.org/2001/XMLSchema", "pattern");
      private static final QName BASE$26 = new QName("", "base");

      public RestrictionImpl(SchemaType sType) {
         super(sType);
      }

      public LocalSimpleType getSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SIMPLETYPE$0) != 0;
         }
      }

      public void setSimpleType(LocalSimpleType simpleType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
            if (target == null) {
               target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
            }

            target.set(simpleType);
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

      public void unsetSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)SIMPLETYPE$0, 0);
         }
      }

      public Facet[] getMinExclusiveArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MINEXCLUSIVE$2, targetList);
            Facet[] result = new Facet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Facet getMinExclusiveArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MINEXCLUSIVE$2, i);
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
            return this.get_store().count_elements(MINEXCLUSIVE$2);
         }
      }

      public void setMinExclusiveArray(Facet[] minExclusiveArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(minExclusiveArray, MINEXCLUSIVE$2);
         }
      }

      public void setMinExclusiveArray(int i, Facet minExclusive) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MINEXCLUSIVE$2, i);
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
            target = (Facet)this.get_store().insert_element_user(MINEXCLUSIVE$2, i);
            return target;
         }
      }

      public Facet addNewMinExclusive() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().add_element_user(MINEXCLUSIVE$2);
            return target;
         }
      }

      public void removeMinExclusive(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MINEXCLUSIVE$2, i);
         }
      }

      public Facet[] getMinInclusiveArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MININCLUSIVE$4, targetList);
            Facet[] result = new Facet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Facet getMinInclusiveArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MININCLUSIVE$4, i);
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
            return this.get_store().count_elements(MININCLUSIVE$4);
         }
      }

      public void setMinInclusiveArray(Facet[] minInclusiveArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(minInclusiveArray, MININCLUSIVE$4);
         }
      }

      public void setMinInclusiveArray(int i, Facet minInclusive) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MININCLUSIVE$4, i);
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
            target = (Facet)this.get_store().insert_element_user(MININCLUSIVE$4, i);
            return target;
         }
      }

      public Facet addNewMinInclusive() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().add_element_user(MININCLUSIVE$4);
            return target;
         }
      }

      public void removeMinInclusive(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MININCLUSIVE$4, i);
         }
      }

      public Facet[] getMaxExclusiveArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MAXEXCLUSIVE$6, targetList);
            Facet[] result = new Facet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Facet getMaxExclusiveArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MAXEXCLUSIVE$6, i);
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
            return this.get_store().count_elements(MAXEXCLUSIVE$6);
         }
      }

      public void setMaxExclusiveArray(Facet[] maxExclusiveArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(maxExclusiveArray, MAXEXCLUSIVE$6);
         }
      }

      public void setMaxExclusiveArray(int i, Facet maxExclusive) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MAXEXCLUSIVE$6, i);
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
            target = (Facet)this.get_store().insert_element_user(MAXEXCLUSIVE$6, i);
            return target;
         }
      }

      public Facet addNewMaxExclusive() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().add_element_user(MAXEXCLUSIVE$6);
            return target;
         }
      }

      public void removeMaxExclusive(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MAXEXCLUSIVE$6, i);
         }
      }

      public Facet[] getMaxInclusiveArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MAXINCLUSIVE$8, targetList);
            Facet[] result = new Facet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Facet getMaxInclusiveArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MAXINCLUSIVE$8, i);
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
            return this.get_store().count_elements(MAXINCLUSIVE$8);
         }
      }

      public void setMaxInclusiveArray(Facet[] maxInclusiveArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(maxInclusiveArray, MAXINCLUSIVE$8);
         }
      }

      public void setMaxInclusiveArray(int i, Facet maxInclusive) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().find_element_user(MAXINCLUSIVE$8, i);
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
            target = (Facet)this.get_store().insert_element_user(MAXINCLUSIVE$8, i);
            return target;
         }
      }

      public Facet addNewMaxInclusive() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Facet target = null;
            target = (Facet)this.get_store().add_element_user(MAXINCLUSIVE$8);
            return target;
         }
      }

      public void removeMaxInclusive(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MAXINCLUSIVE$8, i);
         }
      }

      public TotalDigitsDocument.TotalDigits[] getTotalDigitsArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)TOTALDIGITS$10, targetList);
            TotalDigitsDocument.TotalDigits[] result = new TotalDigitsDocument.TotalDigits[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TotalDigitsDocument.TotalDigits getTotalDigitsArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TotalDigitsDocument.TotalDigits target = null;
            target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user(TOTALDIGITS$10, i);
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
            return this.get_store().count_elements(TOTALDIGITS$10);
         }
      }

      public void setTotalDigitsArray(TotalDigitsDocument.TotalDigits[] totalDigitsArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(totalDigitsArray, TOTALDIGITS$10);
         }
      }

      public void setTotalDigitsArray(int i, TotalDigitsDocument.TotalDigits totalDigits) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TotalDigitsDocument.TotalDigits target = null;
            target = (TotalDigitsDocument.TotalDigits)this.get_store().find_element_user(TOTALDIGITS$10, i);
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
            target = (TotalDigitsDocument.TotalDigits)this.get_store().insert_element_user(TOTALDIGITS$10, i);
            return target;
         }
      }

      public TotalDigitsDocument.TotalDigits addNewTotalDigits() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TotalDigitsDocument.TotalDigits target = null;
            target = (TotalDigitsDocument.TotalDigits)this.get_store().add_element_user(TOTALDIGITS$10);
            return target;
         }
      }

      public void removeTotalDigits(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(TOTALDIGITS$10, i);
         }
      }

      public NumFacet[] getFractionDigitsArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)FRACTIONDIGITS$12, targetList);
            NumFacet[] result = new NumFacet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NumFacet getFractionDigitsArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(FRACTIONDIGITS$12, i);
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
            return this.get_store().count_elements(FRACTIONDIGITS$12);
         }
      }

      public void setFractionDigitsArray(NumFacet[] fractionDigitsArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(fractionDigitsArray, FRACTIONDIGITS$12);
         }
      }

      public void setFractionDigitsArray(int i, NumFacet fractionDigits) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(FRACTIONDIGITS$12, i);
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
            target = (NumFacet)this.get_store().insert_element_user(FRACTIONDIGITS$12, i);
            return target;
         }
      }

      public NumFacet addNewFractionDigits() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().add_element_user(FRACTIONDIGITS$12);
            return target;
         }
      }

      public void removeFractionDigits(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(FRACTIONDIGITS$12, i);
         }
      }

      public NumFacet[] getLengthArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)LENGTH$14, targetList);
            NumFacet[] result = new NumFacet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NumFacet getLengthArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(LENGTH$14, i);
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
            return this.get_store().count_elements(LENGTH$14);
         }
      }

      public void setLengthArray(NumFacet[] lengthArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(lengthArray, LENGTH$14);
         }
      }

      public void setLengthArray(int i, NumFacet length) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(LENGTH$14, i);
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
            target = (NumFacet)this.get_store().insert_element_user(LENGTH$14, i);
            return target;
         }
      }

      public NumFacet addNewLength() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().add_element_user(LENGTH$14);
            return target;
         }
      }

      public void removeLength(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(LENGTH$14, i);
         }
      }

      public NumFacet[] getMinLengthArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MINLENGTH$16, targetList);
            NumFacet[] result = new NumFacet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NumFacet getMinLengthArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(MINLENGTH$16, i);
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
            return this.get_store().count_elements(MINLENGTH$16);
         }
      }

      public void setMinLengthArray(NumFacet[] minLengthArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(minLengthArray, MINLENGTH$16);
         }
      }

      public void setMinLengthArray(int i, NumFacet minLength) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(MINLENGTH$16, i);
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
            target = (NumFacet)this.get_store().insert_element_user(MINLENGTH$16, i);
            return target;
         }
      }

      public NumFacet addNewMinLength() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().add_element_user(MINLENGTH$16);
            return target;
         }
      }

      public void removeMinLength(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MINLENGTH$16, i);
         }
      }

      public NumFacet[] getMaxLengthArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MAXLENGTH$18, targetList);
            NumFacet[] result = new NumFacet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NumFacet getMaxLengthArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(MAXLENGTH$18, i);
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
            return this.get_store().count_elements(MAXLENGTH$18);
         }
      }

      public void setMaxLengthArray(NumFacet[] maxLengthArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(maxLengthArray, MAXLENGTH$18);
         }
      }

      public void setMaxLengthArray(int i, NumFacet maxLength) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().find_element_user(MAXLENGTH$18, i);
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
            target = (NumFacet)this.get_store().insert_element_user(MAXLENGTH$18, i);
            return target;
         }
      }

      public NumFacet addNewMaxLength() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NumFacet target = null;
            target = (NumFacet)this.get_store().add_element_user(MAXLENGTH$18);
            return target;
         }
      }

      public void removeMaxLength(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MAXLENGTH$18, i);
         }
      }

      public NoFixedFacet[] getEnumerationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ENUMERATION$20, targetList);
            NoFixedFacet[] result = new NoFixedFacet[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NoFixedFacet getEnumerationArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NoFixedFacet target = null;
            target = (NoFixedFacet)this.get_store().find_element_user(ENUMERATION$20, i);
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
            return this.get_store().count_elements(ENUMERATION$20);
         }
      }

      public void setEnumerationArray(NoFixedFacet[] enumerationArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(enumerationArray, ENUMERATION$20);
         }
      }

      public void setEnumerationArray(int i, NoFixedFacet enumeration) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NoFixedFacet target = null;
            target = (NoFixedFacet)this.get_store().find_element_user(ENUMERATION$20, i);
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
            target = (NoFixedFacet)this.get_store().insert_element_user(ENUMERATION$20, i);
            return target;
         }
      }

      public NoFixedFacet addNewEnumeration() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NoFixedFacet target = null;
            target = (NoFixedFacet)this.get_store().add_element_user(ENUMERATION$20);
            return target;
         }
      }

      public void removeEnumeration(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ENUMERATION$20, i);
         }
      }

      public WhiteSpaceDocument.WhiteSpace[] getWhiteSpaceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)WHITESPACE$22, targetList);
            WhiteSpaceDocument.WhiteSpace[] result = new WhiteSpaceDocument.WhiteSpace[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public WhiteSpaceDocument.WhiteSpace getWhiteSpaceArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WhiteSpaceDocument.WhiteSpace target = null;
            target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user(WHITESPACE$22, i);
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
            return this.get_store().count_elements(WHITESPACE$22);
         }
      }

      public void setWhiteSpaceArray(WhiteSpaceDocument.WhiteSpace[] whiteSpaceArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(whiteSpaceArray, WHITESPACE$22);
         }
      }

      public void setWhiteSpaceArray(int i, WhiteSpaceDocument.WhiteSpace whiteSpace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WhiteSpaceDocument.WhiteSpace target = null;
            target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user(WHITESPACE$22, i);
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
            target = (WhiteSpaceDocument.WhiteSpace)this.get_store().insert_element_user(WHITESPACE$22, i);
            return target;
         }
      }

      public WhiteSpaceDocument.WhiteSpace addNewWhiteSpace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WhiteSpaceDocument.WhiteSpace target = null;
            target = (WhiteSpaceDocument.WhiteSpace)this.get_store().add_element_user(WHITESPACE$22);
            return target;
         }
      }

      public void removeWhiteSpace(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WHITESPACE$22, i);
         }
      }

      public PatternDocument.Pattern[] getPatternArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)PATTERN$24, targetList);
            PatternDocument.Pattern[] result = new PatternDocument.Pattern[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public PatternDocument.Pattern getPatternArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            PatternDocument.Pattern target = null;
            target = (PatternDocument.Pattern)this.get_store().find_element_user(PATTERN$24, i);
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
            return this.get_store().count_elements(PATTERN$24);
         }
      }

      public void setPatternArray(PatternDocument.Pattern[] patternArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(patternArray, PATTERN$24);
         }
      }

      public void setPatternArray(int i, PatternDocument.Pattern pattern) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            PatternDocument.Pattern target = null;
            target = (PatternDocument.Pattern)this.get_store().find_element_user(PATTERN$24, i);
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
            target = (PatternDocument.Pattern)this.get_store().insert_element_user(PATTERN$24, i);
            return target;
         }
      }

      public PatternDocument.Pattern addNewPattern() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            PatternDocument.Pattern target = null;
            target = (PatternDocument.Pattern)this.get_store().add_element_user(PATTERN$24);
            return target;
         }
      }

      public void removePattern(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(PATTERN$24, i);
         }
      }

      public QName getBase() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(BASE$26);
            return target == null ? null : target.getQNameValue();
         }
      }

      public XmlQName xgetBase() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(BASE$26);
            return target;
         }
      }

      public boolean isSetBase() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(BASE$26) != null;
         }
      }

      public void setBase(QName base) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(BASE$26);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(BASE$26);
            }

            target.setQNameValue(base);
         }
      }

      public void xsetBase(XmlQName base) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(BASE$26);
            if (target == null) {
               target = (XmlQName)this.get_store().add_attribute_user(BASE$26);
            }

            target.set(base);
         }
      }

      public void unsetBase() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(BASE$26);
         }
      }
   }
}
