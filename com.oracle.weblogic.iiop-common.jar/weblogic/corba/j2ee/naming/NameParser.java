package weblogic.corba.j2ee.naming;

import java.util.HashMap;
import java.util.Properties;
import javax.naming.CompoundName;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;

public final class NameParser implements javax.naming.NameParser {
   public static final String IIOP_PROTOCOL = "iiop";
   public static final String TGIOP_PROTOCOL = "tgiop";
   public static final String COMPLEX_PROTOCOL = "complex";
   public static final String IIOPS_PROTOCOL = "iiops";
   public static final String HTTP_PROTOCOL = "http";
   public static final String HTTPS_PROTOCOL = "https";
   public static final String TGIOP_PREFIX = "tgiop:";
   public static final String IIOP_PREFIX = "iiop:";
   public static final String T3_PREFIX = "t3:";
   public static final String T3S_PREFIX = "t3s:";
   public static final String IOR_PREFIX = "IOR:";
   public static final String IIOPS_PREFIX = "iiops:";
   public static final String IIOPLOC_PREFIX = "iioploc:";
   public static final String HTTP_PREFIX = "http:";
   public static final String HTTPS_PREFIX = "https:";
   public static final String CORBALOC_PREFIX = "corbaloc:";
   public static final String IIOPNAME_PREFIX = "iiopname:";
   public static final String CORBANAME_PREFIX = "corbaname:";
   public static final String NAME_SERVICE = "NameService";
   public static final boolean DEBUG = false;
   private static HashMap protocolMap = new HashMap();
   private static HashMap clientProtocolMap;
   public static final String CORBALOC_RIR_PREFIX = "corbaloc:rir:";
   public static final String CORBALOC_TGIOP_PREFIX = "corbaloc:tgiop:";
   public static final String CORBALOC_IIOP_PREFIX = "corbaloc:iiop:";
   public static final String CORBALOC_HTTP_PREFIX = "corbaloc:http:";
   public static final String CORBALOC_HTTPS_PREFIX = "corbaloc:https:";
   public static final String CORBALOC_IIOP_PREFIX2 = "corbaloc::";
   public static final String CORBALOC_IIOPS_PREFIX = "corbaloc:iiops:";
   public static final String CORBANAME_RIR_PREFIX = "corbaname:rir:";
   public static final String CORBANAME_TGIOP_PREFIX = "corbaname:tgiop:";
   public static final String CORBANAME_IIOP_PREFIX = "corbaname:iiop:";
   public static final String CORBANAME_IIOP_PREFIX2 = "corbaname::";
   public static final String CORBANAME_IIOPS_PREFIX = "corbaname:iiops:";
   private static HashMap complexProtocolMap;
   private static final Properties defaultProps;

   public static boolean isGIOPProtocol(String url) {
      return getProtocol(url) != null;
   }

   public Name parse(String name) throws NamingException {
      return parseName(name);
   }

   public static Name parseName(String name) throws NamingException {
      return new CompoundName(name, defaultProps);
   }

   public static String getProtocol(String url) {
      if (url == null) {
         return null;
      } else {
         int delim = url.indexOf(58);
         if (delim < 0) {
            return null;
         } else {
            String protocolprefix = url.substring(0, delim + 1);
            String protocol = (String)protocolMap.get(protocolprefix);
            if (protocol == "complex") {
               delim = url.indexOf(58, delim + 1);
               if (delim < 0) {
                  return null;
               }

               protocolprefix = url.substring(0, delim + 1);
               protocol = getComplexProtocol(protocolprefix);
            }

            return protocol;
         }
      }
   }

   public static String getBasicUrl(String url) {
      if (isNotUrl(url)) {
         return null;
      } else {
         int delim = url.indexOf(35);
         if (delim >= 0) {
            return url.substring(0, delim);
         } else {
            delim = url.indexOf(47);
            if (delim < 0) {
               return url;
            } else {
               int delim2 = url.indexOf(47, delim + 1);
               if (delim2 < 0) {
                  return url;
               } else if (url.substring(delim + 1, delim2).equals("NameService")) {
                  return url.substring(0, delim2 + 1);
               } else {
                  delim = url.indexOf(47, delim2 + 1);
                  return delim < 0 ? url : url.substring(0, delim + 1);
               }
            }
         }
      }
   }

