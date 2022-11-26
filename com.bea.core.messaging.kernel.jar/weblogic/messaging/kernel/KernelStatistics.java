package weblogic.messaging.kernel;

public interface KernelStatistics extends Statistics {
   int getUnpagedMessages();

   long getUnpagedBytes();

   int getMessagesPagedOut();

   int getMessagesPagedIn();

   long getBytesPagedOut();

   long getBytesPagedIn();

   long getPagingAllocatedWindowBufferBytes();

   long getPagingAllocatedIoBufferBytes();

   long getPagingPhysicalWriteCount();
}
