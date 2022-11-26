package weblogic.i18n.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class MethodPropGen extends PropGen {
   private boolean debug = false;
   private static final String METHOD_PROPERTIES_RESOURCE = "methods.properties";
   private static final String USER_METHOD_PROPERTIES_RESOURCE = "user_methods.properties";

   public MethodPropGen(String dest, Config c) {
      this.propDir = dest;
      this.cfg = c;
      if (this.debug) {
         this.cfg.setDebug(true);
      }

   }

   public void loadProp() {
      if (this.cfg.server) {
         this.propFile = this.propDir + File.separator + "methods.properties";
         File pFile = new File(this.propFile);

         try {
            pFile.createNewFile();
         } catch (IOException var4) {
            this.cfg.inform("Can't create new method properties: " + var4.toString());
         }
      } else {
         this.propFile = this.propDir + File.separator + "user_methods.properties";
      }

      this.cfg.debug("Loading properties from " + this.propFile);
      this.i18nProps = new Properties();

      try {
         this.propIs = new FileInputStream(this.propFile);
         this.i18nProps.load(this.propIs);
         this.propIs.close();
      } catch (Throwable var3) {
         this.cfg.debug(var3.toString());
      }

   }

   public void gen(MessageCatalog msgcat) throws GenException {
      String pkg = msgcat.get("i18n_package");
      this.cfg.debug("checking methods for package " + pkg);
      Vector v = msgcat.getLogMessages();
      if (v != null) {
         Enumeration e = v.elements();

         while(e.hasMoreElements()) {
            LogMessage lm = (LogMessage)e.nextElement();
            String id = lm.get("messageid");
            if (!lm.isRetired()) {
               String method = lm.getMethodName();
               Object old = this.i18nProps.put(id, pkg + "." + method);
               if (old == null) {
                  this.cfg.debug("Added property: " + id + "=" + pkg + "." + method);
               } else {
                  this.cfg.debug("Replaced property: " + id + "=" + pkg + "." + method);
               }
            }
         }
      }

   }
}
