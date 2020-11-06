package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.item.Course;
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