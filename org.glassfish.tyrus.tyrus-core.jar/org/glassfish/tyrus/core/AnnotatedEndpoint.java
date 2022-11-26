package org.glassfish.tyrus.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Builder;
import org.glassfish.tyrus.core.coder.PrimitiveDecoders;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;

public class AnnotatedEndpoint extends Endpoint {
   private static final Logger LOGGER = Logger.getLogger(AnnotatedEndpoint.class.getName());
   private final Object annotatedInstance;
   private final Class annotatedClass;
   private final Method onOpenMethod;
   private final Method onCloseMethod;
   private final Method onErrorMethod;
   private final ParameterExtractor[] onOpenParameters;
   private final ParameterExtractor[] onCloseParameters;
   private final ParameterExtractor[] onErrorParameters;
   private final EndpointConfig configuration;
   private final ComponentProviderService componentProvider;
   private final EndpointEventListener endpointEventListener;
   private final Set messageHandlerFactories = new HashSet();

   public static AnnotatedEndpoint fromClass(Class annotatedClass, ComponentProviderService componentProvider, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector, EndpointEventListener endpointEventListener) {
      return new AnnotatedEndpoint(annotatedClass, (Object)null, componentProvider, isServerEndpoint, incomingBufferSize, collector, endpointEventListener);
   }

   public static AnnotatedEndpoint fromInstance(Object annotatedInstance, ComponentProviderService componentProvider, boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector) {
      return new AnnotatedEndpoint(annotatedInstance.getClass(), annotatedInstance, componentProvider, isServerEndpoint, incomingBufferSize, collector, EndpointEventListener.NO_OP);
   }

