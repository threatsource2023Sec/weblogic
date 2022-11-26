package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.RandomHttpSessionIdGenerator;
import com.tangosol.run.xml.XmlConfigurable;
import com.tangosol.run.xml.XmlElement;
import java.util.UUID;
import java.util.regex.Pattern;
import weblogic.servlet.internal.WebAppServletContext;

public class WebLogicSPIHttpSessionIdGenerator extends RandomHttpSessionIdGenerator implements XmlConfigurable {
   private static final Pattern PATTERN = Pattern.compile("-");
   protected String suffix;
   protected XmlElement xmlConfig;

   public void setConfig(XmlElement xml) {
      this.xmlConfig = xml;
      boolean isCompatibilityModeEnabled = CoherenceWebUtils.isCompatibilityModeEnabled((WebAppServletContext)null, xml);
      if (isCompatibilityModeEnabled) {
         String webAppName = xml.getSafeElement("coherence-application-name").getString((String)null);
         if (webAppName != null) {
            this.suffix = "-" + webAppName;
         }
      }

   }

   public XmlElement getConfig() {
      return this.xmlConfig;
   }

   public String generateSessionId(int length) {
      String id = this.generateUUID(length);
      if (this.suffix != null) {
         id = id + this.suffix;
      }

      return id;
   }

   private String generateUUID(int length) {
      StringBuilder builder = new StringBuilder(length);
      this.generateUUID(length, builder);
      return builder.toString();
   }

   private void generateUUID(int length, StringBuilder builder) {
      UUID uuid = UUID.randomUUID();
      String randomId = PATTERN.matcher(uuid.toString()).replaceAll("");
      if (randomId.length() < length) {
         builder.append(randomId);
         this.generateUUID(length - randomId.length(), builder);
      } else {
         builder.append(randomId.substring(randomId.length() - length));
      }
   }

   protected String getSuffix() {
      return this.suffix;
   }
}
