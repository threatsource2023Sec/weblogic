package weblogic.management.utils.fileobserver;

public interface FileChangeObserver {
   void initialize() throws Exception;

   void addListener(FileChangeListener var1);

   void removeListener(FileChangeListener var1);

   Iterable getListeners();

   void observeChanges();

   void destroy() throws Exception;
}
