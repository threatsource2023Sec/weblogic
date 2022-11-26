package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationEntityCacheType;
import com.oracle.xmlns.weblogic.weblogicApplication.EjbType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbTypeImpl extends XmlComplexContentImpl implements EjbType {
   private static final long serialVersionUID = 1L;
   private static final QName ENTITYCACHE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "entity-cache");
   private static final QName STARTMDBSWITHAPPLICATION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "start-mdbs-with-application");

   public EjbTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ApplicationEntityCacheType[] getEntityCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENTITYCACHE$0, targetList);
         ApplicationEntityCacheType[] result = new ApplicationEntityCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ApplicationEntityCacheType getEntityCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationEntityCacheType target = null;
         target = (ApplicationEntityCacheType)this.get_store().find_element_user(ENTITYCACHE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEntityCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYCACHE$0);
      }
   }

   public void setEntityCacheArray(ApplicationEntityCacheType[] entityCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(entityCacheArray, ENTITYCACHE$0);
   }

   public void setEntityCacheArray(int i, ApplicationEntityCacheType entityCache) {
      this.generatedSetterHelperImpl(entityCache, ENTITYCACHE$0, i, (short)2);
   }

   public ApplicationEntityCacheType insertNewEntityCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationEntityCacheType target = null;
         target = (ApplicationEntityCacheType)this.get_store().insert_element_user(ENTITYCACHE$0, i);
         return target;
      }
   }

   public ApplicationEntityCacheType addNewEntityCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ApplicationEntityCacheType target = null;
         target = (ApplicationEntityCacheType)this.get_store().add_element_user(ENTITYCACHE$0);
         return target;
      }
   }

   public void removeEntityCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYCACHE$0, i);
      }
   }

   public TrueFalseType getStartMdbsWithApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(STARTMDBSWITHAPPLICATION$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStartMdbsWithApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STARTMDBSWITHAPPLICATION$2) != 0;
      }
   }

   public void setStartMdbsWithApplication(TrueFalseType startMdbsWithApplication) {
      this.generatedSetterHelperImpl(startMdbsWithApplication, STARTMDBSWITHAPPLICATION$2, 0, (short)1);
   }

   public TrueFalseType addNewStartMdbsWithApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(STARTMDBSWITHAPPLICATION$2);
         return target;
      }
   }

   public void unsetStartMdbsWithApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STARTMDBSWITHAPPLICATION$2, 0);
      }
   }
}
