package weblogic.jndi.internal;

public final class PartitionVisibleRef {
   private Object orig;
   private Object referent;
   private CPAwareSource source;

   public PartitionVisibleRef(Object original, Object obj, CPAwareSource s) {
      this.orig = original;
      this.referent = obj;
      this.source = s;
   }

   public Object getOriginal() {
      return this.orig;
   }

   public Object getReferent() {
      return this.referent;
   }

   public CPAwareSource getSource() {
      return this.source;
   }

   public static enum CPAwareSource {
      METHOD_TRUE,
      METHOD_FALSE,
      INTERFACE,
      ANNOTATION,
      NONE;
   }
}
