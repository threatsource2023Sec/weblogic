package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;

public class Endpoint extends DomainRealmScope implements PersistenceCapable {
   private String partnerName;
   private int index;
   private String bindingLocation;
   private boolean defaultEndPoint;
   private boolean defaultSet;
   private String bindingType;
   private String serviceType;
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
   static Class class$Lcom$bea$common$security$store$data$Endpoint;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$EndpointId;

   public String getBindingLocation() {
      return pcGetbindingLocation(this);
   }

   public void setBindingLocation(String bindingLocation) {
      pcSetbindingLocation(this, bindingLocation);
   }

   public String getBindingType() {
      return pcGetbindingType(this);
   }

   public void setBindingType(String bindingType) {
      pcSetbindingType(this, bindingType);
   }

   public boolean isDefaultEndPoint() {
      return pcGetdefaultEndPoint(this);
   }

   public void setDefaultEndPoint(boolean defaultEndPoint) {
      pcSetdefaultEndPoint(this, defaultEndPoint);
   }

   public boolean isDefaultSet() {
      return pcGetdefaultSet(this);
   }

   public void setDefaultSet(boolean defaultSet) {
      pcSetdefaultSet(this, defaultSet);
   }

   public int getIndex() {
      return pcGetindex(this);
   }

   public void setIndex(int index) {
      pcSetindex(this, index);
   }

   public String getPartnerName() {
      return pcGetpartnerName(this);
   }

   public void setPartnerName(String partnerName) {
      pcSetpartnerName(this, partnerName);
   }

   public String getServiceType() {
      return pcGetserviceType(this);
   }

   public void setServiceType(String serviceType) {
      pcSetserviceType(this, serviceType);
   }

   public String toString() {
      return "bindingLocation=" + pcGetbindingLocation(this) + ", bindingType=" + pcGetbindingType(this) + ", partnerName=" + pcGetpartnerName(this) + ", serviceType=" + pcGetserviceType(this) + ", " + super.toString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof Endpoint)) {
         return false;
      } else {
         Endpoint e = (Endpoint)other;
         return pcGetbindingLocation(e).equals(pcGetbindingLocation(this)) && pcGetbindingType(e).equals(pcGetbindingType(this)) && pcGetpartnerName(e).equals(pcGetpartnerName(this)) && pcGetserviceType(e).equals(pcGetserviceType(this));
      }
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"bindingLocation", "bindingType", "defaultEndPoint", "defaultSet", "index", "partnerName", "serviceType"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Boolean.TYPE, Boolean.TYPE, Integer.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$Endpoint != null ? class$Lcom$bea$common$security$store$data$Endpoint : (class$Lcom$bea$common$security$store$data$Endpoint = class$("com.bea.common.security.store.data.Endpoint")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "Endpoint", new Endpoint());
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
      this.bindingLocation = null;
      this.bindingType = null;
      this.defaultEndPoint = false;
      this.defaultSet = false;
      this.index = 0;
      this.partnerName = null;
      this.serviceType = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      Endpoint var4 = new Endpoint();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      Endpoint var3 = new Endpoint();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 7 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.bindingLocation = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.bindingType = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.defaultEndPoint = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 3:
               this.defaultSet = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 4:
               this.index = this.pcStateManager.replaceIntField(this, var1);
               return;
            case 5:
               this.partnerName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.serviceType = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.bindingLocation);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.bindingType);
               return;
            case 2:
               this.pcStateManager.providedBooleanField(this, var1, this.defaultEndPoint);
               return;
            case 3:
               this.pcStateManager.providedBooleanField(this, var1, this.defaultSet);
               return;
            case 4:
               this.pcStateManager.providedIntField(this, var1, this.index);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.partnerName);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.serviceType);
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

   protected void pcCopyField(Endpoint var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.bindingLocation = var1.bindingLocation;
               return;
            case 1:
               this.bindingType = var1.bindingType;
               return;
            case 2:
               this.defaultEndPoint = var1.defaultEndPoint;
               return;
            case 3:
               this.defaultSet = var1.defaultSet;
               return;
            case 4:
               this.index = var1.index;
               return;
            case 5:
               this.partnerName = var1.partnerName;
               return;
            case 6:
               this.serviceType = var1.serviceType;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      Endpoint var3 = (Endpoint)var1;
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
      EndpointId var3 = (EndpointId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingLocation", true), var1.fetchStringField(0 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingType", true), var1.fetchStringField(1 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "partnerName", true), var1.fetchStringField(5 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "serviceType", true), var1.fetchStringField(6 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      EndpointId var2 = (EndpointId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingLocation", true), this.bindingLocation);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingType", true), this.bindingType);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "partnerName", true), this.partnerName);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "serviceType", true), this.serviceType);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      EndpointId var3 = (EndpointId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingLocation", true)));
      var1.storeStringField(1 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingType", true)));
      var1.storeStringField(5 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "partnerName", true)));
      var1.storeStringField(6 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "serviceType", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      EndpointId var2 = (EndpointId)var1;
      this.bindingLocation = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingLocation", true));
      this.bindingType = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "bindingType", true));
      this.partnerName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "partnerName", true));
      this.serviceType = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$EndpointId != null ? class$Lcom$bea$common$security$store$data$EndpointId : (class$Lcom$bea$common$security$store$data$EndpointId = class$("com.bea.common.security.store.data.EndpointId")), "serviceType", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      throw new IllegalArgumentException("The id type \"class com.bea.common.security.store.data.EndpointId\" specfied by persistent type \"class com.bea.common.security.store.data.Endpoint\" does not have a public string or class + string constructor.");
   }

   public Object pcNewObjectIdInstance() {
      return new EndpointId();
   }

   private static final String pcGetbindingLocation(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.bindingLocation;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.bindingLocation;
      }
   }

   private static final void pcSetbindingLocation(Endpoint var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.bindingLocation = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.bindingLocation, var1, 0);
      }
   }

   private static final String pcGetbindingType(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.bindingType;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.bindingType;
      }
   }

   private static final void pcSetbindingType(Endpoint var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.bindingType = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.bindingType, var1, 0);
      }
   }

   private static final boolean pcGetdefaultEndPoint(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.defaultEndPoint;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.defaultEndPoint;
      }
   }

   private static final void pcSetdefaultEndPoint(Endpoint var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.defaultEndPoint = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 2, var0.defaultEndPoint, var1, 0);
      }
   }

   private static final boolean pcGetdefaultSet(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.defaultSet;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.defaultSet;
      }
   }

   private static final void pcSetdefaultSet(Endpoint var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.defaultSet = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 3, var0.defaultSet, var1, 0);
      }
   }

   private static final int pcGetindex(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.index;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.index;
      }
   }

   private static final void pcSetindex(Endpoint var0, int var1) {
      if (var0.pcStateManager == null) {
         var0.index = var1;
      } else {
         var0.pcStateManager.settingIntField(var0, pcInheritedFieldCount + 4, var0.index, var1, 0);
      }
   }

   private static final String pcGetpartnerName(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.partnerName;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.partnerName;
      }
   }

   private static final void pcSetpartnerName(Endpoint var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.partnerName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.partnerName, var1, 0);
      }
   }

   private static final String pcGetserviceType(Endpoint var0) {
      if (var0.pcStateManager == null) {
         return var0.serviceType;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.serviceType;
      }
   }

   private static final void pcSetserviceType(Endpoint var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.serviceType = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.serviceType, var1, 0);
      }
   }
}
