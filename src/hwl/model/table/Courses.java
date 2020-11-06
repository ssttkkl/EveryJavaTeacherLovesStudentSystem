package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.item.Course;
import hwl.model.item.Score;
import hwl.model.item.Student;
import hwl.model.table.IntTable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Courses extends IntTable<Course> {

    public Courses(Database database) {
        super(database);
    }

    public Course emplace(String name, String number, double point) {
        int id = getNewId();
        Course c = new Course(id, name, number, point);
        put(c);
        return c;
    }

    @Override
    public Course remove(Integer id) {
        Course old = super.remove(id);
        for (Score s : Database.getInstance().scores.get(s -> s.getCourseId() == id))
            Database.getInstance().scores.remove(s.getId());
        return old;
    }

    private final ItemSerializer<Course> itemSerializer = new ItemSerializer<>() {
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
            dos.writeInt(item.getId());
            dos.writeUTF(item.getName());
            dos.writeUTF(item.getNumber());
            dos.writeDouble(item.getPoint());
        }
    };

    @Override
    public ItemSerializer<Course> getItemSerializer() {
        return itemSerializer;
    }
}