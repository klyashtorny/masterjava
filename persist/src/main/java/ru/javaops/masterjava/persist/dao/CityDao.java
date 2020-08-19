package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

/**
 * @author Anton Klyashtorny 16.08.2020
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao implements AbstractDao {

    public City insert(City city) {
        if (city.isNew()) {
            int id = insertGeneratedId(city);
            city.setId(id);
        } else {
            insertWitId(city);
        }
        return city;
    }

    @SqlUpdate("INSERT INTO cities (short_name, value) VALUES (:shortName, :value) ON CONFLICT (short_name) DO NOTHING")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean City city);

    @SqlUpdate("INSERT INTO cities (id, short_name, value) VALUES (:shortName, :value) ON CONFLICT (short_name) DO NOTHING")
    abstract void insertWitId(@BindBean City city);

    @SqlQuery("SELECT * FROM cities")
    public abstract List<City> getCities();

    @SqlUpdate("TRUNCATE cities CASCADE")
    @Override
    public abstract void clean();

    @SqlBatch("INSERT INTO cities (id, short_name, value) VALUES (:id, :shortName, :value)" +
            "ON CONFLICT DO NOTHING")
    public abstract int[] insertBatch(@BindBean List<City> cities);
}
