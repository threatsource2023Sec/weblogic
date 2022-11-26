package org.glassfish.grizzly.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class StringDecoder extends AbstractTransformer {
   private static final Logger logger = Grizzly.logger(StringDecoder.class);
   protected Charset charset;
   protected final Attribute lengthAttribute;
   protected byte[] stringTerminateBytes;

   public StringDecoder() {
      this((Charset)null, (String)null);
   }

   public StringDecoder(String stringTerminator) {
      this(Charset.forName("UTF-8"), stringTerminator);
   }

   public StringDecoder(Charset charset) {
      this(charset, (String)null);
   }

   public StringDecoder(Charset charset, String stringTerminator) {
      this.charset = charset != null ? charset : Charset.defaultCharset();
      if (stringTerminator != null) {
         try {
            this.stringTerminateBytes = stringTerminator.getBytes(this.charset.name());
         } catch (UnsupportedEncodingException var4) {
         }
      }

      this.lengthAttribute = this.attributeBuilder.createAttribute("StringDecoder.StringSize");
   }

   public String getName() {
      return "StringDecoder";
   }

   protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
      if (input == null) {
         throw new TransformationException("Input could not be null");
      } else {
         TransformationResult result = this.stringTerminateBytes == null ? this.parseWithLengthPrefix(storage, input) : this.parseWithTerminatingSeq(storage, input);
         return result;
      }
   }

   protected TransformationResult parseWithLengthPrefix(AttributeStorage storage, Buffer input) {
      Integer stringSize = (Integer)this.lengthAttribute.get(storage);
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "StringDecoder decode stringSize={0} buffer={1} content={2}", new Object[]{stringSize, input, input.toStringContent()});
      }

      if (stringSize == null) {
         if (input.remaining() < 4) {
            return TransformationResult.createIncompletedResult(input);
         }

         stringSize = input.getInt();
         this.lengthAttribute.set((AttributeStorage)storage, stringSize);
      }

      if (input.remaining() < stringSize) {
         return TransformationResult.createIncompletedResult(input);
      } else {
         int tmpLimit = input.limit();
         input.limit(input.position() + stringSize);
         String stringMessage = input.toStringContent(this.charset);
         input.position(input.limit());
         input.limit(tmpLimit);
         return TransformationResult.createCompletedResult(stringMessage, input);
      }
   }

   protected TransformationResult parseWithTerminatingSeq(AttributeStorage storage, Buffer input) {
      int terminationBytesLength = this.stringTerminateBytes.length;
      int checkIndex = 0;
      int termIndex = -1;
      Integer offsetInt = (Integer)this.lengthAttribute.get(storage);
      int offset = 0;
      if (offsetInt != null) {
         offset = offsetInt;
      }

      int i = input.position() + offset;

      for(int lim = input.limit(); i < lim; ++i) {
         if (input.get(i) == this.stringTerminateBytes[checkIndex]) {
            ++checkIndex;
            if (checkIndex >= terminationBytesLength) {
               termIndex = i - terminationBytesLength + 1;
               break;
            }
         }
      }

      if (termIndex >= 0) {
         i = input.limit();
         input.limit(termIndex);
         String stringMessage = input.toStringContent(this.charset);
         input.limit(i);
         input.position(termIndex + terminationBytesLength);
         return TransformationResult.createCompletedResult(stringMessage, input);
      } else {
         offset = input.remaining() - terminationBytesLength;
         if (offset < 0) {
            offset = 0;
         }

         this.lengthAttribute.set((AttributeStorage)storage, offset);
         return TransformationResult.createIncompletedResult(input);
      }
   }

   public void release(AttributeStorage storage) {
      this.lengthAttribute.remove(storage);
      super.release(storage);
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input != null && input.hasRemaining();
   }

   public Charset getCharset() {
      return this.charset;
   }

   public void setCharset(Charset charset) {
      this.charset = charset;
   }
}
