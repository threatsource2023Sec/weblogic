package weblogic.i18ntools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import weblogic.i18n.tools.GenException;
import weblogic.i18ntools.internal.I18nConfig;
import weblogic.i18ntools.parser.L10nParserTextFormatter;
import weblogic.i18ntools.parser.LocaleCatalogParser;
import weblogic.i18ntools.parser.LocaleLogMessage;
import weblogic.i18ntools.parser.LocaleMessage;
import weblogic.i18ntools.parser.LocaleMessageCatalog;
import weblogic.utils.Getopt2;

public final class l10ngen {
   static final String VERBOSE = "verbose";
   static final String DEBUG = "debug";
   static final String IGNORE = "ignore";
   static final String LANGUAGE = "language";
   static final String COUNTRY = "country";
   static final String VARIANT = "variant";
   static final String NOEXIT = "noexit";
   static final int LOG = 1;
   static final int LOG_DETAIL = 2;
   static final int TEXT = 3;
   private String[] arglist;
   private boolean verbose = false;
   private boolean debug = false;
   private boolean ignore = false;
   private static boolean noexit = false;
   private String language;
   private String country;
   private String variant;
   private String target;
   private LocaleLogMessage currentLogMessage;
   private LocaleMessage currentMessage;
   private Set outputs = new HashSet();
   private HashSet catalogs = new HashSet();
   private static I18nConfig cfg;
   private LocaleMessageCatalog currCatalog;
   private String currLanguage = "";
   private String currCountry = "";
   private String currVariant = "";
   private Locale currLocale;
   private static L10nParserTextFormatter fmt;
   private Getopt2 opts;

   private void prepare(String[] args) {
      this.arglist = args;
      this.opts = new Getopt2();
      fmt = new L10nParserTextFormatter();
      this.opts.addOption("language", fmt.language(), fmt.ISOLanguage());
      this.opts.addAlias("l", "language");
      this.opts.addOption("country", fmt.country(), fmt.ISOCountry());
      this.opts.addAlias("c", "country");
      this.opts.addOption("variant", fmt.variant(), fmt.localeVariant());
      this.opts.addAlias("v", "variant");
      this.opts.addFlag("ignore", fmt.ignoreErrors());
      this.opts.addFlag("verbose", fmt.verbose());
      this.opts.addFlag("debug", fmt.debug());
      this.opts.addFlag("noexit", "Will not invoke System.exit()");
      this.opts.markPrivate("debug");
      this.opts.addOption("d", "dir", "Target (top-level) directory.");
      this.opts.setUsageArgs(fmt.filelist());
      this.opts.grok(this.arglist);
      noexit = this.opts.hasOption("noexit");
      this.ignore = this.opts.hasOption("ignore");
      this.debug = this.opts.hasOption("debug");
      this.verbose = this.opts.hasOption("verbose");
      this.language = this.opts.getOption("language");
      this.country = this.opts.getOption("country");
      this.variant = this.opts.getOption("variant");
      this.target = this.opts.getOption("d");
      if (this.target == null) {
         this.target = ".";
      }

      cfg = new I18nConfig();
      cfg.setDebug(this.debug);
      cfg.setVerbose(this.verbose);
      cfg.debug(fmt.options());
      cfg.debug(fmt.ignoreOpt(this.ignore));
      cfg.debug("noexit=" + noexit);
      cfg.debug(fmt.verboseOpt(this.verbose));
      cfg.debug(fmt.debugOpt(this.debug));
      cfg.debug(fmt.langOpt(this.language));
      cfg.debug(fmt.countryOpt(this.country));
      cfg.debug(fmt.variantOpt(this.variant));
   }

   private void buildVariantOutputs(File variantDir, String name) throws GenException {
      if (this.variant == null || this.variant.equals(variantDir.getName())) {
         this.currVariant = variantDir.getName();
         File[] content = variantDir.listFiles();

         for(int i = 0; i < content.length; ++i) {
            File curr = content[i];
            if (!curr.isDirectory()) {
               try {
                  this.parseIt(curr, name);
               } catch (GenException var7) {
                  if (!this.ignore) {
                     throw var7;
                  }
               }
            }
         }
      }

      this.currVariant = "";
   }

   private void buildCountryOutputs(File countryDir, String name) throws GenException {
      if (this.country == null || this.country.equals(countryDir.getName())) {
         this.currCountry = countryDir.getName();
         File[] content = countryDir.listFiles();

         for(int i = 0; i < content.length; ++i) {
            File curr = content[i];
            if (curr.isDirectory()) {
               this.buildVariantOutputs(curr, name);
            } else {
               try {
                  this.parseIt(curr, name);
               } catch (GenException var7) {
                  if (!this.ignore) {
                     throw var7;
                  }
               }
            }
         }
      }

      this.currCountry = "";
   }

