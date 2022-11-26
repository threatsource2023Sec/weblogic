package org.apache.xml.security.binding.xmlenc11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.xml.security.binding.xmldsig.DigestMethodType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ConcatKDFParamsType",
   namespace = "http://www.w3.org/2009/xmlenc11#",
   propOrder = {"digestMethod"}
)
public class ConcatKDFParamsType {
   @XmlElement(
      name = "DigestMethod",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected DigestMethodType digestMethod;
   @XmlAttribute(
      name = "AlgorithmID"
   )
   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
   @XmlSchemaType(
      name = "hexBinary"
   )
   protected byte[] algorithmID;
   @XmlAttribute(
      name = "PartyUInfo"
   )
   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
   @XmlSchemaType(
      name = "hexBinary"
   )
   protected byte[] partyUInfo;
   @XmlAttribute(
      name = "PartyVInfo"
   )
   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
   @XmlSchemaType(
      name = "hexBinary"
   )
   protected byte[] partyVInfo;
   @XmlAttribute(
      name = "SuppPubInfo"
   )
   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
   @XmlSchemaType(
      name = "hexBinary"
   )
   protected byte[] suppPubInfo;
   @XmlAttribute(
      name = "SuppPrivInfo"
   )
   @XmlJavaTypeAdapter(HexBinaryAdapter.class)
   @XmlSchemaType(
      name = "hexBinary"
   )
   protected byte[] suppPrivInfo;

   public DigestMethodType getDigestMethod() {
      return this.digestMethod;
   }

   public void setDigestMethod(DigestMethodType value) {
      this.digestMethod = value;
   }

   public byte[] getAlgorithmID() {
      return this.algorithmID;
   }

   public void setAlgorithmID(byte[] value) {
      this.algorithmID = value;
   }

   public byte[] getPartyUInfo() {
      return this.partyUInfo;
   }

   public void setPartyUInfo(byte[] value) {
      this.partyUInfo = value;
   }

   public byte[] getPartyVInfo() {
      return this.partyVInfo;
   }

   public void setPartyVInfo(byte[] value) {
      this.partyVInfo = value;
   }

   public byte[] getSuppPubInfo() {
      return this.suppPubInfo;
   }

   public void setSuppPubInfo(byte[] value) {
      this.suppPubInfo = value;
   }

   public byte[] getSuppPrivInfo() {
      return this.suppPrivInfo;
   }

   public void setSuppPrivInfo(byte[] value) {
      this.suppPrivInfo = value;
   }
}
