package org.python.bouncycastle.math.ec;

import java.math.BigInteger;
import org.python.bouncycastle.util.Arrays;

class LongArray implements Cloneable {
   private static final short[] INTERLEAVE2_TABLE = new short[]{0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
   private static final int[] INTERLEAVE3_TABLE = new int[]{0, 1, 8, 9, 64, 65, 72, 73, 512, 513, 520, 521, 576, 577, 584, 585, 4096, 4097, 4104, 4105, 4160, 4161, 4168, 4169, 4608, 4609, 4616, 4617, 4672, 4673, 4680, 4681, 32768, 32769, 32776, 32777, 32832, 32833, 32840, 32841, 33280, 33281, 33288, 33289, 33344, 33345, 33352, 33353, 36864, 36865, 36872, 36873, 36928, 36929, 36936, 36937, 37376, 37377, 37384, 37385, 37440, 37441, 37448, 37449, 262144, 262145, 262152, 262153, 262208, 262209, 262216, 262217, 262656, 262657, 262664, 262665, 262720, 262721, 262728, 262729, 266240, 266241, 266248, 266249, 266304, 266305, 266312, 266313, 266752, 266753, 266760, 266761, 266816, 266817, 266824, 266825, 294912, 294913, 294920, 294921, 294976, 294977, 294984, 294985, 295424, 295425, 295432, 295433, 295488, 295489, 295496, 295497, 299008, 299009, 299016, 299017, 299072, 299073, 299080, 299081, 299520, 299521, 299528, 299529, 299584, 299585, 299592, 299593};
   private static final int[] INTERLEAVE4_TABLE = new int[]{0, 1, 16, 17, 256, 257, 272, 273, 4096, 4097, 4112, 4113, 4352, 4353, 4368, 4369, 65536, 65537, 65552, 65553, 65792, 65793, 65808, 65809, 69632, 69633, 69648, 69649, 69888, 69889, 69904, 69905, 1048576, 1048577, 1048592, 1048593, 1048832, 1048833, 1048848, 1048849, 1052672, 1052673, 1052688, 1052689, 1052928, 1052929, 1052944, 1052945, 1114112, 1114113, 1114128, 1114129, 1114368, 1114369, 1114384, 1114385, 1118208, 1118209, 1118224, 1118225, 1118464, 1118465, 1118480, 1118481, 16777216, 16777217, 16777232, 16777233, 16777472, 16777473, 16777488, 16777489, 16781312, 16781313, 16781328, 16781329, 16781568, 16781569, 16781584, 16781585, 16842752, 16842753, 16842768, 16842769, 16843008, 16843009, 16843024, 16843025, 16846848, 16846849, 16846864, 16846865, 16847104, 16847105, 16847120, 16847121, 17825792, 17825793, 17825808, 17825809, 17826048, 17826049, 17826064, 17826065, 17829888, 17829889, 17829904, 17829905, 17830144, 17830145, 17830160, 17830161, 17891328, 17891329, 17891344, 17891345, 17891584, 17891585, 17891600, 17891601, 17895424, 17895425, 17895440, 17895441, 17895680, 17895681, 17895696, 17895697, 268435456, 268435457, 268435472, 268435473, 268435712, 268435713, 268435728, 268435729, 268439552, 268439553, 268439568, 268439569, 268439808, 268439809, 268439824, 268439825, 268500992, 268500993, 268501008, 268501009, 268501248, 268501249, 268501264, 268501265, 268505088, 268505089, 268505104, 268505105, 268505344, 268505345, 268505360, 268505361, 269484032, 269484033, 269484048, 269484049, 269484288, 269484289, 269484304, 269484305, 269488128, 269488129, 269488144, 269488145, 269488384, 269488385, 269488400, 269488401, 269549568, 269549569, 269549584, 269549585, 269549824, 269549825, 269549840, 269549841, 269553664, 269553665, 269553680, 269553681, 269553920, 269553921, 269553936, 269553937, 285212672, 285212673, 285212688, 285212689, 285212928, 285212929, 285212944, 285212945, 285216768, 285216769, 285216784, 285216785, 285217024, 285217025, 285217040, 285217041, 285278208, 285278209, 285278224, 285278225, 285278464, 285278465, 285278480, 285278481, 285282304, 285282305, 285282320, 285282321, 285282560, 285282561, 285282576, 285282577, 286261248, 286261249, 286261264, 286261265, 286261504, 286261505, 286261520, 286261521, 286265344, 286265345, 286265360, 286265361, 286265600, 286265601, 286265616, 286265617, 286326784, 286326785, 286326800, 286326801, 286327040, 286327041, 286327056, 286327057, 286330880, 286330881, 286330896, 286330897, 286331136, 286331137, 286331152, 286331153};
   private static final int[] INTERLEAVE5_TABLE = new int[]{0, 1, 32, 33, 1024, 1025, 1056, 1057, 32768, 32769, 32800, 32801, 33792, 33793, 33824, 33825, 1048576, 1048577, 1048608, 1048609, 1049600, 1049601, 1049632, 1049633, 1081344, 1081345, 1081376, 1081377, 1082368, 1082369, 1082400, 1082401, 33554432, 33554433, 33554464, 33554465, 33555456, 33555457, 33555488, 33555489, 33587200, 33587201, 33587232, 33587233, 33588224, 33588225, 33588256, 33588257, 34603008, 34603009, 34603040, 34603041, 34604032, 34604033, 34604064, 34604065, 34635776, 34635777, 34635808, 34635809, 34636800, 34636801, 34636832, 34636833, 1073741824, 1073741825, 1073741856, 1073741857, 1073742848, 1073742849, 1073742880, 1073742881, 1073774592, 1073774593, 1073774624, 1073774625, 1073775616, 1073775617, 1073775648, 1073775649, 1074790400, 1074790401, 1074790432, 1074790433, 1074791424, 1074791425, 1074791456, 1074791457, 1074823168, 1074823169, 1074823200, 1074823201, 1074824192, 1074824193, 1074824224, 1074824225, 1107296256, 1107296257, 1107296288, 1107296289, 1107297280, 1107297281, 1107297312, 1107297313, 1107329024, 1107329025, 1107329056, 1107329057, 1107330048, 1107330049, 1107330080, 1107330081, 1108344832, 1108344833, 1108344864, 1108344865, 1108345856, 1108345857, 1108345888, 1108345889, 1108377600, 1108377601, 1108377632, 1108377633, 1108378624, 1108378625, 1108378656, 1108378657};
   private static final long[] INTERLEAVE7_TABLE = new long[]{0L, 1L, 128L, 129L, 16384L, 16385L, 16512L, 16513L, 2097152L, 2097153L, 2097280L, 2097281L, 2113536L, 2113537L, 2113664L, 2113665L, 268435456L, 268435457L, 268435584L, 268435585L, 268451840L, 268451841L, 268451968L, 268451969L, 270532608L, 270532609L, 270532736L, 270532737L, 270548992L, 270548993L, 270549120L, 270549121L, 34359738368L, 34359738369L, 34359738496L, 34359738497L, 34359754752L, 34359754753L, 34359754880L, 34359754881L, 34361835520L, 34361835521L, 34361835648L, 34361835649L, 34361851904L, 34361851905L, 34361852032L, 34361852033L, 34628173824L, 34628173825L, 34628173952L, 34628173953L, 34628190208L, 34628190209L, 34628190336L, 34628190337L, 34630270976L, 34630270977L, 34630271104L, 34630271105L, 34630287360L, 34630287361L, 34630287488L, 34630287489L, 4398046511104L, 4398046511105L, 4398046511232L, 4398046511233L, 4398046527488L, 4398046527489L, 4398046527616L, 4398046527617L, 4398048608256L, 4398048608257L, 4398048608384L, 4398048608385L, 4398048624640L, 4398048624641L, 4398048624768L, 4398048624769L, 4398314946560L, 4398314946561L, 4398314946688L, 4398314946689L, 4398314962944L, 4398314962945L, 4398314963072L, 4398314963073L, 4398317043712L, 4398317043713L, 4398317043840L, 4398317043841L, 4398317060096L, 4398317060097L, 4398317060224L, 4398317060225L, 4432406249472L, 4432406249473L, 4432406249600L, 4432406249601L, 4432406265856L, 4432406265857L, 4432406265984L, 4432406265985L, 4432408346624L, 4432408346625L, 4432408346752L, 4432408346753L, 4432408363008L, 4432408363009L, 4432408363136L, 4432408363137L, 4432674684928L, 4432674684929L, 4432674685056L, 4432674685057L, 4432674701312L, 4432674701313L, 4432674701440L, 4432674701441L, 4432676782080L, 4432676782081L, 4432676782208L, 4432676782209L, 4432676798464L, 4432676798465L, 4432676798592L, 4432676798593L, 562949953421312L, 562949953421313L, 562949953421440L, 562949953421441L, 562949953437696L, 562949953437697L, 562949953437824L, 562949953437825L, 562949955518464L, 562949955518465L, 562949955518592L, 562949955518593L, 562949955534848L, 562949955534849L, 562949955534976L, 562949955534977L, 562950221856768L, 562950221856769L, 562950221856896L, 562950221856897L, 562950221873152L, 562950221873153L, 562950221873280L, 562950221873281L, 562950223953920L, 562950223953921L, 562950223954048L, 562950223954049L, 562950223970304L, 562950223970305L, 562950223970432L, 562950223970433L, 562984313159680L, 562984313159681L, 562984313159808L, 562984313159809L, 562984313176064L, 562984313176065L, 562984313176192L, 562984313176193L, 562984315256832L, 562984315256833L, 562984315256960L, 562984315256961L, 562984315273216L, 562984315273217L, 562984315273344L, 562984315273345L, 562984581595136L, 562984581595137L, 562984581595264L, 562984581595265L, 562984581611520L, 562984581611521L, 562984581611648L, 562984581611649L, 562984583692288L, 562984583692289L, 562984583692416L, 562984583692417L, 562984583708672L, 562984583708673L, 562984583708800L, 562984583708801L, 567347999932416L, 567347999932417L, 567347999932544L, 567347999932545L, 567347999948800L, 567347999948801L, 567347999948928L, 567347999948929L, 567348002029568L, 567348002029569L, 567348002029696L, 567348002029697L, 567348002045952L, 567348002045953L, 567348002046080L, 567348002046081L, 567348268367872L, 567348268367873L, 567348268368000L, 567348268368001L, 567348268384256L, 567348268384257L, 567348268384384L, 567348268384385L, 567348270465024L, 567348270465025L, 567348270465152L, 567348270465153L, 567348270481408L, 567348270481409L, 567348270481536L, 567348270481537L, 567382359670784L, 567382359670785L, 567382359670912L, 567382359670913L, 567382359687168L, 567382359687169L, 567382359687296L, 567382359687297L, 567382361767936L, 567382361767937L, 567382361768064L, 567382361768065L, 567382361784320L, 567382361784321L, 567382361784448L, 567382361784449L, 567382628106240L, 567382628106241L, 567382628106368L, 567382628106369L, 567382628122624L, 567382628122625L, 567382628122752L, 567382628122753L, 567382630203392L, 567382630203393L, 567382630203520L, 567382630203521L, 567382630219776L, 567382630219777L, 567382630219904L, 567382630219905L, 72057594037927936L, 72057594037927937L, 72057594037928064L, 72057594037928065L, 72057594037944320L, 72057594037944321L, 72057594037944448L, 72057594037944449L, 72057594040025088L, 72057594040025089L, 72057594040025216L, 72057594040025217L, 72057594040041472L, 72057594040041473L, 72057594040041600L, 72057594040041601L, 72057594306363392L, 72057594306363393L, 72057594306363520L, 72057594306363521L, 72057594306379776L, 72057594306379777L, 72057594306379904L, 72057594306379905L, 72057594308460544L, 72057594308460545L, 72057594308460672L, 72057594308460673L, 72057594308476928L, 72057594308476929L, 72057594308477056L, 72057594308477057L, 72057628397666304L, 72057628397666305L, 72057628397666432L, 72057628397666433L, 72057628397682688L, 72057628397682689L, 72057628397682816L, 72057628397682817L, 72057628399763456L, 72057628399763457L, 72057628399763584L, 72057628399763585L, 72057628399779840L, 72057628399779841L, 72057628399779968L, 72057628399779969L, 72057628666101760L, 72057628666101761L, 72057628666101888L, 72057628666101889L, 72057628666118144L, 72057628666118145L, 72057628666118272L, 72057628666118273L, 72057628668198912L, 72057628668198913L, 72057628668199040L, 72057628668199041L, 72057628668215296L, 72057628668215297L, 72057628668215424L, 72057628668215425L, 72061992084439040L, 72061992084439041L, 72061992084439168L, 72061992084439169L, 72061992084455424L, 72061992084455425L, 72061992084455552L, 72061992084455553L, 72061992086536192L, 72061992086536193L, 72061992086536320L, 72061992086536321L, 72061992086552576L, 72061992086552577L, 72061992086552704L, 72061992086552705L, 72061992352874496L, 72061992352874497L, 72061992352874624L, 72061992352874625L, 72061992352890880L, 72061992352890881L, 72061992352891008L, 72061992352891009L, 72061992354971648L, 72061992354971649L, 72061992354971776L, 72061992354971777L, 72061992354988032L, 72061992354988033L, 72061992354988160L, 72061992354988161L, 72062026444177408L, 72062026444177409L, 72062026444177536L, 72062026444177537L, 72062026444193792L, 72062026444193793L, 72062026444193920L, 72062026444193921L, 72062026446274560L, 72062026446274561L, 72062026446274688L, 72062026446274689L, 72062026446290944L, 72062026446290945L, 72062026446291072L, 72062026446291073L, 72062026712612864L, 72062026712612865L, 72062026712612992L, 72062026712612993L, 72062026712629248L, 72062026712629249L, 72062026712629376L, 72062026712629377L, 72062026714710016L, 72062026714710017L, 72062026714710144L, 72062026714710145L, 72062026714726400L, 72062026714726401L, 72062026714726528L, 72062026714726529L, 72620543991349248L, 72620543991349249L, 72620543991349376L, 72620543991349377L, 72620543991365632L, 72620543991365633L, 72620543991365760L, 72620543991365761L, 72620543993446400L, 72620543993446401L, 72620543993446528L, 72620543993446529L, 72620543993462784L, 72620543993462785L, 72620543993462912L, 72620543993462913L, 72620544259784704L, 72620544259784705L, 72620544259784832L, 72620544259784833L, 72620544259801088L, 72620544259801089L, 72620544259801216L, 72620544259801217L, 72620544261881856L, 72620544261881857L, 72620544261881984L, 72620544261881985L, 72620544261898240L, 72620544261898241L, 72620544261898368L, 72620544261898369L, 72620578351087616L, 72620578351087617L, 72620578351087744L, 72620578351087745L, 72620578351104000L, 72620578351104001L, 72620578351104128L, 72620578351104129L, 72620578353184768L, 72620578353184769L, 72620578353184896L, 72620578353184897L, 72620578353201152L, 72620578353201153L, 72620578353201280L, 72620578353201281L, 72620578619523072L, 72620578619523073L, 72620578619523200L, 72620578619523201L, 72620578619539456L, 72620578619539457L, 72620578619539584L, 72620578619539585L, 72620578621620224L, 72620578621620225L, 72620578621620352L, 72620578621620353L, 72620578621636608L, 72620578621636609L, 72620578621636736L, 72620578621636737L, 72624942037860352L, 72624942037860353L, 72624942037860480L, 72624942037860481L, 72624942037876736L, 72624942037876737L, 72624942037876864L, 72624942037876865L, 72624942039957504L, 72624942039957505L, 72624942039957632L, 72624942039957633L, 72624942039973888L, 72624942039973889L, 72624942039974016L, 72624942039974017L, 72624942306295808L, 72624942306295809L, 72624942306295936L, 72624942306295937L, 72624942306312192L, 72624942306312193L, 72624942306312320L, 72624942306312321L, 72624942308392960L, 72624942308392961L, 72624942308393088L, 72624942308393089L, 72624942308409344L, 72624942308409345L, 72624942308409472L, 72624942308409473L, 72624976397598720L, 72624976397598721L, 72624976397598848L, 72624976397598849L, 72624976397615104L, 72624976397615105L, 72624976397615232L, 72624976397615233L, 72624976399695872L, 72624976399695873L, 72624976399696000L, 72624976399696001L, 72624976399712256L, 72624976399712257L, 72624976399712384L, 72624976399712385L, 72624976666034176L, 72624976666034177L, 72624976666034304L, 72624976666034305L, 72624976666050560L, 72624976666050561L, 72624976666050688L, 72624976666050689L, 72624976668131328L, 72624976668131329L, 72624976668131456L, 72624976668131457L, 72624976668147712L, 72624976668147713L, 72624976668147840L, 72624976668147841L};
   private static final String ZEROES = "0000000000000000000000000000000000000000000000000000000000000000";
   static final byte[] bitLengths = new byte[]{0, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
   private long[] m_ints;

   public LongArray(int var1) {
      this.m_ints = new long[var1];
   }

   public LongArray(long[] var1) {
      this.m_ints = var1;
   }

   public LongArray(long[] var1, int var2, int var3) {
      if (var2 == 0 && var3 == var1.length) {
         this.m_ints = var1;
      } else {
         this.m_ints = new long[var3];
         System.arraycopy(var1, var2, this.m_ints, 0, var3);
      }

   }

   public LongArray(BigInteger var1) {
      if (var1 != null && var1.signum() >= 0) {
         if (var1.signum() == 0) {
            this.m_ints = new long[]{0L};
         } else {
            byte[] var2 = var1.toByteArray();
            int var3 = var2.length;
            byte var4 = 0;
            if (var2[0] == 0) {
               --var3;
               var4 = 1;
            }

            int var5 = (var3 + 7) / 8;
            this.m_ints = new long[var5];
            int var6 = var5 - 1;
            int var7 = var3 % 8 + var4;
            long var8 = 0L;
            int var10 = var4;
            int var11;
            if (var4 < var7) {
               while(var10 < var7) {
                  var8 <<= 8;
                  var11 = var2[var10] & 255;
                  var8 |= (long)var11;
                  ++var10;
               }

               this.m_ints[var6--] = var8;
            }

            while(var6 >= 0) {
               var8 = 0L;

               for(var11 = 0; var11 < 8; ++var11) {
                  var8 <<= 8;
                  int var12 = var2[var10++] & 255;
                  var8 |= (long)var12;
               }

               this.m_ints[var6] = var8;
               --var6;
            }

         }
      } else {
         throw new IllegalArgumentException("invalid F2m field value");
      }
   }

   public boolean isOne() {
      long[] var1 = this.m_ints;
      if (var1[0] != 1L) {
         return false;
      } else {
         for(int var2 = 1; var2 < var1.length; ++var2) {
            if (var1[var2] != 0L) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isZero() {
      long[] var1 = this.m_ints;

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2] != 0L) {
            return false;
         }
      }

      return true;
   }

   public int getUsedLength() {
      return this.getUsedLengthFrom(this.m_ints.length);
   }

   public int getUsedLengthFrom(int var1) {
      long[] var2 = this.m_ints;
      var1 = Math.min(var1, var2.length);
      if (var1 < 1) {
         return 0;
      } else if (var2[0] != 0L) {
         do {
            --var1;
         } while(var2[var1] == 0L);

         return var1 + 1;
      } else {
         do {
            --var1;
            if (var2[var1] != 0L) {
               return var1 + 1;
            }
         } while(var1 > 0);

         return 0;
      }
   }

   public int degree() {
      int var1 = this.m_ints.length;

      while(var1 != 0) {
         --var1;
         long var2 = this.m_ints[var1];
         if (var2 != 0L) {
            return (var1 << 6) + bitLength(var2);
         }
      }

      return 0;
   }

   private int degreeFrom(int var1) {
      int var2 = var1 + 62 >>> 6;

      while(var2 != 0) {
         --var2;
         long var3 = this.m_ints[var2];
         if (var3 != 0L) {
            return (var2 << 6) + bitLength(var3);
         }
      }

      return 0;
   }

   private static int bitLength(long var0) {
      int var2 = (int)(var0 >>> 32);
      byte var3;
      if (var2 == 0) {
         var2 = (int)var0;
         var3 = 0;
      } else {
         var3 = 32;
      }

      int var4 = var2 >>> 16;
      int var5;
      if (var4 == 0) {
         var4 = var2 >>> 8;
         var5 = var4 == 0 ? bitLengths[var2] : 8 + bitLengths[var4];
      } else {
         int var6 = var4 >>> 8;
         var5 = var6 == 0 ? 16 + bitLengths[var4] : 24 + bitLengths[var6];
      }

      return var3 + var5;
   }

   private long[] resizedInts(int var1) {
      long[] var2 = new long[var1];
      System.arraycopy(this.m_ints, 0, var2, 0, Math.min(this.m_ints.length, var1));
      return var2;
   }

   public BigInteger toBigInteger() {
      int var1 = this.getUsedLength();
      if (var1 == 0) {
         return ECConstants.ZERO;
      } else {
         long var2 = this.m_ints[var1 - 1];
         byte[] var4 = new byte[8];
         int var5 = 0;
         boolean var6 = false;

         int var7;
         for(var7 = 7; var7 >= 0; --var7) {
            byte var8 = (byte)((int)(var2 >>> 8 * var7));
            if (var6 || var8 != 0) {
               var6 = true;
               var4[var5++] = var8;
            }
         }

         var7 = 8 * (var1 - 1) + var5;
         byte[] var13 = new byte[var7];

         int var9;
         for(var9 = 0; var9 < var5; ++var9) {
            var13[var9] = var4[var9];
         }

         for(var9 = var1 - 2; var9 >= 0; --var9) {
            long var10 = this.m_ints[var9];

            for(int var12 = 7; var12 >= 0; --var12) {
               var13[var5++] = (byte)((int)(var10 >>> 8 * var12));
            }
         }

         return new BigInteger(1, var13);
      }
   }

   private static long shiftUp(long[] var0, int var1, int var2, int var3) {
      int var4 = 64 - var3;
      long var5 = 0L;

      for(int var7 = 0; var7 < var2; ++var7) {
         long var8 = var0[var1 + var7];
         var0[var1 + var7] = var8 << var3 | var5;
         var5 = var8 >>> var4;
      }

      return var5;
   }

   private static long shiftUp(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      int var6 = 64 - var5;
      long var7 = 0L;

      for(int var9 = 0; var9 < var4; ++var9) {
         long var10 = var0[var1 + var9];
         var2[var3 + var9] = var10 << var5 | var7;
         var7 = var10 >>> var6;
      }

      return var7;
   }

   public LongArray addOne() {
      if (this.m_ints.length == 0) {
         return new LongArray(new long[]{1L});
      } else {
         int var1 = Math.max(1, this.getUsedLength());
         long[] var2 = this.resizedInts(var1);
         var2[0] ^= 1L;
         return new LongArray(var2);
      }
   }

   private void addShiftedByBitsSafe(LongArray var1, int var2, int var3) {
      int var4 = var2 + 63 >>> 6;
      int var5 = var3 >>> 6;
      int var6 = var3 & 63;
      if (var6 == 0) {
         add(this.m_ints, var5, var1.m_ints, 0, var4);
      } else {
         long var7 = addShiftedUp(this.m_ints, var5, var1.m_ints, 0, var4, var6);
         if (var7 != 0L) {
            long[] var10000 = this.m_ints;
            var10000[var4 + var5] ^= var7;
         }

      }
   }

   private static long addShiftedUp(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      int var6 = 64 - var5;
      long var7 = 0L;

      for(int var9 = 0; var9 < var4; ++var9) {
         long var10 = var2[var3 + var9];
         var0[var1 + var9] ^= var10 << var5 | var7;
         var7 = var10 >>> var6;
      }

      return var7;
   }

   private static long addShiftedDown(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      int var6 = 64 - var5;
      long var7 = 0L;
      int var9 = var4;

      while(true) {
         --var9;
         if (var9 < 0) {
            return var7;
         }

         long var10 = var2[var3 + var9];
         var0[var1 + var9] ^= var10 >>> var5 | var7;
         var7 = var10 << var6;
      }
   }

   public void addShiftedByWords(LongArray var1, int var2) {
      int var3 = var1.getUsedLength();
      if (var3 != 0) {
         int var4 = var3 + var2;
         if (var4 > this.m_ints.length) {
            this.m_ints = this.resizedInts(var4);
         }

         add(this.m_ints, var2, var1.m_ints, 0, var3);
      }
   }

   private static void add(long[] var0, int var1, long[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         var0[var1 + var5] ^= var2[var3 + var5];
      }

   }

   private static void add(long[] var0, int var1, long[] var2, int var3, long[] var4, int var5, int var6) {
      for(int var7 = 0; var7 < var6; ++var7) {
         var4[var5 + var7] = var0[var1 + var7] ^ var2[var3 + var7];
      }

   }

   private static void addBoth(long[] var0, int var1, long[] var2, int var3, long[] var4, int var5, int var6) {
      for(int var7 = 0; var7 < var6; ++var7) {
         var0[var1 + var7] ^= var2[var3 + var7] ^ var4[var5 + var7];
      }

   }

   private static void distribute(long[] var0, int var1, int var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         long var6 = var0[var1 + var5];
         var0[var2 + var5] ^= var6;
         var0[var3 + var5] ^= var6;
      }

   }

   public int getLength() {
      return this.m_ints.length;
   }

   private static void flipWord(long[] var0, int var1, int var2, long var3) {
      int var5 = var1 + (var2 >>> 6);
      int var6 = var2 & 63;
      if (var6 == 0) {
         var0[var5] ^= var3;
      } else {
         var0[var5] ^= var3 << var6;
         var3 >>>= 64 - var6;
         if (var3 != 0L) {
            ++var5;
            var0[var5] ^= var3;
         }
      }

   }

   public boolean testBitZero() {
      return this.m_ints.length > 0 && (this.m_ints[0] & 1L) != 0L;
   }

   private static boolean testBit(long[] var0, int var1, int var2) {
      int var3 = var2 >>> 6;
      int var4 = var2 & 63;
      long var5 = 1L << var4;
      return (var0[var1 + var3] & var5) != 0L;
   }

   private static void flipBit(long[] var0, int var1, int var2) {
      int var3 = var2 >>> 6;
      int var4 = var2 & 63;
      long var5 = 1L << var4;
      var0[var1 + var3] ^= var5;
   }

   private static void multiplyWord(long var0, long[] var2, int var3, long[] var4, int var5) {
      if ((var0 & 1L) != 0L) {
         add(var4, var5, var2, 0, var3);
      }

      for(int var6 = 1; (var0 >>>= 1) != 0L; ++var6) {
         if ((var0 & 1L) != 0L) {
            long var7 = addShiftedUp(var4, var5, var2, 0, var3, var6);
            if (var7 != 0L) {
               var4[var5 + var3] ^= var7;
            }
         }
      }

   }

   public LongArray modMultiplyLD(LongArray var1, int var2, int[] var3) {
      int var4 = this.degree();
      if (var4 == 0) {
         return this;
      } else {
         int var5 = var1.degree();
         if (var5 == 0) {
            return var1;
         } else {
            LongArray var6 = this;
            LongArray var7 = var1;
            int var8;
            if (var4 > var5) {
               var6 = var1;
               var7 = this;
               var8 = var4;
               var4 = var5;
               var5 = var8;
            }

            var8 = var4 + 63 >>> 6;
            int var9 = var5 + 63 >>> 6;
            int var10 = var4 + var5 + 62 >>> 6;
            long[] var13;
            if (var8 == 1) {
               long var11 = var6.m_ints[0];
               if (var11 == 1L) {
                  return var7;
               } else {
                  var13 = new long[var10];
                  multiplyWord(var11, var7.m_ints, var9, var13, 0);
                  return reduceResult(var13, 0, var10, var2, var3);
               }
            } else {
               int var14 = var5 + 7 + 63 >>> 6;
               int[] var15 = new int[16];
               var13 = new long[var14 << 4];
               int var16 = var14;
               var15[1] = var14;
               System.arraycopy(var7.m_ints, 0, var13, var14, var9);

               for(int var17 = 2; var17 < 16; ++var17) {
                  var15[var17] = var16 += var14;
                  if ((var17 & 1) == 0) {
                     shiftUp(var13, var16 >>> 1, var13, var16, var14, 1);
                  } else {
                     add(var13, var14, var13, var16 - var14, var13, var16, var14);
                  }
               }

               long[] var26 = new long[var13.length];
               shiftUp(var13, 0, var26, 0, var13.length, 4);
               long[] var18 = var6.m_ints;
               long[] var19 = new long[var10];
               byte var20 = 15;

               int var21;
               int var22;
               int var23;
               int var24;
               int var25;
               for(var21 = 56; var21 >= 0; var21 -= 8) {
                  for(var22 = 1; var22 < var8; var22 += 2) {
                     var23 = (int)(var18[var22] >>> var21);
                     var24 = var23 & var20;
                     var25 = var23 >>> 4 & var20;
                     addBoth(var19, var22 - 1, var13, var15[var24], var26, var15[var25], var14);
                  }

                  shiftUp(var19, 0, var10, 8);
               }

               for(var21 = 56; var21 >= 0; var21 -= 8) {
                  for(var22 = 0; var22 < var8; var22 += 2) {
                     var23 = (int)(var18[var22] >>> var21);
                     var24 = var23 & var20;
                     var25 = var23 >>> 4 & var20;
                     addBoth(var19, var22, var13, var15[var24], var26, var15[var25], var14);
                  }

                  if (var21 > 0) {
                     shiftUp(var19, 0, var10, 8);
                  }
               }

               return reduceResult(var19, 0, var10, var2, var3);
            }
         }
      }
   }

   public LongArray modMultiply(LongArray var1, int var2, int[] var3) {
      int var4 = this.degree();
      if (var4 == 0) {
         return this;
      } else {
         int var5 = var1.degree();
         if (var5 == 0) {
            return var1;
         } else {
            LongArray var6 = this;
            LongArray var7 = var1;
            int var8;
            if (var4 > var5) {
               var6 = var1;
               var7 = this;
               var8 = var4;
               var4 = var5;
               var5 = var8;
            }

            var8 = var4 + 63 >>> 6;
            int var9 = var5 + 63 >>> 6;
            int var10 = var4 + var5 + 62 >>> 6;
            long[] var13;
            if (var8 == 1) {
               long var11 = var6.m_ints[0];
               if (var11 == 1L) {
                  return var7;
               } else {
                  var13 = new long[var10];
                  multiplyWord(var11, var7.m_ints, var9, var13, 0);
                  return reduceResult(var13, 0, var10, var2, var3);
               }
            } else {
               int var14 = var5 + 7 + 63 >>> 6;
               int[] var15 = new int[16];
               var13 = new long[var14 << 4];
               int var16 = var14;
               var15[1] = var14;
               System.arraycopy(var7.m_ints, 0, var13, var14, var9);

               for(int var17 = 2; var17 < 16; ++var17) {
                  var15[var17] = var16 += var14;
                  if ((var17 & 1) == 0) {
                     shiftUp(var13, var16 >>> 1, var13, var16, var14, 1);
                  } else {
                     add(var13, var14, var13, var16 - var14, var13, var16, var14);
                  }
               }

               long[] var27 = new long[var13.length];
               shiftUp(var13, 0, var27, 0, var13.length, 4);
               long[] var18 = var6.m_ints;
               long[] var19 = new long[var10 << 3];
               byte var20 = 15;

               int var21;
               for(var21 = 0; var21 < var8; ++var21) {
                  long var22 = var18[var21];
                  int var24 = var21;

                  while(true) {
                     int var25 = (int)var22 & var20;
                     var22 >>>= 4;
                     int var26 = (int)var22 & var20;
                     addBoth(var19, var24, var13, var15[var25], var27, var15[var26], var14);
                     var22 >>>= 4;
                     if (var22 == 0L) {
                        break;
                     }

                     var24 += var10;
                  }
               }

               var21 = var19.length;

               while((var21 -= var10) != 0) {
                  addShiftedUp(var19, var21 - var10, var19, var21, var10, 8);
               }

               return reduceResult(var19, 0, var10, var2, var3);
            }
         }
      }
   }

   public LongArray modMultiplyAlt(LongArray var1, int var2, int[] var3) {
      int var4 = this.degree();
      if (var4 == 0) {
         return this;
      } else {
         int var5 = var1.degree();
         if (var5 == 0) {
            return var1;
         } else {
            LongArray var6 = this;
            LongArray var7 = var1;
            int var8;
            if (var4 > var5) {
               var6 = var1;
               var7 = this;
               var8 = var4;
               var4 = var5;
               var5 = var8;
            }

            var8 = var4 + 63 >>> 6;
            int var9 = var5 + 63 >>> 6;
            int var10 = var4 + var5 + 62 >>> 6;
            if (var8 == 1) {
               long var11 = var6.m_ints[0];
               if (var11 == 1L) {
                  return var7;
               } else {
                  long[] var32 = new long[var10];
                  multiplyWord(var11, var7.m_ints, var9, var32, 0);
                  return reduceResult(var32, 0, var10, var2, var3);
               }
            } else {
               byte var14 = 4;
               byte var15 = 16;
               byte var13 = 64;
               byte var16 = 8;
               int var17 = var13 < 64 ? var15 : var15 - 1;
               int var18 = var5 + var17 + 63 >>> 6;
               int var19 = var18 * var16;
               int var20 = var14 * var16;
               int[] var21 = new int[1 << var14];
               var21[0] = var8;
               int var22 = var8 + var19;
               var21[1] = var22;

               for(int var23 = 2; var23 < var21.length; ++var23) {
                  var22 += var10;
                  var21[var23] = var22;
               }

               var22 += var10;
               ++var22;
               long[] var33 = new long[var22];
               interleave(var6.m_ints, 0, var33, 0, var8, var14);
               int var24 = var8;
               System.arraycopy(var7.m_ints, 0, var33, var8, var9);

               int var25;
               for(var25 = 1; var25 < var16; ++var25) {
                  shiftUp(var33, var8, var33, var24 += var18, var18, var25);
               }

               var24 = (1 << var14) - 1;
               var25 = 0;

               while(true) {
                  int var26 = 0;

                  do {
                     long var27 = var33[var26] >>> var25;
                     int var29 = 0;
                     int var30 = var8;

                     while(true) {
                        int var31 = (int)var27 & var24;
                        if (var31 != 0) {
                           add(var33, var26 + var21[var31], var33, var30, var18);
                        }

                        ++var29;
                        if (var29 == var16) {
                           ++var26;
                           break;
                        }

                        var30 += var18;
                        var27 >>>= var14;
                     }
                  } while(var26 < var8);

                  if ((var25 += var20) >= var13) {
                     if (var25 >= 64) {
                        var26 = var21.length;

                        while(true) {
                           --var26;
                           if (var26 <= 1) {
                              return reduceResult(var33, var21[1], var10, var2, var3);
                           }

                           if (((long)var26 & 1L) == 0L) {
                              addShiftedUp(var33, var21[var26 >>> 1], var33, var21[var26], var10, var15);
                           } else {
                              distribute(var33, var21[var26], var21[var26 - 1], var21[1], var10);
                           }
                        }
                     }

                     var25 = 64 - var14;
                     var24 &= var24 << var13 - var25;
                  }

                  shiftUp(var33, var8, var19, var16);
               }
            }
         }
      }
   }

   public LongArray modReduce(int var1, int[] var2) {
      long[] var3 = Arrays.clone(this.m_ints);
      int var4 = reduceInPlace(var3, 0, var3.length, var1, var2);
      return new LongArray(var3, 0, var4);
   }

   public LongArray multiply(LongArray var1, int var2, int[] var3) {
      int var4 = this.degree();
      if (var4 == 0) {
         return this;
      } else {
         int var5 = var1.degree();
         if (var5 == 0) {
            return var1;
         } else {
            LongArray var6 = this;
            LongArray var7 = var1;
            int var8;
            if (var4 > var5) {
               var6 = var1;
               var7 = this;
               var8 = var4;
               var4 = var5;
               var5 = var8;
            }

            var8 = var4 + 63 >>> 6;
            int var9 = var5 + 63 >>> 6;
            int var10 = var4 + var5 + 62 >>> 6;
            long[] var13;
            if (var8 == 1) {
               long var11 = var6.m_ints[0];
               if (var11 == 1L) {
                  return var7;
               } else {
                  var13 = new long[var10];
                  multiplyWord(var11, var7.m_ints, var9, var13, 0);
                  return new LongArray(var13, 0, var10);
               }
            } else {
               int var14 = var5 + 7 + 63 >>> 6;
               int[] var15 = new int[16];
               var13 = new long[var14 << 4];
               int var16 = var14;
               var15[1] = var14;
               System.arraycopy(var7.m_ints, 0, var13, var14, var9);

               for(int var17 = 2; var17 < 16; ++var17) {
                  var15[var17] = var16 += var14;
                  if ((var17 & 1) == 0) {
                     shiftUp(var13, var16 >>> 1, var13, var16, var14, 1);
                  } else {
                     add(var13, var14, var13, var16 - var14, var13, var16, var14);
                  }
               }

               long[] var27 = new long[var13.length];
               shiftUp(var13, 0, var27, 0, var13.length, 4);
               long[] var18 = var6.m_ints;
               long[] var19 = new long[var10 << 3];
               byte var20 = 15;

               int var21;
               for(var21 = 0; var21 < var8; ++var21) {
                  long var22 = var18[var21];
                  int var24 = var21;

                  while(true) {
                     int var25 = (int)var22 & var20;
                     var22 >>>= 4;
                     int var26 = (int)var22 & var20;
                     addBoth(var19, var24, var13, var15[var25], var27, var15[var26], var14);
                     var22 >>>= 4;
                     if (var22 == 0L) {
                        break;
                     }

                     var24 += var10;
                  }
               }

               var21 = var19.length;

               while((var21 -= var10) != 0) {
                  addShiftedUp(var19, var21 - var10, var19, var21, var10, 8);
               }

               return new LongArray(var19, 0, var10);
            }
         }
      }
   }

   public void reduce(int var1, int[] var2) {
      long[] var3 = this.m_ints;
      int var4 = reduceInPlace(var3, 0, var3.length, var1, var2);
      if (var4 < var3.length) {
         this.m_ints = new long[var4];
         System.arraycopy(var3, 0, this.m_ints, 0, var4);
      }

   }

   private static LongArray reduceResult(long[] var0, int var1, int var2, int var3, int[] var4) {
      int var5 = reduceInPlace(var0, var1, var2, var3, var4);
      return new LongArray(var0, var1, var5);
   }

   private static int reduceInPlace(long[] var0, int var1, int var2, int var3, int[] var4) {
      int var5 = var3 + 63 >>> 6;
      if (var2 < var5) {
         return var2;
      } else {
         int var6 = Math.min(var2 << 6, (var3 << 1) - 1);

         int var7;
         for(var7 = (var2 << 6) - var6; var7 >= 64; var7 -= 64) {
            --var2;
         }

         int var8 = var4.length;
         int var9 = var4[var8 - 1];
         int var10 = var8 > 1 ? var4[var8 - 2] : 0;
         int var11 = Math.max(var3, var9 + 64);
         int var12 = var7 + Math.min(var6 - var11, var3 - var10) >> 6;
         if (var12 > 1) {
            int var13 = var2 - var12;
            reduceVectorWise(var0, var1, var2, var13, var3, var4);

            while(var2 > var13) {
               --var2;
               var0[var1 + var2] = 0L;
            }

            var6 = var13 << 6;
         }

         if (var6 > var11) {
            reduceWordWise(var0, var1, var2, var11, var3, var4);
            var6 = var11;
         }

         if (var6 > var3) {
            reduceBitWise(var0, var1, var6, var3, var4);
         }

         return var5;
      }
   }

   private static void reduceBitWise(long[] var0, int var1, int var2, int var3, int[] var4) {
      while(true) {
         --var2;
         if (var2 < var3) {
            return;
         }

         if (testBit(var0, var1, var2)) {
            reduceBit(var0, var1, var2, var3, var4);
         }
      }
   }

   private static void reduceBit(long[] var0, int var1, int var2, int var3, int[] var4) {
      flipBit(var0, var1, var2);
      int var5 = var2 - var3;
      int var6 = var4.length;

      while(true) {
         --var6;
         if (var6 < 0) {
            flipBit(var0, var1, var5);
            return;
         }

         flipBit(var0, var1, var4[var6] + var5);
      }
   }

   private static void reduceWordWise(long[] var0, int var1, int var2, int var3, int var4, int[] var5) {
      int var6 = var3 >>> 6;

      while(true) {
         --var2;
         if (var2 <= var6) {
            int var9 = var3 & 63;
            long var10 = var0[var1 + var6] >>> var9;
            if (var10 != 0L) {
               var0[var1 + var6] ^= var10 << var9;
               reduceWord(var0, var1, var3, var10, var4, var5);
            }

            return;
         }

         long var7 = var0[var1 + var2];
         if (var7 != 0L) {
            var0[var1 + var2] = 0L;
            reduceWord(var0, var1, var2 << 6, var7, var4, var5);
         }
      }
   }

   private static void reduceWord(long[] var0, int var1, int var2, long var3, int var5, int[] var6) {
      int var7 = var2 - var5;
      int var8 = var6.length;

      while(true) {
         --var8;
         if (var8 < 0) {
            flipWord(var0, var1, var7, var3);
            return;
         }

         flipWord(var0, var1, var7 + var6[var8], var3);
      }
   }

   private static void reduceVectorWise(long[] var0, int var1, int var2, int var3, int var4, int[] var5) {
      int var6 = (var3 << 6) - var4;
      int var7 = var5.length;

      while(true) {
         --var7;
         if (var7 < 0) {
            flipVector(var0, var1, var0, var1 + var3, var2 - var3, var6);
            return;
         }

         flipVector(var0, var1, var0, var1 + var3, var2 - var3, var6 + var5[var7]);
      }
   }

   private static void flipVector(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      var1 += var5 >>> 6;
      var5 &= 63;
      if (var5 == 0) {
         add(var0, var1, var2, var3, var4);
      } else {
         long var6 = addShiftedDown(var0, var1 + 1, var2, var3, var4, 64 - var5);
         var0[var1] ^= var6;
      }

   }

   public LongArray modSquare(int var1, int[] var2) {
      int var3 = this.getUsedLength();
      if (var3 == 0) {
         return this;
      } else {
         int var4 = var3 << 1;
         long[] var5 = new long[var4];

         long var7;
         for(int var6 = 0; var6 < var4; var5[var6++] = interleave2_32to64((int)(var7 >>> 32))) {
            var7 = this.m_ints[var6 >>> 1];
            var5[var6++] = interleave2_32to64((int)var7);
         }

         return new LongArray(var5, 0, reduceInPlace(var5, 0, var5.length, var1, var2));
      }
   }

   public LongArray modSquareN(int var1, int var2, int[] var3) {
      int var4 = this.getUsedLength();
      if (var4 == 0) {
         return this;
      } else {
         int var5 = var2 + 63 >>> 6;
         long[] var6 = new long[var5 << 1];
         System.arraycopy(this.m_ints, 0, var6, 0, var4);

         while(true) {
            --var1;
            if (var1 < 0) {
               return new LongArray(var6, 0, var4);
            }

            squareInPlace(var6, var4, var2, var3);
            var4 = reduceInPlace(var6, 0, var6.length, var2, var3);
         }
      }
   }

   public LongArray square(int var1, int[] var2) {
      int var3 = this.getUsedLength();
      if (var3 == 0) {
         return this;
      } else {
         int var4 = var3 << 1;
         long[] var5 = new long[var4];

         long var7;
         for(int var6 = 0; var6 < var4; var5[var6++] = interleave2_32to64((int)(var7 >>> 32))) {
            var7 = this.m_ints[var6 >>> 1];
            var5[var6++] = interleave2_32to64((int)var7);
         }

         return new LongArray(var5, 0, var5.length);
      }
   }

   private static void squareInPlace(long[] var0, int var1, int var2, int[] var3) {
      int var4 = var1 << 1;

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         long var5 = var0[var1];
         --var4;
         var0[var4] = interleave2_32to64((int)(var5 >>> 32));
         --var4;
         var0[var4] = interleave2_32to64((int)var5);
      }
   }

