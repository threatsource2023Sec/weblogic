package com.sun.faces.renderkit;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

public class RenderKitUtils {
   private static final String XHTML_ATTR_PREFIX = "xml:";
   private static final String[] BOOLEAN_ATTRIBUTES = new String[]{"disabled", "ismap", "readonly"};
   private static final String[] XHTML_PREFIX_ATTRIBUTES = new String[]{"lang"};
   private static final int MAX_CONTENT_TYPE_PARTS = 4;
   private static final String CONTENT_TYPE_DELIMITER = ",";
   private static final String CONTENT_TYPE_SUBTYPE_DELIMITER = "/";
   private static final String SUN_JSF_JS = "com.sun.faces.sunJsfJs";
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
               throw new IllegalStateException();
            }

            RequestStateManager.set(context, "com.sun.faces.renderKitImplForRequest", factory);
            renderKit = factory.getRenderKit(context, renderKitId);
         }
      }

      return renderKit.getResponseStateManager();
   }

   public static List getSelectItems(FacesContext context, UIComponent component) {
      if (context == null) {
         throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context"));
      } else {
         ArrayList list = new ArrayList();
         Iterator i$ = component.getChildren().iterator();

         while(true) {
            while(i$.hasNext()) {
               UIComponent kid = (UIComponent)i$.next();
               if (kid instanceof UISelectItem) {
                  UISelectItem item = (UISelectItem)kid;
                  Object value = item.getValue();
                  if (value == null) {
                     list.add(new SelectItem(item.getItemValue(), item.getItemLabel(), item.getItemDescription(), item.isItemDisabled(), item.isItemEscaped()));
                  } else {
                     if (!(value instanceof SelectItem)) {
                        throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.OPTION_NOT_SELECT_ITEM", component.getId(), value.getClass().getName()));
                     }

                     list.add((SelectItem)value);
                  }
               } else if (kid instanceof UISelectItems) {
                  Object value = ((UISelectItems)kid).getValue();
                  if (value instanceof SelectItem) {
                     list.add((SelectItem)value);
                  } else if (value instanceof SelectItem[]) {
                     SelectItem[] items = (SelectItem[])((SelectItem[])value);
                     SelectItem[] arr$ = items;
                     int len$ = items.length;

                     for(int i$ = 0; i$ < len$; ++i$) {
                        SelectItem item = arr$[i$];
                        list.add(item);
                     }
                  } else if (value instanceof Collection) {
                     Iterator i$ = ((Collection)value).iterator();

                     while(i$.hasNext()) {
                        Object element = i$.next();
                        if (!SelectItem.class.isInstance(element)) {
                           throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.OPTION_NOT_SELECT_ITEM", component.getId(), value.getClass().getName()));
                        }

                        list.add((SelectItem)element);
                     }
                  } else {
                     if (!(value instanceof Map)) {
                        throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.CHILD_NOT_OF_EXPECTED_TYPE", "UISelectItem/UISelectItems", component.getFamily(), component.getId(), value != null ? value.getClass().getName() : "null"));
                     }

                     Map optionMap = (Map)value;
                     Iterator i$ = optionMap.entrySet().iterator();

                     while(i$.hasNext()) {
                        Object o = i$.next();
                        Map.Entry entry = (Map.Entry)o;
                        Object key = entry.getKey();
                        Object val = entry.getValue();
                        if (key != null && val != null) {
                           list.add(new SelectItem(val, key.toString()));
                        }
                     }
                  }
               }
            }

            return list;
         }
      }
   }

   public static void renderPassThruAttributes(ResponseWriter writer, UIComponent component, String[] attributes) throws IOException {
      assert null != writer;

      assert null != component;

      Map attrMap = component.getAttributes();
      if (canBeOptimized(component)) {
         List setAttributes = (List)component.getAttributes().get(ATTRIBUTES_THAT_ARE_SET_KEY);
         if (setAttributes != null) {
            renderPassThruAttributesOptimized(writer, component, attributes, setAttributes);
         }
      } else {
         boolean isXhtml = "application/xhtml+xml".equals(writer.getContentType());
         String[] arr$ = attributes;
         int len$ = attributes.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String attrName = arr$[i$];
            Object value = attrMap.get(attrName);
            if (value != null && shouldRenderAttribute(value)) {
               writer.writeAttribute(prefixAttribute(attrName, isXhtml), value, attrName);
            }
         }
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

      Map attrMap = component.getAttributes();
      String[] arr$ = BOOLEAN_ATTRIBUTES;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String attrName = arr$[i$];
         Object val = attrMap.get(attrName);
         if (val != null && Boolean.valueOf(val.toString())) {
            writer.writeAttribute(attrName, true, attrName);
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

   private static boolean canBeOptimized(UIComponent component) {
      String name = component.getClass().getName();
      return name != null && name.startsWith("javax.faces.component.");
   }

   private static void renderPassThruAttributesOptimized(ResponseWriter writer, UIComponent component, String[] knownAttributes, List setAttributes) throws IOException {
      String[] attributes = (String[])setAttributes.toArray(new String[setAttributes.size()]);
      Arrays.sort(attributes);
      boolean isXhtml = "application/xhtml+xml".equals(writer.getContentType());
      Map attrMap = component.getAttributes();
      String[] arr$ = attributes;
      int len$ = attributes.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String name = arr$[i$];
         if (Arrays.binarySearch(knownAttributes, name) >= 0) {
            Object value = attrMap.get(name);
            if (value != null && shouldRenderAttribute(value)) {
               writer.writeAttribute(prefixAttribute(name, isXhtml), value, name);
            }
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

   private static String[][] buildTypeArrayFromString(String accept) {
      if (accept != null && accept.length() != 0) {
         String level = null;
         String quality = null;
         String[] types = Util.split(accept, ",");
         String[][] arrayAccept = new String[types.length][4];
         int index = -1;

         for(int i = 0; i < types.length; ++i) {
            String token = types[i].trim();
            ++index;
            StringBuilder typeSubType;
            String[] typeSubTypeParts;
            if (token.contains(";")) {
               typeSubTypeParts = Util.split(token, ";");
               typeSubType = new StringBuilder(typeSubTypeParts[0].trim());

               for(int j = 1; j < typeSubTypeParts.length; ++j) {
                  quality = "not set";
                  token = typeSubTypeParts[j].trim();
                  String[] levelParts;
                  if (token.contains("level")) {
                     typeSubType.append(';').append(token);
                     levelParts = Util.split(token, "=");
                     level = levelParts[0].trim();
                     if (level.equalsIgnoreCase("level")) {
                        level = levelParts[1].trim();
                     }
                  } else {
                     levelParts = Util.split(token, "=");
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
               typeSubTypeParts = Util.split(typeSubType.toString(), "/");
               if (typeSubTypeParts.length == 1) {
                  type = typeSubTypeParts[0].trim();
                  subtype = "*";
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

   public static void renderFormInitScript(ResponseWriter writer, FacesContext context) throws IOException {
      WebConfiguration webConfig = WebConfiguration.getInstance(context.getExternalContext());
      if (webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ExternalizeJavaScript)) {
         String mapping = Util.getFacesMapping(context);
         String uri;
         if (mapping != null && Util.isPrefixMapped(mapping)) {
            uri = mapping + '/' + "com_sun_faces_sunjsf.js";
         } else {
            uri = "/com_sun_faces_sunjsf.js" + mapping;
         }

         writer.write(10);
         writer.startElement("script", (UIComponent)null);
         writer.writeAttribute("type", "text/javascript", (String)null);
         writer.writeAttribute("src", context.getExternalContext().getRequestContextPath() + uri, (String)null);
         writer.endElement("script");
         writer.write("\n");
      } else {
         writer.write(10);
         writer.startElement("script", (UIComponent)null);
         writer.writeAttribute("type", "text/javascript", (String)null);
         writer.writeAttribute("language", "Javascript", (String)null);
         writeSunJS(context, writer);
         writer.endElement("script");
         writer.write("\n");
      }

   }

   public static String getCommandLinkOnClickScript(String formClientId, String commandClientId, String target, HtmlBasicRenderer.Param[] params) {
      StringBuilder sb = new StringBuilder(256);
      sb.append("if(typeof jsfcljs == 'function'){jsfcljs(document.getElementById('");
      sb.append(formClientId);
      sb.append("'),{'");
      sb.append(commandClientId).append("':'").append(commandClientId);
      HtmlBasicRenderer.Param[] arr$ = params;
      int len$ = params.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         HtmlBasicRenderer.Param param = arr$[i$];
         String pn = param.name;
         if (pn != null && pn.length() != 0) {
            String pv = param.value;
            sb.append("','");
            sb.append(pn.replace("'", "\\'"));
            sb.append("':'");
            if (pv != null && pv.length() != 0) {
               sb.append(pv.replace("'", "\\'"));
            }
         }
      }

      sb.append("'},'");
      sb.append(target);
      sb.append("');}return false");
      return sb.toString();
   }

   public static char[] compressJS(String JSString) {
      BufferedReader reader = new BufferedReader(new StringReader(JSString));
      StringWriter writer = new StringWriter(1024);

      try {
         for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            line = line.trim();
            writer.write(line);
         }

         return writer.toString().toCharArray();
      } catch (IOException var4) {
         return null;
      }
   }

   public static void writeSunJS(FacesContext context, Writer writer) throws IOException {
      writer.write((char[])((char[])context.getExternalContext().getApplicationMap().get("com.sun.faces.sunJsfJs")));
   }

   public static synchronized void loadSunJsfJs(ExternalContext extContext) {
      Map appMap = extContext.getApplicationMap();
      BufferedReader reader = null;

      try {
         URL url = RenderKitUtils.class.getClassLoader().getResource("com/sun/faces/sunjsf.js");
         if (url != null) {
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            InputStream input = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder builder = new StringBuilder(128);

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
               String temp = line.trim();
               if (temp.length() != 0 && !temp.startsWith("/*") && !temp.startsWith("*") && !temp.startsWith("*/") && !temp.startsWith("//")) {
                  builder.append(line).append('\n');
               }
            }

            builder.deleteCharAt(builder.length() - 1);
            char[] sunJsfJs;
            if (WebConfiguration.getInstance(extContext).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CompressJavaScript)) {
               sunJsfJs = compressJS(builder.toString());
            } else {
               sunJsfJs = builder.toString().toCharArray();
            }

            appMap.put("com.sun.faces.sunJsfJs", sunJsfJs);
            return;
         }

         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.severe("jsf.renderkit.util.cannot_load_js");
         }
      } catch (IOException var19) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "jsf.renderkit.util.cannot_load_js", var19);
         }

         return;
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException var18) {
            }
         }

      }

   }

   static {
      LOGGER = FacesLogger.RENDERKIT.getLogger();
   }
}
