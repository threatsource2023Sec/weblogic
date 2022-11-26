package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class PKITypeScope extends DomainRealmScope implements PersistenceCapable {
   private String typeName;
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
   static Class class$Lcom$bea$common$security$store$data$PKITypeScope;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PKITypeScopeId;

   public PKITypeScope() {
   }

   public PKITypeScope(String domainName, String realmName, String typeName) {
      super(domainName, realmName);
      pcSettypeName(this, typeName);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof PKITypeScope)) {
         return false;
      } else {
         PKITypeScope o = (PKITypeScope)other;
         return pcGettypeName(this) == pcGettypeName(o) || pcGettypeName(this) != null && pcGettypeName(this).equals(pcGettypeName(o));
      }
   }

   public int hashCode() {
      return (pcGettypeName(this) != null ? pcGettypeName(this).hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "typeName=" + ApplicationIdUtil.encode(pcGettypeName(this)) + ',' + super.toString();
   }

   public String getTypeName() {
      return pcGettypeName(this);
   }

   public void setTypeName(String typeName) {
      pcSettypeName(this, typeName);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"typeName"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$PKITypeScope != null ? class$Lcom$bea$common$security$store$data$PKITypeScope : (class$Lcom$bea$common$security$store$data$PKITypeScope = class$("com.bea.common.security.store.data.PKITypeScope")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "PKITypeScope", (PersistenceCapable)null);
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
      this.typeName = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 1 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.typeName = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.typeName);
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

   protected void pcCopyField(PKITypeScope var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.typeName = var1.typeName;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      PKITypeScope var3 = (PKITypeScope)var1;
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
      PKITypeScopeId var3 = (PKITypeScopeId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$PKITypeScopeId != null ? class$Lcom$bea$common$security$store$data$PKITypeScopeId : (class$Lcom$bea$common$security$store$data$PKITypeScopeId = class$("com.bea.common.security.store.data.PKITypeScopeId")), "typeName", true), var1.fetchStringField(0 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      PKITypeScopeId var2 = (PKITypeScopeId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$PKITypeScopeId != null ? class$Lcom$bea$common$security$store$data$PKITypeScopeId : (class$Lcom$bea$common$security$store$data$PKITypeScopeId = class$("com.bea.common.security.store.data.PKITypeScopeId")), "typeName", true), this.typeName);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      PKITypeScopeId var3 = (PKITypeScopeId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$PKITypeScopeId != null ? class$Lcom$bea$common$security$store$data$PKITypeScopeId : (class$Lcom$bea$common$security$store$data$PKITypeScopeId = class$("com.bea.common.security.store.data.PKITypeScopeId")), "typeName", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      PKITypeScopeId var2 = (PKITypeScopeId)var1;
      this.typeName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$PKITypeScopeId != null ? class$Lcom$bea$common$security$store$data$PKITypeScopeId : (class$Lcom$bea$common$security$store$data$PKITypeScopeId = class$("com.bea.common.security.store.data.PKITypeScopeId")), "typeName", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new PKITypeScopeId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new PKITypeScopeId();
   }

   private static final String pcGettypeName(PKITypeScope var0) {
      if (var0.pcStateManager == null) {
         return var0.typeName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.typeName;
      }
   }

   private static final void pcSettypeName(PKITypeScope var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.typeName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.typeName, var1, 0);
      }
   }
}
