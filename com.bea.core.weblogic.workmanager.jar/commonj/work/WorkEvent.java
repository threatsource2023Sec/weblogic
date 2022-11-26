package commonj.work;

public interface WorkEvent {
   int WORK_ACCEPTED = 1;
   int WORK_REJECTED = 2;
   int WORK_STARTED = 3;
   int WORK_COMPLETED = 4;

   int getType();

   WorkItem getWorkItem();

   WorkException getException();
}
