package weblogic.management.j2ee;

public interface JDBCResourceMBean extends J2EEResourceMBean {
   String[] getjdbcDataSources();
}
