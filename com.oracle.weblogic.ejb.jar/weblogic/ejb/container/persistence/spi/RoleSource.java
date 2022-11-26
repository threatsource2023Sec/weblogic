package weblogic.ejb.container.persistence.spi;

public interface RoleSource {
   String[] getDescriptions();

   String getEjbName();
}
