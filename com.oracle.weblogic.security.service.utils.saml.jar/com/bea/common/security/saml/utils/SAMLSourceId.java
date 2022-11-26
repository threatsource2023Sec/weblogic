package com.bea.common.security.saml.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import weblogic.utils.Hex;

public final class SAMLSourceId {
   public static final int URL_VALUE = 1;
   public static final int HEX_VALUE = 2;
   public static final int BASE64_VALUE = 3;
   private String sourceSiteURL = null;
   private byte[] sourceId = null;
   private String sourceIdHex = null;
   private String sourceIdBase64 = null;

   public SAMLSourceId(int type, String value) throws IllegalArgumentException {
      if (type != 1 && type != 2 && type != 3) {
         throw new IllegalArgumentException("SAMLSourceId: Invalid input type '" + type + "'");
      } else if (value != null && value.length() != 0) {
         switch (type) {
            case 1:
               this.sourceSiteURL = value;
               this.deriveIdFromURL();
               break;
            case 2:
               value = value.toUpperCase();
               if (value.length() != 40) {
                  throw new IllegalArgumentException("SAMLSourceId: Hex source ID must be 40 hex digits");
               }

               for(int i = 0; i < 40; ++i) {
                  if (Character.digit(value.charAt(i), 16) == -1) {
                     throw new IllegalArgumentException("SAMLSourceId: Hex source ID must be 40 hex digits");
                  }
               }

               this.sourceIdHex = value;
               this.deriveIdFromHex();
               break;
            case 3:
               this.sourceIdBase64 = value;
               this.deriveIdFromBase64();
         }

         if (!this.isValid()) {
            throw new IllegalArgumentException("SAMLSourceId: Invalid source ID value '" + value + "'");
         }
      } else {
         throw new IllegalArgumentException("SAMLSourceId: Invalid source ID value (null or zero-length)");
      }
   }

   public SAMLSourceId(String sourceId) {
      if (sourceId != null && sourceId.length() != 0) {
         if (!sourceId.startsWith("http:") && !sourceId.startsWith("https:")) {
            if (sourceId.length() == 40) {
               sourceId = sourceId.toUpperCase();

               for(int i = 0; i < 40; ++i) {
                  if (Character.digit(sourceId.charAt(i), 16) == -1) {
                     throw new IllegalArgumentException("SAMLSourceId: Hex source ID must be 40 hex digits");
                  }
               }

               this.sourceIdHex = sourceId;
               this.deriveIdFromHex();
            } else {
               this.sourceIdBase64 = sourceId;
               this.deriveIdFromBase64();
            }
         } else {
            this.sourceSiteURL = sourceId;
            this.deriveIdFromURL();
         }

         if (!this.isValid()) {
            throw new IllegalArgumentException("SAMLSourceId: Invalid source ID value '" + sourceId + "'");
         }
      } else {
         throw new IllegalArgumentException("SAMLSourceId: Invalid source ID value (null or zero-length)");
      }
   }

   public SAMLSourceId(byte[] sourceId) throws IllegalArgumentException {
      this.sourceId = sourceId;
      if (!this.isValid()) {
         throw new IllegalArgumentException("SAMLSourceId: Invalid source ID parameter");
      }
   }

   public String getSourceSiteURL() {
      return this.sourceSiteURL;
   }

   public byte[] getSourceIdBytes() {
      return this.sourceId;
   }

   public String getSourceIdHex() {
      if (this.sourceIdHex == null) {
         this.calculateHexId();
      }

      return this.sourceIdHex;
   }

   public String getSourceIdBase64() {
      if (this.sourceIdBase64 == null) {
         this.calculateBase64Id();
      }

      return this.sourceIdBase64;
   }

   private boolean isValid() {
      return this.sourceId != null && this.sourceId.length == 20;
   }

   private void deriveIdFromURL() {
      try {
         new URL(this.sourceSiteURL);
      } catch (MalformedURLException var3) {
         this.sourceId = null;
         return;
      }

      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         this.sourceId = md.digest(this.sourceSiteURL.getBytes());
      } catch (NoSuchAlgorithmException var2) {
         this.sourceId = null;
      }
   }

   private void deriveIdFromHex() {
      byte[] bytes = this.sourceIdHex.getBytes();
      this.sourceId = Hex.fromHexString(bytes, bytes.length);
   }

   private void deriveIdFromBase64() {
      try {
         this.sourceId = SAMLUtil.base64Decode(this.sourceIdBase64);
      } catch (IOException var2) {
         this.sourceId = null;
      }
   }

   private void calculateHexId() {
      this.sourceIdHex = Hex.asHex(this.sourceId, this.sourceId.length, false).toUpperCase();
   }

   private void calculateBase64Id() {
      this.sourceIdBase64 = SAMLUtil.base64Encode(this.sourceId);
   }
}
