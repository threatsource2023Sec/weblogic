package org.apache.openjpa.jdbc.ant;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

public class SchemaToolTask extends AbstractTask {
   private static final Localizer _loc = Localizer.forPackage(SchemaToolTask.class);
   protected SchemaTool.Flags flags = new SchemaTool.Flags();
   protected String file = null;

   public void setAction(Action act) {
      this.flags.action = act.getValue();
   }

   public void setIgnoreErrors(boolean ignoreErrors) {
      this.flags.ignoreErrors = ignoreErrors;
   }

   public void setOpenJPATables(boolean openjpaTables) {
      this.flags.openjpaTables = openjpaTables;
   }

   public void setDropSequences(boolean dropSequences) {
      this.flags.dropSequences = dropSequences;
   }

   public void setSequences(boolean sequences) {
      this.flags.sequences = sequences;
   }

   public void setPrimaryKeys(boolean pks) {
      this.flags.primaryKeys = pks;
   }

   public void setForeignKeys(boolean fks) {
      this.flags.foreignKeys = fks;
   }

   public void setIndexes(boolean idxs) {
      this.flags.indexes = idxs;
   }

   public void setRecord(boolean record) {
      this.flags.record = record;
   }

   public void setFile(String file) {
      this.file = file;
   }

   protected ConfigurationImpl newConfiguration() {
      return new JDBCConfigurationImpl();
   }

   protected void executeOn(String[] files) throws Exception {
      if ("import".equals(this.flags.action)) {
         this.assertFiles(files);
      }

      ClassLoader loader = this.getClassLoader();
      this.flags.writer = Files.getWriter(this.file, loader);
      if (!SchemaTool.run((JDBCConfiguration)this.getConfiguration(), files, this.flags, loader)) {
         throw new BuildException(_loc.get("bad-conf", (Object)"SchemaToolTask").getMessage());
      }
   }

   public static class Action extends EnumeratedAttribute {
      public String[] getValues() {
         return SchemaTool.ACTIONS;
      }
   }
}
