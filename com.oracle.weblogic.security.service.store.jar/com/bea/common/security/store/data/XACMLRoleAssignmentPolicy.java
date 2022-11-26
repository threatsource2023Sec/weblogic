package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class XACMLRoleAssignmentPolicy extends XACMLEntry implements PersistenceCapable {
   private Collection xacmlRole;
   private static int pcInheritedFieldCount = XACMLEntry.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLEntry;
   // $FF: synthetic field
   static Class class$Ljava$util$Collection;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLRoleAssignmentPolicy;

   public XACMLRoleAssignmentPolicy() {
   }

   public XACMLRoleAssignmentPolicy(String domainName, String realmName, String typeName, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName, cn, xacmlVersion, xacmlDocument, xacmlStatus);
   }

   public XACMLRoleAssignmentPolicy(String domainName, String realmName, String typeName, Collection xacmlResourceScope, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName, xacmlResourceScope, cn, xacmlVersion, xacmlDocument, xacmlStatus);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof XACMLRoleAssignmentPolicy) ? false : super.equals(other);
      }
   }

   public Collection getXacmlRole() {
      return pcGetxacmlRole(this);
   }

   public void setXacmlRole(Collection xacmlRole) {
      pcSetxacmlRole(this, xacmlRole);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$XACMLEntry != null ? class$Lcom$bea$common$security$store$data$XACMLEntry : (class$Lcom$bea$common$security$store$data$XACMLEntry = class$("com.bea.common.security.store.data.XACMLEntry"));
      pcFieldNames = new String[]{"xacmlRole"};
      pcFieldTypes = new Class[]{class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection"))};
      pcFieldFlags = new byte[]{5};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$XACMLRoleAssignmentPolicy != null ? class$Lcom$bea$common$security$store$data$XACMLRoleAssignmentPolicy : (class$Lcom$bea$common$security$store$data$XACMLRoleAssignmentPolicy = class$("com.bea.common.security.store.data.XACMLRoleAssignmentPolicy")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "XACMLRoleAssignmentPolicy", new XACMLRoleAssignmentPolicy());
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
      this.xacmlRole = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      XACMLRoleAssignmentPolicy var4 = new XACMLRoleAssignmentPolicy();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      XACMLRoleAssignmentPolicy var3 = new XACMLRoleAssignmentPolicy();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 1 + XACMLEntry.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.xacmlRole = (Collection)this.pcStateManager.replaceObjectField(this, var1);
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
               this.pcStateManager.providedObjectField(this, var1, this.xacmlRole);
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

   protected void pcCopyField(XACMLRoleAssignmentPolicy var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.xacmlRole = var1.xacmlRole;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      XACMLRoleAssignmentPolicy var3 = (XACMLRoleAssignmentPolicy)var1;
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
      XACMLRoleAssignmentPolicyId var3 = (XACMLRoleAssignmentPolicyId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      XACMLRoleAssignmentPolicyId var2 = (XACMLRoleAssignmentPolicyId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      XACMLRoleAssignmentPolicyId var3 = (XACMLRoleAssignmentPolicyId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      XACMLRoleAssignmentPolicyId var2 = (XACMLRoleAssignmentPolicyId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new XACMLRoleAssignmentPolicyId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new XACMLRoleAssignmentPolicyId();
   }

   private static final Collection pcGetxacmlRole(XACMLRoleAssignmentPolicy var0) {
      if (var0.pcStateManager == null) {
         return var0.xacmlRole;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.xacmlRole;
      }
   }

   private static final void pcSetxacmlRole(XACMLRoleAssignmentPolicy var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.xacmlRole = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.xacmlRole, var1, 0);
      }
   }
}
