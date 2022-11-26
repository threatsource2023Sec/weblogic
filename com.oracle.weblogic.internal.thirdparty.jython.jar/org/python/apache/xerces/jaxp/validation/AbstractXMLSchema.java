package org.python.apache.xerces.jaxp.validation;

import java.util.HashMap;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;

abstract class AbstractXMLSchema extends Schema implements XSGrammarPoolContainer {
   private final HashMap fFeatures = new HashMap();

   public AbstractXMLSchema() {
   }

   public final Validator newValidator() {
      return new ValidatorImpl(this);
   }

   public final ValidatorHandler newValidatorHandler() {
      return new ValidatorHandlerImpl(this);
   }

   public final Boolean getFeature(String var1) {
      return (Boolean)this.fFeatures.get(var1);
   }

   final void setFeature(String var1, boolean var2) {
      this.fFeatures.put(var1, var2 ? Boolean.TRUE : Boolean.FALSE);
   }

   public abstract boolean isFullyComposed();

   public abstract XMLGrammarPool getGrammarPool();
}
