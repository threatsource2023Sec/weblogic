package weblogic.jms.common;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import weblogic.jms.dd.DDConstants;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDMember;
import weblogic.jms.frontend.FESession;
import weblogic.rmi.extensions.RemoteHelper;

public final class JMSLoadBalancer implements DDConstants {
   private int sizePerMember;
   private int sizePerJVM;
   private boolean doInitPerJVM = true;
   private boolean doInitPerMember = true;
   private boolean isTopic;
   private boolean isPartitionedDistributedTopic;
   private LoadBalancer connectionConsumerLB;
   private LoadBalancer consumerLB;
   private LoadBalancer consumerAllLB;
   private LoadBalancer npProducerLB;
   private LoadBalancer pProducerLB;
   private LoadBalancer npProducerAllLB;
   private LoadBalancer pProducerAllLB;
   private LoadBalancer npProducerLBPerJVM;
   private LoadBalancer pProducerLBPerJVM;
   private LoadBalancer npProducerAllLBPerJVM;
   private LoadBalancer pProducerAllLBPerJVM;
   private DDHandler ddHandler;

   public JMSLoadBalancer(DDHandler ddHandler) {
      this.ddHandler = ddHandler;
      this.isTopic = !ddHandler.isQueue();
      this.isPartitionedDistributedTopic = ddHandler.getForwardingPolicy() == 0;
      this.refresh();
   }

   public void refresh() {
      synchronized(this.ddHandler) {
         if (this.isTopic && !this.isPartitionedDistributedTopic) {
            this.refreshTopic(true);
            this.refreshTopic(false);
         } else {
            this.refreshQueue(true);
            this.refreshQueue(false);
         }

      }
   }

   private DistributedDestinationImpl[] getUpMembers(boolean perJVM) {
      LinkedList tmp = new LinkedList();
      Iterator iter = this.ddHandler.memberCloneIterator();
      int order = 0;

      while(iter.hasNext()) {
         DDMember member = (DDMember)iter.next();
         DistributedDestinationImpl ddImpl = member.getDDImpl();
         if (member.isUp() && ddImpl != null) {
            if (ddImpl.getDispatcherId() == null) {
               member.setOutOfDate(true);
               ddImpl = member.getDDImpl();
               if (ddImpl != null && ddImpl.getDispatcherId() != null) {
                  ddImpl.setOrder(order++);
                  tmp.add(ddImpl);
               }
            } else {
               ddImpl.setOrder(order++);
               tmp.add(ddImpl);
            }
         }

         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug(this.ddHandler.getName() + " getUpMembers() " + member + " is up? " + member.isUp() + " DDImpl " + member.getDDImpl() + " member dipatcherId " + member.getDispatcherId() + ", member wlsServerName[" + member.getWLSServerName() + "]");
            if (ddImpl == null) {
               JMSDebug.JMSCommon.debug(this.ddHandler.getName() + " getUpMembers() UNEXPECTED... getDDImpl is null for member " + member);
            }

            if (ddImpl != null && ddImpl.getDispatcherId() == null && member.isUp()) {
               JMSDebug.JMSCommon.debug(this.ddHandler.getName() + " getUpMembers() UNEXPECTED... getDDImpl dispatcherId is null for member " + member);
            }
         }
      }

      if (perJVM) {
         Collections.sort(tmp, new JMSComparator(3, false));
         DistributedDestinationImpl lastM = null;
         ListIterator li = tmp.listIterator(0);

         while(true) {
            while(li.hasNext()) {
               DistributedDestinationImpl curM = (DistributedDestinationImpl)li.next();
               if (curM.isPossiblyClusterTargeted() && !curM.isOnPreferredServer() || lastM != null && curM.getWLSServerSortingName().equals(lastM.getWLSServerSortingName())) {
                  li.remove();
               } else {
                  lastM = curM;
               }
            }

            Collections.sort(tmp, new JMSComparator(2, false));
            break;
         }
      }

