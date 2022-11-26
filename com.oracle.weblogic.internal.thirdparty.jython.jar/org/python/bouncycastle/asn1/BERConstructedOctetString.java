package org.python.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/** @deprecated */
public class BERConstructedOctetString extends BEROctetString {
   private static final int MAX_LENGTH = 1000;
   private Vector octs;

   private static byte[] toBytes(Vector var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      for(int var2 = 0; var2 != var0.size(); ++var2) {
         try {
            DEROctetString var3 = (DEROctetString)var0.elementAt(var2);
            var1.write(var3.getOctets());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(var0.elementAt(var2).getClass().getName() + " found in input should only contain DEROctetString");
         } catch (IOException var5) {
            throw new IllegalArgumentException("exception converting octets " + var5.toString());
         }
      }

      return var1.toByteArray();
   }

   public BERConstructedOctetString(byte[] var1) {
      super(var1);
   }

   public BERConstructedOctetString(Vector var1) {
      super(toBytes(var1));
      this.octs = var1;
   }

   public BERConstructedOctetString(ASN1Primitive var1) {
      super(toByteArray(var1));
   }

   private static byte[] toByteArray(ASN1Primitive var0) {
      try {
         return var0.getEncoded();
      } catch (IOException var2) {
         throw new IllegalArgumentException("Unable to encode object");
      }
   }

   public BERConstructedOctetString(ASN1Encodable var1) {
      this(var1.toASN1Primitive());
   }

   public byte[] getOctets() {
      return this.string;
   }

   public Enumeration getObjects() {
      return this.octs == null ? this.generateOcts().elements() : this.octs.elements();
   }

   private Vector generateOcts() {
      Vector var1 = new Vector();

      for(int var2 = 0; var2 < this.string.length; var2 += 1000) {
         int var3;
         if (var2 + 1000 > this.string.length) {
            var3 = this.string.length;
         } else {
            var3 = var2 + 1000;
         }

         byte[] var4 = new byte[var3 - var2];
         System.arraycopy(this.string, var2, var4, 0, var4.length);
         var1.addElement(new DEROctetString(var4));
      }

      return var1;
   }

   public static BEROctetString fromSequence(ASN1Sequence var0) {
      Vector var1 = new Vector();
      Enumeration var2 = var0.getObjects();

      while(var2.hasMoreElements()) {
         var1.addElement(var2.nextElement());
      }

      return new BERConstructedOctetString(var1);
   }
}
