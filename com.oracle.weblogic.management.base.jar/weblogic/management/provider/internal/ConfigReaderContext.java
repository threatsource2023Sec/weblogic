package weblogic.management.provider.internal;

public class ConfigReaderContext {
   private boolean streamModified;

   public boolean isStreamModifed() {
      return this.streamModified;
   }

   public void setStreamModified(boolean modified) {
      this.streamModified = modified;
   }
}
