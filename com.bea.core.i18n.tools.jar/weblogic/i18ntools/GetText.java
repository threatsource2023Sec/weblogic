package weblogic.i18ntools;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.SeverityI18N;

public class GetText {
   static CatInfoTextFormatter cat = CatInfoTextFormatter.getInstance();

   public static String getText(String id) {
      return getText(id, false, false, (String)null, Locale.getDefault());
   }

   public static String getText(String id, Locale l) {
      return getText(id, false, false, (String)null, l);
   }

   public static String getDetailedText(String id) {
      return getText(id, true, false, (String)null, Locale.getDefault());
   }

   public static String getDetailedText(String id, Locale l) {
      return getText(id, true, false, (String)null, l);
   }

   public static String getText(String id, boolean detailed, boolean verbose, String subSystem, Locale l) {
      String retval = null;
      if (id == null) {
         throw new NullPointerException(cat.noId());
      } else {
         int dash;
         try {
            DecimalFormat df = new DecimalFormat("000000");
            dash = Integer.parseInt(id);
            id = df.format((long)dash);
         } catch (NumberFormatException var12) {
            dash = id.indexOf("-");
            if (dash != -1) {
               try {
                  String newId = id.substring(dash + 1);
                  DecimalFormat df = new DecimalFormat("000000");
                  int n = Integer.parseInt(newId);
                  id = df.format((long)n);
               } catch (NumberFormatException var11) {
               }
            }
         }

         L10nLookup l10n = L10nLookup.getL10n();
         String prop = l10n.getProperty(id);
         if (prop != null) {
            retval = processKey(id, detailed, verbose, subSystem, l);
         } else {
            retval = cat.noSuchMessage(id);
         }

         return retval;
      }
   }

   public static String getRetiredMessage(String id) {
      return getRetiredMessage(id, (String)null);
   }

   public static String getRetiredMessage(String id, String subSystem) {
      if (id == null) {
         throw new NullPointerException(cat.noId());
      } else {
         try {
            DecimalFormat df = new DecimalFormat("000000");
            int n = Integer.parseInt(id);
            id = df.format((long)n);
         } catch (NumberFormatException var6) {
         }

         L10nLookup l10n = L10nLookup.getL10n();
         String prop = l10n.getProperty(id);
         String retval = null;
         if (prop != null && prop.endsWith("retired")) {
            String ss = prop.substring(0, prop.indexOf(":"));
            if (subSystem != null && subSystem.length() > 0) {
               if (subSystem.equals(ss)) {
                  retval = cat.retiredMessage(id, ss);
               }
            } else {
               retval = cat.retiredMessage(id, ss);
            }
         }

         return retval;
      }
   }

   private static String processKey(String key, boolean detail, boolean verbose, String subSystem, Locale locale) {
      StringBuilder sb = new StringBuilder();
      L10nLookup l10n = L10nLookup.getL10n();
      String prop = l10n.getProperty(key);
      String ss = prop.substring(0, prop.indexOf(":"));
      if (subSystem != null && subSystem.length() > 0 && !subSystem.equals(ss)) {
         return "";
      } else if (prop.endsWith("retired")) {
         return "";
      } else {
         try {
            Localizer l = l10n.getLocalizer(key, locale);
            if (l == null) {
               throw new MissingResourceException("", "", key);
            }

            if (detail || verbose) {
               sb.append(key + ": ");
            }

            sb.append(l.getBody(key));
            if (detail || verbose) {
               sb.append("\n");
               if (verbose) {
                  sb.append(cat.l10nPackage(l.getL10nPackage()) + "\n");
               }

               if (verbose) {
                  sb.append(cat.i18nPackage(l.getI18nPackage()) + "\n");
               }

               sb.append(cat.subsystem(l.getSubSystem()) + "\n");

               try {
                  Localizer ld = l10n.getLocalizer(key, locale, true);
                  String sev = SeverityI18N.severityNumToString(ld.getSeverity(key), locale);
                  sb.append(cat.severity(sev) + "\n");
                  if (verbose) {
                     sb.append(cat.stackTrace(ld.getStackTrace(key)) + "\n");
                  }

                  String txt = ld.getDetail(key);
                  if (txt == null || txt.length() == 0) {
                     txt = cat.noText();
                  }

                  sb.append(cat.messageDetail(txt) + "\n");
                  txt = ld.getCause(key);
                  if (txt == null || txt.length() == 0) {
                     txt = cat.noText();
                  }

                  sb.append(cat.cause(txt) + "\n");
                  txt = ld.getAction(key);
                  if (txt == null || txt.length() == 0) {
                     txt = cat.noText();
                  }

                  sb.append(cat.action(txt));
               } catch (MissingResourceException var13) {
                  sb.append(cat.noDetail(key));
               }
            }
         } catch (MissingResourceException var14) {
            sb.append(cat.noInfo(key));
         }

         return sb.toString();
      }
   }
}
