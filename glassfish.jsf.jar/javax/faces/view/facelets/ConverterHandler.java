package javax.faces.view.facelets;

import javax.faces.view.ValueHolderAttachedObjectHandler;

public class ConverterHandler extends FaceletsAttachedObjectHandler implements ValueHolderAttachedObjectHandler {
   private String converterId;
   private TagHandlerDelegate helper;

   public ConverterHandler(ConverterConfig config) {
      super(config);
      this.converterId = config.getConverterId();
   }

   protected TagHandlerDelegate getTagHandlerDelegate() {
      if (null == this.helper) {
         this.helper = this.delegateFactory.createConverterHandlerDelegate(this);
      }

      return this.helper;
   }

   public String getConverterId(FaceletContext ctx) {
      if (this.converterId == null) {
         TagAttribute idAttr = this.getAttribute("converterId");
         return idAttr == null ? null : idAttr.getValue(ctx);
      } else {
         return this.converterId;
      }
   }
}
