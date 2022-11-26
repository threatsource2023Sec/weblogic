package javax.faces.view.facelets;

import java.io.IOException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;

public abstract class DelegatingMetaTagHandler extends MetaTagHandler {
   private final TagAttribute binding = this.getAttribute("binding");
   private final TagAttribute disabled = this.getAttribute("disabled");
   protected TagHandlerDelegateFactory delegateFactory = (TagHandlerDelegateFactory)FactoryFinder.getFactory("javax.faces.view.facelets.TagHandlerDelegateFactory");

   public DelegatingMetaTagHandler(TagConfig config) {
      super(config);
   }

   protected abstract TagHandlerDelegate getTagHandlerDelegate();

   public boolean isDisabled(FaceletContext ctx) {
      return this.disabled != null && Boolean.TRUE.equals(this.disabled.getBoolean(ctx));
   }

   public TagAttribute getBinding() {
      return this.binding;
   }

   public Tag getTag() {
      return this.tag;
   }

   public String getTagId() {
      return this.tagId;
   }

   public TagAttribute getTagAttribute(String localName) {
      return super.getAttribute(localName);
   }

   public void setAttributes(FaceletContext ctx, Object instance) {
      super.setAttributes(ctx, instance);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      this.getTagHandlerDelegate().apply(ctx, parent);
   }

   public void applyNextHandler(FaceletContext ctx, UIComponent c) throws IOException, FacesException, ELException {
      this.nextHandler.apply(ctx, c);
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      return this.getTagHandlerDelegate().createMetaRuleset(type);
   }
}
