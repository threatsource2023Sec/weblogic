package javax.enterprise.deploy.model;

public interface DDBean {
   String getXpath();

   String getText();

   String getId();

   DDBeanRoot getRoot();

   DDBean[] getChildBean(String var1);

   String[] getText(String var1);

   void addXpathListener(String var1, XpathListener var2);

   void removeXpathListener(String var1, XpathListener var2);

   String[] getAttributeNames();

   String getAttributeValue(String var1);
}
