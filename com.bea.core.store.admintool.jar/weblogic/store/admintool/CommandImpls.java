package weblogic.store.admintool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.StoreWritePolicy;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.utils.FileUtils;

class CommandImpls {
   static String[] getStoreNames(StoreAdmin sa) {
      HashSet hs = new HashSet();
      PersistentStoreManager psMgr = PersistentStoreManager.getManager();
      synchronized(psMgr) {
         Iterator it = psMgr.getAllStores();

         while(it.hasNext()) {
            hs.add(((PersistentStoreXA)it.next()).getName());
         }

         return (String[])((String[])hs.toArray(new String[hs.size()]));
      }
   }

   static String[] getConnectionNames(StoreAdmin sa, String storeName) {
      PersistentStoreXA pStore = getOpenStore(storeName, sa.getInterpreter());
      if (pStore == null) {
         return null;
      } else {
         String[] ret = null;

         try {
            ret = sa.getAdminIF().getAllConnections(pStore);
         } catch (PersistentStoreException var5) {
            sa.getInterpreter().error("Failed to get connections from store " + storeName, var5);
         }

         return ret;
      }
   }

   private static PersistentStoreXA getOpenStore(String name, Interpreter ip) {
      PersistentStoreManager psMgr = PersistentStoreManager.getManager();
      PersistentStoreXA pStore = (PersistentStoreXA)psMgr.getStore(name);
      if (pStore == null && ip != null) {
         ip.error("Store " + name + " is not open");
      }

      return pStore;
   }

   private static String[] getConnList(CommandDefs.Command cmd, String storeName) {
      String conn = cmd.getParamVal(CommandDefs.CommandParam.CONNECTION);
      String[] connNames = null;
      if (conn != null) {
         connNames = new String[]{conn};
      } else {
         connNames = getConnectionNames(cmd.sa, storeName);
      }

      return connNames;
   }

   static class FileStoreAdminUtil {
      static String FILE_SUFFIX = "." + "dat".toUpperCase();
      static String FILENAME_PATTERN;
      static int SUFFIX_PATTERN_LEN;
      static final String TEMP_PREFIX;

      static Set validateStoreDir(String dirName, boolean allowCreate, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();
         File dir = new File(dirName);
         if (!dir.exists()) {
            if (!allowCreate) {
               ip.error(dirName + " does not exist");
            }

            return new HashSet();
         } else if (!dir.isDirectory()) {
            ip.error(dirName + " is not a directory");
            return null;
         } else {
            Set storeNameSet = getPotentialStoreNames(dirName);
            if (storeNameSet == null) {
               ip.error("Could not read directory " + dirName);
            } else if (storeNameSet.size() == 0 && !allowCreate) {
               ip.error("Directory " + dirName + " does not contain any store data files");
            }

            return storeNameSet;
         }
      }

      static String moveStoreDir(String fromDirName, String toDirName, boolean createUniqDir, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();
         validateStoreDir(fromDirName, false, sa);
         if (ip.checkForError()) {
            return null;
         } else {
            File fromDir = new File(fromDirName);
            File toDir = new File(toDirName);

            try {
               if (!toDir.exists() || !toDir.isDirectory()) {
                  ip.error(toDir + " does not exist/is not a directory");
                  return null;
               }

               if (createUniqDir) {
                  toDir = FileUtils.createTempDir(TEMP_PREFIX, toDir);
               }

               FileUtils.copy(fromDir, toDir);
               ip.info("Original " + fromDir + " moved to " + toDir);
               FileUtils.remove(fromDir);
               if (!fromDir.mkdir()) {
                  ip.error("Failed while re-creating " + fromDir);
                  return null;
               }
            } catch (IOException var8) {
               ip.error("Failed while moving " + fromDir + " to " + toDir, var8);
               return null;
            }

            return toDir.toString();
         }
      }

