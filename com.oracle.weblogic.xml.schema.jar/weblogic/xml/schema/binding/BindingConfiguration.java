package weblogic.xml.schema.binding;

import java.util.Map;

public interface BindingConfiguration {
   String JAVA_NS_PREFIX = "java:";
   String HOLDER_SUB_PACKAGE = "holders";
   String HOLDER_SUFFIX = "Holder";
   String HOLDER_VALUE_FIELD = "value";

   String getClassTargetDirectory();

   void setClassTargetDirectory(String var1);

   boolean isSeparateBeanInterfaces();

   void setSeparateBeanInterfaces(boolean var1);

   String getCompiler();

   void setCompiler(String var1);

   String getCompilerClasspath();

   void setCompilerClasspath(String var1);

   boolean isCompilerExit();

   void setCompilerExit(boolean var1);

   boolean isKeepGenerated();

   void setKeepGenerated(boolean var1);

   boolean isIncludeHolders();

   void setIncludeHolders(boolean var1);

   String getPackageBase();

   void setPackageBase(String var1);

   String getFixedPackage();

   void setFixedPackage(String var1);

   String getTargetNamespace(String var1);

   void setTargetNamespace(String var1, String var2);

   Map getTargetNamespaceMap();

   String getTargetNamespacePrefix();

   void setTargetNamespacePrefix(String var1);

   String getFixedTargetNamespace();

   void setFixedTargetNamespace(String var1);

   boolean isReversePackageIntoUrl();

   void setReversePackageIntoUrl(boolean var1);

   boolean isGeneratePublicFields();

   void setGeneratePublicFields(boolean var1);

   void setUseSoapStyleArrays(boolean var1);

   boolean isUseSoapStyleArrays();

   boolean isUseMultiDimensionalSoapArrays();

   void setUseMultiDimensionalSoapArrays(boolean var1);

   boolean isAutoCreateSerials();

   void setAutoCreateSerials(boolean var1);

   String getBeanImplExtension();

   void setBeanImplExtension(String var1);

   String getBeanOutputDirectory();

   void setBeanOutputDirectory(String var1);

   String getDeserializerExtension();

   void setDeserializerExtension(String var1);

   String getSerializerExtension();

   void setSerializerExtension(String var1);

   String getCodecExtension();

   void setCodecExtension(String var1);

   String getSequenceCodecExtension();

   void setSequenceCodecExtension(String var1);

   boolean isIncludeAnnotationAttributes();

   void setIncludeAnnotationAttributes(boolean var1);

   boolean isSchemaElementFormQualified();

   void setSchemaElementFormQualified(boolean var1);

   boolean isSchemaAttributeFormQualified();

   void setSchemaAttributeFormQualified(boolean var1);

   String getXSDAnySerializer();

   void setXSDAnySerializer(String var1);

   String getXSDAnyDeserializer();

   void setXSDAnyDeserializer(String var1);

   String getJavaLanguageNamespaceUri();

   void setJavaLanguageNamespaceUri(String var1);

   boolean isMapJavaInheritance();

   void setMapJavaInheritance(boolean var1);

   boolean isVerbose();

   void setVerbose(boolean var1);

   void setWSICompliance(boolean var1);

   boolean isWSICompliance();

   void setJaxRPCWrappedArrayStyle(boolean var1);

   boolean isJaxRPCWrappedArrayStyle();

   void setCodecAsFinal(boolean var1);

   boolean isCodecAsFinal();
}
