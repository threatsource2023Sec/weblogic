package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.LRUMap;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.TypedCollections;
import com.sun.faces.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class ServerSideStateHelper extends StateHelper {
   private static final Logger LOGGER;
   public static final String STATEMANAGED_SERIAL_ID_KEY;
   public static final String LOGICAL_VIEW_MAP;
   protected final Integer numberOfLogicalViews;
   protected final Integer numberOfViews;
   protected boolean generateUniqueStateIds;
   protected final SecureRandom random;

   public ServerSideStateHelper() {
      this.numberOfLogicalViews = this.getIntegerConfigValue(WebConfiguration.WebContextInitParameter.NumberOfLogicalViews);
      this.numberOfViews = this.getIntegerConfigValue(WebConfiguration.WebContextInitParameter.NumberOfViews);
      WebConfiguration webConfig = WebConfiguration.getInstance();
      this.generateUniqueStateIds = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.GenerateUniqueServerStateIds);
      if (this.generateUniqueStateIds) {
         this.random = new SecureRandom();
         this.random.nextBytes(new byte[1]);
      } else {
         this.random = null;
      }

   }

   public void writeState(FacesContext ctx, Object state, StringBuilder stateCapture) throws IOException {
      Util.notNull("context", ctx);
      UIViewRoot viewRoot = ctx.getViewRoot();
      String id;
      if (!viewRoot.isTransient()) {
         if (!ctx.getAttributes().containsKey("com.sun.faces.ViewStateValue")) {
            Util.notNull("state", state);
            Object[] stateToWrite = (Object[])((Object[])state);
            ExternalContext externalContext = ctx.getExternalContext();
            Object sessionObj = externalContext.getSession(true);
            Map sessionMap = externalContext.getSessionMap();
            synchronized(sessionObj) {
               Map logicalMap = TypedCollections.dynamicallyCastMap((Map)sessionMap.get(LOGICAL_VIEW_MAP), String.class, Map.class);
               if (logicalMap == null) {
                  logicalMap = Collections.synchronizedMap(new LRUMap(this.numberOfLogicalViews));
                  sessionMap.put(LOGICAL_VIEW_MAP, logicalMap);
               }

               Object structure = stateToWrite[0];
               Object savedState = this.handleSaveState(stateToWrite[1]);
               String idInLogicalMap = (String)RequestStateManager.get(ctx, "com.sun.faces.logicalViewMap");
               if (idInLogicalMap == null) {
                  idInLogicalMap = this.generateUniqueStateIds ? this.createRandomId() : this.createIncrementalRequestId(ctx);
               }

               String idInActualMap = null;
               if (ctx.getPartialViewContext().isPartialRequest()) {
                  idInActualMap = (String)RequestStateManager.get(ctx, "com.sun.faces.actualViewMap");
               }

               if (null == idInActualMap) {
                  idInActualMap = this.generateUniqueStateIds ? this.createRandomId() : this.createIncrementalRequestId(ctx);
               }

               Map actualMap = TypedCollections.dynamicallyCastMap((Map)logicalMap.get(idInLogicalMap), String.class, Object[].class);
               if (actualMap == null) {
                  actualMap = new LRUMap(this.numberOfViews);
                  logicalMap.put(idInLogicalMap, actualMap);
               }

               id = idInLogicalMap + ':' + idInActualMap;
               Object[] stateArray = (Object[])((Map)actualMap).get(idInActualMap);
               if (stateArray != null) {
                  stateArray[0] = structure;
                  stateArray[1] = savedState;
               } else {
                  ((Map)actualMap).put(idInActualMap, new Object[]{structure, savedState});
               }

               sessionMap.put(LOGICAL_VIEW_MAP, logicalMap);
               ctx.getAttributes().put("com.sun.faces.ViewStateValue", id);
            }
         } else {
            id = (String)ctx.getAttributes().get("com.sun.faces.ViewStateValue");
         }
      } else {
         id = "stateless";
      }

      if (stateCapture != null) {
         stateCapture.append(id);
      } else {
         ResponseWriter writer = ctx.getResponseWriter();
         writer.startElement("input", (UIComponent)null);
         writer.writeAttribute("type", "hidden", (String)null);
         writer.writeAttribute("name", RenderKitUtils.PredefinedPostbackParameter.VIEW_STATE_PARAM.getName(ctx), (String)null);
         if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableViewStateIdRendering)) {
            String viewStateId = Util.getViewStateId(ctx);
            writer.writeAttribute("id", viewStateId, (String)null);
         }

         writer.writeAttribute("value", id, (String)null);
         if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AutoCompleteOffOnViewState)) {
            writer.writeAttribute("autocomplete", "off", (String)null);
         }

         writer.endElement("input");
         this.writeClientWindowField(ctx, writer);
         this.writeRenderKitIdField(ctx, writer);
      }

   }

   public Object getState(FacesContext ctx, String viewId) {
      String compoundId = getStateParamValue(ctx);
      if (compoundId == null) {
         return null;
      } else if ("stateless".equals(compoundId)) {
         return "stateless";
      } else {
         int sep = compoundId.indexOf(58);

         assert sep != -1;

         assert sep < compoundId.length();

         String idInLogicalMap = compoundId.substring(0, sep);
         String idInActualMap = compoundId.substring(sep + 1);
         ExternalContext externalCtx = ctx.getExternalContext();
         Object sessionObj = externalCtx.getSession(false);
         if (sessionObj == null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unable to restore server side state for view ID {0} as no session is available", viewId);
            }

            return null;
         } else {
            synchronized(sessionObj) {
               Map logicalMap = (Map)externalCtx.getSessionMap().get(LOGICAL_VIEW_MAP);
               if (logicalMap != null) {
                  Map actualMap = (Map)logicalMap.get(idInLogicalMap);
                  if (actualMap != null) {
                     RequestStateManager.set(ctx, "com.sun.faces.logicalViewMap", idInLogicalMap);
                     Object[] restoredState = new Object[2];
                     Object[] state = (Object[])((Object[])actualMap.get(idInActualMap));
                     if (state != null) {
                        restoredState[0] = state[0];
                        restoredState[1] = state[1];
                        RequestStateManager.set(ctx, "com.sun.faces.actualViewMap", idInActualMap);
                        if (state.length == 2 && state[1] != null) {
                           restoredState[1] = this.handleRestoreState(state[1]);
                        }
                     }

                     return restoredState;
                  }
               }

               return null;
            }
         }
      }
   }

   protected Integer getIntegerConfigValue(WebConfiguration.WebContextInitParameter param) {
      String noOfViewsStr = this.webConfig.getOptionValue(param);
      Integer value = null;

      try {
         value = Integer.valueOf(noOfViewsStr);
      } catch (NumberFormatException var8) {
         String defaultValue = param.getDefaultValue();
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.state.server.cannot.parse.int.option", new Object[]{param.getQualifiedName(), defaultValue});
         }

         try {
            value = Integer.valueOf(defaultValue);
         } catch (NumberFormatException var7) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Unable to convert number", var7);
            }
         }
      }

      return value;
   }

   protected Object handleSaveState(Object state) {
      if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerStateDeprecated) && !this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerState)) {
         return state;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
         ObjectOutputStream oas = null;

         try {
            oas = this.serialProvider.createObjectOutputStream((OutputStream)(this.compressViewState ? new GZIPOutputStream(baos, 1024) : baos));
            oas.writeObject(state);
            oas.flush();
         } catch (Exception var11) {
            throw new FacesException(var11);
         } finally {
            if (oas != null) {
               try {
                  oas.close();
               } catch (IOException var12) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Closing stream", var12);
                  }
               }
            }

         }

         return baos.toByteArray();
      }
   }

   protected Object handleRestoreState(Object state) {
      if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerStateDeprecated) && !this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerState)) {
         return state;
      } else {
         try {
            ByteArrayInputStream bais = new ByteArrayInputStream((byte[])((byte[])state));
            Throwable var3 = null;

            Object var6;
            try {
               ObjectInputStream ois = this.serialProvider.createObjectInputStream((InputStream)(this.compressViewState ? new GZIPInputStream(bais, 1024) : bais));
               Throwable var5 = null;

               try {
                  var6 = ois.readObject();
               } catch (Throwable var31) {
                  var6 = var31;
                  var5 = var31;
                  throw var31;
               } finally {
                  if (ois != null) {
                     if (var5 != null) {
                        try {
                           ois.close();
                        } catch (Throwable var30) {
                           var5.addSuppressed(var30);
                        }
                     } else {
                        ois.close();
                     }
                  }

               }
            } catch (Throwable var33) {
               var3 = var33;
               throw var33;
            } finally {
               if (bais != null) {
                  if (var3 != null) {
                     try {
                        bais.close();
                     } catch (Throwable var29) {
                        var3.addSuppressed(var29);
                     }
                  } else {
                     bais.close();
                  }
               }

            }

            return var6;
         } catch (Exception var35) {
            throw new FacesException(var35);
         }
      }
   }

   private String createIncrementalRequestId(FacesContext ctx) {
      Map sm = ctx.getExternalContext().getSessionMap();
      AtomicInteger idgen = (AtomicInteger)sm.get(STATEMANAGED_SERIAL_ID_KEY);
      if (idgen == null) {
         idgen = new AtomicInteger(1);
      }

      sm.put(STATEMANAGED_SERIAL_ID_KEY, idgen);
      return "j_id" + idgen.getAndIncrement();
   }

   private String createRandomId() {
      return Long.valueOf(this.random.nextLong()).toString();
   }

   public boolean isStateless(FacesContext facesContext, String viewId) throws IllegalStateException {
      if (facesContext.isPostback()) {
         String compoundId = getStateParamValue(facesContext);
         return compoundId != null && "stateless".equals(compoundId);
      } else {
         throw new IllegalStateException("Cannot determine whether or not the request is stateless");
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      STATEMANAGED_SERIAL_ID_KEY = ServerSideStateHelper.class.getName() + ".SerialId";
      LOGICAL_VIEW_MAP = ServerSideStateHelper.class.getName() + ".LogicalViewMap";
   }
}
