package weblogic.j2ee.descriptor.wl;

public interface MemberDefinitionBean {
   String getMemberName();

   void setMemberName(String var1);

   boolean getIsArray();

   void setIsArray(boolean var1);

   boolean getIsRequired();

   void setIsRequired(boolean var1);

   String getAnnotationRef();

   void setAnnotationRef(String var1);

   EnumRefBean getEnumRef();

   EnumRefBean createEnumRef();

   SimpleTypeDefinitionBean getSimpleTypeDefinition();

   SimpleTypeDefinitionBean createSimpleTypeDefinition();
}
