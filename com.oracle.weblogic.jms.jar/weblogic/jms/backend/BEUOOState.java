package weblogic.jms.backend;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.naming.NamingException;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.extensions.JMSOrderException;
import weblogic.jms.server.SequenceData;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.path.helper.KeyString;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.utils.collections.CircularQueue;
import weblogic.work.ContextWrap;
import weblogic.work.InheritableThreadContext;

abstract class BEUOOState implements BEExtension {
   private static long PATH_SERVICE_RESUME_RETRY_DELAY = 400L;
   private static boolean verbose = false;
   private static int QOS_STORE_OWNED_CACHE_ON_EQUAL = 49216;
   private static long PATH_SERVICE_DELETE_RETRY_DELAY = 120000L;
   public static HashMap TODOremoveDebug = new HashMap();
   private static boolean delayedRemoveRunning;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AbstractSubject anonymous = SubjectManager.getSubjectManager().getAnonymousSubject();
   private static final Object retryListLock = BEUOOState.class;
   private static HashMap delayedRemoves;
   private static CircularQueue delayedRemove = new CircularQueue(4);
   private DDHandler ddHandler;
   private BEDestinationImpl destination;
   private HashMap uooStates = new HashMap();
   private HashMap uowStates = new HashMap();

   protected BEUOOState(BEDestinationImpl destination, DDHandler dd) {
      this.ddHandler = dd;
      this.destination = destination;

      assert !verbose || null == TODOremoveDebug.put(destination.getName(), this);

   }

   public final void sendExtension(BEProducerSendRequest request) throws JMSException {
      int uooOpcode = getUOOSequenceOpcode(request);
      Sequence sequence = request.getSequence();
      String uoo = request.getMessage().getUnitOfOrder();
      byte indexKey = 1;
      if (uoo == null && this.destination.isUOWDestination()) {
         uoo = request.getMessage().getStringProperty("JMS_BEA_UnitOfWork");
         indexKey = 5;
      }

      if (uooOpcode == 0) {
         if (uoo == null) {
            return;
         }

         if (request.getCheckUOO() == 0) {
            return;
         }
      } else {
         if (sequence == null) {
            throw new weblogic.jms.common.JMSException("no Sequence for control message " + Integer.toHexString(uooOpcode) + " for destination " + this.destination.getName());
         }

         if (!"Hash".equals(this.ddHandler.getUnitOfOrderRouting())) {
            request.setState(Integer.MAX_VALUE);
            return;
         }

         if (uooOpcode != 196608) {
            assert 131072 >= uooOpcode;

            this.controlSequenceRelease(request);
            return;
         }

         if (uoo == null) {
            throw new weblogic.jms.common.JMSException("no Unit of Order for Reserve " + this.destination.getName());
         }
      }

      request.setCheckUOO(0);

      assert "PathService".equals(this.ddHandler.getUnitOfOrderRouting());

      request.setUOOInfo(getNewKeyString(this.ddHandler, uoo, indexKey), new BEUOOMember(this.destination.getName(), ManagementService.getRuntimeAccess(kernelId).getServerName(), true), new PathServiceCompReqListener(request));
      State state;
      synchronized(this) {
         state = this.findOrCreateState(uoo, true, indexKey);
      }

      state.setupPutIfAbsent(request);
   }

   private void controlSequenceRelease(BEProducerSendRequest request) throws JMSException {
      CircularQueue removeStates = new CircularQueue(4);
      JMSException jmse = null;

      try {
         synchronized(this) {
            Iterator iterator = this.uooStates.values().iterator();

            while(true) {
               if (!iterator.hasNext()) {
                  break;
               }

               State state = (State)iterator.next();
               synchronized(state) {
                  PathServiceRemoveRetry remove = this.removeLienInternal(request.getSequence(), state.uoo);
                  if (remove != null) {
                     removeStates.add(remove);
                  }
               }
            }
         }
      } finally {
         try {
            request.getSequence().delete(false);
         } catch (KernelException var36) {
            jmse = new weblogic.jms.common.JMSException(var36);
         } finally {
            while(!removeStates.isEmpty()) {
               ((PathServiceRemoveRetry)removeStates.remove()).processRemove();
            }

         }

      }

      if (jmse != null) {
         throw jmse;
      } else {
         request.setState(Integer.MAX_VALUE);
      }
   }

