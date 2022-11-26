package com.oracle.tyrus.cluster.coherence;

import com.oracle.tyrus.cluster.common.AbstractClusterContext;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.MemberEvent;
import com.tangosol.net.MemberListener;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.SendHandler;
import org.glassfish.tyrus.core.TyrusFuture;
import org.glassfish.tyrus.core.cluster.BroadcastListener;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.cluster.SessionEventListener;
import org.glassfish.tyrus.core.cluster.SessionListener;
import org.glassfish.tyrus.core.cluster.RemoteSession.DistributedMapKey;

public class CoherenceClusterContext extends AbstractClusterContext {
   private static final Logger LOGGER = Logger.getLogger(CoherenceClusterContext.class.getName());
   private static final int DEFAULT_HA_TIMEOUT = 30;
   private final Set localIds = new ConcurrentSkipListSet();
   private final Set endpointPaths = new ConcurrentSkipListSet();
   private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
   private final Map distributedUserPropertiesDestroyTasks = new ConcurrentHashMap();
   private final int clusterHaTimeout;
   private final Executor executor;

   public CoherenceClusterContext(Executor executor, Integer clusterHaTimeout) {
      boolean running = CacheFactory.getCluster().isRunning();
      if (!running) {
         throw new IllegalStateException("Coherence cluster is not running.");
      } else {
         if (clusterHaTimeout != null) {
            this.clusterHaTimeout = clusterHaTimeout;
         } else {
            this.clusterHaTimeout = 30;
         }

         if (executor != null) {
            this.executor = executor;
         } else {
            this.executor = new Executor() {
               public void execute(Runnable command) {
                  command.run();
               }
            };
         }

         CacheFactory.getCache(this.getSessionTopicName((String)null)).getCacheService().addMemberListener(new MemberListener() {
            public void memberJoined(MemberEvent memberEvent) {
            }

            public void memberLeaving(MemberEvent memberEvent) {
            }

            public void memberLeft(final MemberEvent memberEvent) {
               CoherenceClusterContext.this.executor.execute(new Runnable() {
                  public void run() {
                     int nodeLeftId = memberEvent.getMember().getId();
                     Set sessionIdsToClear = new HashSet();
                     List caches = new ArrayList();
                     Iterator i$ = CoherenceClusterContext.this.endpointPaths.iterator();

                     String sessionIdToClear;
                     while(i$.hasNext()) {
                        sessionIdToClear = (String)i$.next();
                        caches.add(CacheFactory.getCache(CoherenceClusterContext.this.getSessionSetName(sessionIdToClear)));
                     }

                     i$ = caches.iterator();

                     while(i$.hasNext()) {
                        NamedCache cache = (NamedCache)i$.next();
                        Iterator it = cache.entrySet().iterator();

                        while(it.hasNext()) {
                           Map.Entry entry = (Map.Entry)it.next();
                           if (entry.getValue().equals(nodeLeftId)) {
                              sessionIdsToClear.add(entry.getKey().toString());
                              CoherenceClusterContext.LOGGER.log(Level.FINEST, "Cluster node went down - removing session: " + entry.getKey() + " originating from node: " + entry.getValue());
                              it.remove();
                           }
                        }
                     }

                     i$ = sessionIdsToClear.iterator();

                     while(i$.hasNext()) {
                        sessionIdToClear = (String)i$.next();
                        CoherenceClusterContext.this.clearDistributedSessionData(sessionIdToClear);
                     }

                  }
               });
            }
         });
         CacheFactory.getCache(this.getDistributedPropertiesRemoveName()).addMapListener(new MapListener() {
            public void entryInserted(final MapEvent mapEvent) {
               CoherenceClusterContext.this.executor.execute(new Runnable() {
                  public void run() {
                     final String connectionId = mapEvent.getKey().toString();
                     if (!CoherenceClusterContext.this.distributedUserPropertiesDestroyTasks.containsKey(connectionId)) {
                        ScheduledFuture destroyTask = CoherenceClusterContext.this.scheduledExecutorService.schedule(new Runnable() {
                           public void run() {
                              CoherenceClusterContext.this.executor.execute(new Runnable() {
                                 public void run() {
                                    CoherenceClusterContext.LOGGER.log(Level.FINEST, "UserProperties - connectionID: " + connectionId + " destroyed.");
                                    CoherenceClusterContext.this.distributedUserPropertiesDestroyTasks.remove(connectionId);
                                    CacheFactory.destroyCache(CacheFactory.getCache(CoherenceClusterContext.this.getSessionUserPropertiesName(connectionId)));
                                    CacheFactory.getCache(CoherenceClusterContext.this.getDistributedPropertiesRemoveName()).remove(connectionId);
                                 }
                              });
                           }
                        }, (long)CoherenceClusterContext.this.clusterHaTimeout, TimeUnit.SECONDS);
                        CoherenceClusterContext.this.distributedUserPropertiesDestroyTasks.put(connectionId, destroyTask);
                     }
                  }
               });
            }

            public void entryUpdated(MapEvent mapEvent) {
            }

            public void entryDeleted(MapEvent mapEvent) {
            }
         });
      }
   }

