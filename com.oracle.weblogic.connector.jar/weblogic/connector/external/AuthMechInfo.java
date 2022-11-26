package weblogic.connector.external;

public interface AuthMechInfo {
   String getDescription();

   String[] getDescriptions();

   String getType();

   String getCredentialInterface();
}
