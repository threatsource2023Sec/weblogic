package com.bea.security.saml2.binding.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.SynchronousBindingClient;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.w3c.dom.Element;

public class SoapSynchronousBindingClient implements SynchronousBindingClient {
   private static final String LOGGING_PREFIX = "SoapSynchronousBindingClient.";
   protected LoggerSpi log;
   protected SAML2ConfigSpi config;
   private HttpURLConnection connection;

   public SoapSynchronousBindingClient(SAML2ConfigSpi config, URLConnection connection) {
      this.config = config;
      this.connection = (HttpURLConnection)connection;
      this.log = config.getLogger();
   }

   public SAMLObject sendAndReceive(SAMLObject object) throws BindingHandlerException {
      boolean debug = this.log.isDebugEnabled();
      String method = "SoapSynchronousBindingClient.sendAndReceive: ";

      try {
         if (debug) {
            this.log.debug(method + "begin to send SAMLObject to server.");
         }

         Envelope envelope = BindingUtil.buildEnvelope(object);
         Element soapElement = BindingUtil.marshallXMLObject(envelope);
         OutputStream out = this.connection.getOutputStream();
         out.write(BindingUtil.transformNode(this.config, soapElement));
         out.flush();
         if (debug) {
            this.log.debug(method + "sending completed, now waiting for server response.");
         }

         InputStream in = this.connection.getInputStream();
         if (debug) {
            this.log.debug(method + "response code from server is: " + this.connection.getResponseCode());
         }

         if (this.connection.getResponseCode() == 200) {
            if (debug) {
               this.log.debug(method + "get a HTTP_OK response, now receive a SOAP envelope message.");
            }

            Envelope env = (Envelope)BindingUtil.unmarshall(in);
            if (env.getBody().getOrderedChildren().size() > 0) {
               if (debug) {
                  this.log.debug(method + "found XMLObject in envelope, return it.");
               }

               return (SAMLObject)env.getBody().getOrderedChildren().get(0);
            }
         } else {
            if (this.connection.getResponseCode() == 500) {
               if (debug) {
                  this.log.debug(method + "get a HTTP_INTERNAL_ERROR response.");
               }

               String errorMessage = "HTTP Internal error.";
               Envelope env = (Envelope)BindingUtil.unmarshall(in);
               if (env != null && env.getBody().getOrderedChildren().size() > 0) {
                  Fault fault = (Fault)env.getBody().getOrderedChildren().get(0);
                  if (fault.getMessage() != null) {
                     errorMessage = fault.getMessage().getValue();
                     if (debug) {
                        this.log.debug(method + "get a SOAP Fault from envelope, with error message:" + errorMessage);
                     }
                  }
               }

               throw new BindingHandlerException(errorMessage, 500);
            }

            if (this.connection.getResponseCode() == 403) {
               if (debug) {
                  this.log.debug(method + "get a HTTP_FORBIDDEN response.");
               }

               throw new BindingHandlerException(403);
            }

            if (this.connection.getResponseCode() == 401) {
               if (debug) {
                  this.log.debug(method + "get a HTTP_UNAUTHORIZED response.");
               }

               throw new BindingHandlerException(401);
            }
         }

         return null;
      } catch (BindingHandlerException var11) {
         throw var11;
      } catch (Exception var12) {
         if (debug) {
            this.log.debug(method + "Exception occurs.", var12);
         }

         throw new BindingHandlerException("sendAndReceive - error occurred: " + var12.getMessage(), var12, 500);
      }
   }
}
