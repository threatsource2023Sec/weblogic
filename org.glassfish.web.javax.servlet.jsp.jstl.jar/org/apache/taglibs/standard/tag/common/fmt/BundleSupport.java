package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class BundleSupport extends BodyTagSupport {
   private static final Locale EMPTY_LOCALE = new Locale("", "");
   protected String basename;
   protected String prefix;
   private LocalizationContext locCtxt;

   public BundleSupport() {
      this.init();
   }

   private void init() {
      this.basename = this.prefix = null;
      this.locCtxt = null;
   }

   public LocalizationContext getLocalizationContext() {
      return this.locCtxt;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public int doStartTag() throws JspException {
      this.locCtxt = getLocalizationContext(this.pageContext, this.basename);
      return 2;
   }

   public int doEndTag() throws JspException {
      if (this.bodyContent != null) {
         try {
            this.pageContext.getOut().print(this.bodyContent.getString());
         } catch (IOException var2) {
            throw new JspTagException(var2.toString(), var2);
         }
      }

      return 6;
   }

   public void release() {
      this.init();
   }

   public static LocalizationContext getLocalizationContext(PageContext pc) {
      LocalizationContext locCtxt = null;
      Object obj = Config.find(pc, "javax.servlet.jsp.jstl.fmt.localizationContext");
      if (obj == null) {
         return null;
      } else {
         if (obj instanceof LocalizationContext) {
            locCtxt = (LocalizationContext)obj;
         } else {
            locCtxt = getLocalizationContext(pc, (String)obj);
         }

         return locCtxt;
      }
   }

   public static LocalizationContext getLocalizationContext(PageContext pc, String basename) {
      LocalizationContext locCtxt = null;
      ResourceBundle bundle = null;
      if (basename != null && !basename.equals("")) {
         Locale pref = SetLocaleSupport.getLocale(pc, "javax.servlet.jsp.jstl.fmt.locale");
         if (pref != null) {
            bundle = findMatch(basename, pref);
            if (bundle != null) {
               locCtxt = new LocalizationContext(bundle, pref);
            }
         } else {
            locCtxt = findMatch(pc, basename);
         }

         if (locCtxt == null) {
            pref = SetLocaleSupport.getLocale(pc, "javax.servlet.jsp.jstl.fmt.fallbackLocale");
            if (pref != null) {
               bundle = findMatch(basename, pref);
               if (bundle != null) {
                  locCtxt = new LocalizationContext(bundle, pref);
               }
            }
         }

         if (locCtxt == null) {
            try {
               bundle = ResourceBundle.getBundle(basename, EMPTY_LOCALE, Thread.currentThread().getContextClassLoader());
               if (bundle != null) {
                  locCtxt = new LocalizationContext(bundle, (Locale)null);
               }
            } catch (MissingResourceException var6) {
            }
         }

         if (locCtxt != null) {
            if (locCtxt.getLocale() != null) {
               SetLocaleSupport.setResponseLocale(pc, locCtxt.getLocale());
            }
         } else {
            locCtxt = new LocalizationContext();
         }

         return locCtxt;
      } else {
         return new LocalizationContext();
      }
   }

   private static LocalizationContext findMatch(PageContext pageContext, String basename) {
      LocalizationContext locCtxt = null;
      Enumeration enum_ = Util.getRequestLocales((HttpServletRequest)pageContext.getRequest());

      while(enum_.hasMoreElements()) {
         Locale pref = (Locale)enum_.nextElement();
         ResourceBundle match = findMatch(basename, pref);
         if (match != null) {
            locCtxt = new LocalizationContext(match, pref);
            break;
         }
      }

      return locCtxt;
   }

   private static ResourceBundle findMatch(String basename, Locale pref) {
      ResourceBundle match = null;

      try {
         ResourceBundle bundle = ResourceBundle.getBundle(basename, pref, Thread.currentThread().getContextClassLoader());
         Locale avail = bundle.getLocale();
         if (pref.equals(avail)) {
            match = bundle;
         } else if (pref.getLanguage().equals(avail.getLanguage()) && ("".equals(avail.getCountry()) || pref.getCountry().equals(avail.getCountry()))) {
            match = bundle;
         }
      } catch (MissingResourceException var5) {
      }

      return match;
   }
}
