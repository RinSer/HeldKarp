import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSP {

	public static void main(String[] args) {
		
		String file_path = "/home/rinser/AlgorithmsCourseraStanford/ttsp.txt";
		try {
			
			List<String> file_lines = Files.readAllLines(Paths.get(file_path));
			int number_of_vertices = Integer.parseInt(file_lines.remove(0).trim());
			List<Vertex> graph = new ArrayList<Vertex>();
			List<Integer> vertices = new ArrayList<Integer>();
			int vertex_index = 0;
			for (String current : file_lines) {
				String[] coordinates = current.trim().split(" ");
				double x_coordinates = Double.parseDouble(coordinates[0]);
				double y_coordinates = Double.parseDouble(coordinates[1]);
				graph.add(new Vertex(x_coordinates, y_coordinates, vertex_index));
				vertices.add(vertex_index);
				vertex_index++;
				
			}
			
			HashMap<Set<Integer>, double[]> A = new HashMap<Set<Integer>, double[]>();
			// Dynamic Algorithm
			for (int k = 1; k < number_of_vertices; k++) {
				Set<Integer> set = new HashSet<Integer>();
				set.add(0);
				set.add(k);
				A.put(set, new double[number_of_vertices]);
				A.get(set)[k] = graph.get(0).distanceTo(graph.get(k));
			}
			
			for (int s = 3; s < number_of_vertices+1; s++) {
				List<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
				subsets = findSubsets(0, vertices, s);
				System.out.println("Processing "+subsets.size()+" cases ("+s+"/"+number_of_vertices+")");
				for (Set<Integer> set : subsets) {
					A.put(set, new double[number_of_vertices]);
					for (int k : set) {
						if (k != 0) {
							Set<Integer> subset = new HashSet<Integer>();
							for (int v : set) {
								if (v != k) {
									subset.add(v);
								}
							}
							double min_distance = 999999999;
							for (int m : subset) {
								if (m != 0) {
									double m_distance = A.get(subset)[m] + graph.get(m).distanceTo(graph.get(k));
									if (m_distance < min_distance) min_distance = m_distance;
								}
							}
							A.get(set)[k] = min_distance;
						}
					}
				}
			}
			// Compute the minimum path
			Set<Integer> final_set = new HashSet<Integer>();
			for (int k = 0; k < number_of_vertices; k++) {
				final_set.add(k);
			}
			
			double min_path = 999999999;
			for (int k = 1; k < number_of_vertices; k++) {
				double k_path = A.get(final_set)[k] + graph.get(k).distanceTo(graph.get(0));
				if (k_path < min_path) min_path = k_path;
			}
			System.out.println("Minimum salesman path: "+min_path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void findSubsets(int vertex, List<Integer> superSet, int k, int idx, Set<Integer> current, List<Set<Integer>> solution) {
		// Stop when found subset of size k
		if (current.size() == k) {
			for (Integer v : current) {
				if (v == vertex) {
					solution.add(new HashSet<Integer>(current));
				}
			}
			return;
		}
		// Unsuccessful stop clause
	    if (idx == superSet.size()) return;
	    Integer next = superSet.get(idx);
	    current.add(next);
	    // next vertex is in the subset
	    findSubsets(vertex, superSet, k, idx+1, current, solution);
	    current.remove(next);
	    // next vertex is not in the subset
	    findSubsets(vertex, superSet, k, idx+1, current, solution);
	}
	
	public static List<Set<Integer>> findSubsets(int vertex, List<Integer> superSet, int k) {
	    List<Set<Integer>> result = new ArrayList<Set<Integer>>();
	    findSubsets(vertex, superSet, k, 0, new HashSet<Integer>(), result);
	    return result;
	}
	
}
