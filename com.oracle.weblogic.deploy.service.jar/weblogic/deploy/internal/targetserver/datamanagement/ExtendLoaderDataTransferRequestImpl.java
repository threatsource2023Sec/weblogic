package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import weblogic.deploy.service.ExtendLoaderDataTransferRequest;

public class ExtendLoaderDataTransferRequestImpl extends DataTransferRequestImpl implements ExtendLoaderDataTransferRequest {
   private static final long serialVersionUID = -4628419306442622621L;

   public ExtendLoaderDataTransferRequestImpl() {
   }

   public ExtendLoaderDataTransferRequestImpl(long requestId, List filePaths, String lockPath) {
      super(requestId, filePaths, lockPath);
   }

   public ExtendLoaderDataTransferRequestImpl(long requestId, List filePaths, List targetPaths, String lockPath) {
      super(requestId, filePaths, targetPaths, lockPath);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
