package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class ModuleRedeployDataTransferRequestImpl extends AppDataTransferRequestImpl {
   private static final long serialVersionUID = 4480914022040417421L;

   public ModuleRedeployDataTransferRequestImpl() {
   }

   public ModuleRedeployDataTransferRequestImpl(String appName, String appVersionIdentifier, long requestId, List filePaths, String lockPath, boolean isPlanUpdate, String partition) {
      super(appName, appVersionIdentifier, requestId, filePaths, lockPath, isPlanUpdate, partition);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
