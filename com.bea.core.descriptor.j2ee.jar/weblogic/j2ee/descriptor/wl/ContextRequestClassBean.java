package weblogic.j2ee.descriptor.wl;

public interface ContextRequestClassBean {
   String getName();

   void setName(String var1);

   ContextCaseBean[] getContextCases();

   ContextCaseBean createContextCase();

   void destroyContextCase(ContextCaseBean var1);

   String getId();

   void setId(String var1);
}
