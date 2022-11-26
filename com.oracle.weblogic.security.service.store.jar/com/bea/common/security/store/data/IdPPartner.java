package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class IdPPartner extends Partner implements PersistenceCapable {
   private static final String SAML2_WEBSSO_SERVICE = "WEBSSOSERVICE";
   private String identityProviderNameMapperClassname;
   private boolean virtualUserEnabled;
   private Collection redirectURIs;
   private boolean processAttributes;
   private boolean wantAssertionSigned;
   private boolean wantAuthnRequestsSigned;
   private Collection audienceURIs;
   private Collection services;
   private static int pcInheritedFieldCount = Partner.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$Partner;
   // $FF: synthetic field
   static Class class$Ljava$util$Collection;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$IdPPartner;

   public boolean isWantAuthnRequestsSigned() {
      return pcGetwantAuthnRequestsSigned(this);
   }

   public void setWantAuthnRequestsSigned(boolean wantAuthnRequestsSigned) {
      pcSetwantAuthnRequestsSigned(this, wantAuthnRequestsSigned);
   }

   public boolean isWantAssertionSigned() {
      return pcGetwantAssertionSigned(this);
   }

   public void setWantAssertionSigned(boolean wantAssertionSigned) {
      pcSetwantAssertionSigned(this, wantAssertionSigned);
   }

   public String getIdentityProviderNameMapperClassname() {
      return pcGetidentityProviderNameMapperClassname(this);
   }

   public void setIdentityProviderNameMapperClassname(String identityProviderNameMapperClassname) {
      pcSetidentityProviderNameMapperClassname(this, identityProviderNameMapperClassname);
   }

   public Collection getRedirectURIs() {
      return pcGetredirectURIs(this);
   }

   public void setRedirectURIs(Collection redirectURIs) {
      pcSetredirectURIs(this, redirectURIs);
   }

   public boolean isVirtualUserEnabled() {
      return pcGetvirtualUserEnabled(this);
   }

   public void setVirtualUserEnabled(boolean virtualUserEnabled) {
      pcSetvirtualUserEnabled(this, virtualUserEnabled);
   }

   public Endpoint[] getSingleSignOnServices() {
      return this.getTypedServices("WEBSSOSERVICE");
   }

   public boolean isProcessAttributes() {
      return pcGetprocessAttributes(this);
   }

   public void setProcessAttributes(boolean processAttributes) {
      pcSetprocessAttributes(this, processAttributes);
   }

   public Collection getAudienceURIs() {
      return pcGetaudienceURIs(this);
   }

   public void setAudienceURIs(Collection audienceURIs) {
      pcSetaudienceURIs(this, audienceURIs);
   }

   public Collection getServices() {
      return pcGetservices(this);
   }

   public void setServices(Collection services) {
      pcSetservices(this, services);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$Partner != null ? class$Lcom$bea$common$security$store$data$Partner : (class$Lcom$bea$common$security$store$data$Partner = class$("com.bea.common.security.store.data.Partner"));
      pcFieldNames = new String[]{"audienceURIs", "identityProviderNameMapperClassname", "processAttributes", "redirectURIs", "services", "virtualUserEnabled", "wantAssertionSigned", "wantAuthnRequestsSigned"};
      pcFieldTypes = new Class[]{class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Boolean.TYPE, class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
      pcFieldFlags = new byte[]{10, 26, 26, 10, 10, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$IdPPartner != null ? class$Lcom$bea$common$security$store$data$IdPPartner : (class$Lcom$bea$common$security$store$data$IdPPartner = class$("com.bea.common.security.store.data.IdPPartner")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "IdPPartner", new IdPPartner());
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
      this.audienceURIs = null;
      this.identityProviderNameMapperClassname = null;
      this.processAttributes = false;
      this.redirectURIs = null;
      this.services = null;
      this.virtualUserEnabled = false;
      this.wantAssertionSigned = false;
      this.wantAuthnRequestsSigned = false;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      IdPPartner var4 = new IdPPartner();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      IdPPartner var3 = new IdPPartner();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 8 + Partner.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.audienceURIs = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 1:
               this.identityProviderNameMapperClassname = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.processAttributes = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 3:
               this.redirectURIs = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 4:
               this.services = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 5:
               this.virtualUserEnabled = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 6:
               this.wantAssertionSigned = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 7:
               this.wantAuthnRequestsSigned = this.pcStateManager.replaceBooleanField(this, var1);
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
               this.pcStateManager.providedObjectField(this, var1, this.audienceURIs);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.identityProviderNameMapperClassname);
               return;
            case 2:
               this.pcStateManager.providedBooleanField(this, var1, this.processAttributes);
               return;
            case 3:
               this.pcStateManager.providedObjectField(this, var1, this.redirectURIs);
               return;
            case 4:
               this.pcStateManager.providedObjectField(this, var1, this.services);
               return;
            case 5:
               this.pcStateManager.providedBooleanField(this, var1, this.virtualUserEnabled);
               return;
            case 6:
               this.pcStateManager.providedBooleanField(this, var1, this.wantAssertionSigned);
               return;
            case 7:
               this.pcStateManager.providedBooleanField(this, var1, this.wantAuthnRequestsSigned);
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

   protected void pcCopyField(IdPPartner var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.audienceURIs = var1.audienceURIs;
               return;
            case 1:
               this.identityProviderNameMapperClassname = var1.identityProviderNameMapperClassname;
               return;
            case 2:
               this.processAttributes = var1.processAttributes;
               return;
            case 3:
               this.redirectURIs = var1.redirectURIs;
               return;
            case 4:
               this.services = var1.services;
               return;
            case 5:
               this.virtualUserEnabled = var1.virtualUserEnabled;
               return;
            case 6:
               this.wantAssertionSigned = var1.wantAssertionSigned;
               return;
            case 7:
               this.wantAuthnRequestsSigned = var1.wantAuthnRequestsSigned;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      IdPPartner var3 = (IdPPartner)var1;
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
      IdPPartnerId var3 = (IdPPartnerId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      IdPPartnerId var2 = (IdPPartnerId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      IdPPartnerId var3 = (IdPPartnerId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      IdPPartnerId var2 = (IdPPartnerId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      throw new IllegalArgumentException("The id type \"class com.bea.common.security.store.data.IdPPartnerId\" specfied by persistent type \"class com.bea.common.security.store.data.IdPPartner\" does not have a public string or class + string constructor.");
   }

   public Object pcNewObjectIdInstance() {
      return new IdPPartnerId();
   }

   private static final Collection pcGetaudienceURIs(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.audienceURIs;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.audienceURIs;
      }
   }

   private static final void pcSetaudienceURIs(IdPPartner var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.audienceURIs = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.audienceURIs, var1, 0);
      }
   }

   private static final String pcGetidentityProviderNameMapperClassname(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.identityProviderNameMapperClassname;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.identityProviderNameMapperClassname;
      }
   }

   private static final void pcSetidentityProviderNameMapperClassname(IdPPartner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.identityProviderNameMapperClassname = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.identityProviderNameMapperClassname, var1, 0);
      }
   }

   private static final boolean pcGetprocessAttributes(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.processAttributes;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.processAttributes;
      }
   }

   private static final void pcSetprocessAttributes(IdPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.processAttributes = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 2, var0.processAttributes, var1, 0);
      }
   }

   private static final Collection pcGetredirectURIs(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.redirectURIs;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.redirectURIs;
      }
   }

   private static final void pcSetredirectURIs(IdPPartner var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.redirectURIs = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 3, var0.redirectURIs, var1, 0);
      }
   }

   private static final Collection pcGetservices(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.services;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.services;
      }
   }

   private static final void pcSetservices(IdPPartner var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.services = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 4, var0.services, var1, 0);
      }
   }

   private static final boolean pcGetvirtualUserEnabled(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.virtualUserEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.virtualUserEnabled;
      }
   }

   private static final void pcSetvirtualUserEnabled(IdPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.virtualUserEnabled = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 5, var0.virtualUserEnabled, var1, 0);
      }
   }

   private static final boolean pcGetwantAssertionSigned(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.wantAssertionSigned;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.wantAssertionSigned;
      }
   }

   private static final void pcSetwantAssertionSigned(IdPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.wantAssertionSigned = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 6, var0.wantAssertionSigned, var1, 0);
      }
   }

   private static final boolean pcGetwantAuthnRequestsSigned(IdPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.wantAuthnRequestsSigned;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.wantAuthnRequestsSigned;
      }
   }

   private static final void pcSetwantAuthnRequestsSigned(IdPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.wantAuthnRequestsSigned = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 7, var0.wantAuthnRequestsSigned, var1, 0);
      }
   }
}
