package weblogic.diagnostics.instrumentation.support;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.DiagnosticContextConstants;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitorControl;
import weblogic.diagnostics.instrumentation.InstrumentationConstants;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationLibrary;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.InboundRequest;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.servlet.internal.ServletRequestImpl;

public final class DyeInjectionMonitorSupport implements DiagnosticContextConstants, InstrumentationConstants {
   private static DiagnosticMonitorControl dyeInjectionMonitor = null;
   private static boolean computeDyes;
   private static final long ADDR_USER_MASK = 255L;
   private static final String[] dyeInjectionPointcutClasses = new String[]{"weblogic.rmi.internal.BasicServerRef", "weblogic.servlet.internal.WebAppServletContext"};
   private static final String ENABLE_WLDF_DYE_INJECTION_METHOD_NAME = "enableWLDFDyeInjection";
   private static final String ENABLE_DYE_INJECTION_METHOD_NAME = "enableDyeInjection";
   private static long throttleInterval = -1L;
   private static long throttleRate = -1L;
   private static long reqNumber;
   private static long lastReqTime;
   private static boolean throttlingEnabled;
   private static List addressList1;
   private static List addressList2;
   private static List addressList3;
   private static List addressList4;
   private static boolean checkADDR;
   private static List userList1;
   private static List userList2;
   private static List userList3;
   private static List userList4;
   private static boolean checkUSER;
   private static List cookieList1;
   private static List cookieList2;
   private static List cookieList3;
   private static List cookieList4;
   private static boolean checkCOOKIE;
   private static boolean wlsEntriesEnabled = false;

   public static synchronized void setDyeInjectionMonitor(DiagnosticMonitorControl monitor) {
      dyeInjectionMonitor = monitor;
      captureProperties(monitor);
      boolean newState = monitor != null;
      Class[] argTypes;
      Object[] args;
      if (newState != wlsEntriesEnabled) {
         argTypes = new Class[]{Boolean.class};
         args = new Object[]{new Boolean(newState)};

         for(int i = 0; i < dyeInjectionPointcutClasses.length; ++i) {
            try {
               Class clz = Class.forName(dyeInjectionPointcutClasses[i]);
               Method method = clz.getMethod("enableWLDFDyeInjection", argTypes);
               method.invoke((Object)null, args);
            } catch (Throwable var10) {
               UnexpectedExceptionHandler.handle("Unexpected exception in setDyeInjectionMonitor", var10);
            }
         }

         wlsEntriesEnabled = newState;
      }

      argTypes = new Class[]{DiagnosticMonitor.class};
      args = new Object[]{monitor};
      String[] entryClasses = InstrumentationLibrary.getInstrumentationLibrary().getInstrumentationEngineConfiguration().getEntryClasses();
      int size = entryClasses != null ? entryClasses.length : 0;

      for(int i = 0; i < size; ++i) {
         try {
            Class clz = Class.forName(entryClasses[i]);
            Method method = clz.getMethod("enableDyeInjection", argTypes);
            method.invoke((Object)null, args);
         } catch (Throwable var9) {
            UnexpectedExceptionHandler.handle("Unexpected exception in setDyeInjectionMonitor", var9);
         }
      }

   }

   public static boolean isThrottlingEnabled() {
      return throttlingEnabled;
   }