      static File createTempDir(String tempDirName, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();

         try {
            File toDir = new File(tempDirName);
            return FileUtils.createTempDir(TEMP_PREFIX, toDir);
         } catch (IOException var4) {
            ip.error("Failed creating temporary directory " + tempDirName, var4);
            return null;
         }
      }

      static void moveStore(String fromDirName, File toDir, StoreAdmin sa, String storeName) {
         Interpreter ip = sa.getInterpreter();
         validateStoreDir(fromDirName, false, sa);
         if (!ip.checkForError()) {
            String sourceName = storeName.toUpperCase();
            File fromDir = new File(fromDirName);

            try {
               if (toDir.exists() && toDir.isDirectory()) {
                  File[] fllist = fromDir.listFiles(new FilenameFilter() {
                     public boolean accept(File _dir, String _file) {
                        return Pattern.matches(CommandImpls.FileStoreAdminUtil.FILENAME_PATTERN, _file);
                     }
                  });
                  if (fllist == null) {
                     ip.error("Could not read files in " + fromDirName);
                  } else if (fllist.length == 0) {
                     ip.warn("No files found for store " + sourceName + " in " + fromDirName);
                  } else {
                     for(int i = 0; i < fllist.length; ++i) {
                        File f = fllist[i];
                        if (f.isFile()) {
                           String fileName = f.getName();
                           int prefixLen = fileName.length() - SUFFIX_PATTERN_LEN;
                           if (prefixLen > 0) {
                              String prefix = fileName.substring(0, prefixLen);
                              if (prefix.toUpperCase().equals(sourceName)) {
                                 FileUtils.copy(f, toDir);
                                 FileUtils.remove(f);
                                 ip.info("Original " + fromDir + " moved to " + toDir);
                              }
                           }
                        }
                     }

                  }
               } else {
                  ip.error(toDir + " does not exist/is not a directory");
               }
            } catch (IOException var14) {
               ip.error("Failed while moving " + fromDir + " to " + toDir, var14);
            }
         }
      }

      static void copyStore(String storeName, String sourceDir, String targetDir, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();
         boolean prevAdvMode = sa.getAdvancedMode();

         try {
            sa.setAdvancedMode(true);
            if (!sourceDir.equalsIgnoreCase(targetDir)) {
               ip.showInfo(false);
               ip.showWarn(false);
               ip.showMessage(false);
               String sourceName = storeName.toUpperCase();
               String targetName = (TEMP_PREFIX + sourceName).toUpperCase();
               String command = null;
               command = CommandDefs.CommandType.OPENFILE.toString() + CommandDefs.CommandParam.STORE.asString(sourceName) + CommandDefs.CommandParam.DIR.asString(sourceDir.toString());
               if (!sa.execute(command)) {
                  return;
               }

               command = CommandDefs.CommandType.OPENFILE.toString() + CommandDefs.CommandParam.STORE.asString(targetName) + CommandDefs.CommandParam.DIR.asString(targetDir.toString()) + CommandDefs.CommandParam.CREATE.asString((String)null);
               if (!sa.execute(command)) {
                  return;
               }

               command = CommandDefs.CommandType.COPY.toString() + CommandDefs.CommandParam.FROM.asString(sourceName) + CommandDefs.CommandParam.TO.asString(targetName);
               if (!sa.execute(command)) {
                  return;
               }

               command = CommandDefs.CommandType.CLOSE.toString() + CommandDefs.CommandParam.STORE.asString(sourceName);
               if (!sa.execute(command)) {
                  return;
               }

               command = CommandDefs.CommandType.CLOSE.toString() + CommandDefs.CommandParam.STORE.asString(targetName);
               if (!sa.execute(command)) {
                  return;
               }

               renameStore(targetDir, targetName, sourceName, sa);
               return;
            }

            ip.warn("Source and target of copy the same");
         } finally {
            ip.showInfo(true);
            ip.showWarn(true);
            ip.showMessage(true);
            sa.setAdvancedMode(prevAdvMode);
         }

      }

