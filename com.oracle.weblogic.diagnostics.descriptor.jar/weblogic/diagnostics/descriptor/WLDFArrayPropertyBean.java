package weblogic.diagnostics.descriptor;

public interface WLDFArrayPropertyBean extends WLDFConfigurationPropertyBean {
   String[] DEFAULT_VALUE = new String[0];

   String[] getValue();

   void setValue(String[] var1);
}
