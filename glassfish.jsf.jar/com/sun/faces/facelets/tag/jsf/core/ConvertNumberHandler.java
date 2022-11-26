package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.view.facelets.ConverterConfig;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;

public final class ConvertNumberHandler extends ConverterHandler {
   private final TagAttribute locale = this.getAttribute("locale");

   public ConvertNumberHandler(ConverterConfig config) {
      super(config);
   }

   protected Converter createConverter(FaceletContext ctx) throws FacesException, ELException, FaceletException {
      return ctx.getFacesContext().getApplication().createConverter("javax.faces.Number");
   }

   public void setAttributes(FaceletContext ctx, Object obj) {
      super.setAttributes(ctx, obj);
      NumberConverter c = (NumberConverter)obj;
      if (this.locale != null) {
         c.setLocale(ComponentSupport.getLocale(ctx, this.locale));
      }

   }

   public MetaRuleset createMetaRuleset(Class type) {
      return super.createMetaRuleset(type).ignore("locale");
   }
}
