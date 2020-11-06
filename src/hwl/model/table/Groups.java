package hwl.model.table;

import hwl.model.Database;
import hwl.model.ItemSerializer;
import hwl.model.item.Group;
import hwl.model.table.IntTable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Groups extends IntTable<Group> {

    public Groups(Database database) {
        super(database);
    }

    public Group emplace(String name) {
        int id = getNewId();
        Group g = new Group(id, name);
        put(g);
        return g;
    }

    private final ItemSerializer<Group> itemSerializer = new ItemSerializer<>() {
        @Override
        public Group read(DataInputStream dis) throws IOException {
            int id = dis.readInt();
            String name = dis.readUTF();

            return new Group(id, name);
        }

        @Override
        public void write(DataOutputStream dos, Group item) throws IOException {
            dos.writeInt(item.getId());
            dos.writeUTF(item.getName());
        }
    };

    @Override
    public ItemSerializer<Group> getItemSerializer() {
        return itemSerializer;
    }
}
