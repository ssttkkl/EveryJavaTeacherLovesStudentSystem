package hwl.model.item;

import java.io.Serializable;

/**
 * 数据类的接口
 */
public interface Item<P> extends Serializable {
    P getPrimitiveKey();
}
