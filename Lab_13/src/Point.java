public class Point {
	public long x;
	public long y;
	//TODO ?

	public Point(long x, long y){
		this.x = x;
		this.y = y;
	}

	public int det (Point p, Point q){
//		long p1 = p.x*q.y;
//		long p2 = p.y*(this.x);
//		long p3 = q.x*(this.y);
//		long n1 = q.y*(this.x);
//		long n2 = p.x*(this.y);
//		long n3 = p.y*q.x;
//		long det = p1+p2+p3-(n1+n2+n3);
		long det = (p.x*q.y)+(p.y*(this.x))+(q.x*(this.y))-((q.y*(this.x))+(p.x*(this.y))+(p.y*q.x));
		if (det>0)
			return 1;
		else{
			if (det<0)
				return -1;
		}
		return 0;
	}

	public long dist(Point p){
		return (this.x-p.x)*(this.x-p.x)+(this.y-p.y)*(this.y-p.y);
	}

	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
}
