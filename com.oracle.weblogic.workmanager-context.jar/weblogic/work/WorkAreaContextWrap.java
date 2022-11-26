package weblogic.work;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public class WorkAreaContextWrap implements Work, Describable, ComponentRequest {
   private final WorkContextMapInterceptor workAreaContext;
   private final InheritableThreadContext context;
   private final Runnable action;
   private final Runnable cancelAction;
   private final Runnable overloadAction;
   private final ComponentInvocationContext cic;

   public WorkAreaContextWrap(Runnable r) {
      this(r, (Runnable)null);
   }

   public WorkAreaContextWrap(Runnable r, Runnable o) {
      this((Runnable)r, (Runnable)o, (Runnable)null);
   }

   public WorkAreaContextWrap(Runnable r, Runnable o, Runnable cancelAction) {
      this.action = r;
      this.overloadAction = o;
      this.cancelAction = cancelAction;
      this.context = InheritableThreadContext.getContext();
      this.cic = PartitionUtility.getCurrentComponentInvocationContext();
      this.workAreaContext = WorkContextHelper.getWorkContextHelper().getInterceptor().copyThreadContexts(2);
   }

   private WorkAreaContextWrap(InheritableThreadContext c, ComponentInvocationContext cic, Runnable a) {
      this.action = a;
      this.context = c;
      this.overloadAction = null;
      this.cancelAction = null;
      this.cic = cic;
      this.workAreaContext = null;
   }

   public final void run() {
      this.context.push();
      if (this.workAreaContext != null) {
         WorkContextHelper.getWorkContextHelper().getInterceptor().restoreThreadContexts(this.workAreaContext);
      }

      try {
         this.action.run();
      } finally {
         this.context.pop();
      }

   }

   public Runnable overloadAction(String reason) {
      Runnable r;
      return this.overloadAction == null ? null : (this.overloadAction instanceof Work ? ((r = ((Work)this.overloadAction).overloadAction(reason)) != null ? new WorkAreaContextWrap(this.context, this.cic, r) : null) : new WorkAreaContextWrap(this.context, this.cic, this.overloadAction));
   }

   public Runnable cancel(String reason) {
      Runnable r;
      return this.cancelAction == null ? null : (this.cancelAction instanceof Work ? ((r = ((Work)this.cancelAction).cancel(reason)) != null ? new WorkAreaContextWrap(this.context, this.cic, r) : null) : new WorkAreaContextWrap(this.context, this.cic, this.cancelAction));
   }

   public String getDescription() {
      return this.action instanceof Describable ? ((Describable)this.action).getDescription() : null;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.cic;
   }
}
