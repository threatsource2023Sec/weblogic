package javax.batch.runtime;

public interface Metric {
   MetricType getType();

   long getValue();

   public static enum MetricType {
      READ_COUNT,
      WRITE_COUNT,
      COMMIT_COUNT,
      ROLLBACK_COUNT,
      READ_SKIP_COUNT,
      PROCESS_SKIP_COUNT,
      FILTER_COUNT,
      WRITE_SKIP_COUNT;
   }
}
