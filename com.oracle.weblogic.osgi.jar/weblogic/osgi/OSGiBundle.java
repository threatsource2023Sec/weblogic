package weblogic.osgi;

public interface OSGiBundle {
   void start() throws OSGiException;

   void uninstall();

   void stop();
}
