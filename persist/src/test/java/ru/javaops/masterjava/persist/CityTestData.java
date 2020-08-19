package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

/**
 * @author Anton Klyashtorny 16.08.2020
 */
public class CityTestData {

    public static City MINSK;
    public static City KIEV;
    public static City SPB;
    public static City MOSCOW;
    public static List<City> CITIES;

    public static void init() {
        MINSK = new City("mnsk", "Минск");
        MOSCOW = new City("mow", "Москва");
        KIEV = new City("kiv", "Киев");
        SPB = new City("spb", "Санкт-Петербург");
        CITIES = ImmutableList.of(MINSK, KIEV, SPB, MOSCOW);
    }

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            CITIES.forEach(dao::insert);
        });
    }

}
