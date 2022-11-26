package weblogic.messaging.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.cache.lld.BaseCacheEntry;
import weblogic.cache.lld.ChangeListener;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.messaging.path.helper.KeyString;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.path.internal.PathObjectHandler;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.PersistentMapAsyncTX;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.xa.PersistentStoreXA;

public class PathServiceMap extends AsyncMapImpl implements AsyncMapWithId {
   static final String MAP_CONNECTION_PREFIX = "weblogic.messaging.PathService.";
   HashMap createdMaps = new HashMap();
   private final PersistentStoreXA store;
   private ChangeListener invalidator;

   PathServiceMap(String jndiName, PathHelper pathHelper, PersistentStoreXA store) {
      super(jndiName, pathHelper, (AsyncMap)null);
      this.store = store;

      assert store != null;

      this.registerInvalidator();
      if (PathHelper.retired || PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("PathService store.getName: " + this.store.getName() + ", store: " + store + ", jndiName " + this.getJndiName());
      }

   }

   private void registerInvalidator() {
      this.pathHelper.register(true, this.getJndiName(), this);
      this.invalidator = this.pathHelper.getDirtyCacheUpdaterMap(this.getJndiName());
   }

   PersistentMapAsyncTX mapByKey(Key key) throws PersistentStoreException {
      String mapName = mapNameFromKey(key);
      synchronized(this.createdMaps) {
         PersistentMapAsyncTX map = (PersistentMapAsyncTX)this.createdMaps.get(mapName);
         if (map != null) {
            return map;
         } else {
            map = this.store.createPersistentMap(mapName, PathObjectHandler.getObjectHandler(key.getSubsystem()));
            Object old = this.createdMaps.put(mapName, map);

            assert old == null;

            return map;
         }
      }
   }

   private static String mapNameFromKey(Key key) {
      return "weblogic.messaging.PathService." + key.getAssemblyId() + (String)Key.RESERVED_SUBSYSTEMS.get(key.getSubsystem());
   }

   static KeyString sampleKeyFromMapName(String mapName) {
      if (mapName.startsWith("weblogic.messaging.PathService.")) {
         byte i = 0;

         for(Iterator var2 = Key.RESERVED_SUBSYSTEMS.iterator(); var2.hasNext(); ++i) {
            String subsystemName = (String)var2.next();
            if (mapName.endsWith(subsystemName)) {
               String assemblyId = mapName.substring("weblogic.messaging.PathService.".length(), mapName.length() - subsystemName.length());
               return new KeyString(i, assemblyId, assemblyId);
            }
         }
      }

      return null;
   }

   public void putIfAbsent(Object inputKey, final Object newValue, final CompletionRequest callerCompReq) {
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("PathService putIfAbsent key: " + inputKey + ", value " + newValue);
      }

      final Key key = (Key)inputKey;

      final PersistentMapAsyncTX mapToUse;
      try {
         mapToUse = this.mapByKey(key);
      } catch (PersistentStoreException var7) {
         callerCompReq.setResult(this.exceptionAdapter.wrapException(var7));
         return;
      }