   private static void captureProperties(DiagnosticMonitorControl monitor) {
      computeDyes = monitor != null ? monitor.isEnabled() : false;
      throttleInterval = getLongAttribute(monitor, "THROTTLE_INTERVAL");
      throttleRate = getLongAttribute(monitor, "THROTTLE_RATE");
      throttlingEnabled = throttleInterval > 0L || throttleRate > 0L;
      addressList1 = getAttributeList(monitor, "ADDR1");
      addressList2 = getAttributeList(monitor, "ADDR2");
      addressList3 = getAttributeList(monitor, "ADDR3");
      addressList4 = getAttributeList(monitor, "ADDR4");
      checkADDR = addressList1.size() > 0 || addressList2.size() > 0 || addressList3.size() > 0 || addressList4.size() > 0;
      userList1 = getAttributeList(monitor, "USER1");
      userList2 = getAttributeList(monitor, "USER2");
      userList3 = getAttributeList(monitor, "USER3");
      userList4 = getAttributeList(monitor, "USER4");
      checkUSER = userList1.size() > 0 || userList2.size() > 0 || userList3.size() > 0 || userList4.size() > 0;
      cookieList1 = getAttributeList(monitor, "COOKIE1");
      cookieList2 = getAttributeList(monitor, "COOKIE2");
      cookieList3 = getAttributeList(monitor, "COOKIE3");
      cookieList4 = getAttributeList(monitor, "COOKIE4");
      checkCOOKIE = cookieList1.size() > 0 || cookieList2.size() > 0 || cookieList3.size() > 0 || cookieList4.size() > 0;
   }

   private static long getLongAttribute(DiagnosticMonitorControl monitor, String attrName) {
      long retVal = -1L;

      try {
         String val = getAttribute(monitor, attrName);
         retVal = Long.parseLong(val);
      } catch (Exception var5) {
      }

      if (retVal <= 0L) {
         retVal = -1L;
      }

      return retVal;
   }

   private static String getAttribute(DiagnosticMonitorControl monitor, String attrName) {
      String val = monitor != null ? monitor.getAttribute(attrName) : null;
      if (val != null) {
         val = val.trim();
         if (val.length() == 0) {
            val = null;
         }
      }

      return val;
   }

   private static List getAttributeList(DiagnosticMonitorControl monitor, String attrName) {
      ArrayList list = new ArrayList(8);
      String valList = getAttribute(monitor, attrName);
      if (valList != null) {
         list = new ArrayList();
         String[] values = valList.split(",");
         String[] var5 = values;
         int var6 = values.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String val = var5[var7];
            list.add(val);
         }
      }

