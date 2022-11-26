package weblogic.j2ee.descriptor.wl;

public interface MemberBean {
   String getMemberName();

   void setMemberName(String var1);

   String getMemberValue();

   void setMemberValue(String var1);

   String getOverrideValue();

   void setOverrideValue(String var1);

   boolean getRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   String getCleartextOverrideValue();

   void setCleartextOverrideValue(String var1);

   String getSecuredOverrideValue();

   void setSecuredOverrideValue(String var1);

   byte[] getSecuredOverrideValueEncrypted();

   void setSecuredOverrideValueEncrypted(byte[] var1);

   String getShortDescription();
}
