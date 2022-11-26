package weblogic.utils.classloaders;

public class SourceWithMetadata extends DelegateSource {
   private final Object metadata;

   public SourceWithMetadata(Source s, Object metadata) {
      super(s);
      this.metadata = metadata;
   }

   public Object getMetadata() {
      return this.metadata;
   }
}
