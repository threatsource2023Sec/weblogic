package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ServerSessionPool;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationSecurity;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageEventLogListener;
import weblogic.jms.common.JMSMessageLogHelper;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.common.JMSSQLFilter;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.extensions.ConsumerClosedException;
import weblogic.logging.jms.JMSMessageLogger;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.EventListener;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Filter;
import weblogic.messaging.kernel.GroupAddEvent;
import weblogic.messaging.kernel.GroupRemoveEvent;
import weblogic.messaging.kernel.InvalidExpressionException;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageAddEvent;
import weblogic.messaging.kernel.MessageEvent;
import weblogic.messaging.kernel.MessageExpirationEvent;
import weblogic.messaging.kernel.MessageReceiveEvent;
import weblogic.messaging.kernel.MessageRedeliveryLimitEvent;
import weblogic.messaging.kernel.MessageRemoveEvent;
import weblogic.messaging.kernel.MessageSendEvent;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.UnitOfWorkEvent;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public class BEQueueImpl extends BEDestinationImpl implements JMSMessageEventLogListener, EventListener {
   private Queue queue;
   private Filter sqlFilter;
   private final Map browsers;
   private boolean isNewlyCreated;
   protected String alternativeFullyQualifiedDestinationName;

   public BEQueueImpl(BackEnd backEnd, String name, boolean temporary, JMSDestinationSecurity jmsDestinationSecurity) throws JMSException {
      this(backEnd, name, temporary, jmsDestinationSecurity, (String)null);
   }

   public BEQueueImpl(BackEnd backEnd, String name, boolean temporary, JMSDestinationSecurity jmsDestinationSecurity, String alternativeName) throws JMSException {
      super(backEnd, name, temporary, jmsDestinationSecurity);
      this.browsers = new HashMap();
      Queue kernelQueue = backEnd.findKernelQueue(name);
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Looked up kernel queue using name " + name + " kernelQueue = " + kernelQueue + " this = " + this + " alternativeName = " + alternativeName + " kernel useAlternativeName = " + backEnd.getKernel().useAlternativeName());
      }

      if (kernelQueue == null) {
         if (alternativeName != null && backEnd.getKernel().useAlternativeName()) {
            kernelQueue = backEnd.findKernelQueue(alternativeName);
            if (kernelQueue != null) {
               this.alternativeFullyQualifiedDestinationName = this.getFullyQualifiedServerName() + '@' + alternativeName;
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Found kernel queue using alternative name " + alternativeName + " kernelQueue = " + kernelQueue + " this = " + this);
               }
            }
         }

         if (kernelQueue == null) {
            kernelQueue = backEnd.createKernelQueue(name, (Map)null, alternativeName);
            this.isNewlyCreated = true;
         }
      }

      this.setKernel(kernelQueue);
   }

   protected void setKernel(Queue queue) throws JMSException {
      super.setKernel(queue);
      this.queue = queue;
   }

   public void open() throws JMSException {
      super.open();
      this.sqlFilter = new JMSSQLFilter(this.queue.getKernel());
      this.queue.setFilter(this.sqlFilter);
      this.queue.setComparator(this.comparator);

      try {
         this.queue.setProperty("RedirectionListener", this);
      } catch (KernelException var2) {
         throw new weblogic.jms.common.JMSException(var2);
      }

      this.queue.addListener(this);
      if (this.isMessageLoggingEnabled() && !this.backEnd.isMemoryLow()) {
         this.resumeMessageLogging();
      }

      BEExtension stableExtension = this.getExtension();
      if (stableExtension != null) {
         stableExtension.restorePersistentState(this.queue);
         addPropertyFlags(this.queue, "Logging", 16);
      }

   }

   public int getDestinationTypeIndicator() {
      return this.isTemporary() ? 4 : 1;
   }

   public synchronized void setDestinationKeysList(List destinationKeysList) {
      super.setDestinationKeysList(destinationKeysList);
      if (this.queue != null) {
         this.queue.setComparator(this.comparator);
      }

   }

   private Expression createFilterExpression(String selector) throws JMSException {
      if (selector != null) {
         try {
            return this.sqlFilter.createExpression(new JMSSQLExpression(selector));
         } catch (InvalidExpressionException var3) {
            throw new InvalidSelectorException(var3.toString());
         } catch (KernelException var4) {
            throw new weblogic.jms.common.JMSException(var4);
         }
      } else {
         return null;
      }
   }

   protected BEConsumerImpl createConsumer(BESessionImpl session, boolean started, BEConsumerCreateRequest createRequest) throws JMSException {
      if (createRequest.getName() != null) {
         throw new weblogic.jms.common.JMSException("Durable or Topic shared consumers are not supported on queues: unexpected " + createRequest.getName());
      } else {
         this.checkShutdownOrSuspendedNeedLock("create consumer");
         BEConsumerImpl consumer = new BEConsumerImpl(session, this, this.queue, this.createFilterExpression(createRequest.getSelector()), 0, false, createRequest);
         this.addConsumer(consumer);
         if (started) {
            consumer.start();
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Created a new consumer with ID " + createRequest.getConsumerId() + " on queue " + this.name);
         }

         return consumer;
      }
   }

   protected BEConnectionConsumerImpl createConnectionConsumer(JMSID id, ServerSessionPool ssp, String clientId, String name, String selector, boolean noLocal, int messagesMaximum, long redeliveryDelay, boolean isDurable, boolean started) throws JMSException {
      if (isDurable) {
         throw new weblogic.jms.common.JMSException("Durable consumers are not supported on queues");
      } else {
         this.checkShutdownOrSuspendedNeedLock("create connection consumer");
         BEConnectionConsumerImpl consumer = new BEConnectionConsumerImpl(id, this, ssp, this.queue, this.createFilterExpression(selector), selector, messagesMaximum, redeliveryDelay, 0);
         this.addConsumer(consumer);
         if (started) {
            consumer.start();
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Created a new ConnectionConsumer with ID " + id + " on queue " + this.name);
         }

         return consumer;
      }
   }

   public BEBrowser createBrowser(BESession session, String selector) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("create browser");
      BEBrowserImpl browser = new BEBrowserImpl(session, this, this.queue, selector);
      synchronized(this) {
         this.browsers.put(browser.getJMSID(), browser);
         return browser;
      }
   }

   synchronized void removeBrowser(JMSID browserId) {
      this.browsers.remove(browserId);
   }

   protected void closeAllBrowsers(String reason) {
      ArrayList browsersCopy;
      synchronized(this) {
         browsersCopy = new ArrayList(this.browsers.values());
         this.browsers.clear();
      }

      Iterator i = browsersCopy.iterator();

      while(i.hasNext()) {
         try {
            BEBrowserImpl browser = (BEBrowserImpl)i.next();
            BESession session = browser.getSession();
            browser.close();
            if (session != null) {
               BEConnection connection = session.getConnection();
               if (connection != null) {
                  JMSServerUtilities.anonDispatchNoReply(new JMSPushExceptionRequest(11, browser.getJMSID(), new ConsumerClosedException((MessageConsumer)null, reason)), connection.getDispatcher());
               }
            }
         } catch (JMSException var7) {
         }
      }

   }

   public Queue getKernelQueue() {
      return this.queue;
   }

   public final void setMessageLoggingEnabled(boolean value) {
      if ((!super.isMessageLoggingEnabled() || !value) && (super.isMessageLoggingEnabled() || value)) {
         super.setMessageLoggingEnabled(value);

         try {
            if (super.isMessageLoggingEnabled() && !this.backEnd.isMemoryLow()) {
               this.resumeMessageLogging();
            } else if (!value) {
               this.suspendMessageLogging();
            }
         } catch (JMSException var3) {
         }

      }
   }

   public JMSMessageLogger getJMSMessageLogger() {
      return this.backEnd.getJMSMessageLogger();
   }

   public void onEvent(Event event) {
      if (event instanceof MessageSendEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageAddEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageReceiveEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageExpirationEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageRedeliveryLimitEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof MessageRemoveEvent) {
         this.onMessageEvent((MessageEvent)event);
      } else if (event instanceof UnitOfWorkEvent) {
         if (this.getExtension() != null) {
            UnitOfWorkEvent uowEvent = (UnitOfWorkEvent)event;
            if (uowEvent.isAdd()) {
               this.getExtension().unitOfWorkAddEvent(uowEvent.getUnitOfWork());
            } else {
               this.getExtension().unitOfWorkRemoveEvent(uowEvent.getUnitOfWork());
            }
         }
      } else if (event instanceof GroupRemoveEvent) {
         if (this.getExtension() != null) {
            this.getExtension().groupRemoveEvent(((GroupRemoveEvent)event).getGroup().getName());
         }
      } else if (event instanceof GroupAddEvent && this.getExtension() != null) {
         this.getExtension().groupAddEvent(((GroupAddEvent)event).getGroup().getName());
      }

   }

   private final void onMessageEvent(MessageEvent event) {
      JMSMessageLogHelper.logMessageEvent(this, event);
   }

   public void resumeMessageLogging() throws JMSException {
      if (this.destination != null) {
         addPropertyFlags(this.destination, "Logging", 15);
      }
   }

   public void suspendMessageLogging() throws JMSException {
      if (this.destination != null) {
         removePropertyFlags(this.destination, "Logging", 15);
      }
   }

   public boolean isMessageLogging() {
      if (this.destination == null) {
         return false;
      } else {
         synchronized(this.destination) {
            Integer kernelLoggingFlags = (Integer)this.destination.getProperty("Logging");
            if (kernelLoggingFlags == null) {
               return false;
            } else {
               int flags = kernelLoggingFlags;
               return (flags & 15) == 15;
            }
         }
      }
   }

   public void setQuota(Quota paramQuota) throws BeanUpdateFailedException {
      HashMap properties = new HashMap();

      try {
         properties.put("Quota", paramQuota);
         this.destination.setProperties(properties);
      } catch (KernelException var4) {
         throw new BeanUpdateFailedException("Messaging Kernel failed to act on the quota", var4);
      }
   }

   public boolean isNewlyCreated() {
      return this.isNewlyCreated;
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Queue");
      super.dump(imageSource, xsw);
      xsw.writeEndElement();
   }
}
