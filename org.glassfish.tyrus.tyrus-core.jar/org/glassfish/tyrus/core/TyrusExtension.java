package org.glassfish.tyrus.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.websocket.Extension;

public class TyrusExtension implements Extension, Serializable {
   private static final transient Logger LOGGER = Logger.getLogger(TyrusExtension.class.getName());
   private final String name;
   private final ArrayList parameters;
   private static final long serialVersionUID = -3671075267907614851L;

   public TyrusExtension(String name) {
      this(name, (List)null);
   }

   public TyrusExtension(String name, List parameters) {
      if (name != null && name.length() != 0) {
         this.name = name;
         if (parameters != null) {
            this.parameters = new ArrayList(parameters);
         } else {
            this.parameters = new ArrayList();
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public String getName() {
      return this.name;
   }

   public List getParameters() {
      return Collections.unmodifiableList(this.parameters);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("TyrusExtension{");
      sb.append("name='").append(this.name).append('\'');
      sb.append(", parameters=").append(this.parameters);
      sb.append('}');
      return sb.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TyrusExtension that = (TyrusExtension)o;
         return this.name.equals(that.name) && this.parameters.equals(that.parameters);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.name.hashCode();
      result = 31 * result + this.parameters.hashCode();
      return result;
   }

   static String toString(Extension extension) {
      StringBuilder sb = new StringBuilder();
      sb.append(extension.getName());
      List extensionParameters = extension.getParameters();
      if (extensionParameters != null && !extensionParameters.isEmpty()) {
         Iterator var3 = extensionParameters.iterator();

         while(var3.hasNext()) {
            Extension.Parameter p = (Extension.Parameter)var3.next();
            sb.append("; ");
            sb.append(TyrusExtension.TyrusParameter.toString(p));
         }
      }

      return sb.toString();
   }

   public static List fromString(List s) {
      return fromHeaders(s);
   }

   public static List fromHeaders(List extensionHeaders) {
      List extensions = new ArrayList();
      if (extensionHeaders == null) {
         return extensions;
      } else {
         Iterator var2 = extensionHeaders.iterator();

         while(var2.hasNext()) {
            String singleHeader = (String)var2.next();
            if (singleHeader == null) {
               break;
            }

            char[] chars = singleHeader.toCharArray();
            int i = 0;
            ParserState next = TyrusExtension.ParserState.NAME;
            StringBuilder name = new StringBuilder();
            StringBuilder paramName = new StringBuilder();
            StringBuilder paramValue = new StringBuilder();
            List params = new ArrayList();

            do {
               label84:
               switch (next) {
                  case NAME:
                     switch (chars[i]) {
                        case ',':
                           if (name.length() > 0) {
                              extensions.add(new TyrusExtension(name.toString().trim(), params));
                              name = new StringBuilder();
                              paramName = new StringBuilder();
                              paramValue = new StringBuilder();
                              params.clear();
                           }

                           next = TyrusExtension.ParserState.NAME;
                           break label84;
                        case ';':
                           next = TyrusExtension.ParserState.PARAM_NAME;
                           break label84;
                        case '=':
                           next = TyrusExtension.ParserState.ERROR;
                           break label84;
                        default:
                           name.append(chars[i]);
                           break label84;
                     }
                  case PARAM_NAME:
                     switch (chars[i]) {
                        case ',':
                           next = TyrusExtension.ParserState.NAME;
                           params.add(new TyrusParameter(paramName.toString().trim(), (String)null));
                           paramName = new StringBuilder();
                           paramValue = new StringBuilder();
                           if (name.length() > 0) {
                              extensions.add(new TyrusExtension(name.toString().trim(), params));
                              name = new StringBuilder();
                              paramName = new StringBuilder();
                              paramValue = new StringBuilder();
                              params.clear();
                           }
                           break label84;
                        case ';':
                           next = TyrusExtension.ParserState.PARAM_NAME;
                           params.add(new TyrusParameter(paramName.toString().trim(), (String)null));
                           paramName = new StringBuilder();
                           paramValue = new StringBuilder();
                           break label84;
                        case '=':
                           next = TyrusExtension.ParserState.PARAM_VALUE;
                           break label84;
                        default:
                           paramName.append(chars[i]);
                           break label84;
                     }
                  case PARAM_VALUE:
                     switch (chars[i]) {
                        case '"':
                           if (paramValue.length() > 0) {
                              next = TyrusExtension.ParserState.ERROR;
                           } else {
                              next = TyrusExtension.ParserState.PARAM_VALUE_QUOTED;
                           }
                           break label84;
                        case ',':
                           next = TyrusExtension.ParserState.NAME;
                           params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString().trim()));
                           paramName = new StringBuilder();
                           paramValue = new StringBuilder();
                           if (name.length() > 0) {
                              extensions.add(new TyrusExtension(name.toString().trim(), params));
                              name = new StringBuilder();
                              paramName = new StringBuilder();
                              paramValue = new StringBuilder();
                              params.clear();
                           }
                           break label84;
                        case ';':
                           next = TyrusExtension.ParserState.PARAM_NAME;
                           params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString().trim()));
                           paramName = new StringBuilder();
                           paramValue = new StringBuilder();
                           break label84;
                        case '=':
                           next = TyrusExtension.ParserState.ERROR;
                           break label84;
                        default:
                           paramValue.append(chars[i]);
                           break label84;
                     }
                  case PARAM_VALUE_QUOTED:
                     switch (chars[i]) {
                        case '"':
                           next = TyrusExtension.ParserState.PARAM_VALUE_QUOTED_POST;
                           params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString()));
                           paramName = new StringBuilder();
                           paramValue = new StringBuilder();
                           break label84;
                        case '=':
                           next = TyrusExtension.ParserState.ERROR;
                           break label84;
                        case '\\':
                           next = TyrusExtension.ParserState.PARAM_VALUE_QUOTED_QP;
                           break label84;
                        default:
                           paramValue.append(chars[i]);
                           break label84;
                     }
                  case PARAM_VALUE_QUOTED_QP:
                     next = TyrusExtension.ParserState.PARAM_VALUE_QUOTED;
                     paramValue.append(chars[i]);
                     break;
                  case PARAM_VALUE_QUOTED_POST:
                     switch (chars[i]) {
                        case ',':
                           next = TyrusExtension.ParserState.NAME;
                           if (name.length() > 0) {
                              extensions.add(new TyrusExtension(name.toString().trim(), params));
                              name = new StringBuilder();
                              paramName = new StringBuilder();
                              paramValue = new StringBuilder();
                              params.clear();
                           }
                           break label84;
                        case ';':
                           next = TyrusExtension.ParserState.PARAM_NAME;
                           break label84;
                        default:
                           next = TyrusExtension.ParserState.ERROR;
                           break label84;
                     }
                  case ERROR:
                     LOGGER.fine(String.format("Error during parsing Extension: %s", name));
                     if (name.length() > 0) {
                        name = new StringBuilder();
                        paramName = new StringBuilder();
                        paramValue = new StringBuilder();
                        params.clear();
                     }

                     switch (chars[i]) {
                        case ',':
                           next = TyrusExtension.ParserState.NAME;
                           if (name.length() > 0) {
                              extensions.add(new TyrusExtension(name.toString().trim(), params));
                              name = new StringBuilder();
                              paramName = new StringBuilder();
                              paramValue = new StringBuilder();
                              params.clear();
                           }
                           break;
                        case ';':
                           next = TyrusExtension.ParserState.PARAM_NAME;
                     }
               }

               ++i;
            } while(i < chars.length);

