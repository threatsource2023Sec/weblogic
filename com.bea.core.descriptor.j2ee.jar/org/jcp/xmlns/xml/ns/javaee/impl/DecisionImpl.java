package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ArtifactRef;
import org.jcp.xmlns.xml.ns.javaee.Decision;
import org.jcp.xmlns.xml.ns.javaee.End;
import org.jcp.xmlns.xml.ns.javaee.Fail;
import org.jcp.xmlns.xml.ns.javaee.Next;
import org.jcp.xmlns.xml.ns.javaee.Properties;
import org.jcp.xmlns.xml.ns.javaee.Stop;

public class DecisionImpl extends XmlComplexContentImpl implements Decision {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "properties");
   private static final QName END$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "end");
   private static final QName FAIL$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "fail");
   private static final QName NEXT$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "next");
   private static final QName STOP$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "stop");
   private static final QName ID$10 = new QName("", "id");
   private static final QName REF$12 = new QName("", "ref");

   public DecisionImpl(SchemaType sType) {
      super(sType);
   }

   public Properties getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().find_element_user(PROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$0) != 0;
      }
   }

   public void setProperties(Properties properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$0, 0, (short)1);
   }

   public Properties addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().add_element_user(PROPERTIES$0);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$0, 0);
      }
   }

   public End[] getEndArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(END$2, targetList);
         End[] result = new End[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public End getEndArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().find_element_user(END$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEndArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(END$2);
      }
   }

   public void setEndArray(End[] endArray) {
      this.check_orphaned();
      this.arraySetterHelper(endArray, END$2);
   }

   public void setEndArray(int i, End end) {
      this.generatedSetterHelperImpl(end, END$2, i, (short)2);
   }

   public End insertNewEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().insert_element_user(END$2, i);
         return target;
      }
   }

   public End addNewEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().add_element_user(END$2);
         return target;
      }
   }

   public void removeEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(END$2, i);
      }
   }

   public Fail[] getFailArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FAIL$4, targetList);
         Fail[] result = new Fail[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Fail getFailArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().find_element_user(FAIL$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFailArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAIL$4);
      }
   }

   public void setFailArray(Fail[] failArray) {
      this.check_orphaned();
      this.arraySetterHelper(failArray, FAIL$4);
   }

   public void setFailArray(int i, Fail fail) {
      this.generatedSetterHelperImpl(fail, FAIL$4, i, (short)2);
   }

   public Fail insertNewFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().insert_element_user(FAIL$4, i);
         return target;
      }
   }

   public Fail addNewFail() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().add_element_user(FAIL$4);
         return target;
      }
   }

   public void removeFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIL$4, i);
      }
   }

   public Next[] getNextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NEXT$6, targetList);
         Next[] result = new Next[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Next getNextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().find_element_user(NEXT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NEXT$6);
      }
   }

   public void setNextArray(Next[] nextArray) {
      this.check_orphaned();
      this.arraySetterHelper(nextArray, NEXT$6);
   }

   public void setNextArray(int i, Next next) {
      this.generatedSetterHelperImpl(next, NEXT$6, i, (short)2);
   }

   public Next insertNewNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().insert_element_user(NEXT$6, i);
         return target;
      }
   }

   public Next addNewNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().add_element_user(NEXT$6);
         return target;
      }
   }

   public void removeNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NEXT$6, i);
      }
   }

   public Stop[] getStopArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STOP$8, targetList);
         Stop[] result = new Stop[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Stop getStopArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().find_element_user(STOP$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStopArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOP$8);
      }
   }

   public void setStopArray(Stop[] stopArray) {
      this.check_orphaned();
      this.arraySetterHelper(stopArray, STOP$8);
   }

   public void setStopArray(int i, Stop stop) {
      this.generatedSetterHelperImpl(stop, STOP$8, i, (short)2);
   }

   public Stop insertNewStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().insert_element_user(STOP$8, i);
         return target;
      }
   }

   public Stop addNewStop() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().add_element_user(STOP$8);
         return target;
      }
   }

   public void removeStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOP$8, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public String getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public ArtifactRef xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArtifactRef target = null;
         target = (ArtifactRef)this.get_store().find_attribute_user(REF$12);
         return target;
      }
   }

   public void setRef(String ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$12);
         }

         target.setStringValue(ref);
      }
   }

   public void xsetRef(ArtifactRef ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArtifactRef target = null;
         target = (ArtifactRef)this.get_store().find_attribute_user(REF$12);
         if (target == null) {
            target = (ArtifactRef)this.get_store().add_attribute_user(REF$12);
         }

         target.set(ref);
      }
   }
}
