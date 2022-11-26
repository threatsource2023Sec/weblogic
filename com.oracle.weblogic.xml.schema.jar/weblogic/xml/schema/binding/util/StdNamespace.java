package weblogic.xml.schema.binding.util;

public abstract class StdNamespace {
   private static StdNamespace stdNamespace = newInstance();
   public static final SoapVersion SOAP11 = new SoapVersion();
   public static final SoapVersion SOAP12 = new SoapVersion();

   StdNamespace() {
   }

   public static StdNamespace instance() {
      return stdNamespace;
   }

   public abstract String soapEnvelope();

   public abstract String soapEncoding();

   public abstract String schema();

   public abstract String schemaInstance();

   public abstract String wsdl();

   public abstract String wsdlSoap();

   public abstract SoapVersion soapVersion();

   private static StdNamespace newInstance() {
      try {
         String version = System.getProperty("weblogic.webservice.soap.version");
         if (version != null && !"1.1".equals(version)) {
            if ("1.2".equals(version)) {
               System.out.println("NOTICE: setting SOAP version to: 1.2");
               return new Soap12Namespace();
            } else {
               System.out.println("WARNING: unknown soap version:" + version);
               System.out.println("WARNING: using SOAP 1.1 instead");
               return new Soap11Namespace();
            }
         } else {
            return new Soap11Namespace();
         }
      } catch (Throwable var1) {
         System.out.println("WARNING: unable to find soap version:" + var1);
         System.out.println("WARNING: using SOAP 1.1 instead");
         return new Soap11Namespace();
      }
   }

   public static class Soap12Namespace extends Soap11Namespace {
      public String soapEnvelope() {
         return "http://www.w3.org/2003/05/soap-envelope";
      }

      public String soapEncoding() {
         return "http://www.w3.org/2003/05/soap-encoding";
      }

      public SoapVersion soapVersion() {
         return SOAP12;
      }
   }

   public static class Soap11Namespace extends StdNamespace {
      public String soapEnvelope() {
         return "http://schemas.xmlsoap.org/soap/envelope/";
      }

      public String soapEncoding() {
         return "http://schemas.xmlsoap.org/soap/encoding/";
      }

      public String schema() {
         return "http://www.w3.org/2001/XMLSchema";
      }

      public String schemaInstance() {
         return "http://www.w3.org/2001/XMLSchema-instance";
      }

      public String wsdl() {
         return "http://schemas.xmlsoap.org/wsdl/";
      }

      public String wsdlSoap() {
         return "http://schemas.xmlsoap.org/wsdl/soap/";
      }

      public SoapVersion soapVersion() {
         return SOAP11;
      }
   }

   public static class SoapVersion {
   }
}
