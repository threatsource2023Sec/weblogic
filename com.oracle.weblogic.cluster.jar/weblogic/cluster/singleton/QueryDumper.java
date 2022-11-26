package weblogic.cluster.singleton;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class QueryDumper {
   public static final String TABLE_NAME = "dummyTable";
   public static final String MACHINE_TABLE_NAME = "dummyMachineTable";
   public static final String LEASE_NAME = "dummyLease";
   public static final String LEASE_NAME2 = "dummyLease2";
   public static final int LEASE_TIMEOUT = 15;

   private static int getDBType(String dbname) {
      dbname = dbname.toLowerCase(Locale.ENGLISH);

      try {
         return Integer.parseInt(dbname);
      } catch (NumberFormatException var2) {
         if (dbname.indexOf("oracle") != -1) {
            return 1;
         } else if (dbname.indexOf("sybase") != -1) {
            return 3;
         } else if (dbname.indexOf("microsoft") == -1 && dbname.indexOf("mssql") == -1) {
            if (dbname.indexOf("informix") != -1) {
               return 5;
            } else if (dbname.indexOf("db2") != -1) {
               return 6;
            } else if (dbname.indexOf("mysql") != -1) {
               return 9;
            } else if (dbname.indexOf("timesten") != -1) {
               return 7;
            } else {
               return dbname.indexOf("derby") != -1 ? 12 : 0;
            }
         } else {
            return 4;
         }
      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         System.out.println("err: " + args);
         System.exit(1);
      }

      String queryClass = args[0];
      QueryHelper qh = getQueryHelper(queryClass, "dummyTable", "dummyDomain", "dummyCluster", getDBType(args[1]));
      if (qh == null) {
         System.out.println("no query helper");
         System.exit(2);
      }

      String query = null;
      query = qh.getAcquireLeaseQuery();
      System.out.println("getAcquireLeaseQuery: \n\t" + query + "\n");
      query = qh.getAssumeLeaseQuery();
      System.out.println("getAssumeLeaseQuery: \n\t" + query + "\n");
      query = qh.getLockLeaseQuery();
      System.out.println("getLockLeaseQuery: \n\t" + query + "\n");
      query = qh.getUpdateLeaseQuery();
      System.out.println("getUpdateLeaseQuery: \n\t" + query + "\n");
      query = qh.getRenewLeaseQuery();
      System.out.println("getRenewLeaseQuery: \n\t" + query + "\n");
      Set leaseNames = new HashSet();
      leaseNames.add("dummyLease");
      query = qh.getRenewLeasesQuery(leaseNames.size());
      System.out.println("getRenewLeasesQuery: \n\t" + query + "\n");
      leaseNames.add("dummyLease2");
      query = qh.getRenewLeasesQuery(leaseNames.size());
      System.out.println("getRenewLeasesQuery: \n\t" + query + "\n");
      query = qh.getRenewLeasesQuery();
      System.out.println("getRenewLeasesQuery: \n\t" + query + "\n");
      query = qh.getAbdicateLeaseQuery();
      System.out.println("getAbdicateLeaseQuery: \n\t" + query + "\n");
      query = qh.getRenewAllLeasesQuery();
      System.out.println("getRenewAllLeasesQuery: \n\t" + query + "\n");
      query = qh.getLeaseOwnerQuery();
      System.out.println("getLeaseOwnerQuery: \n\t" + query + "\n");
      query = qh.getPreviousLeaseOwnerQuery();
      System.out.println("getPreviousLeaseOwnerQuery: \n\t" + query + "\n");
      query = qh.getUnresponsiveMigratableServersQuery();
      System.out.println("getUnresponsiveMigratableServersQuery: \n\t" + query + "\n");
      query = qh.getDeleteMachineQuery("dummyServer", "dummyMachineTable");
      System.out.println("getDeleteMachineQuery: \n\t" + query + "\n");
      query = qh.getInsertMachineQuery("dummyServer", "dummyMachine", "dummyMachineTable");
      System.out.println("getInsertMachineQuery: \n\t" + query + "\n");
      query = qh.getRetrieveMachineQuery("dummyServer", "dummyMachineTable");
      System.out.println("getRetrieveMachineQuery: \n\t" + query + "\n");
   }

   private static QueryHelper getQueryHelper(String queryHelperClassName, String tableName, String domainName, String clusterName, int dbType) {
      try {
         return new QueryHelperImpl(tableName, domainName, clusterName, dbType);
      } catch (Throwable var6) {
         System.out.println("Error creating " + queryHelperClassName + ": " + var6);
         var6.printStackTrace();
         return null;
      }
   }
}
