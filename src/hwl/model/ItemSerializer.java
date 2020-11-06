package hwl.model;

import hwl.model.item.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 包含将数据项序列化到DataOutputStream/从DataInputStream反序列化的方法
 */
public interface ItemSerializer<T extends Item<?>> {
    /**
     * 从DataInputStream反序列化数据项
     *
     * @param dis 输入流
     * @return 反序列化得到的数据项
     * @throws IOException
     */
    T read(DataInputStream dis) throws IOException;

    /**
     * 将数据项序列化到DataOutputStream
     *
     * @param dos  输出流
     * @param item 数据项
     * @throws IOException
     */
    void write(DataOutputStream dos, T item) throws IOException;
}
