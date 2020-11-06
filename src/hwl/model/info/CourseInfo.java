package hwl.model.info;

import hwl.model.Database;
import hwl.model.item.Score;

import java.util.List;

public class CourseInfo {
    public final int courseId;
    public final int scoreCount;
    public final double averageScore;

    public CourseInfo(int courseId, int scoreCount, double averageScore) {
        this.courseId = courseId;
        this.scoreCount = scoreCount;
        this.averageScore = averageScore;
    }

    public static CourseInfo calc(int courseId) {
        List<Score> scores = Database.getInstance().scores.get(s -> s.getCourseId() == courseId);
        if (scores.isEmpty())
            return new CourseInfo(courseId, 0, 0);
        else {
            double totalScore = 0;
            for (Score s : scores) {
                totalScore += s.getPoint();
            }
            return new CourseInfo(courseId, scores.size(), totalScore / scores.size());
        }
    }
}
