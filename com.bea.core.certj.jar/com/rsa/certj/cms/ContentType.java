package com.rsa.certj.cms;

import java.util.HashMap;
import java.util.Map;

/** @deprecated */
public final class ContentType {
   private static final Map a = new HashMap();
   /** @deprecated */
   public static final ContentType AUTHENTICATED_DATA;
   /** @deprecated */
   public static final ContentType CONTENT_INFO;
   /** @deprecated */
   public static final ContentType DATA;
   /** @deprecated */
   public static final ContentType DIGESTED_DATA;
   /** @deprecated */
   public static final ContentType ENCRYPTED_DATA;
   /** @deprecated */
   public static final ContentType ENVELOPED_DATA;
   /** @deprecated */
   public static final ContentType SIGNED_DATA;
   /** @deprecated */
   public static final ContentType TIMESTAMP_INFO;
   private final com.rsa.jsafe.cms.ContentType b;

   private ContentType(com.rsa.jsafe.cms.ContentType var1) {
      this.b = var1;
      a.put(var1.getIdentifier(), this);
   }

   /** @deprecated */
   public String getIdentifier() {
      return this.b.getIdentifier();
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof ContentType) ? false : this.getIdentifier().equals(((ContentType)var1).getIdentifier());
      }
   }

   /** @deprecated */
   public int hashCode() {
      return this.getIdentifier().hashCode();
   }

   /** @deprecated */
   public String toString() {
      return this.getIdentifier();
   }

   com.rsa.jsafe.cms.ContentType a() {
      return this.b;
   }

   /** @deprecated */
   public static ContentType getContentType(String var0) {
      ContentType var1 = (ContentType)a.get(var0);
      return var1 != null ? var1 : new ContentType(com.rsa.jsafe.cms.ContentType.getContentType(var0));
   }

   static {
      AUTHENTICATED_DATA = new ContentType(com.rsa.jsafe.cms.ContentType.AUTHENTICATED_DATA);
      CONTENT_INFO = new ContentType(com.rsa.jsafe.cms.ContentType.CONTENT_INFO);
      DATA = new ContentType(com.rsa.jsafe.cms.ContentType.DATA);
      DIGESTED_DATA = new ContentType(com.rsa.jsafe.cms.ContentType.DIGESTED_DATA);
      ENCRYPTED_DATA = new ContentType(com.rsa.jsafe.cms.ContentType.ENCRYPTED_DATA);
      ENVELOPED_DATA = new ContentType(com.rsa.jsafe.cms.ContentType.ENVELOPED_DATA);
      SIGNED_DATA = new ContentType(com.rsa.jsafe.cms.ContentType.SIGNED_DATA);
      TIMESTAMP_INFO = new ContentType(com.rsa.jsafe.cms.ContentType.TIMESTAMP_INFO);
   }
}
