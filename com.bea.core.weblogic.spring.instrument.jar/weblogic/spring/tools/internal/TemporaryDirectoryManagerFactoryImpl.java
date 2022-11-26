package weblogic.spring.tools.internal;

public class TemporaryDirectoryManagerFactoryImpl implements TemporaryDirectoryManagerFactory {
   public TemporaryDirectoryManager buildInstance() {
      return new TemporaryDirectoryManagerImpl();
   }
}
