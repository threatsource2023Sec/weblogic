package weblogic.ejb20.cmp.rdbms;

public final class RDBMSException extends Exception {
   private static final long serialVersionUID = -2933697743992982394L;

   public RDBMSException() {
      super("Unknown error in WebLogic RDBMS CMP package.");
   }

   public RDBMSException(String s) {
      super(s);
   }
}
