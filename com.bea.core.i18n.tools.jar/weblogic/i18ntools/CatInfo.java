package weblogic.i18ntools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Vector;
import weblogic.i18n.Localizer;
import weblogic.utils.Getopt2;

public final class CatInfo {
   private static final String HELP = "help";
   private static final String VERBOSE = "verbose";
   private static final String DEBUG = "debug";
   private static final String NODETAIL = "nodetail";
   private static final String ID = "id";
   private static final String SUBSYSTEM = "subsystem";
   private static final String SUBSYSTEMS = "subsystems";
   private static final String LANG = "lang";
   private static final String COUNTRY = "country";
   private static final String VARIANT = "variant";
   private static final String RETIRED = "retired";
   private boolean detail = true;
   private boolean verbose = false;
   private boolean debug = false;
   private boolean retired = false;
   private boolean listsubsys = false;
   private String lang = "";
   private String country = "";
   private String variant = "";
   private String[] arglist;
   Getopt2 opts;
   private String name = CatInfo.class.getName();
   private boolean specialCase;
   private L10nLookup l10n;
   private String id;
   private String sub = "";
   private CatInfoTextFormatter cat = CatInfoTextFormatter.getInstance();

   private CatInfo(String[] args) {
      this.name = this.getName(args);
      this.arglist = args;
      this.opts = new Getopt2();
      this.opts.addFlag("help", this.cat.helpOption());
      this.opts.addAlias("h", "help");
      this.opts.addFlag("retired", this.cat.retiredOption());
      this.opts.addAlias("r", "retired");
      this.opts.addFlag("verbose", this.cat.verboseOption());
      this.opts.addFlag("debug", this.cat.debugOption());
      this.opts.addFlag("nodetail", this.cat.nodetailOption());
      this.opts.addAlias("n", "nodetail");
      this.opts.addOption("id", "nnnnnn", this.cat.idOption());
      this.opts.addAlias("i", "id");
      this.opts.addOption("subsystem", "XYZ", this.cat.subsystemOption());
      this.opts.addAlias("s", "subsystem");
      this.opts.addFlag("subsystems", this.cat.subsystemsOption());
      this.opts.addOption("lang", "en", this.cat.langOption());
      this.opts.addAlias("l", "lang");
      this.opts.addOption("country", "US", this.cat.countryOption());
      this.opts.addAlias("c", "country");
      this.opts.addOption("variant", "Mac", this.cat.variantOption());
      this.opts.addAlias("v", "variant");
      this.opts.addOption("__name__", "String", "How to overide the name of this class");
      this.opts.markPrivate("__name__");
   }

   private void runCommand() {
      this.opts.grok(this.arglist);
      if (this.arglist.length != 0 && !this.opts.hasOption("help") && (this.arglist.length != 2 || !this.specialCase)) {
         if (this.opts.hasOption("verbose")) {
            this.verbose = true;
         }

         if (this.opts.hasOption("debug")) {
            this.debug = true;
         }

         if (this.opts.hasOption("nodetail")) {
            this.detail = false;
         }

         if (this.opts.hasOption("retired")) {
            this.retired = true;
         }

         if (this.opts.hasOption("subsystem")) {
            this.sub = this.opts.getOption("subsystem");
         }

         if (this.opts.hasOption("subsystems")) {
            this.listsubsys = true;
         }

         if (this.opts.hasOption("lang")) {
            this.lang = this.opts.getOption("lang");
         }

         if (this.opts.hasOption("country")) {
            this.country = this.opts.getOption("country");
         }

         if (this.opts.hasOption("variant")) {
            this.variant = this.opts.getOption("variant");
         }

         if (!this.lang.equals("")) {
            Locale lcl = new Locale(this.lang, this.country, this.variant);
            this.debugger(this.cat.setLocale(lcl.toString()));
            Locale.setDefault(lcl);
         }

         this.debugger(this.cat.usingLocale(Locale.getDefault().toString()));
         this.l10n = L10nLookup.getL10n();
         this.l10n.ensureResourcesLoaded();
         int n;
         if (this.listsubsys) {
            Vector vec = this.l10n.getSubSystems();

            for(n = 0; n < vec.size(); ++n) {
               String txt = (String)vec.get(n);
               this.print(txt);
            }

         } else {
            if (this.opts.hasOption("id")) {
               try {
                  DecimalFormat df = new DecimalFormat("000000");
                  n = Integer.parseInt(this.opts.getOption("id"));
                  this.id = df.format((long)n);
               } catch (NumberFormatException var6) {
                  this.id = this.opts.getOption("id");
               }

               String txt = GetText.getText(this.id, this.detail, this.verbose, this.sub, Locale.getDefault());
               if (txt == null || txt.length() == 0) {
                  txt = GetText.getRetiredMessage(this.id, this.sub);
                  if (txt == null) {
                     if (this.sub.length() == 0) {
                        txt = this.cat.noSuchMessage(this.id);
                     } else {
                        txt = this.cat.noSuchMessage(this.sub + ":" + this.id);
                     }
                  }
               }

               this.print(txt);
            } else {
               Enumeration e = this.l10n.propertyNames();
               List keys = new ArrayList();

               while(e.hasMoreElements()) {
                  keys.add(e.nextElement());
               }

               Collections.sort(keys);
               Iterator iter = keys.iterator();

               while(iter.hasNext()) {
                  String id = (String)iter.next();
                  String text;
                  if (this.retired) {
                     text = GetText.getRetiredMessage(id, this.sub);
                     if (text != null && text.length() > 0) {
                        this.print(text);
                     }
                  } else {
                     text = GetText.getText(id, this.detail, this.verbose, this.sub, Locale.getDefault());
                     if (text != null && text.length() > 0) {
                        if (!this.detail) {
                           text = id + ": " + text;
                        }

                        this.print(text);
                     }
                  }
               }
            }

         }
      } else {
         this.opts.usageError(this.name);
      }
   }

   private boolean checkFilter(Localizer l) {
      boolean retval = false;
      if (l != null && this.opts.hasOption("subsystem")) {
         String s = l.getSubSystem();
         if (!this.sub.equals(s)) {
            retval = true;
         }
      }

      return retval;
   }

   private Localizer getLocalizer(String key) {
      Localizer l = null;

      try {
         l = this.l10n.getLocalizer(key);
      } catch (MissingResourceException var4) {
      }

      return l;
   }

   private void print(String msg) {
      System.out.println(msg);
   }

   private void debugger(String msg) {
      if (this.debug) {
         this.print(msg);
      }

   }

   private String getName(String[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("__name__")) {
               this.specialCase = true;
               ++i;
               return args[i];
            }
         }
      }

      return this.getClass().getName();
   }

   public static void main(String[] args) throws Exception {
      try {
         (new CatInfo(args)).runCommand();
      } catch (Exception var2) {
         var2.printStackTrace();
         System.exit(1);
      }

   }
}
