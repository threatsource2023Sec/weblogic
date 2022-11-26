package kodo.bea;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.lib.util.FormatPreservingProperties;
import org.apache.openjpa.lib.util.Options;
import serp.util.Strings;

public class LocalizedMessageMapper {
   static final String MESSAGE_ID_DICTIONARY_RESOURCE = "kodo/bea/message-id-dictionary.properties";
   private static final int MIN_KODO_BEA_SEQ = 2000000;
   private static final int MAX_KODO_BEA_SEQ = 2001999;
   private static final int MIN_KODO_JDBC_BEA_SEQ = 2002000;
   private static final int MAX_KODO_JDBC_BEA_SEQ = 2003999;
   private File msgCatalogDir;
   private Set paths = new HashSet();
   private Properties dictionary;
   private File dictionaryFile;
   private Collection newKodoMessages = new ArrayList();
   private Collection newKodoJdbcMessages = new ArrayList();
   private Collection kodoMessages = new ArrayList();
   private Collection kodoJdbcMessages = new ArrayList();
   private Set properties = new HashSet();

   public static void main(String[] args) throws IOException {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      boolean usage = true;

      try {
         if (args.length >= 3) {
            LocalizedMessageMapper m = new LocalizedMessageMapper();
            opts.setInto(m);
            m.setDictionaryFile(args[0]);
            m.setMessageCatalogDirectory(args[1]);
            m.paths.clear();

            for(int i = 2; i < args.length; ++i) {
               m.paths.add(new File(args[i]));
            }

            m.run();
            usage = false;
         }
      } finally {
         if (usage) {
            System.out.println("Usage: LocalizedMessageMapper <dict-file> <msg-cat-dir> <files-and-dirs-to-scan>+");
         }

      }

   }

   public LocalizedMessageMapper() {
      OpenJPAConfiguration conf = Bootstrap.newBrokerFactory().getConfiguration();

      for(int i = 0; i < conf.getValues().length; ++i) {
         String prop = conf.getValues()[i].getProperty();
         if (prop.indexOf(46) != -1) {
            prop = prop.substring(prop.lastIndexOf(46) + 1);
         }

         this.properties.add(prop);
      }

   }

   public void setDictionaryFile(String dict) {
      this.dictionaryFile = new File(dict);
   }

   public void setMessageCatalogDirectory(String dir) {
      this.msgCatalogDir = new File(dir);
   }

   private boolean ignorePathElement(String elem) {
      return elem.indexOf("openjpa") == -1 && !elem.endsWith("src") && !elem.endsWith("src/") && !elem.endsWith("src\\");
   }

   public void run() throws IOException {
      this.loadDictionary();
      Iterator iter = this.paths.iterator();

      while(iter.hasNext()) {
         File f = (File)iter.next();
         if (!f.exists()) {
            System.err.println("Skipping nonexistant file / dir " + f);
         } else if (f.isDirectory()) {
            this.addLocalizersInDirectory(f, f);
         } else {
            this.addLocalizersInJar(f);
         }
      }

      this.populateNewBEAMessageIds();
      this.saveDictionary();
      this.saveSubsystemCatalogs();
   }

   private void populateNewBEAMessageIds() {
      this.populateNewBEAMessageIds(2000000, 2001999, "Kodo", this.kodoMessages, this.newKodoMessages);
      this.populateNewBEAMessageIds(2002000, 2003999, "Kodo JDBC", this.kodoJdbcMessages, this.newKodoJdbcMessages);
   }

   private void populateNewBEAMessageIds(int minAvailableId, int maxAvailableId, String subName, Collection msgs, Collection newMsgs) {
      Iterator iter = msgs.iterator();

      LocalizedMessage msg;
      while(iter.hasNext()) {
         msg = (LocalizedMessage)iter.next();
         if (msg.hasBEAId()) {
            minAvailableId = Math.max(minAvailableId, msg.getBEAId());
         }
      }

      if (minAvailableId + newMsgs.size() > maxAvailableId) {
         throw new IllegalStateException("There are " + newMsgs.size() + " new messages to add to the BEA message catalogs, but only " + (maxAvailableId - minAvailableId) + " BEA IDs remaining in the " + subName + " subsystem range.");
      } else {
         iter = newMsgs.iterator();

         while(iter.hasNext()) {
            msg = (LocalizedMessage)iter.next();
            msg.setBEAId(minAvailableId++);
            this.dictionary.setProperty(msg.getKodoKey(), Integer.toString(msg.getBEAId()));
         }

      }
   }