   public static String getNameString(String candidate) {
      if (isNotUrl(candidate)) {
         return candidate;
      } else if (hasCorbanameNameElement(candidate)) {
         return getCorbanameNamePortion(candidate);
      } else if (specifiesNameService(candidate)) {
         return getNameService(candidate);
      } else {
         return specifiesGenericKey(candidate) ? getGenericKey(candidate) : "";
      }
   }

   private static boolean isNotUrl(String candidate) {
      return getProtocol(candidate) == null;
   }

   private static String getCorbanameNamePortion(String candidate) {
      return candidate.substring(candidate.indexOf(35) + 1);
   }

   private static boolean hasCorbanameNameElement(String candidate) {
      return candidate.indexOf(35) >= 0;
   }

   private static boolean specifiesNameService(String candidate) {
      return hasTwoOrMoreSlashes(candidate) && firstSlashDelimitedString(candidate).equals("NameService");
   }

   private static boolean hasTwoOrMoreSlashes(String candidate) {
      return candidate.split("/").length > 2;
   }

   private static String firstSlashDelimitedString(String candidate) {
      return candidate.split("/")[1];
   }

   private static String getNameService(String candidate) {
      return candidate.substring(candidate.indexOf(47, candidate.indexOf(47) + 1) + 1);
   }

   private static boolean specifiesGenericKey(String candidate) {
      return hasThreeOrMoreSlashes(candidate);
   }

   private static boolean hasThreeOrMoreSlashes(String candidate) {
      return candidate.split("/").length > 3;
   }

   private static String getGenericKey(String candidate) {
      return stringAfterThirdSlash(candidate);
   }

   private static String stringAfterThirdSlash(String candidate) {
      return candidate.split("/", 4)[3];
   }

   static String getUrlClientProtocol(String url) {
      int delim = url.indexOf(58);
      String protocolprefix = url.substring(0, delim + 1);
      return (String)clientProtocolMap.get(protocolprefix);
   }

   static String getComplexProtocol(String protocolprefix) {
      return (String)complexProtocolMap.get(protocolprefix);
   }

   public static void main(String... args) throws InvalidNameException {
      if (args.length != 1) {
         System.out.println("NameParser <url>");
      } else {
         String url = args[0];
         System.out.println(EndPointList.createEndPointList(url));
      }

   }

   static {
      protocolMap.put("iiops:", "iiops");
      protocolMap.put("iiop:", "iiop");
      protocolMap.put("IOR:", "iiop");
      protocolMap.put("tgiop:", "tgiop");
      protocolMap.put("iioploc:", "iiop");
      protocolMap.put("iiopname:", "iiop");
      protocolMap.put("corbaloc:", "complex");
      protocolMap.put("corbaname:", "complex");
      clientProtocolMap = new HashMap(protocolMap);
      clientProtocolMap.put("t3s:", "iiops");
      clientProtocolMap.put("t3:", "iiop");
      clientProtocolMap.put("http:", "http");
      clientProtocolMap.put("https:", "https");
      complexProtocolMap = new HashMap();
      complexProtocolMap.put("corbaloc:rir:", "iiop");
      complexProtocolMap.put("corbaloc:tgiop:", "tgiop");
      complexProtocolMap.put("corbaloc:iiop:", "iiop");
      complexProtocolMap.put("corbaloc::", "iiop");
      complexProtocolMap.put("corbaloc:iiops:", "iiops");
      complexProtocolMap.put("corbaloc:http:", "http");
      complexProtocolMap.put("corbaloc:https:", "https");
      complexProtocolMap.put("corbaname:rir:", "iiop");
      complexProtocolMap.put("corbaname:tgiop:", "tgiop");
      complexProtocolMap.put("corbaname:iiop:", "iiop");
      complexProtocolMap.put("corbaname::", "iiop");
      complexProtocolMap.put("corbaname:iiops:", "iiops");
      defaultProps = new Properties();
      defaultProps.put("jndi.syntax.direction", "left_to_right");
      defaultProps.put("jndi.syntax.separator", "/");
      defaultProps.put("jndi.syntax.separator2", ".");
      defaultProps.put("jndi.syntax.ignorecase", "false");
      defaultProps.put("jndi.syntax.escape", "\\");
      defaultProps.put("jndi.syntax.beginquote", "\"");
      defaultProps.put("jndi.syntax.endquote", "\"");
      defaultProps.put("jndi.syntax.beginquote2", "'");
      defaultProps.put("jndi.syntax.endquote2", "'");
      defaultProps.put("jndi.syntax.separator.ava", ",");
      defaultProps.put("jndi.syntax.separator.typeval", "=");
   }
}
