package weblogic.apache.xerces.impl;

import java.util.Hashtable;
import java.util.Locale;
import org.xml.sax.ErrorHandler;
import weblogic.apache.xerces.util.DefaultErrorHandler;
import weblogic.apache.xerces.util.ErrorHandlerProxy;
import weblogic.apache.xerces.util.MessageFormatter;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLComponent;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLErrorHandler;
import weblogic.apache.xerces.xni.parser.XMLParseException;

public class XMLErrorReporter implements XMLComponent {
   public static final short SEVERITY_WARNING = 0;
   public static final short SEVERITY_ERROR = 1;
   public static final short SEVERITY_FATAL_ERROR = 2;
   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   private static final String[] RECOGNIZED_FEATURES = new String[]{"http://apache.org/xml/features/continue-after-fatal-error"};
   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[]{null};
   private static final String[] RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/error-handler"};
   private static final Object[] PROPERTY_DEFAULTS = new Object[]{null};
   protected Locale fLocale;
   protected Hashtable fMessageFormatters = new Hashtable();
   protected XMLErrorHandler fErrorHandler;
   protected XMLLocator fLocator;
   protected boolean fContinueAfterFatalError;
   protected XMLErrorHandler fDefaultErrorHandler;
   private ErrorHandler fSaxProxy = null;

   public void setLocale(Locale var1) {
      this.fLocale = var1;
   }

   public Locale getLocale() {
      return this.fLocale;
   }

   public void setDocumentLocator(XMLLocator var1) {
      this.fLocator = var1;
   }

   public void putMessageFormatter(String var1, MessageFormatter var2) {
      this.fMessageFormatters.put(var1, var2);
   }

   public MessageFormatter getMessageFormatter(String var1) {
      return (MessageFormatter)this.fMessageFormatters.get(var1);
   }

   public MessageFormatter removeMessageFormatter(String var1) {
      return (MessageFormatter)this.fMessageFormatters.remove(var1);
   }

   public String reportError(String var1, String var2, Object[] var3, short var4) throws XNIException {
      return this.reportError(this.fLocator, var1, var2, var3, var4);
   }

   public String reportError(String var1, String var2, Object[] var3, short var4, Exception var5) throws XNIException {
      return this.reportError(this.fLocator, var1, var2, var3, var4, var5);
   }

   public String reportError(XMLLocator var1, String var2, String var3, Object[] var4, short var5) throws XNIException {
      return this.reportError(var1, var2, var3, var4, var5, (Exception)null);
   }

   public String reportError(XMLLocator var1, String var2, String var3, Object[] var4, short var5, Exception var6) throws XNIException {
      MessageFormatter var7 = this.getMessageFormatter(var2);
      String var8;
      if (var7 != null) {
         var8 = var7.formatMessage(this.fLocale, var3, var4);
      } else {
         StringBuffer var9 = new StringBuffer();
         var9.append(var2);
         var9.append('#');
         var9.append(var3);
         int var10 = var4 != null ? var4.length : 0;
         if (var10 > 0) {
            var9.append('?');

            for(int var11 = 0; var11 < var10; ++var11) {
               var9.append(var4[var11]);
               if (var11 < var10 - 1) {
                  var9.append('&');
               }
            }
         }

         var8 = var9.toString();
      }

      XMLParseException var13 = var6 != null ? new XMLParseException(var1, var8, var6) : new XMLParseException(var1, var8);
      XMLErrorHandler var12 = this.fErrorHandler;
      if (var12 == null) {
         if (this.fDefaultErrorHandler == null) {
            this.fDefaultErrorHandler = new DefaultErrorHandler();
         }

         var12 = this.fDefaultErrorHandler;
      }

      switch (var5) {
         case 0:
            var12.warning(var2, var3, var13);
            break;
         case 1:
            var12.error(var2, var3, var13);
            break;
         case 2:
            var12.fatalError(var2, var3, var13);
            if (!this.fContinueAfterFatalError) {
               throw var13;
            }
      }

      return var8;
   }

   public void reset(XMLComponentManager var1) throws XNIException {
      try {
         this.fContinueAfterFatalError = var1.getFeature("http://apache.org/xml/features/continue-after-fatal-error");
      } catch (XNIException var3) {
         this.fContinueAfterFatalError = false;
      }

      this.fErrorHandler = (XMLErrorHandler)var1.getProperty("http://apache.org/xml/properties/internal/error-handler");
   }

   public String[] getRecognizedFeatures() {
      return (String[])RECOGNIZED_FEATURES.clone();
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
      if (var1.startsWith("http://apache.org/xml/features/")) {
         int var3 = var1.length() - "http://apache.org/xml/features/".length();
         if (var3 == "continue-after-fatal-error".length() && var1.endsWith("continue-after-fatal-error")) {
            this.fContinueAfterFatalError = var2;
         }
      }

   }

   public boolean getFeature(String var1) throws XMLConfigurationException {
      if (var1.startsWith("http://apache.org/xml/features/")) {
         int var2 = var1.length() - "http://apache.org/xml/features/".length();
         if (var2 == "continue-after-fatal-error".length() && var1.endsWith("continue-after-fatal-error")) {
            return this.fContinueAfterFatalError;
         }
      }

      return false;
   }

   public String[] getRecognizedProperties() {
      return (String[])RECOGNIZED_PROPERTIES.clone();
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      if (var1.startsWith("http://apache.org/xml/properties/")) {
         int var3 = var1.length() - "http://apache.org/xml/properties/".length();
         if (var3 == "internal/error-handler".length() && var1.endsWith("internal/error-handler")) {
            this.fErrorHandler = (XMLErrorHandler)var2;
         }
      }

   }

   public Boolean getFeatureDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_FEATURES.length; ++var2) {
         if (RECOGNIZED_FEATURES[var2].equals(var1)) {
            return FEATURE_DEFAULTS[var2];
         }
      }

      return null;
   }

   public Object getPropertyDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_PROPERTIES.length; ++var2) {
         if (RECOGNIZED_PROPERTIES[var2].equals(var1)) {
            return PROPERTY_DEFAULTS[var2];
         }
      }

      return null;
   }

   public XMLErrorHandler getErrorHandler() {
      return this.fErrorHandler;
   }

   public ErrorHandler getSAXErrorHandler() {
      if (this.fSaxProxy == null) {
         this.fSaxProxy = new ErrorHandlerProxy() {
            protected XMLErrorHandler getErrorHandler() {
               return XMLErrorReporter.this.fErrorHandler;
            }
         };
      }

      return this.fSaxProxy;
   }
}
