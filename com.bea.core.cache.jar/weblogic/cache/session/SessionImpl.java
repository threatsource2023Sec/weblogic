package weblogic.cache.session;

import weblogic.cache.CacheMap;

public class SessionImpl extends AbstractWorkspaceMapAdapter implements Session {
   private Workspace _workspace;
   private boolean _closed;

   public SessionImpl(CacheMap delegate) {
      super(delegate);
   }

   public Workspace getWorkspace() {
      this.assertOpen();
      if (this._workspace == null) {
         this._workspace = new WorkspaceImpl();
      }

      return this._workspace;
   }

   public void flush() {
      this.assertOpen();
      if (this._workspace != null && !this._workspace.isEmpty()) {
         super.prepare(new WorkspaceFlushAction(this._workspace)).run();
         this._workspace.reset();
      }

   }

   public void cancel() {
      this.assertOpen();
      if (this._workspace != null) {
         this._workspace.reset();
      }

   }

   public boolean isClosed() {
      return this._closed;
   }

   public void close() {
      this.flush();
      this._closed = true;
   }

   protected void assertOpen() {
      if (this._closed) {
         throw new IllegalStateException();
      }
   }
}
