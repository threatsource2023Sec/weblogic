package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.common.store.service.StoreService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.utils.BusinessObjectListerManager;
import weblogic.security.providers.utils.GenericEntryConverter;
import weblogic.security.providers.utils.ListerManager;
import weblogic.security.providers.utils.Utils;
import weblogic.security.providers.utils.UtilsJAXP;
import weblogic.security.spi.SecurityServices;
import weblogic.utils.XXEUtils;

public class SAMLPartnerRegHelper {
   private static final String FORMAT_XML_REGISTRY = "XML Partner Registry";
   private static final String FORMAT_KEYSTORE = "JKS KeyStore";
   private static final String FORMAT_LDIFTEMPLATE = "LDIF Template";
   private static final String PASSWORDS_CONSTRAINT = "passwords";
   private static final String CLEAR_PASSWORDS = "cleartext";
   private static final String DSIG_SCHEMA_PATH = "opensaml/schemas/xmldsig-core-schema.xsd";
   private static final String SAML_PARTNER_RIGISTRY_SCHEMA_PATH = "saml-partner-registry.xsd";
   private LoggerSpi log = null;
   private String registryName = null;
   private SecurityServices securityServices = null;
   private StoreService storeService = null;
   private BootStrapService bootStrapService = null;
   private LegacyEncryptorSpi legacyEncryptor = null;
   private JAXPFactoryService jaxpFactoryService = null;
   private BusinessObjectListerManager listerManager;
   private Validator xmlSchemaValidator;

   protected boolean isDebug() {
      return this.log == null ? false : this.log.isDebugEnabled();
   }

   private void debug(String method, String info) {
      if (this.log != null) {
         String msg = this.registryName + "." + method + ": " + info;
         if (this.log.isDebugEnabled()) {
            this.log.debug(msg);
         }

      }
   }

   public SAMLPartnerRegHelper(LoggerSpi log, String registryName, SecurityServices securityServices) {
      String method = "constructor";
      this.log = log;
      this.registryName = registryName;
      this.securityServices = securityServices;
      this.storeService = Utils.getStoreService(securityServices);
      this.bootStrapService = Utils.getBootStrapService(securityServices);
      this.legacyEncryptor = Utils.getLegacyEncryptorSpi(securityServices);
      this.jaxpFactoryService = UtilsJAXP.getJAXPFactoryService(securityServices);
      this.listerManager = new BusinessObjectListerManager();
      if (this.isDebug()) {
         this.debug(method, "succeeded.");
      }

   }

   private Validator getXMLSchemaValidator() {
      String method = "getXMLSchemaValidator";
      if (this.xmlSchemaValidator == null) {
         SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         StreamSource ss = null;

         try {
            ErrorHandler handler = new myErrorHandler(this.log, this.registryName, method);
            sf.setErrorHandler(handler);
            InputStream schemaInputStream = this.getClass().getClassLoader().getResourceAsStream("saml-partner-registry.xsd");
            if (schemaInputStream == null) {
               throw new RuntimeException(ProvidersLogger.getSAMLCouldNotLoadPartnerRegistryFile("saml-partner-registry.xsd"));
            }

            ss = new StreamSource(schemaInputStream);
            Schema xmlSchema = sf.newSchema(ss);
            this.xmlSchemaValidator = XXEUtils.createValidator(xmlSchema);
         } catch (SAXParseException var7) {
            return this.getLocalXMLSchemaValidator();
         } catch (SAXException var8) {
            throw new RuntimeException(ProvidersLogger.getSAMLCouldNotGeneratePartnerRegistryFile(var8.getMessage()));
         }
      }

      return this.xmlSchemaValidator;
   }

   private Validator getLocalXMLSchemaValidator() {
      String method = "getLocalXMLSchemaValidator";
      if (this.xmlSchemaValidator == null) {
         SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         StreamSource ss = null;

         try {
            ErrorHandler handler = new myBackupErrorHandler(this.log, this.registryName, method);
            sf.setErrorHandler(handler);
            InputStream dsig = this.getClass().getClassLoader().getResourceAsStream("opensaml/schemas/xmldsig-core-schema.xsd");
            if (dsig == null) {
               throw new RuntimeException("Can not load dsig core schema file");
            }

            InputStream myschema = this.getClass().getClassLoader().getResourceAsStream("saml-partner-registry.xsd");
            if (myschema == null) {
               throw new RuntimeException("Can not load SAML partner registry schema file");
            }

            StreamSource[] sources = new StreamSource[]{new StreamSource(dsig), new StreamSource(myschema)};
            Schema xmlSchema = sf.newSchema(sources);
            this.xmlSchemaValidator = XXEUtils.createValidator(xmlSchema);
         } catch (SAXException var9) {
            if (this.isDebug()) {
               this.debug(method, " Can not load SAML partner registry schema file and/or dsig core schema file: " + var9.getMessage());
            }

            throw new RuntimeException(" Can not load SAML partner registry schema file and/or dsig core schema file: " + var9.getMessage());
         }
      }

      return this.xmlSchemaValidator;
   }

