package weblogic.ejb.container.cache;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.PassivatibleEntityCache;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.utils.PartialOrderSet;

public final class EntityPassivator {
   static final String DISABLE_ENTITY_PASSIVATION_IN_TX_PROP = "weblogic.ejb.container.cache.disable_entity_passivation_in_tx";
   private static final boolean disable_entity_passivation_in_tx = System.getProperty("weblogic.ejb.container.cache.disable_entity_passivation_in_tx") != null;
   private static final DebugLogger debugLogger;
   private final PassivatibleEntityCache cache;

   public EntityPassivator(PassivatibleEntityCache c) {
      this.cache = c;
   }

   public long passivate(Transaction ourTx, long maxCacheSize, int beanSize) {
      if (disable_entity_passivation_in_tx) {
         if (debugLogger.isDebugEnabled()) {
            debug("Passivation of Entity Beans in a transaction is disabled.  Skipping Entity passivation.");
         }

         return 0L;
      } else {
         assert beanSize > 0 : "Bean Size " + beanSize + " is <= 0, it must be greater than 0 !!";

         long onePercent = (long)((double)(maxCacheSize / (long)beanSize) * 0.01);
         long tenBeans = 10L * (long)beanSize;
         long target = onePercent > tenBeans ? onePercent : tenBeans;
         if (debugLogger.isDebugEnabled()) {
            debug(" passivate in our tx with target: " + target);
         }

         long freedTotal = 0L;
         freedTotal = this.passivateInOurTx(ourTx, target);
         if (debugLogger.isDebugEnabled()) {
            debug(" After passivation in our tx we've freed " + freedTotal);
         }

         if (freedTotal >= target) {
            return freedTotal;
         } else {
            freedTotal += this.passivateNotInOurTx(ourTx, target - freedTotal);
            if (debugLogger.isDebugEnabled()) {
               debug(" After passivation NOT in our tx we've freed " + freedTotal);
            }

            return freedTotal;
         }
      }
   }

   private long passivateInOurTx(Transaction ourTx, long target) {
      long freed = 0L;
      BaseEntityManager cm = null;
      List inTxManagerList = this.setInTxManagers(ourTx);
      if (debugLogger.isDebugEnabled()) {
         debug("\n\n+++ begin passivation in our Tx of unModified ops complete beans.\n\n");
      }

      LinkedList opsCompleteUnknown = new LinkedList();
      Iterator cms = inTxManagerList.iterator();

      while(true) {
         PartialOrderSet pkSet;
         do {
            if (!cms.hasNext()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("\n\n+++ begin passivation in our Tx of unModified non-ops complete beans.\n");
               }

               if (opsCompleteUnknown.size() > 0) {
                  Iterator it = opsCompleteUnknown.iterator();

                  while(it.hasNext()) {
                     CacheKey ck = (CacheKey)it.next();
                     freed += (long)((BaseEntityManager)ck.getCallback()).passivateUnModifiedBean(ourTx, ck.getPrimaryKey());
                     if (debugLogger.isDebugEnabled()) {
                        debug("passivate target is " + target + ", and we have freed = " + freed + ", after non-opsComplete cm.passivateUnModifiedBean on pk " + ck.getPrimaryKey());
                     }

                     if (freed >= target) {
                        return freed;
                     }
                  }
               }

               if (cm == null) {
                  return freed;
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("\n\n+++ begin passivation in our Tx of modified beans.\n");
               }

               boolean flushSuccess = false;

               try {
                  if (debugLogger.isDebugEnabled()) {
                     debug("\n\n+++ flush modified beans \n");
                  }

                  cm.getEntityTxManager().flushModifiedBeans(ourTx, true);
                  flushSuccess = true;
               } catch (Throwable var15) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("\n flushModifiedBeans, ourTx " + ourTx + "\n resulted in Throwable " + var15.toString() + "\n");
                  }
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("\n flush modified success: " + (flushSuccess ? "true" : "false"));
               }

               opsCompleteUnknown.clear();
               boolean done = false;
               cms = inTxManagerList.iterator();

               while(cms.hasNext() && !done) {
                  cm = (BaseEntityManager)cms.next();
                  List flushedKeys = cm.getEntityTxManager().getFlushedKeys(ourTx);
                  if (flushedKeys != null && flushedKeys.size() > 0) {
                     if (debugLogger.isDebugEnabled()) {
                        debug("\n\n+++ processing " + flushedKeys.size() + " flushed ops complete beans\n");
                     }

                     Iterator ks = flushedKeys.iterator();

                     while(ks.hasNext()) {
                        Object pk = ks.next();
                        if (cm.beanIsOpsComplete(ourTx, pk)) {
                           freed += (long)cm.passivateModifiedBean(ourTx, pk, flushSuccess);
                           if (debugLogger.isDebugEnabled()) {
                              debug("passivate target is " + target + ", and we have freed = " + freed + ", after flushed bean cm.passivateModifiedBean on pk " + pk);
                           }

                           if (freed >= target) {
                              done = true;
                              break;
                           }
                        } else {
                           opsCompleteUnknown.add(new CacheKey(pk, cm));
                        }
                     }
                  }
               }

               if (done) {
                  return freed;
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("\n\n+++ begin passivation in our Tx of modified non-ops complete beans.\n");
               }

               if (opsCompleteUnknown.size() > 0) {
                  Iterator it = opsCompleteUnknown.iterator();

                  while(it.hasNext()) {
                     CacheKey ck = (CacheKey)it.next();
                     freed += (long)((BaseEntityManager)ck.getCallback()).passivateModifiedBean(ourTx, ck.getPrimaryKey(), flushSuccess);
                     if (debugLogger.isDebugEnabled()) {
                        debug("passivate target is " + target + ", and we have freed = " + freed + ", after flushed bean cm.passivateModifiedBean on pk " + ck.getPrimaryKey());
                     }

                     if (freed >= target) {
                        break;
                     }
                  }
               }

               return freed;
            }

