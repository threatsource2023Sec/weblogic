package kodo.persistence.jdbc;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataParser;
import org.apache.openjpa.persistence.jdbc.PersistenceMappingFactory;

public class KodoPersistenceMappingFactory extends PersistenceMappingFactory {
   protected AnnotationPersistenceMetaDataParser newAnnotationParser() {
      return new KodoAnnotationPersistenceMappingParser((JDBCConfiguration)this.repos.getConfiguration());
   }
}
