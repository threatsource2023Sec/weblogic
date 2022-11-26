package com.sun.faces.facelets.tag.jsf.core;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.convert.Converter;
import javax.faces.view.facelets.ConverterConfig;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;

public final class ConvertDelegateHandler extends ConverterHandler {
   private final TagAttribute converterId = this.getAttribute("converterId");

   public ConvertDelegateHandler(ConverterConfig config) {
      super(config);
   }

   protected Converter createConverter(FaceletContext ctx) throws FacesException, ELException, FaceletException {
      return ctx.getFacesContext().getApplication().createConverter(this.converterId.getValue(ctx));
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      return super.createMetaRuleset(type).ignoreAll();
   }
}
