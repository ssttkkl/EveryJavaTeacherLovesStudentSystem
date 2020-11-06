package hwl.model.table;

import hwl.model.Database;
import hwl.model.item.Item;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class IntTable<T extends Item<Integer>> extends AbstractTable<Integer, T> {

    public IntTable(Database database) {
        super(database);
    }

    /**
     * 保存下一个自增ID的数值
     */
    private final AtomicInteger counter = new AtomicInteger();

    /**
     * 获取下一个自增ID
     *
     * @return 下一个自增ID
     */
    protected int getNewId() {
        return counter.getAndIncrement();
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        super.read(dis);
        int newCounter = this.getAll().stream().mapToInt(Item<Integer>::getId).max().orElse(-1) + 1;
        counter.set(newCounter);
    }
}
