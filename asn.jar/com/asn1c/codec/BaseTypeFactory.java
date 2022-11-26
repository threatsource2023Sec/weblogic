package com.asn1c.codec;

import com.asn1c.core.BitString;
import com.asn1c.core.Bool;
import com.asn1c.core.CharacterString;
import com.asn1c.core.DataValue;
import com.asn1c.core.EmbeddedPDV;
import com.asn1c.core.External;
import com.asn1c.core.GeneralizedTime;
import com.asn1c.core.Identification;
import com.asn1c.core.Int16;
import com.asn1c.core.Int32;
import com.asn1c.core.Int64;
import com.asn1c.core.Int8;
import com.asn1c.core.Null;
import com.asn1c.core.ObjectDescriptor;
import com.asn1c.core.ObjectIdentifier;
import com.asn1c.core.OctetString;
import com.asn1c.core.Open;
import com.asn1c.core.Real32;
import com.asn1c.core.Real64;
import com.asn1c.core.String16;
import com.asn1c.core.String32;
import com.asn1c.core.UTCTime;
import com.asn1c.core.UnitString;

public class BaseTypeFactory implements Factory {
   public String getModuleName() {
      return null;
   }

   public Null createNull(Null var1) {
      return var1;
   }

   public Bool createBool(boolean var1) {
      return new Bool(var1);
   }

   public Int8 createInt8(byte var1) {
      return new Int8(var1);
   }

   public Int16 createInt16(short var1) {
      return new Int16(var1);
   }

   public Int32 createInt32(int var1) {
      return new Int32(var1);
   }

   public Int64 createInt64(long var1) {
      return new Int64(var1);
   }

   public Real32 createReal32(float var1) {
      return new Real32(var1);
   }

   public Real64 createReal64(double var1) {
      return new Real64(var1);
   }

   public BitString createBitString(BitString var1) {
      return var1;
   }

   public OctetString createOctetString(OctetString var1) {
      return var1;
   }

   public String16 createString16(String var1) {
      return new String16(var1);
   }

   public String32 createString32(String32 var1) {
      return var1;
   }

   public GeneralizedTime createGeneralizedTime(GeneralizedTime var1) {
      return var1;
   }

   public UTCTime createUTCTime(UTCTime var1) {
      return var1;
   }

   public final Identification createIdentification(Identification var1) {
      switch (var1.getSelector()) {
         case 0:
            return this.createIdentification(0, this.createObjectIdentifier(var1.getSyntaxesAbstract()), this.createObjectIdentifier(var1.getSyntaxesTransfer()));
         case 1:
            return this.createIdentification(1, this.createObjectIdentifier(var1.getSyntax()));
         case 2:
            return this.createIdentification(2, var1.getPresentationContextId());
         case 3:
            return this.createIdentification(3, var1.getContextNegotiationPresentationContextId(), this.createObjectIdentifier(var1.getContextNegotiationTransferSyntax()));
         case 4:
            return this.createIdentification(4, this.createObjectIdentifier(var1.getTransferSyntax()));
         case 5:
            return this.createIdentification(5);
         default:
            throw new IllegalArgumentException();
      }
   }

   public Identification createIdentification(int var1, ObjectIdentifier var2, ObjectIdentifier var3) {
      return new Identification(var1, var2, var3);
   }

   public Identification createIdentification(int var1, ObjectIdentifier var2) {
      return new Identification(var1, var2);
   }

   public Identification createIdentification(int var1, int var2) {
      return new Identification(var1, var2);
   }

   public Identification createIdentification(int var1, int var2, ObjectIdentifier var3) {
      return new Identification(var1, var2, var3);
   }

   public Identification createIdentification(int var1) {
      return new Identification(var1);
   }

   public final DataValue createDataValue(DataValue var1) {
      switch (var1.getSelector()) {
         case 0:
            return this.createDataValue(0, (Open)this.createOpen(var1.getNotation().getEncoded()));
         case 1:
            return this.createDataValue(1, (BitString)this.createBitString(var1.getEncodedBitString()));
         case 2:
            return this.createDataValue(2, (OctetString)this.createOctetString(var1.getEncodedOctetString()));
         default:
            throw new IllegalArgumentException();
      }
   }

   public DataValue createDataValue(int var1, Open var2) {
      return new DataValue(var1, var2);
   }

   public DataValue createDataValue(int var1, BitString var2) {
      return new DataValue(var1, var2);
   }

   public DataValue createDataValue(int var1, OctetString var2) {
      return new DataValue(var1, var2);
   }

   public final External createExternal(External var1) {
      Identification var2 = this.createIdentification(var1.getIdentification());
      ObjectDescriptor var3;
      if (var1.getDataValueDescriptor() != null) {
         var3 = this.createObjectDescriptor(var1.getDataValueDescriptor().getValue());
      } else {
         var3 = null;
      }

      DataValue var4 = this.createDataValue(var1.getDataValue());
      return this.createExternal(var2, var3, var4);
   }

   public External createExternal(Identification var1, ObjectDescriptor var2, DataValue var3) {
      return new External(var1, var2, var3);
   }

   public final EmbeddedPDV createEmbeddedPDV(EmbeddedPDV var1) {
      Identification var2 = this.createIdentification(var1.getIdentification());
      DataValue var3 = this.createDataValue(var1.getDataValue());
      return this.createEmbeddedPDV(var2, var3);
   }

   public EmbeddedPDV createEmbeddedPDV(Identification var1, DataValue var2) {
      return new EmbeddedPDV(var1, var2);
   }

   public final CharacterString createCharacterString(CharacterString var1) {
      Identification var2 = this.createIdentification(var1.getIdentification());
      DataValue var3 = this.createDataValue(var1.getDataValue());
      return this.createCharacterString(var2, var3);
   }

   public CharacterString createCharacterString(Identification var1, DataValue var2) {
      return new CharacterString(var1, var2);
   }

   public ObjectIdentifier createObjectIdentifier(ObjectIdentifier var1) {
      return var1;
   }

   public ObjectDescriptor createObjectDescriptor(String var1) {
      return new ObjectDescriptor(var1);
   }

   public Open createOpen(UnitString var1) {
      return new Open(var1);
   }
}
