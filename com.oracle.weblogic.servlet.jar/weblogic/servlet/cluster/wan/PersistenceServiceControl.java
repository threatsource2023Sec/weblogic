package weblogic.servlet.cluster.wan;

/** @deprecated */
@Deprecated
public interface PersistenceServiceControl {
   void start();

   void stop();

   void halt();
}
