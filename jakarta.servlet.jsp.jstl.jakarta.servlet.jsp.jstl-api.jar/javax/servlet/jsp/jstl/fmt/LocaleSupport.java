package javax.servlet.jsp.jstl.fmt;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;

public class LocaleSupport {
   private static final String UNDEFINED_KEY = "???";
   private static final char HYPHEN = '-';
   private static final char UNDERSCORE = '_';
   private static final String REQUEST_CHAR_SET = "javax.servlet.jsp.jstl.fmt.request.charset";
   private static final Locale EMPTY_LOCALE = new Locale("", "");

   public static String getLocalizedMessage(PageContext pageContext, String key) {
      return getLocalizedMessage(pageContext, key, (Object[])null, (String)null);
   }

   public static String getLocalizedMessage(PageContext pageContext, String key, String basename) {
      return getLocalizedMessage(pageContext, key, (Object[])null, basename);
   }

   public static String getLocalizedMessage(PageContext pageContext, String key, Object[] args) {
      return getLocalizedMessage(pageContext, key, args, (String)null);
   }

   public static String getLocalizedMessage(PageContext pageContext, String key, Object[] args, String basename) {
      LocalizationContext locCtxt = null;
      String message = "???" + key + "???";
      if (basename != null) {
         locCtxt = getLocalizationContext(pageContext, basename);
      } else {
         locCtxt = getLocalizationContext(pageContext);
      }

      if (locCtxt != null) {
         ResourceBundle bundle = locCtxt.getResourceBundle();
         if (bundle != null) {
            try {
               message = bundle.getString(key);
               if (args != null) {
                  MessageFormat formatter = new MessageFormat("");
                  if (locCtxt.getLocale() != null) {
                     formatter.setLocale(locCtxt.getLocale());
                  }

                  formatter.applyPattern(message);
                  message = formatter.format(args);
               }
            } catch (MissingResourceException var8) {
            }
         }
      }

      return message;
   }

   private static LocalizationContext getLocalizationContext(PageContext pc) {
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

   private static LocalizationContext getLocalizationContext(PageContext pc, String basename) {
      LocalizationContext locCtxt = null;
      ResourceBundle bundle = null;
      if (basename != null && !basename.equals("")) {
         Locale pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.locale");
         if (pref != null) {
            bundle = findMatch(basename, pref);
            if (bundle != null) {
               locCtxt = new LocalizationContext(bundle, pref);
            }
         } else {
            locCtxt = findMatch(pc, basename);
         }

         if (locCtxt == null) {
            pref = getLocale(pc, "javax.servlet.jsp.jstl.fmt.fallbackLocale");
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
               setResponseLocale(pc, locCtxt.getLocale());
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
      Enumeration enum_ = getRequestLocales((HttpServletRequest)pageContext.getRequest());

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

   private static Locale getLocale(PageContext pageContext, String name) {
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

   private static void setResponseLocale(PageContext pc, Locale locale) {
      ServletResponse response = pc.getResponse();
      response.setLocale(locale);
      if (pc.getSession() != null) {
         try {
            pc.setAttribute("javax.servlet.jsp.jstl.fmt.request.charset", response.getCharacterEncoding(), 3);
         } catch (IllegalStateException var4) {
         }
      }

   }

   private static Locale parseLocale(String locale) {
      return parseLocale(locale, (String)null);
   }

   private static Locale parseLocale(String locale, String variant) {
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
               throw new IllegalArgumentException("Empty country component in 'value' attribute in setLocale");
            }

            if (variant != null) {
               ret = new Locale(language, country, variant);
            } else {
               ret = new Locale(language, country);
            }
         }

         return ret;
      } else {
         throw new IllegalArgumentException("Missing language component in 'value' attribute in setLocale");
      }
   }

   private static Enumeration getRequestLocales(HttpServletRequest request) {
      Enumeration values = request.getHeaders("accept-language");
      return values.hasMoreElements() ? request.getLocales() : values;
   }
}
