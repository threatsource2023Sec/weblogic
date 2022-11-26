package org.apache.taglibs.standard.tag.common.fmt;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class SetLocaleSupport extends TagSupport {
   private static final char HYPHEN = '-';
   private static final char UNDERSCORE = '_';
   private static Locale[] availableFormattingLocales;
   private static final Locale[] dateLocales = DateFormat.getAvailableLocales();
   private static final Locale[] numberLocales = NumberFormat.getAvailableLocales();
   protected Object value;
   protected String variant;
   private int scope;

   public SetLocaleSupport() {
      this.init();
   }

   private void init() {
      this.value = this.variant = null;
      this.scope = 1;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      Locale locale = null;
      if (this.value == null) {
         locale = Locale.getDefault();
      } else if (this.value instanceof String) {
         if (((String)this.value).trim().equals("")) {
            locale = Locale.getDefault();
         } else {
            locale = parseLocale((String)this.value, this.variant);
         }
      } else {
         locale = (Locale)this.value;
      }

      Config.set(this.pageContext, "javax.servlet.jsp.jstl.fmt.locale", locale, this.scope);
      setResponseLocale(this.pageContext, locale);
      return 6;
   }

   public void release() {
      this.init();
   }

   public static Locale parseLocale(String locale) {
      return parseLocale(locale, (String)null);
   }

   public static Locale parseLocale(String locale, String variant) {
      Locale ret = null;
      String language = locale;
      String country = null;
      int index = true;
      int index;
      if ((index = locale.indexOf(45)) > -1 || (index = locale.indexOf(95)) > -1) {
         language = locale.substring(0, index);
         country = locale.substring(index + 1);
      }

      if (language != null && language.length() != 0) {
         if (country == null) {
            if (variant != null) {
               ret = new Locale(language, "", variant);
            } else {
               ret = new Locale(language, "");
            }
         } else {
            if (country.length() <= 0) {
               throw new IllegalArgumentException(Resources.getMessage("LOCALE_EMPTY_COUNTRY"));
            }

            if (variant != null) {
               ret = new Locale(language, country, variant);
            } else {
               ret = new Locale(language, country);
            }
         }

         return ret;
      } else {
         throw new IllegalArgumentException(Resources.getMessage("LOCALE_NO_LANGUAGE"));
      }
   }

   static void setResponseLocale(PageContext pc, Locale locale) {
      ServletResponse response = pc.getResponse();
      response.setLocale(locale);
      if (pc.getSession() != null) {
         try {
            pc.setAttribute("javax.servlet.jsp.jstl.fmt.request.charset", response.getCharacterEncoding(), 3);
         } catch (IllegalStateException var4) {
         }
      }

   }

   static Locale getFormattingLocale(PageContext pc, Tag fromTag, boolean isDate, boolean format) {
      LocalizationContext locCtxt = null;
      Tag parent = findAncestorWithClass(fromTag, BundleSupport.class);
      if (parent != null) {
         locCtxt = ((BundleSupport)parent).getLocalizationContext();
         if (locCtxt.getLocale() != null) {
            if (format) {
               setResponseLocale(pc, locCtxt.getLocale());
            }

            return locCtxt.getLocale();
         }
      }

      if ((locCtxt = BundleSupport.getLocalizationContext(pc)) != null && locCtxt.getLocale() != null) {
         if (format) {
            setResponseLocale(pc, locCtxt.getLocale());
         }

         return locCtxt.getLocale();
      } else {
         Locale match = null;
         Locale pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.locale");
         Locale[] avail = null;
         if (isDate) {
            avail = dateLocales;
         } else {
            avail = numberLocales;
         }

         if (pref != null) {
            match = findFormattingMatch(pref, avail);
         } else {
            match = findFormattingMatch(pc, avail);
         }

         if (match == null) {
            pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.fallbackLocale");
            if (pref != null) {
               match = findFormattingMatch(pref, avail);
            }
         }

         if (format && match != null) {
            setResponseLocale(pc, match);
         }

         return match;
      }
   }

   static Locale getFormattingLocale(PageContext pc) {
      Locale match = null;
      Locale pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.locale");
      if (pref != null) {
         match = findFormattingMatch(pref, availableFormattingLocales);
      } else {
         match = findFormattingMatch(pc, availableFormattingLocales);
      }

      if (match == null) {
         pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.fallbackLocale");
         if (pref != null) {
            match = findFormattingMatch(pref, availableFormattingLocales);
         }
      }

      if (match != null) {
         setResponseLocale(pc, match);
      }

      return match;
   }

   static Locale getLocale(PageContext pageContext, String name) {
      Locale loc = null;
      Object obj = Config.find(pageContext, name);
      if (obj != null) {
         if (obj instanceof Locale) {
            loc = (Locale)obj;
         } else {
            loc = parseLocale((String)obj);
         }
      }

      return loc;
   }

   private static Locale findFormattingMatch(PageContext pageContext, Locale[] avail) {
      Locale match = null;
      Enumeration enum_ = Util.getRequestLocales((HttpServletRequest)pageContext.getRequest());

      while(enum_.hasMoreElements()) {
         Locale locale = (Locale)enum_.nextElement();
         match = findFormattingMatch(locale, avail);
         if (match != null) {
            break;
         }
      }

      return match;
   }

   private static Locale findFormattingMatch(Locale pref, Locale[] avail) {
      Locale match = null;
      boolean langAndCountryMatch = false;

      for(int i = 0; i < avail.length; ++i) {
         if (pref.equals(avail[i])) {
            match = avail[i];
            break;
         }

         if (!"".equals(pref.getVariant()) && "".equals(avail[i].getVariant()) && pref.getLanguage().equals(avail[i].getLanguage()) && pref.getCountry().equals(avail[i].getCountry())) {
            match = avail[i];
            langAndCountryMatch = true;
         } else if (!langAndCountryMatch && pref.getLanguage().equals(avail[i].getLanguage()) && "".equals(avail[i].getCountry()) && match == null) {
            match = avail[i];
         }
      }

      return match;
   }

   static {
      Vector vec = new Vector(dateLocales.length);

      for(int i = 0; i < dateLocales.length; ++i) {
         for(int j = 0; j < numberLocales.length; ++j) {
            if (dateLocales[i].equals(numberLocales[j])) {
               vec.add(dateLocales[i]);
               break;
            }
         }
      }

      availableFormattingLocales = new Locale[vec.size()];
      availableFormattingLocales = (Locale[])((Locale[])vec.toArray(availableFormattingLocales));
   }
}
