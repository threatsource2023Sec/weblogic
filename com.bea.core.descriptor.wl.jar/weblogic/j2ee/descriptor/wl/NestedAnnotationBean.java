package weblogic.j2ee.descriptor.wl;

public interface NestedAnnotationBean {
   String getMemberName();

   void setMemberName(String var1);

   AnnotationInstanceBean getAnnotation();

   AnnotationInstanceBean createAnnotation();
}
