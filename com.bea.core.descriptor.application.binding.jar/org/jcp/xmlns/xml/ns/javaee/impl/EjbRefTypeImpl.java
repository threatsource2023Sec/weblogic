package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.EjbLinkType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefNameType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefTypeType;
import org.jcp.xmlns.xml.ns.javaee.HomeType;
import org.jcp.xmlns.xml.ns.javaee.InjectionTargetType;
import org.jcp.xmlns.xml.ns.javaee.RemoteType;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class EjbRefTypeImpl extends XmlComplexContentImpl implements EjbRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName EJBREFNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref-name");
   private static final QName EJBREFTYPE$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref-type");
   private static final QName HOME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "home");
   private static final QName REMOTE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "remote");
   private static final QName EJBLINK$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-link");
   private static final QName MAPPEDNAME$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapped-name");
   private static final QName INJECTIONTARGET$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "injection-target");
   private static final QName LOOKUPNAME$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "lookup-name");
   private static final QName ID$18 = new QName("", "id");

   public EjbRefTypeImpl(SchemaType sType) {
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

   public EjbRefNameType getEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().find_element_user(EJBREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbRefName(EjbRefNameType ejbRefName) {
      this.generatedSetterHelperImpl(ejbRefName, EJBREFNAME$2, 0, (short)1);
   }

   public EjbRefNameType addNewEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().add_element_user(EJBREFNAME$2);
         return target;
      }
   }

   public EjbRefTypeType getEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefTypeType target = null;
         target = (EjbRefTypeType)this.get_store().find_element_user(EJBREFTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREFTYPE$4) != 0;
      }
   }

   public void setEjbRefType(EjbRefTypeType ejbRefType) {
      this.generatedSetterHelperImpl(ejbRefType, EJBREFTYPE$4, 0, (short)1);
   }

   public EjbRefTypeType addNewEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefTypeType target = null;
         target = (EjbRefTypeType)this.get_store().add_element_user(EJBREFTYPE$4);
         return target;
      }
   }

   public void unsetEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREFTYPE$4, 0);
      }
   }

   public HomeType getHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().find_element_user(HOME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOME$6) != 0;
      }
   }

   public void setHome(HomeType home) {
      this.generatedSetterHelperImpl(home, HOME$6, 0, (short)1);
   }

   public HomeType addNewHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().add_element_user(HOME$6);
         return target;
      }
   }

   public void unsetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOME$6, 0);
      }
   }

   public RemoteType getRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().find_element_user(REMOTE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOTE$8) != 0;
      }
   }

   public void setRemote(RemoteType remote) {
      this.generatedSetterHelperImpl(remote, REMOTE$8, 0, (short)1);
   }

   public RemoteType addNewRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().add_element_user(REMOTE$8);
         return target;
      }
   }

   public void unsetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOTE$8, 0);
      }
   }

   public EjbLinkType getEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().find_element_user(EJBLINK$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLINK$10) != 0;
      }
   }

   public void setEjbLink(EjbLinkType ejbLink) {
      this.generatedSetterHelperImpl(ejbLink, EJBLINK$10, 0, (short)1);
   }

   public EjbLinkType addNewEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().add_element_user(EJBLINK$10);
         return target;
      }
   }

   public void unsetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLINK$10, 0);
      }
   }

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$12) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$12, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$12);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$12, 0);
      }
   }

   public InjectionTargetType[] getInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INJECTIONTARGET$14, targetList);
         InjectionTargetType[] result = new InjectionTargetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InjectionTargetType getInjectionTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().find_element_user(INJECTIONTARGET$14, i);
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
         return this.get_store().count_elements(INJECTIONTARGET$14);
      }
   }

   public void setInjectionTargetArray(InjectionTargetType[] injectionTargetArray) {
      this.check_orphaned();
      this.arraySetterHelper(injectionTargetArray, INJECTIONTARGET$14);
   }

   public void setInjectionTargetArray(int i, InjectionTargetType injectionTarget) {
      this.generatedSetterHelperImpl(injectionTarget, INJECTIONTARGET$14, i, (short)2);
   }

   public InjectionTargetType insertNewInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().insert_element_user(INJECTIONTARGET$14, i);
         return target;
      }
   }

   public InjectionTargetType addNewInjectionTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().add_element_user(INJECTIONTARGET$14);
         return target;
      }
   }

   public void removeInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INJECTIONTARGET$14, i);
      }
   }

   public XsdStringType getLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(LOOKUPNAME$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOOKUPNAME$16) != 0;
      }
   }

   public void setLookupName(XsdStringType lookupName) {
      this.generatedSetterHelperImpl(lookupName, LOOKUPNAME$16, 0, (short)1);
   }

   public XsdStringType addNewLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(LOOKUPNAME$16);
         return target;
      }
   }

   public void unsetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOOKUPNAME$16, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$18) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$18);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$18);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$18);
      }
   }
}
