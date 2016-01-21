package com.vennetics.bell.sam.subx;

import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import com.vennetics.bell.sam.subx.ldap.dao.SdmLdapDao;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rx.Observable;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The Test class for SubxController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SubxApplication.class)
@WebIntegrationTest({ "server.port=0", "management.port=0", "spring.cloud.config.enabled=false",
                "eureka.client.registerWithEureka:false", "eureka.client.fetchRegistry:false" })
public class SubxControllerTest {

    @ClassRule
    public static final RestDocumentation REST_DOCUMENTATION = new RestDocumentation("build/generated-snippets");
    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    @Mock
    private final SdmLdapDao subxLdapDao = new SdmLdapDao();
    private MockMvc mockMvc;
    private SdmAccount sdmAccount;
    @InjectMocks
    private SubxController subxController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Set<String> eamLanguageAttributes = new HashSet<>();
        eamLanguageAttributes.add("E");
        eamLanguageAttributes.add("F");
        eamLanguageAttributes.add("G");

        Set<String> preCannedFilterValue = new HashSet<>();
        preCannedFilterValue.add("123456789");

        final Map<String, Set<String>> result = new HashMap<>();

        result.put("MDN", preCannedFilterValue);
        result.put("eamLanguage", eamLanguageAttributes);
        sdmAccount = new SdmAccount(result);
        mockMvc = MockMvcBuilders.standaloneSetup(subxController)
                                 .apply(documentationConfiguration(this.REST_DOCUMENTATION))
                                 .build();
    }

    @Test
    public void getSubscriberProfileMockMvc() throws Exception {

        final Set<String> attributes = new HashSet<>();
        attributes.add("MDN");
        attributes.add("banid");
        // Mock LDAP response
        when(subxLdapDao.getSubscriberProfile("MDN",
                                              "123456789",
                                              attributes)).thenReturn(Observable.just(sdmAccount));

        // Happy Path
        mockMvc.perform(get("/api/subscriber/profile").param("searchFilter", "MDN")
                                                      .param("filterValue", "123456789")
                                                      .contentType(contentType)
                                                      .accept(contentType)).andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$.attributes").isArray())
               .andExpect(jsonPath("$.attributes[0].name").value("MDN"))
               .andExpect(jsonPath("$.attributes[0].value").value("123456789"))
               .andExpect(jsonPath("$.attributes[1].name").value("eamLanguage"))
               .andExpect(jsonPath("$.attributes[1].value").value("E"))
               .andDo(document("getSubscriberProfile",
                               preprocessRequest(prettyPrint()),
                               preprocessResponse(prettyPrint()),
                        requestParameters(parameterWithName("searchFilter").description("The Filter which is one among this list (MDN,SUBID)"),
                                          parameterWithName("filterValue").description("The Value of the searchFilter.")),
                        responseFields(fieldWithPath("attributes").description("A list of Name Value pairs containing the attributes for a Subscriber").optional())));
    }

}
