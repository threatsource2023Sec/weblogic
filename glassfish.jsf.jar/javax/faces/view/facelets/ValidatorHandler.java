package javax.faces.view.facelets;

import javax.faces.view.EditableValueHolderAttachedObjectHandler;

public class ValidatorHandler extends FaceletsAttachedObjectHandler implements EditableValueHolderAttachedObjectHandler {
   private String validatorId;
   private TagHandlerDelegate helper;
   private ValidatorConfig config;

   public ValidatorHandler(ValidatorConfig config) {
      super(config);
      this.config = config;
      this.validatorId = config.getValidatorId();
   }

   protected TagHandlerDelegate getTagHandlerDelegate() {
      if (null == this.helper) {
         this.helper = this.delegateFactory.createValidatorHandlerDelegate(this);
      }

      return this.helper;
   }

   public String getValidatorId(FaceletContext ctx) {
      if (this.validatorId == null) {
         TagAttribute idAttr = this.getAttribute("validatorId");
         return idAttr == null ? null : idAttr.getValue(ctx);
      } else {
         return this.validatorId;
      }
   }

   public ValidatorConfig getValidatorConfig() {
      return this.config;
   }
}
