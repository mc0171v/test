package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.Attribute;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.SearchFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberImplTest {

    @InjectMocks
    @Autowired
    private SubscriberImpl subscriber;

    @Mock private ISubxService mockSubxService;

    @Test
    public void shouldGetSubscriberProfile() throws Exception {
        when(mockSubxService.getSubscriberProfile(SearchFilterType.MDN, "filterValue"))
                .thenReturn(Observable.just(
                        new SubscriberProfile(Arrays.asList(newAttribute("Key1", "Value1"),
                                                            newAttribute("Key2", "Value2")))));

        final List<Attribute> result = subscriber.getSubscriberProfile(SearchFilter.MDN, "filterValue");

        assertThat(result.get(0).getKey(), is("Key1"));
        assertThat(result.get(1).getKey(), is("Key2"));
    }

    private com.vennetics.bell.sam.model.subx.Attribute newAttribute(final String key, final String value) {
        return new com.vennetics.bell.sam.model.subx.Attribute(key, value);
    }

    @Test
    public void shouldReportFeatureCodes() throws Exception {
        when(mockSubxService.hasFeatureCodes("12345", "AFF_ID", Arrays.asList("F1", "F2")))
                .thenReturn(Observable.just(Arrays.asList(false, true)));

        final List<Boolean> result = subscriber.hasFeatureCodes("12345", "AFF_ID", Arrays.asList("F1", "F2"));

        assertThat(result.get(0), is(false));
        assertThat(result.get(1), is(true));
    }

    @Test
    public void shouldReturnDefaultValueIfUnsupportedMethodsCalled() throws Exception {
        assertThat(subscriber.changeSubscriberPassword("mdn", "old", "new", "new", "id"), is(false));
        assertThat(subscriber.getSubscriberPassword("mdn", "id"), is(nullValue()));
    }

}
