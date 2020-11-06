package hwl.model;

import java.io.Serializable;

/**
 * 性别
 */
public enum Sex implements Serializable, Displayable {
    MALE, FEMALE, OTHER;

    @Override
    public String getDisplayText() {
        switch (this) {
            case MALE:
                return "男";
            case FEMALE:
                return "女";
            case OTHER:
                return "其他";
        }
        return this.name();
    }
}
