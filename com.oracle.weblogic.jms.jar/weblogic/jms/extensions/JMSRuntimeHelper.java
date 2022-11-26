package weblogic.jms.extensions;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import weblogic.jms.client.ConnectionInternal;
import weblogic.jms.client.ConsumerInternal;
import weblogic.jms.client.ProducerInternal;
import weblogic.jms.client.SessionInternal;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSEditHelper;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.PartitionUtils;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.runtime.JMSConnectionRuntimeMBean;
import weblogic.management.runtime.JMSConsumerRuntimeMBean;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.management.runtime.JMSProducerRuntimeMBean;
import weblogic.management.runtime.JMSRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.JMSSessionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public class JMSRuntimeHelper {
   public static final String PARTITION_JNDINAME = "weblogic.partitionName";

   public static JMSConnectionRuntimeMBean getJMSConnectionRuntimeMBean(Context ctx, Connection connection) throws JMSException {
      if (!(connection instanceof ConnectionInternal)) {
         throw new JMSException("Unknown foreign connection");
      } else {
         ConnectionInternal jmsConnection = (ConnectionInternal)connection;

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, jmsConnection.getPartitionName())) {
               throw new JMSException("Context and Connection belong to different partitions");
            } else {
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean serverRuntime = domainRuntime.lookupServerRuntime(jmsConnection.getWLSServerName());
               JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
               JMSConnectionRuntimeMBean[] jmsConnections = jmsRuntime.getConnections();
               JMSConnectionRuntimeMBean jmsConnectionRuntimeMBean = null;

               for(int i = 0; i < jmsConnections.length; ++i) {
                  if (jmsConnection.getRuntimeMBeanName().equals(jmsConnections[i].getName())) {
                     jmsConnectionRuntimeMBean = jmsConnections[i];
                     break;
                  }
               }

               if (jmsConnectionRuntimeMBean == null) {
                  throw new JMSException("JMS Connection runtime mbean not found");
               } else {
                  return jmsConnectionRuntimeMBean;
               }
            }
         } catch (Exception var10) {
            if (var10 instanceof JMSException) {
               throw (JMSException)var10;
            } else {
               JMSException j = new JMSException(var10.toString());
               j.setLinkedException(var10);
               throw j;
            }
         }
      }
   }

   public static JMSProducerRuntimeMBean getJMSMessageProducerRuntimeMBean(Context ctx, MessageProducer messageProducer) throws JMSException {
      if (!(messageProducer instanceof ProducerInternal)) {
         throw new JMSException("Unknown foreign message producer");
      } else {
         ProducerInternal producer = (ProducerInternal)messageProducer;

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, producer.getPartitionName())) {
               throw new JMSException("Context and MessageProducer belong to different partitions");
            } else {
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean serverRuntime = domainRuntime.lookupServerRuntime(producer.getWLSServerName());
               JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
               JMSConnectionRuntimeMBean[] jmsConnections = jmsRuntime.getConnections();
               JMSProducerRuntimeMBean jmsProducerRuntimeMBean = null;

               for(int i = 0; i < jmsConnections.length; ++i) {
                  JMSSessionRuntimeMBean[] jmsSessions = jmsConnections[i].getSessions();

                  for(int j = 0; j < jmsSessions.length; ++j) {
                     JMSProducerRuntimeMBean[] jmsProducers = jmsSessions[j].getProducers();

                     for(int k = 0; k < jmsProducers.length; ++k) {
                        if (producer.getRuntimeMBeanName().equals(jmsProducers[k].getName())) {
                           jmsProducerRuntimeMBean = jmsProducers[k];
                           break;
                        }
                     }
                  }
               }

               if (jmsProducerRuntimeMBean == null) {
                  throw new JMSException("JMS Prodcuer runtime mbean not found");
               } else {
                  return jmsProducerRuntimeMBean;
               }
            }
         } catch (Exception var14) {
            if (var14 instanceof JMSException) {
               throw (JMSException)var14;
            } else {
               JMSException j = new JMSException(var14.toString());
               j.setLinkedException(var14);
               throw j;
            }
         }
      }
   }

   public static JMSConsumerRuntimeMBean getJMSMessageConsumerRuntimeMBean(Context ctx, MessageConsumer messageConsumer) throws JMSException {
      if (!(messageConsumer instanceof ConsumerInternal)) {
         throw new JMSException("Unknown foreign message consumer");
      } else {
         ConsumerInternal jmsConsumer = (ConsumerInternal)messageConsumer;

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, jmsConsumer.getPartitionName())) {
               throw new JMSException("Context and MessageConsumer belong to different partitions");
            } else {
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean serverRuntime = domainRuntime.lookupServerRuntime(jmsConsumer.getWLSServerName());
               JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
               JMSConnectionRuntimeMBean[] jmsConnections = jmsRuntime.getConnections();
               JMSConsumerRuntimeMBean jmsConsumerRuntimeMBean = null;

               for(int i = 0; i < jmsConnections.length; ++i) {
                  JMSSessionRuntimeMBean[] jmsSessions = jmsConnections[i].getSessions();

                  for(int j = 0; j < jmsSessions.length; ++j) {
                     JMSConsumerRuntimeMBean[] jmsConsumers = jmsSessions[j].getConsumers();

                     for(int k = 0; k < jmsConsumers.length; ++k) {
                        if (jmsConsumer.getRuntimeMBeanName().equals(jmsConsumers[k].getName())) {
                           jmsConsumerRuntimeMBean = jmsConsumers[k];
                           break;
                        }
                     }
                  }
               }

               if (jmsConsumerRuntimeMBean == null) {
                  throw new JMSException("JMS Consumer runtime mbean not found");
               } else {
                  return jmsConsumerRuntimeMBean;
               }
            }
         } catch (Exception var14) {
            if (var14 instanceof JMSException) {
               throw (JMSException)var14;
            } else {
               JMSException j = new JMSException(var14.toString());
               j.setLinkedException(var14);
               throw j;
            }
         }
      }
   }

   public static JMSSessionRuntimeMBean getJMSSessionRuntimeMBean(Context ctx, Session customerSession) throws JMSException {
      if (!(customerSession instanceof SessionInternal)) {
         throw new JMSException("Unknown foreign customerSession");
      } else {
         SessionInternal session = (SessionInternal)customerSession;

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, session.getPartitionName())) {
               throw new JMSException("Context and Session belong to different partitions");
            } else {
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean serverRuntime = domainRuntime.lookupServerRuntime(session.getWLSServerName());
               JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
               JMSConnectionRuntimeMBean[] jmsConnections = jmsRuntime.getConnections();
               JMSSessionRuntimeMBean jmsSessionRuntimeMBean = null;

               for(int i = 0; i < jmsConnections.length; ++i) {
                  JMSSessionRuntimeMBean[] jmsSessions = jmsConnections[i].getSessions();

                  for(int j = 0; j < jmsSessions.length; ++j) {
                     if (session.getRuntimeMBeanName().equals(jmsSessions[j].getName())) {
                        jmsSessionRuntimeMBean = jmsSessions[j];
                        break;
                     }
                  }
               }

               if (jmsSessionRuntimeMBean == null) {
                  throw new JMSException("JMS Session runtime mbean not found");
               } else {
                  return jmsSessionRuntimeMBean;
               }
            }
         } catch (Exception var12) {
            if (var12 instanceof JMSException) {
               throw (JMSException)var12;
            } else {
               JMSException j = new JMSException(var12.toString());
               j.setLinkedException(var12);
               throw j;
            }
         }
      }
   }

   public static JMSServerRuntimeMBean getJMSServerRuntimeMBean(Context ctx, Destination destination) throws JMSException {
      if (!(destination instanceof DestinationImpl)) {
         throw new JMSException("Unknown foreign destination");
      } else {
         DestinationImpl jmsDestination = (DestinationImpl)destination;
         String jmsServerName = jmsDestination.getServerName();
         String destPartitionName = jmsDestination.getPartitionName();

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, destPartitionName)) {
               throw new JMSException("Context and Destination belong to different partitions");
            } else {
               if (!PartitionUtils.isDomain(partitionName)) {
                  jmsServerName = PartitionUtils.stripDecoratedPartitionName(partitionName, jmsServerName);
               }

               JMSServerRuntimeMBean jmsServerRuntimeMBean = null;
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean[] serverRuntimes = domainRuntime.getServerRuntimes();

               for(int lcv = 0; lcv < serverRuntimes.length; ++lcv) {
                  ServerRuntimeMBean serverRuntime = serverRuntimes[lcv];
                  JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
                  if (jmsRuntime != null) {
                     JMSServerRuntimeMBean[] jmsServers = jmsRuntime.getJMSServers();

                     for(int i = 0; i < jmsServers.length; ++i) {
                        if (jmsServerName.equals(jmsServers[i].getName())) {
                           jmsServerRuntimeMBean = jmsServers[i];
                           break;
                        }
                     }
                  }
               }

               if (jmsServerRuntimeMBean == null) {
                  throw new JMSException("JMS Server runtime mbean not found");
               } else {
                  return jmsServerRuntimeMBean;
               }
            }
         } catch (Exception var14) {
            if (var14 instanceof JMSException) {
               throw (JMSException)var14;
            } else {
               JMSException j = new JMSException(var14.toString());
               j.setLinkedException(var14);
               throw j;
            }
         }
      }
   }

   public static JMSDestinationRuntimeMBean getJMSDestinationRuntimeMBean(Context ctx, String jmsServerName, String destinationName) throws JMSException {
      try {
         String partitionName = (String)ctx.lookup("weblogic.partitionName");
         JMSDestinationRuntimeMBean jmsDestinationRuntimeMBean = null;
         DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
         ServerRuntimeMBean[] serverRuntimes = domainRuntime.getServerRuntimes();

         for(int lcv = 0; lcv < serverRuntimes.length; ++lcv) {
            ServerRuntimeMBean serverRuntime = serverRuntimes[lcv];
            JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
            if (jmsRuntime != null) {
               JMSServerRuntimeMBean[] jmsServers = jmsRuntime.getJMSServers();

               for(int i = 0; i < jmsServers.length; ++i) {
                  JMSDestinationRuntimeMBean[] jmsDestinations = jmsServers[i].getDestinations();

                  for(int j = 0; j < jmsDestinations.length; ++j) {
                     if (destinationName.equals(jmsDestinations[j].getName())) {
                        jmsDestinationRuntimeMBean = jmsDestinations[j];
                        break;
                     }
                  }
               }
            }
         }

         if (jmsDestinationRuntimeMBean == null) {
            throw new JMSException("JMS Destination runtime mbean not found");
         } else {
            return jmsDestinationRuntimeMBean;
         }
      } catch (Exception var14) {
         if (var14 instanceof JMSException) {
            throw (JMSException)var14;
         } else {
            JMSException j = new JMSException(var14.toString());
            j.setLinkedException(var14);
            throw j;
         }
      }
   }

   public static JMSDestinationRuntimeMBean getJMSDestinationRuntimeMBean(Context ctx, Destination destination) throws JMSException {
      if (!(destination instanceof DestinationImpl)) {
         throw new JMSException("Unknown foreign destination");
      } else {
         DestinationImpl jmsDestination = (DestinationImpl)destination;
         String jmsDestinationName = jmsDestination.getName();
         String destPartitionName = jmsDestination.getPartitionName();

         try {
            String partitionName = (String)ctx.lookup("weblogic.partitionName");
            if (!PartitionUtils.isSamePartition(partitionName, destPartitionName)) {
               throw new JMSException("Context and Destination belong to different partitions");
            } else {
               if (!PartitionUtils.isDomain(partitionName)) {
                  jmsDestinationName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, jmsDestinationName);
               }

               JMSDestinationRuntimeMBean jmsDestinationRuntimeMBean = null;
               DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
               ServerRuntimeMBean[] serverRuntimes = domainRuntime.getServerRuntimes();

               for(int lcv = 0; lcv < serverRuntimes.length; ++lcv) {
                  ServerRuntimeMBean serverRuntime = serverRuntimes[lcv];
                  JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
                  if (jmsRuntime != null) {
                     JMSServerRuntimeMBean[] jmsServers = jmsRuntime.getJMSServers();

                     for(int i = 0; i < jmsServers.length; ++i) {
                        JMSDestinationRuntimeMBean[] jmsDestinations = jmsServers[i].getDestinations();

                        for(int j = 0; j < jmsDestinations.length; ++j) {
                           if (jmsDestinationName.equals(jmsDestinations[j].getName())) {
                              jmsDestinationRuntimeMBean = jmsDestinations[j];
                              break;
                           }
                        }
                     }
                  }
               }

               if (jmsDestinationRuntimeMBean == null) {
                  throw new JMSException("JMS Destination runtime mbean not found");
               } else {
                  return jmsDestinationRuntimeMBean;
               }
            }
         } catch (Exception var16) {
            if (var16 instanceof JMSException) {
               throw (JMSException)var16;
            } else {
               JMSException j = new JMSException(var16.toString());
               j.setLinkedException(var16);
               throw j;
            }
         }
      }
   }

   public static JMSServerRuntimeMBean getJMSServerRuntimeMBean(Context ctx, String mbeanName) throws JMSException {
      try {
         String partitionName = (String)ctx.lookup("weblogic.partitionName");
         JMSServerRuntimeMBean jmsServerRuntimeMBean = null;
         DomainRuntimeServiceMBean domainRuntime = JMSEditHelper.getDomainRuntimeService(ctx);
         ServerRuntimeMBean[] serverRuntimes = domainRuntime.getServerRuntimes();

         for(int lcv = 0; lcv < serverRuntimes.length; ++lcv) {
            ServerRuntimeMBean serverRuntime = serverRuntimes[lcv];
            JMSRuntimeMBean jmsRuntime = getJMSRuntimeMBean(partitionName, serverRuntime);
            if (jmsRuntime != null) {
               JMSServerRuntimeMBean[] jmsServers = jmsRuntime.getJMSServers();

               for(int i = 0; i < jmsServers.length; ++i) {
                  if (mbeanName.equals(jmsServers[i].getName())) {
                     jmsServerRuntimeMBean = jmsServers[i];
                     break;
                  }
               }
            }
         }

         if (jmsServerRuntimeMBean == null) {
            throw new JMSException("JMS Server runtime mbean not found");
         } else {
            return jmsServerRuntimeMBean;
         }
      } catch (Exception var11) {
         if (var11 instanceof JMSException) {
            throw (JMSException)var11;
         } else {
            JMSException j = new JMSException(var11.toString());
            j.setLinkedException(var11);
            throw j;
         }
      }
   }

   public String oldJMSMessageIDToNew(String messageId, long timeStamp) throws JMSException {
      try {
         String prefix = messageId.substring(0, 4);
         long id = Long.parseLong(messageId.substring(4));
         int seed = (int)(id >>> 32);
         int counter = (int)(id - ((long)seed << 32));
         return prefix + "<" + seed + "." + timeStamp + "." + counter + ">";
      } catch (Throwable var9) {
         throw new JMSException("Invalid JMSMessageID, failed to convert it into 6.0 format");
      }
   }

   public String newJMSMessageIDToOld(String messageId) throws JMSException {
      try {
         String prefix = messageId.substring(0, 4);
         int index = messageId.indexOf(".", 5);
         int seed = Integer.parseInt(messageId.substring(5, index));
         int index2 = messageId.indexOf(".", index + 1);
         index = messageId.indexOf(".", index2 + 1);
         int counter = Integer.parseInt(messageId.substring(index2 + 1, index));
         long id = (long)seed;
         id = (id << 32) + (long)counter;
         return prefix + id;
      } catch (Throwable var9) {
         throw new JMSException("Invalid JMSMessageID, failed to convert it into pre-6.0 format");
      }
   }

   private static JMSRuntimeMBean getJMSRuntimeMBean(String partitionName, ServerRuntimeMBean serverRuntime) {
      if (PartitionUtils.isDomain(partitionName)) {
         return serverRuntime.getJMSRuntime();
      } else {
         return serverRuntime.lookupPartitionRuntime(partitionName) == null ? null : serverRuntime.lookupPartitionRuntime(partitionName).getJMSRuntime();
      }
   }
}
