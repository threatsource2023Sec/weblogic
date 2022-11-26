package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomDataCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DataCachesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDataCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GemFireDataCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoConcurrentDataCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LruDataCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TangosolDataCacheType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DataCachesTypeImpl extends XmlComplexContentImpl implements DataCachesType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTDATACACHE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-data-cache");
   private static final QName KODOCONCURRENTDATACACHE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-concurrent-data-cache");
   private static final QName GEMFIREDATACACHE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gem-fire-data-cache");
   private static final QName LRUDATACACHE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lru-data-cache");
   private static final QName TANGOSOLDATACACHE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tangosol-data-cache");
   private static final QName CUSTOMDATACACHE$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-data-cache");

   public DataCachesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DefaultDataCacheType[] getDefaultDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DEFAULTDATACACHE$0, targetList);
         DefaultDataCacheType[] result = new DefaultDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DefaultDataCacheType getDefaultDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheType target = null;
         target = (DefaultDataCacheType)this.get_store().find_element_user(DEFAULTDATACACHE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilDefaultDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheType target = null;
         target = (DefaultDataCacheType)this.get_store().find_element_user(DEFAULTDATACACHE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfDefaultDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDATACACHE$0);
      }
   }

   public void setDefaultDataCacheArray(DefaultDataCacheType[] defaultDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(defaultDataCacheArray, DEFAULTDATACACHE$0);
   }

   public void setDefaultDataCacheArray(int i, DefaultDataCacheType defaultDataCache) {
      this.generatedSetterHelperImpl(defaultDataCache, DEFAULTDATACACHE$0, i, (short)2);
   }

   public void setNilDefaultDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheType target = null;
         target = (DefaultDataCacheType)this.get_store().find_element_user(DEFAULTDATACACHE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public DefaultDataCacheType insertNewDefaultDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheType target = null;
         target = (DefaultDataCacheType)this.get_store().insert_element_user(DEFAULTDATACACHE$0, i);
         return target;
      }
   }

   public DefaultDataCacheType addNewDefaultDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheType target = null;
         target = (DefaultDataCacheType)this.get_store().add_element_user(DEFAULTDATACACHE$0);
         return target;
      }
   }

   public void removeDefaultDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDATACACHE$0, i);
      }
   }

   public KodoConcurrentDataCacheType[] getKodoConcurrentDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(KODOCONCURRENTDATACACHE$2, targetList);
         KodoConcurrentDataCacheType[] result = new KodoConcurrentDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public KodoConcurrentDataCacheType getKodoConcurrentDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentDataCacheType target = null;
         target = (KodoConcurrentDataCacheType)this.get_store().find_element_user(KODOCONCURRENTDATACACHE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilKodoConcurrentDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentDataCacheType target = null;
         target = (KodoConcurrentDataCacheType)this.get_store().find_element_user(KODOCONCURRENTDATACACHE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfKodoConcurrentDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOCONCURRENTDATACACHE$2);
      }
   }

   public void setKodoConcurrentDataCacheArray(KodoConcurrentDataCacheType[] kodoConcurrentDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(kodoConcurrentDataCacheArray, KODOCONCURRENTDATACACHE$2);
   }

   public void setKodoConcurrentDataCacheArray(int i, KodoConcurrentDataCacheType kodoConcurrentDataCache) {
      this.generatedSetterHelperImpl(kodoConcurrentDataCache, KODOCONCURRENTDATACACHE$2, i, (short)2);
   }

   public void setNilKodoConcurrentDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentDataCacheType target = null;
         target = (KodoConcurrentDataCacheType)this.get_store().find_element_user(KODOCONCURRENTDATACACHE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public KodoConcurrentDataCacheType insertNewKodoConcurrentDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentDataCacheType target = null;
         target = (KodoConcurrentDataCacheType)this.get_store().insert_element_user(KODOCONCURRENTDATACACHE$2, i);
         return target;
      }
   }

   public KodoConcurrentDataCacheType addNewKodoConcurrentDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentDataCacheType target = null;
         target = (KodoConcurrentDataCacheType)this.get_store().add_element_user(KODOCONCURRENTDATACACHE$2);
         return target;
      }
   }

   public void removeKodoConcurrentDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOCONCURRENTDATACACHE$2, i);
      }
   }

   public GemFireDataCacheType[] getGemFireDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(GEMFIREDATACACHE$4, targetList);
         GemFireDataCacheType[] result = new GemFireDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public GemFireDataCacheType getGemFireDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireDataCacheType target = null;
         target = (GemFireDataCacheType)this.get_store().find_element_user(GEMFIREDATACACHE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilGemFireDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireDataCacheType target = null;
         target = (GemFireDataCacheType)this.get_store().find_element_user(GEMFIREDATACACHE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfGemFireDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GEMFIREDATACACHE$4);
      }
   }

   public void setGemFireDataCacheArray(GemFireDataCacheType[] gemFireDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(gemFireDataCacheArray, GEMFIREDATACACHE$4);
   }

   public void setGemFireDataCacheArray(int i, GemFireDataCacheType gemFireDataCache) {
      this.generatedSetterHelperImpl(gemFireDataCache, GEMFIREDATACACHE$4, i, (short)2);
   }

   public void setNilGemFireDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireDataCacheType target = null;
         target = (GemFireDataCacheType)this.get_store().find_element_user(GEMFIREDATACACHE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public GemFireDataCacheType insertNewGemFireDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireDataCacheType target = null;
         target = (GemFireDataCacheType)this.get_store().insert_element_user(GEMFIREDATACACHE$4, i);
         return target;
      }
   }

   public GemFireDataCacheType addNewGemFireDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireDataCacheType target = null;
         target = (GemFireDataCacheType)this.get_store().add_element_user(GEMFIREDATACACHE$4);
         return target;
      }
   }

   public void removeGemFireDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GEMFIREDATACACHE$4, i);
      }
   }

   public LruDataCacheType[] getLruDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LRUDATACACHE$6, targetList);
         LruDataCacheType[] result = new LruDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LruDataCacheType getLruDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruDataCacheType target = null;
         target = (LruDataCacheType)this.get_store().find_element_user(LRUDATACACHE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilLruDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruDataCacheType target = null;
         target = (LruDataCacheType)this.get_store().find_element_user(LRUDATACACHE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfLruDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LRUDATACACHE$6);
      }
   }

   public void setLruDataCacheArray(LruDataCacheType[] lruDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(lruDataCacheArray, LRUDATACACHE$6);
   }

   public void setLruDataCacheArray(int i, LruDataCacheType lruDataCache) {
      this.generatedSetterHelperImpl(lruDataCache, LRUDATACACHE$6, i, (short)2);
   }

   public void setNilLruDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruDataCacheType target = null;
         target = (LruDataCacheType)this.get_store().find_element_user(LRUDATACACHE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public LruDataCacheType insertNewLruDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruDataCacheType target = null;
         target = (LruDataCacheType)this.get_store().insert_element_user(LRUDATACACHE$6, i);
         return target;
      }
   }

   public LruDataCacheType addNewLruDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruDataCacheType target = null;
         target = (LruDataCacheType)this.get_store().add_element_user(LRUDATACACHE$6);
         return target;
      }
   }

   public void removeLruDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LRUDATACACHE$6, i);
      }
   }

   public TangosolDataCacheType[] getTangosolDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TANGOSOLDATACACHE$8, targetList);
         TangosolDataCacheType[] result = new TangosolDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TangosolDataCacheType getTangosolDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolDataCacheType target = null;
         target = (TangosolDataCacheType)this.get_store().find_element_user(TANGOSOLDATACACHE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilTangosolDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolDataCacheType target = null;
         target = (TangosolDataCacheType)this.get_store().find_element_user(TANGOSOLDATACACHE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfTangosolDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TANGOSOLDATACACHE$8);
      }
   }

   public void setTangosolDataCacheArray(TangosolDataCacheType[] tangosolDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(tangosolDataCacheArray, TANGOSOLDATACACHE$8);
   }

   public void setTangosolDataCacheArray(int i, TangosolDataCacheType tangosolDataCache) {
      this.generatedSetterHelperImpl(tangosolDataCache, TANGOSOLDATACACHE$8, i, (short)2);
   }

   public void setNilTangosolDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolDataCacheType target = null;
         target = (TangosolDataCacheType)this.get_store().find_element_user(TANGOSOLDATACACHE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public TangosolDataCacheType insertNewTangosolDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolDataCacheType target = null;
         target = (TangosolDataCacheType)this.get_store().insert_element_user(TANGOSOLDATACACHE$8, i);
         return target;
      }
   }

   public TangosolDataCacheType addNewTangosolDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolDataCacheType target = null;
         target = (TangosolDataCacheType)this.get_store().add_element_user(TANGOSOLDATACACHE$8);
         return target;
      }
   }

   public void removeTangosolDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TANGOSOLDATACACHE$8, i);
      }
   }

   public CustomDataCacheType[] getCustomDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMDATACACHE$10, targetList);
         CustomDataCacheType[] result = new CustomDataCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomDataCacheType getCustomDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheType target = null;
         target = (CustomDataCacheType)this.get_store().find_element_user(CUSTOMDATACACHE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilCustomDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheType target = null;
         target = (CustomDataCacheType)this.get_store().find_element_user(CUSTOMDATACACHE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfCustomDataCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMDATACACHE$10);
      }
   }

   public void setCustomDataCacheArray(CustomDataCacheType[] customDataCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(customDataCacheArray, CUSTOMDATACACHE$10);
   }

   public void setCustomDataCacheArray(int i, CustomDataCacheType customDataCache) {
      this.generatedSetterHelperImpl(customDataCache, CUSTOMDATACACHE$10, i, (short)2);
   }

   public void setNilCustomDataCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheType target = null;
         target = (CustomDataCacheType)this.get_store().find_element_user(CUSTOMDATACACHE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public CustomDataCacheType insertNewCustomDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheType target = null;
         target = (CustomDataCacheType)this.get_store().insert_element_user(CUSTOMDATACACHE$10, i);
         return target;
      }
   }

   public CustomDataCacheType addNewCustomDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheType target = null;
         target = (CustomDataCacheType)this.get_store().add_element_user(CUSTOMDATACACHE$10);
         return target;
      }
   }

   public void removeCustomDataCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMDATACACHE$10, i);
      }
   }
}
