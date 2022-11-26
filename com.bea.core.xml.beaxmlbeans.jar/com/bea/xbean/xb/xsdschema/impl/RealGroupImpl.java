package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.All;
import com.bea.xbean.xb.xsdschema.ExplicitGroup;
import com.bea.xbean.xb.xsdschema.RealGroup;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RealGroupImpl extends GroupImpl implements RealGroup {
   private static final QName ALL$0 = new QName("http://www.w3.org/2001/XMLSchema", "all");
   private static final QName CHOICE$2 = new QName("http://www.w3.org/2001/XMLSchema", "choice");
   private static final QName SEQUENCE$4 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");

   public RealGroupImpl(SchemaType sType) {
      super(sType);
   }

   public All[] getAllArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ALL$0, targetList);
         All[] result = new All[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public All getAllArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user(ALL$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAllArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALL$0);
      }
   }

   public void setAllArray(All[] allArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(allArray, ALL$0);
      }
   }

   public void setAllArray(int i, All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user(ALL$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(all);
         }
      }
   }

   public All insertNewAll(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().insert_element_user(ALL$0, i);
         return target;
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$0);
         return target;
      }
   }

   public void removeAll(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALL$0, i);
      }
   }

   public ExplicitGroup[] getChoiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)CHOICE$2, targetList);
         ExplicitGroup[] result = new ExplicitGroup[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExplicitGroup getChoiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(CHOICE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfChoiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHOICE$2);
      }
   }

   public void setChoiceArray(ExplicitGroup[] choiceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(choiceArray, CHOICE$2);
      }
   }

   public void setChoiceArray(int i, ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(CHOICE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(choice);
         }
      }
   }

   public ExplicitGroup insertNewChoice(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().insert_element_user(CHOICE$2, i);
         return target;
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$2);
         return target;
      }
   }

   public void removeChoice(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHOICE$2, i);
      }
   }

   public ExplicitGroup[] getSequenceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)SEQUENCE$4, targetList);
         ExplicitGroup[] result = new ExplicitGroup[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExplicitGroup getSequenceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(SEQUENCE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSequenceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$4);
      }
   }

   public void setSequenceArray(ExplicitGroup[] sequenceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(sequenceArray, SEQUENCE$4);
      }
   }

   public void setSequenceArray(int i, ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(SEQUENCE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(sequence);
         }
      }
   }

   public ExplicitGroup insertNewSequence(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().insert_element_user(SEQUENCE$4, i);
         return target;
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$4);
         return target;
      }
   }

   public void removeSequence(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCE$4, i);
      }
   }
}
