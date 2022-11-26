package javax.enterprise.inject.spi;

public interface ProcessSessionBean extends ProcessManagedBean {
   String getEjbName();

   SessionBeanType getSessionBeanType();
}
