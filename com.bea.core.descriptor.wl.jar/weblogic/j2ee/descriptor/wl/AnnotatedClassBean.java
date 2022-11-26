package weblogic.j2ee.descriptor.wl;

public interface AnnotatedClassBean {
   String getAnnotatedClassName();

   void setAnnotatedClassName(String var1);

   String getComponentType();

   void setComponentType(String var1);

   AnnotationInstanceBean[] getAnnotations();

   AnnotationInstanceBean createAnnotation();

   AnnotatedFieldBean[] getFields();

   AnnotatedFieldBean createField();

   AnnotatedMethodBean[] getMethods();

   AnnotatedMethodBean createMethod();

   String getShortDescription();
}