   private static int getUOOSequenceOpcode(BEProducerSendRequest request) {
      int opcode = request.getMessage().getControlOpcode();
      if (opcode != 0) {
         assert opcode >= 65536;

         if (opcode > 196608) {
            opcode = 0;
         }
      }

      return opcode;
   }

   public final void sequenceExtension(BEProducerSendRequest request) throws JMSException {
      Sequence sequence = request.getSequence();
      if (sequence != null) {
         String uoo = request.getMessage().getUnitOfOrder();
         if (verbose) {
            System.out.println("BEUOOState " + uoo + " Sequence " + sequence.getName());
         }

         SequenceData oldSequenceData = (SequenceData)sequence.getUserData();
         String oldSequenceUOO;
         SequenceData newSequenceData;
         if (oldSequenceData == null) {
            if (uoo == null) {
               return;
            }

            oldSequenceUOO = null;
            newSequenceData = new SequenceData();
         } else {
            oldSequenceUOO = oldSequenceData.getUnitOfOrder();
            newSequenceData = oldSequenceData.copy();
         }

         boolean bothUooAreEqual;
         if (oldSequenceUOO == uoo) {
            if (uoo == null) {
               return;
            }

            bothUooAreEqual = true;
         } else {
            bothUooAreEqual = uoo != null && uoo.equals(oldSequenceUOO);
         }

         if (!bothUooAreEqual) {
            PathServiceRemoveRetry stableRemoveRetry = null;

            try {
               synchronized(this) {
                  if (oldSequenceUOO != null) {
                     stableRemoveRetry = this.removeLienInternal(sequence, oldSequenceUOO);
                  }

                  newSequenceData.setUnitOfOrder(uoo);

                  try {
                     sequence.setUserData(newSequenceData);
                  } catch (KernelException var16) {
                     throw new weblogic.jms.common.JMSException(var16);
                  }

                  if (uoo == null) {
                     return;
                  }

                  State state = request.getUooState();
                  if (state != null) {
                     state.addLienInternal(sequence);
                     return;
                  }
               }
            } finally {
               if (stableRemoveRetry != null) {
                  stableRemoveRetry.processRemove();
               }

            }

         }
      }
   }

   public final void unitOfWorkAddEvent(String uow) {
      this.addEvent(uow, (byte)5);
   }

   public final void groupAddEvent(String uoo) {
      this.addEvent(uoo, (byte)1);
   }

   private final void addEvent(String uoo, byte indexKey) {
      synchronized(this) {
         State state = this.findOrCreateState(uoo, false, indexKey);
         synchronized(state) {
            state.sendSuccessBeforeGroupAdd = false;
            state.hadRemoveGroup = false;
            state.hadCreateGroup = true;
            if (state.hadRemoveGroup && (verbose || PathHelper.PathSvc.isDebugEnabled())) {
               PathHelper.PathSvc.debug("DEBUG onGroupAdd " + state);
            }

            state.cancelPendingRemove();

            assert !state.isRemovable();
         }

      }
   }

   public final void unitOfWorkRemoveEvent(String uow) {
      this.removeEvent(uow, (byte)5);
   }

   public final void groupRemoveEvent(String uoo) {
      this.removeEvent(uoo, (byte)1);
   }

   private final void removeEvent(String uoo, byte indexKey) {
      PathServiceRemoveRetry stableRemoveRetry;
      synchronized(this) {
         State state = this.findState(uoo, indexKey);
         if (state == null) {
            if (verbose || PathHelper.PathSvc.isDebugEnabled()) {
               PathHelper.PathSvc.debug("DEBUG BEUOOState missing for " + uoo);
            }

            return;
         }

         synchronized(state) {
            assert !state.isInvalid;

            if (!state.hadCreateGroup && (verbose || PathHelper.PathSvc.isDebugEnabled())) {
               PathHelper.PathSvc.debug("DEBUG new state " + uoo + " without CreateGroup " + state);
            }

            assert state.hadCreateGroup;

            state.hadRemoveGroup = state.hadCreateGroup = true;
            stableRemoveRetry = state.setupRemoveRetry((PathServiceRemoveRetry)null);
         }
      }

      if (stableRemoveRetry != null) {
         stableRemoveRetry.processRemove();
      }
   }

