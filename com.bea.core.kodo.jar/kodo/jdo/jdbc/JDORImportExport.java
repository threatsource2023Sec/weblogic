package kodo.jdo.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.MappingTool;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;

public class JDORImportExport implements MappingTool.ImportExport, MetaDataModes {
   private static final Localizer _loc = Localizer.forPackage(JDORImportExport.class);

   public boolean exportMappings(JDBCConfiguration conf, Class[] act, boolean meta, Log log, Writer mappingWriter) throws IOException {
      MappingRepository repos = conf.getMappingRepositoryInstance();
      JDORMetaDataSerializer ser = new JDORMetaDataSerializer(conf);
      ser.setSyncMappingInfo(true);
      AbstractDeprecatedJDOMappingFactory dep = null;
      if (!(repos.getMetaDataFactory() instanceof AbstractDeprecatedJDOMappingFactory)) {
         if (!repos.getMappingDefaults().defaultMissingInfo()) {
            repos.setResolve(2, false);
         }
      } else {
         dep = (AbstractDeprecatedJDOMappingFactory)repos.getMetaDataFactory();
      }

      ser.setMode(6);
      ser.setMode(1, meta);

      for(int i = 0; i < act.length; ++i) {
         log.info(_loc.get("export", act[i]));
         ClassMapping cls = repos.getMapping(act[i], (ClassLoader)null, true);
         if (dep != null) {
            if (meta) {
               dep.stripDeprecatedExtensions(cls);
            }

            if (cls.getIdentitySequenceName() != null) {
               ser.addSequenceMetaData(cls.getIdentitySequenceMetaData());
            }
         }

         ser.addMetaData(cls);
      }

      log.info(_loc.get("write-export"));
      if (mappingWriter == null) {
         mappingWriter = new PrintWriter(System.out);
      }

      ser.serialize((Writer)mappingWriter, 1);
      ((Writer)mappingWriter).flush();
      return true;
   }

   public boolean importMappings(JDBCConfiguration conf, Class[] act, String[] args, boolean meta, Log log, ClassLoader loader) throws IOException {
      MappingRepository repos = conf.getMappingRepositoryInstance();
      repos.setResolve(2, false);
      if (!meta) {
         repos.setSourceMode(2, false);

         for(int i = 0; i < act.length; ++i) {
            repos.getMetaData(act[i], loader, true).setSourceMode(2, false);
         }
      }

      JDORMetaDataParser parser = new JDORMetaDataParser(conf);
      parser.setRepository(repos);
      parser.setMode(6);
      parser.setMode(1, meta);
      parser.setClassLoader(loader);
      Set files = new HashSet((int)((double)args.length * 1.33 + 1.0));

      for(int i = 0; i < args.length; ++i) {
         File file = Files.getFile(args[i], loader);
         log.info(_loc.get("import", file));
         parser.parse(file);
         files.add(file);
      }

      ClassMapping[] clss = new ClassMapping[act.length];

      for(int i = 0; i < act.length; ++i) {
         clss[i] = repos.getMapping(act[i], loader, true);
         clss[i].defineSuperclassFields(false);
         ClassMappingInfo var10000 = clss[i].getMappingInfo();
         ClassMapping var10002 = clss[i];
         var10000.setSource((File)null, 0);
         if (meta) {
            ClassMapping var19 = clss[i];
            var10002 = clss[i];
            var19.setSource((File)null, 0);
         }
      }

      SequenceMetaData[] seqs = repos.getSequenceMetaDatas();

      Object var22;
      for(int i = 0; i < seqs.length; ++i) {
         if (files.contains(seqs[i].getSourceFile())) {
            SequenceMetaData var20 = seqs[i];
            var22 = seqs[i].getSourceScope();
            SequenceMetaData var10003 = seqs[i];
            var20.setSource((File)null, var22, 0);
            ((SequenceMapping)seqs[i]).setMappingFile((File)null);
         }
      }

      QueryMetaData[] queries = repos.getQueryMetaDatas();

      int mode;
      for(mode = 0; mode < queries.length; ++mode) {
         if (files.contains(queries[mode].getSourceFile())) {
            QueryMetaData var21 = queries[mode];
            var22 = queries[mode].getSourceScope();
            QueryMetaData var23 = queries[mode];
            var21.setSource((File)null, var22, 0);
         }
      }

      mode = 2;
      if (meta) {
         mode |= 1;
      }

      log.info(_loc.get("store-import"));
      repos.getMetaDataFactory().store(clss, queries, seqs, mode, (Map)null);
      return true;
   }
}
