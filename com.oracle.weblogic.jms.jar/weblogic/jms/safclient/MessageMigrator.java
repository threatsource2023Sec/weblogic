package weblogic.jms.safclient;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.safclient.admin.ConfigurationUtils;
import weblogic.jms.safclient.agent.AgentManager;
import weblogic.jms.safclient.jndi.ContextImpl;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class MessageMigrator {
   private static String SAFCLIENT_KERNEL_PREFIX = "weblogic.messaging.ClientSAFAgent";
   private static String SAFCLIENT_KERNEL_NAME_PREFIX = "ClientSAFAgent";
   private static String DEFAULT_CUTOFF_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
   private static SimpleDateFormat CUTOFF_TIME_FORMAT = null;
   public static final boolean revertBugFix;
   private static Map migrationDone;
   private PersistentStoreXA pStore;
   private ContextImpl context;
   private File pagingDirectory;
   private List oldKernels = new ArrayList();
   private Kernel kernel0;
   private Queue queue0;
   private Sequence sequence0;
   private int migrateTotal = 0;

   MessageMigrator(File rootDirectory, PersistentStoreXA pStore, ContextImpl context) {
      this.pStore = pStore;
      this.context = context;
      this.pagingDirectory = new File(rootDirectory, "paging");
   }

   static void migrateMessagesIfNecessary(File rootDirectory, PersistentStoreXA pStore, ContextImpl context) throws JMSException {
      if (!revertBugFix) {
         boolean doMigration = Boolean.valueOf(System.getProperty("weblogic.jms.safclient.MigrateExistingMessages", "true"));
         if (doMigration) {
            Object doneObj = migrationDone.get(rootDirectory);
            if (doneObj == null || !(Boolean)doneObj) {
               String cutoffTimeString = System.getProperty("weblogic.jms.safclient.MigrationCutoffTime");
               long cutoffTime = -1L;
               if (cutoffTimeString != null) {
                  try {
                     cutoffTime = CUTOFF_TIME_FORMAT.parse(cutoffTimeString).getTime();
                  } catch (ParseException var9) {
                     throwJMSException(var9, "The cutoff time property " + cutoffTimeString + " is not of " + CUTOFF_TIME_FORMAT + " format");
                  }
               }

               MessageMigrator migrator = new MessageMigrator(rootDirectory, pStore, context);
               migrator.migrateMessages(cutoffTime);
               migrationDone.put(rootDirectory, new Boolean(true));
            }
         }
      }
   }

   static void discover(File rootDirectory, PersistentStoreXA pStore, ContextImpl context, String discoveryFilePath, long cutoffTime) throws JMSException {
      File discoveryFile;
      if (discoveryFilePath != null && !discoveryFilePath.equals("")) {
         discoveryFile = new File(discoveryFilePath);
      } else {
         discoveryFile = new File(rootDirectory, "SAF_DISCOVERY");
      }

      MessageMigrator migrator = new MessageMigrator(rootDirectory, pStore, context);
      migrator.discoverLocalSAF(discoveryFile, cutoffTime);
   }

   private void discoverLocalSAF(File discoveryFile, long cutoffTime) throws JMSException {
      PrintStream pw = null;

      try {
         if (discoveryFile == null) {
            pw = System.out;
         } else {
            if (!discoveryFile.exists()) {
               discoveryFile.createNewFile();
            }

            pw = new PrintStream(discoveryFile);
         }
      } catch (IOException var15) {
         throwJMSException(var15, "Error in open discovery file " + discoveryFile);
      }

      try {
         this.printDiscovery(pw, 0, "Client SAF discovery:");
         this.settlePagingDirectory();
         this.openKernels();
         Map destMap = this.context.getDestinationMap();
         Iterator var6 = destMap.keySet().iterator();

         while(var6.hasNext()) {
            Object groupObj = var6.next();
            String group = (String)groupObj;
            Iterator var9 = ((Map)destMap.get(group)).keySet().iterator();

            while(var9.hasNext()) {
               Object destObj = var9.next();
               this.discoverDestination(group, (String)destObj, cutoffTime, pw, 1);
            }
         }
      } catch (JMSException var16) {
         this.printDiscovery(pw, 0, "\nEncouter error during discovery:" + var16.getMessage());
      } finally {
         this.closeKernels();
         if (!pw.equals(System.out)) {
            pw.close();
         }

      }

      System.out.println("Client SAF discovery has been written to file " + discoveryFile);
   }

   private void discoverDestination(String group, String destination, long cutoffTime, PrintStream pw, int indention) {
      this.printDiscovery(pw, indention, "Group:" + group + ", destination:" + destination);
      if (this.kernel0 != null) {
         this.discoverKernelQueue(this.kernel0, group, destination, cutoffTime, pw, indention + 1);
      }

      Iterator var7 = this.oldKernels.iterator();

      while(var7.hasNext()) {
         Kernel kernel = (Kernel)var7.next();
         this.discoverKernelQueue(kernel, group, destination, cutoffTime, pw, indention + 1);
      }

   }

   private void discoverKernelQueue(Kernel kernel, String group, String destination, long cutoffTime, PrintStream pw, int indention) {
      String fullDestName = "(group:" + group + ", destination:" + destination + ")";
      Cursor cursor = null;

      try {
         Queue queue = this.openKernelQueue(kernel, group, destination);
         if (queue != null) {
            this.printDiscovery(pw, indention, "Kernel " + kernel.getName() + ": queue " + queue.getName() + " was created for " + fullDestName);
            cursor = queue.createCursor(true, (Expression)null, 1073);
            this.printDiscovery(pw, indention + 1, "Total message in kernel queue " + cursor.size());
            MessageElement el = null;
            MessageElement first = null;
            MessageElement last = null;
            int msgCount = 0;
            int discardCount = 0;

            while((el = cursor.next()) != null) {
               if (msgCount == 0) {
                  first = el;
               } else {
                  last = el;
               }

               ++msgCount;
               if (((Message)el.getMessage()).getJMSTimestamp() < cutoffTime) {
                  ++discardCount;
               }
            }

            String cutoffStat = "No cutoff time is specified";
            if (cutoffTime > 0L) {
               cutoffStat = "The number of messages before cutoff time is " + discardCount;
            }

            this.printDiscovery(pw, indention + 1, cutoffStat);
            if (first != null) {
               this.printDiscovery(pw, indention + 1, "The first message in this kernel queue:");
               this.discoverMessage(first, pw, indention + 1);
            }

            if (last != null) {
               this.printDiscovery(pw, indention + 1, "The last message in this kernel queue:");
               this.discoverMessage(last, pw, indention + 1);
            }

            return;
         }

         this.printDiscovery(pw, indention, "Kernel " + kernel.getName() + ": No kernel queue was created for " + fullDestName);
      } catch (Throwable var20) {
         this.printDiscovery(pw, indention, "Encounter error when discovery " + fullDestName + " in kernel " + kernel.getName());
         this.printDiscovery(pw, indention, "Error: " + var20.getMessage());
         return;
      } finally {
         if (cursor != null) {
            cursor.close();
         }

      }

   }

   private void discoverMessage(MessageElement el, PrintStream pw, int indention) {
      if (el != null) {
         WLMessage msg = (WLMessage)el.getMessage();

         try {
            this.printDiscovery(pw, indention + 1, "JMSMessageID=" + msg.getJMSMessageID());
         } catch (JMSException var8) {
         }

         try {
            this.printDiscovery(pw, indention + 1, "JMSCorrelationID=" + msg.getJMSCorrelationID());
         } catch (JMSException var7) {
         }

         try {
            this.printDiscovery(pw, indention + 1, "JMSTimestamp=" + CUTOFF_TIME_FORMAT.format(new Date(msg.getJMSTimestamp())));
         } catch (JMSException var6) {
         }

         this.printDiscovery(pw, indention + 1, "SAFSequenceName=" + el.getSequence().getName());
         this.printDiscovery(pw, indention + 1, "SAFSeqNumber=" + el.getSequenceNum());
         this.printDiscovery(pw, indention + 1, "UnitOfOrder=" + msg.getUnitOfOrder());
      }
   }

   private void printDiscovery(PrintStream pw, int indention, String msg) {
      for(int i = 0; i < indention; ++i) {
         pw.print("    ");
      }

      pw.println(msg);
   }

   private void migrateMessages(long cutoffTime) throws JMSException {
      String migrateStatus = "NONE";

      try {
         this.settlePagingDirectory();
         this.openKernels();
         if (this.oldKernels.size() != 0) {
            if (this.kernel0 == null) {
               this.kernel0 = this.openKernel(SAFCLIENT_KERNEL_NAME_PREFIX + "0");
            }

            migrateStatus = "PARTIAL";
            Map destMap = this.context.getDestinationMap();
            Iterator var5 = destMap.keySet().iterator();

            while(var5.hasNext()) {
               Object groupObj = var5.next();
               String group = (String)groupObj;
               Iterator var8 = ((Map)destMap.get(group)).keySet().iterator();

               while(var8.hasNext()) {
                  Object destObj = var8.next();
                  this.migrateDestination(group, (String)destObj, cutoffTime);
               }
            }

            migrateStatus = "COMPLETE";
            return;
         }
      } finally {
         this.closeKernels();
         if (migrateStatus.equals("COMPLETE")) {
            System.out.println("The message migration was successfully done. The total messages migrated was " + this.migrateTotal);
         } else if (migrateStatus.equals("PARTIAL")) {
            System.out.println("The message migration was partially done. The total messages migrated was " + this.migrateTotal);
         }

      }

   }

   private void migrateDestination(String group, String destination, long cutoffTime) throws JMSException {
      this.setupKernelQueue0(group, destination, cutoffTime);
      Iterator var5 = this.oldKernels.iterator();

      while(true) {
         Queue queue;
         do {
            if (!var5.hasNext()) {
               return;
            }

            Kernel kernel = (Kernel)var5.next();
            queue = this.openKernelQueue(kernel, group, destination);
         } while(queue == null);

         Cursor cursor = null;

         try {
            cursor = queue.createCursor(true, (Expression)null, 1073);
            MessageElement el = null;

            label104:
            while(true) {
               while(true) {
                  if ((el = cursor.next()) == null) {
                     break label104;
                  }

                  if (cutoffTime > 0L && ((Message)el.getMessage()).getJMSTimestamp() < cutoffTime) {
                     queue.delete(el);
                  } else {
                     this.moveOneMessageToQueue0(el, queue);
                  }
               }
            }
         } catch (KernelException var13) {
            throwJMSException(var13, "Failed to migrate message for group:" + group + ", destination:" + destination);
         } finally {
            if (cursor != null) {
               cursor.close();
            }

         }

         this.deleteKernelQueue(queue);
      }
   }

   private void moveOneMessageToQueue0(MessageElement element, Queue srcQueue) throws JMSException {
      MessageImpl msg = (MessageImpl)element.getMessage();
      SendOptions options = new SendOptions();
      options.setPersistent(msg.getJMSDeliveryMode() == 2);
      int redeliveryLimit = msg.getJMSRedeliveryLimit();
      if (redeliveryLimit >= 0) {
         options.setRedeliveryLimit(redeliveryLimit);
      }

      options.setExpirationTime(msg.getJMSExpiration());
      options.setDeliveryTime(msg.getJMSDeliveryTime());
      options.setNoDeliveryDelay(msg.getJMSTimestamp() == msg.getDeliveryTime());
      options.setSequence(this.sequence0);
      ClientTransactionManager ctm = TransactionHelper.getTransactionHelper().getTransactionManager();
      boolean inTxn = false;

      try {
         ctm.begin();
         inTxn = true;
         KernelRequest kernelRequest = this.queue0.send(msg, options);
         if (kernelRequest != null) {
            kernelRequest.getResult();
         }

         kernelRequest = srcQueue.delete(element);
         if (kernelRequest != null) {
            kernelRequest.getResult();
         }

         ctm.commit();
         inTxn = false;
         ++this.migrateTotal;
      } catch (Throwable var16) {
         throwJMSException(var16, "Failed to move one message to from other kernel queue to kernel0 queue");
      } finally {
         if (inTxn) {
            try {
               ctm.rollback();
            } catch (Throwable var17) {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("Failed to rollback the migration transaction:" + var17.getMessage());
               }
            }
         }

      }

   }

   private void settlePagingDirectory() throws JMSException {
      if (!this.pagingDirectory.exists()) {
         if (!this.pagingDirectory.mkdirs()) {
            throw new JMSException("Failed to create paging directory " + this.pagingDirectory.getAbsolutePath());
         }
      } else if (!this.pagingDirectory.isDirectory()) {
         throw new JMSException("The file " + this.pagingDirectory.getAbsolutePath() + " must be a directory, it will be used for the paging store");
      }

   }

   private Queue openKernelQueue(Kernel kernel, String group, String destination) throws JMSException {
      String fullName = AgentManager.constructDestinationName(group, destination);
      Queue queue = kernel.findQueue(fullName);
      if (queue != null) {
         try {
            queue.resume(16384);
         } catch (KernelException var7) {
            throwJMSException(var7, "Failed to resume the kernel queue " + fullName + " of kernel " + kernel.getName());
         }
      }

      return queue;
   }

   private void setupKernelQueue0(String group, String destination, long cutoffTime) throws JMSException {
      this.queue0 = this.openKernelQueue(this.kernel0, group, destination);
      String fullName;
      if (this.queue0 == null) {
         fullName = AgentManager.constructDestinationName(group, destination);
         Map properties = new HashMap();
         properties.put("Durable", new Boolean(true));
         properties.put("MaximumMessageSize", new Integer(Integer.MAX_VALUE));

         try {
            this.queue0 = this.kernel0.createQueue(fullName, properties);
         } catch (KernelException var16) {
            throwJMSException(var16, "Failed to create the kernel queue " + fullName + " of kernel0");
         }

         try {
            this.queue0.resume(16384);
         } catch (KernelException var15) {
            throwJMSException(var15, "Failed to resume the kernel queue " + fullName + " of kernel0");
         }
      }

      fullName = ConfigurationUtils.getSequenceNameFromQueue(this.queue0);

      try {
         this.sequence0 = this.queue0.findOrCreateSequence(fullName, 1);
      } catch (KernelException var14) {
         throwJMSException(var14, "Failed to create sequence " + fullName + " for queue " + this.queue0.getName());
      }

      if (cutoffTime > 0L) {
         Cursor cursor = null;

         try {
            cursor = this.queue0.createCursor(true, (Expression)null, 1073);
            MessageElement el = null;

            while((el = cursor.next()) != null) {
               if (((Message)el.getMessage()).getJMSTimestamp() < cutoffTime) {
                  this.queue0.delete(el);
               }
            }
         } catch (KernelException var17) {
            throwJMSException(var17, "Failed to cleanup queue0 based on the cutoff time");
         } finally {
            if (cursor != null) {
               cursor.close();
            }

         }
      }

   }

   private void deleteKernelQueue(Queue queue) {
      try {
         KernelRequest request = new KernelRequest();
         queue.delete(request);
         request.getResult();
      } catch (KernelException var3) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("Failed to delete kernel queue " + queue.getName() + " with error:" + var3.getMessage());
         }
      }

   }

   private Kernel openKernel(String kname) throws JMSException {
      Map props = new HashMap();
      props.put("PagingDirectory", this.pagingDirectory.getAbsolutePath());
      props.put("Store", this.pStore);
      WorkManager workManager = WorkManagerFactory.getInstance().getSystem();
      WorkManager limitedWorkManager = WorkManagerFactory.getInstance().getSystem();
      String seq = AgentManager.getManagerSequence();
      String limitedTimerManagerName = "client.SAF." + kname + seq;
      String directTimerManagerName = "client.SAF." + kname + seq + ".direct";
      props.put("WorkManager", workManager);
      props.put("LimitedWorkManager", limitedWorkManager);
      props.put("LimitedTimerManagerName", limitedTimerManagerName);
      props.put("DirectTimerManagerName", directTimerManagerName);
      Kernel kernel = null;

      try {
         kernel = new KernelImpl(kname, props);
         kernel.open();
         kernel.setProperty("MessageBufferSize", new Long(Long.MAX_VALUE));
         kernel.setProperty("MaximumMessageSize", new Integer(Integer.MAX_VALUE));
      } catch (KernelException var10) {
         throwJMSException(var10, "Failed to open the Kernel " + kname);
      }

      return kernel;
   }

   private void openKernels() throws JMSException {
      SortedMap kernelMap = new TreeMap();
      Iterator iter = this.pStore.getConnectionNames();

      while(true) {
         boolean var12 = false;

         try {
            var12 = true;
            if (!iter.hasNext()) {
               var12 = false;
               break;
            }

            String cname = (String)iter.next();
            if (cname.startsWith(SAFCLIENT_KERNEL_PREFIX) && cname.endsWith(".header")) {
               int count = false;

               int count;
               try {
                  count = Integer.parseInt(cname.substring(SAFCLIENT_KERNEL_PREFIX.length(), cname.indexOf(".header")));
               } catch (NumberFormatException var13) {
                  continue;
               }

               String kname = SAFCLIENT_KERNEL_NAME_PREFIX + count;
               Kernel kernel = this.openKernel(kname);
               if (count == 0) {
                  this.kernel0 = kernel;
               } else {
                  kernelMap.put(new Integer(count), kernel);
               }
            }
         } finally {
            if (var12) {
               Iterator var8 = kernelMap.values().iterator();

               while(var8.hasNext()) {
                  Kernel kernel = (Kernel)var8.next();
                  this.oldKernels.add(kernel);
               }

            }
         }
      }

      Iterator var15 = kernelMap.values().iterator();

      while(var15.hasNext()) {
         Kernel kernel = (Kernel)var15.next();
         this.oldKernels.add(kernel);
      }

   }

   private void closeKernels() {
      if (this.oldKernels != null) {
         Iterator var1 = this.oldKernels.iterator();

         while(var1.hasNext()) {
            Kernel kernel = (Kernel)var1.next();

            try {
               kernel.close();
            } catch (KernelException var5) {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("Failed to close the kernel " + kernel.getName() + " with error:" + var5.getMessage());
               }
            }
         }

         this.oldKernels = null;
      }

      if (this.kernel0 != null) {
         try {
            this.kernel0.close();
         } catch (KernelException var4) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Failed to close the kernel0 with error:" + var4.getMessage());
            }
         }

         this.kernel0 = null;
      }

   }

   private static void throwJMSException(Throwable t, String errorMessage) throws JMSException {
      JMSException jmse = new JMSException(errorMessage);
      jmse.initCause(t);
      throw jmse;
   }

   static {
      String format = null;
      if ((format = System.getProperty("weblogic.jms.safclient.MigrationCutoffTimeFormat")) == null) {
         format = DEFAULT_CUTOFF_TIME_FORMAT;
      }

      CUTOFF_TIME_FORMAT = new SimpleDateFormat(format);
      revertBugFix = Boolean.valueOf(System.getProperty("weblogic.jms.safclient.revertBug8174629Fix", "true"));
      migrationDone = new HashMap();
   }
}
