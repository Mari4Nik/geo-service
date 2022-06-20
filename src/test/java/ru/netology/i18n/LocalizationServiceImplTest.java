package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

class LocalizationServiceImplTest {

    @Test
    void testLocaleRu() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        String expected = "Добро пожаловать";
        String actual = localizationService.locale(Country.RUSSIA);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void localeEng() {

        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        String expected = "Welcome";
        String actual = localizationService.locale(Country.USA);

        Assertions.assertEquals(expected, actual);

    }
}
