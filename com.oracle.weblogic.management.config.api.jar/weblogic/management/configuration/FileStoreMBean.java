package weblogic.management.configuration;

@SingleTargetOnly
public interface FileStoreMBean extends GenericFileStoreMBean, PersistentStoreMBean {
   String getName();
}
