package weblogic.i18n.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class PropGen {
   String propDir;
   String propFile;
   FileInputStream propIs;
   Properties i18nProps;
   boolean backup;
   Config cfg;
   boolean debug;

   public PropGen() {
      this(System.getProperty("user.dir"), new Config());
   }

   public PropGen(Config c) {
      this(System.getProperty("user.dir"), c);
   }

   public PropGen(String dest) {
      this(dest, new Config());
   }

   public PropGen(String dest, Config c) {
      this.backup = false;
      this.debug = false;
      this.propDir = dest;
      this.cfg = c;
      if (this.debug) {
         this.cfg.setDebug(true);
      }

   }

   public void loadProp() throws IOException {
      if (this.cfg.server) {
         this.propFile = this.propDir + File.separator + "weblogic/i18n/i18n.properties";
      } else {
         this.propFile = this.propDir + File.separator + "i18n_user.properties";
      }

      File pFile = new File(this.propFile);
      File parent = pFile.getParentFile();
      if (parent != null) {
         parent.mkdirs();
      }

      pFile.createNewFile();
      this.cfg.debug("Loading properties from " + this.propFile);
      this.i18nProps = new Properties();

      try {
         this.propIs = new FileInputStream(this.propFile);
         this.i18nProps.load(this.propIs);
      } finally {
         if (this.propIs != null) {
            this.propIs.close();
         }

      }

   }

   public void gen(MessageCatalog msgcat) throws GenException {
      String subSystem = msgcat.get("subsystem");
      String tPath = msgcat.get("filename");
      int suffix = tPath.indexOf(".xml");
      String pkg = msgcat.get("l10n_package");
      String fileName = pkg + "." + tPath.substring(0, suffix) + "LogLocalizer";
      this.cfg.debug("Gen properties for " + subSystem + ":" + fileName);
      Vector v = msgcat.getLogMessages();
      if (v != null) {
         Enumeration e = v.elements();

         while(e.hasMoreElements()) {
            LogMessage lm = (LogMessage)e.nextElement();
            String id = lm.get("messageid");
            Object old;
            if (lm.isRetired()) {
               old = this.i18nProps.put(id, subSystem + ":" + "retired");
               if (old != null) {
                  this.cfg.warn("Retired property: " + id + "=" + subSystem + ":" + fileName);
               }
            } else {
               old = this.i18nProps.put(id, subSystem + ":" + fileName);
               if (old == null) {
                  this.cfg.debug("Added property: " + id + "=" + subSystem + ":" + fileName);
               } else {
                  this.cfg.debug("Replaced property: " + id + "=" + subSystem + ":" + fileName);
               }
            }
         }
      }

   }

   public void unload() throws GenException {
      if (this.backup) {
         File pFile = new File(this.propFile);
         File bFile = new File(this.propDir + File.separator + "i18n.oldprops");

         try {
            bFile.delete();
            pFile.renameTo(bFile);
         } catch (SecurityException var12) {
            this.cfg.inform("Can't backup old props to " + bFile.getAbsolutePath());
            this.cfg.inform(var12.toString());
         }
      }

      FileOutputStream propOs = null;

      try {
         String errmsg;
         try {
            propOs = new FileOutputStream(this.propFile);
            this.i18nProps.store(propOs, "I18n Properties");
         } catch (FileNotFoundException var13) {
            errmsg = "Can't store new props to " + this.propFile + ", " + var13;
            this.cfg.inform(errmsg);
            throw new GenException(errmsg);
         } catch (IOException var14) {
            errmsg = "Can't store new props to " + this.propFile + ", " + var14;
            this.cfg.inform(errmsg);
            if (this.backup) {
               try {
                  File bFile = new File(this.propDir + File.separator + "i18n.oldprops");
                  bFile.renameTo(new File(this.propFile));
               } catch (SecurityException var11) {
                  this.cfg.inform("Can't restore old props to " + this.propFile);
                  this.cfg.inform(var11.toString());
               }
            }

            throw new GenException(errmsg);
         }
      } finally {
         this.close(propOs);
      }

   }

   private void close(FileOutputStream os) {
      try {
         if (os != null) {
            os.close();
         }
      } catch (IOException var3) {
      }

   }
}