   private static void interleave(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      switch (var5) {
         case 3:
            interleave3(var0, var1, var2, var3, var4);
            break;
         case 4:
         case 6:
         default:
            interleave2_n(var0, var1, var2, var3, var4, bitLengths[var5] - 1);
            break;
         case 5:
            interleave5(var0, var1, var2, var3, var4);
            break;
         case 7:
            interleave7(var0, var1, var2, var3, var4);
      }

   }

   private static void interleave3(long[] var0, int var1, long[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         var2[var3 + var5] = interleave3(var0[var1 + var5]);
      }

   }

   private static long interleave3(long var0) {
      long var2 = var0 & Long.MIN_VALUE;
      return var2 | interleave3_21to63((int)var0 & 2097151) | interleave3_21to63((int)(var0 >>> 21) & 2097151) << 1 | interleave3_21to63((int)(var0 >>> 42) & 2097151) << 2;
   }

   private static long interleave3_21to63(int var0) {
      int var1 = INTERLEAVE3_TABLE[var0 & 127];
      int var2 = INTERLEAVE3_TABLE[var0 >>> 7 & 127];
      int var3 = INTERLEAVE3_TABLE[var0 >>> 14];
      return ((long)var3 & 4294967295L) << 42 | ((long)var2 & 4294967295L) << 21 | (long)var1 & 4294967295L;
   }