   private void retryPathServiceLater(PathServiceRemoveRetry restartRequest) {
      synchronized(retryListLock) {
         if (delayedRemoves == null) {
            delayedRemoves = new HashMap();
         }

         PathServiceRemoveRetry old = (PathServiceRemoveRetry)delayedRemoves.put(restartRequest.getKey(), restartRequest);
         if (old != null) {
            delayedRemoves.put(restartRequest.getKey(), old);
            if (old.member == null && restartRequest.member != null) {
               old.member = restartRequest.member;
            }

            return;
         }

         if (delayedRemove.contains(restartRequest)) {
            return;
         }

         delayedRemove.add(restartRequest);
         if (delayedRemove.size() != 1) {
            return;
         }
      }

      this.destination.getBackEnd().getTimerManager().schedule(restartRequest, PATH_SERVICE_DELETE_RETRY_DELAY);
   }

   private static KeyString getNewKeyString(DDHandler ddHandler, String uoo, byte indexKey) {
      return new KeyString(indexKey, ddHandler.getName(), uoo);
   }

   private static void doDebug(BEProducerSendRequest request, BEUOOMember storedMember, Throwable t) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("BESend Key:" + request.getUOOKey() + ", got other member:" + storedMember + ", guessed:" + request.getUOOMember(), t);
      } else if (PathHelper.PathSvc.isDebugEnabled()) {
         PathHelper.PathSvc.debug("BESend Key:" + request.getUOOKey() + ", got other member:" + storedMember + ", guessed:" + request.getUOOMember(), t);
      }

   }

   private static void pathBackDebug(String dbg) {
      if (PathHelper.PathSvc.isDebugEnabled()) {
         PathHelper.PathSvc.debug(dbg);
      } else {
         JMSDebug.JMSBackEnd.debug(dbg);
      }

   }

   private static void dispatcherPathBackDebug(String dbg, Throwable thrown) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug(dbg, thrown);
      } else if (PathHelper.PathSvc.isDebugEnabled()) {
         PathHelper.PathSvc.debug(dbg, thrown);
      } else {
         JMSDebug.JMSBackEnd.debug(dbg, thrown);
      }

   }

   private HashMap getStates(byte indexKey) {
      return indexKey == 1 ? this.uooStates : this.uowStates;
   }

   private State removeState(String uoo, byte indexKey) {
      HashMap states = this.getStates(indexKey);
      return (State)states.remove(uoo);
   }

   private State findState(String uoo, byte indexKey) {
      HashMap states = this.getStates(indexKey);
      return (State)states.get(uoo);
   }

   private void storeState(String uoo, byte indexKey, State state) {
      HashMap states = this.getStates(indexKey);
      states.put(uoo, state);
   }

   private State findOrCreateState(String uoo, boolean incrementSendInProgress, byte indexKey) {
      State state = this.findState(uoo, indexKey);
      if (state == null) {
         state = new State(uoo, incrementSendInProgress, indexKey);
         this.storeState(uoo, indexKey, state);
      } else if (incrementSendInProgress) {
         state.incrementSendInProgress();
      }

      assert !state.isInvalid;

      return state;
   }

   private PathServiceRemoveRetry removeLienInternal(Sequence sequence, String uoo) {
      State state = this.findState(uoo, (byte)1);
      if (state == null) {
         return null;
      } else {
         synchronized(state) {
            if (state.isInvalid) {
               return null;
            } else {
               boolean oldIsRemovable = state.isRemovable();
               if (!state.removeLienInternal(sequence)) {
                  return null;
               } else {
                  if (PathHelper.PathSvc.isDebugEnabled()) {
                     PathHelper.PathSvc.debug("DEBUG removed " + sequence.getName() + " from uoo " + state.uoo);
                  }

                  if (state.isRemovable() && !oldIsRemovable) {
                     if (PathHelper.PathSvc.isDebugEnabled()) {
                        PathHelper.PathSvc.debug("releasing " + state.uoo);
                     }

                     return state.setupRemoveRetry((PathServiceRemoveRetry)null);
                  } else {
                     return null;
                  }
               }
            }
         }
      }
   }

   private void addLienInternal(Sequence sequence, String uoo) {
      this.findOrCreateState(uoo, false, (byte)1).addLienInternal(sequence);
   }

   public void restorePersistentState(Destination kernelQueue) {
      Iterator iterator = kernelQueue.getSequences().iterator();
      synchronized(this) {
         while(iterator.hasNext()) {
            Sequence sequence = (Sequence)iterator.next();
            SequenceData data = (SequenceData)sequence.getUserData();
            if (data != null) {
               String uoo = data.getUnitOfOrder();
               if (uoo != null) {
                  this.addLienInternal(sequence, uoo);
               }
            }
         }

      }
   }

   private class DelayedPutIfAbsent extends ContextWrap {
      BEProducerSendRequest request;
      State state;

      DelayedPutIfAbsent(final BEProducerSendRequest request, final State state) {
         super(new Runnable() {
            public void run() {
               if (PathHelper.PathSvc.isDebugEnabled() || JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  BEUOOState.pathBackDebug("BEDest resumed putIfAbsent: " + request.getUOOKey() + " request: " + request);
               }

               try {
                  state.callPutIfAbsent(request);
               } catch (Throwable var2) {
                  request.resumeRequest(var2, false);
               }

            }
         });
         this.request = request;
         this.state = state;
      }
   }

   class State {
      private PathServiceRemoveRetry pendingRemove;
      private PathServiceRemoveRetry psRemoveInProgress;
      private BEProducerSendRequest putIfAbsentInProgress;
      private HashSet sequences;
      private boolean hadCreateGroup;
      private boolean hadRemoveGroup;
      private boolean sendSuccessBeforeGroupAdd;
      private boolean isInvalid;
      private int sendInProgress;
      private String uoo;
      private CircularQueue waitingSends;
      private byte indexKey;

      State(String name, boolean incrementSendInProgress, byte indexKey) {
         this.uoo = name;
         this.indexKey = indexKey;
         if (incrementSendInProgress) {
            ++this.sendInProgress;
         }

      }

      private synchronized void incrementSendInProgress() {
         ++this.sendInProgress;
      }

      BEUOOState getBEUOOState() {
         return BEUOOState.this;
      }

      boolean isRemovable() {
         return this.hadCreateGroup == this.hadRemoveGroup && this.sendInProgress == 0 && !this.sendSuccessBeforeGroupAdd && (this.sequences == null || this.sequences.isEmpty());
      }

      private boolean removableWithDefaultValues() {
         return this.pendingRemove == null && this.psRemoveInProgress == null && !this.hadCreateGroup;
      }

      synchronized void addLienInternal(Sequence sequence) {
         if (this.sequences == null) {
            this.sequences = new HashSet();
         }

         if (PathHelper.PathSvc.isDebugEnabled()) {
            PathHelper.PathSvc.debug("lien " + this.uoo + " add sequence " + sequence + ", " + this);
         }

         this.sequences.add(sequence);
      }

      boolean removeLienInternal(Sequence sequence) {
         if (this.sequences == null) {
            return false;
         } else {
            boolean found = this.sequences.remove(sequence);
            if (PathHelper.PathSvc.isDebugEnabled()) {
               PathHelper.PathSvc.debug("remove lien " + sequence.getName() + " from uoo " + this.uoo);
            }

            if (this.sequences.isEmpty()) {
               this.sequences = null;
            }

            return found;
         }
      }

      void removeReference(BEProducerSendRequest request, boolean success) {
         PathServiceRemoveRetry stableRemoveRetry = null;
         DelayedPutIfAbsent resumePutIfAbsent = null;

         try {
            synchronized(BEUOOState.this) {
               synchronized(this) {
                  resumePutIfAbsent = this.computeSendToResume(request);
                  request.setUooState((State)null);
                  --this.sendInProgress;
                  if (success && !this.hadCreateGroup && !this.isInvalid) {
                     this.sendSuccessBeforeGroupAdd = true;
                  }

                  if (resumePutIfAbsent != null) {
                     return;
                  }

                  if (!success) {
                     if (!this.isRemovable()) {
                        return;
                     }

                     if (this.removableWithDefaultValues()) {
                        Object old = BEUOOState.this.removeState(this.uoo, this.indexKey);

                        assert this == old;

                        this.isInvalid = true;
                        return;
                     }

                     if (!this.hadRemoveGroup) {
                        return;
                     }

                     stableRemoveRetry = this.setupRemoveRetry((PathServiceRemoveRetry)null);
                     return;
                  }
               }
            }
         } finally {
            if (resumePutIfAbsent != null) {
               BEUOOState.this.destination.getBackEnd().getWorkManager().schedule(resumePutIfAbsent);
            } else if (stableRemoveRetry != null) {
               stableRemoveRetry.processRemove();
            }

         }

      }

      private void setupPutIfAbsent(BEProducerSendRequest request) throws JMSException {
         synchronized(request) {
            request.setState(1101);
            request.needOutsideResult();
            request.rememberThreadContext();
            request.getCompletionRequest().addListener(request);
         }

         synchronized(this) {
            request.setUooState(this);
            if (this.psRemoveInProgress != null || this.putIfAbsentInProgress != null) {
               if (this.waitingSends == null) {
                  this.waitingSends = new CircularQueue(4);
               }

               this.waitingSends.add(BEUOOState.this.new DelayedPutIfAbsent(request, this));
               if (PathHelper.PathSvc.isDebugEnabled() || JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  BEUOOState.pathBackDebug("setupPutIfAbsent sees " + this.putIfAbsentInProgress + " send with UOO: " + request.getUOOKey() + " request: " + request);
               }

               return;
            }

            this.putIfAbsentInProgress = request;
         }

         if (PathHelper.PathSvc.isDebugEnabled() || JMSDebug.JMSBackEnd.isDebugEnabled()) {
            BEUOOState.pathBackDebug("BEDest inline putIfAbsent key: " + request.getUOOKey() + ", member: " + request.getUOOMember());
         }

         this.callPutIfAbsent(request);
      }

      private synchronized DelayedPutIfAbsent computeSendToResume(BEProducerSendRequest sendCompletedPutIfAbsent) {
         if (sendCompletedPutIfAbsent == null) {
            this.putIfAbsentInProgress = null;
            this.psRemoveInProgress = null;
         } else {
            if (this.putIfAbsentInProgress != sendCompletedPutIfAbsent && this.putIfAbsentInProgress != null) {
               boolean found = false;
               if (this.waitingSends != null) {
                  Iterator iterator = this.waitingSends.iterator();

                  while(iterator.hasNext()) {
                     Object next = iterator.next();
                     if (next == sendCompletedPutIfAbsent) {
                        found = true;
                        iterator.remove();
                        break;
                     }
                  }
               }

               if (found && this.waitingSends.isEmpty()) {
                  this.waitingSends = null;
               }

               if (PathHelper.PathSvc.isDebugEnabled() || JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  BEUOOState.pathBackDebug("computeSendToResume found=" + found + " waiting has " + (this.waitingSends == null ? "zero" : "" + this.waitingSends.size()) + ", sendCompletedPutIfAbsent:" + sendCompletedPutIfAbsent + " is not putIfAbsentInProgress:" + this.putIfAbsentInProgress);
               }

               return null;
            }

            this.putIfAbsentInProgress = null;
         }

         if (this.waitingSends == null) {
            return null;
         } else {
            DelayedPutIfAbsent resumed = (DelayedPutIfAbsent)this.waitingSends.remove();
            if (this.waitingSends.isEmpty()) {
               this.waitingSends = null;
            }

            this.putIfAbsentInProgress = resumed.request;
            if (PathHelper.PathSvc.isDebugEnabled() || JMSDebug.JMSBackEnd.isDebugEnabled()) {
               BEUOOState.pathBackDebug("BEDest putIfAbsentInProgress: " + resumed.request.getUOOKey() + " state: " + resumed.request.getState() + " request: " + resumed.request);
            }

            return resumed;
         }
      }

      private void callPutIfAbsent(BEProducerSendRequest request) {
         assert this.putIfAbsentInProgress == request;

         try {
            SubjectManager.getSubjectManager().pushSubject(BEUOOState.kernelId, BEUOOState.anonymous);
            BEUOOState.this.destination.backEnd.findOrCreateServerInfo(request.getUOOKey()).cachedPutIfAbsent(request.getUOOKey(), request.getUOOMember(), BEUOOState.QOS_STORE_OWNED_CACHE_ON_EQUAL, request.getCompletionRequest());
         } catch (NamingException var11) {
            boolean noResultYet;
            synchronized(request.getCompletionRequest()) {
               noResultYet = !request.getCompletionRequest().hasResult();
            }

            if (noResultYet) {
               request.getCompletionRequest().setResult(new JMSOrderException("path service not available", var11));
            }
         } finally {
            SubjectManager.getSubjectManager().popSubject(BEUOOState.kernelId);
         }

      }

      void processPathServiceResult(BEProducerSendRequest request) {
         CompletionRequest cr = request.getCompletionRequest();
         BEUOOMember storedMember = null;

         try {
            storedMember = (BEUOOMember)cr.getResult();
            if (storedMember == null) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled() || PathHelper.PathSvc.isDebugEnabled()) {
                  BEUOOState.pathBackDebug("BESend stored success State:" + request.getState() + ", Key: " + request.getUOOKey() + ", got: " + storedMember + ", guessed: " + request.getUOOMember());
               }

               synchronized(request) {
                  request.setState(1102);
               }

               request.resumeExecution(false);
               return;
            }

            if (BEUOOState.this.destination.getName().equals(storedMember.getMemberId())) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled() || PathHelper.PathSvc.isDebugEnabled()) {
                  BEUOOState.pathBackDebug("BESend success State:" + request.getState() + ", Key: " + request.getUOOKey() + ", got: " + storedMember + ", guessed: " + request.getUOOMember());
               }

               request.setUOOInfo(request.getUOOKey(), storedMember, cr);
               synchronized(request) {
                  request.setState(1102);
               }

               request.resumeExecution(false);
               return;
            }
         } catch (Throwable var9) {
            BEUOOState.doDebug(request, storedMember, var9);
            request.resumeRequest(var9, true);
            return;
         }

         JMSOrderException jmsoe = new JMSOrderException("Unit of Order on Distributed Destination " + BEUOOState.this.destination.getName() + " rather than " + storedMember.getMemberId());
         jmsoe.setMember(storedMember);
         BEUOOState.doDebug(request, storedMember, jmsoe);
         request.resumeRequest(jmsoe, true);
      }

      private void cancelPendingRemove() {
         PathServiceRemoveRetry stablePendingRemove = this.pendingRemove;
         if (stablePendingRemove != null) {
            DelayedPutIfAbsent delayedPutIfAbsent;
            synchronized(stablePendingRemove) {
               stablePendingRemove.cancel();
               this.pendingRemove = null;
               delayedPutIfAbsent = this.computeSendToResume((BEProducerSendRequest)null);

               assert this.psRemoveInProgress == null;

               synchronized(BEUOOState.retryListLock) {
                  if (BEUOOState.delayedRemoves == null) {
                     return;
                  }

                  PathServiceRemoveRetry old = (PathServiceRemoveRetry)BEUOOState.delayedRemoves.remove(stablePendingRemove.getKey());
                  if (old != null && old != stablePendingRemove) {
                     old.cancel();
                     BEUOOState.delayedRemove.remove(stablePendingRemove);
                     break label15;
                  }
               }

               return;
            }

            if (delayedPutIfAbsent != null) {
               BEUOOState.this.destination.getBackEnd().getWorkManager().schedule(delayedPutIfAbsent);
            }

         }
      }

      private PathServiceRemoveRetry setupRemoveRetry(PathServiceRemoveRetry current) {
         PathServiceRemoveRetry newPathServiceRemove = BEUOOState.this.new PathServiceRemoveRetry(BEUOOState.getNewKeyString(BEUOOState.this.ddHandler, this.uoo, this.indexKey), (BEUOOMember)null);
         synchronized(this) {
            if (current != this.pendingRemove) {
               if (PathHelper.PathSvc.isDebugEnabled()) {
                  PathHelper.PathSvc.debug("different remove pending" + this);
               }

               return null;
            } else if (!this.isRemovable()) {
               if (PathHelper.PathSvc.isDebugEnabled()) {
                  PathHelper.PathSvc.debug("DEBUG not removed on groupRemoveEvent " + this);
               }

               this.cancelPendingRemove();
               return null;
            } else {
               if (PathHelper.PathSvc.isDebugEnabled()) {
                  PathHelper.PathSvc.debug("DEBUG removing " + this);
               }

               if (this.pendingRemove == null) {
                  newPathServiceRemove.setState(this);
                  this.pendingRemove = newPathServiceRemove;
               }

               return this.pendingRemove;
            }
         }
      }

      private boolean completeRemove() {
         synchronized(BEUOOState.this) {
            boolean var10000;
            synchronized(this) {
               if (this.isRemovable()) {
                  if (BEUOOState.verbose) {
                     Object oldx = BEUOOState.TODOremoveDebug.remove(BEUOOState.this.destination.getName());
                     if (oldx != BEUOOState.this && null != oldx) {
                        BEUOOState.TODOremoveDebug.put(BEUOOState.this.destination.getName(), oldx);
                     }
                  }

                  State old = BEUOOState.this.removeState(this.uoo, this.indexKey);
                  if (old != this && null != old) {
                     BEUOOState.this.storeState(this.uoo, this.indexKey, old);
                  }

                  this.isInvalid = true;
                  var10000 = true;
                  return var10000;
               }

               var10000 = false;
            }

            return var10000;
         }
      }

      public String toString() {
         return "beUOOState.state uoo=" + this.uoo + " create=" + this.hadCreateGroup + " |remove=" + this.hadRemoveGroup + " |pending=" + this.pendingRemove + " |sendInProgress=" + this.sendInProgress + " |successBeforeAdd=" + this.sendSuccessBeforeGroupAdd + " |isRemovable=" + this.isRemovable() + " |sequences=" + this.sequences;
      }
   }

   private final class PathServiceRemoveRetry implements TimerListener, Runnable {
      private State state;
      private KeyString key;
      private BEUOOMember member;
      private boolean cancelled;

      PathServiceRemoveRetry(KeyString keyArg, BEUOOMember memberArg) {
         this.key = keyArg;
         this.member = memberArg;
      }

      private void setState(State s) {
         this.state = s;
      }

      private void cancel() {
         this.cancelled = true;
      }

      protected boolean processRemove() {
         synchronized(this.state) {
            if (this.state.setupRemoveRetry(this) != this) {
               this.cancel();
               return true;
            }
         }

         boolean var2;
         try {
            SubjectManager.getSubjectManager().pushSubject(BEUOOState.kernelId, BEUOOState.anonymous);
            BEUOOState.this.destination.backEnd.findOrCreateServerInfo(this.key).cachedGet(this.key, 32832, this.getCompReqListener());
            return true;
         } catch (NamingException var7) {
            BEUOOState.this.retryPathServiceLater(this);
            var2 = false;
         } finally {
            SubjectManager.getSubjectManager().popSubject(BEUOOState.kernelId);
         }

         return var2;
      }

      private CompReqListener getCompReqListener() {
         return new CompReqListener() {
            public final void onException(CompletionRequest cr, Throwable reason) {
               BEUOOState.this.retryPathServiceLater(PathServiceRemoveRetry.this);
            }

            public final void onCompletion(CompletionRequest ignored, Object result) {
               PathServiceRemoveRetry.this.member = (BEUOOMember)result;
               if (PathServiceRemoveRetry.this.member != null && PathServiceRemoveRetry.this.member.getDynamic() && PathServiceRemoveRetry.this.member.getMemberId().equals(BEUOOState.this.destination.getName())) {
                  CompReqListener resume = new CompReqListener() {
                     public final void onException(CompletionRequest cr, Throwable reason) {
                        BEUOOState.this.retryPathServiceLater(PathServiceRemoveRetry.this);
                     }

                     public final void onCompletion(CompletionRequest ignored, Object result) {
                        PathServiceRemoveRetry.this.completeProcessing();
                     }
                  };
                  boolean cancelledSnapshot;
                  synchronized(BEUOOState.retryListLock) {
                     cancelledSnapshot = PathServiceRemoveRetry.this.cancelled;
                  }

                  if (cancelledSnapshot) {
                     resume.onCompletion(resume, Boolean.FALSE);
                  } else {
                     try {
                        SubjectManager.getSubjectManager().pushSubject(BEUOOState.kernelId, BEUOOState.anonymous);
                        BEUOOState.this.destination.backEnd.findOrCreateServerInfo(PathServiceRemoveRetry.this.key).cachedRemove(PathServiceRemoveRetry.this.key, PathServiceRemoveRetry.this.member, 33352, resume);
                     } catch (NamingException var11) {
                        BEUOOState.this.retryPathServiceLater(PathServiceRemoveRetry.this);
                     } finally {
                        SubjectManager.getSubjectManager().popSubject(BEUOOState.kernelId);
                     }

                  }
               } else {
                  PathHelper.PathSvc.debug("DEBUG not deleting key:" + PathServiceRemoveRetry.this.key + " , value: " + PathServiceRemoveRetry.this.member + " from PathService on " + BEUOOState.this.destination.getName());
                  PathServiceRemoveRetry.this.completeProcessing();
               }
            }
         };
      }

      private KeyString getKey() {
         return this.key;
      }

      public final void timerExpired(Timer timer) {
         this.processPendingEntry();
      }

      public final void run() {
         this.processPendingEntry();
      }

      private void processPendingEntry() {
         boolean amRunningThread = false;

         while(true) {
            boolean var20 = false;

            label216: {
               label217: {
                  try {
                     label218: {
                        var20 = true;
                        PathServiceRemoveRetry current;
                        synchronized(BEUOOState.retryListLock) {
                           if (!amRunningThread && BEUOOState.delayedRemoveRunning) {
                              var20 = false;
                              break label218;
                           }

                           amRunningThread = BEUOOState.delayedRemoveRunning = true;
                           if (BEUOOState.delayedRemoves == null || BEUOOState.delayedRemove.isEmpty()) {
                              var20 = false;
                              break label217;
                           }

                           current = (PathServiceRemoveRetry)BEUOOState.delayedRemove.remove();
                           BEUOOState.delayedRemoves.remove(current.key);
                        }

                        boolean runAgain = current.processRemove();
                        synchronized(BEUOOState.retryListLock) {
                           if (runAgain) {
                              if (!BEUOOState.this.destination.getBackEnd().getWorkManager().scheduleIfBusy(this)) {
                                 continue;
                              }

                              amRunningThread = false;
                              BEUOOState.delayedRemoveRunning = false;
                              var20 = false;
                           } else {
                              var20 = false;
                           }
                           break label216;
                        }
                     }
                  } finally {
                     if (var20) {
                        if (amRunningThread) {
                           synchronized(BEUOOState.retryListLock) {
                              BEUOOState.delayedRemoveRunning = false;
                           }
                        }

                     }
                  }

                  if (amRunningThread) {
                     synchronized(BEUOOState.retryListLock) {
                        BEUOOState.delayedRemoveRunning = false;
                     }
                  }

                  return;
               }

               if (amRunningThread) {
                  synchronized(BEUOOState.retryListLock) {
                     BEUOOState.delayedRemoveRunning = false;
                  }
               }

               return;
            }

            if (amRunningThread) {
               synchronized(BEUOOState.retryListLock) {
                  BEUOOState.delayedRemoveRunning = false;
               }
            }

            return;
         }
      }

      private void completeProcessing() {
         if (this.state.completeRemove()) {
            synchronized(BEUOOState.retryListLock) {
               if (BEUOOState.delayedRemoves == null) {
                  return;
               }

               BEUOOState.delayedRemoves.remove(this.key);
               BEUOOState.delayedRemove.remove(this);
               if (BEUOOState.delayedRemoveRunning || BEUOOState.delayedRemove.isEmpty()) {
                  return;
               }
            }

            BEUOOState.this.destination.getBackEnd().getTimerManager().schedule(this, BEUOOState.PATH_SERVICE_RESUME_RETRY_DELAY);
         }

      }
   }

   private static class PathServiceCompReqListener extends CompReqListener {
      private final InheritableThreadContext context = InheritableThreadContext.getContext();
      private final BEProducerSendRequest sendRequest;

      PathServiceCompReqListener(BEProducerSendRequest request) {
         this.sendRequest = request;
      }

      public final void run() {
         this.context.push();

         try {
            super.run();
         } finally {
            this.context.pop();
         }

      }

      public void setResult(Object o) {
         super.setResult(o);
      }

      public Object getResult() throws Throwable {
         return super.getResult();
      }

      public boolean hasResult() {
         return super.hasResult();
      }

      public void onCompletion(CompletionRequest compRequest, Object result) {
         State state = this.sendRequest.getUooState();
         DelayedPutIfAbsent resumePutIfAbsent;
         if (state == null) {
            resumePutIfAbsent = null;
         } else {
            resumePutIfAbsent = state.computeSendToResume(this.sendRequest);
         }

         State uooState = this.sendRequest.getUooState();

         try {
            uooState.processPathServiceResult(this.sendRequest);
         } finally {
            if (resumePutIfAbsent != null) {
               uooState.getBEUOOState().destination.getBackEnd().getWorkManager().schedule(resumePutIfAbsent);
            }

         }

      }

      public void onException(CompletionRequest compRequest, Throwable reason) {
         DelayedPutIfAbsent resumePutIfAbsent = this.sendRequest.getUooState().computeSendToResume(this.sendRequest);
         State uooState = this.sendRequest.getUooState();

         try {
            this.sendRequest.resumeRequest(PathHelper.wrapExtensionImpl(reason), false);
         } finally {
            if (resumePutIfAbsent != null) {
               uooState.getBEUOOState().destination.getBackEnd().getWorkManager().schedule(resumePutIfAbsent);
            }

         }

      }
   }

   private abstract static class CompReqListener extends CompletionRequest implements CompletionListener {
      CompReqListener() {
         this.addListener(this);
      }
   }
}
