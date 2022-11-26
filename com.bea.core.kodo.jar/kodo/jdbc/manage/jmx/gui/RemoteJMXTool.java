package kodo.jdbc.manage.jmx.gui;

import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;

public class RemoteJMXTool {
   public static void main(String[] args) {
      com.solarmetric.manage.jmx.gui.RemoteJMXTool.main(new JDBCConfigurationImpl(), args);
   }
}