   private static void interleave5(long[] var0, int var1, long[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         var2[var3 + var5] = interleave5(var0[var1 + var5]);
      }

   }

   private static long interleave5(long var0) {
      return interleave3_13to65((int)var0 & 8191) | interleave3_13to65((int)(var0 >>> 13) & 8191) << 1 | interleave3_13to65((int)(var0 >>> 26) & 8191) << 2 | interleave3_13to65((int)(var0 >>> 39) & 8191) << 3 | interleave3_13to65((int)(var0 >>> 52) & 8191) << 4;
   }

   private static long interleave3_13to65(int var0) {
      int var1 = INTERLEAVE5_TABLE[var0 & 127];
      int var2 = INTERLEAVE5_TABLE[var0 >>> 7];
      return ((long)var2 & 4294967295L) << 35 | (long)var1 & 4294967295L;
   }

   private static void interleave7(long[] var0, int var1, long[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         var2[var3 + var5] = interleave7(var0[var1 + var5]);
      }

   }

   private static long interleave7(long var0) {
      long var2 = var0 & Long.MIN_VALUE;
      return var2 | INTERLEAVE7_TABLE[(int)var0 & 511] | INTERLEAVE7_TABLE[(int)(var0 >>> 9) & 511] << 1 | INTERLEAVE7_TABLE[(int)(var0 >>> 18) & 511] << 2 | INTERLEAVE7_TABLE[(int)(var0 >>> 27) & 511] << 3 | INTERLEAVE7_TABLE[(int)(var0 >>> 36) & 511] << 4 | INTERLEAVE7_TABLE[(int)(var0 >>> 45) & 511] << 5 | INTERLEAVE7_TABLE[(int)(var0 >>> 54) & 511] << 6;
   }

