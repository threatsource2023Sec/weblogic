package weblogic.kernel;

final class ServerExecuteThread extends ExecuteThread {
   private ClassLoader contextClassLoader;
   private ClassLoader defaultContextClassLoader;

   ServerExecuteThread(int which, ExecuteThreadManager etm) {
      super(which, etm);
   }

   ServerExecuteThread(int which, ExecuteThreadManager etm, ThreadGroup tg) {
      super(which, etm, tg);
   }

   protected void init(ExecuteThreadManager e) {
      super.init(e);
      this.defaultContextClassLoader = super.getContextClassLoader();
      this.setContextClassLoader(this.defaultContextClassLoader);
   }

   public ClassLoader getContextClassLoader() {
      return this.contextClassLoader;
   }

   public void setContextClassLoader(ClassLoader cl) {
      this.contextClassLoader = cl != null ? cl : ClassLoader.getSystemClassLoader();
   }

   protected final void reset() {
      super.reset();
      this.setContextClassLoader(this.defaultContextClassLoader);
   }

   public ClassLoader getDefaultContextClassLoader() {
      return this.defaultContextClassLoader;
   }
}
