package weblogic.diagnostics.descriptor;

public interface WLDFEncryptedPropertyBean extends WLDFConfigurationPropertyBean {
   String getEncryptedValue();

   void setEncryptedValue(String var1);

   byte[] getEncryptedValueEncrypted();

   void setEncryptedValueEncrypted(byte[] var1);
}
