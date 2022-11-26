package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AdjudicationProvider;
import weblogic.security.spi.AdjudicationProviderV2;
import weblogic.security.spi.Adjudicator;
import weblogic.security.spi.AdjudicatorV2;
import weblogic.security.spi.BulkAdjudicationProvider;
import weblogic.security.spi.BulkAdjudicator;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class BulkAdjudicationProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AdjudicationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AdjudicatorProviderConfig myconfig = (AdjudicatorProviderConfig)config;
      Object provider = dependentServices.getService(myconfig.getAdjudicationProviderName());
      if (provider instanceof AdjudicationProvider) {
         Adjudicator adjudicator = ((AdjudicationProvider)provider).getAdjudicator();
         if (adjudicator == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AdjudicationProvider", "Adjudicator"));
         } else {
            AdjudicatorV2 a = new V1Adapter(adjudicator);
            return new BulkServiceImpl(this.wrap(a), a);
         }
      } else if (provider instanceof AdjudicationProviderV2) {
         AdjudicatorV2 adjudicator = ((AdjudicationProviderV2)provider).getAdjudicator();
         if (adjudicator == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AdjudicationProviderV2", "AdjudicatorV2"));
         } else {
            BulkAdjudicator bulkAdjudicator;
            if (provider instanceof BulkAdjudicationProvider) {
               bulkAdjudicator = ((BulkAdjudicationProvider)provider).getBulkAdjudicator();
               if (bulkAdjudicator == null) {
                  throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("BulkAdjudicationProvider", "BulkAdjudicator"));
               } else {
                  return new BulkServiceImpl(bulkAdjudicator, adjudicator);
               }
            } else {
               bulkAdjudicator = this.wrap(adjudicator);
               return new BulkServiceImpl(bulkAdjudicator, adjudicator);
            }
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("AdjudicationProvider"));
      }
   }

   private BulkAdjudicator wrap(final AdjudicatorV2 adjudicatorV2) {
      return new BulkAdjudicator() {
         private boolean init = false;

         public void initialize(String[] accessDecisionClassNames) {
            if (!this.init) {
               adjudicatorV2.initialize(accessDecisionClassNames);
            }

            this.init = true;
         }

         public Set adjudicate(final List results, final List resources, final ContextHandler handler) {
            if (results.size() == 1) {
               final Map m = (Map)results.get(0);
               return new Set() {
                  private Iterator ei = m.entrySet().iterator();
                  private Set inner = new HashSet();

                  private void init() {
                     if (this.ei != null) {
                        while(true) {
                           if (!this.ei.hasNext()) {
                              this.ei = null;
                              break;
                           }

                           Map.Entry e = (Map.Entry)this.ei.next();
                           if (Result.PERMIT.equals(((Result)e.getValue()).narrow())) {
                              this.inner.add(e.getKey());
                           }
                        }
                     }

                  }

                  public int size() {
                     this.init();
                     return this.inner.size();
                  }

                  public boolean isEmpty() {
                     if (!this.inner.isEmpty()) {
                        return false;
                     } else {
                        if (this.ei != null) {
                           while(this.ei.hasNext()) {
                              Map.Entry e = (Map.Entry)this.ei.next();
                              if (Result.PERMIT.equals(((Result)e.getValue()).narrow())) {
                                 this.inner.add(e.getKey());
                                 return false;
                              }
                           }

                           this.ei = null;
                        }

                        return true;
                     }
                  }

                  public boolean contains(Object o) {
                     Resource r = (Resource)o;
                     if (this.inner.contains(r)) {
                        return true;
                     } else {
                        if (this.ei != null) {
                           while(this.ei.hasNext()) {
                              Map.Entry e = (Map.Entry)this.ei.next();
                              if (Result.PERMIT.equals(((Result)e.getValue()).narrow())) {
                                 Resource z = (Resource)e.getKey();
                                 this.inner.add(z);
                                 if (r.equals(z)) {
                                    return true;
                                 }
                              }
                           }

                           this.ei = null;
                        }

                        return false;
                     }
                  }

                  public Iterator iterator() {
                     if (this.ei == null) {
                        return this.inner.iterator();
                     } else {
                        final Iterator ii = this.inner.iterator();
                        return new Iterator() {
                           private Resource next = null;
                           private Iterator i = ii;

                           public boolean hasNext() {
                              return this.moveHasNext();
                           }

                           private boolean moveHasNext() {
                              if (this.next != null) {
                                 return true;
                              } else {
                                 if (this.i != null) {
                                    if (this.i.hasNext()) {
                                       this.next = (Resource)this.i.next();
                                       return true;
                                    }

                                    this.i = null;
                                 }

                                 if (ei != null) {
                                    while(ei.hasNext()) {
                                       Map.Entry e = (Map.Entry)ei.next();
                                       if (Result.PERMIT.equals(((Result)e.getValue()).narrow())) {
                                          Resource z = (Resource)e.getKey();
                                          inner.add(z);
                                          this.next = z;
                                          return true;
                                       }
                                    }

                                    ei = null;
                                 }

                                 return false;
                              }
                           }

                           public Resource next() {
                              if (!this.moveHasNext()) {
                                 throw new NoSuchElementException();
                              } else {
                                 Resource result = this.next;
                                 this.next = null;
                                 return result;
                              }
                           }

                           public void remove() {
                              throw new UnsupportedOperationException();
                           }
                        };
                     }
                  }

                  public Object[] toArray() {
                     this.init();
                     return this.inner.toArray();
                  }

                  public Object[] toArray(Object[] a) {
                     this.init();
                     return this.inner.toArray(a);
                  }

                  public boolean add(Resource o) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean remove(Object o) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean containsAll(Collection c) {
                     Iterator ci = c.iterator();

                     do {
                        if (!ci.hasNext()) {
                           return true;
                        }
                     } while(this.contains(ci.next()));

                     return false;
                  }

                  public boolean addAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean retainAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean removeAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public void clear() {
                     throw new UnsupportedOperationException();
                  }
               };
            } else {
               final Result[] rt = new Result[results.size()];
               return new Set() {
                  private Iterator resi = resources.iterator();
                  private Set inner = new HashSet();

                  private void init() {
                     if (this.resi != null) {
                        while(true) {
                           if (!this.resi.hasNext()) {
                              this.resi = null;
                              break;
                           }

                           Resource r = (Resource)this.resi.next();
                           int i = 0;

                           for(Iterator ri = results.iterator(); ri.hasNext(); ++i) {
                              Result tt = (Result)((Map)ri.next()).get(r);
                              rt[i] = tt != null ? tt.narrow() : Result.ABSTAIN;
                           }

                           if (adjudicatorV2.adjudicate(rt, r, handler)) {
                              this.inner.add(r);
                           }
                        }
                     }

                  }

                  public int size() {
                     this.init();
                     return this.inner.size();
                  }

                  public boolean isEmpty() {
                     if (!this.inner.isEmpty()) {
                        return false;
                     } else {
                        if (this.resi != null) {
                           while(true) {
                              if (!this.resi.hasNext()) {
                                 this.resi = null;
                                 break;
                              }

                              Resource r = (Resource)this.resi.next();
                              int i = 0;

                              for(Iterator ri = results.iterator(); ri.hasNext(); ++i) {
                                 Result tt = (Result)((Map)ri.next()).get(r);
                                 rt[i] = tt != null ? tt.narrow() : Result.ABSTAIN;
                              }

                              if (adjudicatorV2.adjudicate(rt, r, handler)) {
                                 this.inner.add(r);
                                 return false;
                              }
                           }
                        }

                        return true;
                     }
                  }

                  public boolean contains(Object o) {
                     Resource z = (Resource)o;
                     if (this.inner.contains(z)) {
                        return true;
                     } else {
                        if (this.resi != null) {
                           while(true) {
                              if (!this.resi.hasNext()) {
                                 this.resi = null;
                                 break;
                              }

                              Resource r = (Resource)this.resi.next();
                              int i = 0;

                              for(Iterator ri = results.iterator(); ri.hasNext(); ++i) {
                                 Result tt = (Result)((Map)ri.next()).get(r);
                                 rt[i] = tt != null ? tt.narrow() : Result.ABSTAIN;
                              }

                              if (adjudicatorV2.adjudicate(rt, r, handler)) {
                                 this.inner.add(r);
                                 if (z.equals(r)) {
                                    return true;
                                 }
                              }
                           }
                        }

                        return false;
                     }
                  }

                  public Iterator iterator() {
                     if (this.resi == null) {
                        return this.inner.iterator();
                     } else {
                        final Iterator ii = this.inner.iterator();
                        return new Iterator() {
                           private Resource next = null;
                           private Iterator i = ii;

                           public boolean hasNext() {
                              return this.moveHasNext();
                           }

                           private boolean moveHasNext() {
                              if (this.next != null) {
                                 return true;
                              } else {
                                 if (this.i != null) {
                                    if (this.i.hasNext()) {
                                       this.next = (Resource)this.i.next();
                                       return true;
                                    }

                                    this.i = null;
                                 }

                                 if (resi != null) {
                                    while(true) {
                                       if (!resi.hasNext()) {
                                          resi = null;
                                          break;
                                       }

                                       Resource r = (Resource)resi.next();
                                       int i = 0;

                                       for(Iterator ri = results.iterator(); ri.hasNext(); ++i) {
                                          Result tt = (Result)((Map)ri.next()).get(r);
                                          rt[i] = tt != null ? tt.narrow() : Result.ABSTAIN;
                                       }

                                       if (adjudicatorV2.adjudicate(rt, r, handler)) {
                                          inner.add(r);
                                          this.next = r;
                                       }
                                    }
                                 }

                                 return false;
                              }
                           }

                           public Resource next() {
                              if (!this.moveHasNext()) {
                                 throw new NoSuchElementException();
                              } else {
                                 Resource result = this.next;
                                 this.next = null;
                                 return result;
                              }
                           }

                           public void remove() {
                              throw new UnsupportedOperationException();
                           }
                        };
                     }
                  }

                  public Object[] toArray() {
                     this.init();
                     return this.inner.toArray();
                  }

                  public Object[] toArray(Object[] a) {
                     this.init();
                     return this.inner.toArray(a);
                  }

                  public boolean add(Resource o) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean remove(Object o) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean containsAll(Collection c) {
                     Iterator ci = c.iterator();

                     do {
                        if (!ci.hasNext()) {
                           return true;
                        }
                     } while(this.contains(ci.next()));

                     return false;
                  }

                  public boolean addAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean retainAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public boolean removeAll(Collection c) {
                     throw new UnsupportedOperationException();
                  }

                  public void clear() {
                     throw new UnsupportedOperationException();
                  }
               };
            }
         }
      };
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class BulkServiceImpl implements com.bea.common.security.spi.BulkAdjudicationProvider {
      private AdjudicatorV2 adjudicator;
      private BulkAdjudicator bulkAdjudicator;

      private BulkServiceImpl(BulkAdjudicator bulkAdjudicator, AdjudicatorV2 adjudicator) {
         this.adjudicator = adjudicator;
         this.bulkAdjudicator = bulkAdjudicator;
      }

      public AdjudicatorV2 getAdjudicator() {
         return this.adjudicator;
      }

      public BulkAdjudicator getBulkAdjudicator() {
         return this.bulkAdjudicator;
      }

      // $FF: synthetic method
      BulkServiceImpl(BulkAdjudicator x1, AdjudicatorV2 x2, Object x3) {
         this(x1, x2);
      }
   }

   private class V1Adapter implements AdjudicatorV2 {
      private Adjudicator v1;

      private V1Adapter(Adjudicator v1) {
         this.v1 = v1;
      }

      public void initialize(String[] accessDecisionClassNames) {
         this.v1.initialize(accessDecisionClassNames);
      }

      public boolean adjudicate(Result[] results, Resource resource, ContextHandler contextHandler) {
         return this.v1.adjudicate(results);
      }

      // $FF: synthetic method
      V1Adapter(Adjudicator x1, Object x2) {
         this(x1);
      }
   }
}
