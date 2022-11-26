package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.ChangeList.Change.Type;
import weblogic.nodemanager.plugin.ConfigurationPlugin;

class ConfigurationManager {
   private final File stagingDir;
   private final ConfigurationPlugin plugin;
   private final HashMap transactions = new HashMap();

   ConfigurationManager(ConfigurationPlugin plugin, String systemComponentType, File domainDir) {
      if (plugin == null) {
         throw new IllegalArgumentException();
      } else {
         this.plugin = plugin;
         if (systemComponentType == null) {
            throw new IllegalArgumentException();
         } else if (domainDir == null) {
            throw new IllegalArgumentException();
         } else {
            this.stagingDir = this.createStagingDir(systemComponentType, domainDir);
         }
      }
   }

   private File createStagingDir(String systemComponentType, File domainDir) {
      File stagingDir = new File(new File(domainDir, "tmp"), "cam");
      return new File(stagingDir, systemComponentType);
   }

   public String[] syncChangeList(String trans, ChangeList adminCl, List componentNames) throws IOException {
      File dir = this.getStagingDir(trans);
      ChangeList cl = this.plugin.syncChangeList(dir, adminCl);
      synchronized(this.transactions) {
         this.transactions.put(trans, new Transaction(trans, cl, componentNames));
      }

      ArrayList files = new ArrayList();
      Iterator var7 = cl.getChanges().values().iterator();

      while(true) {
         ChangeList.Change change;
         do {
            if (!var7.hasNext()) {
               return files != null && !files.isEmpty() ? (String[])files.toArray(new String[files.size()]) : null;
            }

            change = (ChangeList.Change)var7.next();
         } while(change.getType() != Type.ADD && change.getType() != Type.EDIT);

         String file = change.getRelativePath();
         files.add(file);
      }
   }

   public String[] changeList(String trans, ChangeList adminCl, List componentNames) throws IOException {
      File dir = this.getStagingDir(trans);
      ChangeList cl = this.plugin.diffConfig(dir, adminCl);
      synchronized(this.transactions) {
         this.transactions.put(trans, new Transaction(trans, cl, componentNames));
      }

      ArrayList files = new ArrayList();
      Iterator var7 = cl.getChanges().values().iterator();

      while(true) {
         ChangeList.Change change;
         do {
            if (!var7.hasNext()) {
               return !files.isEmpty() ? (String[])files.toArray(new String[files.size()]) : null;
            }

            change = (ChangeList.Change)var7.next();
         } while(change.getType() != Type.ADD && change.getType() != Type.EDIT);

         String file = change.getRelativePath();
         files.add(file);
      }
   }

   public void validateChangeList(String transaction) throws ConfigurationPlugin.ValidationException {
      try {
         Transaction t;
         synchronized(this.transactions) {
            t = (Transaction)this.transactions.get(transaction);
         }

         if (t != null) {
            File dir = this.getStagingDir(transaction);
            this.plugin.validate(dir, t.getChangeList());
         }

      } catch (ConfigurationPlugin.ValidationException var6) {
         this.cleanUpTransaction(transaction);
         throw var6;
      }
   }

   public void commitChangeList(String transaction) throws IOException {
      try {
         Transaction t;
         synchronized(this.transactions) {
            t = (Transaction)this.transactions.get(transaction);
         }

         if (t != null) {
            File dir = this.getStagingDir(transaction);
            ChangeList cl = t.getChangeList();
            String[] componentNames = t.getComponentNames();
            this.plugin.commit(dir, cl, componentNames);
         }
      } finally {
         this.cleanUpTransaction(transaction);
      }

   }

   public void rollbackChangeList(String transaction) {
      this.cleanUpTransaction(transaction);
   }

   private void cleanUpTransaction(String transaction) {
      synchronized(this.transactions) {
         this.transactions.remove(transaction);
      }

      File path = this.getStagingDir(transaction);
      this.deleteDirectory(path);
   }

   private boolean deleteDirectory(File path) {
      if (path.exists()) {
         File[] files = path.listFiles();
         File[] var3 = files;
         int var4 = files.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            if (file.isDirectory()) {
               this.deleteDirectory(file);
            } else {
               file.delete();
            }
         }
      }

      return path.delete();
   }

   public File getStagingDir(String trans) {
      return new File(this.stagingDir, trans);
   }

   public NMComponentTypeChangeList getChangeList(String compName) throws IOException {
      return this.plugin.getChangeListForAllFiles(compName);
   }

   public File getFile(String compName, String relativePath) throws IOException {
      return this.plugin.getFile(compName, relativePath);
   }

   private static class Transaction {
      private final String name;
      private final ChangeList changeList;
      private final String[] componentNames;

      public Transaction(String name, ChangeList changeList, List componentNames) {
         this(name, changeList, componentNames == null ? null : (String[])componentNames.toArray(new String[componentNames.size()]));
      }

      public Transaction(String name, ChangeList changeList, String[] componentNames) {
         this.name = name;
         this.changeList = changeList;
         this.componentNames = componentNames;
      }

      public String getName() {
         return this.name;
      }

      public ChangeList getChangeList() {
         return this.changeList;
      }

      public String[] getComponentNames() {
         return this.componentNames;
      }
   }
}
