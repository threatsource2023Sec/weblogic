package weblogic.ejb.container.cmp.rdbms;

import java.util.ArrayList;
import java.util.List;

public final class RelationshipCaching {
   private String cachingName;
   private final List cachingElements = new ArrayList();

   public void setCachingName(String cachingName) {
      this.cachingName = cachingName;
   }

   public String getCachingName() {
      return this.cachingName;
   }

   public void addCachingElement(CachingElement cachingElement) {
      this.cachingElements.add(cachingElement);
   }

   public List getCachingElements() {
      return this.cachingElements;
   }

   public String toString() {
      return "[RelationshipCaching name:" + this.cachingName + " cachingElements:" + this.cachingElements + "]";
   }

   public static class CachingElement {
      private String cmrField;
      private String groupName;
      private final List cachingElements = new ArrayList();

      public void setCmrField(String cmrField) {
         this.cmrField = cmrField;
      }

      public String getCmrField() {
         return this.cmrField;
      }

      public void setGroupName(String groupName) {
         this.groupName = groupName;
      }

      public String getGroupName() {
         return this.groupName;
      }

      public void addCachingElement(CachingElement cachingElement) {
         this.cachingElements.add(cachingElement);
      }

      public List getCachingElements() {
         return this.cachingElements;
      }

      public String toString() {
         return "[CachingElement cmrField:" + this.cmrField + " group name:" + this.groupName + " cachingElements:" + this.cachingElements + "]";
      }
   }
}
