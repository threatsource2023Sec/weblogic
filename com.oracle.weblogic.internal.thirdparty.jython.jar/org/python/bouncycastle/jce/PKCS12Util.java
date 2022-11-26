package org.python.bouncycastle.jce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DEROutputStream;
import org.python.bouncycastle.asn1.pkcs.ContentInfo;
import org.python.bouncycastle.asn1.pkcs.MacData;
import org.python.bouncycastle.asn1.pkcs.Pfx;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.DigestInfo;

public class PKCS12Util {
   public static byte[] convertToDefiniteLength(byte[] var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      DEROutputStream var2 = new DEROutputStream(var1);
      Pfx var3 = Pfx.getInstance(var0);
      var1.reset();
      var2.writeObject(var3);
      return var1.toByteArray();
   }

   public static byte[] convertToDefiniteLength(byte[] var0, char[] var1, String var2) throws IOException {
      Pfx var3 = Pfx.getInstance(var0);
      ContentInfo var4 = var3.getAuthSafe();
      ASN1OctetString var5 = ASN1OctetString.getInstance(var4.getContent());
      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      DEROutputStream var7 = new DEROutputStream(var6);
      ASN1InputStream var8 = new ASN1InputStream(var5.getOctets());
      ASN1Primitive var9 = var8.readObject();
      var7.writeObject(var9);
      var4 = new ContentInfo(var4.getContentType(), new DEROctetString(var6.toByteArray()));
      MacData var10 = var3.getMacData();

      try {
         int var11 = var10.getIterationCount().intValue();
         byte[] var12 = ASN1OctetString.getInstance(var4.getContent()).getOctets();
         byte[] var13 = calculatePbeMac(var10.getMac().getAlgorithmId().getAlgorithm(), var10.getSalt(), var11, var1, var12, var2);
         AlgorithmIdentifier var14 = new AlgorithmIdentifier(var10.getMac().getAlgorithmId().getAlgorithm(), DERNull.INSTANCE);
         DigestInfo var15 = new DigestInfo(var14, var13);
         var10 = new MacData(var15, var10.getSalt(), var11);
      } catch (Exception var16) {
         throw new IOException("error constructing MAC: " + var16.toString());
      }

      var3 = new Pfx(var4, var10);
      var6.reset();
      var7.writeObject(var3);
      return var6.toByteArray();
   }

   private static byte[] calculatePbeMac(ASN1ObjectIdentifier var0, byte[] var1, int var2, char[] var3, byte[] var4, String var5) throws Exception {
      SecretKeyFactory var6 = SecretKeyFactory.getInstance(var0.getId(), var5);
      PBEParameterSpec var7 = new PBEParameterSpec(var1, var2);
      PBEKeySpec var8 = new PBEKeySpec(var3);
      SecretKey var9 = var6.generateSecret(var8);
      Mac var10 = Mac.getInstance(var0.getId(), var5);
      var10.init(var9, var7);
      var10.update(var4);
      return var10.doFinal();
   }
}
