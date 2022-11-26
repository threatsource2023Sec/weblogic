package com.bea.security.saml2.util;

import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;

public class SAMLObjectBuilder {
   public static Issuer buildIssuer(String issuerId) {
      Issuer issuer = (Issuer)XMLObjectSupport.buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
      issuer.setValue(issuerId);
      return issuer;
   }

   public static Status buildStatus(String code, String message, XMLObject detail) {
      if (code == null) {
         throw new IllegalArgumentException("StatusCode can not be null to build a Status.");
      } else {
         return buildStatus(buildStatusCode(code), message == null ? null : buildStatusMessage(message), detail == null ? null : buildStatusDetail(detail));
      }
   }

   public static Status buildStatus(StatusCode code, StatusMessage message, StatusDetail detail) {
      if (code == null) {
         throw new IllegalArgumentException("StatusCode can not be null to build a Status.");
      } else {
         Status status = (Status)XMLObjectSupport.buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
         status.setStatusCode(code);
         if (message != null) {
            status.setStatusMessage(message);
         }

         if (detail != null) {
            status.setStatusDetail(detail);
         }

         return status;
      }
   }

   public static Status buildSuccessStatus() {
      return buildStatus((String)"urn:oasis:names:tc:SAML:2.0:status:Success", (String)null, (XMLObject)null);
   }

   public static StatusCode buildStatusCode(String statusCode) {
      StatusCode code = (StatusCode)XMLObjectSupport.buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
      code.setValue(statusCode);
      return code;
   }

   public static StatusMessage buildStatusMessage(String statusMessage) {
      StatusMessage message = (StatusMessage)XMLObjectSupport.buildXMLObject(StatusMessage.DEFAULT_ELEMENT_NAME);
      message.setMessage(statusMessage);
      return message;
   }

   public static StatusDetail buildStatusDetail(XMLObject detail) {
      StatusDetail statusDetail = (StatusDetail)XMLObjectSupport.buildXMLObject(StatusDetail.DEFAULT_ELEMENT_NAME);
      statusDetail.getUnknownXMLObjects().add(detail);
      return statusDetail;
   }

   public static ArtifactResponse buildArtifactResponse() {
      return buildArtifactResponse((String)null, (DateTime)null, (Status)null);
   }

   public static ArtifactResponse buildArtifactResponse(String id, DateTime issueInstant, Status status) {
      ArtifactResponse response = (ArtifactResponse)XMLObjectSupport.buildXMLObject(ArtifactResponse.DEFAULT_ELEMENT_NAME);
      response.setID(id != null && id.length() != 0 ? id : SAML2Utils.getXMLSafeSecureUUID());
      response.setIssueInstant(issueInstant == null ? new DateTime() : issueInstant);
      response.setStatus(status == null ? buildSuccessStatus() : status);
      return response;
   }

   public static Artifact buildArtifact(String artifact) {
      Artifact result = null;
      if (artifact != null && !artifact.equals("")) {
         result = (Artifact)XMLObjectSupport.buildXMLObject(Artifact.DEFAULT_ELEMENT_NAME);
         result.setArtifact(artifact);
      }

      return result;
   }

   public static ArtifactResolve buildArtifactResolve(String id, DateTime issueInstant, Issuer issuer, Artifact artifact) {
      ArtifactResolve result = (ArtifactResolve)XMLObjectSupport.buildXMLObject(ArtifactResolve.DEFAULT_ELEMENT_NAME);
      result.setID(id != null && id.length() != 0 ? id : SAML2Utils.getXMLSafeSecureUUID());
      result.setIssueInstant(issueInstant == null ? new DateTime() : issueInstant);
      if (issuer != null) {
         result.setIssuer(issuer);
      }

      if (artifact != null) {
         result.setArtifact(artifact);
      }

      return result;
   }
}
