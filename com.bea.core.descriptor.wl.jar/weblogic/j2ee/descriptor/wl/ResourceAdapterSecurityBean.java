package weblogic.j2ee.descriptor.wl;

public interface ResourceAdapterSecurityBean {
   AnonPrincipalBean getDefaultPrincipalName();

   AnonPrincipalBean createDefaultPrincipalName();

   void destroyDefaultPrincipalName(AnonPrincipalBean var1);

   AnonPrincipalBean getManageAsPrincipalName();

   AnonPrincipalBean createManageAsPrincipalName();

   void destroyManageAsPrincipalName(AnonPrincipalBean var1);

   AnonPrincipalCallerBean getRunAsPrincipalName();

   AnonPrincipalCallerBean createRunAsPrincipalName();

   void destroyRunAsPrincipalName(AnonPrincipalCallerBean var1);

   AnonPrincipalCallerBean getRunWorkAsPrincipalName();

   AnonPrincipalCallerBean createRunWorkAsPrincipalName();

   void destroyRunWorkAsPrincipalName(AnonPrincipalCallerBean var1);

   SecurityWorkContextBean getSecurityWorkContext();

   boolean isSecurityWorkContextSet();

   String getId();

   void setId(String var1);
}
