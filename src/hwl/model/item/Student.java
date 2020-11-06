package hwl.model.item;

import hwl.model.Database;
import hwl.model.Displayable;
import hwl.model.Sex;

import java.util.Collection;
import java.util.Objects;

/**
 * 学生
 */
public class Student implements Item<Integer>, Displayable {

    /**
     * 在数据表中的唯一标识
     */
    private final int id;

    /**
     * 姓名
     */
    private final String name;

    /**
     * 学号
     */
    private final String number;

    /**
     * 学号
     */
    private final Sex sex;

    /**
     * 所属班级ID
     */
    private final int groupId;

    public Student(int id, String name, String number, Sex sex, int groupId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.sex = sex;
        this.groupId = groupId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Sex getSex() {
        return sex;
    }

    public int getGroupId() {
        return groupId;
    }

    @Override
    public String getDisplayText() {
        return String.format("%s（学号：%s，性别：%s）", getName(), getNumber(), getSex().getDisplayText());
    }

    public Group getGroup() {
        return Database.getInstance().groups.get(this.getGroupId());
    }

    public Collection<Score> getScores() {
        return Database.getInstance().scores.get(sc -> sc.getStudentId() == this.getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", sex=" + sex +
                ", groupId=" + groupId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                groupId == student.groupId &&
                Objects.equals(name, student.name) &&
                Objects.equals(number, student.number) &&
                sex == student.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, sex, groupId);
    }
}
