package org.apache.openjpa.jdbc.ant;

import java.security.AccessController;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.MappingTool;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MultiLoaderClassResolver;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

public class MappingToolTask extends AbstractTask {
   private static final Localizer _loc = Localizer.forPackage(MappingToolTask.class);
   protected MappingTool.Flags flags = new MappingTool.Flags();
   protected String file = null;
   protected String schemaFile = null;
   protected String sqlFile = null;
   protected boolean tmpClassLoader = true;

   public void setAction(Action act) {
      this.flags.action = act.getValue();
   }

   public void setSchemaAction(SchemaAction act) {
      this.flags.schemaAction = act.getValue();
   }

   public void setReadSchema(boolean readSchema) {
      this.flags.readSchema = readSchema;
   }

   public void setIgnoreErrors(boolean ignoreErrors) {
      this.flags.ignoreErrors = ignoreErrors;
   }

   public void setDropTables(boolean dropTables) {
      this.flags.dropTables = dropTables;
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

   public void setFile(String file) {
      this.file = file;
   }

   public void setSchemaFile(String schemaFile) {
      this.schemaFile = schemaFile;
   }

   public void setSQLFile(String sqlFile) {
      this.sqlFile = sqlFile;
   }

   public void setMeta(boolean meta) {
      this.flags.meta = meta;
   }

   protected ConfigurationImpl newConfiguration() {
      return new JDBCConfigurationImpl();
   }

   protected void executeOn(String[] files) throws Exception {
      if ("import".equals(this.flags.action)) {
         this.assertFiles(files);
      }

      ClassLoader toolLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(MappingTool.class));
      ClassLoader loader = toolLoader;
      MultiLoaderClassResolver resolver = new MultiLoaderClassResolver();
      if (this.tmpClassLoader) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newTemporaryClassLoaderAction(this.getClassLoader()));
         resolver.addClassLoader(loader);
      }

      resolver.addClassLoader(toolLoader);
      if (this.flags.meta && "add".equals(this.flags.action)) {
         this.flags.metaDataFile = Files.getFile(this.file, loader);
      } else {
         this.flags.mappingWriter = Files.getWriter(this.file, loader);
      }

      this.flags.schemaWriter = Files.getWriter(this.schemaFile, loader);
      this.flags.sqlWriter = Files.getWriter(this.sqlFile, loader);
      JDBCConfiguration conf = (JDBCConfiguration)this.getConfiguration();
      conf.setClassResolver(resolver);
      if (!MappingTool.run(conf, files, this.flags, loader)) {
         throw new BuildException(_loc.get("bad-conf", (Object)"MappingToolTask").getMessage());
      }
   }

   public void setTmpClassLoader(boolean tmpClassLoader) {
      this.tmpClassLoader = tmpClassLoader;
   }

   public static class SchemaAction extends EnumeratedAttribute {
      public String[] getValues() {
         String[] actions = new String[SchemaTool.ACTIONS.length + 1];
         System.arraycopy(SchemaTool.ACTIONS, 0, actions, 0, SchemaTool.ACTIONS.length);
         actions[actions.length - 1] = "none";
         return actions;
      }
   }

   public static class Action extends EnumeratedAttribute {
      public String[] getValues() {
         return MappingTool.ACTIONS;
      }
   }
}
