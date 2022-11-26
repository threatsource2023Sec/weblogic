package org.opensaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import org.w3c.dom.Element;
import weblogic.utils.encoders.BASE64Decoder;

public class SAMLPOSTProfile {
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();
   private static TreeMap replayExpMap = new TreeMap();
   private static HashSet replayCache = new HashSet();

   private static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0);
      }

   }

   public static SAMLAssertion getSSOAssertion(SAMLResponse var0, Collection var1) throws SAMLException {
      int var2 = 0;
      boolean var3 = false;
      Iterator var4 = var0.getAssertions();

      while(true) {
         label74:
         while(true) {
            SAMLAssertion var5;
            Date var6;
            Date var7;
            do {
               do {
                  if (!var4.hasNext()) {
                     if (var3 && var2 == 1) {
                        throw new ExpiredAssertionException(SAMLException.RESPONDER, "SAMLPOSTProfile.getSSOAssertion() unable to find a SSO assertion with valid time condition");
                     }

                     throw new FatalProfileException(SAMLException.RESPONDER, "SAMLPOSTProfile.getSSOAssertion() unable to find a valid SSO assertion");
                  }

                  ++var2;
                  var3 = false;
                  var5 = (SAMLAssertion)var4.next();
                  var6 = var5.getNotBefore();
                  var7 = var5.getNotOnOrAfter();
               } while(var6 == null);
            } while(var7 == null);

            if (var6.getTime() - 300000L > System.currentTimeMillis()) {
               var3 = true;
            } else if (var7.getTime() + 300000L <= System.currentTimeMillis()) {
               var3 = true;
            } else {
               Iterator var8 = var5.getConditions();

               while(var8.hasNext()) {
                  SAMLCondition var9 = (SAMLCondition)var8.next();
                  if (!(var9 instanceof SAMLAudienceRestrictionCondition) || !((SAMLAudienceRestrictionCondition)var9).eval(var1)) {
                     continue label74;
                  }
               }

               Iterator var13 = var5.getStatements();

               while(true) {
                  SAMLStatement var10;
                  do {
                     if (!var13.hasNext()) {
                        continue label74;
                     }

                     var10 = (SAMLStatement)var13.next();
                  } while(!(var10 instanceof SAMLAuthenticationStatement));

                  SAMLSubject var11 = ((SAMLAuthenticationStatement)var10).getSubject();
                  Iterator var12 = var11.getConfirmationMethods();

                  while(var12.hasNext()) {
                     if (((String)var12.next()).equals("urn:oasis:names:tc:SAML:1.0:cm:bearer")) {
                        return var5;
                     }
                  }
               }
            }
         }
      }
   }

   public static SAMLAuthenticationStatement getSSOStatement(SAMLAssertion var0) throws SAMLException {
      Iterator var1 = var0.getStatements();

      while(true) {
         SAMLStatement var2;
         do {
            if (!var1.hasNext()) {
               throw new FatalProfileException(SAMLException.RESPONDER, "SAMLPOSTProfile.getSSOStatement() unable to find a valid SSO statement");
            }

            var2 = (SAMLStatement)var1.next();
         } while(!(var2 instanceof SAMLAuthenticationStatement));

         SAMLSubject var3 = ((SAMLAuthenticationStatement)var2).getSubject();
         Iterator var4 = var3.getConfirmationMethods();

         while(var4.hasNext()) {
            if (((String)var4.next()).equals("urn:oasis:names:tc:SAML:1.0:cm:bearer")) {
               return (SAMLAuthenticationStatement)var2;
            }
         }
      }
   }

   public static synchronized boolean checkReplayCache(SAMLAssertion var0) {
      Set var1 = replayExpMap.headMap(new Date()).keySet();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         replayCache.remove(replayExpMap.get(var2.next()));
      }

      var1.clear();
      if (!replayCache.add(var0.getId())) {
         return false;
      } else {
         Date var3 = new Date(var0.getNotOnOrAfter().getTime() + 300000L);

         while(replayExpMap.containsKey(var3)) {
            var3.setTime(var3.getTime() + 1L);
         }

         replayExpMap.put(var3, var0.getId());
         return true;
      }
   }

   public static SAMLResponse accept(byte[] var0, String var1, int var2, boolean var3) throws SAMLException {
      try {
         SAMLResponse var4 = new SAMLResponse(new ByteArrayInputStream((new BASE64Decoder()).decodeBuffer(new ByteArrayInputStream(var0))));
         if (var3) {
            process(var4, var1, var2);
         }

         return var4;
      } catch (IOException var5) {
         throw new InvalidAssertionException(SAMLException.REQUESTER, "SAMLPOSTProfile.accept() unable to decode base64 response - " + var5.toString());
      }
   }

   public static void process(SAMLResponse var0, String var1, int var2) throws SAMLException {
      if (var1 != null && var1.length() != 0 && var1.equals(var0.getRecipient())) {
         if (var0.getIssueInstant().getTime() + (long)(1000 * var2) + 300000L < System.currentTimeMillis()) {
            throw new ExpiredAssertionException(SAMLException.RESPONDER, "SAMLPOSTProfile.accept() detected expired response");
         }
      } else {
         throw new InvalidAssertionException(SAMLException.REQUESTER, "SAMLPOSTProfile.accept() detected recipient mismatch: " + var0.getRecipient());
      }
   }

   /** @deprecated */
   public static SAMLResponse prepare(String var0, String var1, Collection var2, String var3, String var4, String var5, String var6, String var7, Date var8, Collection var9) throws SAMLException {
      return prepare(var0, var1, var2, new SAMLNameIdentifier(var3, var4, var5), var6, var7, var8, var9);
   }

   public static SAMLResponse prepare(String var0, String var1, Collection var2, SAMLNameIdentifier var3, String var4, String var5, Date var6, Collection var7) throws SAMLException {
      logDebug("Creating SAML Response.");
      if (var0 != null && var0.length() != 0) {
         Vector var8 = new Vector(1);
         if (var2 != null && var2.size() > 0) {
            var8.add(new SAMLAudienceRestrictionCondition(var2));
         }

         String[] var9 = new String[]{"urn:oasis:names:tc:SAML:1.0:cm:bearer"};
         SAMLSubject var10 = new SAMLSubject(var3, Arrays.asList(var9), (Element)null, (Object)null);
         SAMLStatement[] var11 = new SAMLStatement[]{new SAMLAuthenticationStatement(var10, var5, var6, var4, (String)null, var7)};
         SAMLAssertion[] var12 = new SAMLAssertion[]{new SAMLAssertion(var1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 300000L), var8, (Collection)null, Arrays.asList(var11))};
         return new SAMLResponse((String)null, var0, Arrays.asList(var12), (SAMLException)null);
      } else {
         throw new SAMLException(SAMLException.RESPONDER, "SAMLPOSTProfile.prepare() requires recipient");
      }
   }
}
