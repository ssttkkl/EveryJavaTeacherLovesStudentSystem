package hwl.model.item;

import hwl.model.Database;
import hwl.model.Displayable;
import hwl.model.IntPair;

import java.util.Objects;

/**
 * 成绩
 */
public class Score implements Item<IntPair>, Displayable {

    /**
     * 该成绩所属学生的ID
     */
    private final int studentId;

    /**
     * 该成绩所属课程的ID
     */
    private final int courseId;

    /**
     * 成绩得分
     */
    private final double point;

    public Score(int studentId, int courseId, double point) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.point = point;
    }

    @Override
    public IntPair getId() {
        return new IntPair(studentId, courseId);
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public double getPoint() {
        return point;
    }

    @Override
    public String getDisplayText() {
        Course c = Database.getInstance().courses.get(getCourseId());
        return String.format("%s：%.1f", c.getName(), getPoint());
    }

    public Student getStudent() {
        return Database.getInstance().students.get(this.getStudentId());
    }

    public Course getCourse() {
        return Database.getInstance().courses.get(this.getCourseId());
    }

    @Override
    public String toString() {
        return "Score{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return studentId == score.studentId &&
                courseId == score.courseId &&
                Double.compare(score.point, point) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, point);
    }
}
