package org.python.bouncycastle.cert.crmf;

public interface EncryptedValuePadder {
   byte[] getPaddedData(byte[] var1);

   byte[] getUnpaddedData(byte[] var1);
}
