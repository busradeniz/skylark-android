package ostmodern.skylark.repository.remote;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;

import ostmodern.skylark.model.Image;


public class ImageApiResponse {

    private final List<Image> objects;

    /**
     * Constructor for {@link ImageApiResponse}.
     *
     * @param objects list of image
     */
    public ImageApiResponse(List<Image> objects) {
        this.objects = objects;
    }

    public List<Image> getObjects() {
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

        ImageApiResponse that = (ImageApiResponse) obj;
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
