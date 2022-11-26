package javax.faces.view.facelets;

public abstract class MetaTagHandler extends TagHandler {
   private Class lastType = Object.class;
   private Metadata mapper;

   public MetaTagHandler(TagConfig config) {
      super(config);
   }

   protected abstract MetaRuleset createMetaRuleset(Class var1);

   protected void setAttributes(FaceletContext ctx, Object instance) {
      if (instance != null) {
         Class type = instance.getClass();
         if (this.mapper == null || !this.lastType.equals(type)) {
            this.lastType = type;
            this.mapper = this.createMetaRuleset(type).finish();
         }

         this.mapper.applyMetadata(ctx, instance);
      }

   }
}
