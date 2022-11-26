package weblogic.cluster.messaging.internal;

public interface ClusterMessageReceiver {
   boolean accept(ClusterMessage var1);

   ClusterResponse process(ClusterMessage var1) throws ClusterMessageProcessingException;
}
