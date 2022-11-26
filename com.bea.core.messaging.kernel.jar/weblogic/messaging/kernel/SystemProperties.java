package weblogic.messaging.kernel;

public interface SystemProperties {
   String IN_LINE_BODY_THRESHOLD = "weblogic.messaging.kernel.persistence.InLineBodyThreshold";
   String PAGE_IN_ON_BOOT = "weblogic.messaging.kernel.persistence.PageInOnBoot";
   String ALWAYS_USE_PAGING_STORE = "weblogic.messaging.kernel.paging.AlwaysUsePagingStore";
   String PAGING_BATCH_SIZE = "weblogic.messaging.kernel.paging.BatchSize";
   String PAGED_MESSAGE_THRESHOLD = "weblogic.messaging.kernel.paging.PagedMessageThreshold";
   String TOPIC_PACK_SIZE = "weblogic.messaging.kernel.TopicPackSize";
   String DIRECT_TM_POOL_LIMIT = "weblogic.messaging.kernel.DirectTimerManagerPoolLimit";
   String LIMITED_TM_POOL_LIMIT = "weblogic.messaging.kernel.LimitedTimerManagerPoolLimit";
   String SAF_STORE_UPGRADE_MODE = "weblogic.saf.StoreUpgradeMode";
   String OLD_ONLY = "OldOnly";
   String IGNORE_OLD = "IgnoreOld";
   String FORCE_OLD = "ForceOld";
   String DELETE_BAD_MSGHEADERS = "weblogic.messaging.kernel.DeleteBadMessageHeaders";
   String DELETE_BAD_2PCRECORDS = "weblogic.messaging.kernel.DeleteBad2PCRecords";
   String DELETE_BAD_INSTRTEST = "weblogic.messaging.kernel.DeleteBadInstrTest";
}
