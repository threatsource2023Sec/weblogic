package weblogic.j2ee.descriptor.wl;

public interface AnnotationManifestBean {
   AnnotatedClassBean[] getAnnotatedClasses();

   AnnotatedClassBean createAnnotatedClass();

   AnnotationDefinitionBean[] getAnnotationDefinitions();

   AnnotationDefinitionBean createAnnotationDefinition();

   EnumDefinitionBean[] getEnumDefinitions();

   EnumDefinitionBean createEnumDefinition();

   long getUpdateCount();

   void setUpdateCount(long var1);

   String getVersion();

   void setVersion(String var1);
}