      mapToUse.putIfAbsent(key.getKeyId(), newValue, new CompReqListener() {
         public final void onException(CompletionRequest ignored, Throwable reason) {
            if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
               PathHelper.PathSvcVerbose.debug("PathService putIfAbsent key: " + key + ", value " + newValue, reason);
            }

            PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
         }

         public final void onCompletion(CompletionRequest ignored, final Object putIfAbsentValue) {
            if (putIfAbsentValue != null && !putIfAbsentValue.equals(newValue)) {
               if (!(putIfAbsentValue instanceof LegalMember)) {
                  PathServiceMap.wrappedSetResult(callerCompReq, putIfAbsentValue, PathServiceMap.this.exceptionAdapter);
               } else {
                  ((LegalMember)putIfAbsentValue).isLegal(key, (LegalMember)putIfAbsentValue, new CompReqListener() {
                     public final void onException(CompletionRequest ignored, Throwable reason) {
                        PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
                     }

                     public final void onCompletion(CompletionRequest ignored, Object isLegalResult) {
                        if (isLegalResult == Boolean.TRUE) {
                           PathServiceMap.wrappedSetResult(callerCompReq, putIfAbsentValue, PathServiceMap.this.exceptionAdapter);
                        } else {
                           mapToUse.remove(key.getKeyId(), putIfAbsentValue, new CompReqListener() {
                              public final void onException(CompletionRequest ignored, Throwable reason) {
                                 PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
                              }

                              public final void onCompletion(CompletionRequest ignored, Object currentValue) {
                                 boolean setResult;
                                 try {
                                    mapToUse.putIfAbsent(key.getKeyId(), newValue, new CompReqListener() {
                                       public final void onException(CompletionRequest ignored, Throwable reason) {
                                          try {
                                             PathServiceMap.this.broadcastRemove(key);
                                          } finally {
                                             PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
                                          }

                                       }

                                       public final void onCompletion(CompletionRequest ignored, Object resultValue) {
                                          try {
                                             if (resultValue != null && !putIfAbsentValue.equals(resultValue)) {
                                                PathServiceMap.this.broadcastRemove(key);
                                             }
                                          } finally {
                                             if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                                                PathHelper.PathSvcVerbose.debug("PathService putIfAbsent key: " + key + ", value " + newValue + ", result " + resultValue);
                                             }

                                             PathServiceMap.wrappedSetResult(callerCompReq, resultValue, PathServiceMap.this.exceptionAdapter);
                                          }

                                       }
                                    });
                                 } catch (RuntimeException var10) {
                                    synchronized(callerCompReq) {
                                       setResult = !callerCompReq.hasResult();
                                    }

                                    if (setResult) {
                                       callerCompReq.setResult(PathServiceMap.this.exceptionAdapter.wrapException(var10));
                                    }

                                    throw var10;
                                 } catch (Error var11) {
                                    synchronized(callerCompReq) {
                                       setResult = !callerCompReq.hasResult();
                                    }

                                    if (setResult) {
                                       callerCompReq.setResult(PathServiceMap.this.exceptionAdapter.wrapException(var11));
                                    }

                                    throw var11;
                                 }
                              }
                           });
                        }
                     }
                  });
               }
            } else {
               PathServiceMap.wrappedSetResult(callerCompReq, putIfAbsentValue, PathServiceMap.this.exceptionAdapter);
            }
         }
      });
   }

   public void put(Object inputKey, Object value, final CompletionRequest callerCompReq) {
      boolean setResult;
      try {
         final Key key = (Key)inputKey;
         final UpdatableMember newMember = (UpdatableMember)value;

         final PersistentMapAsyncTX map;
         try {
            map = this.mapByKey(key);
         } catch (PersistentStoreException var12) {
            callerCompReq.setResult(this.exceptionAdapter.wrapException(var12));
            return;
         }

         final PersistentStoreTransaction ptx = map.begin();
         map.putIfAbsent(key.getKeyId(), newMember, ptx, new CompReqListener() {
            public final void onException(CompletionRequest ignored, Throwable reason) {
               try {
                  ptx.rollback();
               } catch (PersistentStoreException var4) {
                  PathServiceMap.wrappedSetResult(callerCompReq, var4, PathServiceMap.this.exceptionAdapter);
                  return;
               }

               PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
            }

            public final void onCompletion(CompletionRequest ignored, Object result) {
               final UpdatableMember prevMember;
               boolean matched;
               try {
                  prevMember = (UpdatableMember)result;
                  matched = prevMember == null || prevMember.equals(newMember);
               } catch (RuntimeException var6) {
                  PathServiceMap.rollbackSameThreadSetResult(ptx, callerCompReq, var6, PathServiceMap.this.exceptionAdapter);
                  throw var6;
               } catch (Error var7) {
                  PathServiceMap.rollbackSameThreadSetResult(ptx, callerCompReq, var7, PathServiceMap.this.exceptionAdapter);
                  throw var7;
               }

               if (matched) {
                  ptx.commit(callerCompReq);
               } else {
                  map.put(key.getKeyId(), newMember, ptx, new CompReqListener() {
                     public final void onException(CompletionRequest completionRequest, Throwable reason) {
                        try {
                           ptx.rollback();
                        } catch (PersistentStoreException var4) {
                           PathServiceMap.wrappedSetResult(callerCompReq, var4, PathServiceMap.this.exceptionAdapter);
                           return;
                        }

                        PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
                     }

                     public final void onCompletion(CompletionRequest completionRequest, Object result) {
                        prevMember.update(key, newMember, new CompReqListener() {
                           public final void onException(CompletionRequest completionRequest, Throwable reason) {
                              try {
                                 newMember.updateException(reason, key, prevMember, ptx, callerCompReq);
                              } catch (RuntimeException var4) {
                                 PathServiceMap.rollbackSameThreadSetResult(ptx, callerCompReq, var4, PathServiceMap.this.exceptionAdapter);
                                 throw var4;
                              } catch (Error var5) {
                                 PathServiceMap.rollbackSameThreadSetResult(ptx, callerCompReq, var5, PathServiceMap.this.exceptionAdapter);
                                 throw var5;
                              }
                           }

                           public final void onCompletion(CompletionRequest completionRequest, Object result) {
                              ptx.commit(new CompReqListener() {
                                 public void onException(CompletionRequest cr, Throwable t) {
                                    PathServiceMap.wrappedSetResult(callerCompReq, t, PathServiceMap.this.exceptionAdapter);
                                 }

                                 public void onCompletion(CompletionRequest ignored, Object result) {
                                    if (result == null) {
                                       result = Boolean.TRUE;
                                    }

                                    try {
                                       PathServiceMap.this.broadcastRemove(key);
                                    } finally {
                                       PathServiceMap.wrappedSetResult(callerCompReq, result, PathServiceMap.this.exceptionAdapter);
                                    }

                                 }
                              });
                           }
                        });
                     }
                  });
               }
            }
         });
      } catch (RuntimeException var13) {
         synchronized(callerCompReq) {
            setResult = !callerCompReq.hasResult();
         }

         if (setResult) {
            callerCompReq.setResult(this.exceptionAdapter.wrapException(var13));
         }

         throw var13;
      } catch (Error var14) {
         synchronized(callerCompReq) {
            setResult = !callerCompReq.hasResult();
         }

         if (setResult) {
            callerCompReq.setResult(this.exceptionAdapter.wrapException(var14));
         }

         throw var14;
      }
   }

   private static void wrappedSetResult(CompletionRequest userCompletionRequest, Object value, ExceptionAdapter exceptionAdapter) {
      synchronized(userCompletionRequest) {
         if (userCompletionRequest.hasResult()) {
            return;
         }
      }

      if (value instanceof Throwable) {
         value = exceptionAdapter.wrapException((Throwable)value);
      }

      userCompletionRequest.setResult(value);
   }

   private static void rollbackSameThreadSetResult(PersistentStoreTransaction ptx, CompletionRequest callerCompReq, Object value, ExceptionAdapter exceptionAdapter) {
      try {
         ptx.rollback();
      } catch (PersistentStoreException var8) {
         value = var8;
      } finally {
         wrappedSetResult(callerCompReq, value, exceptionAdapter);
      }

   }

   public void get(Object inputKey, CompletionRequest callerCompReq) {
      boolean setResult;
      try {
         Key key = (Key)inputKey;
         this.mapByKey(key).get(key.getKeyId(), callerCompReq);
      } catch (PersistentStoreException var11) {
         synchronized(callerCompReq) {
            if (callerCompReq.hasResult()) {
               return;
            }
         }

         callerCompReq.setResult(this.exceptionAdapter.wrapException(var11));
      } catch (RuntimeException var12) {
         synchronized(callerCompReq) {
            setResult = !callerCompReq.hasResult();
         }

         if (setResult) {
            callerCompReq.setResult(this.exceptionAdapter.wrapException(var12));
         }

         throw var12;
      } catch (Error var13) {
         synchronized(callerCompReq) {
            setResult = !callerCompReq.hasResult();
         }

         if (setResult) {
            callerCompReq.setResult(this.exceptionAdapter.wrapException(var13));
         }

         throw var13;
      }

   }

   public void remove(Object inputKey, final Object value, final CompletionRequest callerCompReq) {
      try {
         if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
            PathHelper.PathSvcVerbose.debug("PathService remove key: " + inputKey + ", value " + value);
         }

         final Key key = (Key)inputKey;
         this.mapByKey(key).remove(key.getKeyId(), value, new CompReqListener() {
            public final void onException(CompletionRequest ignored, Throwable reason) {
               try {
                  PathServiceMap.this.broadcastRemove(key);
               } finally {
                  PathServiceMap.wrappedSetResult(callerCompReq, reason, PathServiceMap.this.exceptionAdapter);
               }

            }

            public final void onCompletion(CompletionRequest ignored, Object resultValue) {
               try {
                  if (resultValue == Boolean.TRUE) {
                     PathServiceMap.this.broadcastRemove(key);
                  }
               } finally {
                  if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                     PathHelper.PathSvcVerbose.debug("PathService remove key: " + key + ", value " + value + ", result " + resultValue);
                  }

                  PathServiceMap.wrappedSetResult(callerCompReq, resultValue, PathServiceMap.this.exceptionAdapter);
               }

            }
         });
      } catch (PersistentStoreException var8) {
         synchronized(callerCompReq) {
            if (callerCompReq.hasResult()) {
               return;
            }
         }

         callerCompReq.setResult(this.exceptionAdapter.wrapException(var8));
      }

   }

   private void broadcastRemove(Key key) {
      try {
         this.pathHelper.findOrCreateServerInfo(key).cachedRemove(key, (Member)null, 520);
      } catch (NamingException var7) {
         PathHelper.PathSvcVerbose.debug(var7.getMessage(), var7);
      } catch (PathHelper.PathServiceException var8) {
         PathHelper.PathSvcVerbose.debug(var8.getMessage(), var8);
      } finally {
         this.invalidator.onDelete(new BaseCacheEntry(key, (Object)null));
      }

   }

   public PersistentStoreXA getStore() {
      return this.store;
   }

   public void dump(PathServiceDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException, PersistentStoreException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Map");
      xsw.writeAttribute("jndiName", this.getJndiName());
      xsw.writeAttribute("storeName", this.store.getName());
      xsw.writeStartElement("Assemblies");
      Iterator it = this.store.getMapConnectionNames();
      ArrayList connectionNames = new ArrayList();

      KeyString keyString;
      while(it.hasNext()) {
         keyString = sampleKeyFromMapName((String)it.next());
         if (keyString != null) {
            connectionNames.add(keyString);
         }
      }

      xsw.writeAttribute("count", String.valueOf(connectionNames.size()));
      it = connectionNames.iterator();

      while(it.hasNext()) {
         imageSource.checkTimeout();
         keyString = (KeyString)it.next();
         xsw.writeStartElement("Assembly");
         xsw.writeAttribute("name", keyString.getAssemblyId() + keyString.getSubsystem());
         PersistentMapAsyncTX assemblyMap = this.mapByKey(keyString);
         Set keyValues = assemblyMap.keySet();
         xsw.writeStartElement("Keys");
         if (keyValues != null && keyValues.size() != 0) {
            xsw.writeAttribute("count", String.valueOf(keyValues.size()));
            Iterator iterator = keyValues.iterator();

            while(iterator.hasNext()) {
               xsw.writeStartElement("Key");
               Object o = iterator.next();
               xsw.writeCharacters(o.toString());
               xsw.writeEndElement();
            }
         } else {
            xsw.writeAttribute("count", "0");
         }

         xsw.writeEndElement();
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   class SubsystemInfo {
      String name;
      int index;

      SubsystemInfo(String n, int i) {
         this.name = n;
         this.index = i;
      }
   }

   private abstract static class CompReqListener extends CompletionRequest implements CompletionListener {
      CompReqListener() {
         this.addListener(this);
      }
   }
}
