package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.Base64InputStream;
import com.sun.faces.io.Base64OutputStreamWriter;
import com.sun.faces.spi.SerializationProvider;
import com.sun.faces.spi.SerializationProviderFactory;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.ResponseStateManager;

public class ResponseStateManagerImpl extends ResponseStateManager {
   private static final Logger LOGGER;
   private static final char[] STATE_FIELD_START;
   private static final char[] STATE_FIELD_START_NO_ID;
   private static final char[] STATE_FIELD_END;
   private static final char[] STATE_FIELD_AUTOCOMPLETE_END;
   private char[] stateFieldStart;
   private char[] stateFieldEnd;
   private SerializationProvider serialProvider;
   private WebConfiguration webConfig;
   private Boolean compressState;
   private ByteArrayGuard guard;
   private int csBuffSize;

   public ResponseStateManagerImpl() {
      this.init();
   }

   public Object getComponentStateToRestore(FacesContext context) {
      return RequestStateManager.get(context, "com.sun.faces.FACES_VIEW_STATE");
   }

   public boolean isPostback(FacesContext context) {
      return context.getExternalContext().getRequestParameterMap().containsKey("javax.faces.ViewState");
   }

   public Object getTreeStructureToRestore(FacesContext context, String treeId) {
      Object s = RequestStateManager.get(context, "com.sun.faces.FACES_VIEW_STRUCTURE");
      if (s != null) {
         return s;
      } else {
         StateManager stateManager = Util.getStateManager(context);
         String viewString = getStateParam(context);
         if (viewString == null) {
            return null;
         } else if (stateManager.isSavingStateInClient(context)) {
            ObjectInputStream ois = null;

            try {
               Object bis;
               if (this.compressState) {
                  bis = new GZIPInputStream(new Base64InputStream(viewString));
               } else {
                  bis = new Base64InputStream(viewString);
               }

               if (this.guard != null) {
                  ois = this.serialProvider.createObjectInputStream(new CipherInputStream((InputStream)bis, this.guard.getDecryptionCipher()));
               } else {
                  ois = this.serialProvider.createObjectInputStream((InputStream)bis);
               }

               long stateTime = 0L;
               Object state;
               if (this.webConfig.isSet(WebConfiguration.WebContextInitParameter.ClientStateTimeout)) {
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

               Object structure = ois.readObject();
               state = ois.readObject();
               Object var12;
               if (stateTime != 0L && this.hasStateExpired(stateTime)) {
                  var12 = null;
                  return var12;
               } else {
                  storeStateInRequest(context, state);
                  storeStructureInRequest(context, structure);
                  var12 = structure;
                  return var12;
               }
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
            } catch (IOException var29) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var29.getMessage(), var29);
               }

               throw new FacesException(var29);
            } finally {
               if (ois != null) {
                  try {
                     ois.close();
                  } catch (IOException var25) {
                  }
               }

            }
         } else {
            return viewString;
         }
      }
   }

   public void writeState(FacesContext context, StateManager.SerializedView view) throws IOException {
      StateManager stateManager = Util.getStateManager(context);
      ResponseWriter writer = context.getResponseWriter();
      writer.write(this.stateFieldStart);
      if (stateManager.isSavingStateInClient(context)) {
         ObjectOutputStream oos = null;

         try {
            Base64OutputStreamWriter bos = new Base64OutputStreamWriter(this.csBuffSize, writer);
            Object base;
            if (this.compressState) {
               base = new GZIPOutputStream(bos, 1024);
            } else {
               base = bos;
            }

            if (this.guard != null) {
               oos = this.serialProvider.createObjectOutputStream(new BufferedOutputStream(new CipherOutputStream((OutputStream)base, this.guard.getEncryptionCipher())));
            } else {
               oos = this.serialProvider.createObjectOutputStream(new BufferedOutputStream((OutputStream)base, 1024));
            }

            if (this.webConfig.isSet(WebConfiguration.WebContextInitParameter.ClientStateTimeout)) {
               oos.writeLong(System.currentTimeMillis());
            }

            oos.writeObject(view.getStructure());
            oos.writeObject(view.getState());
            oos.flush();
            oos.close();
            bos.finish();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Client State: total number of characters written: " + bos.getTotalCharsWritten());
            }
         } finally {
            if (oos != null) {
               try {
                  oos.close();
               } catch (IOException var13) {
               }
            }

         }
      } else {
         writer.write(view.getStructure().toString());
      }

      writer.write(this.stateFieldEnd);
      writeRenderKitIdField(context, writer);
   }

   private boolean hasStateExpired(long stateTime) {
      if (this.webConfig.isSet(WebConfiguration.WebContextInitParameter.ClientStateTimeout)) {
         long elapsed = (System.currentTimeMillis() - stateTime) / 60000L;
         int timeout = Integer.parseInt(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ClientStateTimeout));
         return elapsed > (long)timeout;
      } else {
         return false;
      }
   }

   private static void storeStateInRequest(FacesContext context, Object state) {
      RequestStateManager.set(context, "com.sun.faces.FACES_VIEW_STATE", state);
   }

   private static void storeStructureInRequest(FacesContext context, Object structure) {
      RequestStateManager.set(context, "com.sun.faces.FACES_VIEW_STRUCTURE", structure);
   }

   private static void writeRenderKitIdField(FacesContext context, ResponseWriter writer) throws IOException {
      String result = context.getApplication().getDefaultRenderKitId();
      if (result != null && !"HTML_BASIC".equals(result)) {
         writer.startElement("input", context.getViewRoot());
         writer.writeAttribute("type", "hidden", "type");
         writer.writeAttribute("name", "javax.faces.RenderKitId", "name");
         writer.writeAttribute("value", result, "value");
         writer.endElement("input");
      }

   }

   private static String getStateParam(FacesContext context) {
      String pValue = (String)context.getExternalContext().getRequestParameterMap().get("javax.faces.ViewState");
      if (pValue != null && pValue.length() == 0) {
         pValue = null;
      }

      return pValue;
   }

   private void init() {
      this.webConfig = WebConfiguration.getInstance();

      assert this.webConfig != null;

      String pass = this.webConfig.getEnvironmentEntry(WebConfiguration.WebEnvironmentEntry.ClientStateSavingPassword);
      if (pass != null) {
         this.guard = new ByteArrayGuard(pass);
      }

      this.compressState = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CompressViewState);
      String size = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ClientStateWriteBufferSize);
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

      this.stateFieldStart = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableViewStateIdRendering) ? STATE_FIELD_START : STATE_FIELD_START_NO_ID;
      this.stateFieldEnd = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AutoCompleteOffOnViewState) ? STATE_FIELD_AUTOCOMPLETE_END : STATE_FIELD_END;
      if (this.serialProvider == null) {
         this.serialProvider = SerializationProviderFactory.createInstance(FacesContext.getCurrentInstance().getExternalContext());
      }

   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
      STATE_FIELD_START = "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"".toCharArray();
      STATE_FIELD_START_NO_ID = "<input type=\"hidden\" name=\"javax.faces.ViewState\" value=\"".toCharArray();
      STATE_FIELD_END = "\" />".toCharArray();
      STATE_FIELD_AUTOCOMPLETE_END = "\" autocomplete=\"off\" />".toCharArray();
   }
}
