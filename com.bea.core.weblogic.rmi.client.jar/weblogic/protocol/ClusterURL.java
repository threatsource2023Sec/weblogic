package weblogic.protocol;

import java.net.MalformedURLException;

public interface ClusterURL {
   String CLUSTER_URL_PREFIX = "cluster:";
   String CLUSTER_PROVIDER_URL = "weblogic.cluster.provider.url";

   String parseClusterURL(String var1) throws MalformedURLException;
}