      static void renameStore(String dirName, final String fromName, String toName, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();
         File dir = new File(dirName);
         fromName = fromName.toUpperCase();
         toName = toName.toUpperCase();
         File[] fllist = dir.listFiles(new FilenameFilter() {
            public boolean accept(File _dir, String _file) {
               return _file.startsWith(fromName);
            }
         });
         if (fllist == null) {
            ip.error("Could not read files in " + dirName);
         } else if (fllist.length == 0) {
            ip.warn("No files to be renamed found for store " + fromName + " in " + dirName);
         } else {
            for(int i = 0; i < fllist.length; ++i) {
               File f = fllist[i];
               String newName = f.getName().replaceFirst(fromName, toName);
               File newf = new File(f.getParent(), newName);
               if (!f.renameTo(newf)) {
                  ip.error("Failed renaming " + f + " to " + newf);
                  return;
               }
            }

            ip.info("Renamed " + fromName + " to " + toName);
         }
      }

      private static Set getPotentialStoreNames(String dirName) {
         File dir = new File(dirName);
         String[] fllist = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return Pattern.matches(CommandImpls.FileStoreAdminUtil.FILENAME_PATTERN, name);
            }
         });
         if (fllist == null) {
            return null;
         } else {
            HashSet storeNameSet = new HashSet();

            for(int i = 0; i < fllist.length; ++i) {
               String fileName = fllist[i];
               int prefixLen = fileName.length() - SUFFIX_PATTERN_LEN;
               if (prefixLen > 0) {
                  String prefix = fileName.substring(0, prefixLen);
                  storeNameSet.add(prefix.toUpperCase());
               }
            }

            return storeNameSet;
         }
      }

      static {
         FILENAME_PATTERN = ".*[0-9]{6}" + FILE_SUFFIX;
         SUFFIX_PATTERN_LEN = 6 + FILE_SUFFIX.length();
         TEMP_PREFIX = "storeadmin_".toUpperCase();
      }
   }

   static class CommandCompact extends CommandDefs.Command {
      private static final String TEMPDIR_PROPERTY = "java.io.tmpdir";

      public void run() {
         String dirName = this.getParamVal(CommandDefs.CommandParam.DIR);
         Set storeNameSet = CommandImpls.FileStoreAdminUtil.validateStoreDir(dirName, false, this.sa);
         if (!this.ip.checkForError()) {
            this.validateNoOpenStores(storeNameSet);
            if (!this.ip.checkForError()) {
               String tempDirName = this.getTempDir();
               if (tempDirName != null) {
                  File tmpDir = CommandImpls.FileStoreAdminUtil.createTempDir(tempDirName, this.sa);
                  if (!this.ip.checkForError()) {
                     Iterator it = storeNameSet.iterator();

                     while(it.hasNext()) {
                        String source = (String)it.next();
                        this.ip.info("Moving store ... " + source);
                        CommandImpls.FileStoreAdminUtil.moveStore(dirName, tmpDir, this.sa, source);
                        if (this.ip.checkForError()) {
                           return;
                        }

                        this.ip.info("... Moved store " + source);
                     }

                     it = storeNameSet.iterator();
                     int successCount = 0;
                     int failCount = 0;

                     while(it.hasNext()) {
                        String source = (String)it.next();
                        this.ip.info("Compacting store ... " + source);
                        CommandImpls.FileStoreAdminUtil.copyStore(source, tmpDir.toString(), dirName, this.sa);
                        if (this.ip.checkForError()) {
                           ++failCount;
                           this.ip.info("... Failed to compact store " + source);
                        } else {
                           ++successCount;
                           this.ip.info("... Compacted store " + source);
                        }
                     }

                     if (failCount == 0) {
                        this.ip.info("Compact of " + dirName + " successful : pre-compact files in " + tempDirName);
                     } else {
                        this.ip.info("Compact of " + dirName + ", succeed for " + successCount + " stores and failed for " + failCount + " stores");
                     }

                  }
               }
            }
         }
      }

      private void validateNoOpenStores(Set storeNameSet) {
         String[] storeNames = CommandImpls.getStoreNames(this.sa);
         Set openStores = new HashSet();

         for(int i = 0; i < storeNames.length; ++i) {
            openStores.add(storeNames[i].toUpperCase());
         }

         Iterator it = storeNameSet.iterator();

         String s;
         do {
            if (!it.hasNext()) {
               return;
            }

            s = (String)it.next();
         } while(!openStores.contains(s));

         this.ip.error("Store " + s + " is open, compact cannot proceed");
      }

      private String getTempDir() {
         String tempDirName = this.getParamVal(CommandDefs.CommandParam.TEMPDIR);
         if (tempDirName == null) {
            tempDirName = System.getProperty("java.io.tmpdir");
            if (tempDirName == null) {
               this.ip.error("Could not determine default " + CommandDefs.CommandParam.TEMPDIR + " from property " + "java.io.tmpdir" + ", specify " + CommandDefs.CommandParam.TEMPDIR + " explicitly");
               return null;
            }
         }

         return tempDirName;
      }
   }

   static class CommandOpts extends CommandDefs.Command {
      public void run() {
         this.ip.message("  Use any of the following options while invoking this tool:");
         this.ip.message("    -infile filename #to read input commands from file filename");
         this.ip.message("    -outfile filename #to send output messages to file filename");
         this.ip.message("    -verbose #to start this tool with verbose mode on");
         this.ip.message("  Invalid/extraneous options will be ignored");
      }
   }

   static class CommandQuit extends CommandDefs.Command {
      public void run() {
         String[] snames = CommandImpls.getStoreNames(this.sa);

         for(int i = 0; i < snames.length; ++i) {
            PersistentStoreXA ps = CommandImpls.getOpenStore(snames[i], this.ip);
            if (ps != null) {
               try {
                  ps.close();
               } catch (Exception var7) {
                  this.ip.warn("Closing store : " + ps.getName(), var7);
               }
            }
         }

         if (this.ip.isPromptChanged()) {
            try {
               if (this.sa.getAdminIF().isAttached()) {
                  try {
                     this.sa.getAdminIF().rsDetach();
                  } catch (IOException var5) {
                     this.ip.error("Failed to detach from RS daemon cluster", var5);
                  }
               }
            } catch (IOException var6) {
               this.ip.error("Failed to determine isAttached to RS Daemon Cluster", var6);
            }
         }

         this.ip.quit("done");
      }
   }

   static class CommandAdvanced extends CommandDefs.Command {
      public void run() {
         if (this.args.length == 2 && this.getParamVal(CommandDefs.CommandParam.ON) != null) {
            this.sa.setAdvancedMode(true);
         } else {
            this.sa.setAdvancedMode(false);
         }

      }
   }

   static class CommandVerbose extends CommandDefs.Command {
      public void run() {
         if (this.args.length > 2) {
            this.ip.error("Invalid number of paramters");
         } else {
            if (this.args.length == 2) {
               if (this.getParamVal(CommandDefs.CommandParam.ON) != null) {
                  this.ip.setVerbose(true);
               } else if (this.getParamVal(CommandDefs.CommandParam.OFF) != null) {
                  this.ip.setVerbose(false);
               }
            }

            CommandDefs.CommandParam cp = this.ip.getVerbose() ? CommandDefs.CommandParam.ON : CommandDefs.CommandParam.OFF;
            this.ip.info(this.cmdType + " " + cp);
         }
      }
   }

   static class CommandHelp extends CommandDefs.Command {
      public void run() {
         boolean doPreamble = true;
         if (this.args.length >= 2) {
            CommandDefs.CommandType ct = CommandDefs.getCommandType(this.args[1]);
            if (ct != null && (this.sa.getAdvancedMode() || !ct.isAdvanced())) {
               ct.manPage(this.ip, this.sa.getAdvancedMode());
               return;
            }

            this.ip.error("Unknown command : " + this.args[1]);
            doPreamble = false;
         }

         int i;
         if (doPreamble) {
            this.ip.message("");
            this.cmdType.manPage(this.ip, this.sa.getAdvancedMode());
            this.ip.message("");
            String[] preamble = CommandDefs.HELP_PREAMBLE;

            for(i = 0; i < preamble.length; ++i) {
               this.ip.message("  " + preamble[i]);
            }
         }

         this.ip.message("");
         this.ip.message("  Available Commands:");
         CommandDefs.CommandType[] cta = CommandDefs.getAllCommandTypes();

         for(i = 0; i < cta.length; ++i) {
            cta[i].describe(this.ip, this.sa.getAdvancedMode());
         }

         this.ip.message("");
      }
   }

   static class CommandCopy extends CommandDefs.Command {
      public void run() {
         String fromName = this.getParamVal(CommandDefs.CommandParam.FROM).toUpperCase();
         String toName = this.getParamVal(CommandDefs.CommandParam.TO).toUpperCase();
         if (fromName.equalsIgnoreCase(toName)) {
            this.ip.error("Source and target of copy cannot be the same store");
         } else {
            PersistentStoreXA pStoreFrom = CommandImpls.getOpenStore(fromName, this.ip);
            if (pStoreFrom != null) {
               PersistentStoreXA pStoreTo = CommandImpls.getOpenStore(toName, this.ip);
               if (pStoreTo != null) {
                  boolean allowOverWrite = this.getParamVal(CommandDefs.CommandParam.OVERWRITE) != null;
                  String[] connNames = CommandImpls.getConnList(this, fromName);
                  if (connNames != null) {
                     if (allowOverWrite || this.checkForCollisions(toName, connNames)) {
                        try {
                           this.sa.getAdminIF().copyConnections(pStoreFrom, pStoreTo, allowOverWrite, connNames);
                        } catch (PersistentStoreException var8) {
                           this.ip.error("Failed while copying contents from store " + fromName + " to " + toName, var8);
                           return;
                        }

                        this.ip.info("Copy from " + fromName + " to " + toName + " successful");
                     }
                  }
               }
            }
         }
      }

      private boolean checkForCollisions(String toStoreName, String[] conns) {
         HashSet hs = new HashSet();
         String[] targetConns = CommandImpls.getConnectionNames(this.sa, toStoreName);
         if (targetConns == null) {
            return false;
         } else {
            int i;
            for(i = 0; i < targetConns.length; ++i) {
               hs.add(targetConns[i]);
            }

            for(i = 0; i < conns.length; ++i) {
               if (hs.contains(conns[i])) {
                  this.ip.error("Store " + toStoreName + " already contains connection " + conns[i] + ". Use " + CommandDefs.CommandParam.OVERWRITE + " if necessary");
                  return false;
               }
            }

            return true;
         }
      }
   }

   static class CommandDump extends CommandDefs.Command {
      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         PersistentStoreXA pStore = CommandImpls.getOpenStore(storeName, this.ip);
         if (pStore != null) {
            String outfile = this.getParamVal(CommandDefs.CommandParam.OUTFILE);
            if (!outfile.endsWith(".xml")) {
               outfile = outfile + ".xml";
            }

            String[] connNames = CommandImpls.getConnList(this, storeName);
            if (connNames != null) {
               boolean dumpRecordContents = this.getParamVal(CommandDefs.CommandParam.DEEP) != null;

               try {
                  this.sa.getAdminIF().dumpConnections(pStore, outfile, connNames, dumpRecordContents);
               } catch (PersistentStoreException var7) {
                  this.ip.error("Failed while dumping contents from store " + storeName, var7);
                  return;
               } catch (IOException var8) {
                  this.ip.error("Failed while dumping contents to " + outfile, var8);
                  return;
               }

               this.ip.info("Dump from " + storeName + " to " + outfile + " successful");
            }
         }
      }
   }

   static class CommandDelete extends CommandDefs.Command {
      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         PersistentStoreXA pStore = CommandImpls.getOpenStore(storeName, this.ip);
         if (pStore != null) {
            String conn = this.getParamVal(CommandDefs.CommandParam.CONNECTION);
            String allConn = this.getParamVal(CommandDefs.CommandParam.ALL);
            if (!(allConn == null ^ conn == null)) {
               this.ip.error("Exactly one of {" + CommandDefs.CommandParam.CONNECTION + "|" + CommandDefs.CommandParam.ALL + "} must be specified");
            } else {
               String[] connNames = CommandImpls.getConnList(this, storeName);
               if (connNames != null) {
                  try {
                     this.sa.getAdminIF().deleteConnections(pStore, connNames);
                  } catch (PersistentStoreException var7) {
                     this.ip.error("Failed while deleting contents from " + storeName, var7);
                     return;
                  }

                  this.ip.info("Delete" + (conn != null ? " of connection " + conn : " of all connections") + " from " + storeName + " successful");
               }
            }
         }
      }
   }

   static class CommandList extends CommandDefs.Command {
      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         String dirName = this.getParamVal(CommandDefs.CommandParam.DIR);
         if (dirName != null && storeName != null) {
            this.ip.error("At most one of [" + CommandDefs.CommandParam.STORE + "|" + CommandDefs.CommandParam.DIR + "] can be specified");
         } else if (storeName == null && dirName == null) {
            String[] snames = CommandImpls.getStoreNames(this.sa);
            this.ip.info(snames.length == 0 ? "No open stores" : "Currently open stores : ");

            for(int i = 0; i < snames.length; ++i) {
               this.ip.message("  " + snames[i]);
            }

         } else if (storeName == null && dirName != null) {
            Set storeNameSet = CommandImpls.FileStoreAdminUtil.validateStoreDir(dirName, true, this.sa);
            if (!this.ip.checkForError()) {
               this.ip.info(storeNameSet.size() == 0 ? "No stores in directory " + dirName : "Stores in directory " + dirName + " :");
               Iterator it = storeNameSet.iterator();

               while(it.hasNext()) {
                  this.ip.message("  " + (String)it.next());
               }

            }
         } else {
            PersistentStoreXA pStore = CommandImpls.getOpenStore(storeName, this.ip);
            if (pStore != null) {
               String[] conns = CommandImpls.getConnectionNames(this.sa, storeName);
               if (conns != null) {
                  this.ip.info(conns.length == 0 ? "No connections in store " + storeName : "Connections in store " + storeName + " : ");

                  for(int i = 0; i < conns.length; ++i) {
                     this.ip.message("  " + conns[i]);
                  }

               }
            }
         }
      }
   }

   static class CommandClose extends CommandDefs.Command {
      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         PersistentStoreXA pStore = CommandImpls.getOpenStore(storeName, this.ip);
         if (pStore != null) {
            try {
               this.sa.getAdminIF().closeStore(pStore);
            } catch (PersistentStoreException var4) {
               this.ip.error("Failed while closing file store " + storeName, var4);
               return;
            }

            this.ip.info("Store " + storeName + " closed successfully");
         }
      }
   }

   static class CommandOpenJDBCStore extends CommandDefs.Command {
      private Properties jdbcProps;

      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         if (CommandImpls.getOpenStore(storeName, (Interpreter)null) != null) {
            this.ip.error("Store " + storeName + " is already open");
         } else {
            String propFile = this.getParamVal(CommandDefs.CommandParam.PROPFILE);
            if ((this.jdbcProps = this.loadProperties(propFile)) != null) {
               String driverClass = this.getValue(CommandDefs.CommandParam.DRIVER);
               String dbUrl = this.getValue(CommandDefs.CommandParam.URL);
               String user = this.getValue(CommandDefs.CommandParam.USER);
               String password = this.getValue(CommandDefs.CommandParam.PASSWORD);
               String prefix = this.getValue(CommandDefs.CommandParam.PREFIX);
               String ddl = this.getValue(CommandDefs.CommandParam.DDL);
               if (driverClass == null) {
                  this.ip.error("Value for " + CommandDefs.CommandParam.DRIVER + " is not specified in command-line or properties file");
               } else if (dbUrl == null) {
                  this.ip.error("Value for " + CommandDefs.CommandParam.URL + " is not specified in command-line or properties file");
               } else {
                  try {
                     this.sa.getAdminIF().openJDBCStore(storeName, prefix, ddl, dbUrl, user, password, driverClass, this.jdbcProps);
                  } catch (PersistentStoreException var10) {
                     this.ip.error("Failed to open JDBC store " + storeName, var10);
                     return;
                  }

                  this.ip.info("Store " + storeName + " opened successfully");
               }
            }
         }
      }

      private Properties loadProperties(String propFile) {
         Properties props = new Properties();
         if (propFile == null) {
            return props;
         } else {
            FileInputStream fis = null;

            try {
               fis = new FileInputStream(propFile);
               props.load(fis);
            } catch (IOException var13) {
               this.ip.error("Error loading properties from " + propFile, var13);
               props = null;
            } finally {
               if (fis != null) {
                  try {
                     fis.close();
                  } catch (IOException var12) {
                  }
               }

            }

            return props;
         }
      }

      private String getValue(CommandDefs.CommandParam cp) {
         String ret = this.getParamVal(cp);
         if (ret != null) {
            this.jdbcProps.setProperty(cp.toString(), ret);
         } else if (this.jdbcProps != null) {
            ret = this.jdbcProps.getProperty(cp.toString());
         }

         return ret;
      }
   }

   static class CommandOpenFileStore extends CommandDefs.Command {
      static final String DEFAULT_FILESTORE_DIR = ".";

      public void run() {
         String storeName = this.getParamVal(CommandDefs.CommandParam.STORE);
         if (storeName != null) {
            storeName = storeName.toUpperCase();
         }

         if (CommandImpls.getOpenStore(storeName, (Interpreter)null) != null) {
            this.ip.error("Store " + storeName + " is already open");
         } else {
            String dirName = this.getParamVal(CommandDefs.CommandParam.DIR);
            if (dirName == null) {
               dirName = ".";
            }

            boolean autoCreate = this.getParamVal(CommandDefs.CommandParam.CREATE) != null;
            StoreWritePolicy storePolicy = StoreWritePolicy.CACHE_FLUSH;
            if (this.validateOpenFileStore(storeName, dirName, autoCreate)) {
               try {
                  this.sa.getAdminIF().openFileStore(storeName, dirName, storePolicy, autoCreate);
               } catch (PersistentStoreException var6) {
                  this.ip.error("Failed to open file store " + storeName, var6);
                  return;
               }

               this.ip.info("Store " + storeName + (autoCreate ? " created and" : "") + " opened successfully");
            }
         }
      }

      private boolean validateOpenFileStore(String storeName, String dirName, boolean autoCreate) {
         Set storeNameSet = CommandImpls.FileStoreAdminUtil.validateStoreDir(dirName, autoCreate, this.sa);
         if (this.ip.checkForError()) {
            if (storeNameSet != null && !autoCreate) {
               this.ip.info("Specify " + CommandDefs.CommandParam.CREATE + " if necessary");
            }

            return false;
         } else {
            storeName = storeName.toUpperCase();
            if (!storeNameSet.contains(storeName) && !autoCreate) {
               this.ip.error("Data files for store " + storeName + " not found in directory " + dirName);
               this.ip.info("Specify " + CommandDefs.CommandParam.CREATE + " if necessary");
               Iterator it = storeNameSet.iterator();

               while(it.hasNext()) {
                  String s = (String)it.next();
                  if (!s.equals(storeName) && PersistentStoreManager.getManager().getStore(s) == null) {
                     this.ip.warn("Data files found for unspecified store name : " + s);
                  }
               }

               return false;
            } else {
               return true;
            }
         }
      }
   }
}
