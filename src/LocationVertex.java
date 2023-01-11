import java.util.ArrayList;

public class LocationVertex {
        String name;
        ArrayList<DistanceEdge> edges;

        public LocationVertex(String name) {
            this.name = name;
            this.edges = new ArrayList<>();
        }
}
