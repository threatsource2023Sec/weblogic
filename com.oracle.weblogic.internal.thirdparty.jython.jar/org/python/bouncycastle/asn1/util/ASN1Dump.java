package org.python.bouncycastle.asn1.util;

import java.io.IOException;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.python.bouncycastle.asn1.ASN1Boolean;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Enumerated;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.ASN1UTCTime;
import org.python.bouncycastle.asn1.BERApplicationSpecific;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.BERSequence;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.BERTaggedObject;
import org.python.bouncycastle.asn1.DERApplicationSpecific;
import org.python.bouncycastle.asn1.DERBMPString;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERExternal;
import org.python.bouncycastle.asn1.DERGraphicString;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DERPrintableString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERT61String;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.DERVideotexString;
import org.python.bouncycastle.asn1.DERVisibleString;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.encoders.Hex;

public class ASN1Dump {
   private static final String TAB = "    ";
   private static final int SAMPLE_SIZE = 32;

   static void _dumpAsString(String var0, boolean var1, ASN1Primitive var2, StringBuffer var3) {
      String var4 = Strings.lineSeparator();
      Enumeration var5;
      String var6;
      Object var7;
      if (var2 instanceof ASN1Sequence) {
         var5 = ((ASN1Sequence)var2).getObjects();
         var6 = var0 + "    ";
         var3.append(var0);
         if (var2 instanceof BERSequence) {
            var3.append("BER Sequence");
         } else if (var2 instanceof DERSequence) {
            var3.append("DER Sequence");
         } else {
            var3.append("Sequence");
         }

         var3.append(var4);

         while(true) {
            while(var5.hasMoreElements()) {
               var7 = var5.nextElement();
               if (var7 != null && !var7.equals(DERNull.INSTANCE)) {
                  if (var7 instanceof ASN1Primitive) {
                     _dumpAsString(var6, var1, (ASN1Primitive)var7, var3);
                  } else {
                     _dumpAsString(var6, var1, ((ASN1Encodable)var7).toASN1Primitive(), var3);
                  }
               } else {
                  var3.append(var6);
                  var3.append("NULL");
                  var3.append(var4);
               }
            }

            return;
         }
      } else if (var2 instanceof ASN1TaggedObject) {
         String var8 = var0 + "    ";
         var3.append(var0);
         if (var2 instanceof BERTaggedObject) {
            var3.append("BER Tagged [");
         } else {
            var3.append("Tagged [");
         }

         ASN1TaggedObject var10 = (ASN1TaggedObject)var2;
         var3.append(Integer.toString(var10.getTagNo()));
         var3.append(']');
         if (!var10.isExplicit()) {
            var3.append(" IMPLICIT ");
         }

         var3.append(var4);
         if (var10.isEmpty()) {
            var3.append(var8);
            var3.append("EMPTY");
            var3.append(var4);
         } else {
            _dumpAsString(var8, var1, var10.getObject(), var3);
         }
      } else if (var2 instanceof ASN1Set) {
         var5 = ((ASN1Set)var2).getObjects();
         var6 = var0 + "    ";
         var3.append(var0);
         if (var2 instanceof BERSet) {
            var3.append("BER Set");
         } else {
            var3.append("DER Set");
         }

         var3.append(var4);

         while(var5.hasMoreElements()) {
            var7 = var5.nextElement();
            if (var7 == null) {
               var3.append(var6);
               var3.append("NULL");
               var3.append(var4);
            } else if (var7 instanceof ASN1Primitive) {
               _dumpAsString(var6, var1, (ASN1Primitive)var7, var3);
            } else {
               _dumpAsString(var6, var1, ((ASN1Encodable)var7).toASN1Primitive(), var3);
            }
         }
      } else if (var2 instanceof ASN1OctetString) {
         ASN1OctetString var9 = (ASN1OctetString)var2;
         if (var2 instanceof BEROctetString) {
            var3.append(var0 + "BER Constructed Octet String" + "[" + var9.getOctets().length + "] ");
         } else {
            var3.append(var0 + "DER Octet String" + "[" + var9.getOctets().length + "] ");
         }

         if (var1) {
            var3.append(dumpBinaryDataAsString(var0, var9.getOctets()));
         } else {
            var3.append(var4);
         }
      } else if (var2 instanceof ASN1ObjectIdentifier) {
         var3.append(var0 + "ObjectIdentifier(" + ((ASN1ObjectIdentifier)var2).getId() + ")" + var4);
      } else if (var2 instanceof ASN1Boolean) {
         var3.append(var0 + "Boolean(" + ((ASN1Boolean)var2).isTrue() + ")" + var4);
      } else if (var2 instanceof ASN1Integer) {
         var3.append(var0 + "Integer(" + ((ASN1Integer)var2).getValue() + ")" + var4);
      } else if (var2 instanceof DERBitString) {
         DERBitString var11 = (DERBitString)var2;
         var3.append(var0 + "DER Bit String" + "[" + var11.getBytes().length + ", " + var11.getPadBits() + "] ");
         if (var1) {
            var3.append(dumpBinaryDataAsString(var0, var11.getBytes()));
         } else {
            var3.append(var4);
         }
      } else if (var2 instanceof DERIA5String) {
         var3.append(var0 + "IA5String(" + ((DERIA5String)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERUTF8String) {
         var3.append(var0 + "UTF8String(" + ((DERUTF8String)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERPrintableString) {
         var3.append(var0 + "PrintableString(" + ((DERPrintableString)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERVisibleString) {
         var3.append(var0 + "VisibleString(" + ((DERVisibleString)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERBMPString) {
         var3.append(var0 + "BMPString(" + ((DERBMPString)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERT61String) {
         var3.append(var0 + "T61String(" + ((DERT61String)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERGraphicString) {
         var3.append(var0 + "GraphicString(" + ((DERGraphicString)var2).getString() + ") " + var4);
      } else if (var2 instanceof DERVideotexString) {
         var3.append(var0 + "VideotexString(" + ((DERVideotexString)var2).getString() + ") " + var4);
      } else if (var2 instanceof ASN1UTCTime) {
         var3.append(var0 + "UTCTime(" + ((ASN1UTCTime)var2).getTime() + ") " + var4);
      } else if (var2 instanceof ASN1GeneralizedTime) {
         var3.append(var0 + "GeneralizedTime(" + ((ASN1GeneralizedTime)var2).getTime() + ") " + var4);
      } else if (var2 instanceof BERApplicationSpecific) {
         var3.append(outputApplicationSpecific("BER", var0, var1, var2, var4));
      } else if (var2 instanceof DERApplicationSpecific) {
         var3.append(outputApplicationSpecific("DER", var0, var1, var2, var4));
      } else if (var2 instanceof ASN1Enumerated) {
         ASN1Enumerated var12 = (ASN1Enumerated)var2;
         var3.append(var0 + "DER Enumerated(" + var12.getValue() + ")" + var4);
      } else if (var2 instanceof DERExternal) {
         DERExternal var13 = (DERExternal)var2;
         var3.append(var0 + "External " + var4);
         var6 = var0 + "    ";
         if (var13.getDirectReference() != null) {
            var3.append(var6 + "Direct Reference: " + var13.getDirectReference().getId() + var4);
         }

         if (var13.getIndirectReference() != null) {
            var3.append(var6 + "Indirect Reference: " + var13.getIndirectReference().toString() + var4);
         }

         if (var13.getDataValueDescriptor() != null) {
            _dumpAsString(var6, var1, var13.getDataValueDescriptor(), var3);
         }

         var3.append(var6 + "Encoding: " + var13.getEncoding() + var4);
         _dumpAsString(var6, var1, var13.getExternalContent(), var3);
      } else {
         var3.append(var0 + var2.toString() + var4);
      }

   }

   private static String outputApplicationSpecific(String var0, String var1, boolean var2, ASN1Primitive var3, String var4) {
      ASN1ApplicationSpecific var5 = ASN1ApplicationSpecific.getInstance(var3);
      StringBuffer var6 = new StringBuffer();
      if (var5.isConstructed()) {
         try {
            ASN1Sequence var7 = ASN1Sequence.getInstance(var5.getObject(16));
            var6.append(var1 + var0 + " ApplicationSpecific[" + var5.getApplicationTag() + "]" + var4);
            Enumeration var8 = var7.getObjects();

            while(var8.hasMoreElements()) {
               _dumpAsString(var1 + "    ", var2, (ASN1Primitive)var8.nextElement(), var6);
            }
         } catch (IOException var9) {
            var6.append(var9);
         }

         return var6.toString();
      } else {
         return var1 + var0 + " ApplicationSpecific[" + var5.getApplicationTag() + "] (" + Strings.fromByteArray(Hex.encode(var5.getContents())) + ")" + var4;
      }
   }

   public static String dumpAsString(Object var0) {
      return dumpAsString(var0, false);
   }

   public static String dumpAsString(Object var0, boolean var1) {
      StringBuffer var2 = new StringBuffer();
      if (var0 instanceof ASN1Primitive) {
         _dumpAsString("", var1, (ASN1Primitive)var0, var2);
      } else {
         if (!(var0 instanceof ASN1Encodable)) {
            return "unknown object type " + var0.toString();
         }

         _dumpAsString("", var1, ((ASN1Encodable)var0).toASN1Primitive(), var2);
      }

      return var2.toString();
   }

   private static String dumpBinaryDataAsString(String var0, byte[] var1) {
      String var2 = Strings.lineSeparator();
      StringBuffer var3 = new StringBuffer();
      var0 = var0 + "    ";
      var3.append(var2);

      for(int var4 = 0; var4 < var1.length; var4 += 32) {
         if (var1.length - var4 > 32) {
            var3.append(var0);
            var3.append(Strings.fromByteArray(Hex.encode(var1, var4, 32)));
            var3.append("    ");
            var3.append(calculateAscString(var1, var4, 32));
            var3.append(var2);
         } else {
            var3.append(var0);
            var3.append(Strings.fromByteArray(Hex.encode(var1, var4, var1.length - var4)));

            for(int var5 = var1.length - var4; var5 != 32; ++var5) {
               var3.append("  ");
            }

            var3.append("    ");
            var3.append(calculateAscString(var1, var4, var1.length - var4));
            var3.append(var2);
         }
      }

      return var3.toString();
   }

   private static String calculateAscString(byte[] var0, int var1, int var2) {
      StringBuffer var3 = new StringBuffer();

      for(int var4 = var1; var4 != var1 + var2; ++var4) {
         if (var0[var4] >= 32 && var0[var4] <= 126) {
            var3.append((char)var0[var4]);
         }
      }

      return var3.toString();
   }
}
