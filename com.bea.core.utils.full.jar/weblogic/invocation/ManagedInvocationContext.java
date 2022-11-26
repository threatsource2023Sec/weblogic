package weblogic.invocation;

public interface ManagedInvocationContext extends AutoCloseable {
   void close();
}