   private AnnotatedEndpoint(Class annotatedClass, Object instance, ComponentProviderService componentProvider, Boolean isServerEndpoint, int incomingBufferSize, ErrorCollector collector, EndpointEventListener endpointEventListener) {
      this.configuration = this.createEndpointConfig(annotatedClass, isServerEndpoint, collector);
      this.annotatedInstance = instance;
      this.annotatedClass = annotatedClass;
      this.endpointEventListener = endpointEventListener;
      if (isServerEndpoint) {
         if (TyrusServerEndpointConfigurator.class.equals(((ServerEndpointConfig)this.configuration).getConfigurator().getClass())) {
            this.componentProvider = componentProvider;
         } else {
            this.componentProvider = new ComponentProviderService(componentProvider) {
               public Object getEndpointInstance(Class endpointClass) throws InstantiationException {
                  return ((ServerEndpointConfig)AnnotatedEndpoint.this.configuration).getConfigurator().getEndpointInstance(endpointClass);
               }
            };
         }
      } else {
         this.componentProvider = componentProvider;
      }

      Method onOpen = null;
      Method onClose = null;
      Method onError = null;
      ParameterExtractor[] onOpenParameters = null;
      ParameterExtractor[] onCloseParameters = null;
      ParameterExtractor[] onErrorParameters = null;
      Map unknownParams = new HashMap();
      AnnotatedClassValidityChecker validityChecker = new AnnotatedClassValidityChecker(annotatedClass, this.configuration.getEncoders(), this.configuration.getDecoders(), collector);
      Method[] var16 = annotatedClass.getMethods();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         Method m = var16[var18];
         if (!m.isBridge()) {
            Annotation[] var20 = m.getAnnotations();
            int var21 = var20.length;

            for(int var22 = 0; var22 < var21; ++var22) {
               Annotation a = var20[var22];
               if (a instanceof OnOpen) {
                  if (onOpen == null) {
                     onOpen = m;
                     onOpenParameters = this.getParameterExtractors(m, unknownParams, collector);
                     validityChecker.checkOnOpenParams(m, unknownParams);
                  } else {
                     collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(OnOpen.class.getSimpleName(), annotatedClass.getName(), onOpen.getName(), m.getName())));
                  }
               } else if (a instanceof OnClose) {
                  if (onClose == null) {
                     onClose = m;
                     onCloseParameters = this.getOnCloseParameterExtractors(m, unknownParams, collector);
                     validityChecker.checkOnCloseParams(m, unknownParams);
                     if (unknownParams.size() == 1 && unknownParams.values().iterator().next() != CloseReason.class) {
                        onCloseParameters[(Integer)unknownParams.keySet().iterator().next()] = new ParamValue(0);
                     }
                  } else {
                     collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(OnClose.class.getSimpleName(), annotatedClass.getName(), onClose.getName(), m.getName())));
                  }
               } else if (a instanceof OnError) {
                  if (onError == null) {
                     onError = m;
                     onErrorParameters = this.getParameterExtractors(m, unknownParams, collector);
                     validityChecker.checkOnErrorParams(m, unknownParams);
                     if (unknownParams.size() == 1 && Throwable.class == unknownParams.values().iterator().next()) {
                        onErrorParameters[(Integer)unknownParams.keySet().iterator().next()] = new ParamValue(0);
                     } else if (!unknownParams.isEmpty()) {
                        LOGGER.warning(LocalizationMessages.ENDPOINT_UNKNOWN_PARAMS(annotatedClass.getName(), m.getName(), unknownParams));
                        onError = null;
                        onErrorParameters = null;
                     }
                  } else {
                     collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_MULTIPLE_METHODS(OnError.class.getSimpleName(), annotatedClass.getName(), onError.getName(), m.getName())));
                  }
               } else if (a instanceof OnMessage) {
                  long maxMessageSize = ((OnMessage)a).maxMessageSize();
                  if (maxMessageSize > (long)incomingBufferSize) {
                     LOGGER.config(LocalizationMessages.ENDPOINT_MAX_MESSAGE_SIZE_TOO_LONG(maxMessageSize, m.getName(), annotatedClass.getName(), incomingBufferSize));
                  }

                  ParameterExtractor[] extractors = this.getParameterExtractors(m, unknownParams, collector);
                  if (unknownParams.size() == 1) {
                     Map.Entry entry = (Map.Entry)unknownParams.entrySet().iterator().next();
                     extractors[(Integer)entry.getKey()] = new ParamValue(0);
                     MessageHandlerFactory handlerFactory = new WholeHandler(componentProvider.getInvocableMethod(m), extractors, (Class)entry.getValue(), maxMessageSize);
                     this.messageHandlerFactories.add(handlerFactory);
                     validityChecker.checkOnMessageParams(m, handlerFactory.create((Session)null));
                  } else if (unknownParams.size() != 2) {
                     collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_WRONG_PARAMS(annotatedClass.getName(), m.getName())));
                  } else {
                     Iterator it = unknownParams.entrySet().iterator();
                     Map.Entry message = (Map.Entry)it.next();
                     Map.Entry last;
                     if (message.getValue() != Boolean.TYPE && message.getValue() != Boolean.class) {
                        last = (Map.Entry)it.next();
                     } else {
                        last = message;
                        message = (Map.Entry)it.next();
                     }

                     extractors[(Integer)message.getKey()] = new ParamValue(0);
                     extractors[(Integer)last.getKey()] = new ParamValue(1);
                     if (last.getValue() != Boolean.TYPE && last.getValue() != Boolean.class) {
                        collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_WRONG_PARAMS(annotatedClass.getName(), m.getName())));
                     } else {
                        MessageHandlerFactory handlerFactory = new PartialHandler(componentProvider.getInvocableMethod(m), extractors, (Class)message.getValue(), maxMessageSize);
                        this.messageHandlerFactories.add(handlerFactory);
                        validityChecker.checkOnMessageParams(m, handlerFactory.create((Session)null));
                     }
                  }
               }
            }
         }
      }

      this.onOpenMethod = onOpen == null ? null : componentProvider.getInvocableMethod(onOpen);
      this.onErrorMethod = onError == null ? null : componentProvider.getInvocableMethod(onError);
      this.onCloseMethod = onClose == null ? null : componentProvider.getInvocableMethod(onClose);
      this.onOpenParameters = onOpenParameters;
      this.onErrorParameters = onErrorParameters;
      this.onCloseParameters = onCloseParameters;
   }

   private EndpointConfig createEndpointConfig(Class annotatedClass, boolean isServerEndpoint, ErrorCollector collector) {
      ArrayList encoderClasses;
      ArrayList decoderClasses;
      String[] subProtocols;
      if (isServerEndpoint) {
         ServerEndpoint wseAnnotation = (ServerEndpoint)annotatedClass.getAnnotation(ServerEndpoint.class);
         if (wseAnnotation == null) {
            collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_ANNOTATION_NOT_FOUND(ServerEndpoint.class.getSimpleName(), annotatedClass.getName())));
            return null;
         } else {
            encoderClasses = new ArrayList();
            decoderClasses = new ArrayList();
            encoderClasses.addAll(Arrays.asList(wseAnnotation.encoders()));
            decoderClasses.addAll(Arrays.asList(wseAnnotation.decoders()));
            subProtocols = wseAnnotation.subprotocols();
            decoderClasses.addAll(TyrusEndpointWrapper.getDefaultDecoders());
            MaxSessions wseMaxSessionsAnnotation = (MaxSessions)annotatedClass.getAnnotation(MaxSessions.class);
            if (wseMaxSessionsAnnotation != null) {
               TyrusServerEndpointConfig.Builder builder = TyrusServerEndpointConfig.Builder.create(annotatedClass, wseAnnotation.value()).encoders(encoderClasses).decoders(decoderClasses).subprotocols(Arrays.asList(subProtocols));
               if (!wseAnnotation.configurator().equals(ServerEndpointConfig.Configurator.class)) {
                  builder = builder.configurator((ServerEndpointConfig.Configurator)ReflectionHelper.getInstance(wseAnnotation.configurator(), collector));
               }

               builder.maxSessions(wseMaxSessionsAnnotation.value());
               return builder.build();
            } else {
               ServerEndpointConfig.Builder builder = Builder.create(annotatedClass, wseAnnotation.value()).encoders(encoderClasses).decoders(decoderClasses).subprotocols(Arrays.asList(subProtocols));
               if (!wseAnnotation.configurator().equals(ServerEndpointConfig.Configurator.class)) {
                  builder = builder.configurator((ServerEndpointConfig.Configurator)ReflectionHelper.getInstance(wseAnnotation.configurator(), collector));
               }

               return builder.build();
            }
         }
      } else {
         ClientEndpoint wscAnnotation = (ClientEndpoint)annotatedClass.getAnnotation(ClientEndpoint.class);
         if (wscAnnotation == null) {
            collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_ANNOTATION_NOT_FOUND(ClientEndpoint.class.getSimpleName(), annotatedClass.getName())));
            return null;
         } else {
            encoderClasses = new ArrayList();
            decoderClasses = new ArrayList();
            encoderClasses.addAll(Arrays.asList(wscAnnotation.encoders()));
            decoderClasses.addAll(Arrays.asList(wscAnnotation.decoders()));
            subProtocols = wscAnnotation.subprotocols();
            decoderClasses.addAll(TyrusEndpointWrapper.getDefaultDecoders());
            ClientEndpointConfig.Configurator configurator = (ClientEndpointConfig.Configurator)ReflectionHelper.getInstance(wscAnnotation.configurator(), collector);
            return javax.websocket.ClientEndpointConfig.Builder.create().encoders(encoderClasses).decoders(decoderClasses).preferredSubprotocols(Arrays.asList(subProtocols)).configurator(configurator).build();
         }
      }
   }

   static Class getDecoderClassType(Class decoder) {
      Class rootClass = null;
      if (Decoder.Text.class.isAssignableFrom(decoder)) {
         rootClass = Decoder.Text.class;
      } else if (Decoder.Binary.class.isAssignableFrom(decoder)) {
         rootClass = Decoder.Binary.class;
      } else if (Decoder.TextStream.class.isAssignableFrom(decoder)) {
         rootClass = Decoder.TextStream.class;
      } else if (Decoder.BinaryStream.class.isAssignableFrom(decoder)) {
         rootClass = Decoder.BinaryStream.class;
      }

      ReflectionHelper.DeclaringClassInterfacePair p = ReflectionHelper.getClass(decoder, rootClass);
      Class[] as = ReflectionHelper.getParameterizedClassArguments(p);
      return as == null ? Object.class : (as[0] == null ? Object.class : as[0]);
   }

   static Class getEncoderClassType(Class encoder) {
      Class rootClass = null;
      if (Encoder.Text.class.isAssignableFrom(encoder)) {
         rootClass = Encoder.Text.class;
      } else if (Encoder.Binary.class.isAssignableFrom(encoder)) {
         rootClass = Encoder.Binary.class;
      } else if (Encoder.TextStream.class.isAssignableFrom(encoder)) {
         rootClass = Encoder.TextStream.class;
      } else if (Encoder.BinaryStream.class.isAssignableFrom(encoder)) {
         rootClass = Encoder.BinaryStream.class;
      }

      ReflectionHelper.DeclaringClassInterfacePair p = ReflectionHelper.getClass(encoder, rootClass);
      Class[] as = ReflectionHelper.getParameterizedClassArguments(p);
      return as == null ? Object.class : (as[0] == null ? Object.class : as[0]);
   }

   private ParameterExtractor[] getOnCloseParameterExtractors(Method method, Map unknownParams, ErrorCollector collector) {
      return this.getParameterExtractors(method, unknownParams, new HashSet(Arrays.asList(CloseReason.class)), collector);
   }

   private ParameterExtractor[] getParameterExtractors(Method method, Map unknownParams, ErrorCollector collector) {
      return this.getParameterExtractors(method, unknownParams, Collections.emptySet(), collector);
   }

   private ParameterExtractor[] getParameterExtractors(Method method, Map unknownParams, Set params, ErrorCollector collector) {
      ParameterExtractor[] result = new ParameterExtractor[method.getParameterTypes().length];
      boolean sessionPresent = false;
      unknownParams.clear();

      for(int i = 0; i < method.getParameterTypes().length; ++i) {
         final Class type = method.getParameterTypes()[i];
         final String pathParamName = this.getPathParamName(method.getParameterAnnotations()[i]);
         if (pathParamName != null) {
            if (!PrimitivesToWrappers.isPrimitiveWrapper(type) && !type.isPrimitive() && !type.equals(String.class)) {
               collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_WRONG_PATH_PARAM(method.getName(), type.getName())));
            }

            result[i] = new ParameterExtractor() {
               final Decoder.Text decoder;

               {
                  this.decoder = (Decoder.Text)PrimitiveDecoders.ALL_INSTANCES.get(PrimitivesToWrappers.getPrimitiveWrapper(type));
               }

               public Object value(Session session, Object... values) throws DecodeException {
                  Object result = null;
                  if (this.decoder != null) {
                     result = this.decoder.decode((String)session.getPathParameters().get(pathParamName));
                  } else if (type.equals(String.class)) {
                     result = session.getPathParameters().get(pathParamName);
                  }

                  return result;
               }
            };
         } else if (type == Session.class) {
            if (sessionPresent) {
               collector.addException(new DeploymentException(LocalizationMessages.ENDPOINT_MULTIPLE_SESSION_PARAM(method.getName())));
            } else {
               sessionPresent = true;
            }

            result[i] = new ParameterExtractor() {
               public Object value(Session session, Object... values) {
                  return session;
               }
            };
         } else if (type == EndpointConfig.class) {
            result[i] = new ParameterExtractor() {
               public Object value(Session session, Object... values) {
                  return AnnotatedEndpoint.this.getEndpointConfig();
               }
            };
         } else if (params.contains(type)) {
            result[i] = new ParameterExtractor() {
               public Object value(Session session, Object... values) {
                  Object[] var3 = values;
                  int var4 = values.length;

                  for(int var5 = 0; var5 < var4; ++var5) {
                     Object value = var3[var5];
                     if (value != null && type.isAssignableFrom(value.getClass())) {
                        return value;
                     }
                  }

                  return null;
               }
            };
         } else {
            unknownParams.put(i, type);
         }
      }

      return result;
   }

   private String getPathParamName(Annotation[] annotations) {
      Annotation[] var2 = annotations;
      int var3 = annotations.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation a = var2[var4];
         if (a instanceof PathParam) {
            return ((PathParam)a).value();
         }
      }

      return null;
   }

   private Object callMethod(Method method, ParameterExtractor[] extractors, Session session, boolean callOnError, Object... params) {
      ErrorCollector collector = new ErrorCollector();
      Object[] paramValues = new Object[extractors.length];

      try {
         Object endpoint = this.annotatedInstance != null ? this.annotatedInstance : this.componentProvider.getInstance(this.annotatedClass, session, collector);
         if (callOnError && endpoint == null) {
            if (!collector.isEmpty()) {
               Throwable t = collector.composeComprehensiveException();
               LOGGER.log(Level.FINE, t.getMessage(), t);
            }

            try {
               session.close(CloseReasons.UNEXPECTED_CONDITION.getCloseReason());
            } catch (Exception var10) {
               LOGGER.log(Level.FINEST, var10.getMessage(), var10);
            }

            return null;
         } else if (!collector.isEmpty()) {
            throw collector.composeComprehensiveException();
         } else {
            for(int i = 0; i < paramValues.length; ++i) {
               paramValues[i] = extractors[i].value(session, params);
            }

            return method.invoke(endpoint, paramValues);
         }
      } catch (Exception var11) {
         if (callOnError) {
            this.onError(session, (Throwable)(var11 instanceof InvocationTargetException ? var11.getCause() : var11));
         } else {
            LOGGER.log(Level.INFO, LocalizationMessages.ENDPOINT_EXCEPTION_FROM_ON_ERROR(method), var11);
         }

         return null;
      }
   }

   void onClose(CloseReason closeReason, Session session) {
      try {
         if (this.onCloseMethod != null) {
            this.callMethod(this.onCloseMethod, this.onCloseParameters, session, true, closeReason);
         }
      } finally {
         this.componentProvider.removeSession(session);
      }

   }

   public void onClose(Session session, CloseReason closeReason) {
      this.onClose(closeReason, session);
   }

   public void onError(Session session, Throwable thr) {
      if (this.onErrorMethod != null) {
         this.callMethod(this.onErrorMethod, this.onErrorParameters, session, false, thr);
      } else {
         LOGGER.log(Level.INFO, LocalizationMessages.ENDPOINT_UNHANDLED_EXCEPTION(this.annotatedClass.getCanonicalName()), thr);
      }

      this.endpointEventListener.onError(session.getId(), thr);
   }

   public EndpointConfig getEndpointConfig() {
      return this.configuration;
   }

   public void onOpen(Session session, EndpointConfig configuration) {
      Iterator var3 = this.messageHandlerFactories.iterator();

      while(var3.hasNext()) {
         MessageHandlerFactory f = (MessageHandlerFactory)var3.next();
         session.addMessageHandler(f.create(session));
      }

      if (this.onOpenMethod != null) {
         this.callMethod(this.onOpenMethod, this.onOpenParameters, session, true);
      }

   }

   private class PartialHandler extends MessageHandlerFactory {
      PartialHandler(Method method, ParameterExtractor[] extractors, Class type, long maxMessageSize) {
         super(method, extractors, type, maxMessageSize);
      }

      public MessageHandler create(final Session session) {
         return new AsyncMessageHandler() {
            public void onMessage(Object partialMessage, boolean last) {
               Object result = AnnotatedEndpoint.this.callMethod(PartialHandler.this.method, PartialHandler.this.extractors, session, true, partialMessage, last);
               if (result != null) {
                  try {
                     session.getBasicRemote().sendObject(result);
                  } catch (Exception var5) {
                     AnnotatedEndpoint.this.onError(session, var5);
                  }
               }

            }

            public Class getType() {
               return PartialHandler.this.type;
            }

            public long getMaxMessageSize() {
               return PartialHandler.this.maxMessageSize;
            }
         };
      }
   }

   private class WholeHandler extends MessageHandlerFactory {
      WholeHandler(Method method, ParameterExtractor[] extractors, Class type, long maxMessageSize) {
         super(method, extractors, type, maxMessageSize);
      }

      public MessageHandler create(final Session session) {
         return new BasicMessageHandler() {
            public void onMessage(Object message) {
               Object result = AnnotatedEndpoint.this.callMethod(WholeHandler.this.method, WholeHandler.this.extractors, session, true, message);
               if (result != null) {
                  try {
                     session.getBasicRemote().sendObject(result);
                  } catch (Exception var4) {
                     AnnotatedEndpoint.this.onError(session, var4);
                  }
               }

            }

            public Class getType() {
               return WholeHandler.this.type;
            }

            public long getMaxMessageSize() {
               return WholeHandler.this.maxMessageSize;
            }
         };
      }
   }

   private abstract class MessageHandlerFactory {
      final Method method;
      final ParameterExtractor[] extractors;
      final Class type;
      final long maxMessageSize;

      MessageHandlerFactory(Method method, ParameterExtractor[] extractors, Class type, long maxMessageSize) {
         this.method = method;
         this.extractors = extractors;
         this.type = PrimitivesToWrappers.getPrimitiveWrapper(type) == null ? type : PrimitivesToWrappers.getPrimitiveWrapper(type);
         this.maxMessageSize = maxMessageSize;
      }

      abstract MessageHandler create(Session var1);
   }

   static class ParamValue implements ParameterExtractor {
      private final int index;

      ParamValue(int index) {
         this.index = index;
      }

      public Object value(Session session, Object... paramValues) {
         return paramValues[this.index];
      }
   }

   interface ParameterExtractor {
      Object value(Session var1, Object... var2) throws DecodeException;
   }
}
