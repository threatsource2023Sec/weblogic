package weblogic.descriptor.codegen;

public interface CodeGenOptionsBean {
   void setSourceDir(String var1);

   void setTemplateDir(String var1);

   void setSourcePath(String var1);

   void setTargetDirectory(String var1);

   void setPackage(String var1);

   void setTemplateName(String var1);

   void setDefaultTemplate(String var1);

   void setTargetExtension(String var1);

   void setSourceExtension(String var1);

   void setGenerateToCustom(boolean var1);

   void setBeanFactory(String var1);

   void setSources(String[] var1);

   void setExcludes(String[] var1);

   void setExternalDir(String var1);

   void setVerbose(boolean var1);
}
