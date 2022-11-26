package org.glassfish.tyrus.core;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.Decoder;
import javax.websocket.MessageHandler;
import javax.websocket.PongMessage;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class MessageHandlerManager {
   private static final List WHOLE_TEXT_HANDLER_TYPES = Arrays.asList(String.class, Reader.class);
   private static final Class PARTIAL_TEXT_HANDLER_TYPE = String.class;
   private static final List WHOLE_BINARY_HANDLER_TYPES = Arrays.asList(ByteBuffer.class, InputStream.class, byte[].class);
   private static final List PARTIAL_BINARY_HANDLER_TYPES = Arrays.asList(ByteBuffer.class, byte[].class);
   private static final Class PONG_HANDLER_TYPE = PongMessage.class;
   private boolean textHandlerPresent;
   private boolean textWholeHandlerPresent;
   private boolean binaryHandlerPresent;
   private boolean binaryWholeHandlerPresent;
   private boolean pongHandlerPresent;
   private boolean readerHandlerPresent;
   private boolean inputStreamHandlerPresent;
   private final Map registeredHandlers;
   private final List decoders;
   private Set messageHandlerCache;

   public MessageHandlerManager() {
      this(Collections.emptyList());
   }

   MessageHandlerManager(List decoders) {
      this.registeredHandlers = new HashMap();
      this.decoders = decoders;
   }

   static MessageHandlerManager fromDecoderInstances(List decoders) {
      List decoderList = new ArrayList();
      Iterator var2 = decoders.iterator();

      while(var2.hasNext()) {
         Decoder decoder = (Decoder)var2.next();
         if (decoder instanceof CoderWrapper) {
            decoderList.add(((CoderWrapper)decoder).getCoderClass());
         } else {
            decoderList.add(decoder.getClass());
         }
      }

      return new MessageHandlerManager(decoderList);
   }

   public static MessageHandlerManager fromDecoderClasses(List decoderClasses) {
      return new MessageHandlerManager(decoderClasses);
   }

   public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
      Class handlerClass = getHandlerType(handler);
      if (handler instanceof MessageHandler.Whole) {
         this.addMessageHandler(handlerClass, (MessageHandler.Whole)handler);
      } else if (handler instanceof MessageHandler.Partial) {
         this.addMessageHandler(handlerClass, (MessageHandler.Partial)handler);
      } else {
         this.throwException(LocalizationMessages.MESSAGE_HANDLER_WHOLE_OR_PARTIAL());
      }

   }

   public void addMessageHandler(Class clazz, MessageHandler.Whole handler) throws IllegalStateException {
      if (WHOLE_TEXT_HANDLER_TYPES.contains(clazz)) {
         if (this.textHandlerPresent) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
         } else {
            if (Reader.class.isAssignableFrom(clazz)) {
               this.readerHandlerPresent = true;
            }

            this.textHandlerPresent = true;
            this.textWholeHandlerPresent = true;
         }
      } else if (WHOLE_BINARY_HANDLER_TYPES.contains(clazz)) {
         if (this.binaryHandlerPresent) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
         } else {
            if (InputStream.class.isAssignableFrom(clazz)) {
               this.inputStreamHandlerPresent = true;
            }

            this.binaryHandlerPresent = true;
            this.binaryWholeHandlerPresent = true;
         }
      } else if (PONG_HANDLER_TYPE.equals(clazz)) {
         if (this.pongHandlerPresent) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_PONG());
         } else {
            this.pongHandlerPresent = true;
         }
      } else {
         boolean viable = false;
         if (this.checkTextDecoders(clazz)) {
            if (this.textHandlerPresent) {
               this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
            } else {
               this.textHandlerPresent = true;
               this.textWholeHandlerPresent = true;
               viable = true;
            }
         }

         if (this.checkBinaryDecoders(clazz)) {
            if (this.binaryHandlerPresent) {
               this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
            } else {
               this.binaryHandlerPresent = true;
               this.binaryWholeHandlerPresent = true;
               viable = true;
            }
         }

         if (!viable) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_DECODER_NOT_REGISTERED(clazz));
         }
      }

      this.registerMessageHandler(clazz, handler);
   }

   public void addMessageHandler(Class clazz, MessageHandler.Partial handler) throws IllegalStateException {
      boolean viable = false;
      if (PARTIAL_TEXT_HANDLER_TYPE.equals(clazz)) {
         if (this.textHandlerPresent) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
         } else {
            this.textHandlerPresent = true;
            viable = true;
         }
      }

      if (PARTIAL_BINARY_HANDLER_TYPES.contains(clazz)) {
         if (this.binaryHandlerPresent) {
            this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
         } else {
            this.binaryHandlerPresent = true;
            viable = true;
         }
      }

      if (!viable) {
         this.throwException(LocalizationMessages.MESSAGE_HANDLER_PARTIAL_INVALID_TYPE(clazz.getName()));
      }

      this.registerMessageHandler(clazz, handler);
   }

   private void registerMessageHandler(Class clazz, MessageHandler handler) {
      if (this.registeredHandlers.containsKey(clazz)) {
         this.throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TYPE(clazz));
      } else {
         this.registeredHandlers.put(clazz, handler);
      }

      this.messageHandlerCache = null;
   }

   private void throwException(String text) throws IllegalStateException {
      throw new IllegalStateException(text);
   }

   public void removeMessageHandler(MessageHandler handler) {
      Iterator iterator = this.registeredHandlers.entrySet().iterator();
      Class handlerClass = null;

      while(iterator.hasNext()) {
         Map.Entry next = (Map.Entry)iterator.next();
         if (((MessageHandler)next.getValue()).equals(handler)) {
            handlerClass = (Class)next.getKey();
            iterator.remove();
            this.messageHandlerCache = null;
            break;
         }
      }

      if (handlerClass != null) {
         if (handler instanceof MessageHandler.Whole) {
            if (WHOLE_TEXT_HANDLER_TYPES.contains(handlerClass)) {
               this.textHandlerPresent = false;
               this.textWholeHandlerPresent = false;
            } else if (WHOLE_BINARY_HANDLER_TYPES.contains(handlerClass)) {
               this.binaryHandlerPresent = false;
               this.binaryWholeHandlerPresent = false;
            } else if (PONG_HANDLER_TYPE.equals(handlerClass)) {
               this.pongHandlerPresent = false;
            } else if (this.checkTextDecoders(handlerClass)) {
               this.textHandlerPresent = false;
               this.textWholeHandlerPresent = false;
            } else if (this.checkBinaryDecoders(handlerClass)) {
               this.binaryHandlerPresent = false;
               this.binaryWholeHandlerPresent = false;
            }
         } else if (PARTIAL_TEXT_HANDLER_TYPE.equals(handlerClass)) {
            this.textHandlerPresent = false;
         } else if (PARTIAL_BINARY_HANDLER_TYPES.contains(handlerClass)) {
            this.binaryHandlerPresent = false;
         }

      }
   }

   public Set getMessageHandlers() {
      if (this.messageHandlerCache == null) {
         this.messageHandlerCache = Collections.unmodifiableSet(new HashSet(this.registeredHandlers.values()));
      }

      return this.messageHandlerCache;
   }

   public List getOrderedWholeMessageHandlers() {
      List result = new ArrayList();
      Iterator var2 = this.registeredHandlers.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (entry.getValue() instanceof MessageHandler.Whole) {
            result.add(entry);
         }
      }

      Collections.sort(result, new MessageHandlerComparator());
      return result;
   }

   static Class getHandlerType(MessageHandler handler) {
      if (handler instanceof AsyncMessageHandler) {
         return ((AsyncMessageHandler)handler).getType();
      } else if (handler instanceof BasicMessageHandler) {
         return ((BasicMessageHandler)handler).getType();
      } else {
         Class root;
         if (handler instanceof MessageHandler.Partial) {
            root = MessageHandler.Partial.class;
         } else {
            if (!(handler instanceof MessageHandler.Whole)) {
               throw new IllegalArgumentException(LocalizationMessages.MESSAGE_HANDLER_ILLEGAL_ARGUMENT(handler));
            }

            root = MessageHandler.Whole.class;
         }

         Class result = ReflectionHelper.getClassType(handler.getClass(), root);
         return result == null ? Object.class : result;
      }
   }

   private boolean checkTextDecoders(Class requiredType) {
      Iterator var2 = this.decoders.iterator();

      Class decoderClass;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         decoderClass = (Class)var2.next();
      } while(!this.isTextDecoder(decoderClass) || !requiredType.isAssignableFrom(AnnotatedEndpoint.getDecoderClassType(decoderClass)));

      return true;
   }

   private boolean checkBinaryDecoders(Class requiredType) {
      Iterator var2 = this.decoders.iterator();

      Class decoderClass;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         decoderClass = (Class)var2.next();
      } while(!this.isBinaryDecoder(decoderClass) || !requiredType.isAssignableFrom(AnnotatedEndpoint.getDecoderClassType(decoderClass)));

      return true;
   }

   private boolean isTextDecoder(Class decoderClass) {
      return Decoder.Text.class.isAssignableFrom(decoderClass) || Decoder.TextStream.class.isAssignableFrom(decoderClass);
   }

   private boolean isBinaryDecoder(Class decoderClass) {
      return Decoder.Binary.class.isAssignableFrom(decoderClass) || Decoder.BinaryStream.class.isAssignableFrom(decoderClass);
   }

   boolean isWholeTextHandlerPresent() {
      return this.textWholeHandlerPresent;
   }

   boolean isWholeBinaryHandlerPresent() {
      return this.binaryWholeHandlerPresent;
   }

   boolean isPartialTextHandlerPresent() {
      return this.textHandlerPresent && !this.textWholeHandlerPresent;
   }

   boolean isPartialBinaryHandlerPresent() {
      return this.binaryHandlerPresent && !this.binaryWholeHandlerPresent;
   }

   public boolean isReaderHandlerPresent() {
      return this.readerHandlerPresent;
   }

   public boolean isInputStreamHandlerPresent() {
      return this.inputStreamHandlerPresent;
   }

   boolean isPongHandlerPresent() {
      return this.pongHandlerPresent;
   }

   private static class MessageHandlerComparator implements Comparator, Serializable {
      private static final long serialVersionUID = -5136634876439146784L;

      private MessageHandlerComparator() {
      }

      public int compare(Map.Entry o1, Map.Entry o2) {
         if (((Class)o1.getKey()).isAssignableFrom((Class)o2.getKey())) {
            return 1;
         } else {
            return ((Class)o2.getKey()).isAssignableFrom((Class)o1.getKey()) ? -1 : 0;
         }
      }

      // $FF: synthetic method
      MessageHandlerComparator(Object x0) {
         this();
      }
   }
}
