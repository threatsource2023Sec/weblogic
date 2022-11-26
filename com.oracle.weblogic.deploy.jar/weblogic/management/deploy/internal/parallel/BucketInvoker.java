package weblogic.management.deploy.internal.parallel;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.management.DeploymentException;
import weblogic.server.ServiceFailureException;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public abstract class BucketInvoker {
   protected final Collection exceptions = new ConcurrentLinkedDeque();

   protected BucketInvoker() {
   }

   public Collection getExceptions() {
      return this.exceptions;
   }

   protected abstract void doItem(Object var1) throws Throwable;

   protected Throwable handleThrowable(Throwable t, Object item) {
      return t;
   }

   protected Runnable wrapContext(Runnable job, Runnable overloadAction, Runnable cancelAction) {
      ComponentInvocationContextManager man = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext context = man.getCurrentComponentInvocationContext();
      return (Runnable)(context == null ? new ContextWrap(job, overloadAction, cancelAction) : new ContextRunnable(job, overloadAction, cancelAction, context));
   }

   public void invoke(Collection buckets) throws DeploymentException, ServiceFailureException {
      this.invoke(buckets, WorkManagerFactory.getInstance().getDefault());
   }

   public void invoke(Collection buckets, WorkManager wm) throws DeploymentException, ServiceFailureException {
      Iterator var3 = buckets.iterator();

      while(true) {
         while(true) {
            while(var3.hasNext()) {
               Bucket bucket = (Bucket)var3.next();
               Object item;
               Throwable x;
               if (bucket.isParallel) {
                  int count = bucket.contents.size();
                  if (count > 1) {
                     final CountDownLatch doneSignal = new CountDownLatch(count - 1);
                     Iterator it = bucket.contents.iterator();

                     while(true) {
                        final Object item = it.next();
                        if (!it.hasNext()) {
                           try {
                              this.doItem(item);
                           } catch (Throwable var15) {
                              Throwable x = this.handleThrowable(var15, item);
                              if (x != null) {
                                 if (x instanceof DeploymentException) {
                                    throw (DeploymentException)x;
                                 }

                                 if (x instanceof ServiceFailureException) {
                                    throw (ServiceFailureException)x;
                                 }

                                 throw new ServiceFailureException(x);
                              }
                           }

                           try {
                              doneSignal.await();
                              break;
                           } catch (InterruptedException var12) {
                              throw new DeploymentException(var12);
                           }
                        }

                        final AtomicBoolean firstCall = new AtomicBoolean(false);
                        final Runnable onFinish = new Runnable() {
                           public void run() {
                              if (!firstCall.getAndSet(true)) {
                                 doneSignal.countDown();
                              }

                           }
                        };
                        Runnable job = new Runnable() {
                           public void run() {
                              try {
                                 BucketInvoker.this.doItem(item);
                              } catch (Throwable var6) {
                                 Throwable x = BucketInvoker.this.handleThrowable(var6, item);
                                 if (x != null) {
                                    BucketInvoker.this.exceptions.add(x);
                                 }
                              } finally {
                                 onFinish.run();
                              }

                           }
                        };
                        wm.schedule(this.wrapContext(job, onFinish, onFinish));
                     }
                  } else {
                     item = bucket.contents.getFirst();

                     try {
                        this.doItem(item);
                     } catch (Throwable var14) {
                        x = this.handleThrowable(var14, item);
                        if (x != null) {
                           if (x instanceof DeploymentException) {
                              throw (DeploymentException)x;
                           }

                           if (x instanceof ServiceFailureException) {
                              throw (ServiceFailureException)x;
                           }

                           throw new ServiceFailureException(x);
                        }
                     }
                  }
               } else {
                  Iterator var5 = bucket.contents.iterator();

                  while(var5.hasNext()) {
                     item = var5.next();

                     try {
                        this.doItem(item);
                     } catch (Throwable var13) {
                        x = this.handleThrowable(var13, item);
                        if (x != null) {
                           if (x instanceof DeploymentException) {
                              throw (DeploymentException)x;
                           }

                           if (x instanceof ServiceFailureException) {
                              throw (ServiceFailureException)x;
                           }

                           throw new ServiceFailureException(x);
                        }
                     }
                  }
               }
            }

            if (!this.exceptions.isEmpty()) {
               if (this.exceptions.size() == 1) {
                  Throwable x = (Throwable)this.exceptions.iterator().next();
                  if (x != null) {
                     if (x instanceof DeploymentException) {
                        throw (DeploymentException)x;
                     }

                     if (x instanceof ServiceFailureException) {
                        throw (ServiceFailureException)x;
                     }

                     throw new DeploymentException(x);
                  }
               }

               throw new MultiDeploymentException(this.exceptions);
            }

            return;
         }
      }
   }

   private static class ContextRunnable extends ContextWrap implements ComponentRequest {
      private final ComponentInvocationContext context;

      public ContextRunnable(Runnable run, Runnable overloadAction, Runnable cancelAction, ComponentInvocationContext context) {
         super(run, overloadAction, cancelAction);
         this.context = context;
      }

      public ComponentInvocationContext getComponentInvocationContext() {
         return this.context;
      }
   }
}
