package ru.javaops.masterjava.service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import ru.javaops.masterjava.persist.model.BaseEntity;

/**
 * @author Anton Klyashtorny 28.08.2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailResult extends BaseEntity {

    private static final String OK = "OK";

    private @NonNull final String email;
    private @NonNull final String result;

    public static MailResult ok(String email) {
        return new MailResult(email, OK);
    }

    public static MailResult error(String email, String error) {
        return new MailResult(email, error);
    }

    public boolean isOk() {
        return OK.equals(result);
    }

    public MailResult(String email, String cause) {
        this.email = email;
        this.result = cause;
    }

    @Override
    public String toString() {
        return '(' + email + ',' + result + ')';
    }


}
