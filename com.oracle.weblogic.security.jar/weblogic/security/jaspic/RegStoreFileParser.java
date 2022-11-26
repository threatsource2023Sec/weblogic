package weblogic.security.jaspic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.message.config.AuthConfigFactory;

public class RegStoreFileParser implements PersistentRegistrationSource {
   private static Logger logger = Logger.getLogger("Security");
   private static final String SEP = ":";
   private static final String CON_ENTRY = "con-entry";
   private static final String REG_ENTRY = "reg-entry";
   private static final String REG_CTX = "reg-ctx";
   private static final String LAYER = "layer";
   private static final String APP_CTX = "app-ctx";
   private static final String DESCRIPTION = "description";
   private static final String[] INDENT = new String[]{"", "  ", "    "};
   private File confFile;
   private List entries;

   public RegStoreFileParser(String pathParent, String pathChild, boolean create) {
      try {
         this.confFile = new File(pathParent, pathChild);
         if (this.confFile.exists()) {
            this.loadEntries();
         } else if (create) {
            synchronized(this.confFile) {
               this.writeEntries();
            }
         } else if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "jmac.factory_file_not_found", pathParent + File.pathSeparator + pathChild);
         }
      } catch (IOException var7) {
         this.logWarningDefault(var7);
      } catch (IllegalArgumentException var8) {
         this.logWarningDefault(var8);
      }

      if (this.entries == null) {
         this.entries = new ArrayList();
      }

   }

   private void logWarningUpdated(Exception exception) {
      if (logger.isLoggable(Level.WARNING)) {
         logger.log(Level.WARNING, "jmac.factory_could_not_persist", exception.toString());
      }

   }

   private void logWarningDefault(Exception exception) {
      if (logger.isLoggable(Level.WARNING)) {
         logger.log(Level.WARNING, "jmac.factory_could_not_read", exception.toString());
      }

   }

   public List getPersistedEntries() {
      return this.entries;
   }

   public void store(String className, AuthConfigFactory.RegistrationContext ctx, Map properties) {
      synchronized(this.confFile) {
         if (this.checkAndAddToList(className, ctx, properties)) {
            try {
               this.writeEntries();
            } catch (IOException var7) {
               this.logWarningUpdated(var7);
            }
         }

      }
   }

   public void delete(AuthConfigFactory.RegistrationContext ctx) {
      synchronized(this.confFile) {
         if (this.checkAndRemoveFromList(ctx)) {
            try {
               this.writeEntries();
            } catch (IOException var5) {
               this.logWarningUpdated(var5);
            }
         }

      }
   }

   private boolean checkAndAddToList(String className, AuthConfigFactory.RegistrationContext ctx, Map props) {
      if (props != null && props.isEmpty()) {
         props = null;
      }

      EntryInfo newEntry = new EntryInfo(className, props, ctx);
      EntryInfo entry = this.getMatchingRegEntry(newEntry);
      if (entry == null) {
         this.entries.add(newEntry);
         return true;
      } else if (entry.isConstructorEntry()) {
         return false;
      } else if (entry.getRegContexts().contains(ctx)) {
         return false;
      } else {
         entry.getRegContexts().add(new RegistrationContextImpl(ctx));
         return true;
      }
   }

   private boolean checkAndRemoveFromList(AuthConfigFactory.RegistrationContext target) {
      boolean retValue = false;
      Iterator var3 = this.entries.iterator();

      while(true) {
         EntryInfo info;
         do {
            if (!var3.hasNext()) {
               return retValue;
            }

            info = (EntryInfo)var3.next();
         } while(info.isConstructorEntry());

         Iterator iter = info.getRegContexts().iterator();

         while(iter.hasNext()) {
            AuthConfigFactory.RegistrationContext ctx = (AuthConfigFactory.RegistrationContext)iter.next();
            if (ctx.equals(target)) {
               iter.remove();
               retValue = true;
            }
         }
      }
   }

   private EntryInfo getMatchingRegEntry(EntryInfo target) {
      Iterator var2 = this.entries.iterator();

      EntryInfo info;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         info = (EntryInfo)var2.next();
      } while(!info.equals(target));

      return info;
   }

   private void writeEntries() throws IOException {
      if (!this.confFile.canWrite() && logger.isLoggable(Level.WARNING)) {
         logger.log(Level.WARNING, "jmac.factory_cannot_write_file", this.confFile.getPath());
      }

      this.clearExistingFile();
      PrintWriter out = new PrintWriter(this.confFile);
      int indent = 0;
      Iterator var3 = this.entries.iterator();

      while(var3.hasNext()) {
         EntryInfo info = (EntryInfo)var3.next();
         if (info.isConstructorEntry()) {
            this.writeConEntry(info, out, indent);
         } else {
            this.writeRegEntry(info, out, indent);
         }
      }

      out.close();
   }

   private void writeConEntry(EntryInfo info, PrintWriter out, int i) {
      out.println(INDENT[i++] + "con-entry" + " {");
      out.println(INDENT[i] + info.getClassName());
      Map props = info.getProperties();
      if (props != null) {
         Iterator var5 = props.keySet().iterator();

         while(var5.hasNext()) {
            String key = (String)var5.next();
            out.println(INDENT[i] + key + ":" + (String)props.get(key));
         }
      }

      StringBuilder var10001 = new StringBuilder();
      --i;
      out.println(var10001.append(INDENT[i]).append("}").toString());
   }

   private void writeRegEntry(EntryInfo info, PrintWriter out, int i) {
      out.println(INDENT[i++] + "reg-entry" + " {");
      if (info.getClassName() != null) {
         this.writeConEntry(info, out, i);
      }

      Iterator var4 = info.getRegContexts().iterator();

      StringBuilder var10001;
      while(var4.hasNext()) {
         AuthConfigFactory.RegistrationContext ctx = (AuthConfigFactory.RegistrationContext)var4.next();
         out.println(INDENT[i++] + "reg-ctx" + " {");
         if (ctx.getMessageLayer() != null) {
            out.println(INDENT[i] + "layer" + ":" + ctx.getMessageLayer());
         }

         if (ctx.getAppContext() != null) {
            out.println(INDENT[i] + "app-ctx" + ":" + ctx.getAppContext());
         }

         if (ctx.getDescription() != null) {
            out.println(INDENT[i] + "description" + ":" + ctx.getDescription());
         }

         var10001 = new StringBuilder();
         --i;
         out.println(var10001.append(INDENT[i]).append("}").toString());
      }

      var10001 = new StringBuilder();
      --i;
      out.println(var10001.append(INDENT[i]).append("}").toString());
   }

   private void clearExistingFile() throws IOException {
      if (this.confFile.exists()) {
         this.confFile.delete();
      }

      this.confFile.createNewFile();
   }

   private void loadEntries() throws IOException {
      this.entries = new ArrayList();
      BufferedReader reader = new BufferedReader(new FileReader(this.confFile));

      for(String line = reader.readLine(); line != null; line = reader.readLine()) {
         String trimLine = line.trim();
         if (trimLine.startsWith("con-entry")) {
            this.entries.add(this.readConEntry(reader));
         } else if (trimLine.startsWith("reg-entry")) {
            this.entries.add(this.readRegEntry(reader));
         }
      }

   }

   private EntryInfo readConEntry(BufferedReader reader) throws IOException {
      String className = reader.readLine().trim();
      Map properties = this.readProperties(reader);
      return new EntryInfo(className, properties);
   }

   private Map readProperties(BufferedReader reader) throws IOException {
      String line = reader.readLine().trim();
      if (line.equals("}")) {
         return null;
      } else {
         HashMap properties;
         for(properties = new HashMap(); !line.equals("}"); line = reader.readLine().trim()) {
            properties.put(line.substring(0, line.indexOf(":")), line.substring(line.indexOf(":") + 1, line.length()));
         }

         return properties;
      }
   }

   private EntryInfo readRegEntry(BufferedReader reader) throws IOException {
      String className = null;
      Map properties = null;
      List ctxs = new ArrayList();

      for(String line = reader.readLine().trim(); !line.equals("}"); line = reader.readLine().trim()) {
         if (line.startsWith("con-entry")) {
            EntryInfo conEntry = this.readConEntry(reader);
            className = conEntry.getClassName();
            properties = conEntry.getProperties();
         } else if (line.startsWith("reg-ctx")) {
            ctxs.add(this.readRegContext(reader));
         }
      }

      return new EntryInfo(className, properties, ctxs);
   }

   private AuthConfigFactory.RegistrationContext readRegContext(BufferedReader reader) throws IOException {
      String layer = null;
      String appCtx = null;
      String description = null;

      for(String line = reader.readLine().trim(); !line.equals("}"); line = reader.readLine().trim()) {
         String value = line.substring(line.indexOf(":") + 1, line.length());
         if (line.startsWith("layer")) {
            layer = value;
         } else if (line.startsWith("app-ctx")) {
            appCtx = value;
         } else if (line.startsWith("description")) {
            description = value;
         }
      }

      return new RegistrationContextImpl(layer, appCtx, description, true);
   }
}
