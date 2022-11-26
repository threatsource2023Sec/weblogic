package org.apache.openjpa.jdbc.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.PropertiesReverseCustomizer;
import org.apache.openjpa.jdbc.meta.ReverseCustomizer;
import org.apache.openjpa.jdbc.meta.ReverseMappingTool;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.CodeFormat;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.tools.ant.types.EnumeratedAttribute;

public class ReverseMappingToolTask extends AbstractTask {
   protected ReverseMappingTool.Flags flags = new ReverseMappingTool.Flags();
   protected String dirName = null;
   protected String typeMap = null;
   protected String customizerProperties = null;
   protected String customizerClass = PropertiesReverseCustomizer.class.getName();

   public ReverseMappingToolTask() {
      this.flags.metaDataLevel = "package";
      this.flags.format = new CodeFormat();
   }

   public void setPackage(String pkg) {
      this.flags.packageName = pkg;
   }

   public void setDirectory(String dirName) {
      this.dirName = dirName;
   }

   public void setUseSchemaName(boolean useSchemaName) {
      this.flags.useSchemaName = useSchemaName;
   }

   public void setUseForeignKeyName(boolean useForeignKeyName) {
      this.flags.useForeignKeyName = useForeignKeyName;
   }

   public void setNullableAsObject(boolean nullableAsObject) {
      this.flags.nullableAsObject = nullableAsObject;
   }

   public void setBlobAsObject(boolean blobAsObject) {
      this.flags.blobAsObject = blobAsObject;
   }

   public void setUseGenericCollections(boolean useGenericCollections) {
      this.flags.useGenericCollections = useGenericCollections;
   }

   public void setTypeMap(String typeMap) {
      this.typeMap = typeMap;
   }

   public void setPrimaryKeyOnJoin(boolean primaryKeyOnJoin) {
      this.flags.primaryKeyOnJoin = primaryKeyOnJoin;
   }

   public void setUseDataStoreIdentity(boolean useDataStoreIdentity) {
      this.flags.useDataStoreIdentity = useDataStoreIdentity;
   }

   public void setUseBuiltinIdentityClass(boolean useBuiltinIdentityClass) {
      this.flags.useBuiltinIdentityClass = useBuiltinIdentityClass;
   }

   public void setInverseRelations(boolean inverseRelations) {
      this.flags.inverseRelations = inverseRelations;
   }

   public void setDetachable(boolean detachable) {
      this.flags.detachable = detachable;
   }

   public void setDiscriminatorStrategy(String discStrat) {
      this.flags.discriminatorStrategy = discStrat;
   }

   public void setVersionStrategy(String versionStrat) {
      this.flags.versionStrategy = versionStrat;
   }

   public void setInnerIdentityClasses(boolean innerAppId) {
      this.flags.innerIdentityClasses = innerAppId;
   }

   public void setIdentityClassSuffix(String suffix) {
      this.flags.identityClassSuffix = suffix;
   }

   public void setMetadata(Level level) {
      this.flags.metaDataLevel = level.getValue();
   }

   public void setGenerateAnnotations(boolean genAnnotations) {
      this.flags.generateAnnotations = genAnnotations;
   }

   public void setAccessType(AccessType accessType) {
      this.flags.accessType = accessType.getValue();
   }

   public void setCustomizerClass(String customizerClass) {
      this.customizerClass = customizerClass;
   }

   public void setCustomizerProperties(String customizerProperties) {
      this.customizerProperties = customizerProperties;
   }

   public Object createCodeFormat() {
      return this.flags.format;
   }

   protected ConfigurationImpl newConfiguration() {
      return new JDBCConfigurationImpl();
   }

   protected void executeOn(String[] files) throws Exception {
      ClassLoader loader = this.getClassLoader();
      if (!StringUtils.isEmpty(this.dirName)) {
         this.flags.directory = Files.getFile(this.dirName, loader);
      }

      if (!StringUtils.isEmpty(this.typeMap)) {
         this.flags.typeMap = Configurations.parseProperties(this.typeMap);
      }

      Properties customProps = new Properties();
      File propsFile = Files.getFile(this.customizerProperties, loader);
      if (propsFile != null && (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(propsFile))) {
         FileInputStream fis = null;

         try {
            fis = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(propsFile));
         } catch (PrivilegedActionException var7) {
            throw (FileNotFoundException)var7.getException();
         }

         customProps.load(fis);
      }

      JDBCConfiguration conf = (JDBCConfiguration)this.getConfiguration();
      this.flags.customizer = (ReverseCustomizer)Configurations.newInstance(this.customizerClass, conf, (String)null, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(ReverseCustomizer.class)));
      if (this.flags.customizer != null) {
         this.flags.customizer.setConfiguration(customProps);
      }

      ReverseMappingTool.run(conf, files, this.flags, loader);
   }

   public static class AccessType extends EnumeratedAttribute {
      public String[] getValues() {
         return new String[]{"field", "property"};
      }
   }

   public static class Level extends EnumeratedAttribute {
      public String[] getValues() {
         return new String[]{"package", "class", "none"};
      }
   }
}
