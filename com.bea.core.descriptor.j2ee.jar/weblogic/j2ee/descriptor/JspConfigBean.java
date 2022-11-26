package weblogic.j2ee.descriptor;

public interface JspConfigBean {
   TagLibBean[] getTagLibs();

   TagLibBean createTagLib();

   void destroyTagLib(TagLibBean var1);

   JspPropertyGroupBean[] getJspPropertyGroups();

   JspPropertyGroupBean createJspPropertyGroup();

   void destroyJspPropertyGroup(JspPropertyGroupBean var1);

   String getId();

   void setId(String var1);
}
