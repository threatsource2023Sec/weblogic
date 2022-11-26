package weblogic.j2ee.descriptor.wl;

public interface AnnotatedFieldBean {
   String getFieldName();

   void setFieldName(String var1);

   String getInstanceType();

   void setInstanceType(String var1);

   AnnotationInstanceBean[] getAnnotations();

   AnnotationInstanceBean createAnnotation();
}
