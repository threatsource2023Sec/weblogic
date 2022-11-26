package kodo.bea;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactoryAdapter;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Strings;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;

public class BEALogFactory extends LogFactoryAdapter implements MessageLoggerRegistryListener {
   private static final Properties dictionary = new Properties();
   protected MessageLogger rootMessageLogger;
   protected MessageDispatcher systemMessageDispatcher;
   private String diagnosticContext;

   private static void loadDictionary(URL url) {
      Properties dict = new Properties();
      InputStream in = null;

      try {
         in = url.openStream();
         dict.load(in);
      } catch (IOException var13) {
         KodoIntegrationLogger.logExceptionLoadingMessageDictionary("kodo/bea/message-id-dictionary.properties", var13);
      } finally {
         try {
            if (in != null) {
               in.close();
            }
         } catch (IOException var12) {
         }

      }

      if (!dict.isEmpty()) {
         Iterator iter = dict.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next();
            String key = (String)e.getKey();
            String value = (String)e.getValue();
            if (!dictionary.containsKey(key)) {
               dictionary.setProperty(key, value);
            } else if (!dictionary.get(key).equals(value)) {
               KodoIntegrationLogger.logDuplicateKey(key, dictionary.getProperty(key), value, url);
            }
         }
      }

   }

   public BEALogFactory() {
      this.messageLoggerRegistryUpdated();
      MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
   }

   public void messageLoggerRegistryUpdated() {
      this.rootMessageLogger = MessageLoggerRegistry.findMessageLogger("");
      this.systemMessageDispatcher = this.rootMessageLogger.getMessageDispatcher("com.oracle.logging.SystemLogger");
   }

   protected final Log newLogAdapter(String channel) {
      String channelName = channel.substring(channel.lastIndexOf(".") + 1);
      MessageDispatcher md;
      String subsystem;
      String bundleBaseName;
      if (!"MetaData".equals(channelName) && !"Enhance".equals(channelName) && !"Runtime".equals(channelName) && !"Query".equals(channelName) && !"DataCache".equals(channelName) && !"Tool".equals(channelName) && !"Manage".equals(channelName) && !"Profile".equals(channelName)) {
         if (!"SQL".equals(channelName) && !"JDBC".equals(channelName) && !"Schema".equals(channelName)) {
            md = this.rootMessageLogger.getMessageDispatcher(channel);
            subsystem = channel;
            bundleBaseName = "kodo.bea." + Strings.replace(channel, " ", "") + "LogLocalizer";
         } else {
            md = this.systemMessageDispatcher;
            subsystem = "Kodo JDBC";
            bundleBaseName = "kodo.bea.jdbc.KodoJDBCLogLocalizer";
         }
      } else {
         md = this.systemMessageDispatcher;
         subsystem = "Kodo";
         bundleBaseName = "kodo.bea.KodoLogLocalizer";
      }

      return this.newBEALogImpl(channel, this.rootMessageLogger, md, subsystem, bundleBaseName);
   }

   protected BEALogImpl newBEALogImpl(String channel, MessageLogger ml, String subsystem, String bundleBaseName) {
      return this.newBEALogImpl(channel, ml, this.systemMessageDispatcher, subsystem, bundleBaseName);
   }

   protected BEALogImpl newBEALogImpl(String channel, MessageLogger ml, MessageDispatcher md, String subsystem, String bundleBaseName) {
      return new BEALogImpl(channel, ml, md, subsystem, bundleBaseName, this);
   }

   protected static String toMessageId(Localizer.Message msg) {
      String kodoKey = LocalizedMessageMapper.toKodoKey(msg.getPackageName(), msg.getKey());
      return dictionary.getProperty(kodoKey);
   }

   public void setDiagnosticContext(String val) {
      this.diagnosticContext = val;
   }

   public String getDiagnosticContext() {
      return this.diagnosticContext;
   }

   static {
      Enumeration dictionaries = null;

      try {
         dictionaries = BEALogFactory.class.getClassLoader().getResources("kodo/bea/message-id-dictionary.properties");
      } catch (IOException var2) {
         KodoIntegrationLogger.logExceptionLoadingMessageDictionary("kodo/bea/message-id-dictionary.properties", var2);
      }

      if (dictionaries != null && !dictionaries.hasMoreElements()) {
         KodoIntegrationLogger.logNoMessageDictionary("kodo/bea/message-id-dictionary.properties");
      } else {
         while(dictionaries.hasMoreElements()) {
            loadDictionary((URL)dictionaries.nextElement());
         }
      }

   }
}
