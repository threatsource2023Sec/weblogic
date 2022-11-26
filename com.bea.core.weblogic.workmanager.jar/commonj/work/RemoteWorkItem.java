package commonj.work;

public interface RemoteWorkItem extends WorkItem {
   void release();

   WorkManager getPinnedWorkManager();
}
