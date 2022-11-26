package weblogic.xml.stax;

public interface ParserProperties {
   String MAX_TOTAL_ELEMENTS = "weblogic.xml.stax.MaxTotalElements";
   String MAX_ELEMENT_DEPTH = "weblogic.xml.stax.MaxElementDepth";
   String MAX_CHILD_ELEMENTS = "weblogic.xml.stax.MaxChildElements";
   String MAX_ATTRS_PER_ELEMENT = "weblogic.xml.stax.MaxAttrsPerElement";
   String MAX_INPUT_SIZE = "weblogic.xml.stax.MaxInputSize";
   String ENABLE_ALL_LIMIT_CHECKS = "weblogic.xml.stax.EnableAllLimitChecks";
   String ENABLE_START_ELEMENT_CHECKS = "weblogic.xml.stax.EnableStartElementChecks";
   int LIMIT_DISABLED = -1;
   boolean DEFAULT_LIMIT_CHERCKS_ENABLED = true;
   long DEFAULT_MAX_TOTAL_ELEMENTS = Long.MAX_VALUE;
   long DEFAULT_MAX_INPUT_SIZE = -1L;
   int DEFAULT_MAX_ELEMENT_DEPTH = 1000;
   int DEFAULT_MAX_CHILD_ELEMENTS = -1;
   int DEFAULT_MAX_ATTRS_PER_ELEMENT = 1000;
   boolean DEFAULT_START_ELEMENT_CHECKS_ENABLED = true;
}
