package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

public class NativeJDBCSeq extends AbstractJDBCSeq implements Configurable {
   public static final String ACTION_DROP = "drop";
   public static final String ACTION_ADD = "add";
   public static final String ACTION_GET = "get";
   private static Localizer _loc = Localizer.forPackage(NativeJDBCSeq.class);
   private JDBCConfiguration _conf = null;
   private String _seqName = "OPENJPA_SEQUENCE";
   private int _increment = 1;
   private int _initial = 1;
   private int _allocate = 0;
   private Sequence _seq = null;
   private String _select = null;
   private String _format = null;
   private String _tableName = "DUAL";
   private boolean _subTable = false;

   public String getSequence() {
      return this._seqName;
   }

   public void setSequence(String seqName) {
      this._seqName = seqName;
   }

   /** @deprecated */
   public void setSequenceName(String seqName) {
      this.setSequence(seqName);
   }

   public int getInitialValue() {
      return this._initial;
   }

   public void setInitialValue(int initial) {
      this._initial = initial;
   }

   public int getAllocate() {
      return this._allocate;
   }

   public void setAllocate(int allocate) {
      this._allocate = allocate;
   }

   public int getIncrement() {
      return this._increment;
   }

   public void setIncrement(int increment) {
      this._increment = increment;
   }

   /** @deprecated */
   public void setTableName(String table) {
      this._tableName = table;
   }

   /** @deprecated */
   public void setFormat(String format) {
      this._format = format;
      this._subTable = true;
   }

   public void addSchema(ClassMapping mapping, SchemaGroup group) {
      if (!group.isKnownSequence(this._seqName)) {
         String schemaName = Strings.getPackageName(this._seqName);
         if (schemaName.length() == 0) {
            schemaName = Schemas.getNewTableSchema(this._conf);
         }

         Schema schema = group.getSchema(schemaName);
         if (schema == null) {
            schema = group.addSchema(schemaName);
         }

         schema.importSequence(this._seq);
      }
   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      this.buildSequence();
      DBDictionary dict = this._conf.getDBDictionaryInstance();
      if (this._format == null) {
         this._format = dict.nextSequenceQuery;
         if (this._format == null) {
            throw new MetaDataException(_loc.get("no-seq-sql", (Object)this._seqName));
         }
      }

      if (this._tableName == null) {
         this._tableName = "DUAL";
      }

      String name = dict.getFullName(this._seq);
      Object[] subs = this._subTable ? new Object[]{name, this._tableName} : new Object[]{name};
      this._select = MessageFormat.format(this._format, subs);
   }

   protected Object nextInternal(JDBCStore store, ClassMapping mapping) throws SQLException {
      Connection conn = this.getConnection(store);

      Long var4;
      try {
         var4 = Numbers.valueOf(this.getSequence(conn));
      } finally {
         this.closeConnection(conn);
      }

      return var4;
   }

   private void buildSequence() {
      String seqName = Strings.getClassName(this._seqName);
      String schemaName = Strings.getPackageName(this._seqName);
      if (schemaName.length() == 0) {
         schemaName = Schemas.getNewTableSchema(this._conf);
      }

      SchemaGroup group = new SchemaGroup();
      Schema schema = group.addSchema(schemaName);
      this._seq = schema.addSequence(seqName);
      this._seq.setInitialValue(this._initial);
      this._seq.setIncrement(this._increment);
      this._seq.setAllocate(this._allocate);
   }

   public void refreshSequence() throws SQLException {
      Log log = this._conf.getLog("openjpa.Runtime");
      if (log.isInfoEnabled()) {
         log.info(_loc.get("make-native-seq"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.createSequence(this._seq);
   }

   public void dropSequence() throws SQLException {
      Log log = this._conf.getLog("openjpa.Runtime");
      if (log.isInfoEnabled()) {
         log.info(_loc.get("drop-native-seq"));
      }

      SchemaTool tool = new SchemaTool(this._conf);
      tool.setIgnoreErrors(true);
      tool.dropSequence(this._seq);
   }

   private long getSequence(Connection conn) throws SQLException {
      PreparedStatement stmnt = null;
      ResultSet rs = null;

      long var4;
      try {
         stmnt = conn.prepareStatement(this._select);
         synchronized(this) {
            rs = stmnt.executeQuery();
         }

         if (!rs.next()) {
            throw new UserException(_loc.get("invalid-seq-sql", (Object)this._select));
         }

         var4 = rs.getLong(1);
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var16) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var15) {
            }
         }

      }

      return var4;
   }

   public static void main(String[] args) throws Exception {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            JDBCConfiguration conf = new JDBCConfigurationImpl();

            boolean var3;
            try {
               var3 = NativeJDBCSeq.run(conf, arguments, (Options)opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.out.println(_loc.get("native-seq-usage"));
      }

   }

   public static boolean run(JDBCConfiguration conf, String[] args, Options opts) throws Exception {
      String action = opts.removeProperty("action", "a", (String)null);
      Configurations.populateConfiguration(conf, opts);
      return run(conf, args, action);
   }

   public static boolean run(JDBCConfiguration conf, String[] args, String action) throws Exception {
      if (args.length != 0) {
         return false;
      } else {
         NativeJDBCSeq seq = new NativeJDBCSeq();
         String props = Configurations.getProperties(conf.getSequence());
         Configurations.configureInstance(seq, conf, (String)props);
         if ("drop".equals(action)) {
            seq.dropSequence();
         } else if ("add".equals(action)) {
            seq.refreshSequence();
         } else {
            if (!"get".equals(action)) {
               return false;
            }

            Connection conn = conf.getDataSource2((StoreContext)null).getConnection();

            try {
               long cur = seq.getSequence(conn);
               System.out.println(cur);
            } finally {
               try {
                  conn.close();
               } catch (SQLException var13) {
               }

            }
         }

         return true;
      }
   }
}
