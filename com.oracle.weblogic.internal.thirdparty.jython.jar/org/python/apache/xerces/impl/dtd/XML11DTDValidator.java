package org.python.apache.xerces.impl.dtd;

import org.python.apache.xerces.xni.parser.XMLComponentManager;

public class XML11DTDValidator extends XMLDTDValidator {
   protected static final String DTD_VALIDATOR_PROPERTY = "http://apache.org/xml/properties/internal/validator/dtd";

   public void reset(XMLComponentManager var1) {
      XMLDTDValidator var2 = null;
      if ((var2 = (XMLDTDValidator)var1.getProperty("http://apache.org/xml/properties/internal/validator/dtd")) != null && var2 != this) {
         this.fGrammarBucket = var2.getGrammarBucket();
      }

      super.reset(var1);
   }

   protected void init() {
      if (this.fValidation || this.fDynamicValidation) {
         super.init();

         try {
            this.fValID = this.fDatatypeValidatorFactory.getBuiltInDV("XML11ID");
            this.fValIDRef = this.fDatatypeValidatorFactory.getBuiltInDV("XML11IDREF");
            this.fValIDRefs = this.fDatatypeValidatorFactory.getBuiltInDV("XML11IDREFS");
            this.fValNMTOKEN = this.fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKEN");
            this.fValNMTOKENS = this.fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKENS");
         } catch (Exception var2) {
            var2.printStackTrace(System.err);
         }
      }

   }
}
