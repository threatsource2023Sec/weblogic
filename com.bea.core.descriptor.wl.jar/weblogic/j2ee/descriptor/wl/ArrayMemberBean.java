package weblogic.j2ee.descriptor.wl;

public interface ArrayMemberBean {
   String getMemberName();

   void setMemberName(String var1);

   String[] getMemberValues();

   void setMemberValues(String[] var1);

   String[] getOverrideValues();

   void setOverrideValues(String[] var1);

   boolean getRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   String[] getCleartextOverrideValues();

   void setCleartextOverrideValues(String[] var1);

   String getSecuredOverrideValue();

   void setSecuredOverrideValue(String var1);

   byte[] getSecuredOverrideValueEncrypted();

   void setSecuredOverrideValueEncrypted(byte[] var1);

   String getShortDescription();
}
