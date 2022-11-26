package weblogic.j2ee.descriptor.wl;

public interface NestedAnnotationArrayBean {
   String getMemberName();

   void setMemberName(String var1);

   AnnotationInstanceBean[] getAnnotations();

   AnnotationInstanceBean createAnnotation();
}
