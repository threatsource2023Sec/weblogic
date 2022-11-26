package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import weblogic.deploy.service.ConfigDataTransferRequest;

public class ConfigDataTransferRequestImpl extends DataTransferRequestImpl implements ConfigDataTransferRequest {
   private static final long serialVersionUID = 2517073255669231968L;

   public ConfigDataTransferRequestImpl() {
   }

   public ConfigDataTransferRequestImpl(long requestId, List filePaths, String lockPath) {
      super(requestId, filePaths, lockPath);
   }

   public ConfigDataTransferRequestImpl(long requestId, List filePaths, List targetPaths, String lockPath) {
      super(requestId, filePaths, targetPaths, lockPath);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
