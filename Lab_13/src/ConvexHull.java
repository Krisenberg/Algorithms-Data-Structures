import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

public class ConvexHull {
	static Point p0;
	
	public static LinkedList<Point> solve(LinkedList<Point> points) {
		//DONE
		LinkedList<Point> sortedList = sortPoints(points);
		System.out.println("POINTS SORTED");
		System.out.println(sortedList);
		LinkedList<Point> convexHull = new LinkedList<>();
		Iterator<Point> iterator = sortedList.iterator();

		convexHull.add(iterator.next());
		convexHull.add(iterator.next());
		convexHull.add(iterator.next());

		while (iterator.hasNext()){
			Point nextPoint = iterator.next();
			ListIterator<Point> convexIter = convexHull.listIterator(convexHull.size());

			while (nextPoint.det(convexIter.previous(), convexIter.previous())>0){
				convexIter.next();
				convexIter.next();
				convexIter.remove();
			}
			convexHull.add(nextPoint);
		}
		return convexHull;
	}

	public static void findFirst(LinkedList<Point> points){
		Iterator<Point> iter = points.iterator();
		Point first = iter.next();

		while (iter.hasNext()){
			Point candidate = iter.next();
			if (candidate.y<=first.y){
				if (candidate.y == first.y){
					if (candidate.x<first.x)
						first = candidate;
				} else {
					first = candidate;
				}
			}
		}
		p0 = first;
	}

	public static LinkedList<Point> sortPoints(LinkedList<Point> points){
		findFirst(points);
		return mergesort(points, 0, points.size()-1);
	}

	private static LinkedList<Point> mergesort(LinkedList<Point> points, int startI, int endI){
		if (startI == endI){
			LinkedList<Point> result = new LinkedList<>();
			result.add(points.get(startI));
			return result;
		}
		int splitI = startI + (endI - startI)/2;
		return merge(mergesort(points, startI, splitI),
					mergesort(points, (splitI+1), endI));
	}

	private static LinkedList<Point> merge(LinkedList<Point> left,LinkedList<Point> right) {
		LinkedList<Point> result = new LinkedList<>();
		Iterator<Point> l = left.iterator();
		Iterator<Point> r = right.iterator();
		Point elemL=null,elemR=null;

		boolean contL,contR;
		if(contL=l.hasNext()) elemL=l.next();
		if(contR=r.hasNext()) elemR=r.next();
		boolean addL, addR;

		while (contL && contR) {
			int det;
			addL=false;
			addR=false;
			if (elemL == p0 || elemR == p0) {
				if (elemL == p0)
					addL=true;
				if (elemR == p0)
					addR=true;
			} else {
				if ((det = elemL.det(p0, elemR)) < 0)
					addL = true;
				else {
					if (det > 0)
						addR=true;
					else {
						if (elemL.dist(p0) >= elemR.dist(p0))
							result.add(elemL);
						else
							result.add(elemR);
						if (contL = l.hasNext())
							elemL = l.next();
						if (contR = r.hasNext())
							elemR = r.next();
					}
				}
			}

			if (addL){
				result.add(elemL);
				if (contL = l.hasNext())
					elemL = l.next();
				else
					result.add(elemR);
			}
			if (addR){
				result.add(elemR);
				if (contR = r.hasNext())
					elemR = r.next();
				else
					result.add(elemL);
			}
		}
		while(l.hasNext()) result.add(l.next());
		while(r.hasNext()) result.add(r.next());
		return result;
	}
}
