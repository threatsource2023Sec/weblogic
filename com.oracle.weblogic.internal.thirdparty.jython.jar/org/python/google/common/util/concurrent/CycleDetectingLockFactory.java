package org.python.google.common.util.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Lists;
import org.python.google.common.collect.MapMaker;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Sets;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.Weak;

@Beta
@CanIgnoreReturnValue
@ThreadSafe
@GwtIncompatible
public class CycleDetectingLockFactory {
   private static final ConcurrentMap lockGraphNodesPerType = (new MapMaker()).weakKeys().makeMap();
   private static final Logger logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
   final Policy policy;
   private static final ThreadLocal acquiredLocks = new ThreadLocal() {
      protected ArrayList initialValue() {
         return Lists.newArrayListWithCapacity(3);
      }
   };

   public static CycleDetectingLockFactory newInstance(Policy policy) {
      return new CycleDetectingLockFactory(policy);
   }

   public ReentrantLock newReentrantLock(String lockName) {
      return this.newReentrantLock(lockName, false);
   }

   public ReentrantLock newReentrantLock(String lockName, boolean fair) {
      return (ReentrantLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED ? new ReentrantLock(fair) : new CycleDetectingReentrantLock(new LockGraphNode(lockName), fair));
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName) {
      return this.newReentrantReadWriteLock(lockName, false);
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName, boolean fair) {
      return (ReentrantReadWriteLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED ? new ReentrantReadWriteLock(fair) : new CycleDetectingReentrantReadWriteLock(new LockGraphNode(lockName), fair));
   }

   public static WithExplicitOrdering newInstanceWithExplicitOrdering(Class enumClass, Policy policy) {
      Preconditions.checkNotNull(enumClass);
      Preconditions.checkNotNull(policy);
      Map lockGraphNodes = getOrCreateNodes(enumClass);
      return new WithExplicitOrdering(policy, lockGraphNodes);
   }

   private static Map getOrCreateNodes(Class clazz) {
      Map existing = (Map)lockGraphNodesPerType.get(clazz);
      if (existing != null) {
         return existing;
      } else {
         Map created = createNodes(clazz);
         existing = (Map)lockGraphNodesPerType.putIfAbsent(clazz, created);
         return (Map)MoreObjects.firstNonNull(existing, created);
      }
   }

   @VisibleForTesting
   static Map createNodes(Class clazz) {
      EnumMap map = Maps.newEnumMap(clazz);
      Enum[] keys = (Enum[])clazz.getEnumConstants();
      int numKeys = keys.length;
      ArrayList nodes = Lists.newArrayListWithCapacity(numKeys);
      Enum[] var5 = keys;
      int var6 = keys.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Enum key = var5[var7];
         LockGraphNode node = new LockGraphNode(getLockName(key));
         nodes.add(node);
         map.put(key, node);
      }

      int i;
      for(i = 1; i < numKeys; ++i) {
         ((LockGraphNode)nodes.get(i)).checkAcquiredLocks(CycleDetectingLockFactory.Policies.THROW, nodes.subList(0, i));
      }

      for(i = 0; i < numKeys - 1; ++i) {
         ((LockGraphNode)nodes.get(i)).checkAcquiredLocks(CycleDetectingLockFactory.Policies.DISABLED, nodes.subList(i + 1, numKeys));
      }

