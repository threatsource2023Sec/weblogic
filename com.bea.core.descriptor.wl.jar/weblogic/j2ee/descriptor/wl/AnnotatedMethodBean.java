package weblogic.j2ee.descriptor.wl;

public interface AnnotatedMethodBean {
   String getMethodKey();

   void setMethodKey(String var1);

   String getMethodName();

   void setMethodName(String var1);

   AnnotationInstanceBean[] getAnnotations();

   AnnotationInstanceBean createAnnotation();

   String[] getParamterTypes();

   void setParamterTypes(String[] var1);
}
