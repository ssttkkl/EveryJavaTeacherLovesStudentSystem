package hwl.model.info;

import hwl.model.Database;
import hwl.model.item.Course;
import hwl.model.item.Score;

import java.util.List;

public class StudentInfo {
    public final int studentId;
    public final int courseCount;
    public final double totalScore;
    public final Double averageScore;
    public final double totalPoint;

    public StudentInfo(int studentId, int courseCount, double totalScore, Double averageScore, double totalPoint) {
        this.studentId = studentId;
        this.courseCount = courseCount;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
        this.totalPoint = totalPoint;
    }

    public static StudentInfo calc(int studentId) {
        List<Score> scores = Database.getInstance().scores.get(s -> s.getStudentId() == studentId);
        if (scores.isEmpty())
            return new StudentInfo(studentId, 0, 0, null, 0);
        else {
            double totalPoint = 0, totalScore = 0;
            for (Score s : scores) {
                Course c = s.getCourse();
                totalPoint += c.getPoint();
                totalScore += s.getPoint();
            }
            return new StudentInfo(studentId, scores.size(), totalScore, totalScore / scores.size(), totalPoint);
        }
    }
}
