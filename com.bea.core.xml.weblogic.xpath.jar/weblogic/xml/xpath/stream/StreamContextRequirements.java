package weblogic.xml.xpath.stream;

public final class StreamContextRequirements {
   private boolean mIsRecordingRequired = false;
   private boolean mIsNamespaceAxisUsed = false;
   private boolean mIsAncestorAxisUsed = false;

   void union(StreamContextRequirements sr) {
      if (!this.mIsNamespaceAxisUsed) {
         this.mIsNamespaceAxisUsed = sr.mIsNamespaceAxisUsed;
      }

      if (!this.mIsAncestorAxisUsed) {
         this.mIsAncestorAxisUsed = sr.mIsAncestorAxisUsed;
      }

      if (!this.mIsRecordingRequired) {
         this.mIsRecordingRequired = sr.mIsRecordingRequired;
      }

   }

   boolean isNamespaceAxisUsed() {
      return this.mIsNamespaceAxisUsed;
   }

   boolean isAncestorAxisUsed() {
      return this.mIsAncestorAxisUsed;
   }

   boolean isRecordingRequired() {
      return this.mIsRecordingRequired;
   }

   void setNamespaceAxisUsed(boolean b) {
      this.mIsNamespaceAxisUsed = b;
   }

   void setAncestorAxisUsed(boolean b) {
      this.mIsAncestorAxisUsed = b;
   }

   void setRecordingRequired(boolean b) {
      this.mIsRecordingRequired = b;
   }
}
