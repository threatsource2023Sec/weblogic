package kodo.jdo.jdbc;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import kodo.jdo.JDOMetaDataFactory;
import kodo.jdo.JDOMetaDataParser;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.meta.MetaDataParser;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.InternalException;

public class ORMFileJDORMappingFactory extends JDOMetaDataFactory implements Configurable {
   private boolean _cnames = false;
   private String _mapping = null;

   public boolean getConstraintNames() {
      return this._cnames;
   }

   public void setConstraintNames(boolean cnames) {
      this._cnames = cnames;
   }

   public String getMapping() {
      return this._mapping;
   }

   public void setMapping(String mapping) {
      this._mapping = mapping;
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (cls != null && (mode & 2) != 0) {
         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
         JDOMetaDataParser parser = this.getParser();
         parser.setClassLoader(loader);
         parser.setEnvClassLoader(envLoader);
         parser.setMode(mode);
         this.parse(parser, new Class[]{cls});
      }
   }

   public MetaDataDefaults getDefaults() {
      throw new InternalException();
   }

   public void addClassExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDORMetaDataParser.CLASS_EXTENSIONS));
   }

   public void addFieldExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDORMetaDataParser.FIELD_EXTENSIONS));
   }

   protected AbstractCFMetaDataFactory.Parser newParser(boolean loading) {
      JDORMetaDataParser parser = new JDORMetaDataParser((JDBCConfiguration)this.repos.getConfiguration());
      parser.setMappingOverride(loading && ((MappingRepository)this.repos).getStrategyInstaller().isAdapting());
      return parser;
   }

   protected AbstractCFMetaDataFactory.Serializer newSerializer() {
      JDORMetaDataSerializer ser = new JDORMetaDataSerializer((JDBCConfiguration)this.repos.getConfiguration());
      ser.setConstraintNames(this._cnames);
      ser.setSyncMappingInfo(true);
      return ser;
   }

   protected String getMetaDataSuffix() {
      return this._mapping == null ? ".orm" : "-" + this._mapping + ".orm";
   }

   protected boolean isMappingOnlyFactory() {
      return true;
   }

   protected void parse(MetaDataParser parser, Class[] cls) {
      ((JDOMetaDataParser)parser).setSuffix(this.getMetaDataSuffix());
      super.parse(parser, cls);
   }

   protected File getSourceFile(ClassMetaData meta) {
      return ((ClassMapping)meta).getMappingInfo().getSourceFile();
   }

   protected void setSourceFile(ClassMetaData meta, File file) {
      ClassMappingInfo info = ((ClassMapping)meta).getMappingInfo();
      info.setSource(file, info.getSourceType());
   }

   protected File getSourceFile(SequenceMetaData meta) {
      return ((SequenceMapping)meta).getMappingFile();
   }

   protected void setSourceFile(SequenceMetaData meta, File file) {
      ((SequenceMapping)meta).setMappingFile(file);
   }

   public void setConfiguration(Configuration conf) {
      this._mapping = ((JDBCConfiguration)conf).getMapping();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}
