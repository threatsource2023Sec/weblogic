package weblogic.diagnostics.image.descriptor;

public interface ServerRuntimeStateBean {
   InstanceMetricBean[] getInstanceMetrics();

   InstanceMetricBean createInstanceMetric();
}
