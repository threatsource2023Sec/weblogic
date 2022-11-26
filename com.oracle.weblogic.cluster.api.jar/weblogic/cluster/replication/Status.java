package weblogic.cluster.replication;

public interface Status {
   int SUCCESS = 1;
   int FAILURE = -1;

   int getStatus();

   String getInfo();
}
