package javax.security.auth.message;

public class AuthStatus {
   public static final AuthStatus SUCCESS = new AuthStatus(1);
   public static final AuthStatus FAILURE = new AuthStatus(2);
   public static final AuthStatus SEND_SUCCESS = new AuthStatus(3);
   public static final AuthStatus SEND_FAILURE = new AuthStatus(4);
   public static final AuthStatus SEND_CONTINUE = new AuthStatus(5);
   private final int value;

   private AuthStatus(int value) {
      this.value = value;
   }

   public String toString() {
      switch (this.value) {
         case 1:
            return "AuthStatus.SUCCESS";
         case 2:
            return "AuthStatus.FAILURE";
         case 3:
            return "AuthStatus.SEND_SUCCESS";
         case 4:
            return "AuthStatus.SEND_FAILUR";
         case 5:
            return "AuthStatus.SEND_CONTINUE";
         default:
            return "Unknown AuthStatus";
      }
   }
}
