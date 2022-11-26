package weblogic.j2ee.descriptor.wl;

public interface ResourceDescriptionBean {
   String getResRefName();

   void setResRefName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);

   String getResourceLink();

   void setResourceLink(String var1);

   DefaultResourcePrincipalBean getDefaultResourcePrincipal();

   DefaultResourcePrincipalBean createDefaultResourcePrincipal(String var1);

   void destroyDefaultResourcePrincipal(DefaultResourcePrincipalBean var1);

   String getId();

   void setId(String var1);
}
