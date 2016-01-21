package com.vennetics.bell.sam.parlayx.soapadapters.subx.commands;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class HasFeatureCodesCommandTest {

    @Mock private RestTemplate mockTemplate;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldGetFeatureCodeEnablement() throws Exception {
        final String expectedUrl = "http://subx/api/subscriber/featureCodes?mdn=12345&affiliateId=AFF_ID";

        final HasFeatureCodesCommand.BooleanList dummyResult = new HasFeatureCodesCommand.BooleanList();

        when(mockTemplate.getForObject(expectedUrl, HasFeatureCodesCommand.BooleanList.class)).thenReturn(dummyResult);

        final HasFeatureCodesCommand command = new HasFeatureCodesCommand("12345", "AFF_ID",
                                                    Arrays.asList("F1", "F2", "F3", "F4"), mockTemplate);

        assertThat(command.observe().toBlocking().single(), sameInstance(dummyResult));
    }
}
