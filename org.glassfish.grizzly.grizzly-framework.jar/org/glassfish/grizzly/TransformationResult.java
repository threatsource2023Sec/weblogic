package org.glassfish.grizzly;

public class TransformationResult implements Cacheable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(TransformationResult.class, 2);
   private Object message;
   private Status status;
   private int errorCode;
   private String errorDescription;
   private Object externalRemainder;

   public static TransformationResult createErrorResult(int errorCode, String errorDescription) {
      return create(TransformationResult.Status.ERROR, (Object)null, (Object)null, errorCode, errorDescription);
   }

   public static TransformationResult createCompletedResult(Object message, Object externalRemainder) {
      return create(TransformationResult.Status.COMPLETE, message, externalRemainder, 0, (String)null);
   }

   public static TransformationResult createIncompletedResult(Object externalRemainder) {
      return create(TransformationResult.Status.INCOMPLETE, (Object)null, externalRemainder, 0, (String)null);
   }

   private static TransformationResult create(Status status, Object message, Object externalRemainder, int errorCode, String errorDescription) {
      TransformationResult result = (TransformationResult)ThreadCache.takeFromCache(CACHE_IDX);
      if (result != null) {
         result.setStatus(status);
         result.setMessage(message);
         result.setExternalRemainder(externalRemainder);
         result.setErrorCode(errorCode);
         result.setErrorDescription(errorDescription);
         return result;
      } else {
         return new TransformationResult(status, message, externalRemainder, errorCode, errorDescription);
      }
   }

   public TransformationResult() {
      this(TransformationResult.Status.COMPLETE, (Object)null, (Object)null);
   }

   public TransformationResult(Status status, Object message, Object externalRemainder) {
      this.status = status;
      this.message = message;
      this.externalRemainder = externalRemainder;
   }

   public TransformationResult(int errorCode, String errorDescription) {
      this.status = TransformationResult.Status.ERROR;
      this.errorCode = errorCode;
      this.errorDescription = errorDescription;
   }

   protected TransformationResult(Status status, Object message, Object externalRemainder, int errorCode, String errorDescription) {
      this.status = status;
      this.message = message;
      this.externalRemainder = externalRemainder;
      this.errorCode = errorCode;
      this.errorDescription = errorDescription;
   }

   public Object getMessage() {
      return this.message;
   }

   public void setMessage(Object message) {
      this.message = message;
   }

   public Object getExternalRemainder() {
      return this.externalRemainder;
   }

   public void setExternalRemainder(Object externalRemainder) {
      this.externalRemainder = externalRemainder;
   }

   public Status getStatus() {
      return this.status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public void setErrorCode(int errorCode) {
      this.errorCode = errorCode;
   }

   public String getErrorDescription() {
      return this.errorDescription;
   }

   public void setErrorDescription(String errorDescription) {
      this.errorDescription = errorDescription;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(128);
      sb.append("Transformation result. Status: ").append(this.status);
      sb.append(" message: ").append(this.message);
      if (this.status == TransformationResult.Status.ERROR) {
         sb.append(" errorCode: ").append(this.errorCode);
         sb.append(" errorDescription: ").append(this.errorDescription);
      }

      return sb.toString();
   }

   public void reset() {
      this.message = null;
      this.status = null;
      this.errorCode = 0;
      this.errorDescription = null;
      this.externalRemainder = null;
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static enum Status {
      COMPLETE,
      INCOMPLETE,
      ERROR;
   }
}