   private void buildLangOutputs(String name, String path) throws GenException {
      if (path == null) {
         path = ".";
      }

      cfg.debug(fmt.buildingLang(path, name));
      cfg.debug(fmt.langVal(this.language));
      File catDir = new File(path);
      File[] content = catDir.listFiles();
      if (content != null && content.length > 0) {
         for(int i = 0; i < content.length; ++i) {
            File currDir = content[i];
            if (currDir.isDirectory() && (this.language == null || this.language.equals(currDir.getName()))) {
               this.currLanguage = currDir.getName();
               File[] langContent = currDir.listFiles();

               for(int j = 0; j < langContent.length; ++j) {
                  File curr = langContent[j];
                  if (curr.isDirectory()) {
                     this.buildCountryOutputs(curr, name);
                  } else {
                     try {
                        this.parseIt(curr, name);
                     } catch (GenException var11) {
                        if (!this.ignore) {
                           throw var11;
                        }
                     }
                  }
               }
            }

            this.currLanguage = "";
         }
      }

   }

   private void parseIt(File f, String name) throws GenException {
      if (name == null || name.equals(f.getName())) {
         if (f.getName().endsWith(".xml")) {
            try {
               String fPath = f.getCanonicalPath();
               cfg.inform(fmt.parsing(fPath));
               cfg.debug(fmt.currLocale(this.currLanguage, this.currCountry, this.currVariant));
               Locale lcl = new Locale(this.currLanguage, this.currCountry, this.currVariant);
               LocaleCatalogParser parser = new LocaleCatalogParser(lcl, cfg, true);
               LocaleMessageCatalog currCatalog = parser.parse(fPath);
               if (currCatalog != null) {
                  Output output;
                  if (currCatalog.getCatType() == 2) {
                     output = new Output(fPath, "LogLocalizer", currCatalog);
                     output.setPath(this.target);
                     this.createProperties(currCatalog, 1, output.getPath());
                     this.outputs.add(output);
                     output = new Output(fPath, "LogLocalizerDetail", currCatalog);
                     output.setPath(this.target);
                     this.createProperties(currCatalog, 2, output.getPath());
                     this.outputs.add(output);
                  } else if (currCatalog.getCatType() == 1) {
                     output = new Output(fPath, "TextLocalizer", currCatalog);
                     output.setPath(this.target);
                     this.createProperties(currCatalog, 3, output.getPath());
                     this.outputs.add(output);
                  }
               } else {
                  cfg.inform(fmt.unableToParse(fPath));
               }
            } catch (IOException var9) {
               cfg.warn(var9.toString());
               if (this.debug) {
                  var9.printStackTrace();
               }

               throw new GenException(fmt.writeError(var9.toString()));
            } catch (Throwable var10) {
               cfg.warn(var10.toString());
               if (this.debug) {
                  var10.printStackTrace();
               }

               throw new GenException(fmt.unableToParse(f.getAbsolutePath()));
            }
         }

      }
   }

   private void createProperties(LocaleMessageCatalog cat, int catType, String path) throws IOException {
      FileOutputStream fos = null;

      try {
         File f = new File(path);
         File parent = f.getParentFile();
         if (parent != null && !parent.mkdirs() && !parent.exists()) {
            cfg.inform("Parent dir does not exist " + parent);
            throw new IOException(fmt.noPath(parent.getAbsolutePath()));
         }

         fos = new FileOutputStream(path);
         String data = "version=" + cat.getVersion() + "\n";
         fos.write(this.getBytes(this.getUnicodeEscape(data)));
         Iterator it;
         String id;
         if (catType == 3) {
            it = cat.getMessages().iterator();

            while(it.hasNext()) {
               LocaleMessage currentMessage = (LocaleMessage)it.next();
               id = currentMessage.getMessageId();
               if (!currentMessage.isRetired()) {
                  fos.write(this.getBytes(this.getUnicodeEscape(id + "=" + currentMessage.getMessageBody().getCdata() + "\n")));
               } else {
                  cfg.debug("Skipping retired message: " + id);
               }
            }
         } else {
            it = cat.getLogMessages().iterator();

            while(it.hasNext()) {
               LocaleLogMessage currentLogMessage = (LocaleLogMessage)it.next();
               id = currentLogMessage.getMessageId();
               if (!currentLogMessage.isRetired()) {
                  if (catType == 1) {
                     fos.write(this.getBytes(this.getUnicodeEscape(id + "=" + currentLogMessage.getMessageBody().getCdata() + "\n")));
                  } else {
                     fos.write(this.getBytes(this.getUnicodeEscape("messagedetail" + id + "=" + currentLogMessage.getMessageDetail().getCdata() + "\n")));
                     fos.write(this.getBytes(this.getUnicodeEscape("cause" + id + "=" + currentLogMessage.getCause().getCdata() + "\n")));
                     fos.write(this.getBytes(this.getUnicodeEscape("action" + id + "=" + currentLogMessage.getAction().getCdata() + "\n")));
                  }
               } else {
                  cfg.debug("Skipping retired message: " + id);
               }
            }
         }
      } finally {
         if (fos != null) {
            fos.close();
         }

      }

   }

