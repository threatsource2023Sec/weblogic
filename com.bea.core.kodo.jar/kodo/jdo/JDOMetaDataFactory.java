package kodo.jdo;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.ApplicationIdTool;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.meta.MetaDataFilter;
import org.apache.openjpa.lib.meta.SuffixMetaDataFilter;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.MultiClassLoader;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.Project;
import serp.util.Strings;

public class JDOMetaDataFactory extends AbstractCFMetaDataFactory implements ApplicationIdTool.ObjectIdLoader {
   private final JDOMetaDataDefaults _defaults = new JDOMetaDataDefaults();
   private JDOMetaDataParser _parser = null;
   private boolean _topDown = false;
   private boolean _useSchema = false;

   public boolean isScanTopDown() {
      return this._topDown;
   }

   public void setScanTopDown(boolean topDown) {
      this._topDown = topDown;
   }

   public boolean useSchemaValidation() {
      return this._useSchema;
   }

   public void setUseSchemaValidation(boolean useSchema) {
      this._useSchema = useSchema;
   }

   public boolean isDeclaredInterfacePersistent() {
      return this._defaults.isDeclaredInterfacePersistent();
   }

   public void setDeclaredInterfacePersistent(boolean pers) {
      this._defaults.setDeclaredInterfacePersistent(pers);
   }

   public JDOMetaDataParser getParser() {
      if (this._parser == null) {
         this._parser = (JDOMetaDataParser)this.newParser(true);
         this._parser.setRepository(this.repos);
         this._parser.setUseSchemaValidation(this._useSchema);
      }

      return this._parser;
   }

   public void setParser(JDOMetaDataParser parser) {
      if (this._parser != null) {
         this._parser.setRepository((MetaDataRepository)null);
      }

      if (parser != null) {
         parser.setRepository(this.repos);
      }

      this._parser = parser;
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (mode != 0) {
         if (!this.strict && (mode & 1) != 0) {
            mode |= 2;
         }

         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
         JDOMetaDataParser parser = this.getParser();
         parser.setClassLoader(loader);
         parser.setEnvClassLoader(envLoader);
         parser.setMode(mode);
         this.parse(parser, new Class[]{cls});
      }
   }

   public MetaDataDefaults getDefaults() {
      return this._defaults;
   }

   public ClassArgParser newClassArgParser() {
      ClassArgParser parser = new ClassArgParser();
      parser.setMetaDataStructure("package", "name", new String[]{"class", "interface"}, "name");
      return parser;
   }

   public void clear() {
      super.clear();
      if (this._parser != null) {
         this._parser.clear();
      }

   }

   public void addClassExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDOMetaDataParser.CLASS_EXTENSIONS));
   }

   public void addFieldExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDOMetaDataParser.FIELD_EXTENSIONS));
   }

   public void loadXMLMetaData(FieldMetaData fieldMetaData) {
   }

   protected AbstractCFMetaDataFactory.Parser newParser(boolean loading) {
      return new JDOMetaDataParser(this.repos.getConfiguration());
   }

   protected AbstractCFMetaDataFactory.Serializer newSerializer() {
      return new JDOMetaDataSerializer(this.repos.getConfiguration());
   }

   protected AbstractCFMetaDataFactory.Serializer newAnnotationSerializer() {
      return this.newSerializer();
   }

   protected boolean isParseTopDown() {
      return this._topDown;
   }

   protected File defaultSourceFile(ClassMetaData meta) {
      String fileName;
      if ((this.store & 1) != 0) {
         fileName = Strings.getClassName(meta.getDescribedType());
      } else {
         fileName = "package";
      }

      fileName = fileName + this.getMetaDataSuffix();
      File sdir = this.dir;
      if (sdir == null) {
         sdir = Files.getSourceFile(meta.getDescribedType());
         if (sdir == null) {
            sdir = Files.getClassFile(meta.getDescribedType());
         }

         if (sdir != null) {
            sdir = sdir.getParentFile();
         }
      }

      String pkg = Strings.getPackageName(meta.getDescribedType());
      sdir = Files.getPackageFile(sdir, pkg, false);
      return new File(sdir, fileName);
   }

   protected File defaultSourceFile(QueryMetaData query, Map clsNames) {
      ClassMetaData meta = this.getDefiningMetaData(query, clsNames);
      File sdir = meta == null ? null : this.getSourceFile(meta);
      String fileName;
      if (sdir != null) {
         if (query.getSourceMode() != 4) {
            return sdir;
         }

         fileName = Strings.getPackageName(sdir.getName());
         sdir = sdir.getParentFile();
      } else {
         fileName = "package";
         sdir = Files.getPackageFile(this.dir, Strings.getPackageName(query.getDefiningType()), false);
      }

      String ext = query.getSourceMode() == 4 ? this.getQueryMetaDataSuffix() : this.getMetaDataSuffix();
      return new File(sdir, fileName + ext);
   }

   protected File defaultSourceFile(SequenceMetaData seq, Map clsNames) {
      String pkg = Strings.getPackageName(seq.getName());
      ClassMetaData meta = this.getPackageMetaData(pkg, clsNames);
      File file = meta == null ? null : this.getSourceFile(meta);
      return file != null ? file : new File(Files.getPackageFile(this.dir, pkg, false), "package" + this.getMetaDataSuffix());
   }

   protected MetaDataFilter newMetaDataFilter() {
      return new SuffixMetaDataFilter(this.getMetaDataSuffix());
   }

   protected String getMetaDataSuffix() {
      return ".jdo";
   }

   private String getQueryMetaDataSuffix() {
      return ".jdoquery";
   }

   private ClassMetaData getPackageMetaData(String pkg, Map clsNames) {
      if (pkg == null) {
         pkg = "";
      }

      Iterator itr = clsNames.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         entry = (Map.Entry)itr.next();
      } while(!pkg.equals(Strings.getPackageName((String)entry.getKey())));

      return (ClassMetaData)entry.getValue();
   }

   public void setLoadObjectIds() {
      this.setParser(new ObjectIdMetaDataParser(this.repos.getConfiguration()));
   }

   private static class ObjectIdMetaDataParser extends JDOMetaDataParser {
      private final BCClassLoader _oidLoader = new BCClassLoader(new Project());

      public ObjectIdMetaDataParser(OpenJPAConfiguration conf) {
         super(conf);
      }

      public void setClassLoader(ClassLoader loader) {
         MultiClassLoader multiLoader = new MultiClassLoader();
         multiLoader.addClassLoader(loader);
         multiLoader.addClassLoader(this._oidLoader);
         super.setClassLoader(multiLoader);
      }

      protected boolean startClass(String className, Attributes attrs) throws SAXException {
         String oidName = attrs.getValue("objectid-class");
         if (oidName != null) {
            try {
               this.classForName(oidName, false);
            } catch (SAXException var6) {
               if (oidName.indexOf(46) == -1 && this.currentPackage().length() > 0) {
                  oidName = this.currentPackage() + "." + oidName;
               }

               BCClass oid = this._oidLoader.getProject().loadClass(oidName, (ClassLoader)null);
               oid.addDefaultConstructor();
            }
         }

         return super.startClass(className, attrs);
      }
   }
}
