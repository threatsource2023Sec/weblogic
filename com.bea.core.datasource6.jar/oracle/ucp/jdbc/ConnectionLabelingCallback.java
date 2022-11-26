package oracle.ucp.jdbc;

import java.util.Properties;

public interface ConnectionLabelingCallback extends oracle.ucp.ConnectionLabelingCallback {
   Properties getRequestedLabels();
}
