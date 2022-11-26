package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class DomainRealmScope extends Top implements PersistenceCapable {
   private String domainName;
   private String realmName;
   private static int pcInheritedFieldCount = Top.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$Top;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$DomainRealmScope;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$DomainRealmScopeId;

   public DomainRealmScope() {
   }

   public DomainRealmScope(String domainName, String realmName) {
      pcSetdomainName(this, domainName);
      pcSetrealmName(this, realmName);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DomainRealmScope)) {
         return false;
      } else {
         DomainRealmScope o = (DomainRealmScope)other;
         return pcGetdomainName(this) == pcGetdomainName(o) || pcGetdomainName(this) != null && pcGetdomainName(this).equals(pcGetdomainName(o)) && pcGetrealmName(this) == pcGetrealmName(o) || pcGetrealmName(this) != null && pcGetrealmName(this).equals(pcGetrealmName(o));
      }
   }

   public int hashCode() {
      return (pcGetdomainName(this) != null ? pcGetdomainName(this).hashCode() : 0) ^ (pcGetrealmName(this) != null ? pcGetrealmName(this).hashCode() : 0);
   }

   public String toString() {
      return "realm=" + ApplicationIdUtil.encode(pcGetrealmName(this)) + ',' + "domain" + '=' + ApplicationIdUtil.encode(pcGetdomainName(this));
   }

   public String getDomainName() {
      return pcGetdomainName(this);
   }

   public void setDomainName(String domainName) {
      pcSetdomainName(this, domainName);
   }

   public String getRealmName() {
      return pcGetrealmName(this);
   }

   public void setRealmName(String realmName) {
      pcSetrealmName(this, realmName);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$Top != null ? class$Lcom$bea$common$security$store$data$Top : (class$Lcom$bea$common$security$store$data$Top = class$("com.bea.common.security.store.data.Top"));
      pcFieldNames = new String[]{"domainName", "realmName"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "DomainRealmScope", (PersistenceCapable)null);
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
      this.domainName = null;
      this.realmName = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 2 + Top.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.domainName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.realmName = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.domainName);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.realmName);
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

   protected void pcCopyField(DomainRealmScope var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.domainName = var1.domainName;
               return;
            case 1:
               this.realmName = var1.realmName;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      DomainRealmScope var3 = (DomainRealmScope)var1;
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
      DomainRealmScopeId var3 = (DomainRealmScopeId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "domainName", true), var1.fetchStringField(0 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "realmName", true), var1.fetchStringField(1 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      DomainRealmScopeId var2 = (DomainRealmScopeId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "domainName", true), this.domainName);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "realmName", true), this.realmName);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      DomainRealmScopeId var3 = (DomainRealmScopeId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "domainName", true)));
      var1.storeStringField(1 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "realmName", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      DomainRealmScopeId var2 = (DomainRealmScopeId)var1;
      this.domainName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "domainName", true));
      this.realmName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$DomainRealmScopeId != null ? class$Lcom$bea$common$security$store$data$DomainRealmScopeId : (class$Lcom$bea$common$security$store$data$DomainRealmScopeId = class$("com.bea.common.security.store.data.DomainRealmScopeId")), "realmName", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new DomainRealmScopeId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new DomainRealmScopeId();
   }

   private static final String pcGetdomainName(DomainRealmScope var0) {
      if (var0.pcStateManager == null) {
         return var0.domainName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.domainName;
      }
   }

   private static final void pcSetdomainName(DomainRealmScope var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.domainName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.domainName, var1, 0);
      }
   }

   private static final String pcGetrealmName(DomainRealmScope var0) {
      if (var0.pcStateManager == null) {
         return var0.realmName;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.realmName;
      }
   }

   private static final void pcSetrealmName(DomainRealmScope var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.realmName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.realmName, var1, 0);
      }
   }
}