   public String listBegin(Object delegate, String wildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      String method = "listBegin";
      String rtn = null;
      if (delegate instanceof SAMLCertRegLDAPDelegate) {
         SAMLCertRegLDAPDelegate del = (SAMLCertRegLDAPDelegate)delegate;
         if (this.isDebug()) {
            this.debug(method, "listing certificates: wildcard=" + wildcard + ", maxToReturn=" + maxToReturn);
         }

         rtn = del.listCertificates(this.listerManager, wildcard, maxToReturn);
      } else {
         if (!(delegate instanceof SAMLPartnerRegistry)) {
            throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAliasWildcard());
         }

         SAMLPartnerRegistry del = (SAMLPartnerRegistry)delegate;
         if (this.isDebug()) {
            this.debug(method, "listing partners: wildcard=" + wildcard + ", maxToReturn=" + maxToReturn);
         }

         rtn = del.listPartners(this.listerManager, wildcard, maxToReturn);
      }

      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public boolean listHaveCurrent(String cursor) throws InvalidCursorException {
      String method = "listHaveCurrent";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      boolean rtn = ListerManager.haveCurrent(cursor);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public String listGetCurrentName(String cursor) throws InvalidCursorException {
      String method = "listGetCurrentName";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      String rtn = (String)this.listerManager.getCurrentBusinessObject(cursor);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public void listAdvance(String cursor) throws InvalidCursorException {
      String method = "listAdvance";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      ListerManager.advance(cursor);
   }

   public void listClose(String cursor) throws InvalidCursorException {
      String method = "listClose";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      ListerManager.close(cursor);
   }

   private static void validateParams(String format, String filename, Properties constraints) throws InvalidParameterException {
      validateFormat(format);
      validateFilename(filename);
      validateConstraints(constraints);
   }

   private static void validateFormat(String format) throws InvalidParameterException {
      if (format == null || !format.equals("XML Partner Registry") && !format.equals("JKS KeyStore") && !format.equals("LDIF Template")) {
         throw new InvalidParameterException(ProvidersLogger.getSAMLInvalidImpExpFormat());
      }
   }

   private static void validateFilename(String filename) throws InvalidParameterException {
      if (filename == null || filename.length() < 1) {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullFileName());
      }
   }

   private static void validateConstraints(Properties constraints) throws InvalidParameterException {
   }

   public void importData(SAMLCertRegLDAPDelegate certReg, SAMLPartnerRegistry partnerReg, String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      validateParams(format, filename, constraints);
      if (format.equals("JKS KeyStore")) {
         certReg.importData(format, filename, (Properties)null);
      } else {
         if (!format.equals("XML Partner Registry")) {
            throw new InvalidParameterException(SecurityLogger.getInvalidFormat(format));
         }

         this.importDataXMLRegistry(partnerReg, certReg, filename, constraints);
      }

   }

   public void exportData(SAMLCertRegLDAPDelegate certReg, SAMLPartnerRegistry partnerReg, String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      validateParams(format, filename, constraints);
      if (format.equalsIgnoreCase("LDIF Template")) {
         this.exportPartnerRegistryDataLDIF(partnerReg, certReg, filename, constraints);
      } else if (format.equals("JKS KeyStore")) {
         certReg.exportData(format, filename, (Properties)null);
      } else if (format.equals("XML Partner Registry")) {
         this.exportDataXMLRegistry(partnerReg, certReg, filename, constraints);
      }

   }

   private void importCertificate(SAMLCertRegLDAPDelegate certReg, SAMLPartnerCertificate partnerCertificate, ImportExportConstraints constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "importCertificate";
      String alias = partnerCertificate.getAlias();
      X509Certificate x509Certificate = partnerCertificate.getCert();
      if (constraints.isUpdateMode() && certReg.aliasExists(alias)) {
         this.debug(method, "update certificate with alias: " + alias);

         try {
            certReg.unregisterCertificate(alias);
         } catch (NotFoundException var8) {
         }
      }

      this.debug(method, "register certificate with alias: " + alias);
      certReg.registerCertificate(alias, x509Certificate);
   }

   private void validateCertificateAlias(SAMLCertRegLDAPDelegate certReg, String alias) throws InvalidParameterException {
      if (alias != null && alias.length() > 0 && !certReg.aliasExists(alias)) {
         throw new InvalidParameterException(ProvidersLogger.getSAMLNoCertForAlias(alias));
      }
   }

   private void validateCertificateAliasReferences(SAMLCertRegLDAPDelegate certReg, SAMLPartnerEntry partnerEntry) throws InvalidParameterException {
      String alias;
      if (partnerEntry instanceof SAMLAssertingPartyEntry) {
         SAMLAssertingPartyEntry assertingParty = (SAMLAssertingPartyEntry)partnerEntry;
         alias = assertingParty.getAssertionSigningCertAlias();
         this.validateCertificateAlias(certReg, alias);
         alias = assertingParty.getProtocolSigningCertAlias();
         this.validateCertificateAlias(certReg, alias);
      } else if (partnerEntry instanceof SAMLRelyingPartyEntry) {
         SAMLRelyingPartyEntry relyingParty = (SAMLRelyingPartyEntry)partnerEntry;
         alias = relyingParty.getSSLClientCertAlias();
         this.validateCertificateAlias(certReg, alias);
      }

   }

   private void savePartnerEntry(SAMLPartnerRegistry partnerReg, SAMLPartnerEntry partner, SAMLCertRegLDAPDelegate certReg, boolean addOrUpdate, boolean addWithRename) throws InvalidParameterException, CreateException, NotFoundException {
      String method = "savePartnerEntry";
      if (partner.isEnabled()) {
         try {
            this.validateCertificateAliasReferences(certReg, partner);
            this.debug(method, "Certificate alias reference validation passed");
            if (addOrUpdate) {
               partnerReg.addPartner(partner, addWithRename);
            } else {
               partnerReg.updatePartner(partner);
            }

            this.debug(method, "Save partner " + partner.getPartnerId() + " success");
            return;
         } catch (InvalidParameterException var8) {
            this.debug(method, "Save enabled partner failed, InvalidParameterException: " + var8.getMessage());
            this.debug(method, "Disable the partner and try to save it");
            partner.setEnabled(false);
         }
      }

      if (addOrUpdate) {
         partnerReg.addPartner(partner, addWithRename);
      } else {
         partnerReg.updatePartner(partner);
      }

      this.debug(method, "Save partner " + partner.getPartnerId() + " success");
   }

   private void importPartnerEntry(SAMLPartnerRegistry partnerReg, SAMLPartnerEntry partner, ImportExportConstraints constraints, SAMLCertRegLDAPDelegate certReg) throws InvalidParameterException, ErrorCollectionException {
      String method = "importPartnerEntry";
      String partnerId = partner.getPartnerId();

      try {
         if (!partnerReg.partnerExists(partnerId)) {
            this.debug(method, "add partner with partnerId: " + partnerId);
            this.savePartnerEntry(partnerReg, partner, certReg, true, false);
         } else {
            if (constraints.isFailMode()) {
               String errorMessage = ProvidersLogger.getSAMLImportTerminateInFailMode(partnerId);
               this.debug(method, errorMessage);
               throw new ErrorCollectionException(errorMessage);
            }

            if (constraints.isRenameMode()) {
               this.debug(method, "Renaming the partner before added to the registry");
               this.savePartnerEntry(partnerReg, partner, certReg, true, true);
            } else if (constraints.isUpdateMode()) {
               this.debug(method, "Update the partner entry with the same Id: " + partnerId);
               this.savePartnerEntry(partnerReg, partner, certReg, false, true);
            }
         }

      } catch (CreateException var8) {
         this.debug(method, "CreationException: " + var8.getMessage());
         throw new ErrorCollectionException(var8);
      } catch (NotFoundException var9) {
         this.debug(method, "CreationException: " + var9.getMessage());
         throw new ErrorCollectionException(var9);
      } catch (InvalidParameterException var10) {
         this.debug(method, "InvalidParameterException: " + var10.getMessage());
         throw var10;
      }
   }

   private void importDataXMLRegistry(SAMLPartnerRegistry partnerReg, SAMLCertRegLDAPDelegate certReg, String fileName, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "importDataXMLRegistry";
      if (fileName != null && fileName.length() != 0) {
         File file = new File(fileName);
         if (!file.isDirectory() && file.canRead() && file.length() != 0L) {
            DocumentBuilderFactory dbf = this.jaxpFactoryService.newDocumentBuilderFactory();
            dbf.setNamespaceAware(true);
            Document dom = null;

            try {
               DocumentBuilder db = dbf.newDocumentBuilder();
               dom = db.parse(file);
               this.debug(method, "XML docuement parse success");
               this.getXMLSchemaValidator().validate(new DOMSource(dom));
               this.debug(method, "XML document validation success");
            } catch (ParserConfigurationException var19) {
               this.debug(method, "ParseConfigurationException: " + var19.getMessage());
               throw new ErrorCollectionException(var19);
            } catch (SAXException var20) {
               this.debug(method, "SAXException: " + var20.getMessage());
               throw new ErrorCollectionException(var20);
            } catch (IOException var21) {
               this.debug(method, "IOException: " + var21.getMessage());
               throw new ErrorCollectionException(var21);
            }

            ImportExportConstraints importExportConstraints = new ImportExportConstraints(constraints);
            String partnerElementStr;
            if (partnerReg instanceof SAMLAssertingPartyRegistry) {
               partnerElementStr = "AssertingParty";
            } else {
               partnerElementStr = "RelyingParty";
            }

            NodeList partnerEntryNodeList = dom.getElementsByTagNameNS("urn:bea:security:saml:1.1:partner-registry", partnerElementStr);
            SAMLPartnerEntry[] partnerEntries = this.resolveImportPartners(partnerReg, partnerEntryNodeList, importExportConstraints);
            NodeList partnerCertificateNodeList = dom.getElementsByTagNameNS("urn:bea:security:saml:1.1:partner-registry", "PartnerCertificate");
            SAMLPartnerCertificate[] partnerCertificates = this.getPartnerCertificates(partnerCertificateNodeList);
            SAMLPartnerCertificate[] importPartnerCertificates = this.resolveImportPartnerCertificates(partnerCertificates, importExportConstraints, certReg, partnerEntries);
            SAMLPartnerCertificate[] partnerCertificatesWithoutConflicts = this.resolveImportNameConflicts(partnerReg, partnerEntries, certReg, importPartnerCertificates, importExportConstraints);
            int i;
            if (partnerCertificatesWithoutConflicts != null) {
               this.debug(method, "Import partner certificates, total " + importPartnerCertificates.length);

               for(i = 0; i < partnerCertificatesWithoutConflicts.length; ++i) {
                  this.importCertificate(certReg, partnerCertificatesWithoutConflicts[i], importExportConstraints);
               }
            }

            if (partnerEntries != null) {
               this.debug(method, "Import partner entries, total " + partnerEntries.length);

               for(i = 0; i < partnerEntries.length; ++i) {
                  this.importPartnerEntry(partnerReg, partnerEntries[i], importExportConstraints, certReg);
               }
            }

         } else {
            throw new InvalidParameterException(SecurityLogger.getUnableToReadFile(file.getAbsolutePath()));
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullFileName());
      }
   }

   private SAMLPartnerCertificate[] getPartnerCertificates(NodeList partnerCertificateNodeList) throws InvalidParameterException, ErrorCollectionException {
      String method = "getPartnerCertificates";
      if (partnerCertificateNodeList != null && partnerCertificateNodeList.getLength() != 0) {
         int length = partnerCertificateNodeList.getLength();
         SAMLPartnerCertificate[] partnerCertificates = new SAMLPartnerCertificate[length];

         for(int i = 0; i < length; ++i) {
            partnerCertificates[i] = new SAMLPartnerCertificate(this.log, (Element)partnerCertificateNodeList.item(i));
         }

         return partnerCertificates;
      } else {
         this.debug(method, "Partner Certificate node list is empty.");
         return new SAMLPartnerCertificate[0];
      }
   }

   private SAMLPartnerCertificate[] resolveImportPartnerCertificates(SAMLPartnerCertificate[] partnerCertificates, ImportExportConstraints constraints, SAMLCertRegLDAPDelegate certReg, SAMLPartnerEntry[] partnerEntries) throws InvalidParameterException, ErrorCollectionException {
      String method = "resolveImportPartnerCertificates";
      SAMLPartnerCertificate[] resolvedPartnerCertificates = null;
      int constraintType = constraints.getCertificateConstraintType();
      if (constraintType == 1) {
         resolvedPartnerCertificates = new SAMLPartnerCertificate[0];
      } else if (constraintType == 0) {
         resolvedPartnerCertificates = partnerCertificates;
      } else {
         Set aliasSet = null;
         if (constraintType == 3) {
            aliasSet = constraints.getCertificateAliases();
         } else if (constraintType == 2) {
            aliasSet = referencedCertificateAliases(partnerEntries);
         }

         int length = partnerCertificates.length;
         ArrayList arrayList = new ArrayList();

         for(int i = 0; i < length; ++i) {
            String alias = partnerCertificates[i].getAlias();
            if (aliasSet.contains(alias)) {
               arrayList.add(partnerCertificates[i]);
               aliasSet.remove(alias);
            }
         }

         if (!aliasSet.isEmpty()) {
            String errorMessage = ProvidersLogger.getSAMLImportFailForCouldNotLocateFromFile("certificate", aliasSet.toString());
            this.debug(method, errorMessage);
            throw new ErrorCollectionException(errorMessage);
         }

         resolvedPartnerCertificates = (SAMLPartnerCertificate[])((SAMLPartnerCertificate[])arrayList.toArray(new SAMLPartnerCertificate[arrayList.size()]));
      }

      this.debug(method, "SAML Partner Certificates, total: " + resolvedPartnerCertificates.length);
      return resolvedPartnerCertificates;
   }

   private SAMLPartnerCertificate[] resolveImportNameConflicts(SAMLPartnerRegistry partnerReg, SAMLPartnerEntry[] partnerEntries, SAMLCertRegLDAPDelegate certReg, SAMLPartnerCertificate[] partnerCertificates, ImportExportConstraints constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "resolveImportNameConflicts";
      if (constraints.isFailMode()) {
         for(int i = 0; i < partnerEntries.length; ++i) {
            if (partnerReg.partnerExists(partnerEntries[i].getPartnerId())) {
               String errorMessage = "Partner " + partnerEntries[i].getPartnerId() + " already exists, Import operation fail.";
               this.debug(method, errorMessage);
               throw new ErrorCollectionException(ProvidersLogger.getSAMLImportFailForAlreadyExists("partner", partnerEntries[i].getPartnerId()));
            }
         }

         ArrayList partnersWithoutConflicts = new ArrayList();

         for(int i = 0; i < partnerCertificates.length; ++i) {
            if (certReg.aliasExists(partnerCertificates[i].getAlias())) {
               String errorMessage;
               try {
                  X509Certificate registeredCert = certReg.getCertificateFromAlias(partnerCertificates[i].getAlias());
                  if (!registeredCert.equals(partnerCertificates[i].getCert())) {
                     errorMessage = "Partner Certificate " + partnerCertificates[i].getAlias() + " already exists, Import operation fail.";
                     this.debug(method, errorMessage);
                     throw new ErrorCollectionException(ProvidersLogger.getSAMLImportFailForAlreadyExists("certificate", partnerCertificates[i].getAlias()));
                  }

                  SecurityLogger.logWarningCertificateAlreadyExists(this.log, partnerCertificates[i].getAlias());
               } catch (NotFoundException var14) {
                  errorMessage = "Error getting certificate from registry " + partnerCertificates[i].getAlias() + ", Import operation fail.";
                  this.debug(method, errorMessage);
                  throw new ErrorCollectionException(ProvidersLogger.getSAMLImportFailForCouldNotLocateFromRegistry("certificate", partnerCertificates[i].getAlias()));
               }
            } else {
               partnersWithoutConflicts.add(partnerCertificates[i]);
            }
         }

         return (SAMLPartnerCertificate[])((SAMLPartnerCertificate[])partnersWithoutConflicts.toArray(new SAMLPartnerCertificate[partnersWithoutConflicts.size()]));
      } else if (constraints.isUpdateMode()) {
         return partnerCertificates;
      } else {
         HashSet reservedAliases = null;
         HashMap aliasTrackingMap = new HashMap();

         boolean foundConflict;
         do {
            foundConflict = false;
            reservedAliases = new HashSet();

            for(int i = 0; i < partnerCertificates.length; ++i) {
               reservedAliases.add(partnerCertificates[i].getAlias());
            }

            Set referencedAliases = referencedCertificateAliases(partnerEntries);
            reservedAliases.addAll(referencedAliases);

            for(int i = 0; i < partnerCertificates.length; ++i) {
               String currentAlias = partnerCertificates[i].getAlias();
               reservedAliases.remove(currentAlias);
               String resolvedAlias = certReg.resolveCertificateAliasConflict(currentAlias, reservedAliases);
               if (resolvedAlias.equals(currentAlias)) {
                  reservedAliases.add(resolvedAlias);
               } else {
                  foundConflict = true;
                  this.debug(method, "Certificate Alias " + currentAlias + " is changed to " + resolvedAlias);
                  reservedAliases.add(resolvedAlias);
                  aliasTrackingMap.put(currentAlias, resolvedAlias);
                  partnerCertificates[i].setAlias(resolvedAlias);
               }
            }
         } while(foundConflict);

         this.updateCertificateReferences(partnerReg, partnerEntries, aliasTrackingMap);
         return partnerCertificates;
      }
   }

   private String trackAliasChange(String alias, HashMap aliasTrackingMap) {
      for(String changedTo = (String)aliasTrackingMap.get(alias); changedTo != null; changedTo = (String)aliasTrackingMap.get(changedTo)) {
         alias = changedTo;
      }

      return alias;
   }

   private void updateCertificateReferences(SAMLPartnerRegistry partnerReg, SAMLPartnerEntry[] partnerEntries, HashMap aliasTrackingMap) {
      String method = "updateCertificateReferences";

      for(int i = 0; i < partnerEntries.length; ++i) {
         String alias;
         String changedAlias;
         if (partnerEntries[i] instanceof SAMLAssertingPartyEntry) {
            SAMLAssertingPartyEntry assertingParty = (SAMLAssertingPartyEntry)partnerEntries[i];
            alias = assertingParty.getAssertionSigningCertAlias();
            if (alias != null && alias.length() > 0) {
               changedAlias = this.trackAliasChange(alias, aliasTrackingMap);
               if (!changedAlias.equals(alias)) {
                  this.debug(method, "change partner referenced certificate alias from " + alias + " to " + changedAlias);
                  assertingParty.setAssertionSigningCertAlias(changedAlias);
               }
            }

            alias = assertingParty.getProtocolSigningCertAlias();
            if (alias != null && alias.length() > 0) {
               changedAlias = this.trackAliasChange(alias, aliasTrackingMap);
               if (!changedAlias.equals(alias)) {
                  this.debug(method, "change partner referenced certificate alias from " + alias + " to " + changedAlias);
                  assertingParty.setProtocolSigningCertAlias(changedAlias);
               }
            }
         } else if (partnerEntries[i] instanceof SAMLRelyingPartyEntry) {
            SAMLRelyingPartyEntry relyingParty = (SAMLRelyingPartyEntry)partnerEntries[i];
            alias = relyingParty.getSSLClientCertAlias();
            if (alias != null && alias.length() > 0) {
               changedAlias = this.trackAliasChange(alias, aliasTrackingMap);
               if (!changedAlias.equals(alias)) {
                  this.debug(method, "change partner referenced certificate alias from " + alias + " to " + changedAlias);
                  relyingParty.setSSLClientCertAlias(changedAlias);
               }
            }
         }
      }

   }

   private SAMLPartnerEntry[] resolveImportPartners(SAMLPartnerRegistry partnerReg, NodeList partnerNodeList, ImportExportConstraints constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "resolveImportPartners";
      if (partnerNodeList != null && partnerNodeList.getLength() != 0) {
         int constraintType = constraints.getPartnerConstraintType();
         if (constraintType == 1) {
            this.debug(method, "Partner Constriants is none");
            return new SAMLPartnerEntry[0];
         } else {
            int length = partnerNodeList.getLength();
            SAMLPartnerEntry[] partnerEntries = new SAMLPartnerEntry[length];
            String[] attributeNames = partnerReg.getEntryAttributes();

            for(int i = 0; i < length; ++i) {
               partnerEntries[i] = partnerReg.makeNewEntryInstance();
               partnerEntries[i].setAttributesFromDOMElement((Element)partnerNodeList.item(i), attributeNames);
            }

            Set partnerIdSet = null;
            if (constraintType == 4) {
               partnerIdSet = constraints.getPartnerIds();
            }

            ArrayList importPartnerEntries = new ArrayList();

            for(int i = 0; i < length; ++i) {
               if (constraintType == 0) {
                  importPartnerEntries.add(partnerEntries[i]);
               } else if (constraintType == 2) {
                  if (partnerEntries[i].isEnabled()) {
                     importPartnerEntries.add(partnerEntries[i]);
                  }
               } else if (constraintType == 3) {
                  if (!partnerEntries[i].isEnabled()) {
                     importPartnerEntries.add(partnerEntries[i]);
                  }
               } else if (constraintType == 4) {
                  String partnerId = partnerEntries[i].getPartnerId();
                  if (partnerId != null && partnerIdSet.contains(partnerId)) {
                     importPartnerEntries.add(partnerEntries[i]);
                     partnerIdSet.remove(partnerId);
                  }
               }
            }

            if (constraintType == 4 && !partnerIdSet.isEmpty()) {
               String errorMessage = "Can not locate SAML partners " + partnerIdSet + " in the import xml document, import operation abort!";
               this.debug(method, errorMessage);
               throw new ErrorCollectionException(ProvidersLogger.getSAMLImportFailForCouldNotLocateFromFile("partner", partnerIdSet.toString()));
            } else {
               SAMLPartnerEntry[] partners = (SAMLPartnerEntry[])((SAMLPartnerEntry[])importPartnerEntries.toArray(new SAMLPartnerEntry[importPartnerEntries.size()]));
               this.debug(method, "resolved  SAML Partner Entries, total: " + partners.length);
               return partners;
            }
         }
      } else {
         this.debug(method, "No partner elements in the XML document");
         return new SAMLPartnerEntry[0];
      }
   }

   private void exportDataXMLRegistry(SAMLPartnerRegistry partnerReg, SAMLCertRegLDAPDelegate certReg, String fileName, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "exportDataXMLRegistry";
      ImportExportConstraints registryConstraints = new ImportExportConstraints(constraints);
      SAMLPartnerEntry[] partnerEntries = this.resolveExportPartners(partnerReg, registryConstraints);
      this.debug(method, "Export SAML Partners, total: " + partnerEntries.length);
      String[] certificateAliases = this.resolveExportCertificateAliases(certReg, registryConstraints, partnerEntries);
      this.debug(method, "Export Partner Certificates, total: " + certificateAliases.length);
      this.exportDataToXML(fileName, partnerReg, partnerEntries, certReg, certificateAliases, registryConstraints);
   }

   private void exportDataToXML(String fileName, SAMLPartnerRegistry partnerReg, SAMLPartnerEntry[] partnerEntries, SAMLCertRegLDAPDelegate certReg, String[] certificateAliases, ImportExportConstraints constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "exportDataToXML";
      ErrorCollectionException errorCollectionException = new ErrorCollectionException("SAML Export DATA");
      DocumentBuilderFactory dbf = this.jaxpFactoryService.newDocumentBuilderFactory();
      dbf.setNamespaceAware(true);
      Document dom = null;

      try {
         DocumentBuilder db = dbf.newDocumentBuilder();
         dom = db.newDocument();
      } catch (ParserConfigurationException var22) {
         this.debug(method, "Export data to xml, ParseConfigurationException: " + var22.getMessage());
         errorCollectionException.add(var22);
         throw errorCollectionException;
      }

      String partnerElementStr;
      String rootElementStr;
      if (partnerReg instanceof SAMLAssertingPartyRegistry) {
         rootElementStr = "spr:AssertingPartyRegistry";
         partnerElementStr = "spr:AssertingParty";
      } else {
         rootElementStr = "spr:RelyingPartyRegistry";
         partnerElementStr = "spr:RelyingParty";
      }

      try {
         Element rootElement = dom.createElement(rootElementStr);
         dom.appendChild(rootElement);
         rootElement.setAttribute("xmlns:spr", "urn:bea:security:saml:1.1:partner-registry");
         rootElement.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");

         int i;
         for(i = 0; i < certificateAliases.length; ++i) {
            String alias = certificateAliases[i];
            this.debug(method, "Exporting certificate: " + alias);
            Element partnerCertificateElement = this.generatePartnerCertificateElement(dom, certReg, alias);
            if (partnerCertificateElement != null) {
               rootElement.appendChild(partnerCertificateElement);
            }
         }

         for(i = 0; i < partnerEntries.length; ++i) {
            this.debug(method, "Exporting partner entry: " + partnerEntries[i].getPartnerId());
            Element partnerElement = partnerEntries[i].getAttributesAsDOMElement(dom, partnerElementStr, partnerReg.getEntryAttributes(), constraints.isPasswordsCleartext());
            rootElement.appendChild(partnerElement);
         }
      } catch (DOMException var23) {
         this.debug(method, "Export data to xml, DOMException: " + var23.getMessage());
         errorCollectionException.add(var23);
         throw errorCollectionException;
      }

      try {
         TransformerFactory tFactory = this.jaxpFactoryService.newTransformerFactory();

         Transformer transformer;
         try {
            transformer = tFactory.newTransformer();
         } catch (TransformerConfigurationException var20) {
            this.debug(method, "Export data to xml, TransformerConfigurationException: " + var20.getMessage());
            errorCollectionException.add(var20);
            throw errorCollectionException;
         }

         DOMSource source = new DOMSource(dom);
         FileOutputStream fos = new FileOutputStream(new File(fileName));
         StreamResult result = new StreamResult(fos);

         try {
            transformer.transform(source, result);
         } catch (TransformerException var19) {
            this.debug(method, "Export data to xml, TransformerException: " + var19.getMessage());
            errorCollectionException.add(var19);
            throw errorCollectionException;
         }

         fos.close();
      } catch (IOException var21) {
         this.debug(method, "Export data to xml, IOException: " + var21.getMessage());
         errorCollectionException.add(var21);
         throw errorCollectionException;
      }
   }

   private Element generatePartnerCertificateElement(Document dom, SAMLCertRegLDAPDelegate certReg, String alias) throws InvalidParameterException, DOMException {
      String method = "generatePartnerCertificateElement";
      X509Certificate certificate = null;

      try {
         certificate = certReg.getCertificateFromAlias(alias);
      } catch (NotFoundException var7) {
         this.debug(method, "NotFoundException: " + var7.getMessage());
         throw new InvalidParameterException(ProvidersLogger.getSAMLImportFailForCouldNotLocateFromRegistry("certificate", alias));
      } catch (InvalidParameterException var8) {
         this.debug(method, "InvalidParameterException: " + var8.getMessage());
         throw var8;
      }

      if (certificate != null) {
         this.debug(method, "Certificate alias: " + alias);
         SAMLPartnerCertificate partnerCertificate = new SAMLPartnerCertificate(this.log, alias, certificate);
         return partnerCertificate.toDOMElement(dom);
      } else {
         return null;
      }
   }

   private String[] getAllCertificateAliasesFromRegistry(SAMLCertRegLDAPDelegate certReg) throws InvalidParameterException, ErrorCollectionException {
      ArrayList aliasList = new ArrayList();

      try {
         String cursor = this.listBegin(certReg, "*", 0);

         while(this.listHaveCurrent(cursor)) {
            String currentAlias = this.listGetCurrentName(cursor);
            aliasList.add(currentAlias);
            this.listAdvance(cursor);
         }

         this.listClose(cursor);
         return (String[])((String[])aliasList.toArray(new String[aliasList.size()]));
      } catch (InvalidCursorException var5) {
         ErrorCollectionException ece = new ErrorCollectionException("GetAllCertificateAliasFromRegistry");
         ece.add(var5);
         throw ece;
      }
   }

   private String[] resolveExportCertificateAliases(SAMLCertRegLDAPDelegate certReg, ImportExportConstraints constraints, SAMLPartnerEntry[] partnerEntries) throws InvalidParameterException, ErrorCollectionException {
      int constraintType = constraints.getCertificateConstraintType();
      if (constraintType == 0) {
         return this.getAllCertificateAliasesFromRegistry(certReg);
      } else if (constraintType == 1) {
         return new String[0];
      } else if (constraintType == 3) {
         return (String[])((String[])constraints.getCertificateAliases().toArray(new String[0]));
      } else if (constraintType == 2) {
         Set aliases = referencedCertificateAliases(partnerEntries);
         return (String[])((String[])aliases.toArray(new String[aliases.size()]));
      } else {
         return new String[0];
      }
   }

   private SAMLPartnerEntry[] resolveExportPartners(SAMLPartnerRegistry partnerReg, ImportExportConstraints constraints) throws InvalidParameterException {
      String method = "resolveExportPartners";
      int constraintType = constraints.getPartnerConstraintType();
      if (constraintType == 1) {
         return new SAMLPartnerEntry[0];
      } else {
         HashSet partnerEntries = new HashSet();
         Set partnerIdSet = null;
         if (constraintType == 4) {
            partnerIdSet = constraints.getPartnerIds();
         } else {
            partnerIdSet = partnerReg.getAllPartnerIds();
         }

         String[] partnerIds = (String[])((String[])partnerIdSet.toArray(new String[0]));

         for(int i = 0; i < partnerIds.length; ++i) {
            SAMLPartnerEntry partnerEntry = null;

            try {
               partnerEntry = partnerReg.getPartner(partnerIds[i]);
            } catch (NotFoundException var11) {
               this.debug(method, "NotFoundException: " + var11.getMessage());
               throw new InvalidParameterException(ProvidersLogger.getSAMLCouldNotLocateFromRegistry("partner", partnerIds[i]));
            } catch (InvalidParameterException var12) {
               this.debug(method, "InvalidParameterException: " + var12.getMessage());
               throw var12;
            }

            if (partnerEntry != null) {
               if (constraintType != 4 && constraintType != 0) {
                  if (constraintType == 2) {
                     if (partnerEntry.isEnabled()) {
                        this.debug(method, "Include partner: " + partnerEntry.getPartnerId());
                        partnerEntries.add(partnerEntry);
                     }
                  } else if (constraintType == 3 && !partnerEntry.isEnabled()) {
                     this.debug(method, "Include partner: " + partnerEntry.getPartnerId());
                     partnerEntries.add(partnerEntry);
                  }
               } else {
                  this.debug(method, "Include partner: " + partnerEntry.getPartnerId());
                  partnerEntries.add(partnerEntry);
               }
            }
         }

         SAMLPartnerEntry[] partners = (SAMLPartnerEntry[])((SAMLPartnerEntry[])partnerEntries.toArray(new SAMLPartnerEntry[0]));
         this.debug(method, "return SAMLPartnerEntry[" + partners.length + "]");
         return partners;
      }
   }

   private void exportPartnerRegistryDataLDIF(SAMLPartnerRegistry partnerReg, SAMLCertRegLDAPDelegate certReg, String fileName, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      GenericEntryConverter converter = this.createEntryConverter(partnerReg);
      boolean isClearPasswordExport = this.hasClearPasswordConstraint(constraints);
      converter.setClearTextExport(isClearPasswordExport);
      String providerType = partnerReg instanceof SAMLAssertingPartyRegistry ? "IdentityAsserter" : "SAMLCredentialMapper";
      List entries = new ArrayList();
      List partnerBusinessObjects = null;

      try {
         partnerBusinessObjects = partnerReg.searchPartnerByWildcard("*");
      } catch (Throwable var12) {
         if (this.isDebug()) {
            this.debug("exportPartnerRegistryDataLDIF", "failed." + var12.getMessage());
         }

         throw new ErrorCollectionException(ProvidersLogger.getSAMLCouldNotExportPartner(var12.getMessage()));
      }

      List partnerCertificates;
      if (partnerBusinessObjects != null) {
         partnerCertificates = converter.convertToLDIFEntries(partnerBusinessObjects);
         if (partnerCertificates != null) {
            entries.addAll(partnerCertificates);
         }
      }

      partnerCertificates = null;
      partnerCertificates = certReg.searchCertificatesByAliasFilter("*");
      if (partnerCertificates != null) {
         List convertedCertificateEntries = converter.convertToLDIFEntries(partnerCertificates);
         if (convertedCertificateEntries != null) {
            entries.addAll(convertedCertificateEntries);
         }
      }

      this.bootStrapService.exportSAMLDataToLDIFT(this.log, providerType, fileName, partnerReg.getDomainName(), partnerReg.getRealmName(), entries);
   }

   private GenericEntryConverter createEntryConverter(SAMLPartnerRegistry partnerReg) {
      return (GenericEntryConverter)(partnerReg instanceof SAMLAssertingPartyRegistry ? new SAMLAssertingPartyEntryConverter(this.log, this.legacyEncryptor) : new SAMLRelyingPartyEntryConverter(this.log, this.legacyEncryptor));
   }

   private boolean hasClearPasswordConstraint(Properties constraints) {
      if (constraints != null && constraints.size() != 0) {
         String passwordConstraintValue = constraints.getProperty("passwords");
         return passwordConstraintValue != null && passwordConstraintValue.equalsIgnoreCase("cleartext");
      } else {
         return false;
      }
   }

   public void loadInitialLDIFData(String providerType, SAMLPartnerRegistry partnerReg, SAMLCertRegLDAPDelegate certReg) {
      String method = "loadInitialLDIFData";

      try {
         EntryConverter converter = this.createEntryConverter(partnerReg);
         this.bootStrapService.importSAMLDataLDIFT(this.log, this.storeService, converter, providerType, partnerReg.getDomainName(), partnerReg.getRealmName());
      } catch (Exception var6) {
         if (this.isDebug()) {
            this.debug(method, "failed." + var6.getMessage());
         }
      }

   }

   private static Set referencedCertificateAliases(SAMLPartnerEntry[] partnerEntries) {
      HashSet aliases = new HashSet();

      for(int i = 0; i < partnerEntries.length; ++i) {
         String alias;
         if (partnerEntries[i] instanceof SAMLAssertingPartyEntry) {
            SAMLAssertingPartyEntry assertingParty = (SAMLAssertingPartyEntry)partnerEntries[i];
            alias = assertingParty.getAssertionSigningCertAlias();
            if (alias != null && alias.length() > 0) {
               aliases.add(alias);
            }

            alias = assertingParty.getProtocolSigningCertAlias();
            if (alias != null && alias.length() > 0) {
               aliases.add(alias);
            }
         } else if (partnerEntries[i] instanceof SAMLRelyingPartyEntry) {
            SAMLRelyingPartyEntry relyingParty = (SAMLRelyingPartyEntry)partnerEntries[i];
            alias = relyingParty.getSSLClientCertAlias();
            if (alias != null && alias.length() > 0) {
               aliases.add(alias);
            }
         }
      }

      return aliases;
   }

   public class myBackupErrorHandler implements ErrorHandler {
      private LoggerSpi log;
      private String method;
      private String registryName;

      public myBackupErrorHandler(LoggerSpi log, String registryName, String method) {
         this.log = log;
         this.registryName = registryName;
         this.method = method;
      }

      public void error(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Error loading alternate SAML partner registry schema";
            this.log.debug(msg);
         }

      }

      public void fatalError(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Fatal error loading alternate SAML partner registry schema";
            this.log.debug(msg);
         }

      }

