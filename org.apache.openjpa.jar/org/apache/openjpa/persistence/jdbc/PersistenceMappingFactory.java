package org.apache.openjpa.persistence.jdbc;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataParser;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataSerializer;
import org.apache.openjpa.persistence.PersistenceMetaDataFactory;
import org.apache.openjpa.persistence.XMLPersistenceMetaDataParser;
import org.apache.openjpa.persistence.XMLPersistenceMetaDataSerializer;

public class PersistenceMappingFactory extends PersistenceMetaDataFactory {
   protected AnnotationPersistenceMetaDataParser newAnnotationParser() {
      AnnotationPersistenceMappingParser parser = new AnnotationPersistenceMappingParser((JDBCConfiguration)this.repos.getConfiguration());
      if (this.strict) {
         parser.setMappingOverride(((MappingRepository)this.repos).getStrategyInstaller().isAdapting());
      }

      return parser;
   }

   protected AnnotationPersistenceMetaDataSerializer newAnnotationSerializer() {
      AnnotationPersistenceMappingSerializer ser = new AnnotationPersistenceMappingSerializer((JDBCConfiguration)this.repos.getConfiguration());
      ser.setSyncMappingInfo(true);
      return ser;
   }

   protected XMLPersistenceMetaDataParser newXMLParser(boolean loading) {
      XMLPersistenceMappingParser parser = new XMLPersistenceMappingParser((JDBCConfiguration)this.repos.getConfiguration());
      if (this.strict && loading) {
         parser.setMappingOverride(((MappingRepository)this.repos).getStrategyInstaller().isAdapting());
      }

      return parser;
   }

   protected XMLPersistenceMetaDataSerializer newXMLSerializer() {
      XMLPersistenceMappingSerializer ser = new XMLPersistenceMappingSerializer((JDBCConfiguration)this.repos.getConfiguration());
      ser.setSyncMappingInfo(true);
      return ser;
   }
}
