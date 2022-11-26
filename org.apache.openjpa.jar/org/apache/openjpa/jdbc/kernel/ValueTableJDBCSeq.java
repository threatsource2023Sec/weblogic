package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;

public class ValueTableJDBCSeq extends TableJDBCSeq {
   public static final String PK_VALUE = "PrimaryKeyValue";
   private static final Localizer _loc = Localizer.forPackage(ValueTableJDBCSeq.class);
   private String _value = "DEFAULT";

   public ValueTableJDBCSeq() {
      this.setTable("OPENJPA_SEQUENCES_TABLE");
   }

   public String getPrimaryKeyValue() {
      return this._value;
   }

   public void setPrimaryKeyValue(String value) {
      this._value = value;
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
      return this._value;
   }

   public static void main(String[] args) throws Exception {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = ValueTableJDBCSeq.run(conf, arguments, opts);
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
      ValueTableJDBCSeq seq = new ValueTableJDBCSeq();
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

         if (args.length > 0) {
            seq.setPrimaryKeyValue(args[0]);
         }

         Connection conn = conf.getDataSource2((StoreContext)null).getConnection();

         boolean var9;
         try {
            long cur = seq.getSequence((ClassMapping)null, conn);
            if ("get".equals(action)) {
               System.out.println(seq.getPrimaryKeyValue() + ": " + cur);
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
                  TableJDBCSeq.Status stat = seq.getStatus((ClassMapping)null);
                  seq.setSequence((ClassMapping)null, stat, (int)(set - cur), true, conn);
                  set = stat.seq;
               }

               System.err.println(seq.getPrimaryKeyValue() + ": " + set);
            }

            return true;
         } catch (NumberFormatException var21) {
            var9 = false;
         } finally {
            try {
               conn.close();
            } catch (SQLException var20) {
            }

         }

         return var9;
      }

      return true;
   }
}
