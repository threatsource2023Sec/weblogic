package org.glassfish.grizzly.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class StringEncoder extends AbstractTransformer {
   protected Charset charset;
   protected String stringTerminator;

   public StringEncoder() {
      this((String)null);
   }

   public StringEncoder(String stringTerminator) {
      this((Charset)null, stringTerminator);
   }

   public StringEncoder(Charset charset) {
      this(charset, (String)null);
   }

   public StringEncoder(Charset charset, String stringTerminator) {
      this.charset = charset != null ? charset : Charset.defaultCharset();
      this.stringTerminator = stringTerminator;
   }

   public String getName() {
      return "StringEncoder";
   }

   protected TransformationResult transformImpl(AttributeStorage storage, String input) throws TransformationException {
      if (input == null) {
         throw new TransformationException("Input could not be null");
      } else {
         byte[] byteRepresentation;
         try {
            if (this.stringTerminator != null) {
               input = input + this.stringTerminator;
            }

            byteRepresentation = input.getBytes(this.charset.name());
         } catch (UnsupportedEncodingException var5) {
            throw new TransformationException("Charset " + this.charset.name() + " is not supported", var5);
         }

         Buffer output = this.obtainMemoryManager(storage).allocate(byteRepresentation.length + 4);
         if (this.stringTerminator == null) {
            output.putInt(byteRepresentation.length);
         }

         output.put(byteRepresentation);
         output.flip();
         output.allowBufferDispose(true);
         return TransformationResult.createCompletedResult(output, (Object)null);
      }
   }

   public boolean hasInputRemaining(AttributeStorage storage, String input) {
      return input != null;
   }

   public Charset getCharset() {
      return this.charset;
   }

   public void setCharset(Charset charset) {
      this.charset = charset;
   }
}
