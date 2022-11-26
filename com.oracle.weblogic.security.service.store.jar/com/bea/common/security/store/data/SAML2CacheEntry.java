package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;

public class SAML2CacheEntry extends DomainRealmScope implements PersistenceCapable {
   private String cacheName;
   private String key;
   private byte[] value;
   private long expirationTime;
   private static int pcInheritedFieldCount = DomainRealmScope.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$DomainRealmScope;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$SAML2CacheEntry;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$SAML2CacheEntryId;

   public SAML2CacheEntry(String domainName, String realmName, String cacheName, String key, byte[] value, long expireTime) {
      super(domainName, realmName);
      pcSetcacheName(this, cacheName);
      pcSetkey(this, key);
      pcSetvalue(this, value);
      pcSetexpirationTime(this, expireTime);
   }

   public String getCacheName() {
      return pcGetcacheName(this);
   }

   public String getKey() {
      return pcGetkey(this);
   }

   public byte[] getValue() {
      return pcGetvalue(this);
   }

   public void setValue(byte[] value) {
      pcSetvalue(this, value);
   }

   public long getExpirationTime() {
      return pcGetexpirationTime(this);
   }

   public void setExpirationTime(long expirationTime) {
      pcSetexpirationTime(this, expirationTime);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   protected SAML2CacheEntry() {
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"cacheName", "expirationTime", "key", "value"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Long.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B"))};
      pcFieldFlags = new byte[]{26, 26, 26, 21};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$SAML2CacheEntry != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntry : (class$Lcom$bea$common$security$store$data$SAML2CacheEntry = class$("com.bea.common.security.store.data.SAML2CacheEntry")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "SAML2CacheEntry", new SAML2CacheEntry());
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected void pcClearFields() {
      super.pcClearFields();
      this.cacheName = null;
      this.expirationTime = 0L;
      this.key = null;
      this.value = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      SAML2CacheEntry var4 = new SAML2CacheEntry();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      SAML2CacheEntry var3 = new SAML2CacheEntry();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 4 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.cacheName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.expirationTime = this.pcStateManager.replaceLongField(this, var1);
               return;
            case 2:
               this.key = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.value = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcReplaceFields(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.pcReplaceField(var1[var2]);
      }

   }

   public void pcProvideField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcProvideField(var1);
      } else {
         switch (var2) {
            case 0:
               this.pcStateManager.providedStringField(this, var1, this.cacheName);
               return;
            case 1:
               this.pcStateManager.providedLongField(this, var1, this.expirationTime);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.key);
               return;
            case 3:
               this.pcStateManager.providedObjectField(this, var1, this.value);
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcProvideFields(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.pcProvideField(var1[var2]);
      }

   }

   protected void pcCopyField(SAML2CacheEntry var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.cacheName = var1.cacheName;
               return;
            case 1:
               this.expirationTime = var1.expirationTime;
               return;
            case 2:
               this.key = var1.key;
               return;
            case 3:
               this.value = var1.value;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      SAML2CacheEntry var3 = (SAML2CacheEntry)var1;
      if (var3.pcStateManager != this.pcStateManager) {
         throw new IllegalArgumentException();
      } else if (this.pcStateManager == null) {
         throw new IllegalStateException();
      } else {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            this.pcCopyField(var3, var2[var4]);
         }

      }
   }

   public void pcCopyKeyFieldsToObjectId(FieldSupplier var1, Object var2) {
      super.pcCopyKeyFieldsToObjectId(var1, var2);
      SAML2CacheEntryId var3 = (SAML2CacheEntryId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "cacheName", true), var1.fetchStringField(0 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "key", true), var1.fetchStringField(2 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      SAML2CacheEntryId var2 = (SAML2CacheEntryId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "cacheName", true), this.cacheName);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "key", true), this.key);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      SAML2CacheEntryId var3 = (SAML2CacheEntryId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "cacheName", true)));
      var1.storeStringField(2 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "key", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      SAML2CacheEntryId var2 = (SAML2CacheEntryId)var1;
      this.cacheName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "cacheName", true));
      this.key = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$SAML2CacheEntryId != null ? class$Lcom$bea$common$security$store$data$SAML2CacheEntryId : (class$Lcom$bea$common$security$store$data$SAML2CacheEntryId = class$("com.bea.common.security.store.data.SAML2CacheEntryId")), "key", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      throw new IllegalArgumentException("The id type \"class com.bea.common.security.store.data.SAML2CacheEntryId\" specfied by persistent type \"class com.bea.common.security.store.data.SAML2CacheEntry\" does not have a public string or class + string constructor.");
   }

   public Object pcNewObjectIdInstance() {
      return new SAML2CacheEntryId();
   }

   private static final String pcGetcacheName(SAML2CacheEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.cacheName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.cacheName;
      }
   }

   private static final void pcSetcacheName(SAML2CacheEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cacheName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.cacheName, var1, 0);
      }
   }

   private static final long pcGetexpirationTime(SAML2CacheEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.expirationTime;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.expirationTime;
      }
   }

   private static final void pcSetexpirationTime(SAML2CacheEntry var0, long var1) {
      if (var0.pcStateManager == null) {
         var0.expirationTime = var1;
      } else {
         var0.pcStateManager.settingLongField(var0, pcInheritedFieldCount + 1, var0.expirationTime, var1, 0);
      }
   }

   private static final String pcGetkey(SAML2CacheEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.key;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.key;
      }
   }

   private static final void pcSetkey(SAML2CacheEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.key = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.key, var1, 0);
      }
   }

   private static final byte[] pcGetvalue(SAML2CacheEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.value;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.value;
      }
   }

   private static final void pcSetvalue(SAML2CacheEntry var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.value = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 3, var0.value, var1, 0);
      }
   }
}
