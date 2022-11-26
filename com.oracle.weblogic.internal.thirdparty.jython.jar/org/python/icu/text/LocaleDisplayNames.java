package org.python.icu.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.python.icu.impl.ICUConfig;
import org.python.icu.lang.UScript;
import org.python.icu.util.ULocale;

public abstract class LocaleDisplayNames {
   private static final Method FACTORY_DIALECTHANDLING;
   private static final Method FACTORY_DISPLAYCONTEXT;

   public static LocaleDisplayNames getInstance(ULocale locale) {
      return getInstance(locale, LocaleDisplayNames.DialectHandling.STANDARD_NAMES);
   }

   public static LocaleDisplayNames getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static LocaleDisplayNames getInstance(ULocale locale, DialectHandling dialectHandling) {
      LocaleDisplayNames result = null;
      if (FACTORY_DIALECTHANDLING != null) {
         try {
            result = (LocaleDisplayNames)FACTORY_DIALECTHANDLING.invoke((Object)null, locale, dialectHandling);
         } catch (InvocationTargetException var4) {
         } catch (IllegalAccessException var5) {
         }
      }

      if (result == null) {
         result = new LastResortLocaleDisplayNames(locale, dialectHandling);
      }

      return (LocaleDisplayNames)result;
   }

   public static LocaleDisplayNames getInstance(ULocale locale, DisplayContext... contexts) {
      LocaleDisplayNames result = null;
      if (FACTORY_DISPLAYCONTEXT != null) {
         try {
            result = (LocaleDisplayNames)FACTORY_DISPLAYCONTEXT.invoke((Object)null, locale, contexts);
         } catch (InvocationTargetException var4) {
         } catch (IllegalAccessException var5) {
         }
      }

      if (result == null) {
         result = new LastResortLocaleDisplayNames(locale, contexts);
      }

      return (LocaleDisplayNames)result;
   }

   public static LocaleDisplayNames getInstance(Locale locale, DisplayContext... contexts) {
      return getInstance(ULocale.forLocale(locale), contexts);
   }

   public abstract ULocale getLocale();

   public abstract DialectHandling getDialectHandling();

   public abstract DisplayContext getContext(DisplayContext.Type var1);

   public abstract String localeDisplayName(ULocale var1);

   public abstract String localeDisplayName(Locale var1);

   public abstract String localeDisplayName(String var1);

   public abstract String languageDisplayName(String var1);

   public abstract String scriptDisplayName(String var1);

   /** @deprecated */
   @Deprecated
   public String scriptDisplayNameInContext(String script) {
      return this.scriptDisplayName(script);
   }

   public abstract String scriptDisplayName(int var1);

   public abstract String regionDisplayName(String var1);

   public abstract String variantDisplayName(String var1);

   public abstract String keyDisplayName(String var1);

   public abstract String keyValueDisplayName(String var1, String var2);

   public List getUiList(Set localeSet, boolean inSelf, Comparator collator) {
      return this.getUiListCompareWholeItems(localeSet, LocaleDisplayNames.UiListItem.getComparator(collator, inSelf));
   }

   public abstract List getUiListCompareWholeItems(Set var1, Comparator var2);

   /** @deprecated */
   @Deprecated
   protected LocaleDisplayNames() {
   }

   static {
      String implClassName = ICUConfig.get("org.python.icu.text.LocaleDisplayNames.impl", "org.python.icu.impl.LocaleDisplayNamesImpl");
      Method factoryDialectHandling = null;
      Method factoryDisplayContext = null;

      try {
         Class implClass = Class.forName(implClassName);

         try {
            factoryDialectHandling = implClass.getMethod("getInstance", ULocale.class, DialectHandling.class);
         } catch (NoSuchMethodException var6) {
         }

         try {
            factoryDisplayContext = implClass.getMethod("getInstance", ULocale.class, DisplayContext[].class);
         } catch (NoSuchMethodException var5) {
         }
      } catch (ClassNotFoundException var7) {
      }

      FACTORY_DIALECTHANDLING = factoryDialectHandling;
      FACTORY_DISPLAYCONTEXT = factoryDisplayContext;
   }

   private static class LastResortLocaleDisplayNames extends LocaleDisplayNames {
      private ULocale locale;
      private DisplayContext[] contexts;

