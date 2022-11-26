package weblogic.jdbc.utils;

import java.util.Iterator;
import java.util.Properties;
import weblogic.jdbc.common.internal.AddressList;
import weblogic.jdbc.common.internal.JDBCUtil;

public class OracleJDBC4DescriptorURLHelper extends JDBCURLHelper {
   public OracleJDBC4DescriptorURLHelper() {
   }

   public OracleJDBC4DescriptorURLHelper(JDBCDriverInfo info) {
      super(info);
   }

   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      AddressList hostPorts = info.getHostPorts();
      if (hostPorts != null && hostPorts.size() != 0) {
         Iterator it = hostPorts.iterator();

         while(it.hasNext()) {
            AddressList.HostPort hp = (AddressList.HostPort)it.next();
            if (!this.isValid(hp.host)) {
               throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
            }

            if (hp.port <= 0) {
               throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
            }
         }
      } else {
         if (!this.isValid(info.getDbmsHost())) {
            throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
         }

         if (!this.isValid(info.getDbmsPort())) {
            throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
         }

         hostPorts = new AddressList();
         hostPorts.add(info.getDbmsHost(), Integer.parseInt(info.getDbmsPort()));
      }

      String protocol = this.getOtherAttribute("protocol", info);
      if (!this.isValid(protocol)) {
         protocol = "TCP";
      }

      String drcpConnectionClass = this.getOtherAttribute("DRCPConnectionClass", info);
      String serviceName = this.getOtherAttribute("servicename", info);
      if (!this.isValid(serviceName)) {
         throw new JDBCDriverInfoException("Service name required");
      } else {
         StringBuffer buff = new StringBuffer("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=");
         Iterator it = hostPorts.iterator();
         boolean first = true;

         while(it.hasNext()) {
            AddressList.HostPort hp = (AddressList.HostPort)it.next();
            if (first) {
               first = false;
               if (it.hasNext()) {
                  buff.append("(LOAD_BALANCE=on)");
               }
            }

            if (hp.protocol != null) {
               buff.append("(ADDRESS=(PROTOCOL=" + hp.protocol + ")(HOST=");
            } else {
               buff.append("(ADDRESS=(PROTOCOL=" + protocol + ")(HOST=");
            }

            buff.append(hp.host);
            buff.append(")(PORT=");
            buff.append(hp.port);
            buff.append("))");
         }

         buff.append(")");
         buff.append("(CONNECT_DATA=");
         buff.append("(SERVICE_NAME=");
         buff.append(serviceName);
         buff.append(")");
         if (this.isValid(drcpConnectionClass)) {
            buff.append("(SERVER=POOLED)");
         }

         buff.append(")");
         buff.append(")");
         return buff.toString();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      Properties props = new Properties();
      String userName = info.getUserName();
      if (userName != null) {
         props.put("user", userName);
      }

      String drcpConnectionClass = this.getOtherAttribute("DRCPConnectionClass", info);
      if (this.isValid(drcpConnectionClass)) {
         props.put("oracle.jdbc.DRCPConnectionClass", drcpConnectionClass);
      }

      return props;
   }
}
