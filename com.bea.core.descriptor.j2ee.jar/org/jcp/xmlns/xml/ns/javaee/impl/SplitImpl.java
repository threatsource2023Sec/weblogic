package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Flow;
import org.jcp.xmlns.xml.ns.javaee.Split;

public class SplitImpl extends XmlComplexContentImpl implements Split {
   private static final long serialVersionUID = 1L;
   private static final QName FLOW$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "flow");
   private static final QName ID$2 = new QName("", "id");
   private static final QName NEXT$4 = new QName("", "next");

   public SplitImpl(SchemaType sType) {
      super(sType);
   }

   public Flow[] getFlowArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FLOW$0, targetList);
         Flow[] result = new Flow[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Flow getFlowArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().find_element_user(FLOW$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFlowArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOW$0);
      }
   }

   public void setFlowArray(Flow[] flowArray) {
      this.check_orphaned();
      this.arraySetterHelper(flowArray, FLOW$0);
   }

   public void setFlowArray(int i, Flow flow) {
      this.generatedSetterHelperImpl(flow, FLOW$0, i, (short)2);
   }

   public Flow insertNewFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().insert_element_user(FLOW$0, i);
         return target;
      }
   }

   public Flow addNewFlow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().add_element_user(FLOW$0);
         return target;
      }
   }

   public void removeFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOW$0, i);
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

   public String getNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT$4);
         return target;
      }
   }

   public boolean isSetNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NEXT$4) != null;
      }
   }

   public void setNext(String next) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NEXT$4);
         }

         target.setStringValue(next);
      }
   }

   public void xsetNext(XmlString next) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NEXT$4);
         }

         target.set(next);
      }
   }

   public void unsetNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NEXT$4);
      }
   }
}
