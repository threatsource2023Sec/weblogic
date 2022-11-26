package org.apache.openjpa.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.GenericConfigurable;
import org.apache.openjpa.lib.meta.ClassAnnotationMetaDataFilter;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.meta.MetaDataFilter;
import org.apache.openjpa.lib.meta.MetaDataParser;
import org.apache.openjpa.lib.util.J2DoPriv5Helper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.MultiClassLoader;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.MetaDataException;

public class PersistenceMetaDataFactory extends AbstractCFMetaDataFactory implements Configurable, GenericConfigurable {
   private static final Localizer _loc = Localizer.forPackage(PersistenceMetaDataFactory.class);
   private final PersistenceMetaDataDefaults _def = new PersistenceMetaDataDefaults();
   private AnnotationPersistenceMetaDataParser _annoParser = null;
   private AnnotationPersistenceXMLMetaDataParser _annoXMLParser = null;
   private XMLPersistenceMetaDataParser _xmlParser = null;
   private Map _xml = null;
   private Set _unparsed = null;
   private boolean _fieldOverride = true;

   public void setFieldOverride(boolean field) {
      this._fieldOverride = field;
   }

   public boolean getFieldOverride() {
      return this._fieldOverride;
   }

   public AnnotationPersistenceMetaDataParser getAnnotationParser() {
      if (this._annoParser == null) {
         this._annoParser = this.newAnnotationParser();
         this._annoParser.setRepository(this.repos);
      }

      return this._annoParser;
   }

   public void setAnnotationParser(AnnotationPersistenceMetaDataParser parser) {
      if (this._annoParser != null) {
         this._annoParser.setRepository((MetaDataRepository)null);
      }

      if (parser != null) {
         parser.setRepository(this.repos);
      }

      this._annoParser = parser;
   }

   protected AnnotationPersistenceMetaDataParser newAnnotationParser() {
      return new AnnotationPersistenceMetaDataParser(this.repos.getConfiguration());
   }

   protected AnnotationPersistenceMetaDataSerializer newAnnotationSerializer() {
      return new AnnotationPersistenceMetaDataSerializer(this.repos.getConfiguration());
   }

   public XMLPersistenceMetaDataParser getXMLParser() {
      if (this._xmlParser == null) {
         this._xmlParser = this.newXMLParser(true);
         this._xmlParser.setRepository(this.repos);
         if (this._fieldOverride) {
            this._xmlParser.setAnnotationParser(this.getAnnotationParser());
         }
      }

      return this._xmlParser;
   }

   public void setXMLParser(XMLPersistenceMetaDataParser parser) {
      if (this._xmlParser != null) {
         this._xmlParser.setRepository((MetaDataRepository)null);
      }

      if (parser != null) {
         parser.setRepository(this.repos);
      }

      this._xmlParser = parser;
   }

   protected XMLPersistenceMetaDataParser newXMLParser(boolean loading) {
      return new XMLPersistenceMetaDataParser(this.repos.getConfiguration());
   }

   protected XMLPersistenceMetaDataSerializer newXMLSerializer() {
      return new XMLPersistenceMetaDataSerializer(this.repos.getConfiguration());
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (mode != 0) {
         if (!this.strict && (mode & 1) != 0) {
            mode |= 2;
         }

         this.getPersistentTypeNames(false, envLoader);
         URL xml = this.findXML(cls);
         boolean parsedXML = false;
         ClassMetaData meta;
         if (this._unparsed != null && !this._unparsed.isEmpty() && (mode & 1) != 0) {
            Iterator i$ = this._unparsed.iterator();

            while(i$.hasNext()) {
               URL url = (URL)i$.next();
               this.parseXML(url, cls, mode, envLoader);
            }

            parsedXML = this._unparsed.contains(xml);
            this._unparsed.clear();
            meta = this.repos.getCachedMetaData(cls);
            if (meta != null && (meta.getSourceMode() & mode) == mode) {
               this.validateStrategies(meta);
               return;
            }
         }

         if (cls != null) {
            if (!parsedXML && xml != null) {
               this.parseXML(xml, cls, mode, envLoader);
               meta = this.repos.getCachedMetaData(cls);
               if (meta != null && (meta.getSourceMode() & mode) == mode) {
                  this.validateStrategies(meta);
                  return;
               }
            }

            AnnotationPersistenceMetaDataParser parser = this.getAnnotationParser();
            parser.setEnvClassLoader(envLoader);
            parser.setMode(mode);
            parser.parse(cls);
            meta = this.repos.getCachedMetaData(cls);
            if (meta != null && (meta.getSourceMode() & mode) == mode) {
               this.validateStrategies(meta);
            }

         }
      }
   }

