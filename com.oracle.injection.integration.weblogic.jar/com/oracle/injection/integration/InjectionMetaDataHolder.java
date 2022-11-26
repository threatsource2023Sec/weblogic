package com.oracle.injection.integration;

import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.util.HashMap;
import java.util.Map;

public class InjectionMetaDataHolder {
   private Jsr250MetadataI metaData;
   private boolean interceptor;
   private Map ejbInterceptorMetaDataMap;

   public InjectionMetaDataHolder(Jsr250MetadataI metaData) {
      this(metaData, false);
   }

   public InjectionMetaDataHolder(Jsr250MetadataI metaData, boolean interceptor) {
      this.interceptor = false;
      this.metaData = metaData;
      this.interceptor = interceptor;
   }

   public InjectionMetaDataHolder(Jsr250MetadataI metaData, String ejbName) {
      this.interceptor = false;
      if (ejbName == null) {
         this.metaData = metaData;
      } else {
         this.addEjbInterceptorMetaData(ejbName, metaData);
      }

      this.interceptor = true;
   }

   public void addEjbInterceptorMetaData(String ejbName, Jsr250MetadataI metaData) {
      if (this.ejbInterceptorMetaDataMap == null) {
         this.ejbInterceptorMetaDataMap = new HashMap();
      }

      this.ejbInterceptorMetaDataMap.put(ejbName, metaData);
   }

   public Jsr250MetadataI getMetaData(String ejbName) {
      if (ejbName != null && this.interceptor) {
         return this.ejbInterceptorMetaDataMap == null ? null : (Jsr250MetadataI)this.ejbInterceptorMetaDataMap.get(ejbName);
      } else {
         return this.metaData;
      }
   }

   public Jsr250MetadataI getMetaData() {
      return this.metaData;
   }

   public void setMetaData(Jsr250MetadataI metaData) {
      this.metaData = metaData;
   }

   public boolean isInterceptor() {
      return this.interceptor;
   }
}
