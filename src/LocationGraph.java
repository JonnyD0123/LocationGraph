import java.util.*;

public class LocationGraph {
    List<LocationVertex> vertices = new ArrayList<>();

    public LocationGraph() {}

    public boolean addLocation(String locationName) {
        LocationVertex toAdd = new LocationVertex(locationName);
        if (!vertices.contains(toAdd)) {
            vertices.add(toAdd);
            return true;
        } else return false;
    }

    protected int getIndexOfVertex(String vertexName) {
        int idx = 0;
        for (LocationVertex toCheck : this.vertices) {
            if (toCheck.name.compareTo(vertexName) == 0) return idx;
            else idx++;
        }
        return -1;
    }

    public boolean addDistance(String locationA, String locationB, double distance) {
        LocationVertex start;
        LocationVertex end;

        int idx = this.getIndexOfVertex(locationA);
        if (idx >= 0)
            start = vertices.get(idx);
        else
            start = new LocationVertex(locationA);
        vertices.add(start);

        idx = this.getIndexOfVertex(locationB);
        if (idx >= 0)
            end = this.vertices.get(idx);
        else {
            end = new LocationVertex(locationB);
            vertices.add(end);
        }
        boolean exists = false;
        for (int i = 0; i < start.edges.size(); i++) {
            if (start.edges.get(i).vertex.equals(end))
                exists = true;
        }
        for (int i = 0; i < end.edges.size(); i++) {
            if (end.edges.get(i).vertex.equals(start))
                exists = true;
        }

        if (!exists) {
            start.edges.add(new DistanceEdge(end, distance));
            end.edges.add(new DistanceEdge(start, distance));
            return true;
        } else return false;
    }

    public double findDistanceBFS(String startName, String endName) {
        Queue<LocationVertex> searchQ = new LinkedList<>();
        ArrayList<LocationVertex> visitedVertices = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();

        int index = this.getIndexOfVertex(startName);
        searchQ.add(this.vertices.get(index));
        visitedVertices.add(this.vertices.get(index));
        distances.add(0.0);

        while (!searchQ.isEmpty()) {
            LocationVertex current = searchQ.remove();
            int pos = visitedVertices.indexOf(current);
            if (current.name.equals(endName)) {
                return distances.get(pos);
            }
            for (DistanceEdge edge : current.edges) {
                if (!visitedVertices.contains(edge.vertex)) {
                    searchQ.add(edge.vertex);
                    visitedVertices.add(edge.vertex);
                    distances.add(distances.get(pos) + edge.distance);
                }
            }
        }
        return 0.0;
    }

    public double findDistanceDFS(String startName, String endName) {
        Stack<LocationVertex> stack = new Stack<>();
        ArrayList<LocationVertex> visited = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();

        int index = this.getIndexOfVertex(startName);
        stack.add(this.vertices.get(index));
        visited.add(this.vertices.get(index));
        distances.add(0.0);

        while (!stack.isEmpty()) {
            LocationVertex current = stack.remove(index);
            int pos = visited.indexOf(current);
            if (current.name.equals(endName))
                return distances.get(pos);
            for (DistanceEdge edge : current.edges) {
                if (!visited.contains(edge.vertex)) {
                    stack.add(edge.vertex);
                    visited.add(edge.vertex);
                    distances.add(distances.get(pos) + edge.distance);
                }
            }
        }
        return 0.0;
    }

    public boolean detectCycle() {
        ArrayList<Boolean> isVisited = new ArrayList<>(vertices.size());

        for (int i = 0; i < vertices.size(); i++)
            isVisited.add(false);

        for (LocationVertex vertex : vertices)
            if(!isVisited.get(vertices.indexOf(vertex)) && detectCycle(null, vertex, isVisited))
                return true;
        return false;
    }

    public boolean detectCycle(LocationVertex caller, LocationVertex current, ArrayList<Boolean> isVisited) {
         isVisited.set(vertices.indexOf(current), true);

         for (DistanceEdge edge : current.edges)
             if (isVisited.get(vertices.indexOf(edge.vertex)) && edge.vertex != caller ||
             !isVisited.get(vertices.indexOf(edge.vertex)) && detectCycle(current, edge.vertex, isVisited))
                 return true;
         return false;
    }

    @Override
    public String toString () {
            StringBuilder stb = new StringBuilder();

            for (int i = 0; i < vertices.size(); i++) {
                ArrayList<DistanceEdge> edges = new ArrayList<>();
                edges.addAll(vertices.get(i).edges);
                if (edges.size()==0) {
                    stb.append("-1");
                    return stb.toString();
                }
            }

            for (int i = 0; i < this.vertices.size() - 1; i++) {
                for (DistanceEdge edge : this.vertices.get(i).edges) {
                    stb = stb.append("{" + this.vertices.get(i).name);
                    stb = stb.append(", " + edge.vertex.name);
                    stb = stb.append(", " + edge.distance + "}; ");
                }
            }
            return stb.toString();
        }
}

