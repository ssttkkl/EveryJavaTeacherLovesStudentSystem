package hwl.model.item;

import hwl.model.Database;
import hwl.model.Displayable;

import java.util.Collection;
import java.util.Objects;

/**
 * 课程
 */
public class Course implements Item<Integer>, Displayable {

    /**
     * 在数据表中的唯一标识
     */
    private final int id;

    /**
     * 课程名称
     */
    private final String name;

    /**
     * 课程编号
     */
    private final String number;

    /**
     * 课程学分
     */
    private final double point;

    public Course(int id, String name, String number, double point) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.point = point;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public double getPoint() {
        return point;
    }

    @Override
    public String getDisplayText() {
        return String.format("%s（课程编号：%s，学分：%.1f）", getName(), getNumber(), getPoint());
    }

    public Collection<Score> getScores() {
        return Database.getInstance().scores.get(sc -> sc.getCourseId() == this.getId());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                point == course.point &&
                Objects.equals(name, course.name) &&
                Objects.equals(number, course.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, point);
    }
}
