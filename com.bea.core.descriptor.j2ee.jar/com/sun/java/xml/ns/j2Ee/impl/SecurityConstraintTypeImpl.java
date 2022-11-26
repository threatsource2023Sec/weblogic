package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.AuthConstraintType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.SecurityConstraintType;
import com.sun.java.xml.ns.j2Ee.UserDataConstraintType;
import com.sun.java.xml.ns.j2Ee.WebResourceCollectionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SecurityConstraintTypeImpl extends XmlComplexContentImpl implements SecurityConstraintType {
   private static final long serialVersionUID = 1L;
   private static final QName DISPLAYNAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName WEBRESOURCECOLLECTION$2 = new QName("http://java.sun.com/xml/ns/j2ee", "web-resource-collection");
   private static final QName AUTHCONSTRAINT$4 = new QName("http://java.sun.com/xml/ns/j2ee", "auth-constraint");
   private static final QName USERDATACONSTRAINT$6 = new QName("http://java.sun.com/xml/ns/j2ee", "user-data-constraint");
   private static final QName ID$8 = new QName("", "id");

   public SecurityConstraintTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$0, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$0);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$0);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$0, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$0, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$0);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$0, i);
      }
   }

   public WebResourceCollectionType[] getWebResourceCollectionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBRESOURCECOLLECTION$2, targetList);
         WebResourceCollectionType[] result = new WebResourceCollectionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WebResourceCollectionType getWebResourceCollectionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebResourceCollectionType target = null;
         target = (WebResourceCollectionType)this.get_store().find_element_user(WEBRESOURCECOLLECTION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWebResourceCollectionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBRESOURCECOLLECTION$2);
      }
   }

   public void setWebResourceCollectionArray(WebResourceCollectionType[] webResourceCollectionArray) {
      this.check_orphaned();
      this.arraySetterHelper(webResourceCollectionArray, WEBRESOURCECOLLECTION$2);
   }

   public void setWebResourceCollectionArray(int i, WebResourceCollectionType webResourceCollection) {
      this.generatedSetterHelperImpl(webResourceCollection, WEBRESOURCECOLLECTION$2, i, (short)2);
   }

   public WebResourceCollectionType insertNewWebResourceCollection(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebResourceCollectionType target = null;
         target = (WebResourceCollectionType)this.get_store().insert_element_user(WEBRESOURCECOLLECTION$2, i);
         return target;
      }
   }

   public WebResourceCollectionType addNewWebResourceCollection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebResourceCollectionType target = null;
         target = (WebResourceCollectionType)this.get_store().add_element_user(WEBRESOURCECOLLECTION$2);
         return target;
      }
   }

   public void removeWebResourceCollection(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBRESOURCECOLLECTION$2, i);
      }
   }

   public AuthConstraintType getAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthConstraintType target = null;
         target = (AuthConstraintType)this.get_store().find_element_user(AUTHCONSTRAINT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHCONSTRAINT$4) != 0;
      }
   }

   public void setAuthConstraint(AuthConstraintType authConstraint) {
      this.generatedSetterHelperImpl(authConstraint, AUTHCONSTRAINT$4, 0, (short)1);
   }

   public AuthConstraintType addNewAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthConstraintType target = null;
         target = (AuthConstraintType)this.get_store().add_element_user(AUTHCONSTRAINT$4);
         return target;
      }
   }

   public void unsetAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHCONSTRAINT$4, 0);
      }
   }

   public UserDataConstraintType getUserDataConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserDataConstraintType target = null;
         target = (UserDataConstraintType)this.get_store().find_element_user(USERDATACONSTRAINT$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUserDataConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERDATACONSTRAINT$6) != 0;
      }
   }

   public void setUserDataConstraint(UserDataConstraintType userDataConstraint) {
      this.generatedSetterHelperImpl(userDataConstraint, USERDATACONSTRAINT$6, 0, (short)1);
   }

   public UserDataConstraintType addNewUserDataConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserDataConstraintType target = null;
         target = (UserDataConstraintType)this.get_store().add_element_user(USERDATACONSTRAINT$6);
         return target;
      }
   }

   public void unsetUserDataConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERDATACONSTRAINT$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
