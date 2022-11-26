package com.octetstring.vde.frontend;

import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class ListenerHandler {
   private static ListenerHandler instance = null;
   private static Vector listeners = null;

   private ListenerHandler() {
      listeners = new Vector();
   }

   public static ListenerHandler getInstance() {
      if (instance == null) {
         instance = new ListenerHandler();
      }

      return instance;
   }

   public void init() {
      String ihome = System.getProperty("vde.home");
      Properties listenProp = new Properties();

      String nl;
      try {
         nl = (String)ServerConfig.getInstance().get("vde.listeners.file");
         String fullname = null;
         if (ihome == null) {
            fullname = nl;
         } else {
            fullname = ihome + "/" + nl;
         }

         FileInputStream is = new FileInputStream(fullname);
         listenProp.load(is);
         is.close();
      } catch (Exception var14) {
         Logger.getInstance().log(0, this, Messages.getString("Error_parsing_Listener_properties._3"));
      }

      nl = System.getProperty("listener.num");
      if (nl == null) {
         nl = (String)listenProp.get("listener.num");
      }

      int numListen = new Integer(nl);

      for(int lCount = 0; lCount < numListen; ++lCount) {
         String listenType = System.getProperty("listener." + lCount + ".type");
         if (listenType == null) {
            listenType = (String)listenProp.get("listener." + lCount + ".type");
         }

         Logger.getInstance().log(7, this, Messages.getString("Listener_of_type___10") + listenType);
         Enumeration keys = listenProp.keys();
         Hashtable lConfig = new Hashtable();

         String key;
         int loc;
         String configKey;
         while(keys.hasMoreElements()) {
            key = (String)keys.nextElement();
            if (key.startsWith("listener." + lCount + ".config.")) {
               loc = key.lastIndexOf(46) + 1;
               configKey = key.substring(loc);
               if (!listenProp.get(key).equals("")) {
                  lConfig.put(configKey, listenProp.get(key));
               }
            }
         }

         keys = System.getProperties().keys();

         while(keys.hasMoreElements()) {
            key = (String)keys.nextElement();
            if (key.startsWith("listener." + lCount + ".config.")) {
               loc = key.lastIndexOf(46) + 1;
               configKey = key.substring(loc);
               if (!listenProp.get(key).equals("")) {
                  lConfig.put(configKey, System.getProperty(key));
               }
            }
         }

         try {
            Constructor[] cons = Class.forName(listenType).getConstructors();
            Constructor useCon = null;

            for(int i = 0; i < cons.length; ++i) {
               Class[] pt = cons[i].getParameterTypes();
               if (pt.length == 1 && pt[0].getName().equals("java.util.Hashtable")) {
                  useCon = cons[i];
                  break;
               }
            }

            if (useCon != null) {
               Object[] args = new Object[]{lConfig};
               listeners.addElement(useCon.newInstance(args));
            } else {
               Logger.getInstance().log(0, this, Messages.getString("Invalid_Listener___18") + listenType);
            }
         } catch (Exception var15) {
            Logger.getInstance().log(0, this, Messages.getString("Error_Instantiating_listener_of_type____19") + listenType + "': " + var15.getMessage());
         }
      }

   }
}
