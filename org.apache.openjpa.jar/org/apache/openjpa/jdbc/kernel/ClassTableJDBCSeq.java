package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;

public class ClassTableJDBCSeq extends TableJDBCSeq {
   private static final Localizer _loc = Localizer.forPackage(ClassTableJDBCSeq.class);
   private final Map _stats = new HashMap();
   private boolean _ignore = false;
   private boolean _aliases = false;

   public ClassTableJDBCSeq() {
      this.setTable("OPENJPA_SEQUENCES_TABLE");
   }

   public boolean getIgnoreUnmapped() {
      return this._ignore;
   }

   public void setIgnoreUnmapped(boolean ignore) {
      this._ignore = ignore;
   }

   /** @deprecated */
   public void setIgnoreVirtual(boolean ignore) {
      this.setIgnoreUnmapped(ignore);
   }

   public boolean getUseAliases() {
      return this._aliases;
   }

   public void setUseAliases(boolean aliases) {
      this._aliases = aliases;
   }

   protected synchronized TableJDBCSeq.Status getStatus(ClassMapping mapping) {
      if (mapping == null) {
         return null;
      } else {
         String key = this.getKey(mapping, false);
         TableJDBCSeq.Status stat = (TableJDBCSeq.Status)this._stats.get(key);
         if (stat == null) {
            stat = new TableJDBCSeq.Status();
            this._stats.put(key, stat);
         }

         return stat;
      }
   }

   protected Column addPrimaryKeyColumn(Table table) {
      DBDictionary dict = this.getConfiguration().getDBDictionaryInstance();
      Column pkColumn = table.addColumn(dict.getValidColumnName(this.getPrimaryKeyColumn(), table));
      pkColumn.setType(dict.getPreferredType(12));
      pkColumn.setJavaType(9);
      pkColumn.setSize(dict.characterColumnSize);
      return pkColumn;
   }

   protected Object getPrimaryKey(ClassMapping mapping) {
      return mapping == null ? null : this.getKey(mapping, true);
   }

   private String getKey(ClassMapping mapping, boolean db) {
      if (this._ignore) {
         while(mapping.getMappedPCSuperclassMapping() != null) {
            mapping = mapping.getMappedPCSuperclassMapping();
         }
      } else {
         while(mapping.getPCSuperclass() != null) {
            mapping = mapping.getPCSuperclassMapping();
         }
      }

      return this._aliases ? mapping.getTypeAlias() : mapping.getDescribedType().getName();
   }

   public static void main(String[] args) throws Exception {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = ClassTableJDBCSeq.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("clstable-seq-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws Exception {
      String action = opts.removeProperty("action", "a", (String)null);
      Configurations.populateConfiguration(conf, opts);
      return run(conf, args, action, (MappingRepository)null, (ClassLoader)null);
   }

   public static boolean run(JDBCConfiguration conf, String[] args, String action, MappingRepository repos, ClassLoader loader) throws Exception {
      ClassTableJDBCSeq seq = new ClassTableJDBCSeq();
      String props = Configurations.getProperties(conf.getSequence());
      Configurations.configureInstance(seq, conf, (String)props);
      if ("drop".equals(action)) {
         if (args.length != 0) {
            return false;
         }

         seq.dropTable();
      } else if ("add".equals(action)) {
         if (args.length != 0) {
            return false;
         }

         seq.refreshTable();
      } else {
         if (!"get".equals(action) && !"set".equals(action)) {
            return false;
         }

         if (args.length == 0) {
            return false;
         }

         if (loader == null) {
            loader = conf.getClassResolverInstance().getClassLoader(ClassTableJDBCSeq.class, (ClassLoader)null);
         }

         ClassArgParser cap = conf.getMetaDataRepositoryInstance().getMetaDataFactory().newClassArgParser();
         cap.setClassLoader(loader);
         Class cls = cap.parseTypes(args[0])[0];
         if (repos == null) {
            repos = conf.getMappingRepositoryInstance();
         }

         ClassMapping mapping = repos.getMapping((Class)cls, (ClassLoader)null, true);
         Connection conn = conf.getDataSource2((StoreContext)null).getConnection();

         try {
            long cur = seq.getSequence(mapping, conn);
            if ("get".equals(action)) {
               System.out.println(mapping + ": " + cur);
            } else {
               long set;
               if (args.length > 1) {
                  set = Long.parseLong(args[1]);
               } else {
                  set = cur + (long)seq.getAllocate();
               }

               if (set < cur) {
                  set = cur;
               } else {
                  TableJDBCSeq.Status stat = seq.getStatus(mapping);
                  seq.setSequence((ClassMapping)null, stat, (int)(set - cur), true, conn);
                  set = stat.seq;
               }

               System.err.println(mapping + ": " + set);
            }
         } catch (NumberFormatException var24) {
            boolean var12 = false;
            return var12;
         } finally {
            try {
               conn.close();
            } catch (SQLException var23) {
            }

         }
      }

      return true;
   }
}
