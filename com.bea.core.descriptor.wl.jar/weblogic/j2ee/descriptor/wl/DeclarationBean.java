package weblogic.j2ee.descriptor.wl;

public interface DeclarationBean {
   String getUri();

   void setUri(String var1);

   String[] getXpaths();

   void addXpath(String var1);

   void removeXpath(String var1);

   void setXpaths(String[] var1);

   String getDescription();

   void setDescription(String var1);
}
