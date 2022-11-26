package com.bea.common.security.store.data;

import com.bea.common.security.utils.encoders.BASE64Decoder;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class SPPartner extends Partner implements PersistenceCapable {
   private static final String SAML2_ASSERTION_CONSUMER_SERVICE = "ASSERTIONCONSUMERSERVICE";
   private String servicePartnerNameMapperClassName;
   private int timeToLive;
   private int timeToLiveOffset;
   private boolean includeOneTimeUseCondition;
   private boolean generateAttributes;
   private boolean wantAssertionSigned;
   private boolean keyinfoInclude;
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
   static Class class$Lcom$bea$common$security$store$data$SPPartner;

   public boolean isWantAssertionSigned() {
      return pcGetwantAssertionSigned(this);
   }

   public void setWantAssertionSigned(boolean wantAssertionSigned) {
      pcSetwantAssertionSigned(this, wantAssertionSigned);
   }

   public boolean isGenerateAttributes() {
      return pcGetgenerateAttributes(this);
   }

   public void setGenerateAttributes(boolean generateAttributes) {
      pcSetgenerateAttributes(this, generateAttributes);
   }

   public boolean isIncludeOneTimeUseCondition() {
      return pcGetincludeOneTimeUseCondition(this);
   }

   public void setIncludeOneTimeUseCondition(boolean includeOneTimeUseCondition) {
      pcSetincludeOneTimeUseCondition(this, includeOneTimeUseCondition);
   }

   public boolean isKeyinfoInclude() {
      return pcGetkeyinfoInclude(this);
   }

   public void setKeyinfoInclude(boolean keyinfoInclude) {
      pcSetkeyinfoInclude(this, keyinfoInclude);
   }

   public String getServicePartnerNameMapperClassName() {
      return pcGetservicePartnerNameMapperClassName(this);
   }

   public void setServicePartnerNameMapperClassName(String servicePartnerNameMapperClassName) {
      pcSetservicePartnerNameMapperClassName(this, servicePartnerNameMapperClassName);
   }

   public int getTimeToLive() {
      return pcGettimeToLive(this);
   }

   public void setTimeToLive(int timeToLive) {
      pcSettimeToLive(this, timeToLive);
   }

   public int getTimeToLiveOffset() {
      return pcGettimeToLiveOffset(this);
   }

   public void setTimeToLiveOffset(int timeToLiveOffset) {
      pcSettimeToLiveOffset(this, timeToLiveOffset);
   }

   public boolean isWantAuthnRequestsSigned() {
      return pcGetwantAuthnRequestsSigned(this);
   }

   public void setWantAuthnRequestsSigned(boolean wantAuthnRequestsSigned) {
      pcSetwantAuthnRequestsSigned(this, wantAuthnRequestsSigned);
   }

   public Endpoint[] getAssertionConsumerServices() {
      return this.getTypedServices("ASSERTIONCONSUMERSERVICE");
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

   public byte[] getEncryptionCert() {
      byte[] cert = null;
      if (super.getSigningCert() != null) {
         cert = (new SPPartnerExtendedMetaData(super.getSigningCert())).getEncryptionCert();
      }

      return cert;
   }

   public void setEncryptionCert(byte[] encryptionCert) {
      SPPartnerExtendedMetaData sPPartnerExtendedMetaData = new SPPartnerExtendedMetaData(super.getSigningCert());
      sPPartnerExtendedMetaData.setEncryptionCert(encryptionCert);
      super.setSigningCert(sPPartnerExtendedMetaData.toString().getBytes());
   }

   public List getEncryptionAlgorithms() {
      List algorithms = null;
      if (super.getSigningCert() != null) {
         algorithms = (new SPPartnerExtendedMetaData(super.getSigningCert())).getEncryptionAlgorithmIDs();
      }

      return algorithms;
   }

   public void setEncryptionAlgorithms(List algorithms) {
      SPPartnerExtendedMetaData sPPartnerExtendedMetaData = new SPPartnerExtendedMetaData(super.getSigningCert());
      sPPartnerExtendedMetaData.setEncryptionAlgorithmIDs(algorithms);
      super.setSigningCert(sPPartnerExtendedMetaData.toString().getBytes());
   }

   public void setEncryptionCertObject(X509Certificate cert) throws CertificateException {
      this.setEncryptionCert(this.serializeX509Certificate(cert));
   }

   public X509Certificate getEncryptionCertObject() throws CertificateException {
      return this.deserializeX509Certificate(this.getEncryptionCert());
   }

   public byte[] getSigningCert() {
      byte[] cert = null;
      if (super.getSigningCert() != null) {
         cert = (new SPPartnerExtendedMetaData(super.getSigningCert())).getSigningCert();
      }

      return cert;
   }

   public void setSigningCert(byte[] signingCert) {
      SPPartnerExtendedMetaData sPPartnerExtendedMetaData = new SPPartnerExtendedMetaData(super.getSigningCert());
      sPPartnerExtendedMetaData.setSigningCert(signingCert);
      super.setSigningCert(sPPartnerExtendedMetaData.toString().getBytes());
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$Partner != null ? class$Lcom$bea$common$security$store$data$Partner : (class$Lcom$bea$common$security$store$data$Partner = class$("com.bea.common.security.store.data.Partner"));
      pcFieldNames = new String[]{"audienceURIs", "generateAttributes", "includeOneTimeUseCondition", "keyinfoInclude", "servicePartnerNameMapperClassName", "services", "timeToLive", "timeToLiveOffset", "wantAssertionSigned", "wantAuthnRequestsSigned"};
      pcFieldTypes = new Class[]{class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
      pcFieldFlags = new byte[]{10, 26, 26, 26, 26, 10, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$SPPartner != null ? class$Lcom$bea$common$security$store$data$SPPartner : (class$Lcom$bea$common$security$store$data$SPPartner = class$("com.bea.common.security.store.data.SPPartner")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "SPPartner", new SPPartner());
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
      this.generateAttributes = false;
      this.includeOneTimeUseCondition = false;
      this.keyinfoInclude = false;
      this.servicePartnerNameMapperClassName = null;
      this.services = null;
      this.timeToLive = 0;
      this.timeToLiveOffset = 0;
      this.wantAssertionSigned = false;
      this.wantAuthnRequestsSigned = false;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      SPPartner var4 = new SPPartner();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      SPPartner var3 = new SPPartner();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 10 + Partner.pcGetManagedFieldCount();
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
               this.generateAttributes = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 2:
               this.includeOneTimeUseCondition = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 3:
               this.keyinfoInclude = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 4:
               this.servicePartnerNameMapperClassName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.services = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 6:
               this.timeToLive = this.pcStateManager.replaceIntField(this, var1);
               return;
            case 7:
               this.timeToLiveOffset = this.pcStateManager.replaceIntField(this, var1);
               return;
            case 8:
               this.wantAssertionSigned = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 9:
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
               this.pcStateManager.providedBooleanField(this, var1, this.generateAttributes);
               return;
            case 2:
               this.pcStateManager.providedBooleanField(this, var1, this.includeOneTimeUseCondition);
               return;
            case 3:
               this.pcStateManager.providedBooleanField(this, var1, this.keyinfoInclude);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.servicePartnerNameMapperClassName);
               return;
            case 5:
               this.pcStateManager.providedObjectField(this, var1, this.services);
               return;
            case 6:
               this.pcStateManager.providedIntField(this, var1, this.timeToLive);
               return;
            case 7:
               this.pcStateManager.providedIntField(this, var1, this.timeToLiveOffset);
               return;
            case 8:
               this.pcStateManager.providedBooleanField(this, var1, this.wantAssertionSigned);
               return;
            case 9:
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

   protected void pcCopyField(SPPartner var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.audienceURIs = var1.audienceURIs;
               return;
            case 1:
               this.generateAttributes = var1.generateAttributes;
               return;
            case 2:
               this.includeOneTimeUseCondition = var1.includeOneTimeUseCondition;
               return;
            case 3:
               this.keyinfoInclude = var1.keyinfoInclude;
               return;
            case 4:
               this.servicePartnerNameMapperClassName = var1.servicePartnerNameMapperClassName;
               return;
            case 5:
               this.services = var1.services;
               return;
            case 6:
               this.timeToLive = var1.timeToLive;
               return;
            case 7:
               this.timeToLiveOffset = var1.timeToLiveOffset;
               return;
            case 8:
               this.wantAssertionSigned = var1.wantAssertionSigned;
               return;
            case 9:
               this.wantAuthnRequestsSigned = var1.wantAuthnRequestsSigned;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      SPPartner var3 = (SPPartner)var1;
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
      SPPartnerId var3 = (SPPartnerId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      SPPartnerId var2 = (SPPartnerId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      SPPartnerId var3 = (SPPartnerId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      SPPartnerId var2 = (SPPartnerId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      throw new IllegalArgumentException("The id type \"class com.bea.common.security.store.data.SPPartnerId\" specfied by persistent type \"class com.bea.common.security.store.data.SPPartner\" does not have a public string or class + string constructor.");
   }

   public Object pcNewObjectIdInstance() {
      return new SPPartnerId();
   }

   private static final Collection pcGetaudienceURIs(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.audienceURIs;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.audienceURIs;
      }
   }

   private static final void pcSetaudienceURIs(SPPartner var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.audienceURIs = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.audienceURIs, var1, 0);
      }
   }

   private static final boolean pcGetgenerateAttributes(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.generateAttributes;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.generateAttributes;
      }
   }

   private static final void pcSetgenerateAttributes(SPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.generateAttributes = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 1, var0.generateAttributes, var1, 0);
      }
   }

   private static final boolean pcGetincludeOneTimeUseCondition(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.includeOneTimeUseCondition;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.includeOneTimeUseCondition;
      }
   }

   private static final void pcSetincludeOneTimeUseCondition(SPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.includeOneTimeUseCondition = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 2, var0.includeOneTimeUseCondition, var1, 0);
      }
   }

   private static final boolean pcGetkeyinfoInclude(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.keyinfoInclude;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.keyinfoInclude;
      }
   }

   private static final void pcSetkeyinfoInclude(SPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.keyinfoInclude = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 3, var0.keyinfoInclude, var1, 0);
      }
   }

   private static final String pcGetservicePartnerNameMapperClassName(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.servicePartnerNameMapperClassName;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.servicePartnerNameMapperClassName;
      }
   }

   private static final void pcSetservicePartnerNameMapperClassName(SPPartner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.servicePartnerNameMapperClassName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.servicePartnerNameMapperClassName, var1, 0);
      }
   }

   private static final Collection pcGetservices(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.services;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.services;
      }
   }

   private static final void pcSetservices(SPPartner var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.services = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 5, var0.services, var1, 0);
      }
   }

   private static final int pcGettimeToLive(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.timeToLive;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.timeToLive;
      }
   }

   private static final void pcSettimeToLive(SPPartner var0, int var1) {
      if (var0.pcStateManager == null) {
         var0.timeToLive = var1;
      } else {
         var0.pcStateManager.settingIntField(var0, pcInheritedFieldCount + 6, var0.timeToLive, var1, 0);
      }
   }

   private static final int pcGettimeToLiveOffset(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.timeToLiveOffset;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.timeToLiveOffset;
      }
   }

   private static final void pcSettimeToLiveOffset(SPPartner var0, int var1) {
      if (var0.pcStateManager == null) {
         var0.timeToLiveOffset = var1;
      } else {
         var0.pcStateManager.settingIntField(var0, pcInheritedFieldCount + 7, var0.timeToLiveOffset, var1, 0);
      }
   }

   private static final boolean pcGetwantAssertionSigned(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.wantAssertionSigned;
      } else {
         int var1 = pcInheritedFieldCount + 8;
         var0.pcStateManager.accessingField(var1);
         return var0.wantAssertionSigned;
      }
   }

   private static final void pcSetwantAssertionSigned(SPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.wantAssertionSigned = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 8, var0.wantAssertionSigned, var1, 0);
      }
   }

   private static final boolean pcGetwantAuthnRequestsSigned(SPPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.wantAuthnRequestsSigned;
      } else {
         int var1 = pcInheritedFieldCount + 9;
         var0.pcStateManager.accessingField(var1);
         return var0.wantAuthnRequestsSigned;
      }
   }

   private static final void pcSetwantAuthnRequestsSigned(SPPartner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.wantAuthnRequestsSigned = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 9, var0.wantAuthnRequestsSigned, var1, 0);
      }
   }

   public static class SPPartnerExtendedMetaData {
      private static final String SIGNING_CERT_BEGIN_TAG = "SIGNING_CERT:";
      private static final String ENCRYPTION_CERT_BEGIN_TAG = "ENCRYPTION_CERT:";
      private static final String ENCRYPTION_ALGORITHMS_BEGIN_TAG = "ENCRYPTION_ALGORITHMS:";
      private static final String BEGIN_TAG_BRACKET = "{";
      private static final String END_TAG_BRACKET = "}";
      private static final String CERT_OR_ALGS = "(.*)";
      private static final Pattern CERT_ALGS_PATTERN = Pattern.compile("\\{SIGNING_CERT:(.*)\\}\\{ENCRYPTION_CERT:(.*)\\}\\{ENCRYPTION_ALGORITHMS:(.*)\\}");
      private byte[] signingCert;
      private byte[] encryptionCert;
      private List encryptionAlgorithmIDs;

      public SPPartnerExtendedMetaData() {
      }

      public SPPartnerExtendedMetaData(byte[] certsAndAlgorithms) {
         if (certsAndAlgorithms != null) {
            this.parseCertsAndAlgorithms(certsAndAlgorithms);
         }

      }

      private void parseCertsAndAlgorithms(byte[] certsAndAlgorithms) {
         if (beginsWith(certsAndAlgorithms, "{SIGNING_CERT:".getBytes())) {
            String base64CertsAndAlgorithms = new String(certsAndAlgorithms);
            Matcher m = CERT_ALGS_PATTERN.matcher(base64CertsAndAlgorithms);
            if (m.matches()) {
               String signingCertEncoding = m.group(1);
               if (signingCertEncoding != null && signingCertEncoding.length() > 0) {
                  try {
                     this.signingCert = (new BASE64Decoder()).decodeBuffer(signingCertEncoding);
                  } catch (IOException var13) {
                     throw new IllegalArgumentException("Signing certificate cannot be BASE64 decoded.", var13);
                  }
               }

               String encryptionCertEncoding = m.group(2);
               if (encryptionCertEncoding != null && encryptionCertEncoding.length() > 0) {
                  try {
                     this.encryptionCert = (new BASE64Decoder()).decodeBuffer(encryptionCertEncoding);
                  } catch (IOException var12) {
                     throw new IllegalArgumentException("Encryption certificate cannot be BASE64 decoded.", var12);
                  }
               }

               String encryptionAlgorithmsEncoding = m.group(3);
               if (encryptionAlgorithmsEncoding != null && encryptionAlgorithmsEncoding.length() > 0) {
                  String[] algorithms = encryptionAlgorithmsEncoding.split(",");
                  if (algorithms != null && algorithms.length > 0) {
                     this.encryptionAlgorithmIDs = new ArrayList(algorithms.length);
                     String[] var8 = algorithms;
                     int var9 = algorithms.length;

                     for(int var10 = 0; var10 < var9; ++var10) {
                        String s = var8[var10];
                        this.encryptionAlgorithmIDs.add(s);
                     }
                  }
               }
            }
         } else {
            this.signingCert = certsAndAlgorithms;
         }

      }

      private static boolean beginsWith(byte[] array, byte[] subArray) {
         boolean result = false;
         if (array != null && subArray != null && array.length >= subArray.length) {
            result = true;

            for(int i = 0; i < subArray.length; ++i) {
               if (subArray[i] != array[i]) {
                  result = false;
                  break;
               }
            }
         }

         return result;
      }

      private static String joinStrings(List strings, String delimiter) {
         String result = null;
         if (strings != null) {
            if (strings.size() == 1) {
               result = (String)strings.get(0);
            } else if (strings.size() > 1) {
               StringBuilder stringBuilder = new StringBuilder();

               for(int i = 0; i < strings.size() - 1; ++i) {
                  stringBuilder.append((String)strings.get(i));
                  stringBuilder.append(delimiter);
               }

               stringBuilder.append((String)strings.get(strings.size() - 1));
               result = stringBuilder.toString();
            }
         }

         return result;
      }

      public byte[] getSigningCert() {
         return this.signingCert;
      }

      public void setSigningCert(byte[] signingCert) {
         this.signingCert = signingCert;
      }

      public byte[] getEncryptionCert() {
         return this.encryptionCert;
      }

      public void setEncryptionCert(byte[] encryptionCert) {
         this.encryptionCert = encryptionCert;
      }

      public List getEncryptionAlgorithmIDs() {
         return this.encryptionAlgorithmIDs;
      }

      public void setEncryptionAlgorithmIDs(List encryptionAlgorithmIDs) {
         this.encryptionAlgorithmIDs = encryptionAlgorithmIDs;
      }

      public String toString() {
         String encodedSigningCert = this.signingCert == null ? "" : (new BASE64Encoder()).encodeBuffer(this.signingCert);
         String encodedEncryptionCert = this.encryptionCert == null ? "" : (new BASE64Encoder()).encodeBuffer(this.encryptionCert);
         String joinedAlgorithms = this.encryptionAlgorithmIDs != null && this.encryptionAlgorithmIDs.size() > 0 ? joinStrings(this.encryptionAlgorithmIDs, ",") : "";
         return "{SIGNING_CERT:" + encodedSigningCert + "}" + "{" + "ENCRYPTION_CERT:" + encodedEncryptionCert + "}" + "{" + "ENCRYPTION_ALGORITHMS:" + joinedAlgorithms + "}";
      }
   }
}
