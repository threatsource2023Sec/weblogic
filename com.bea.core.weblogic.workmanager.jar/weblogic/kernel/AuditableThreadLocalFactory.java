package weblogic.kernel;

public class AuditableThreadLocalFactory {
   public static AuditableThreadLocal createThreadLocal() {
      return createThreadLocal(new ThreadLocalInitialValue());
   }

   public static AuditableThreadLocal createThreadLocal(ThreadLocalInitialValue init) {
      return (AuditableThreadLocal)(KernelStatus.isServer() && !FinalThreadLocal.isFinalized() ? new FinalThreadLocal(init) : new ResettableThreadLocal(init));
   }
}
