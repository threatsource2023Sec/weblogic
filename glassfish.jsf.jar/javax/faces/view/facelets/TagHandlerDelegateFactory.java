package javax.faces.view.facelets;

import javax.faces.FacesWrapper;

public abstract class TagHandlerDelegateFactory implements FacesWrapper {
   private TagHandlerDelegateFactory wrapped;

   /** @deprecated */
   @Deprecated
   public TagHandlerDelegateFactory() {
   }

   public TagHandlerDelegateFactory(TagHandlerDelegateFactory wrapped) {
      this.wrapped = wrapped;
   }

   public TagHandlerDelegateFactory getWrapped() {
      return this.wrapped;
   }

   public abstract TagHandlerDelegate createComponentHandlerDelegate(ComponentHandler var1);

   public abstract TagHandlerDelegate createValidatorHandlerDelegate(ValidatorHandler var1);

   public abstract TagHandlerDelegate createConverterHandlerDelegate(ConverterHandler var1);

   public abstract TagHandlerDelegate createBehaviorHandlerDelegate(BehaviorHandler var1);
}