            cm = (BaseEntityManager)cms.next();
            pkSet = cm.getEntityTxManager().getEnrolledKeys(ourTx);
         } while(pkSet == null);

         Iterator it = pkSet.iterator();

         while(it.hasNext()) {
            Object pk = it.next();
            if (!cm.isFlushPending(ourTx, pk)) {
               if (cm.beanIsOpsComplete(ourTx, pk)) {
                  freed += (long)cm.passivateUnModifiedBean(ourTx, pk);
                  if (debugLogger.isDebugEnabled()) {
                     debug("passivate target is " + target + ", and we have freed = " + freed + ", after opsComplete cm.passivateUnModifiedBean on pk " + pk);
                  }

                  if (freed >= target) {
                     return freed;
                  }
               } else {
                  opsCompleteUnknown.add(new CacheKey(pk, cm));
               }
            }
         }
      }
   }

   private long passivateNotInOurTx(Transaction ourTx, long target) {
      if (debugLogger.isDebugEnabled()) {
         debug("\n\n +++ passivateNotInOurTx  entered. \n");
      }

      LinkedList opsCompleteUnknown = new LinkedList();
      long freed = 0L;
      Iterator it = this.cache.getCachingManagers().iterator();

      while(it.hasNext()) {
         BaseEntityManager cm = (BaseEntityManager)it.next();
         List l = cm.getEntityTxManager().getNotModifiedOtherTxKeys(ourTx);
         if (debugLogger.isDebugEnabled()) {
            debug("\n passivateNotInOurTx got a list of " + l.size() + " not modified, not in our Tx Beans ");
         }

         Iterator ks = l.iterator();

         while(ks.hasNext()) {
            TxPk txPk = (TxPk)ks.next();
            Transaction tx = txPk.getTx();
            Object pk = txPk.getPk();
            if (cm.beanIsOpsComplete(tx, pk)) {
               freed += (long)cm.passivateUnModifiedBean(tx, pk);
               if (debugLogger.isDebugEnabled()) {
                  debug("passivate target is " + target + ", and we have freed = " + freed + ", after opsComplete, not in our Tx, cm.passivateUnModifiedBean on pk " + pk);
               }

               if (freed >= target) {
                  return freed;
               }
            } else {
               opsCompleteUnknown.add(new TxKey(tx, new CacheKey(pk, cm)));
            }
         }
      }

      if (opsCompleteUnknown.size() > 0) {
         it = opsCompleteUnknown.iterator();

         while(it.hasNext()) {
            TxKey txKey = (TxKey)it.next();
            freed += (long)((BaseEntityManager)txKey.getKey().getCallback()).passivateUnModifiedBean(txKey.getTx(), txKey.getKey().getPrimaryKey());
            if (debugLogger.isDebugEnabled()) {
               debug("passivate target is " + target + ", and we have freed = " + freed + ", after non-opsComplete, not in our Tx, cm.passivateUnModifiedBean on pk " + txKey.getKey().getPrimaryKey());
            }

            if (freed >= target) {
               return freed;
            }
         }
      }

      return freed;
   }

   private List setInTxManagers(Transaction tx) {
      List inTxManagerList = new LinkedList();
      Iterator it = this.cache.getCachingManagers().iterator();

      while(it.hasNext()) {
         BaseEntityManager cm = (BaseEntityManager)it.next();
         if (cm.hasBeansEnrolledInTx(tx)) {
            if (debugLogger.isDebugEnabled()) {
               debug(" adding CachingManager in inTxManagerList: " + cm);
            }

            inTxManagerList.add(cm);
         }
      }

      return inTxManagerList;
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityPassivator] " + s);
   }

   static {
      debugLogger = EJBDebugService.cachingLogger;
   }
}
