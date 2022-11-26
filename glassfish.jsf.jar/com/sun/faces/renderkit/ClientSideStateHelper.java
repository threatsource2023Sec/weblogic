package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.DebugObjectOutputStream;
import com.sun.faces.util.DebugUtil;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class ClientSideStateHelper extends StateHelper {
   private static final Logger LOGGER;
   private ByteArrayGuard guard;
   private boolean stateTimeoutEnabled;
   private long stateTimeout;
   private int csBuffSize;
   private boolean debugSerializedState;

   public ClientSideStateHelper() {
      this.init();
   }

   public void writeState(FacesContext ctx, Object state, StringBuilder stateCapture) throws IOException {
      if (stateCapture != null) {
         this.doWriteState(ctx, state, new StringBuilderWriter(stateCapture));
      } else {
         ResponseWriter writer = ctx.getResponseWriter();
         writer.startElement("input", (UIComponent)null);
         writer.writeAttribute("type", "hidden", (String)null);
         writer.writeAttribute("name", RenderKitUtils.PredefinedPostbackParameter.VIEW_STATE_PARAM.getName(ctx), (String)null);
         if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableViewStateIdRendering)) {
            String viewStateId = Util.getViewStateId(ctx);
            writer.writeAttribute("id", viewStateId, (String)null);
         }

         StringBuilder stateBuilder = new StringBuilder();
         this.doWriteState(ctx, state, new StringBuilderWriter(stateBuilder));
         writer.writeAttribute("value", stateBuilder.toString(), (String)null);
         if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AutoCompleteOffOnViewState)) {
            writer.writeAttribute("autocomplete", "off", (String)null);
         }

         writer.endElement("input");
         this.writeClientWindowField(ctx, writer);
         this.writeRenderKitIdField(ctx, writer);
      }

   }

   public Object getState(FacesContext ctx, String viewId) throws IOException {
      String stateString = getStateParamValue(ctx);
      if (stateString == null) {
         return null;
      } else {
         return "stateless".equals(stateString) ? "stateless" : this.doGetState(ctx, stateString);
      }
   }

   protected Object doGetState(FacesContext ctx, String stateString) {
      if ("stateless".equals(stateString)) {
         return null;
      } else {
         ObjectInputStream ois = null;
         InputStream bis = null;

         Object var6;
         try {
            Object structure;
            if (this.guard != null) {
               byte[] bytes = stateString.getBytes("UTF-8");
               byte[] decodedBytes = Base64.getDecoder().decode(bytes);
               bytes = this.guard.decrypt(ctx, decodedBytes);
               if (bytes == null) {
                  structure = null;
                  return structure;
               }

               bis = new ByteArrayInputStream(bytes);
            }

            if (null != bis && this.compressViewState) {
               bis = new GZIPInputStream((InputStream)bis);
            }

            if (null == bis) {
               throw new FacesException("Unable to encode stateString");
            }

            ois = this.serialProvider.createObjectInputStream((InputStream)bis);
            long stateTime = 0L;
            Object state;
            if (this.stateTimeoutEnabled) {
               try {
                  stateTime = ois.readLong();
               } catch (IOException var26) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("Client state timeout is enabled, but unable to find the time marker in the serialized state.  Assuming state to be old and returning null.");
                  }

                  state = null;
                  return state;
               }
            }

            structure = ois.readObject();
            state = ois.readObject();
            Object[] var9;
            if (stateTime != 0L && this.hasStateExpired(stateTime)) {
               var9 = null;
               return var9;
            }

            var9 = new Object[]{structure, state};
            return var9;
         } catch (OptionalDataException var27) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var27.getMessage(), var27);
            }

            throw new FacesException(var27);
         } catch (ClassNotFoundException var28) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var28.getMessage(), var28);
            }

            throw new FacesException(var28);
         } catch (InvalidClassException var29) {
            var6 = null;
         } catch (IOException var30) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var30.getMessage(), var30);
            }

            throw new FacesException(var30);
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var25) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Closing stream", var25);
                  }
               }
            }

         }

         return var6;
      }
   }

   protected void doWriteState(FacesContext facesContext, Object state, Writer writer) throws IOException {
      if (facesContext.getViewRoot().isTransient()) {
         writer.write("stateless");
         writer.flush();
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         OutputStream base = null;
         if (this.compressViewState) {
            base = new GZIPOutputStream(baos, this.csBuffSize);
         } else {
            base = baos;
         }

         ObjectOutputStream oos = null;

         try {
            oos = this.serialProvider.createObjectOutputStream(new BufferedOutputStream((OutputStream)base));
            if (this.stateTimeoutEnabled) {
               oos.writeLong(System.currentTimeMillis());
            }

            Object[] stateToWrite = (Object[])((Object[])state);
            ByteArrayOutputStream discard;
            DebugObjectOutputStream out;
            if (this.debugSerializedState) {
               discard = new ByteArrayOutputStream();
               out = new DebugObjectOutputStream(discard);

               try {
                  out.writeObject(stateToWrite[0]);
               } catch (Exception var19) {
                  throw new FacesException("Serialization error. Path to offending instance: " + out.getStack(), var19);
               }
            }

            oos.writeObject(stateToWrite[0]);
            if (this.debugSerializedState) {
               discard = new ByteArrayOutputStream();
               out = new DebugObjectOutputStream(discard);

               try {
                  out.writeObject(stateToWrite[1]);
               } catch (Exception var18) {
                  DebugUtil.printState((Map)stateToWrite[1], LOGGER);
                  throw new FacesException("Serialization error. Path to offending instance: " + out.getStack(), var18);
               }
            }

            oos.writeObject(stateToWrite[1]);
            oos.flush();
            oos.close();
            oos = null;
            byte[] bytes = baos.toByteArray();
            if (this.guard != null) {
               bytes = this.guard.encrypt(facesContext, bytes);
            }

            String encodedBytes = new String(Base64.getEncoder().encode(bytes));
            writer.write(encodedBytes);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Client State: total number of characters written: {0}", encodedBytes.length());
            }
         } finally {
            if (oos != null) {
               try {
                  oos.close();
               } catch (IOException var20) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Closing stream", var20);
                  }
               }
            }

         }

      }
   }

   protected boolean hasStateExpired(long stateTime) {
      if (this.stateTimeoutEnabled) {
         long elapsed = (System.currentTimeMillis() - stateTime) / 60000L;
         return elapsed > this.stateTimeout;
      } else {
         return false;
      }
   }

   protected void init() {
      if (this.webConfig.canProcessJndiEntries() && !this.webConfig.isSet(WebConfiguration.BooleanWebContextInitParameter.DisableClientStateEncryption)) {
         this.guard = new ByteArrayGuard();
      } else if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "jsf.config.webconfig.enventry.clientencrypt");
      }

      this.stateTimeoutEnabled = this.webConfig.isSet(WebConfiguration.WebContextInitParameter.ClientStateTimeout);
      String size;
      if (this.stateTimeoutEnabled) {
         size = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ClientStateTimeout);

         try {
            this.stateTimeout = Long.parseLong(size);
         } catch (NumberFormatException var4) {
            this.stateTimeout = Long.parseLong(WebConfiguration.WebContextInitParameter.ClientStateTimeout.getDefaultValue());
         }
      }

      size = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ClientStateWriteBufferSize);
      String defaultSize = WebConfiguration.WebContextInitParameter.ClientStateWriteBufferSize.getDefaultValue();

      try {
         this.csBuffSize = Integer.parseInt(size);
         if (this.csBuffSize % 2 != 0) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.renderkit.resstatemgr.clientbuf_div_two", new Object[]{WebConfiguration.WebContextInitParameter.ClientStateWriteBufferSize.getQualifiedName(), size, defaultSize});
            }

            this.csBuffSize = Integer.parseInt(defaultSize);
         } else {
            this.csBuffSize /= 2;
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Using client state buffer size of " + this.csBuffSize);
            }
         }
      } catch (NumberFormatException var5) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.renderkit.resstatemgr.clientbuf_not_integer", new Object[]{WebConfiguration.WebContextInitParameter.ClientStateWriteBufferSize.getQualifiedName(), size, defaultSize});
         }

         this.csBuffSize = Integer.parseInt(defaultSize);
      }

      this.debugSerializedState = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableClientStateDebugging);
   }

   public boolean isStateless(FacesContext facesContext, String viewId) throws IllegalStateException {
      if (facesContext.isPostback()) {
         Object stateObject;
         try {
            stateObject = this.getState(facesContext, viewId);
         } catch (IOException var5) {
            throw new IllegalStateException("Cannot determine whether or not the request is stateless", var5);
         }

         return stateObject instanceof String && "stateless".equals(stateObject);
      } else {
         throw new IllegalStateException("Cannot determine whether or not the request is stateless");
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   protected static final class StringBuilderWriter extends Writer {
      private StringBuilder sb;

      protected StringBuilderWriter(StringBuilder sb) {
         this.sb = sb;
      }

      public void write(int c) throws IOException {
         this.sb.append((char)c);
      }

      public void write(char[] cbuf) throws IOException {
         this.sb.append(cbuf);
      }

      public void write(String str) throws IOException {
         this.sb.append(str);
      }

      public void write(String str, int off, int len) throws IOException {
         this.sb.append(str.toCharArray(), off, len);
      }

      public Writer append(CharSequence csq) throws IOException {
         this.sb.append(csq);
         return this;
      }

      public Writer append(CharSequence csq, int start, int end) throws IOException {
         this.sb.append(csq, start, end);
         return this;
      }

      public Writer append(char c) throws IOException {
         this.sb.append(c);
         return this;
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
         this.sb.append(cbuf, off, len);
      }

      public void flush() throws IOException {
      }

      public void close() throws IOException {
      }
   }
}
