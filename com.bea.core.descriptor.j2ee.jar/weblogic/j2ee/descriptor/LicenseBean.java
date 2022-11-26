package weblogic.j2ee.descriptor;

public interface LicenseBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   boolean isLicenseRequired();

   void setLicenseRequired(boolean var1);

   String getId();

   void setId(String var1);
}
