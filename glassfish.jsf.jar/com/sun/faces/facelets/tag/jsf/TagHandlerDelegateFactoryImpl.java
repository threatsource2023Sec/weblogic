package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.jsf.html.ScriptResourceDelegate;
import com.sun.faces.facelets.tag.jsf.html.ScriptResourceHandler;
import com.sun.faces.facelets.tag.jsf.html.StylesheetResourceDelegate;
import com.sun.faces.facelets.tag.jsf.html.StylesheetResourceHandler;
import javax.faces.view.facelets.BehaviorHandler;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.TagHandlerDelegate;
import javax.faces.view.facelets.TagHandlerDelegateFactory;
import javax.faces.view.facelets.ValidatorHandler;

public class TagHandlerDelegateFactoryImpl extends TagHandlerDelegateFactory {
   public TagHandlerDelegateFactoryImpl() {
      super((TagHandlerDelegateFactory)null);
   }

   public TagHandlerDelegate createComponentHandlerDelegate(ComponentHandler owner) {
      if (owner instanceof StylesheetResourceHandler) {
         return new StylesheetResourceDelegate(owner);
      } else {
         return (TagHandlerDelegate)(owner instanceof ScriptResourceHandler ? new ScriptResourceDelegate(owner) : new ComponentTagHandlerDelegateImpl(owner));
      }
   }

   public TagHandlerDelegate createValidatorHandlerDelegate(ValidatorHandler owner) {
      return new ValidatorTagHandlerDelegateImpl(owner);
   }

   public TagHandlerDelegate createConverterHandlerDelegate(ConverterHandler owner) {
      return new ConverterTagHandlerDelegateImpl(owner);
   }

   public TagHandlerDelegate createBehaviorHandlerDelegate(BehaviorHandler owner) {
      return new BehaviorTagHandlerDelegateImpl(owner);
   }
}
