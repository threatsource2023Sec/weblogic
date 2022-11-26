package org.glassfish.grizzly.localization;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localizer {
   private final Locale _locale;
   private final Map _resourceBundles;

   public Localizer() {
      this(Locale.getDefault());
   }

   public Localizer(Locale l) {
      this._locale = l;
      this._resourceBundles = new HashMap();
   }

   public Locale getLocale() {
      return this._locale;
   }

   public String localize(Localizable l) {
      String key = l.getKey();
      if (key == "\u0000") {
         return (String)l.getArguments()[0];
      } else {
         String bundlename = l.getResourceBundleName();

         try {
            ResourceBundle bundle = (ResourceBundle)this._resourceBundles.get(bundlename);
            if (bundle == null) {
               try {
                  bundle = ResourceBundle.getBundle(bundlename, this._locale);
               } catch (MissingResourceException var11) {
                  int i = bundlename.lastIndexOf(46);
                  if (i != -1) {
                     String alternateBundleName = bundlename.substring(i + 1);

                     try {
                        bundle = ResourceBundle.getBundle(alternateBundleName, this._locale);
                     } catch (MissingResourceException var10) {
                        return this.getDefaultMessage(l);
                     }
                  }
               }

               this._resourceBundles.put(bundlename, bundle);
            }

            if (bundle == null) {
               return this.getDefaultMessage(l);
            } else {
               if (key == null) {
                  key = "undefined";
               }

               String msg;
               try {
                  msg = bundle.getString(key);
               } catch (MissingResourceException var9) {
                  msg = bundle.getString("undefined");
               }

               Object[] args = l.getArguments();

               for(int i = 0; i < args.length; ++i) {
                  if (args[i] instanceof Localizable) {
                     args[i] = this.localize((Localizable)args[i]);
                  }
               }

               return MessageFormat.format(msg, args);
            }
         } catch (MissingResourceException var12) {
            return this.getDefaultMessage(l);
         }
      }
   }

   private String getDefaultMessage(Localizable l) {
      String key = l.getKey();
      Object[] args = l.getArguments();
      StringBuilder sb = new StringBuilder();
      sb.append("[failed to localize] ");
      sb.append(key);
      if (args != null) {
         sb.append('(');

         for(int i = 0; i < args.length; ++i) {
            if (i != 0) {
               sb.append(", ");
            }

            sb.append(String.valueOf(args[i]));
         }

         sb.append(')');
      }

      return sb.toString();
   }
}
