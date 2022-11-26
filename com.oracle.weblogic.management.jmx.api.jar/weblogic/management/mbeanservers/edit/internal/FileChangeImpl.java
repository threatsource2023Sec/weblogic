package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.mbeanservers.edit.FileChange;

public class FileChangeImpl implements FileChange {
   private final String operation;
   private final String path;
   private final long currentLastModifiedTime;
   private final long proposedLastModifiedTime;

   public FileChangeImpl(String path, String operation) {
      this(path, operation, 0L, 0L);
   }

   public FileChangeImpl(String path, String operation, long currentModifiedTime, long proposedModifiedTime) {
      this.path = path;
      this.operation = operation;
      this.currentLastModifiedTime = currentModifiedTime;
      this.proposedLastModifiedTime = proposedModifiedTime;
   }

   public String getPath() {
      return new String(this.path);
   }

   public String getOperation() {
      return new String(this.operation);
   }

   public long getCurrentLastModifiedTime() {
      return this.currentLastModifiedTime;
   }

   public long getProposedLastModifiedTime() {
      return this.proposedLastModifiedTime;
   }

   public String toString() {
      return this.operation + "|" + this.path;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object other) {
      if (!(other instanceof FileChangeImpl)) {
         return false;
      } else {
         return this.hashCode() == other.hashCode();
      }
   }
}
