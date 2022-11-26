package weblogic.ejb.container.persistence.spi;

public interface EjbEntityRef {
   String getDescription();

   String getRemoteEjbName();

   String getEjbRefName();

   String getHome();

   String getRemote();

   String getEjbLink();

   String getJndiName();
}
