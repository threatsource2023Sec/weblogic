package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.el.ELUtils;
import com.sun.faces.facelets.util.DevTools;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

public class RenderKitUtils {
   private static final String XHTML_ATTR_PREFIX = "xml:";
   private static final String[] BOOLEAN_ATTRIBUTES = new String[]{"disabled", "ismap", "readonly"};
   private static final String[] XHTML_PREFIX_ATTRIBUTES = new String[]{"lang"};
   private static final int MAX_CONTENT_TYPE_PARTS = 4;
   private static final String CONTENT_TYPE_DELIMITER = ",";
   private static final String CONTENT_TYPE_SUBTYPE_DELIMITER = "/";
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   private static final String ATTRIBUTES_THAT_ARE_SET_KEY = UIComponentBase.class.getName() + ".attributesThatAreSet";
   protected static final Logger LOGGER;

   private RenderKitUtils() {
   }

   public static RenderKit getCurrentRenderKit(FacesContext context) {
      RenderKitFactory renderKitFactory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
      return renderKitFactory.getRenderKit(context, context.getViewRoot().getRenderKitId());
   }

   public static ResponseStateManager getResponseStateManager(FacesContext context, String renderKitId) throws FacesException {
      assert null != renderKitId;

      assert null != context;

      RenderKit renderKit = context.getRenderKit();
      if (renderKit == null) {
         RenderKitFactory factory = (RenderKitFactory)RequestStateManager.get(context, "com.sun.faces.renderKitImplForRequest");
         if (factory != null) {
            renderKit = factory.getRenderKit(context, renderKitId);
         } else {
            factory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
            if (factory == null) {
               throw new FacesException("Unable to locate RenderKitFactory for javax.faces.render.RenderKitFactory");
            }

            RequestStateManager.set(context, "com.sun.faces.renderKitImplForRequest", factory);
            renderKit = factory.getRenderKit(context, renderKitId);
            if (renderKit == null) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to locate renderkit instance for render-kit-id {0}.  Using {1} instead.", new String[]{renderKitId, "HTML_BASIC"});
               }

               renderKitId = "HTML_BASIC";
               UIViewRoot root = context.getViewRoot();
               if (null != root) {
                  root.setRenderKitId(renderKitId);
               }
            }

            renderKit = factory.getRenderKit(context, renderKitId);
            if (renderKit == null) {
               throw new FacesException("Unable to locate renderkit instance for render-kit-id " + renderKitId);
            }
         }
      }

      return renderKit.getResponseStateManager();
   }

   public static SelectItemsIterator getSelectItems(FacesContext context, UIComponent component) {
      Util.notNull("context", context);
      Util.notNull("component", component);
      return new SelectItemsIterator(context, component);
   }

   public static void renderPassThruAttributes(FacesContext context, ResponseWriter writer, UIComponent component, Attribute[] attributes) throws IOException {
      assert null != context;

      assert null != writer;

      assert null != component;

      Map behaviors = null;
      if (component instanceof ClientBehaviorHolder) {
         behaviors = ((ClientBehaviorHolder)component).getClientBehaviors();
      }

      if (null != behaviors && behaviors.size() > 0 && Util.componentIsDisabled(component)) {
         behaviors = null;
      }

      renderPassThruAttributes(context, writer, component, attributes, behaviors);
   }

   public static void renderPassThruAttributes(FacesContext context, ResponseWriter writer, UIComponent component, Attribute[] attributes, Map behaviors) throws IOException {
      assert null != writer;

      assert null != component;

      if (behaviors == null) {
         behaviors = Collections.emptyMap();
      }

      if (canBeOptimized(component, behaviors)) {
         List setAttributes = (List)component.getAttributes().get(ATTRIBUTES_THAT_ARE_SET_KEY);
         if (setAttributes != null) {
            renderPassThruAttributesOptimized(context, writer, component, attributes, setAttributes, behaviors);
         }
      } else {
         renderPassThruAttributesUnoptimized(context, writer, component, attributes, behaviors);
      }

   }

   public static void renderOnchange(FacesContext context, UIComponent component, boolean incExec) throws IOException {
      String handlerName = "onchange";
      Object userHandler = component.getAttributes().get("onchange");
      String behaviorEventName = "valueChange";
      if (component instanceof ClientBehaviorHolder) {
         Map behaviors = ((ClientBehaviorHolder)component).getClientBehaviors();
         if (null != behaviors && behaviors.containsKey("change")) {
            behaviorEventName = "change";
         }
      }

      Object params;
      if (!incExec) {
         params = Collections.emptyList();
      } else {
         params = new LinkedList();
         ((List)params).add(new ClientBehaviorContext.Parameter("incExec", true));
      }

      renderHandler(context, component, (Collection)params, "onchange", userHandler, behaviorEventName, (String)null, false, incExec);
   }

   public static void renderSelectOnclick(FacesContext context, UIComponent component, boolean incExec) throws IOException {
      String handlerName = "onclick";
      Object userHandler = component.getAttributes().get("onclick");
      String behaviorEventName = "valueChange";
      if (component instanceof ClientBehaviorHolder) {
         Map behaviors = ((ClientBehaviorHolder)component).getClientBehaviors();
         if (null != behaviors && behaviors.containsKey("click")) {
            behaviorEventName = "click";
         }
      }

      Object params;
      if (!incExec) {
         params = Collections.emptyList();
      } else {
         params = new LinkedList();
         ((List)params).add(new ClientBehaviorContext.Parameter("incExec", true));
      }

      renderHandler(context, component, (Collection)params, "onclick", userHandler, behaviorEventName, (String)null, false, incExec);
   }

   public static void renderOnclick(FacesContext context, UIComponent component, Collection params, String submitTarget, boolean needsSubmit) throws IOException {
      String handlerName = "onclick";
      Object userHandler = component.getAttributes().get("onclick");
      String behaviorEventName = "action";
      if (component instanceof ClientBehaviorHolder) {
         Map behaviors = ((ClientBehaviorHolder)component).getClientBehaviors();
         boolean mixed = null != behaviors && behaviors.containsKey("click") && behaviors.containsKey("action");
         if (mixed) {
            behaviorEventName = "click";
            List clickBehaviors = (List)behaviors.get("click");
            List actionBehaviors = (List)behaviors.get("action");
            clickBehaviors.addAll(actionBehaviors);
            actionBehaviors.clear();
         } else if (null != behaviors && behaviors.containsKey("click")) {
            behaviorEventName = "click";
         }
      }

      renderHandler(context, component, params, "onclick", userHandler, behaviorEventName, submitTarget, needsSubmit, false);
   }

   public static void renderFunction(FacesContext context, UIComponent component, Collection params, String submitTarget) throws IOException {
      ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, component, "action", submitTarget, params);
      AjaxBehavior behavior = (AjaxBehavior)context.getApplication().createBehavior("javax.faces.behavior.Ajax");
      mapAttributes(component, behavior, "execute", "render", "onerror", "onevent", "resetValues");
      context.getResponseWriter().append(behavior.getScript(behaviorContext));
   }

   private static void mapAttributes(UIComponent component, AjaxBehavior behavior, String... attributeNames) {
      String[] var3 = attributeNames;
      int var4 = attributeNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String attributeName = var3[var5];
         ValueExpression binding = component.getValueExpression(attributeName);
         if (binding == null) {
            Object value = component.getAttributes().get(attributeName);
            if (value != null) {
               binding = ELUtils.createValueExpression(value.toString(), value.getClass());
            }
         }

         behavior.setValueExpression(attributeName, binding);
      }

   }

   public static String prefixAttribute(String attrName, ResponseWriter writer) {
      return prefixAttribute(attrName, "application/xhtml+xml".equals(writer.getContentType()));
   }

   public static String prefixAttribute(String attrName, boolean isXhtml) {
      if (isXhtml) {
         return Arrays.binarySearch(XHTML_PREFIX_ATTRIBUTES, attrName) > -1 ? "xml:" + attrName : attrName;
      } else {
         return attrName;
      }
   }

   public static void renderXHTMLStyleBooleanAttributes(ResponseWriter writer, UIComponent component) throws IOException {
      assert writer != null;

      assert component != null;

      List excludedAttributes = null;
      renderXHTMLStyleBooleanAttributes(writer, component, (List)excludedAttributes);
   }

   public static void renderXHTMLStyleBooleanAttributes(ResponseWriter writer, UIComponent component, List excludedAttributes) throws IOException {
      assert writer != null;

      assert component != null;

      Map attrMap = component.getAttributes();
      String[] var4 = BOOLEAN_ATTRIBUTES;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String attrName = var4[var6];
         if (!isExcludedAttribute(attrName, excludedAttributes)) {
            Object val = attrMap.get(attrName);
            if (val != null && Boolean.valueOf(val.toString())) {
               writer.writeAttribute(attrName, true, attrName);
            }
         }
      }

   }

   public static String determineContentType(String accept, String serverSupportedTypes, String preferredType) {
      String contentType = null;
      if (null != accept && null != serverSupportedTypes) {
         String[][] clientContentTypes = buildTypeArrayFromString(accept);
         String[][] serverContentTypes = buildTypeArrayFromString(serverSupportedTypes);
         String[][] preferredContentType = buildTypeArrayFromString(preferredType);
         String[][] matchedInfo = findMatch(clientContentTypes, serverContentTypes, preferredContentType);
         if (matchedInfo[0][1] != null && !matchedInfo[0][2].equals("*")) {
            contentType = matchedInfo[0][1] + "/" + matchedInfo[0][2];
         }

         return contentType;
      } else {
         return contentType;
      }
   }

   public static boolean isXml(String contentType) {
      return "application/xhtml+xml".equals(contentType) || "application/xml".equals(contentType) || "text/xml".equals(contentType);
   }

   private static boolean canBeOptimized(UIComponent component, Map behaviors) {
      assert component != null;

      assert behaviors != null;

      String name = component.getClass().getName();
      if (name != null && name.startsWith("javax.faces.component.")) {
         return behaviors.size() < 2;
      } else {
         return false;
      }
   }

   private static void renderPassThruAttributesOptimized(FacesContext context, ResponseWriter writer, UIComponent component, Attribute[] knownAttributes, List setAttributes, Map behaviors) throws IOException {
      assert behaviors != null && behaviors.size() < 2;

      String behaviorEventName = getSingleBehaviorEventName(behaviors);
      boolean renderedBehavior = false;
      Collections.sort(setAttributes);
      boolean isXhtml = "application/xhtml+xml".equals(writer.getContentType());
      Map attrMap = component.getAttributes();
      Iterator var10 = setAttributes.iterator();

      while(var10.hasNext()) {
         String name = (String)var10.next();
         int index = Arrays.binarySearch(knownAttributes, Attribute.attr(name));
         if (index >= 0) {
            Object value = attrMap.get(name);
            if (value != null && shouldRenderAttribute(value)) {
               Attribute attr = knownAttributes[index];
               if (isBehaviorEventAttribute(attr, behaviorEventName)) {
                  renderHandler(context, component, (Collection)null, name, value, behaviorEventName, (String)null, false, false);
                  renderedBehavior = true;
               } else {
                  writer.writeAttribute(prefixAttribute(name, isXhtml), value, name);
               }
            }
         }
      }

      if (behaviorEventName != null && !renderedBehavior) {
         for(int i = 0; i < knownAttributes.length; ++i) {
            Attribute attr = knownAttributes[i];
            String[] events = attr.getEvents();
            if (events != null && events.length > 0 && behaviorEventName.equals(events[0])) {
               renderHandler(context, component, (Collection)null, attr.getName(), (Object)null, behaviorEventName, (String)null, false, false);
            }
         }
      }

   }

   private static void renderPassThruAttributesUnoptimized(FacesContext context, ResponseWriter writer, UIComponent component, Attribute[] knownAttributes, Map behaviors) throws IOException {
      boolean isXhtml = "application/xhtml+xml".equals(writer.getContentType());
      Map attrMap = component.getAttributes();
      Attribute[] var7 = knownAttributes;
      int var8 = knownAttributes.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Attribute attribute = var7[var9];
         String attrName = attribute.getName();
         String[] events = attribute.getEvents();
         boolean hasBehavior = events != null && events.length > 0 && behaviors.containsKey(events[0]);
         Object value = attrMap.get(attrName);
         if (value != null && shouldRenderAttribute(value) && !hasBehavior) {
            writer.writeAttribute(prefixAttribute(attrName, isXhtml), value, attrName);
         } else if (hasBehavior) {
            renderHandler(context, component, (Collection)null, attrName, value, events[0], (String)null, false, false);
         }
      }

   }

   private static boolean shouldRenderAttribute(Object attributeVal) {
      if (attributeVal instanceof String) {
         return true;
      } else if (attributeVal instanceof Boolean && Boolean.FALSE.equals(attributeVal)) {
         return false;
      } else if (attributeVal instanceof Integer && (Integer)attributeVal == Integer.MIN_VALUE) {
         return false;
      } else if (attributeVal instanceof Double && (Double)attributeVal == Double.MIN_VALUE) {
         return false;
      } else if (attributeVal instanceof Character && (Character)attributeVal == 0) {
         return false;
      } else if (attributeVal instanceof Float && (Float)attributeVal == Float.MIN_VALUE) {
         return false;
      } else if (attributeVal instanceof Short && (Short)attributeVal == Short.MIN_VALUE) {
         return false;
      } else if (attributeVal instanceof Byte && (Byte)attributeVal == -128) {
         return false;
      } else {
         return !(attributeVal instanceof Long) || (Long)attributeVal != Long.MIN_VALUE;
      }
   }

   private static boolean isExcludedAttribute(String attributeName, List excludedAttributes) {
      if (null == excludedAttributes) {
         return false;
      } else {
         return excludedAttributes.contains(attributeName);
      }
   }

   private static String[][] buildTypeArrayFromString(String accept) {
      if (accept != null && accept.length() != 0) {
         String level = null;
         String quality = null;
         Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
         String[] types = Util.split(appMap, accept, ",");
         String[][] arrayAccept = new String[types.length][4];
         int index = -1;

         for(int i = 0; i < types.length; ++i) {
            String token = types[i].trim();
            ++index;
            StringBuilder typeSubType;
            String[] typeSubTypeParts;
            if (token.contains(";")) {
               typeSubTypeParts = Util.split(appMap, token, ";");
               typeSubType = new StringBuilder(typeSubTypeParts[0].trim());

               for(int j = 1; j < typeSubTypeParts.length; ++j) {
                  quality = "not set";
                  token = typeSubTypeParts[j].trim();
                  String[] levelParts;
                  if (token.contains("level")) {
                     typeSubType.append(';').append(token);
                     levelParts = Util.split(appMap, token, "=");
                     level = levelParts[0].trim();
                     if (level.equalsIgnoreCase("level")) {
                        level = levelParts[1].trim();
                     }
                  } else {
                     levelParts = Util.split(appMap, token, "=");
                     quality = levelParts[0].trim();
                     if (quality.equalsIgnoreCase("q")) {
                        quality = levelParts[1].trim();
                        break;
                     }

                     quality = "not set";
                  }
               }
            } else {
               typeSubType = new StringBuilder(token);
               quality = "not set";
            }

            String type;
            String subtype;
            if (typeSubType.indexOf("/") >= 0) {
               typeSubTypeParts = Util.split(appMap, typeSubType.toString(), "/");
               if (typeSubTypeParts.length == 1) {
                  type = typeSubTypeParts[0].trim();
                  subtype = "*";
               } else if (typeSubTypeParts.length == 0) {
                  type = typeSubType.toString();
                  subtype = "";
               } else {
                  type = typeSubTypeParts[0].trim();
                  subtype = typeSubTypeParts[1].trim();
               }
            } else {
               type = typeSubType.toString();
               subtype = "";
            }

            if ("not set".equals(quality)) {
               if (type.equals("*") && subtype.equals("*")) {
                  quality = "0.01";
               } else if (!type.equals("*") && subtype.equals("*")) {
                  quality = "0.02";
               } else if (type.equals("*") && subtype.length() == 0) {
                  quality = "0.01";
               } else {
                  quality = "1";
               }
            }

            arrayAccept[index][0] = quality;
            arrayAccept[index][1] = type;
            arrayAccept[index][2] = subtype;
            arrayAccept[index][3] = level;
         }

         return arrayAccept;
      } else {
         return new String[0][0];
      }
   }

   private static String[][] findMatch(String[][] clientContentTypes, String[][] serverSupportedContentTypes, String[][] preferredContentType) {
      List resultList = new ArrayList(serverSupportedContentTypes.length);
      double highestQFactor = 0.0;
      int idx = 0;
      int sidx = 0;

      int cidx;
      for(int slen = serverSupportedContentTypes.length; sidx < slen; ++sidx) {
         String serverType = serverSupportedContentTypes[sidx][1];
         if (serverType != null) {
            cidx = 0;

            for(int clen = clientContentTypes.length; cidx < clen; ++cidx) {
               String browserType = clientContentTypes[cidx][1];
               if (browserType != null && (browserType.equalsIgnoreCase(serverType) || browserType.equals("*")) && (clientContentTypes[cidx][2].equalsIgnoreCase(serverSupportedContentTypes[sidx][2]) || clientContentTypes[cidx][2].equals("*"))) {
                  double cLevel = 0.0;
                  double sLevel = 0.0;
                  if (clientContentTypes[cidx][3] != null) {
                     cLevel = Double.parseDouble(clientContentTypes[cidx][3]) * 0.1;
                  }

                  if (serverSupportedContentTypes[sidx][3] != null) {
                     sLevel = Double.parseDouble(serverSupportedContentTypes[sidx][3]) * 0.1;
                  }

                  double cQfactor = Double.parseDouble(clientContentTypes[cidx][0]) + cLevel;
                  double sQfactor = Double.parseDouble(serverSupportedContentTypes[sidx][0]) + sLevel;
                  double resultQuality = cQfactor * sQfactor;
                  String[] curResult = new String[4];
                  resultList.add(curResult);
                  curResult[0] = String.valueOf(resultQuality);
                  if (clientContentTypes[cidx][2].equals("*")) {
                     curResult[1] = clientContentTypes[cidx][1];
                     curResult[2] = clientContentTypes[cidx][2];
                  } else {
                     curResult[1] = serverSupportedContentTypes[sidx][1];
                     curResult[2] = serverSupportedContentTypes[sidx][2];
                     curResult[3] = serverSupportedContentTypes[sidx][3];
                  }

                  if (resultQuality > highestQFactor) {
                     idx = resultList.size() - 1;
                     highestQFactor = resultQuality;
                  }
               }
            }
         }
      }

      String[][] match = new String[1][3];
      if (preferredContentType.length != 0 && preferredContentType[0][0] != null) {
         BigDecimal highestQual = BigDecimal.valueOf(highestQFactor);
         int i = 0;

         for(cidx = resultList.size(); i < cidx; ++i) {
            String[] result = (String[])resultList.get(i);
            if (BigDecimal.valueOf(Double.parseDouble(result[0])).compareTo(highestQual) == 0 && result[1].equals(preferredContentType[0][1]) && result[2].equals(preferredContentType[0][2])) {
               match[0][0] = result[0];
               match[0][1] = result[1];
               match[0][2] = result[2];
               return match;
            }
         }
      }

      if (!resultList.isEmpty()) {
         String[] fallBack = (String[])resultList.get(idx);
         match[0][0] = fallBack[0];
         match[0][1] = fallBack[1];
         match[0][2] = fallBack[2];
      }

      return match;
   }

   public static String createValidECMAIdentifier(String origIdentifier) {
      return origIdentifier.replace("-", "$_");
   }

   private static UIComponent createJsfJs() {
      UIOutput output = new UIOutput();
      output.setRendererType("javax.faces.resource.Script");
      output.getAttributes().put("name", "jsf.js");
      output.getAttributes().put("library", "javax.faces");
      return output;
   }

   public static void installJsfJsIfNecessary(FacesContext context) {
      if (!isJsfJsInstalled(context)) {
         ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
         if (!resourceHandler.isResourceRendered(context, "jsf.js", "javax.faces")) {
            context.getViewRoot().addComponentResource(context, createJsfJs(), "head");
         }
      }
   }

   public static void renderJsfJsIfNecessary(FacesContext context) throws IOException {
      if (!isJsfJsInstalled(context)) {
         ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
         if (!resourceHandler.isResourceRendered(context, "jsf.js", "javax.faces")) {
            createJsfJs().encodeAll(context);
            resourceHandler.markResourceRendered(context, "jsf.js", "javax.faces");
         }
      }
   }

   public static boolean isJsfJsInstalled(FacesContext context) {
      if (RequestStateManager.containsKey(context, "com.sun.faces.SCRIPT_STATE")) {
         return true;
      } else {
         UIViewRoot viewRoot = context.getViewRoot();
         Iterator var2 = viewRoot.getComponentResources(context).iterator();

         Object name;
         Object library;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            UIComponent resource = (UIComponent)var2.next();
            name = resource.getAttributes().get("name");
            library = resource.getAttributes().get("library");
         } while(!"jsf.js".equals(name) || !"javax.faces".equals(library));

         RequestStateManager.set(context, "com.sun.faces.SCRIPT_STATE", true);
         return true;
      }
   }

   public static void renderUnhandledMessages(FacesContext ctx) {
      if (ctx.isProjectStage(ProjectStage.Development)) {
         Application app = ctx.getApplication();
         HtmlMessages messages = (HtmlMessages)app.createComponent("javax.faces.HtmlMessages");
         messages.setId("javax_faces_developmentstage_messages");
         Renderer messagesRenderer = ctx.getRenderKit().getRenderer("javax.faces.Messages", "javax.faces.Messages");
         messages.setErrorStyle("Color: red");
         messages.setWarnStyle("Color: orange");
         messages.setInfoStyle("Color: blue");
         messages.setFatalStyle("Color: red");
         messages.setTooltip(true);
         messages.setTitle("Project Stage[Development]: Unhandled Messages");
         messages.setRedisplay(false);

         try {
            messagesRenderer.encodeBegin(ctx, messages);
            messagesRenderer.encodeEnd(ctx, messages);
         } catch (IOException var7) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var7.toString(), var7);
            }
         }
      } else {
         Iterator clientIds = ctx.getClientIdsWithMessages();
         int messageCount = 0;
         if (clientIds.hasNext()) {
            StringBuilder builder = new StringBuilder();

            while(clientIds.hasNext()) {
               String clientId = (String)clientIds.next();
               Iterator messages = ctx.getMessages(clientId);

               while(messages.hasNext()) {
                  FacesMessage message = (FacesMessage)messages.next();
                  if (!message.isRendered()) {
                     ++messageCount;
                     builder.append("\n");
                     builder.append("sourceId=").append(clientId);
                     builder.append("[severity=(").append(message.getSeverity());
                     builder.append("), summary=(").append(message.getSummary());
                     builder.append("), detail=(").append(message.getDetail()).append(")]");
                  }
               }
            }

            if (messageCount > 0 && LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "jsf.non_displayed_message", builder.toString());
            }
         }
      }

   }

   public static void renderHtmlErrorPage(FacesContext ctx, FacesException fe) {
      ExternalContext extContext = ctx.getExternalContext();
      if (!extContext.isResponseCommitted()) {
         extContext.setResponseContentType("text/html; charset=UTF-8");

         try {
            Writer w = extContext.getResponseOutputWriter();
            if (ctx.isProjectStage(ProjectStage.Development)) {
               DevTools.debugHtml(w, ctx, fe.getCause());
            } else {
               w.write("Please see your server log for the actual error");
            }

            w.flush();
         } catch (IOException var4) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unable to generate Facelets error page.", var4);
            }
         }

         ctx.responseComplete();
      } else {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.facelets.error.page.response.committed");
         }

         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, fe.toString(), fe);
         }
      }

   }

   public static boolean isPartialOrBehaviorAction(FacesContext context, String clientId) {
      if (clientId != null && clientId.length() != 0) {
         String source = RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_SOURCE_PARAM.getValue(context);
         if (!clientId.equals(source)) {
            return false;
         } else {
            String behaviorEvent = RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_EVENT_PARAM.getValue(context);
            if (null != behaviorEvent) {
               return "action".equals(behaviorEvent);
            } else {
               String partialEvent = RenderKitUtils.PredefinedPostbackParameter.PARTIAL_EVENT_PARAM.getValue(context);
               return "click".equals(partialEvent);
            }
         }
      } else {
         return false;
      }
   }

   public static String getFormClientId(UIComponent component, FacesContext context) {
      UIForm form = getForm(component, context);
      return form != null ? form.getClientId(context) : null;
   }

   public static UIForm getForm(UIComponent component, FacesContext context) {
      UIComponent parent;
      for(parent = component.getParent(); parent != null && !(parent instanceof UIForm); parent = parent.getParent()) {
      }

      UIForm form = (UIForm)parent;
      return form != null ? form : null;
   }

   public static String getImageSource(FacesContext context, UIComponent component, String attrName) {
      String resName = (String)component.getAttributes().get("name");
      ResourceHandler handler = context.getApplication().getResourceHandler();
      String value;
      WebConfiguration webConfig;
      String msg;
      if (resName != null) {
         value = (String)component.getAttributes().get("library");
         webConfig = WebConfiguration.getInstance();
         if (value == null && resName.startsWith(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory))) {
            if (context.isProjectStage(ProjectStage.Development)) {
               msg = "Illegal path, direct contract references are not allowed: " + resName;
               context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            }

            return "RES_NOT_FOUND";
         } else {
            Resource res = handler.createResource(resName, value);
            String msg;
            if (res == null) {
               if (context.isProjectStage(ProjectStage.Development)) {
                  msg = "Unable to find resource " + (value == null ? "" : value + ", ") + resName;
                  context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
               }

               return "RES_NOT_FOUND";
            } else {
               msg = res.getRequestPath();
               return context.getExternalContext().encodeResourceURL(msg);
            }
         }
      } else {
         value = (String)component.getAttributes().get(attrName);
         if (value != null && value.length() != 0) {
            webConfig = WebConfiguration.getInstance();
            if (value.startsWith(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory))) {
               if (context.isProjectStage(ProjectStage.Development)) {
                  msg = "Illegal path, direct contract references are not allowed: " + value;
                  context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
               }

               return "RES_NOT_FOUND";
            } else if (handler.isResourceURL(value)) {
               return value;
            } else {
               value = context.getApplication().getViewHandler().getResourceURL(context, value);
               return context.getExternalContext().encodeResourceURL(value);
            }
         } else {
            return "";
         }
      }
   }

   public static String getParameterName(FacesContext context, String name) {
      return Util.getNamingContainerPrefix(context) + name;
   }

   private static void appendScriptToChain(StringBuilder builder, String script) {
      if (script != null && script.length() != 0) {
         if (builder.length() == 0) {
            builder.append("jsf.util.chain(this,event,");
         }

         if (builder.charAt(builder.length() - 1) != ',') {
            builder.append(',');
         }

         appendQuotedValue(builder, script);
      }
   }

   public static void appendProperty(StringBuilder builder, String name, Object value) {
      appendProperty(builder, name, value, true);
   }

   public static void appendProperty(StringBuilder builder, String name, Object value, boolean quoteValue) {
      if (null != name && name.length() != 0) {
         char lastChar = builder.charAt(builder.length() - 1);
         if (lastChar != ',' && lastChar != '{') {
            builder.append(',');
         }

         appendQuotedValue(builder, name);
         builder.append(":");
         if (value == null) {
            builder.append("''");
         } else if (quoteValue) {
            appendQuotedValue(builder, value.toString());
         } else {
            builder.append(value.toString());
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public static void appendQuotedValue(StringBuilder builder, String script) {
      builder.append("'");
      int length = script.length();

      for(int i = 0; i < length; ++i) {
         char c = script.charAt(i);
         if (c == '\'' || c == '\\') {
            builder.append('\\');
         }

         builder.append(c);
      }

      builder.append("'");
   }

   private static boolean appendBehaviorsToChain(StringBuilder builder, FacesContext context, UIComponent component, List behaviors, String behaviorEventName, Collection params) {
      if (behaviors != null && !behaviors.isEmpty()) {
         ClientBehaviorContext bContext = createClientBehaviorContext(context, component, behaviorEventName, params);
         boolean submitting = false;
         Iterator var8 = behaviors.iterator();

         while(var8.hasNext()) {
            ClientBehavior behavior = (ClientBehavior)var8.next();
            String script = behavior.getScript(bContext);
            if (script != null && script.length() > 0) {
               appendScriptToChain(builder, script);
               if (isSubmitting(behavior)) {
                  submitting = true;
               }
            }
         }

         return submitting;
      } else {
         return false;
      }
   }

   private static String getSingleBehaviorEventName(Map behaviors) {
      assert behaviors != null;

      int size = behaviors.size();
      if (size == 0) {
         return null;
      } else {
         assert size == 1;

         Iterator keys = behaviors.keySet().iterator();

         assert keys.hasNext();

         return (String)keys.next();
      }
   }

   private static boolean isBehaviorEventAttribute(Attribute attr, String behaviorEventName) {
      String[] events = attr.getEvents();
      return behaviorEventName != null && events != null && events.length > 0 && behaviorEventName.equals(events[0]);
   }

   private static String getNonEmptyUserHandler(Object handlerObject) {
      String handler = null;
      if (null != handlerObject) {
         handler = handlerObject.toString();
         handler = handler.trim();
         if (handler.length() == 0) {
            handler = null;
         }
      }

      return handler;
   }

   private static List getClientBehaviors(UIComponent component, String behaviorEventName) {
      if (component instanceof ClientBehaviorHolder) {
         ClientBehaviorHolder bHolder = (ClientBehaviorHolder)component;
         Map behaviors = bHolder.getClientBehaviors();
         if (null != behaviors) {
            return (List)behaviors.get(behaviorEventName);
         }
      }

      return null;
   }

   private static String getSubmitHandler(FacesContext context, UIComponent component, Collection params, String submitTarget, boolean preventDefault) {
      StringBuilder builder = new StringBuilder(256);
      String formClientId = getFormClientId(component, context);
      String componentClientId = component.getClientId(context);
      builder.append("mojarra.jsfcljs(document.getElementById('");
      builder.append(formClientId);
      builder.append("'),{");
      appendProperty(builder, componentClientId, componentClientId);
      if (null != params && !params.isEmpty()) {
         Iterator var8 = params.iterator();

         while(var8.hasNext()) {
            ClientBehaviorContext.Parameter param = (ClientBehaviorContext.Parameter)var8.next();
            appendProperty(builder, getParameterName(context, param.getName()), param.getValue());
         }
      }

      builder.append("},'");
      if (submitTarget != null) {
         builder.append(submitTarget);
      }

      builder.append("')");
      if (preventDefault) {
         builder.append(";return false");
      }

      return builder.toString();
   }

   private static String getChainedHandler(FacesContext context, UIComponent component, List behaviors, Collection params, String behaviorEventName, String userHandler, String submitTarget, boolean needsSubmit) {
      StringBuilder builder = new StringBuilder(100);
      appendScriptToChain(builder, userHandler);
      boolean submitting = appendBehaviorsToChain(builder, context, component, behaviors, behaviorEventName, params);
      boolean hasParams = null != params && !params.isEmpty();
      if (!submitting && (hasParams || needsSubmit)) {
         String submitHandler = getSubmitHandler(context, component, params, submitTarget, false);
         appendScriptToChain(builder, submitHandler);
         submitting = true;
      }

      if (builder.length() == 0) {
         return null;
      } else {
         builder.append(")");
         if (submitting && ("action".equals(behaviorEventName) || "click".equals(behaviorEventName))) {
            builder.append(";return false");
         }

         return builder.toString();
      }
   }

   private static String getSingleBehaviorHandler(FacesContext context, UIComponent component, ClientBehavior behavior, Collection params, String behaviorEventName, String submitTarget, boolean needsSubmit) {
      ClientBehaviorContext bContext = createClientBehaviorContext(context, component, behaviorEventName, params);
      String script = behavior.getScript(bContext);
      boolean preventDefault = (needsSubmit || isSubmitting(behavior)) && (component instanceof ActionSource || component instanceof ActionSource2);
      if (script == null) {
         if (needsSubmit) {
            script = getSubmitHandler(context, component, params, submitTarget, preventDefault);
         }
      } else if (preventDefault) {
         script = script + ";return false";
      }

      return script;
   }

   private static ClientBehaviorContext createClientBehaviorContext(FacesContext context, UIComponent component, String behaviorEventName, Collection params) {
      return ClientBehaviorContext.createClientBehaviorContext(context, component, behaviorEventName, (String)null, params);
   }

   private static boolean isSubmitting(ClientBehavior behavior) {
      return behavior.getHints().contains(ClientBehaviorHint.SUBMITTING);
   }

   private static void renderHandler(FacesContext context, UIComponent component, Collection params, String handlerName, Object handlerValue, String behaviorEventName, String submitTarget, boolean needsSubmit, boolean includeExec) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      String userHandler = getNonEmptyUserHandler(handlerValue);
      List behaviors = getClientBehaviors(component, behaviorEventName);
      if (null != behaviors && behaviors.size() > 0 && Util.componentIsDisabled(component)) {
         behaviors = null;
      }

      if (params == null) {
         params = Collections.emptyList();
      }

      String handler = null;
      switch (getHandlerType(behaviors, (Collection)params, userHandler, needsSubmit, includeExec)) {
         case USER_HANDLER_ONLY:
            handler = userHandler;
            break;
         case SINGLE_BEHAVIOR_ONLY:
            handler = getSingleBehaviorHandler(context, component, (ClientBehavior)behaviors.get(0), (Collection)params, behaviorEventName, submitTarget, needsSubmit);
            break;
         case SUBMIT_ONLY:
            handler = getSubmitHandler(context, component, (Collection)params, submitTarget, true);
            break;
         case CHAIN:
            handler = getChainedHandler(context, component, behaviors, (Collection)params, behaviorEventName, userHandler, submitTarget, needsSubmit);
            break;
         default:
            assert false;
      }

      writer.writeAttribute(handlerName, handler, (String)null);
   }

   private static HandlerType getHandlerType(List behaviors, Collection params, String userHandler, boolean needsSubmit, boolean includeExec) {
      if (behaviors != null && !behaviors.isEmpty()) {
         if (behaviors.size() == 1 && userHandler == null) {
            ClientBehavior behavior = (ClientBehavior)behaviors.get(0);
            if (isSubmitting(behavior) || params.isEmpty() && !needsSubmit) {
               return RenderKitUtils.HandlerType.SINGLE_BEHAVIOR_ONLY;
            }
         }

         return RenderKitUtils.HandlerType.CHAIN;
      } else if ((!params.isEmpty() || needsSubmit) && !includeExec) {
         return userHandler == null ? RenderKitUtils.HandlerType.SUBMIT_ONLY : RenderKitUtils.HandlerType.CHAIN;
      } else {
         return RenderKitUtils.HandlerType.USER_HANDLER_ONLY;
      }
   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
   }

   private static enum HandlerType {
      USER_HANDLER_ONLY,
      SINGLE_BEHAVIOR_ONLY,
      SUBMIT_ONLY,
      CHAIN;
   }

   public static enum PredefinedPostbackParameter {
      VIEW_STATE_PARAM("javax.faces.ViewState"),
      CLIENT_WINDOW_PARAM("javax.faces.ClientWindow"),
      RENDER_KIT_ID_PARAM("javax.faces.RenderKitId"),
      BEHAVIOR_SOURCE_PARAM("javax.faces.source"),
      BEHAVIOR_EVENT_PARAM("javax.faces.behavior.event"),
      PARTIAL_EVENT_PARAM("javax.faces.partial.event"),
      PARTIAL_EXECUTE_PARAM("javax.faces.partial.execute"),
      PARTIAL_RENDER_PARAM("javax.faces.partial.render"),
      PARTIAL_RESET_VALUES_PARAM("javax.faces.partial.resetValues");

      private String name;

      private PredefinedPostbackParameter(String name) {
         this.name = name;
      }

      public String getValue(FacesContext context) {
         return (String)context.getExternalContext().getRequestParameterMap().get(this.getName(context));
      }

      public String getName(FacesContext context) {
         return RenderKitUtils.getParameterName(context, this.name);
      }
   }
}
