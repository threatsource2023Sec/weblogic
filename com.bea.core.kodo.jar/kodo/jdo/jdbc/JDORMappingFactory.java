package kodo.jdo.jdbc;

import java.util.Arrays;
import java.util.Collection;
import kodo.jdo.JDOMetaDataFactory;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;

public class JDORMappingFactory extends JDOMetaDataFactory {
   private boolean _cnames = false;

   public boolean getConstraintNames() {
      return this._cnames;
   }

   public void setConstraintNames(boolean cnames) {
      this._cnames = cnames;
   }

   public void addClassExtensionKeys(Collection exts) {
      super.addClassExtensionKeys(exts);
      exts.addAll(Arrays.asList(JDORMetaDataParser.CLASS_EXTENSIONS));
   }

   public void addFieldExtensionKeys(Collection exts) {
      super.addFieldExtensionKeys(exts);
      exts.addAll(Arrays.asList(JDORMetaDataParser.FIELD_EXTENSIONS));
   }

   protected AbstractCFMetaDataFactory.Parser newParser(boolean loading) {
      JDORMetaDataParser parser = new JDORMetaDataParser((JDBCConfiguration)this.repos.getConfiguration());
      if (this.strict && loading) {
         parser.setMappingOverride(((MappingRepository)this.repos).getStrategyInstaller().isAdapting());
      }

      return parser;
   }

   protected AbstractCFMetaDataFactory.Serializer newSerializer() {
      JDORMetaDataSerializer ser = new JDORMetaDataSerializer((JDBCConfiguration)this.repos.getConfiguration());
      ser.setConstraintNames(this._cnames);
      ser.setSyncMappingInfo(true);
      return ser;
   }
}
