package com.bea.core.repackaged.aspectj.weaver.bcel;

public class UnwovenClassFileWithThirdPartyManagedBytecode extends UnwovenClassFile {
   IByteCodeProvider provider;

   public UnwovenClassFileWithThirdPartyManagedBytecode(String filename, String classname, IByteCodeProvider provider) {
      super(filename, classname, (byte[])null);
      this.provider = provider;
   }

   public byte[] getBytes() {
      return this.provider.getBytes();
   }

   public interface IByteCodeProvider {
      byte[] getBytes();
   }
}
