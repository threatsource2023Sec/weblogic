package com.bea.common.security.service;

public abstract class SAML2PublishException extends Exception {
   private SAML2PublishException() {
   }

   private SAML2PublishException(String msg) {
      super(msg);
   }

   private SAML2PublishException(Exception e) {
      super(e);
   }

   private SAML2PublishException(String msg, Exception e) {
      super(msg, e);
   }

   // $FF: synthetic method
   SAML2PublishException(String x0, Object x1) {
      this(x0);
   }

   // $FF: synthetic method
   SAML2PublishException(String x0, Exception x1, Object x2) {
      this(x0, x1);
   }

   public static class MetadataXMLException extends SAML2PublishException {
      public MetadataXMLException(String msg, Exception e) {
         super(msg, e, null);
      }
   }

   public static class InvalidConfigException extends SAML2PublishException {
      public InvalidConfigException(String msg) {
         super(msg, (<undefinedtype>)null);
      }
   }

   public static class NotEnabledException extends SAML2PublishException {
      public NotEnabledException(String msg) {
         super(msg, (<undefinedtype>)null);
      }
   }

   public static class FileNotFoundException extends SAML2PublishException {
      public FileNotFoundException(String msg, Exception e) {
         super(msg, e, null);
      }
   }

   public static class FileCreateException extends SAML2PublishException {
      public FileCreateException(String msg, Exception e) {
         super(msg, e, null);
      }
   }

   public static class OverwriteProhibitedException extends SAML2PublishException {
      public OverwriteProhibitedException(String msg) {
         super(msg, (<undefinedtype>)null);
      }
   }
}
