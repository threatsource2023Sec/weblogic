package weblogic.deploy.service;

import java.io.Externalizable;
import java.util.List;

public interface DataTransferRequest extends Externalizable {
   long getRequestId();

   List getFilePaths();

   List getTargetPaths();

   String getLockPath();
}
