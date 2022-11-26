package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class XACMLAuthorizationPolicy extends XACMLEntry implements PersistenceCapable {
   private static int pcInheritedFieldCount = XACMLEntry.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLEntry;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLAuthorizationPolicy;

   public XACMLAuthorizationPolicy() {
   }

   public XACMLAuthorizationPolicy(String domainName, String realmName, String typeName, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName, cn, xacmlVersion, xacmlDocument, xacmlStatus);
   }

   public XACMLAuthorizationPolicy(String domainName, String realmName, String typeName, Collection xacmlResourceScope, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName, xacmlResourceScope, cn, xacmlVersion, xacmlDocument, xacmlStatus);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof XACMLAuthorizationPolicy) ? false : super.equals(other);
      }
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$XACMLEntry != null ? class$Lcom$bea$common$security$store$data$XACMLEntry : (class$Lcom$bea$common$security$store$data$XACMLEntry = class$("com.bea.common.security.store.data.XACMLEntry"));
      pcFieldNames = new String[0];
      pcFieldTypes = new Class[0];
      pcFieldFlags = new byte[0];
      PCRegistry.register(class$Lcom$bea$common$security$store$data$XACMLAuthorizationPolicy != null ? class$Lcom$bea$common$security$store$data$XACMLAuthorizationPolicy : (class$Lcom$bea$common$security$store$data$XACMLAuthorizationPolicy = class$("com.bea.common.security.store.data.XACMLAuthorizationPolicy")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "XACMLAuthorizationPolicy", new XACMLAuthorizationPolicy());
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
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      XACMLAuthorizationPolicy var4 = new XACMLAuthorizationPolicy();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      XACMLAuthorizationPolicy var3 = new XACMLAuthorizationPolicy();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 0 + XACMLEntry.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         throw new IllegalArgumentException();
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
         throw new IllegalArgumentException();
      }
   }

   public void pcProvideFields(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.pcProvideField(var1[var2]);
      }

   }

   protected void pcCopyField(XACMLAuthorizationPolicy var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      XACMLAuthorizationPolicy var3 = (XACMLAuthorizationPolicy)var1;
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
      XACMLAuthorizationPolicyId var3 = (XACMLAuthorizationPolicyId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      XACMLAuthorizationPolicyId var2 = (XACMLAuthorizationPolicyId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      XACMLAuthorizationPolicyId var3 = (XACMLAuthorizationPolicyId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      XACMLAuthorizationPolicyId var2 = (XACMLAuthorizationPolicyId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new XACMLAuthorizationPolicyId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new XACMLAuthorizationPolicyId();
   }
}
