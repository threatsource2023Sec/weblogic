package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingSignaturePrevalidator implements SignaturePrevalidator {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(ChainingSignaturePrevalidator.class);
   @Nonnull
   @NonnullElements
   private List validators;

   public ChainingSignaturePrevalidator(@Nonnull @ParameterName(name = "validatorChain") @NonnullElements List validatorChain) {
      Constraint.isNotNull(validatorChain, "SignaturePrevalidator list cannot be null");
      this.validators = new ArrayList(Collections2.filter(validatorChain, Predicates.notNull()));
   }

   public void validate(@Nonnull Signature signature) throws SignatureException {
      Iterator var2 = this.validators.iterator();

      while(var2.hasNext()) {
         SignaturePrevalidator validator = (SignaturePrevalidator)var2.next();
         this.log.debug("Validating signature using prevalidator: {}", validator.getClass().getName());
         validator.validate(signature);
      }

   }
}
