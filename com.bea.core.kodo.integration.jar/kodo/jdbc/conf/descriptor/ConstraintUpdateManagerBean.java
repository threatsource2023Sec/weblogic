package kodo.jdbc.conf.descriptor;

public interface ConstraintUpdateManagerBean extends UpdateManagerBean {
   boolean getMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);
}
