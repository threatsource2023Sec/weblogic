package weblogic.jdbc.utils;

import weblogic.utils.AssertionError;

public class JDBCURLHelperFactory {
   private JDBCURLHelperFactory() {
   }

   public static JDBCURLHelperFactory newInstance() {
      return new JDBCURLHelperFactory();
   }

   public JDBCURLHelper getJDBCURLHelper(JDBCDriverInfo driverInfo) throws JDBCURLHelperException {
      if (driverInfo == null) {
         throw new AssertionError("JDBCDriverInfo can't be null");
      } else {
         String helperClassName = driverInfo.getURLHelperClassName();
         if (helperClassName != null && helperClassName.length() >= 1) {
            try {
               JDBCURLHelper helper = (JDBCURLHelper)Class.forName(helperClassName).newInstance();
               helper.setJDBCDriverInfo(driverInfo);
               return helper;
            } catch (ClassCastException var4) {
               throw new JDBCURLHelperException("Exception instantiating JDBCURLHelperClass", var4);
            } catch (ClassNotFoundException var5) {
               throw new JDBCURLHelperException("Exception instantiating JDBCURLHelperClass", var5);
            } catch (InstantiationException var6) {
               throw new JDBCURLHelperException("Exception instantiating JDBCURLHelperClass", var6);
            } catch (IllegalAccessException var7) {
               throw new JDBCURLHelperException("Exception instantiating JDBCURLHelperClass", var7);
            }
         } else {
            throw new JDBCURLHelperException("URLHelperClassName is invalid for " + driverInfo.getDriverVendor() + "'s " + driverInfo.getDbmsVendor() + " driver");
         }
      }
   }
}