      return list;
   }

   private static long computeThrottleDye() {
      if (throttleInterval <= 0L && throttleRate <= 0L) {
         return 4294967296L;
      } else {
         long now = System.currentTimeMillis();
         long dye = 0L;
         if (throttleInterval > 0L && now - lastReqTime >= throttleInterval) {
            dye = 4294967296L;
         }

         if (throttleRate > 0L && reqNumber >= throttleRate) {
            dye = 4294967296L;
         }

         ++reqNumber;
         if (dye != 0L) {
            reqNumber = 0L;
            lastReqTime = now;
         }

         return dye;
      }
   }

   private static void dyeWebAppRequest(Object request, DiagnosticMonitorControl monitor) {
      try {
         Correlation context = getCorrelation();
         if (context == null) {
            return;
         }

         if (!computeDyes) {
            return;
         }

         ServletRequestImpl req = (ServletRequestImpl)request;
         long dye = context.getDyeVector();
         dye |= computeThrottleDye();
         dye |= 2097152L;
         if (req.isSecure()) {
            dye |= 67108864L;
         }

         if (checkADDR) {
            String remoteAddr = req.getRemoteAddr();
            dye |= computeAddressDye(remoteAddr);
         }

         if (checkUSER) {
            Principal userPrincipal = req.getUserPrincipal();
            String userName = userPrincipal != null ? userPrincipal.getName() : null;
            dye |= computeUsernameDye(userName);
         }

         if (checkCOOKIE) {
            dye |= computeCookiesDye(req.getCookies());
         }

         setDye(context, dye);
      } catch (Throwable var8) {
         UnexpectedExceptionHandler.handle("Unexpected exception in DyeInjectionMonitorSupport", var8);
      }

   }

   public static void dyeWebAppRequest(Object request) {
      if (dyeInjectionMonitor != null) {
         dyeWebAppRequest(request, dyeInjectionMonitor);
      }

   }

   private static void dyeRMIRequest(Object request, DiagnosticMonitorControl monitor) {
      try {
         Correlation context = getCorrelation();
         if (context == null) {
            return;
         }

         if (!computeDyes) {
            return;
         }

         InboundRequest req = (InboundRequest)request;
         long dye = context.getDyeVector();
         dye |= computeThrottleDye();
         Channel remoteChannel = req.getEndPoint().getRemoteChannel();
         String protocol;
         if (checkADDR) {
            protocol = remoteChannel.getInetAddress().getHostAddress();
            dye |= computeAddressDye(protocol);
         }

         if (checkUSER) {
            AuthenticatedSubject subject = (AuthenticatedSubject)req.getSubject();
            if (subject != null) {
               String userName = SubjectUtils.getUsername(subject);
               dye |= computeUsernameDye(userName);
            }
         }

         protocol = remoteChannel.getProtocolPrefix();
         dye |= computeProtocolDye(protocol);
         dye |= 4194304L;
         setDye(context, dye);
      } catch (Throwable var9) {
         UnexpectedExceptionHandler.handle("Unexpected exception in DyeInjectionMonitorSupport", var9);
      }

   }

   public static void dyeRMIRequest(Object request) {
      if (dyeInjectionMonitor != null) {
         dyeRMIRequest(request, dyeInjectionMonitor);
      }

   }

   private static void setDye(Correlation context, long dye) {
      dye |= context.getDyeVector();
      context.setDyeVector(dye);
      if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_ACTIONS.debug("dyeWebAppRequest: setting request dye to: " + dye);
      }

   }

   private static long computeAddressDye(String remoteAddr) {
      long dye = 0L;
      if (remoteAddr != null) {
         if (addressList1.contains(remoteAddr)) {
            dye |= 1L;
         }

         if (addressList2.contains(remoteAddr)) {
            dye |= 2L;
         }

         if (addressList3.contains(remoteAddr)) {
            dye |= 4L;
         }

         if (addressList4.contains(remoteAddr)) {
            dye |= 8L;
         }
      }

      return dye;
   }

   private static long computeUsernameDye(String userName) {
      long dye = 0L;
      if (userName != null) {
         if (userList1.contains(userName)) {
            dye |= 16L;
         }

         if (userList2.contains(userName)) {
            dye |= 32L;
         }

         if (userList3.contains(userName)) {
            dye |= 64L;
         }

         if (userList4.contains(userName)) {
            dye |= 128L;
         }
      }

      return dye;
   }

   private static long computeCookiesDye(Cookie[] cookies) {
      int size = cookies != null ? cookies.length : 0;

      for(int i = 0; i < size; ++i) {
         Cookie c = cookies[i];
         if (c.getName().equals("weblogic.diagnostics.dye")) {
            String cookieValue = c.getValue();
            long dye = 0L;
            if (cookieValue != null) {
               if (cookieList1.contains(cookieValue)) {
                  dye |= 256L;
               }

               if (cookieList2.contains(cookieValue)) {
                  dye |= 512L;
               }

               if (cookieList3.contains(cookieValue)) {
                  dye |= 1024L;
               }

               if (cookieList4.contains(cookieValue)) {
                  dye |= 2048L;
               }
            }

            return dye;
         }
      }

      return 0L;
   }

   private static long computeProtocolDye(String protocol) {
      protocol = protocol.toLowerCase();
      if (protocol.equals("t3")) {
         return 1048576L;
      } else if (protocol.equals("t3s")) {
         return 68157440L;
      } else if (protocol.equals("http")) {
         return 2097152L;
      } else if (protocol.equals("https")) {
         return 69206016L;
      } else if (protocol.equals("iiop")) {
         return 16777216L;
      } else if (protocol.equals("iiops")) {
         return 83886080L;
      } else {
         return protocol.equals("jrmp") ? 33554432L : 0L;
      }
   }

   private static Correlation getCorrelation() {
      return CorrelationFactory.findOrCreateCorrelation(true);
   }

   public static void setDyes(long dyes, long mask, String remoteAddress, String remoteUser) {
      Correlation ctx = getCorrelation();
      mask |= 255L;
      dyes |= computeUsernameDye(remoteUser);
      dyes |= computeAddressDye(remoteAddress);
      long old_dyes = ctx.getDyeVector();
      dyes = old_dyes & ~mask | dyes & mask;
      ctx.setDyeVector(dyes);
   }
}
