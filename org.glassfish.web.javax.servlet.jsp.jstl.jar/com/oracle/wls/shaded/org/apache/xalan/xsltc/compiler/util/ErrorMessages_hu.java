package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import java.util.ListResourceBundle;

public class ErrorMessages_hu extends ListResourceBundle {
   public Object[][] getContents() {
      return new Object[][]{{"MULTIPLE_STYLESHEET_ERR", "Egynél több stíluslap van meghatározva ugyanabban a fájlban."}, {"TEMPLATE_REDEF_ERR", "A(z) ''{0}'' sablon már meg van határozva ebben a stíluslapban."}, {"TEMPLATE_UNDEF_ERR", "A(z) ''{0}'' sablon nincs meghatározva ebben a stíluslapban."}, {"VARIABLE_REDEF_ERR", "A(z) ''{0}'' változó többször van meghatározva ugyanabban a hatókörben."}, {"VARIABLE_UNDEF_ERR", "A(z) ''{0}'' változó vagy paraméter nincs meghatározva."}, {"CLASS_NOT_FOUND_ERR", "A(z) ''{0}'' osztály nem található."}, {"METHOD_NOT_FOUND_ERR", "Nem található a(z) ''{0}'' külső metódus (nyilvánosnak kell lennie)."}, {"ARGUMENT_CONVERSION_ERR", "Nem lehet átalakítani az argumentum/visszatérési típust a(z) ''{0}'' metódus hívásában."}, {"FILE_NOT_FOUND_ERR", "A(z) ''{0}'' fájl vagy URI nem található."}, {"INVALID_URI_ERR", "Érvénytelen URI: ''{0}''."}, {"FILE_ACCESS_ERR", "A(z) ''{0}'' fájlt vagy URI nem nyitható meg. "}, {"MISSING_ROOT_ERR", "Hiányzik az <xsl:stylesheet> vagy <xsl:transform> elem."}, {"NAMESPACE_UNDEF_ERR", "A(z) ''{0}'' névtér előtag nincs deklarálva."}, {"FUNCTION_RESOLVE_ERR", "Nem lehet feloldani a(z) ''{0}'' függvény hívását."}, {"NEED_LITERAL_ERR", "A(z) ''{0}'' argumentumának egy literál karaktersorozatnak kell lennie."}, {"XPATH_PARSER_ERR", "Hiba történt a(z) ''{0}'' XPath kifejezés értelmezésekor."}, {"REQUIRED_ATTR_ERR", "Hiányzik a(z) ''{0}'' kötelező attribútum."}, {"ILLEGAL_CHAR_ERR", "Nem megengedett karakter (''{0}'') szerepel az XPath kifejezésben."}, {"ILLEGAL_PI_ERR", "Nem megengedett név (''{0}'') szerepel a feldolgozási utasításban."}, {"STRAY_ATTRIBUTE_ERR", "A(z) ''{0}'' attribútum kívül esik az elemen."}, {"ILLEGAL_ATTRIBUTE_ERR", "Illegális attribútum: ''{0}''."}, {"CIRCULAR_INCLUDE_ERR", "Körkörös importálás/tartalmazás. A(z) ''{0}'' stíluslap már be van töltve."}, {"RESULT_TREE_SORT_ERR", "Az eredményfa-részleteket nem lehet rendezni (az <xsl:sort> elemek figyelmen kívül maradnak). Rendeznie kell a node-okat, amikor eredményfát hoz létre."}, {"SYMBOLS_REDEF_ERR", "Már definiálva van a(z) ''{0}'' decimális formázás."}, {"XSL_VERSION_ERR", "Az XSLTC nem támogatja a(z) ''{0}'' XSL verziót."}, {"CIRCULAR_VARIABLE_ERR", "Körkörös változó/paraméter hivatkozás a(z) ''{0}'' helyen."}, {"ILLEGAL_BINARY_OP_ERR", "Ismeretlen operátort használt a bináris kifejezésben."}, {"ILLEGAL_ARG_ERR", "Nem megengedett argumentumo(ka)t használt a függvényhívásban."}, {"DOCUMENT_ARG_ERR", "A document() függvény második argumentuma egy node-készlet kell legyen."}, {"MISSING_WHEN_ERR", "Legalább egy <xsl:when> elem szükséges az <xsl:choose>-ban."}, {"MULTIPLE_OTHERWISE_ERR", "Csak egy <xsl:otherwise> elem megengedett <xsl:choose>-ban."}, {"STRAY_OTHERWISE_ERR", "Az <xsl:otherwise> csak <xsl:choose>-on belül használható."}, {"STRAY_WHEN_ERR", "Az <xsl:when> csak <xsl:choose>-on belül használható."}, {"WHEN_ELEMENT_ERR", "Csak <xsl:when> és <xsl:otherwise> elemek megengedettek az <xsl:choose>-ban."}, {"UNNAMED_ATTRIBSET_ERR", "Hiányzik az <xsl:attribute-set>-ből a 'name' attribútum."}, {"ILLEGAL_CHILD_ERR", "Nem megengedett gyermek elem."}, {"ILLEGAL_ELEM_NAME_ERR", "Az elem neve nem lehet ''{0}''."}, {"ILLEGAL_ATTR_NAME_ERR", "Az attribútum neve nem lehet ''{0}''."}, {"ILLEGAL_TEXT_NODE_ERR", "Szövegadat szerepel a felső szintű <xsl:stylesheet> elemen kívül."}, {"SAX_PARSER_CONFIG_ERR", "Nincs megfelelően konfigurálva a JAXP értelmező."}, {"INTERNAL_ERR", "Helyrehozhatatlan belső XSLTC hiba történt: ''{0}''  "}, {"UNSUPPORTED_XSL_ERR", "Nem támogatott XSL elem: ''{0}''."}, {"UNSUPPORTED_EXT_ERR", "Ismeretlen XSLTC kiterjesztés: ''{0}''."}, {"MISSING_XSLT_URI_ERR", "A bemenő dokumentum nem stíluslap (az XSL névtér nincs deklarálva a root elemben)."}, {"MISSING_XSLT_TARGET_ERR", "A(z) ''{0}'' stíluslap cél nem található."}, {"NOT_IMPLEMENTED_ERR", "Nincs megvalósítva: ''{0}''."}, {"NOT_STYLESHEET_ERR", "A bemenő dokumentum nem tartalmaz XSL stíluslapot."}, {"ELEMENT_PARSE_ERR", "A(z) ''{0}'' elem nem értelmezhető. "}, {"KEY_USE_ATTR_ERR", "A(z) <key> attribútuma node, node-készlet, szöveg vagy szám lehet."}, {"OUTPUT_VERSION_ERR", "A kimenő XML dokumentum-verzió 1.0 kell legyen."}, {"ILLEGAL_RELAT_OP_ERR", "Ismeretlen operátort használt a relációs kifejezésben."}, {"ATTRIBSET_UNDEF_ERR", "Nemlétező attribútumkészletet (''{0}'') próbált használni."}, {"ATTR_VAL_TEMPLATE_ERR", "Nem lehet értelmezni a(z) ''{0}'' attribútumérték-sablont."}, {"UNKNOWN_SIG_TYPE_ERR", "Ismeretlen adattípus szerepel a(z) ''{0}'' osztály aláírásában."}, {"DATA_CONVERSION_ERR", "Nem lehet a(z) ''{0}'' adattípust ''{1}'' típusra konvertálni."}, {"NO_TRANSLET_CLASS_ERR", "Ez a Templates osztály nem tartalmaz érvényes translet osztálymeghatározást."}, {"NO_MAIN_TRANSLET_ERR", "Ez a Templates osztály nem tartalmaz ''{0}'' nevű osztályt."}, {"TRANSLET_CLASS_ERR", "Nem lehet betölteni a(z) ''{0}'' translet osztályt."}, {"TRANSLET_OBJECT_ERR", "A translet osztály betöltődött, de nem sikerült létrehozni a translet példányt."}, {"ERROR_LISTENER_NULL_ERR", "Megpróbálta nullértékre állítani a(z) ''{0}'' objektum ErrorListener felületét."}, {"JAXP_UNKNOWN_SOURCE_ERR", "Az XSLTC csak a StreamSource, SAXSource és DOMSource interfészeket támogatja."}, {"JAXP_NO_SOURCE_ERR", "A(z) ''{0}''  metódusnak átadott source objektumnak nincs tartalma."}, {"JAXP_COMPILE_ERR", "Nem sikerült lefordítani a stíluslapot."}, {"JAXP_INVALID_ATTR_ERR", "A TransformerFactory osztály nem simeri fel a(z) ''{0}'' attribútumot."}, {"JAXP_SET_RESULT_ERR", "A setResult() metódust a startDocument() hívása előtt kell meghívni."}, {"JAXP_NO_TRANSLET_ERR", "A transformer interfész nem tartalmaz beágyazott translet objektumot."}, {"JAXP_NO_HANDLER_ERR", "Nincs definiálva kimenetkezelő az átalakítás eredményéhez."}, {"JAXP_NO_RESULT_ERR", "A(z) ''{0}'' metódusnak átadott result objektum érvénytelen."}, {"JAXP_UNKNOWN_PROP_ERR", "Érvénytelen Transformer tulajdonságot (''{0}'') próbált meg elérni."}, {"SAX2DOM_ADAPTER_ERR", "Nem lehet létrehozni a SAX2DOM adaptert: ''{0}''."}, {"XSLTC_SOURCE_ERR", "XSLTCSource.build() hívása systemId beállítása nélkül történt."}, {"ER_RESULT_NULL", "Az eredmény nem lehet null"}, {"JAXP_INVALID_SET_PARAM_VALUE", "A(z) {0} paraméter értéke egy érvényes Jáva objektum kell legyen"}, {"COMPILE_STDIN_ERR", "A -i kapcsolót a -o kapcsolóval együtt kell használni."}, {"COMPILE_USAGE_STR", "Használat:\n   java org.apache.xalan.xsltc.cmdline.Compile [-o <kimenet>]\n      [-d <könyvtár>] [-j <jar_fájl>] [-p <csomag>]\n      [-n] [-x] [-u] [-v] [-h] { <stíluslap> | -i }\n\nBEÁLLÍTÁSOK\n   -o <kimenet>   hozzárendeli a <kimenet> nevet az előállított\n                  translethez. Alapértelmezés szerint\n                  a translet neve a <stíluslap>\n                  nevéből származik. Ez a beállítás figyelmen\n                  kívül marad, ha több stíluslapot fordít.\n   -d <könyvtár>  meghatározza a translet célkönyvtárát\n   -j <jar_fájl>  a translet osztályokat egy jar fájlba csomagolja,\n                  aminek a nevét a <jar_fájl> attribútum adja meg\n   -p <csomag>    meghatározza az összes előállított translet osztály\n                  csomagnév előtagját.\n   -n             engedélyezi a sablonbeemelést\n                  (az alapértelmezett viselkedés általában jobb).\n   -x             bekapcsolja a további hibakeresési üzeneteket\n   -u             értelmezi a <stíluslap> argumentumokat és URL címeket\n   -i             kényszeríti a fordítót, hogy a stíluslapot az stdin\n                  bemenetről olvassa\n   -v             megjeleníti a fordító verziószámát\n   -h             megjeleníti ezt a használati utasítást\n"}, {"TRANSFORM_USAGE_STR", "HASZNÁLAT:\n   java org.apache.xalan.xsltc.cmdline.Transform [-j <jar_fájl>]\n      [-x] [-n <ismétlés>] {-u <dokumentum_url_címe> | <dokumentum>}\n      <osztály> [<param1>=<érték1> ...]\n\n   a translet <osztály> segítségével átalakítja a\n   <dokumentum> paraméterben megadott dokumentumot. A translet\n   <osztály> vagy a felhasználó CLASSPATH változója\n   alapján, vagy a megadott <jar_fájl>-ban található meg.\nBEÁLLÍTÁSOK\n   -j <jar_fájl>   megadja a jar fájlt a translet betöltéséhez\n   -x              bekapcsolja a további hibakeresési üzeneteket\n   -n <ismétlés>   az átalakítást az <ismétlés> paraméterben megadott\n                   alkalommal futtatja le, és megjeleníti a profilozási\n                   információkat\n   -u <dokumentum_url_címe> megadja a bemeneti XML dokumentum URL címét\n"}, {"STRAY_SORT_ERR", "Az <xsl:sort> csak <xsl:for-each>-en vagy <xsl:apply-templates>-en belül használható."}, {"UNSUPPORTED_ENCODING", "A(z) ''{0}'' kimeneti kódolást nem támogatja ez a JVM."}, {"SYNTAX_ERR", "Szintaktikai hiba a(z) ''{0}'' kifejezésben."}, {"CONSTRUCTOR_NOT_FOUND", "A(z) ''{0}'' külső konstruktor nem található."}, {"NO_JAVA_FUNCT_THIS_REF", "A(z) ''{0}'' nem statikus Java függvény első argumentuma nem érvényes objektumhivatkozás."}, {"TYPE_CHECK_ERR", "Hiba történt a(z) ''{0}'' kifejezés típusának ellenőrzésekor."}, {"TYPE_CHECK_UNK_LOC_ERR", "Hiba történt egy ismeretlen helyen lévő kifejezés típusának ellenőrzésekor."}, {"ILLEGAL_CMDLINE_OPTION_ERR", "A(z) ''{0}'' parancssori paraméter érvénytelen."}, {"CMDLINE_OPT_MISSING_ARG_ERR", "A(z) ''{0}'' parancssori paraméterhez hiányzik egy kötelező argumentum."}, {"WARNING_PLUS_WRAPPED_MSG", "FIGYELMEZTETÉS:  ''{0}''\n       :{1}"}, {"WARNING_MSG", "FIGYELMEZTETÉS:  ''{0}''"}, {"FATAL_ERR_PLUS_WRAPPED_MSG", "SÚLYOS HIBA:  ''{0}''\n           :{1}"}, {"FATAL_ERR_MSG", "SÚLYOS HIBA:  ''{0}''"}, {"ERROR_PLUS_WRAPPED_MSG", "HIBA:   ''{0}''\n     :{1}"}, {"ERROR_MSG", "HIBA:   ''{0}''"}, {"TRANSFORM_WITH_TRANSLET_STR", "Átalakítás a(z) ''{0}'' translet segítségével. "}, {"TRANSFORM_WITH_JAR_STR", "Átalakítás a(z) ''{0}'' translet használatával a(z) ''{1}'' jar fájlból. "}, {"COULD_NOT_CREATE_TRANS_FACT", "Nem lehet létrehozni a(z) ''{0}'' TransformerFactory osztály példányát."}, {"TRANSLET_NAME_JAVA_CONFLICT", "A(z) ''{0}'' név nem használható a translet osztály neveként, mivel olyan karaktereket tartalmaz, amelyek nem megengedettek Java osztályok nevében. A rendszer a(z) ''{1}'' nevet használta helyette. "}, {"COMPILER_ERROR_KEY", "Fordítás hibák:"}, {"COMPILER_WARNING_KEY", "Fordítási figyelmeztetések:"}, {"RUNTIME_ERROR_KEY", "Translet hibák:"}, {"INVALID_QNAME_ERR", "Egy olyan attribútum, amelynek az értéke csak QName vagy QName értékek szóközzel elválasztott listája lehet, ''{0}'' értékkel rendelkezett."}, {"INVALID_NCNAME_ERR", "Egy olyan attribútum, amelynek értéke csak NCName lehet, ''{0}'' értékkel rendelkezett."}, {"INVALID_METHOD_IN_OUTPUT", "Egy <xsl:output> elem metódus attribútumának értéke ''{0}'' volt. Az érték csak ''xml'', ''html'', ''text'' vagy qname-but-not-ncname lehet."}, {"JAXP_GET_FEATURE_NULL_NAME", "A szolgáltatás neve nem lehet null a TransformerFactory.getFeature(String name) metódusban."}, {"JAXP_SET_FEATURE_NULL_NAME", "A szolgáltatás neve nem lehet null a TransformerFactory.setFeature(String name, boolean value) metódusban."}, {"JAXP_UNSUPPORTED_FEATURE", "A(z) ''{0}'' szolgáltatás nem állítható be ehhez a TransformerFactory osztályhoz."}};
   }
}
