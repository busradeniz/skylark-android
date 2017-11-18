package ostmodern.skylark.repository.remote;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;

import ostmodern.skylark.model.Set;


public class SetApiResponse {

    private final List<Set> objects;

    /**
     * Constructor for {@link SetApiResponse}.
     *
     * @param objects list of set
     */
    public SetApiResponse(List<Set> objects) {
        this.objects = objects;
    }

    public List<Set> getObjects() {
        return objects;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SetApiResponse that = (SetApiResponse) obj;
        return Objects.equals(objects, that.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("objects", objects)
                .toString();
    }
}