   private void addLocalizersInDirectory(File basedir, File dir) throws IOException {
      File[] localizerFiles = dir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return "localizer.properties".equals(name);
         }
      });
      String packageName = this.computePackageName(basedir.getCanonicalPath(), dir.getCanonicalPath());

      for(int i = 0; i < localizerFiles.length; ++i) {
         InputStream in = new BufferedInputStream(new FileInputStream(localizerFiles[i]));

         try {
            this.addKodoMessages(packageName, in);
         } finally {
            in.close();
         }
      }

      File[] dirs = dir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return (new File(dir, name)).isDirectory();
         }
      });

      for(int i = 0; i < dirs.length; ++i) {
         this.addLocalizersInDirectory(basedir, dirs[i]);
      }

   }

   private String computePackageName(String base, String fullPath) {
      if (base != null && !fullPath.startsWith(base)) {
         throw new IllegalStateException(fullPath + " does not start with " + base);
      } else {
         String packageName;
         for(packageName = base == null ? fullPath : fullPath.substring(base.length()); packageName.startsWith("/") || packageName.startsWith("\\"); packageName = packageName.substring(1)) {
         }

         while(packageName.endsWith("/") || packageName.endsWith("\\")) {
            packageName = packageName.substring(0, packageName.length() - 1);
         }

         packageName = Strings.replace(packageName, "/", ".");
         packageName = Strings.replace(packageName, "\\", ".");
         return packageName;
      }
   }

   private void addLocalizersInJar(File jar) throws IOException {
      ZipFile zip = new ZipFile(jar);
      Enumeration entries = zip.entries();

      while(entries.hasMoreElements()) {
         ZipEntry entry = (ZipEntry)entries.nextElement();
         if (entry.getName().endsWith("localizer.properties")) {
            this.addLocalizers(zip, entry);
         }
      }

   }

   private void addLocalizers(ZipFile zip, ZipEntry entry) throws IOException {
      String packageName = entry.getName().substring(0, entry.getName().lastIndexOf("localizer.properties"));
      packageName = this.computePackageName((String)null, packageName);
      InputStream in = zip.getInputStream(entry);

      try {
         this.addKodoMessages(packageName, in);
      } finally {
         in.close();
      }

   }

   private void addKodoMessages(String packageName, InputStream in) throws IOException {
      Properties messages = new Properties();
      messages.load(in);
      Iterator iter = messages.keySet().iterator();

      while(iter.hasNext()) {
         String shortKey = (String)iter.next();
         if (!this.ignoreKodoKey(packageName, shortKey)) {
            LocalizedMessage msg = new LocalizedMessage(packageName, shortKey, messages.getProperty(shortKey));
            if (!this.dictionary.containsKey(msg.getKodoKey())) {
               if ("Kodo".equals(msg.getBEASubsystem())) {
                  this.newKodoMessages.add(msg);
               } else {
                  if (!"Kodo JDBC".equals(msg.getBEASubsystem())) {
                     throw new IllegalStateException("subsystem=" + msg.getBEASubsystem() + " was unexpected");
                  }

                  this.newKodoJdbcMessages.add(msg);
               }

               this.dictionary.setProperty(msg.getKodoKey(), Integer.toString(msg.getBEAId()));
            } else {
               msg.setBEAId(Integer.parseInt(this.dictionary.getProperty(msg.getKodoKey())));
            }

            if ("Kodo".equals(msg.getBEASubsystem())) {
               this.kodoMessages.add(msg);
            } else if ("Kodo JDBC".equals(msg.getBEASubsystem())) {
               this.kodoJdbcMessages.add(msg);
            }
         }
      }

   }

   private boolean ignoreKodoKey(String packageName, String shortKey) {
      if (shortKey.endsWith("-displayOrder") || shortKey.endsWith("-name") || shortKey.endsWith("-cat") || shortKey.endsWith("-desc") || shortKey.endsWith("-type") || shortKey.endsWith("-expert") || shortKey.endsWith("-displayorder") || shortKey.endsWith("-values") || shortKey.endsWith("-interface")) {
         String prop = shortKey.substring(0, shortKey.lastIndexOf("-"));
         if (this.properties.contains(prop)) {
            return true;
         }
      }

      if (packageName.indexOf(".ide") != -1) {
         return true;
      } else if (packageName.indexOf(".gui") != -1) {
         return true;
      } else if (packageName.indexOf(".jdbc.integration") != -1) {
         return true;
      } else {
         return packageName.indexOf(".jdbc.migration.kodo2") != -1;
      }
   }

   private void loadDictionary() throws IOException {
      if (!this.dictionaryFile.exists()) {
         this.dictionary = new FormatPreservingProperties();
      } else {
         InputStream in = new BufferedInputStream(new FileInputStream(this.dictionaryFile));

         try {
            this.dictionary = new FormatPreservingProperties();
            this.dictionary.load(in);
         } finally {
            in.close();
         }
      }

   }

   private void saveDictionary() throws IOException {
      if (!this.dictionaryFile.exists()) {
         this.dictionaryFile.createNewFile();
      }

      OutputStream out = new BufferedOutputStream(new FileOutputStream(this.dictionaryFile));

      try {
         this.dictionary.store(out, (String)null);
      } finally {
         out.close();
      }

   }

   private void saveSubsystemCatalogs() throws IOException {
      if (!this.msgCatalogDir.exists()) {
         this.msgCatalogDir.mkdirs();
      }

      this.writeMessageCatalog("Kodo", this.kodoMessages, 2000000, 2001999, "kodo.bea");
      this.writeMessageCatalog("Kodo JDBC", this.kodoJdbcMessages, 2002000, 2003999, "kodo.bea.jdbc");
   }

   private void writeMessageCatalog(String subsystem, Collection messages, int minSeq, int maxSeq, String pkg) throws IOException {
      String eol = "\n";
      StringBuffer buf = new StringBuffer();
      buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(eol);
      buf.append("<!DOCTYPE message_catalog PUBLIC ").append("\"weblogic-message-catalog-dtd\"  ").append("\"http://www.bea.com/servers/wls710/dtd/msgcat.dtd\">").append(eol);
      buf.append("<message_catalog").append(eol);
      buf.append("    i18n_package=\"" + pkg + "\"").append(eol);
      buf.append("    l10n_package=\"" + pkg + "\"").append(eol);
      buf.append("    subsystem=\"").append(subsystem).append("\"").append(eol);
      buf.append("    version=\"1.0\"").append(eol);
      buf.append("    baseid=\"" + minSeq + "\"").append(eol);
      buf.append("    endid=\"" + maxSeq + "\"").append(eol);
      buf.append("    loggables=\"true\"").append(eol);
      buf.append("    prefix=\"BEA\"").append(eol);
      buf.append("    >").append(eol);
      buf.append(eol);
      String fileName = Strings.replace(subsystem, " ", "") + ".xml";
      File f = new File(this.msgCatalogDir, fileName);
      PrintWriter out = new PrintWriter(new FileOutputStream(f));

      try {
         out.print(buf.toString());
         out.flush();
         Iterator iter = messages.iterator();

         while(iter.hasNext()) {
            ((LocalizedMessage)iter.next()).writeXML(out, eol);
            out.flush();
         }

         out.print("</message_catalog>");
         out.flush();
      } finally {
         out.close();
      }
   }

   static String toKodoKey(String pkg, String name) {
      return pkg + "/" + name;
   }
}