   private static void interleave2_n(long[] var0, int var1, long[] var2, int var3, int var4, int var5) {
      for(int var6 = 0; var6 < var4; ++var6) {
         var2[var3 + var6] = interleave2_n(var0[var1 + var6], var5);
      }

   }

   private static long interleave2_n(long var0, int var2) {
      while(var2 > 1) {
         var2 -= 2;
         var0 = interleave4_16to64((int)var0 & '\uffff') | interleave4_16to64((int)(var0 >>> 16) & '\uffff') << 1 | interleave4_16to64((int)(var0 >>> 32) & '\uffff') << 2 | interleave4_16to64((int)(var0 >>> 48) & '\uffff') << 3;
      }

      if (var2 > 0) {
         var0 = interleave2_32to64((int)var0) | interleave2_32to64((int)(var0 >>> 32)) << 1;
      }

      return var0;
   }

   private static long interleave4_16to64(int var0) {
      int var1 = INTERLEAVE4_TABLE[var0 & 255];
      int var2 = INTERLEAVE4_TABLE[var0 >>> 8];
      return ((long)var2 & 4294967295L) << 32 | (long)var1 & 4294967295L;
   }

   private static long interleave2_32to64(int var0) {
      int var1 = INTERLEAVE2_TABLE[var0 & 255] | INTERLEAVE2_TABLE[var0 >>> 8 & 255] << 16;
      int var2 = INTERLEAVE2_TABLE[var0 >>> 16 & 255] | INTERLEAVE2_TABLE[var0 >>> 24] << 16;
      return ((long)var2 & 4294967295L) << 32 | (long)var1 & 4294967295L;
   }