            if (name.length() > 0 && next != TyrusExtension.ParserState.ERROR) {
               if (paramName.length() > 0) {
                  String paramValueString = paramValue.toString();
                  params.add(new TyrusParameter(paramName.toString().trim(), paramValueString.equals("") ? null : paramValueString));
               }

               extensions.add(new TyrusExtension(name.toString().trim(), params));
               params.clear();
            } else {
               LOGGER.fine(String.format("Unable to parse Extension: %s", name));
            }
         }

         return extensions;
      }
   }

   public static class TyrusParameter implements Extension.Parameter, Serializable {
      private static final long serialVersionUID = -6818457211703933087L;
      private final String name;
      private final String value;

      public TyrusParameter(String name, String value) {
         this.name = name;
         this.value = value;
      }

      public String getName() {
         return this.name;
      }

      public String getValue() {
         return this.value;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("TyrusParameter{");
         sb.append("name='").append(this.name).append('\'');
         sb.append(", value='").append(this.value).append('\'');
         sb.append('}');
         return sb.toString();
      }

      static String toString(Extension.Parameter parameter) {
         StringBuilder sb = new StringBuilder();
         sb.append(parameter.getName());
         String value = parameter.getValue();
         if (value != null) {
            sb.append('=').append(value);
         }

         return sb.toString();
      }
   }

   private static enum ParserState {
      NAME,
      PARAM_NAME,
      PARAM_VALUE,
      PARAM_VALUE_QUOTED,
      PARAM_VALUE_QUOTED_POST,
      PARAM_VALUE_QUOTED_QP,
      ERROR;
   }
}