      return Collections.unmodifiableMap(map);
   }

   private static String getLockName(Enum rank) {
      return rank.getDeclaringClass().getSimpleName() + "." + rank.name();
   }

   private CycleDetectingLockFactory(Policy policy) {
      this.policy = (Policy)Preconditions.checkNotNull(policy);
   }

   private void aboutToAcquire(CycleDetectingLock lock) {
      if (!lock.isAcquiredByCurrentThread()) {
         ArrayList acquiredLockList = (ArrayList)acquiredLocks.get();
         LockGraphNode node = lock.getLockGraphNode();
         node.checkAcquiredLocks(this.policy, acquiredLockList);
         acquiredLockList.add(node);
      }

   }

   private static void lockStateChanged(CycleDetectingLock lock) {
      if (!lock.isAcquiredByCurrentThread()) {
         ArrayList acquiredLockList = (ArrayList)acquiredLocks.get();
         LockGraphNode node = lock.getLockGraphNode();

         for(int i = acquiredLockList.size() - 1; i >= 0; --i) {
            if (acquiredLockList.get(i) == node) {
               acquiredLockList.remove(i);
               break;
            }
         }
      }

   }

   // $FF: synthetic method
   CycleDetectingLockFactory(Policy x0, Object x1) {
      this(x0);
   }

   private class CycleDetectingReentrantWriteLock extends ReentrantReadWriteLock.WriteLock {
      @Weak
      final CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock readWriteLock) {
         super(readWriteLock);
         this.readWriteLock = readWriteLock;
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }
   }

   private class CycleDetectingReentrantReadLock extends ReentrantReadWriteLock.ReadLock {
      @Weak
      final CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock readWriteLock) {
         super(readWriteLock);
         this.readWriteLock = readWriteLock;
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }
   }

   final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock {
      private final CycleDetectingReentrantReadLock readLock;
      private final CycleDetectingReentrantWriteLock writeLock;
      private final LockGraphNode lockGraphNode;

      private CycleDetectingReentrantReadWriteLock(LockGraphNode lockGraphNode, boolean fair) {
         super(fair);
         this.readLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantReadLock(this);
         this.writeLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantWriteLock(this);
         this.lockGraphNode = (LockGraphNode)Preconditions.checkNotNull(lockGraphNode);
      }

      public ReentrantReadWriteLock.ReadLock readLock() {
         return this.readLock;
      }

      public ReentrantReadWriteLock.WriteLock writeLock() {
         return this.writeLock;
      }

      public LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      public boolean isAcquiredByCurrentThread() {
         return this.isWriteLockedByCurrentThread() || this.getReadHoldCount() > 0;
      }

      // $FF: synthetic method
      CycleDetectingReentrantReadWriteLock(LockGraphNode x1, boolean x2, Object x3) {
         this(x1, x2);
      }
   }

   final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock {
      private final LockGraphNode lockGraphNode;

      private CycleDetectingReentrantLock(LockGraphNode lockGraphNode, boolean fair) {
         super(fair);
         this.lockGraphNode = (LockGraphNode)Preconditions.checkNotNull(lockGraphNode);
      }

      public LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      public boolean isAcquiredByCurrentThread() {
         return this.isHeldByCurrentThread();
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

         return var1;
      }

      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }

      // $FF: synthetic method
      CycleDetectingReentrantLock(LockGraphNode x1, boolean x2, Object x3) {
         this(x1, x2);
      }
   }

   private static class LockGraphNode {
      final Map allowedPriorLocks = (new MapMaker()).weakKeys().makeMap();
      final Map disallowedPriorLocks = (new MapMaker()).weakKeys().makeMap();
      final String lockName;

      LockGraphNode(String lockName) {
         this.lockName = (String)Preconditions.checkNotNull(lockName);
      }

      String getLockName() {
         return this.lockName;
      }

      void checkAcquiredLocks(Policy policy, List acquiredLocks) {
         int i = 0;

         for(int size = acquiredLocks.size(); i < size; ++i) {
            this.checkAcquiredLock(policy, (LockGraphNode)acquiredLocks.get(i));
         }

      }

      void checkAcquiredLock(Policy policy, LockGraphNode acquiredLock) {
         Preconditions.checkState(this != acquiredLock, "Attempted to acquire multiple locks with the same rank %s", (Object)acquiredLock.getLockName());
         if (!this.allowedPriorLocks.containsKey(acquiredLock)) {
            PotentialDeadlockException previousDeadlockException = (PotentialDeadlockException)this.disallowedPriorLocks.get(acquiredLock);
            if (previousDeadlockException != null) {
               PotentialDeadlockException exception = new PotentialDeadlockException(acquiredLock, this, previousDeadlockException.getConflictingStackTrace());
               policy.handlePotentialDeadlock(exception);
            } else {
               Set seen = Sets.newIdentityHashSet();
               ExampleStackTrace path = acquiredLock.findPathTo(this, seen);
               if (path == null) {
                  this.allowedPriorLocks.put(acquiredLock, new ExampleStackTrace(acquiredLock, this));
               } else {
                  PotentialDeadlockException exception = new PotentialDeadlockException(acquiredLock, this, path);
                  this.disallowedPriorLocks.put(acquiredLock, exception);
                  policy.handlePotentialDeadlock(exception);
               }

            }
         }
      }

      @Nullable
      private ExampleStackTrace findPathTo(LockGraphNode node, Set seen) {
         if (!seen.add(this)) {
            return null;
         } else {
            ExampleStackTrace found = (ExampleStackTrace)this.allowedPriorLocks.get(node);
            if (found != null) {
               return found;
            } else {
               Iterator var4 = this.allowedPriorLocks.entrySet().iterator();

               Map.Entry entry;
               LockGraphNode preAcquiredLock;
               do {
                  if (!var4.hasNext()) {
                     return null;
                  }

                  entry = (Map.Entry)var4.next();
                  preAcquiredLock = (LockGraphNode)entry.getKey();
                  found = preAcquiredLock.findPathTo(node, seen);
               } while(found == null);

               ExampleStackTrace path = new ExampleStackTrace(preAcquiredLock, this);
               path.setStackTrace(((ExampleStackTrace)entry.getValue()).getStackTrace());
               path.initCause(found);
               return path;
            }
         }
      }
   }

   private interface CycleDetectingLock {
      LockGraphNode getLockGraphNode();

      boolean isAcquiredByCurrentThread();
   }

   @Beta
   public static final class PotentialDeadlockException extends ExampleStackTrace {
      private final ExampleStackTrace conflictingStackTrace;

      private PotentialDeadlockException(LockGraphNode node1, LockGraphNode node2, ExampleStackTrace conflictingStackTrace) {
         super(node1, node2);
         this.conflictingStackTrace = conflictingStackTrace;
         this.initCause(conflictingStackTrace);
      }

      public ExampleStackTrace getConflictingStackTrace() {
         return this.conflictingStackTrace;
      }

      public String getMessage() {
         StringBuilder message = new StringBuilder(super.getMessage());

         for(Throwable t = this.conflictingStackTrace; t != null; t = ((Throwable)t).getCause()) {
            message.append(", ").append(((Throwable)t).getMessage());
         }

         return message.toString();
      }

      // $FF: synthetic method
      PotentialDeadlockException(LockGraphNode x0, LockGraphNode x1, ExampleStackTrace x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class ExampleStackTrace extends IllegalStateException {
      static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
      static final ImmutableSet EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());

      ExampleStackTrace(LockGraphNode node1, LockGraphNode node2) {
         super(node1.getLockName() + " -> " + node2.getLockName());
         StackTraceElement[] origStackTrace = this.getStackTrace();
         int i = 0;

         for(int n = origStackTrace.length; i < n; ++i) {
            if (WithExplicitOrdering.class.getName().equals(origStackTrace[i].getClassName())) {
               this.setStackTrace(EMPTY_STACK_TRACE);
               break;
            }

            if (!EXCLUDED_CLASS_NAMES.contains(origStackTrace[i].getClassName())) {
               this.setStackTrace((StackTraceElement[])Arrays.copyOfRange(origStackTrace, i, n));
               break;
            }
         }

      }
   }

   @Beta
   public static final class WithExplicitOrdering extends CycleDetectingLockFactory {
      private final Map lockGraphNodes;

      @VisibleForTesting
      WithExplicitOrdering(Policy policy, Map lockGraphNodes) {
         super(policy, null);
         this.lockGraphNodes = lockGraphNodes;
      }

      public ReentrantLock newReentrantLock(Enum rank) {
         return this.newReentrantLock(rank, false);
      }

      public ReentrantLock newReentrantLock(Enum rank, boolean fair) {
         return (ReentrantLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED ? new ReentrantLock(fair) : new CycleDetectingReentrantLock((LockGraphNode)this.lockGraphNodes.get(rank), fair));
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(Enum rank) {
         return this.newReentrantReadWriteLock(rank, false);
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(Enum rank, boolean fair) {
         return (ReentrantReadWriteLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED ? new ReentrantReadWriteLock(fair) : new CycleDetectingReentrantReadWriteLock((LockGraphNode)this.lockGraphNodes.get(rank), fair));
      }
   }

   @Beta
   public static enum Policies implements Policy {
      THROW {
         public void handlePotentialDeadlock(PotentialDeadlockException e) {
            throw e;
         }
      },
      WARN {
         public void handlePotentialDeadlock(PotentialDeadlockException e) {
            CycleDetectingLockFactory.logger.log(Level.SEVERE, "Detected potential deadlock", e);
         }
      },
      DISABLED {
         public void handlePotentialDeadlock(PotentialDeadlockException e) {
         }
      };

      private Policies() {
      }

      // $FF: synthetic method
      Policies(Object x2) {
         this();
      }
   }

   @Beta
   @ThreadSafe
   public interface Policy {
      void handlePotentialDeadlock(PotentialDeadlockException var1);
   }
}