   public LongArray modInverse(int var1, int[] var2) {
      int var3 = this.degree();
      if (var3 == 0) {
         throw new IllegalStateException();
      } else if (var3 == 1) {
         return this;
      } else {
         LongArray var4 = (LongArray)this.clone();
         int var5 = var1 + 63 >>> 6;
         LongArray var6 = new LongArray(var5);
         reduceBit(var6.m_ints, 0, var1, var1, var2);
         LongArray var7 = new LongArray(var5);
         var7.m_ints[0] = 1L;
         LongArray var8 = new LongArray(var5);
         int[] var9 = new int[]{var3, var1 + 1};
         LongArray[] var10 = new LongArray[]{var4, var6};
         int[] var11 = new int[]{1, 0};
         LongArray[] var12 = new LongArray[]{var7, var8};
         int var13 = 1;
         int var14 = var9[var13];
         int var15 = var11[var13];
         int var16 = var14 - var9[1 - var13];

         while(true) {
            if (var16 < 0) {
               var16 = -var16;
               var9[var13] = var14;
               var11[var13] = var15;
               var13 = 1 - var13;
               var14 = var9[var13];
               var15 = var11[var13];
            }

            var10[var13].addShiftedByBitsSafe(var10[1 - var13], var9[1 - var13], var16);
            int var17 = var10[var13].degreeFrom(var14);
            if (var17 == 0) {
               return var12[1 - var13];
            }

            int var18 = var11[1 - var13];
            var12[var13].addShiftedByBitsSafe(var12[1 - var13], var18, var16);
            var18 += var16;
            if (var18 > var15) {
               var15 = var18;
            } else if (var18 == var15) {
               var15 = var12[var13].degreeFrom(var15);
            }

            var16 += var17 - var14;
            var14 = var17;
         }
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof LongArray)) {
         return false;
      } else {
         LongArray var2 = (LongArray)var1;
         int var3 = this.getUsedLength();
         if (var2.getUsedLength() != var3) {
            return false;
         } else {
            for(int var4 = 0; var4 < var3; ++var4) {
               if (this.m_ints[var4] != var2.m_ints[var4]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int var1 = this.getUsedLength();
      int var2 = 1;

      for(int var3 = 0; var3 < var1; ++var3) {
         long var4 = this.m_ints[var3];
         var2 *= 31;
         var2 ^= (int)var4;
         var2 *= 31;
         var2 ^= (int)(var4 >>> 32);
      }

      return var2;
   }

   public Object clone() {
      return new LongArray(Arrays.clone(this.m_ints));
   }

   public String toString() {
      int var1 = this.getUsedLength();
      if (var1 == 0) {
         return "0";
      } else {
         --var1;
         StringBuffer var2 = new StringBuffer(Long.toBinaryString(this.m_ints[var1]));

         while(true) {
            --var1;
            if (var1 < 0) {
               return var2.toString();
            }

            String var3 = Long.toBinaryString(this.m_ints[var1]);
            int var4 = var3.length();
            if (var4 < 64) {
               var2.append("0000000000000000000000000000000000000000000000000000000000000000".substring(var4));
            }

            var2.append(var3);
         }
      }
   }
}
