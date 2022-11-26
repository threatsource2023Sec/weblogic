package weblogic.common.resourcepool;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.common.ResourceException;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public abstract class ResourcePoolImpl implements ResourcePool {
   private static final int OP_RESERVE = 10;
   private static final int OP_RELEASE = 11;
   private static final int OP_CREATE = 12;
   private static final int OP_TEST_AVL = 13;
   private static final int OP_POOL_SHUTDOWN = 14;
   private static final int OP_POOL_PURGE = 15;
   private static final int OP_POOL_RESUME = 16;
   private static final int OP_POOL_SUSPEND = 17;
   private static final int DEFAULT_SCAN_UNIT = 5;
   protected String name;
   private static int poolCount = 0;
   protected volatile int state = 100;
   private boolean suspending = false;
   protected IPooledResourceLinkedList available;
   protected Deque dead;
   protected IPooledResourceSet reserved;
   protected PooledResourceFactory resFactory;
   protected int initialCapacity = 0;
   protected int minCapacity = -1;
   protected int maxCapacity = 1;
   protected int capacityIncrement = 1;
   protected boolean matchSupported = true;
   protected boolean returnNewlyCreatedResource = false;
   protected boolean testOnReserve = false;
   protected boolean testOnRelease = false;
   protected boolean testOnCreate = false;
   protected int inactiveSecs = 0;
   protected int testSecs = 0;
   protected int shrinkSecs = 900;
   protected int timeElapsedAfterShrinking = 0;
   protected boolean allowShrinking = true;
   protected int reserveTimeoutSecs = -1;
   protected int retryIntervalSecs = 0;
   protected int maintSecs = 0;
   private int maxUnavl = 0;
   private int scanSecs = 5;
   private int waitersMax = Integer.MAX_VALUE;
   private int resvRetryMax = Integer.MAX_VALUE;
   private boolean ignoreInUseResources = true;
   private boolean quietMessages = false;
   private ResourcePoolMaintanenceTask poolMaintTask = null;
   private CheckHangTask checkHangTask = null;
   private Timer poolTimer;
   private Timer hangTimer;
   private TimerManager poolTimerManager;
   private Object pmtLockObject;
   private static final int PMT_STATUS_RUNNABLE = 0;
   private static final int PMT_STATUS_RUNNING = 1;
   private static final int PMT_STATUS_STOP = 2;
   private static final int PMT_STATUS_STOPPED = 3;
   private int pmtStatus = 0;
   private int DEFAULT_SYNC_WAIT = 2;
   private long createTime = 0L;
   private long createCount = 0L;
   private int waiters = 0;
   private int waitersHigh = 0;
   private long waitersTotal = 0L;
   private long waitersSuccessTotal = 0L;
   private long waitersFailureTotal = 0L;
   private int waitSecsHigh;
   private int refreshCount = 0;
   private int refreshOKCount = 0;
   private int leaked;
   private int created;
   protected int destroyed;
   private int numIdleDetected = 0;
   private long resvReqCnt;
   private long resvReqFailCnt;
   private int resourcesDestroyedByShrinkingCount = 0;
   private int currCapacityHigh = 0;
   protected int beingProcessed;
   private int poolHistorySize = 0;
   private int poolHistCollected = 0;
   private int[] poolUseHistory;
   private int oldestUsageCount = 0;
   protected volatile int lastUsageCount = 0;
   private int runningAverage = 0;
   private final Object poolHistoryLock = new Object();
   private CommonTextTextFormatter textFormatter;
   private static final int SLEEP_COUNT = 100;
   private static final int TEST_TIME_HISTORY_LENGTH = 10;
   private static final double TYPICAL_TIME_FACTOR = 10.0;
   public int max_test_wait_millis = 10000;
   private boolean gotCommandlineProps = false;
   private boolean ignoreExceptionsWhileCreatingInitialCapacity = false;
   private long lastShrinkTime = 0L;
   private float shrinkFactor = 1.0F;
   private int harvestSecs = 0;
   private Group defaultPrimaryGroup = new Group("DEFAULT_GROUP_ID");
   private AtomicInteger num_reservers = new AtomicInteger();
   public String reason_for_suspension = " Not explicitly suspended.";
   public String most_recent_connection_failure_info = " There has been no DBMS connect failure yet.";
   public String most_recent_successful_connect_info = " There has been no successful connect to the DBMS yet.";
   private int preTestOnReleaseCount = 0;
   private int highest_num_unavailable;
   private int beingCreated = 0;
   protected int resetFailCount = 0;
   protected volatile boolean disabledUponResetFailure = false;
   protected volatile boolean resumeInternallyAllowed = true;
   protected int countToDisablePool = 0;
   protected int countToFlushPool = 0;
   protected Object forSynchronizingSelfDisabling = new Object();
   private int beingTestedCount = 0;
   private boolean interrupt_testing_idle_connections = false;
   private Table groups = HashBasedTable.create();

   private void getCommandLineProps() {
      if (!this.gotCommandlineProps) {
         this.gotCommandlineProps = true;
         if (System.getProperty("weblogic.jdbc.ignoreExceptionsWhileCreatingInitialCapacity") != null) {
            this.ignoreExceptionsWhileCreatingInitialCapacity = true;
         }

         String max_test_wait_str = System.getProperty("weblogic.resourcepool.max_test_wait_secs");
         if (max_test_wait_str != null) {
            try {
               this.max_test_wait_millis = Integer.parseInt(max_test_wait_str) * 1000;
               if (this.max_test_wait_millis < 0) {
                  this.max_test_wait_millis = 0;
               }
            } catch (Exception var3) {
               if (ResourcePoolUtil.doLog()) {
                  ResourcePoolUtil.log("RP(" + this.name + ") cannot convert max_test_wait_secs '" + max_test_wait_str + "' to an integer. Will default to " + this.max_test_wait_millis / 1000);
               }
            }
         }

      }
   }

   protected boolean isStartupCreateRetryDisabled() {
      return false;
   }

   protected int getStartInitialCapacity() {
      return this.initialCapacity;
   }

   public void start(Object initInfo) throws ResourceException {
      if (this.state != 102) {
         if (this.state != 100) {
            throw new ObjectLifeCycleException("Attempted to start the pool " + this.name + " when it is currently not in SHUTDOWN state. (" + this.getDerivedState() + ")");
         } else {
            this.initParameters((Properties)initInfo);
            this.validateParameters();
            this.getCommandLineProps();
            this.createInternalObjs();
            this.resFactory = this.initPooledResourceFactory((Properties)initInfo);
            if (this.ignoreExceptionsWhileCreatingInitialCapacity) {
               PooledResourceInfo[] resInfoList = new PooledResourceInfo[this.initialCapacity];
               Arrays.fill(resInfoList, (Object)null);

               try {
                  this.makeResources(this.initialCapacity, resInfoList, true);
               } catch (Exception var7) {
               }
            } else {
               int startInitialCapacity = this.getStartInitialCapacity();
               boolean setupCreateRetry = !this.isStartupCreateRetryDisabled();
               PooledResourceInfo[] resInfoList = new PooledResourceInfo[startInitialCapacity];
               Arrays.fill(resInfoList, (Object)null);

               try {
                  this.makeResources(startInitialCapacity, resInfoList, setupCreateRetry);
               } catch (ResourceException var6) {
                  this.destroyAllResources(14);
                  throw var6;
               }
            }

            this.startPoolMaintenance();
            this.state = 102;
            this.reason_for_suspension = " In initial suspension state during start().";
         }
      }
   }

   public void resume() throws ResourceException {
      CommonLogger.logResumingPoolByExternalCommand(this.name);
      this.resumeInternallyAllowed = true;
      this.resumeInternal();
   }

   private void resumeInternal() throws ResourceException {
      if (this.state != 101) {
         if (this.state != 102) {
            throw new ObjectLifeCycleException("Attempted to resume the pool " + this.name + " when it is currently not in SUSPENDED state");
         } else {
            this.resumeOrSuspendResources(16, true, 0);
            this.enableAllGroups();
            this.state = 101;
         }
      }
   }

   public void suspend(boolean shuttingDown) throws ResourceException {
      this.suspend(shuttingDown, 0);
   }

   public void suspend(boolean shuttingDown, int operationSecs) throws ResourceException {
      CommonLogger.logSuspendingPoolByExternalCommand(this.name);
      this.reason_for_suspension = " It was suspended at " + new Date() + " because of an external call to suspend.";
      this.disablePool(false, shuttingDown, operationSecs);
   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      CommonLogger.logForceSuspendingPoolByExternalCommand(this.name);
      this.reason_for_suspension = " It was suspended at " + new Date() + " because of an external call to force-suspend.";
      this.disablePool(true, shuttingDown, 0);
   }

   public void shutdown() throws ResourceException {
      if (this.state != 100) {
         if (this.state != 102) {
            throw new ObjectLifeCycleException("Attempted to shutdown the pool " + this.name + " when it is currently not in SUSPENDED state (" + this.getDerivedState() + ")");
         } else {
            this.stopPoolMaintenance();
            this.destroyAllResources(14);
            this.disableAllGroups();
            this.state = 100;
         }
      }
   }

   public PooledResource reserveResource(PooledResourceInfo resInfo) throws ResourceException {
      return this.reserveResource(this.reserveTimeoutSecs, resInfo);
   }

   public PooledResource reserveResource(int waitSecs, PooledResourceInfo resInfo) throws ResourceException {
      return this.reserveResource(waitSecs, resInfo, false, true);
   }

   protected PooledResource reserveResource(PooledResourceInfo resInfo, boolean internalUse) throws ResourceException {
      return this.reserveResource(this.reserveTimeoutSecs, resInfo, internalUse, true);
   }

   protected PooledResource reserveResource(int waitSecs, PooledResourceInfo resInfo, boolean internalUse, boolean createNew) throws ResourceException {
      if (createNew) {
         ++this.resvReqCnt;
      }

      PooledResource res;
      try {
         res = this.reserveResourceInternal(waitSecs, resInfo, internalUse, createNew);
         if (res != null) {
            if (!createNew) {
               ++this.resvReqCnt;
            }

            Collection groups = res.getGroups();
            if (groups != null) {
               Iterator var7 = groups.iterator();

               while(var7.hasNext()) {
                  ResourcePoolGroup group = (ResourcePoolGroup)var7.next();
                  ((Group)group).incrementReservedCount();
               }
            }
         }
      } catch (ResourceException var12) {
         ++this.resvReqFailCnt;
         if (this.getProfiler() != null && this.getProfiler().isResourceReserveFailProfilingEnabled()) {
            this.getProfiler().addResvFailData(var12.toString() + "\n" + StackTraceUtils.throwable2StackTrace(new Exception()));
         }

         throw var12;
      } finally {
         if (this.getProfiler() != null) {
            this.getProfiler().deleteWaitData();
         }

      }

      if (res != null) {
         res.setup();
         if (this.getProfiler() != null && this.getProfiler().isResourceUsageProfilingEnabled()) {
            this.getProfiler().addUsageData(res);
         }
      }

      return res;
   }

   protected boolean returnNewlyCreatedResource(PooledResourceInfo resourceInfo) {
      return this.returnNewlyCreatedResource;
   }

   private PooledResource reserveResourceInternal(int waitSecs, PooledResourceInfo resInfo, boolean internalUse, boolean createNew) throws ResourceException {
      if (!internalUse && this.state == 102) {
         throw new ResourceDisabledException("Pool " + this.name + " is suspended. Cannot allocate resources to applications." + this.reason_for_suspension + this.most_recent_connection_failure_info + this.most_recent_successful_connect_info);
      } else if (!internalUse && this.state != 101) {
         throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications.");
      } else if (this.state == 100) {
         throw new ResourceException("Pool " + this.name + " is Shut Down, cannot allocate resources to applications..");
      } else {
         PooledResource res = null;

         try {
            this.num_reservers.incrementAndGet();
            synchronized(this) {
               if (this.state != 101 && !internalUse) {
                  throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications..");
               }

               if (this.matchSupported) {
                  res = this.matchResource(resInfo);
               }

               if (res != null) {
                  res.setUsed(true);
                  this.reserved.add(res);
               }
            }

            PooledResource var39;
            if (res != null) {
               var39 = this.checkAndReturnResource(res, 10);
               return var39;
            } else if (!createNew) {
               var39 = null;
               return var39;
            } else {
               int numToMake = this.capacityIncrement;
               PooledResourceInfo[] infoList = new PooledResourceInfo[numToMake];
               Arrays.fill(infoList, resInfo);
               if (this.returnNewlyCreatedResource(resInfo)) {
                  Vector successfullyCreatedResource = new Vector();

                  try {
                     this.makeResources(numToMake, infoList, successfullyCreatedResource, false, false, true);
                  } catch (Exception var34) {
                     if (var34 instanceof ResourceException) {
                        throw (ResourceException)var34;
                     }

                     throw new ResourceDeadException(StackTraceUtils.throwable2StackTrace(var34));
                  }

                  synchronized(this) {
                     if (this.state != 101 && !internalUse) {
                        if (successfullyCreatedResource.size() > 0) {
                           Object[] created_array = successfullyCreatedResource.toArray();

                           for(int i = 0; i < successfullyCreatedResource.size(); ++i) {
                              ((PooledResource)((PooledResource)created_array[i])).forceDestroy();
                           }
                        }

                        throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications..");
                     }

                     if (successfullyCreatedResource.size() > 0) {
                        res = (PooledResource)successfullyCreatedResource.firstElement();
                        this.beingCreated -= successfullyCreatedResource.size();
                        if (this.beingCreated < 0) {
                           this.beingCreated = 0;
                        }
                     } else {
                        if (this.matchSupported) {
                           res = this.matchResource(resInfo);
                        }

                        if (res == null) {
                           res = this.refreshOldestAvailResource(resInfo);
                        }
                     }

                     if (res != null) {
                        res.setUsed(true);
                        this.reserved.add(res);
                     }
                  }

                  if (res != null) {
                     if (resInfo != null && res.getPooledResourceInfo() == null) {
                        res.setPooledResourceInfo(resInfo);
                        this.refreshResource(res);
                     }

                     PooledResource var42 = this.checkAndReturnResource(res, 10);
                     return var42;
                  }
               } else {
                  try {
                     this.makeResources(numToMake, infoList, false);
                  } catch (ResourceException var33) {
                     int lcv = this.parseExceptionMsg(var33.getMessage());
                     if (lcv == 0) {
                        throw new ResourceDeadException(var33.getMessage(), var33);
                     }

                     throw var33;
                  }
               }

               boolean gotResource = false;
               boolean waitTimedout = false;
               long whenWeStartedWaiting = 0L;
               int currTotalWaitSecs = 0;
               long remainingWaitMillis = 0L;
               boolean setWaiters = false;
               int resvRetryCount = this.resvRetryMax;
               if (this.reserveTimeoutSecs < waitSecs) {
                  waitSecs = this.reserveTimeoutSecs;
               }

               remainingWaitMillis = (long)waitSecs * 1000L;
               if (waitSecs < 0) {
                  remainingWaitMillis = 0L;
               } else if (waitSecs == 0) {
                  remainingWaitMillis = Long.MAX_VALUE;
                  waitSecs = Integer.MAX_VALUE;
               }

               if (this.getProfiler() != null && this.getProfiler().isResourceReserveWaitProfilingEnabled()) {
                  this.getProfiler().addWaitData();
               }

               synchronized(this) {
                  if (this.state != 101 && !internalUse) {
                     throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications..");
                  }

                  while(true) {
                     while(!gotResource && !waitTimedout && resvRetryCount > 0) {
                        --resvRetryCount;
                        if (this.matchSupported) {
                           res = this.matchResource(resInfo);
                        }

                        if (res == null) {
                           res = this.refreshOldestAvailResource(resInfo);
                        }

                        if (res != null) {
                           res.setUsed(true);
                           this.reserved.add(res);
                           gotResource = true;
                           if (currTotalWaitSecs > this.waitSecsHigh) {
                              this.waitSecsHigh = currTotalWaitSecs;
                           }
                        } else {
                           if (!setWaiters) {
                              if (this.waiters == this.waitersMax) {
                                 throw new ResourceLimitException("Configured maximum limit of (" + this.waitersMax + ") on number of threads allowed to wait for a resource reached for pool " + this.name);
                              }

                              ++this.waiters;
                              ++this.waitersTotal;
                              if (this.waiters > this.waitersHigh) {
                                 ++this.waitersHigh;
                              }

                              setWaiters = true;
                           }

                           if (waitSecs > -1 && currTotalWaitSecs < waitSecs && remainingWaitMillis > 0L) {
                              if (whenWeStartedWaiting == 0L) {
                                 whenWeStartedWaiting = System.currentTimeMillis();
                              }

                              long preWait = System.currentTimeMillis();
                              long thisWaitDuration = 0L;

                              try {
                                 this.wait(remainingWaitMillis);
                              } catch (InterruptedException var32) {
                              }

                              thisWaitDuration = System.currentTimeMillis() - preWait;
                              currTotalWaitSecs = (int)((System.currentTimeMillis() - whenWeStartedWaiting) / 1000L);
                              if (currTotalWaitSecs > this.waitSecsHigh) {
                                 this.waitSecsHigh = currTotalWaitSecs;
                              }

                              remainingWaitMillis -= thisWaitDuration;
                           } else {
                              waitTimedout = true;
                           }
                        }
                     }

                     if (setWaiters) {
                        --this.waiters;
                        if (gotResource) {
                           ++this.waitersSuccessTotal;
                        } else {
                           ++this.waitersFailureTotal;
                        }
                     }
                     break;
                  }
               }

               if (!gotResource) {
                  if (!internalUse && this.state != 101) {
                     throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications..");
                  } else if (waitTimedout) {
                     if (waitSecs >= 0) {
                        throw new ResourceLimitException("No resources currently available in pool " + this.name + " to allocate to applications, please increase the size of the pool and retry..");
                     } else {
                        throw new ResourceUnavailableException("No resources currently available in pool " + this.name + " to allocate to applications. Either specify a time period to wait for resources to become available, or increase the size of the pool and retry..");
                     }
                  } else if (resvRetryCount == 0) {
                     throw new ResourceLimitException("Exhausted specified reserve retry count (" + this.resvRetryMax + ") for pool " + this.name + ", unable to allocate resource to application...");
                  } else {
                     throw new ResourceException("Unable to allocate resources from Pool " + this.name + "  to applications..");
                  }
               } else {
                  if (resInfo != null && res != null && res.getPooledResourceInfo() == null) {
                     res.setPooledResourceInfo(resInfo);
                     this.refreshResource(res);
                  }

                  PooledResource var17 = this.checkAndReturnResource(res, 10);
                  return var17;
               }
            }
         } finally {
            this.num_reservers.decrementAndGet();
         }
      }
   }

   public void releaseResource(PooledResource res) throws ResourceException {
      if (this.state == 100) {
         if (this.name != null) {
            CommonLogger.logWarnShutdownRelease(this.name, res.toString());
         }

      } else {
         if (this.getProfiler() != null) {
            this.getProfiler().deleteUsageData(res);
         }

         res.cleanup();
         boolean currTestOnRelease = this.testOnRelease;
         boolean found;
         synchronized(this) {
            found = this.reserved.remove(res);
            if (found) {
               res.setUsed(false);
               res.setResourceCleanupHandler((ResourceCleanupHandler)null);
               if (res.needDestroyAfterRelease()) {
                  this.destroyResource(res);
                  return;
               }

               if (!currTestOnRelease) {
                  if (!this.available.contains(res)) {
                     ReserveReleaseInterceptor rri = this.getReserveReleaseInterceptor();
                     boolean makeAvailable = true;
                     if (rri != null) {
                        try {
                           rri.onRelease(res);
                        } catch (ResourceException var9) {
                           makeAvailable = false;
                        }
                     }

                     if (makeAvailable) {
                        this.available.addFirst(res);
                     }
                  }

                  if (this.waiters > 0) {
                     this.notify();
                  }
               } else {
                  ++this.preTestOnReleaseCount;
               }
            }
         }

         if (found) {
            this.checkAndReturnResource(res, 11, currTestOnRelease);
         } else if (ResourcePoolUtil.doLog()) {
            ResourcePoolUtil.log("   RP(" + this.name + "): Unknown resource \"" + res.toString() + "\" being released to pool");
         }

      }
   }

   public void createResources(int count, PooledResourceInfo[] infoList) throws ResourceException {
      this.makeResources(count, infoList, false);
   }

   public void createResources(int count, PooledResourceInfo[] infoList, List created) throws ResourceException {
      this.makeResources(count, infoList, created, false, true);
   }

   public PooledResource matchResource(PooledResourceInfo info) throws ResourceException {
      synchronized(this) {
         return info == null ? (PooledResource)this.available.removeFirst() : this.available.removeMatching(info);
      }
   }

   public PooledResource[] getResources() {
      int lcv = 0;
      synchronized(this) {
         if (this.available != null && this.reserved != null) {
            PooledResource[] retList = new PooledResource[this.available.size() + this.reserved.size()];

            for(ListIterator iter = this.available.listIterator(0); iter.hasNext(); retList[lcv++] = (PooledResource)iter.next()) {
            }

            for(Iterator iter2 = this.reserved.iterator(); iter2.hasNext(); retList[lcv++] = (PooledResource)iter2.next()) {
            }

            return retList;
         } else {
            return null;
         }
      }
   }

   public String getDerivedState() {
      if (this.state == 100) {
         return "Shutdown";
      } else if (this.state == 103) {
         return "Unhealthy";
      } else if (this.state == 102) {
         return "Suspended";
      } else if (this.state == 101) {
         if (this.getCurrCapacity() > 0 && this.getCurrCapacity() == this.dead.size()) {
            return "Unhealthy";
         } else {
            return this.maxCapacity > 0 && this.maxCapacity == this.reserved.size() && this.getNumWaiters() > 0 ? "Overloaded" : "Running";
         }
      } else {
         return "Unknown";
      }
   }

   public String getState() {
      if (this.state == 100) {
         return "Shutdown";
      } else if (this.state == 102) {
         return "Suspended";
      } else if (this.state == 101) {
         return "Running";
      } else {
         return this.state == 103 ? "Unhealthy" : "Unknown";
      }
   }

   public int getNumLeaked() {
      return this.leaked;
   }

   public void incrementNumLeaked() {
      ++this.leaked;
   }

   public int getNumFailuresToRefresh() {
      return this.refreshCount - this.refreshOKCount;
   }

   public int getCreationDelayTime() {
      int result = 0;
      if (this.createCount > 0L) {
         result = (int)(this.createTime / this.createCount);
      }

      return result;
   }

   public int getNumWaiters() {
      return this.waiters;
   }

   public int getHighestNumWaiters() {
      return this.waitersHigh;
   }

   public long getTotalWaitingForConnection() {
      return this.waitersTotal;
   }

   public long getTotalWaitingForConnectionSuccess() {
      return this.waitersSuccessTotal;
   }

   public long getTotalWaitingForConnectionFailure() {
      return this.waitersFailureTotal;
   }

   public int getHighestWaitSeconds() {
      return this.waitSecsHigh;
   }

   public int getNumReserved() {
      return this.reserved.size();
   }

   public int getHighestNumReserved() {
      return this.reserved.sizeHigh();
   }

   public int getNumAvailable() {
      return this.available.size();
   }

   public int getHighestNumAvailable() {
      return this.available.sizeHigh();
   }

   public int getNumUnavailable() {
      return this.getCurrCapacity() - this.available.size();
   }

   public int getHighestNumUnavailable() {
      if (this.reserved.sizeHigh() > this.highest_num_unavailable) {
         this.highest_num_unavailable = this.reserved.sizeHigh();
      }

      if (this.getNumUnavailable() > this.highest_num_unavailable) {
         this.highest_num_unavailable = this.getNumUnavailable();
      }

      return this.highest_num_unavailable;
   }

   public int getTotalNumAllocated() {
      return this.created + this.refreshOKCount;
   }

   public int getTotalNumDestroyed() {
      return this.destroyed;
   }

   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   public int getMinCapacity() {
      return this.minCapacity < 0 ? this.initialCapacity : this.minCapacity;
   }

   public int getCurrCapacity() {
      int ret = false;
      synchronized(this) {
         int ret = this.available.size() + this.reserved.size() + this.beingTestedCount + this.preTestOnReleaseCount + this.beingProcessed;
         if (ret > this.currCapacityHigh) {
            this.currCapacityHigh = ret;
         }

         return ret;
      }
   }

   public int getHighestCurrCapacity() {
      return this.currCapacityHigh;
   }

   public int getAverageReserved() {
      return this.runningAverage;
   }

   public long getNumReserveRequests() {
      return this.resvReqCnt;
   }

   public long getNumFailedReserveRequests() {
      return this.resvReqFailCnt;
   }

   public void shrink() throws ResourceException {
      this.doShrink(true);
   }

   public void refresh() throws ResourceException {
      int failCnt = 0;
      int currCnt;
      synchronized(this) {
         currCnt = this.getCurrCapacity();
         failCnt += this.refreshResources();
         failCnt += this.recreateDeadResources();
      }

      if (failCnt > 0) {
         throw new ResourceException("Refresh operation was partially successful, (" + failCnt + ") out of (" + currCnt + ") resources could not be refreshed.");
      }
   }

   public void setMaximumCapacity(int newVal) throws ResourceException, IllegalArgumentException {
      if (this.maxCapacity != newVal) {
         PooledResource[] resources = null;
         int numToDestroy = 0;
         if (newVal < this.initialCapacity) {
            throw new IllegalArgumentException("Cannot set Maximum Capacity of Pool " + this.name + " to (" + newVal + "), this value is lower than the configured Initial Capacity of the pool (" + this.initialCapacity + ").");
         } else if (newVal < this.getMinCapacity()) {
            throw new IllegalArgumentException("Cannot set Maximum Capacity of Pool " + this.name + " to (" + newVal + "), this value is lower than the configured Minimum Capacity of the pool (" + this.getMinCapacity() + ").");
         } else {
            synchronized(this) {
               if (this.getCurrCapacity() > newVal) {
                  numToDestroy = this.getCurrCapacity() - newVal;
               }

               if (numToDestroy > this.available.size() + this.dead.size()) {
                  throw new IllegalArgumentException("Cannot set Maximum Capacity of Pool " + this.name + " to (" + newVal + "), as more than that number of resources in the pool are currently in use..");
               }

               this.maxCapacity = newVal;
               if (numToDestroy > 0) {
                  resources = this.removeResources(numToDestroy);
               }
            }

            if (resources != null) {
               for(int lcv = 0; lcv < resources.length; ++lcv) {
                  this.destroyResource(resources[lcv]);
               }
            }

         }
      }
   }

   public void setMinimumCapacity(int newVal) throws ResourceException, IllegalArgumentException {
      if (this.minCapacity != newVal) {
         int numToMake = 0;
         if (newVal > this.maxCapacity) {
            throw new IllegalArgumentException("Cannot set Minimum Capacity of Pool " + this.name + " to (" + newVal + "), this value is higher than the configured Maximum Capacity of the pool (" + this.maxCapacity + ").");
         } else {
            synchronized(this) {
               this.minCapacity = newVal;
               if (newVal > this.getCurrCapacity()) {
                  numToMake = newVal - this.getCurrCapacity();
               }
            }

            if (numToMake > 0 && this.state != 100) {
               PooledResourceInfo[] resInfoList = new PooledResourceInfo[numToMake];
               Arrays.fill(resInfoList, (Object)null);
               this.makeResources(numToMake, resInfoList, true);
            }

         }
      }
   }

   public void setInitialCapacity(int newVal) throws ResourceException, IllegalArgumentException {
      if (this.initialCapacity != newVal) {
         int numToMake = 0;
         if (newVal > this.maxCapacity) {
            throw new IllegalArgumentException("Cannot set Initial Capacity of Pool " + this.name + " to (" + newVal + "), this value is higher than the configured Maximum Capacity of the pool (" + this.maxCapacity + ").");
         } else {
            synchronized(this) {
               this.initialCapacity = newVal;
               if (this.minCapacity < 0 && newVal > this.getCurrCapacity()) {
                  numToMake = newVal - this.getCurrCapacity();
               }
            }

            if (numToMake > 0 && this.state != 100) {
               PooledResourceInfo[] resInfoList = new PooledResourceInfo[numToMake];
               Arrays.fill(resInfoList, (Object)null);
               this.makeResources(numToMake, resInfoList, true);
            }

         }
      }
   }

   public void setCapacityIncrement(int newVal) {
      this.capacityIncrement = 1;
   }

   public void setHighestNumWaiters(int newVal) {
      this.waitersMax = newVal;
   }

   public void setHighestNumUnavailable(int newVal) {
      this.maxUnavl = newVal;
   }

   public void setShrinkEnabled(boolean newVal) {
      this.allowShrinking = newVal;
   }

   public void setInactiveResourceTimeoutSeconds(int newVal) {
      this.inactiveSecs = newVal;
   }

   public void setResourceCreationRetrySeconds(int newVal) {
      this.retryIntervalSecs = newVal;
   }

   public void setResourceReserveTimeoutSeconds(int newVal) {
      this.reserveTimeoutSecs = newVal;
   }

   public void setTestFrequencySeconds(int newVal) {
      this.testSecs = newVal;
   }

   public void setProfileHarvestFrequencySeconds(int newVal) {
      this.harvestSecs = newVal;
   }

   public void setMaintenanceFrequencySeconds(int newVal) {
      this.maintSecs = newVal;
   }

   public void setShrinkFrequencySeconds(int newVal) {
      if (newVal <= 0) {
         newVal = 0;
         this.allowShrinking = false;
      }

      if (this.shrinkSecs != newVal) {
         this.shrinkSecs = newVal;
         synchronized(this.poolHistoryLock) {
            this.poolHistorySize = this.shrinkSecs / this.scanSecs;
            if (this.poolHistorySize > 0) {
               this.poolUseHistory = new int[this.poolHistorySize];
            }

            this.poolHistCollected = 0;
            this.oldestUsageCount = 0;
         }
      }
   }

   public void setTestOnReserve(boolean newVal) {
      if (this.testOnReserve != newVal) {
         this.testOnReserve = newVal;
         if (!this.quietMessages) {
            if (this.testOnReserve) {
               CommonLogger.logTestOnReserveEnabled(this.name);
            } else {
               CommonLogger.logTestOnReserveDisabled(this.name);
            }
         }

      }
   }

   public void setTestOnRelease(boolean newVal) {
      if (this.testOnRelease != newVal) {
         this.testOnRelease = newVal;
         if (!this.quietMessages) {
            if (this.testOnRelease) {
               CommonLogger.logTestOnReleaseEnabled(this.name);
            } else {
               CommonLogger.logTestOnReleaseDisabled(this.name);
            }
         }

      }
   }

   public void setTestOnCreate(boolean newVal) {
      if (this.testOnCreate != newVal) {
         this.testOnCreate = newVal;
         if (!this.quietMessages) {
            if (this.testOnCreate) {
               CommonLogger.logTestOnCreateEnabled(this.name);
            } else {
               CommonLogger.logTestOnCreateDisabled(this.name);
            }
         }

      }
   }

   public void setIgnoreInUseResources(boolean newVal) {
      this.ignoreInUseResources = newVal;
   }

   public void setCountOfTestFailuresTillFlush(int newVal) {
      this.countToFlushPool = newVal;
   }

   public void setCountOfRefreshFailuresTillDisable(int newVal) {
      this.countToDisablePool = newVal;
   }

   public ResourcePoolProfiler getProfiler() {
      return null;
   }

   public ResourcePoolMaintainer getMaintainer() {
      return null;
   }

   public void dumpPool(PrintWriter pw) {
      ResourcePoolUtil.log(pw, "Dumping Resource Pool:" + this.name);

      try {
         synchronized(this) {
            ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool Current Capacity = " + this.getCurrCapacity());
            int lcv = 0;
            ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool dumping available resources, #entries = " + this.available.size());
            ListIterator listIter = this.available.listIterator(0);

            while(true) {
               if (!listIter.hasNext()) {
                  ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool dumping reserved resources, #entries = " + this.reserved.size());
                  Iterator iter = this.reserved.iterator();
                  lcv = 0;

                  while(iter.hasNext()) {
                     ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool reserved[" + lcv++ + "] = " + iter.next());
                  }

                  ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool # dead resources = " + this.dead.size());
                  break;
               }

               ResourcePoolUtil.log(pw, "Resource Pool:" + this.name + ":dumpPool available[" + lcv++ + "] = " + listIter.next());
            }
         }
      } catch (Exception var8) {
         ResourcePoolUtil.log(pw, "Dumping of resource pool " + this.name + " aborted, got exception : " + var8.toString());
         return;
      }

      ResourcePoolUtil.log(pw, "Dumping Resource Pool: " + this.name + " complete");
   }

   protected void initParameters(Properties poolConfig) {
      String val = null;
      if ((val = poolConfig.getProperty("name")) != null) {
         this.name = val;
      } else {
         this.name = "pool_" + poolCount++;
      }

      if ((val = poolConfig.getProperty("maxCapacity")) != null) {
         this.maxCapacity = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("initialCapacity")) != null) {
         this.initialCapacity = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("minCapacity")) != null) {
         this.minCapacity = Integer.parseInt(val);
      } else {
         this.minCapacity = -1;
      }

      if ((val = poolConfig.getProperty("testOnReserve")) != null) {
         this.testOnReserve = Boolean.valueOf(val);
      }

      if ((val = poolConfig.getProperty("testOnRelease")) != null) {
         this.testOnRelease = Boolean.valueOf(val);
      }

      if ((val = poolConfig.getProperty("testOnCreate")) != null) {
         this.testOnCreate = Boolean.valueOf(val);
      }

      if ((val = poolConfig.getProperty("testFrequencySeconds")) != null) {
         this.testSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("shrinkEnabled")) != null) {
         this.allowShrinking = Boolean.valueOf(val);
      }

      if ((val = poolConfig.getProperty("shrinkFrequencySeconds")) != null) {
         this.shrinkSecs = Integer.parseInt(val);
         if (this.shrinkSecs <= 0) {
            this.allowShrinking = false;
         } else {
            this.allowShrinking = true;
         }
      }

      if ((val = poolConfig.getProperty("ignoreInUseResources")) != null) {
         this.ignoreInUseResources = Boolean.valueOf(val);
      }

      if ((val = poolConfig.getProperty("resvTimeoutSeconds")) != null) {
         this.reserveTimeoutSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("resCreationRetrySeconds")) != null) {
         this.retryIntervalSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("maxWaiters")) != null) {
         this.waitersMax = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("maxResvRetry")) != null) {
         this.resvRetryMax = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("maxUnavl")) != null) {
         this.maxUnavl = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("inactiveResTimeoutSeconds")) != null) {
         this.inactiveSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("countOfTestFailuresTillFlush")) != null) {
         this.countToFlushPool = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("countOfRefreshFailuresTillDisable")) != null) {
         this.countToDisablePool = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("harvestFreqSecsonds")) != null) {
         this.harvestSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("maintenanceFrequencySeconds")) != null) {
         this.maintSecs = Integer.parseInt(val);
      }

      if ((val = poolConfig.getProperty("quietMessages")) != null) {
         this.quietMessages = Boolean.valueOf(val);
      }

   }

   private void resumeOrSuspendResources(int opcode, boolean force, int operationSecs) {
      int waitSecs = 60;
      if (this.inactiveSecs > 0) {
         waitSecs = this.inactiveSecs;
      }

      if (operationSecs > 0) {
         waitSecs = operationSecs;
      }

      if (force || opcode != 17) {
         waitSecs = 0;
      }

      for(int loop = waitSecs * 10; loop > 0 && this.reserved.size() != 0; --loop) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var8) {
         }
      }

      PooledResource[] resources = this.getResources();
      if (resources != null) {
         for(int lcv = 0; lcv < resources.length; ++lcv) {
            PooledResource res = resources[lcv];
            if (opcode == 16) {
               res.enable();
            } else if (opcode == 17) {
               res.disable();
            }
         }
      }

   }

   private void validateParameters() throws ResourceException {
      if (this.capacityIncrement < 1) {
         CommonLogger.logAdjustedCapacityIncrement(this.name, this.capacityIncrement);
         this.capacityIncrement = 1;
      }

      if (this.initialCapacity > this.maxCapacity) {
         throw new ResourceException("Connection Pool " + this.name + ": initial size (" + this.initialCapacity + ") is greater than maximum size (" + this.maxCapacity + ")");
      } else if (this.getMinCapacity() > this.maxCapacity) {
         throw new ResourceException("Connection Pool " + this.name + ": minimum size (" + this.getMinCapacity() + ") is greater than maximum size (" + this.maxCapacity + ")");
      } else {
         if (this.testSecs < 0) {
            CommonLogger.logAdjustedTestSeconds(this.name, this.testSecs);
            this.testSecs = 5;
         } else if (!this.quietMessages && this.testSecs == 0) {
            CommonLogger.logNoTest(this.name);
         } else if (!this.quietMessages && this.testSecs > 0) {
            CommonLogger.logTest(this.name, this.testSecs);
         }

      }
   }

   protected void createCollections() {
      this.available = new PooledResourceLinkedList();
      this.dead = new LinkedList();
      this.reserved = new PooledResourceHashSet(this.maxCapacity);
   }

   private void createInternalObjs() {
      this.createCollections();
      TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
      this.poolTimerManager = tmf.getTimerManager("ResourcePoolTimerManager");
      this.pmtLockObject = new Object() {
      };
      this.poolHistorySize = this.shrinkSecs / this.scanSecs;
      this.poolUseHistory = new int[this.poolHistorySize];
      this.textFormatter = new CommonTextTextFormatter();
   }

   protected int makeResources(int count, PooledResourceInfo[] infoList, boolean setupCreateRetry) throws ResourceException {
      return this.makeResources(count, infoList, (List)null, setupCreateRetry, false);
   }

   protected int makeResources(int count, PooledResourceInfo[] infoList, List successfullyCreatedResource, boolean setupCreateRetry) throws ResourceException {
      return this.makeResources(count, infoList, successfullyCreatedResource, setupCreateRetry, false);
   }

   protected int makeResources(int count, PooledResourceInfo[] infoList, List successfullyCreatedResource, boolean setupCreateRetry, boolean makeAllAvailable) throws ResourceException {
      return this.makeResources(count, infoList, successfullyCreatedResource, setupCreateRetry, makeAllAvailable, false);
   }

   protected int makeResources(int count, PooledResourceInfo[] infoList, List successfullyCreatedResource, boolean setupCreateRetry, boolean makeAllAvailable, boolean callerDecrementsBeingCreatedCount) throws ResourceException {
      boolean doLog = ResourcePoolUtil.doLog();
      if (doLog) {
         ResourcePoolUtil.log(" > RP(" + this.name + "):makeResources (10) count = " + count);
      }

      int curCap = false;
      int curCap;
      int currAvlCnt;
      synchronized(this) {
         curCap = this.getCurrCapacity();
         if (curCap + count + this.beingCreated > this.maxCapacity) {
            currAvlCnt = count;
            count = this.maxCapacity - (curCap + this.beingCreated);
            if (count < 0) {
               count = 0;
            }

            CommonLogger.logAdjustedMakeCount(this.name, currAvlCnt, count);
         }

         if (count > 0) {
            this.beingCreated += count;
         }
      }

      if (doLog) {
         ResourcePoolUtil.log("  RP(" + this.name + "):makeResources (20) maxCapacity = " + this.maxCapacity + ", Current Capacity = " + this.getCurrCapacity() + ", count = " + count);
      }

      if (count <= 0) {
         if (doLog) {
            ResourcePoolUtil.log(" <* RP(" + this.name + "):makeResources (30) returns -1");
         }

         return -1;
      } else {
         PooledResource res = null;
         currAvlCnt = 0;
         int currDeadCnt = 0;
         long currCreateTime = 0L;
         PooledResource[] addToAvailable = new PooledResource[count];
         PooledResourceInfo[] addToDead = new PooledResourceInfo[count];
         int errCnt = 0;
         ResourceException err = null;
         int currRetryIntervalSecs = this.retryIntervalSecs;
         int actuallyCreated = 0;

         int lcv;
         for(lcv = 0; lcv < count; ++lcv) {
            if (doLog) {
               ResourcePoolUtil.log("   RP(" + this.name + "):makeResources (50) lcv = " + lcv);
            }

            try {
               res = null;

               try {
                  res = this.makeResource(infoList[lcv]);
               } finally {
                  if (res != null) {
                     this.zeroResetFailCount();
                  } else if (curCap == 0) {
                     this.incrementResetFailCount();
                  }

               }

               if (res != null) {
                  res.initialize();
                  this.incrementGroupCapacities(res);
                  this.incrementGroupCreatedCounts(res);
                  if ((currCreateTime = res.getCreationTime()) > 0L) {
                     ++this.createCount;
                     this.createTime += currCreateTime;
                  }

                  this.checkResource(res, 12);
                  ++this.created;
                  ++actuallyCreated;
                  if (infoList[lcv] != null) {
                     res.setPooledResourceInfo(infoList[lcv]);
                  }

                  addToAvailable[currAvlCnt++] = res;
               }
            } catch (Throwable var30) {
               if (err == null) {
                  if (var30 instanceof ResourceException) {
                     err = (ResourceException)var30;
                  } else {
                     err = new ResourceException(var30.toString());
                     err.initCause(var30);
                  }
               }

               if (!(var30 instanceof ResourceSystemException) && currRetryIntervalSecs != 0 && setupCreateRetry) {
                  if (!this.continueMakeResourceAttemptsAfterFailure()) {
                     break;
                  }

                  if (doLog) {
                     ResourcePoolUtil.log(" *  RP(" + this.name + "):makeResources (60)");
                  }

                  if (res != null) {
                     res.destroy();
                  }

                  addToDead[currDeadCnt++] = infoList[lcv];
               } else {
                  if (doLog) {
                     ResourcePoolUtil.log(" *  RP(" + this.name + "):makeResources (55)");
                  }

                  ++errCnt;
                  if (var30 instanceof ResourceSystemException) {
                     err = (ResourceException)var30;
                  }

                  if (!this.continueMakeResourceAttemptsAfterFailure()) {
                     break;
                  }
               }
            }
         }

         synchronized(this) {
            if (doLog) {
               ResourcePoolUtil.log("   RP(" + this.name + "):makeResources (75) Current Capacity = " + this.getCurrCapacity());
            }

            if (count - actuallyCreated > 0) {
               this.beingCreated -= count - actuallyCreated;
               if (this.beingCreated < 0) {
                  this.beingCreated = 0;
               }
            }

            boolean addToAvailList = successfullyCreatedResource == null || err != null;
            lcv = 0;

            while(true) {
               if (lcv >= currAvlCnt) {
                  for(lcv = 0; lcv < currDeadCnt; ++lcv) {
                     this.dead.addFirst(addToDead[lcv]);
                  }
                  break;
               }

               if (makeAllAvailable) {
                  if (!this.available.contains(addToAvailable[lcv])) {
                     this.available.addFirst(addToAvailable[lcv]);
                  }

                  if (this.waiters > 0) {
                     this.notify();
                  }

                  successfullyCreatedResource.add(addToAvailable[lcv]);
                  --this.beingCreated;
                  if (this.beingCreated < 0) {
                     this.beingCreated = 0;
                  }
               } else if (addToAvailList) {
                  if (!this.available.contains(addToAvailable[lcv])) {
                     this.available.addFirst(addToAvailable[lcv]);
                  }

                  if (this.waiters > 0) {
                     this.notify();
                  }

                  --this.beingCreated;
                  if (this.beingCreated < 0) {
                     this.beingCreated = 0;
                  }
               } else {
                  successfullyCreatedResource.add(addToAvailable[lcv]);
                  addToAvailList = true;
                  if (!callerDecrementsBeingCreatedCount) {
                     --this.beingCreated;
                     if (this.beingCreated < 0) {
                        this.beingCreated = 0;
                     }
                  }
               }

               ++lcv;
            }
         }

         if (err != null) {
            int actualCount = count - errCnt;
            if (currRetryIntervalSecs <= 0 || !setupCreateRetry) {
               if (doLog) {
                  ResourcePoolUtil.log(" <* RP(" + this.name + "):makeResources (90)");
               }

               throw new ResourceDeadException(actualCount + ":" + err.getMessage(), err);
            }

            if (errCnt > 0) {
               if (doLog) {
                  ResourcePoolUtil.log(" <* RP(" + this.name + "):makeResources (80) errCnt = " + errCnt);
               }

               throw new ResourceSystemException(actualCount + ":" + err.getMessage(), err);
            }
         }

         if (!this.quietMessages) {
            CommonLogger.logResourcesMade(this.name, count, currAvlCnt, currDeadCnt);
         }

         if (doLog) {
            ResourcePoolUtil.log(" <  RP(" + this.name + "):makeResources (100) count = " + count);
         }

         return count;
      }
   }

   protected PooledResource makeResource(PooledResourceInfo pri) throws ResourceException {
      try {
         PooledResource res = this.resFactory.createResource(pri);
         this.most_recent_successful_connect_info = " Last connection created successfully at " + new Date() + ".";
         return res;
      } catch (ResourceException var3) {
         this.most_recent_connection_failure_info = " Last connect attempt failed at " + new Date() + " because of " + var3 + ".";
         throw var3;
      }
   }

   protected boolean continueMakeResourceAttemptsAfterFailure() {
      return true;
   }

   protected PooledResource refreshOldestAvailResource(PooledResourceInfo resInfo) throws ResourceException {
      return null;
   }

   public int getNumIdleDetected() {
      return this.numIdleDetected;
   }

   public int getTimeToNextShrinkOperation() {
      return this.shrinkSecs - this.timeElapsedAfterShrinking;
   }

   public int getResourcesDestroyedByShrinkingCount() {
      return this.resourcesDestroyedByShrinkingCount;
   }

   public long getLastShrinkTime() {
      return this.lastShrinkTime;
   }

   private void startPoolMaintenance() {
      this.poolMaintTask = new ResourcePoolMaintanenceTask();
      this.poolTimer = this.poolTimerManager.scheduleAtFixedRate(this.poolMaintTask, (long)(this.scanSecs * 1000), (long)(this.scanSecs * 1000));
      if (this.max_test_wait_millis > 0) {
         this.checkHangTask = new CheckHangTask();
         this.hangTimer = this.poolTimerManager.scheduleAtFixedRate(this.checkHangTask, (long)this.max_test_wait_millis, (long)this.max_test_wait_millis);
      }

   }

   private void stopPoolMaintenance() {
      if (this.poolMaintTask != null) {
         this.syncWithPoolMaintTask();
         if (this.poolTimer != null) {
            this.poolTimer.cancel();
         }

         this.poolMaintTask = null;
         if (this.hangTimer != null) {
            this.hangTimer.cancel();
         }

         this.checkHangTask = null;
      }

   }

   private void disablePool(boolean force, boolean shuttingDown, int operationSecs) throws ResourceException {
      if (!this.ignoreInUseResources && shuttingDown && !force) {
         synchronized(this) {
            if (this.reserved.size() != 0) {
               throw new ResourceException("Pool " + this.name + " cannot be shut down gracefully, because ignoreInUseResources is false, and " + this.reserved.size() + " resource instances still reserved by application!");
            }

            this.doDisable(force, shuttingDown, operationSecs);
         }
      } else {
         this.doDisable(force, shuttingDown, operationSecs);
      }

   }

   private void doDisable(boolean force, boolean shuttingDown, int operationSecs) throws ResourceException {
      if (!force || !shuttingDown) {
         if (this.state == 102 || this.state == 100) {
            return;
         }

         if (this.state != 101) {
            throw new ObjectLifeCycleException("Pool " + this.name + " can be suspended only when it is in RUNNING state. It is currently in " + this.getState() + " state.");
         }
      }

      this.suspending = true;
      this.state = 102;
      this.resumeOrSuspendResources(17, force, operationSecs);
      if (force || shuttingDown) {
         this.reclaimReservedResources();
      }

      this.disableAllGroups();
      this.state = 102;
      this.suspending = false;
   }

   private void reclaimReservedResources() {
      synchronized(this) {
         int resvCnt = this.reserved.size();
         PooledResourceInfo[] reclaimList = new PooledResourceInfo[resvCnt];
         Iterator iter = this.reserved.iterator();
         int lcv = 0;

         while(iter.hasNext()) {
            PooledResource res = (PooledResource)iter.next();
            iter.remove();
            reclaimList[lcv++] = res.getPooledResourceInfo();
            this.forceDestroyResource(res);
         }

      }
   }

   private PooledResource[] removeResources(int numToRemove) {
      int numDeadToRemove = false;
      int numAvlToRemove = 0;
      PooledResource[] resources = null;
      synchronized(this) {
         int numDeadToRemove;
         if (numToRemove > this.dead.size()) {
            numDeadToRemove = this.dead.size();
            numAvlToRemove = numToRemove - numDeadToRemove;
         } else {
            numDeadToRemove = numToRemove;
         }

         while(numDeadToRemove-- > 0) {
            this.dead.removeFirst();
         }

         if (numAvlToRemove > 0) {
            if (numAvlToRemove > this.available.size()) {
               numAvlToRemove = this.available.size();
            }

            resources = new PooledResource[numAvlToRemove];

            for(int lcv = 0; lcv < numAvlToRemove; ++lcv) {
               resources[lcv] = (PooledResource)this.available.removeLast();
            }
         }

         return resources;
      }
   }

   private void destroyAllResources(int opcode) throws ResourceException {
      synchronized(this) {
         int count = this.available.size() + this.dead.size();
         PooledResource[] resources = this.removeResources(count);
         if (resources != null) {
            for(int lcv = 0; lcv < resources.length; ++lcv) {
               if (opcode == 14) {
                  this.destroyResource(resources[lcv]);
               } else if (opcode == 15) {
                  this.forceDestroyResource(resources[lcv]);
               }
            }
         }

         if (opcode == 14) {
            if (this.getCurrCapacity() > 0) {
               if (!this.ignoreInUseResources) {
                  throw new ResourceException("Pool " + this.name + " still in use, " + this.reserved.size() + " resource instances still reserved by application!");
               }

               CommonLogger.logShuttingDownIgnoringInUse(this.name, this.getCurrCapacity());
            }
         } else if (opcode == 15) {
            PooledResourceHashSet remainders = new PooledResourceHashSet(this.reserved.size());
            Iterator iter = this.reserved.iterator();

            while(true) {
               while(iter.hasNext()) {
                  PooledResource res = (PooledResource)iter.next();
                  iter.remove();
                  if (res instanceof ResourceInfo && ((ResourceInfo)res).isInUse()) {
                     remainders.add(res);
                  } else {
                     this.forceDestroyResource(res);
                  }
               }

               if (remainders.size() > 0) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new AsyncCleanupTask(remainders));
               }
               break;
            }
         }

      }
   }

   private PooledResource checkAndReturnResource(PooledResource res, int operation) throws ResourceException {
      return this.checkAndReturnResource(res, operation, false);
   }

   private PooledResource checkAndReturnResource(PooledResource res, int operation, boolean currTestOnRelease) throws ResourceException {
      boolean checkFailed = false;
      Exception errorCode = null;
      ReserveReleaseInterceptor rri = this.getReserveReleaseInterceptor();
      if (rri != null && (operation == 10 || operation == 13)) {
         try {
            rri.onReserve(res);
         } catch (ResourceException var16) {
            checkFailed = true;
            errorCode = var16;
         }
      }

      try {
         if (!checkFailed) {
            this.checkResource(res, operation, currTestOnRelease);
         }
      } catch (Exception var15) {
         checkFailed = true;
         errorCode = var15;
      }

      if (rri != null && operation == 13) {
         try {
            rri.onRelease(res);
         } catch (ResourceException var14) {
            checkFailed = true;
            errorCode = var14;
         }
      }

      IPooledResourceLinkedList destList = null;
      if (operation == 10) {
         if (checkFailed) {
            if (rri != null) {
               try {
                  rri.onRelease(res);
               } catch (ResourceException var12) {
               }
            }

            synchronized(this) {
               this.reserved.remove(res);
            }

            this.destroyResource(res);
            throw new ResourceDeadException(((Exception)errorCode).getMessage(), (Throwable)errorCode);
         }
      } else if (operation == 11) {
         if (checkFailed) {
            this.destroyResource(res);
         } else if (currTestOnRelease) {
            destList = this.available;
         }
      } else if (operation == 13) {
         if (checkFailed) {
            this.destroyResource(res);
         } else {
            destList = this.available;
         }
      }

      if (destList != null) {
         synchronized(this) {
            if (currTestOnRelease) {
               --this.preTestOnReleaseCount;
            } else if (operation == 13) {
               this.beingTestedCount = 0;
            }

            destList.addFirst(res);
            if (destList == this.available && this.waiters > 0) {
               this.notify();
            }
         }
      }

      return operation == 10 ? res : null;
   }

   private void checkResource(PooledResource res, int operation) throws ResourceException {
      this.checkResource(res, operation, false);
   }

   private void checkResource(PooledResource res, int operation, boolean currTestOnRelease) throws ResourceException {
      if (operation == 10 && this.testOnReserve || operation == 11 && currTestOnRelease || operation == 12 && this.testOnCreate || operation == 13) {
         Group group = (Group)this.getPrimaryGroup(res);
         if (group == null) {
            throw new ResourceException("primary group is null, groups=" + this.groups);
         }

         group.checkHang();
         if (this.testResource(res) == -1) {
            boolean restockThisResource = group.incrementTestFailCount();
            this.refreshResource(res);
            if (this.testResource(res) == -1) {
               this.destroyResource(res);
               this.incrementResetFailCount();
               throw new ResourceDeadException();
            }

            this.zeroResetFailCount();
            group = (Group)this.getPrimaryGroup(res);
            CommonLogger.logEnablingGroupDueToSuccess(this.name, group.groupId);
            group.enable();
            if (restockThisResource) {
               synchronized(this) {
                  if (operation == 10) {
                     if (this.state != 101) {
                        res.forceDestroy();
                        throw new ResourceDisabledException("Pool " + this.name + " is " + this.getDerivedState() + ", cannot allocate resources to applications..");
                     }

                     this.reserved.add(res);
                  } else if (operation == 11 && !this.available.contains(res)) {
                     this.available.add(res);
                  }
               }
            }
         } else {
            group.zeroTestFailCount();
         }
      }

   }

   private void incrementResetFailCount() {
      if (this.created != 0) {
         if (this.state != 102) {
            int currCountToDisablePool = this.countToDisablePool;
            if (currCountToDisablePool > 0) {
               if (this.disabledUponResetFailure) {
                  return;
               }

               ++this.resetFailCount;
               if (this.resetFailCount >= currCountToDisablePool) {
                  this.processFailCountExceededDisableThreshold();
               }
            }

         }
      }
   }

   protected void processFailCountExceededDisableThreshold() {
      if (!this.disabledUponResetFailure) {
         synchronized(this.forSynchronizingSelfDisabling) {
            if (!this.disabledUponResetFailure && this.state == 101) {
               this.disabledUponResetFailure = true;
               this.resetFailCount = 0;
               CommonLogger.logSuspendingPoolDueToFailures(this.name, this.countToDisablePool);

               try {
                  this.reason_for_suspension = " It was suspended at " + new Date() + " because of " + this.countToDisablePool + " consecutive connect failures.";
                  this.disablePool(true, false, 0);
               } catch (Exception var5) {
               }

               this.disableAllGroups();

               try {
                  this.destroyAllResources(15);
               } catch (Throwable var4) {
               }
            }
         }
      }

   }

   public void zeroResetFailCount() {
      this.resetFailCount = 0;
      if (this.disabledUponResetFailure && !this.isSuspending() && this.resumeInternallyAllowed) {
         synchronized(this.forSynchronizingSelfDisabling) {
            if (this.disabledUponResetFailure) {
               CommonLogger.logResumingPoolDueToSuccess(this.name);

               try {
                  this.resumeInternal();
               } catch (Exception var4) {
               }

               this.state = 101;
               this.disabledUponResetFailure = false;
            }
         }
      }

   }

   private boolean isSuspending() {
      return this.state == 102 && this.suspending;
   }

   private void refreshResource(PooledResource res) throws ResourceException {
      try {
         Collection oldGroups = new ArrayList(res.getGroups());
         if (this.beingTestedCount == 1) {
            this.beingTestedCount = 0;
         }

         ++this.refreshCount;
         this.resFactory.refreshResource(res);
         this.most_recent_successful_connect_info = " Last connection refreshed successfully at " + new Date() + ".";
         ++this.refreshOKCount;
         res.initialize();
         if (oldGroups != null) {
            Iterator var5 = oldGroups.iterator();

            while(var5.hasNext()) {
               ResourcePoolGroup group = (ResourcePoolGroup)var5.next();
               ((Group)group).decrementCapacityCount();
            }
         }

         this.incrementGroupCapacities(res);
      } catch (ResourceException var7) {
         this.incrementResetFailCount();
         throw var7;
      }

      long currCreateTime;
      if ((currCreateTime = res.getCreationTime()) > 0L) {
         ++this.createCount;
         this.createTime += currCreateTime;
      }

   }

   protected void destroyResource(PooledResource res) {
      res.destroy();
      this.decrementGroupCapacities(res);
      ++this.destroyed;
   }

   protected void forceDestroyResource(PooledResource res) {
      res.forceDestroy();
      this.decrementGroupCapacities(res);
      ++this.destroyed;
   }

   protected void incrementResourcesDestroyedByShrinkingCount() {
      ++this.resourcesDestroyedByShrinkingCount;
   }

   public void resetStatistics() {
      this.createTime = 0L;
      this.createCount = 0L;
      this.waitersHigh = 0;
      this.waitersTotal = 0L;
      this.waitersSuccessTotal = 0L;
      this.waitersFailureTotal = 0L;
      this.waitSecsHigh = 0;
      this.refreshCount = 0;
      this.refreshOKCount = 0;
      this.leaked = 0;
      this.created = 0;
      this.destroyed = 0;
      this.numIdleDetected = 0;
      this.resvReqCnt = 0L;
      this.resvReqFailCnt = 0L;
      this.resourcesDestroyedByShrinkingCount = 0;
      this.currCapacityHigh = 0;
      this.highest_num_unavailable = 0;
      this.reserved.resetStatistics();
      this.available.resetStatistics();
      this.defaultPrimaryGroup.resetStatistics();
      Iterator var1 = this.groups.values().iterator();

      while(var1.hasNext()) {
         ResourcePoolGroup group = (ResourcePoolGroup)var1.next();
         group.resetStatistics();
      }

   }

   private int maintainUseAverage() {
      this.lastUsageCount = this.reserved.size();
      synchronized(this.poolHistoryLock) {
         if (this.poolHistorySize == 0) {
            return this.lastUsageCount;
         } else {
            if (this.poolHistCollected < this.poolHistorySize) {
               this.poolUseHistory[this.poolHistCollected++] = this.lastUsageCount;
            } else {
               this.poolUseHistory[this.oldestUsageCount++] = this.lastUsageCount;
            }

            if (this.oldestUsageCount == this.poolHistorySize) {
               this.oldestUsageCount = 0;
            }

            int local_runningAverage = 0;

            for(int i = 0; i < this.poolHistCollected; ++i) {
               local_runningAverage += this.poolUseHistory[i];
            }

            this.runningAverage = local_runningAverage / this.poolHistCollected;
            return Math.max(this.runningAverage, this.lastUsageCount);
         }
      }
   }

   private void doShrink(boolean hard) {
      int numToShrink = false;
      PooledResource[] resources = null;
      synchronized(this) {
         this.lastShrinkTime = System.currentTimeMillis();
         int currentUsage = this.reserved.size() + this.num_reservers.get();
         int shrinkSize = Math.max(this.getMinCapacity(), currentUsage);
         if (!hard) {
            if (this.shrinkFactor == 1.0F) {
               shrinkSize = Math.max(shrinkSize, this.runningAverage);
            } else {
               shrinkSize = Math.max(this.getMinCapacity() + (int)((float)(this.getCurrCapacity() - this.getMinCapacity()) * this.shrinkFactor), currentUsage);
            }
         }

         int numToShrink = this.getCurrCapacity() - shrinkSize;
         if (numToShrink <= 0) {
            return;
         }

         resources = this.removeResources(numToShrink);
      }

      if (resources != null) {
         for(int lcv = 0; lcv < resources.length; ++lcv) {
            this.destroyResource(resources[lcv]);
            this.incrementResourcesDestroyedByShrinkingCount();
         }
      }

   }

   private int parseExceptionMsg(String msg) {
      int count = 0;
      if (msg != null) {
         String[] st = msg.split(":");
         count = Integer.parseInt(st[0]);
      }

      return count;
   }

   private void timeoutInactiveResources() {
      boolean doLog = ResourcePoolUtil.doLog();
      if (doLog) {
         ResourcePoolUtil.log(" > RP(" + this.name + "):timeoutInactiveRes (10)");
      }

      int leakCnt = 0;
      PooledResource[] leakedResources;
      ResourceCleanupHandler[] leakedResourcesCleanupHandlers;
      synchronized(this) {
         int resCnt = this.reserved.size();
         leakedResources = new PooledResource[resCnt];
         leakedResourcesCleanupHandlers = new ResourceCleanupHandler[resCnt];
         Iterator iter = this.reserved.iterator();

         while(true) {
            if (!iter.hasNext()) {
               break;
            }

            PooledResource res = (PooledResource)iter.next();
            if (res.getUsed()) {
               res.setUsed(false);
               if (doLog) {
                  ResourcePoolUtil.log("  RP(" + this.name + "):timeoutInactiveRes (20)");
               }
            } else {
               leakedResources[leakCnt] = res;
               leakedResourcesCleanupHandlers[leakCnt] = res.getResourceCleanupHandler();
               ++leakCnt;
               if (doLog) {
                  ResourcePoolUtil.log("  RP(" + this.name + "):timeoutInactiveRes (30)");
               }
            }
         }
      }

      if (doLog) {
         ResourcePoolUtil.log("  RP(" + this.name + "):timeoutInactiveRes (40) leakCnt = " + leakCnt);
      }

      for(int i = 0; i < leakCnt; ++i) {
         if (this.getProfiler() != null && this.getProfiler().isResourceLeakProfilingEnabled()) {
            this.getProfiler().addLeakData(leakedResources[i]);
         }

         ++this.numIdleDetected;
         ResourceCleanupHandler hdlr = leakedResourcesCleanupHandlers[i];
         if (hdlr != null) {
            hdlr.forcedCleanup();
         } else {
            CommonLogger.logForcedRelease(this.name, leakedResources[i].toString());

            try {
               this.releaseResource(leakedResources[i]);
            } catch (ResourceException var11) {
               CommonLogger.logErrForcedRelease(this.name, leakedResources[i].toString(), var11.getMessage());
            }
         }
      }

      if (doLog) {
         ResourcePoolUtil.log(" < RP(" + this.name + "):timeoutInactiveRes (100)");
      }

   }

   private void harvestProfileData() {
      ResourcePoolProfiler profiler = this.getProfiler();
      if (profiler != null) {
         profiler.harvestData();
         profiler.deleteData();
      }

   }

   private void invokeMaintenanceCallback() {
      ResourcePoolMaintainer maintainer = this.getMaintainer();
      if (maintainer != null) {
         maintainer.performMaintenance();
      }

   }

   private void testUnusedResources() {
      if (this.state != 102) {
         int numToTest = this.available.size();
         if (this.maxUnavl > 0) {
            numToTest = this.maxUnavl - this.dead.size();
            if (numToTest <= 0) {
               CommonLogger.logMaxUnavlReached(this.name, this.maxUnavl);
               return;
            }

            if (numToTest > this.available.size()) {
               numToTest = this.available.size();
            }
         }

         while(numToTest-- > 0) {
            PooledResource res = null;
            synchronized(this) {
               res = (PooledResource)this.available.removeLast();
               if (res == null) {
                  break;
               }

               this.beingTestedCount = 1;
            }

            try {
               this.checkAndReturnResource(res, 13);
               this.interrupt_testing_idle_connections = false;
            } catch (Exception var9) {
            } finally {
               this.beingTestedCount = 0;
               if (this.interrupt_testing_idle_connections) {
                  this.interrupt_testing_idle_connections = false;
                  break;
               }

            }
         }

      }
   }

   private int refreshResources() {
      int failCnt = 0;
      synchronized(this) {
         ListIterator listIter = this.available.listIterator(0);

         while(listIter.hasNext()) {
            try {
               this.refreshResource((PooledResource)listIter.next());
            } catch (ResourceException var8) {
               ++failCnt;
            }
         }

         Iterator iter = this.reserved.iterator();

         while(iter.hasNext()) {
            try {
               this.refreshResource((PooledResource)iter.next());
            } catch (ResourceException var7) {
               ++failCnt;
            }
         }

         return failCnt;
      }
   }

   private int recreateDeadResources() {
      int failCnt = false;
      PooledResourceInfo[] infoList = null;
      int lcv;
      int deadCnt;
      synchronized(this) {
         deadCnt = this.dead.size();
         if (deadCnt == 0) {
            return 0;
         }

         infoList = new PooledResourceInfo[deadCnt];
         lcv = 0;

         while(true) {
            if (lcv >= deadCnt) {
               break;
            }

            Object o = this.dead.removeFirst();
            if (o instanceof PooledResourceInfo) {
               infoList[lcv] = (PooledResourceInfo)o;
            }

            ++lcv;
         }
      }

      try {
         lcv = this.makeResources(deadCnt, infoList, true);
      } catch (ResourceException var8) {
         lcv = this.parseExceptionMsg(var8.getMessage());
      }

      int failCnt;
      if (lcv == -1) {
         failCnt = deadCnt;
      } else {
         failCnt = deadCnt - lcv;
      }

      return failCnt;
   }

   private void syncWithPoolMaintTask() {
      boolean continueWait = true;

      while(continueWait) {
         continueWait = false;
         synchronized(this.pmtLockObject) {
            if (this.pmtStatus == 0) {
               this.pmtStatus = 2;
            } else if (this.pmtStatus == 1) {
               this.pmtStatus = 2;
               continueWait = true;
            } else if (this.pmtStatus == 2) {
               continueWait = true;
            }
         }

         if (continueWait) {
            try {
               Thread.sleep((long)(this.DEFAULT_SYNC_WAIT * 1000));
            } catch (Exception var4) {
            }
         }
      }

   }

   private int testResource(PooledResource res) throws ResourceException {
      long testStartTimeStamp = System.currentTimeMillis();
      Group group = (Group)this.getPrimaryGroup(res);
      TestRecord record = null;
      if (this.max_test_wait_millis > 0) {
         record = new TestRecord(Thread.currentThread(), testStartTimeStamp, res);
         group.registerRecord(record);
      }

      int testResult = true;

      int testResult;
      try {
         testResult = res.test();
      } finally {
         if (this.max_test_wait_millis > 0) {
            group.removeRecord(record);
         }

      }

      if (testResult == 1) {
         group.storeNewSuccessfulTestTime((int)(System.currentTimeMillis() - testStartTimeStamp));
      }

      return testResult;
   }

   protected boolean disableGroupOnTestFailCount() {
      return false;
   }

   protected ResourcePoolGroup addGroup(String category, String groupId) {
      if (!this.groups.contains(category, groupId)) {
         Group newgroup = new Group(groupId, category);
         this.groups.put(category, groupId, newgroup);
         return newgroup;
      } else {
         return null;
      }
   }

   protected ResourcePoolGroup getGroup(String category, String groupId) {
      return (ResourcePoolGroup)this.groups.get(category, groupId);
   }

   protected ResourcePoolGroup getOrCreateGroup(String category, String groupId) {
      this.addGroup(category, groupId);
      return this.getGroup(category, groupId);
   }

   protected void incrementGroupCapacities(PooledResource res) {
      Collection groups = res.getGroups();
      if (groups != null) {
         Iterator var3 = groups.iterator();

         while(var3.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var3.next();
            ((Group)group).incrementCapacityCount();
         }
      }

   }

   protected void decrementGroupCapacities(PooledResource res) {
      Collection groups = res.getGroups();
      if (groups != null) {
         Iterator var3 = groups.iterator();

         while(var3.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var3.next();
            ((Group)group).decrementCapacityCount();
         }
      }

   }

   protected void incrementGroupCreatedCounts(PooledResource res) {
      Collection groups = res.getGroups();
      if (groups != null) {
         Iterator var3 = groups.iterator();

         while(var3.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var3.next();
            ((Group)group).incrementCreatedCount();
         }
      }

   }

   private void disableAllGroups() {
      this.defaultPrimaryGroup.disable();
      Iterator var1 = this.groups.values().iterator();

      while(var1.hasNext()) {
         ResourcePoolGroup group = (ResourcePoolGroup)var1.next();
         group.disable();
      }

   }

   private void enableAllGroups() {
      this.defaultPrimaryGroup.enable();
      Iterator var1 = this.groups.values().iterator();

      while(var1.hasNext()) {
         ResourcePoolGroup group = (ResourcePoolGroup)var1.next();
         group.enable();
      }

   }

   private boolean isAllGroupDisabled() {
      if (this.defaultPrimaryGroup.isEnabled()) {
         return false;
      } else {
         Iterator var1 = this.groups.values().iterator();

         ResourcePoolGroup group;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            group = (ResourcePoolGroup)var1.next();
         } while(!group.isEnabled());

         return false;
      }
   }

   private ResourcePoolGroup getPrimaryGroup(PooledResource res) {
      ResourcePoolGroup primaryGroup = res.getPrimaryGroup();
      return (ResourcePoolGroup)(primaryGroup == null ? this.defaultPrimaryGroup : primaryGroup);
   }

   public List getGroups() {
      List ret = new ArrayList();
      synchronized(this) {
         Iterator var3 = this.groups.values().iterator();

         while(var3.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var3.next();
            ret.add(group);
         }

         return ret;
      }
   }

   public List getGroups(String category) {
      List ret = new ArrayList();
      synchronized(this) {
         Map categoryGroups = this.groups.row(category);
         Iterator var5 = categoryGroups.values().iterator();

         while(var5.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var5.next();
            ret.add(group);
         }

         return ret;
      }
   }

   public void checkHangForAllGroups() {
      if (this.groups.isEmpty()) {
         try {
            this.defaultPrimaryGroup.checkHang();
         } catch (ResourceDisabledException var5) {
         }
      } else {
         Iterator var1 = this.groups.values().iterator();

         while(var1.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var1.next();

            try {
               ((Group)group).checkHang();
            } catch (ResourceDisabledException var4) {
            }
         }
      }

   }

   public void setShrinkFactor(float factor) {
      this.shrinkFactor = factor;
   }

   public ReserveReleaseInterceptor getReserveReleaseInterceptor() {
      return null;
   }

   private class CheckHangTask implements NakedTimerListener {
      private CheckHangTask() {
      }

      public void timerExpired(Timer timer) {
         ResourcePoolImpl.this.checkHangForAllGroups();
      }

      // $FF: synthetic method
      CheckHangTask(Object x1) {
         this();
      }
   }

   private class ResourcePoolMaintanenceTask implements NakedTimerListener {
      private int inactiveTime = 0;
      private int shrinkTime = 0;
      private int testTime = 0;
      private int retryTime = 0;
      private int harvestTime = 0;
      private int currMaintTime = 0;

      public ResourcePoolMaintanenceTask() {
         synchronized(ResourcePoolImpl.this.pmtLockObject) {
            ResourcePoolImpl.this.pmtStatus = 0;
         }
      }

      public void timerExpired(Timer timer) {
         synchronized(ResourcePoolImpl.this.pmtLockObject) {
            if (ResourcePoolImpl.this.pmtStatus == 2 || ResourcePoolImpl.this.pmtStatus == 3) {
               ResourcePoolImpl.this.pmtStatus = 3;
               return;
            }

            if (ResourcePoolImpl.this.pmtStatus == 0) {
               ResourcePoolImpl.this.pmtStatus = 1;
            }
         }

         if (ResourcePoolImpl.this.inactiveSecs > 0) {
            this.inactiveTime += ResourcePoolImpl.this.scanSecs;
            if (this.inactiveTime >= ResourcePoolImpl.this.inactiveSecs) {
               this.inactiveTime = 0;
               ResourcePoolImpl.this.timeoutInactiveResources();
            }
         }

         int currMaintSecs;
         if (ResourcePoolImpl.this.allowShrinking) {
            this.shrinkTime += ResourcePoolImpl.this.scanSecs;
            ResourcePoolImpl.this.timeElapsedAfterShrinking = this.shrinkTime;
            currMaintSecs = ResourcePoolImpl.this.maintainUseAverage();
            int localCurrCapacity = ResourcePoolImpl.this.getCurrCapacity();
            if (localCurrCapacity > ResourcePoolImpl.this.getMinCapacity() && localCurrCapacity > currMaintSecs && this.shrinkTime >= ResourcePoolImpl.this.shrinkSecs) {
               this.shrinkTime = 0;
               ResourcePoolImpl.this.doShrink(false);
            }
         }

         if (ResourcePoolImpl.this.testSecs > 0) {
            this.testTime += ResourcePoolImpl.this.scanSecs;
            if (this.testTime >= ResourcePoolImpl.this.testSecs) {
               this.testTime = 0;
               ResourcePoolImpl.this.testUnusedResources();
            }
         }

         if (ResourcePoolImpl.this.retryIntervalSecs > 0) {
            this.retryTime += ResourcePoolImpl.this.scanSecs;
            if (this.retryTime >= ResourcePoolImpl.this.retryIntervalSecs) {
               this.retryTime = 0;
               currMaintSecs = ResourcePoolImpl.this.recreateDeadResources();
               if (currMaintSecs > 0) {
                  CommonLogger.logPoolRetryFailure(ResourcePoolImpl.this.name, currMaintSecs);
               }
            }
         }

         if (ResourcePoolImpl.this.disabledUponResetFailure && !ResourcePoolImpl.this.isSuspending() && ResourcePoolImpl.this.resumeInternallyAllowed || ResourcePoolImpl.this.getMinCapacity() > ResourcePoolImpl.this.getCurrCapacity() && ResourcePoolImpl.this.state == 101 && ResourcePoolImpl.this.created > 0) {
            PooledResourceInfo[] holder = new PooledResourceInfo[1];

            try {
               ResourcePoolImpl.this.makeResources(1, holder, (List)null, false, false);
            } catch (Exception var6) {
            }
         }

         if (ResourcePoolImpl.this.harvestSecs > 0) {
            this.harvestTime += ResourcePoolImpl.this.scanSecs;
            if (this.harvestTime >= ResourcePoolImpl.this.harvestSecs) {
               this.harvestTime = 0;
               ResourcePoolImpl.this.harvestProfileData();
            }
         }

         currMaintSecs = ResourcePoolImpl.this.maintSecs;
         if (currMaintSecs > 0) {
            this.currMaintTime += ResourcePoolImpl.this.scanSecs;
            if (this.currMaintTime >= currMaintSecs) {
               this.currMaintTime = 0;
               ResourcePoolImpl.this.invokeMaintenanceCallback();
            }
         }

         synchronized(ResourcePoolImpl.this.pmtLockObject) {
            if (ResourcePoolImpl.this.pmtStatus == 1) {
               ResourcePoolImpl.this.pmtStatus = 0;
            } else if (ResourcePoolImpl.this.pmtStatus == 2) {
               ResourcePoolImpl.this.pmtStatus = 3;
            }

         }
      }
   }

   protected class Group implements ResourcePoolGroup {
      private String groupId;
      private String categoryName;
      private volatile boolean enabled = true;
      private volatile int testFailCount = 0;
      private int[] successfulTestTimes = new int[10];
      private long successfulTimeCounter = 0L;
      private SortedSet currentlyRunningTests = new TreeSet();
      private AtomicInteger reservedCount = new AtomicInteger(0);
      private AtomicInteger createdCount = new AtomicInteger(0);
      private AtomicInteger capacityCount = new AtomicInteger(0);
      private AtomicInteger capacityCountHigh = new AtomicInteger(0);
      private int numberOfTestFailuresToIgnore = 0;

      public Group(String groupId) {
         this.groupId = groupId;
      }

      public Group(String groupId, String categoryName) {
         this.groupId = groupId;
         this.categoryName = categoryName;
      }

      private String getGroupId() {
         return this.groupId;
      }

      public void disable() {
         this.enabled = false;
      }

      public void enable() {
         this.enabled = true;
      }

      public boolean isEnabled() {
         return this.enabled;
      }

      public void incrementCapacityCount() {
         synchronized(this) {
            int currCapacity = this.capacityCount.incrementAndGet();
            if (currCapacity > this.capacityCountHigh.get()) {
               this.capacityCountHigh.set(currCapacity);
            }
         }

         ResourcePoolImpl.this.getCurrCapacity();
      }

      public void decrementCapacityCount() {
         this.capacityCount.decrementAndGet();
      }

      public void incrementCreatedCount() {
         this.createdCount.incrementAndGet();
      }

      public void incrementReservedCount() {
         this.reservedCount.incrementAndGet();
      }

      private void destroyIdleResources() throws ResourceException {
         synchronized(ResourcePoolImpl.this) {
            this.killAllConnectionsBeingTested();
            List toDestroy = new ArrayList();
            ListIterator liter = ResourcePoolImpl.this.available.listIterator(0);

            while(liter.hasNext()) {
               PooledResource res = (PooledResource)liter.next();
               if (this.equals(ResourcePoolImpl.this.getPrimaryGroup(res))) {
                  toDestroy.add(res);
               }
            }

            Iterator deadIterator = toDestroy.iterator();

            PooledResource resx;
            while(deadIterator.hasNext()) {
               resx = (PooledResource)deadIterator.next();
               ResourcePoolImpl.this.available.remove(resx);
               ResourcePoolImpl.this.forceDestroyResource(resx);
            }

            deadIterator = ResourcePoolImpl.this.dead.iterator();

            while(deadIterator.hasNext()) {
               resx = (PooledResource)deadIterator.next();
               if (this.equals(ResourcePoolImpl.this.getPrimaryGroup(resx))) {
                  deadIterator.remove();
                  ResourcePoolImpl.this.forceDestroyResource(resx);
               }
            }

         }
      }

      private void markReservedResources() {
         synchronized(ResourcePoolImpl.this) {
            Iterator iter = ResourcePoolImpl.this.reserved.iterator();

            while(iter.hasNext()) {
               PooledResource res = (PooledResource)iter.next();
               if (res instanceof HangSuspect && this.equals(ResourcePoolImpl.this.getPrimaryGroup(res))) {
                  ((HangSuspect)res).setAsHangSuspect();
               }
            }

         }
      }

      private boolean incrementTestFailCount() {
         int currCountToFlushPool = ResourcePoolImpl.this.countToFlushPool;
         if (currCountToFlushPool > 0) {
            if (this.numberOfTestFailuresToIgnore > 0) {
               --this.numberOfTestFailuresToIgnore;
            } else {
               ++this.testFailCount;
            }

            if (this.testFailCount >= currCountToFlushPool) {
               synchronized(ResourcePoolImpl.this) {
                  if (this.testFailCount == 0) {
                     return false;
                  }

                  this.testFailCount = 0;
                  if (ResourcePoolImpl.this.disableGroupOnTestFailCount()) {
                     CommonLogger.logDisablingGroupDueToFailures(ResourcePoolImpl.this.name, this.groupId, ResourcePoolImpl.this.countToFlushPool);
                     this.disable();
                  }

                  try {
                     this.destroyIdleResources();
                  } catch (ResourceException var5) {
                  }

                  this.markReservedResources();
                  return true;
               }
            }
         }

         return false;
      }

      private void zeroTestFailCount() {
         this.testFailCount = 0;
         this.enable();
         if (ResourcePoolImpl.this.disabledUponResetFailure && !ResourcePoolImpl.this.isSuspending() && ResourcePoolImpl.this.resumeInternallyAllowed) {
            ResourcePoolImpl.this.state = 101;
         }

      }

      private TestRecord getCheckRecord() {
         synchronized(this.currentlyRunningTests) {
            return this.currentlyRunningTests.size() == 0 ? null : (TestRecord)this.currentlyRunningTests.first();
         }
      }

      private void registerRecord(TestRecord record) {
         synchronized(this.currentlyRunningTests) {
            this.currentlyRunningTests.add(record);
         }
      }

      private void removeRecord(TestRecord record) {
         synchronized(this.currentlyRunningTests) {
            this.currentlyRunningTests.remove(record);
         }
      }

      private void killAllConnectionsBeingTested() {
         synchronized(this.currentlyRunningTests) {
            if (this.currentlyRunningTests.size() != 0) {
               ResourcePoolImpl.this.interrupt_testing_idle_connections = true;
               Iterator iter = this.currentlyRunningTests.iterator();

               while(iter.hasNext()) {
                  TestRecord res_rec = (TestRecord)iter.next();
                  PooledResource res = res_rec.res;
                  iter.remove();
                  ResourcePoolImpl.this.forceDestroyResource(res);
               }

            }
         }
      }

      private int getCurrentTypicalTime() {
         int typicalTime = 0;

         for(int i = 0; i < 10; ++i) {
            if (this.successfulTestTimes[i] > typicalTime) {
               typicalTime = this.successfulTestTimes[i];
            }
         }

         if (typicalTime == 0) {
            typicalTime = 1;
         }

         if (ResourcePoolUtil.doLog()) {
            ResourcePoolUtil.log("  RP-Group(" + ResourcePoolImpl.this.name + "-" + this.groupId + "): TypicalTime is " + typicalTime + " ms");
         }

         return typicalTime;
      }

      private void storeNewSuccessfulTestTime(int time) {
         this.successfulTestTimes[(int)(this.successfulTimeCounter++ % 10L)] = time;
      }

      private void checkHang() throws ResourceDisabledException {
         if (ResourcePoolImpl.this.max_test_wait_millis != 0) {
            TestRecord checkRecord = this.getCheckRecord();
            boolean hanging = this.suspectHang(checkRecord);
            int loopCount = 0;
            long start_sleep = System.currentTimeMillis();

            for(int sleep_amount = ResourcePoolImpl.this.max_test_wait_millis / 100; hanging && loopCount < 100; ++loopCount) {
               long this_sleep_start = System.currentTimeMillis();

               try {
                  Thread.sleep((long)sleep_amount);
               } catch (Exception var15) {
               }

               long this_sleep_end = System.currentTimeMillis();
               if (this_sleep_end - this_sleep_start > (long)(sleep_amount * 2)) {
                  loopCount = 0;
               }

               if (ResourcePoolImpl.this.state == 102) {
                  throw new ResourceDisabledException("Pool " + ResourcePoolImpl.this.name + " is " + ResourcePoolImpl.this.getDerivedState() + ", cannot allocate resources to applications.");
               }

               hanging = checkRecord.equals(this.getCheckRecord());
            }

            if (hanging) {
               long think_hanging = System.currentTimeMillis();
               this.numberOfTestFailuresToIgnore = this.getCurrCapacity();

               try {
                  this.destroyIdleResources();
               } catch (ResourceException var14) {
               }

               this.markReservedResources();
               CommonLogger.logHangDetected(ResourcePoolImpl.this.name, this.groupId, think_hanging - start_sleep, this.getCurrentTypicalTime());
               if (ResourcePoolUtil.doLog()) {
                  ResourcePoolUtil.log("  RP-Group(" + ResourcePoolImpl.this.name + "-" + this.groupId + "): cannot allocate resources to applications. We waited " + (think_hanging - start_sleep) + " milliseconds. A typical test has been taking " + this.getCurrentTypicalTime());
               }
            }

         }
      }

      private boolean suspectHang(TestRecord checkRecord) {
         if (checkRecord == null) {
            return false;
         } else {
            long typicalTime = (long)this.getCurrentTypicalTime();
            return System.currentTimeMillis() - checkRecord.getTimeStamp() >= (long)((double)typicalTime * 10.0);
         }
      }

      public String getName() {
         return this.groupId;
      }

      public String getCategoryName() {
         return this.categoryName;
      }

      public int getCurrCapacity() {
         return this.capacityCount.get();
      }

      public int getHighestCurrCapacity() {
         return this.capacityCountHigh.get();
      }

      public int getNumAvailable() {
         int count = 0;
         synchronized(ResourcePoolImpl.this) {
            ListIterator lit = ResourcePoolImpl.this.available.listIterator();

            while(lit.hasNext()) {
               PooledResource pr = (PooledResource)lit.next();
               Collection groups = pr.getGroups();
               if (groups != null && groups.contains(this)) {
                  ++count;
               }
            }

            return count;
         }
      }

      public int getNumReserved() {
         int count = 0;
         synchronized(ResourcePoolImpl.this) {
            Iterator it = ResourcePoolImpl.this.reserved.iterator();

            while(it.hasNext()) {
               PooledResource pr = (PooledResource)it.next();
               Collection groups = pr.getGroups();
               if (groups != null && groups.contains(this)) {
                  ++count;
               }
            }

            return count;
         }
      }

      public int getNumReserveRequests() {
         return this.reservedCount.get();
      }

      public int getNumUnavailable() {
         synchronized(this) {
            return this.getCurrCapacity() - this.getNumAvailable();
         }
      }

      public int getTotalNumAllocated() {
         return this.createdCount.get();
      }

      public String getState() {
         return this.enabled ? "Enabled" : "Disabled";
      }

      public int hashCode() {
         return this.idString().hashCode();
      }

      public boolean equals(Object obj) {
         return !(obj instanceof Group) ? false : this.idString().equals(((Group)obj).idString());
      }

      public String toString() {
         return "(" + this.categoryName + "," + this.groupId + ")";
      }

      private String idString() {
         return this.groupId + "@" + this.categoryName;
      }

      public void resetStatistics() {
         this.createdCount.set(0);
         this.reservedCount.set(0);
         this.capacityCountHigh.set(this.capacityCount.get());
      }
   }

   private class AsyncCleanupTask extends WorkAdapter {
      private PooledResourceHashSet resources;

      private AsyncCleanupTask(PooledResourceHashSet resources) {
         this.resources = null;
         this.resources = resources;
      }

      public void run() {
         Iterator iter = this.resources.iterator();

         while(iter.hasNext()) {
            PooledResource res = (PooledResource)iter.next();
            iter.remove();
            ResourcePoolImpl.this.destroyResource(res);
         }

      }

      // $FF: synthetic method
      AsyncCleanupTask(PooledResourceHashSet x1, Object x2) {
         this(x1);
      }
   }

   private class TestRecord implements Comparable {
      private Thread thread;
      private long timeStamp;
      private PooledResource res;

      TestRecord(Thread thread, long timeStamp, PooledResource res) {
         this.thread = thread;
         this.timeStamp = timeStamp;
         this.res = res;
      }

      public long getTimeStamp() {
         return this.timeStamp;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else {
            return obj instanceof TestRecord && this.thread == ((TestRecord)obj).thread && this.timeStamp == ((TestRecord)obj).timeStamp;
         }
      }

      public int hashCode() {
         int result = 17;
         result = 37 * result + (this.thread == null ? 0 : this.thread.hashCode());
         result = 37 * result + (int)(this.timeStamp ^ this.timeStamp >>> 32);
         return result;
      }

      public int compareTo(TestRecord tr) {
         long ts = tr.timeStamp;
         return this.timeStamp < ts ? -1 : (this.timeStamp == ts ? 0 : 1);
      }
   }
}
