import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PointsReader {
	static LinkedList<Point> load(Scanner scan,int nrOfPoints){
		//DONE
		LinkedList<Point> list = new LinkedList<>();

		for (int i=0; i<nrOfPoints; i++){
			String line=scan.nextLine();
			String coords[]=line.split(" ");
			list.add(new Point(Long.parseLong(coords[0]), Long.parseLong(coords[1])));
		}

		return list;
	}
}
