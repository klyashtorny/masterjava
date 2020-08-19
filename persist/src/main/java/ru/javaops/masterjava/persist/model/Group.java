package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Group  extends BaseEntity {

    @NonNull
    private  String name;

    @NonNull
    private GroupType type;

    @NonNull
    @Column("project_fk")
    private Project project;

    public Group(Integer id, String name, GroupType type, Project project) {
        this(name, type, project);
        this.id = id;
    }
}
