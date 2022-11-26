package javax.jdo;

public enum ObjectState {
   TRANSIENT("transient"),
   TRANSIENT_CLEAN("transient-clean"),
   TRANSIENT_DIRTY("transient-dirty"),
   PERSISTENT_NEW("persistent-new"),
   HOLLOW_PERSISTENT_NONTRANSACTIONAL("hollow/persistent-nontransactional"),
   PERSISTENT_NONTRANSACTIONAL_DIRTY("persistent-nontransactional-dirty"),
   PERSISTENT_CLEAN("persistent-clean"),
   PERSISTENT_DIRTY("persistent-dirty"),
   PERSISTENT_DELETED("persistent-deleted"),
   PERSISTENT_NEW_DELETED("persistent-new-deleted"),
   DETACHED_CLEAN("detached-clean"),
   DETACHED_DIRTY("detached-dirty");

   private final String value;

   private ObjectState(String value) {
      this.value = value;
   }

   public String toString() {
      return this.value;
   }
}