      public void warning(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Warning received while loading alternate SAML partner registry schema";
            this.log.debug(msg);
         }

      }
   }

   public class myErrorHandler implements ErrorHandler {
      private LoggerSpi log;
      private String method;
      private String registryName;

      public myErrorHandler(LoggerSpi log, String registryName, String method) {
         this.log = log;
         this.method = method;
         this.registryName = registryName;
      }

      public void error(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Error loading SAML partner registry schema, using alternate";
            this.log.debug(msg);
         }

         throw spe;
      }

      public void fatalError(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Fatal error loading SAML partner registry schema";
            this.log.debug(msg);
         }

      }

      public void warning(SAXParseException spe) throws SAXException {
         if (this.log != null && this.log.isDebugEnabled()) {
            String msg = this.registryName + "." + this.method + ": Warning received while loading SAML partner registry schema";
            this.log.debug(msg);
         }

      }
   }

   private class ImportExportConstraints {
      private static final String PARTNERS = "Partners";
      private static final String CERTIFICATES = "Certificates";
      private static final String IMPORT_MODE = "ImportMode";
      private static final String PASSWORDS_CONSTRAINT = "Passwords";
      private static final String CONSTRAINT_VALUE_ALL = "all";
      private static final String CONSTRAINT_VALUE_NONE = "none";
      private static final String CONSTRAINT_VALUE_ENABLED = "enabled";
      private static final String CONSTRAINT_VALUE_DISABLED = "disabled";
      private static final String CONSTRAINT_VALUE_REFERENCED = "referenced";
      public static final int PARTNER_CONSTRAINT_TYPE_ALL = 0;
      public static final int PARTNER_CONSTRAINT_TYPE_NONE = 1;
      public static final int PARTNER_CONSTRAINT_TYPE_ENABLED = 2;
      public static final int PARTNER_CONSTRAINT_TYPE_DISABLED = 3;
      public static final int PARTNER_CONSTRAINT_TYPE_LIST = 4;
      public static final int CERTIFICATE_CONSTRAINT_TYPE_ALL = 0;
      public static final int CERTIFICATE_CONSTRAINT_TYPE_NONE = 1;
      public static final int CERTIFICATE_CONSTRAINT_TYPE_REFERENCED = 2;
      public static final int CERTIFICATE_CONSTRAINT_TYPE_LIST = 3;
      private static final String IMPORT_MODE_VALUE_RENAME = "rename";
      private static final String IMPORT_MODE_VALUE_UPDATE = "replace";
      private static final String IMPORT_MODE_VALUE_FAIL = "fail";
      private static final int IMPORT_MODE_TYPE_RENAME = 0;
      private static final int IMPORT_MODE_TYPE_UPDATE = 1;
      private static final int IMPORT_MODE_TYPE_FAIL = 2;
      private static final String PASSWORDS_CLEARTEXT = "cleartext";
      public static final String DELIMITER = ",";
      private Properties constraints;
      private int partnerConstraintType = 0;
      private int certificateConstraintType = 0;
      private int importModeType = 2;
      private HashSet partnerIds = null;
      private HashSet certificateAliases = null;
      private String passwordsConstraint = null;

      public ImportExportConstraints(Properties prop) {
         this.constraints = prop;
         this.parseConstraints();
      }

      private void parseConstraints() {
         String method = "Registry Constraints parsing";
         SAMLPartnerRegHelper.this.debug(method, "Parsing import/export Constraints");
         if (this.constraints == null) {
            SAMLPartnerRegHelper.this.debug(method, "No constraints specified, using default constaint value");
         } else {
            String partnerConstraint = this.constraints.getProperty("Partners", "all");
            if (partnerConstraint.equalsIgnoreCase("all")) {
               this.partnerConstraintType = 0;
            } else if (partnerConstraint.equalsIgnoreCase("none")) {
               this.partnerConstraintType = 1;
            } else if (partnerConstraint.equalsIgnoreCase("enabled")) {
               this.partnerConstraintType = 2;
            } else if (partnerConstraint.equalsIgnoreCase("disabled")) {
               this.partnerConstraintType = 3;
            } else {
               this.partnerConstraintType = 4;
               this.partnerIds = this.parsingList(partnerConstraint);
            }

            String certificateConstraint = this.constraints.getProperty("Certificates", "all");
            if (certificateConstraint.equalsIgnoreCase("all")) {
               this.certificateConstraintType = 0;
            } else if (certificateConstraint.equalsIgnoreCase("none")) {
               this.certificateConstraintType = 1;
            } else if (certificateConstraint.equalsIgnoreCase("referenced")) {
               this.certificateConstraintType = 2;
            } else {
               this.certificateConstraintType = 3;
               this.certificateAliases = this.parsingList(certificateConstraint);
            }

            String importMode = this.constraints.getProperty("ImportMode", "fail");
            if (importMode.equalsIgnoreCase("rename")) {
               this.importModeType = 0;
            } else if (importMode.equalsIgnoreCase("replace")) {
               this.importModeType = 1;
            } else if (importMode.equalsIgnoreCase("fail")) {
               this.importModeType = 2;
            }

            this.passwordsConstraint = this.constraints.getProperty("Passwords");
            if (this.passwordsConstraint != null) {
               this.passwordsConstraint = this.passwordsConstraint.trim();
            }

            SAMLPartnerRegHelper.this.debug(method, "Partner Constraint type is " + this.partnerConstraintType);
            SAMLPartnerRegHelper.this.debug(method, "Certificate Constraint type is " + this.certificateConstraintType);
            SAMLPartnerRegHelper.this.debug(method, "Import Mode type is " + this.importModeType);
            SAMLPartnerRegHelper.this.debug(method, "Passwords Constraint is " + this.passwordsConstraint);
         }
      }

      private HashSet parsingList(String list) {
         HashSet hashSet = new HashSet();
         StringTokenizer st = new StringTokenizer(list, ",");

         while(st.hasMoreTokens()) {
            hashSet.add(st.nextToken().trim());
         }

         return hashSet;
      }

      public int getPartnerConstraintType() {
         return this.partnerConstraintType;
      }

      public int getCertificateConstraintType() {
         return this.certificateConstraintType;
      }

      public boolean isRenameMode() {
         return this.importModeType == 0;
      }

      public boolean isUpdateMode() {
         return this.importModeType == 1;
      }

      public boolean isFailMode() {
         return this.importModeType == 2;
      }

      public Set getPartnerIds() {
         return (Set)this.partnerIds.clone();
      }

      public Set getCertificateAliases() {
         return (Set)this.certificateAliases.clone();
      }

      public boolean isPasswordsCleartext() {
         return this.passwordsConstraint != null && this.passwordsConstraint.equalsIgnoreCase("cleartext");
      }
   }
}
