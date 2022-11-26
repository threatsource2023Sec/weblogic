package org.glassfish.admin.rest.model;

public class LinkInfo implements Comparable {
   private MethodInfo method;
   private String title;
   private String relationship;
   private String uri;
   private String description;
   private boolean linkToUri;

   LinkInfo(MethodInfo method, String rel, String uri, String title, String desc) {
      this.method = method;
      this.relationship = rel;
      this.uri = uri;
      this.title = title;
      this.description = desc;
   }

   public MethodInfo getMethod() {
      return this.method;
   }

   public String getTitle() {
      return this.title;
   }

   public String getRelationship() {
      return this.relationship;
   }

   public String getUri() {
      return this.uri;
   }

   public String getDescription() {
      return this.description;
   }

   public int compareTo(LinkInfo o) {
      int order = this.compareStrings(this.relationship, o.relationship);
      if (order == 0) {
         order = this.compareStrings(this.title, o.title);
      }

      return order;
   }

   private int compareStrings(String s1, String s2) {
      if (s1 != null) {
         return s2 != null ? s1.compareTo(s2) : 1;
      } else {
         return s2 != null ? -1 : 0;
      }
   }

   public String toString() {
      return "LinkInfo [relationship=" + this.getRelationship() + ", title=" + this.getTitle() + ", uri=" + this.getUri() + ", description=" + this.getDescription() + "]";
   }
}
