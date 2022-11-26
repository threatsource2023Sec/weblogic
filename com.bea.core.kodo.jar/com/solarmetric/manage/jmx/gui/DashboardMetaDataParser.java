package com.solarmetric.manage.jmx.gui;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.XMLMetaDataParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DashboardMetaDataParser extends XMLMetaDataParser {
   public static final String DOCTYPE_DEC = "<!DOCTYPE dashboard SYSTEM 'file:/com/solarmetric/manage/jmx/gui/dashboard.dtd'>";
   private static final DashboardEntityResolver _resolver = new DashboardEntityResolver();
   private DashboardMetaData _dashboard = null;
   private DashboardChartPanelMetaData _chartPanel = null;
   private DashboardMBeanMetaData _mbean = null;
   private DashboardNotificationMetaData _notif = null;

   public DashboardMetaDataParser(Log log) {
      this.setLog(log);
      this.setSuffix(".dashboards");
   }

   protected void reset() {
      this._dashboard = null;
      this._chartPanel = null;
      this._mbean = null;
      this._notif = null;
   }

   protected Reader getDocType() throws IOException {
      return new StringReader("<!DOCTYPE dashboard SYSTEM 'file:/com/solarmetric/manage/jmx/gui/dashboard.dtd'>");
   }

   protected boolean startElement(String name, Attributes attrs) throws SAXException {
      if (this.currentDepth() == 0) {
         return true;
      } else {
         char firstLetter = name.charAt(0);
         switch (firstLetter) {
            case 'c':
               this.startChartPanel(attrs);
               return true;
            case 'd':
               this.startDashboard(attrs);
               return true;
            case 'm':
               this.startMBean(attrs);
               return true;
            case 'n':
               this.startNotification(attrs);
               return true;
            default:
               return false;
         }
      }
   }

   protected void endElement(String name) {
      if (this.currentDepth() != 0) {
         char firstLetter = name.charAt(0);
         switch (firstLetter) {
            case 'c':
               this.endChartPanel();
               break;
            case 'd':
               this.endDashboard();
               break;
            case 'm':
               this.endMBean();
               break;
            case 'n':
               this.endNotification();
         }

      }
   }

   protected void startDashboard(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      this._dashboard = new DashboardMetaData(name);
   }

   protected void endDashboard() {
      this.addResult(this._dashboard);
      this._dashboard = null;
   }

   protected void startChartPanel(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      this._chartPanel = new DashboardChartPanelMetaData(name);
   }

   protected void endChartPanel() {
      this._dashboard.add(this._chartPanel);
      this._chartPanel = null;
   }

   protected void startMBean(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      this._mbean = new DashboardMBeanMetaData(name);
   }

   protected void endMBean() {
      this._chartPanel.add(this._mbean);
      this._mbean = null;
   }

   protected void startNotification(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      this._notif = new DashboardNotificationMetaData(name);
   }

   protected void endNotification() {
      this._mbean.add(this._notif);
      this._notif = null;
   }

   public InputSource resolveEntity(String pub, String sys) throws SAXException {
      InputSource source = _resolver.resolveEntity(pub, sys);

      try {
         return source == null ? super.resolveEntity(pub, sys) : source;
      } catch (SAXException var5) {
         throw var5;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new SAXException(var7);
      }
   }
}
