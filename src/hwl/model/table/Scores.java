package hwl.model.table;

import hwl.model.Database;
import hwl.model.IntPair;
import hwl.model.ItemSerializer;
import hwl.model.item.Score;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Scores extends AbstractTable<IntPair, Score> {

    public Scores(Database database) {
        super(database);
    }

    public Score emplace(int studentId, int courseId, double point) {
        Score s = new Score(studentId, courseId, point);
        put(s);
        return s;
    }

    private static final ItemSerializer<Score> itemSerializer = new ItemSerializer<>() {
        @Override
        public Score read(DataInputStream dis) throws IOException {
            int studentId = dis.readInt();
            int courseId = dis.readInt();
            double point = dis.readDouble();

            return new Score(studentId, courseId, point);
        }

        @Override
        public void write(DataOutputStream dos, Score item) throws IOException {
            dos.writeInt(item.studentId);
            dos.writeInt(item.courseId);
            dos.writeDouble(item.point);
        }
    };

    @Override
    public ItemSerializer<Score> getItemSerializer() {
        return itemSerializer;
    }
}