   private String getUnicodeEscape(String str) {
      StringBuilder sb = new StringBuilder();
      char[] buf = str.toCharArray();
      int len = buf.length;

      for(int i = 0; i < len; ++i) {
         if (buf[i] <= 127) {
            sb.append(buf[i]);
         } else {
            sb.append('\\');
            sb.append('u');
            String hex = Integer.toHexString(buf[i]);
            StringBuilder hex4 = new StringBuilder(hex);
            hex4.reverse();
            int length = 4 - hex4.length();

            int j;
            for(j = 0; j < length; ++j) {
               hex4.append('0');
            }

            for(j = 0; j < 4; ++j) {
               sb.append(hex4.charAt(3 - j));
            }
         }
      }

      return sb.toString();
   }

   private byte[] getBytes(String s) throws UnsupportedEncodingException {
      try {
         return s.getBytes("ISO_8859-1");
      } catch (UnsupportedEncodingException var3) {
         return s.getBytes("UTF-8");
      }
   }

   private Enumeration generate(String[] inputs) {
      try {
         for(int i = 0; i < inputs.length; ++i) {
            File f = new File(inputs[i]);
            String fName = f.getAbsolutePath();
            if (!f.isDirectory()) {
               this.buildLangOutputs(f.getName(), f.getParent());
            } else {
               this.buildLangOutputs((String)null, fName);
            }
         }

         return Collections.enumeration(this.outputs);
      } catch (Throwable var5) {
         return null;
      }
   }

   private void run() throws IOException, GenException {
      Enumeration props = this.generate(this.opts.args());
      if (props == null || !props.hasMoreElements()) {
         cfg.warn(fmt.nothingToDo());
         throw new GenException("No properties generated");
      }
   }

   public static void main(String[] args) throws Exception {
      l10ngen l = new l10ngen();

      try {
         l.prepare(args);
         l.run();
      } catch (Exception var3) {
         if (l.debug) {
            var3.printStackTrace();
         }

         if (noexit) {
            throw var3;
         }

         if (!l.ignore) {
            System.exit(1);
         }
      }

   }

   private static class Output {
      String currClassName = null;
      LocaleMessageCatalog catalog = null;
      String className = null;
      String srcName = null;
      String pkgName = null;
      String path = null;

      Output(String inputFile, String outName, LocaleMessageCatalog msgcat) {
         this.makeOutputFile(inputFile, outName, msgcat.getLocale());
         l10ngen.cfg.debug(l10ngen.fmt.inputFile(inputFile));
         this.setCatalog(msgcat);
         this.setPackage();
      }

      LocaleMessageCatalog getMessageCatalog() {
         return this.catalog;
      }

      void setCatalog(LocaleMessageCatalog cat) {
         this.catalog = cat;
      }

      String getClassName() {
         return this.className;
      }

      void setClassName(String name) {
         this.className = name;
      }

      String getSrcName() {
         return this.srcName;
      }

      void setSrcName(String name) {
         this.srcName = name;
      }

      String getPackage() {
         return this.pkgName;
      }

      void setPackage() {
         this.pkgName = this.catalog.getL10nPackage();
      }

      String getPath() {
         return this.path;
      }

      void setPath(String target) {
         this.path = target + "/" + this.getPackage().replace('.', '/') + "/" + this.getClassName() + ".properties";
      }

      private String makeOutputFile(String inputFile, String outName, Locale lcl) {
         File f = new File(inputFile);
         String inFilename = f.getName();
         inputFile = inFilename.substring(0, inFilename.length() - ".xml".length());
         this.currClassName = inputFile + outName;
         String lang = lcl.getLanguage();
         String country = lcl.getCountry();
         String variant = lcl.getVariant();
         this.currClassName = this.currClassName + "_" + lang;
         if (country != null && !country.equals("")) {
            this.currClassName = this.currClassName + "_" + country;
            if (variant != null && !variant.equals("")) {
               this.currClassName = this.currClassName + "_" + variant;
            }
         }

         this.setSrcName(this.currClassName + ".properties");
         this.setClassName(this.currClassName);
         l10ngen.cfg.debug(l10ngen.fmt.outFile(this.getSrcName()));
         return this.getSrcName();
      }
   }
}
