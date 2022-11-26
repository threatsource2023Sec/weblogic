package org.opensaml.security.x509.tls;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;

public class CertificateNameOptions implements Cloneable {
   private boolean evaluateSubjectDN;
   private boolean evaluateSubjectCommonName;
   private Set subjectAltNames = Collections.emptySet();
   private X500DNHandler x500DNHandler = new InternalX500DNHandler();
   private String x500SubjectDNFormat = "RFC2253";

   public boolean evaluateSubjectCommonName() {
      return this.evaluateSubjectCommonName;
   }

   public void setEvaluateSubjectCommonName(boolean flag) {
      this.evaluateSubjectCommonName = flag;
   }

   public boolean evaluateSubjectDN() {
      return this.evaluateSubjectDN;
   }

   public void setEvaluateSubjectDN(boolean flag) {
      this.evaluateSubjectDN = flag;
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Set getSubjectAltNames() {
      return ImmutableSet.copyOf(this.subjectAltNames);
   }

   public void setSubjectAltNames(@Nullable Set names) {
      if (names == null) {
         this.subjectAltNames = Collections.emptySet();
      } else {
         this.subjectAltNames = new HashSet();
         this.subjectAltNames.addAll(Collections2.filter(names, Predicates.notNull()));
      }
   }

   public X500DNHandler getX500DNHandler() {
      return this.x500DNHandler;
   }

   public void setX500DNHandler(X500DNHandler handler) {
      if (handler == null) {
         throw new IllegalArgumentException("X500DNHandler may not be null");
      } else {
         this.x500DNHandler = handler;
      }
   }

   public String getX500SubjectDNFormat() {
      return this.x500SubjectDNFormat;
   }

   public void setX500SubjectDNFormat(String format) {
      this.x500SubjectDNFormat = format;
   }

   public CertificateNameOptions clone() {
      CertificateNameOptions clonedOptions;
      try {
         clonedOptions = (CertificateNameOptions)super.clone();
      } catch (CloneNotSupportedException var3) {
         return null;
      }

      clonedOptions.subjectAltNames = new LinkedHashSet();
      clonedOptions.subjectAltNames.addAll(this.subjectAltNames);
      clonedOptions.x500DNHandler = this.x500DNHandler.clone();
      return clonedOptions;
   }
}
