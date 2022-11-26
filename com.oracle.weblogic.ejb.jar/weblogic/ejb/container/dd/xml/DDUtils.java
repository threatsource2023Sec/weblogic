package weblogic.ejb.container.dd.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Locale;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.deployer.IncomprehensibleMethodSignatureException;
import weblogic.logging.Loggable;
import weblogic.management.descriptors.Encoding;
import weblogic.utils.StringUtils;
import weblogic.utils.io.XMLDeclaration;

public final class DDUtils {
   private static final boolean debug = false;

   public static String getMethodName(String signature) throws IncomprehensibleMethodSignatureException {
      int lparen = signature.indexOf(40);
      int rparen = signature.indexOf(41);
      String methodName;
      Loggable l;
      if (lparen == -1) {
         if (rparen != -1) {
            l = EJBLogger.loginvalidMethodSignatureLoggable(signature);
            throw new IncomprehensibleMethodSignatureException(l.getMessageText());
         }

         methodName = signature;
      } else {
         if (rparen == -1) {
            l = EJBLogger.loginvalidMethodSignatureLoggable(signature);
            throw new IncomprehensibleMethodSignatureException(l.getMessageText());
         }

         methodName = signature.substring(0, lparen);
      }

      return methodName;
   }

   public static String[] getMethodParams(String signature) throws IncomprehensibleMethodSignatureException {
      int lparen = signature.indexOf(40);
      int rparen = signature.indexOf(41);
      String[] params;
      if (lparen == -1 && rparen == -1) {
         params = null;
      } else {
         if (lparen == -1 || rparen == -1) {
            Loggable l = EJBLogger.loginvalidMethodSignatureLoggable(signature);
            throw new IncomprehensibleMethodSignatureException(l.getMessageText());
         }

         params = StringUtils.splitCompletely(signature.substring(lparen + 1, rparen), ",", false);

         for(int i = 0; i < params.length; ++i) {
            params[i] = params[i].trim();
         }
      }

      return params;
   }

   public static String getMethodSignature(String methodName, String[] methodParams) {
      if (methodParams != null && !"*".equals(methodName)) {
         String[] localMethodParams = new String[methodParams.length];

         for(int i = 0; i < methodParams.length; ++i) {
            localMethodParams[i] = makeMethodParam(methodParams[i]);
         }

         return methodName + "(" + StringUtils.join(localMethodParams, ",") + ")";
      } else {
         return methodName;
      }
   }

   public static String getMethodSignature(Method m) {
      String methodName = m.getName();
      Class[] paramClasses = m.getParameterTypes();
      String[] methodParams = new String[paramClasses.length];

      for(int i = 0; i < paramClasses.length; ++i) {
         methodParams[i] = paramClasses[i].getCanonicalName();
      }

      return getMethodSignature(methodName, methodParams);
   }

   public static String getMethodSignature(String methodName, Class[] paramTypes) {
      String[] paramNames = new String[paramTypes.length];

      for(int i = 0; i < paramTypes.length; ++i) {
         paramNames[i] = paramTypes[i].getCanonicalName();
      }

      return getMethodSignature(methodName, paramNames);
   }

   public static String getEjbHomeMethodSignature(Method m) {
      String name = m.getName();
      String ejbHomeName = "ejbHome" + name.substring(0, 1).toUpperCase(Locale.ENGLISH);
      if (name.length() > 1) {
         ejbHomeName = ejbHomeName + name.substring(1);
      }

      return getMethodSignature(ejbHomeName, m.getParameterTypes());
   }

   static String makeMethodParam(String s) {
      if (!s.endsWith("[]")) {
         return s;
      } else {
         StringBuilder sb = new StringBuilder();
         int i = s.length() - 2;

         while(true) {
            sb.append('[');
            if (i < 2 || s.charAt(i - 2) != '[' || s.charAt(i - 1) != ']') {
               return sb.append(arrayClassName(s.substring(0, i).trim())).toString();
            }

            i -= 2;
         }
      }
   }

   private static String arrayClassName(String name) {
      if (name.equals("byte")) {
         return "B";
      } else if (name.equals("char")) {
         return "C";
      } else if (name.equals("int")) {
         return "I";
      } else if (name.equals("long")) {
         return "J";
      } else if (name.equals("float")) {
         return "F";
      } else if (name.equals("double")) {
         return "D";
      } else if (name.equals("short")) {
         return "S";
      } else {
         return name.equals("boolean") ? "Z" : "L" + name + ";";
      }
   }

   public static int isoStringToInt(String iso) throws IllegalArgumentException {
      if (iso.equalsIgnoreCase("TransactionSerializable")) {
         return 8;
      } else if (iso.equalsIgnoreCase("TransactionRepeatableRead")) {
         return 4;
      } else if (iso.equalsIgnoreCase("TransactionReadCommitted")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TransactionReadCommittedForUpdate")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TransactionReadCommittedForUpdateNoWait")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TransactionReadUncommitted")) {
         return 1;
      } else if (iso.equalsIgnoreCase("TransactionNone")) {
         return 0;
      } else if (iso.equalsIgnoreCase("TRANSACTION_SERIALIZABLE")) {
         return 8;
      } else if (iso.equalsIgnoreCase("TRANSACTION_REPEATABLE_READ")) {
         return 4;
      } else if (iso.equalsIgnoreCase("TRANSACTION_READ_COMMITTED")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TRANSACTION_READ_COMMITTED_FOR_UPDATE")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TRANSACTION_READ_COMMITTED_FOR_UPDATE_NO_WAIT")) {
         return 2;
      } else if (iso.equalsIgnoreCase("TRANSACTION_READ_UNCOMMITTED")) {
         return 1;
      } else if (iso.equalsIgnoreCase("TRANSACTION_NONE")) {
         return 0;
      } else {
         throw new IllegalArgumentException("Bad isolation level: " + iso);
      }
   }

   public static int concurrencyStringToInt(String concurrencyString) {
      int concurrencyStrategy = true;
      byte concurrencyStrategy;
      if ("Exclusive".equalsIgnoreCase(concurrencyString)) {
         concurrencyStrategy = 1;
      } else if ("database".equalsIgnoreCase(concurrencyString)) {
         concurrencyStrategy = 2;
      } else if ("ReadOnlyExclusive".equalsIgnoreCase(concurrencyString)) {
         concurrencyStrategy = 4;
      } else if ("ReadOnly".equalsIgnoreCase(concurrencyString)) {
         concurrencyStrategy = 5;
      } else {
         if (!"Optimistic".equalsIgnoreCase(concurrencyString)) {
            throw new AssertionError("Bad concurrency setting: " + concurrencyString);
         }

         concurrencyStrategy = 6;
      }

      return concurrencyStrategy;
   }

   public static String getXMLEncoding(InputStream xml, String fileName) throws IOException {
      String encoding = null;
      xml.mark(1048576);

      try {
         XMLDeclaration xd = new XMLDeclaration();
         xd.parse(xml);
         encoding = xd.getEncoding();
      } finally {
         xml.reset();
      }

      validateEncoding(encoding, fileName);
      return encoding;
   }

   private static void validateEncoding(String encoding, String fileName) throws IOException {
      if (encoding != null && Encoding.getIANA2JavaMapping(encoding) == null && Encoding.getJava2IANAMapping(encoding) == null && !Charset.isSupported(encoding)) {
         throw new UnsupportedEncodingException(fileName + " uses invalid encoding");
      }
   }
}
