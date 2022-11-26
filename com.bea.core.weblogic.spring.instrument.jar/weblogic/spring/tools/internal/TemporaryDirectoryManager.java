package weblogic.spring.tools.internal;

public interface TemporaryDirectoryManager {
   String getTemporaryDirectory();

   void cleanup();
}
