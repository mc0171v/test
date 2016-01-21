package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.vennetics.bell.sam.model.subx.Attribute;
import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class SubxServiceTest {

    @InjectMocks
    @Autowired
    private SubxService service;

    @Mock private RestTemplate mockTemplate;

    @Test
    public void shouldGetSubscriberProfile() throws Exception {
        final String expectedUrl = "http://subx/api/subscriber/profile?searchFilter=SUBID&filterValue=VALUE";
        final SubscriberProfile dummyResult = new SubscriberProfile(Collections.singletonList(new Attribute("SUBID", "123456789")));
        when(mockTemplate.getForObject(expectedUrl, SubscriberProfile.class)).thenReturn(dummyResult);

        final Observable observableProfile = service.getSubscriberProfile(SearchFilterType.SUBID, "VALUE");
        assertThat(observableProfile, is(not(nullValue())));
        assertThat(observableProfile.toBlocking().single(), is(sameInstance(dummyResult)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReportFeatureCodeEnablement() throws Exception {

        final Observable observableProfile = service.hasFeatureCodes("12345", "AFF_ID", Arrays.asList("F1", "F2"));
        assertThat(observableProfile, is(not(nullValue())));
        assertThat(observableProfile.toBlocking().single(), is(nullValue()));

        verify(mockTemplate).getForObject(contains("12345"), any(Class.class));
    }
}
