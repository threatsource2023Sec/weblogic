package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.KeyTransportAlgorithmPredicate;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicEncryptionConfiguration extends BasicWhitelistBlacklistConfiguration implements EncryptionConfiguration {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BasicEncryptionConfiguration.class);
   @Nonnull
   @NonnullElements
   private List dataEncryptionCredentials = Collections.emptyList();
   @Nonnull
   @NonnullElements
   private List dataEncryptionAlgorithms = Collections.emptyList();
   @Nonnull
   @NonnullElements
   private List keyTransportEncryptionCredentials = Collections.emptyList();
   @Nonnull
   @NonnullElements
   private List keyTransportEncryptionAlgorithms = Collections.emptyList();
   @Nullable
   private NamedKeyInfoGeneratorManager dataKeyInfoGeneratorManager;
   @Nullable
   private NamedKeyInfoGeneratorManager keyTransportKeyInfoGeneratorManager;
   @Nullable
   private RSAOAEPParameters rsaOAEPParameters;
   private boolean rsaOAEPParametersMerge = true;
   @Nullable
   private KeyTransportAlgorithmPredicate keyTransportPredicate;

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getDataEncryptionCredentials() {
      return ImmutableList.copyOf(this.dataEncryptionCredentials);
   }

   public void setDataEncryptionCredentials(@Nullable List credentials) {
      if (credentials == null) {
         this.dataEncryptionCredentials = Collections.emptyList();
      } else {
         this.dataEncryptionCredentials = new ArrayList(Collections2.filter(credentials, Predicates.notNull()));
      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getDataEncryptionAlgorithms() {
      return ImmutableList.copyOf(this.dataEncryptionAlgorithms);
   }

   public void setDataEncryptionAlgorithms(@Nullable List algorithms) {
      if (algorithms == null) {
         this.dataEncryptionAlgorithms = Collections.emptyList();
      } else {
         this.dataEncryptionAlgorithms = new ArrayList(StringSupport.normalizeStringCollection(algorithms));
      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getKeyTransportEncryptionCredentials() {
      return ImmutableList.copyOf(this.keyTransportEncryptionCredentials);
   }

   public void setKeyTransportEncryptionCredentials(@Nullable List credentials) {
      if (credentials == null) {
         this.keyTransportEncryptionCredentials = Collections.emptyList();
      } else {
         this.keyTransportEncryptionCredentials = new ArrayList(Collections2.filter(credentials, Predicates.notNull()));
      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getKeyTransportEncryptionAlgorithms() {
      return ImmutableList.copyOf(this.keyTransportEncryptionAlgorithms);
   }

   public void setKeyTransportEncryptionAlgorithms(@Nullable List algorithms) {
      if (algorithms == null) {
         this.keyTransportEncryptionAlgorithms = Collections.emptyList();
      } else {
         this.keyTransportEncryptionAlgorithms = new ArrayList(StringSupport.normalizeStringCollection(algorithms));
      }
   }

   @Nullable
   public NamedKeyInfoGeneratorManager getDataKeyInfoGeneratorManager() {
      return this.dataKeyInfoGeneratorManager;
   }

   public void setDataKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
      this.dataKeyInfoGeneratorManager = keyInfoManager;
   }

   @Nullable
   public NamedKeyInfoGeneratorManager getKeyTransportKeyInfoGeneratorManager() {
      return this.keyTransportKeyInfoGeneratorManager;
   }

   public void setKeyTransportKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
      this.keyTransportKeyInfoGeneratorManager = keyInfoManager;
   }

   @Nullable
   public RSAOAEPParameters getRSAOAEPParameters() {
      return this.rsaOAEPParameters;
   }

   public void setRSAOAEPParameters(@Nullable RSAOAEPParameters params) {
      this.rsaOAEPParameters = params;
   }

   public boolean isRSAOAEPParametersMerge() {
      return this.rsaOAEPParametersMerge;
   }

   public void setRSAOAEPParametersMerge(boolean flag) {
      this.rsaOAEPParametersMerge = flag;
   }

   @Nullable
   public KeyTransportAlgorithmPredicate getKeyTransportAlgorithmPredicate() {
      return this.keyTransportPredicate;
   }

   public void setKeyTransportAlgorithmPredicate(KeyTransportAlgorithmPredicate predicate) {
      this.keyTransportPredicate = predicate;
   }
}
