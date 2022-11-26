package weblogic.j2ee.descriptor.wl;

public interface AnnotationInstanceBean {
   String getAnnotationClassName();

   void setAnnotationClassName(String var1);

   MemberBean[] getMembers();

   MemberBean createMember();

   ArrayMemberBean[] getArrayMembers();

   ArrayMemberBean createArrayMember();

   NestedAnnotationBean[] getNestedAnnotations();

   NestedAnnotationBean createNestedAnnotation();

   NestedAnnotationArrayBean[] getNestedAnnotationArrays();

   NestedAnnotationArrayBean createNestedAnnotationArray();

   String getShortDescription();
}
