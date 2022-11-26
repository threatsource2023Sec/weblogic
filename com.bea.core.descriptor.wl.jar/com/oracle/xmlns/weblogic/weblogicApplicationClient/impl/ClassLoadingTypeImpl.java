package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.ClassLoadingType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.ShareableType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ClassLoadingTypeImpl extends XmlComplexContentImpl implements ClassLoadingType {
   private static final long serialVersionUID = 1L;
   private static final QName SHAREABLE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "shareable");

   public ClassLoadingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ShareableType[] getShareableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SHAREABLE$0, targetList);
         ShareableType[] result = new ShareableType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ShareableType getShareableArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShareableType target = null;
         target = (ShareableType)this.get_store().find_element_user(SHAREABLE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfShareableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHAREABLE$0);
      }
   }

   public void setShareableArray(ShareableType[] shareableArray) {
      this.check_orphaned();
      this.arraySetterHelper(shareableArray, SHAREABLE$0);
   }

   public void setShareableArray(int i, ShareableType shareable) {
      this.generatedSetterHelperImpl(shareable, SHAREABLE$0, i, (short)2);
   }

   public ShareableType insertNewShareable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShareableType target = null;
         target = (ShareableType)this.get_store().insert_element_user(SHAREABLE$0, i);
         return target;
      }
   }

   public ShareableType addNewShareable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShareableType target = null;
         target = (ShareableType)this.get_store().add_element_user(SHAREABLE$0);
         return target;
      }
   }

   public void removeShareable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHAREABLE$0, i);
      }
   }
}
