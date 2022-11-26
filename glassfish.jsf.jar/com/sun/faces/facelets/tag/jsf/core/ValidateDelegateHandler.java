package com.sun.faces.facelets.tag.jsf.core;

import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.ValidatorConfig;
import javax.faces.view.facelets.ValidatorHandler;

public final class ValidateDelegateHandler extends ValidatorHandler {
   private final TagAttribute validatorId = this.getAttribute("validatorId");

   public ValidateDelegateHandler(ValidatorConfig config) {
      super(config);
   }

   protected String getValidator(FaceletContext ctx) {
      return this.validatorId != null ? this.validatorId.getValue(ctx) : null;
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      return super.createMetaRuleset(type).ignoreAll();
   }
}
