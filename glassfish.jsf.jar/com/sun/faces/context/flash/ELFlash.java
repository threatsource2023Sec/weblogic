package com.sun.faces.context.flash;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.tag.ui.UIDebug;
import com.sun.faces.util.ByteArrayGuardAESCTR;
import com.sun.faces.util.FacesLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.PhaseId;
import javax.faces.event.PostKeepFlashValueEvent;
import javax.faces.event.PostPutFlashValueEvent;
import javax.faces.event.PreClearFlashEvent;
import javax.faces.event.PreRemoveFlashValueEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ELFlash extends Flash {
   private Map flashInnerMap = null;
   private final AtomicLong sequenceNumber = new AtomicLong(0L);
   private int numberOfConcurentFlashUsers;
   private long numberOfFlashesBetweenFlashReapings;
   private final boolean distributable;
   private ByteArrayGuardAESCTR guard;
   private static final String ELEMENT_TYPE_MISMATCH = "element-type-mismatch";
   private static final Logger LOGGER;
   static final String PREFIX = "csfcf";
   static final String FLASH_ATTRIBUTE_NAME = "csfcff";
   static final String FLASH_COOKIE_NAME = "csfcfc";
   static final String FLASH_NOW_REQUEST_KEY = "csfcffn";
   public static final String ACT_AS_DO_LAST_PHASE_ACTIONS;

   private ELFlash(ExternalContext extContext) {
      this.numberOfConcurentFlashUsers = Integer.parseInt(WebConfiguration.WebContextInitParameter.NumberOfConcurrentFlashUsers.getDefaultValue());
      this.numberOfFlashesBetweenFlashReapings = Long.parseLong(WebConfiguration.WebContextInitParameter.NumberOfFlashesBetweenFlashReapings.getDefaultValue());
      this.flashInnerMap = new ConcurrentHashMap();
      WebConfiguration config = WebConfiguration.getInstance(extContext);

      String value;
      try {
         value = config.getOptionValue(WebConfiguration.WebContextInitParameter.NumberOfConcurrentFlashUsers);
         this.numberOfConcurentFlashUsers = Integer.parseInt(value);
      } catch (NumberFormatException var6) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Unable to set number of concurrent flash users.  Defaulting to {0}", this.numberOfConcurentFlashUsers);
         }
      }

      try {
         value = config.getOptionValue(WebConfiguration.WebContextInitParameter.NumberOfFlashesBetweenFlashReapings);
         this.numberOfFlashesBetweenFlashReapings = Long.parseLong(value);
      } catch (NumberFormatException var5) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Unable to set number flashes between flash repaings.  Defaulting to {0}", this.numberOfFlashesBetweenFlashReapings);
         }
      }

      this.distributable = config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableDistributable);
      this.guard = new ByteArrayGuardAESCTR();
   }

   public static Map getFlash() {
      FacesContext context = FacesContext.getCurrentInstance();
      return getFlash(context.getExternalContext(), true);
   }

   static ELFlash getFlash(ExternalContext extContext, boolean create) {
      Map appMap = extContext.getApplicationMap();
      ELFlash flash = (ELFlash)appMap.get("csfcff");
      if (null == flash && create) {
         synchronized(extContext.getContext()) {
            if (null == (flash = (ELFlash)appMap.get("csfcff"))) {
               flash = new ELFlash(extContext);
               appMap.put("csfcff", flash);
            }
         }
      }

      if (appMap.get(WebConfiguration.BooleanWebContextInitParameter.EnableDistributable.getQualifiedName()) != null) {
         synchronized(extContext.getContext()) {
            if (extContext.getSession(false) != null) {
               SessionHelper sessionHelper = SessionHelper.getInstance(extContext);
               if (sessionHelper == null) {
                  sessionHelper = new SessionHelper();
               }

               sessionHelper.update(extContext, flash);
            }
         }
      }

      return flash;
   }

   public boolean isKeepMessages() {
      boolean result = false;
      Map phaseMap;
      if (null != (phaseMap = this.loggingGetPhaseMapForReading(false))) {
         Object value = phaseMap.get(ELFlash.CONSTANTS.KeepAllMessagesAttributeName.toString());
         result = null != value ? (Boolean)value : false;
      }

      return result;
   }

   public void setKeepMessages(boolean newValue) {
      this.loggingGetPhaseMapForWriting(false).put(ELFlash.CONSTANTS.KeepAllMessagesAttributeName.toString(), newValue);
   }

   public boolean isRedirect() {
      boolean result = false;
      FacesContext context = FacesContext.getCurrentInstance();
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, false))) {
         result = flashManager.getPreviousRequestFlashInfo().isIsRedirect();
      }

      return result;
   }

   public void setRedirect(boolean newValue) {
   }

   public Object get(Object key) {
      Object result = null;
      FacesContext context = FacesContext.getCurrentInstance();
      if (null != key) {
         if (key.equals("keepMessages")) {
            result = this.isKeepMessages();
         } else if (key.equals("redirect")) {
            result = this.isRedirect();
         } else if (this.isKeepFlagSet(context)) {
            result = this.getPhaseMapForReading().get(key);
            this.keep(key.toString());
            this.clearKeepFlag(context);
            return result;
         }
      }

      if (null == result) {
         result = this.getPhaseMapForReading().get(key);
      }

      if (this.distributable && context.getExternalContext().getSession(false) != null) {
         SessionHelper sessionHelper = SessionHelper.getInstance(context.getExternalContext());

         assert null != sessionHelper;

         sessionHelper.update(context.getExternalContext(), this);
      }

      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "get({0}) = {1}", new Object[]{key, result});
      }

      return result;
   }

   public Object put(String key, Object value) {
      Boolean b = null;
      Object result = null;
      boolean wasSpecialPut = false;
      if (null != key) {
         if (key.equals("keepMessages")) {
            this.setKeepMessages(b = Boolean.parseBoolean((String)value));
            wasSpecialPut = true;
         }

         if (key.equals("redirect")) {
            this.setRedirect(b = Boolean.parseBoolean((String)value));
            wasSpecialPut = true;
         }
      }

      FacesContext context = FacesContext.getCurrentInstance();
      if (!wasSpecialPut) {
         result = null == b ? this.getPhaseMapForWriting().put(key, value) : b;
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "put({0},{1})", new Object[]{key, value});
         }

         context.getApplication().publishEvent(context, PostPutFlashValueEvent.class, key);
      }

      if (this.distributable && context.getExternalContext().getSession(false) != null) {
         SessionHelper sessionHelper = SessionHelper.getInstance(context.getExternalContext());
         if (sessionHelper != null) {
            sessionHelper.update(context.getExternalContext(), this);
         }
      }

      return result;
   }

   public Object remove(Object key) {
      Object result = null;
      FacesContext context = FacesContext.getCurrentInstance();
      context.getApplication().publishEvent(context, PreRemoveFlashValueEvent.class, key);
      result = this.getPhaseMapForWriting().remove(key);
      return result;
   }

   public boolean containsKey(Object key) {
      boolean result = false;
      result = this.getPhaseMapForReading().containsKey(key);
      return result;
   }

   public boolean containsValue(Object value) {
      boolean result = false;
      result = this.getPhaseMapForReading().containsValue(value);
      return result;
   }

   public void putAll(Map t) {
      this.getPhaseMapForWriting().putAll(t);
   }

   public Collection values() {
      Collection result = null;
      result = this.getPhaseMapForReading().values();
      return result;
   }

   public int size() {
      int result = false;
      int result = this.getPhaseMapForReading().size();
      return result;
   }

   public void clear() {
      this.getPhaseMapForWriting().clear();
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public Set entrySet() {
      Set readingMapEntrySet = this.getPhaseMapForReading().entrySet();
      Set writingMapEntrySet = this.getPhaseMapForWriting().entrySet();
      Set result = null;
      result = new HashSet();
      result.addAll(readingMapEntrySet);
      result.addAll(writingMapEntrySet);
      return result;
   }

   public boolean isEmpty() {
      boolean readingMapIsEmpty = this.getPhaseMapForReading().isEmpty();
      boolean writingMapIsEmpty = this.getPhaseMapForWriting().isEmpty();
      boolean result = false;
      result = readingMapIsEmpty && writingMapIsEmpty;
      return result;
   }

   public Set keySet() {
      Set readingMapKeySet = this.getPhaseMapForReading().keySet();
      Set writingMapKeySet = this.getPhaseMapForWriting().keySet();
      Set result = null;
      result = new HashSet();
      result.addAll(readingMapKeySet);
      result.addAll(writingMapKeySet);
      return result;
   }

   public void keep(String key) {
      FacesContext context = FacesContext.getCurrentInstance();
      Map requestMap = context.getExternalContext().getRequestMap();
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, true))) {
         Object toKeep;
         if (null == (toKeep = requestMap.remove(key))) {
            FlashInfo flashInfo = null;
            if (null != (flashInfo = flashManager.getPreviousRequestFlashInfo())) {
               toKeep = flashInfo.getFlashMap().get(key);
            }
         }

         if (null != toKeep) {
            this.getPhaseMapForWriting().put(key, toKeep);
            context.getApplication().publishEvent(context, PostKeepFlashValueEvent.class, key);
         }
      }

   }

   public void putNow(String key, Object value) {
      FacesContext context = FacesContext.getCurrentInstance();
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, true))) {
         FlashInfo flashInfo = null;
         if (null != (flashInfo = flashManager.getPreviousRequestFlashInfo())) {
            flashInfo.getFlashMap().put(key, value);
         }
      }

   }

   public void doPrePhaseActions(FacesContext context) {
      PhaseId currentPhase = context.getCurrentPhaseId();
      Map contextMap = context.getAttributes();
      contextMap.put(ELFlash.CONSTANTS.SavedResponseCompleteFlagValue, context.getResponseComplete());
      Cookie cookie = null;
      if (currentPhase.equals(PhaseId.RESTORE_VIEW)) {
         if (null != (cookie = this.getCookie(context.getExternalContext()))) {
            this.getCurrentFlashManager(context, contextMap, cookie);
         }

         if (this.isKeepMessages()) {
            this.restoreAllMessages(context);
         }
      } else if (currentPhase.equals(PhaseId.RENDER_RESPONSE) && contextMap.containsKey(WebConfiguration.BooleanWebContextInitParameter.ForceAlwaysWriteFlashCookie) && (Boolean)contextMap.get(WebConfiguration.BooleanWebContextInitParameter.ForceAlwaysWriteFlashCookie)) {
         PreviousNextFlashInfoManager flashManager = this.getCurrentFlashManager(contextMap, true);
         cookie = flashManager.encode();
         if (null != cookie) {
            this.setCookie(context, flashManager, cookie, true);
         } else if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.externalcontext.flash.force.write.cookie.failed");
         }
      }

   }

   public void doPostPhaseActions(FacesContext context) {
      if (context.getAttributes().containsKey(ACT_AS_DO_LAST_PHASE_ACTIONS)) {
         Boolean outgoingResponseIsRedirect = (Boolean)context.getAttributes().get(ACT_AS_DO_LAST_PHASE_ACTIONS);
         this.doLastPhaseActions(context, outgoingResponseIsRedirect);
      } else {
         PhaseId currentPhase = context.getCurrentPhaseId();
         Map contextMap = context.getAttributes();
         boolean responseCompleteJustSetTrue = this.responseCompleteWasJustSetTrue(context, contextMap);
         boolean lastPhaseForThisRequest = responseCompleteJustSetTrue || currentPhase == PhaseId.RENDER_RESPONSE;
         if (lastPhaseForThisRequest) {
            this.doLastPhaseActions(context, false);
         }

      }
   }

   public void doLastPhaseActions(FacesContext context, boolean outgoingResponseIsRedirect) {
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager = this.getCurrentFlashManager(contextMap, false);
      if (null != flashManager) {
         if (this.isKeepMessages()) {
            this.saveAllMessages(context);
         }

         this.releaseCurrentFlashManager(contextMap);
         FlashInfo flashInfo;
         if (outgoingResponseIsRedirect) {
            flashInfo = flashManager.getPreviousRequestFlashInfo();
            flashInfo.setIsRedirect(true);
            flashManager.expireNext_MovePreviousToNext();
         } else {
            flashInfo = flashManager.getPreviousRequestFlashInfo();
            if (null != flashInfo && flashInfo.getLifetimeMarker() == ELFlash.LifetimeMarker.SecondTimeThru) {
               flashManager.expirePrevious();
            }
         }

         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "---------------------------------------");
         }

         this.setCookie(context, flashManager, flashManager.encode(), false);
      }
   }

   void setFlashInnerMap(Map flashInnerMap) {
      this.flashInnerMap = flashInnerMap;
   }

   Map getFlashInnerMap() {
      return this.flashInnerMap;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("[\n");
      Iterator var2 = this.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         builder.append("{").append((String)entry.getKey()).append(", ").append(entry.getValue()).append("}\n");
      }

      builder.append("]\n");
      return builder.toString();
   }

   private void maybeWriteCookie(FacesContext context, PreviousNextFlashInfoManager flashManager) {
      FlashInfo flashInfo = flashManager.getPreviousRequestFlashInfo();
      if (null != flashInfo && flashInfo.getLifetimeMarker() == ELFlash.LifetimeMarker.SecondTimeThru) {
         PreviousNextFlashInfoManager copiedFlashManager = flashManager.copyWithoutInnerMap();
         copiedFlashManager.expirePrevious();
         this.setCookie(context, flashManager, copiedFlashManager.encode(), false);
      }

   }

   static void setKeepFlag(FacesContext context) {
      context.getAttributes().put(ELFlash.CONSTANTS.KeepFlagAttributeName, Boolean.TRUE);
   }

   void clearKeepFlag(FacesContext context) {
      context.getAttributes().remove(ELFlash.CONSTANTS.KeepFlagAttributeName);
   }

   boolean isKeepFlagSet(FacesContext context) {
      return Boolean.TRUE == context.getAttributes().get(ELFlash.CONSTANTS.KeepFlagAttributeName);
   }

   private long getNewSequenceNumber() {
      long result = this.sequenceNumber.incrementAndGet();
      if (0L == result % this.numberOfFlashesBetweenFlashReapings) {
         this.reapFlashes();
      }

      if (result == Long.MAX_VALUE) {
         result = 1L;
         this.sequenceNumber.set(1L);
      }

      return result;
   }

   private void reapFlashes() {
      if (this.flashInnerMap.size() >= this.numberOfConcurentFlashUsers) {
         Set keys = this.flashInnerMap.keySet();
         long currentSequenceNumber = this.sequenceNumber.get();
         Iterator var7 = keys.iterator();

         while(var7.hasNext()) {
            String cur = (String)var7.next();
            long sequenceNumberToTest = Long.parseLong(cur);
            if ((long)this.numberOfConcurentFlashUsers < currentSequenceNumber - sequenceNumberToTest) {
               Map curFlash;
               if (null != (curFlash = (Map)this.flashInnerMap.get(cur))) {
                  curFlash.clear();
               }

               this.flashInnerMap.remove(cur);
            }
         }

         if (this.distributable && FacesContext.getCurrentInstance().getExternalContext().getSession(false) != null) {
            ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
            SessionHelper sessionHelper = SessionHelper.getInstance(extContext);
            if (null != sessionHelper) {
               sessionHelper.remove(extContext);
               sessionHelper = new SessionHelper();
               sessionHelper.update(extContext, this);
            }
         }

      }
   }

   private boolean responseCompleteWasJustSetTrue(FacesContext context, Map contextMap) {
      boolean result = false;
      result = Boolean.FALSE == contextMap.get(ELFlash.CONSTANTS.SavedResponseCompleteFlagValue) && context.getResponseComplete();
      return result;
   }

   private static String getLogPrefix(FacesContext context) {
      StringBuilder result = new StringBuilder();
      ExternalContext extContext = context.getExternalContext();
      Object request = extContext.getRequest();
      if (request instanceof HttpServletRequest) {
         result.append(((HttpServletRequest)request).getMethod()).append(" ");
      }

      UIViewRoot root = context.getViewRoot();
      if (null != root) {
         String viewId = root.getViewId();
         if (null != viewId) {
            result.append(viewId).append(" ");
         }
      }

      return result.toString();
   }

   private Map loggingGetPhaseMapForWriting(boolean loggingEnabled) {
      FacesContext context = FacesContext.getCurrentInstance();
      Map result = null;
      PhaseId currentPhase = context.getCurrentPhaseId();
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, true))) {
         boolean isDebugLog = loggingEnabled && LOGGER.isLoggable(Level.FINEST);
         FlashInfo flashInfo;
         if (currentPhase.getOrdinal() < PhaseId.RENDER_RESPONSE.getOrdinal()) {
            flashInfo = flashManager.getPreviousRequestFlashInfo();
            if (isDebugLog) {
               LOGGER.log(Level.FINEST, "{0}previous[{1}]", new Object[]{getLogPrefix(context), flashInfo.getSequenceNumber()});
            }
         } else {
            flashInfo = flashManager.getNextRequestFlashInfo(this, true);
            if (isDebugLog) {
               LOGGER.log(Level.FINEST, "{0}next[{1}]", new Object[]{getLogPrefix(context), flashInfo.getSequenceNumber()});
            }

            this.maybeWriteCookie(context, flashManager);
         }

         result = flashInfo.getFlashMap();
      }

      return result;
   }

   private Map getPhaseMapForWriting() {
      return this.loggingGetPhaseMapForWriting(true);
   }

   private Map loggingGetPhaseMapForReading(boolean loggingEnabled) {
      FacesContext context = FacesContext.getCurrentInstance();
      Map result = Collections.emptyMap();
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      FlashInfo flashInfo;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, false)) && null != (flashInfo = flashManager.getPreviousRequestFlashInfo())) {
         boolean isDebugLog = loggingEnabled && LOGGER.isLoggable(Level.FINEST);
         if (isDebugLog) {
            LOGGER.log(Level.FINEST, "{0}previous[{1}]", new Object[]{getLogPrefix(context), flashInfo.getSequenceNumber()});
         }

         result = flashInfo.getFlashMap();
      }

      return result;
   }

   private Map getPhaseMapForReading() {
      return this.loggingGetPhaseMapForReading(true);
   }

   void saveAllMessages(FacesContext context) {
      Map contextMap = context.getAttributes();
      PreviousNextFlashInfoManager flashManager;
      if (null != (flashManager = this.getCurrentFlashManager(contextMap, true))) {
         if (!flashManager.getPreviousRequestFlashInfo().isIsRedirect()) {
            Iterator messageClientIds = context.getClientIdsWithMessages();

            ArrayList facesMessages;
            HashMap allFacesMessages;
            Iterator messageIter;
            String curMessageId;
            for(allFacesMessages = null; messageClientIds.hasNext(); allFacesMessages.put(curMessageId, facesMessages)) {
               curMessageId = (String)messageClientIds.next();
               messageIter = context.getMessages(curMessageId);
               facesMessages = new ArrayList();

               while(messageIter.hasNext()) {
                  facesMessages.add(messageIter.next());
               }

               if (null == allFacesMessages) {
                  allFacesMessages = new HashMap();
               }
            }

            facesMessages = null;
            messageIter = context.getMessages((String)null);
            facesMessages = new ArrayList();

            while(messageIter.hasNext()) {
               facesMessages.add(messageIter.next());
            }

            if (null != facesMessages) {
               if (null == allFacesMessages) {
                  allFacesMessages = new HashMap();
               }

               allFacesMessages.put((Object)null, facesMessages);
            }

            this.getPhaseMapForWriting().put(ELFlash.CONSTANTS.FacesMessageAttributeName.toString(), allFacesMessages);
         }
      }
   }

   void restoreAllMessages(FacesContext context) {
      Map phaseMap = this.getPhaseMapForReading();
      Map allFacesMessages;
      if (null != (allFacesMessages = (Map)phaseMap.get(ELFlash.CONSTANTS.FacesMessageAttributeName.toString()))) {
         Iterator var5 = allFacesMessages.entrySet().iterator();

         while(true) {
            List facesMessages;
            Map.Entry cur;
            do {
               if (!var5.hasNext()) {
                  phaseMap.remove(ELFlash.CONSTANTS.FacesMessageAttributeName.toString());
                  return;
               }

               cur = (Map.Entry)var5.next();
            } while(null == (facesMessages = (List)allFacesMessages.get(cur.getKey())));

            Iterator var7 = facesMessages.iterator();

            while(var7.hasNext()) {
               FacesMessage curMessage = (FacesMessage)var7.next();
               context.addMessage((String)cur.getKey(), curMessage);
            }
         }
      }
   }

   private Cookie getCookie(ExternalContext extContext) {
      Cookie result = null;
      result = (Cookie)extContext.getRequestCookieMap().get("csfcfc");
      return result;
   }

   private void setCookie(FacesContext context, PreviousNextFlashInfoManager flashManager, Cookie toSet, boolean forceWrite) {
      Map contextMap = context.getAttributes();
      ExternalContext extContext = context.getExternalContext();
      if (!contextMap.containsKey(ELFlash.CONSTANTS.DidWriteCookieAttributeName)) {
         FlashInfo nextFlash = flashManager.getNextRequestFlashInfo();
         FlashInfo prevFlash = flashManager.getPreviousRequestFlashInfo();
         if (context.getAttributes().containsKey(ELFlash.CONSTANTS.ForceSetMaxAgeZero)) {
            this.removeCookie(extContext, toSet);
         } else {
            if (!forceWrite && (null == nextFlash || nextFlash.getFlashMap().isEmpty()) && (null == prevFlash || prevFlash.getFlashMap().isEmpty())) {
               this.removeCookie(extContext, toSet);
            } else {
               if (extContext.isResponseCommitted()) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.externalcontext.flash.response.already.committed");
                  }
               } else {
                  Map properties = new HashMap();
                  String val;
                  if (null != (val = toSet.getComment())) {
                     properties.put("comment", val);
                  }

                  if (null != (val = toSet.getDomain())) {
                     properties.put("domain", val);
                  }

                  Integer val;
                  if (null != (val = toSet.getMaxAge())) {
                     properties.put("maxAge", val);
                  }

                  if (extContext.isSecure()) {
                     properties.put("secure", Boolean.TRUE);
                  } else {
                     Boolean val;
                     if (null != (val = toSet.getSecure())) {
                        properties.put("secure", val);
                     }
                  }

                  if (null != (val = toSet.getPath())) {
                     properties.put("path", val);
                  }

                  properties.put("httpOnly", Boolean.TRUE);
                  extContext.addResponseCookie(toSet.getName(), toSet.getValue(), !properties.isEmpty() ? properties : null);
                  properties = null;
               }

               contextMap.put(ELFlash.CONSTANTS.DidWriteCookieAttributeName, Boolean.TRUE);
            }

         }
      }
   }

   private void removeCookie(ExternalContext extContext, Cookie toRemove) {
      if (!extContext.isResponseCommitted()) {
         Map properties = new HashMap();
         toRemove.setMaxAge(0);
         String val;
         if (null != (val = toRemove.getComment())) {
            properties.put("comment", val);
         }

         if (null != (val = toRemove.getDomain())) {
            properties.put("domain", val);
         }

         Integer val;
         if (null != (val = toRemove.getMaxAge())) {
            properties.put("maxAge", val);
         }

         if (extContext.isSecure()) {
            properties.put("secure", Boolean.TRUE);
         } else {
            Boolean val;
            if (null != (val = toRemove.getSecure())) {
               properties.put("secure", val);
            }
         }

         if (null != (val = toRemove.getPath())) {
            properties.put("path", val);
         }

         properties.put("httpOnly", Boolean.TRUE);
         extContext.addResponseCookie(toRemove.getName(), toRemove.getValue(), !properties.isEmpty() ? properties : null);
         properties = null;
      }
   }

   private void releaseCurrentFlashManager(Map contextMap) {
      contextMap.remove(ELFlash.CONSTANTS.RequestFlashManager);
   }

   private PreviousNextFlashInfoManager getCurrentFlashManager(Map contextMap, boolean create) {
      PreviousNextFlashInfoManager result = (PreviousNextFlashInfoManager)contextMap.get(ELFlash.CONSTANTS.RequestFlashManager);
      if (null == result && create) {
         result = new PreviousNextFlashInfoManager(this.guard, this.flashInnerMap);
         result.initializeBaseCase(this);
         contextMap.put(ELFlash.CONSTANTS.RequestFlashManager, result);
      }

      return result;
   }

   private PreviousNextFlashInfoManager getCurrentFlashManager(FacesContext context, Map contextMap, Cookie cookie) {
      PreviousNextFlashInfoManager result = (PreviousNextFlashInfoManager)contextMap.get(ELFlash.CONSTANTS.RequestFlashManager);
      if (null == result) {
         result = new PreviousNextFlashInfoManager(this.guard, this.flashInnerMap);

         try {
            result.decode(context, this, cookie);
            contextMap.put(ELFlash.CONSTANTS.RequestFlashManager, result);
         } catch (InvalidKeyException var6) {
            contextMap.put(ELFlash.CONSTANTS.ForceSetMaxAgeZero, Boolean.TRUE);
            if (LOGGER.isLoggable(Level.SEVERE)) {
               result = this.getCurrentFlashManager(contextMap, true);
               LOGGER.log(Level.SEVERE, "jsf.externalcontext.flash.bad.cookie", new Object[]{var6.getMessage()});
            }
         }
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.FLASH.getLogger();
      ACT_AS_DO_LAST_PHASE_ACTIONS = ELFlash.class.getPackage().getName() + ".ACT_AS_DO_LAST_PHASE_ACTIONS";
   }

   private static final class FlashInfo {
      private boolean isRedirect;
      private LifetimeMarker lifetimeMarker;
      private long sequenceNumber;
      private Map flashMap;

      private FlashInfo() {
      }

      FlashInfo(long sequenceNumber, LifetimeMarker lifetimeMarker, boolean isRedirect) {
         this.setSequenceNumber(sequenceNumber);
         this.setLifetimeMarker(lifetimeMarker);
         this.setIsRedirect(isRedirect);
      }

      FlashInfo copyWithoutInnerMap() {
         FlashInfo result = new FlashInfo(this.sequenceNumber, this.lifetimeMarker, this.isRedirect);
         return result;
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            FlashInfo other = (FlashInfo)obj;
            if (this.isRedirect != other.isRedirect) {
               return false;
            } else if (this.lifetimeMarker != other.lifetimeMarker && (this.lifetimeMarker == null || !this.lifetimeMarker.equals(other.lifetimeMarker))) {
               return false;
            } else {
               return this.sequenceNumber == other.sequenceNumber;
            }
         }
      }

      public int hashCode() {
         int hash = 7;
         hash = 71 * hash + (this.isRedirect ? 1 : 0);
         hash = 71 * hash + (this.lifetimeMarker != null ? this.lifetimeMarker.hashCode() : 0);
         hash = 71 * hash + (int)(this.sequenceNumber ^ this.sequenceNumber >>> 32);
         return hash;
      }

      void decode(String value) {
         if (null != value && 0 != value.length()) {
            int i = value.indexOf(88);
            this.setSequenceNumber(Long.parseLong(value.substring(0, i++)));
            this.setLifetimeMarker(ELFlash.LifetimeMarker.decode(value.charAt(i++)));
            this.setIsRedirect(ELFlash.LifetimeMarker.IsRedirect == ELFlash.LifetimeMarker.decode(value.charAt(i++)));
         }
      }

      String encode() {
         String value = null;
         if (this.isIsRedirect()) {
            value = Long.toString(this.getSequenceNumber()) + "X" + this.getLifetimeMarker().encode() + ELFlash.LifetimeMarker.IsRedirect.encode();
         } else {
            value = Long.toString(this.getSequenceNumber()) + "X" + this.getLifetimeMarker().encode() + ELFlash.LifetimeMarker.IsNormal.encode();
         }

         return value;
      }

      boolean isIsRedirect() {
         return this.isRedirect;
      }

      void setIsRedirect(boolean isRedirect) {
         this.isRedirect = isRedirect;
      }

      long getSequenceNumber() {
         return this.sequenceNumber;
      }

      void setSequenceNumber(long sequenceNumber) {
         this.sequenceNumber = sequenceNumber;
      }

      LifetimeMarker getLifetimeMarker() {
         return this.lifetimeMarker;
      }

      void setLifetimeMarker(LifetimeMarker lifetimeMarker) {
         this.lifetimeMarker = lifetimeMarker;
      }

      Map getFlashMap() {
         return this.flashMap;
      }

      void setFlashMap(Map flashMap) {
         this.flashMap = flashMap;
      }

      // $FF: synthetic method
      FlashInfo(Object x0) {
         this();
      }
   }

   private static final class PreviousNextFlashInfoManager {
      private FlashInfo previousRequestFlashInfo;
      private FlashInfo nextRequestFlashInfo;
      private boolean incomingCookieCameFromRedirect;
      private Map innerMap;
      private ByteArrayGuardAESCTR guard;

      private PreviousNextFlashInfoManager(ByteArrayGuardAESCTR guard) {
         this.incomingCookieCameFromRedirect = false;
         this.guard = guard;
      }

      private PreviousNextFlashInfoManager(ByteArrayGuardAESCTR guard, Map innerMap) {
         this.incomingCookieCameFromRedirect = false;
         this.guard = guard;
         this.innerMap = innerMap;
      }

      protected PreviousNextFlashInfoManager copyWithoutInnerMap() {
         PreviousNextFlashInfoManager result = new PreviousNextFlashInfoManager(this.guard);
         result.innerMap = Collections.emptyMap();
         if (null != this.previousRequestFlashInfo) {
            result.previousRequestFlashInfo = this.previousRequestFlashInfo.copyWithoutInnerMap();
         }

         if (null != this.nextRequestFlashInfo) {
            result.nextRequestFlashInfo = this.nextRequestFlashInfo.copyWithoutInnerMap();
         }

         result.incomingCookieCameFromRedirect = this.incomingCookieCameFromRedirect;
         return result;
      }

      public String toString() {
         String result = null;
         result = "previousRequestSequenceNumber: " + (null != this.previousRequestFlashInfo ? this.previousRequestFlashInfo.getSequenceNumber() : "null") + " nextRequestSequenceNumber: " + (null != this.nextRequestFlashInfo ? this.nextRequestFlashInfo.getSequenceNumber() : "null");
         return result;
      }

      void initializeBaseCase(ELFlash flash) {
         Map flashMap = null;
         this.previousRequestFlashInfo = new FlashInfo(flash.getNewSequenceNumber(), ELFlash.LifetimeMarker.FirstTimeThru, false);
         this.innerMap.put(this.previousRequestFlashInfo.getSequenceNumber() + "", flashMap = new HashMap());
         this.previousRequestFlashInfo.setFlashMap(flashMap);
         this.nextRequestFlashInfo = new FlashInfo(flash.getNewSequenceNumber(), ELFlash.LifetimeMarker.FirstTimeThru, false);
         this.innerMap.put(this.nextRequestFlashInfo.getSequenceNumber() + "", flashMap = new HashMap());
         this.nextRequestFlashInfo.setFlashMap(flashMap);
      }

      void expirePrevious() {
         if (null != this.previousRequestFlashInfo) {
            Map flashMap;
            if (null != (flashMap = this.previousRequestFlashInfo.getFlashMap())) {
               if (ELFlash.LOGGER.isLoggable(Level.FINEST)) {
                  ELFlash.LOGGER.log(Level.FINEST, "{0} expire previous[{1}]", new Object[]{ELFlash.getLogPrefix(FacesContext.getCurrentInstance()), this.previousRequestFlashInfo.getSequenceNumber()});
               }

               FacesContext context = FacesContext.getCurrentInstance();
               context.getApplication().publishEvent(context, PreClearFlashEvent.class, flashMap);
               flashMap.clear();
            }

            this.innerMap.remove(this.previousRequestFlashInfo.getSequenceNumber() + "");
            this.previousRequestFlashInfo = null;
         }

      }

      void expireNext() {
         if (null != this.nextRequestFlashInfo) {
            Map flashMap;
            if (null != (flashMap = this.nextRequestFlashInfo.getFlashMap())) {
               if (ELFlash.LOGGER.isLoggable(Level.FINEST)) {
                  ELFlash.LOGGER.log(Level.FINEST, "{0} expire next[{1}]", new Object[]{ELFlash.getLogPrefix(FacesContext.getCurrentInstance()), this.nextRequestFlashInfo.getSequenceNumber()});
               }

               FacesContext context = FacesContext.getCurrentInstance();
               context.getApplication().publishEvent(context, PreClearFlashEvent.class, flashMap);
               flashMap.clear();
            }

            this.innerMap.remove(this.nextRequestFlashInfo.getSequenceNumber() + "");
            this.nextRequestFlashInfo = null;
         }

      }

      void expireNext_MovePreviousToNext() {
         if (null != this.nextRequestFlashInfo) {
            if (ELFlash.LOGGER.isLoggable(Level.FINEST)) {
               ELFlash.LOGGER.log(Level.FINEST, "{0} expire next[{1}], move previous to next", new Object[]{ELFlash.getLogPrefix(FacesContext.getCurrentInstance()), this.nextRequestFlashInfo.getSequenceNumber()});
            }

            Map flashMap = this.nextRequestFlashInfo.getFlashMap();
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().publishEvent(context, PreClearFlashEvent.class, flashMap);
            flashMap.clear();
            this.innerMap.remove(this.nextRequestFlashInfo.getSequenceNumber() + "");
            this.nextRequestFlashInfo = null;
         }

         this.nextRequestFlashInfo = this.previousRequestFlashInfo;
         this.previousRequestFlashInfo = null;
      }

      void decode(FacesContext context, ELFlash flash, Cookie cookie) throws InvalidKeyException {
         String urlDecodedValue = null;

         try {
            urlDecodedValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
         } catch (UnsupportedEncodingException var9) {
            urlDecodedValue = cookie.getValue();
         }

         String value = this.guard.decrypt(urlDecodedValue);

         try {
            int i = value.indexOf("_");
            String temp = value.substring(0, i++);
            if (0 < temp.length()) {
               this.nextRequestFlashInfo = new FlashInfo();
               this.nextRequestFlashInfo.decode(temp);
            }

            this.previousRequestFlashInfo = new FlashInfo();
            this.previousRequestFlashInfo.decode(value.substring(i));
            if (this.previousRequestFlashInfo.isIsRedirect()) {
               this.setIncomingCookieCameFromRedirect(true);
               this.previousRequestFlashInfo.setIsRedirect(false);
            } else if (!UIDebug.debugRequest(context)) {
               this.previousRequestFlashInfo.setLifetimeMarker(ELFlash.LifetimeMarker.SecondTimeThru);
               this.expireNext();
            }

            Object flashMap;
            if (null == (flashMap = (Map)this.innerMap.get(this.previousRequestFlashInfo.getSequenceNumber() + ""))) {
               this.previousRequestFlashInfo = new FlashInfo();
               this.previousRequestFlashInfo.setSequenceNumber(flash.getNewSequenceNumber());
               this.previousRequestFlashInfo.setLifetimeMarker(ELFlash.LifetimeMarker.FirstTimeThru);
               this.previousRequestFlashInfo.setIsRedirect(false);
               this.innerMap.put(this.previousRequestFlashInfo.getSequenceNumber() + "", flashMap = new HashMap());
            }

            this.previousRequestFlashInfo.setFlashMap((Map)flashMap);
            if (null != this.nextRequestFlashInfo) {
               if (null == (flashMap = (Map)this.innerMap.get(this.nextRequestFlashInfo.getSequenceNumber() + ""))) {
                  this.nextRequestFlashInfo = new FlashInfo();
                  this.nextRequestFlashInfo.setSequenceNumber(flash.getNewSequenceNumber());
                  this.nextRequestFlashInfo.setLifetimeMarker(ELFlash.LifetimeMarker.FirstTimeThru);
                  this.nextRequestFlashInfo.setIsRedirect(false);
                  this.innerMap.put(this.nextRequestFlashInfo.getSequenceNumber() + "", flashMap = new HashMap());
               }

               this.nextRequestFlashInfo.setFlashMap((Map)flashMap);
            }
         } catch (Throwable var10) {
            context.getAttributes().put(ELFlash.CONSTANTS.ForceSetMaxAgeZero, Boolean.TRUE);
            if (ELFlash.LOGGER.isLoggable(Level.SEVERE)) {
               ELFlash.LOGGER.log(Level.SEVERE, "jsf.externalcontext.flash.bad.cookie", new Object[]{value});
            }
         }

      }

      Cookie encode() {
         Cookie result = null;
         String value = (null != this.previousRequestFlashInfo ? this.previousRequestFlashInfo.encode() : "") + "_" + (null != this.nextRequestFlashInfo ? this.nextRequestFlashInfo.encode() : "");
         String encryptedValue = this.guard.encrypt(value);

         try {
            result = new Cookie("csfcfc", URLEncoder.encode(encryptedValue, "UTF-8"));
         } catch (UnsupportedEncodingException var5) {
            result = new Cookie("csfcfc", encryptedValue);
         }

         if (1 == value.length()) {
            result.setMaxAge(0);
         }

         String requestContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
         if (requestContextPath.isEmpty()) {
            requestContextPath = "/";
         }

         result.setPath(requestContextPath);
         return result;
      }

      FlashInfo getPreviousRequestFlashInfo() {
         return this.previousRequestFlashInfo;
      }

      void setPreviousRequestFlashInfo(FlashInfo thisRequestFlashInfo) {
         this.previousRequestFlashInfo = thisRequestFlashInfo;
      }

      FlashInfo getNextRequestFlashInfo() {
         return this.nextRequestFlashInfo;
      }

      FlashInfo getNextRequestFlashInfo(ELFlash flash, boolean create) {
         if (create && null == this.nextRequestFlashInfo) {
            this.nextRequestFlashInfo = new FlashInfo();
            this.nextRequestFlashInfo.setSequenceNumber(flash.getNewSequenceNumber());
            this.nextRequestFlashInfo.setLifetimeMarker(ELFlash.LifetimeMarker.FirstTimeThru);
            this.nextRequestFlashInfo.setIsRedirect(false);
            Map flashMap = null;
            this.innerMap.put(this.nextRequestFlashInfo.getSequenceNumber() + "", flashMap = new HashMap());
            this.nextRequestFlashInfo.setFlashMap(flashMap);
         }

         return this.nextRequestFlashInfo;
      }

      void setNextRequestFlashInfo(FlashInfo nextRequestFlashInfo) {
         this.nextRequestFlashInfo = nextRequestFlashInfo;
      }

      boolean isIncomingCookieCameFromRedirect() {
         return this.incomingCookieCameFromRedirect;
      }

      void setIncomingCookieCameFromRedirect(boolean incomingCookieCameFromRedirect) {
         this.incomingCookieCameFromRedirect = incomingCookieCameFromRedirect;
      }

      // $FF: synthetic method
      PreviousNextFlashInfoManager(ByteArrayGuardAESCTR x0, Map x1, Object x2) {
         this(x0, x1);
      }
   }

   private static enum LifetimeMarker {
      FirstTimeThru("f"),
      SecondTimeThru("s"),
      IsRedirect("r"),
      IsNormal("n");

      private static char FIRST_TIME_THRU = 'f';
      private static char SECOND_TIME_THRU = 's';
      private static char IS_REDIRECT = 'r';
      private static char IS_NORMAL = 'n';
      private String name;

      private LifetimeMarker(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      public char encode() {
         return this.name.charAt(0);
      }

      public static LifetimeMarker decode(char c) {
         LifetimeMarker result = FirstTimeThru;
         if (FIRST_TIME_THRU == c) {
            result = FirstTimeThru;
         } else if (SECOND_TIME_THRU == c) {
            result = SecondTimeThru;
         } else if (IS_REDIRECT == c) {
            result = IsRedirect;
         } else {
            if (IS_NORMAL != c) {
               throw new IllegalStateException("class invariant failed: invalid lifetime marker");
            }

            result = IsNormal;
         }

         return result;
      }
   }

   private static enum CONSTANTS {
      RequestFlashManager,
      SavedResponseCompleteFlagValue,
      FacesMessageAttributeName,
      KeepAllMessagesAttributeName,
      KeepFlagAttributeName,
      DidWriteCookieAttributeName,
      ForceSetMaxAgeZero;
   }
}
