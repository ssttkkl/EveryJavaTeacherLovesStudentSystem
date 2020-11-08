package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.Sex;
import hwl.model.item.Score;
import hwl.model.item.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Students extends IntTable<Student> {

    public Students(Database database) {
        super(database);
    }

    public Student emplace(int id, String name, String number, Sex sex, int groupId) {
        Student s = new Student(id, name, number, sex, groupId);
        put(s);
        return s;
    }

    public Student emplace(String name, String number, Sex sex, int groupId) {
        return emplace(newPrimitiveKey(), name, number, sex, groupId);
    }

    @Override
    public Student remove(Integer id) {
        Student old = super.remove(id);
        for (Score s : Database.getInstance().scores.get(s -> s.studentId == id))
            Database.getInstance().scores.remove(s.getPrimitiveKey());
        return old;
    }

    private static final ItemSerializer<Student> itemSerializer = new ItemSerializer<>() {
        @Override
        public Student read(DataInputStream dis) throws IOException {
            int id = dis.readInt();
            String name = dis.readUTF();
            String number = dis.readUTF();
            Sex sex = Sex.valueOf(dis.readUTF());
            int groupId = dis.readInt();

            return new Student(id, name, number, sex, groupId);
        }

        @Override
        public void write(DataOutputStream dos, Student item) throws IOException {
            dos.writeInt(item.id);
            dos.writeUTF(item.name);
            dos.writeUTF(item.number);
            dos.writeUTF(item.sex.name());
            dos.writeInt(item.groupId);
        }
    };

    @Override
    public ItemSerializer<Student> getItemSerializer() {
        return itemSerializer;
    }
}
