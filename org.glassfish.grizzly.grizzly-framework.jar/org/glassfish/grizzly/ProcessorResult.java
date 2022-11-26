package org.glassfish.grizzly;

public class ProcessorResult {
   private static final ProcessorResult NOT_RUN_RESULT;
   private static final ProcessorResult COMPLETE_RESULT;
   private static final ProcessorResult LEAVE_RESULT;
   private static final ProcessorResult REREGISTER_RESULT;
   private static final ProcessorResult ERROR_RESULT;
   private static final ProcessorResult TERMINATE_RESULT;
   private Status status;
   private Object data;

   private static ProcessorResult create() {
      return new ProcessorResult();
   }

   public static ProcessorResult createComplete() {
      return COMPLETE_RESULT;
   }

   public static ProcessorResult createComplete(Object data) {
      return create().setStatus(ProcessorResult.Status.COMPLETE).setData(data);
   }

   public static ProcessorResult createLeave() {
      return LEAVE_RESULT;
   }

   public static ProcessorResult createReregister(Context context) {
      return create().setStatus(ProcessorResult.Status.REREGISTER).setData(context);
   }

   public static ProcessorResult createError() {
      return ERROR_RESULT;
   }

   public static ProcessorResult createError(Object description) {
      return create().setStatus(ProcessorResult.Status.ERROR).setData(description);
   }

   public static ProcessorResult createRerun(Context context) {
      return create().setStatus(ProcessorResult.Status.RERUN).setData(context);
   }

   public static ProcessorResult createTerminate() {
      return TERMINATE_RESULT;
   }

   public static ProcessorResult createNotRun() {
      return NOT_RUN_RESULT;
   }

   private ProcessorResult() {
      this((Status)null, (Object)null);
   }

   private ProcessorResult(Status status) {
      this(status, (Object)null);
   }

   private ProcessorResult(Status status, Object context) {
      this.status = status;
      this.data = context;
   }

   public Status getStatus() {
      return this.status;
   }

   protected ProcessorResult setStatus(Status status) {
      this.status = status;
      return this;
   }

   public Object getData() {
      return this.data;
   }

   protected ProcessorResult setData(Object context) {
      this.data = context;
      return this;
   }

   static {
      NOT_RUN_RESULT = new ProcessorResult(ProcessorResult.Status.NOT_RUN, (Object)null);
      COMPLETE_RESULT = new ProcessorResult(ProcessorResult.Status.COMPLETE, (Object)null);
      LEAVE_RESULT = new ProcessorResult(ProcessorResult.Status.LEAVE, (Object)null);
      REREGISTER_RESULT = new ProcessorResult(ProcessorResult.Status.REREGISTER, (Object)null);
      ERROR_RESULT = new ProcessorResult(ProcessorResult.Status.ERROR, (Object)null);
      TERMINATE_RESULT = new ProcessorResult(ProcessorResult.Status.TERMINATE, (Object)null);
   }

   public static enum Status {
      COMPLETE,
      LEAVE,
      REREGISTER,
      RERUN,
      ERROR,
      TERMINATE,
      NOT_RUN;
   }
}
