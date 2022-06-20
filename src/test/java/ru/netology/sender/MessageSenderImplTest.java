package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


class MessageSenderImplTest {
    @ParameterizedTest
    @MethodSource("factory")
    void testSendWithRU(String key, String ip, String expected) {
        Map<String, String> headers = new HashMap<>();
        headers.put(key, ip);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSenderImpl = new MessageSenderImpl(geoService, localizationService);

        String result = messageSenderImpl.send(headers);
        Assertions.assertEquals(expected, result);

    }

    public static Stream<Arguments> factory() {

        return Stream.of(
                Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19", "Добро пожаловать"),
                Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.124.13.0", "Добро пожаловать"),
                Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.145", "Welcome"),
                Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.25.208.178", "Welcome")

        );
    }
}