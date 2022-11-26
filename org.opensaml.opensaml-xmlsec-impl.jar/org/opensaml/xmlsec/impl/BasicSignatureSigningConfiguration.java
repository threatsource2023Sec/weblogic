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
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureSigningConfiguration extends BasicWhitelistBlacklistConfiguration implements SignatureSigningConfiguration {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BasicSignatureSigningConfiguration.class);
   @Nonnull
   @NonnullElements
   private List signingCredentials = Collections.emptyList();
   @Nonnull
   @NonnullElements
   private List signatureAlgorithms = Collections.emptyList();
   @Nonnull
   @NonnullElements
   private List signatureReferenceDigestMethods = Collections.emptyList();
   @Nullable
   private String signatureCanonicalization;
   @Nullable
   private Integer signatureHMACOutputLength;
   @Nullable
   private NamedKeyInfoGeneratorManager keyInfoGeneratorManager;

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getSigningCredentials() {
      return ImmutableList.copyOf(this.signingCredentials);
   }

   public void setSigningCredentials(@Nullable List credentials) {
      if (credentials == null) {
         this.signingCredentials = Collections.emptyList();
      } else {
         this.signingCredentials = new ArrayList(Collections2.filter(credentials, Predicates.notNull()));
      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getSignatureAlgorithms() {
      return ImmutableList.copyOf(this.signatureAlgorithms);
   }

   public void setSignatureAlgorithms(@Nullable List algorithms) {
      if (algorithms == null) {
         this.signatureAlgorithms = Collections.emptyList();
      } else {
         this.signatureAlgorithms = new ArrayList(StringSupport.normalizeStringCollection(algorithms));
      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getSignatureReferenceDigestMethods() {
      return ImmutableList.copyOf(this.signatureReferenceDigestMethods);
   }

   public void setSignatureReferenceDigestMethods(@Nullable List algorithms) {
      if (algorithms == null) {
         this.signatureReferenceDigestMethods = Collections.emptyList();
      } else {
         this.signatureReferenceDigestMethods = new ArrayList(StringSupport.normalizeStringCollection(algorithms));
      }
   }

   @Nullable
   public String getSignatureCanonicalizationAlgorithm() {
      return this.signatureCanonicalization;
   }

   public void setSignatureCanonicalizationAlgorithm(@Nullable String algorithmURI) {
      this.signatureCanonicalization = StringSupport.trimOrNull(algorithmURI);
   }

   @Nullable
   public Integer getSignatureHMACOutputLength() {
      return this.signatureHMACOutputLength;
   }

   public void setSignatureHMACOutputLength(@Nullable Integer length) {
      this.signatureHMACOutputLength = length;
   }

   @Nullable
   public NamedKeyInfoGeneratorManager getKeyInfoGeneratorManager() {
      return this.keyInfoGeneratorManager;
   }

   public void setKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
      this.keyInfoGeneratorManager = keyInfoManager;
   }
}