   public Set getRemoteSessionIds(String endpointPath) {
      Set distributedSessionIds = CacheFactory.getCache(this.getSessionSetName(endpointPath)).keySet();
      Set remoteSessionIds = new HashSet();
      Iterator i$ = distributedSessionIds.iterator();

      while(i$.hasNext()) {
         Object sessionId = i$.next();
         if (!this.localIds.contains(sessionId.toString())) {
            remoteSessionIds.add(sessionId.toString());
         }
      }

      return remoteSessionIds;
   }

   public String createSessionId() {
      return UUID.randomUUID().toString();
   }

   public String createConnectionId() {
      return UUID.randomUUID().toString();
   }

   protected String createMessageId() {
      return UUID.randomUUID().toString();
   }

   public void registerSession(final String sessionId, String endpointPath, final SessionEventListener listener) {
      NamedCache sessionCache = CacheFactory.getCache(this.getSessionTopicName((String)null));
      final NamedCache messageCache = CacheFactory.getCache(this.getMessageTopicName((String)null));
      sessionCache.addMapListener(new MapListener() {
         public void entryInserted(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryUpdated(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryDeleted(MapEvent mapEvent) {
         }

         private void processMapEvent(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  AbstractClusterContext.MessageBase messageBase = (AbstractClusterContext.MessageBase)mapEvent.getNewValue();
                  String messageId = messageBase.getId();
                  CoherenceClusterContext.LOGGER.log(Level.FINEST, "Message " + sessionId + " : " + messageId + " received.");

                  try {
                     messageBase.process(listener);
                     messageCache.put(messageId, new AbstractClusterContext.Ack(messageId));
                  } catch (Throwable var4) {
                     messageCache.put(messageId, new AbstractClusterContext.Nack(messageId, var4));
                  }

               }
            });
         }
      }, sessionId, false);
      this.localIds.add(sessionId);
      CacheFactory.getCache(this.getSessionSetName(endpointPath)).put(sessionId, CacheFactory.getCluster().getLocalMember().getId());
      LOGGER.log(Level.FINEST, "Session " + sessionId + " put into cache.");
      this.checkHaTimeoutTask(sessionId);
   }

   public void registerSessionListener(String endpointPath, final SessionListener listener) {
      this.endpointPaths.add(endpointPath);
      CacheFactory.getCache(this.getSessionSetName(endpointPath)).addMapListener(new MapListener() {
         public void entryInserted(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  String sessionId = mapEvent.getKey().toString();
                  if (!CoherenceClusterContext.this.localIds.contains(sessionId)) {
                     listener.onSessionOpened(sessionId);
                     CoherenceClusterContext.LOGGER.log(Level.FINEST, "Session " + sessionId + " was created on remote node.");
                     CoherenceClusterContext.this.checkHaTimeoutTask(sessionId);
                  }

               }
            });
         }

         public void entryUpdated(MapEvent mapEvent) {
         }

         public void entryDeleted(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  String sessionId = mapEvent.getKey().toString();
                  if (!CoherenceClusterContext.this.localIds.contains(sessionId)) {
                     listener.onSessionClosed(sessionId);
                     CoherenceClusterContext.LOGGER.log(Level.FINEST, "Session " + sessionId + " was closed on remote node.");
                  }

               }
            });
         }
      });
   }

   private void checkHaTimeoutTask(String sessionId) {
      String connectionId = this.getDistributedSessionProperties(sessionId).get(DistributedMapKey.CONNECTION_ID).toString();
      ScheduledFuture scheduledFuture = (ScheduledFuture)this.distributedUserPropertiesDestroyTasks.get(connectionId);
      if (scheduledFuture != null) {
         LOGGER.log(Level.FINEST, "UserProperties - connectionID: " + connectionId + " destroy cancelled.");
         scheduledFuture.cancel(false);
      }

   }

   public void registerBroadcastListener(String endpointPath, final BroadcastListener listener) {
      NamedCache broadcastCache = CacheFactory.getCache(this.getBroadcastChannelName((String)null));
      broadcastCache.addMapListener(new MapListener() {
         public void entryInserted(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryUpdated(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryDeleted(MapEvent mapEvent) {
         }

         private void processMapEvent(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  AbstractClusterContext.BroadcastableMessage message = (AbstractClusterContext.BroadcastableMessage)mapEvent.getNewValue();
                  message.processBroadcast(listener);
               }
            });
         }
      }, endpointPath, false);
   }

   public Map getDistributedSessionProperties(String sessionId) {
      final NamedCache cache = CacheFactory.getCache(this.getSessionTopicName(sessionId));
      return new AbstractMap() {
         public Set entrySet() {
            final Set set = cache.entrySet();
            return new AbstractSet() {
               public Iterator iterator() {
                  final Iterator iterator = set.iterator();
                  return new Iterator() {
                     public boolean hasNext() {
                        return iterator.hasNext();
                     }

                     public Map.Entry next() {
                        final Map.Entry next = (Map.Entry)iterator.next();
                        return new Map.Entry() {
                           public RemoteSession.DistributedMapKey getKey() {
                              return (RemoteSession.DistributedMapKey)next.getKey();
                           }

                           public Object getValue() {
                              return next.getValue();
                           }

                           public Object setValue(Object value) {
                              return next.setValue(value);
                           }
                        };
                     }

                     public void remove() {
                        iterator.remove();
                     }
                  };
               }

               public int size() {
                  return set.size();
               }
            };
         }

         public Object put(RemoteSession.DistributedMapKey key, Object value) {
            return cache.put(key, value);
         }
      };
   }

   public Map getDistributedUserProperties(String connectionId) {
      final NamedCache cache = CacheFactory.getCache(this.getSessionUserPropertiesName(connectionId));
      return new AbstractMap() {
         public Set entrySet() {
            final Set set = cache.entrySet();
            return new AbstractSet() {
               public Iterator iterator() {
                  final Iterator iterator = set.iterator();
                  return new Iterator() {
                     public boolean hasNext() {
                        return iterator.hasNext();
                     }

                     public Map.Entry next() {
                        final Map.Entry next = (Map.Entry)iterator.next();
                        return new Map.Entry() {
                           public String getKey() {
                              return (String)next.getKey();
                           }

                           public Object getValue() {
                              return next.getValue();
                           }

                           public Object setValue(Object value) {
                              return next.setValue(value);
                           }
                        };
                     }

                     public void remove() {
                        iterator.remove();
                     }
                  };
               }

               public int size() {
                  return set.size();
               }
            };
         }

         public Object put(String key, Object value) {
            return cache.put(key, value);
         }
      };
   }

   public void destroyDistributedUserProperties(String connectionId) {
      try {
         CacheFactory.destroyCache(CacheFactory.getCache(this.getSessionUserPropertiesName(connectionId)));
      } catch (Exception var3) {
         LOGGER.log(Level.FINEST, var3.getMessage(), var3);
      }

   }

   public void removeSession(String sessionId, String endpointPath) {
      try {
         CacheFactory.getCache(this.getSessionSetName(endpointPath)).remove(sessionId);
         this.localIds.remove(sessionId);
         this.clearDistributedSessionData(sessionId);
         LOGGER.log(Level.FINEST, "Session (local) " + sessionId + " removed.");
      } catch (Exception var4) {
         LOGGER.log(Level.FINEST, var4.getMessage(), var4);
      }

   }

   private void clearDistributedSessionData(String sessionId) {
      final String connectionId = CacheFactory.getCache(this.getSessionTopicName(sessionId)).get(DistributedMapKey.CONNECTION_ID).toString();
      CacheFactory.destroyCache(CacheFactory.getCache(this.getSessionTopicName(sessionId)));
      if (connectionId != null) {
         ScheduledFuture destroyTask = this.scheduledExecutorService.schedule(new Runnable() {
            public void run() {
               CoherenceClusterContext.this.executor.execute(new Runnable() {
                  public void run() {
                     CoherenceClusterContext.LOGGER.log(Level.FINEST, "UserProperties - connectionID: " + connectionId + " destroyed.");
                     CoherenceClusterContext.this.distributedUserPropertiesDestroyTasks.remove(connectionId);
                     CacheFactory.destroyCache(CacheFactory.getCache(CoherenceClusterContext.this.getSessionUserPropertiesName(connectionId)));
                  }
               });
            }
         }, (long)this.clusterHaTimeout, TimeUnit.SECONDS);
         this.distributedUserPropertiesDestroyTasks.put(connectionId, destroyTask);
         CacheFactory.getCache(this.getDistributedPropertiesRemoveName()).put(connectionId, connectionId);
      }

   }

   public void shutdown() {
      this.scheduledExecutorService.shutdown();
   }

   protected Future sendMessage(final String sessionId, AbstractClusterContext.MessageBase message) {
      final String messageId = message.getId();
      final TyrusFuture voidTyrusFuture = new TyrusFuture();
      final NamedCache messageCache = CacheFactory.getCache(this.getMessageTopicName((String)null));
      MapListener mapListener = new MapListener() {
         public void entryInserted(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryUpdated(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryDeleted(MapEvent mapEvent) {
         }

         private void processMapEvent(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  AbstractClusterContext.ControlMessageBase controlMessage = (AbstractClusterContext.ControlMessageBase)mapEvent.getNewValue();

                  assert controlMessage.getId().equals(messageId);

                  controlMessage.process(voidTyrusFuture, (SendHandler)null);
                  CoherenceClusterContext.LOGGER.log(Level.FINEST, "Message " + sessionId + " : " + messageId + " processing finished.");
                  messageCache.removeMapListener(<VAR_NAMELESS_ENCLOSURE>);
               }
            });
         }
      };
      messageCache.addMapListener(mapListener, messageId, false);
      NamedCache sessionCache = CacheFactory.getCache(this.getSessionTopicName((String)null));
      sessionCache.put(sessionId, message);
      LOGGER.log(Level.FINEST, "Message " + sessionId + " : " + messageId + " put into cache.");
      return voidTyrusFuture;
   }

   protected void sendMessage(String sessionId, AbstractClusterContext.MessageBase message, final SendHandler sendHandler) {
      final String messageId = message.getId();
      final NamedCache messageCache = CacheFactory.getCache(this.getMessageTopicName((String)null));
      MapListener mapListener = new MapListener() {
         public void entryInserted(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryUpdated(MapEvent mapEvent) {
            this.processMapEvent(mapEvent);
         }

         public void entryDeleted(MapEvent mapEvent) {
         }

         private void processMapEvent(final MapEvent mapEvent) {
            CoherenceClusterContext.this.executor.execute(new Runnable() {
               public void run() {
                  AbstractClusterContext.ControlMessageBase controlMessage = (AbstractClusterContext.ControlMessageBase)mapEvent.getNewValue();

                  assert controlMessage.getId().equals(messageId);

                  controlMessage.process((TyrusFuture)null, sendHandler);
                  messageCache.removeMapListener(<VAR_NAMELESS_ENCLOSURE>);
               }
            });
         }
      };
      messageCache.addMapListener(mapListener, message.getId(), false);
      NamedCache sessionCache = CacheFactory.getCache(this.getSessionTopicName((String)null));
      sessionCache.put(sessionId, message);
   }

   protected void broadcast(String endpointPath, AbstractClusterContext.BroadcastableMessage message) {
      NamedCache broadcastCache = CacheFactory.getCache(this.getBroadcastChannelName((String)null));
      broadcastCache.put(endpointPath, message);
   }

   public boolean isSessionOpen(String sessionId, String endpointPath) {
      return CacheFactory.getCache(this.getSessionSetName(endpointPath)).containsKey(sessionId);
   }

   private String getSessionTopicName(String sessionId) {
      return "tyrus-session-" + sessionId;
   }

   private String getSessionUserPropertiesName(String sessionId) {
      return "tyrus-session-user-properties-" + sessionId;
   }

   private String getMessageTopicName(String sessionId) {
      return "tyrus-message-" + sessionId;
   }

   private String getSessionSetName(String endpointPath) {
      return "tyrus-sessions-" + endpointPath;
   }

   private String getBroadcastChannelName(String endpointPath) {
      return "tyrus-broadcast-" + endpointPath;
   }

   private String getDistributedPropertiesRemoveName() {
      return "tyrus-distr-props-remove";
   }
}
