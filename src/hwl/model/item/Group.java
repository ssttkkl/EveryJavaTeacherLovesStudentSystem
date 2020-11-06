package hwl.model.item;

import hwl.model.Database;
import hwl.model.Displayable;

import java.util.Collection;
import java.util.Objects;

/**
 * 班级
 */
public class Group implements Item<Integer>, Displayable {

    /**
     * 在数据表中的唯一标识
     */
    public final int id;

    /**
     * 班级名称
     */
    public final String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getPrimitiveKey() {
        return id;
    }

    @Override
    public String getDisplayText() {
        return name;
    }

    public Collection<Student> getStudents() {
        return Database.getInstance().students.get(stu -> stu.groupId == this.id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id &&
                Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
