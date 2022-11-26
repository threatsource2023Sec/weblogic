package weblogic.descriptor;

public interface SecurityService {
   boolean isEncrypted(byte[] var1) throws DescriptorException;

   byte[] encrypt(String var1) throws DescriptorException;

   String decrypt(byte[] var1) throws DescriptorException;
}
