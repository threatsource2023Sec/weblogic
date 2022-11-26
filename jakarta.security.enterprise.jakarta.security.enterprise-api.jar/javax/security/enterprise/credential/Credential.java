package javax.security.enterprise.credential;

public interface Credential {
   default boolean isCleared() {
      return false;
   }

   default void clear() {
   }

   default boolean isValid() {
      return true;
   }
}
