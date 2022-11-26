package javax.security.enterprise.credential;

public abstract class AbstractClearableCredential implements Credential {
   private volatile boolean cleared = false;

   public final boolean isCleared() {
      return this.cleared;
   }

   protected final void setCleared() {
      this.cleared = true;
   }

   public final void clear() {
      this.clearCredential();
      this.setCleared();
   }

   protected abstract void clearCredential();
}
