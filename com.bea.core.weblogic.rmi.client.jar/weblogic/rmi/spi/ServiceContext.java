package weblogic.rmi.spi;

public interface ServiceContext extends ServiceContextID {
   int getContextId();

   Object getContextData();

   boolean isUser();
}
