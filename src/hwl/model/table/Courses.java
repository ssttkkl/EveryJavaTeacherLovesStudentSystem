package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.item.Course;
import hwl.model.item.Score;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Courses extends IntTable<Course> {

    public Courses(Database database) {
        super(database);
    }

    public Course emplace(int id, String name, String number, double point) {
        Course c = new Course(id, name, number, point);
        put(c);
        return c;
    }

    public Course emplace(String name, String number, double point) {
        return emplace(newPrimitiveKey(), name, number, point);
    }

    @Override
    public Course remove(Integer id) {
        Course old = super.remove(id);
        for (Score s : Database.getInstance().scores.get(s -> s.courseId == id))
            Database.getInstance().scores.remove(s.getPrimitiveKey());
        return old;
    }

    private static final ItemSerializer<Course> itemSerializer = new ItemSerializer<>() {
        @Override
        public Course read(DataInputStream dis) throws IOException {
            int id = dis.readInt();
            String name = dis.readUTF();
            String number = dis.readUTF();
            double point = dis.readDouble();

            return new Course(id, name, number, point);
        }

        @Override
        public void write(DataOutputStream dos, Course item) throws IOException {
            dos.writeInt(item.id);
            dos.writeUTF(item.name);
            dos.writeUTF(item.number);
            dos.writeDouble(item.point);
        }
    };

    @Override
    public ItemSerializer<Course> getItemSerializer() {
        return itemSerializer;
    }
}