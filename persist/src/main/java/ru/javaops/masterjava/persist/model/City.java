package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

/**
 * @author Anton Klyashtorny 16.08.2020
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {

    @Column("short_name")
    private @NonNull String shortName;
    private @NonNull String value;

    public City(Integer id, String shortName, String value) {
        this(shortName, value);
        this.id = id;
    }
}
