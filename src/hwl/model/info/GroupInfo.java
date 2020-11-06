package hwl.model.info;

import hwl.model.Database;
import hwl.model.item.Student;

import java.util.List;

public class GroupInfo {
    public final int groupId;
    public final int studentCount;
    public final Double averageScore;

    public GroupInfo(int groupId, int studentCount, Double averageScore) {
        this.groupId = groupId;
        this.studentCount = studentCount;
        this.averageScore = averageScore;
    }

    public static GroupInfo calc(int groupId) {
        List<Student> students = Database.getInstance().students.get(s -> s.getGroupId() == groupId);
        if (students.isEmpty())
            return new GroupInfo(groupId, 0, null);
        else {
            double totalScore = 0;
            int realScoreCount = students.size();
            for (Student s : students) {
                Double d = StudentInfo.calc(s.getId()).averageScore;
                if (d != null)
                    totalScore += d;
                else
                    realScoreCount--;
            }

            if (realScoreCount > 0)
                return new GroupInfo(groupId, students.size(), totalScore / realScoreCount);
            else
                return new GroupInfo(groupId, 0, null);
        }
    }
}
