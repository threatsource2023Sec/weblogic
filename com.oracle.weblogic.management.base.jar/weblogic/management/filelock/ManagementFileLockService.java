package weblogic.management.filelock;

import java.io.File;
import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ManagementFileLockService {
   long DEFAULT_WAIT_TIME = 300000L;

   FileLockHandle getFileLock(File var1, long var2, boolean var4) throws IOException;

   FileLockHandle getFileLock(File var1, long var2) throws IOException;

   FileLockHandle getFileLock(File var1) throws IOException;

   FileLockHandle getConfigFileLock(long var1) throws IOException;

   FileLockHandle getConfigFileLock() throws IOException;
}
