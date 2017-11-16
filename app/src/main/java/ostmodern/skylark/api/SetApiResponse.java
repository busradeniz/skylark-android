package ostmodern.skylark.api;

import java.util.List;

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
}
