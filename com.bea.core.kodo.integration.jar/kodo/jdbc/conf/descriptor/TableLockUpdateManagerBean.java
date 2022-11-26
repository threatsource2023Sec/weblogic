package kodo.jdbc.conf.descriptor;

public interface TableLockUpdateManagerBean extends UpdateManagerBean {
   boolean getMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);
}
