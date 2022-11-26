package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import java.util.TimeZone;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.view.facelets.ConverterConfig;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

public final class ConvertDateTimeHandler extends ConverterHandler {
   private final TagAttribute dateStyle = this.getAttribute("dateStyle");
   private final TagAttribute locale = this.getAttribute("locale");
   private final TagAttribute pattern = this.getAttribute("pattern");
   private final TagAttribute timeStyle = this.getAttribute("timeStyle");
   private final TagAttribute timeZone = this.getAttribute("timeZone");
   private final TagAttribute type = this.getAttribute("type");

   public ConvertDateTimeHandler(ConverterConfig config) {
      super(config);
   }

   protected Converter createConverter(FaceletContext ctx) throws FacesException, ELException, FaceletException {
      return ctx.getFacesContext().getApplication().createConverter("javax.faces.DateTime");
   }

   public void setAttributes(FaceletContext ctx, Object obj) {
      DateTimeConverter c = (DateTimeConverter)obj;
      if (this.locale != null) {
         c.setLocale(ComponentSupport.getLocale(ctx, this.locale));
      }

      if (this.pattern != null) {
         c.setPattern(this.pattern.getValue(ctx));
         if (this.type != null) {
            String typeStr = this.type.getValue(ctx);
            if (isJavaTimeType(typeStr)) {
               c.setType(typeStr);
            }
         }
      } else {
         if (this.type != null) {
            c.setType(this.type.getValue(ctx));
         }

         if (this.dateStyle != null) {
            c.setDateStyle(this.dateStyle.getValue(ctx));
         }

         if (this.timeStyle != null) {
            c.setTimeStyle(this.timeStyle.getValue(ctx));
         }
      }

      if (this.timeZone != null) {
         Object t = this.timeZone.getObject(ctx);
         if (t != null) {
            if (t instanceof TimeZone) {
               c.setTimeZone((TimeZone)t);
            } else {
               if (!(t instanceof String)) {
                  throw new TagAttributeException(this.tag, this.timeZone, "Illegal TimeZone, must evaluate to either a java.util.TimeZone or String, is type: " + t.getClass());
               }

               TimeZone tz = TimeZone.getTimeZone((String)t);
               c.setTimeZone(tz);
            }
         }
      }

   }

   private static boolean isJavaTimeType(String type) {
      boolean result = false;
      if (null != type && type.length() > 1) {
         char c = type.charAt(0);
         result = c == 'l' || c == 'o' || c == 'z';
      }

      return result;
   }

   public MetaRuleset createMetaRuleset(Class type) {
      return super.createMetaRuleset(type).ignoreAll();
   }
}
