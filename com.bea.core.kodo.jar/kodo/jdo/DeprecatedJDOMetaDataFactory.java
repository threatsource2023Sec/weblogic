package kodo.jdo;

import java.util.Arrays;
import java.util.Collection;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.Extensions;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.xml.sax.SAXException;
import serp.util.Strings;

public class DeprecatedJDOMetaDataFactory extends JDOMetaDataFactory {
   public static final String ELEMENT = "element-";
   public static final String KEY = "key-";
   public static final String VALUE = "value-";
   public static final String EXT_SUBCLASS_FETCH_MODE = "subclass-fetch-mode";
   public static final String EXT_AUTO_INCREMENT = "jdbc-auto-increment";
   public static final String EXT_SEQUENCE_FACTORY = "jdbc-sequence-factory";
   public static final String EXT_SEQUENCE_NAME = "jdbc-sequence-name";
   public static final String EXT_DETACHABLE = "detachable";
   public static final String[] CLASS_EXTENSIONS = new String[]{"subclass-fetch-mode", "jdbc-auto-increment", "jdbc-sequence-factory", "jdbc-sequence-name", "detachable"};
   public static final String EXT_INVERSE_OWNER = "inverse-owner";
   public static final String EXT_FETCH_GROUP = "fetch-group";
   public static final String EXT_EAGER_FETCH_MODE = "eager-fetch-mode";
   public static final String EXT_SEQUENCE_ASSIGNED = "sequence-assigned";
   public static final String EXT_DEPENDENT = "dependent";
   public static final String[] FIELD_EXTENSIONS = new String[]{"inverse-owner", "fetch-group", "eager-fetch-mode", "sequence-assigned", "jdbc-auto-increment", "dependent", "element-dependent", "key-dependent", "value-dependent"};

   protected AbstractCFMetaDataFactory.Parser newParser(boolean loading) {
      return new ExtensionTranslatingParser(this, this.repos.getConfiguration());
   }

   public void addClassExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDOMetaDataParser.CLASS_EXTENSIONS));
      exts.addAll(Arrays.asList(CLASS_EXTENSIONS));
   }

   public void addFieldExtensionKeys(Collection exts) {
      exts.addAll(Arrays.asList(JDOMetaDataParser.FIELD_EXTENSIONS));
      exts.addAll(Arrays.asList(FIELD_EXTENSIONS));
   }

   protected boolean translateClassExtension(ClassMetaData cls, String key, String value) {
      if ("detachable".equals(key)) {
         cls.setDetachable(!"false".equals(value));
         return true;
      } else {
         if ("jdbc-auto-increment".equals(key) && "true".equals(value)) {
            cls.setIdentityStrategy(3);
         } else {
            String seqName;
            if ("jdbc-sequence-factory".equals(key)) {
               seqName = this.createNamedSequence(cls, value, (String)null);
               cls.setIdentityStrategy(2);
               cls.setIdentitySequenceName(seqName);
            } else if ("jdbc-sequence-name".equals(key) && cls.getIdentityType() != 2) {
               seqName = this.createNamedSequence(cls, (String)null, value);
               cls.setIdentityStrategy(2);
               cls.setIdentitySequenceName(seqName);
            }
         }

         return false;
      }
   }

   private String createNamedSequence(ClassMetaData meta, String seqPlugin, String seqName) {
      String name = meta.getDescribedType().getName() + "IdentitySequence";
      SequenceMetaData seq = this.repos.addSequenceMetaData(name);
      seq.setSource(meta.getSourceFile(), meta.getDescribedType(), 0);
      if (seqPlugin != null) {
         seq.setSequencePlugin(seqPlugin);
      } else if (seqName != null) {
         seq.setSequence(seqName);
      }

      return name;
   }

   protected boolean translateFieldExtension(FieldMetaData field, String key, String value) {
      if ("fetch-group".equals(key)) {
         field.getDefiningMetaData().addDeclaredFetchGroup(value);
         field.setInFetchGroup(value, true);
         return true;
      } else if ("dependent".equals(key)) {
         if ("true".equals(value)) {
            field.setCascadeDelete(2);
         } else {
            field.setCascadeDelete(0);
         }

         return true;
      } else {
         if ("element-dependent".equals(key) || "value-dependent".equals(key)) {
            ValueMetaData elem = field.getElement();
            if ("true".equals(value)) {
               elem.setCascadeDelete(2);
            } else {
               elem.setCascadeDelete(0);
            }
         }

         if ("key-dependent".equals(key)) {
            if ("true".equals(value)) {
               field.getKey().setCascadeDelete(2);
            } else {
               field.getKey().setCascadeDelete(0);
            }

            return true;
         } else {
            if ("inverse-owner".equals(key)) {
               field.setMappedBy(value);
            } else if ("sequence-assigned".equals(key) && "true".equals(value)) {
               this.handleSequence(field);
            } else if ("jdbc-auto-increment".equals(key) && "true".equals(value)) {
               field.setValueStrategy(3);
            }

            return false;
         }
      }
   }

   private void handleSequence(FieldMetaData field) {
      String name = "system";
      ClassMetaData cmd = field.getDeclaringMetaData();
      if (cmd != null && cmd.hasExtension("kodo", "jdbc-sequence-name")) {
         String seqName = cmd.getStringExtension("kodo", "jdbc-sequence-name");
         name = this.createNamedSequence(cmd, (String)null, seqName);
      }

      field.setValueSequenceName(name);
   }

   public static class ExtensionTranslatingParser extends JDOMetaDataParser {
      final DeprecatedJDOMetaDataFactory _factory;

      public ExtensionTranslatingParser(DeprecatedJDOMetaDataFactory factory, OpenJPAConfiguration conf) {
         super(conf);
         this._factory = factory;
      }

      protected boolean setKnownClassExtension(ClassMetaData cls, String key, String value) throws SAXException {
         if (this.addNestedExtension(cls, key, value)) {
            return true;
         } else {
            return super.setKnownClassExtension(cls, key, value) || this._factory.translateClassExtension(cls, key, value);
         }
      }

      protected boolean setKnownFieldExtension(FieldMetaData fmd, String key, String value) throws SAXException {
         if (this.addNestedExtension(fmd, key, value)) {
            return true;
         } else {
            return super.setKnownFieldExtension(fmd, key, value) || this._factory.translateFieldExtension(fmd, key, value);
         }
      }

      private boolean addNestedExtension(Extensions exts, String key, String value) {
         if (key != null && key.indexOf(47) != -1) {
            String[] keys = Strings.split(key, "/", 0);

            for(int i = 0; i < keys.length - 1; ++i) {
               exts = exts.getEmbeddedExtensions("kodo", keys[i], true);
            }

            exts.addExtension("kodo", keys[keys.length - 1], value);
            return true;
         } else {
            return false;
         }
      }
   }
}
