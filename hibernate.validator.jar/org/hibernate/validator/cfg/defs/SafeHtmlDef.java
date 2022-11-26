package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.AnnotationDef;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.SafeHtml;

public class SafeHtmlDef extends ConstraintDef {
   public SafeHtmlDef() {
      super(SafeHtml.class);
   }

   private SafeHtmlDef(ConstraintDef original) {
      super(original);
   }

   public SafeHtmlDef whitelistType(SafeHtml.WhiteListType whitelistType) {
      this.addParameter("whitelistType", whitelistType);
      return this;
   }

   public SafeHtmlDef additionalTags(String... additionalTags) {
      this.addParameter("additionalTags", additionalTags);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public SafeHtmlDef additionalTagsWithAttributes(SafeHtml.Tag... additionalTagsWithAttributes) {
      this.addParameter("additionalTagsWithAttributes", additionalTagsWithAttributes);
      return this;
   }

   public SafeHtmlDef additionalTags(TagDef tag, TagDef... furtherTags) {
      this.addAnnotationAsParameter("additionalTagsWithAttributes", tag);
      if (furtherTags != null && furtherTags.length > 0) {
         TagDef[] var3 = furtherTags;
         int var4 = furtherTags.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TagDef tagDef = var3[var5];
            this.addAnnotationAsParameter("additionalTagsWithAttributes", tagDef);
         }
      }

      return this;
   }

   public SafeHtmlDef baseURI(String baseURI) {
      this.addParameter("baseURI", baseURI);
      return this;
   }

   public static class AttributeDef extends AnnotationDef {
      public AttributeDef(String name, String protocol, String... furtherProtocols) {
         super(SafeHtml.Attribute.class);
         this.addParameter("name", name);
         this.addProtocols(protocol, furtherProtocols);
      }

      private void addProtocols(String protocol, String... furtherProtocols) {
         String[] protocols;
         if (furtherProtocols != null) {
            protocols = new String[furtherProtocols.length + 1];
            System.arraycopy(furtherProtocols, 0, protocols, 1, furtherProtocols.length);
            protocols[0] = protocol;
         } else {
            protocols = new String[]{protocol};
         }

         this.addParameter("protocols", protocols);
      }
   }

   public static class TagDef extends AnnotationDef {
      public TagDef(String name) {
         super(SafeHtml.Tag.class);
         this.addParameter("name", name);
      }

      public TagDef attributes(String attribute, String... furtherAttributes) {
         String[] attributes;
         if (furtherAttributes != null && furtherAttributes.length > 0) {
            attributes = new String[furtherAttributes.length + 1];
            System.arraycopy(furtherAttributes, 0, attributes, 1, furtherAttributes.length);
            attributes[0] = attribute;
         } else {
            attributes = new String[]{attribute};
         }

         this.addParameter("attributes", attributes);
         return this;
      }

      public TagDef attributes(AttributeDef attribute, AttributeDef... furtherAttributes) {
         this.addAnnotationAsParameter("attributesWithProtocols", attribute);
         if (furtherAttributes != null && furtherAttributes.length > 0) {
            AttributeDef[] var3 = furtherAttributes;
            int var4 = furtherAttributes.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               AttributeDef attributeDef = var3[var5];
               this.addAnnotationAsParameter("attributesWithProtocols", attributeDef);
            }
         }

         return this;
      }
   }
}
