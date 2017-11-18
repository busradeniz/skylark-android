package ostmodern.skylark.model;

import java.util.Objects;

import ostmodern.skylark.repository.local.SetEntity;

public class SetUI {
    private final SetEntity setEntity;
    private boolean isFavourite;

    public SetUI(SetEntity setEntity, boolean isFavourite) {
        this.setEntity = setEntity;
        this.isFavourite = isFavourite;
    }

    public SetEntity getSetEntity() {
        return setEntity;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SetUI setUI = (SetUI) object;
        return isFavourite == setUI.isFavourite
                && Objects.equals(setEntity, setUI.setEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setEntity, isFavourite);
    }
}
