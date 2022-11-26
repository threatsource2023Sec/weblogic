package weblogic.rmi.internal.dgc;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.Enrollable;
import weblogic.rmi.internal.PartitionAwareRef;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.utils.Debug;
import weblogic.utils.KeyTable;
import weblogic.utils.SyncKeyTable;

public final class DGCClientHelper implements Enrollable {
   private static boolean DEBUG = false;
   private static final Object chainLock = new Object();
   private static final Hashtable hostTable = new Hashtable();
   private static final Stack stack = new Stack();
   private static volatile DGCClientHelper chain = null;
   private volatile DGCClientHelper next = null;
   private volatile boolean hasBeenStacked = false;
   private static final DGCClientImpl dgcClientImpl = DGCClientImpl.getDGCClientImpl();
   private final DGCReferenceCounter counter;

   static Vector getHeartBeats() {
      synchronized(hostTable) {
         Vector heartBeats = new Vector();
         Enumeration e = hostTable.keys();

         while(true) {
            label47:
            while(true) {
               HostID hostID;
               EndPoint endPoint;
               do {
                  do {
                     if (!e.hasMoreElements()) {
                        return heartBeats;
                     }

                     hostID = (HostID)e.nextElement();
                     endPoint = RMIRuntime.findEndPoint(hostID);
                  } while(endPoint == null);
               } while(endPoint.isDead());

               ConcurrentHashMap partitionMap = (ConcurrentHashMap)hostTable.get(hostID);
               if (partitionMap != null && !partitionMap.isEmpty()) {
                  Iterator var6 = partitionMap.keySet().iterator();

                  while(true) {
                     label56:
                     while(true) {
                        if (!var6.hasNext()) {
                           continue label47;
                        }

                        String partition = (String)var6.next();
                        KeyTable workingSet = (KeyTable)partitionMap.get(partition);
                        if (workingSet != null && workingSet.size() != 0) {
                           Vector heartBeatEntry = new Vector();
                           heartBeats.addElement(heartBeatEntry);
                           Enumeration ea = workingSet.elements();

                           while(true) {
                              DGCReferenceCounter xRef;
                              do {
                                 do {
                                    if (!ea.hasMoreElements()) {
                                       continue label56;
                                    }

                                    xRef = (DGCReferenceCounter)ea.nextElement();
                                 } while(xRef == null);
                              } while(xRef.getCount() <= 0 && !xRef.leaseRenewed());

                              heartBeatEntry.addElement(xRef);
                              xRef.renewLease(false);
                           }
                        } else {
                           RMILogger.logEmptyWS("partition[" + partition + "] of " + hostID.toString());
                        }
                     }
                  }
               } else {
                  RMILogger.logEmptyWS(hostID.toString());
               }
            }
         }
      }
   }

   public static DGCClientHelper dequeue() {
      DGCClientHelper current;
      if (stack.empty()) {
         for(current = chain; current != null; current = current.next) {
            if (!current.hasBeenStacked) {
               current.hasBeenStacked = true;
               stack.push(current);
            }
         }

         if (stack.empty()) {
            return null;
         }
      }

      current = (DGCClientHelper)stack.pop();
      current.next = null;
      if (DEBUG) {
         System.out.println("\n*** returning " + current.counter.getOID());
      }

      return current;
   }

   public static void mark(Map remoteHostIDMap) {
      for(DGCClientHelper helper = dequeue(); helper != null; helper = dequeue()) {
         helper.decrement(remoteHostIDMap);
         if (DEBUG) {
            Debug.say("Finalized " + helper.counter.getOID() + " count = " + helper.counter.getCount());
         }
      }

   }

   public static DGCClientHelper findAndEnroll(RemoteReference ror) {
      DGCReferenceCounter counter;
      synchronized(hostTable) {
         ConcurrentHashMap partitionMap = (ConcurrentHashMap)hostTable.get(ror.getHostID());
         if (partitionMap == null) {
            if (ror.getHostID().isLocal()) {
               return null;
            }

            partitionMap = new ConcurrentHashMap();
            hostTable.put(ror.getHostID(), partitionMap);
            if (DEBUG) {
               Debug.say("\n\n\n***********Made partitionMap set for " + ror.getHostID());
            }
         }

         String currentPartition = "DOMAIN";
         if (KernelStatus.isServer()) {
            currentPartition = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         } else if (ror instanceof PartitionAwareRef) {
            currentPartition = ((PartitionAwareRef)ror).getPartitionName();
         }

         KeyTable workingSet = (KeyTable)partitionMap.get(currentPartition);
         if (workingSet == null) {
            if (ror.getHostID().isLocal()) {
               return null;
            }

            workingSet = new SyncKeyTable();
            partitionMap.put(currentPartition, workingSet);
            if (DEBUG) {
               Debug.say("\n\n\n***********Made working set for " + ror.getHostID());
            }
         }

         counter = (DGCReferenceCounter)((KeyTable)workingSet).get(ror.getObjectID());
         if (counter == null) {
            counter = new DGCReferenceCounter(ror, currentPartition);
            ((KeyTable)workingSet).put(counter);
         }

         counter.increment();
      }

      return new DGCClientHelper(counter);
   }

   private DGCClientHelper(DGCReferenceCounter counter) {
      this.counter = counter;
   }

   public synchronized void enroll() {
      this.counter.increment();
   }

   public void unenroll() {
      synchronized(chainLock) {
         this.next = chain;
         chain = this;
      }
   }

   public void renewLease() {
      this.counter.renewLease();
   }

   private void decrement(Map remoteHostIDMap) {
      synchronized(hostTable) {
         if (this.counter.decrement() == 0) {
            ConcurrentHashMap partitionMap = (ConcurrentHashMap)hostTable.get(this.counter.getHostID());
            if (partitionMap == null) {
               return;
            }

            KeyTable workingSet = (KeyTable)partitionMap.get(this.counter.getPartitionName());
            if (workingSet == null) {
               RMILogger.logNoWS(this.counter.getOID(), this.counter.getHostID().toString());
               return;
            }

            Object deadGuy = workingSet.remove(this.counter.getOID());
            if (deadGuy == null) {
               RMILogger.logNoRef(this.counter.getOID());
            }

            if (DEBUG) {
               Debug.say("... removed " + this.counter.getOID() + "");
            }

            if (workingSet.size() == 0) {
               partitionMap.remove(this.counter.getPartitionName());
               if (partitionMap.isEmpty()) {
                  hostTable.remove(this.counter.getHostID());
                  remoteHostIDMap.remove(this.counter.getHostID());
                  if (DEBUG) {
                     Debug.say("\n\n\n**********Removing workingSet  " + this.counter.getHostID() + "for all partitions from " + LocalServerIdentity.getIdentity());
                  }
               }
            }
         }

      }
   }
}
