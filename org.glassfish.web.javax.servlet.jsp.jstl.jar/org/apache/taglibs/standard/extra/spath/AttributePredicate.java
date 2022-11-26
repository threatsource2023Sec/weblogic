package org.apache.taglibs.standard.extra.spath;

import org.xml.sax.Attributes;

public class AttributePredicate extends Predicate {
   private String attribute;
   private String target;

   public AttributePredicate(String attribute, String target) {
      if (attribute == null) {
         throw new IllegalArgumentException("non-null attribute needed");
      } else if (attribute.indexOf(":") != -1) {
         throw new IllegalArgumentException("namespace-qualified attribute names are not currently supported");
      } else {
         this.attribute = attribute;
         if (target == null) {
            throw new IllegalArgumentException("non-null target needed");
         } else {
            this.target = target.substring(1, target.length() - 1);
         }
      }
   }

   public boolean isMatchingAttribute(Attributes a) {
      String attValue = a.getValue("", this.attribute);
      return attValue != null && attValue.equals(this.target);
   }
}
