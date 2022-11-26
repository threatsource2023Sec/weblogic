package kodo.jdbc.conf.descriptor;

public interface BatchingOperationOrderUpdateManagerBean extends UpdateManagerBean {
   boolean getMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);
}