      DistributedDestinationImpl[] ret = (DistributedDestinationImpl[])((DistributedDestinationImpl[])tmp.toArray(new DistributedDestinationImpl[0]));
      return ret;
   }

   private void consumptionPauseFilter(DistributedDestinationImpl[] dests, int size) {
      int j = 0;

      for(int i = 0; i < size && dests[i] != null; ++i) {
         if (!dests[i].isConsumptionPaused()) {
            dests[j++] = dests[i];
         }
      }

      if (j != 0 && j < size) {
         dests[j] = null;
      }

   }

   private void productionPauseFilter(DistributedDestinationImpl[] dests, int size) {
      int j = 0;

      for(int i = 0; i < size && dests[i] != null; ++i) {
         if (!dests[i].isProductionPaused() && !dests[i].isInsertionPaused()) {
            dests[j++] = dests[i];
         }
      }

      if (j != 0 && j < size) {
         dests[j] = null;
      }

   }

   private void refreshQueue(boolean perJVM) {
      DistributedDestinationImpl[] allDests = this.getUpMembers(perJVM);
      int size = allDests.length;
      if (perJVM) {
         this.sizePerJVM = size;
      } else {
         this.sizePerMember = size;
      }

      if (size > 0) {
         Arrays.sort(allDests, new JMSComparator(2, false));
         int a = 0;
         int l = 0;
         int nc = 0;
         int c = 0;
         int p = 0;
         int pl = 0;
         int ncl = 0;
         int cl = 0;
         int pc = 0;
         int pcl = 0;
         DistributedDestinationImpl[] pclDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] pcDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] clDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] nclDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] plDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] pDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] cDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] ncDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] lDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] aDests = new DistributedDestinationImpl[size];
         int aDestsNoNonUPSCount = 0;
         int lDestsNoNonUPSCount = 0;
         int clDestsNoNonUPSCount = 0;
         int cDestsNoNonUPSCount = 0;
         DistributedDestinationImpl[] clDestsForNP = null;
         DistributedDestinationImpl[] cDestsForNP = null;
         DistributedDestinationImpl[] cAllDestsForNP = null;
         int aDestsForNPCount = false;
         int lDestsForNPCount = false;
         int clDestsForNPCount = false;
         int cDestsForNPCount = false;
         DistributedDestinationImpl[] aDestsNoNonUPS = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] lDestsNoNonUPS = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] clDestsNoNonUPS = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] cDestsNoNonUPS = new DistributedDestinationImpl[size];
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug(this.ddHandler.getName() + " with load balancing policy " + this.loadBalanceString(this.ddHandler.getLoadBalancingPolicyAsInt()) + " has the following members: ");
         }

         int nProductionPaused = 0;
         int nConsumptionPaused = 0;
         boolean nonups = false;

         for(int i = 0; i < size; ++i) {
            nonups = false;
            DistributedDestinationImpl dest = allDests[i];
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug(" " + dest.debugString());
            }

            if (dest.isProductionPaused() || dest.isInsertionPaused()) {
               ++nProductionPaused;
            }

            if (dest.isConsumptionPaused()) {
               ++nConsumptionPaused;
            }

            aDests[a++] = dest;
            if (dest.isOnDynamicNonUPS()) {
               nonups = true;
            }

            if (!nonups) {
               aDestsNoNonUPS[aDestsNoNonUPSCount++] = dest;
            }

            if (dest.isLocal()) {
               lDests[l++] = dest;
               if (!nonups) {
                  lDestsNoNonUPS[lDestsNoNonUPSCount++] = dest;
               }

               if (dest.isPersistent()) {
                  plDests[pl++] = dest;
                  if (dest.hasConsumer()) {
                     pclDests[pcl++] = dest;
                  }
               }

               if (dest.hasConsumer()) {
                  clDests[cl++] = dest;
                  if (!nonups) {
                     clDestsNoNonUPS[clDestsNoNonUPSCount++] = dest;
                  }
               } else {
                  nclDests[ncl++] = dest;
               }
            }

            if (dest.isPersistent()) {
               pDests[p++] = dest;
               if (dest.hasConsumer()) {
                  pcDests[pc++] = dest;
               }
            }

            if (dest.hasConsumer()) {
               cDests[c++] = dest;
               if (!nonups) {
                  cDestsNoNonUPS[cDestsNoNonUPSCount++] = dest;
               }
            } else {
               ncDests[nc++] = dest;
            }
         }

         int aDestsForNPCount = 0;
         if (aDestsNoNonUPSCount != a && aDestsNoNonUPSCount > 0) {
            aDestsForNPCount = aDestsNoNonUPSCount;
            DistributedDestinationImpl[] lDestsForNP = lDestsNoNonUPS;
            int lDestsForNPCount = lDestsNoNonUPSCount;
            clDestsForNP = clDestsNoNonUPS;
            int clDestsForNPCount = clDestsNoNonUPSCount;
            cDestsForNP = cDestsNoNonUPS;
            int cDestsForNPCount = cDestsNoNonUPSCount;
            if (lDestsNoNonUPSCount < 1) {
               lDestsForNP = aDestsNoNonUPS;
               lDestsForNPCount = aDestsNoNonUPSCount;
            }

            if (cDestsNoNonUPSCount < 1) {
               cDestsForNP = lDestsForNP;
               cDestsForNPCount = lDestsForNPCount;
            }

            if (clDestsNoNonUPSCount < 1) {
               clDestsForNP = cDestsForNP;
               clDestsForNPCount = cDestsForNPCount;
            }

            if (lDestsForNPCount < 1) {
               lDestsForNP = aDestsNoNonUPS;
            }

            cAllDestsForNP = cDestsForNP;
            if (cDestsForNPCount < 1) {
               cAllDestsForNP = aDestsNoNonUPS;
               cDestsForNP = lDestsForNP;
            }

            if (clDestsForNPCount < 1) {
               clDestsForNP = cDestsForNP;
            }
         }

         if (l < 1) {
            lDests = aDests;
         }

         DistributedDestinationImpl[] cAllDests = cDests;
         if (c < 1) {
            cAllDests = aDests;
            cDests = lDests;
         }

         DistributedDestinationImpl[] ncAllDests = ncDests;
         if (nc < 1) {
            ncAllDests = aDests;
         }

         if (pcl < 1) {
            pclDests = pcDests;
            pcl = pc;
         }

         if (cl < 1) {
            clDests = cDests;
         }

         if (ncl < 1) {
            nclDests = lDests;
         }

         if (pl < 1) {
            plDests = pDests;
            pl = p;
         }

         if (pcl == 0) {
            if (pl == 0) {
               pclDests = clDests;
            } else {
               pclDests = plDests;
            }
         }

         if (pc == 0) {
            if (p == 0) {
               pcDests = cAllDests;
            } else {
               pcDests = pDests;
            }
         }

         if (aDestsForNPCount > 0) {
            cAllDests = cAllDestsForNP;
            clDests = clDestsForNP;
         }

         if (nProductionPaused != 0 && nProductionPaused != size) {
            this.productionPauseFilter(clDests, size);
            this.productionPauseFilter(cAllDests, size);
            this.productionPauseFilter(pclDests, size);
            this.productionPauseFilter(pcDests, size);
         }

         if (nConsumptionPaused != 0 && nProductionPaused != size) {
            this.consumptionPauseFilter(lDests, size);
            this.consumptionPauseFilter(nclDests, size);
            this.consumptionPauseFilter(ncAllDests, size);
         }

         if (this.ddHandler.getLoadBalancingPolicyAsInt() == 1) {
            if (!perJVM) {
               this.connectionConsumerLB = new RandomLoadBalancer(lDests);
               this.consumerLB = new RandomLoadBalancer(nclDests);
               this.consumerAllLB = new RandomLoadBalancer(ncAllDests);
               this.npProducerLB = new RandomLoadBalancer(clDests);
               this.npProducerAllLB = new RandomLoadBalancer(cAllDests);
               this.pProducerLB = new RandomLoadBalancer(pclDests);
               this.pProducerAllLB = new RandomLoadBalancer(pcDests);
            } else {
               this.npProducerLBPerJVM = new RandomLoadBalancer(clDests);
               this.npProducerAllLBPerJVM = new RandomLoadBalancer(cAllDests);
               this.pProducerLBPerJVM = new RandomLoadBalancer(pclDests);
               this.pProducerAllLBPerJVM = new RandomLoadBalancer(pcDests);
            }
         } else {
            label166: {
               label165: {
                  if (perJVM) {
                     if (!this.doInitPerJVM) {
                        break label165;
                     }
                  } else if (!this.doInitPerMember) {
                     break label165;
                  }

                  if (!perJVM) {
                     this.connectionConsumerLB = new RRLoadBalancer(lDests);
                     this.consumerLB = new RRLoadBalancer(nclDests);
                     this.consumerAllLB = new RRLoadBalancer(ncAllDests);
                     this.npProducerLB = new RRLoadBalancer(clDests);
                     this.npProducerAllLB = new RRLoadBalancer(cAllDests);
                     this.pProducerLB = new RRLoadBalancer(pclDests);
                     this.pProducerAllLB = new RRLoadBalancer(pcDests);
                  } else {
                     this.npProducerLBPerJVM = new RRLoadBalancer(clDests);
                     this.npProducerAllLBPerJVM = new RRLoadBalancer(cAllDests);
                     this.pProducerLBPerJVM = new RRLoadBalancer(pclDests);
                     this.pProducerAllLBPerJVM = new RRLoadBalancer(pcDests);
                  }
                  break label166;
               }

               if (!perJVM) {
                  this.connectionConsumerLB.refresh(lDests);
                  this.consumerLB.refresh(nclDests);
                  this.consumerAllLB.refresh(ncAllDests);
                  this.npProducerLB.refresh(clDests);
                  this.npProducerAllLB.refresh(cAllDests);
                  this.pProducerLB.refresh(pclDests);
                  this.pProducerAllLB.refresh(pcDests);
               } else {
                  this.npProducerLBPerJVM.refresh(clDests);
                  this.npProducerAllLBPerJVM.refresh(cAllDests);
                  this.pProducerLBPerJVM.refresh(pclDests);
                  this.pProducerAllLBPerJVM.refresh(pcDests);
               }
            }
         }

         if (perJVM) {
            this.doInitPerJVM = false;
         } else {
            this.doInitPerMember = false;
         }

      }
   }

   private void refreshTopic(boolean perJVM) {
      DistributedDestinationImpl[] allDests = this.getUpMembers(perJVM);
      int size = allDests.length;
      if (perJVM) {
         this.sizePerJVM = size;
      } else {
         this.sizePerMember = size;
      }

      if (size > 0) {
         Arrays.sort(allDests, new JMSComparator(2, false));
         int a = 0;
         int l = 0;
         int p = 0;
         int pl = 0;
         DistributedDestinationImpl[] plDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] pDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] lDests = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] aDests = new DistributedDestinationImpl[size];
         int aDestsNoNonUPSCount = 0;
         int lDestsNoNonUPSCount = 0;
         DistributedDestinationImpl[] lDestsForNP = null;
         int aDestsForNPCount = 0;
         int lDestsForNPCount = false;
         DistributedDestinationImpl[] aDestsNoNonUPS = new DistributedDestinationImpl[size];
         DistributedDestinationImpl[] lDestsNoNonUPS = new DistributedDestinationImpl[size];
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug(this.ddHandler.getName() + " with load balancing policy " + this.loadBalanceString(this.ddHandler.getLoadBalancingPolicyAsInt()) + " has the following members: ");
         }

         int nProductionPaused = 0;
         int nConsumptionPaused = 0;
         boolean nonups = false;

         for(int i = 0; i < size; ++i) {
            nonups = false;
            DistributedDestinationImpl dest = allDests[i];
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug(" " + dest.debugString());
            }

            if (dest.isProductionPaused() || dest.isInsertionPaused()) {
               ++nProductionPaused;
            }

            if (dest.isConsumptionPaused()) {
               ++nConsumptionPaused;
            }

            aDests[a++] = dest;
            if (dest.isOnDynamicNonUPS()) {
               nonups = true;
            }

            if (!nonups) {
               aDestsNoNonUPS[aDestsNoNonUPSCount++] = dest;
            }

            if (dest.isLocal()) {
               lDests[l++] = dest;
               if (!nonups) {
                  lDestsNoNonUPS[lDestsNoNonUPSCount++] = dest;
               }

               if (dest.isPersistent()) {
                  plDests[pl++] = dest;
               }
            }

            if (dest.isPersistent()) {
               pDests[p++] = dest;
            }
         }

         if (aDestsNoNonUPSCount != a && aDestsNoNonUPSCount > 0) {
            aDestsForNPCount = aDestsNoNonUPSCount;
            lDestsForNP = lDestsNoNonUPS;
            if (lDestsNoNonUPSCount < 1) {
               lDestsForNP = aDestsNoNonUPS;
            }
         }

         if (l < 1) {
            lDests = aDests;
         }

         if (pl < 1) {
            plDests = pDests;
            pl = p;
         }

         if (pl == 0) {
            plDests = lDests;
         }

         if (aDestsForNPCount < 1) {
            lDestsForNP = lDests;
         }

         DistributedDestinationImpl[] producerLDests = (DistributedDestinationImpl[])((DistributedDestinationImpl[])lDestsForNP.clone());
         if (nProductionPaused != 0 && nProductionPaused != size) {
            this.productionPauseFilter(producerLDests, size);
            this.productionPauseFilter(plDests, size);
         }

         if (nConsumptionPaused != 0 && nProductionPaused != size) {
            this.consumptionPauseFilter(lDests, size);
            this.consumptionPauseFilter(plDests, size);
            this.consumptionPauseFilter(lDests, size);
         }

         if (this.ddHandler.getLoadBalancingPolicyAsInt() == 1) {
            if (!perJVM) {
               this.connectionConsumerLB = new RandomLoadBalancer(lDests);
               this.consumerLB = new RandomLoadBalancer(lDests);
               this.npProducerLB = new RandomLoadBalancer(producerLDests);
               this.pProducerLB = new RandomLoadBalancer(plDests);
            } else {
               this.npProducerLBPerJVM = new RandomLoadBalancer(producerLDests);
               this.pProducerLBPerJVM = new RandomLoadBalancer(plDests);
            }
         } else {
            label107: {
               label106: {
                  if (perJVM) {
                     if (!this.doInitPerJVM) {
                        break label106;
                     }
                  } else if (!this.doInitPerMember) {
                     break label106;
                  }

                  if (!perJVM) {
                     this.connectionConsumerLB = new RRLoadBalancer(lDests);
                     this.consumerLB = new RRLoadBalancer(lDests);
                     this.npProducerLB = new RRLoadBalancer(producerLDests);
                     this.pProducerLB = new RRLoadBalancer(plDests);
                  } else {
                     this.npProducerLBPerJVM = new RRLoadBalancer(producerLDests);
                     this.pProducerLBPerJVM = new RRLoadBalancer(plDests);
                  }
                  break label107;
               }

               if (perJVM) {
                  this.connectionConsumerLB.refresh(lDests);
                  this.consumerLB.refresh(lDests);
                  this.npProducerLB.refresh(producerLDests);
                  this.pProducerLB.refresh(plDests);
               } else {
                  this.npProducerLBPerJVM.refresh(producerLDests);
                  this.pProducerLBPerJVM.refresh(plDests);
               }
            }
         }

         if (perJVM) {
            this.doInitPerJVM = false;
         } else {
            this.doInitPerMember = false;
         }

      }
   }

   public DestinationImpl connectionConsumerLoadBalance(String ddDestinationName) throws JMSException {
      synchronized(this.ddHandler) {
         DestinationImpl destination = null;
         if (this.sizePerMember > 0 && (destination = this.connectionConsumerLB.getNext((DDTxLoadBalancingOptimizer)null)) != null) {
            assert destination != null;

            return destination;
         } else {
            throw new JMSException("Distributed Destination " + ddDestinationName + " does not have any member destinations which are active");
         }
      }
   }

   public DestinationImpl consumerLoadBalance(FESession session, String ddDestinationName) throws JMSException {
      DestinationImpl retDest = this.consumerLoadBalance(session, false, ddDestinationName);
      if (retDest != null && retDest.getDispatcherId() == null) {
         retDest = this.consumerLoadBalance(session, true, ddDestinationName);
      }

      assert retDest != null;

      return retDest;
   }

   private DestinationImpl consumerLoadBalance(FESession session, boolean forceRefresh, String ddDestinationName) throws JMSException {
      synchronized(this.ddHandler) {
         if (forceRefresh) {
            this.refresh();
         }

         if (this.sizePerMember <= 0) {
            throw new JMSException((forceRefresh ? "After refresh " : "") + "Distributed Destination " + ddDestinationName + " does not have any member destinations which are active");
         } else {
            DistributedDestinationImpl dest;
            if (session != null && session.isTransacted()) {
               dest = session.getCachedDest(this.ddHandler.getName(), false);
               if (dest != null) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("Tx Pick " + dest.debugString());
                  }

                  return dest;
               }

               if (!session.isServerAffinityEnabled()) {
                  dest = this.consumerAllLB.getNext(session);
               } else {
                  dest = this.consumerLB.getNext(session);
               }

               session.addCachedDest(dest);
            } else if (session != null && !session.isServerAffinityEnabled()) {
               dest = this.consumerAllLB.getNext((DDTxLoadBalancingOptimizer)null);
            } else {
               dest = this.consumerLB.getNext((DDTxLoadBalancingOptimizer)null);
            }

            if (dest == null) {
               throw new JMSException((forceRefresh ? "After refresh " : "") + "Distributed Destination " + ddDestinationName + " does not have any member destinations which are active");
            } else {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("Returning destination: " + dest.getInstanceName());
               }

               return dest;
            }
         }
      }
   }

   public DestinationImpl producerLoadBalance(boolean isPersistent, FESession session, String ddDestinationName) throws JMSException {
      DestinationImpl retDest = this.producerLoadBalance(isPersistent, session, false, ddDestinationName);
      if (retDest != null && retDest.getDispatcherId() == null) {
         retDest = this.producerLoadBalance(isPersistent, session, true, ddDestinationName);
      }

      assert retDest != null;

      return retDest;
   }

   public DestinationImpl producerLoadBalance(boolean isPersistent, FESession session, boolean forceRefresh, String ddDestinationName) throws JMSException {
      synchronized(this.ddHandler) {
         if (forceRefresh) {
            this.refresh();
         }

         boolean perJVM = session.isPerJVMProducerLoadBalancingEnabled();
         if ((!perJVM || this.sizePerJVM > 0) && (perJVM || this.sizePerMember > 0)) {
            DistributedDestinationImpl dest;
            if (session != null && session.isTransacted()) {
               dest = session.getCachedDest(this.ddHandler.getName(), isPersistent);
               if (dest != null) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("JMSLoadBalancer.producerLoadBalance(isPersistent=" + isPersistent + "): Returning destination:(Tx Pick cached) " + dest.getInstanceName() + "[" + dest.debugString() + "]");
                  }

                  return dest;
               }

               dest = this.getDestination(isPersistent, session, session);
               session.addCachedDest(dest);
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("JMSLoadBalancer.producerLoadBalance(isPersistent=" + isPersistent + "): Returning destination:(Tx Pick) " + dest.getInstanceName() + "[" + dest.debugString() + "]");
               }
            } else {
               dest = this.getDestination(isPersistent, session, (FESession)null);
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("JMSLoadBalancer.producerLoadBalance(isPersistent=" + isPersistent + "): Returning destination: " + (dest == null ? "null" : dest.getInstanceName() + "[" + dest.debugString() + "]"));
               }
            }

            if (dest == null) {
               throw new JMSException((forceRefresh ? "After refresh " : "") + "Distributed Destination " + ddDestinationName + " does not have any member destinations which are active");
            } else {
               return dest;
            }
         } else {
            throw new JMSException((forceRefresh ? "After refresh " : "") + "Distributed Destination " + ddDestinationName + " does not have any member destinations which are active");
         }
      }
   }

   public JMSFailover getProducerFailover(DistributedDestinationImpl failedDest, Throwable t, boolean isPersistent, FESession session) {
      Exception ex = null;
      boolean perJVM = session == null ? false : session.isPerJVMProducerLoadBalancingEnabled();
      if (t instanceof JMSException) {
         ex = ((JMSException)t).getLinkedException();
      }

      if (t instanceof RemoteException && !RemoteHelper.isRecoverablePreInvokeFailure((RemoteException)t)) {
         return null;
      } else if (ex instanceof RemoteException && !RemoteHelper.isRecoverablePreInvokeFailure((RemoteException)ex)) {
         return null;
      } else {
         DistributedDestinationImpl[] allDests = this.getUpMembers(perJVM);
         if (!this.isTopic) {
            Arrays.sort(allDests, new JMSComparator(0, isPersistent));
         } else {
            Arrays.sort(allDests, new JMSComparator(1, isPersistent));
         }

         return new JMSFailover(allDests, failedDest);
      }
   }

   private DistributedDestinationImpl getDestination(boolean isPersistent, FESession session, FESession passToLoaderBalanace) {
      boolean perJVM = session.isPerJVMProducerLoadBalancingEnabled();
      LoadBalancer lb;
      if (this.isTopic && !this.isPartitionedDistributedTopic) {
         if (isPersistent) {
            lb = perJVM ? this.pProducerLBPerJVM : this.pProducerLB;
         } else {
            lb = perJVM ? this.npProducerLBPerJVM : this.npProducerLB;
         }
      } else if (isPersistent) {
         if (session != null && !session.isServerAffinityEnabled()) {
            lb = perJVM ? this.pProducerAllLBPerJVM : this.pProducerAllLB;
         } else {
            lb = perJVM ? this.pProducerLBPerJVM : this.pProducerLB;
         }
      } else if (session != null && !session.isServerAffinityEnabled()) {
         lb = perJVM ? this.npProducerAllLBPerJVM : this.npProducerAllLB;
      } else {
         lb = perJVM ? this.npProducerLBPerJVM : this.npProducerLB;
      }

      return lb.getNext(passToLoaderBalanace);
   }

   private String loadBalanceString(int policyAsInt) {
      switch (policyAsInt) {
         case 0:
            return "Round Robin";
         case 1:
            return "Random";
         case 2:
            return "Sticky Random";
         default:
            return "Unknown Policy";
      }
   }

   public boolean isPartitionedDistributedTopic() {
      return this.isPartitionedDistributedTopic;
   }

   public final class JMSComparator implements Comparator {
      public static final int SENDER = 0;
      public static final int PUBLISHER = 1;
      public static final int ORDER = 2;
      public static final int PERJVM = 3;
      final int type;
      final boolean isPersistent;

      public JMSComparator(int type, boolean isPersistent) {
         this.type = type;
         this.isPersistent = isPersistent;
      }

      public int compare(Object o1, Object o2) {
         switch (this.type) {
            case 0:
               return this.senderCompare(o1, o2);
            case 1:
               return this.publisherCompare(o1, o2);
            case 2:
               return this.orderCompare(o1, o2);
            case 3:
               return this.perJVMCompare(o1, o2);
            default:
               return this.senderCompare(o1, o2);
         }
      }

      public int orderCompare(Object o1, Object o2) {
         try {
            DistributedDestinationImpl d1 = (DistributedDestinationImpl)o1;
            DistributedDestinationImpl d2 = (DistributedDestinationImpl)o2;
            return d1.getOrder() < d2.getOrder() ? -1 : 1;
         } catch (Exception var6) {
            return 0;
         }
      }

      public int senderCompare(Object o1, Object o2) {
         try {
            DistributedDestinationImpl d1 = (DistributedDestinationImpl)o1;
            DistributedDestinationImpl d2 = (DistributedDestinationImpl)o2;
            if (this.isPersistent && d1.isPersistent() != d2.isPersistent()) {
               if (d2.isPersistent()) {
                  return 1;
               }
            } else if (d1.hasConsumer() == d2.hasConsumer()) {
               if (d1.isLocal() == d2.isLocal()) {
                  if (d1.getOrder() < d2.getOrder()) {
                     return -1;
                  }

                  return 1;
               }

               if (d2.isLocal()) {
                  return 1;
               }
            } else if (d2.hasConsumer()) {
               return 1;
            }

            return -1;
         } catch (Exception var6) {
            return 0;
         }
      }

      public int publisherCompare(Object o1, Object o2) {
         try {
            DistributedDestinationImpl d1 = (DistributedDestinationImpl)o1;
            DistributedDestinationImpl d2 = (DistributedDestinationImpl)o2;
            if (this.isPersistent && d1.isPersistent() != d2.isPersistent()) {
               if (d2.isPersistent()) {
                  return 1;
               }
            } else {
               if (d1.isLocal() == d2.isLocal()) {
                  if (d1.getOrder() < d2.getOrder()) {
                     return -1;
                  }

                  return 1;
               }

               if (d2.isLocal()) {
                  return 1;
               }
            }

            return -1;
         } catch (Exception var6) {
            return 0;
         }
      }

      public int perJVMCompare(Object o1, Object o2) {
         try {
            DistributedDestinationImpl d1 = (DistributedDestinationImpl)o1;
            DistributedDestinationImpl d2 = (DistributedDestinationImpl)o2;
            int ret = d1.getWLSServerSortingName().compareTo(d2.getWLSServerSortingName());
            if (ret != 0) {
               return ret;
            } else {
               boolean d1p = d1.isPossiblyClusterTargeted() && d1.isOnPreferredServer();
               boolean d2p = d2.isPossiblyClusterTargeted() && d2.isOnPreferredServer();
               if (d1p != d2p) {
                  return d1p ? -1 : 1;
               } else {
                  return d1.getJMSServerSortingName().compareTo(d2.getJMSServerSortingName());
               }
            }
         } catch (Exception var8) {
            return 0;
         }
      }
   }
}
