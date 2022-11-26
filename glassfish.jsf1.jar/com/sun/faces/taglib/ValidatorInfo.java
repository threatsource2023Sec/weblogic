package com.sun.faces.taglib;

import org.xml.sax.Attributes;

public class ValidatorInfo {
   private String nameSpace;
   private String localName;
   private String qName;
   private Attributes attributes;
   private FacesValidator validator;
   private String prefix;
   private String uri;

   public void setNameSpace(String nameSpace) {
      this.nameSpace = nameSpace;
   }

   public String getNameSpace() {
      return this.nameSpace;
   }

   public void setLocalName(String localName) {
      this.localName = localName;
   }

   public String getLocalName() {
      return this.localName;
   }

   public void setQName(String qName) {
      this.qName = qName;
   }

   public String getQName() {
      return this.qName;
   }

   public void setAttributes(Attributes attributes) {
      this.attributes = attributes;
   }

   public Attributes getAttributes() {
      return this.attributes;
   }

   public void setValidator(FacesValidator validator) {
      this.validator = validator;
   }

   public FacesValidator getValidator() {
      return this.validator;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public String getUri() {
      return this.uri;
   }

   public String toString() {
      StringBuffer mesg = new StringBuffer();
      mesg.append("\nValidatorInfo NameSpace: ");
      mesg.append(this.nameSpace);
      mesg.append("\nValidatorInfo LocalName: ");
      mesg.append(this.localName);
      mesg.append("\nValidatorInfo QName: ");
      mesg.append(this.qName);

      for(int i = 0; i < this.attributes.getLength(); ++i) {
         mesg.append("\nValidatorInfo attributes.getQName(");
         mesg.append(i);
         mesg.append("): ");
         mesg.append(this.attributes.getQName(i));
         mesg.append("\nValidatorInfo attributes.getValue(");
         mesg.append(i);
         mesg.append("): ");
         mesg.append(this.attributes.getValue(i));
      }

      mesg.append("\nValidatorInfo prefix: ");
      mesg.append(this.prefix);
      mesg.append("\nValidatorInfo uri: ");
      mesg.append(this.uri);
      return mesg.toString();
   }
}
