package weblogic.ejb.container.internal;

public class NeedRealContextHandlerError extends Error {
   public static final NeedRealContextHandlerError THE_ONE = new NeedRealContextHandlerError();

   private NeedRealContextHandlerError() {
   }
}
