package weblogic.management.provider.internal.federatedconfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.utils.federatedconfig.ExclusiveFederatedConfigLocator;
import weblogic.management.utils.federatedconfig.FederatedConfig;
import weblogic.management.utils.federatedconfig.FederatedConfigLocator;

public class FederatedConfigImpl implements FederatedConfig {
   static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugFederatedConfig");
   private static final FederatedConfigImpl SINGLETON = new FederatedConfigImpl();
   private FederatedConfigLocatorManager federatedConfigLocatorManager = FederatedConfigLocatorManager.getSingleton();
   private final TransformerFactory tFactory = TransformerFactory.newInstance();
   private final List updateListeners = new ArrayList();
   private long expirationPeriod = 0L;

   private FederatedConfigImpl() {
   }

   public static FederatedConfig getSingleton() {
      return SINGLETON;
   }

   static boolean isDebug() {
      return debugLogger.isDebugEnabled();
   }

   static void debug(String msg) {
      System.err.println("<FederatedConfig> " + msg);
   }

   public boolean registerUpdateListener(FederatedConfig.UpdateListener listener) {
      boolean result = this.updateListeners.add(listener);
      Iterator var3 = this.federatedConfigLocatorManager.getFederatedConfigLocators().iterator();

      while(var3.hasNext()) {
         FederatedConfigLocator locator = (FederatedConfigLocator)var3.next();
         locator.registerUpdateListener(listener);
      }

      return result;
   }

   public boolean unregisterUpdateListener(FederatedConfig.UpdateListener listener) {
      boolean result = this.updateListeners.remove(listener);
      Iterator var3 = this.federatedConfigLocatorManager.getFederatedConfigLocators().iterator();

      while(var3.hasNext()) {
         FederatedConfigLocator locator = (FederatedConfigLocator)var3.next();
         locator.unregisterUpdateListener(listener);
      }

      return result;
   }

   public InputStream combine(InputStream baseConfigInput) throws Exception {
      return this.combine(baseConfigInput, this.federatedConfigLocatorManager.getFederatedConfigLocators().iterator());
   }

   public InputStream combine(InputStream baseConfigInput, ExclusiveFederatedConfigLocator exclusiveLocator, FederatedConfig.Validator validator) throws Exception {
      return this.combine(baseConfigInput, this.federatedConfigLocatorManager.getFederatedConfigLocators().iterator(), exclusiveLocator, (String)null, validator);
   }

   public InputStream combine(InputStream baseConfigInput, String descriptorFileName, FederatedConfig.Validator validator) throws Exception {
      return this.combine(baseConfigInput, this.federatedConfigLocatorManager.getFederatedConfigLocators().iterator(), (ExclusiveFederatedConfigLocator)null, descriptorFileName, validator);
   }

   public InputStream combine(InputStream baseConfigInput, Iterator locatorIt) throws Exception {
      return this.combine(baseConfigInput, locatorIt, (ExclusiveFederatedConfigLocator)null, (String)null, (FederatedConfig.Validator)null);
   }

   public long getExpirationPeriod() {
      return this.expirationPeriod;
   }

   private InputStream combine(InputStream baseConfigInput, Iterator locatorIt, ExclusiveFederatedConfigLocator exclusiveLocator, String descriptorFileName, FederatedConfig.Validator validator) throws Exception {
      boolean isAnyTransformationProcessed = false;
      Set excludes = exclusiveLocator == null ? Collections.EMPTY_SET : exclusiveLocator.excludes();
      String intermediateContent = null;
      boolean hasValidatedInput = false;
      boolean hasNext = true;

      while(hasNext) {
         Object locator;
         if (locatorIt.hasNext()) {
            locator = (FederatedConfigLocator)locatorIt.next();
         } else {
            if (exclusiveLocator == null) {
               break;
            }

            hasNext = false;
            locator = exclusiveLocator;
         }

         StringWriter intermediateContentWriter;
         for(Iterator sourceIt = ((FederatedConfigLocator)locator).sources(excludes, descriptorFileName); sourceIt.hasNext(); intermediateContent = intermediateContentWriter.toString()) {
            Source source = (Source)sourceIt.next();
            if (!hasValidatedInput) {
               String originalContent = null;

               try {
                  originalContent = this.copyContent(baseConfigInput);
               } catch (Exception var18) {
                  throw new Exception(var18.toString() + "[" + source + "]", var18);
               }

               if (isDebug()) {
                  if (validator == null) {
                     debug("Skipping requested just-in-time validation because validator is null");
                  }

                  debug("Performing just-in-time validation of input");
               }

               if (validator != null) {
                  validator.validate(toInputStream(originalContent));
               }

               hasValidatedInput = true;
               intermediateContent = originalContent;
            }

            Source currentInput = new StreamSource(new StringReader(intermediateContent));
            isAnyTransformationProcessed = true;
            if (isDebug()) {
               debug("Processing " + source.getSystemId());
            }

            intermediateContentWriter = new StringWriter();
            Result transformationResult = new StreamResult(intermediateContentWriter);
            this.transform(currentInput, source, transformationResult);
         }
      }

      if (isAnyTransformationProcessed) {
         if (isDebug()) {
            debug("Effective config:" + FederatedConfigUtils.LINE_SEP + intermediateContent);
         }

         return toInputStream(intermediateContent);
      } else {
         if (isDebug()) {
            debug("No config transformation was applied; continuing with original contents");
         }

         return baseConfigInput;
      }
   }

   static InputStream toInputStream(String content) throws UnsupportedEncodingException {
      return new ByteArrayInputStream(content.getBytes(Charset.defaultCharset().toString()));
   }

   static String toString(InputStream is) throws IOException {
      StringBuilder result = new StringBuilder();
      Scanner s = new Scanner(is);

      while(s.hasNextLine()) {
         result.append(s.nextLine()).append(FederatedConfigUtils.LINE_SEP);
      }

      return result.toString();
   }

   private void transform(Source source, Source styleSheet, Result result) throws TransformerException {
      Transformer transformer = this.tFactory.newTransformer(styleSheet);
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(source, result);
   }

   private String copyContent(InputStream in) throws IOException {
      return in != null ? this.readContent(in).toString("UTF-8") : "<?xml version='1.0' encoding='UTF-8'?>";
   }

   private ByteArrayOutputStream readContent(InputStream in) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1000];

      int bytesRead;
      while((bytesRead = in.read(buffer)) != -1) {
         baos.write(buffer, 0, bytesRead);
      }

      return baos;
   }
}
