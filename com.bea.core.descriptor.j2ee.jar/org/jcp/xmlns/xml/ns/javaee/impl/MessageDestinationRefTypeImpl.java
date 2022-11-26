package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.InjectionTargetType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationLinkType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationRefType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationTypeType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationUsageType;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class MessageDestinationRefTypeImpl extends XmlComplexContentImpl implements MessageDestinationRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName MESSAGEDESTINATIONREFNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-ref-name");
   private static final QName MESSAGEDESTINATIONTYPE$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-type");
   private static final QName MESSAGEDESTINATIONUSAGE$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-usage");
   private static final QName MESSAGEDESTINATIONLINK$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-link");
   private static final QName MAPPEDNAME$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapped-name");
   private static final QName INJECTIONTARGET$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "injection-target");
   private static final QName LOOKUPNAME$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "lookup-name");
   private static final QName ID$16 = new QName("", "id");

   public MessageDestinationRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public JndiNameType getMessageDestinationRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(MESSAGEDESTINATIONREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMessageDestinationRefName(JndiNameType messageDestinationRefName) {
      this.generatedSetterHelperImpl(messageDestinationRefName, MESSAGEDESTINATIONREFNAME$2, 0, (short)1);
   }

   public JndiNameType addNewMessageDestinationRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(MESSAGEDESTINATIONREFNAME$2);
         return target;
      }
   }

   public MessageDestinationTypeType getMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().find_element_user(MESSAGEDESTINATIONTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONTYPE$4) != 0;
      }
   }

   public void setMessageDestinationType(MessageDestinationTypeType messageDestinationType) {
      this.generatedSetterHelperImpl(messageDestinationType, MESSAGEDESTINATIONTYPE$4, 0, (short)1);
   }

   public MessageDestinationTypeType addNewMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().add_element_user(MESSAGEDESTINATIONTYPE$4);
         return target;
      }
   }

   public void unsetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONTYPE$4, 0);
      }
   }

   public MessageDestinationUsageType getMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationUsageType target = null;
         target = (MessageDestinationUsageType)this.get_store().find_element_user(MESSAGEDESTINATIONUSAGE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONUSAGE$6) != 0;
      }
   }

   public void setMessageDestinationUsage(MessageDestinationUsageType messageDestinationUsage) {
      this.generatedSetterHelperImpl(messageDestinationUsage, MESSAGEDESTINATIONUSAGE$6, 0, (short)1);
   }

   public MessageDestinationUsageType addNewMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationUsageType target = null;
         target = (MessageDestinationUsageType)this.get_store().add_element_user(MESSAGEDESTINATIONUSAGE$6);
         return target;
      }
   }

   public void unsetMessageDestinationUsage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONUSAGE$6, 0);
      }
   }

   public MessageDestinationLinkType getMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().find_element_user(MESSAGEDESTINATIONLINK$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONLINK$8) != 0;
      }
   }

   public void setMessageDestinationLink(MessageDestinationLinkType messageDestinationLink) {
      this.generatedSetterHelperImpl(messageDestinationLink, MESSAGEDESTINATIONLINK$8, 0, (short)1);
   }

   public MessageDestinationLinkType addNewMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().add_element_user(MESSAGEDESTINATIONLINK$8);
         return target;
      }
   }

   public void unsetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONLINK$8, 0);
      }
   }

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$10) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$10, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$10);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$10, 0);
      }
   }

   public InjectionTargetType[] getInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INJECTIONTARGET$12, targetList);
         InjectionTargetType[] result = new InjectionTargetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InjectionTargetType getInjectionTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().find_element_user(INJECTIONTARGET$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INJECTIONTARGET$12);
      }
   }

   public void setInjectionTargetArray(InjectionTargetType[] injectionTargetArray) {
      this.check_orphaned();
      this.arraySetterHelper(injectionTargetArray, INJECTIONTARGET$12);
   }

   public void setInjectionTargetArray(int i, InjectionTargetType injectionTarget) {
      this.generatedSetterHelperImpl(injectionTarget, INJECTIONTARGET$12, i, (short)2);
   }

   public InjectionTargetType insertNewInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().insert_element_user(INJECTIONTARGET$12, i);
         return target;
      }
   }

   public InjectionTargetType addNewInjectionTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().add_element_user(INJECTIONTARGET$12);
         return target;
      }
   }

   public void removeInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INJECTIONTARGET$12, i);
      }
   }

   public XsdStringType getLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(LOOKUPNAME$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOOKUPNAME$14) != 0;
      }
   }

   public void setLookupName(XsdStringType lookupName) {
      this.generatedSetterHelperImpl(lookupName, LOOKUPNAME$14, 0, (short)1);
   }

   public XsdStringType addNewLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(LOOKUPNAME$14);
         return target;
      }
   }

   public void unsetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOOKUPNAME$14, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
