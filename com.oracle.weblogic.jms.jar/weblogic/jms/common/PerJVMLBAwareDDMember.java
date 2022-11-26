package weblogic.jms.common;

public interface PerJVMLBAwareDDMember {
   String getMemberName();

   String getMemberNameForSort();

   String getDispatcherName();

   boolean isOnPreferredServer();

   boolean isPossiblyClusterTargeted();
}
