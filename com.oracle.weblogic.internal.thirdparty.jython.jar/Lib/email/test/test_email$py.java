package email.test;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("email/test/test_email.py")
public class test_email$py extends PyFunctionTable implements PyRunnable {
   static test_email$py self;
   static final PyCode f$0;
   static final PyCode openfile$1;
   static final PyCode TestEmailBase$2;
   static final PyCode ndiffAssertEqual$3;
   static final PyCode _msgobj$4;
   static final PyCode TestMessageAPI$5;
   static final PyCode test_get_all$6;
   static final PyCode test_getset_charset$7;
   static final PyCode test_set_charset_from_string$8;
   static final PyCode test_set_payload_with_charset$9;
   static final PyCode test_get_charsets$10;
   static final PyCode test_get_filename$11;
   static final PyCode test_get_filename_with_name_parameter$12;
   static final PyCode test_get_boundary$13;
   static final PyCode test_set_boundary$14;
   static final PyCode test_make_boundary$15;
   static final PyCode test_message_rfc822_only$16;
   static final PyCode test_get_decoded_payload$17;
   static final PyCode test_get_decoded_uu_payload$18;
   static final PyCode test_decode_bogus_uu_payload_quietly$19;
   static final PyCode test_decoded_generator$20;
   static final PyCode test__contains__$21;
   static final PyCode test_as_string$22;
   static final PyCode test_bad_param$23;
   static final PyCode test_missing_filename$24;
   static final PyCode test_bogus_filename$25;
   static final PyCode test_missing_boundary$26;
   static final PyCode test_get_params$27;
   static final PyCode test_get_param_liberal$28;
   static final PyCode test_get_param$29;
   static final PyCode test_get_param_funky_continuation_lines$30;
   static final PyCode test_get_param_with_semis_in_quotes$31;
   static final PyCode test_get_param_with_quotes$32;
   static final PyCode test_has_key$33;
   static final PyCode test_set_param$34;
   static final PyCode test_del_param$35;
   static final PyCode test_del_param_on_other_header$36;
   static final PyCode test_set_type$37;
   static final PyCode test_set_type_on_other_header$38;
   static final PyCode test_get_content_type_missing$39;
   static final PyCode test_get_content_type_missing_with_default_type$40;
   static final PyCode test_get_content_type_from_message_implicit$41;
   static final PyCode test_get_content_type_from_message_explicit$42;
   static final PyCode test_get_content_type_from_message_text_plain_implicit$43;
   static final PyCode test_get_content_type_from_message_text_plain_explicit$44;
   static final PyCode test_get_content_maintype_missing$45;
   static final PyCode test_get_content_maintype_missing_with_default_type$46;
   static final PyCode test_get_content_maintype_from_message_implicit$47;
   static final PyCode test_get_content_maintype_from_message_explicit$48;
   static final PyCode test_get_content_maintype_from_message_text_plain_implicit$49;
   static final PyCode test_get_content_maintype_from_message_text_plain_explicit$50;
   static final PyCode test_get_content_subtype_missing$51;
   static final PyCode test_get_content_subtype_missing_with_default_type$52;
   static final PyCode test_get_content_subtype_from_message_implicit$53;
   static final PyCode test_get_content_subtype_from_message_explicit$54;
   static final PyCode test_get_content_subtype_from_message_text_plain_implicit$55;
   static final PyCode test_get_content_subtype_from_message_text_plain_explicit$56;
   static final PyCode test_get_content_maintype_error$57;
   static final PyCode test_get_content_subtype_error$58;
   static final PyCode test_replace_header$59;
   static final PyCode test_broken_base64_payload$60;
   static final PyCode test_get_content_charset$61;
   static final PyCode test_embeded_header_via_Header_rejected$62;
   static final PyCode test_embeded_header_via_string_rejected$63;
   static final PyCode TestEncoders$64;
   static final PyCode test_encode_empty_payload$65;
   static final PyCode test_default_cte$66;
   static final PyCode test_encode7or8bit$67;
   static final PyCode TestLongHeaders$68;
   static final PyCode test_split_long_continuation$69;
   static final PyCode test_another_long_almost_unsplittable_header$70;
   static final PyCode test_long_nonstring$71;
   static final PyCode test_long_header_encode$72;
   static final PyCode test_long_header_encode_with_tab_continuation$73;
   static final PyCode test_header_splitter$74;
   static final PyCode test_no_semis_header_splitter$75;
   static final PyCode test_no_split_long_header$76;
   static final PyCode test_splitting_multiple_long_lines$77;
   static final PyCode test_splitting_first_line_only_is_long$78;
   static final PyCode test_long_8bit_header$79;
   static final PyCode test_long_8bit_header_no_charset$80;
   static final PyCode test_long_to_header$81;
   static final PyCode test_long_line_after_append$82;
   static final PyCode test_shorter_line_with_append$83;
   static final PyCode test_long_field_name$84;
   static final PyCode test_long_received_header$85;
   static final PyCode test_string_headerinst_eq$86;
   static final PyCode test_long_unbreakable_lines_with_continuation$87;
   static final PyCode test_another_long_multiline_header$88;
   static final PyCode test_long_lines_with_different_header$89;
   static final PyCode TestFromMangling$90;
   static final PyCode setUp$91;
   static final PyCode test_mangled_from$92;
   static final PyCode test_dont_mangle_from$93;
   static final PyCode test_mangle_from_in_preamble_and_epilog$94;
   static final PyCode TestMIMEAudio$95;
   static final PyCode setUp$96;
   static final PyCode test_guess_minor_type$97;
   static final PyCode test_encoding$98;
   static final PyCode test_checkSetMinor$99;
   static final PyCode test_add_header$100;
   static final PyCode TestMIMEImage$101;
   static final PyCode setUp$102;
   static final PyCode test_guess_minor_type$103;
   static final PyCode test_encoding$104;
   static final PyCode test_checkSetMinor$105;
   static final PyCode test_add_header$106;
   static final PyCode TestMIMEText$107;
   static final PyCode setUp$108;
   static final PyCode test_types$109;
   static final PyCode test_payload$110;
   static final PyCode test_charset$111;
   static final PyCode test_7bit_unicode_input$112;
   static final PyCode test_7bit_unicode_input_no_charset$113;
   static final PyCode test_8bit_unicode_input$114;
   static final PyCode test_8bit_unicode_input_no_charset$115;
   static final PyCode TestMultipart$116;
   static final PyCode setUp$117;
   static final PyCode test_hierarchy$118;
   static final PyCode test_empty_multipart_idempotent$119;
   static final PyCode test_no_parts_in_a_multipart_with_none_epilogue$120;
   static final PyCode test_no_parts_in_a_multipart_with_empty_epilogue$121;
   static final PyCode test_one_part_in_a_multipart$122;
   static final PyCode test_seq_parts_in_a_multipart_with_empty_preamble$123;
   static final PyCode test_seq_parts_in_a_multipart_with_none_preamble$124;
   static final PyCode test_seq_parts_in_a_multipart_with_none_epilogue$125;
   static final PyCode test_seq_parts_in_a_multipart_with_empty_epilogue$126;
   static final PyCode test_seq_parts_in_a_multipart_with_nl_epilogue$127;
   static final PyCode test_message_external_body$128;
   static final PyCode test_double_boundary$129;
   static final PyCode test_nested_inner_contains_outer_boundary$130;
   static final PyCode test_nested_with_same_boundary$131;
   static final PyCode test_boundary_in_non_multipart$132;
   static final PyCode test_boundary_with_leading_space$133;
   static final PyCode test_boundary_without_trailing_newline$134;
   static final PyCode TestNonConformant$135;
   static final PyCode test_parse_missing_minor_type$136;
   static final PyCode test_same_boundary_inner_outer$137;
   static final PyCode test_multipart_no_boundary$138;
   static final PyCode test_invalid_content_type$139;
   static final PyCode test_no_start_boundary$140;
   static final PyCode test_no_separating_blank_line$141;
   static final PyCode test_lying_multipart$142;
   static final PyCode test_missing_start_boundary$143;
   static final PyCode test_first_line_is_continuation_header$144;
   static final PyCode TestRFC2047$145;
   static final PyCode test_rfc2047_multiline$146;
   static final PyCode test_whitespace_eater_unicode$147;
   static final PyCode test_whitespace_eater_unicode_2$148;
   static final PyCode test_rfc2047_without_whitespace$149;
   static final PyCode test_rfc2047_with_whitespace$150;
   static final PyCode test_rfc2047_B_bad_padding$151;
   static final PyCode test_rfc2047_Q_invalid_digits$152;
   static final PyCode TestMIMEMessage$153;
   static final PyCode setUp$154;
   static final PyCode test_type_error$155;
   static final PyCode test_valid_argument$156;
   static final PyCode test_bad_multipart$157;
   static final PyCode test_generate$158;
   static final PyCode test_parse_message_rfc822$159;
   static final PyCode test_dsn$160;
   static final PyCode test_epilogue$161;
   static final PyCode test_no_nl_preamble$162;
   static final PyCode test_default_type$163;
   static final PyCode test_default_type_with_explicit_container_type$164;
   static final PyCode test_default_type_non_parsed$165;
   static final PyCode test_mime_attachments_in_constructor$166;
   static final PyCode test_default_multipart_constructor$167;
   static final PyCode TestIdempotent$168;
   static final PyCode _msgobj$169;
   static final PyCode _idempotent$170;
   static final PyCode test_parse_text_message$171;
   static final PyCode test_parse_untyped_message$172;
   static final PyCode test_simple_multipart$173;
   static final PyCode test_MIME_digest$174;
   static final PyCode test_long_header$175;
   static final PyCode test_MIME_digest_with_part_headers$176;
   static final PyCode test_mixed_with_image$177;
   static final PyCode test_multipart_report$178;
   static final PyCode test_dsn$179;
   static final PyCode test_preamble_epilogue$180;
   static final PyCode test_multipart_one_part$181;
   static final PyCode test_multipart_no_parts$182;
   static final PyCode test_no_start_boundary$183;
   static final PyCode test_rfc2231_charset$184;
   static final PyCode test_more_rfc2231_parameters$185;
   static final PyCode test_text_plain_in_a_multipart_digest$186;
   static final PyCode test_nested_multipart_mixeds$187;
   static final PyCode test_message_external_body_idempotent$188;
   static final PyCode test_content_type$189;
   static final PyCode test_parser$190;
   static final PyCode TestMiscellaneous$191;
   static final PyCode test_message_from_string$192;
   static final PyCode test_message_from_file$193;
   static final PyCode test_message_from_string_with_class$194;
   static final PyCode MyMessage$195;
   static final PyCode test_message_from_file_with_class$196;
   static final PyCode MyMessage$197;
   static final PyCode test__all__$198;
   static final PyCode test_formatdate$199;
   static final PyCode test_formatdate_localtime$200;
   static final PyCode test_formatdate_usegmt$201;
   static final PyCode test_parsedate_none$202;
   static final PyCode test_parsedate_compact$203;
   static final PyCode test_parsedate_no_dayofweek$204;
   static final PyCode test_parsedate_compact_no_dayofweek$205;
   static final PyCode test_parsedate_acceptable_to_time_functions$206;
   static final PyCode test_mktime_tz$207;
   static final PyCode test_parsedate_y2k$208;
   static final PyCode test_parseaddr_empty$209;
   static final PyCode test_noquote_dump$210;
   static final PyCode test_escape_dump$211;
   static final PyCode test_escape_backslashes$212;
   static final PyCode test_name_with_dot$213;
   static final PyCode test_parseaddr_preserves_quoted_pairs_in_addresses$214;
   static final PyCode test_multiline_from_comment$215;
   static final PyCode test_quote_dump$216;
   static final PyCode test_fix_eols$217;
   static final PyCode test_charset_richcomparisons$218;
   static final PyCode test_getaddresses$219;
   static final PyCode test_getaddresses_nasty$220;
   static final PyCode test_getaddresses_embedded_comment$221;
   static final PyCode test_utils_quote_unquote$222;
   static final PyCode test_get_body_encoding_with_bogus_charset$223;
   static final PyCode test_get_body_encoding_with_uppercase_charset$224;
   static final PyCode test_charsets_case_insensitive$225;
   static final PyCode test_partial_falls_inside_message_delivery_status$226;
   static final PyCode TestIterators$227;
   static final PyCode test_body_line_iterator$228;
   static final PyCode test_typed_subpart_iterator$229;
   static final PyCode test_typed_subpart_iterator_default_type$230;
   static final PyCode test_pushCR_LF$231;
   static final PyCode TestParsers$232;
   static final PyCode test_header_parser$233;
   static final PyCode test_whitespace_continuation$234;
   static final PyCode test_whitespace_continuation_last_header$235;
   static final PyCode test_crlf_separation$236;
   static final PyCode test_multipart_digest_with_extra_mime_headers$237;
   static final PyCode test_three_lines$238;
   static final PyCode test_strip_line_feed_and_carriage_return_in_headers$239;
   static final PyCode test_rfc2822_header_syntax$240;
   static final PyCode test_rfc2822_space_not_allowed_in_header$241;
   static final PyCode test_rfc2822_one_character_header$242;
   static final PyCode test_CRLFLF_at_end_of_part$243;
   static final PyCode TestBase64$244;
   static final PyCode test_len$245;
   static final PyCode test_decode$246;
   static final PyCode test_encode$247;
   static final PyCode test_header_encode$248;
   static final PyCode TestQuopri$249;
   static final PyCode setUp$250;
   static final PyCode test_header_quopri_check$251;
   static final PyCode test_body_quopri_check$252;
   static final PyCode test_header_quopri_len$253;
   static final PyCode test_body_quopri_len$254;
   static final PyCode test_quote_unquote_idempotent$255;
   static final PyCode test_header_encode$256;
   static final PyCode test_decode$257;
   static final PyCode test_encode$258;
   static final PyCode TestCharset$259;
   static final PyCode tearDown$260;
   static final PyCode test_idempotent$261;
   static final PyCode test_body_encode$262;
   static final PyCode test_unicode_charset_name$263;
   static final PyCode test_codecs_aliases_accepted$264;
   static final PyCode TestHeader$265;
   static final PyCode test_simple$266;
   static final PyCode test_simple_surprise$267;
   static final PyCode test_header_needs_no_decoding$268;
   static final PyCode test_long$269;
   static final PyCode test_multilingual$270;
   static final PyCode test_header_ctor_default_args$271;
   static final PyCode test_explicit_maxlinelen$272;
   static final PyCode test_us_ascii_header$273;
   static final PyCode test_string_charset$274;
   static final PyCode test_utf8_shortest$275;
   static final PyCode test_bad_8bit_header$276;
   static final PyCode test_encoded_adjacent_nonencoded$277;
   static final PyCode test_whitespace_eater$278;
   static final PyCode test_broken_base64_header$279;
   static final PyCode test_ascii_add_header$280;
   static final PyCode test_nonascii_add_header_via_triple$281;
   static final PyCode test_encode_unaliased_charset$282;
   static final PyCode TestRFC2231$283;
   static final PyCode test_get_param$284;
   static final PyCode test_set_param$285;
   static final PyCode test_del_param$286;
   static final PyCode test_rfc2231_get_content_charset$287;
   static final PyCode test_rfc2231_no_language_or_charset$288;
   static final PyCode test_rfc2231_no_language_or_charset_in_filename$289;
   static final PyCode test_rfc2231_no_language_or_charset_in_filename_encoded$290;
   static final PyCode test_rfc2231_partly_encoded$291;
   static final PyCode test_rfc2231_partly_nonencoded$292;
   static final PyCode test_rfc2231_no_language_or_charset_in_boundary$293;
   static final PyCode test_rfc2231_no_language_or_charset_in_charset$294;
   static final PyCode test_rfc2231_bad_encoding_in_filename$295;
   static final PyCode test_rfc2231_bad_encoding_in_charset$296;
   static final PyCode test_rfc2231_bad_character_in_charset$297;
   static final PyCode test_rfc2231_bad_character_in_filename$298;
   static final PyCode test_rfc2231_unknown_encoding$299;
   static final PyCode test_rfc2231_single_tick_in_filename_extended$300;
   static final PyCode test_rfc2231_single_tick_in_filename$301;
   static final PyCode test_rfc2231_tick_attack_extended$302;
   static final PyCode test_rfc2231_tick_attack$303;
   static final PyCode test_rfc2231_no_extended_values$304;
   static final PyCode test_rfc2231_encoded_then_unencoded_segments$305;
   static final PyCode test_rfc2231_unencoded_then_encoded_segments$306;
   static final PyCode TestSigned$307;
   static final PyCode _msg_and_obj$308;
   static final PyCode _signed_parts_eq$309;
   static final PyCode test_long_headers_as_string$310;
   static final PyCode test_long_headers_flatten$311;
   static final PyCode _testclasses$312;
   static final PyCode suite$313;
   static final PyCode test_main$314;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(13);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(15);
      var3 = imp.importOne("email", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(17);
      var5 = new String[]{"Charset"};
      var6 = imp.importFrom("email.Charset", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Charset", var4);
      var4 = null;
      var1.setline(18);
      var5 = new String[]{"Header", "decode_header", "make_header"};
      var6 = imp.importFrom("email.Header", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Header", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("decode_header", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("make_header", var4);
      var4 = null;
      var1.setline(19);
      var5 = new String[]{"Parser", "HeaderParser"};
      var6 = imp.importFrom("email.Parser", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Parser", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("HeaderParser", var4);
      var4 = null;
      var1.setline(20);
      var5 = new String[]{"Generator", "DecodedGenerator"};
      var6 = imp.importFrom("email.Generator", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Generator", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DecodedGenerator", var4);
      var4 = null;
      var1.setline(21);
      var5 = new String[]{"Message"};
      var6 = imp.importFrom("email.Message", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Message", var4);
      var4 = null;
      var1.setline(22);
      var5 = new String[]{"MIMEAudio"};
      var6 = imp.importFrom("email.MIMEAudio", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEAudio", var4);
      var4 = null;
      var1.setline(23);
      var5 = new String[]{"MIMEText"};
      var6 = imp.importFrom("email.MIMEText", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEText", var4);
      var4 = null;
      var1.setline(24);
      var5 = new String[]{"MIMEImage"};
      var6 = imp.importFrom("email.MIMEImage", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEImage", var4);
      var4 = null;
      var1.setline(25);
      var5 = new String[]{"MIMEBase"};
      var6 = imp.importFrom("email.MIMEBase", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEBase", var4);
      var4 = null;
      var1.setline(26);
      var5 = new String[]{"MIMEMessage"};
      var6 = imp.importFrom("email.MIMEMessage", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEMessage", var4);
      var4 = null;
      var1.setline(27);
      var5 = new String[]{"MIMEMultipart"};
      var6 = imp.importFrom("email.MIMEMultipart", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEMultipart", var4);
      var4 = null;
      var1.setline(28);
      var5 = new String[]{"Utils"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Utils", var4);
      var4 = null;
      var1.setline(29);
      var5 = new String[]{"Errors"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Errors", var4);
      var4 = null;
      var1.setline(30);
      var5 = new String[]{"Encoders"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Encoders", var4);
      var4 = null;
      var1.setline(31);
      var5 = new String[]{"Iterators"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Iterators", var4);
      var4 = null;
      var1.setline(32);
      var5 = new String[]{"base64MIME"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("base64MIME", var4);
      var4 = null;
      var1.setline(33);
      var5 = new String[]{"quopriMIME"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("quopriMIME", var4);
      var4 = null;
      var1.setline(35);
      var5 = new String[]{"findfile", "run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("findfile", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(36);
      var5 = new String[]{"__file__"};
      var6 = imp.importFrom("email.test", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("landmark", var4);
      var4 = null;
      var1.setline(37);
      var5 = new String[]{"is_jython"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("is_jython", var4);
      var4 = null;
      var1.setline(39);
      PyString var7 = PyString.fromInterned("\n");
      var1.setlocal("NL", var7);
      var3 = null;
      var1.setline(40);
      var7 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var7);
      var3 = null;
      var1.setline(41);
      var7 = PyString.fromInterned(" ");
      var1.setlocal("SPACE", var7);
      var3 = null;
      var1.setline(45);
      var6 = new PyObject[]{PyString.fromInterned("r")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, openfile$1, (PyObject)null);
      var1.setlocal("openfile", var8);
      var3 = null;
      var1.setline(52);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestEmailBase", var6, TestEmailBase$2);
      var1.setlocal("TestEmailBase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(74);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMessageAPI", var6, TestMessageAPI$5);
      var1.setlocal("TestMessageAPI", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(571);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestEncoders", var6, TestEncoders$64);
      var1.setlocal("TestEncoders", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(600);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestLongHeaders", var6, TestLongHeaders$68);
      var1.setlocal("TestLongHeaders", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(921);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestFromMangling", var6, TestFromMangling$90);
      var1.setlocal("TestFromMangling", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(977);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEAudio", var6, TestMIMEAudio$95);
      var1.setlocal("TestMIMEAudio", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1026);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEImage", var6, TestMIMEImage$101);
      var1.setlocal("TestMIMEImage", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1069);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEText", var6, TestMIMEText$107);
      var1.setlocal("TestMIMEText", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1121);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMultipart", var6, TestMultipart$116);
      var1.setlocal("TestMultipart", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1500);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestNonConformant", var6, TestNonConformant$135);
      var1.setlocal("TestNonConformant", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1615);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestRFC2047", var6, TestRFC2047$145);
      var1.setlocal("TestRFC2047", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1677);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMIMEMessage", var6, TestMIMEMessage$153);
      var1.setlocal("TestMIMEMessage", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1994);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestIdempotent", var6, TestIdempotent$168);
      var1.setlocal("TestIdempotent", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2147);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMiscellaneous", var6, TestMiscellaneous$191);
      var1.setlocal("TestMiscellaneous", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2508);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestIterators", var6, TestIterators$227);
      var1.setlocal("TestIterators", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2600);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestParsers", var6, TestParsers$232);
      var1.setlocal("TestParsers", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2763);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestBase64", var6, TestBase64$244);
      var1.setlocal("TestBase64", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2835);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestQuopri", var6, TestQuopri$249);
      var1.setlocal("TestQuopri", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2946);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestCharset", var6, TestCharset$259);
      var1.setlocal("TestCharset", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3010);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestHeader", var6, TestHeader$265);
      var1.setlocal("TestHeader", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3195);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestRFC2231", var6, TestRFC2231$283);
      var1.setlocal("TestRFC2231", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3509);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestSigned", var6, TestSigned$307);
      var1.setlocal("TestSigned", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3542);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _testclasses$312, (PyObject)null);
      var1.setlocal("_testclasses", var8);
      var3 = null;
      var1.setline(3547);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, suite$313, (PyObject)null);
      var1.setlocal("suite", var8);
      var3 = null;
      var1.setline(3554);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, test_main$314, (PyObject)null);
      var1.setlocal("test_main", var8);
      var3 = null;
      var1.setline(3560);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3561);
         var10000 = var1.getname("unittest").__getattr__("main");
         var6 = new PyObject[]{PyString.fromInterned("suite")};
         String[] var9 = new String[]{"defaultTest"};
         var10000.__call__(var2, var6, var9);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject openfile$1(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("landmark")), (PyObject)PyString.fromInterned("data"), (PyObject)var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestEmailBase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(53);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, ndiffAssertEqual$3, PyString.fromInterned("Like assertEqual except use ndiff for readable output."));
      var1.setlocal("ndiffAssertEqual", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _msgobj$4, (PyObject)null);
      var1.setlocal("_msgobj", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ndiffAssertEqual$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Like assertEqual except use ndiff for readable output.");
      var1.setline(55);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(57);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(58);
         var3 = var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getlocal(3).__getattr__("splitlines").__call__(var2), var1.getlocal(4).__getattr__("splitlines").__call__(var2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(59);
         var3 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(60);
         var3 = var1.getlocal(6);
         Py.printComma(var3, var1.getglobal("NL"));
         Py.println(var3, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(5)));
         var1.setline(61);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException"), var1.getlocal(6).__getattr__("getvalue").__call__(var2));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _msgobj$4(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getglobal("findfile").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(66);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(68);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(68);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(69);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestMessageAPI$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_all$6, (PyObject)null);
      var1.setlocal("test_get_all", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getset_charset$7, (PyObject)null);
      var1.setlocal("test_getset_charset", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_charset_from_string$8, (PyObject)null);
      var1.setlocal("test_set_charset_from_string", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_payload_with_charset$9, (PyObject)null);
      var1.setlocal("test_set_payload_with_charset", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_charsets$10, (PyObject)null);
      var1.setlocal("test_get_charsets", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_filename$11, (PyObject)null);
      var1.setlocal("test_get_filename", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_filename_with_name_parameter$12, (PyObject)null);
      var1.setlocal("test_get_filename_with_name_parameter", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_boundary$13, (PyObject)null);
      var1.setlocal("test_get_boundary", var4);
      var3 = null;
      var1.setline(161);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_boundary$14, (PyObject)null);
      var1.setlocal("test_set_boundary", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_boundary$15, (PyObject)null);
      var1.setlocal("test_make_boundary", var4);
      var3 = null;
      var1.setline(194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_rfc822_only$16, (PyObject)null);
      var1.setlocal("test_message_rfc822_only", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_decoded_payload$17, (PyObject)null);
      var1.setlocal("test_get_decoded_payload", var4);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_decoded_uu_payload$18, (PyObject)null);
      var1.setlocal("test_get_decoded_uu_payload", var4);
      var3 = null;
      var1.setline(239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decode_bogus_uu_payload_quietly$19, (PyObject)null);
      var1.setlocal("test_decode_bogus_uu_payload_quietly", var4);
      var3 = null;
      var1.setline(252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decoded_generator$20, (PyObject)null);
      var1.setlocal("test_decoded_generator", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test__contains__$21, (PyObject)null);
      var1.setlocal("test__contains__", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_as_string$22, (PyObject)null);
      var1.setlocal("test_as_string", var4);
      var3 = null;
      var1.setline(300);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_param$23, (PyObject)null);
      var1.setlocal("test_bad_param", var4);
      var3 = null;
      var1.setline(304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_filename$24, (PyObject)null);
      var1.setlocal("test_missing_filename", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bogus_filename$25, (PyObject)null);
      var1.setlocal("test_bogus_filename", var4);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_boundary$26, (PyObject)null);
      var1.setlocal("test_missing_boundary", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_params$27, (PyObject)null);
      var1.setlocal("test_get_params", var4);
      var3 = null;
      var1.setline(333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_liberal$28, (PyObject)null);
      var1.setlocal("test_get_param_liberal", var4);
      var3 = null;
      var1.setline(338);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param$29, (PyObject)null);
      var1.setlocal("test_get_param", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_funky_continuation_lines$30, (PyObject)null);
      var1.setlocal("test_get_param_funky_continuation_lines", var4);
      var3 = null;
      var1.setline(359);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_with_semis_in_quotes$31, (PyObject)null);
      var1.setlocal("test_get_param_with_semis_in_quotes", var4);
      var3 = null;
      var1.setline(366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_with_quotes$32, (PyObject)null);
      var1.setlocal("test_get_param_with_quotes", var4);
      var3 = null;
      var1.setline(374);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_has_key$33, (PyObject)null);
      var1.setlocal("test_has_key", var4);
      var3 = null;
      var1.setline(381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_param$34, (PyObject)null);
      var1.setlocal("test_set_param", var4);
      var3 = null;
      var1.setline(398);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param$35, (PyObject)null);
      var1.setlocal("test_del_param", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param_on_other_header$36, (PyObject)null);
      var1.setlocal("test_del_param_on_other_header", var4);
      var3 = null;
      var1.setline(421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_type$37, (PyObject)null);
      var1.setlocal("test_set_type", var4);
      var3 = null;
      var1.setline(432);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_type_on_other_header$38, (PyObject)null);
      var1.setlocal("test_set_type_on_other_header", var4);
      var3 = null;
      var1.setline(438);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_missing$39, (PyObject)null);
      var1.setlocal("test_get_content_type_missing", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_missing_with_default_type$40, (PyObject)null);
      var1.setlocal("test_get_content_type_missing_with_default_type", var4);
      var3 = null;
      var1.setline(447);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_implicit$41, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_implicit", var4);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_explicit$42, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_explicit", var4);
      var3 = null;
      var1.setline(457);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_text_plain_implicit$43, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_text_plain_explicit$44, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_missing$45, (PyObject)null);
      var1.setlocal("test_get_content_maintype_missing", var4);
      var3 = null;
      var1.setline(469);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_missing_with_default_type$46, (PyObject)null);
      var1.setlocal("test_get_content_maintype_missing_with_default_type", var4);
      var3 = null;
      var1.setline(474);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_implicit$47, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_implicit", var4);
      var3 = null;
      var1.setline(478);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_explicit$48, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_explicit", var4);
      var3 = null;
      var1.setline(482);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_text_plain_implicit$49, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(486);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_text_plain_explicit$50, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(490);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_missing$51, (PyObject)null);
      var1.setlocal("test_get_content_subtype_missing", var4);
      var3 = null;
      var1.setline(494);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_missing_with_default_type$52, (PyObject)null);
      var1.setlocal("test_get_content_subtype_missing_with_default_type", var4);
      var3 = null;
      var1.setline(499);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_implicit$53, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_implicit", var4);
      var3 = null;
      var1.setline(503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_explicit$54, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_explicit", var4);
      var3 = null;
      var1.setline(507);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_text_plain_implicit$55, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_text_plain_explicit$56, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(515);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_error$57, (PyObject)null);
      var1.setlocal("test_get_content_maintype_error", var4);
      var3 = null;
      var1.setline(520);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_error$58, (PyObject)null);
      var1.setlocal("test_get_content_subtype_error", var4);
      var3 = null;
      var1.setline(525);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_replace_header$59, (PyObject)null);
      var1.setlocal("test_replace_header", var4);
      var3 = null;
      var1.setline(542);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_broken_base64_payload$60, (PyObject)null);
      var1.setlocal("test_broken_base64_payload", var4);
      var3 = null;
      var1.setline(550);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_charset$61, (PyObject)null);
      var1.setlocal("test_get_content_charset", var4);
      var3 = null;
      var1.setline(559);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_embeded_header_via_Header_rejected$62, (PyObject)null);
      var1.setlocal("test_embeded_header_via_Header_rejected", var4);
      var3 = null;
      var1.setline(564);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_embeded_header_via_string_rejected$63, (PyObject)null);
      var1.setlocal("test_embeded_header_via_string_rejected", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_all$6(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_20.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(78);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_all").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cc")), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ccc@zzz.org"), PyString.fromInterned("ddd@zzz.org"), PyString.fromInterned("eee@zzz.org")})));
      var1.setline(79);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_all").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx"), (PyObject)PyString.fromInterned("n/a")), (PyObject)PyString.fromInterned("n/a"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getset_charset$7(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(84);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_charset").__call__(var2), var1.getglobal("None"));
      var1.setline(85);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(86);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getlocal(3));
      var1.setline(87);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("mime-version")), (PyObject)PyString.fromInterned("1.0"));
      var1.setline(88);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(89);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"iso-8859-1\""));
      var1.setline(90);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(91);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quoted-printable"));
      var1.setline(92);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(94);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getglobal("None"));
      var1.setline(95);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_charset").__call__(var2), var1.getglobal("None"));
      var1.setline(96);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(98);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(99);
      PyString var4 = PyString.fromInterned("2.0");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("MIME-Version"), var4);
      var3 = null;
      var1.setline(100);
      var4 = PyString.fromInterned("text/x-weird");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(101);
      var4 = PyString.fromInterned("quinted-puntable");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
      var3 = null;
      var1.setline(102);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getlocal(3));
      var1.setline(103);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("mime-version")), (PyObject)PyString.fromInterned("2.0"));
      var1.setline(104);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/x-weird; charset=\"iso-8859-1\""));
      var1.setline(105);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quinted-puntable"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_charset_from_string$8(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(109);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(110);
      var1.getlocal(2).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(111);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(112);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_payload_with_charset$9(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(117);
      var1.getlocal(1).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This is a string payload"), (PyObject)var1.getlocal(2));
      var1.setline(118);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_charsets$10(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_08.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(125);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("koi8-r")})));
      var1.setline(127);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_09.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dingbat"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(129);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("dingbat"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("dingbat"), PyString.fromInterned("koi8-r")})));
      var1.setline(132);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_12.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(134);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), var1.getglobal("None"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("iso-8859-3"), PyString.fromInterned("us-ascii"), PyString.fromInterned("koi8-r")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_filename$11(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(141);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(141);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(141);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(141);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(142);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("msg.txt"), PyString.fromInterned("msg.txt")})));
            var1.setline(144);
            var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(145);
            var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(146);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("dingusfish.gif"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(141);
         var1.getlocal(4).__call__(var2, var1.getlocal(5).__getattr__("get_filename").__call__(var2));
      }
   }

   public PyObject test_get_filename_with_name_parameter$12(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_44.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(152);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(152);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(152);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(152);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(153);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("msg.txt"), PyString.fromInterned("msg.txt")})));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(152);
         var1.getlocal(4).__call__(var2, var1.getlocal(5).__getattr__("get_filename").__call__(var2));
      }
   }

   public PyObject test_get_boundary$13(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_boundary$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(166);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(167);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(168);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2), (PyObject)PyString.fromInterned("content-type"));
      var1.setline(169);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\"; boundary=\"BOUNDARY\""));
      var1.setline(173);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(174);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(175);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(4));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(176);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2), (PyObject)PyString.fromInterned("content-type"));
      var1.setline(177);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("multipart/mixed; boundary=\"BOUNDARY\""));
      var1.setline(179);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(180);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("Errors").__getattr__("HeaderParseError"), (PyObject)var1.getlocal(2).__getattr__("set_boundary"), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_boundary$15(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getglobal("MIMEMultipart").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("form-data"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(187);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("multipart/form-data"));
      var1.setline(189);
      var1.getlocal(1).__getattr__("as_string").__call__(var2);
      var1.setline(190);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)).__getslice__((PyObject)null, Py.newInteger(33), (PyObject)null), (PyObject)PyString.fromInterned("multipart/form-data; boundary=\"=="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_rfc822_only$16(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getglobal("findfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_46.txt")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getlocal(1).__getattr__("read").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getglobal("email").__getattr__("Parser").__getattr__("HeaderParser").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getlocal(3).__getattr__("parsestr").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(201);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getglobal("email").__getattr__("Generator").__getattr__("Generator").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)var1.getglobal("True"), (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(203);
      var1.getlocal(6).__getattr__("flatten").__call__(var2, var1.getlocal(4), var1.getglobal("False"));
      var1.setline(204);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("getvalue").__call__(var2), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_decoded_payload$17(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(208);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_10.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(210);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      PyObject[] var5 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(212);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a 7bit encoded message.\n"));
      var1.setline(215);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("¡This is a Quoted Printable encoded message!\n"));
      var1.setline(218);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a Base64 encoded message."));
      var1.setline(222);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a Base64 encoded message.\n"));
      var1.setline(225);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This has no Content-Transfer-Encoding: header.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_decoded_uu_payload$18(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(231);
      var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("begin 666 -\n+:&5L;&\\@=V]R;&0 \n \nend\n"));
      var1.setline(232);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("x-uuencode"), PyString.fromInterned("uuencode"), PyString.fromInterned("uue"), PyString.fromInterned("x-uue")})).__iter__();

      while(true) {
         var1.setline(232);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject var10002;
         if (var4 == null) {
            var1.setline(236);
            var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
            var1.setline(237);
            var10000 = var1.getlocal(1);
            var10002 = var1.getlocal(2).__getattr__("get_payload");
            PyObject[] var8 = new PyObject[]{var1.getglobal("True")};
            String[] var7 = new String[]{"decode"};
            var10002 = var10002.__call__(var2, var8, var7);
            var3 = null;
            var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("foo"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(233);
         PyObject var5 = var1.getlocal(3);
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-transfer-encoding"), var5);
         var5 = null;
         var1.setline(234);
         var10000 = var1.getlocal(1);
         var10002 = var1.getlocal(2).__getattr__("get_payload");
         PyObject[] var9 = new PyObject[]{var1.getglobal("True")};
         String[] var6 = new String[]{"decode"};
         var10002 = var10002.__call__(var2, var9, var6);
         var5 = null;
         var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("hello world"));
      }
   }

   public PyObject test_decode_bogus_uu_payload_quietly$19(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(241);
      var1.getlocal(1).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("begin 664 foo.txt\n%<W1F=0000H \n \nend\n"));
      var1.setline(242);
      PyString var7 = PyString.fromInterned("x-uuencode");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var7);
      var3 = null;
      var1.setline(243);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(245);
         var4 = var1.getglobal("StringIO").__call__(var2);
         var1.getglobal("sys").__setattr__("stderr", var4);
         var1.setlocal(3, var4);
         var1.setline(247);
         PyObject var10000 = var1.getlocal(1).__getattr__("get_payload");
         PyObject[] var8 = new PyObject[]{var1.getglobal("True")};
         String[] var5 = new String[]{"decode"};
         var10000.__call__(var2, var8, var5);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(249);
         var4 = var1.getlocal(2);
         var1.getglobal("sys").__setattr__("stderr", var4);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(249);
      var4 = var1.getlocal(2);
      var1.getglobal("sys").__setattr__("stderr", var4);
      var4 = null;
      var1.setline(250);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_decoded_generator$20(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_17.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(257);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(259);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(259);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(260);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("DecodedGenerator").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(262);
      var1.getlocal(6).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(263);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("getvalue").__call__(var2), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test__contains__$21(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(267);
      PyString var4 = PyString.fromInterned("Me");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(268);
      var4 = PyString.fromInterned("You");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("to"), var4);
      var3 = null;
      var1.setline(270);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("from");
      PyObject var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(271);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("From");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(272);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("FROM");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(273);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("to");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(274);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("To");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(275);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("TO");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_as_string$22(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(279);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(280);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(291);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)PyString.fromInterned(" "));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(293);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(293);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(294);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("as_string").__call__(var2));
      var1.setline(295);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(296);
      var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(297);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From ")));
      var1.setline(298);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_param$23(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: blarg; baz; boo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(302);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("baz")), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_filename$24(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: foo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(306);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_filename").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bogus_filename$25(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Disposition: blarg; filename\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(311);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_boundary$26(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: foo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(315);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_boundary").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_params$27(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(319);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo=one; bar=two; baz=three\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(321);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_params");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("x-header")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("two")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("three")})})));
      var1.setline(323);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=one; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(325);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("two")})})));
      var1.setline(327);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_params").__call__(var2), var1.getglobal("None"));
      var1.setline(328);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=\"one\"; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(330);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("two")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_liberal$28(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(335);
      PyString var4 = PyString.fromInterned("Content-Type: Multipart/mixed; boundary = \"CPIMSSMTPC06p5f3tG\"");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(336);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("boundary")), (PyObject)PyString.fromInterned("CPIMSSMTPC06p5f3tG"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param$29(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo=one; bar=two; baz=three\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(342);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("x-header")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("two"));
      var1.setline(343);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("quuz"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(344);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("quuz")), var1.getglobal("None"));
      var1.setline(345);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=\"one\"; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(347);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(348);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("one"));
      var1.setline(349);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("two"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_funky_continuation_lines$30(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_22.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(357);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("wibble.JPG"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_with_semis_in_quotes$31(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: image/pjpeg; name=\"Jim&amp;&amp;Jill\"\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(362);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("Jim&amp;&amp;Jill"));
      var1.setline(363);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(1).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("name"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("\"Jim&amp;&amp;Jill\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_with_quotes$32(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: foo; bar*0=\"baz\\\"foobar\"; bar*1=\"\\\"baz\""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(369);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bar")), (PyObject)PyString.fromInterned("baz\"foobar\"baz"));
      var1.setline(370);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: foo; bar*0=\"baz\\\"foobar\"; bar*1=\"\\\"baz\""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(372);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bar")), (PyObject)PyString.fromInterned("baz\"foobar\"baz"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_has_key$33(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header: exists"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(376);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header")));
      var1.setline(377);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header")));
      var1.setline(378);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HEADER")));
      var1.setline(379);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("headeri")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_param$34(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(383);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(384);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)PyString.fromInterned("iso-2022-jp"));
      var1.setline(385);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("iso-2022-jp"));
      var1.setline(386);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("importance"), (PyObject)PyString.fromInterned("high value"));
      var1.setline(387);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("importance")), (PyObject)PyString.fromInterned("high value"));
      var1.setline(388);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("importance"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("\"high value\""));
      var1.setline(389);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("text/plain"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("iso-2022-jp")}), new PyTuple(new PyObject[]{PyString.fromInterned("importance"), PyString.fromInterned("high value")})})));
      var1.setline(392);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{var1.getglobal("False")};
      var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("text/plain"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("\"iso-2022-jp\"")}), new PyTuple(new PyObject[]{PyString.fromInterned("importance"), PyString.fromInterned("\"high value\"")})})));
      var1.setline(395);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("iso-9999-xx"), PyString.fromInterned("X-Jimmy")};
      var4 = new String[]{"header"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(396);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("X-Jimmy")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("iso-9999-xx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param$35(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(400);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(401);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("report-type"), PyString.fromInterned("delivery-status")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")})})));
      var1.setline(404);
      var3 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(405);
      var1.getlocal(2).__getattr__("del_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"));
      var1.setline(406);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")})})));
      var1.setline(409);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"), (PyObject)var1.getlocal(3));
      var1.setline(410);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")}), new PyTuple(new PyObject[]{PyString.fromInterned("report-type"), var1.getlocal(3)})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param_on_other_header$36(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(417);
      PyObject var10000 = var1.getlocal(1).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("bud.gif")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(418);
      var1.getlocal(1).__getattr__("del_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filename"), (PyObject)PyString.fromInterned("content-disposition"));
      var1.setline(419);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_type$37(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(423);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(424);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("ValueError"), (PyObject)var1.getlocal(2).__getattr__("set_type"), (PyObject)PyString.fromInterned("text"));
      var1.setline(425);
      var1.getlocal(2).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(426);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(427);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(428);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.setline(429);
      var1.getlocal(2).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("text/html"));
      var1.setline(430);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/html; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_type_on_other_header$38(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(434);
      PyString var4 = PyString.fromInterned("text/plain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("X-Content-Type"), var4);
      var3 = null;
      var1.setline(435);
      var1.getlocal(1).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("application/octet-stream"), (PyObject)PyString.fromInterned("X-Content-Type"));
      var1.setline(436);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("x-content-type")), (PyObject)PyString.fromInterned("application/octet-stream"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_missing$39(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(440);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_missing_with_default_type$40(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(444);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(445);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_implicit$41(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(449);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_explicit$42(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(454);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_text_plain_implicit$43(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(459);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_text_plain_explicit$44(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(463);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_missing$45(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(467);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_missing_with_default_type$46(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(471);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(472);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_implicit$47(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(476);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_explicit$48(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(480);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_text_plain_implicit$49(PyFrame var1, ThreadState var2) {
      var1.setline(483);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(484);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_text_plain_explicit$50(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(488);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_missing$51(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(492);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_missing_with_default_type$52(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(496);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(497);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_implicit$53(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(501);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_explicit$54(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(505);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_text_plain_implicit$55(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(509);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_text_plain_explicit$56(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(513);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_error$57(PyFrame var1, ThreadState var2) {
      var1.setline(516);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(517);
      PyString var4 = PyString.fromInterned("no-slash-in-this-string");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(518);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_error$58(PyFrame var1, ThreadState var2) {
      var1.setline(521);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(522);
      PyString var4 = PyString.fromInterned("no-slash-in-this-string");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(523);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_replace_header$59(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(527);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(528);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("One"));
      var1.setline(529);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second"), (PyObject)PyString.fromInterned("Two"));
      var1.setline(530);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Third"), (PyObject)PyString.fromInterned("Three"));
      var1.setline(531);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third")})));
      var1.setline(532);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One"), PyString.fromInterned("Two"), PyString.fromInterned("Three")})));
      var1.setline(533);
      var1.getlocal(2).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second"), (PyObject)PyString.fromInterned("Twenty"));
      var1.setline(534);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third")})));
      var1.setline(535);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One"), PyString.fromInterned("Twenty"), PyString.fromInterned("Three")})));
      var1.setline(536);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("Eleven"));
      var1.setline(537);
      var1.getlocal(2).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("One Hundred"));
      var1.setline(538);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third"), PyString.fromInterned("First")})));
      var1.setline(539);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One Hundred"), PyString.fromInterned("Twenty"), PyString.fromInterned("Three"), PyString.fromInterned("Eleven")})));
      var1.setline(540);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(2).__getattr__("replace_header"), PyString.fromInterned("Fourth"), PyString.fromInterned("Missing"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_broken_base64_payload$60(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyString var3 = PyString.fromInterned("AwDp0P7//y6LwKEAcPa/6Q=9");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(544);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(545);
      var3 = PyString.fromInterned("audio/x-midi");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-type"), var3);
      var3 = null;
      var1.setline(546);
      var3 = PyString.fromInterned("base64");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-transfer-encoding"), var3);
      var3 = null;
      var1.setline(547);
      var1.getlocal(2).__getattr__("set_payload").__call__(var2, var1.getlocal(1));
      var1.setline(548);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      PyObject[] var6 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_charset$61(PyFrame var1, ThreadState var2) {
      var1.setline(551);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(552);
      var1.getlocal(1).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(553);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"), (PyObject)var1.getlocal(1).__getattr__("get_content_charset").__call__(var2));
      var1.setline(554);
      var1.getlocal(1).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("us-ascii"));
      var1.setline(555);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"), (PyObject)var1.getlocal(1).__getattr__("get_content_charset").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_embeded_header_via_Header_rejected$62(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(561);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dummy\nX-Injected-Header: test"));
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Dummy"), var3);
      var3 = null;
      var1.setline(562);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("Errors").__getattr__("HeaderParseError"), var1.getlocal(1).__getattr__("as_string"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_embeded_header_via_string_rejected$63(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(566);
      PyString var4 = PyString.fromInterned("dummy\nX-Injected-Header: test");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Dummy"), var4);
      var3 = null;
      var1.setline(567);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("Errors").__getattr__("HeaderParseError"), var1.getlocal(1).__getattr__("as_string"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestEncoders$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(572);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_encode_empty_payload$65, (PyObject)null);
      var1.setlocal("test_encode_empty_payload", var4);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_cte$66, (PyObject)null);
      var1.setlocal("test_default_cte", var4);
      var3 = null;
      var1.setline(590);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode7or8bit$67, (PyObject)null);
      var1.setlocal("test_encode7or8bit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_encode_empty_payload$65(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(574);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(575);
      var1.getlocal(2).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(576);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_cte$66(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(581);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(582);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.setline(584);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello ø world"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(585);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("8bit"));
      var1.setline(587);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello ø world"), PyString.fromInterned("iso-8859-1")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(588);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quoted-printable"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode7or8bit$67(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(595);
      PyObject var10000 = var1.getglobal("email").__getattr__("MIMEText").__getattr__("MIMEText");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Ê¸"), PyString.fromInterned("euc-jp")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(596);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestLongHeaders$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(601);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_split_long_continuation$69, (PyObject)null);
      var1.setlocal("test_split_long_continuation", var4);
      var3 = null;
      var1.setline(621);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_another_long_almost_unsplittable_header$70, (PyObject)null);
      var1.setlocal("test_another_long_almost_unsplittable_header", var4);
      var3 = null;
      var1.setline(638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_nonstring$71, (PyObject)null);
      var1.setlocal("test_long_nonstring", var4);
      var3 = null;
      var1.setline(681);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header_encode$72, (PyObject)null);
      var1.setlocal("test_long_header_encode", var4);
      var3 = null;
      var1.setline(690);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header_encode_with_tab_continuation$73, (PyObject)null);
      var1.setlocal("test_long_header_encode_with_tab_continuation", var4);
      var3 = null;
      var1.setline(700);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_splitter$74, (PyObject)null);
      var1.setlocal("test_header_splitter", var4);
      var3 = null;
      var1.setline(720);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_semis_header_splitter$75, (PyObject)null);
      var1.setlocal("test_no_semis_header_splitter", var4);
      var3 = null;
      var1.setline(736);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_split_long_header$76, (PyObject)null);
      var1.setlocal("test_no_split_long_header", var4);
      var3 = null;
      var1.setline(743);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_splitting_multiple_long_lines$77, (PyObject)null);
      var1.setlocal("test_splitting_multiple_long_lines", var4);
      var3 = null;
      var1.setline(765);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_splitting_first_line_only_is_long$78, (PyObject)null);
      var1.setlocal("test_splitting_first_line_only_is_long", var4);
      var3 = null;
      var1.setline(781);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_8bit_header$79, (PyObject)null);
      var1.setlocal("test_long_8bit_header", var4);
      var3 = null;
      var1.setline(794);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_8bit_header_no_charset$80, (PyObject)null);
      var1.setlocal("test_long_8bit_header_no_charset", var4);
      var3 = null;
      var1.setline(803);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_to_header$81, (PyObject)null);
      var1.setlocal("test_long_to_header", var4);
      var3 = null;
      var1.setline(816);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_line_after_append$82, (PyObject)null);
      var1.setlocal("test_long_line_after_append", var4);
      var3 = null;
      var1.setline(825);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_shorter_line_with_append$83, (PyObject)null);
      var1.setlocal("test_shorter_line_with_append", var4);
      var3 = null;
      var1.setline(833);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_field_name$84, (PyObject)null);
      var1.setlocal("test_long_field_name", var4);
      var3 = null;
      var1.setline(845);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_received_header$85, (PyObject)null);
      var1.setlocal("test_long_received_header", var4);
      var3 = null;
      var1.setline(860);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_string_headerinst_eq$86, (PyObject)null);
      var1.setlocal("test_string_headerinst_eq", var4);
      var3 = null;
      var1.setline(874);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_unbreakable_lines_with_continuation$87, (PyObject)null);
      var1.setlocal("test_long_unbreakable_lines_with_continuation", var4);
      var3 = null;
      var1.setline(890);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_another_long_multiline_header$88, (PyObject)null);
      var1.setlocal("test_another_long_multiline_header", var4);
      var3 = null;
      var1.setline(902);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_lines_with_different_header$89, (PyObject)null);
      var1.setlocal("test_long_lines_with_different_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_split_long_continuation$69(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(603);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Subject: bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text\n\ntest\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(610);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(611);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(612);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(613);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Subject: bug demonstration\n 12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n more text\n\ntest\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_another_long_almost_unsplittable_header$70(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(623);
      PyString var5 = PyString.fromInterned("bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(627);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(628);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text"));
      var1.setline(632);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(633);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("bug demonstration\n 12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n more text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_nonstring$71(PyFrame var1, ThreadState var2) {
      var1.setline(639);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(640);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(641);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-2"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(642);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(643);
      PyString var5 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(644);
      var5 = PyString.fromInterned("Finanèni metropole se hroutily pod tlakem jejich dùvtipu.. ");
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(645);
      var3 = PyUnicode.fromInterned("正確に言うと翻訳はされていません。一部はドイツ語ですが、あとはでたらめです。実際には「Wenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.」と言っています。").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(646);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(2), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(647);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      var1.setline(648);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7), var1.getlocal(4));
      var1.setline(649);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(650);
      var3 = var1.getlocal(8);
      var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(651);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(652);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(10));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(653);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(9));
      var1.setline(654);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerd?=\n =?iso-8859-1?q?erband_komfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndi?=\n =?iso-8859-1?q?schen_Wandgem=E4lden_vorbei=2C_gegen_die_rotierenden_Kling?=\n =?iso-8859-1?q?en_bef=F6rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_met?=\n =?iso-8859-2?q?ropole_se_hroutily_pod_tlakem_jejich_d=F9vtipu=2E=2E_?=\n =?utf-8?b?5q2j56K644Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE?=\n =?utf-8?b?44G+44Gb44KT44CC5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB?=\n =?utf-8?b?44GC44Go44Gv44Gn44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CM?=\n =?utf-8?q?Wenn_ist_das_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das?=\n =?utf-8?b?IE9kZXIgZGllIEZsaXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBow==?=\n =?utf-8?b?44Gm44GE44G+44GZ44CC?=\n\n"));
      var1.setline(668);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerd?=\n =?iso-8859-1?q?erband_komfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndi?=\n =?iso-8859-1?q?schen_Wandgem=E4lden_vorbei=2C_gegen_die_rotierenden_Kling?=\n =?iso-8859-1?q?en_bef=F6rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_met?=\n =?iso-8859-2?q?ropole_se_hroutily_pod_tlakem_jejich_d=F9vtipu=2E=2E_?=\n =?utf-8?b?5q2j56K644Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE?=\n =?utf-8?b?44G+44Gb44KT44CC5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB?=\n =?utf-8?b?44GC44Go44Gv44Gn44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CM?=\n =?utf-8?q?Wenn_ist_das_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das?=\n =?utf-8?b?IE9kZXIgZGllIEZsaXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBow==?=\n =?utf-8?b?44Gm44GE44G+44GZ44CC?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header_encode$72(PyFrame var1, ThreadState var2) {
      var1.setline(682);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(683);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""), PyString.fromInterned("X-Foobar-Spoink-Defrobnit")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(686);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\";\n spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header_encode_with_tab_continuation$73(PyFrame var1, ThreadState var2) {
      var1.setline(691);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(692);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""), PyString.fromInterned("X-Foobar-Spoink-Defrobnit"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(696);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\";\n\tspooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_splitter$74(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(702);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(705);
      PyString var4 = PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\"");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("X-Foobar-Spoink-Defrobnit"), var4);
      var3 = null;
      var1.setline(708);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(709);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(710);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(711);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nX-Foobar-Spoink-Defrobnit: wasnipoop; giraffes=\"very-long-necked-animals\";\n spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\"\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_semis_header_splitter$75(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(722);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(723);
      PyString var5 = PyString.fromInterned("test@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var5);
      var3 = null;
      var1.setline(724);
      PyObject var10000 = var1.getglobal("SPACE").__getattr__("join");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(724);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(10)).__iter__();

      while(true) {
         var1.setline(724);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(724);
            var1.dellocal(3);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("References"), var3);
            var3 = null;
            var1.setline(725);
            var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test"));
            var1.setline(726);
            var3 = var1.getglobal("StringIO").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(727);
            var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(728);
            var1.getlocal(6).__getattr__("flatten").__call__(var2, var1.getlocal(2));
            var1.setline(729);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: test@dom.ain\nReferences: <0@dom.ain> <1@dom.ain> <2@dom.ain> <3@dom.ain> <4@dom.ain>\n <5@dom.ain> <6@dom.ain> <7@dom.ain> <8@dom.ain> <9@dom.ain>\n\nTest"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(724);
         var1.getlocal(3).__call__(var2, PyString.fromInterned("<%d@dom.ain>")._mod(var1.getlocal(4)));
      }
   }

   public PyObject test_no_split_long_header$76(PyFrame var1, ThreadState var2) {
      var1.setline(737);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(738);
      var3 = PyString.fromInterned("References: ")._add(PyString.fromInterned("x")._mul(Py.newInteger(80)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(739);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(740);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("References: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_splitting_multiple_long_lines$77(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(745);
      PyString var5 = PyString.fromInterned("from babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(750);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(751);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("from babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_splitting_first_line_only_is_long$78(PyFrame var1, ThreadState var2) {
      var1.setline(766);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(767);
      PyString var5 = PyString.fromInterned("from modemcable093.139-201-24.que.mc.videotron.ca ([24.201.139.93] helo=cthulhu.gerg.ca)\n\tby kronos.mems-exchange.org with esmtp (Exim 4.05)\n\tid 17k4h5-00034i-00\n\tfor test@mems-exchange.org; Wed, 28 Aug 2002 11:25:20 -0400");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(772);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), Py.newInteger(78), PyString.fromInterned("Received"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"maxlinelen", "header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(774);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("from modemcable093.139-201-24.que.mc.videotron.ca ([24.201.139.93]\n\thelo=cthulhu.gerg.ca)\n\tby kronos.mems-exchange.org with esmtp (Exim 4.05)\n\tid 17k4h5-00034i-00\n\tfor test@mems-exchange.org; Wed, 28 Aug 2002 11:25:20 -0400"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_8bit_header$79(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(783);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(784);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Britische Regierung gibt"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(786);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("grünes Licht für Offshore-Windkraftprojekte"));
      var1.setline(787);
      var3 = var1.getlocal(3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(788);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?iso-8859-1?q?Britische_Regierung_gibt?= =?iso-8859-1?q?gr=FCnes?=\n =?iso-8859-1?q?_Licht_f=FCr_Offshore-Windkraftprojekte?=\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_8bit_header_no_charset$80(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(796);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(797);
      PyString var4 = PyString.fromInterned("Britische Regierung gibt grünes Licht für Offshore-Windkraftprojekte <a-very-long-address@example.com>");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Reply-To"), var4);
      var3 = null;
      var1.setline(798);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Reply-To: Britische Regierung gibt grünes Licht für Offshore-Windkraftprojekte <a-very-long-address@example.com>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_to_header$81(PyFrame var1, ThreadState var2) {
      var1.setline(804);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(805);
      PyString var4 = PyString.fromInterned("\"Someone Test #A\" <someone@eecs.umich.edu>,<someone@eecs.umich.edu>,\"Someone Test #B\" <someone@umich.edu>, \"Someone Test #C\" <someone@eecs.umich.edu>, \"Someone Test #D\" <someone@eecs.umich.edu>");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(806);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(807);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("To"), var3);
      var3 = null;
      var1.setline(808);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("To: \"Someone Test #A\" <someone@eecs.umich.edu>, <someone@eecs.umich.edu>,\n \"Someone Test #B\" <someone@umich.edu>,\n \"Someone Test #C\" <someone@eecs.umich.edu>,\n \"Someone Test #D\" <someone@eecs.umich.edu>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_line_after_append$82(PyFrame var1, ThreadState var2) {
      var1.setline(817);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(818);
      PyString var4 = PyString.fromInterned("This is an example of string which has almost the limit of header length.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(819);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(820);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Add another line."));
      var1.setline(821);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("This is an example of string which has almost the limit of header length.\n Add another line."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_shorter_line_with_append$83(PyFrame var1, ThreadState var2) {
      var1.setline(826);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(827);
      PyString var4 = PyString.fromInterned("This is a shorter line.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(828);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(829);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Add another sentence. (Surprise?)"));
      var1.setline(830);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("This is a shorter line. Add another sentence. (Surprise?)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_field_name$84(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(835);
      PyString var5 = PyString.fromInterned("X-Very-Very-Very-Long-Header-Name");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(836);
      var5 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(837);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("iso-8859-1"), var1.getlocal(2)};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(839);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_?=\n =?iso-8859-1?q?ein_werden_mit_einem_Foerderband_komfortabel_den_Korridor_?=\n =?iso-8859-1?q?entlang=2C_an_s=FCdl=FCndischen_Wandgem=E4lden_vorbei=2C_g?=\n =?iso-8859-1?q?egen_die_rotierenden_Klingen_bef=F6rdert=2E_?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_received_header$85(PyFrame var1, ThreadState var2) {
      var1.setline(846);
      PyString var3 = PyString.fromInterned("from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by hrothgar.la.mastaler.com (tmda-ofmipd) with ESMTP; Wed, 05 Mar 2003 18:10:18 -0700");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(847);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(848);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received-1"), var5);
      var3 = null;
      var1.setline(849);
      var5 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received-2"), var5);
      var3 = null;
      var1.setline(850);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received-1: from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by\n\throthgar.la.mastaler.com (tmda-ofmipd) with ESMTP;\n\tWed, 05 Mar 2003 18:10:18 -0700\nReceived-2: from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by\n hrothgar.la.mastaler.com (tmda-ofmipd) with ESMTP;\n Wed, 05 Mar 2003 18:10:18 -0700\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_string_headerinst_eq$86(PyFrame var1, ThreadState var2) {
      var1.setline(861);
      PyString var3 = PyString.fromInterned("<15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de> (David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(862);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(863);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("Received"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received"), var5);
      var3 = null;
      var1.setline(865);
      var5 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received"), var5);
      var3 = null;
      var1.setline(866);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received: <15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de>\n\t(David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")\nReceived: <15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de>\n (David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_unbreakable_lines_with_continuation$87(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(876);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(877);
      PyString var5 = PyString.fromInterned(" iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(880);
      var3 = var1.getlocal(3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Face-1"), var3);
      var3 = null;
      var1.setline(881);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("Face-2")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Face-2"), var3);
      var3 = null;
      var1.setline(882);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Face-1: iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp\nFace-2: iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_another_long_multiline_header$88(PyFrame var1, ThreadState var2) {
      var1.setline(891);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(892);
      PyString var4 = PyString.fromInterned("Received: from siimage.com ([172.25.1.3]) by zima.siliconimage.com with Microsoft SMTPSVC(5.0.2195.4905);\n Wed, 16 Oct 2002 07:41:11 -0700");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(895);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(896);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received: from siimage.com ([172.25.1.3]) by zima.siliconimage.com with\n Microsoft SMTPSVC(5.0.2195.4905); Wed, 16 Oct 2002 07:41:11 -0700\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_lines_with_different_header$89(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(904);
      PyString var5 = PyString.fromInterned("List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n        <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(907);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(908);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("List"), var3);
      var3 = null;
      var1.setline(909);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("List")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("List"), var3);
      var3 = null;
      var1.setline(910);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("List: List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>\nList: List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestFromMangling$90(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(922);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$91, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(930);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mangled_from$92, (PyObject)null);
      var1.setlocal("test_mangled_from", var4);
      var3 = null;
      var1.setline(941);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dont_mangle_from$93, (PyObject)null);
      var1.setlocal("test_dont_mangle_from", var4);
      var3 = null;
      var1.setline(952);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mangle_from_in_preamble_and_epilog$94, (PyObject)null);
      var1.setlocal("test_mangle_from_in_preamble_and_epilog", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$91(PyFrame var1, ThreadState var2) {
      var1.setline(923);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(924);
      PyString var4 = PyString.fromInterned("aaa@bbb.org");
      var1.getlocal(0).__getattr__("msg").__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(925);
      var1.getlocal(0).__getattr__("msg").__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mangled_from$92(PyFrame var1, ThreadState var2) {
      var1.setline(931);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(932);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"mangle_from_"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(933);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.setline(934);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: aaa@bbb.org\n\n>From the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dont_mangle_from$93(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(943);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"mangle_from_"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(944);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.setline(945);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: aaa@bbb.org\n\nFrom the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mangle_from_in_preamble_and_epilog$94(PyFrame var1, ThreadState var2) {
      var1.setline(953);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(954);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"mangle_from_"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(955);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("            From: foo@bar.com\n            Mime-Version: 1.0\n            Content-Type: multipart/mixed; boundary=XXX\n\n            From somewhere unknown\n\n            --XXX\n            Content-Type: text/plain\n\n            foo\n\n            --XXX--\n\n            From somewhere unknowable\n            ")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(971);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(3));
      var1.setline(972);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getglobal("len");
      PyList var10004 = new PyList();
      var3 = var10004.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(972);
      var3 = var1.getlocal(1).__getattr__("getvalue").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(972);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(972);
            var1.dellocal(4);
            var10000.__call__((ThreadState)var2, (PyObject)var10002.__call__((ThreadState)var2, (PyObject)var10004), (PyObject)Py.newInteger(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var6);
         var1.setline(973);
         if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">From ")).__nonzero__()) {
            var1.setline(972);
            var1.getlocal(4).__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }
      }
   }

   public PyObject TestMIMEAudio$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(978);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$96, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(992);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_guess_minor_type$97, (PyObject)null);
      var1.setlocal("test_guess_minor_type", var4);
      var3 = null;
      var1.setline(995);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding$98, (PyObject)null);
      var1.setlocal("test_encoding", var4);
      var3 = null;
      var1.setline(999);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_checkSetMinor$99, (PyObject)null);
      var1.setlocal("test_checkSetMinor", var4);
      var3 = null;
      var1.setline(1003);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_add_header$100, (PyObject)null);
      var1.setlocal("test_add_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$96(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("landmark")), (PyObject)PyString.fromInterned("data"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(985);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("findfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("audiotest.au"), (PyObject)var1.getlocal(1)), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(987);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_audiodata", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(989);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(989);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(990);
      var3 = var1.getglobal("MIMEAudio").__call__(var2, var1.getlocal(0).__getattr__("_audiodata"));
      var1.getlocal(0).__setattr__("_au", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_guess_minor_type$97(PyFrame var1, ThreadState var2) {
      var1.setline(993);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_au").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("audio/basic"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding$98(PyFrame var1, ThreadState var2) {
      var1.setline(996);
      PyObject var3 = var1.getlocal(0).__getattr__("_au").__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(997);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_audiodata"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_checkSetMinor$99(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyObject var3 = var1.getglobal("MIMEAudio").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_audiodata"), (PyObject)PyString.fromInterned("fish"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1001);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("audio/fish"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_add_header$100(PyFrame var1, ThreadState var2) {
      var1.setline(1004);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1005);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1006);
      PyObject var10000 = var1.getlocal(0).__getattr__("_au").__getattr__("add_header");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("audiotest.au")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(1008);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_au").__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment; filename=\"audiotest.au\""));
      var1.setline(1010);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_params");
      var7 = new PyObject[]{PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("audiotest.au")})})));
      var1.setline(1012);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("audiotest.au"));
      var1.setline(1014);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(1015);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1016);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), var1.getlocal(3), PyString.fromInterned("content-disposition")};
      String[] var6 = new String[]{"failobj", "header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1019);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1020);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("attachment"), var1.getlocal(3), PyString.fromInterned("foobar")};
      var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEImage$101(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1027);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$102, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_guess_minor_type$103, (PyObject)null);
      var1.setlocal("test_guess_minor_type", var4);
      var3 = null;
      var1.setline(1038);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding$104, (PyObject)null);
      var1.setlocal("test_encoding", var4);
      var3 = null;
      var1.setline(1042);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_checkSetMinor$105, (PyObject)null);
      var1.setlocal("test_checkSetMinor", var4);
      var3 = null;
      var1.setline(1046);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_add_header$106, (PyObject)null);
      var1.setlocal("test_add_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$102(PyFrame var1, ThreadState var2) {
      var1.setline(1028);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PyBanner048.gif"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1030);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_imgdata", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1032);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1032);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(1033);
      var3 = var1.getglobal("MIMEImage").__call__(var2, var1.getlocal(0).__getattr__("_imgdata"));
      var1.getlocal(0).__setattr__("_im", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_guess_minor_type$103(PyFrame var1, ThreadState var2) {
      var1.setline(1036);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_im").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("image/gif"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding$104(PyFrame var1, ThreadState var2) {
      var1.setline(1039);
      PyObject var3 = var1.getlocal(0).__getattr__("_im").__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1040);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_imgdata"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_checkSetMinor$105(PyFrame var1, ThreadState var2) {
      var1.setline(1043);
      PyObject var3 = var1.getglobal("MIMEImage").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_imgdata"), (PyObject)PyString.fromInterned("fish"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1044);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("image/fish"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_add_header$106(PyFrame var1, ThreadState var2) {
      var1.setline(1047);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1048);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1049);
      PyObject var10000 = var1.getlocal(0).__getattr__("_im").__getattr__("add_header");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("dingusfish.gif")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(1051);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_im").__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment; filename=\"dingusfish.gif\""));
      var1.setline(1053);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_params");
      var7 = new PyObject[]{PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("dingusfish.gif")})})));
      var1.setline(1055);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("dingusfish.gif"));
      var1.setline(1057);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(1058);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1059);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), var1.getlocal(3), PyString.fromInterned("content-disposition")};
      String[] var6 = new String[]{"failobj", "header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1062);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1063);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("attachment"), var1.getlocal(3), PyString.fromInterned("foobar")};
      var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEText$107(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1070);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$108, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1073);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_types$109, (PyObject)null);
      var1.setlocal("test_types", var4);
      var3 = null;
      var1.setline(1083);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_payload$110, (PyObject)null);
      var1.setlocal("test_payload", var4);
      var3 = null;
      var1.setline(1087);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charset$111, (PyObject)null);
      var1.setlocal("test_charset", var4);
      var3 = null;
      var1.setline(1093);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_7bit_unicode_input$112, (PyObject)null);
      var1.setlocal("test_7bit_unicode_input", var4);
      var3 = null;
      var1.setline(1099);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_7bit_unicode_input_no_charset$113, (PyObject)null);
      var1.setlocal("test_7bit_unicode_input_no_charset", var4);
      var3 = null;
      var1.setline(1106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_8bit_unicode_input$114, (PyObject)null);
      var1.setlocal("test_8bit_unicode_input", var4);
      var3 = null;
      var1.setline(1114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_8bit_unicode_input_no_charset$115, (PyObject)null);
      var1.setlocal("test_8bit_unicode_input_no_charset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$108(PyFrame var1, ThreadState var2) {
      var1.setline(1071);
      PyObject var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello there"));
      var1.getlocal(0).__setattr__("_msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_types$109(PyFrame var1, ThreadState var2) {
      var1.setline(1074);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1075);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1076);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1077);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1078);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(1079);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_msg").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      PyObject var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1080);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_msg").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("charset"), var1.getlocal(3), PyString.fromInterned("foobar")};
      String[] var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_payload$110(PyFrame var1, ThreadState var2) {
      var1.setline(1084);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("hello there"));
      var1.setline(1085);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(0).__getattr__("_msg").__getattr__("is_multipart").__call__(var2).__not__());
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charset$111(PyFrame var1, ThreadState var2) {
      var1.setline(1088);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1089);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello there"), PyString.fromInterned("us-ascii")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1090);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1091);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_7bit_unicode_input$112(PyFrame var1, ThreadState var2) {
      var1.setline(1094);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1095);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var5 = new PyObject[]{PyUnicode.fromInterned("hello there"), PyString.fromInterned("us-ascii")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1096);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1097);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_7bit_unicode_input_no_charset$113(PyFrame var1, ThreadState var2) {
      var1.setline(1100);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1101);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("hello there"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1102);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1103);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.setline(1104);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var4 = PyString.fromInterned("hello there");
      PyObject var10002 = var4._in(var1.getlocal(2).__getattr__("as_string").__call__(var2));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_8bit_unicode_input$114(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      PyUnicode var3 = PyUnicode.fromInterned("кирилица");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1108);
      PyObject var5 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1109);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("utf-8")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1110);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_charset").__call__(var2).__getattr__("output_charset"), (PyObject)PyString.fromInterned("utf-8"));
      var1.setline(1111);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"utf-8\""));
      var1.setline(1112);
      var10000 = var1.getlocal(2);
      PyObject var10002 = var1.getlocal(3).__getattr__("get_payload");
      var6 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_8bit_unicode_input_no_charset$115(PyFrame var1, ThreadState var2) {
      var1.setline(1115);
      PyUnicode var3 = PyUnicode.fromInterned("кирилица");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1116);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("UnicodeEncodeError"), var1.getglobal("MIMEText"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMultipart$116(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1122);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$117, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1162);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_hierarchy$118, (PyObject)null);
      var1.setlocal("test_hierarchy", var4);
      var3 = null;
      var1.setline(1181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_empty_multipart_idempotent$119, (PyObject)null);
      var1.setlocal("test_empty_multipart_idempotent", var4);
      var3 = null;
      var1.setline(1198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_parts_in_a_multipart_with_none_epilogue$120, (PyObject)null);
      var1.setlocal("test_no_parts_in_a_multipart_with_none_epilogue", var4);
      var3 = null;
      var1.setline(1215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_parts_in_a_multipart_with_empty_epilogue$121, (PyObject)null);
      var1.setlocal("test_no_parts_in_a_multipart_with_empty_epilogue", var4);
      var3 = null;
      var1.setline(1236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_one_part_in_a_multipart$122, (PyObject)null);
      var1.setlocal("test_one_part_in_a_multipart", var4);
      var3 = null;
      var1.setline(1260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_empty_preamble$123, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_empty_preamble", var4);
      var3 = null;
      var1.setline(1287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_none_preamble$124, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_none_preamble", var4);
      var3 = null;
      var1.setline(1313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_none_epilogue$125, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_none_epilogue", var4);
      var3 = null;
      var1.setline(1339);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_empty_epilogue$126, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_empty_epilogue", var4);
      var3 = null;
      var1.setline(1366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_nl_epilogue$127, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_nl_epilogue", var4);
      var3 = null;
      var1.setline(1393);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_external_body$128, (PyObject)null);
      var1.setlocal("test_message_external_body", var4);
      var3 = null;
      var1.setline(1406);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_double_boundary$129, (PyObject)null);
      var1.setlocal("test_double_boundary", var4);
      var3 = null;
      var1.setline(1413);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_inner_contains_outer_boundary$130, (PyObject)null);
      var1.setlocal("test_nested_inner_contains_outer_boundary", var4);
      var3 = null;
      var1.setline(1432);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_with_same_boundary$131, (PyObject)null);
      var1.setlocal("test_nested_with_same_boundary", var4);
      var3 = null;
      var1.setline(1449);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_in_non_multipart$132, (PyObject)null);
      var1.setlocal("test_boundary_in_non_multipart", var4);
      var3 = null;
      var1.setline(1464);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_with_leading_space$133, (PyObject)null);
      var1.setlocal("test_boundary_with_leading_space", var4);
      var3 = null;
      var1.setline(1483);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_without_trailing_newline$134, (PyObject)null);
      var1.setlocal("test_boundary_without_trailing_newline", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$117(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PyBanner048.gif"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1125);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1127);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1127);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(1129);
      PyObject var10000 = var1.getglobal("MIMEBase");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("multipart"), PyString.fromInterned("mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var7 = new String[]{"boundary"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1130);
      var10000 = var1.getglobal("MIMEImage");
      var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("dingusfish.gif")};
      var7 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1131);
      var10000 = var1.getlocal(4).__getattr__("add_header");
      var6 = new PyObject[]{PyString.fromInterned("content-disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("dingusfish.gif")};
      var7 = new String[]{"filename"};
      var10000.__call__(var2, var6, var7);
      var3 = null;
      var1.setline(1133);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hi there,\n\nThis is the dingus fish.\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1138);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(5));
      var1.setline(1139);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(4));
      var1.setline(1140);
      PyString var8 = PyString.fromInterned("Barry <barry@digicool.com>");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("From"), var8);
      var3 = null;
      var1.setline(1141);
      var8 = PyString.fromInterned("Dingus Lovers <cravindogs@cravindogs.com>");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("To"), var8);
      var3 = null;
      var1.setline(1142);
      var8 = PyString.fromInterned("Here is your dingus fish");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Subject"), var8);
      var3 = null;
      var1.setline(1144);
      PyFloat var9 = Py.newFloat(9.87809702548486E8);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(1145);
      var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1146);
      var3 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1147);
         var3 = var1.getglobal("time").__getattr__("timezone");
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(1149);
         var3 = var1.getglobal("time").__getattr__("altzone");
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1150);
      var3 = var1.getlocal(8);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1151);
         var8 = PyString.fromInterned("-");
         var1.setlocal(9, var8);
         var3 = null;
      } else {
         var1.setline(1153);
         var8 = PyString.fromInterned("+");
         var1.setlocal(9, var8);
         var3 = null;
      }

      var1.setline(1154);
      var3 = PyString.fromInterned(" %s%04d")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(8)._floordiv(Py.newInteger(36))}));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1155);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S"), (PyObject)var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(6)))._add(var1.getlocal(10));
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Date"), var3);
      var3 = null;
      var1.setline(1158);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_msg", var3);
      var3 = null;
      var1.setline(1159);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_im", var3);
      var3 = null;
      var1.setline(1160);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_txt", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_hierarchy$118(PyFrame var1, ThreadState var2) {
      var1.setline(1164);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1165);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1166);
      var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1168);
      var3 = var1.getlocal(0).__getattr__("_msg");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1169);
      var1.getlocal(2).__call__(var2, var1.getlocal(4).__getattr__("is_multipart").__call__(var2));
      var1.setline(1170);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed"));
      var1.setline(1171);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1172);
      var1.getlocal(3).__call__((ThreadState)var2, var1.getglobal("IndexError"), (PyObject)var1.getlocal(4).__getattr__("get_payload"), (PyObject)Py.newInteger(2));
      var1.setline(1173);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1174);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1175);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(5);
      PyObject var10002 = var3._is(var1.getlocal(0).__getattr__("_txt"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1176);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(6);
      var10002 = var3._is(var1.getlocal(0).__getattr__("_im"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1177);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      var1.setline(1178);
      var1.getlocal(2).__call__(var2, var1.getlocal(5).__getattr__("is_multipart").__call__(var2).__not__());
      var1.setline(1179);
      var1.getlocal(2).__call__(var2, var1.getlocal(6).__getattr__("is_multipart").__call__(var2).__not__());
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_empty_multipart_idempotent$119(PyFrame var1, ThreadState var2) {
      var1.setline(1182);
      PyString var3 = PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\n\n\n--BOUNDARY--\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1195);
      PyObject var4 = var1.getglobal("Parser").__call__(var2).__getattr__("parsestr").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1196);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getattr__("as_string").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_parts_in_a_multipart_with_none_epilogue$120(PyFrame var1, ThreadState var2) {
      var1.setline(1199);
      PyObject var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1200);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1201);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1202);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1203);
      var1.getlocal(1).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1204);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\n\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_parts_in_a_multipart_with_empty_epilogue$121(PyFrame var1, ThreadState var2) {
      var1.setline(1216);
      PyObject var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1217);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1218);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1219);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1220);
      var4 = PyString.fromInterned("");
      var1.getlocal(1).__setattr__((String)"preamble", var4);
      var3 = null;
      var1.setline(1221);
      var4 = PyString.fromInterned("");
      var1.getlocal(1).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1222);
      var1.getlocal(1).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1223);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one_part_in_a_multipart$122(PyFrame var1, ThreadState var2) {
      var1.setline(1237);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1238);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1239);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1240);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1241);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1242);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1243);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1244);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1245);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_empty_preamble$123(PyFrame var1, ThreadState var2) {
      var1.setline(1261);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1262);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1263);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1264);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1265);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1266);
      var4 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"preamble", var4);
      var3 = null;
      var1.setline(1267);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1268);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1269);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1270);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_none_preamble$124(PyFrame var1, ThreadState var2) {
      var1.setline(1288);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1289);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1290);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1291);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1292);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1293);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("preamble", var3);
      var3 = null;
      var1.setline(1294);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1295);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1296);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1297);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_none_epilogue$125(PyFrame var1, ThreadState var2) {
      var1.setline(1314);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1315);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1316);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1317);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1318);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1319);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("epilogue", var3);
      var3 = null;
      var1.setline(1320);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1321);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1322);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1323);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_empty_epilogue$126(PyFrame var1, ThreadState var2) {
      var1.setline(1340);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1341);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1342);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1343);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1344);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1345);
      var4 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1346);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1347);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1348);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1349);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_nl_epilogue$127(PyFrame var1, ThreadState var2) {
      var1.setline(1367);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1368);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1369);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1370);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1371);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1372);
      var4 = PyString.fromInterned("\n");
      var1.getlocal(2).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1373);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1374);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1375);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1376);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_external_body$128(PyFrame var1, ThreadState var2) {
      var1.setline(1394);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1395);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_36.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1396);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1397);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1398);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/alternative"));
      var1.setline(1399);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1400);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(1400);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1401);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/external-body"));
         var1.setline(1402);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
         var1.setline(1403);
         PyObject var5 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1404);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      }
   }

   public PyObject test_double_boundary$129(PyFrame var1, ThreadState var2) {
      var1.setline(1410);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_37.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1411);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_inner_contains_outer_boundary$130(PyFrame var1, ThreadState var2) {
      var1.setline(1414);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1419);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_38.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1420);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1421);
      var1.getglobal("Iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(1422);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed\n    multipart/mixed\n        multipart/alternative\n            text/plain\n        text/plain\n    text/plain\n    text/plain\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_with_same_boundary$131(PyFrame var1, ThreadState var2) {
      var1.setline(1433);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1437);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_39.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1438);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1439);
      var1.getglobal("Iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(1440);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed\n    multipart/mixed\n        multipart/alternative\n        application/octet-stream\n        application/octet-stream\n    text/plain\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_in_non_multipart$132(PyFrame var1, ThreadState var2) {
      var1.setline(1450);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_40.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1451);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("MIME-Version: 1.0\nContent-Type: text/html; boundary=\"--961284236552522269\"\n\n----961284236552522269\nContent-Type: text/html;\nContent-Transfer-Encoding: 7Bit\n\n<html></html>\n\n----961284236552522269--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_with_leading_space$133(PyFrame var1, ThreadState var2) {
      var1.setline(1465);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1466);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MIME-Version: 1.0\nContent-Type: multipart/mixed; boundary=\"    XXXX\"\n\n--    XXXX\nContent-Type: text/plain\n\n\n--    XXXX\nContent-Type: text/plain\n\n--    XXXX--\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1479);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("is_multipart").__call__(var2));
      var1.setline(1480);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("    XXXX"));
      var1.setline(1481);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_without_trailing_newline$134(PyFrame var1, ThreadState var2) {
      var1.setline(1484);
      PyObject var3 = var1.getglobal("Parser").__call__(var2).__getattr__("parsestr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"===============0012394164==\"\nMIME-Version: 1.0\n\n--===============0012394164==\nContent-Type: image/file1.jpg\nMIME-Version: 1.0\nContent-Transfer-Encoding: base64\n\nYXNkZg==\n--===============0012394164==--"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1495);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("YXNkZg=="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestNonConformant$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1501);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_parse_missing_minor_type$136, (PyObject)null);
      var1.setlocal("test_parse_missing_minor_type", var4);
      var3 = null;
      var1.setline(1508);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_same_boundary_inner_outer$137, (PyObject)null);
      var1.setlocal("test_same_boundary_inner_outer", var4);
      var3 = null;
      var1.setline(1518);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_no_boundary$138, (PyObject)null);
      var1.setlocal("test_multipart_no_boundary", var4);
      var3 = null;
      var1.setline(1527);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_invalid_content_type$139, (PyObject)null);
      var1.setlocal("test_invalid_content_type", var4);
      var3 = null;
      var1.setline(1548);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_start_boundary$140, (PyObject)null);
      var1.setlocal("test_no_start_boundary", var4);
      var3 = null;
      var1.setline(1565);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_separating_blank_line$141, (PyObject)null);
      var1.setlocal("test_no_separating_blank_line", var4);
      var3 = null;
      var1.setline(1576);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_lying_multipart$142, (PyObject)null);
      var1.setlocal("test_lying_multipart", var4);
      var3 = null;
      var1.setline(1585);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_start_boundary$143, (PyObject)null);
      var1.setlocal("test_missing_start_boundary", var4);
      var3 = null;
      var1.setline(1600);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_first_line_is_continuation_header$144, (PyObject)null);
      var1.setlocal("test_first_line_is_continuation_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_parse_missing_minor_type$136(PyFrame var1, ThreadState var2) {
      var1.setline(1502);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1503);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_14.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1504);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1505);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1506);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_same_boundary_inner_outer$137(PyFrame var1, ThreadState var2) {
      var1.setline(1509);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1510);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_15.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1512);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1513);
      var1.getlocal(1).__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("defects")));
      var1.setline(1514);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1515);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("Errors").__getattr__("StartBoundaryNotFoundDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_no_boundary$138(PyFrame var1, ThreadState var2) {
      var1.setline(1519);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1520);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_25.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1521);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.setline(1522);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(2));
      var1.setline(1523);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("Errors").__getattr__("NoBoundaryInMultipartDefect")));
      var1.setline(1524);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(1)), var1.getglobal("Errors").__getattr__("MultipartInvariantViolationDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_content_type$139(PyFrame var1, ThreadState var2) {
      var1.setline(1528);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1529);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1530);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1532);
      PyString var4 = PyString.fromInterned("text");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(1533);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1534);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(1535);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1537);
      var1.getlocal(3).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1538);
      var4 = PyString.fromInterned("foo");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(1539);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1540);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(1541);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1543);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1544);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1545);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
      var1.setline(1546);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: foo\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_start_boundary$140(PyFrame var1, ThreadState var2) {
      var1.setline(1549);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1550);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_31.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1551);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("--BOUNDARY\nContent-Type: text/plain\n\nmessage 1\n\n--BOUNDARY\nContent-Type: text/plain\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_separating_blank_line$141(PyFrame var1, ThreadState var2) {
      var1.setline(1566);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1567);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_35.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1568);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: here's something interesting\n\ncounter to RFC 2822, there's no separating newline here\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_lying_multipart$142(PyFrame var1, ThreadState var2) {
      var1.setline(1577);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1578);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_41.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1579);
      var1.getlocal(1).__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("defects")));
      var1.setline(1580);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(2));
      var1.setline(1581);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("Errors").__getattr__("NoBoundaryInMultipartDefect")));
      var1.setline(1582);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(1)), var1.getglobal("Errors").__getattr__("MultipartInvariantViolationDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_start_boundary$143(PyFrame var1, ThreadState var2) {
      var1.setline(1586);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_42.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1595);
      var3 = var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1596);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1597);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("Errors").__getattr__("StartBoundaryNotFoundDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_first_line_is_continuation_header$144(PyFrame var1, ThreadState var2) {
      var1.setline(1601);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1602);
      PyString var4 = PyString.fromInterned(" Line 1\nLine 2\nLine 3");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1603);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1604);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("keys").__call__(var2), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(1605);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Line 2\nLine 3"));
      var1.setline(1606);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1607);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("Errors").__getattr__("FirstHeaderLineIsContinuationDefect")));
      var1.setline(1609);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)).__getattr__("line"), (PyObject)PyString.fromInterned(" Line 1\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestRFC2047$145(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1616);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_multiline$146, (PyObject)null);
      var1.setlocal("test_rfc2047_multiline", var4);
      var3 = null;
      var1.setline(1630);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater_unicode$147, (PyObject)null);
      var1.setlocal("test_whitespace_eater_unicode", var4);
      var3 = null;
      var1.setline(1638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater_unicode_2$148, (PyObject)null);
      var1.setlocal("test_whitespace_eater_unicode_2", var4);
      var3 = null;
      var1.setline(1647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_without_whitespace$149, (PyObject)null);
      var1.setlocal("test_rfc2047_without_whitespace", var4);
      var3 = null;
      var1.setline(1652);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_with_whitespace$150, (PyObject)null);
      var1.setlocal("test_rfc2047_with_whitespace", var4);
      var3 = null;
      var1.setline(1659);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_B_bad_padding$151, (PyObject)null);
      var1.setlocal("test_rfc2047_B_bad_padding", var4);
      var3 = null;
      var1.setline(1669);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_Q_invalid_digits$152, (PyObject)null);
      var1.setlocal("test_rfc2047_Q_invalid_digits", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_rfc2047_multiline$146(PyFrame var1, ThreadState var2) {
      var1.setline(1617);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1618);
      PyString var4 = PyString.fromInterned("Re: =?mac-iceland?q?r=8Aksm=9Arg=8Cs?= baz\n foo bar =?mac-iceland?q?r=8Aksm=9Arg=8Cs?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1620);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1621);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Re:"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("r\u008aksm\u009arg\u008cs"), PyString.fromInterned("mac-iceland")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz foo bar"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("r\u008aksm\u009arg\u008cs"), PyString.fromInterned("mac-iceland")})})));
      var1.setline(1626);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("make_header").__call__(var2, var1.getlocal(3))), (PyObject)PyString.fromInterned("Re: =?mac-iceland?q?r=8Aksm=9Arg=8Cs?= baz foo bar\n =?mac-iceland?q?r=8Aksm=9Arg=8Cs?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater_unicode$147(PyFrame var1, ThreadState var2) {
      var1.setline(1631);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1632);
      PyString var4 = PyString.fromInterned("=?ISO-8859-1?Q?Andr=E9?= Pirard <pirard@dom.ain>");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1633);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1634);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("André"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("Pirard <pirard@dom.ain>"), var1.getglobal("None")})})));
      var1.setline(1635);
      var3 = var1.getglobal("unicode").__call__(var2, var1.getglobal("make_header").__call__(var2, var1.getlocal(3))).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin-1"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1636);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("André Pirard <pirard@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater_unicode_2$148(PyFrame var1, ThreadState var2) {
      var1.setline(1639);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1640);
      PyString var4 = PyString.fromInterned("The =?iso-8859-1?b?cXVpY2sgYnJvd24gZm94?= jumped over the =?iso-8859-1?b?bGF6eSBkb2c=?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1641);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1642);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("The"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("quick brown fox"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("jumped over the"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("lazy dog"), PyString.fromInterned("iso-8859-1")})})));
      var1.setline(1644);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3)).__getattr__("__unicode__").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1645);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyUnicode.fromInterned("The quick brown fox jumped over the lazy dog"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2047_without_whitespace$149(PyFrame var1, ThreadState var2) {
      var1.setline(1648);
      PyString var3 = PyString.fromInterned("Sm=?ISO-8859-1?B?9g==?=rg=?ISO-8859-1?B?5Q==?=sbord");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1649);
      PyObject var4 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1650);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2047_with_whitespace$150(PyFrame var1, ThreadState var2) {
      var1.setline(1653);
      PyString var3 = PyString.fromInterned("Sm =?ISO-8859-1?B?9g==?= rg =?ISO-8859-1?B?5Q==?= sbord");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1654);
      PyObject var4 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1655);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Sm"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("ö"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("rg"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("å"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("sbord"), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2047_B_bad_padding$151(PyFrame var1, ThreadState var2) {
      var1.setline(1660);
      PyString var3 = PyString.fromInterned("=?iso-8859-1?B?%s?=");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1661);
      PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("dm=="), PyString.fromInterned("v")}), new PyTuple(new PyObject[]{PyString.fromInterned("dm="), PyString.fromInterned("v")}), new PyTuple(new PyObject[]{PyString.fromInterned("dm"), PyString.fromInterned("v")}), new PyTuple(new PyObject[]{PyString.fromInterned("dmk="), PyString.fromInterned("vi")}), new PyTuple(new PyObject[]{PyString.fromInterned("dmk"), PyString.fromInterned("vi")})});
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(1665);
      PyObject var8 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(1665);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(1666);
         PyObject var9 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(1)._mod(var1.getlocal(3)));
         var1.setlocal(5, var9);
         var5 = null;
         var1.setline(1667);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("iso-8859-1")})})));
      }
   }

   public PyObject test_rfc2047_Q_invalid_digits$152(PyFrame var1, ThreadState var2) {
      var1.setline(1671);
      PyString var3 = PyString.fromInterned("=?iso-8659-1?Q?andr=e9=zz?=");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1672);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(1)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("andré=zz"), PyString.fromInterned("iso-8659-1")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEMessage$153(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1678);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$154, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1685);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_type_error$155, (PyObject)null);
      var1.setlocal("test_type_error", var4);
      var3 = null;
      var1.setline(1688);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_valid_argument$156, (PyObject)null);
      var1.setlocal("test_valid_argument", var4);
      var3 = null;
      var1.setline(1703);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_multipart$157, (PyObject)null);
      var1.setlocal("test_bad_multipart", var4);
      var3 = null;
      var1.setline(1712);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_generate$158, (PyObject)null);
      var1.setlocal("test_generate", var4);
      var3 = null;
      var1.setline(1732);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_message_rfc822$159, (PyObject)null);
      var1.setlocal("test_parse_message_rfc822", var4);
      var3 = null;
      var1.setline(1745);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dsn$160, (PyObject)null);
      var1.setlocal("test_dsn", var4);
      var3 = null;
      var1.setline(1803);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_epilogue$161, (PyObject)null);
      var1.setlocal("test_epilogue", var4);
      var3 = null;
      var1.setline(1826);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_nl_preamble$162, (PyObject)null);
      var1.setlocal("test_no_nl_preamble", var4);
      var3 = null;
      var1.setline(1861);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type$163, (PyObject)null);
      var1.setlocal("test_default_type", var4);
      var3 = null;
      var1.setline(1881);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type_with_explicit_container_type$164, (PyObject)null);
      var1.setlocal("test_default_type_with_explicit_container_type", var4);
      var3 = null;
      var1.setline(1901);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type_non_parsed$165, (PyObject)null);
      var1.setlocal("test_default_type_non_parsed", var4);
      var3 = null;
      var1.setline(1975);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mime_attachments_in_constructor$166, (PyObject)null);
      var1.setlocal("test_mime_attachments_in_constructor", var4);
      var3 = null;
      var1.setline(1984);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_multipart_constructor$167, (PyObject)null);
      var1.setlocal("test_default_multipart_constructor", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$154(PyFrame var1, ThreadState var2) {
      var1.setline(1679);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_11.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1681);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_text", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1683);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1683);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_type_error$155(PyFrame var1, ThreadState var2) {
      var1.setline(1686);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)var1.getglobal("MIMEMessage"), (PyObject)PyString.fromInterned("a plain string"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_valid_argument$156(PyFrame var1, ThreadState var2) {
      var1.setline(1689);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1690);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1691);
      PyString var4 = PyString.fromInterned("A sub-message");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1692);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1693);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(1694);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1695);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1696);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1697);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("list")));
      var1.setline(1698);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(6)), (PyObject)Py.newInteger(1));
      var1.setline(1699);
      var3 = var1.getlocal(6).__getitem__(Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1700);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(7);
      PyObject var10002 = var3._is(var1.getlocal(4));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1701);
      var1.getlocal(1).__call__(var2, var1.getlocal(7).__getitem__(PyString.fromInterned("subject")), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_multipart$157(PyFrame var1, ThreadState var2) {
      var1.setline(1704);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1705);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1706);
      PyString var4 = PyString.fromInterned("subpart 1");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1707);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1708);
      var4 = PyString.fromInterned("subpart 2");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1709);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1710);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("Errors").__getattr__("MultipartConversionError"), var1.getlocal(4).__getattr__("attach"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_generate$158(PyFrame var1, ThreadState var2) {
      var1.setline(1714);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1715);
      PyString var4 = PyString.fromInterned("An enclosed message");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1716);
      var1.getlocal(1).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Here is the body of the message.\n"));
      var1.setline(1717);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1718);
      var4 = PyString.fromInterned("The enclosing message");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1719);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1720);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1721);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(1722);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: message/rfc822\nMIME-Version: 1.0\nSubject: The enclosing message\n\nSubject: An enclosed message\n\nHere is the body of the message.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_message_rfc822$159(PyFrame var1, ThreadState var2) {
      var1.setline(1733);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1734);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1735);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_11.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1736);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1737);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1738);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")));
      var1.setline(1739);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(1));
      var1.setline(1740);
      var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1741);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Message")));
      var1.setline(1742);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("An enclosed message"));
      var1.setline(1743);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here is the body of the message.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dsn$160(PyFrame var1, ThreadState var2) {
      var1.setline(1746);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1747);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1749);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_16.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1750);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/report"));
      var1.setline(1751);
      var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("is_multipart").__call__(var2));
      var1.setline(1752);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.setline(1754);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1755);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1756);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("This report relates to a message you sent with the following header fields:\n\n  Message-id: <002001c144a6$8752e060$56104586@oxy.edu>\n  Date: Sun, 23 Sep 2001 20:10:55 -0700\n  From: \"Ian T. Henry\" <henryi@oxy.edu>\n  To: SoCal Raves <scr@socal-raves.org>\n  Subject: [scr] yeah for Ians!!\n\nYour message cannot be delivered to the following recipients:\n\n  Recipient address: jangel1@cougar.noc.ucla.edu\n  Reason: recipient reached disk quota\n\n"));
      var1.setline(1774);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1775);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/delivery-status"));
      var1.setline(1776);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1779);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1780);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Message")));
      var1.setline(1781);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("original-envelope-id")), (PyObject)PyString.fromInterned("0GK500B4HD0888@cougar.noc.ucla.edu"));
      var1.setline(1782);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(5).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("dns"), PyString.fromInterned("reporting-mta")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1784);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(5).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("nsd"), PyString.fromInterned("reporting-mta")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(1785);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1786);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Message")));
      var1.setline(1787);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getitem__(PyString.fromInterned("action")), (PyObject)PyString.fromInterned("failed"));
      var1.setline(1788);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(6).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("original-recipient")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("rfc822"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("jangel1@cougar.noc.ucla.edu"), PyString.fromInterned("")})})));
      var1.setline(1790);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(6).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("rfc822"), PyString.fromInterned("final-recipient")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1792);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1793);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1794);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1795);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("list")));
      var1.setline(1796);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)Py.newInteger(1));
      var1.setline(1797);
      var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1798);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("Message")));
      var1.setline(1799);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1800);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("<002001c144a6$8752e060$56104586@oxy.edu>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_epilogue$161(PyFrame var1, ThreadState var2) {
      var1.setline(1804);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1805);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_21.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1807);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1809);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1809);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1810);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1811);
      PyString var7 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("From"), var7);
      var3 = null;
      var1.setline(1812);
      var7 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("To"), var7);
      var3 = null;
      var1.setline(1813);
      var7 = PyString.fromInterned("Test");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Subject"), var7);
      var3 = null;
      var1.setline(1814);
      var7 = PyString.fromInterned("MIME message");
      var1.getlocal(4).__setattr__((String)"preamble", var7);
      var3 = null;
      var1.setline(1815);
      var7 = PyString.fromInterned("End of MIME message\n");
      var1.getlocal(4).__setattr__((String)"epilogue", var7);
      var3 = null;
      var1.setline(1816);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("One"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1817);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Two"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1818);
      PyObject var10000 = var1.getlocal(4).__getattr__("add_header");
      PyObject[] var8 = new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("multipart/mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var6 = new String[]{"boundary"};
      var10000.__call__(var2, var8, var6);
      var3 = null;
      var1.setline(1819);
      var1.getlocal(4).__getattr__("attach").__call__(var2, var1.getlocal(5));
      var1.setline(1820);
      var1.getlocal(4).__getattr__("attach").__call__(var2, var1.getlocal(6));
      var1.setline(1821);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1822);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1823);
      var1.getlocal(8).__getattr__("flatten").__call__(var2, var1.getlocal(4));
      var1.setline(1824);
      var1.getlocal(1).__call__(var2, var1.getlocal(7).__getattr__("getvalue").__call__(var2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_nl_preamble$162(PyFrame var1, ThreadState var2) {
      var1.setline(1827);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1828);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1829);
      PyString var5 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var5);
      var3 = null;
      var1.setline(1830);
      var5 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var5);
      var3 = null;
      var1.setline(1831);
      var5 = PyString.fromInterned("Test");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var5);
      var3 = null;
      var1.setline(1832);
      var5 = PyString.fromInterned("MIME message");
      var1.getlocal(2).__setattr__((String)"preamble", var5);
      var3 = null;
      var1.setline(1833);
      var5 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"epilogue", var5);
      var3 = null;
      var1.setline(1834);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("One"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1835);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Two"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1836);
      PyObject var10000 = var1.getlocal(2).__getattr__("add_header");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("multipart/mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var4 = new String[]{"boundary"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(1837);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1838);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(4));
      var1.setline(1839);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: Test\nContent-Type: multipart/mixed; boundary=\"BOUNDARY\"\n\nMIME message\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nOne\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nTwo\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type$163(PyFrame var1, ThreadState var2) {
      var1.setline(1862);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1863);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1865);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1867);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1867);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1868);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1869);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1870);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1871);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1872);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1873);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1874);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1875);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1876);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1877);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1878);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1879);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type_with_explicit_container_type$164(PyFrame var1, ThreadState var2) {
      var1.setline(1882);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1883);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1885);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1887);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1887);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1888);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1889);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1890);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1891);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1892);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1893);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1894);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1895);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1896);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1897);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1898);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1899);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type_non_parsed$165(PyFrame var1, ThreadState var2) {
      var1.setline(1902);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1903);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1905);
      var3 = var1.getglobal("MIMEMultipart").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("digest"), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1906);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(3).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1908);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message 1\n"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1909);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message 2\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1910);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(4));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1911);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1912);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(6));
      var1.setline(1913);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(7));
      var1.setline(1914);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1915);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1916);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1917);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1918);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("Content-Type: multipart/digest; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\n\n--BOUNDARY\nContent-Type: message/rfc822\nMIME-Version: 1.0\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 1\n\n--BOUNDARY\nContent-Type: message/rfc822\nMIME-Version: 1.0\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.setline(1944);
      var1.getlocal(6).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1945);
      var1.getlocal(6).__delitem__((PyObject)PyString.fromInterned("mime-version"));
      var1.setline(1946);
      var1.getlocal(7).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1947);
      var1.getlocal(7).__delitem__((PyObject)PyString.fromInterned("mime-version"));
      var1.setline(1948);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1949);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1950);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1951);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1952);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("Content-Type: multipart/digest; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\n\n--BOUNDARY\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 1\n\n--BOUNDARY\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mime_attachments_in_constructor$166(PyFrame var1, ThreadState var2) {
      var1.setline(1976);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1977);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1978);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1979);
      PyObject var10000 = var1.getglobal("MIMEMultipart");
      PyObject[] var5 = new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})};
      String[] var4 = new String[]{"_subparts"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1980);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1981);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getlocal(2));
      var1.setline(1982);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_multipart_constructor$167(PyFrame var1, ThreadState var2) {
      var1.setline(1985);
      PyObject var3 = var1.getglobal("MIMEMultipart").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1986);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("is_multipart").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestIdempotent$168(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1995);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _msgobj$169, (PyObject)null);
      var1.setlocal("_msgobj", var4);
      var3 = null;
      var1.setline(2004);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _idempotent$170, (PyObject)null);
      var1.setlocal("_idempotent", var4);
      var3 = null;
      var1.setline(2011);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_text_message$171, (PyObject)null);
      var1.setlocal("test_parse_text_message", var4);
      var3 = null;
      var1.setline(2023);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_untyped_message$172, (PyObject)null);
      var1.setlocal("test_parse_untyped_message", var4);
      var3 = null;
      var1.setline(2031);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_multipart$173, (PyObject)null);
      var1.setlocal("test_simple_multipart", var4);
      var3 = null;
      var1.setline(2035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_MIME_digest$174, (PyObject)null);
      var1.setlocal("test_MIME_digest", var4);
      var3 = null;
      var1.setline(2039);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header$175, (PyObject)null);
      var1.setlocal("test_long_header", var4);
      var3 = null;
      var1.setline(2043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_MIME_digest_with_part_headers$176, (PyObject)null);
      var1.setlocal("test_MIME_digest_with_part_headers", var4);
      var3 = null;
      var1.setline(2047);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mixed_with_image$177, (PyObject)null);
      var1.setlocal("test_mixed_with_image", var4);
      var3 = null;
      var1.setline(2051);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_report$178, (PyObject)null);
      var1.setlocal("test_multipart_report", var4);
      var3 = null;
      var1.setline(2055);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dsn$179, (PyObject)null);
      var1.setlocal("test_dsn", var4);
      var3 = null;
      var1.setline(2059);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_preamble_epilogue$180, (PyObject)null);
      var1.setlocal("test_preamble_epilogue", var4);
      var3 = null;
      var1.setline(2063);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_one_part$181, (PyObject)null);
      var1.setlocal("test_multipart_one_part", var4);
      var3 = null;
      var1.setline(2067);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_no_parts$182, (PyObject)null);
      var1.setlocal("test_multipart_no_parts", var4);
      var3 = null;
      var1.setline(2071);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_start_boundary$183, (PyObject)null);
      var1.setlocal("test_no_start_boundary", var4);
      var3 = null;
      var1.setline(2075);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_charset$184, (PyObject)null);
      var1.setlocal("test_rfc2231_charset", var4);
      var3 = null;
      var1.setline(2079);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_more_rfc2231_parameters$185, (PyObject)null);
      var1.setlocal("test_more_rfc2231_parameters", var4);
      var3 = null;
      var1.setline(2083);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_text_plain_in_a_multipart_digest$186, (PyObject)null);
      var1.setlocal("test_text_plain_in_a_multipart_digest", var4);
      var3 = null;
      var1.setline(2087);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_multipart_mixeds$187, (PyObject)null);
      var1.setlocal("test_nested_multipart_mixeds", var4);
      var3 = null;
      var1.setline(2091);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_external_body_idempotent$188, (PyObject)null);
      var1.setlocal("test_message_external_body_idempotent", var4);
      var3 = null;
      var1.setline(2095);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_content_type$189, (PyObject)null);
      var1.setlocal("test_content_type", var4);
      var3 = null;
      var1.setline(2127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parser$190, (PyObject)null);
      var1.setlocal("test_parser", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _msgobj$169(PyFrame var1, ThreadState var2) {
      var1.setline(1996);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1998);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2000);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2000);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2001);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2002);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _idempotent$170(PyFrame var1, ThreadState var2) {
      var1.setline(2005);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2006);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2007);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
      String[] var4 = new String[]{"maxheaderlen"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2008);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(1));
      var1.setline(2009);
      var1.getlocal(3).__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_text_message$171(PyFrame var1, ThreadState var2) {
      var1.setline(2012);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2013);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(2014);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2015);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(2016);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(2017);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2).__getitem__(Py.newInteger(1)), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("us-ascii")})));
      var1.setline(2018);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2019);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("preamble"), var1.getglobal("None"));
      var1.setline(2020);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("epilogue"), var1.getglobal("None"));
      var1.setline(2021);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_untyped_message$172(PyFrame var1, ThreadState var2) {
      var1.setline(2024);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2025);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(2026);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2027);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_params").__call__(var2), var1.getglobal("None"));
      var1.setline(2028);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), var1.getglobal("None"));
      var1.setline(2029);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_multipart$173(PyFrame var1, ThreadState var2) {
      var1.setline(2032);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2033);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_MIME_digest$174(PyFrame var1, ThreadState var2) {
      var1.setline(2036);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2037);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header$175(PyFrame var1, ThreadState var2) {
      var1.setline(2040);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_27.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2041);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_MIME_digest_with_part_headers$176(PyFrame var1, ThreadState var2) {
      var1.setline(2044);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2045);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mixed_with_image$177(PyFrame var1, ThreadState var2) {
      var1.setline(2048);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_06.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2049);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_report$178(PyFrame var1, ThreadState var2) {
      var1.setline(2052);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2053);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dsn$179(PyFrame var1, ThreadState var2) {
      var1.setline(2056);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_16.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2057);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_preamble_epilogue$180(PyFrame var1, ThreadState var2) {
      var1.setline(2060);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_21.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2061);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_one_part$181(PyFrame var1, ThreadState var2) {
      var1.setline(2064);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_23.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2065);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_no_parts$182(PyFrame var1, ThreadState var2) {
      var1.setline(2068);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_24.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2069);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_start_boundary$183(PyFrame var1, ThreadState var2) {
      var1.setline(2072);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_31.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2073);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_charset$184(PyFrame var1, ThreadState var2) {
      var1.setline(2076);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_32.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2077);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_more_rfc2231_parameters$185(PyFrame var1, ThreadState var2) {
      var1.setline(2080);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_33.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2081);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_text_plain_in_a_multipart_digest$186(PyFrame var1, ThreadState var2) {
      var1.setline(2084);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_34.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2085);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_multipart_mixeds$187(PyFrame var1, ThreadState var2) {
      var1.setline(2088);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_12a.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2089);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_external_body_idempotent$188(PyFrame var1, ThreadState var2) {
      var1.setline(2092);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_36.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2093);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_content_type$189(PyFrame var1, ThreadState var2) {
      var1.setline(2096);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2097);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2099);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2100);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/report"));
      var1.setline(2102);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(2103);
      var3 = var1.getlocal(3).__getattr__("get_params").__call__(var2).__iter__();

      while(true) {
         var1.setline(2103);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(2105);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("report-type")), (PyObject)PyString.fromInterned("delivery-status"));
            var1.setline(2106);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("boundary")), (PyObject)PyString.fromInterned("D1690A7AC1.996856090/mail.example.com"));
            var1.setline(2107);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("preamble"), (PyObject)PyString.fromInterned("This is a MIME-encapsulated message.\n"));
            var1.setline(2108);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("epilogue"), (PyObject)PyString.fromInterned("\n"));
            var1.setline(2109);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
            var1.setline(2111);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(2112);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
            var1.setline(2113);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.setline(2114);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(2115);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
            var1.setline(2116);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.setline(2117);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(2118);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
            var1.setline(2119);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("Message")));
            var1.setline(2120);
            var3 = var1.getlocal(10).__getattr__("get_payload").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(2121);
            var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(11), var1.getglobal("list")));
            var1.setline(2122);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(11)), (PyObject)Py.newInteger(1));
            var1.setline(2123);
            var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(2124);
            var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(12), var1.getglobal("Message")));
            var1.setline(2125);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(12).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var9 = Py.unpackSequence(var7, 2);
         PyObject var6 = var9[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(2104);
         var5 = var1.getlocal(7);
         var1.getlocal(5).__setitem__(var1.getlocal(6), var5);
         var5 = null;
      }
   }

   public PyObject test_parser$190(PyFrame var1, ThreadState var2) {
      var1.setline(2128);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2129);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2130);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_06.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2132);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2135);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2136);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("list")));
      var1.setline(2137);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(1));
      var1.setline(2138);
      var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(2139);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Message")));
      var1.setline(2140);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2141);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.setline(2142);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMiscellaneous$191(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2148);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_message_from_string$192, (PyObject)null);
      var1.setlocal("test_message_from_string", var4);
      var3 = null;
      var1.setline(2162);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_file$193, (PyObject)null);
      var1.setlocal("test_message_from_file", var4);
      var3 = null;
      var1.setline(2177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_string_with_class$194, (PyObject)null);
      var1.setlocal("test_message_from_string_with_class", var4);
      var3 = null;
      var1.setline(2200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_file_with_class$196, (PyObject)null);
      var1.setlocal("test_message_from_file_with_class", var4);
      var3 = null;
      var1.setline(2221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test__all__$198, (PyObject)null);
      var1.setlocal("test__all__", var4);
      var3 = null;
      var1.setline(2239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate$199, (PyObject)null);
      var1.setlocal("test_formatdate", var4);
      var3 = null;
      var1.setline(2244);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate_localtime$200, (PyObject)null);
      var1.setlocal("test_formatdate_localtime", var4);
      var3 = null;
      var1.setline(2250);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate_usegmt$201, (PyObject)null);
      var1.setlocal("test_formatdate_usegmt", var4);
      var3 = null;
      var1.setline(2259);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_none$202, (PyObject)null);
      var1.setlocal("test_parsedate_none", var4);
      var3 = null;
      var1.setline(2262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_compact$203, (PyObject)null);
      var1.setlocal("test_parsedate_compact", var4);
      var3 = null;
      var1.setline(2267);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_no_dayofweek$204, (PyObject)null);
      var1.setlocal("test_parsedate_no_dayofweek", var4);
      var3 = null;
      var1.setline(2272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_compact_no_dayofweek$205, (PyObject)null);
      var1.setlocal("test_parsedate_compact_no_dayofweek", var4);
      var3 = null;
      var1.setline(2277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_acceptable_to_time_functions$206, (PyObject)null);
      var1.setlocal("test_parsedate_acceptable_to_time_functions", var4);
      var3 = null;
      var1.setline(2288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mktime_tz$207, (PyObject)null);
      var1.setlocal("test_mktime_tz", var4);
      var3 = null;
      var1.setline(2294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_y2k$208, PyString.fromInterned("Test for parsing a date with a two-digit year.\n\n        Parsing a date with a two-digit year should return the correct\n        four-digit year. RFC822 allows two-digit years, but RFC2822 (which\n        obsoletes RFC822) requires four-digit years.\n\n        "));
      var1.setlocal("test_parsedate_y2k", var4);
      var3 = null;
      var1.setline(2307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parseaddr_empty$209, (PyObject)null);
      var1.setlocal("test_parseaddr_empty", var4);
      var3 = null;
      var1.setline(2311);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_noquote_dump$210, (PyObject)null);
      var1.setlocal("test_noquote_dump", var4);
      var3 = null;
      var1.setline(2316);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_escape_dump$211, (PyObject)null);
      var1.setlocal("test_escape_dump", var4);
      var3 = null;
      var1.setline(2324);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_escape_backslashes$212, (PyObject)null);
      var1.setlocal("test_escape_backslashes", var4);
      var3 = null;
      var1.setline(2332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_name_with_dot$213, (PyObject)null);
      var1.setlocal("test_name_with_dot", var4);
      var3 = null;
      var1.setline(2341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parseaddr_preserves_quoted_pairs_in_addresses$214, (PyObject)null);
      var1.setlocal("test_parseaddr_preserves_quoted_pairs_in_addresses", var4);
      var3 = null;
      var1.setline(2359);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multiline_from_comment$215, (PyObject)null);
      var1.setlocal("test_multiline_from_comment", var4);
      var3 = null;
      var1.setline(2365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_quote_dump$216, (PyObject)null);
      var1.setlocal("test_quote_dump", var4);
      var3 = null;
      var1.setline(2370);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_fix_eols$217, (PyObject)null);
      var1.setlocal("test_fix_eols", var4);
      var3 = null;
      var1.setline(2378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charset_richcomparisons$218, (PyObject)null);
      var1.setlocal("test_charset_richcomparisons", var4);
      var3 = null;
      var1.setline(2398);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses$219, (PyObject)null);
      var1.setlocal("test_getaddresses", var4);
      var3 = null;
      var1.setline(2405);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses_nasty$220, (PyObject)null);
      var1.setlocal("test_getaddresses_nasty", var4);
      var3 = null;
      var1.setline(2415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses_embedded_comment$221, PyString.fromInterned("Test proper handling of a nested comment"));
      var1.setlocal("test_getaddresses_embedded_comment", var4);
      var3 = null;
      var1.setline(2421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_utils_quote_unquote$222, (PyObject)null);
      var1.setlocal("test_utils_quote_unquote", var4);
      var3 = null;
      var1.setline(2428);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_body_encoding_with_bogus_charset$223, (PyObject)null);
      var1.setlocal("test_get_body_encoding_with_bogus_charset", var4);
      var3 = null;
      var1.setline(2432);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_body_encoding_with_uppercase_charset$224, (PyObject)null);
      var1.setlocal("test_get_body_encoding_with_uppercase_charset", var4);
      var3 = null;
      var1.setline(2458);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charsets_case_insensitive$225, (PyObject)null);
      var1.setlocal("test_charsets_case_insensitive", var4);
      var3 = null;
      var1.setline(2463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_partial_falls_inside_message_delivery_status$226, (PyObject)null);
      var1.setlocal("test_partial_falls_inside_message_delivery_status", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_message_from_string$192(PyFrame var1, ThreadState var2) {
      var1.setline(2149);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2151);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2153);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2153);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(2154);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2155);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2158);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var7 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
      String[] var6 = new String[]{"maxheaderlen"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2159);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
      var1.setline(2160);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_from_file$193(PyFrame var1, ThreadState var2) {
      var1.setline(2163);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2165);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(2166);
         var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(2167);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(2168);
         var4 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(2171);
         PyObject var10000 = var1.getglobal("Generator");
         PyObject[] var7 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
         String[] var5 = new String[]{"maxheaderlen"};
         var10000 = var10000.__call__(var2, var7, var5);
         var4 = null;
         var4 = var10000;
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(2172);
         var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
         var1.setline(2173);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2175);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2175);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_from_string_with_class$194(PyFrame var1, ThreadState var2) {
      var1.setline(2178);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2179);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(2181);
         var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2183);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2183);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2185);
      PyObject[] var7 = new PyObject[]{var1.getglobal("Message")};
      var4 = Py.makeClass("MyMessage", var7, MyMessage$195);
      var1.setlocal(4, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2188);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2189);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getlocal(4)));
      var1.setline(2191);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2193);
         var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2195);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2195);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2196);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2197);
      var3 = var1.getlocal(5).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(2197);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2198);
         var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getlocal(4)));
      }
   }

   public PyObject MyMessage$195(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2186);
      return var1.getf_locals();
   }

   public PyObject test_message_from_file_with_class$196(PyFrame var1, ThreadState var2) {
      var1.setline(2201);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2203);
      PyObject[] var7 = new PyObject[]{var1.getglobal("Message")};
      PyObject var4 = Py.makeClass("MyMessage", var7, MyMessage$197);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2206);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2208);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2210);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2210);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2211);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getlocal(2)));
      var1.setline(2213);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2215);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2217);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2217);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2218);
      var3 = var1.getlocal(4).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(2218);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(2219);
         var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getlocal(2)));
      }
   }

   public PyObject MyMessage$197(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2204);
      return var1.getf_locals();
   }

   public PyObject test__all__$198(PyFrame var1, ThreadState var2) {
      var1.setline(2222);
      PyObject var3 = var1.getglobal("__import__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("email"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2223);
      var3 = var1.getlocal(1).__getattr__("__all__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2224);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(2225);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Charset"), PyString.fromInterned("Encoders"), PyString.fromInterned("Errors"), PyString.fromInterned("Generator"), PyString.fromInterned("Header"), PyString.fromInterned("Iterators"), PyString.fromInterned("MIMEAudio"), PyString.fromInterned("MIMEBase"), PyString.fromInterned("MIMEImage"), PyString.fromInterned("MIMEMessage"), PyString.fromInterned("MIMEMultipart"), PyString.fromInterned("MIMENonMultipart"), PyString.fromInterned("MIMEText"), PyString.fromInterned("Message"), PyString.fromInterned("Parser"), PyString.fromInterned("Utils"), PyString.fromInterned("base64MIME"), PyString.fromInterned("base64mime"), PyString.fromInterned("charset"), PyString.fromInterned("encoders"), PyString.fromInterned("errors"), PyString.fromInterned("generator"), PyString.fromInterned("header"), PyString.fromInterned("iterators"), PyString.fromInterned("message"), PyString.fromInterned("message_from_file"), PyString.fromInterned("message_from_string"), PyString.fromInterned("mime"), PyString.fromInterned("parser"), PyString.fromInterned("quopriMIME"), PyString.fromInterned("quoprimime"), PyString.fromInterned("utils")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate$199(PyFrame var1, ThreadState var2) {
      var1.setline(2240);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2241);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("parsedate").__call__(var2, var1.getglobal("Utils").__getattr__("formatdate").__call__(var2, var1.getlocal(1))).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate_localtime$200(PyFrame var1, ThreadState var2) {
      var1.setline(2245);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2246);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getglobal("Utils").__getattr__("parsedate");
      PyObject var10004 = var1.getglobal("Utils").__getattr__("formatdate");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"localtime"};
      var10004 = var10004.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__(var2, var10004).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(1)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate_usegmt$201(PyFrame var1, ThreadState var2) {
      var1.setline(2251);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2252);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getglobal("Utils").__getattr__("formatdate");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"localtime"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S -0000"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1))));
      var1.setline(2255);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getglobal("Utils").__getattr__("formatdate");
      var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False"), var1.getglobal("True")};
      var4 = new String[]{"localtime", "usegmt"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S GMT"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_none$202(PyFrame var1, ThreadState var2) {
      var1.setline(2260);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_compact$203(PyFrame var1, ThreadState var2) {
      var1.setline(2264);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wed,3 Apr 2002 14:58:26 +0800")), var1.getglobal("Utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wed, 3 Apr 2002 14:58:26 +0800")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_no_dayofweek$204(PyFrame var1, ThreadState var2) {
      var1.setline(2268);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2269);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 2003 13:47:26 -0800")), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2003), Py.newInteger(2), Py.newInteger(25), Py.newInteger(13), Py.newInteger(47), Py.newInteger(26), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(-28800)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_compact_no_dayofweek$205(PyFrame var1, ThreadState var2) {
      var1.setline(2273);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2274);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800")), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2003), Py.newInteger(2), Py.newInteger(5), Py.newInteger(13), Py.newInteger(47), Py.newInteger(26), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(-28800)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_acceptable_to_time_functions$206(PyFrame var1, ThreadState var2) {
      var1.setline(2278);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2279);
      var3 = var1.getglobal("Utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2280);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2281);
      var1.getlocal(1).__call__(var2, var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.setline(2282);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y"), (PyObject)var1.getlocal(2))), (PyObject)Py.newInteger(2003));
      var1.setline(2283);
      var3 = var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2284);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2285);
      var1.getlocal(1).__call__(var2, var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.setline(2286);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y"), (PyObject)var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null))), (PyObject)Py.newInteger(2003));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mktime_tz$207(PyFrame var1, ThreadState var2) {
      var1.setline(2289);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("mktime_tz").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1970), Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(-1), Py.newInteger(-1), Py.newInteger(-1), Py.newInteger(0)}))), (PyObject)Py.newInteger(0));
      var1.setline(2291);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("mktime_tz").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1970), Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(-1), Py.newInteger(-1), Py.newInteger(-1), Py.newInteger(1234)}))), (PyObject)Py.newInteger(-1234));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_y2k$208(PyFrame var1, ThreadState var2) {
      var1.setline(2301);
      PyString.fromInterned("Test for parsing a date with a two-digit year.\n\n        Parsing a date with a two-digit year should return the correct\n        four-digit year. RFC822 allows two-digit years, but RFC2822 (which\n        obsoletes RFC822) requires four-digit years.\n\n        ");
      var1.setline(2302);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 03 13:47:26 -0800")), var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 2003 13:47:26 -0800")));
      var1.setline(2304);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 71 13:47:26 -0800")), var1.getglobal("Utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 1971 13:47:26 -0800")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parseaddr_empty$209(PyFrame var1, ThreadState var2) {
      var1.setline(2308);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<>")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})));
      var1.setline(2309);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("formataddr").__call__(var2, var1.getglobal("Utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<>"))), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_noquote_dump$210(PyFrame var1, ThreadState var2) {
      var1.setline(2312);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A Silly Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("A Silly Person <person@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_escape_dump$211(PyFrame var1, ThreadState var2) {
      var1.setline(2317);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A (Very) Silly Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"A \\(Very\\) Silly Person\" <person@dom.ain>"));
      var1.setline(2320);
      PyString var3 = PyString.fromInterned("A \\(Special\\) Person");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2321);
      var3 = PyString.fromInterned("person@dom.ain");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2322);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__(var2, var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})))), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_escape_backslashes$212(PyFrame var1, ThreadState var2) {
      var1.setline(2325);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("Arthur \\Backslash\\ Foobar"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"Arthur \\\\Backslash\\\\ Foobar\" <person@dom.ain>"));
      var1.setline(2328);
      PyString var3 = PyString.fromInterned("Arthur \\Backslash\\ Foobar");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2329);
      var3 = PyString.fromInterned("person@dom.ain");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2330);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__(var2, var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})))), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_name_with_dot$213(PyFrame var1, ThreadState var2) {
      var1.setline(2333);
      PyString var3 = PyString.fromInterned("John X. Doe <jxd@example.com>");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2334);
      var3 = PyString.fromInterned("\"John X. Doe\" <jxd@example.com>");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2335);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("John X. Doe"), PyString.fromInterned("jxd@example.com")});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2336);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(1)), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(2337);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(2)), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(2339);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}))), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parseaddr_preserves_quoted_pairs_in_addresses$214(PyFrame var1, ThreadState var2) {
      var1.setline(2351);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2352);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"\"example\" example\"@example.com")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\"\"example\" example\"@example.com")})));
      var1.setline(2354);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"\\\"example\\\" example\"@example.com")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\"\\\"example\\\" example\"@example.com")})));
      var1.setline(2356);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"\\\\\"example\\\\\" example\"@example.com")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\"\\\\\"example\\\\\" example\"@example.com")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multiline_from_comment$215(PyFrame var1, ThreadState var2) {
      var1.setline(2360);
      PyString var3 = PyString.fromInterned("Foo\n\tBar <foo@example.com>");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2363);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(1)), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("Foo Bar"), PyString.fromInterned("foo@example.com")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_quote_dump$216(PyFrame var1, ThreadState var2) {
      var1.setline(2366);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A Silly; Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"A Silly; Person\" <person@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_fix_eols$217(PyFrame var1, ThreadState var2) {
      var1.setline(2371);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2372);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2373);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2374);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2375);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\n")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2376);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n\r")), (PyObject)PyString.fromInterned("hello\r\n\r\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charset_richcomparisons$218(PyFrame var1, ThreadState var2) {
      var1.setline(2379);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2380);
      var3 = var1.getlocal(0).__getattr__("assertNotEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2381);
      var3 = var1.getglobal("Charset").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2382);
      var3 = var1.getglobal("Charset").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2383);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2384);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("US-ASCII"));
      var1.setline(2385);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("Us-AsCiI"));
      var1.setline(2386);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"), (PyObject)var1.getlocal(3));
      var1.setline(2387);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("US-ASCII"), (PyObject)var1.getlocal(3));
      var1.setline(2388);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Us-AsCiI"), (PyObject)var1.getlocal(3));
      var1.setline(2389);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("usascii"));
      var1.setline(2390);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("USASCII"));
      var1.setline(2391);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("UsAsCiI"));
      var1.setline(2392);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usascii"), (PyObject)var1.getlocal(3));
      var1.setline(2393);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("USASCII"), (PyObject)var1.getlocal(3));
      var1.setline(2394);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UsAsCiI"), (PyObject)var1.getlocal(3));
      var1.setline(2395);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setline(2396);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses$219(PyFrame var1, ThreadState var2) {
      var1.setline(2399);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2400);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("aperson@dom.ain (Al Person)"), PyString.fromInterned("Bud Person <bperson@dom.ain>")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Al Person"), PyString.fromInterned("aperson@dom.ain")}), new PyTuple(new PyObject[]{PyString.fromInterned("Bud Person"), PyString.fromInterned("bperson@dom.ain")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses_nasty$220(PyFrame var1, ThreadState var2) {
      var1.setline(2406);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2407);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo: ;")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})})));
      var1.setline(2408);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("[]*-- =~$")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("*--")})})));
      var1.setline(2411);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("Utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo: ;"), PyString.fromInterned("\"Jason R. Mastaler\" <jason@dom.ain>")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("Jason R. Mastaler"), PyString.fromInterned("jason@dom.ain")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses_embedded_comment$221(PyFrame var1, ThreadState var2) {
      var1.setline(2416);
      PyString.fromInterned("Test proper handling of a nested comment");
      var1.setline(2417);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2418);
      var3 = var1.getglobal("Utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("User ((nested comment)) <foo@bar.com>")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2419);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("foo@bar.com"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_utils_quote_unquote$222(PyFrame var1, ThreadState var2) {
      var1.setline(2422);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2423);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2424);
      PyObject var10000 = var1.getlocal(2).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("content-disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("foo\\wacky\"name")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2426);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("foo\\wacky\"name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_body_encoding_with_bogus_charset$223(PyFrame var1, ThreadState var2) {
      var1.setline(2429);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a charset"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2430);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_body_encoding").__call__(var2), (PyObject)PyString.fromInterned("base64"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_body_encoding_with_uppercase_charset$224(PyFrame var1, ThreadState var2) {
      var1.setline(2433);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2434);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2435);
      PyString var5 = PyString.fromInterned("text/plain; charset=UTF-8");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var5);
      var3 = null;
      var1.setline(2436);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=UTF-8"));
      var1.setline(2437);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2438);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      var1.setline(2439);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("utf-8"));
      var1.setline(2440);
      var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2441);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_body_encoding").__call__(var2), (PyObject)PyString.fromInterned("base64"));
      var1.setline(2442);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_payload");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("hello world"), var1.getlocal(4)};
      String[] var4 = new String[]{"charset"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2443);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("aGVsbG8gd29ybGQ=\n"));
      var1.setline(2444);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      var6 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("hello world"));
      var1.setline(2445);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("base64"));
      var1.setline(2447);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2448);
      var5 = PyString.fromInterned("text/plain; charset=\"US-ASCII\"");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var5);
      var3 = null;
      var1.setline(2449);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2450);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      var1.setline(2451);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2452);
      var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2453);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_body_encoding").__call__(var2), var1.getglobal("Encoders").__getattr__("encode_7or8bit"));
      var1.setline(2454);
      var10000 = var1.getlocal(2).__getattr__("set_payload");
      var6 = new PyObject[]{PyString.fromInterned("hello world"), var1.getlocal(4)};
      var4 = new String[]{"charset"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2455);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("hello world"));
      var1.setline(2456);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charsets_case_insensitive$225(PyFrame var1, ThreadState var2) {
      var1.setline(2459);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2460);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("US-ASCII"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2461);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_body_encoding").__call__(var2), var1.getlocal(2).__getattr__("get_body_encoding").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_partial_falls_inside_message_delivery_status$226(PyFrame var1, ThreadState var2) {
      var1.setline(2464);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2469);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_43.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2470);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2471);
      var1.getglobal("Iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(2472);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/report\n    text/plain\n    message/delivery-status\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n    text/rfc822-headers\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestIterators$227(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2509);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_body_line_iterator$228, (PyObject)null);
      var1.setlocal("test_body_line_iterator", var4);
      var3 = null;
      var1.setline(2529);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_typed_subpart_iterator$229, (PyObject)null);
      var1.setlocal("test_typed_subpart_iterator", var4);
      var3 = null;
      var1.setline(2546);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_typed_subpart_iterator_default_type$230, (PyObject)null);
      var1.setlocal("test_typed_subpart_iterator_default_type", var4);
      var3 = null;
      var1.setline(2565);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_pushCR_LF$231, PyString.fromInterned("FeedParser BufferedSubFile.push() assumed it received complete\n           line endings.  A CR ending one push() followed by a LF starting\n           the next push() added an empty line.\n        "));
      var1.setlocal("test_pushCR_LF", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_body_line_iterator$228(PyFrame var1, ThreadState var2) {
      var1.setline(2510);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2511);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2513);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2514);
      var3 = var1.getglobal("Iterators").__getattr__("body_line_iterator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2515);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2516);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(6));
      var1.setline(2517);
      var1.getlocal(2).__call__(var2, var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(5)), var1.getlocal(3).__getattr__("get_payload").__call__(var2));
      var1.setline(2519);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2520);
      var3 = var1.getglobal("Iterators").__getattr__("body_line_iterator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2521);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2522);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(43));
      var1.setline(2523);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_19.txt"));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2525);
         var1.getlocal(2).__call__(var2, var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(5)), var1.getlocal(6).__getattr__("read").__call__(var2));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(2527);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(2527);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_typed_subpart_iterator$229(PyFrame var1, ThreadState var2) {
      var1.setline(2530);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2531);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2532);
      var3 = var1.getglobal("Iterators").__getattr__("typed_subpart_iterator").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("text"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2533);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(2534);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(2535);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(2535);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2538);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(2));
            var1.setline(2539);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("a simple kind of mirror\nto reflect upon our own\na simple kind of mirror\nto reflect upon our own\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2536);
         PyObject var5 = var1.getlocal(5);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(5, var5);
         var1.setline(2537);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2));
      }
   }

   public PyObject test_typed_subpart_iterator_default_type$230(PyFrame var1, ThreadState var2) {
      var1.setline(2547);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2548);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2549);
      var3 = var1.getglobal("Iterators").__getattr__("typed_subpart_iterator").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("text"), (PyObject)PyString.fromInterned("plain"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2550);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(2551);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(2552);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(2552);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2555);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(1));
            var1.setline(2556);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("\nHi,\n\nDo you like this message?\n\n-Me\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2553);
         PyObject var5 = var1.getlocal(5);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(5, var5);
         var1.setline(2554);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2));
      }
   }

   public PyObject test_pushCR_LF$231(PyFrame var1, ThreadState var2) {
      var1.setline(2569);
      PyString.fromInterned("FeedParser BufferedSubFile.push() assumed it received complete\n           line endings.  A CR ending one push() followed by a LF starting\n           the next push() added an empty line.\n        ");
      var1.setline(2570);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("a\r \n"), Py.newInteger(2)}), new PyTuple(new PyObject[]{PyString.fromInterned("b"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("c\n"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("d\r\n"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("e\r"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("\nf"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("\r\n"), Py.newInteger(1)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2580);
      String[] var9 = new String[]{"BufferedSubFile", "NeedMoreData"};
      PyObject[] var10 = imp.importFrom("email.feedparser", var9, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal(2, var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(2581);
      PyObject var11 = var1.getlocal(2).__call__(var2);
      var1.setlocal(4, var11);
      var3 = null;
      var1.setline(2582);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2583);
      PyInteger var13 = Py.newInteger(0);
      var1.setlocal(6, var13);
      var3 = null;
      var1.setline(2584);
      var11 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(2584);
         var4 = var11.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var12;
         PyObject var10000;
         PyObject var10002;
         if (var4 == null) {
            var1.setline(2595);
            var10000 = var1.getlocal(0).__getattr__("assertTrue");
            var11 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10002 = var11._eq(var1.getlocal(6));
            var3 = null;
            var10000.__call__(var2, var10002);
            var1.setline(2596);
            var10000 = var1.getlocal(0).__getattr__("assertTrue");
            var10002 = PyString.fromInterned("").__getattr__("join");
            PyList var10004 = new PyList();
            var12 = var10004.__getattr__("append");
            var1.setlocal(11, var12);
            var5 = null;
            var1.setline(2596);
            var12 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(2596);
               var6 = var12.__iternext__();
               if (var6 == null) {
                  var1.setline(2596);
                  var1.dellocal(11);
                  var11 = var10002.__call__((ThreadState)var2, (PyObject)var10004);
                  var10002 = var11._eq(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(5)));
                  var3 = null;
                  var10000.__call__(var2, var10002);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var7 = Py.unpackSequence(var6, 2);
               PyObject var8 = var7[0];
               var1.setlocal(7, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(8, var8);
               var8 = null;
               var1.setline(2596);
               var1.getlocal(11).__call__(var2, var1.getlocal(7));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(2585);
         var1.getlocal(4).__getattr__("push").__call__(var2, var1.getlocal(7));
         var1.setline(2586);
         var12 = var1.getlocal(6);
         var12 = var12._iadd(var1.getlocal(8));
         var1.setlocal(6, var12);
         var1.setline(2587);
         PyInteger var14 = Py.newInteger(0);
         var1.setlocal(9, var14);
         var5 = null;

         while(true) {
            var1.setline(2588);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(2589);
            var12 = var1.getlocal(4).__getattr__("readline").__call__(var2);
            var1.setlocal(10, var12);
            var5 = null;
            var1.setline(2590);
            var12 = var1.getlocal(10);
            var10000 = var12._eq(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(2592);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(10));
            var1.setline(2593);
            var12 = var1.getlocal(9);
            var12 = var12._iadd(Py.newInteger(1));
            var1.setlocal(9, var12);
         }

         var1.setline(2594);
         var10000 = var1.getlocal(0).__getattr__("assertTrue");
         var12 = var1.getlocal(8);
         var10002 = var12._eq(var1.getlocal(9));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject TestParsers$232(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2601);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_header_parser$233, (PyObject)null);
      var1.setlocal("test_header_parser", var4);
      var3 = null;
      var1.setline(2615);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_continuation$234, (PyObject)null);
      var1.setlocal("test_whitespace_continuation", var4);
      var3 = null;
      var1.setline(2633);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_continuation_last_header$235, (PyObject)null);
      var1.setlocal("test_whitespace_continuation_last_header", var4);
      var3 = null;
      var1.setline(2651);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_crlf_separation$236, (PyObject)null);
      var1.setlocal("test_crlf_separation", var4);
      var3 = null;
      var1.setline(2665);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_digest_with_extra_mime_headers$237, (PyObject)null);
      var1.setlocal("test_multipart_digest_with_extra_mime_headers", var4);
      var3 = null;
      var1.setline(2699);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_three_lines$238, (PyObject)null);
      var1.setlocal("test_three_lines", var4);
      var3 = null;
      var1.setline(2707);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_strip_line_feed_and_carriage_return_in_headers$239, (PyObject)null);
      var1.setlocal("test_strip_line_feed_and_carriage_return_in_headers", var4);
      var3 = null;
      var1.setline(2718);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_header_syntax$240, (PyObject)null);
      var1.setlocal("test_rfc2822_header_syntax", var4);
      var3 = null;
      var1.setline(2728);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_space_not_allowed_in_header$241, (PyObject)null);
      var1.setlocal("test_rfc2822_space_not_allowed_in_header", var4);
      var3 = null;
      var1.setline(2734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_one_character_header$242, (PyObject)null);
      var1.setlocal("test_rfc2822_one_character_header", var4);
      var3 = null;
      var1.setline(2743);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_CRLFLF_at_end_of_part$243, (PyObject)null);
      var1.setlocal("test_CRLFLF_at_end_of_part", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_header_parser$233(PyFrame var1, ThreadState var2) {
      var1.setline(2602);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2604);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2606);
         PyObject var4 = var1.getglobal("HeaderParser").__call__(var2).__getattr__("parse").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2608);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2608);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2609);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(PyString.fromInterned("from")), (PyObject)PyString.fromInterned("ppp-request@zzz.org"));
      var1.setline(2610);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(PyString.fromInterned("to")), (PyObject)PyString.fromInterned("ppp@zzz.org"));
      var1.setline(2611);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed"));
      var1.setline(2612);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("is_multipart").__call__(var2));
      var1.setline(2613);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_continuation$234(PyFrame var1, ThreadState var2) {
      var1.setline(2616);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2619);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: the next line has a space on it\n \nDate: Mon, 8 Apr 2002 15:09:19 -0400\nMessage-ID: spam\n\nHere's the message body\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2629);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("the next line has a space on it\n "));
      var1.setline(2630);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("spam"));
      var1.setline(2631);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here's the message body\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_continuation_last_header$235(PyFrame var1, ThreadState var2) {
      var1.setline(2634);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2637);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nDate: Mon, 8 Apr 2002 15:09:19 -0400\nMessage-ID: spam\nSubject: the next line has a space on it\n \n\nHere's the message body\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2647);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("the next line has a space on it\n "));
      var1.setline(2648);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("spam"));
      var1.setline(2649);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here's the message body\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_crlf_separation$236(PyFrame var1, ThreadState var2) {
      var1.setline(2652);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2653);
      PyObject var10000 = var1.getglobal("openfile");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("msg_26.txt"), PyString.fromInterned("rb")};
      String[] var4 = new String[]{"mode"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2655);
         PyObject var7 = var1.getglobal("Parser").__call__(var2).__getattr__("parse").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var7);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2657);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2657);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2658);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(2659);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2660);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2661);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Simple email with attachment.\r\n\r\n"));
      var1.setline(2662);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2663);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("application/riscos"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_digest_with_extra_mime_headers$237(PyFrame var1, ThreadState var2) {
      var1.setline(2666);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2667);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2668);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2670);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2672);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2672);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2679);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2680);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(2681);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2682);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2683);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2684);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
      var1.setline(2685);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(2686);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(0));
      var1.setline(2687);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2688);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("message 1\n"));
      var1.setline(2690);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(2691);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2692);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2693);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
      var1.setline(2694);
      var3 = var1.getlocal(7).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(2695);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(0));
      var1.setline(2696);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2697);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("message 2\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_three_lines$238(PyFrame var1, ThreadState var2) {
      var1.setline(2701);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("From: Andrew Person <aperson@dom.ain"), PyString.fromInterned("Subject: Test"), PyString.fromInterned("Date: Tue, 20 Aug 2002 16:43:45 +1000")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2704);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2705);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("date")), (PyObject)PyString.fromInterned("Tue, 20 Aug 2002 16:43:45 +1000"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_strip_line_feed_and_carriage_return_in_headers$239(PyFrame var1, ThreadState var2) {
      var1.setline(2708);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2710);
      PyString var4 = PyString.fromInterned("text");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2711);
      var4 = PyString.fromInterned("more text");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2712);
      var3 = PyString.fromInterned("Header: %s\r\nNext-Header: %s\r\n\r\nBody\r\n\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2714);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2715);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header")), var1.getlocal(2));
      var1.setline(2716);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Next-Header")), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_header_syntax$240(PyFrame var1, ThreadState var2) {
      var1.setline(2719);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2720);
      PyString var4 = PyString.fromInterned(">From: foo\nFrom: bar\n!\"#QUX;~: zoo\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2721);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2722);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("keys").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.setline(2723);
      var3 = var1.getlocal(3).__getattr__("keys").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2724);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(2725);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("!\"#QUX;~"), PyString.fromInterned(">From"), PyString.fromInterned("From")})));
      var1.setline(2726);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("body"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_space_not_allowed_in_header$241(PyFrame var1, ThreadState var2) {
      var1.setline(2729);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2730);
      PyString var4 = PyString.fromInterned(">From foo@example.com 11:25:53\nFrom: bar\n!\"#QUX;~: zoo\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2731);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2732);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("keys").__call__(var2)), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_one_character_header$242(PyFrame var1, ThreadState var2) {
      var1.setline(2735);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2736);
      PyString var4 = PyString.fromInterned("A: first header\nB: second header\nCC: third header\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2737);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2738);
      var3 = var1.getlocal(3).__getattr__("keys").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2739);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(2740);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("A"), PyString.fromInterned("B"), PyString.fromInterned("CC")})));
      var1.setline(2741);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("body"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_CRLFLF_at_end_of_part$243(PyFrame var1, ThreadState var2) {
      var1.setline(2746);
      PyString var3 = PyString.fromInterned("From: foo@bar.com\nTo: baz\nMime-Version: 1.0\nContent-Type: multipart/mixed; boundary=BOUNDARY\n\n--BOUNDARY\nContent-Type: text/plain\n\nbody ending with CRLF newline\r\n\n--BOUNDARY--\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2759);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2760);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_payload").__call__(var2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestBase64$244(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2764);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_len$245, (PyObject)null);
      var1.setlocal("test_len", var4);
      var3 = null;
      var1.setline(2777);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decode$246, (PyObject)null);
      var1.setlocal("test_decode", var4);
      var3 = null;
      var1.setline(2784);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode$247, (PyObject)null);
      var1.setlocal("test_encode", var4);
      var3 = null;
      var1.setline(2806);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_encode$248, (PyObject)null);
      var1.setlocal("test_header_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_len$245(PyFrame var1, ThreadState var2) {
      var1.setline(2765);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2766);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("base64MIME").__getattr__("base64_len").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"));
      PyObject var10003 = var1.getglobal("len");
      PyObject var10005 = var1.getglobal("base64MIME").__getattr__("encode");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("")};
      String[] var4 = new String[]{"eol"};
      var10005 = var10005.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var10003.__call__(var2, var10005));
      var1.setline(2768);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(15)).__iter__();

      while(true) {
         var1.setline(2768);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var7);
         var1.setline(2769);
         PyObject var5 = var1.getlocal(2);
         var10000 = var5._eq(Py.newInteger(0));
         var5 = null;
         PyInteger var8;
         if (var10000.__nonzero__()) {
            var1.setline(2769);
            var8 = Py.newInteger(0);
            var1.setlocal(3, var8);
            var5 = null;
         } else {
            var1.setline(2770);
            var5 = var1.getlocal(2);
            var10000 = var5._le(Py.newInteger(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2770);
               var8 = Py.newInteger(4);
               var1.setlocal(3, var8);
               var5 = null;
            } else {
               var1.setline(2771);
               var5 = var1.getlocal(2);
               var10000 = var5._le(Py.newInteger(6));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2771);
                  var8 = Py.newInteger(8);
                  var1.setlocal(3, var8);
                  var5 = null;
               } else {
                  var1.setline(2772);
                  var5 = var1.getlocal(2);
                  var10000 = var5._le(Py.newInteger(9));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2772);
                     var8 = Py.newInteger(12);
                     var1.setlocal(3, var8);
                     var5 = null;
                  } else {
                     var1.setline(2773);
                     var5 = var1.getlocal(2);
                     var10000 = var5._le(Py.newInteger(12));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2773);
                        var8 = Py.newInteger(16);
                        var1.setlocal(3, var8);
                        var5 = null;
                     } else {
                        var1.setline(2774);
                        var8 = Py.newInteger(20);
                        var1.setlocal(3, var8);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var1.setline(2775);
         var1.getlocal(1).__call__(var2, var1.getglobal("base64MIME").__getattr__("base64_len").__call__(var2, PyString.fromInterned("x")._mul(var1.getlocal(2))), var1.getlocal(3));
      }
   }

   public PyObject test_decode$246(PyFrame var1, ThreadState var2) {
      var1.setline(2778);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2779);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2780);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8=")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2781);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8="), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2782);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8NCndvcmxk\n"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("helloXworld"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode$247(PyFrame var1, ThreadState var2) {
      var1.setline(2785);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2786);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2787);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("aGVsbG8=\n"));
      var1.setline(2789);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n")), (PyObject)PyString.fromInterned("aGVsbG8K\n"));
      var1.setline(2790);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64MIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n"), (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("aGVsbG8NCg==\n"));
      var1.setline(2792);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("base64MIME").__getattr__("encode");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      String[] var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("eHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IA==\n"));
      var1.setline(2799);
      var10000 = var1.getlocal(1);
      var10002 = var1.getglobal("base64MIME").__getattr__("encode");
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("eHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IA==\r\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_encode$248(PyFrame var1, ThreadState var2) {
      var1.setline(2807);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2808);
      var3 = var1.getglobal("base64MIME").__getattr__("header_encode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2809);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8=?="));
      var1.setline(2810);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld")), (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8NCndvcmxk?="));
      var1.setline(2812);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("iso-8859-2")};
      String[] var4 = new String[]{"charset"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-2?b?aGVsbG8=?="));
      var1.setline(2814);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("hello\nworld"), var1.getglobal("True")};
      var4 = new String[]{"keep_eols"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8Kd29ybGQ=?="));
      var1.setline(2817);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?eHh4eCB4eHh4IHh4eHggeHg=?=\n =?iso-8859-1?b?eHggeHh4eCB4eHh4IHh4eHg=?=\n =?iso-8859-1?b?IHh4eHggeHh4eCB4eHh4IHg=?=\n =?iso-8859-1?b?eHh4IHh4eHggeHh4eCB4eHg=?=\n =?iso-8859-1?b?eCB4eHh4IHh4eHggeHh4eCA=?=\n =?iso-8859-1?b?eHh4eCB4eHh4IHh4eHgg?="));
      var1.setline(2825);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?eHh4eCB4eHh4IHh4eHggeHg=?=\r\n =?iso-8859-1?b?eHggeHh4eCB4eHh4IHh4eHg=?=\r\n =?iso-8859-1?b?IHh4eHggeHh4eCB4eHh4IHg=?=\r\n =?iso-8859-1?b?eHh4IHh4eHggeHh4eCB4eHg=?=\r\n =?iso-8859-1?b?eCB4eHh4IHh4eHggeHh4eCA=?=\r\n =?iso-8859-1?b?eHh4eCB4eHh4IHh4eHgg?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestQuopri$249(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2836);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$250, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(2848);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_quopri_check$251, (PyObject)null);
      var1.setlocal("test_header_quopri_check", var4);
      var3 = null;
      var1.setline(2854);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_quopri_check$252, (PyObject)null);
      var1.setlocal("test_body_quopri_check", var4);
      var3 = null;
      var1.setline(2860);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_quopri_len$253, (PyObject)null);
      var1.setlocal("test_header_quopri_len", var4);
      var3 = null;
      var1.setline(2872);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_quopri_len$254, (PyObject)null);
      var1.setlocal("test_body_quopri_len", var4);
      var3 = null;
      var1.setline(2880);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_quote_unquote_idempotent$255, (PyObject)null);
      var1.setlocal("test_quote_unquote_idempotent", var4);
      var3 = null;
      var1.setline(2885);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_encode$256, (PyObject)null);
      var1.setlocal("test_header_encode", var4);
      var3 = null;
      var1.setline(2911);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decode$257, (PyObject)null);
      var1.setlocal("test_decode", var4);
      var3 = null;
      var1.setline(2918);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode$258, (PyObject)null);
      var1.setlocal("test_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$250(PyFrame var1, ThreadState var2) {
      var1.setline(2837);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2837);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("z"))._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(2837);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2837);
            var1.dellocal(1);
            PyList var10001 = new PyList();
            var3 = var10001.__getattr__("append");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(2838);
            var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Z"))._add(Py.newInteger(1))).__iter__();

            while(true) {
               var1.setline(2838);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(2838);
                  var1.dellocal(3);
                  PyObject var7 = var10000._add(var10001);
                  var10001 = new PyList();
                  var3 = var10001.__getattr__("append");
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(2839);
                  var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("9"))._add(Py.newInteger(1))).__iter__();

                  while(true) {
                     var1.setline(2839);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(2839);
                        var1.dellocal(4);
                        var3 = var7._add(var10001)._add(new PyList(new PyObject[]{PyString.fromInterned("!"), PyString.fromInterned("*"), PyString.fromInterned("+"), PyString.fromInterned("-"), PyString.fromInterned("/"), PyString.fromInterned(" ")}));
                        var1.getlocal(0).__setattr__("hlit", var3);
                        var3 = null;
                        var1.setline(2841);
                        var10000 = new PyList();
                        var3 = var10000.__getattr__("append");
                        var1.setlocal(5, var3);
                        var3 = null;
                        var1.setline(2841);
                        var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

                        while(true) {
                           var1.setline(2841);
                           var4 = var3.__iternext__();
                           PyObject var5;
                           PyObject var8;
                           if (var4 == null) {
                              var1.setline(2841);
                              var1.dellocal(5);
                              PyList var6 = var10000;
                              var1.getlocal(0).__setattr__((String)"hnon", var6);
                              var3 = null;
                              var1.setline(2842);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("hlit"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("hnon")));
                                 var7 = var3._eq(Py.newInteger(256));
                                 var3 = null;
                                 if (!var7.__nonzero__()) {
                                    var7 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var7);
                                 }
                              }

                              var1.setline(2843);
                              var10000 = new PyList();
                              var3 = var10000.__getattr__("append");
                              var1.setlocal(6, var3);
                              var3 = null;
                              var1.setline(2843);
                              var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" ")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~"))._add(Py.newInteger(1))).__iter__();

                              while(true) {
                                 var1.setline(2843);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(2843);
                                    var1.dellocal(6);
                                    var3 = var10000._add(new PyList(new PyObject[]{PyString.fromInterned("\t")}));
                                    var1.getlocal(0).__setattr__("blit", var3);
                                    var3 = null;
                                    var1.setline(2844);
                                    var1.getlocal(0).__getattr__("blit").__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
                                    var1.setline(2845);
                                    var10000 = new PyList();
                                    var3 = var10000.__getattr__("append");
                                    var1.setlocal(7, var3);
                                    var3 = null;
                                    var1.setline(2845);
                                    var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

                                    while(true) {
                                       var1.setline(2845);
                                       var4 = var3.__iternext__();
                                       if (var4 == null) {
                                          var1.setline(2845);
                                          var1.dellocal(7);
                                          var6 = var10000;
                                          var1.getlocal(0).__setattr__((String)"bnon", var6);
                                          var3 = null;
                                          var1.setline(2846);
                                          if (var1.getglobal("__debug__").__nonzero__()) {
                                             var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("blit"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("bnon")));
                                             var7 = var3._eq(Py.newInteger(256));
                                             var3 = null;
                                             if (!var7.__nonzero__()) {
                                                var7 = Py.None;
                                                throw Py.makeException(var1.getglobal("AssertionError"), var7);
                                             }
                                          }

                                          var1.f_lasti = -1;
                                          return Py.None;
                                       }

                                       var1.setlocal(2, var4);
                                       var1.setline(2845);
                                       var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(2));
                                       var8 = var5._notin(var1.getlocal(0).__getattr__("blit"));
                                       var5 = null;
                                       if (var8.__nonzero__()) {
                                          var1.setline(2845);
                                          var1.getlocal(7).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                                       }
                                    }
                                 }

                                 var1.setlocal(2, var4);
                                 var1.setline(2843);
                                 var1.getlocal(6).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                              }
                           }

                           var1.setlocal(2, var4);
                           var1.setline(2841);
                           var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(2));
                           var8 = var5._notin(var1.getlocal(0).__getattr__("hlit"));
                           var5 = null;
                           if (var8.__nonzero__()) {
                              var1.setline(2841);
                              var1.getlocal(5).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                           }
                        }
                     }

                     var1.setlocal(2, var4);
                     var1.setline(2839);
                     var1.getlocal(4).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(2838);
               var1.getlocal(3).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(2837);
         var1.getlocal(1).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject test_header_quopri_check$251(PyFrame var1, ThreadState var2) {
      var1.setline(2849);
      PyObject var3 = var1.getlocal(0).__getattr__("hlit").__iter__();

      while(true) {
         var1.setline(2849);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2851);
            var3 = var1.getlocal(0).__getattr__("hnon").__iter__();

            while(true) {
               var1.setline(2851);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(2852);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("quopriMIME").__getattr__("header_quopri_check").__call__(var2, var1.getlocal(1)));
            }
         }

         var1.setlocal(1, var4);
         var1.setline(2850);
         var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("quopriMIME").__getattr__("header_quopri_check").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_body_quopri_check$252(PyFrame var1, ThreadState var2) {
      var1.setline(2855);
      PyObject var3 = var1.getlocal(0).__getattr__("blit").__iter__();

      while(true) {
         var1.setline(2855);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2857);
            var3 = var1.getlocal(0).__getattr__("bnon").__iter__();

            while(true) {
               var1.setline(2857);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(2858);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("quopriMIME").__getattr__("body_quopri_check").__call__(var2, var1.getlocal(1)));
            }
         }

         var1.setlocal(1, var4);
         var1.setline(2856);
         var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("quopriMIME").__getattr__("body_quopri_check").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_header_quopri_len$253(PyFrame var1, ThreadState var2) {
      var1.setline(2861);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2862);
      var3 = var1.getglobal("quopriMIME").__getattr__("header_quopri_len");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2863);
      var3 = var1.getglobal("quopriMIME").__getattr__("header_encode");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2864);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("h@e@l@l@o@")})).__iter__();

      while(true) {
         var1.setline(2864);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2867);
            var3 = var1.getlocal(0).__getattr__("hlit").__iter__();

            while(true) {
               var1.setline(2867);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(2869);
                  var3 = var1.getlocal(0).__getattr__("hnon").__iter__();

                  while(true) {
                     var1.setline(2869);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(2870);
                     var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(3));
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(2868);
               var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(1));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(2866);
         PyObject var10000 = var1.getlocal(1);
         PyObject var10002 = var1.getlocal(2).__call__(var2, var1.getlocal(4));
         PyObject var10003 = var1.getglobal("len");
         PyObject var10005 = var1.getlocal(3);
         PyObject[] var5 = new PyObject[]{var1.getlocal(4), PyString.fromInterned(""), PyString.fromInterned("")};
         String[] var6 = new String[]{"charset", "eol"};
         var10005 = var10005.__call__(var2, var5, var6);
         var5 = null;
         var10000.__call__(var2, var10002, var10003.__call__(var2, var10005)._sub(Py.newInteger(7)));
      }
   }

   public PyObject test_body_quopri_len$254(PyFrame var1, ThreadState var2) {
      var1.setline(2873);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2874);
      var3 = var1.getglobal("quopriMIME").__getattr__("body_quopri_len");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2875);
      var3 = var1.getlocal(0).__getattr__("blit").__iter__();

      while(true) {
         var1.setline(2875);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2877);
            var3 = var1.getlocal(0).__getattr__("bnon").__iter__();

            while(true) {
               var1.setline(2877);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(2878);
               var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(3));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(2876);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      }
   }

   public PyObject test_quote_unquote_idempotent$255(PyFrame var1, ThreadState var2) {
      var1.setline(2881);
      PyObject var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(2881);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(2882);
         PyObject var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(2883);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("quopriMIME").__getattr__("unquote").__call__(var2, var1.getglobal("quopriMIME").__getattr__("quote").__call__(var2, var1.getlocal(2))), var1.getlocal(2));
      }
   }

   public PyObject test_header_encode$256(PyFrame var1, ThreadState var2) {
      var1.setline(2886);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2887);
      var3 = var1.getglobal("quopriMIME").__getattr__("header_encode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2888);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?="));
      var1.setline(2889);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=0D=0Aworld?="));
      var1.setline(2891);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("iso-8859-2")};
      String[] var4 = new String[]{"charset"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-2?q?hello?="));
      var1.setline(2893);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("hello\nworld"), var1.getglobal("True")};
      var4 = new String[]{"keep_eols"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=0Aworld?="));
      var1.setline(2895);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("helloÇthere")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=C7there?="));
      var1.setline(2897);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?xxxx_xxxx_xxxx_xxxx_xx?=\n =?iso-8859-1?q?xx_xxxx_xxxx_xxxx_xxxx?=\n =?iso-8859-1?q?_xxxx_xxxx_xxxx_xxxx_x?=\n =?iso-8859-1?q?xxx_xxxx_xxxx_xxxx_xxx?=\n =?iso-8859-1?q?x_xxxx_xxxx_?="));
      var1.setline(2904);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?xxxx_xxxx_xxxx_xxxx_xx?=\r\n =?iso-8859-1?q?xx_xxxx_xxxx_xxxx_xxxx?=\r\n =?iso-8859-1?q?_xxxx_xxxx_xxxx_xxxx_x?=\r\n =?iso-8859-1?q?xxx_xxxx_xxxx_xxxx_xxx?=\r\n =?iso-8859-1?q?x_xxxx_xxxx_?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_decode$257(PyFrame var1, ThreadState var2) {
      var1.setline(2912);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2913);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2914);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2915);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2916);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("helloXworld"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode$258(PyFrame var1, ThreadState var2) {
      var1.setline(2919);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2920);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2921);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2923);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\nworld")), (PyObject)PyString.fromInterned("hello\nworld"));
      var1.setline(2924);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\nworld"), (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("hello\nworld"));
      var1.setline(2926);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("quopriMIME").__getattr__("encode");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      String[] var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx=\n xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxx=\nx xxxx xxxx xxxx xxxx=20"));
      var1.setline(2931);
      var10000 = var1.getlocal(1);
      var10002 = var1.getglobal("quopriMIME").__getattr__("encode");
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx=\r\n xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxx=\r\nx xxxx xxxx xxxx xxxx=20"));
      var1.setline(2935);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quopriMIME").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("one line\n\ntwo line")), (PyObject)PyString.fromInterned("one line\n\ntwo line"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestCharset$259(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2947);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tearDown$260, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(2954);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_idempotent$261, (PyObject)null);
      var1.setlocal("test_idempotent", var4);
      var3 = null;
      var1.setline(2966);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_encode$262, (PyObject)null);
      var1.setlocal("test_body_encode", var4);
      var3 = null;
      var1.setline(2999);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_unicode_charset_name$263, (PyObject)null);
      var1.setlocal("test_unicode_charset_name", var4);
      var3 = null;
      var1.setline(3004);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_codecs_aliases_accepted$264, (PyObject)null);
      var1.setlocal("test_codecs_aliases_accepted", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDown$260(PyFrame var1, ThreadState var2) {
      var1.setline(2948);
      String[] var3 = new String[]{"Charset"};
      PyObject[] var6 = imp.importFrom("email", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;

      try {
         var1.setline(2950);
         var1.getlocal(1).__getattr__("CHARSETS").__delitem__((PyObject)PyString.fromInterned("fake"));
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getglobal("KeyError"))) {
            throw var7;
         }

         var1.setline(2952);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_idempotent$261(PyFrame var1, ThreadState var2) {
      var1.setline(2955);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2957);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2958);
      PyString var4 = PyString.fromInterned("Hello World!");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2959);
      var3 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2960);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(4)));
      var1.setline(2962);
      var4 = PyString.fromInterned("¤¢¤¤¤¦¤¨¤ª");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2963);
      var3 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2964);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_body_encode$262(PyFrame var1, ThreadState var2) {
      var1.setline(2967);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2969);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2970);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello w=F6rld"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld")));
      var1.setline(2972);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2973);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8gd29ybGQ=\n"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world")));
      var1.setline(2975);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2976);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world")));
      var1.setline(2978);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("euc-jp"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2980);
      if (var1.getglobal("is_jython").__not__().__nonzero__()) {
         try {
            var1.setline(2984);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u001b$B5FCO;~IW\u001b(B"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×")));
            var1.setline(2986);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×"), (PyObject)var1.getglobal("False")));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("LookupError"))) {
               throw var6;
            }

            var1.setline(2990);
         }
      }

      var1.setline(2994);
      String[] var7 = new String[]{"Charset"};
      PyObject[] var8 = imp.importFrom("email", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(2995);
      var1.getlocal(3).__getattr__("add_charset").__call__((ThreadState)var2, PyString.fromInterned("fake"), (PyObject)var1.getlocal(3).__getattr__("QP"), (PyObject)var1.getglobal("None"));
      var1.setline(2996);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fake"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2997);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_unicode_charset_name$263(PyFrame var1, ThreadState var2) {
      var1.setline(3000);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("us-ascii"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3001);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3002);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("Errors").__getattr__("CharsetError"), (PyObject)var1.getglobal("Charset"), (PyObject)PyString.fromInterned("ascÿii"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_codecs_aliases_accepted$264(PyFrame var1, ThreadState var2) {
      var1.setline(3005);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3006);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("utf-8"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestHeader$265(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(3011);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_simple$266, (PyObject)null);
      var1.setlocal("test_simple", var4);
      var3 = null;
      var1.setline(3018);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_surprise$267, (PyObject)null);
      var1.setlocal("test_simple_surprise", var4);
      var3 = null;
      var1.setline(3025);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_needs_no_decoding$268, (PyObject)null);
      var1.setlocal("test_header_needs_no_decoding", var4);
      var3 = null;
      var1.setline(3029);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long$269, (PyObject)null);
      var1.setlocal("test_long", var4);
      var3 = null;
      var1.setline(3035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multilingual$270, (PyObject)null);
      var1.setlocal("test_multilingual", var4);
      var3 = null;
      var1.setline(3084);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_ctor_default_args$271, (PyObject)null);
      var1.setlocal("test_header_ctor_default_args", var4);
      var3 = null;
      var1.setline(3091);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_explicit_maxlinelen$272, (PyObject)null);
      var1.setlocal("test_explicit_maxlinelen", var4);
      var3 = null;
      var1.setline(3105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_us_ascii_header$273, (PyObject)null);
      var1.setlocal("test_us_ascii_header", var4);
      var3 = null;
      var1.setline(3113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_string_charset$274, (PyObject)null);
      var1.setlocal("test_string_charset", var4);
      var3 = null;
      var1.setline(3128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_utf8_shortest$275, (PyObject)null);
      var1.setlocal("test_utf8_shortest", var4);
      var3 = null;
      var1.setline(3135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_8bit_header$276, (PyObject)null);
      var1.setlocal("test_bad_8bit_header", var4);
      var3 = null;
      var1.setline(3146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoded_adjacent_nonencoded$277, (PyObject)null);
      var1.setlocal("test_encoded_adjacent_nonencoded", var4);
      var3 = null;
      var1.setline(3156);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater$278, (PyObject)null);
      var1.setlocal("test_whitespace_eater", var4);
      var3 = null;
      var1.setline(3165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_broken_base64_header$279, (PyObject)null);
      var1.setlocal("test_broken_base64_header", var4);
      var3 = null;
      var1.setline(3171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ascii_add_header$280, (PyObject)null);
      var1.setlocal("test_ascii_add_header", var4);
      var3 = null;
      var1.setline(3178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nonascii_add_header_via_triple$281, (PyObject)null);
      var1.setlocal("test_nonascii_add_header_via_triple", var4);
      var3 = null;
      var1.setline(3186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode_unaliased_charset$282, (PyObject)null);
      var1.setlocal("test_encode_unaliased_charset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_simple$266(PyFrame var1, ThreadState var2) {
      var1.setline(3012);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3013);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello World!"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3014);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!"));
      var1.setline(3015);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" Goodbye World!"));
      var1.setline(3016);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!  Goodbye World!"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_surprise$267(PyFrame var1, ThreadState var2) {
      var1.setline(3019);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3020);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello World!"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3021);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!"));
      var1.setline(3022);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Goodbye World!"));
      var1.setline(3023);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World! Goodbye World!"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_needs_no_decoding$268(PyFrame var1, ThreadState var2) {
      var1.setline(3026);
      PyString var3 = PyString.fromInterned("no decoding needed");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3027);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(1)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long$269(PyFrame var1, ThreadState var2) {
      var1.setline(3030);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("I am the very model of a modern Major-General; I've information vegetable, animal, and mineral; I know the kings of England, and I quote the fights historical from Marathon to Waterloo, in order categorical; I'm very well acquainted, too, with matters mathematical; I understand equations, both the simple and quadratical; about binomial theorem I'm teeming with a lot o' news, with many cheerful facts about the square of the hypotenuse."), Py.newInteger(76)};
      String[] var4 = new String[]{"maxlinelen"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(3032);
      var10000 = var1.getlocal(1).__getattr__("encode");
      var3 = new PyObject[]{PyString.fromInterned(" ")};
      var4 = new String[]{"splitchars"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000.__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n ")).__iter__();

      while(true) {
         var1.setline(3032);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var7);
         var1.setline(3033);
         var10000 = var1.getlocal(0).__getattr__("assertTrue");
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10002 = var5._le(Py.newInteger(76));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject test_multilingual$270(PyFrame var1, ThreadState var2) {
      var1.setline(3036);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3037);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3038);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-2"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3039);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3040);
      PyString var4 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(3041);
      var4 = PyString.fromInterned("Finanèni metropole se hroutily pod tlakem jejich dùvtipu.. ");
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(3042);
      var3 = PyUnicode.fromInterned("正確に言うと翻訳はされていません。一部はドイツ語ですが、あとはでたらめです。実際には「Wenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.」と言っています。").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(3043);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(5), var1.getlocal(2));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(3044);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      var1.setline(3045);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7), var1.getlocal(4));
      var1.setline(3046);
      var3 = var1.getlocal(8).__getattr__("encode").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(3047);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerderband_ko?=\n =?iso-8859-1?q?mfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndischen_Wan?=\n =?iso-8859-1?q?dgem=E4lden_vorbei=2C_gegen_die_rotierenden_Klingen_bef=F6?=\n =?iso-8859-1?q?rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_metropole_se_hroutily?=\n =?iso-8859-2?q?_pod_tlakem_jejich_d=F9vtipu=2E=2E_?= =?utf-8?b?5q2j56K6?=\n =?utf-8?b?44Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE44G+44Gb44KT44CC?=\n =?utf-8?b?5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB44GC44Go44Gv44Gn?=\n =?utf-8?b?44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CMV2VubiBpc3QgZGFz?=\n =?utf-8?q?_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das_Oder_die_Fl?=\n =?utf-8?b?aXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBo+OBpuOBhOOBvuOBmQ==?=\n =?utf-8?b?44CC?="));
      var1.setline(3059);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(9)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned("iso-8859-2")}), new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned("utf-8")})})));
      var1.setline(3062);
      var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(8));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(3063);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")), (PyObject)PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an sÃ¼dlÃ¼ndischen WandgemÃ¤lden vorbei, gegen die rotierenden Klingen befÃ¶rdert. FinanÄ\u008dni metropole se hroutily pod tlakem jejich dÅ¯vtipu.. æ\u00ad£ç¢ºã\u0081«è¨\u0080ã\u0081\u0086ã\u0081¨ç¿»è¨³ã\u0081¯ã\u0081\u0095ã\u0082\u008cã\u0081¦ã\u0081\u0084ã\u0081¾ã\u0081\u009bã\u0082\u0093ã\u0080\u0082ä¸\u0080é\u0083¨ã\u0081¯ã\u0083\u0089ã\u0082¤ã\u0083\u0084èª\u009eã\u0081§ã\u0081\u0099ã\u0081\u008cã\u0080\u0081ã\u0081\u0082ã\u0081¨ã\u0081¯ã\u0081§ã\u0081\u009fã\u0082\u0089ã\u0082\u0081ã\u0081§ã\u0081\u0099ã\u0080\u0082å®\u009fé\u009a\u009bã\u0081«ã\u0081¯ã\u0080\u008cWenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.ã\u0080\u008dã\u0081¨è¨\u0080ã\u0081£ã\u0081¦ã\u0081\u0084ã\u0081¾ã\u0081\u0099ã\u0080\u0082"));
      var1.setline(3081);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getglobal("decode_header").__call__(var2, var1.getlocal(9)));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(3082);
      var1.getlocal(1).__call__(var2, var1.getlocal(11), var1.getlocal(9));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_ctor_default_args$271(PyFrame var1, ThreadState var2) {
      var1.setline(3085);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3086);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3087);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(""));
      var1.setline(3088);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1")));
      var1.setline(3089);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?foo?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_explicit_maxlinelen$272(PyFrame var1, ThreadState var2) {
      var1.setline(3092);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3093);
      PyString var5 = PyString.fromInterned("A very long line that must get split to something other than at the 76th character boundary to test the non-default behavior");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(3094);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3095);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("A very long line that must get split to something other than at the 76th\n character boundary to test the non-default behavior"));
      var1.setline(3098);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3099);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("A very long line that must get split to something other than at the\n 76th character boundary to test the non-default behavior"));
      var1.setline(3102);
      var10000 = var1.getglobal("Header");
      var6 = new PyObject[]{var1.getlocal(2), Py.newInteger(1024), PyString.fromInterned("Subject")};
      var4 = new String[]{"maxlinelen", "header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3103);
      var1.getlocal(1).__call__(var2, var1.getlocal(3).__getattr__("encode").__call__(var2), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_us_ascii_header$273(PyFrame var1, ThreadState var2) {
      var1.setline(3106);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3107);
      PyString var4 = PyString.fromInterned("hello");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3108);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3109);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("hello"), var1.getglobal("None")})})));
      var1.setline(3110);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3111);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("encode").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_string_charset$274(PyFrame var1, ThreadState var2) {
      var1.setline(3114);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3115);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3116);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(3117);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_utf8_shortest$275(PyFrame var1, ThreadState var2) {
      var1.setline(3129);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3130);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("pöstal"), (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3131);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?utf-8?q?p=C3=B6stal?="));
      var1.setline(3132);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("菊地時夫"), (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3133);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?utf-8?b?6I+K5Zyw5pmC5aSr?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_8bit_header$276(PyFrame var1, ThreadState var2) {
      var1.setline(3136);
      PyObject var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3137);
      var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3138);
      PyString var5 = PyString.fromInterned("Ynwp4dUEbay Auction Semiar- No Charge \u0096 Earn Big");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(3139);
      var1.getlocal(1).__call__(var2, var1.getglobal("UnicodeError"), var1.getglobal("Header"), var1.getlocal(3));
      var1.setline(3140);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3141);
      var1.getlocal(1).__call__(var2, var1.getglobal("UnicodeError"), var1.getlocal(4).__getattr__("append"), var1.getlocal(3));
      var1.setline(3142);
      PyObject var10000 = var1.getlocal(2);
      PyObject var10002 = var1.getglobal("str");
      PyObject var10004 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("replace")};
      String[] var4 = new String[]{"errors"};
      var10004 = var10004.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__(var2, var10004), var1.getlocal(3));
      var1.setline(3143);
      var10000 = var1.getlocal(4).__getattr__("append");
      var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("replace")};
      var4 = new String[]{"errors"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(3144);
      var1.getlocal(2).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoded_adjacent_nonencoded$277(PyFrame var1, ThreadState var2) {
      var1.setline(3147);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3148);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3149);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(3150);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("world"));
      var1.setline(3151);
      var3 = var1.getlocal(2).__getattr__("encode").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3152);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?= world"));
      var1.setline(3153);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getglobal("decode_header").__call__(var2, var1.getlocal(3)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3154);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("encode").__call__(var2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater$278(PyFrame var1, ThreadState var2) {
      var1.setline(3157);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3158);
      PyString var4 = PyString.fromInterned("Subject: =?koi8-r?b?8NLP18XSy8EgzsEgxsnOwczYztk=?= =?koi8-r?q?=CA?= zz.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3159);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3160);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Subject:"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("ðÒÏ×ÅÒËÁ ÎÁ ÆÉÎÁÌØÎÙÊ"), PyString.fromInterned("koi8-r")}), new PyTuple(new PyObject[]{PyString.fromInterned("zz."), var1.getglobal("None")})})));
      var1.setline(3161);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3162);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?koi8-r?b?8NLP18XSy8EgzsEgxsnOwczYztnK?= zz."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_broken_base64_header$279(PyFrame var1, ThreadState var2) {
      var1.setline(3166);
      PyObject var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3167);
      PyString var4 = PyString.fromInterned("Subject: =?EUC-KR?B?CSixpLDtKSC/7Liuvsax4iC6uLmwMcijIKHaILzSwd/H0SC8+LCjwLsgv7W/+Mj3I ?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3168);
      var1.getlocal(1).__call__(var2, var1.getglobal("Errors").__getattr__("HeaderParseError"), var1.getglobal("decode_header"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ascii_add_header$280(PyFrame var1, ThreadState var2) {
      var1.setline(3172);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3173);
      PyObject var10000 = var1.getlocal(1).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("bud.gif")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3175);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attachment; filename=\"bud.gif\""), (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("Content-Disposition")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nonascii_add_header_via_triple$281(PyFrame var1, ThreadState var2) {
      var1.setline(3179);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3180);
      PyObject var10000 = var1.getlocal(1).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), new PyTuple(new PyObject[]{PyString.fromInterned("iso-8859-1"), PyString.fromInterned(""), PyString.fromInterned("Fußballer.ppt")})};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3182);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attachment; filename*=\"iso-8859-1''Fu%DFballer.ppt\""), (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("Content-Disposition")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode_unaliased_charset$282(PyFrame var1, ThreadState var2) {
      var1.setline(3189);
      PyObject var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("abc"), (PyObject)PyString.fromInterned("iso-8859-2")).__getattr__("encode").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3190);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("=?iso-8859-2?q?abc?="));
      var1.setline(3191);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(1), var1.getglobal("str"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestRFC2231$283(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(3196);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_param$284, (PyObject)null);
      var1.setlocal("test_get_param", var4);
      var3 = null;
      var1.setline(3204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_param$285, (PyObject)null);
      var1.setlocal("test_set_param", var4);
      var3 = null;
      var1.setline(3241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param$286, (PyObject)null);
      var1.setlocal("test_del_param", var4);
      var3 = null;
      var1.setline(3271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_get_content_charset$287, (PyObject)null);
      var1.setlocal("test_rfc2231_get_content_charset", var4);
      var3 = null;
      var1.setline(3276);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset$288, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset", var4);
      var3 = null;
      var1.setline(3290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_filename$289, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_filename", var4);
      var3 = null;
      var1.setline(3302);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_filename_encoded$290, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_filename_encoded", var4);
      var3 = null;
      var1.setline(3314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_partly_encoded$291, (PyObject)null);
      var1.setlocal("test_rfc2231_partly_encoded", var4);
      var3 = null;
      var1.setline(3327);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_partly_nonencoded$292, (PyObject)null);
      var1.setlocal("test_rfc2231_partly_nonencoded", var4);
      var3 = null;
      var1.setline(3340);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_boundary$293, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_boundary", var4);
      var3 = null;
      var1.setline(3352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_charset$294, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_charset", var4);
      var3 = null;
      var1.setline(3365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_encoding_in_filename$295, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_encoding_in_filename", var4);
      var3 = null;
      var1.setline(3377);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_encoding_in_charset$296, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_encoding_in_charset", var4);
      var3 = null;
      var1.setline(3387);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_character_in_charset$297, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_character_in_charset", var4);
      var3 = null;
      var1.setline(3397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_character_in_filename$298, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_character_in_filename", var4);
      var3 = null;
      var1.setline(3409);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_unknown_encoding$299, (PyObject)null);
      var1.setlocal("test_rfc2231_unknown_encoding", var4);
      var3 = null;
      var1.setline(3418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_single_tick_in_filename_extended$300, (PyObject)null);
      var1.setlocal("test_rfc2231_single_tick_in_filename_extended", var4);
      var3 = null;
      var1.setline(3431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_single_tick_in_filename$301, (PyObject)null);
      var1.setlocal("test_rfc2231_single_tick_in_filename", var4);
      var3 = null;
      var1.setline(3441);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_tick_attack_extended$302, (PyObject)null);
      var1.setlocal("test_rfc2231_tick_attack_extended", var4);
      var3 = null;
      var1.setline(3454);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_tick_attack$303, (PyObject)null);
      var1.setlocal("test_rfc2231_tick_attack", var4);
      var3 = null;
      var1.setline(3465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_extended_values$304, (PyObject)null);
      var1.setlocal("test_rfc2231_no_extended_values", var4);
      var3 = null;
      var1.setline(3474);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_encoded_then_unencoded_segments$305, (PyObject)null);
      var1.setlocal("test_rfc2231_encoded_then_unencoded_segments", var4);
      var3 = null;
      var1.setline(3489);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_unencoded_then_encoded_segments$306, (PyObject)null);
      var1.setlocal("test_rfc2231_unencoded_then_encoded_segments", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_param$284(PyFrame var1, ThreadState var2) {
      var1.setline(3197);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3198);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_29.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3199);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3201);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("title"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("\"This is even more ***fun*** isn't it!\"")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_param$285(PyFrame var1, ThreadState var2) {
      var1.setline(3205);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3206);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3207);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii")};
      String[] var4 = new String[]{"charset"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3209);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned(""), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3211);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3213);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3215);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3216);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3218);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Return-Path: <bbb@zzz.org>\nDelivered-To: bbb@zzz.org\nReceived: by mail.zzz.org (Postfix, from userid 889)\n id 27CEAD38CC; Fri,  4 May 2001 14:05:44 -0400 (EDT)\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nMessage-ID: <15090.61304.110929.45684@aaa.zzz.org>\nFrom: bbb@ddd.com (John X. Doe)\nTo: bbb@zzz.org\nSubject: This is a test message\nDate: Fri, 4 May 2001 14:05:44 -0400\nContent-Type: text/plain; charset=us-ascii;\n title*=\"us-ascii'en'This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20isn%27t%20it%21\"\n\n\nHi,\n\nDo you like this message?\n\n-Me\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param$286(PyFrame var1, ThreadState var2) {
      var1.setline(3242);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3243);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3244);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("bar"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      String[] var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3245);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3247);
      var10000 = var1.getlocal(2).__getattr__("del_param");
      var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("Content-Type")};
      var4 = new String[]{"header"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3248);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Return-Path: <bbb@zzz.org>\nDelivered-To: bbb@zzz.org\nReceived: by mail.zzz.org (Postfix, from userid 889)\n id 27CEAD38CC; Fri,  4 May 2001 14:05:44 -0400 (EDT)\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nMessage-ID: <15090.61304.110929.45684@aaa.zzz.org>\nFrom: bbb@ddd.com (John X. Doe)\nTo: bbb@zzz.org\nSubject: This is a test message\nDate: Fri, 4 May 2001 14:05:44 -0400\nContent-Type: text/plain; charset=\"us-ascii\";\n title*=\"us-ascii'en'This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20isn%27t%20it%21\"\n\n\nHi,\n\nDo you like this message?\n\n-Me\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_get_content_charset$287(PyFrame var1, ThreadState var2) {
      var1.setline(3272);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3273);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_32.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3274);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), (PyObject)PyString.fromInterned("us-ascii"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset$288(PyFrame var1, ThreadState var2) {
      var1.setline(3277);
      PyString var3 = PyString.fromInterned("Content-Transfer-Encoding: 8bit\nContent-Disposition: inline; filename=\"file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEMP_nsmail.htm\"\nContent-Type: text/html; NAME*0=file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEM; NAME*1=P_nsmail.htm\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3283);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3284);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NAME"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3285);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3286);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEMP_nsmail.htm"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_filename$289(PyFrame var1, ThreadState var2) {
      var1.setline(3291);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3298);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3299);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_filename_encoded$290(PyFrame var1, ThreadState var2) {
      var1.setline(3303);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3310);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3311);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_partly_encoded$291(PyFrame var1, ThreadState var2) {
      var1.setline(3315);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3322);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3323);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This%20is%20even%20more%20***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_partly_nonencoded$292(PyFrame var1, ThreadState var2) {
      var1.setline(3328);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0=\"This%20is%20even%20more%20\";\n\tfilename*1=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3335);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3336);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_boundary$293(PyFrame var1, ThreadState var2) {
      var1.setline(3341);
      PyString var3 = PyString.fromInterned("Content-Type: multipart/alternative;\n\tboundary*0*=\"''This%20is%20even%20more%20\";\n\tboundary*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tboundary*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3348);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3349);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_charset$294(PyFrame var1, ThreadState var2) {
      var1.setline(3354);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain;\n\tcharset*0*=\"This%20is%20even%20more%20\";\n\tcharset*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tcharset*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3361);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3362);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), (PyObject)PyString.fromInterned("this is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_encoding_in_filename$295(PyFrame var1, ThreadState var2) {
      var1.setline(3366);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"bogus'xx'This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3373);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3374);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_encoding_in_charset$296(PyFrame var1, ThreadState var2) {
      var1.setline(3378);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain; charset*=bogus''utf-8%E2%80%9D\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3382);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3385);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_character_in_charset$297(PyFrame var1, ThreadState var2) {
      var1.setline(3388);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain; charset*=ascii''utf-8%E2%80%9D\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3392);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3395);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_character_in_filename$298(PyFrame var1, ThreadState var2) {
      var1.setline(3398);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"ascii'xx'This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2*=\"is it not.pdf%E2\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3405);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3406);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyUnicode.fromInterned("This is even more ***fun*** is it not.pdf�"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_unknown_encoding$299(PyFrame var1, ThreadState var2) {
      var1.setline(3410);
      PyString var3 = PyString.fromInterned("Content-Transfer-Encoding: 8bit\nContent-Disposition: inline; filename*=X-UNKNOWN''myfile.txt\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3415);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3416);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("myfile.txt"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_single_tick_in_filename_extended$300(PyFrame var1, ThreadState var2) {
      var1.setline(3419);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3420);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"Frank's\"; name*1*=\" Document\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3425);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3426);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3427);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getglobal("None"));
      var1.setline(3428);
      var1.getlocal(1).__call__(var2, var1.getlocal(5), var1.getglobal("None"));
      var1.setline(3429);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_single_tick_in_filename$301(PyFrame var1, ThreadState var2) {
      var1.setline(3432);
      PyString var3 = PyString.fromInterned("Content-Type: application/x-foo; name*0=\"Frank's\"; name*1=\" Document\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3436);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3437);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3438);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3439);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_tick_attack_extended$302(PyFrame var1, ThreadState var2) {
      var1.setline(3442);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3443);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"us-ascii'en-us'Frank's\"; name*1*=\" Document\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3448);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3449);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3450);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3451);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3452);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_tick_attack$303(PyFrame var1, ThreadState var2) {
      var1.setline(3455);
      PyString var3 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0=\"us-ascii'en-us'Frank's\"; name*1=\" Document\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3460);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3461);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3462);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3463);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("us-ascii'en-us'Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_extended_values$304(PyFrame var1, ThreadState var2) {
      var1.setline(3466);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3467);
      PyString var4 = PyString.fromInterned("Content-Type: application/x-foo; name=\"Frank's Document\"\n\n");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3471);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3472);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_encoded_then_unencoded_segments$305(PyFrame var1, ThreadState var2) {
      var1.setline(3475);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3476);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"us-ascii'en-us'My\";\n\tname*1=\" Document\";\n\tname*2*=\" For You\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3483);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3484);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3485);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3486);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3487);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("My Document For You"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_unencoded_then_encoded_segments$306(PyFrame var1, ThreadState var2) {
      var1.setline(3490);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3491);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0=\"us-ascii'en-us'My\";\n\tname*1*=\" Document\";\n\tname*2*=\" For You\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3498);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3499);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3500);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3501);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3502);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("My Document For You"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestSigned$307(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(3511);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _msg_and_obj$308, (PyObject)null);
      var1.setlocal("_msg_and_obj", var4);
      var3 = null;
      var1.setline(3520);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _signed_parts_eq$309, (PyObject)null);
      var1.setlocal("_signed_parts_eq", var4);
      var3 = null;
      var1.setline(3528);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_headers_as_string$310, (PyObject)null);
      var1.setlocal("test_long_headers_as_string", var4);
      var3 = null;
      var1.setline(3533);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_headers_flatten$311, (PyObject)null);
      var1.setlocal("test_long_headers_flatten", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _msg_and_obj$308(PyFrame var1, ThreadState var2) {
      var1.setline(3512);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getglobal("findfile").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(3514);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(3515);
         var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(3517);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(3517);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(3518);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _signed_parts_eq$309(PyFrame var1, ThreadState var2) {
      var1.setline(3522);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3523);
      var3 = var1.getlocal(3).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^--([^\\n]+)\\n(.*?)\\n--\\1$"), (PyObject)var1.getlocal(3).__getattr__("S")._or(var1.getlocal(3).__getattr__("M")));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3524);
      var3 = var1.getlocal(4).__getattr__("search").__call__(var2, var1.getlocal(1)).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(3525);
      var3 = var1.getlocal(4).__getattr__("search").__call__(var2, var1.getlocal(2)).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(3526);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_headers_as_string$310(PyFrame var1, ThreadState var2) {
      var1.setline(3529);
      PyObject var3 = var1.getlocal(0).__getattr__("_msg_and_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_45.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(3530);
      var3 = var1.getlocal(2).__getattr__("as_string").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3531);
      var1.getlocal(0).__getattr__("_signed_parts_eq").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_headers_flatten$311(PyFrame var1, ThreadState var2) {
      var1.setline(3534);
      PyObject var3 = var1.getlocal(0).__getattr__("_msg_and_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_45.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(3535);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3536);
      var1.getglobal("Generator").__call__(var2, var1.getlocal(3)).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(3537);
      var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(3538);
      var1.getlocal(0).__getattr__("_signed_parts_eq").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _testclasses$312(PyFrame var1, ThreadState var2) {
      var1.setline(3543);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getglobal("__name__"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(3544);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3544);
      var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(3544);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(3544);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(3544);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test")).__nonzero__()) {
            var1.setline(3544);
            var1.getlocal(1).__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)));
         }
      }
   }

   public PyObject suite$313(PyFrame var1, ThreadState var2) {
      var1.setline(3548);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(3549);
      var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(3549);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(3551);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(3550);
         var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_main$314(PyFrame var1, ThreadState var2) {
      var1.setline(3555);
      PyObject var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(3555);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(3556);
         var1.getglobal("run_unittest").__call__(var2, var1.getlocal(0));
      }
   }

   public test_email$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "mode", "path"};
      openfile$1 = Py.newCode(2, var2, var1, "openfile", 45, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestEmailBase$2 = Py.newCode(0, var2, var1, "TestEmailBase", 52, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "first", "second", "sfirst", "ssecond", "diff", "fp"};
      ndiffAssertEqual$3 = Py.newCode(3, var2, var1, "ndiffAssertEqual", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "fp", "msg"};
      _msgobj$4 = Py.newCode(2, var2, var1, "_msgobj", 63, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMessageAPI$5 = Py.newCode(0, var2, var1, "TestMessageAPI", 74, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_all$6 = Py.newCode(1, var2, var1, "test_get_all", 75, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charset"};
      test_getset_charset$7 = Py.newCode(1, var2, var1, "test_getset_charset", 81, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_charset_from_string$8 = Py.newCode(1, var2, var1, "test_set_charset_from_string", 107, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "charset"};
      test_set_payload_with_charset$9 = Py.newCode(1, var2, var1, "test_set_payload_with_charset", 114, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charsets"};
      test_get_charsets$10 = Py.newCode(1, var2, var1, "test_get_charsets", 120, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "filenames", "_[141_21]", "p", "subpart"};
      test_get_filename$11 = Py.newCode(1, var2, var1, "test_get_filename", 137, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "filenames", "_[152_21]", "p"};
      test_get_filename_with_name_parameter$12 = Py.newCode(1, var2, var1, "test_get_filename_with_name_parameter", 148, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_boundary$13 = Py.newCode(1, var2, var1, "test_get_boundary", 155, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "header", "value"};
      test_set_boundary$14 = Py.newCode(1, var2, var1, "test_set_boundary", 161, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_make_boundary$15 = Py.newCode(1, var2, var1, "test_make_boundary", 183, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "msgdata", "parser", "msg", "out", "gen"};
      test_message_rfc822_only$16 = Py.newCode(1, var2, var1, "test_message_rfc822_only", 194, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_decoded_payload$17 = Py.newCode(1, var2, var1, "test_get_decoded_payload", 206, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "cte"};
      test_get_decoded_uu_payload$18 = Py.newCode(1, var2, var1, "test_get_decoded_uu_payload", 228, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "old_stderr", "sfp"};
      test_decode_bogus_uu_payload_quietly$19 = Py.newCode(1, var2, var1, "test_decode_bogus_uu_payload_quietly", 239, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "fp", "text", "s", "g"};
      test_decoded_generator$20 = Py.newCode(1, var2, var1, "test_decoded_generator", 252, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test__contains__$21 = Py.newCode(1, var2, var1, "test__contains__", 265, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "fp", "text", "fullrepr", "lines"};
      test_as_string$22 = Py.newCode(1, var2, var1, "test_as_string", 277, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_bad_param$23 = Py.newCode(1, var2, var1, "test_bad_param", 300, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_missing_filename$24 = Py.newCode(1, var2, var1, "test_missing_filename", 304, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_bogus_filename$25 = Py.newCode(1, var2, var1, "test_bogus_filename", 308, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_missing_boundary$26 = Py.newCode(1, var2, var1, "test_missing_boundary", 313, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_params$27 = Py.newCode(1, var2, var1, "test_get_params", 317, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_liberal$28 = Py.newCode(1, var2, var1, "test_get_param_liberal", 333, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_param$29 = Py.newCode(1, var2, var1, "test_get_param", 338, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_funky_continuation_lines$30 = Py.newCode(1, var2, var1, "test_get_param_funky_continuation_lines", 355, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_with_semis_in_quotes$31 = Py.newCode(1, var2, var1, "test_get_param_with_semis_in_quotes", 359, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_with_quotes$32 = Py.newCode(1, var2, var1, "test_get_param_with_quotes", 366, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_has_key$33 = Py.newCode(1, var2, var1, "test_has_key", 374, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_param$34 = Py.newCode(1, var2, var1, "test_set_param", 381, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "old_val"};
      test_del_param$35 = Py.newCode(1, var2, var1, "test_del_param", 398, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_del_param_on_other_header$36 = Py.newCode(1, var2, var1, "test_del_param_on_other_header", 415, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_type$37 = Py.newCode(1, var2, var1, "test_set_type", 421, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_set_type_on_other_header$38 = Py.newCode(1, var2, var1, "test_set_type_on_other_header", 432, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_missing$39 = Py.newCode(1, var2, var1, "test_get_content_type_missing", 438, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_missing_with_default_type$40 = Py.newCode(1, var2, var1, "test_get_content_type_missing_with_default_type", 442, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_implicit$41 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_implicit", 447, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_explicit$42 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_explicit", 452, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_text_plain_implicit$43 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_text_plain_implicit", 457, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_text_plain_explicit$44 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_text_plain_explicit", 461, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_missing$45 = Py.newCode(1, var2, var1, "test_get_content_maintype_missing", 465, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_missing_with_default_type$46 = Py.newCode(1, var2, var1, "test_get_content_maintype_missing_with_default_type", 469, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_implicit$47 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_implicit", 474, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_explicit$48 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_explicit", 478, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_text_plain_implicit$49 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_text_plain_implicit", 482, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_text_plain_explicit$50 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_text_plain_explicit", 486, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_missing$51 = Py.newCode(1, var2, var1, "test_get_content_subtype_missing", 490, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_missing_with_default_type$52 = Py.newCode(1, var2, var1, "test_get_content_subtype_missing_with_default_type", 494, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_implicit$53 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_implicit", 499, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_explicit$54 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_explicit", 503, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_text_plain_implicit$55 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_text_plain_implicit", 507, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_text_plain_explicit$56 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_text_plain_explicit", 511, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_error$57 = Py.newCode(1, var2, var1, "test_get_content_maintype_error", 515, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_error$58 = Py.newCode(1, var2, var1, "test_get_content_subtype_error", 520, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_replace_header$59 = Py.newCode(1, var2, var1, "test_replace_header", 525, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "msg"};
      test_broken_base64_payload$60 = Py.newCode(1, var2, var1, "test_broken_base64_payload", 542, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_charset$61 = Py.newCode(1, var2, var1, "test_get_content_charset", 550, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_embeded_header_via_Header_rejected$62 = Py.newCode(1, var2, var1, "test_embeded_header_via_Header_rejected", 559, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_embeded_header_via_string_rejected$63 = Py.newCode(1, var2, var1, "test_embeded_header_via_string_rejected", 564, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestEncoders$64 = Py.newCode(0, var2, var1, "TestEncoders", 571, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_encode_empty_payload$65 = Py.newCode(1, var2, var1, "test_encode_empty_payload", 572, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_default_cte$66 = Py.newCode(1, var2, var1, "test_default_cte", 578, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_encode7or8bit$67 = Py.newCode(1, var2, var1, "test_encode7or8bit", 590, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestLongHeaders$68 = Py.newCode(0, var2, var1, "TestLongHeaders", 600, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg", "sfp", "g"};
      test_split_long_continuation$69 = Py.newCode(1, var2, var1, "test_split_long_continuation", 601, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_another_long_almost_unsplittable_header$70 = Py.newCode(1, var2, var1, "test_another_long_almost_unsplittable_header", 621, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "g", "cz", "utf8", "g_head", "cz_head", "utf8_head", "h", "msg", "sfp"};
      test_long_nonstring$71 = Py.newCode(1, var2, var1, "test_long_nonstring", 638, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_long_header_encode$72 = Py.newCode(1, var2, var1, "test_long_header_encode", 681, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_long_header_encode_with_tab_continuation$73 = Py.newCode(1, var2, var1, "test_long_header_encode_with_tab_continuation", 690, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp", "g"};
      test_header_splitter$74 = Py.newCode(1, var2, var1, "test_header_splitter", 700, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "_[724_40]", "i", "sfp", "g"};
      test_no_semis_header_splitter$75 = Py.newCode(1, var2, var1, "test_no_semis_header_splitter", 720, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_no_split_long_header$76 = Py.newCode(1, var2, var1, "test_no_split_long_header", 736, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_splitting_multiple_long_lines$77 = Py.newCode(1, var2, var1, "test_splitting_multiple_long_lines", 743, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_splitting_first_line_only_is_long$78 = Py.newCode(1, var2, var1, "test_splitting_first_line_only_is_long", 765, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "h"};
      test_long_8bit_header$79 = Py.newCode(1, var2, var1, "test_long_8bit_header", 781, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_long_8bit_header_no_charset$80 = Py.newCode(1, var2, var1, "test_long_8bit_header_no_charset", 794, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "to", "msg"};
      test_long_to_header$81 = Py.newCode(1, var2, var1, "test_long_to_header", 803, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "h"};
      test_long_line_after_append$82 = Py.newCode(1, var2, var1, "test_long_line_after_append", 816, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "h"};
      test_shorter_line_with_append$83 = Py.newCode(1, var2, var1, "test_shorter_line_with_append", 825, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fn", "gs", "h"};
      test_long_field_name$84 = Py.newCode(1, var2, var1, "test_long_field_name", 833, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "msg"};
      test_long_received_header$85 = Py.newCode(1, var2, var1, "test_long_received_header", 845, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "msg"};
      test_string_headerinst_eq$86 = Py.newCode(1, var2, var1, "test_string_headerinst_eq", 860, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "t"};
      test_long_unbreakable_lines_with_continuation$87 = Py.newCode(1, var2, var1, "test_long_unbreakable_lines_with_continuation", 874, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_another_long_multiline_header$88 = Py.newCode(1, var2, var1, "test_another_long_multiline_header", 890, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h", "msg"};
      test_long_lines_with_different_header$89 = Py.newCode(1, var2, var1, "test_long_lines_with_different_header", 902, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestFromMangling$90 = Py.newCode(0, var2, var1, "TestFromMangling", 921, false, false, self, 90, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$91 = Py.newCode(1, var2, var1, "setUp", 922, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "g"};
      test_mangled_from$92 = Py.newCode(1, var2, var1, "test_mangled_from", 930, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "g"};
      test_dont_mangle_from$93 = Py.newCode(1, var2, var1, "test_dont_mangle_from", 941, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "g", "msg", "_[972_30]", "x"};
      test_mangle_from_in_preamble_and_epilog$94 = Py.newCode(1, var2, var1, "test_mangle_from_in_preamble_and_epilog", 952, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEAudio$95 = Py.newCode(0, var2, var1, "TestMIMEAudio", 977, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "datadir", "fp"};
      setUp$96 = Py.newCode(1, var2, var1, "setUp", 978, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_guess_minor_type$97 = Py.newCode(1, var2, var1, "test_guess_minor_type", 992, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload"};
      test_encoding$98 = Py.newCode(1, var2, var1, "test_encoding", 995, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "au"};
      test_checkSetMinor$99 = Py.newCode(1, var2, var1, "test_checkSetMinor", 999, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_add_header$100 = Py.newCode(1, var2, var1, "test_add_header", 1003, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEImage$101 = Py.newCode(0, var2, var1, "TestMIMEImage", 1026, false, false, self, 101, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      setUp$102 = Py.newCode(1, var2, var1, "setUp", 1027, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_guess_minor_type$103 = Py.newCode(1, var2, var1, "test_guess_minor_type", 1035, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload"};
      test_encoding$104 = Py.newCode(1, var2, var1, "test_encoding", 1038, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "im"};
      test_checkSetMinor$105 = Py.newCode(1, var2, var1, "test_checkSetMinor", 1042, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_add_header$106 = Py.newCode(1, var2, var1, "test_add_header", 1046, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEText$107 = Py.newCode(0, var2, var1, "TestMIMEText", 1069, false, false, self, 107, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$108 = Py.newCode(1, var2, var1, "setUp", 1070, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_types$109 = Py.newCode(1, var2, var1, "test_types", 1073, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_payload$110 = Py.newCode(1, var2, var1, "test_payload", 1083, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_charset$111 = Py.newCode(1, var2, var1, "test_charset", 1087, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_7bit_unicode_input$112 = Py.newCode(1, var2, var1, "test_7bit_unicode_input", 1093, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_7bit_unicode_input_no_charset$113 = Py.newCode(1, var2, var1, "test_7bit_unicode_input_no_charset", 1099, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "teststr", "eq", "msg"};
      test_8bit_unicode_input$114 = Py.newCode(1, var2, var1, "test_8bit_unicode_input", 1106, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "teststr"};
      test_8bit_unicode_input_no_charset$115 = Py.newCode(1, var2, var1, "test_8bit_unicode_input_no_charset", 1114, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMultipart$116 = Py.newCode(0, var2, var1, "TestMultipart", 1121, false, false, self, 116, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "data", "container", "image", "intro", "now", "timetuple", "tzsecs", "sign", "tzoffset"};
      setUp$117 = Py.newCode(1, var2, var1, "setUp", 1122, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "raises", "m", "m0", "m1"};
      test_hierarchy$118 = Py.newCode(1, var2, var1, "test_hierarchy", 1162, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "msg"};
      test_empty_multipart_idempotent$119 = Py.newCode(1, var2, var1, "test_empty_multipart_idempotent", 1181, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer"};
      test_no_parts_in_a_multipart_with_none_epilogue$120 = Py.newCode(1, var2, var1, "test_no_parts_in_a_multipart_with_none_epilogue", 1198, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer"};
      test_no_parts_in_a_multipart_with_empty_epilogue$121 = Py.newCode(1, var2, var1, "test_no_parts_in_a_multipart_with_empty_epilogue", 1215, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_one_part_in_a_multipart$122 = Py.newCode(1, var2, var1, "test_one_part_in_a_multipart", 1236, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_empty_preamble$123 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_empty_preamble", 1260, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_none_preamble$124 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_none_preamble", 1287, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_none_epilogue$125 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_none_epilogue", 1313, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_empty_epilogue$126 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_empty_epilogue", 1339, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_nl_epilogue$127 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_nl_epilogue", 1366, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "msg1", "subpart", "subsubpart"};
      test_message_external_body$128 = Py.newCode(1, var2, var1, "test_message_external_body", 1393, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_double_boundary$129 = Py.newCode(1, var2, var1, "test_double_boundary", 1406, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_nested_inner_contains_outer_boundary$130 = Py.newCode(1, var2, var1, "test_nested_inner_contains_outer_boundary", 1413, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_nested_with_same_boundary$131 = Py.newCode(1, var2, var1, "test_nested_with_same_boundary", 1432, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_boundary_in_non_multipart$132 = Py.newCode(1, var2, var1, "test_boundary_in_non_multipart", 1449, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_boundary_with_leading_space$133 = Py.newCode(1, var2, var1, "test_boundary_with_leading_space", 1464, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m"};
      test_boundary_without_trailing_newline$134 = Py.newCode(1, var2, var1, "test_boundary_without_trailing_newline", 1483, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestNonConformant$135 = Py.newCode(0, var2, var1, "TestNonConformant", 1500, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_parse_missing_minor_type$136 = Py.newCode(1, var2, var1, "test_parse_missing_minor_type", 1501, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg", "inner"};
      test_same_boundary_inner_outer$137 = Py.newCode(1, var2, var1, "test_same_boundary_inner_outer", 1508, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg"};
      test_multipart_no_boundary$138 = Py.newCode(1, var2, var1, "test_multipart_no_boundary", 1518, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "msg", "s", "g"};
      test_invalid_content_type$139 = Py.newCode(1, var2, var1, "test_invalid_content_type", 1527, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_no_start_boundary$140 = Py.newCode(1, var2, var1, "test_no_start_boundary", 1548, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_no_separating_blank_line$141 = Py.newCode(1, var2, var1, "test_no_separating_blank_line", 1565, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg"};
      test_lying_multipart$142 = Py.newCode(1, var2, var1, "test_lying_multipart", 1576, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer", "bad"};
      test_missing_start_boundary$143 = Py.newCode(1, var2, var1, "test_missing_start_boundary", 1585, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_first_line_is_continuation_header$144 = Py.newCode(1, var2, var1, "test_first_line_is_continuation_header", 1600, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestRFC2047$145 = Py.newCode(0, var2, var1, "TestRFC2047", 1615, false, false, self, 145, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "s", "dh"};
      test_rfc2047_multiline$146 = Py.newCode(1, var2, var1, "test_rfc2047_multiline", 1616, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "dh", "hu"};
      test_whitespace_eater_unicode$147 = Py.newCode(1, var2, var1, "test_whitespace_eater_unicode", 1630, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "dh", "hu"};
      test_whitespace_eater_unicode_2$148 = Py.newCode(1, var2, var1, "test_whitespace_eater_unicode_2", 1638, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "dh"};
      test_rfc2047_without_whitespace$149 = Py.newCode(1, var2, var1, "test_rfc2047_without_whitespace", 1647, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "dh"};
      test_rfc2047_with_whitespace$150 = Py.newCode(1, var2, var1, "test_rfc2047_with_whitespace", 1652, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "data", "q", "a", "dh"};
      test_rfc2047_B_bad_padding$151 = Py.newCode(1, var2, var1, "test_rfc2047_B_bad_padding", 1659, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      test_rfc2047_Q_invalid_digits$152 = Py.newCode(1, var2, var1, "test_rfc2047_Q_invalid_digits", 1669, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEMessage$153 = Py.newCode(0, var2, var1, "TestMIMEMessage", 1677, false, false, self, 153, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      setUp$154 = Py.newCode(1, var2, var1, "setUp", 1678, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_type_error$155 = Py.newCode(1, var2, var1, "test_type_error", 1685, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "subject", "m", "r", "payload", "subpart"};
      test_valid_argument$156 = Py.newCode(1, var2, var1, "test_valid_argument", 1688, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg1", "msg2", "r"};
      test_bad_multipart$157 = Py.newCode(1, var2, var1, "test_bad_multipart", 1703, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "r", "s", "g"};
      test_generate$158 = Py.newCode(1, var2, var1, "test_generate", 1712, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "payload", "submsg"};
      test_parse_message_rfc822$159 = Py.newCode(1, var2, var1, "test_parse_message_rfc822", 1732, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "subpart", "dsn1", "dsn2", "payload", "subsubpart"};
      test_dsn$160 = Py.newCode(1, var2, var1, "test_dsn", 1745, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "text", "msg", "msg1", "msg2", "sfp", "g"};
      test_epilogue$161 = Py.newCode(1, var2, var1, "test_epilogue", 1803, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "msg1", "msg2"};
      test_no_nl_preamble$162 = Py.newCode(1, var2, var1, "test_no_nl_preamble", 1826, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "container1", "container2", "container1a", "container2a"};
      test_default_type$163 = Py.newCode(1, var2, var1, "test_default_type", 1861, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "container1", "container2", "container1a", "container2a"};
      test_default_type_with_explicit_container_type$164 = Py.newCode(1, var2, var1, "test_default_type_with_explicit_container_type", 1881, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "container", "subpart1a", "subpart2a", "subpart1", "subpart2"};
      test_default_type_non_parsed$165 = Py.newCode(1, var2, var1, "test_default_type_non_parsed", 1901, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "text1", "text2", "msg"};
      test_mime_attachments_in_constructor$166 = Py.newCode(1, var2, var1, "test_mime_attachments_in_constructor", 1975, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_default_multipart_constructor$167 = Py.newCode(1, var2, var1, "test_default_multipart_constructor", 1984, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestIdempotent$168 = Py.newCode(0, var2, var1, "TestIdempotent", 1994, false, false, self, 168, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "fp", "data", "msg"};
      _msgobj$169 = Py.newCode(2, var2, var1, "_msgobj", 1995, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text", "eq", "s", "g"};
      _idempotent$170 = Py.newCode(3, var2, var1, "_idempotent", 2004, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "text"};
      test_parse_text_message$171 = Py.newCode(1, var2, var1, "test_parse_text_message", 2011, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "text"};
      test_parse_untyped_message$172 = Py.newCode(1, var2, var1, "test_parse_untyped_message", 2023, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_simple_multipart$173 = Py.newCode(1, var2, var1, "test_simple_multipart", 2031, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_MIME_digest$174 = Py.newCode(1, var2, var1, "test_MIME_digest", 2035, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_long_header$175 = Py.newCode(1, var2, var1, "test_long_header", 2039, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_MIME_digest_with_part_headers$176 = Py.newCode(1, var2, var1, "test_MIME_digest_with_part_headers", 2043, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_mixed_with_image$177 = Py.newCode(1, var2, var1, "test_mixed_with_image", 2047, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_report$178 = Py.newCode(1, var2, var1, "test_multipart_report", 2051, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_dsn$179 = Py.newCode(1, var2, var1, "test_dsn", 2055, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_preamble_epilogue$180 = Py.newCode(1, var2, var1, "test_preamble_epilogue", 2059, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_one_part$181 = Py.newCode(1, var2, var1, "test_multipart_one_part", 2063, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_no_parts$182 = Py.newCode(1, var2, var1, "test_multipart_no_parts", 2067, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_no_start_boundary$183 = Py.newCode(1, var2, var1, "test_no_start_boundary", 2071, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_rfc2231_charset$184 = Py.newCode(1, var2, var1, "test_rfc2231_charset", 2075, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_more_rfc2231_parameters$185 = Py.newCode(1, var2, var1, "test_more_rfc2231_parameters", 2079, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_text_plain_in_a_multipart_digest$186 = Py.newCode(1, var2, var1, "test_text_plain_in_a_multipart_digest", 2083, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_nested_multipart_mixeds$187 = Py.newCode(1, var2, var1, "test_nested_multipart_mixeds", 2087, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_message_external_body_idempotent$188 = Py.newCode(1, var2, var1, "test_message_external_body_idempotent", 2091, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "text", "params", "pk", "pv", "msg1", "msg2", "msg3", "payload", "msg4"};
      test_content_type$189 = Py.newCode(1, var2, var1, "test_content_type", 2095, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "text", "payload", "msg1"};
      test_parser$190 = Py.newCode(1, var2, var1, "test_parser", 2127, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMiscellaneous$191 = Py.newCode(0, var2, var1, "TestMiscellaneous", 2147, false, false, self, 191, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "text", "msg", "s", "g"};
      test_message_from_string$192 = Py.newCode(1, var2, var1, "test_message_from_string", 2148, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "text", "msg", "s", "g"};
      test_message_from_file$193 = Py.newCode(1, var2, var1, "test_message_from_file", 2162, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "fp", "text", "MyMessage", "msg", "subpart"};
      test_message_from_string_with_class$194 = Py.newCode(1, var2, var1, "test_message_from_string_with_class", 2177, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyMessage$195 = Py.newCode(0, var2, var1, "MyMessage", 2185, false, false, self, 195, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "unless", "MyMessage", "fp", "msg", "subpart"};
      test_message_from_file_with_class$196 = Py.newCode(1, var2, var1, "test_message_from_file_with_class", 2200, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyMessage$197 = Py.newCode(0, var2, var1, "MyMessage", 2203, false, false, self, 197, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "all"};
      test__all__$198 = Py.newCode(1, var2, var1, "test__all__", 2221, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate$199 = Py.newCode(1, var2, var1, "test_formatdate", 2239, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate_localtime$200 = Py.newCode(1, var2, var1, "test_formatdate_localtime", 2244, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate_usegmt$201 = Py.newCode(1, var2, var1, "test_formatdate_usegmt", 2250, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parsedate_none$202 = Py.newCode(1, var2, var1, "test_parsedate_none", 2259, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parsedate_compact$203 = Py.newCode(1, var2, var1, "test_parsedate_compact", 2262, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_parsedate_no_dayofweek$204 = Py.newCode(1, var2, var1, "test_parsedate_no_dayofweek", 2267, false, false, self, 204, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_parsedate_compact_no_dayofweek$205 = Py.newCode(1, var2, var1, "test_parsedate_compact_no_dayofweek", 2272, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "timetup", "t"};
      test_parsedate_acceptable_to_time_functions$206 = Py.newCode(1, var2, var1, "test_parsedate_acceptable_to_time_functions", 2277, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_mktime_tz$207 = Py.newCode(1, var2, var1, "test_mktime_tz", 2288, false, false, self, 207, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parsedate_y2k$208 = Py.newCode(1, var2, var1, "test_parsedate_y2k", 2294, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parseaddr_empty$209 = Py.newCode(1, var2, var1, "test_parseaddr_empty", 2307, false, false, self, 209, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_noquote_dump$210 = Py.newCode(1, var2, var1, "test_noquote_dump", 2311, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      test_escape_dump$211 = Py.newCode(1, var2, var1, "test_escape_dump", 2316, false, false, self, 211, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      test_escape_backslashes$212 = Py.newCode(1, var2, var1, "test_escape_backslashes", 2324, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "y", "a", "b"};
      test_name_with_dot$213 = Py.newCode(1, var2, var1, "test_name_with_dot", 2332, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_parseaddr_preserves_quoted_pairs_in_addresses$214 = Py.newCode(1, var2, var1, "test_parseaddr_preserves_quoted_pairs_in_addresses", 2341, false, false, self, 214, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      test_multiline_from_comment$215 = Py.newCode(1, var2, var1, "test_multiline_from_comment", 2359, false, false, self, 215, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_quote_dump$216 = Py.newCode(1, var2, var1, "test_quote_dump", 2365, false, false, self, 216, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_fix_eols$217 = Py.newCode(1, var2, var1, "test_fix_eols", 2370, false, false, self, 217, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "ne", "cset1", "cset2"};
      test_charset_richcomparisons$218 = Py.newCode(1, var2, var1, "test_charset_richcomparisons", 2378, false, false, self, 218, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_getaddresses$219 = Py.newCode(1, var2, var1, "test_getaddresses", 2398, false, false, self, 219, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_getaddresses_nasty$220 = Py.newCode(1, var2, var1, "test_getaddresses_nasty", 2405, false, false, self, 220, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "addrs"};
      test_getaddresses_embedded_comment$221 = Py.newCode(1, var2, var1, "test_getaddresses_embedded_comment", 2415, false, false, self, 221, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_utils_quote_unquote$222 = Py.newCode(1, var2, var1, "test_utils_quote_unquote", 2421, false, false, self, 222, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      test_get_body_encoding_with_bogus_charset$223 = Py.newCode(1, var2, var1, "test_get_body_encoding_with_bogus_charset", 2428, false, false, self, 223, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charsets", "charset"};
      test_get_body_encoding_with_uppercase_charset$224 = Py.newCode(1, var2, var1, "test_get_body_encoding_with_uppercase_charset", 2432, false, false, self, 224, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lc", "uc"};
      test_charsets_case_insensitive$225 = Py.newCode(1, var2, var1, "test_charsets_case_insensitive", 2458, false, false, self, 225, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_partial_falls_inside_message_delivery_status$226 = Py.newCode(1, var2, var1, "test_partial_falls_inside_message_delivery_status", 2463, false, false, self, 226, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestIterators$227 = Py.newCode(0, var2, var1, "TestIterators", 2508, false, false, self, 227, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "neq", "msg", "it", "lines", "fp"};
      test_body_line_iterator$228 = Py.newCode(1, var2, var1, "test_body_line_iterator", 2509, false, false, self, 228, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "it", "lines", "subparts", "subpart"};
      test_typed_subpart_iterator$229 = Py.newCode(1, var2, var1, "test_typed_subpart_iterator", 2529, false, false, self, 229, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "it", "lines", "subparts", "subpart"};
      test_typed_subpart_iterator_default_type$230 = Py.newCode(1, var2, var1, "test_typed_subpart_iterator_default_type", 2546, false, false, self, 230, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "imt", "BufferedSubFile", "NeedMoreData", "bsf", "om", "nt", "il", "n", "n1", "ol", "_[2596_33]"};
      test_pushCR_LF$231 = Py.newCode(1, var2, var1, "test_pushCR_LF", 2565, false, false, self, 231, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestParsers$232 = Py.newCode(0, var2, var1, "TestParsers", 2600, false, false, self, 232, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "fp", "msg"};
      test_header_parser$233 = Py.newCode(1, var2, var1, "test_header_parser", 2601, false, false, self, 233, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_whitespace_continuation$234 = Py.newCode(1, var2, var1, "test_whitespace_continuation", 2615, false, false, self, 234, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_whitespace_continuation_last_header$235 = Py.newCode(1, var2, var1, "test_whitespace_continuation_last_header", 2633, false, false, self, 235, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "part1", "part2"};
      test_crlf_separation$236 = Py.newCode(1, var2, var1, "test_crlf_separation", 2651, false, false, self, 236, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "fp", "msg", "part1", "part1a", "part2", "part2a"};
      test_multipart_digest_with_extra_mime_headers$237 = Py.newCode(1, var2, var1, "test_multipart_digest_with_extra_mime_headers", 2665, false, false, self, 237, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "msg"};
      test_three_lines$238 = Py.newCode(1, var2, var1, "test_three_lines", 2699, false, false, self, 238, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "value1", "value2", "m", "msg"};
      test_strip_line_feed_and_carriage_return_in_headers$239 = Py.newCode(1, var2, var1, "test_strip_line_feed_and_carriage_return_in_headers", 2707, false, false, self, 239, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "keys"};
      test_rfc2822_header_syntax$240 = Py.newCode(1, var2, var1, "test_rfc2822_header_syntax", 2718, false, false, self, 240, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_rfc2822_space_not_allowed_in_header$241 = Py.newCode(1, var2, var1, "test_rfc2822_space_not_allowed_in_header", 2728, false, false, self, 241, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "headers"};
      test_rfc2822_one_character_header$242 = Py.newCode(1, var2, var1, "test_rfc2822_one_character_header", 2734, false, false, self, 242, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_CRLFLF_at_end_of_part$243 = Py.newCode(1, var2, var1, "test_CRLFLF_at_end_of_part", 2743, false, false, self, 243, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestBase64$244 = Py.newCode(0, var2, var1, "TestBase64", 2763, false, false, self, 244, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "size", "bsize"};
      test_len$245 = Py.newCode(1, var2, var1, "test_len", 2764, false, false, self, 245, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_decode$246 = Py.newCode(1, var2, var1, "test_decode", 2777, false, false, self, 246, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_encode$247 = Py.newCode(1, var2, var1, "test_encode", 2784, false, false, self, 247, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "he"};
      test_header_encode$248 = Py.newCode(1, var2, var1, "test_header_encode", 2806, false, false, self, 248, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestQuopri$249 = Py.newCode(0, var2, var1, "TestQuopri", 2835, false, false, self, 249, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_[2837_21]", "x", "_[2838_21]", "_[2839_21]", "_[2841_21]", "_[2843_21]", "_[2845_21]"};
      setUp$250 = Py.newCode(1, var2, var1, "setUp", 2836, false, false, self, 250, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      test_header_quopri_check$251 = Py.newCode(1, var2, var1, "test_header_quopri_check", 2848, false, false, self, 251, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      test_body_quopri_check$252 = Py.newCode(1, var2, var1, "test_body_quopri_check", 2854, false, false, self, 252, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hql", "enc", "s", "c"};
      test_header_quopri_len$253 = Py.newCode(1, var2, var1, "test_header_quopri_len", 2860, false, false, self, 253, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "bql", "c"};
      test_body_quopri_len$254 = Py.newCode(1, var2, var1, "test_body_quopri_len", 2872, false, false, self, 254, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "c"};
      test_quote_unquote_idempotent$255 = Py.newCode(1, var2, var1, "test_quote_unquote_idempotent", 2880, false, false, self, 255, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "he"};
      test_header_encode$256 = Py.newCode(1, var2, var1, "test_header_encode", 2885, false, false, self, 256, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_decode$257 = Py.newCode(1, var2, var1, "test_decode", 2911, false, false, self, 257, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_encode$258 = Py.newCode(1, var2, var1, "test_encode", 2918, false, false, self, 258, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCharset$259 = Py.newCode(0, var2, var1, "TestCharset", 2946, false, false, self, 259, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "CharsetModule"};
      tearDown$260 = Py.newCode(1, var2, var1, "tearDown", 2947, false, false, self, 260, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "c", "s", "sp"};
      test_idempotent$261 = Py.newCode(1, var2, var1, "test_idempotent", 2954, false, false, self, 261, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "c", "CharsetModule"};
      test_body_encode$262 = Py.newCode(1, var2, var1, "test_body_encode", 2966, false, false, self, 262, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      test_unicode_charset_name$263 = Py.newCode(1, var2, var1, "test_unicode_charset_name", 2999, false, false, self, 263, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      test_codecs_aliases_accepted$264 = Py.newCode(1, var2, var1, "test_codecs_aliases_accepted", 3004, false, false, self, 264, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestHeader$265 = Py.newCode(0, var2, var1, "TestHeader", 3010, false, false, self, 265, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "h"};
      test_simple$266 = Py.newCode(1, var2, var1, "test_simple", 3011, false, false, self, 266, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_simple_surprise$267 = Py.newCode(1, var2, var1, "test_simple_surprise", 3018, false, false, self, 267, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h"};
      test_header_needs_no_decoding$268 = Py.newCode(1, var2, var1, "test_header_needs_no_decoding", 3025, false, false, self, 268, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "l"};
      test_long$269 = Py.newCode(1, var2, var1, "test_long", 3029, false, false, self, 269, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "g", "cz", "utf8", "g_head", "cz_head", "utf8_head", "h", "enc", "ustr", "newh"};
      test_multilingual$270 = Py.newCode(1, var2, var1, "test_multilingual", 3035, false, false, self, 270, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_header_ctor_default_args$271 = Py.newCode(1, var2, var1, "test_header_ctor_default_args", 3084, false, false, self, 271, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_explicit_maxlinelen$272 = Py.newCode(1, var2, var1, "test_explicit_maxlinelen", 3091, false, false, self, 272, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "x", "h"};
      test_us_ascii_header$273 = Py.newCode(1, var2, var1, "test_us_ascii_header", 3105, false, false, self, 273, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_string_charset$274 = Py.newCode(1, var2, var1, "test_string_charset", 3113, false, false, self, 274, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_utf8_shortest$275 = Py.newCode(1, var2, var1, "test_utf8_shortest", 3128, false, false, self, 275, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raises", "eq", "x", "h"};
      test_bad_8bit_header$276 = Py.newCode(1, var2, var1, "test_bad_8bit_header", 3135, false, false, self, 276, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h", "s"};
      test_encoded_adjacent_nonencoded$277 = Py.newCode(1, var2, var1, "test_encoded_adjacent_nonencoded", 3146, false, false, self, 277, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "parts", "hdr"};
      test_whitespace_eater$278 = Py.newCode(1, var2, var1, "test_whitespace_eater", 3156, false, false, self, 278, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raises", "s"};
      test_broken_base64_header$279 = Py.newCode(1, var2, var1, "test_broken_base64_header", 3165, false, false, self, 279, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_ascii_add_header$280 = Py.newCode(1, var2, var1, "test_ascii_add_header", 3171, false, false, self, 280, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_nonascii_add_header_via_triple$281 = Py.newCode(1, var2, var1, "test_nonascii_add_header_via_triple", 3178, false, false, self, 281, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "res"};
      test_encode_unaliased_charset$282 = Py.newCode(1, var2, var1, "test_encode_unaliased_charset", 3186, false, false, self, 282, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestRFC2231$283 = Py.newCode(0, var2, var1, "TestRFC2231", 3195, false, false, self, 283, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_param$284 = Py.newCode(1, var2, var1, "test_get_param", 3196, false, false, self, 284, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_param$285 = Py.newCode(1, var2, var1, "test_set_param", 3204, false, false, self, 285, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_del_param$286 = Py.newCode(1, var2, var1, "test_del_param", 3241, false, false, self, 286, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_rfc2231_get_content_charset$287 = Py.newCode(1, var2, var1, "test_rfc2231_get_content_charset", 3271, false, false, self, 287, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_no_language_or_charset$288 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset", 3276, false, false, self, 288, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_filename$289 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_filename", 3290, false, false, self, 289, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_filename_encoded$290 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_filename_encoded", 3302, false, false, self, 290, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_partly_encoded$291 = Py.newCode(1, var2, var1, "test_rfc2231_partly_encoded", 3314, false, false, self, 291, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_partly_nonencoded$292 = Py.newCode(1, var2, var1, "test_rfc2231_partly_nonencoded", 3327, false, false, self, 292, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_boundary$293 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_boundary", 3340, false, false, self, 293, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_charset$294 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_charset", 3352, false, false, self, 294, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_encoding_in_filename$295 = Py.newCode(1, var2, var1, "test_rfc2231_bad_encoding_in_filename", 3365, false, false, self, 295, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_encoding_in_charset$296 = Py.newCode(1, var2, var1, "test_rfc2231_bad_encoding_in_charset", 3377, false, false, self, 296, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_character_in_charset$297 = Py.newCode(1, var2, var1, "test_rfc2231_bad_character_in_charset", 3387, false, false, self, 297, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_character_in_filename$298 = Py.newCode(1, var2, var1, "test_rfc2231_bad_character_in_filename", 3397, false, false, self, 298, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_unknown_encoding$299 = Py.newCode(1, var2, var1, "test_rfc2231_unknown_encoding", 3409, false, false, self, 299, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_single_tick_in_filename_extended$300 = Py.newCode(1, var2, var1, "test_rfc2231_single_tick_in_filename_extended", 3418, false, false, self, 300, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_single_tick_in_filename$301 = Py.newCode(1, var2, var1, "test_rfc2231_single_tick_in_filename", 3431, false, false, self, 301, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_tick_attack_extended$302 = Py.newCode(1, var2, var1, "test_rfc2231_tick_attack_extended", 3441, false, false, self, 302, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_tick_attack$303 = Py.newCode(1, var2, var1, "test_rfc2231_tick_attack", 3454, false, false, self, 303, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_rfc2231_no_extended_values$304 = Py.newCode(1, var2, var1, "test_rfc2231_no_extended_values", 3465, false, false, self, 304, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_encoded_then_unencoded_segments$305 = Py.newCode(1, var2, var1, "test_rfc2231_encoded_then_unencoded_segments", 3474, false, false, self, 305, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_unencoded_then_encoded_segments$306 = Py.newCode(1, var2, var1, "test_rfc2231_unencoded_then_encoded_segments", 3489, false, false, self, 306, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestSigned$307 = Py.newCode(0, var2, var1, "TestSigned", 3509, false, false, self, 307, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "fp", "original", "msg"};
      _msg_and_obj$308 = Py.newCode(2, var2, var1, "_msg_and_obj", 3511, false, false, self, 308, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "original", "result", "re", "repart", "inpart", "outpart"};
      _signed_parts_eq$309 = Py.newCode(3, var2, var1, "_signed_parts_eq", 3520, false, false, self, 309, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "original", "msg", "result"};
      test_long_headers_as_string$310 = Py.newCode(1, var2, var1, "test_long_headers_as_string", 3528, false, false, self, 310, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "original", "msg", "fp", "result"};
      test_long_headers_flatten$311 = Py.newCode(1, var2, var1, "test_long_headers_flatten", 3533, false, false, self, 311, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod", "_[3544_12]", "name"};
      _testclasses$312 = Py.newCode(0, var2, var1, "_testclasses", 3542, false, false, self, 312, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "testclass"};
      suite$313 = Py.newCode(0, var2, var1, "suite", 3547, false, false, self, 313, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testclass"};
      test_main$314 = Py.newCode(0, var2, var1, "test_main", 3554, false, false, self, 314, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_email$py("email/test/test_email$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_email$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.openfile$1(var2, var3);
         case 2:
            return this.TestEmailBase$2(var2, var3);
         case 3:
            return this.ndiffAssertEqual$3(var2, var3);
         case 4:
            return this._msgobj$4(var2, var3);
         case 5:
            return this.TestMessageAPI$5(var2, var3);
         case 6:
            return this.test_get_all$6(var2, var3);
         case 7:
            return this.test_getset_charset$7(var2, var3);
         case 8:
            return this.test_set_charset_from_string$8(var2, var3);
         case 9:
            return this.test_set_payload_with_charset$9(var2, var3);
         case 10:
            return this.test_get_charsets$10(var2, var3);
         case 11:
            return this.test_get_filename$11(var2, var3);
         case 12:
            return this.test_get_filename_with_name_parameter$12(var2, var3);
         case 13:
            return this.test_get_boundary$13(var2, var3);
         case 14:
            return this.test_set_boundary$14(var2, var3);
         case 15:
            return this.test_make_boundary$15(var2, var3);
         case 16:
            return this.test_message_rfc822_only$16(var2, var3);
         case 17:
            return this.test_get_decoded_payload$17(var2, var3);
         case 18:
            return this.test_get_decoded_uu_payload$18(var2, var3);
         case 19:
            return this.test_decode_bogus_uu_payload_quietly$19(var2, var3);
         case 20:
            return this.test_decoded_generator$20(var2, var3);
         case 21:
            return this.test__contains__$21(var2, var3);
         case 22:
            return this.test_as_string$22(var2, var3);
         case 23:
            return this.test_bad_param$23(var2, var3);
         case 24:
            return this.test_missing_filename$24(var2, var3);
         case 25:
            return this.test_bogus_filename$25(var2, var3);
         case 26:
            return this.test_missing_boundary$26(var2, var3);
         case 27:
            return this.test_get_params$27(var2, var3);
         case 28:
            return this.test_get_param_liberal$28(var2, var3);
         case 29:
            return this.test_get_param$29(var2, var3);
         case 30:
            return this.test_get_param_funky_continuation_lines$30(var2, var3);
         case 31:
            return this.test_get_param_with_semis_in_quotes$31(var2, var3);
         case 32:
            return this.test_get_param_with_quotes$32(var2, var3);
         case 33:
            return this.test_has_key$33(var2, var3);
         case 34:
            return this.test_set_param$34(var2, var3);
         case 35:
            return this.test_del_param$35(var2, var3);
         case 36:
            return this.test_del_param_on_other_header$36(var2, var3);
         case 37:
            return this.test_set_type$37(var2, var3);
         case 38:
            return this.test_set_type_on_other_header$38(var2, var3);
         case 39:
            return this.test_get_content_type_missing$39(var2, var3);
         case 40:
            return this.test_get_content_type_missing_with_default_type$40(var2, var3);
         case 41:
            return this.test_get_content_type_from_message_implicit$41(var2, var3);
         case 42:
            return this.test_get_content_type_from_message_explicit$42(var2, var3);
         case 43:
            return this.test_get_content_type_from_message_text_plain_implicit$43(var2, var3);
         case 44:
            return this.test_get_content_type_from_message_text_plain_explicit$44(var2, var3);
         case 45:
            return this.test_get_content_maintype_missing$45(var2, var3);
         case 46:
            return this.test_get_content_maintype_missing_with_default_type$46(var2, var3);
         case 47:
            return this.test_get_content_maintype_from_message_implicit$47(var2, var3);
         case 48:
            return this.test_get_content_maintype_from_message_explicit$48(var2, var3);
         case 49:
            return this.test_get_content_maintype_from_message_text_plain_implicit$49(var2, var3);
         case 50:
            return this.test_get_content_maintype_from_message_text_plain_explicit$50(var2, var3);
         case 51:
            return this.test_get_content_subtype_missing$51(var2, var3);
         case 52:
            return this.test_get_content_subtype_missing_with_default_type$52(var2, var3);
         case 53:
            return this.test_get_content_subtype_from_message_implicit$53(var2, var3);
         case 54:
            return this.test_get_content_subtype_from_message_explicit$54(var2, var3);
         case 55:
            return this.test_get_content_subtype_from_message_text_plain_implicit$55(var2, var3);
         case 56:
            return this.test_get_content_subtype_from_message_text_plain_explicit$56(var2, var3);
         case 57:
            return this.test_get_content_maintype_error$57(var2, var3);
         case 58:
            return this.test_get_content_subtype_error$58(var2, var3);
         case 59:
            return this.test_replace_header$59(var2, var3);
         case 60:
            return this.test_broken_base64_payload$60(var2, var3);
         case 61:
            return this.test_get_content_charset$61(var2, var3);
         case 62:
            return this.test_embeded_header_via_Header_rejected$62(var2, var3);
         case 63:
            return this.test_embeded_header_via_string_rejected$63(var2, var3);
         case 64:
            return this.TestEncoders$64(var2, var3);
         case 65:
            return this.test_encode_empty_payload$65(var2, var3);
         case 66:
            return this.test_default_cte$66(var2, var3);
         case 67:
            return this.test_encode7or8bit$67(var2, var3);
         case 68:
            return this.TestLongHeaders$68(var2, var3);
         case 69:
            return this.test_split_long_continuation$69(var2, var3);
         case 70:
            return this.test_another_long_almost_unsplittable_header$70(var2, var3);
         case 71:
            return this.test_long_nonstring$71(var2, var3);
         case 72:
            return this.test_long_header_encode$72(var2, var3);
         case 73:
            return this.test_long_header_encode_with_tab_continuation$73(var2, var3);
         case 74:
            return this.test_header_splitter$74(var2, var3);
         case 75:
            return this.test_no_semis_header_splitter$75(var2, var3);
         case 76:
            return this.test_no_split_long_header$76(var2, var3);
         case 77:
            return this.test_splitting_multiple_long_lines$77(var2, var3);
         case 78:
            return this.test_splitting_first_line_only_is_long$78(var2, var3);
         case 79:
            return this.test_long_8bit_header$79(var2, var3);
         case 80:
            return this.test_long_8bit_header_no_charset$80(var2, var3);
         case 81:
            return this.test_long_to_header$81(var2, var3);
         case 82:
            return this.test_long_line_after_append$82(var2, var3);
         case 83:
            return this.test_shorter_line_with_append$83(var2, var3);
         case 84:
            return this.test_long_field_name$84(var2, var3);
         case 85:
            return this.test_long_received_header$85(var2, var3);
         case 86:
            return this.test_string_headerinst_eq$86(var2, var3);
         case 87:
            return this.test_long_unbreakable_lines_with_continuation$87(var2, var3);
         case 88:
            return this.test_another_long_multiline_header$88(var2, var3);
         case 89:
            return this.test_long_lines_with_different_header$89(var2, var3);
         case 90:
            return this.TestFromMangling$90(var2, var3);
         case 91:
            return this.setUp$91(var2, var3);
         case 92:
            return this.test_mangled_from$92(var2, var3);
         case 93:
            return this.test_dont_mangle_from$93(var2, var3);
         case 94:
            return this.test_mangle_from_in_preamble_and_epilog$94(var2, var3);
         case 95:
            return this.TestMIMEAudio$95(var2, var3);
         case 96:
            return this.setUp$96(var2, var3);
         case 97:
            return this.test_guess_minor_type$97(var2, var3);
         case 98:
            return this.test_encoding$98(var2, var3);
         case 99:
            return this.test_checkSetMinor$99(var2, var3);
         case 100:
            return this.test_add_header$100(var2, var3);
         case 101:
            return this.TestMIMEImage$101(var2, var3);
         case 102:
            return this.setUp$102(var2, var3);
         case 103:
            return this.test_guess_minor_type$103(var2, var3);
         case 104:
            return this.test_encoding$104(var2, var3);
         case 105:
            return this.test_checkSetMinor$105(var2, var3);
         case 106:
            return this.test_add_header$106(var2, var3);
         case 107:
            return this.TestMIMEText$107(var2, var3);
         case 108:
            return this.setUp$108(var2, var3);
         case 109:
            return this.test_types$109(var2, var3);
         case 110:
            return this.test_payload$110(var2, var3);
         case 111:
            return this.test_charset$111(var2, var3);
         case 112:
            return this.test_7bit_unicode_input$112(var2, var3);
         case 113:
            return this.test_7bit_unicode_input_no_charset$113(var2, var3);
         case 114:
            return this.test_8bit_unicode_input$114(var2, var3);
         case 115:
            return this.test_8bit_unicode_input_no_charset$115(var2, var3);
         case 116:
            return this.TestMultipart$116(var2, var3);
         case 117:
            return this.setUp$117(var2, var3);
         case 118:
            return this.test_hierarchy$118(var2, var3);
         case 119:
            return this.test_empty_multipart_idempotent$119(var2, var3);
         case 120:
            return this.test_no_parts_in_a_multipart_with_none_epilogue$120(var2, var3);
         case 121:
            return this.test_no_parts_in_a_multipart_with_empty_epilogue$121(var2, var3);
         case 122:
            return this.test_one_part_in_a_multipart$122(var2, var3);
         case 123:
            return this.test_seq_parts_in_a_multipart_with_empty_preamble$123(var2, var3);
         case 124:
            return this.test_seq_parts_in_a_multipart_with_none_preamble$124(var2, var3);
         case 125:
            return this.test_seq_parts_in_a_multipart_with_none_epilogue$125(var2, var3);
         case 126:
            return this.test_seq_parts_in_a_multipart_with_empty_epilogue$126(var2, var3);
         case 127:
            return this.test_seq_parts_in_a_multipart_with_nl_epilogue$127(var2, var3);
         case 128:
            return this.test_message_external_body$128(var2, var3);
         case 129:
            return this.test_double_boundary$129(var2, var3);
         case 130:
            return this.test_nested_inner_contains_outer_boundary$130(var2, var3);
         case 131:
            return this.test_nested_with_same_boundary$131(var2, var3);
         case 132:
            return this.test_boundary_in_non_multipart$132(var2, var3);
         case 133:
            return this.test_boundary_with_leading_space$133(var2, var3);
         case 134:
            return this.test_boundary_without_trailing_newline$134(var2, var3);
         case 135:
            return this.TestNonConformant$135(var2, var3);
         case 136:
            return this.test_parse_missing_minor_type$136(var2, var3);
         case 137:
            return this.test_same_boundary_inner_outer$137(var2, var3);
         case 138:
            return this.test_multipart_no_boundary$138(var2, var3);
         case 139:
            return this.test_invalid_content_type$139(var2, var3);
         case 140:
            return this.test_no_start_boundary$140(var2, var3);
         case 141:
            return this.test_no_separating_blank_line$141(var2, var3);
         case 142:
            return this.test_lying_multipart$142(var2, var3);
         case 143:
            return this.test_missing_start_boundary$143(var2, var3);
         case 144:
            return this.test_first_line_is_continuation_header$144(var2, var3);
         case 145:
            return this.TestRFC2047$145(var2, var3);
         case 146:
            return this.test_rfc2047_multiline$146(var2, var3);
         case 147:
            return this.test_whitespace_eater_unicode$147(var2, var3);
         case 148:
            return this.test_whitespace_eater_unicode_2$148(var2, var3);
         case 149:
            return this.test_rfc2047_without_whitespace$149(var2, var3);
         case 150:
            return this.test_rfc2047_with_whitespace$150(var2, var3);
         case 151:
            return this.test_rfc2047_B_bad_padding$151(var2, var3);
         case 152:
            return this.test_rfc2047_Q_invalid_digits$152(var2, var3);
         case 153:
            return this.TestMIMEMessage$153(var2, var3);
         case 154:
            return this.setUp$154(var2, var3);
         case 155:
            return this.test_type_error$155(var2, var3);
         case 156:
            return this.test_valid_argument$156(var2, var3);
         case 157:
            return this.test_bad_multipart$157(var2, var3);
         case 158:
            return this.test_generate$158(var2, var3);
         case 159:
            return this.test_parse_message_rfc822$159(var2, var3);
         case 160:
            return this.test_dsn$160(var2, var3);
         case 161:
            return this.test_epilogue$161(var2, var3);
         case 162:
            return this.test_no_nl_preamble$162(var2, var3);
         case 163:
            return this.test_default_type$163(var2, var3);
         case 164:
            return this.test_default_type_with_explicit_container_type$164(var2, var3);
         case 165:
            return this.test_default_type_non_parsed$165(var2, var3);
         case 166:
            return this.test_mime_attachments_in_constructor$166(var2, var3);
         case 167:
            return this.test_default_multipart_constructor$167(var2, var3);
         case 168:
            return this.TestIdempotent$168(var2, var3);
         case 169:
            return this._msgobj$169(var2, var3);
         case 170:
            return this._idempotent$170(var2, var3);
         case 171:
            return this.test_parse_text_message$171(var2, var3);
         case 172:
            return this.test_parse_untyped_message$172(var2, var3);
         case 173:
            return this.test_simple_multipart$173(var2, var3);
         case 174:
            return this.test_MIME_digest$174(var2, var3);
         case 175:
            return this.test_long_header$175(var2, var3);
         case 176:
            return this.test_MIME_digest_with_part_headers$176(var2, var3);
         case 177:
            return this.test_mixed_with_image$177(var2, var3);
         case 178:
            return this.test_multipart_report$178(var2, var3);
         case 179:
            return this.test_dsn$179(var2, var3);
         case 180:
            return this.test_preamble_epilogue$180(var2, var3);
         case 181:
            return this.test_multipart_one_part$181(var2, var3);
         case 182:
            return this.test_multipart_no_parts$182(var2, var3);
         case 183:
            return this.test_no_start_boundary$183(var2, var3);
         case 184:
            return this.test_rfc2231_charset$184(var2, var3);
         case 185:
            return this.test_more_rfc2231_parameters$185(var2, var3);
         case 186:
            return this.test_text_plain_in_a_multipart_digest$186(var2, var3);
         case 187:
            return this.test_nested_multipart_mixeds$187(var2, var3);
         case 188:
            return this.test_message_external_body_idempotent$188(var2, var3);
         case 189:
            return this.test_content_type$189(var2, var3);
         case 190:
            return this.test_parser$190(var2, var3);
         case 191:
            return this.TestMiscellaneous$191(var2, var3);
         case 192:
            return this.test_message_from_string$192(var2, var3);
         case 193:
            return this.test_message_from_file$193(var2, var3);
         case 194:
            return this.test_message_from_string_with_class$194(var2, var3);
         case 195:
            return this.MyMessage$195(var2, var3);
         case 196:
            return this.test_message_from_file_with_class$196(var2, var3);
         case 197:
            return this.MyMessage$197(var2, var3);
         case 198:
            return this.test__all__$198(var2, var3);
         case 199:
            return this.test_formatdate$199(var2, var3);
         case 200:
            return this.test_formatdate_localtime$200(var2, var3);
         case 201:
            return this.test_formatdate_usegmt$201(var2, var3);
         case 202:
            return this.test_parsedate_none$202(var2, var3);
         case 203:
            return this.test_parsedate_compact$203(var2, var3);
         case 204:
            return this.test_parsedate_no_dayofweek$204(var2, var3);
         case 205:
            return this.test_parsedate_compact_no_dayofweek$205(var2, var3);
         case 206:
            return this.test_parsedate_acceptable_to_time_functions$206(var2, var3);
         case 207:
            return this.test_mktime_tz$207(var2, var3);
         case 208:
            return this.test_parsedate_y2k$208(var2, var3);
         case 209:
            return this.test_parseaddr_empty$209(var2, var3);
         case 210:
            return this.test_noquote_dump$210(var2, var3);
         case 211:
            return this.test_escape_dump$211(var2, var3);
         case 212:
            return this.test_escape_backslashes$212(var2, var3);
         case 213:
            return this.test_name_with_dot$213(var2, var3);
         case 214:
            return this.test_parseaddr_preserves_quoted_pairs_in_addresses$214(var2, var3);
         case 215:
            return this.test_multiline_from_comment$215(var2, var3);
         case 216:
            return this.test_quote_dump$216(var2, var3);
         case 217:
            return this.test_fix_eols$217(var2, var3);
         case 218:
            return this.test_charset_richcomparisons$218(var2, var3);
         case 219:
            return this.test_getaddresses$219(var2, var3);
         case 220:
            return this.test_getaddresses_nasty$220(var2, var3);
         case 221:
            return this.test_getaddresses_embedded_comment$221(var2, var3);
         case 222:
            return this.test_utils_quote_unquote$222(var2, var3);
         case 223:
            return this.test_get_body_encoding_with_bogus_charset$223(var2, var3);
         case 224:
            return this.test_get_body_encoding_with_uppercase_charset$224(var2, var3);
         case 225:
            return this.test_charsets_case_insensitive$225(var2, var3);
         case 226:
            return this.test_partial_falls_inside_message_delivery_status$226(var2, var3);
         case 227:
            return this.TestIterators$227(var2, var3);
         case 228:
            return this.test_body_line_iterator$228(var2, var3);
         case 229:
            return this.test_typed_subpart_iterator$229(var2, var3);
         case 230:
            return this.test_typed_subpart_iterator_default_type$230(var2, var3);
         case 231:
            return this.test_pushCR_LF$231(var2, var3);
         case 232:
            return this.TestParsers$232(var2, var3);
         case 233:
            return this.test_header_parser$233(var2, var3);
         case 234:
            return this.test_whitespace_continuation$234(var2, var3);
         case 235:
            return this.test_whitespace_continuation_last_header$235(var2, var3);
         case 236:
            return this.test_crlf_separation$236(var2, var3);
         case 237:
            return this.test_multipart_digest_with_extra_mime_headers$237(var2, var3);
         case 238:
            return this.test_three_lines$238(var2, var3);
         case 239:
            return this.test_strip_line_feed_and_carriage_return_in_headers$239(var2, var3);
         case 240:
            return this.test_rfc2822_header_syntax$240(var2, var3);
         case 241:
            return this.test_rfc2822_space_not_allowed_in_header$241(var2, var3);
         case 242:
            return this.test_rfc2822_one_character_header$242(var2, var3);
         case 243:
            return this.test_CRLFLF_at_end_of_part$243(var2, var3);
         case 244:
            return this.TestBase64$244(var2, var3);
         case 245:
            return this.test_len$245(var2, var3);
         case 246:
            return this.test_decode$246(var2, var3);
         case 247:
            return this.test_encode$247(var2, var3);
         case 248:
            return this.test_header_encode$248(var2, var3);
         case 249:
            return this.TestQuopri$249(var2, var3);
         case 250:
            return this.setUp$250(var2, var3);
         case 251:
            return this.test_header_quopri_check$251(var2, var3);
         case 252:
            return this.test_body_quopri_check$252(var2, var3);
         case 253:
            return this.test_header_quopri_len$253(var2, var3);
         case 254:
            return this.test_body_quopri_len$254(var2, var3);
         case 255:
            return this.test_quote_unquote_idempotent$255(var2, var3);
         case 256:
            return this.test_header_encode$256(var2, var3);
         case 257:
            return this.test_decode$257(var2, var3);
         case 258:
            return this.test_encode$258(var2, var3);
         case 259:
            return this.TestCharset$259(var2, var3);
         case 260:
            return this.tearDown$260(var2, var3);
         case 261:
            return this.test_idempotent$261(var2, var3);
         case 262:
            return this.test_body_encode$262(var2, var3);
         case 263:
            return this.test_unicode_charset_name$263(var2, var3);
         case 264:
            return this.test_codecs_aliases_accepted$264(var2, var3);
         case 265:
            return this.TestHeader$265(var2, var3);
         case 266:
            return this.test_simple$266(var2, var3);
         case 267:
            return this.test_simple_surprise$267(var2, var3);
         case 268:
            return this.test_header_needs_no_decoding$268(var2, var3);
         case 269:
            return this.test_long$269(var2, var3);
         case 270:
            return this.test_multilingual$270(var2, var3);
         case 271:
            return this.test_header_ctor_default_args$271(var2, var3);
         case 272:
            return this.test_explicit_maxlinelen$272(var2, var3);
         case 273:
            return this.test_us_ascii_header$273(var2, var3);
         case 274:
            return this.test_string_charset$274(var2, var3);
         case 275:
            return this.test_utf8_shortest$275(var2, var3);
         case 276:
            return this.test_bad_8bit_header$276(var2, var3);
         case 277:
            return this.test_encoded_adjacent_nonencoded$277(var2, var3);
         case 278:
            return this.test_whitespace_eater$278(var2, var3);
         case 279:
            return this.test_broken_base64_header$279(var2, var3);
         case 280:
            return this.test_ascii_add_header$280(var2, var3);
         case 281:
            return this.test_nonascii_add_header_via_triple$281(var2, var3);
         case 282:
            return this.test_encode_unaliased_charset$282(var2, var3);
         case 283:
            return this.TestRFC2231$283(var2, var3);
         case 284:
            return this.test_get_param$284(var2, var3);
         case 285:
            return this.test_set_param$285(var2, var3);
         case 286:
            return this.test_del_param$286(var2, var3);
         case 287:
            return this.test_rfc2231_get_content_charset$287(var2, var3);
         case 288:
            return this.test_rfc2231_no_language_or_charset$288(var2, var3);
         case 289:
            return this.test_rfc2231_no_language_or_charset_in_filename$289(var2, var3);
         case 290:
            return this.test_rfc2231_no_language_or_charset_in_filename_encoded$290(var2, var3);
         case 291:
            return this.test_rfc2231_partly_encoded$291(var2, var3);
         case 292:
            return this.test_rfc2231_partly_nonencoded$292(var2, var3);
         case 293:
            return this.test_rfc2231_no_language_or_charset_in_boundary$293(var2, var3);
         case 294:
            return this.test_rfc2231_no_language_or_charset_in_charset$294(var2, var3);
         case 295:
            return this.test_rfc2231_bad_encoding_in_filename$295(var2, var3);
         case 296:
            return this.test_rfc2231_bad_encoding_in_charset$296(var2, var3);
         case 297:
            return this.test_rfc2231_bad_character_in_charset$297(var2, var3);
         case 298:
            return this.test_rfc2231_bad_character_in_filename$298(var2, var3);
         case 299:
            return this.test_rfc2231_unknown_encoding$299(var2, var3);
         case 300:
            return this.test_rfc2231_single_tick_in_filename_extended$300(var2, var3);
         case 301:
            return this.test_rfc2231_single_tick_in_filename$301(var2, var3);
         case 302:
            return this.test_rfc2231_tick_attack_extended$302(var2, var3);
         case 303:
            return this.test_rfc2231_tick_attack$303(var2, var3);
         case 304:
            return this.test_rfc2231_no_extended_values$304(var2, var3);
         case 305:
            return this.test_rfc2231_encoded_then_unencoded_segments$305(var2, var3);
         case 306:
            return this.test_rfc2231_unencoded_then_encoded_segments$306(var2, var3);
         case 307:
            return this.TestSigned$307(var2, var3);
         case 308:
            return this._msg_and_obj$308(var2, var3);
         case 309:
            return this._signed_parts_eq$309(var2, var3);
         case 310:
            return this.test_long_headers_as_string$310(var2, var3);
         case 311:
            return this.test_long_headers_flatten$311(var2, var3);
         case 312:
            return this._testclasses$312(var2, var3);
         case 313:
            return this.suite$313(var2, var3);
         case 314:
            return this.test_main$314(var2, var3);
         default:
            return null;
      }
   }
}
