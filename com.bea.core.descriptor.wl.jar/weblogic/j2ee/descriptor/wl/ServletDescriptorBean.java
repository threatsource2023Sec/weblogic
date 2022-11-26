package weblogic.j2ee.descriptor.wl;

public interface ServletDescriptorBean {
   String getServletName();

   void setServletName(String var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);

   String getInitAsPrincipalName();

   void setInitAsPrincipalName(String var1);

   String getDestroyAsPrincipalName();

   void setDestroyAsPrincipalName(String var1);

   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   String getId();

   void setId(String var1);
}
