package org.apache.xml.security.stax.ext;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;

public class SecurePart {
   private QName name;
   private boolean generateXPointer;
   private Modifier modifier;
   private String idToSign;
   private String idToReference;
   private String externalReference;
   private String[] transforms;
   private String digestMethod;
   private boolean required;
   private boolean secureEntireRequest;

   public SecurePart(QName name, Modifier modifier) {
      this(name, false, modifier);
   }

   public SecurePart(QName name, Modifier modifier, String[] transforms, String digestMethod) {
      this(name, false, modifier, transforms, digestMethod);
   }

   public SecurePart(QName name, boolean generateXPointer, Modifier modifier) {
      this.required = true;
      this.name = name;
      this.generateXPointer = generateXPointer;
      this.modifier = modifier;
   }

   public SecurePart(QName name, boolean generateXPointer, Modifier modifier, String[] transforms, String digestMethod) {
      this.required = true;
      this.name = name;
      this.generateXPointer = generateXPointer;
      this.modifier = modifier;
      this.transforms = transforms;
      this.digestMethod = digestMethod;
   }

   public SecurePart(QName name, String idToSign, String idToReference, Modifier modifier) {
      this.required = true;
      this.name = name;
      this.idToSign = idToSign;
      this.idToReference = idToReference;
      this.modifier = modifier;
   }

   public SecurePart(String externalReference) {
      this.required = true;
      this.externalReference = externalReference;
   }

   public SecurePart(String externalReference, Modifier modifier) {
      this.required = true;
      this.externalReference = externalReference;
      this.modifier = modifier;
   }

   public SecurePart(String externalReference, String[] transforms, String digestMethod) {
      this.required = true;
      this.externalReference = externalReference;
      this.transforms = transforms;
      this.digestMethod = digestMethod;
   }

   public QName getName() {
      return this.name;
   }

   public void setName(QName name) {
      this.name = name;
   }

   public Modifier getModifier() {
      return this.modifier;
   }

   public void setModifier(Modifier modifier) {
      this.modifier = modifier;
   }

   public String getIdToSign() {
      return this.idToSign;
   }

   public void setIdToSign(String idToSign) {
      this.idToSign = idToSign;
   }

   public String getIdToReference() {
      return this.idToReference;
   }

   public void setIdToReference(String idToReference) {
      this.idToReference = idToReference;
   }

   public boolean isGenerateXPointer() {
      return this.generateXPointer;
   }

   public void setGenerateXPointer(boolean generateXPointer) {
      this.generateXPointer = generateXPointer;
   }

   public String getExternalReference() {
      return this.externalReference;
   }

   public void setExternalReference(String externalReference) {
      this.externalReference = externalReference;
   }

   public String[] getTransforms() {
      return this.transforms;
   }

   public void setTransforms(String[] transforms) {
      this.transforms = transforms;
   }

   public String getDigestMethod() {
      return this.digestMethod;
   }

   public void setDigestMethod(String digestMethod) {
      this.digestMethod = digestMethod;
   }

   public boolean isRequired() {
      return this.required;
   }

   public void setRequired(boolean required) {
      this.required = required;
   }

   public boolean isSecureEntireRequest() {
      return this.secureEntireRequest;
   }

   public void setSecureEntireRequest(boolean secureEntireRequest) {
      this.secureEntireRequest = secureEntireRequest;
   }

   public static enum Modifier {
      Element("http://www.w3.org/2001/04/xmlenc#Element"),
      Content("http://www.w3.org/2001/04/xmlenc#Content");

      private final String modifier;
      private static final Map modifierMap = new HashMap();

      private Modifier(String modifier) {
         this.modifier = modifier;
      }

      public String getModifier() {
         return this.modifier;
      }

      public static Modifier getModifier(String modifier) {
         return (Modifier)modifierMap.get(modifier);
      }

      static {
         Iterator var0 = EnumSet.allOf(Modifier.class).iterator();

         while(var0.hasNext()) {
            Modifier modifier = (Modifier)var0.next();
            modifierMap.put(modifier.getModifier(), modifier);
         }

      }
   }
}
