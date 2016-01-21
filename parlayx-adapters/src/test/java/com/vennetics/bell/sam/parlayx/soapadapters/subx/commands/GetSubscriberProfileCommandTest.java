package com.vennetics.bell.sam.parlayx.soapadapters.subx.commands;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import com.vennetics.bell.sam.model.subx.Attribute;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetSubscriberProfileCommandTest {

    @Mock
    private RestTemplate mockTemplate;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetSubscriberProfile() throws Exception {
        final String expectedUrl = "http://subx/api/subscriber/profile?searchFilter=SUBID&filterValue=VALUE";
        final SubscriberProfile dummyResult = new SubscriberProfile(
                Collections.singletonList(new Attribute(
                        "SUBID",
                        "123456789")));
        when(mockTemplate.getForObject(expectedUrl, SubscriberProfile.class)).thenReturn(dummyResult);

        final GetSubscriberProfileCommand command = new GetSubscriberProfileCommand("SUBID", "VALUE", mockTemplate);

        assertThat(command.observe().toBlocking().single(), sameInstance(dummyResult));
    }
}
