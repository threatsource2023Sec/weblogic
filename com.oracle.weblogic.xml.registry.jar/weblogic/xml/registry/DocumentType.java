package weblogic.xml.registry;

class DocumentType {
   private String rootTag;
   private String publicId;
   private String systemId;

   public DocumentType(String pubId, String sysId, String root) {
      this.publicId = pubId == null ? null : (pubId.trim().length() == 0 ? null : pubId);
      this.systemId = sysId == null ? null : (sysId.trim().length() == 0 ? null : sysId);
      this.rootTag = root == null ? null : (root.trim().length() == 0 ? null : root);
   }

   public String toString() {
      return "[PublicID=" + this.publicId + ", SystemID=" + this.systemId + ", Root=" + this.rootTag + "]";
   }

   public boolean equals(Object obj) {
      DocumentType other = null;

      try {
         other = (DocumentType)obj;
      } catch (ClassCastException var4) {
         return false;
      }

      return this.nullableStringEquals(this.publicId, other.publicId) && this.nullableStringEquals(this.systemId, other.systemId) && this.nullableStringEquals(this.rootTag, other.rootTag);
   }

   public int hashCode() {
      int hash = 0;
      if (this.publicId != null) {
         hash ^= this.publicId.hashCode();
      }

      if (this.systemId != null) {
         hash ^= this.systemId.hashCode();
      }

      if (this.rootTag != null) {
         hash ^= this.rootTag.hashCode();
      }

      return hash;
   }

   private boolean nullableStringEquals(String s1, String s2) {
      if (s1 == null) {
         return s2 == null;
      } else {
         return s1.equals(s2);
      }
   }
}
