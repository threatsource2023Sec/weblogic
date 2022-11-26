package weblogic.diagnostics.image.descriptor;

public interface InstanceMetricBean {
   String getInstanceName();

   void setInstanceName(String var1);

   MetricBean[] getMBeanMetrics();

   MetricBean createMBeanMetric();
}
