package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.EnterpriseBeansType;
import org.jcp.xmlns.xml.ns.javaee.EntityBeanType;
import org.jcp.xmlns.xml.ns.javaee.MessageDrivenBeanType;
import org.jcp.xmlns.xml.ns.javaee.SessionBeanType;

public class EnterpriseBeansTypeImpl extends XmlComplexContentImpl implements EnterpriseBeansType {
   private static final long serialVersionUID = 1L;
   private static final QName SESSION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "session");
   private static final QName ENTITY$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "entity");
   private static final QName MESSAGEDRIVEN$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-driven");
   private static final QName ID$6 = new QName("", "id");

   public EnterpriseBeansTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SessionBeanType[] getSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SESSION$0, targetList);
         SessionBeanType[] result = new SessionBeanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SessionBeanType getSessionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionBeanType target = null;
         target = (SessionBeanType)this.get_store().find_element_user(SESSION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSION$0);
      }
   }

   public void setSessionArray(SessionBeanType[] sessionArray) {
      this.check_orphaned();
      this.arraySetterHelper(sessionArray, SESSION$0);
   }

   public void setSessionArray(int i, SessionBeanType session) {
      this.generatedSetterHelperImpl(session, SESSION$0, i, (short)2);
   }

   public SessionBeanType insertNewSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionBeanType target = null;
         target = (SessionBeanType)this.get_store().insert_element_user(SESSION$0, i);
         return target;
      }
   }

   public SessionBeanType addNewSession() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionBeanType target = null;
         target = (SessionBeanType)this.get_store().add_element_user(SESSION$0);
         return target;
      }
   }

   public void removeSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSION$0, i);
      }
   }

   public EntityBeanType[] getEntityArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENTITY$2, targetList);
         EntityBeanType[] result = new EntityBeanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EntityBeanType getEntityArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityBeanType target = null;
         target = (EntityBeanType)this.get_store().find_element_user(ENTITY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEntityArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITY$2);
      }
   }

   public void setEntityArray(EntityBeanType[] entityArray) {
      this.check_orphaned();
      this.arraySetterHelper(entityArray, ENTITY$2);
   }

   public void setEntityArray(int i, EntityBeanType entity) {
      this.generatedSetterHelperImpl(entity, ENTITY$2, i, (short)2);
   }

   public EntityBeanType insertNewEntity(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityBeanType target = null;
         target = (EntityBeanType)this.get_store().insert_element_user(ENTITY$2, i);
         return target;
      }
   }

   public EntityBeanType addNewEntity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityBeanType target = null;
         target = (EntityBeanType)this.get_store().add_element_user(ENTITY$2);
         return target;
      }
   }

   public void removeEntity(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITY$2, i);
      }
   }

   public MessageDrivenBeanType[] getMessageDrivenArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDRIVEN$4, targetList);
         MessageDrivenBeanType[] result = new MessageDrivenBeanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDrivenBeanType getMessageDrivenArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDrivenBeanType target = null;
         target = (MessageDrivenBeanType)this.get_store().find_element_user(MESSAGEDRIVEN$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDrivenArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDRIVEN$4);
      }
   }

   public void setMessageDrivenArray(MessageDrivenBeanType[] messageDrivenArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDrivenArray, MESSAGEDRIVEN$4);
   }

   public void setMessageDrivenArray(int i, MessageDrivenBeanType messageDriven) {
      this.generatedSetterHelperImpl(messageDriven, MESSAGEDRIVEN$4, i, (short)2);
   }

   public MessageDrivenBeanType insertNewMessageDriven(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDrivenBeanType target = null;
         target = (MessageDrivenBeanType)this.get_store().insert_element_user(MESSAGEDRIVEN$4, i);
         return target;
      }
   }

   public MessageDrivenBeanType addNewMessageDriven() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDrivenBeanType target = null;
         target = (MessageDrivenBeanType)this.get_store().add_element_user(MESSAGEDRIVEN$4);
         return target;
      }
   }

   public void removeMessageDriven(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDRIVEN$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
