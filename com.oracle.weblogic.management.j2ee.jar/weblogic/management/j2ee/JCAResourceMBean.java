package weblogic.management.j2ee;

public interface JCAResourceMBean extends J2EEResourceMBean, StatsProviderMBean {
   String[] getconnectionFactories();
}
