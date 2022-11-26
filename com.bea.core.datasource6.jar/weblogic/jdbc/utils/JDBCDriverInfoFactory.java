package weblogic.jdbc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public final class JDBCDriverInfoFactory {
   private List driverInfos = null;
   private String[] dbVendorNames;

   JDBCDriverInfoFactory(List listOfJDBCDriverInfos) {
      this.driverInfos = listOfJDBCDriverInfos;
   }

   public String[] getDBMSVendorNames() {
      if (this.driverInfos == null) {
         return null;
      } else {
         if (this.dbVendorNames == null) {
            HashSet set = new HashSet();
            Iterator it = this.driverInfos.iterator();

            while(it.hasNext()) {
               MetaJDBCDriverInfo info = (MetaJDBCDriverInfo)it.next();
               set.add(info.getDbmsVendor());
            }

            this.dbVendorNames = new String[set.size()];
            this.dbVendorNames = (String[])((String[])set.toArray(this.dbVendorNames));
            Arrays.sort(this.dbVendorNames);
         }

         return this.dbVendorNames;
      }
   }

   public JDBCDriverInfo[] getDriverInfos(String vendorName) throws Exception {
      TreeSet set = new TreeSet();
      Iterator i = this.driverInfos.iterator();

      while(true) {
         MetaJDBCDriverInfo driverInfo;
         do {
            if (!i.hasNext()) {
               JDBCDriverInfo[] vendorDriverInfos = new JDBCDriverInfo[set.size()];
               vendorDriverInfos = (JDBCDriverInfo[])((JDBCDriverInfo[])set.toArray(vendorDriverInfos));
               Arrays.sort(vendorDriverInfos);
               return vendorDriverInfos;
            }

            driverInfo = (MetaJDBCDriverInfo)i.next();
         } while(driverInfo.getDescription() != null && (driverInfo.getDescription().indexOf("GridLink") != -1 || driverInfo.getDescription().indexOf("UCP") != -1));

         if (driverInfo.getDbmsVendor().equals(vendorName)) {
            set.add(new JDBCDriverInfo(driverInfo));
         }
      }
   }

   public JDBCDriverInfo[] getGridLinkDriverInfos() throws Exception {
      TreeSet set = new TreeSet();
      Iterator i = this.driverInfos.iterator();

      while(i.hasNext()) {
         MetaJDBCDriverInfo driverInfo = (MetaJDBCDriverInfo)i.next();
         if (driverInfo.getDescription() != null && driverInfo.getDescription().indexOf("GridLink") != -1) {
            set.add(new JDBCDriverInfo(driverInfo));
         }
      }

      JDBCDriverInfo[] vendorDriverInfos = new JDBCDriverInfo[set.size()];
      vendorDriverInfos = (JDBCDriverInfo[])((JDBCDriverInfo[])set.toArray(vendorDriverInfos));
      return vendorDriverInfos;
   }

   public JDBCDriverInfo[] getUCPDriverInfos() throws Exception {
      TreeSet set = new TreeSet();
      Iterator i = this.driverInfos.iterator();

      while(i.hasNext()) {
         MetaJDBCDriverInfo driverInfo = (MetaJDBCDriverInfo)i.next();
         if (driverInfo.getDescription() != null && driverInfo.getDescription().indexOf("UCP") != -1) {
            set.add(new JDBCDriverInfo(driverInfo));
         }
      }

      JDBCDriverInfo[] vendorDriverInfos = new JDBCDriverInfo[set.size()];
      vendorDriverInfos = (JDBCDriverInfo[])((JDBCDriverInfo[])set.toArray(vendorDriverInfos));
      return vendorDriverInfos;
   }

   public JDBCDriverInfo getDriverInfo(String vendorName) throws JDBCDriverInfoException {
      Iterator i = this.driverInfos.iterator();

      MetaJDBCDriverInfo metaInfo;
      do {
         if (!i.hasNext()) {
            return null;
         }

         metaInfo = (MetaJDBCDriverInfo)i.next();
      } while(!metaInfo.toString().equals(vendorName));

      return new JDBCDriverInfo(metaInfo);
   }

   public JDBCDriverInfo getDriverInfoByClass(String driverClassName) {
      Iterator i = this.driverInfos.iterator();

      MetaJDBCDriverInfo metaInfo;
      do {
         if (!i.hasNext()) {
            return null;
         }

         metaInfo = (MetaJDBCDriverInfo)i.next();
      } while(!metaInfo.getDriverClassName().equals(driverClassName));

      return new JDBCDriverInfo(metaInfo);
   }

   public JDBCDriverInfo getDriverInfoByClass(String driverClassName, boolean isForXA) {
      Iterator i = this.driverInfos.iterator();

      MetaJDBCDriverInfo metaInfo;
      do {
         if (!i.hasNext()) {
            return null;
         }

         metaInfo = (MetaJDBCDriverInfo)i.next();
      } while(metaInfo.isForXA() != isForXA || !metaInfo.getDriverClassName().equals(driverClassName));

      return new JDBCDriverInfo(metaInfo);
   }

   public List getJDBCDriverInfos() {
      List neweL = new ArrayList();
      Iterator i = this.driverInfos.iterator();

      while(true) {
         MetaJDBCDriverInfo metaInfo;
         do {
            if (!i.hasNext()) {
               return neweL;
            }

            metaInfo = (MetaJDBCDriverInfo)i.next();
         } while(metaInfo.getDescription() != null && (metaInfo.getDescription().indexOf("GridLink") != -1 || metaInfo.getDescription().indexOf("UCP") != -1));

         neweL.add(new JDBCDriverInfo(metaInfo));
      }
   }
}
