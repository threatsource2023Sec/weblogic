package com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime;

import java.util.ListResourceBundle;

public class ErrorMessages_es extends ListResourceBundle {
   public Object[][] getContents() {
      return new Object[][]{{"RUN_TIME_INTERNAL_ERR", "Error interno de ejecución en ''{0}''"}, {"RUN_TIME_COPY_ERR", "Error de ejecución al ejecutar <xsl:copy>."}, {"DATA_CONVERSION_ERR", "Conversión no válida de ''{0}'' a ''{1}''."}, {"EXTERNAL_FUNC_ERR", "Función externa ''{0}'' no soportada por XSLTC."}, {"EQUALITY_EXPR_ERR", "Tipo de argumento desconocido en expresión de igualdad."}, {"INVALID_ARGUMENT_ERR", "Tipo de argumento ''{0}'' no válido en la llamada a ''{1}''"}, {"FORMAT_NUMBER_ERR", "Se intenta dar formato al número ''{0}'' con el patrón ''{1}''."}, {"ITERATOR_CLONE_ERR", "No se puede replicar el iterador ''{0}''."}, {"AXIS_SUPPORT_ERR", "Iterador para el eje ''{0}'' no soportado."}, {"TYPED_AXIS_SUPPORT_ERR", "Iterador para el eje escrito ''{0}'' no soportado."}, {"STRAY_ATTRIBUTE_ERR", "Atributo ''{0}'' fuera del elemento."}, {"STRAY_NAMESPACE_ERR", "Declaración del espacio de nombres ''{0}''=''{1}'' fuera del elemento."}, {"NAMESPACE_PREFIX_ERR", "No se ha declarado el espacio de nombres para el prefijo ''{0}''."}, {"DOM_ADAPTER_INIT_ERR", "DOMAdapter creado mediante un tipo incorrecto de DOM origen."}, {"PARSER_DTD_SUPPORT_ERR", "El analizador SAX utilizado no maneja sucesos de declaración DTD."}, {"NAMESPACES_SUPPORT_ERR", "El analizador SAX utilizado no tiene soporte de espacios de nombres XML."}, {"CANT_RESOLVE_RELATIVE_URI_ERR", "No se ha podido resolver la referencia de URI ''{0}''."}, {"UNSUPPORTED_XSL_ERR", "Elemento XSL ''{0}'' no soportado."}, {"UNSUPPORTED_EXT_ERR", "Extensión XSLTC ''{0}'' no reconocida."}, {"UNKNOWN_TRANSLET_VERSION_ERR", "El translet especificado, ''{0}'', se ha creado utilizando una versión de XSLTC más reciente que la versión de ejecución de XSLTC que está en uso. Debe recompilar la hoja de estilos o utilizar una versión más reciente de XSLTC para ejecutar este translet."}, {"INVALID_QNAME_ERR", "Un atributo cuyo valor debe ser un QName tiene el valor ''{0}''"}, {"INVALID_NCNAME_ERR", "Un atributo cuyo valor debe ser un NCName tiene el valor ''{0}''"}, {"UNALLOWED_EXTENSION_FUNCTION_ERR", "No se permite el uso de la función de extensión ''{0}'' cuando la característica de proceso seguro está establecida en true."}, {"UNALLOWED_EXTENSION_ELEMENT_ERR", "No se permite el uso del elemento de extensión ''{0}'' cuando la característica de proceso seguro está establecida en true."}};
   }
}