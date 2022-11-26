package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "AlgorithmType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"value"}
)
public class AlgorithmType {
   @XmlValue
   protected String value;
   @XmlAttribute(
      name = "URI",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String uri;
   @XmlAttribute(
      name = "Description",
      required = true
   )
   protected String description;
   @XmlAttribute(
      name = "AlgorithmClass",
      required = true
   )
   protected String algorithmClass;
   @XmlAttribute(
      name = "RequirementLevel",
      required = true
   )
   protected String requirementLevel;
   @XmlAttribute(
      name = "SpecificationURL"
   )
   protected String specificationURL;
   @XmlAttribute(
      name = "JCEProvider"
   )
   protected String jceProvider;
   @XmlAttribute(
      name = "JCEName",
      required = true
   )
   protected String jceName;
   @XmlAttribute(
      name = "KeyLength"
   )
   protected Integer keyLength;
   @XmlAttribute(
      name = "IVLength"
   )
   protected Integer ivLength;
   @XmlAttribute(
      name = "RequiredKey"
   )
   protected String requiredKey;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      this.uri = value;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public String getAlgorithmClass() {
      return this.algorithmClass;
   }

   public void setAlgorithmClass(String value) {
      this.algorithmClass = value;
   }

   public String getRequirementLevel() {
      return this.requirementLevel;
   }

   public void setRequirementLevel(String value) {
      this.requirementLevel = value;
   }

   public String getSpecificationURL() {
      return this.specificationURL;
   }

   public void setSpecificationURL(String value) {
      this.specificationURL = value;
   }

   public String getJCEProvider() {
      return this.jceProvider;
   }

   public void setJCEProvider(String value) {
      this.jceProvider = value;
   }

   public String getJCEName() {
      return this.jceName;
   }

   public void setJCEName(String value) {
      this.jceName = value;
   }

   public Integer getKeyLength() {
      return this.keyLength;
   }

   public void setKeyLength(Integer value) {
      this.keyLength = value;
   }

   public Integer getIVLength() {
      return this.ivLength;
   }

   public void setIVLength(Integer value) {
      this.ivLength = value;
   }

   public String getRequiredKey() {
      return this.requiredKey;
   }

   public void setRequiredKey(String value) {
      this.requiredKey = value;
   }
}
