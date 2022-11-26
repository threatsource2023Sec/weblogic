package kodo.conf.descriptor;

public interface FetchGroupsBean {
   String[] getFetchGroups();

   void addFetchGroup(String var1);

   void removeFetchGroup(String var1);

   void setFetchGroups(String[] var1);
}
