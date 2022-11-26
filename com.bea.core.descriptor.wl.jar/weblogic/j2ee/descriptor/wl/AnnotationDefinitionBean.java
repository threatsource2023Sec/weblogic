package weblogic.j2ee.descriptor.wl;

public interface AnnotationDefinitionBean {
   String getAnnotationClassName();

   void setAnnotationClassName(String var1);

   MembershipConstraintBean getMembershipConstraint();

   MembershipConstraintBean createMembershipConstraint();

   boolean getAllowedOnDeclaration();

   void setAllowedOnDeclaration(boolean var1);

   MemberDefinitionBean[] getMemberDefinitions();

   MemberDefinitionBean createMemberDefinition();
}
