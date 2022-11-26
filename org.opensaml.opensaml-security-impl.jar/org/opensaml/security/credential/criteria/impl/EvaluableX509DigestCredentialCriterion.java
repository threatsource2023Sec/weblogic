package org.opensaml.security.credential.criteria.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509DigestCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvaluableX509DigestCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableX509DigestCredentialCriterion.class);
   private final String algorithm;
   private final byte[] x509digest;

   public EvaluableX509DigestCredentialCriterion(@Nonnull X509DigestCriterion criteria) {
      this.algorithm = ((X509DigestCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getAlgorithm();
      this.x509digest = criteria.getDigest();
   }

   public EvaluableX509DigestCredentialCriterion(@Nonnull String alg, @Nonnull byte[] digest) {
      this.x509digest = Constraint.isNotEmpty(digest, "X.509 digest cannot be null or empty");
      String trimmed = StringSupport.trimOrNull(alg);
      Constraint.isNotNull(trimmed, "Certificate digest algorithm cannot be null or empty");
      this.algorithm = trimmed;
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else if (!(target instanceof X509Credential)) {
         this.log.info("Credential is not an X509Credential, does not satisfy X.509 digest criteria");
         return false;
      } else {
         X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
         if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return false;
         } else {
            try {
               MessageDigest hasher = MessageDigest.getInstance(this.algorithm);
               byte[] hashed = hasher.digest(entityCert.getEncoded());
               return Arrays.equals(hashed, this.x509digest);
            } catch (CertificateEncodingException var5) {
               this.log.error("Unable to encode certificate for digest operation", var5);
            } catch (NoSuchAlgorithmException var6) {
               this.log.error("Unable to obtain a digest implementation for algorithm {" + this.algorithm + "}", var6);
            }

            return this.isUnevaluableSatisfies();
         }
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableX509DigestCredentialCriterion [algorithm=");
      builder.append(this.algorithm);
      builder.append(", x509digest=");
      builder.append(Hex.encodeHexString(this.x509digest));
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      int result = 17;
      result = result * 37 + this.algorithm.hashCode();
      result = result * 37 + this.x509digest.hashCode();
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof EvaluableX509DigestCredentialCriterion)) {
         return false;
      } else {
         EvaluableX509DigestCredentialCriterion other = (EvaluableX509DigestCredentialCriterion)obj;
         return this.algorithm.equals(other.algorithm) && Arrays.equals(this.x509digest, other.x509digest);
      }
   }
}
