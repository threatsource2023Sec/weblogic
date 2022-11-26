package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import weblogic.deploy.service.DataTransferRequest;

public abstract class DataTransferRequestImpl implements DataTransferRequest {
   private static final long serialVersionUID = -886329126108801508L;
   private long requestId;
   private String lockPath;
   private List filePaths;
   private List targetPaths;

   protected DataTransferRequestImpl() {
   }

   public DataTransferRequestImpl(long requestId, List filePaths, String lockPath) {
      this(requestId, filePaths, (List)null, lockPath);
   }

   public DataTransferRequestImpl(long requestId, List filePaths, List targetPaths, String lockPath) {
      this.requestId = requestId;
      this.filePaths = filePaths;
      this.targetPaths = (List)(targetPaths == null ? new ArrayList() : targetPaths);
      this.lockPath = lockPath;
   }

   public long getRequestId() {
      return this.requestId;
   }

   public List getFilePaths() {
      return this.filePaths;
   }

   public List getTargetPaths() {
      return this.targetPaths;
   }

   public String getLockPath() {
      return this.lockPath;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.requestId);
      out.writeObject(this.filePaths);
      out.writeObject(this.lockPath);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.requestId = in.readLong();
      this.filePaths = (List)in.readObject();
      this.lockPath = (String)in.readObject();
   }
}
