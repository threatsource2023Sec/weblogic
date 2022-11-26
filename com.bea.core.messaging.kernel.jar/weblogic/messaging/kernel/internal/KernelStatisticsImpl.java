package weblogic.messaging.kernel.internal;

import weblogic.messaging.kernel.KernelStatistics;

public class KernelStatisticsImpl extends StatisticsImpl implements KernelStatistics {
   private PagingImpl paging;

   KernelStatisticsImpl(String name, KernelImpl kernel, StatisticsImpl parent) {
      super(name, kernel, parent);
      this.isKernelStats = true;
   }

   void setPaging(PagingImpl paging) {
      this.paging = paging;
   }

   public boolean isKernelStatistics() {
      return this.isKernelStats;
   }

   public int getUnpagedMessages() {
      return this.paging == null ? 0 : this.paging.getNumMessages();
   }

   public long getUnpagedBytes() {
      return this.paging == null ? 0L : this.paging.getNumBytes();
   }

   public int getMessagesPagedOut() {
      return this.paging == null ? 0 : this.paging.getMessagesPagedOut();
   }

   public int getMessagesPagedIn() {
      return this.paging == null ? 0 : this.paging.getMessagesPagedIn();
   }

   public long getBytesPagedOut() {
      return this.paging == null ? 0L : this.paging.getBytesPagedOut();
   }

   public long getBytesPagedIn() {
      return this.paging == null ? 0L : this.paging.getBytesPagedIn();
   }

   public long getPagingAllocatedWindowBufferBytes() {
      return this.paging.storeStats.getMappedBufferBytes();
   }

   public long getPagingAllocatedIoBufferBytes() {
      return this.paging.storeStats.getIOBufferBytes();
   }

   public long getPagingPhysicalWriteCount() {
      return this.paging.storeStats.getPhysicalWriteCount();
   }
}
