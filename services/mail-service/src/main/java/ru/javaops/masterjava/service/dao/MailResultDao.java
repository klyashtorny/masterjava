package ru.javaops.masterjava.service.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.dao.AbstractDao;
import ru.javaops.masterjava.service.model.MailResult;

import java.util.Collection;
import java.util.List;

/**
 * @author Anton Klyashtorny 28.08.2020
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class MailResultDao implements AbstractDao {

    @SqlUpdate("TRUNCATE mails CASCADE ")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM mails")
    public abstract List<MailResult> getAll();

    @SqlBatch("INSERT INTO mails (email, result) VALUES (:email, :result)")
    public abstract void insertBatch(@BindBean Collection<MailResult> mailResults);
}