   private void parseXML(URL xml, Class cls, int mode, ClassLoader envLoader) {
      ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, (ClassLoader)null);
      if (envLoader != null && envLoader != loader) {
         MultiClassLoader mult = new MultiClassLoader();
         mult.addClassLoader(envLoader);
         if (loader instanceof MultiClassLoader) {
            mult.addClassLoaders((MultiClassLoader)loader);
         } else {
            mult.addClassLoader((ClassLoader)loader);
         }

         loader = mult;
      }

      XMLPersistenceMetaDataParser xmlParser = this.getXMLParser();
      xmlParser.setClassLoader((ClassLoader)loader);
      xmlParser.setEnvClassLoader(envLoader);
      xmlParser.setMode(mode);

      try {
         xmlParser.parse(xml);
      } catch (IOException var8) {
         throw new GeneralException(var8);
      }
   }

   private URL findXML(Class cls) {
      if (this._xml != null && cls != null) {
         Iterator i$ = this._xml.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            if (((Set)entry.getValue()).contains(cls.getName())) {
               return (URL)entry.getKey();
            }
         }
      }

      return null;
   }

   protected void mapPersistentTypeNames(Object rsrc, String[] names) {
      if (rsrc.toString().endsWith(".class")) {
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("map-persistent-types-skipping-class", rsrc));
         }

      } else if (!(rsrc instanceof URL)) {
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("map-persistent-types-skipping-non-url", rsrc));
         }

      } else {
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("map-persistent-type-names", rsrc, Arrays.asList(names)));
         }

         if (this._xml == null) {
            this._xml = new HashMap();
         }

         this._xml.put((URL)rsrc, new HashSet(Arrays.asList(names)));
         if (this._unparsed == null) {
            this._unparsed = new HashSet();
         }

         this._unparsed.add((URL)rsrc);
      }
   }

   public Class getQueryScope(String queryName, ClassLoader loader) {
      if (queryName == null) {
         return null;
      } else {
         Collection classes = this.repos.loadPersistentTypes(false, loader);
         Iterator i$ = classes.iterator();

         Class cls;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            cls = (Class)i$.next();
            if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, NamedQuery.class)) && this.hasNamedQuery(queryName, (NamedQuery)cls.getAnnotation(NamedQuery.class))) {
               return cls;
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, NamedQueries.class)) && this.hasNamedQuery(queryName, ((NamedQueries)cls.getAnnotation(NamedQueries.class)).value())) {
               return cls;
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, NamedNativeQuery.class)) && this.hasNamedNativeQuery(queryName, (NamedNativeQuery)cls.getAnnotation(NamedNativeQuery.class))) {
               return cls;
            }
         } while(!(Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, NamedNativeQueries.class)) || !this.hasNamedNativeQuery(queryName, ((NamedNativeQueries)cls.getAnnotation(NamedNativeQueries.class)).value()));

         return cls;
      }
   }

   public Class getResultSetMappingScope(String rsMappingName, ClassLoader loader) {
      if (rsMappingName == null) {
         return null;
      } else {
         Collection classes = this.repos.loadPersistentTypes(false, loader);
         Iterator i$ = classes.iterator();

         Class cls;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            cls = (Class)i$.next();
            if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, SqlResultSetMapping.class)) && this.hasRSMapping(rsMappingName, (SqlResultSetMapping)cls.getAnnotation(SqlResultSetMapping.class))) {
               return cls;
            }
         } while(!(Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(cls, SqlResultSetMappings.class)) || !this.hasRSMapping(rsMappingName, ((SqlResultSetMappings)cls.getAnnotation(SqlResultSetMappings.class)).value()));

         return cls;
      }
   }

   private boolean hasNamedQuery(String query, NamedQuery... queries) {
      NamedQuery[] arr$ = queries;
      int len$ = queries.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         NamedQuery q = arr$[i$];
         if (query.equals(q.name())) {
            return true;
         }
      }

      return false;
   }

   private boolean hasRSMapping(String rsMapping, SqlResultSetMapping... mappings) {
      SqlResultSetMapping[] arr$ = mappings;
      int len$ = mappings.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         SqlResultSetMapping m = arr$[i$];
         if (rsMapping.equals(m.name())) {
            return true;
         }
      }

      return false;
   }

   private boolean hasNamedNativeQuery(String query, NamedNativeQuery... queries) {
      NamedNativeQuery[] arr$ = queries;
      int len$ = queries.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         NamedNativeQuery q = arr$[i$];
         if (query.equals(q.name())) {
            return true;
         }
      }

      return false;
   }

   protected MetaDataFilter newMetaDataFilter() {
      ClassAnnotationMetaDataFilter camdf = new ClassAnnotationMetaDataFilter(new Class[]{Entity.class, Embeddable.class, MappedSuperclass.class});
      camdf.setLog(this.log);
      return camdf;
   }

   private void validateStrategies(ClassMetaData meta) {
      StringBuffer buf = null;
      FieldMetaData[] arr$ = meta.getDeclaredFields();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         FieldMetaData fmd = arr$[i$];
         if (!fmd.isExplicit()) {
            if (buf == null) {
               buf = new StringBuffer();
            } else {
               buf.append(", ");
            }

            buf.append(fmd);
         }
      }

      if (buf != null) {
         throw new MetaDataException(_loc.get("no-pers-strat", (Object)buf));
      }
   }

   public MetaDataDefaults getDefaults() {
      return this._def;
   }

   public ClassArgParser newClassArgParser() {
      ClassArgParser parser = new ClassArgParser();
      parser.setMetaDataStructure("package", (String)null, new String[]{"entity", "embeddable", "mapped-superclass"}, "class");
      return parser;
   }

   public void clear() {
      super.clear();
      if (this._annoParser != null) {
         this._annoParser.clear();
      }

      if (this._xmlParser != null) {
         this._xmlParser.clear();
      }

      if (this._xml != null) {
         this._xml.clear();
      }

   }

   protected AbstractCFMetaDataFactory.Parser newParser(boolean loading) {
      return this.newXMLParser(loading);
   }

   protected AbstractCFMetaDataFactory.Serializer newSerializer() {
      return this.newXMLSerializer();
   }

   protected void parse(MetaDataParser parser, Class[] cls) {
      this.parse(parser, Collections.singleton(this.defaultXMLFile()));
   }

   protected File defaultSourceFile(ClassMetaData meta) {
      return this.defaultXMLFile();
   }

   protected File defaultSourceFile(QueryMetaData query, Map clsNames) {
      ClassMetaData meta = this.getDefiningMetaData(query, clsNames);
      File file = meta == null ? null : meta.getSourceFile();
      return file != null ? file : this.defaultXMLFile();
   }

   protected File defaultSourceFile(SequenceMetaData seq, Map clsNames) {
      return this.defaultXMLFile();
   }

   private File defaultXMLFile() {
      ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(this.getClass(), (ClassLoader)null);
      URL rsrc = (URL)AccessController.doPrivileged(J2DoPriv5Helper.getResourceAction(loader, "META-INF/orm.xml"));
      if (rsrc != null) {
         File file = new File(rsrc.getFile());
         if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.existsAction(file))) {
            return file;
         }
      }

      return new File("orm.xml");
   }

   public void setConfiguration(Configuration conf) {
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      if (this.rsrcs == null) {
         this.rsrcs = Collections.singleton("META-INF/orm.xml");
      } else {
         this.rsrcs.add("META-INF/orm.xml");
      }

   }

   public void setInto(Options opts) {
      opts.keySet().retainAll(opts.setInto(this._def).keySet());
   }

   public AnnotationPersistenceXMLMetaDataParser getXMLAnnotationParser() {
      if (this._annoXMLParser == null) {
         this._annoXMLParser = this.newXMLAnnotationParser();
         this._annoXMLParser.setRepository(this.repos);
      }

      return this._annoXMLParser;
   }

   public void setXMLAnnotationParser(AnnotationPersistenceXMLMetaDataParser parser) {
      if (this._annoXMLParser != null) {
         this._annoXMLParser.setRepository((MetaDataRepository)null);
      }

      if (parser != null) {
         parser.setRepository(this.repos);
      }

      this._annoXMLParser = parser;
   }

   protected AnnotationPersistenceXMLMetaDataParser newXMLAnnotationParser() {
      return new AnnotationPersistenceXMLMetaDataParser(this.repos.getConfiguration());
   }

   public void loadXMLMetaData(FieldMetaData fmd) {
      AnnotationPersistenceXMLMetaDataParser parser = this.getXMLAnnotationParser();
      parser.parse(fmd);
   }
}
