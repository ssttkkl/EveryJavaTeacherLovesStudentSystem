package hwl.model;

import hwl.model.item.Course;
import hwl.model.item.Group;
import hwl.model.item.Score;
import hwl.model.item.Student;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DatabaseTest {
    @Test
    public void test() {
        Database db = Database.getInstance();

        db.addOnErrorListener(exc -> {
            fail(exc.getMessage());
        });

        Group[] groups = new Group[]{
                db.groups.emplace("1"),
                db.groups.emplace("2"),
                db.groups.emplace("3")
        };

        Student[] students = new Student[]{
                db.students.emplace("22", "222", Sex.MALE, groups[0].getId()),
                db.students.emplace("33", "333", Sex.FEMALE, groups[0].getId()),
                db.students.emplace("66", "666", Sex.MALE, groups[1].getId()),
                db.students.emplace("99", "999", Sex.FEMALE, groups[1].getId())
        };

        Course[] courses = new Course[]{
                db.courses.emplace("jaba", "jb", 3),
                db.courses.emplace("python", "py", 2)
        };

        Score[] scores = new Score[]{
                db.scores.emplace(students[0].getId(), courses[0].getId(), 95.0),
                db.scores.emplace(students[1].getId(), courses[0].getId(), 96.1),
                db.scores.emplace(students[1].getId(), courses[1].getId(), 96.2),
                db.scores.emplace(students[3].getId(), courses[1].getId(), 97.1)
        };

        db.postReload();
        db.shutdownAndJoin();

        List<Group> g = db.groups.getAll();
        for (int i = 0; i < g.size(); i++) {
            assertEquals(g.get(i), groups[i]);
        }

        List<Student> s = db.students.getAll();
        for (int i = 0; i < s.size(); i++) {
            assertEquals(s.get(i), students[i]);
        }

        List<Course> c = db.courses.getAll();
        for (int i = 0; i < c.size(); i++) {
            assertEquals(c.get(i), courses[i]);
        }

        List<Score> sc = db.scores.getAll();
        for (int i = 0; i < sc.size(); i++) {
            assertEquals(sc.get(i), scores[i]);
        }
    }
}