      private LastResortLocaleDisplayNames(ULocale locale, DialectHandling dialectHandling) {
         this.locale = locale;
         DisplayContext context = dialectHandling == LocaleDisplayNames.DialectHandling.DIALECT_NAMES ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES;
         this.contexts = new DisplayContext[]{context};
      }

      private LastResortLocaleDisplayNames(ULocale locale, DisplayContext... contexts) {
         this.locale = locale;
         this.contexts = new DisplayContext[contexts.length];
         System.arraycopy(contexts, 0, this.contexts, 0, contexts.length);
      }

      public ULocale getLocale() {
         return this.locale;
      }

      public DialectHandling getDialectHandling() {
         DialectHandling result = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
         DisplayContext[] var2 = this.contexts;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            DisplayContext context = var2[var4];
            if (context.type() == DisplayContext.Type.DIALECT_HANDLING && context.value() == DisplayContext.DIALECT_NAMES.ordinal()) {
               result = LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
               break;
            }
         }

         return result;
      }

      public DisplayContext getContext(DisplayContext.Type type) {
         DisplayContext result = DisplayContext.STANDARD_NAMES;
         DisplayContext[] var3 = this.contexts;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            DisplayContext context = var3[var5];
            if (context.type() == type) {
               result = context;
               break;
            }
         }

         return result;
      }

      public String localeDisplayName(ULocale locale) {
         return locale.getName();
      }

      public String localeDisplayName(Locale locale) {
         return ULocale.forLocale(locale).getName();
      }

      public String localeDisplayName(String localeId) {
         return (new ULocale(localeId)).getName();
      }

      public String languageDisplayName(String lang) {
         return lang;
      }

      public String scriptDisplayName(String script) {
         return script;
      }

      public String scriptDisplayName(int scriptCode) {
         return UScript.getShortName(scriptCode);
      }

      public String regionDisplayName(String region) {
         return region;
      }

      public String variantDisplayName(String variant) {
         return variant;
      }

      public String keyDisplayName(String key) {
         return key;
      }

      public String keyValueDisplayName(String key, String value) {
         return value;
      }

      public List getUiListCompareWholeItems(Set localeSet, Comparator comparator) {
         return Collections.emptyList();
      }

      // $FF: synthetic method
      LastResortLocaleDisplayNames(ULocale x0, DialectHandling x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      LastResortLocaleDisplayNames(ULocale x0, DisplayContext[] x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class UiListItem {
      public final ULocale minimized;
      public final ULocale modified;
      public final String nameInDisplayLocale;
      public final String nameInSelf;

      public UiListItem(ULocale minimized, ULocale modified, String nameInDisplayLocale, String nameInSelf) {
         this.minimized = minimized;
         this.modified = modified;
         this.nameInDisplayLocale = nameInDisplayLocale;
         this.nameInSelf = nameInSelf;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj != null && obj instanceof UiListItem) {
            UiListItem other = (UiListItem)obj;
            return this.nameInDisplayLocale.equals(other.nameInDisplayLocale) && this.nameInSelf.equals(other.nameInSelf) && this.minimized.equals(other.minimized) && this.modified.equals(other.modified);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.modified.hashCode() ^ this.nameInDisplayLocale.hashCode();
      }

      public String toString() {
         return "{" + this.minimized + ", " + this.modified + ", " + this.nameInDisplayLocale + ", " + this.nameInSelf + "}";
      }

      public static Comparator getComparator(Comparator comparator, boolean inSelf) {
         return new UiListItemComparator(comparator, inSelf);
      }

      private static class UiListItemComparator implements Comparator {
         private final Comparator collator;
         private final boolean useSelf;

         UiListItemComparator(Comparator collator, boolean useSelf) {
            this.collator = collator;
            this.useSelf = useSelf;
         }

         public int compare(UiListItem o1, UiListItem o2) {
            int result = this.useSelf ? this.collator.compare(o1.nameInSelf, o2.nameInSelf) : this.collator.compare(o1.nameInDisplayLocale, o2.nameInDisplayLocale);
            return result != 0 ? result : o1.modified.compareTo(o2.modified);
         }
      }
   }

   public static enum DialectHandling {
      STANDARD_NAMES,
      DIALECT_NAMES;
   }
}
