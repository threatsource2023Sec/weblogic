package org.python.bouncycastle.asn1.icao;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class LDSSecurityObject extends ASN1Object implements ICAOObjectIdentifiers {
   public static final int ub_DataGroups = 16;
   private ASN1Integer version = new ASN1Integer(0L);
   private AlgorithmIdentifier digestAlgorithmIdentifier;
   private DataGroupHash[] datagroupHash;
   private LDSVersionInfo versionInfo;

   public static LDSSecurityObject getInstance(Object var0) {
      if (var0 instanceof LDSSecurityObject) {
         return (LDSSecurityObject)var0;
      } else {
         return var0 != null ? new LDSSecurityObject(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private LDSSecurityObject(ASN1Sequence var1) {
      if (var1 != null && var1.size() != 0) {
         Enumeration var2 = var1.getObjects();
         this.version = ASN1Integer.getInstance(var2.nextElement());
         this.digestAlgorithmIdentifier = AlgorithmIdentifier.getInstance(var2.nextElement());
         ASN1Sequence var3 = ASN1Sequence.getInstance(var2.nextElement());
         if (this.version.getValue().intValue() == 1) {
            this.versionInfo = LDSVersionInfo.getInstance(var2.nextElement());
         }

         this.checkDatagroupHashSeqSize(var3.size());
         this.datagroupHash = new DataGroupHash[var3.size()];

         for(int var4 = 0; var4 < var3.size(); ++var4) {
            this.datagroupHash[var4] = DataGroupHash.getInstance(var3.getObjectAt(var4));
         }

      } else {
         throw new IllegalArgumentException("null or empty sequence passed.");
      }
   }

   public LDSSecurityObject(AlgorithmIdentifier var1, DataGroupHash[] var2) {
      this.version = new ASN1Integer(0L);
      this.digestAlgorithmIdentifier = var1;
      this.datagroupHash = var2;
      this.checkDatagroupHashSeqSize(var2.length);
   }

   public LDSSecurityObject(AlgorithmIdentifier var1, DataGroupHash[] var2, LDSVersionInfo var3) {
      this.version = new ASN1Integer(1L);
      this.digestAlgorithmIdentifier = var1;
      this.datagroupHash = var2;
      this.versionInfo = var3;
      this.checkDatagroupHashSeqSize(var2.length);
   }

   private void checkDatagroupHashSeqSize(int var1) {
      if (var1 < 2 || var1 > 16) {
         throw new IllegalArgumentException("wrong size in DataGroupHashValues : not in (2..16)");
      }
   }

   public int getVersion() {
      return this.version.getValue().intValue();
   }

   public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
      return this.digestAlgorithmIdentifier;
   }

   public DataGroupHash[] getDatagroupHash() {
      return this.datagroupHash;
   }

   public LDSVersionInfo getVersionInfo() {
      return this.versionInfo;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.version);
      var1.add(this.digestAlgorithmIdentifier);
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      for(int var3 = 0; var3 < this.datagroupHash.length; ++var3) {
         var2.add(this.datagroupHash[var3]);
      }

      var1.add(new DERSequence(var2));
      if (this.versionInfo != null) {
         var1.add(this.versionInfo);
      }

      return new DERSequence(var1);
   }
}
