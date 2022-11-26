package org.apache.taglibs.standard.extra.spath;

import java.util.List;

public class Step {
   private boolean depthUnlimited;
   private String name;
   private List predicates;
   private String uri;
   private String localPart;

   public Step(boolean depthUnlimited, String name, List predicates) {
      if (name == null) {
         throw new IllegalArgumentException("non-null name required");
      } else {
         this.depthUnlimited = depthUnlimited;
         this.name = name;
         this.predicates = predicates;
      }
   }

   public boolean isMatchingName(String uri, String localPart) {
      if (localPart == null) {
         throw new IllegalArgumentException("need non-null localPart");
      } else {
         if (uri != null && uri.equals("")) {
            uri = null;
         }

         if (this.localPart == null && this.uri == null) {
            this.parseStepName();
         }

         if (this.uri == null && this.localPart.equals("*")) {
            return true;
         } else if (uri == null && this.uri == null && localPart.equals(this.localPart)) {
            return true;
         } else {
            if (uri != null && this.uri != null && uri.equals(this.uri)) {
               if (localPart.equals(this.localPart)) {
                  return true;
               }

               if (this.localPart.equals("*")) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean isDepthUnlimited() {
      return this.depthUnlimited;
   }

   public String getName() {
      return this.name;
   }

   public List getPredicates() {
      return this.predicates;
   }

   private void parseStepName() {
      int colonIndex = this.name.indexOf(":");
      String prefix;
      if (colonIndex == -1) {
         prefix = null;
         this.localPart = this.name;
      } else {
         prefix = this.name.substring(0, colonIndex);
         this.localPart = this.name.substring(colonIndex + 1);
      }

      this.uri = this.mapPrefix(prefix);
   }

   private String mapPrefix(String prefix) {
      if (prefix == null) {
         return null;
      } else {
         throw new IllegalArgumentException("unknown prefix '" + prefix + "'");
      }
   }
}
