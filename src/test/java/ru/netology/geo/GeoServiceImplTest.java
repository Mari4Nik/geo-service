package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoServiceImplTest {
    @ParameterizedTest
    @MethodSource("generateSource")
    void byIpTest(String ip, Country expected) {
        GeoService geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(ip).getCountry();

        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> generateSource() {
        return Stream.of(
                Arguments.of("172.123.12.19", Country.RUSSIA),
                Arguments.of("96.44.183.145", Country.USA));
    }
}