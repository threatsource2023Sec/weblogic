package weblogic.management.scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import javax.management.AttributeChangeNotification;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class WatchListener implements RemoteNotificationListener {
   String logFile = null;
   Object stdOutputMedium = null;
   boolean logToStandardOut = true;
   String watchName = null;
   FileOutputStream fos = null;
   private WLSTMsgTextFormatter txtFmt;

   public WatchListener(String logFile, Object stdOutputMedium, boolean logToStandardOut, String watchName, WLScriptContext ctx) throws ScriptException {
      try {
         this.txtFmt = ctx.getWLSTMsgFormatter();
         this.logFile = logFile;
         ctx.stdOutputMedium = stdOutputMedium;
         ctx.logToStandardOut = logToStandardOut;
         this.watchName = watchName;
         if (logFile != null) {
            this.fos = new FileOutputStream(new File(logFile));
            ctx.stdOutputMedium = this.fos;
            ctx.logToStandardOut = false;
         }
      } catch (FileNotFoundException var7) {
         this.println(this.txtFmt.getListenerFileNotLocated(logFile));
         var7.printStackTrace();
      }

   }

   public void handleNotification(Notification notification, Object handback) {
      if (notification instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)notification;
         this.println(this.getChangeInfo(acn));
      } else if (notification instanceof MBeanServerNotification) {
         this.println(this.getUnregInfo(notification));
      }

   }

   String getChangeInfo(AttributeChangeNotification acn) {
      String s = "\n##################################################################\n";
      s = s + this.txtFmt.getListenerName(this.watchName) + "\n";
      s = s + this.txtFmt.getMBeanChangedListener("" + acn.getSource()) + "\n";
      s = s + this.txtFmt.getAttributeChanged(acn.getAttributeName()) + "\n";
      s = s + this.txtFmt.getAttributeValueChanged(this.format(acn.getOldValue()), this.format(acn.getNewValue())) + "\n";
      s = s + "###################################################################\n";
      return s;
   }

   String getUnregInfo(Notification notif) {
      MBeanServerNotification msn = (MBeanServerNotification)notif;
      String s = "\n##################################################################\n";
      s = s + this.txtFmt.getListenerName(this.watchName) + "\n";
      s = s + this.txtFmt.getMBeanChangedListener("" + msn.getSource()) + "\n";
      s = s + this.txtFmt.getMBeanName("" + msn.getMBeanName()) + "\n";
      s = s + this.txtFmt.getMBeanUnregistered();
      s = s + "\n###################################################################\n";
      return s;
   }

   private void println(String s) {
      try {
         if (this.stdOutputMedium != null) {
            if (this.logToStandardOut) {
               System.out.println(s);
            }

            if (this.stdOutputMedium instanceof OutputStream) {
               ((OutputStream)this.stdOutputMedium).write(s.getBytes());
               ((OutputStream)this.stdOutputMedium).write("\n".getBytes());
               ((OutputStream)this.stdOutputMedium).flush();
            } else if (this.stdOutputMedium instanceof Writer) {
               if (this.stdOutputMedium instanceof PrintWriter) {
                  ((PrintWriter)this.stdOutputMedium).println(s);
                  ((PrintWriter)this.stdOutputMedium).flush();
               } else {
                  ((Writer)this.stdOutputMedium).write(s);
                  ((Writer)this.stdOutputMedium).write("\n");
                  ((Writer)this.stdOutputMedium).flush();
               }

               return;
            }

            return;
         }

         System.out.println(s);
      } catch (IOException var12) {
         var12.printStackTrace();
         return;
      } finally {
         try {
            if (this.stdOutputMedium != null) {
               if (this.stdOutputMedium instanceof OutputStream) {
                  ((OutputStream)this.stdOutputMedium).flush();
               } else if (this.stdOutputMedium instanceof Writer) {
                  if (this.stdOutputMedium instanceof PrintWriter) {
                     ((PrintWriter)this.stdOutputMedium).flush();
                  } else {
                     ((Writer)this.stdOutputMedium).flush();
                  }
               }
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   String format(Object o) {
      if (o == null) {
         return "(null)";
      } else {
         String result = new String(o.getClass().getName());
         result = result + "{ ";
         if (o.getClass().isArray()) {
            Object[] members = (Object[])((Object[])o);
            if (members.length < 1) {
               result = result + "(empty)";
            } else {
               Object[] var4 = members;
               int var5 = members.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object member = var4[var6];
                  result = result + this.format(member) + ", ";
               }

               result = result.substring(0, result.length() - 2);
            }
         } else {
            result = result + o.toString();
         }

         result = result + " }";
         return result;
      }
   }
}
